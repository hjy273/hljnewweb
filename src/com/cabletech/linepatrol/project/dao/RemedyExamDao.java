package com.cabletech.linepatrol.project.dao;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.project.beans.RemedyExamBean;
import com.cabletech.linepatrol.project.constant.RemedyConstant;
import com.cabletech.linepatrol.project.domain.ProjectRemedyApply;

/**
 * 工程管理日常考核操作类
 * @author liusq
 *
 */
@Repository
public class RemedyExamDao extends HibernateDao<ProjectRemedyApply, String> {

	/**
	 * 根据条件查询待考核的工程列表
	 * @param bean
	 * @param userInfo
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<DynaBean> getExamListByCondition(RemedyExamBean bean, UserInfo userInfo) {
		StringBuffer sb = new StringBuffer("");
		sb.append("select t.id,t.remedycode,t.projectname,t.contractorid,c.contractorname,t.remedyaddress,");
		sb.append("to_char(t.remedydate,'yyyy-mm-dd hh24:mi:ss') remedydate,t.remedyreson,");
		sb.append("t.remedysolve,t.creator,u.username,''||t.totalfee totalfee ");
		sb.append("from lp_remedy t,contractorinfo c,userinfo u ");
		sb.append("where t.contractorid=c.contractorid and t.creator=u.userid ");
		sb.append("and t.state='");
		sb.append(RemedyConstant.EVALUATE);
		sb.append("' ");
		if(StringUtils.isNotBlank(bean.getProjectName())){
		sb.append("and t.projectname like '%");
		sb.append(bean.getProjectName());
		sb.append("%' ");
		}
		sb.append("order by t.id desc");
		return getJdbcTemplate().queryForBeans(sb.toString());
	}
}
