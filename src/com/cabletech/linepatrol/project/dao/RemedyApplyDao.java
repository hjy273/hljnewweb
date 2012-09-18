package com.cabletech.linepatrol.project.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.overhaul.beans.OverHaulQueryBean;
import com.cabletech.linepatrol.project.constant.ExecuteCode;
import com.cabletech.linepatrol.project.domain.ProjectRemedyApply;

@Repository
public class RemedyApplyDao extends HibernateDao<ProjectRemedyApply, String> {
	/**
	 * <br>
	 * ����:���ָ���û��ĵ绰���� <br>
	 * ����:�û���� <br>
	 * ����:ָ���û��ĵ绰����
	 * */
	public String getSendPhone(String userid) {
		String sql = "select phone from userinfo where userid='" + userid + "'";
		List list = super.getJdbcTemplate().queryForBeans(sql);
		if (list != null && list.size() > 0) {
			DynaBean bean = (DynaBean) list.get(0);
			return (String) bean.get("phone");
		}
		return null;
	}

	/**
	 * ִ�б�������������Ϣ
	 * 
	 * @param apply RemedyApply ����������Ϣ
	 * @param actionType String �������ͣ�{insert,update}
	 * @return String ���涯������
	 * @throws Exception
	 */
	public boolean saveOneApply(ProjectRemedyApply apply, String actionType) {
		if (ExecuteCode.INSERT_ACTION_TYPE.equals(actionType)) {
			apply.setId(null);
			save(apply);
			return true;
		}
		if (ExecuteCode.UPDATE_ACTION_TYPE.equals(actionType)) {
			save(apply);
			return true;
		}
		return false;
	}

	/**
	 * ִ�и��������������ж����������Ƿ����
	 * @param applyId String ����������
	 * @return boolean ���������Ƿ����
	 * @throws Exception
	 */
	public boolean judgeExistApply(String applyId) throws Exception {
		String condition = " and remedy.id='" + applyId + "'";
		List list = queryList(condition);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	public ProjectRemedyApply viewOneApply(String applyId) {
		return this.findUniqueByProperty("id", applyId);
	}

	/**
	 * ִ��ɾ������������Ϣ
	 * @param apply RemedyApply ����������Ϣ
	 * @return String ɾ����������
	 */
	public boolean deleteOneApply(ProjectRemedyApply apply) {
		super.delete(apply);
		return true;
	}

	/**
	 * ִ���ж��Ƿ������ͬ������������Ϣ
	 * @param apply RemedyApply ����������Ϣ
	 * @return boolean �Ƿ������ͬ������������Ϣ
	 */
	public boolean judgeExistSameApply(ProjectRemedyApply apply) {
		String condition = " and remedy.projectname='" + apply.getProjectName()
				+ "' ";
		condition += " and remedy.id<>" + apply.getId();
		List list = queryList(condition);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * ����������������ںʹ�ά��λ����ж��Ƿ�������µĲ����̵���Ϣ
	 * 
	 * @param apply
	 *            RemedyApply ����������Ϣ
	 * @return boolean �Ƿ�������µĲ����̵���Ϣ
	 * @throws Exception
	 */
	public boolean judgeExistMtUsed(ProjectRemedyApply apply) {
		String date = DateUtil.DateToString(apply.getRemedyDate());
		String sql = "select t.contractorid from LP_MT_USED t,userinfo u ";
		sql = sql + " where t.creator=u.userid and u.deptid='"
				+ apply.getContractorId() + "' ";
		sql = sql + " and t.createtime=add_months(last_day(to_date('" + date
				+ "','yyyy-mm-dd'))+1,-2) ";
		logger.info("Execute sql:" + sql);
		List list = super.getJdbcTemplate().queryForBeans(sql);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * ִ�и��ݲ�ѯ������ȡ����������Ϣ�б�
	 * 
	 * @param condition
	 *            String ��ѯ����
	 * @return List ����������Ϣ�б�
	 * @throws Exception
	 */
	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = "select remedy.id,remedy.remedycode,remedy.projectname, ";
		sql += " to_char(remedy.remedydate,'yyyy-mm-dd') as remedydate,to_char(remedy.totalfee) as totalfee,to_char(remedy.mtotalfee) as mtotalfee, ";
		sql += " remedy.state,'' as flow_state, ";
		// sql +=
		// " decode(balance.totalfee,null,'0',balance.totalfee) as balance_fee, ";
		sql += " '' as allow_edited,'' as allow_deleted,'' as allow_approved ";
		sql += " from LP_REMEDY remedy,contractorinfo c,region r ";
		sql += " where remedy.contractorid=c.contractorid and remedy.regionid=r.regionid ";
		sql = sql + condition;
		List list = super.getJdbcTemplate().queryForBeans(sql);
		return list;
	}
	
	public List<ProjectRemedyApply> getHandeledWorks(UserInfo userInfo){
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct a from ProjectRemedyApply a,ProcessHistory ph where a.id = ph.objectId and ph.objectType = '");
		sb.append(ModuleCatalog.PROJECT);
		sb.append("' and ph.operateUserId = '");
		sb.append(userInfo.getUserID());
		sb.append("' ");
		return find(sb.toString());
	}
	
	public List<ProjectRemedyApply> getProjectForOverHaul(String deptId, OverHaulQueryBean queryBean){
		StringBuffer sb = new StringBuffer();
		List<String> param = new ArrayList<String>();
		sb.append("from ProjectRemedyApply p where p.state in ('30','40') and p.id not in (select ohp.projectId from OverHaulProject ohp ");
		if(StringUtils.isNotBlank(queryBean.getApplyId())){
			sb.append("where ohp.overHaulApply <> (select o from OverHaulApply o where o.id = ?) ");
			param.add(queryBean.getApplyId());
		}
		sb.append(") and p.contractorId = ?");
		param.add(deptId);
		if(StringUtils.isNotBlank(queryBean.getProjectName())){
			sb.append("and p.projectName like '%");
			sb.append(queryBean.getProjectName());
			sb.append("%' ");
		}
		if(StringUtils.isNotBlank(queryBean.getStartTime())){
			sb.append("and p.remedyDate >= to_Date(?,'yyyy/MM/dd')");
			param.add(queryBean.getStartTime());
		}
		if(StringUtils.isNotBlank(queryBean.getEndTime())){
			sb.append("and p.remedyDate <= to_Date(?,'yyyy/MM/dd')");
			param.add(queryBean.getEndTime());
		}
		return find(sb.toString(), param.toArray());
	}
	
	/**
	 * //ȥ�����ĺ�ת��������˺��Լ�Ϊ�����˵����
	 * @param approverId  ת����
	 * @param userId	     ��ǰ�û�
	 * @param objectId	  	����id
	 * @param objectType	��������
	 * @return
	 */
	public String getReaderByCondition(String approverId,String userId,String objectId, String objectType) {
		String readers="";
		String sql="select APPROVER_ID approverId FROM LP_REPLY_APPROVER WHERE FINISH_READED<>'1' AND APPROVER_ID NOT IN ('"+approverId+"','"+userId+"') AND OBJECT_ID ='"+objectId+"' AND OBJECT_TYPE='"+objectType+"' AND APPROVER_TYPE='03'";
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		list=this.getJdbcTemplate().queryForList(sql);
		for(Map<String,String> reader:list){
			readers+=reader.get("approverId")+",";
		}
		if(!"".equals(readers)){
			readers=readers.substring(0, readers.length()-1);
		}
		return readers;
	}
	
	public List<ProjectRemedyApply> getRemedyApplyByIds(String ids){
		String sql="from ProjectRemedyApply where id in("+ids+")";
		return this.find(sql);
	}
	
	/**
	 * �����û���ѯ��ά
	 * @param user
	 * @return
	 */
	public List<Contractor> getContractors(UserInfo user){
		String hql = " from Contractor c  where c.regionid='"+user.getRegionid()+"'";
		return this.getHibernateTemplate().find(hql);
	}
	
	/**
	 * �ı�����״̬
	 * @param id
	 * @param state
	 */
	public void changeStateById(String id, String state) {
		ProjectRemedyApply apply = findByUnique("id", id);
		apply.setState(state);
		save(apply);
	}
}
