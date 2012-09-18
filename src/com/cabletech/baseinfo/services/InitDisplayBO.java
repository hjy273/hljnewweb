package com.cabletech.baseinfo.services;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.HoldSim;
import com.cabletech.baseinfo.domainobjects.OnlinePatrolMan;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.sysmanage.domainobjects.Notice;

public class InitDisplayBO extends BaseBisinessObject {
	private Logger logger = Logger.getLogger("InitDisplayBO");

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
	public String getNoticeInfo(String rootregionid, String regionid,
			String type) {
		// 通过getNoticeList()方法取得公告列表
		List noticelist = getNoticeList(rootregionid, regionid, type);
		String strNotice = assembledNotice(noticelist);
		logger.info("公告 ：" + strNotice + "size :" + noticelist.size());
		return strNotice;
	}

	private String assembledNotice(List noticelist) {
		StringBuffer noticestr = new StringBuffer();
		for (int i = 0; i < noticelist.size(); i++) {
			Notice notice = (Notice) noticelist.get(i);
			String color = "";
			if (notice.getGrade().indexOf("重要") != -1) {
				color = "red";
			}
			noticestr.append("<li style=\"left:10px;color:" + color);

			noticestr.append("\"> <a style=\"color:" + color
					+ "\" href=\"javascript:open_notify('" + notice.getId()
					+ "','0');");
			noticestr.append("\">");
			noticestr.append("【" + notice.getType() + "】 【" + notice.getTitle()
					+ "】   " + getLimitString(notice.getContentString(), 18) + "... "
					+ notice.getIssuedate().toString());
			noticestr.append("</a></li>\n");
		}
		for (int j = 6 - noticelist.size(); j > 0; j--) {
			noticestr.append("<br>");
		}
		return noticestr.toString();
	}

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
	public String getNoticeInfoForNew(String rootregionid, String regionid,
			String type) {
		// 通过getNoticeList()方法取得公告列表
		List noticelist = getNoticeList(rootregionid, regionid, type);
		String strNotice = assembledNoticeForNew(noticelist);
		logger.info("公告 ：" + strNotice + "size :" + noticelist.size());
		return strNotice;
	}

	private String assembledNoticeForNew(List noticelist) {
		StringBuffer noticestr = new StringBuffer();
		if (noticelist != null) {
			noticestr.append("<table width=\"98%\" border=\"0\" align=\"center\">");
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
	private List getNoticeList(String rootregionid, String regionid, String type) {
		String sql = "select ID,TITLE,CONTENT,ISISSUE,grade,type,fileinfo,REGIONID,ISSUEPERSON,to_char(ISSUEDATE,'yyyy-mm-dd') issuedate from (select * from NOTICE where isissue='y' and regionid in ('"
				+ rootregionid
				+ "','"
				+ regionid
				+ "') order by issuedate desc) notice where rownum <= ";
		String num = "6";
		logger.info("type " + type);
		if (type.equals("all")) {
			num = "100";
		}
		sql += num;
		QueryUtil query;
		logger.info("sql:" + sql);
		List noticeList = new ArrayList();
		try {
			query = new QueryUtil();
			Notice notice = null;
			ResultSet rs = query.executeQuery(sql);
			while (rs.next()) {
				notice = new Notice();
				notice.setId(rs.getString("id"));
				notice.setTitle(rs.getString("title"));
//				notice.setContent(rs.getString("content"));
				notice.setIssuedate(rs.getDate("issuedate"));
				notice.setGrade(rs.getString("grade"));
				notice.setType(rs.getString("type"));
				notice.setFileinfo(rs.getString("fileinfo"));
				noticeList.add(notice);
			}
			return noticeList;
		} catch (Exception e) {
			logger.warn(e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 取得巡检人员信息 巡检 1 采集2 盯防3
	 * 
	 * @param userInfo
	 * @return
	 */
	public String getPatrolManStr(UserInfo userInfo) {
		// 取得1、区域 2、下边的代维公司 3、代公司巡检人员，组成的数据对象
		OnlinePatrolMan onlineman = getOnlinePatrolMan(userInfo);
		// 组合字符串。
		String onlinemanstr = assembledOnline(onlineman, userInfo.getType());
		return onlinemanstr;
	}

	private String assembledOnline(OnlinePatrolMan onlineman, String type) {
		StringBuffer str = new StringBuffer();
		String style = "class=\"Opened\"";
		if (type.equals("11")) {
			style = "";
		}
		// 全省
		str.append("<li class=\"Opened\">" + onlineman.getName() + "\n");
		// 地区
		str.append("<ul>");
		for (int i = 0; i < onlineman.getChild().size(); i++) {
			OnlinePatrolMan region = (OnlinePatrolMan) onlineman.getChild()
					.get(i);
			if (onlineman.getId().indexOf("0000") != -1) {
				str.append("<li>" + region.getName() + "\n");
			} else {

				str.append("<li " + style + ">" + region.getName() + " ("
						+ region.getChild().size() + ") \n");
			}
			// 代为单位
			str.append("<ul>");
			for (int j = 0; j < region.getChild().size(); j++) {
				OnlinePatrolMan con = (OnlinePatrolMan) region.getChild()
						.get(j);
				if (con.getChild().size() != 0) {
					if (onlineman.getId().indexOf("0000") != -1) {

						str.append("<li>" + con.getName() + " ("
								+ con.getChild().size() + ") \n");
						// 巡检人员
						str.append("<ul>");
						for (int k = 0; k < con.getChild().size(); k++) {
							OnlinePatrolMan man = (OnlinePatrolMan) con
									.getChild().get(k);
							if (man.getChild().size() != 0) {
								str.append("<li class=\"Child\">"
										+ man.getName());
								str.append("\n<br>"
										+ getSimInfo(man.getChild()));
								str.append("</li>\n");
							}
						}
						str.append("</ul>");
					} else {
						str.append("<li class=\"Child\">" + con.getName()
								+ "\n");
						str.append("\n<br>" + getSimInfo(con.getChild()));
					}

					str.append("</li>");
				}
			}
			str.append("</ul>");
			str.append("</li>");
		}
		str.append("</ul>");
		str.append("</li>");
		return str.toString();
	}

	private String getSimInfo(List man) {
		String str = "";
		for (int l = 0; l < man.size(); l++) {
			Object obj = (Object) man.get(l);
			HoldSim sim = null;
			if (obj instanceof HoldSim) {
				sim = (HoldSim) obj;
				str += "&nbsp;&nbsp;&nbsp;&nbsp;Sim卡号：" + sim.getSimid()
						+ "&nbsp;&nbsp;巡检状态：" + sim.getOperation()
						+ "&nbsp;&nbsp;最近活动时间：" + sim.getLately() + "\n<br>";
			} else {
				break;
			}

		}
		// logger.info(str);
		return str;
	}

	// 1、区域
	private OnlinePatrolMan getOnlinePatrolMan(UserInfo userInfo) {
		String sql = "SELECT RegionID,regionname FROM region where regionid not like '%11111' CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"
				+ userInfo.getRegionID() + "'";

		sql += " order by regionid";
		QueryUtil query;
		OnlinePatrolMan onlineResult = new OnlinePatrolMan();
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			int i = 0;
			OnlinePatrolMan chilRegion = null;
			while (rs.next()) {
				if (i == 0) {// 顶级区域
					onlineResult.setId(rs.getString("regionid"));
					onlineResult.setName(rs.getString("regionname"));
				} else {// 子区域
					chilRegion = new OnlinePatrolMan();
					chilRegion.setId(rs.getString("regionid"));
					chilRegion.setName(rs.getString("regionname"));
					onlineResult.addChild(chilRegion);
				}
				i++;
			}
			rs.close();
			query.close();
			// 代维单位，
			onlineResult = getContractor(onlineResult, userInfo);

		} catch (Exception e) {
			logger.warn(e);
			e.printStackTrace();

		}
		return onlineResult;
	}

	// 2、下边的代维公司
	private OnlinePatrolMan getContractor(OnlinePatrolMan onlineResult,
			UserInfo userInfo) {
		String sql = "";
		String condition = "";
		int size = onlineResult.getChild().size();// 获取子区域数
		if (userInfo.getDeptype().equals("2")) {
			condition = " and contractorid = '" + userInfo.getDeptID() + "'";
		}
		if (size == 0) {// 子区域数等于0,说明其下没有子区域，
			sql = "select contractorid,contractorname from CONTRACTORINFO where state is null and depttype='2' and regionid='"
					+ onlineResult.getId() + "'";
			sql = sql + condition; // if（是代维用户）加上此条件。
			onlineResult = queryCon(onlineResult, sql);
		} else {
			for (int i = 0; i < size; i++) {
				OnlinePatrolMan onlineregion = (OnlinePatrolMan) onlineResult
						.getChild().get(i);
				sql = "select contractorid,contractorname from CONTRACTORINFO where state is null and depttype='2' and regionid='"
						+ onlineregion.getId() + "'";
				sql = sql + condition;
				onlineregion = queryCon(onlineregion, sql);
			}
		}
		return onlineResult;
	}

	/**
	 * 获取巡检人员
	 * 
	 * @param onlineregion
	 * @param sql
	 * @return
	 */
	private OnlinePatrolMan queryCon(OnlinePatrolMan onlineregion, String sql) {
		QueryUtil query = null;
		OnlinePatrolMan onlineCon = null;
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while (rs.next()) {
				onlineCon = new OnlinePatrolMan();
				onlineCon.setId(rs.getString("contractorid"));
				onlineCon.setName(rs.getString("contractorname"));
				onlineCon.setChild(getPatrolman(onlineCon.getId()));
				onlineregion.addChild(onlineCon);
			}
			rs.close();
			query.close();
		} catch (Exception e) {
			logger.warn(e);
			e.printStackTrace();
		}
		return onlineregion;
	}

	// 3、代公司巡检人员
	private List getPatrolman(String id) {
		// String sql =
		// "select p.patrolid,p.patrolname  from PATROLMANINFO p,onlineman m,terminalinfo t where m.simid=t.simnumber and t.ownerid=p.patrolid and m.activetime>hoursbeforenow(12) and parentid='"+id+"'";
		String sql = "  select p.patrolid,p.patrolname  from PATROLMANINFO p where p.parentid='"
				+ id
				+ "' and p.patrolid in  ( select t.ownerid from onlineman m,terminalinfo t where m.simid=t.simnumber and m.activetime>hoursbeforenow(2))";
		QueryUtil query = null;
		List list = new ArrayList();
		OnlinePatrolMan onlineman = null;
		try {
			query = new QueryUtil();

			ResultSet rs = query.executeQuery(sql);
			while (rs.next()) {
				onlineman = new OnlinePatrolMan();
				onlineman.setId(rs.getString("patrolid"));
				onlineman.setName(rs.getString("patrolname"));
				onlineman.setChild(getHoldSim(onlineman.getId()));
				list.add(onlineman);
			}
			rs.close();
			query.close();
			return list;
		} catch (Exception e) {
			logger.warn(e);
			e.printStackTrace();
			return null;
		}
	}

	// 获得sim卡号 巡检 1 采集2 盯防3
	private List getHoldSim(String patrolid) {
		String sql = "select m.simid,to_char(m.activetime,'HH24:MI:SS') activetime,decode(m.operate,'1','巡检','2','制定线路','3','盯防','巡检') operate from onlineman m,terminalinfo t where m.simid=t.simnumber and m.activetime>hoursbeforenow(2) and t.ownerid='"
				+ patrolid + "'";
		QueryUtil query = null;
		HoldSim holdsim = null;
		List hs = new ArrayList();
		try {
			query = new QueryUtil();

			ResultSet rs = query.executeQuery(sql);
			while (rs.next()) {
				holdsim = new HoldSim();
				holdsim.setSimid(rs.getString("simid"));
				holdsim.setLately(rs.getString("activetime"));
				holdsim.setOperation(rs.getString("operate"));
				hs.add(holdsim);
			}
			rs.close();
			query.close();
			return hs;
		} catch (Exception e) {
			logger.warn(e);
			e.printStackTrace();
			return null;
		}
	}

}
