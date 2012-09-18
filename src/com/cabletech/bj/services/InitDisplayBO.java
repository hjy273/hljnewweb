package com.cabletech.bj.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.bj.dao.FrameCommonDao;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.planstat.beans.PatrolStatConditionBean;
import com.cabletech.planstat.services.PlanProgressBO;
import com.cabletech.sysmanage.domainobjects.Notice;

public class InitDisplayBO extends BaseBisinessObject {
	public static final int MAIL_MAX_NUM = 5;
	private Logger logger = Logger.getLogger("InitDisplayBO");
	private FrameCommonDao dao = new FrameCommonDao();

	/**
	 * 该方法能够将公告的相关信息，进行组合，组合后的字符格式为 <li><a href="连接地址" >通知标题 通知内容摘要 发表日期</a></li>
	 * <br>
	 * 例如： <li><a href="/WebApp/NoticeAction.do?method=shownotice&id=00001" >通知
	 * 上午10点钟开会... 2007-07-05</a></li> <li><a
	 * href="/WebApp/NoticeAction.do?method=shownotice&id=00002" >系统更新
	 * 巡检计划模块已经更新.... 发表日期</a></li> 组合成上面的字符串并返回该字符串。
	 * 
	 * @param type
	 * @return
	 */
	// public String getNoticeInfoForNew(String rootregionid, String regionid,
	// String type) {
	// // 通过getNoticeList()方法取得公告列表
	// List noticelist = getNoticeList(rootregionid, regionid, type);
	// String strNotice = assembledNoticeForNew(noticelist);
	// logger.info("公告 ：" + strNotice + "size :" + noticelist.size());
	// if (strNotice == null)
	// strNotice = "";
	// return strNotice;
	// }

	// public Notice message(String rootregionid, UserInfo user) {
	// String sql =
	// "select  id,title, content from notice  where issuedate >= hoursbeforenow(12) and isissue='y' and  grade='重要' and id not in(select noticeid from notice_isread where userid='"
	// + user.getUserID()
	// + "') and regionid in ('"
	// + rootregionid
	// + "','" + user.getRegionID() + "') order by issuedate asc ";
	// QueryUtil query = null;
	// Notice notice = null;
	// logger.info("sql:" + sql);
	// try {
	// query = new QueryUtil();
	// ResultSet rs = query.executeQuery(sql);
	// notice = new Notice();
	// while (rs.next()) {
	// notice.setId(rs.getString("id"));
	// notice.setTitle(rs.getString("title"));
	// notice.setContent(rs.getString("content"));
	// }
	//
	// // rs.close();
	// // query.close();
	// return notice;
	// } catch (Exception e) {
	// logger.error(e);
	// e.printStackTrace();
	// return null;
	// }
	// }

	// 将开始的
	public List getShallStartPlan(UserInfo user) {
		String sql = "select p.id,p.planname,to_char(p.begindate,'YYYY-MM-DD') begindate,to_char(p.enddate,'YYYY-MM-DD') enddate,m.patrolname"
				+ " from plan p ,patrolmaninfo m where p.executorid = m.patrolid and p.begindate > sysdate and p.begindate < sysdate+5";
		if (user.getType().equals("12")) {
			sql += " and m.regionid='" + user.getRegionid() + "'";
		}
		if (user.getType().equals("22")) {
			sql += " and m.parentid='" + user.getDeptID() + "'";
		}
		List shallStartPlan = new ArrayList();
		QueryUtil query;
		try {
			query = new QueryUtil();
			shallStartPlan = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return shallStartPlan;
	}

	// 正在进行的
	public List getProgressPlan(UserInfo user) {
		List progressPlan = new ArrayList();
		PlanProgressBO bo = new PlanProgressBO();
		PatrolStatConditionBean bean = new PatrolStatConditionBean();
		bean.setConID(user.getDeptID());
		progressPlan = bo.getPlanProgressList(user, bean);
		return progressPlan;
	}

	// 已结束的
	public List getFulfillPlan(UserInfo user) {
		String sql = "select p.id,p.planname,to_char(p.begindate,'YYYY-MM-DD') begindate,to_char(p.enddate,'YYYY-MM-DD') enddate"
				+ " ,m.patrolname,ps.patrolp from plan p,plan_stat ps,patrolmaninfo m"
				+ " where p.id=ps.planid and m.patrolid=ps.executorid and p.enddate >= sysdate-5 and p.enddate <sysdate";
		if (user.getType().equals("12")) {
			sql += " and m.regionid='" + user.getRegionID() + "'";
		}
		if (user.getType().equals("22")) {
			sql += " and m.parentid='" + user.getDeptID() + "'";
		}
		List fulfillPlan = new ArrayList();
		QueryUtil query;
		try {
			query = new QueryUtil();
			fulfillPlan = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return fulfillPlan;
	}

	private String assembledNoticeForNew(List noticelist) {
		StringBuffer noticestr = new StringBuffer();
		if (noticelist != null) {
			noticestr
					.append("<table width=\"98%\" border=\"0\" align=\"center\">");
			for (int i = 0; i < noticelist.size(); i++) {
				Notice notice = (Notice) noticelist.get(i);
				String color = "";
				if (notice.getGrade().indexOf("重要") != -1) {
					color = "red";
				}

				noticestr.append("<tr>");
				noticestr.append("<td width=\"150\">");
				noticestr.append(notice.getIssuedate().toString());
				noticestr.append("【" + notice.getType() + "】");
				noticestr.append("</td>");
				noticestr.append("<td>");
				noticestr.append("<span class=\"txt_blue\">");
				noticestr.append("<a href=\"javascript:open_notify('"
						+ notice.getId() + "','0');\"");
				noticestr.append(" style=\"color:" + color + "\">");
				noticestr.append(notice.getTitle() + "</a></span>");
				noticestr.append("</td>");
				noticestr.append("</tr>");
			}
			noticestr.append("</table>");
		}
		return noticestr.toString();
	}

	private String getLimitString(String src, int limitlen) {
		final int srcLen = src.length();
		if (srcLen > limitlen) {
			src = src.substring(0, limitlen);
		}
		return src;

	}

	/**
	 * 获取公告
	 * 
	 * @param regionid
	 * @return
	 */
	// private List getNoticeList(String rootregionid, String regionid, String
	// type) {
	// String sql =
	// "select ID,TITLE,CONTENT,ISISSUE,grade,type,fileinfo,REGIONID,ISSUEPERSON,to_char(ISSUEDATE,'yyyy-mm-dd') issuedate from (select * from NOTICE where isissue='y' and regionid in ('"
	// + rootregionid
	// + "','"
	// + regionid
	// + "') order by issuedate desc) notice where rownum <= ";
	// String num = "6";
	// logger.info("type " + type);
	// if (type.equals("all")) {
	// num = "25 ";
	// }
	// sql += num;
	// QueryUtil query;
	// logger.info("sql:" + sql);
	// List noticeList = new ArrayList();
	// try {
	// query = new QueryUtil();
	// Notice notice = null;
	// ResultSet rs = query.executeQuery(sql);
	// while (rs.next()) {
	// notice = new Notice();
	// notice.setId(rs.getString("id"));
	// notice.setTitle(rs.getString("title"));
	// notice.setContent(rs.getString("content"));
	// notice.setIssuedate(rs.getDate("issuedate"));
	// notice.setGrade(rs.getString("grade"));
	// notice.setType(rs.getString("type"));
	// notice.setFileinfo(rs.getString("fileinfo"));
	// noticeList.add(notice);
	// }
	// return noticeList;
	// } catch (Exception e) {
	// logger.warn(e);
	// e.printStackTrace();
	// return null;
	// }
	// }

	public List getAddressList(UserInfo user, String departName,
			String userName, String mobile) {
		return dao.getAddressList(user, departName, userName, mobile);
	}
	/**
	 * 生成会议日程脚步。
	 * @return
	 */
	public String getMeetInfo() {
		String meetInfo = "";
		List meetInfoList = dao.getMeetInfo();
		
		Map<String,DynaBean> tmpMap = new HashMap<String, DynaBean>();
		DynaBean bean;
		DynaBean tempBean;
		for (int i = 0; meetInfoList != null && i < meetInfoList.size(); i++) {
			bean = (DynaBean) meetInfoList.get(i);
			if(tmpMap.containsKey(bean.get("meet_time"))){
				tempBean = tmpMap.get(bean.get("meet_time"));
				tempBean.set("title", bean.get("title")+"<br/>"+tempBean.get("title"));
				tmpMap.put(bean.get("meet_time").toString(),tempBean );
			}else{
				tmpMap.put(bean.get("meet_time").toString(), bean);
			}
		}
		String meetTimeStr;
		Date meetTime;
		Calendar calendar = new GregorianCalendar();
		calendar = DateUtils.truncate(calendar, Calendar.DATE);
		String highLightStyle = "";
		int i=0;
		for(Object obj:tmpMap.keySet()){
			bean = tmpMap.get(obj);
			meetTimeStr = (String) bean.get("meet_time");
			meetTime = DateUtil.StringToUtilDate(meetTimeStr, "yyyyMMdd");
			if (meetTime.getTime() > calendar.getTimeInMillis()) {
				highLightStyle = "highlight3";
			} else if (meetTime.getTime() < calendar.getTimeInMillis()) {
				highLightStyle = "highlight1";
			} else {
				highLightStyle = "highlight2";
			}
			if (bean != null) {
				meetInfo += meetTimeStr + " : { ";
				meetInfo += " klass : \"" + highLightStyle + "\", ";
				meetInfo += " tooltip : \"<div style='text-align: center'>%Y/%m/%d <br /> ";
				meetInfo += bean.get("title");
				meetInfo += " </div>\"";
				meetInfo += " } ";
				if (i != tmpMap.size() - 1) {
					meetInfo += " , ";
				}
			}
			i++;
		}
		
		return meetInfo;
	}

	public List queryMailLatestList(UserInfo userInfo) {
		// TODO Auto-generated method stub
		String condition = " and mail_user_id='" + userInfo.getUserID() + "' ";
		List mailList = dao.queryMailList(condition);
		List list;
		if (mailList != null && mailList.size() > MAIL_MAX_NUM) {
			list = mailList.subList(0, MAIL_MAX_NUM - 1);
		} else {
			list = mailList;
		}
		return list;
	}
}
