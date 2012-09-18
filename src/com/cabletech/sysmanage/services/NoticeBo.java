package com.cabletech.sysmanage.services;

import java.io.Writer;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import oracle.sql.CLOB;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.lob.SerializableClob;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cabletech.sm.rmi.RmiSmProxyService;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.services.DBService;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.sysmanage.beans.NoticeBean;
import com.cabletech.sysmanage.dao.NoticeDAOImpl;
import com.cabletech.sysmanage.domainobjects.Notice;

@Service
public class NoticeBo extends BaseBisinessObject {
	private Logger logger = Logger.getLogger("NoticeBo");
	@Resource(name = "noticeDAOImpl")
	private NoticeDAOImpl dao;
	@Resource(name = "uploadFileService")
	private UploadFileService uploadFile;
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;
	@Resource(name = "rmiSmProxyService")
	private RmiSmProxyService smSendProxy;

	// public NoticeDAOImpl dao = new NoticeDAOImpl();
	DBService dbs = new DBService();

	/**
	 * 向多个手机发送短信
	 * 
	 * @param content
	 *            短信内容
	 * @param mobiles
	 *            接收短信手机号码,使用逗号分割
	 */
	public void sendMessage(String content, String mobiles) {
		smSendProxy.simpleSend(mobiles, content, null, null, true);
//		List<String> mobileList = new ArrayList<String>();
//		StringTokenizer st = new StringTokenizer(mobiles, ",");
//		while (st.hasMoreTokens()) {
//			mobileList.add(st.nextToken());
//		}
//		sendMessage(content, mobileList);
	}

	/**
	 * 向多个手机发送短信
	 * 
	 * @param content
	 *            ：短信内容
	 * @param mobileList
	 *            ：接收短信手机号码
	 */
//	public void sendMessage(String content, List<String> mobileList) {
//		String mobiles = StringUtils.list2StringComma(mobileList);
//		smSendProxy.simpleSend(mobiles, content, null, null, true);
//		for (String mobile : mobileList) {
//			if (isNumeric(mobile)) {
//				smSendProxy.simpleSend(mobile, content, null, null, true);
//			}
//		}
//	}

	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	@Transactional
	public Notice saveNotice(Notice notice, UserInfo user, List<FileItem> files)
			throws ServiceException {
		// notice.setId(dbs.getSeq("notice", 10));
		notice.setIsCanceled("0");
		notice.setContent(Hibernate.createClob(" "));
		dao.saveNotice(notice);
		dao.getSession().flush();
		dao.getSession().refresh(notice, LockMode.UPGRADE);
		try {
			SerializableClob sc = (SerializableClob) notice.getContent(); // kybasicInfo.getInfoContent()是Clob类型的
			java.sql.Clob wrapclob = sc.getWrappedClob();// 这里的Clob是java.sql.Clob
			CLOB clob = null;
			if ("oracle.sql.CLOB".equals(wrapclob.getClass().getName())) {
				clob = (oracle.sql.CLOB) wrapclob; // 这里的CLOB是oracle.sql.CLOB
			} else if ("weblogic.jdbc.wrapper.Clob_oracle_sql_CLOB"
					.equals(wrapclob.getClass().getName())) {
				Method method = wrapclob.getClass().getMethod("getVendorObj",
						new Class[] {});
				clob = (oracle.sql.CLOB) method.invoke(wrapclob);
			}

			Writer writer = clob.getCharacterOutputStream();
			writer.write(notice.getContentString());// kybasicInfo.getInfoContentToString()
			// 是String类型的,在action里就是传这个进来,然后再通过文件流形式写成CLOB字段中
			writer.close();
		} catch (Exception e) {
			throw new ServiceException("NoticeBo:" + e);
		}
		dao.save(notice);
		uploadFile.saveFiles(files, ModuleCatalog.OTHER, user.getRegionName(),
				notice.getId(), "NOTICE_CLOB", user.getUserID());
		return notice;
	}

	@Transactional
	public void cancelNoticeMeet(String id) {
		Notice notice = dao.get(id);
		dao.initObject(notice);
		notice.setIsCanceled(Notice.CANCEL_STATE);
		String content = "请注意：" + notice.getTitle() + "会议已经取消！";
		String acceptUserIds = notice.getAcceptUserIds();
		String[] userId;
		if (acceptUserIds != null) {
			if (acceptUserIds.indexOf(",") != -1) {
				userId = acceptUserIds.split(",");
			} else {
				userId = new String[1];
				userId[0] = acceptUserIds;
			}
			String sim = "";
			String mobiles = "";
			for (int i = 0; i < userId.length; i++) {
				sim = dao.getSendPhone(userId[i]);
				mobiles += sim;
				if (i < userId.length - 1) {
					mobiles += ",";
				}
			}
			sendMessage(content, mobiles);
		}
	}

	@Transactional
	public boolean delNotice(String id) {
		Notice notice = dao.findById(id);
		if (notice != null) {
			return dao.removeNotice(notice);
		} else
			return false;

	}

	@Transactional(readOnly = true)
	public boolean issueNotice(String id) {
		String sql = "update notice set isissue='y' where id='" + id + "'";
		Notice notice = dao.findById(id);
		notice.setIsissue("y");
		return dao.saveNotice(notice);
	}

	@Transactional(readOnly = true)
	public List query(NoticeBean notice) {
		if (notice.getContentString() == null) {
			notice.setContentString("");
		}
		String beginDate = notice.getBegindate();
		String endDate = notice.getEnddate();
		String sql = "select id,title, content,type ,grade,type,decode(isissue,'y','已发布','n','未发布') isissue ,meet_person,"
				+ "to_char(meet_time,'yyyy-mm-dd hh24:mi:ss') as meet_time,to_char(meet_end_time,'yyyy-mm-dd hh24:mi:ss') as meet_end_time,meet_address, "
				+ "issueperson,to_char(issuedate,'YYYY-MM-DD hh24:mi:ss') issuedate,is_canceled from notice_clob "
				+ "where regionid='"
				+ notice.getRegionid()
				+ "'  and (title like '%"
				+ notice.getContentString()
				+ "%' or content like '%" + notice.getContentString() + "%')";
		/*
		 * if(!notice.getBegindate().equals("") &&
		 * !notice.getEnddate().equals("")){ sql = sql +
		 * "and issuedate >= to_date('"
		 * +notice.getBegindate()+"','YYYY/MM/DD HH24:MI:ss') " +
		 * "and issuedate <= to_date('"
		 * +notice.getEnddate()+"','YYYY/MM/DD HH24:MI:ss') "; }
		 */
		if (beginDate != null && !beginDate.trim().equals("")) {
			sql += " and issuedate >= to_date('" + beginDate.trim()
					+ " 00:00:00','yyyy/MM/dd hh24:mi:ss') ";
		}
		if (endDate != null && !endDate.trim().equals("")) {
			sql += " and issuedate <= to_date('" + endDate.trim()
					+ " 23:59:59','yyyy/MM/dd hh24:mi:ss') ";
		}
		if (notice.getIsissue() != null && !notice.getIsissue().equals("")) {
			sql += " and isissue='" + notice.getIsissue() + "'";
		}
		if (notice.getType() != null && !notice.getType().equals("")) {
			sql += " and type='" + notice.getType() + "'";
		}
		if (notice.getGrade() != null && !notice.getGrade().equals("")) {
			sql += " and grade='" + notice.getGrade() + "'";
		}
		if (notice.getMeetTime() != null && !notice.getMeetTime().equals("")) {
			sql += " and meet_time>=to_date('" + notice.getMeetTime()
					+ " 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
			sql += " and meet_time<=to_date('" + notice.getMeetTime()
					+ " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
			sql += " and meet_end_time>=to_date('" + notice.getMeetTime()
					+ " 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		}

		sql += " order by issuedate desc";

		logger.info("sql :" + sql);
		List<NoticeBean> list = dao.query(sql);
		NoticeBean bean;
		String meetPerson = "";
		String meetPersonId = "";
		String[] meetPersonIds;
		DynaBean tmpBean;
		// List userList = userInfoDao.queryList("");
		List userList = userInfoDao.getMobileUserAndContractorPersonList("");
		for (int i = 0; list != null && i < list.size(); i++) {
			bean = list.get(i);
			meetPersonId = bean.getMeetPerson();
			meetPerson = "";
			if (meetPersonId != null && !"".equals(meetPersonId)) {
				if (meetPersonId.indexOf(",") != -1) {
					meetPersonIds = meetPersonId.split(",");
					for (int j = 0; meetPersonIds != null
							&& j < meetPersonIds.length; j++) {
						for (int k = 0; userList != null && k < userList.size(); k++) {
							tmpBean = (DynaBean) userList.get(k);
							if (tmpBean != null
									&& tmpBean.get("userid").equals(
											meetPersonIds[j])) {
								// meetPerson += tmpBean.get("username");
								meetPerson += tmpBean.get("name");
								break;
							}
						}
						if (j < meetPersonIds.length - 1) {
							meetPerson += ",";
						}
					}
				} else {
					meetPerson += userInfoDao.getUserName(meetPersonId);
				}
			}
			bean.setMeetPersonName(meetPerson);
		}
		return list;
	}

	@Transactional(readOnly = true)
	public Notice loadNotice(String id) {
		return dao.findById(id);
	}

	@Transactional
	public Notice readNotice(String id, String userid, boolean preview) {
		// String sql ="update notice set isread ='y' where id='"+id+"'";
		if (!preview) {
			String sql = "select count(*) c from notice_isread where userid='"
					+ userid + "' and noticeid='" + id + "'";
			Integer count = (Integer) dao.getJdbcTemplate().queryForObject(sql,
					Integer.class);
			if (!userid.equals("") && count <= 0) {
				sql = "insert into notice_isread (userid,noticeid) values('"
						+ userid + "','" + id + "')";
				// logger.info("请注意，getNotice()方法抛出异常不是错误信息");
				dao.isRead(sql);
				// logger.info("请注意，getNotice()方法抛出异常不是错误信息");
			}
		}
		return dao.findById(id);
	}

	public Notice Message(String rootregionid, UserInfo user) {
		String sql = "select  id,title, content from notice  where issuedate >= hoursbeforenow(12) and isissue='y' and  grade='重要' and id not in(select noticeid from notice_isread where userid='"
				+ user.getUserID()
				+ "') and regionid in ('"
				+ rootregionid
				+ "','" + user.getRegionID() + "') order by issuedate asc ";
		QueryUtil query = null;
		Notice notice = null;
		logger.info("sql:" + sql);
		CLOB clob = null;
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			notice = new Notice();
			while (rs.next()) {
				notice.setId(rs.getString("id"));
				notice.setTitle(rs.getString("title"));
				clob = (oracle.sql.CLOB) rs.getClob("content");
				notice.setContentString(dao.ClobToString(clob));
			}

			// rs.close();
			// query.close();
			return notice;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return null;
		}
	}
	//增加是否 现实更多
	public String showNewNotice(String rootregionid, String regionid,
			String shownum, String type,String isMore) {
		return assembledNoticeForNew(queryNewNotices(rootregionid, regionid,
				shownum, type),isMore);
	}

	private List queryNewNotices(String rootregionid, String regionid,
			String shownum, String type) {
		String num = "10";
		if (shownum.equals("all")) {
			num = "100";
		}
		if(shownum!=null&&!shownum.equals("")){
			num=shownum;
		}
		return dao.queryNotices(rootregionid, regionid, num, type);
	}

	private String assembledNoticeForNew(List noticelist,String isMore) {
		StringBuffer noticestr = new StringBuffer();
		String type="";
		if (noticelist != null) {
			noticestr.append("<table width=\"98%\" border=\"0\" align=\"left\">");
			for (int i = 0; i < noticelist.size(); i++) {
				NoticeBean notice = (NoticeBean) noticelist.get(i);
				String color = "";
				if (notice.getGrade().indexOf("重要") != -1) {
					color = "red";
				}
				noticestr.append("<tr>");
				noticestr.append("<td width=\"100%\">");
				if ("会议".equals(notice.getType())) {
					noticestr.append(notice.getMeetTime());
				} else {
					noticestr.append(notice.getIssuedate());
				}
				noticestr.append("【" + notice.getType() + "】&nbsp;&nbsp;&nbsp;");
				type=notice.getType();
				//noticestr.append("</td>");
				//noticestr.append("<td width=\"70%\">");
				noticestr.append("<span class=\"txt_blue\">");
				if(notice.getTitle().length()>20){
					noticestr.append("<a href=\"javascript:open_notify('"
							+ notice.getId() + "','0');\" title='"+notice.getTitle()+"'");
					noticestr.append(" style=\"color:" + color + "\">");
					//noticestr.append(notice.getTitle().substring(0,20) + "...</a>");
					noticestr.append(notice.getTitle() + "</a>");
				}else{
					noticestr.append("<a href=\"javascript:open_notify('"
							+ notice.getId() + "','0');\"");
					noticestr.append(" style=\"color:" + color + "\">");
					noticestr.append(notice.getTitle() + "</a>");
				}
				if (DateUtil.compare(notice.getIssuedate(), 3)) {
					noticestr.append("<img src=\"/WebApp/images/new.gif\">");
				}
				noticestr.append("</span>");
				//noticestr.append("</td>");
				noticestr.append("</tr>");
			}
			noticestr.append("<tr>");
			
			noticestr.append("<td width=\"100%\" style='text-align:right;padding-right:5px'>");
			if(!"no".equals(isMore)){
				noticestr.append("<a href='#' onclick='window.open(\"/WebApp/NoticeAction.do?method=queryAllIssueNotice&&type="+type+"&&rnd=\"+Math.random());'>更多...</a>");
			}
			noticestr.append("</td>");
			noticestr.append("</tr>");
			noticestr.append("</table>");
		}
		return noticestr.toString();
	}

}
