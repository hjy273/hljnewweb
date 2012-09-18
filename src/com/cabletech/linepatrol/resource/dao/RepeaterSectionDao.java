package com.cabletech.linepatrol.resource.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.beans.QueryBean;
import com.cabletech.linepatrol.resource.beans.RepeaterSectionBean;
import com.cabletech.linepatrol.resource.model.RepeaterSection;

@Repository
public class RepeaterSectionDao extends HibernateDao<RepeaterSection, String> {

	public List<RepeaterSection> getRepeaterSection(RepeaterSectionBean bean, UserInfo user) {
		String segmentname = bean.getSegmentname();
		String MIS = bean.getMIS();
		String projectName = bean.getProjectName();
		String maintenanceId = bean.getMaintenanceId();
		String[] cableLevels = bean.getCableLevels();
		String[] laytypes = bean.getLaytypes();
		String finishStartTime = bean.getFinishStartTime();
		String finishEndTime = bean.getFinishEndTime();
		String currentState = bean.getCurrentState();
		String hql = "from RepeaterSection t where 1=1";
		if (StringUtils.isNotBlank(segmentname)) {
			hql += " and t.segmentname like '%" + segmentname + "%'";
		}
		if (StringUtils.isNotBlank(maintenanceId)) {
			hql += " and maintenanceId='" + maintenanceId + "'";
		} else {
			if ("1".equals(user.getDeptype())) {
				hql += " and t.maintenanceId in (select contractorID from Contractor where regionid='"
						+ user.getRegionid() + "') ";
			} else {
				hql += " and t.maintenanceId='" + user.getDeptID() + "' ";
			}
		}
		if (StringUtils.isNotBlank(MIS)) {
			hql += " and MIS like '%" + MIS + "%'";
		}
		if (StringUtils.isNotBlank(projectName)) {
			hql += " and projectName like '%" + projectName + "%'";
		}
		if (cableLevels!=null&&cableLevels.length > 0) {
			hql += " and t.cableLevel in (" + arrayParseToStr(cableLevels) + ")";
		}
		if (laytypes!=null&&laytypes.length > 0) {
			hql += " and laytype in (" + arrayParseToStr(laytypes) + ")";
		}
		if (StringUtils.isNotBlank(finishStartTime)) {
			hql += " and finishTime>=to_date('" + finishStartTime + "','yyyy-MM')";
		}
		if (StringUtils.isNotBlank(finishEndTime)) {
			hql += " and finishTime<=to_date('" + finishEndTime + "','yyyy-MM')";
		}
		if (StringUtils.isNotBlank(currentState)) {
			hql += " and currentState='" + currentState + "'";
		}
		hql += " and scrapState='false' and isCheckOut='y'";
		return find(hql);
	}

	public List<RepeaterSection> getRepeaterSection(String filter, String level, UserInfo user) {
		String hql = "from RepeaterSection t ";
		if ("1".equals(user.getDeptype())) {
			hql += "where t.maintenanceId in (select contractorID from Contractor where regionid='"
					+ user.getRegionid() + "') ";
		} else {
			hql += "where t.maintenanceId='" + user.getDeptID() + "' ";
		}
		if (StringUtils.isNotBlank(filter)) {
			hql += " and t.segmentname like '%" + filter + "%'";
		}
		if (StringUtils.isNotBlank(level)) {
			hql += " and t.cableLevel in (" + getInString(level) + ")";
		}
		hql += " and scrapState='false' and isCheckOut='y'";
		return find(hql);
	}

	public String getInString(String str) {
		if (str.indexOf(",") != -1) {
			str = str.replace(",", "','");
		}
		return "'" + str + "'";
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

	public List<RepeaterSection> getWaitApprove(String type, String module) {
		String hql = "select t from RepeaterSection t, AnnexAddOne a where t.kid = a.entityId and a.isUsable = '0' and a.entityType = ? and a.module = ?";
		return find(hql, type, module);
	}

	public String getTrunkName(String id) {
		return get(id).getSegmentname();
	}

	public int countSegmentid(String segmentid) {
		String hql = "select count(*) from RepeaterSection where segmentid like ?";
		return this.findLong(hql, "%" + segmentid + "%").intValue();

	}

	public boolean hasDuplicateTrunk(String trunkNumber) {
		String hql = "from RepeaterSection r where r.segmentid = ?";
		return find(hql, trunkNumber).isEmpty() ? false : true;
	}

	public boolean hasDuplicateTrunk(String trunkNumber, String cableId) {
		String hql = "from RepeaterSection r where r.segmentid = ? ";
		if (StringUtils.isNotBlank(cableId))
			hql += "and r.kid <> '" + cableId + "'";
		return find(hql, trunkNumber).isEmpty() ? false : true;
	}

	public List<RepeaterSection> queryFromAcceptance(QueryBean queryBean) {
		StringBuffer hql = new StringBuffer();
		hql.append("from RepeaterSection t where 1=1 ");
		//查询必须在交维表中的光缆
		hql.append("and exists (select pc from PayCable pc where pc.cableId = t.kid) ");
		List param = new ArrayList();
		if (StringUtils.isNotBlank(queryBean.getName())) {
			hql.append("and t.segmentname like '%");
			hql.append(queryBean.getName());
			hql.append("%' ");
		}
		if (StringUtils.isNotBlank(queryBean.getPassed())) {
			hql.append("and t.isCheckOut = ? ");
			param.add(queryBean.getPassed());
		}
		if (StringUtils.isNotBlank(queryBean.getCurrentState())) {
			hql.append("and t.currentState = ? ");
			param.add(queryBean.getCurrentState());
		}
		if (StringUtils.isNotBlank(queryBean.getBegintime()) && StringUtils.isNotBlank(queryBean.getEndtime())) {
			hql.append("and t.finishtime >= to_Date(?,'yyyy/MM/dd') ");
			hql.append("and t.finishtime <= to_Date(?,'yyyy/MM/dd') ");
			param.add(queryBean.getBegintime());
			param.add(queryBean.getEndtime());
		}
		if (StringUtils.isNotBlank(queryBean.getTimes())) {
			hql.append("and (select count(*) from PayCable pc where pc.cableId = t.kid)=?");
			param.add(Long.valueOf(queryBean.getTimes()));
		}
		return this.find(hql.toString(), param.toArray());
	}

	/**
	 * 
	 * @param args 参数包括：代维单位id,用户所在区域,光缆级别,交维日期
	 */
	public Double getMaintenaceLength(String cableLevel, Object... args) {
		String sql = "select sum(grosslength) length from repeatersection "
				+ " where maintenance_id=? and region=? and finishtime<=to_date(?,'yyyy-mm')" + "and cable_level in ("
				+ cableLevel + ") and ischeckout='y' ";

		Object object = this.getJdbcTemplate().queryForObject(sql, args, Double.class);
		if (object != null) {
			return (Double) object;
		} else {
			return 0.00;
		}
	}

	/**
	 * 通过光缆名称和代维单位编号查询光缆信息
	 * @param segmentname 光缆名称
	 * @param maintenanceId 代维单位编号
	 * @return
	 */
	public List<RepeaterSection> getRepeaterSectionByCondition(String segmentname, String maintenanceId, String subline) {
		StringBuffer sb = new StringBuffer("from RepeaterSection r where r.maintenanceId='");
		sb.append(maintenanceId);
		sb.append("' ");
		if (StringUtils.isNotBlank(segmentname)) {
			sb.append("and r.segmentname like '%");
			sb.append(segmentname);
			sb.append("%'");
		}
		if (StringUtils.isNotBlank(subline)) {
			if (subline.endsWith(",")) {
				subline = subline.substring(0, subline.length() - 1);
			}
			sb.append(" and r.kid not in('");
			sb.append(subline.replaceAll(",", "','"));
			sb.append("')");
		}
		return find(sb.toString());
	}

	/**
	 * 通过编号查询光缆列表信息
	 * @param sbulineid 光缆编号
	 * @return
	 */
	public List<DynaBean> getRepeaterSectionByKids(String sublineid) {
		List<DynaBean> list = null;
		if (StringUtils.isNotBlank(sublineid)) {
			if (sublineid.endsWith(",")) {
				sublineid = sublineid.substring(0, sublineid.length() - 1);
			}
			StringBuffer sb = new StringBuffer("");
			sb
					.append("select r.kid,r.segmentid,r.assetno,r.segmentname,r.fibertype,to_char(r.finishtime,'yyyy-mm-dd') finishtime ");
			sb.append("from repeatersection r where r.kid in('");
			sb.append(sublineid.replaceAll(",", "','"));
			sb.append("')");
			logger.info("查询光缆列表SQL：" + sb.toString());
			list = getJdbcTemplate().queryForBeans(sb.toString());
		}
		return list;
	}

	/**
	 * 光缆重新分配
	 * @param bean
	 */
	public void assignCable(RepeaterSectionBean bean) {
		String targetMaintenanceId = bean.getTargetMaintenanceId();
		String kid = bean.getKid();
		if (kid.endsWith(",")) {
			kid = kid.substring(0, kid.length() - 1);
		}
		StringBuffer sb = new StringBuffer("");
		sb.append("update repeatersection set maintenance_id='");
		sb.append(targetMaintenanceId);
		sb.append("' where kid in('");
		sb.append(kid.replaceAll(",", "','"));
		sb.append("')");
		logger.info("光缆分配SQL：" + sb.toString());
		getJdbcTemplate().execute(sb.toString());
	}

	/**
	 * 通过PDA查找中继段
	 * @param segmentName 中继段名称
	 * @param finishTime 交维时间
	 * @param userInfo 用户对象
	 * @return
	 */
	public List<Map> getRepeaterSectionFromPDA(String segmentName, String finishTime, UserInfo userInfo,
			String contractorId) {
		String sql = "select c.contractorname contractorName,R.SEGMENTNAME segmentName,R.GROSSLENGTH grossLength,R.FIBERTYPE fiberType,to_char(R.FINISHTIME,'yyyy-MM-dd') finishTime,R.MAINTENANCE_ID maintenanceId,D.LABLE lable "
				+ "from RepeaterSection r,dictionary_formitem d,contractorinfo c "
				+ "where r.maintenance_Id in (select contractorID from Contractorinfo where regionid='"
				+ userInfo.getRegionID()
				+ "') and R.MAINTENANCE_ID like '%"
				+ contractorId
				+ "%' and R.SEGMENTNAME like '%" + segmentName + "%'";
		if (segmentName.equals("")) {
			sql += " and to_char(r.finishTime,'yyyy-MM')='" + finishTime + "'";
		} else {
			sql += " and to_char(r.finishTime,'yyyy-MM') like '%" + finishTime + "%'";
		}
		sql += " and D.ASSORTMENT_ID='cabletype' and D.CODE=R.CABLE_LEVEL and c.contractorid=r.maintenance_id";

		return this.getJdbcTemplate().queryForList(sql);
	}

	public List<RepeaterSection> queryByTrunkIds(String ids) {
		String hql = "from RepeaterSection where id in (" + ids + ")";
		return this.find(hql);
	}
}
