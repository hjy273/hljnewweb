package com.cabletech.linepatrol.resource.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.resource.beans.PipeBean;
import com.cabletech.linepatrol.resource.model.Pipe;

@Repository
public class PipeDao extends HibernateDao<Pipe, String>{

	public List<Pipe> getPipeByFilter(String filter,UserInfo user){
		String hql = "from Pipe t where t.maintenanceId='"+user.getDeptID()+"' and t.pipeAddress like '%" + filter + "%'";
		return find(hql);
	}
	
	public List<Pipe> getAllByHold(UserInfo user, PipeBean pipeBean) {
		String pipeAddress = pipeBean.getPipeAddress();
		String MIS = pipeBean.getMIS();
		String workName = pipeBean.getWorkName();
		String pipeLine = pipeBean.getPipeLine();
		String maintenanceId = pipeBean.getMaintenanceId();
		String finishStartTime = pipeBean.getFinishStartTime();
		String finishEndTime = pipeBean.getFinishEndTime();
		String pipeType = arrayParseToStr(pipeBean.getPipeTypes());
		String routeRes = arrayParseToStr(pipeBean.getRouteRess());
		String hql = "from Pipe t where 1=1";
		if (StringUtils.isNotBlank(maintenanceId)) {
			hql+=" and t.maintenanceId='"+maintenanceId+"'";
		} else {
			if ("1".equals(user.getDeptype())) {
				hql += "and t.maintenanceId in (select contractorID from Contractor where regionid='"
						+ user.getRegionid() + "')";
			} else {
				hql += "and t.maintenanceId='" + user.getDeptID() + "' ";
			}
		}
		if(StringUtils.isNotBlank(MIS)){
			hql+=" and t.MIS like '%"+MIS+"%'";
		}
		if(StringUtils.isNotBlank(pipeAddress)){
			hql+=" and t.pipeAddress like '%"+pipeAddress+"%'";
		}
		if(StringUtils.isNotBlank(workName)){
			hql+=" and t.workName like '%"+workName+"%'";
		}
		if(StringUtils.isNotBlank(pipeLine)){
			hql+=" and t.pipeLine like '%"+pipeLine+"%'";
		}
		if(StringUtils.isNotBlank(finishStartTime)){
			hql+=" and t.finishTime>=to_date('"+finishStartTime+"','yyyy-MM')";
		}
		if(StringUtils.isNotBlank(finishEndTime)){
			hql+=" and t.finishTime<=to_date('"+finishEndTime+"','yyyy-MM')";
		}
		if(StringUtils.isNotBlank(pipeType)){
			hql+=" and t.pipeType in ("+pipeType+")";
		}
		if(StringUtils.isNotBlank(routeRes)){
			hql+=" and t.routeRes in ("+routeRes+")";
		}
		return find(hql);
	}
	public String arrayParseToStr(String[] str) {
		String sqlstr = "";
		if (str != null && str.length > 0) {
			for (int i = 0; i < str.length; i++) {
				if (i == 0) {
					sqlstr += "'" + str[i] + "'";
				} else {
					sqlstr += ",'" + str[i] + "'";
				}
			}
		}
		return sqlstr;
	}
	public boolean hasDuplicatePipe(String pipeNumber){
		String hql = "from Pipe p where p.assetno = ?";
		return find(hql, pipeNumber).isEmpty() ? false : true;
	}
	
	
	/**
	 * 通过管道名称和代维单位编号查询管道信息
	 * @param workName 工程名称
	 * @param maintenanceId 代维单位编号
	 * @return
	 */
	public List<Pipe> getPipeByCondition(String workName, String maintenanceId, String subline) {
		StringBuffer sb = new StringBuffer("from Pipe r where r.maintenanceId='");
		sb.append(maintenanceId);
		sb.append("' ");
		if(StringUtils.isNotBlank(workName)){
			sb.append("and (r.workName like '%");
			sb.append(workName);
			sb.append("%' or r.pipeAddress like '%");
			sb.append(workName);
			sb.append("%')");
		}
		if(StringUtils.isNotBlank(subline)){
			if(subline.endsWith(",")){
				subline = subline.substring(0, subline.length() - 1);
			}
			sb.append(" and r.id not in('");
			sb.append(subline.replaceAll(",", "','"));
			sb.append("')");
		}
		return find(sb.toString());
	}
	
	/**
	 * 通过编号查询管道列表信息
	 * @param sbulineid 管道编号
	 * @return
	 */
	public List<DynaBean> getPipeByKids(String sublineid) {
		List<DynaBean> list = null;
		if(StringUtils.isNotBlank(sublineid)){
			if(sublineid.endsWith(",")){
				sublineid = sublineid.substring(0, sublineid.length() - 1);
			}
			StringBuffer sb = new StringBuffer("");
			sb.append("select r.id,r.work_name,r.pipe_address,to_char(r.pipe_length_channel) pipe_length_channel,");
			sb.append("to_char(r.pipe_length_hole) pipe_length_hole,to_char(r.mobile_scare_channel) mobile_scare_channel,");
			sb.append("to_char(r.mobile_scare_hole) mobile_scare_hole,to_char(r.finishtime,'yyyy-mm-dd') finishtime ");
			sb.append("from pipeline r where r.id in('");
			sb.append(sublineid.replaceAll(",", "','"));
			sb.append("')");
			logger.info("查询光缆列表SQL：" + sb.toString());
			list = getJdbcTemplate().queryForBeans(sb.toString());
		}
		return list;
	}
	
	/**
	 * 管道重新分配
	 * @param bean
	 */
	public void assignPipe(PipeBean bean) {
		String targetMaintenanceId = bean.getTargetMaintenanceId();
		String kid = bean.getId();
		if(kid.endsWith(",")){
			kid = kid.substring(0, kid.length() - 1);
		}
		StringBuffer sb = new StringBuffer("");
		sb.append("update pipeline set maintenance_id='");
		sb.append(targetMaintenanceId);
		sb.append("' where id in('");
		sb.append(kid.replaceAll(",", "','"));
		sb.append("')");
		logger.info("管道分配SQL：" + sb.toString());
		getJdbcTemplate().execute(sb.toString());
	}
	
	
	/**
	 * 通过PDA查询管道
	 * @param pipeName 管道名称
	 * @param userInfo
	 * @return
	 */
	public List<Map> getPipeFromPAD(String pipeAddress, UserInfo userInfo, String finishTime,
			String contractorId) {
		String sql = "select c.contractorname contractorName,P.ID id,P.PIPE_ADDRESS pipeAddress,P.pipe_length_channel pipeLengthChannel,p.pipe_length_hole pipeLengthHole,to_char(P.FINISHTIME,'yyyy-MM-dd') finishTime,P.MAINTENANCE_ID maintenanceId, D.LABLE lable "
				+ "from PIPELINE p,dictionary_formitem d,contractorinfo c  where p.MAINTENANCE_ID in (select CONTRACTORID from Contractorinfo  where regionid='"
				+ userInfo.getRegionID()
				+ "') and p.PIPE_ADDRESS like '%"
				+ pipeAddress
				+ "%'";
		if(pipeAddress.equals("")){
				sql+=" and to_char(p.finishtime, 'yyyy-MM')='"+ finishTime+ "'";
		}else{
			sql+=" and to_char(p.finishtime, 'yyyy-MM') like '%"+ finishTime+ "%'";
		}
		
		sql+=" and D.ASSORTMENT_ID='property_right' and D.CODE=P.ROUTE_RES and p.maintenance_Id like '%"
				+ contractorId + "%' and c.contractorid=p.maintenance_id";
		System.out.println(sql);
		return this.getJdbcTemplate().queryForList(sql);
	}
	
}
