package com.cabletech.watchinfo.services;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.services.DBService;
import com.cabletech.commons.sqlbuild.SqlTemplate;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.watchinfo.beans.WatchPicConBean;
import com.cabletech.watchinfo.dao.WatchPicDAOImpl;
import com.cabletech.watchinfo.domainobjects.WatchAttach;
import com.cabletech.watchinfo.domainobjects.WatchMms;

public class WatchPicBO extends BaseBisinessObject {
	public static WatchPicDAOImpl dao = new WatchPicDAOImpl();

	private static Logger logger = Logger.getLogger(WatchPicBO.class);

	/**
	 * 将附件与盯防关联并存入记录
	 * 
	 * @param attach
	 * @return
	 */
	public boolean saveWatchInfoAttachEx(WatchAttach attach) {
		try {
			UpdateUtil exec = new UpdateUtil();
			String seq = (new DBService().getSeq("watchinfoattach", 15));
//			String template = "insert into watchinfo_attach (ID,URL,PLACEID,FLAG,REMARK) "
//					+ "values($id$,$url$,$placeid$,$flag$,$remark$)";
//			SqlTemplate sql = new SqlTemplate(template);
//			sql.setString("id", seq);
//			sql.setString("url", attach.getAttachPath());
//			sql.setString("placeid", attach.getPlaceId());
//			sql.setInteger("flag", attach.getFlag());
//			sql.setString("remark", attach.getRemark());
//			//sql.setString("uploadtime", attach.getUploadtime());
//			exec.executeUpdate(sql.getSql());
			String sql = "insert into watchinfo_attach (ID,URL,PLACEID,FLAG,REMARK) values("
			             + "'" + seq + "' , "
			             + "'" + attach.getAttachPath() + "' , "
			             + "'" + attach.getPlaceId() + "' , "
			             + attach.getFlag() + " , "
			             + "'" + attach.getRemark() + "')";
			exec.executeUpdate(sql);
			if (attach.getFlag() == 1) {
				dao.updateWatchMMSPicState(attach.getAttachPath(), 1);
			}

			return true;

		} catch (Exception e) {
			logger.error(e);
			return false;
		}

	}

	public boolean saveWatchInfoAttach(WatchPicConBean bean, String placeID,
			String[] relativePathFile, String[] delfileid) {
		List attachments = bean.getAttachments();
		bean.setPlaceID(placeID);
		String sqlInsert = "";
		String sqlDel = "";
		// UploadFile uploadFile = null;
		String remark = "";
		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			try {
				if (relativePathFile != null) {
					for (int i = 0; i < relativePathFile.length; i++) { // 先插入新增附件
						bean.setURL(relativePathFile[i]);
						bean.setID(new DBService()
								.getSeq("watchinfoattach", 15));
						remark = ((UploadFile) attachments.get(i)).getRemark();
						// logger.info(bean.getID());
						sqlInsert = "insert into watchinfo_attach (ID,URL,PLACEID,FLAG,REMARK) "
								+ " values ('"
								+ bean.getID()
								+ "','"
								+ bean.getURL()
								+ "','"
								+ bean.getPlaceID()
								+ "',2,'" + remark + "')";
						logger.info("sql:" + sqlInsert);
						exec.executeUpdate(sqlInsert);
						// boolean flag = dao.updateData(sql);
						// if (!flag){
						// return flag;
						// }
					}
				}
				// 再删除附件
				if (delfileid != null) {
					for (int i = 0; i < delfileid.length; i++) {
						sqlDel = "delete from watchinfo_attach where id='"
								+ delfileid[i] + "'";
						logger.info("sqlDel:" + sqlDel);
						exec.executeUpdate(sqlDel);
					}
				}
				exec.commit();
				exec.setAutoCommitTrue();
				return true;
			} catch (Exception e1) {
				logger.error("出错:" + e1.getMessage());
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("出错:" + e.getMessage());
			return false;
		}
		// for (int i = 0; i < relativePathFile.length; i++) {
		// bean.setURL(relativePathFile[i]);
		// bean.setID(new DBService().getSeq("watchinfoattach", 15));
		// //logger.info(bean.getID());
		// String sql = "insert into watchinfo_attach (ID,URL,PLACEID,FLAG) "
		// + " values ('" + bean.getID() + "','" + bean.getURL()
		// + "','" + bean.getPlaceID() + "',2)";
		// logger.info("sql:" + sql);
		// boolean flag = dao.updateData(sql);
		// if (!flag){
		// return flag;
		// }
		// }
	}

	public List watchPicList(WatchPicConBean bean, String watchID)
			throws Exception {
		String beginDate = bean.getBeginDate();
		String endDate = bean.getEndDate();
		String sql = "select to_char(m.keyid) keyid,m.attach, DECODE (m.remark, NULL, '', m.remark) remark,DECODE (m.subject, NULL, '', m.subject) subject,"
				+ "DECODE (sendtime,NULL, '',TO_CHAR (sendtime, 'yyyy-mm-dd hh24:mi:ss')) sendtime,m.sender from mms_watch m "
				+ "where trunc(m.sendtime) between to_date('"
				+ beginDate
				+ "','yyyy-mm-dd') "
				+ "and to_date('"
				+ endDate
				+ "','yyyy-mm-dd') and watchid = '"
				+ watchID
				+ "' and m.HANDLESTATE =0 order by m.sender";
		logger.info("盯防图片SQL:" + sql);
		List list = dao.getResultList(sql);
		return list;
	}

	// 彩信变更时做的备份
	public List watchPicListOld(WatchPicConBean bean) throws Exception {
		String beginDate = bean.getBeginDate();
		String endDate = bean.getEndDate();
		String sql = "select m.attachname,decode(m.content,null,'',m.content) content,"
				+ "decode(arrtime,null,'',TO_CHAR (arrtime, 'yyyy-mm-dd hh24:mi:ss')) arrtime from mmsdeliver m "
				+ "where trunc(m.arrtime) between to_date('"
				+ beginDate
				+ "','yyyy-mm-dd') "
				+ "and to_date('"
				+ endDate
				+ "','yyyy-mm-dd') order by m.mmsid";
		logger.info("盯防图片SQL:" + sql);
		List list = dao.getResultList(sql);
		return list;
	}

	public boolean saveWatchInfoAttachNew(String watchID, String[] picNamesList) {
		String KeyID = "";
		String attachName = "";
		String attachID_Remark = "";
		String attachID = "";
		String remark = "";
		String flagSql = "";
		UpdateUtil exec = null;
		try {
			exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			for (int i = 0; i < picNamesList.length; i++) {
				attachName = picNamesList[i].substring(0, picNamesList[i]
						.indexOf("*"));
				attachID_Remark = picNamesList[i].substring(picNamesList[i]
						.indexOf("*") + 1);
				attachID = attachID_Remark.substring(0, attachID_Remark
						.indexOf("*"));
				remark = attachID_Remark
						.substring(attachID_Remark.indexOf("*") + 1);
				if (remark == null) {
					remark = "";
				}
				KeyID = new DBService().getSeq("watchinfoattach", 15);
				String sql = "insert into watchinfo_attach (ID,URL,PLACEID,FLAG,Remark) "
						+ " values ('"
						+ KeyID
						+ "','"
						+ attachName
						+ "','"
						+ watchID + "',1,'" + remark + "')";
				logger.info("插入从彩信提取的附件sql:" + sql);
				// boolean flag = dao.updateData(sql);
				exec.executeUpdate(sql);
				flagSql = "update mms_watch set HANDLESTATE = 1 where keyid='"
						+ attachID + "'";
				logger.info("更改彩信附件的关联状态sql:" + flagSql);
				exec.executeUpdate(flagSql);
				exec.commit();
				exec.setAutoCommitTrue();
			}
			return true;
		} catch (Exception ex) {
			logger.error("插入从彩信提取的附件出错:" + ex.getMessage());
			exec.rollback();
			exec.setAutoCommitTrue();
			return false;
		}

	}

	// //依据盯防ID获得其对应的附件ID
	// public String getAttachFilesID(String placeID) {
	// String sql = "select ID,URL,FLAG from watchinfo_attach where flag=2 and
	// placeid='" + placeID + "'";
	// String attachFiles = dao.getStringsBack(sql);
	// logger.info("in bo,the attachFiles:" + attachFiles);
	// return attachFiles;
	// }

	// 依据盯防ID获得其对应的附件ID
	public String getAttachFilesID(String placeID) {
		String sql = "select id i from watchinfo_attach where placeid='"
				+ placeID + "'";
		String attachFiles = dao.getStringsBack(sql);
		logger.info("in bo,the attachFiles:" + attachFiles);
		return attachFiles;
	}

	// 依据盯防ID获得其对应的附件ID
	public List getAttachListByID(String placeID) {
		GisConInfo gis = GisConInfo.newInstance();
		String mmsRoot = "http://" + gis.getWatchPicIP() + ":"
				+ gis.getWatchPicPort() + "/" + gis.getWatchPicDir();
		WatchAttach attach;
		String sql = "select ID,URL,PLACEID,FLAG,REMARK,to_char(UPLOADTIME,'yyyy-mm-dd') UPLOADTIME from watchinfo_attach where placeid='" + placeID
				+ "'";
		ResultSet rs;
		try {
			rs = dao.getResultset(sql);
			List resultList = new ArrayList();
			while (rs.next()) {
				attach = new WatchAttach();
				if (rs.getString("URL") == null) {
					continue;
				} else {
					attach.setId(rs.getString("ID"));
					attach.setPlaceId(rs.getString("PLACEID"));
					attach.setFlag(rs.getInt("FLAG"));
					if (attach.getFlag() == 1) {
						attach.setMmsRoot(mmsRoot);
					} else {
						attach.setMmsRoot(null);
					}
					attach.setAttachPath(rs.getString("URL"));

					attach.setRemark(rs.getString("REMARK"));
					attach.setUploadtime(rs.getString("UPLOADTIME"));
					resultList.add(attach);
				}
			}
			rs.close();
			return resultList;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}

	}

	public List listMmsPicEx(String watchId) throws Exception {

		WatchMms mms;
		String ip = GisConInfo.newInstance().getWatchPicIP();
		String port = GisConInfo.newInstance().getWatchPicPort();
		String dir = GisConInfo.newInstance().getWatchPicDir();

		String pref = "http://" + ip + ":" + port + "/" + dir;

		String sql = "select keyid, sender, to_char(sendtime,'YYYY-MM-DD HH24:Mi') strsendtime,subject,content,watchid,remark,attach from mms_watch "
				+ "where watchid = '"
				+ watchId
				+ "' and HANDLESTATE =0 and (attach is not null) order by sendtime desc";
		String relateUrl;
		ResultSet rs;
		try {
			rs = dao.getResultset(sql);
			List resultList = new ArrayList();
			while (rs.next()) {
				relateUrl = rs.getString("attach");
				mms = new WatchMms();
				mms.setKey(rs.getString("keyid"));
				mms.setRelateurl(relateUrl);
				mms.setAbsoluteurl(pref + "/" + relateUrl);
				String remark = rs.getString("remark");
				if (remark == null) {
					remark = "";
				}
				mms.setRemark(remark);
				mms.setWatchid(watchId);
				mms.setSendtime(rs.getString("strsendtime"));
				mms.setSender(rs.getString("sender"));
				mms.setSubject(rs.getString("subject"));
				mms.setContent(rs.getString("content"));
				resultList.add(mms);

			}
			rs.close();
			return resultList;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}

	}

	// 删除某个盯防的附件
	public void deleteWatchAttachByID(String id, String flag, String relateUrl) {
		String FLAG_MMS = "1";
		dao.deleteWatchAttachRelationship(id);
		logger.info("Flag=" + flag);
		if (flag != null && flag.equals(FLAG_MMS)) {
			dao.updateWatchMMSPicState(relateUrl, 0);
		} else {
			logger.info("nodelete");
		}

	}

}
