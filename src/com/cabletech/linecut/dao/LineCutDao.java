package com.cabletech.linecut.dao;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.beans.ContractorBean;
import com.cabletech.baseinfo.beans.LineLevel;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.linecut.Templates.StatTemplate;
import com.cabletech.linecut.beans.LineCutBean;
import com.cabletech.uploadfile.DelUploadFile;

public class LineCutDao {
	private static Logger logger = Logger.getLogger(LineCutDao.class.getName());
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	public LineCutDao() {
	}

	/**
	 * ��ȡ��·����
	 * 
	 * @return
	 */
	public List getLineLevle() {
		List levleList = new ArrayList();
		String sql = "select * from lineclassdic";
		QueryUtil qu = null;
		try {
			qu = new QueryUtil();
			ResultSet rs = qu.executeQuery(sql);
			while (rs.next()) {
				LineLevel ll = new LineLevel();
				ll.setName(rs.getString("name"));
				ll.setCode(rs.getString("code"));
				levleList.add(ll);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			qu.close();
		}
		return levleList;
	}

	/**
	* <br>����:���ָ���ĸ��ȫ����Ϣ
	* <br>����:����id
	* <br>����ֵ:��óɹ����ض���,���򷵻� NULL;
	*/

	public LineCutBean getOneCutQueryInfo(String reid) {
		LineCutBean bean = new LineCutBean();
		//        String sublinename = this.getSubLineNameCon(reid);
		QueryUtil query1 = null;
		ResultSet rs1 = null;
		try {
			query1 = new QueryUtil();
			//				String sql = "select u1.username reuser,u1.username  audituser,u2.username auusername,l.cutType,l.reid,l.contractorid,l.reuserid,TO_CHAR(l.retime,'yyyy-MM-dd') retime,"
			//                    + "       l.name,l.reason,l.address,TO_CHAR(l.protime,'yyyy-MM-dd HH24:MI') protime,"
			//                    + "       l.prousetime,l.provalue,l.efsystem,l.reremark,l.reacce,l.audituserid,TO_CHAR(l.audittime,'yyyy-MM-dd') audittime,"
			//                    +
			//                    "       l.deptid,l.auditresult,l.auditremark,l.auditacce,l.workuserid,l.endvalue,l.manpower,l.usetime,l.updoc,"
			//                    +
			//                    "       TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.workremark,l.workacce,l.archives,l.isarchive,"
			//                    + "       con.contractorname,de.deptname,lc.AUDITACCE acceptacce,lc.AUDITRESULT acceptresult,lc.AUDITREMARK acceptremark "
			//                    + " from  line_cutinfo l left join (select * from line_cutapprove lc where lc.audittime in ( select max(t.audittime) as  audittime from line_cutapprove t group by t.reid)) lc on l.reid=lc.reid left join userinfo u2 on l.AUDITUSERID = u2.USERID  left join userinfo u3 on lc.AUDITUSERID = u3.USERID,contractorinfo con,deptinfo de,userinfo u1 "
			//                    +
			//                    " where l.REUSERID = u1.USERID  and l.contractorid=con.contractorid and l.deptid=de.deptid and l.ACCEPTID = lc.ID "
			//                    + "       and l.reid='" + reid + "'";
			String sql = " select l.reid,approve.auditremark acceptremark,approve.auditresult acceptresult,to_char(approve.audittime,'yyyy-mm-dd hh24:mi')  paudittime,con.contractorname,sub.sublinename,u.username reuser,l.name,to_char(l.retime,'yyyy-mm-dd hh24:mi')  retime,l.reason,l.reacce,l.address,to_char(l.protime,'yyyy-mm-dd hh24:mi') protime,u1.username  audituser,u2.username auusername,to_char(l.essetime,'yyyy-mm-dd hh24:mi') essetime,l.prousetime,l.usetime,to_char(l.audittime,'yyyy-mm-dd hh24:mi') audittime,l.endvalue,l.provalue,l.efsystem,l.updoc,l.reremark,l.reid,con.contractorname,l.auditresult,l.auditremark,l.auditacce,l.workremark,l.isarchive,l.numerical,l.cuttype  "//DECODE(l.isarchive,'�Ѿ�����',l.AUDITRESULT,
					+ " from line_cutinfo l left join (select * from line_cutapprove lc where lc.audittime in ( select max(t.audittime) as  audittime from line_cutapprove t group by t.reid)) approve on l.reid=approve.reid left join userinfo u1 on l.AUDITUSERID = u1.USERID  left join userinfo u2 on approve.AUDITUSERID = u2.USERID,contractorinfo con,userinfo u,sublineinfo sub,lineinfo lio "
					+ " where u.deptid =con.contractorid and l.CONTRACTORID = con.CONTRACTORID and l.REUSERID = u.USERID  and subStr(l.SUBLINEID,0,8) = sub.sublineid "
					+ "  and sub.lineid = lio.lineid and  (approve.id = l.acceptid or l.acceptid is null) "
					+ " and l.reid='" + reid + "'";

			logger.info("linecutdao->getOneCutQueryInfo:" + sql);
			rs1 = query1.executeQuery(sql);
			if (rs1.next()) {
				bean.setReuser(rs1.getString("reuser"));
				//					bean.setAauditremark(rs1.getString("aauditremark"));
				bean.setAuusername(rs1.getString("auusername"));
				bean.setCutType(rs1.getString("cutType"));
				bean.setReid(rs1.getString("reid"));
				bean.setContractorname(rs1.getString("contractorname"));
				bean.setRetime(rs1.getString("retime"));
				bean.setName(rs1.getString("name"));
				bean.setReason(rs1.getString("reason"));
				bean.setAddress(rs1.getString("address"));
				bean.setProtime(rs1.getString("protime"));
				bean.setProusetime(rs1.getString("prousetime"));
				bean.setProvalue(rs1.getString("provalue"));
				bean.setEfsystem(rs1.getString("efsystem"));
				bean.setReremark(rs1.getString("reremark"));
				bean.setReacce(rs1.getString("reacce"));
				bean.setAudituser(rs1.getString("audituser"));
				//		            bean.setDeptid( rs1.getString( "deptid" ) );
				bean.setAuditresult(rs1.getString("auditresult"));
				bean.setAuditremark(rs1.getString("auditremark"));
				bean.setAuditacce(rs1.getString("auditacce"));
				bean.setAudittime(rs1.getString("audittime"));
				//		            bean.setWorkuserid( rs1.getString( "workuserid" ) );
				bean.setEndvalue(rs1.getString("endvalue"));
				//		            bean.setManpower( rs1.getString( "manpower" ) );
				bean.setUsetime(rs1.getString("usetime"));
				bean.setEssetime(rs1.getString("essetime"));
				bean.setWorkremark(rs1.getString("workremark"));
				//		            bean.setWorkacce( rs1.getString( "workacce" ) );
				//		            bean.setArchives( rs1.getString( "archives" ) );
				bean.setIsarchive(rs1.getString("isarchive"));
				//		            bean.setDeptname( rs1.getString( "deptname" ) );
				bean.setPaudittime(rs1.getString("paudittime"));
				bean.setContractorname(rs1.getString("contractorname"));
				bean.setUpdoc(rs1.getString("updoc"));
				//		            bean.setAcceptacce(rs1.getString("acceptacce"));
				bean.setAcceptremark(rs1.getString("acceptremark"));
				bean.setAcceptresult(rs1.getString("acceptresult"));
				bean.setSublinename(rs1.getString("sublinename"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			query1.close();
		}
		return bean;
	}

	/**
	 * ������·������ָ���û����ڵ�λ�������������·
	 * 
	 * @param userinfo
	 * @param levelId
	 * @return
	 */
	public List getLineByLevel(UserInfo userinfo, String levelId) {
		List lineList = new ArrayList();
		String sql = "";
		if (userinfo.getDeptype().equals("1")) {// ������ƶ��û����ȡ���е���·
			sql = "select distinct l.LINEID,l.LINENAME FROM LINEINFO l,SUBLINEINFO s " + " WHERE l.LINEID = s.LINEID"
					+ "       AND l.linetype = " + levelId + " order by l.linename";
		} else {
			sql = "SELECT distinct l.LINEID,l.LINENAME" + " FROM LINEINFO l,SUBLINEINFO s,PATROLMANINFO p"
					+ " WHERE l.LINEID = s.LINEID" + "       AND s.PATROLID = p.PATROLID" + "       AND p.PARENTID = '"
					+ userinfo.getDeptID() + "'" + "       AND l.linetype = " + levelId + " order by l.linename";
		}

		QueryUtil qu = null;
		try {
			qu = new QueryUtil();
			lineList = qu.queryBeans(sql);

			logger.info("LineCutDao->getLineByLevel:" + sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			qu.close();
		}
		return lineList;
	}

	/**
	 * ������·��ID���ָ���û����ڵ�λ������������߶�
	 * 
	 * @param userinfo
	 * @param levelId
	 * @return
	 */
	public List getSubLineByLineId(UserInfo userinfo, String lineId) {
		List subLineList = new ArrayList();
		String sql = "";
		if (userinfo.getDeptype().equals("1")) {// ������ƶ��û����ȡ���е��߶�
			sql = "SELECT l.LINEID,l.LINENAME,s.SUBLINENAME,s.SUBLINEID" + " from LINEINFO l,SUBLINEINFO s"
					+ " where l.LINEID = s.LINEID and s.lineid=" + lineId + " order by s.sublinename";
		} else {// ��ά�û�
			sql = "SELECT l.LINEID,l.LINENAME,s.SUBLINENAME,s.SUBLINEID"
					+ " FROM LINEINFO l,SUBLINEINFO s,PATROLMANINFO p" + " WHERE l.LINEID = s.LINEID"
					+ "       AND s.PATROLID = p.PATROLID" + "       AND p.PARENTID = '" + userinfo.getDeptID() + "'"
					+ "       AND s.lineid=" + lineId + " order by s.sublinename";
		}
		QueryUtil qu = null;
		try {
			qu = new QueryUtil();
			subLineList = qu.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			qu.close();
		}
		return subLineList;
	}

	/**
	 * <br>
	 * ����:������·������ָ���û����ڵ�λ�������������· <br>
	 * ����:�û�bean <br>
	 * ����ֵ:List ���� null;
	 */
	public List getLineList(UserInfo userinfo) {
		List lLine = null;
		String sql = "SELECT distinct l.LINEID,l.LINENAME" + " FROM LINEINFO l,SUBLINEINFO s,PATROLMANINFO p"
				+ " WHERE l.LINEID = s.LINEID" + "       AND s.PATROLID = p.PATROLID" + "       AND p.PARENTID = '"
				+ userinfo.getDeptID() + "'" + " order by l.linename";
		QueryUtil qu = null;
		try {
			qu = new QueryUtil();
			lLine = qu.queryBeans(sql);
			return lLine;
		} catch (Exception ex) {
			return null;
		} finally {
			qu.close();
		}

	}

	/**
	 * <br>
	 * ����:���ָ���û����ڵ�λ������������߶� <br>
	 * ����:�û�bean <br>
	 * ����ֵ:List ���� null;
	 */
	public List getSubLineList(UserInfo userinfo) {
		List lLine = null;
		String sql = "SELECT l.LINEID,l.LINENAME,s.SUBLINENAME,s.SUBLINEID"
				+ " FROM LINEINFO l,SUBLINEINFO s,PATROLMANINFO p" + " WHERE l.LINEID = s.LINEID"
				+ "       AND s.PATROLID = p.PATROLID" + "       AND p.PARENTID = '" + userinfo.getDeptID() + "'"
				+ " order by s.sublinename";
		QueryUtil qu = null;
		try {
			qu = new QueryUtil();
			lLine = qu.queryBeans(sql);
			return lLine;
		} catch (Exception ex) {
			return null;
		} finally {
			qu.close();
		}
	}

	/**
	 * <br>
	 * ����:д������Ϣ��,д��������Ϣ,���ҳ�ʼ��������Ϣ <br>
	 * ����:���bean <br>
	 * ����ֵ:��óɹ�����true,���򷵻� false;
	 */
	public boolean addInfo(LineCutBean bean, String updoc) {
		String sql = ""; // д��������

		java.util.Date date = new java.util.Date();
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String strDt = dtFormat.format(date);

		OracleIDImpl ora = new OracleIDImpl();
		String reid = ora.getSeq("line_cutinfo", 10);
		String remark = bean.getReremark().length() > 249 ? bean.getReremark().substring(0, 249) : bean.getReremark();
		try {
			UpdateUtil exec = new UpdateUtil();
			sql = "insert into line_cutinfo (reid,contractorid,reuserid,retime,name,sublineid,reason,address,protime,prousetime,provalue,"
					+ " efsystem,reremark,reacce,audituserid,deptid,auditresult,auditremark,auditacce,workuserid,endvalue,manpower,"
					+ " usetime,workremark,workacce,archives,isarchive,updoc,numerical,cutType) values('"
					+ reid
					+ "','"
					+ bean.getContractorid()
					+ "','"
					+ bean.getReuserid()
					+ "',"
					+ "TO_DATE('"
					+ strDt
					+ "','YYYY-MM-DD HH24:MI:SS'),'"
					+ bean.getName()
					+ "','"
					+ bean.getSublineid()
					+ "','"
					+ bean.getReason()
					+ "','"
					+ bean.getAddress()
					+ "',"
					+ "TO_DATE('"
					+ bean.getProtime()
					+ "','YYYY-MM-DD HH24:MI:SS'),"
					+ Float.parseFloat(bean.getProusetime())
					+ ","
					+ Float.parseFloat(bean.getProvalue())
					+ ",'"
					+ bean.getEfsystem()
					+ "','"
					+ remark
					+ "','"
					+ bean.getReacce()
					+ "','','','','','','',0.0,0,0.0,'','','','������','"
					+ updoc
					+ "','"
					+ this.getNumerical() + "','" + bean.getCutType() + "')";
			exec.executeUpdate(sql); // д�������
			return true;
		} catch (Exception e) {
			logger.error("д������Ϣ��,д��������Ϣ,���ҳ�ʼ��������Ϣ����:" + e.getMessage());
			return false;
		}
	}

	private synchronized String getNumerical() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String nowDateStr = format.format(date);
		QueryUtil query = null;
		String numerical = "";
		String sql = "select numerical from line_cutinfo where subStr(numerical,0,8) ='" + nowDateStr
				+ "' order by numerical desc";
		try {
			query = new QueryUtil();
			List tempList = query.queryBeans(sql);
			if (tempList == null || tempList.size() == 0) {
				numerical = nowDateStr + "0001";
			} else {
				String tempStr = ((DynaBean) tempList.get(0)).get("numerical").toString();
				int tempNum = Integer.parseInt(tempStr.substring(8, 12)) + 1;
				if (tempNum <= 9) {
					numerical = nowDateStr + "000" + tempNum;
				}
				if (tempNum > 9 && tempNum <= 99) {
					numerical = nowDateStr + "00" + tempNum;
				}
				if (tempNum >= 100 && tempNum <= 999) {
					numerical = nowDateStr + "0" + tempNum;
				}
				if (tempNum > 1000) {
					numerical = nowDateStr + tempNum;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			query.close();
		}
		return numerical;
	}

	/**
	 * <br>
	 * ����:��õ�ǰ��λ�ĸ��������Ϣ <br>
	 * ����:request <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllOwnRe(HttpServletRequest request) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List useinfo = null;
		String sql = "";
		String contractorid = userinfo.getDeptID();
		try {
			if (userinfo.getDeptype().equals("2")) { // ����Ǵ�ά��λ
				sql = " select l.name,l.reacce,l.ADDRESS,TO_CHAR(l.PROTIME,'YYYY-MM-DD HH24:MI') protime,sub.SUBLINENAME,u.USERNAME,l.REID,l.PROUSETIME,TO_CHAR(l.retime,'YYYY-MM-DD ')  retime,con.contractorname,DECODE(l.isarchive,'�Ѿ�����',l.AUDITRESULT,l.ISARCHIVE) isarchive"
						+ " from line_cutinfo l,contractorinfo con,userinfo u,sublineinfo sub "
						+ " where l.CONTRACTORID = con.CONTRACTORID and l.REUSERID = u.USERID and subStr(l.sublineid,0,8) = sub.sublineid"
						+ " and l.CONTRACTORID='" + contractorid + "' " + " order by l.retime desc";
				System.out.println("~~~~~~~~~:" + sql + "~~~~~~~~~~~~");
			}
			if (userinfo.getDeptype().equals("1")) { // ������ƶ���˾
				sql = " select l.name,l.reacce,l.ADDRESS,TO_CHAR(l.PROTIME,'YYYY-MM-DD HH24:MI') protime,sub.SUBLINENAME,u.USERNAME,l.REID,l.PROUSETIME,TO_CHAR(l.retime,'YYYY-MM-DD ')  retime,d.deptname contractorname,l.isarchive "
						+ " from line_cutinfo l,deptinfo d,userinfo u,sublineinfo sub "
						+ " where l.CONTRACTORID = d.deptid and l.REUSERID = u.USERID and sub.SUBLINEID = subStr(l.SUBLINEID,0,8) "
						+ " and l.CONTRACTORID='" + contractorid + "' " + " order by l.retime desc";
			}
			System.out.println("linecutdao->getAllOwnRe:" + sql);
			logger.info("sql):" + sql);
			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��õ�ǰ��λ�ĸ��������Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	public boolean valiApprove(String reid) {
		String sql = "select auditresult from line_cutinfo where reid='" + reid + "'";
		ResultSet rst;
		try {
			QueryUtil qu = new QueryUtil();
			rst = qu.executeQuery(sql);
			if (rst != null && rst.next()) {
				String auditresult = rst.getString("auditresult");
				if (auditresult == null || auditresult.equals("")) {
					return false; // ������
				} else {
					return true; // �Ѿ���������.
				}
			}
			rst.close();
			return false;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * <br>
	 * ����:���ָ�����뵥������������Ϣ <br>
	 * ����:���뵥id <br>
	 * ����ֵ:List;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	public List getReApprove(String reid) {
		List reApprove = null;
		String sql = "select l.AUDITACCE,l.AUDITREMARK,l.AUDITRESULT,u.USERNAME,d.DEPTNAME,TO_CHAR(l.AUDITTIME,'yyyy-mm-dd HH24:MI') audittime "
				+ " from line_cutapprove l,userinfo u,deptinfo d "
				+ " where l.AUDITUSERID=u.USERID and l.DEPTID = d.DEPTID and l.TYPE='����' and l.REID='"
				+ reid
				+ "' order by l.audittime desc";
		try {
			QueryUtil qu = new QueryUtil();
			reApprove = qu.queryBeans(sql);
			return reApprove;
		} catch (Exception e) {
			logger.warn("���ָ�����뵥������������Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * ����:���һ�������ܷ��޸� <br>
	 * ����:���뵥id <br>
	 * ����ֵ:�ܷ��� true ���򷵻�false;
	 */
	public boolean valiCanUp(String reid) {
		String[][] rst;
		try {
			String sql = " select count(*) aa from line_cutinfo  where (isarchive ='������' or AUDITRESULT='δͨ������') and reid='"
					+ reid + "'";
			QueryUtil query = new QueryUtil();
			rst = query.executeQueryGetArray(sql, "");
			if (rst[0][0].equals("1")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("���һ�������ܷ��޸��쳣:" + e.getMessage());
			return false;
		}
	}

	/**
	 * <br>
	 * ����:���¸�ӱ�������Ϣ,�������ļ���ű� <br>
	 * ����:formbean,��ɾ�����ļ�id���� <br>
	 * ����ֵ:��óɹ�����true,���򷵻� false;
	 */
	public boolean doUp(String[] delfileid, LineCutBean bean) {
		String sql1 = ""; // ���¸�ӱ�
		String sql2 = ""; // ɾ���ļ���
		String remark = bean.getReremark().length() > 249 ? bean.getReremark().substring(0, 249) : bean.getReremark();

		java.util.Date date = new java.util.Date();
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String strDt = dtFormat.format(date);

		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			try {
				// ���»�����
				sql1 = "  update line_cutinfo " + " set  contractorid='" + bean.getContractorid() + "', reuserid='"
						+ bean.getReuserid() + "'," + "		retime=TO_DATE('" + strDt
						+ "','YYYY-MM-DD HH24:MI:SS'),reason='" + bean.getReason() + "'," + "		address='"
						+ bean.getAddress() + "',protime=TO_DATE('" + bean.getProtime() + "','YYYY-MM-DD HH24:MI:SS'),"
						+ "		prousetime=" + Float.parseFloat(bean.getProusetime()) + ",provalue="
						+ Float.parseFloat(bean.getProvalue()) + "," + "	 	efsystem='" + bean.getEfsystem()
						+ "',reremark='" + remark + "'," + "		reacce = '" + bean.getReacce()
						+ "' ,auditresult='',isarchive='������', cutType='" + bean.getCutType() + "' " + " where reid='"
						+ bean.getReid() + "'";
				exec.executeUpdate(sql1);
				if (delfileid != null) {
					for (int i = 0; i < delfileid.length; i++) {
						DelUploadFile.delFile(delfileid[i]);
						sql2 = "delete from filepathinfo where fileid='" + delfileid[i] + "'";
						exec.executeUpdate(sql2);
					}
				}
				exec.commit();
				exec.setAutoCommitTrue();
				return true;
			} catch (Exception e1) {
				logger.error("������Ϣ����:" + e1.getMessage());
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("���³���:" + e.getMessage());
			return false;
		}
	}

	/**
	 * <br>
	 * ����:ɾ����ӱ�������Ϣ,�������ļ���ű� <br>
	 * ����:������,��ɾ�����ļ�id�� <br>
	 * ����ֵ:��óɹ�����true,���򷵻� false;
	 */
	public boolean doDel(String delfileid, String reid) {
		String sql1 = ""; // ɾ����ӱ�
		String sql2 = ""; // ɾ���ļ���
		ArrayList fileIdList = new ArrayList();
		StringTokenizer st = new StringTokenizer(delfileid, ",");
		while (st.hasMoreTokens()) {
			fileIdList.add(st.nextToken());
		}
		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			try {
				// ���»�����
				sql1 = "delete from line_cutinfo where reid='" + reid + "'";
				exec.executeUpdate(sql1);
				if (fileIdList != null) {
					for (int i = 0; i < fileIdList.size(); i++) {
						sql2 = "delete from filepathinfo where fileid='" + fileIdList.get(i) + "'";
						exec.executeUpdate(sql2);
					}
				}
				exec.commit();
				exec.setAutoCommitTrue();
				return true;
			} catch (Exception e1) {
				logger.error("ɾ����Ϣ����:" + e1.getMessage());
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("ɾ������:" + e.getMessage());
			return false;
		}
	}

	/**
	 * <br>
	 * ����:��ò������б� <br>
	 * ����:��άid <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllUsers(String contractorid) {
		List reqPart = null;
		try {
			String sql = "select distinct l.REUSERID,u.USERNAME from line_cutinfo l,userinfo u "
					+ " where l.REUSERID = u.userid and l.contractorid='" + contractorid + "' order by u.username";
			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("��ò������б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * ��ø�������б� <br>
	 * ����: <br>
	 * ����:��άid <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllNames(String contractorid) {
		List reqPart = null;
		try {
			String sql = "select distinct name from line_cutinfo  " + " where contractorid='" + contractorid
					+ "' order by name";
			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("��ø�������б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * ��ø�ӵص��б� <br>
	 * ����: <br>
	 * ����:��άid <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllAddresss(String contractorid) {
		List reqPart = null;
		try {
			String sql = "select distinct address from line_cutinfo  " + " where contractorid='" + contractorid
					+ "' order by address";
			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("��ø�ӵص��б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * �����Ӱ��ϵͳ�б� <br>
	 * ����: <br>
	 * ����:��άid <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllEfsystems(String contractorid) {
		List reqPart = null;
		try {
			String sql = "select distinct efsystem from line_cutinfo  " + " where contractorid='" + contractorid
					+ "' order by efsystem";
			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("�����Ӱ��ϵͳ�б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * ����漰���¶��б� <br>
	 * ����: <br>
	 * ����:��άid <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllSublineids(String contractorid) {
		List reqPart = null;
		try {
			String sql = "select distinct l.SUBLINEID,s.SUBLINENAME from line_cutinfo l,sublineinfo s "
					+ " where subStr(l.SUBLINEID,0,8) = s.SUBLINEID and l.contractorid='" + contractorid
					+ "' order by s.sublinename";
			QueryUtil query = new QueryUtil();
			System.out.println("linecutdao->getAllSublineids:" + sql);
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("����漰���¶��б��쳣:" + e.getMessage());
			return null;
		}
	}

	public List doQueryAfterMod(String sql) {
		QueryUtil query = null;
		try {
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			query.close();
		}

	}

	// ================================��������=============================//
	/**
	 * <br>
	 * ����:������д������ĸ��������Ϣ <br>
	 * ����:request <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllReForAu(String regionid) {
		List re = null;
		try {
			String sql = " select l.numerical, l.name,l.reacce,l.ADDRESS,TO_CHAR(l.PROTIME,'YYYY-MM-DD HH24:MI') protime,sub.SUBLINENAME,u.USERNAME,l.REID,l.PROUSETIME,TO_CHAR(l.retime,'YYYY-MM-DD ')  retime,con.contractorname,l.isarchive "
					+ " from line_cutinfo l,contractorinfo con,userinfo u,sublineinfo sub "
					+ " where l.CONTRACTORID = con.CONTRACTORID and l.REUSERID = u.USERID and sub.SUBLINEID = subStr(l.SUBLINEID,0,8) "
					+ " and l.isarchive='������' " + " and con.regionid = '" + regionid + "'" + " order by l.retime desc";
			System.out.println("linecutdao->getAllReForAu:" + sql);
			QueryUtil query = new QueryUtil();
			re = query.queryBeans(sql);
			return re;
		} catch (Exception e) {
			logger.error("������д������ĸ��������Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * ����:д����������Ϣ <br>
	 * ����:���bean <br>
	 * ����ֵ:��óɹ�����true,���򷵻� false;
	 */
	public boolean addAuInfo(LineCutBean bean) {
		String sql = ""; // д��������
		String sqlAPP = ""; // д���������

		OracleIDImpl ora = new OracleIDImpl();
		String id = ora.getSeq("line_cutapprove", 10);

		java.util.Date date = new java.util.Date();
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String strDt = dtFormat.format(date);

		String remark = bean.getAuditremark().length() > 249 ? bean.getAuditremark().substring(0, 249) : bean
				.getAuditremark();

		sql = "update line_cutinfo set audituserid = '" + bean.getAudituserid() + "',audittime=" + "TO_DATE('" + strDt
				+ "','YYYY-MM-DD HH24:MI:SS'),deptid='" + bean.getDeptid() + "',auditresult='" + bean.getAuditresult()
				+ "',auditremark='" + remark + "',auditacce='" + bean.getAuditacce() + "',isarchive='�Ѿ�����'"
				+ " where reid='" + bean.getReid() + "'";
		sqlAPP = "insert into line_cutapprove (id,reid,auditresult,audituserid,audittime,deptid,auditremark,auditacce,type) values('"
				+ id
				+ "','"
				+ bean.getReid()
				+ "','"
				+ bean.getAuditresult()
				+ "','"
				+ bean.getAudituserid()
				+ "',"
				+ "TO_DATE('"
				+ strDt
				+ "','YYYY-MM-DD HH24:MI:SS'),'"
				+ bean.getDeptid()
				+ "','"
				+ bean.getAuditremark() + "','" + bean.getAuditacce() + "','����')";
		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			try {
				exec.executeUpdate(sql); // д�������
				if (bean.getAuditresult().equals("δͨ������")) {
					exec.executeUpdate(sqlAPP);
				}
				exec.commit();
				exec.setAutoCommitTrue();
				return true;
			} catch (Exception w) {
				logger.error("д����������Ϣִ��ʱ����:" + w.getMessage());
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("д����������Ϣ����:" + e.getMessage());
			return false;
		}
	}

	/**
	 * <br>
	 * ����:��õ�ǰ��λ�ĸ��������Ϣ <br>
	 * ����:request <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllOwnAu(HttpServletRequest request) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List useinfo = null;
		String sql = "";
		String deptid = userinfo.getDeptID();
		try {
			sql = " select l.reid,l.name,l.reacce,l.ADDRESS,TO_CHAR(l.PROTIME,'YYYY-MM-DD HH24:MI') protime,sub.SUBLINENAME,TO_CHAR(l.retime,'YYYY-MM-DD ')  retime,TO_CHAR(l.audittime,'YYYY-MM-DD ')  audittime,l.auditresult,l.isarchive "
					+ " from line_cutinfo l,deptinfo d,userinfo u,sublineinfo sub "
					+ " where l.deptid = d.deptid and l.audituserid = u.USERID and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID "
					+ " and l.deptid='"
					+ deptid
					+ "' and l.isarchive != '������' and l.auditresult != 'δͨ������'"
					+ " order by l.audittime desc";
			System.out.println("linecutdao->getAllOwnAu:" + sql);
			logger.info("sql):" + sql);
			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��õ�ǰ��λ�ĸ��������Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * ����:��ò������б� <br>
	 * ����:�ƶ�id <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllUsersForAu(String deptid) {
		List reqPart = null;
		try {
			String sql = "select distinct l.audituserid,u.USERNAME from line_cutinfo l,userinfo u "
					+ " where l.audituserid = u.userid and l.deptid='" + deptid + "' order by u.username";
			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("��ò������б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * ��ø�������б� <br>
	 * ����: <br>
	 * ����:�ƶ�id <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllNamesForAu(String deptid) {
		List reqPart = null;
		try {
			String sql = "select distinct name from line_cutinfo  " + " where deptid='" + deptid + "' order by name";
			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("��ø�������б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * ��ø��ԭ���б� <br>
	 * ����: <br>
	 * ����:id <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllReasonsForAu(String deptid) {
		List reqPart = null;
		try {
			String sql = "select distinct reason from line_cutinfo  " + " where deptid ='" + deptid
					+ "' order by reason";
			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("��ø��ԭ���б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * ��ȡ���ԭ��
	 * 
	 * @param contractorid
	 * @return
	 */
	public String getAllReason(String deptid) {
		String reason = "";
		QueryUtil query = null;
		String sql = "select distinct reason from line_cutinfo  " + " where deptid ='" + deptid + "' order by reason";
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[");
			query = new QueryUtil();
			rs = query.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				sb.append("['").append(rs.getString("reason")).append("'],");
			}
			int length = sb.toString().length() - 1;
			reason = sb.toString().substring(0, length) + "]";
			return reason;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			query.close();
		}
	}

	/**
	 * ��ø�ӵص��б� <br>
	 * ����: <br>
	 * ����:id <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllAddresssForAu(String deptid) {
		List reqPart = null;
		try {
			String sql = "select distinct address from line_cutinfo  " + " where deptid ='" + deptid
					+ "' order by address";
			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("��ø�ӵص��б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * �����Ӱ��ϵͳ�б� <br>
	 * ����: <br>
	 * ����:id <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllEfsystemsForAu(String deptid) {
		List reqPart = null;
		try {
			String sql = "select distinct efsystem from line_cutinfo  " + " where deptid ='" + deptid
					+ "' order by efsystem";
			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("�����Ӱ��ϵͳ�б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * ����漰���¶��б� <br>
	 * ����: <br>
	 * ����:��άid <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllSublineidsForAu(String deptid) {
		List reqPart = null;
		try {
			String sql = "select distinct l.SUBLINEID,s.SUBLINENAME from line_cutinfo l,sublineinfo s "
					+ " where subStr(l.SUBLINEID,0,8) = s.SUBLINEID and l.deptid ='" + deptid
					+ "' order by s.sublinename";

			QueryUtil query = new QueryUtil();
			System.out.println("linecutdao->getAllSublineidsForAu:" + sql);
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("����漰���¶��б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * ����:��õ�ǰ��λ�ĸ��������Ϣ <br>
	 * ����:request <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllOwnReForAuSearch(LineCutBean bean, HttpSession session) {
		List useinfo = null;
		String sql = "";
		try {
			sql = " select l.numerical,l.reid,l.name,l.reacce,l.ADDRESS,TO_CHAR(l.PROTIME,'YYYY-MM-DD HH24:MI') protime,sub.SUBLINENAME,TO_CHAR(l.retime,'YYYY-MM-DD ')  retime,TO_CHAR(l.audittime,'YYYY-MM-DD ')  audittime,l.auditresult,l.isarchive "
					+ " from line_cutinfo l,deptinfo d,userinfo u,sublineinfo sub "
					+ " where l.deptid = d.deptid and l.audituserid = u.USERID and subStr(l.SUBLINEID,0,8) = sub.sublineid "
					+ " and l.deptid='" + bean.getDeptid() + "' and isarchive != '������' and l.auditresult != 'δͨ������'";

			if (bean.getAudituserid() != null && !bean.getAudituserid().equals("")) {
				sql = sql + " and l.audituserid ='" + bean.getAudituserid() + "' ";
			}
			if (bean.getAuditresult() != null && !bean.getAuditresult().equals("")) {
				sql = sql + "  and l.auditresult='" + bean.getAuditresult() + "'  ";
			}
			if (bean.getSublineid() != null && !bean.getSublineid().equals("")) {
				sql = sql + "  and subStr(l.sublineid,0,8)='" + bean.getSublineid() + "'  ";
			}
			if (bean.getName() != null && !bean.getName().equals("")) {
				sql = sql + "  and l.name='" + bean.getName() + "'  ";
			}
			if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
				sql = sql + " and l.audittime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
			}
			if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
				sql = sql + " and l.audittime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
			}
			sql = sql + " order by l.audittime desc";
			System.out.println("linecutdao->getAllOwnReForAuSearch:" + sql);
			logger.info("sql: " + sql);
			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			session.setAttribute("lcAuQueryCon", sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��õ�ǰ��λ�ĸ��������Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	public List getAllOwnReForAuSearchAfterMod(String sql) {
		QueryUtil query = null;
		try {
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			query.close();
		}
	}

	public List getNameByClewId(String sublineid, String deptid) {
		QueryUtil query = null;
		String sql = "";
		sql = "select l.name from line_cutinfo l where subStr(l.SUBLINEID,0,8)='" + sublineid + "' and l.deptid='"
				+ deptid + "'";
		try {
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List getNameByClewId(String sublineid) {
		QueryUtil query = null;
		String sql = "";
		sql = "select l.name from line_cutinfo l where subStr(l.SUBLINEID,0,8)='" + sublineid + "'";
		try {
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List getNameBySublineid(String sublineid, UserInfo userinfo) {
		QueryUtil query = null;
		String sql = "";
		String deptid = userinfo.getDeptID();
		if (userinfo.getDeptype().equals("1")) {
			sql = "select l.name from line_cutinfo l,deptinfo d where subStr(l.SUBLINEID,0,8)='" + sublineid + "'"
					+ "  and l.isarchive != '������' and l.isarchive !='�Ѿ�����' and l.deptid='" + deptid
					+ "' and l.deptid = d.deptid";
		} else {
			sql = "select l.name from line_cutinfo l,deptinfo d where subStr(l.SUBLINEID,0,8)='" + sublineid + "'"
					+ "  and l.isarchive != '������' and l.isarchive !='�Ѿ�����' and l.contractorid='" + deptid
					+ "' and l.deptid = d.deptid";
		}
		try {
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List getNameBySublineidAndDeptid(String sublineid, String deptid, UserInfo userinfo) {
		String sql = "";
		if (userinfo.getDeptype().equals("1")) { // ���ƶ���˾
			sql = "select distinct l.name from line_cutinfo l, deptinfo d,contractorinfo con where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8)='"
					+ sublineid
					+ "'"
					+ "  and l.isarchive ='�Ѿ��鵵' and l.contractorid= con.contractorid "
					+ " and (l.deptid='"
					+ deptid
					+ "' or l.deptid in (select deptid from deptinfo where parentid='"
					+ deptid + "')) ";
		} else {
			sql = "select l.name from line_cutinfo l, deptinfo d where subStr(l.SUBLINEID,0,8)='" + sublineid + "'"
					+ " and l.contractorid='" + deptid + "'";
		}
		System.out.println("!!" + sql);
		QueryUtil query = null;
		try {
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List getCutNameBySublineid(String sublineid, String contractorid) {
		String sql = "select l.name from line_cutinfo l,contractorinfo con,userinfo u,sublineinfo sub "
				+ " where l.CONTRACTORID = con.CONTRACTORID and l.REUSERID = u.USERID and subStr(l.SUBLINEID,0,8) = sub.sublineid"
				+ " and  l.CONTRACTORID='" + contractorid + "' and subStr(l.sublineid,0,8)='" + sublineid + "'";
		QueryUtil query = null;
		System.out.println(sql);
		try {
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <br>
	 * ����:��õ�ǰ��λ�ĸ��������Ϣ <br>
	 * ����:request <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllOwnReForSearch(LineCutBean bean, HttpSession session) {
		List useinfo = null;

		try {
			String sql = " select l.name,l.reacce,l.ADDRESS,TO_CHAR(l.PROTIME,'YYYY-MM-DD HH24:MI') protime,sub.SUBLINENAME,u.USERNAME,l.REID,l.PROUSETIME,TO_CHAR(l.retime,'YYYY-MM-DD ')  retime,con.contractorname,DECODE(l.isarchive,'�Ѿ�����',l.AUDITRESULT,l.ISARCHIVE) isarchive,l.numerical,l.cutType  "
					+ " from line_cutinfo l,contractorinfo con,userinfo u,sublineinfo sub "
					+ " where l.CONTRACTORID = con.CONTRACTORID and l.REUSERID = u.USERID and subStr(l.SUBLINEID,0,8) = sub.sublineid ";

			if (bean.getContractorid() != null && !bean.getContractorid().equals("")) {
				sql = sql + " and l.CONTRACTORID='" + bean.getContractorid() + "'";
			}

			if (bean.getReuserid() != null && !bean.getReuserid().equals("")) {
				sql = sql + " and l.reuserid ='" + bean.getReuserid() + "' ";
			}
			if (bean.getSublineid() != null && !bean.getSublineid().equals("")) {
				sql = sql + "  and subStr(l.sublineid,0,8)='" + bean.getSublineid() + "'  ";
			}
			if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
				sql = sql + " and l.retime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
			}
			if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
				sql = sql + " and l.retime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
			}
			sql = sql + " order by l.retime desc";
			System.out.println("linecutdao->getAllOwnReForSearch:" + sql);
			session.setAttribute("lcQueryCon", sql);
			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��õ�ǰ��λ�ĸ��������Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * ����:��õ�ǰ��λ�ĸ��������Ϣ <br>
	 * ����:request <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getConditionsReForSearch(LineCutBean bean, HttpServletRequest request) {
		List useinfo = null;

		String lineLevel = request.getParameter("level").trim();
		String linename = request.getParameter("line").trim();

		try {
			String sql = " select l.name,l.reacce,l.ADDRESS,TO_CHAR(l.PROTIME,'YYYY-MM-DD HH24:MI') protime,TO_CHAR(l.AUDITTIME,'YYYY-MM-DD HH24:MI') audittime,sub.SUBLINENAME,u.USERNAME,l.REID,l.PROUSETIME,TO_CHAR(l.retime,'YYYY-MM-DD ')  retime,con.contractorname,l.auditresult,l.ISARCHIVE,l.numerical,l.cutType  "//DECODE(l.isarchive,'�Ѿ�����',l.AUDITRESULT,
					+ " from line_cutinfo l,contractorinfo con,userinfo u,sublineinfo sub,lineinfo lio "
					+ " where l.CONTRACTORID = con.CONTRACTORID and l.REUSERID = u.USERID and subStr(l.SUBLINEID,0,8) = sub.sublineid "
					+ "  and sub.lineid = lio.lineid";
			if (bean.getContractorid() != null && !bean.getContractorid().equals("")) {
				sql = sql + " and l.CONTRACTORID='" + bean.getContractorid() + "'";
			}

			if (bean.getName() != null && !bean.getName().equals("")) {
				sql = sql + " and l.NAME='" + bean.getName() + "'";
			}

			if (bean.getAudituserid() != null && !bean.getAudituserid().equals("")) {
				sql = sql + " and l.AUDITUSERID='" + bean.getAudituserid() + "'";
			}

			if (bean.getReuserid() != null && !bean.getReuserid().equals("")) {
				sql = sql + " and l.reuserid ='" + bean.getReuserid() + "' ";
			}
			if (bean.getSublineid() != null && !bean.getSublineid().equals("")) {
				sql = sql + "  and subStr(l.sublineid,0,8)='" + bean.getSublineid() + "'  ";
			}

			if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
				sql = sql + " and l.retime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
			}
			if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
				sql = sql + " and l.retime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
			}
			if (lineLevel != null && !"".equals(lineLevel) && !"mention".equals(lineLevel)) {
				sql = sql + " and lio.linetype='" + lineLevel + "'";
			}
			if (linename != null && !"".equals(linename) && !"mention".equals(linename)) {
				sql = sql + " and lio.lineid = '" + linename + "'";
			}

			sql = sql + " order by l.retime desc";
			System.out.println("linecutdao->getAllOwnReForSearch:" + sql);
			request.getSession().setAttribute("lcAuQueryCon", sql);
			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��õ�ǰ��λ�ĸ��������Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>��ѯͳ��
	 * ����:��õ�ǰ��λ�ĸ��������Ϣ <br>
	 * ����:request <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getConditionsReForQuery(LineCutBean bean, HttpServletRequest request) {
		List useinfo = null;

		String lineLevel = request.getParameter("level").trim();
		String linename = request.getParameter("line").trim();

		try {
			String sql = " select l.reid,approve.auditremark aauditremark,approve.auditresult type,TO_CHAR(approve.audittime,'YYYY-MM-DD HH24:MI')  paudittime,con.contractorname,sub.SUBLINENAME,u.USERNAME REUSER,l.name,TO_CHAR(l.retime,'YYYY-MM-DD HH24:MI')  retime,l.reason,l.reacce,l.ADDRESS,TO_CHAR(l.PROTIME,'YYYY-MM-DD HH24:MI') protime,u1.username  AUDITUSER,u2.username auusername,TO_CHAR(l.ESSETIME,'YYYY-MM-DD HH24:MI') essetime,l.prousetime,l.USETIME,TO_CHAR(l.AUDITTIME,'YYYY-MM-DD HH24:MI') audittime,l.ENDVALUE,l.PROVALUE,l.EFSYSTEM,l.UPDOC,l.REREMARK,l.AUDITRESULT,l.REID,con.contractorname,l.auditresult,l.AUDITREMARK,l.AUDITACCE,l.WORKREMARK,l.ISARCHIVE,l.numerical,l.cutType  "//DECODE(l.isarchive,'�Ѿ�����',l.AUDITRESULT,
					+ " from line_cutinfo l left join (select * from line_cutapprove lc where lc.audittime in ( select max(t.audittime) as  audittime from line_cutapprove t group by t.reid)) approve on l.reid=approve.reid left join userinfo u1 on l.AUDITUSERID = u1.USERID  left join userinfo u2 on approve.AUDITUSERID = u2.USERID ,contractorinfo con,userinfo u,sublineinfo sub,lineinfo lio "
					+ " where u.deptid =con.contractorid and l.CONTRACTORID = con.CONTRACTORID and l.REUSERID = u.USERID  and subStr(l.SUBLINEID,0,8) = sub.sublineid "
					+ "  and sub.lineid = lio.lineid and  (approve.id = l.acceptid or l.acceptid is null) ";

			if (bean.getName() != null && !bean.getName().equals("")) {
				sql = sql + " and l.NAME='" + bean.getName() + "'";
			}

			if (bean.getIsarchive() != null && !"".equals(bean.getIsarchive()) && !"0".equals(bean.getIsarchive())) {
				if ("δͨ������".equals(bean.getIsarchive())) {
					sql = sql + " and l.auditresult='" + bean.getIsarchive() + "'";
				} else if ("ͨ������".equals(bean.getIsarchive())) {
					sql = sql + " and l.auditresult='ͨ������'  and l.ISARCHIVE='�Ѿ�����'";
					;
				} else {
					sql = sql + " and l.ISARCHIVE='" + bean.getIsarchive() + "'";
				}
			}

			//			if(bean.getIsarchive() != null && !"".equals(bean.getIsarchive()) && !"0".equals(bean.getIsarchive()))
			//			{
			//				sql = sql + " and l.ISARCHIVE='" + bean.getIsarchive() + "'";
			//			}
			//			

			if (bean.getSublineid() != null && !bean.getSublineid().equals("")) {
				sql = sql + "  and subStr(l.sublineid,0,8)='" + bean.getSublineid() + "'  ";
			}

			if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
				sql = sql + " and l.retime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
			}
			if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
				sql = sql + " and l.retime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
			}
			if (lineLevel != null && !"".equals(lineLevel) && !"mention".equals(lineLevel)) {
				sql = sql + " and lio.linetype='" + lineLevel + "'";
			}
			if (linename != null && !"".equals(linename) && !"mention".equals(linename)) {
				sql = sql + " and lio.lineid = '" + linename + "'";
			}

			sql = sql + " order by l.retime desc";
			System.out.println("linecutdao->getConditionsReForQuery:" + sql);
			//			request.getSession().setAttribute("conditionsReForQueryList", sql);
			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��õ�ǰ��λ�ĸ��������Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	// ///////////////�������//////////////////////////////
	/**
	 * <br>
	 * ����:��õ�ǰ��λ�ĸ��������Ϣ <br>
	 * ����:request <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllOwnAcc(HttpServletRequest request) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List useinfo = null;
		String sql = "";
		String deptid = userinfo.getDeptID();
		try {
			// line_cutinfo reuserid
			sql = "select l.REID,l.NAME,TO_CHAR( l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.ADDRESS,l.ISARCHIVE, la.AUDITRESULT,TO_CHAR(la.AUDITTIME,'yyyy-mm-dd')audittime,u.username "
					+ " from line_cutinfo l,line_cutapprove la,userinfo u "
					+ " where l.ACCEPTID = la.ID and "
					// �ϼ����ſ��Կ����������ŵ����յ�
					+ "(l.deptid='"
					+ userinfo.getDeptID()
					+ "' or l.deptid in (select deptid from deptinfo where parentid='"
					+ userinfo.getDeptID()
					+ "')) "
					+ "and (l.isarchive='�Ѿ�����' or l.isarchive='�Ѿ��鵵' or l.isarchive='���ڹ鵵') "
					+ "and l.reuserid=u.userid "
					// ����ʱ������
					+ " order by la.AUDITTIME desc";
			// + " order by l.retime desc";
			System.out.println("linecutdao->getAllOwnAcc:" + sql);
			logger.info("sql):" + sql);
			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��õ�ǰ��λ�ĸ��������Ϣ:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * ����:������д����յĸ��ʩ�� <br>
	 * ����:request <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllWorkForAcc(HttpServletRequest request) {

		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List worklist = null;
		String sql = "";
		String deptid = userinfo.getDeptID();
		try {
			String sql1 = "select l.numerical, l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,u.username, "
					+ " l.reason,l.endvalue,l.usetime,TO_CHAR(l.essetime,'yyyy-mm-dd HH24:MI') essetime "
					+ " from line_cutinfo l,deptinfo d,sublineinfo sub,userinfo u "
					+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID " + " and l.deptid='"
					+ deptid + "' and l.isarchive='ʩ�����' " + " and l.reuserid = u.userid "
					+ " order by l.essetime desc";
			System.out.println("linecutdao->getAllWorkForAcc:" + sql);
			QueryUtil query = new QueryUtil();
			worklist = query.queryBeans(sql1);
			return worklist;
		} catch (Exception e) {
			logger.error("��������������Ѿ�ʩ���ĸ����Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * ����:д����������Ϣ <br>
	 * ����:���bean <br>
	 * ����ֵ:��óɹ�����true,���򷵻� false;
	 */
	public boolean addAcceptInfo(LineCutBean bean) {
		String sql = ""; // д��������
		String sqlAPP = ""; // д���������
		// String sql
		OracleIDImpl ora = new OracleIDImpl();
		String id = ora.getSeq("line_cutapprove", 10);

		java.util.Date date = new java.util.Date();
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String strDt = dtFormat.format(date);

		String remark = bean.getAuditremark().length() > 249 ? bean.getAuditremark().substring(0, 249) : bean
				.getAuditremark();

		if (bean.getAuditresult().equals("δͨ������")) {
			sql = "update line_cutinfo set acceptid='" + id + "',ISARCHIVE='δͨ������' where reid='" + bean.getReid() + "'";
		} else {
			sql = "update line_cutinfo set acceptid='" + id + "',ISARCHIVE='�Ѿ��鵵' where reid='" + bean.getReid() + "'";
		}

		sqlAPP = "insert into line_cutapprove (id,reid,auditresult,audituserid,audittime,deptid,auditremark,auditacce,type) values('"
				+ id
				+ "','"
				+ bean.getReid()
				+ "','"
				+ bean.getAuditresult()
				+ "','"
				+ bean.getAudituserid()
				+ "',"
				+ "TO_DATE('"
				+ strDt
				+ "','YYYY-MM-DD HH24:MI:SS'),'"
				+ bean.getDeptid()
				+ "','"
				+ remark
				+ "','"
				+ bean.getAuditacce() + "','����')";
		// System.out.println( "SQL:" + sql );
		// System.out.println( "SQL:APP:" + sqlAPP );
		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			try {
				exec.executeUpdate(sql); // д�������
				exec.executeUpdate(sqlAPP);
				exec.commit();
				exec.setAutoCommitTrue();
				return true;
			} catch (Exception w) {
				logger.error("д����������Ϣִ��ʱ����:" + w.getMessage());
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("д����������Ϣ����:" + e.getMessage());
			return false;
		}
	}

	/**
	 * ��ȡ���еĴ�ά��λ��ź�����
	 * 
	 * @return
	 */
	public List getAllCon() {
		List conList = new ArrayList();
		ContractorBean bean = null;
		String sql = "select con.contractorID,con.contractorName from contractorinfo con where con.state is null";
		QueryUtil query = null;
		ResultSet rs = null;
		try {
			query = new QueryUtil();
			rs = query.executeQuery(sql);
			while (rs.next()) {
				bean = new ContractorBean();
				bean.setContractorID(rs.getString("contractorID"));
				bean.setContractorName(rs.getString("contractorName"));
				conList.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			query.close();
		}
		return conList;
	}

	/**
	 * <br>
	 * ����:�������ҵ�ǰ��λ�ĸ��������Ϣ <br>
	 * ����:request <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List queryAcc(LineCutBean bean, String deptType, HttpSession session) {
		List acclist;
		String sql = "";
		if ("2".equals(deptType)) {// ����Ǵ�ά�û�
			try {
				sql = "select l.numerical,l.cutType, l.REID,l.NAME,TO_CHAR( l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.ADDRESS, la.AUDITRESULT,TO_CHAR(la.AUDITTIME,'yyyy-mm-dd')audittime, u.username,l.isarchive "
						+ " from line_cutinfo l,line_cutapprove la, contractorinfo con,userinfo u "
						+ " where l.ACCEPTID = la.ID and l.contractorid ='"
						+ bean.getDeptid()
						+ "' and con.contractorid = u.deptid "
						+ "  and (l.isarchive='δͨ������' or l.isarchive='�Ѿ�����' or l.isarchive='�Ѿ��鵵' or l.isarchive='���ڹ鵵') "
						+ "  and (l.reuserid = u.userid)";
				if (bean.getName() != null && !bean.getName().equals("")) {
					sql = sql + "  and l.name like '" + bean.getName() + "%'  ";
				}
				if (bean.getAddress() != null && !bean.getAddress().equals("")) {
					sql = sql + "  and l.address like '" + bean.getAddress() + "%'  ";
				}
				if (bean.getAuditresult() != null && !bean.getAuditresult().equals("")) {
					sql = sql + "  and la.auditresult like '" + bean.getAuditresult() + "%'  ";
				}
				if (bean.getContractorid() != null && !bean.getContractorid().equals("")) {
					sql = sql + " and l.CONTRACTORID =" + bean.getContractorid();
				}
				if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
					sql = sql + " and la.audittime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
				}
				if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
					sql = sql + " and la.audittime <= TO_DATE('" + bean.getEndtime()
							+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
				}
				// ����ʱ������
				sql += " order by la.AUDITTIME desc";
				logger.info("linecutdao->queryAcc:" + sql);
				QueryUtil query = new QueryUtil();
				acclist = query.queryBeans(sql);
				session.setAttribute("lcQueryCon", sql);
				return acclist;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			try {
				sql = "select l.numerical,l.cutType, l.REID,l.NAME,TO_CHAR( l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.ADDRESS, la.AUDITRESULT,TO_CHAR(la.AUDITTIME,'yyyy-mm-dd')audittime, u.username,l.isarchive  "
						+ " from line_cutinfo l,line_cutapprove la, contractorinfo con,userinfo u "
						+ " where l.ACCEPTID = la.ID  and con.contractorid = u.deptid and "
						// �ϼ����ſ��Բ鿴�������ŵ����յ�
						+ "  (l.deptid='"
						+ bean.getDeptid()
						+ "' or l.deptid in (select deptid from deptinfo where parentid='"
						+ bean.getDeptid()
						+ "')) "
						+ "  and (l.isarchive='δͨ������' or l.isarchive='�Ѿ�����' or l.isarchive='�Ѿ��鵵' or l.isarchive='���ڹ鵵') "
						+ "  and (l.reuserid = u.userid)";
				if (bean.getName() != null && !bean.getName().equals("")) {
					sql = sql + "  and l.name like '" + bean.getName() + "%'  ";
				}
				if (bean.getAddress() != null && !bean.getAddress().equals("")) {
					sql = sql + "  and l.address like '" + bean.getAddress() + "%'  ";
				}
				if (bean.getAuditresult() != null && !bean.getAuditresult().equals("")) {
					sql = sql + "  and la.auditresult like '" + bean.getAuditresult() + "%'  ";
				}
				if (bean.getContractorid() != null && !bean.getContractorid().equals("")) {
					sql = sql + " and con.CONTRACTORID =" + bean.getContractorid();
				}
				if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
					sql = sql + " and la.audittime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
				}
				if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
					sql = sql + " and la.audittime <= TO_DATE('" + bean.getEndtime()
							+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
				}
				// ����ʱ������
				sql += " order by la.AUDITTIME desc";
				logger.info("linecutdao->queryAcc:" + sql);
				QueryUtil query = new QueryUtil();
				acclist = query.queryBeans(sql);
				session.setAttribute("lcQueryCon", sql);
				return acclist;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		/*
		 * try { sql = "select l.REID,l.NAME,TO_CHAR( l.essetime,'yyyy-MM-dd
		 * HH24:MI') essetime,l.ADDRESS,
		 * la.AUDITRESULT,TO_CHAR(la.AUDITTIME,'yyyy-mm-dd')audittime,
		 * u.username " + " from line_cutinfo l,line_cutapprove la,
		 * contractorinfo con,userinfo u " + " where l.ACCEPTID = la.ID and " //
		 * �ϼ����ſ��Բ鿴�������ŵ����յ� + " (l.deptid='" + bean.getDeptid() + "' or l.deptid
		 * in (select deptid from deptinfo where parentid='" + bean.getDeptid() +
		 * "')) " + " and (l.isarchive='�Ѿ�����' or l.isarchive='�Ѿ��鵵' or
		 * l.isarchive='���ڹ鵵') " + " and (l.reuserid = u.userid)"; if
		 * (bean.getName() != null && !bean.getName().equals("")) { sql = sql + "
		 * and l.name like '" + bean.getName() + "%' "; } if (bean.getAddress() !=
		 * null && !bean.getAddress().equals("")) { sql = sql + " and l.address
		 * like '" + bean.getAddress() + "%' "; } if (bean.getAuditresult() !=
		 * null && !bean.getAuditresult().equals("")) { sql = sql + " and
		 * la.auditresult like '" + bean.getAuditresult() + "%' "; } if
		 * (bean.getContractorid() != null &&
		 * !bean.getContractorid().endsWith("")) { sql = sql + " and
		 * con.CONTRACTORID =" + bean.getContractorid(); } if
		 * (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
		 * sql = sql + " and la.audittime >=TO_DATE('" + bean.getBegintime() +
		 * "','YYYY-MM-DD')"; } if (bean.getEndtime() != null &&
		 * !bean.getEndtime().equals("")) { sql = sql + " and la.audittime <=
		 * TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')"; } // ����ʱ������ //
		 * sql= sql + " order by l.retime desc"; sql += " order by la.AUDITTIME
		 * desc"; logger.info("linecutdao->queryAcc:" + sql); QueryUtil query =
		 * new QueryUtil(); acclist = query.queryBeans(sql); return acclist; }
		 * catch (Exception e) { logger.error("��õ�ǰ��λ�ĸ��������Ϣ:" +
		 * e.getMessage()); return null; }
		 */
	}

	public List queryAccAfterMod(String sql) {
		QueryUtil query = null;
		try {
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			query.close();
		}
	}

	/**
	 * <br>
	 * ����:���ָ��������뵥��Ϣ <br>
	 * ����:����id <br>
	 * ����ֵ:��óɹ����ض���,���򷵻� NULL;
	 */
	public LineCutBean getOneUseMod(String reid) {
		LineCutBean linecutbean = new LineCutBean();
		String sql = "";
		String sublinename = "";
		QueryUtil query = null;
		ResultSet rs = null;
		sql = "select l.cutType, l.numerical, l.REID,l.contractorid,l.reuserid,l.sublineid,con.CONTRACTORNAME,u.username,"
				+ "sub.sublinename,TO_CHAR(l.RETIME,'yyyy-MM-dd') retime,l.name,l.isarchive,l.updoc,l.reason,"
				+ "l.ADDRESS,TO_CHAR(l.PROTIME,'yyyy-MM-dd HH24:MI') protime,l.prousetime,l.PROVALUE,l.EFSYSTEM,"
				+ "l.REREMARK,l.REACCE from line_cutinfo l,contractorinfo con,userinfo u,sublineinfo sub"
				+ " where l.CONTRACTORID = con.CONTRACTORID and l.reuserid = u.userid "
				+ " and l.reid='"
				+ reid
				+ "'"
				+ " and sub.sublineid in( select * from table ( cast ( myfn_split ((select l.sublineid from line_cutinfo l where l.reid='"
				+ reid + "'), ',' ) as ty_str_split ) ) )";
		try {
			query = new QueryUtil();
			rs = query.executeQuery(sql);
			while (rs.next()) {
				linecutbean.setCutType(rs.getString("cutType"));
				linecutbean.setNumerical(rs.getString("numerical"));
				linecutbean.setReid(rs.getString("reid"));
				linecutbean.setContractorid(rs.getString("contractorid"));
				linecutbean.setReuserid(rs.getString("reuserid"));
				linecutbean.setSublineid(rs.getString("sublineid"));
				linecutbean.setContractorname(rs.getString("contractorname"));
				linecutbean.setUsername(rs.getString("username"));
				linecutbean.setRetime(rs.getString("retime"));
				linecutbean.setName(rs.getString("name"));
				linecutbean.setReason(rs.getString("reason"));
				linecutbean.setAddress(rs.getString("address"));
				linecutbean.setProtime(rs.getString("protime"));
				linecutbean.setProusetime(rs.getString("prousetime"));
				linecutbean.setProvalue(rs.getString("provalue"));
				linecutbean.setEfsystem(rs.getString("efsystem"));
				linecutbean.setReremark(rs.getString("reremark"));
				linecutbean.setReacce(rs.getString("reacce"));
				linecutbean.setIsarchive(rs.getString("isarchive"));
				linecutbean.setUpdoc(rs.getString("updoc"));
				if (sublinename == "") {
					sublinename = rs.getString("sublinename") + "<br>";
				} else {
					sublinename = sublinename + rs.getString("sublinename") + "<br>";
				}
			}
			linecutbean.setSublinename(sublinename);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("���ָ��������뵥��������Ϣ�쳣:" + e.getMessage());
			return null;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			query.close();
		}
		return linecutbean;
	}

	/**
	 * <br>
	 * ����:���ָ��������뵥��Ϣ <br>
	 * ����:����id <br>
	 * ����ֵ:��óɹ����ض���,���򷵻� NULL;
	 */
	/*
	 * public LineCutBean getOneUse(String reid) { LineCutBean linecutbean = new
	 * LineCutBean(); String sql = ""; String sublinename =
	 * this.getSubLineNameCon(reid); QueryUtil query = null; ResultSet rst =
	 * null; sql = "select
	 * l.REID,l.contractorid,l.reuserid,l.sublineid,con.CONTRACTORNAME,u.username,TO_CHAR(l.RETIME,'yyyy-MM-dd')
	 * retime,l.name,l.isarchive,l.updoc, " + "
	 * l.reason,l.ADDRESS,TO_CHAR(l.PROTIME,'yyyy-MM-dd HH24:MI') protime, " + "
	 * l.prousetime,l.PROVALUE,l.EFSYSTEM,l.REREMARK,l.REACCE " + " from
	 * line_cutinfo l,contractorinfo con,userinfo u, sublineinfo sub " + " where
	 * l.CONTRACTORID = con.CONTRACTORID and l.reuserid = u.userid and l.reid='" +
	 * reid + "'"; System.out.println("linecutdao->getOneUse:" + sql); try {
	 * query = new QueryUtil(); rst = query.executeQuery(sql); linecutbean =
	 * this.popuLineCutBeanMulitSubLine2(rst, sublinename); } catch (Exception
	 * e) { logger.error("���ָ��������뵥��������Ϣ�쳣:" + e.getMessage());
	 * e.printStackTrace(); return null; } finally { try { rst.close(); } catch
	 * (SQLException e) { e.printStackTrace(); return null; } query.close(); }
	 * return linecutbean; }
	 */

	/**
	 * <br>
	 * ����:���ָ��������뵥��������Ϣ <br>
	 * ����:����id <br>
	 * ����ֵ:��óɹ����ض���,���򷵻� NULL;
	 */
	public LineCutBean getOneAuInfoMod(String reid) {
		LineCutBean linecutbean = new LineCutBean();
		String sql = "";
		String sublinename = "";
		QueryUtil query = null;
		ResultSet rs = null;
		sql = "select l.cutType, l.reid,TO_CHAR(l.retime,'yyyy-MM-dd') retime,l.name,l.REASON,l.ADDRESS,TO_CHAR(l.protime,'yyyy-MM-dd HH24:MI') protime,"
				+ "l.updoc,l.provalue,l.EFSYSTEM,l.REREMARK,l.REACCE,TO_CHAR(l.audittime,'yyyy-MM-dd') audittime,"
				+ "l.auditresult,l.auditremark,l.auditacce,d.DEPTNAME,con.CONTRACTORNAME,u.USERNAME,sub.sublinename "
				+ " from line_cutinfo l,deptinfo d, contractorinfo con,userinfo u, sublineinfo sub "
				+ " where l.DEPTID=d.DEPTID and l.CONTRACTORID = con.CONTRACTORID and l.AUDITUSERID=u.USERID  and l.reid='"
				+ reid
				+ "' and sub.sublineid in ( select * from table ( cast ( myfn_split ((select l.sublineid from line_cutinfo l where l.reid='"
				+ reid + "'), ',' ) as ty_str_split ) ) )";
		try {
			logger.info("LineCutDao:getOneAuInfoMod:" + sql);
			query = new QueryUtil();
			rs = query.executeQuery(sql);
			while (rs.next()) {
				linecutbean.setCutType(rs.getString("cutType"));
				linecutbean.setReid(rs.getString("reid"));
				linecutbean.setRetime(rs.getString("retime"));
				linecutbean.setName(rs.getString("name"));
				linecutbean.setReason(rs.getString("reason"));
				linecutbean.setAddress(rs.getString("address"));
				linecutbean.setProtime(rs.getString("protime"));
				linecutbean.setProvalue(rs.getString("provalue"));
				linecutbean.setEfsystem(rs.getString("efsystem"));
				linecutbean.setReremark(rs.getString("reremark"));
				linecutbean.setReacce(rs.getString("reacce"));
				linecutbean.setAudittime(rs.getString("audittime"));
				linecutbean.setAuditresult(rs.getString("auditresult"));
				linecutbean.setAuditremark(rs.getString("auditremark"));
				linecutbean.setAuditacce(rs.getString("auditacce"));
				linecutbean.setDeptname(rs.getString("deptname"));
				linecutbean.setContractorname(rs.getString("contractorname"));
				linecutbean.setUsername(rs.getString("username"));
				linecutbean.setUpdoc(rs.getString("updoc"));
				if (sublinename == "") {
					sublinename = rs.getString("sublinename") + "<br>";
				} else {
					sublinename = sublinename + rs.getString("sublinename") + "<br>";
				}
			}
			linecutbean.setSublinename(sublinename);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			query.close();
		}
		return linecutbean;
	}

	/**
	 * <br>
	 * ����:���ָ��������뵥��������Ϣ <br>
	 * ����:����id <br>
	 * ����ֵ:��óɹ����ض���,���򷵻� NULL;
	 */
	/*
	 * public LineCutBean getOneAuInfo(String reid) { LineCutBean linecutbean =
	 * new LineCutBean(); String sql = ""; String sublinename =
	 * this.getSubLineNameCon(reid); QueryUtil query = null; ResultSet rs =
	 * null; sql = "select l.reid,TO_CHAR(l.retime,'yyyy-MM-dd')
	 * retime,l.name,l.REASON,l.ADDRESS,TO_CHAR(l.protime,'yyyy-MM-dd HH24:MI')
	 * protime,l.updoc," + "
	 * l.provalue,l.EFSYSTEM,l.REREMARK,l.REACCE,TO_CHAR(l.audittime,'yyyy-MM-dd')
	 * audittime,l.auditresult,l.auditremark,l.auditacce," + "
	 * d.DEPTNAME,con.CONTRACTORNAME,u.USERNAME " + " from line_cutinfo
	 * l,deptinfo d, contractorinfo con,userinfo u " + " where l.DEPTID=d.DEPTID
	 * and l.CONTRACTORID = con.CONTRACTORID and l.AUDITUSERID=u.USERID and
	 * l.reid='" + reid + "'"; System.out.println("linecutdao->getOneAuInfo" +
	 * sql); try { query = new QueryUtil(); rs = query.executeQuery(sql);
	 * linecutbean = this.popuLineCutBeanMulitSubLine1(rs, sublinename); } catch
	 * (Exception e) { logger.error("���ָ��������뵥��������Ϣ�쳣:" + e.getMessage());
	 * e.printStackTrace(); return null; } finally { try { rs.close(); } catch
	 * (SQLException e) { e.printStackTrace(); } query.close(); } return
	 * linecutbean; }
	 */

	/**
	 * <br>
	 * ����:���ָ�����������Ϣ. <br>
	 * ����:���id <br>
	 * ����ֵ:��óɹ����ض���,���򷵻� NULL;
	 */
	public LineCutBean getOneAccMod(String reid) {
		LineCutBean bean = new LineCutBean();
		String sublinename = "";
		QueryUtil query = null;
		ResultSet rs = null;
		String sql = "";
		sql = "select l.cutType, l.REID,l.NAME,l.ADDRESS,l.ISARCHIVE,l.EFSYSTEM,l.updoc,l.reason,l.usetime, "
				+ "     l.PROVALUE,l.ENDVALUE,l.MANPOWER,TO_CHAR(l.ESSETIME,'yyyy-mm-dd HH24:MI') essetime,sub.sublinename,"
				+ "     la.AUDITACCE,la.AUDITREMARK,la.AUDITRESULT, "
				+ "    TO_CHAR(la.AUDITTIME,'yyyy-mm-dd') audittime "
				+ " from line_cutinfo l,line_cutapprove la,sublineinfo sub "
				+ " where l.ACCEPTID = la.ID and l.reid='"
				+ reid
				+ "' and sub.sublineid in ( select * from table ( cast ( myfn_split ((select l.sublineid from line_cutinfo l where l.reid='"
				+ reid + "'), ',' ) as ty_str_split ) ) )";
		try {
			query = new QueryUtil();
			rs = query.executeQuery(sql);
			while (rs.next()) {
				bean.setCutType(rs.getString("cutType"));
				bean.setReid(rs.getString("reid"));
				bean.setName(rs.getString("name"));
				bean.setAddress(rs.getString("address"));
				bean.setIsarchive(rs.getString("isarchive"));
				bean.setEfsystem(rs.getString("efsystem"));
				bean.setProvalue(rs.getString("provalue"));
				bean.setEndvalue(rs.getString("endvalue"));
				bean.setManpower(rs.getString("manpower"));
				bean.setEssetime(rs.getString("essetime"));
				bean.setReason(rs.getString("reason"));
				bean.setAuditacce(rs.getString("auditacce"));
				bean.setAuditremark(rs.getString("auditremark"));
				bean.setAuditresult(rs.getString("auditresult"));
				bean.setAudittime(rs.getString("audittime"));
				bean.setUpdoc(rs.getString("updoc"));
				bean.setUsetime(rs.getString("usetime"));
				if (sublinename == "") {
					sublinename = rs.getString("sublinename") + "<br>";
				} else {
					sublinename = sublinename + rs.getString("sublinename") + "<br>";
				}
			}
			bean.setSublinename(sublinename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * <br>
	 * ����:���ָ�����������Ϣ. <br>
	 * ����:���id <br>
	 * ����ֵ:��óɹ����ض���,���򷵻� NULL;
	 */
	/*
	 * public LineCutBean getOneAcc(String reid) { LineCutBean bean = new
	 * LineCutBean(); String sublinename = this.getSubLineNameCon(reid);
	 * QueryUtil query = null; ResultSet rs = null; String sql = "select
	 * l.REID,l.NAME,l.ADDRESS,l.ISARCHIVE,l.EFSYSTEM,l.updoc, " + "
	 * l.PROVALUE,l.ENDVALUE,l.MANPOWER,TO_CHAR(l.ESSETIME,'yyyy-mm-dd HH24:MI')
	 * essetime," + " la.AUDITACCE,la.AUDITREMARK,la.AUDITRESULT, " + "
	 * TO_CHAR(la.AUDITTIME,'yyyy-mm-dd') audittime " + " from line_cutinfo
	 * l,line_cutapprove la " + " where l.ACCEPTID = la.ID and l.reid='" + reid +
	 * "'"; System.out.println("linecutdao->getOneAcc" + sql); try { query = new
	 * QueryUtil(); rs = query.executeQuery(sql); bean =
	 * this.popuLineCutBeanMulitSubLine(rs, sublinename); } catch (Exception e) {
	 * e.printStackTrace(); return null; } finally { try { rs.close(); } catch
	 * (SQLException e) { e.printStackTrace(); } query.close(); } return bean; }
	 */

	/**
	 * <br>
	 * ����:���ָ��������뵥��Ϣ�������. <br>
	 * ����:����id <br>
	 * ����ֵ:��óɹ����ض���,���򷵻� NULL;
	 */
	public LineCutBean getOneUseForDMod(String reid) {
		LineCutBean bean = new LineCutBean();
		String sql = "";
		String sublinename = "";
		QueryUtil query = null;
		ResultSet rs1 = null;
		sql = "select l.cutType,l.numerical,l.REID,l.contractorid,l.reuserid,l.sublineid,con.CONTRACTORNAME,u.username,TO_CHAR(l.RETIME,'yyyy-MM-dd') retime,l.name,l.isarchive, l.updoc, sub.sublinename, "
				+ "     l.reason,l.ADDRESS,TO_CHAR(l.PROTIME,'yyyy-MM-dd HH24:MI') protime, "
				+ "     l.prousetime,l.PROVALUE,l.EFSYSTEM,l.REREMARK,l.REACCE,u2.username auusername,TO_CHAR(l.audittime,'yyyy-MM-dd HH24:MI') audittime, dept.deptname,"
				+ "     l.auditresult,l.auditremark,l.auditacce"
				+ " from line_cutinfo l,contractorinfo con,userinfo u,userinfo u2,deptinfo dept, sublineinfo sub "
				+ " where l.CONTRACTORID = con.CONTRACTORID and l.reuserid = u.userid  and l.audituserid=u2.userid and l.deptid=dept.deptid"
				+ "       and l.reid='"
				+ reid
				+ "' and sub.sublineid in ( select * from table ( cast ( myfn_split ((select l.sublineid from line_cutinfo l where l.reid='"
				+ reid + "'), ',' ) as ty_str_split ) ) )";
		try {
			query = new QueryUtil();
			rs1 = query.executeQuery(sql);
			while (rs1.next()) {
				bean.setNumerical(rs1.getString("numerical"));
				bean.setCutType(rs1.getString("cutType"));
				bean.setReid(rs1.getString("reid"));
				bean.setContractorid(rs1.getString("contractorid"));
				bean.setReuserid(rs1.getString("reuserid"));
				bean.setSublineid(rs1.getString("sublineid"));
				bean.setContractorname(rs1.getString("contractorname"));
				bean.setUsername(rs1.getString("username"));
				bean.setRetime(rs1.getString("retime"));
				bean.setName(rs1.getString("name"));
				bean.setReason(rs1.getString("reason"));
				bean.setAddress(rs1.getString("address"));
				bean.setProtime(rs1.getString("protime"));
				bean.setProusetime(rs1.getString("prousetime"));
				bean.setProvalue(rs1.getString("provalue"));
				bean.setEfsystem(rs1.getString("efsystem"));
				bean.setReremark(rs1.getString("reremark"));
				bean.setReacce(rs1.getString("reacce"));
				bean.setAudituserid(rs1.getString("auusername"));
				bean.setAudittime(rs1.getString("audittime"));
				bean.setDeptname(rs1.getString("deptname"));
				bean.setAuditresult(rs1.getString("auditresult"));
				bean.setAuditremark(rs1.getString("auditremark"));
				bean.setAuditacce(rs1.getString("auditacce"));
				bean.setIsarchive(rs1.getString("isarchive"));
				bean.setUpdoc(rs1.getString("updoc"));
				if (sublinename == "") {
					sublinename = rs1.getString("sublinename") + "<br>";
				} else {
					sublinename = sublinename + rs1.getString("sublinename") + "<br>";
				}
			}
			bean.setSublinename(sublinename);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			query.close();
		}
		return bean;
	}

	public List getReasonByInput(String searchCt, String contractorid) {
		QueryUtil query = null;
		List reasonList = null;
		String sql = "select distinct reason from line_cutinfo where contractorid='" + contractorid
				+ "' and reason like '%" + searchCt + "%'" + " order by reason desc";
		logger.info("LineCutDao:getReasonByInput:" + sql);
		try {
			query = new QueryUtil();
			reasonList = query.queryBeans(sql);
			return reasonList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ģ����ѯԭ���б����" + e.getMessage());
			return null;
		}
	}

	public String getReasonByInputMod(String searchCt, String contractorid) {
		QueryUtil query = null;
		List reasonList = null;
		String sql = "select distinct reason from line_cutinfo where contractorid='" + contractorid
				+ "' and reason like '%" + searchCt + "%'" + " order by reason desc";
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		sb.append("new Array(");
		try {
			query = new QueryUtil();
			rs = query.executeQuery(sql);
			while (rs.next()) {
				sb.append("new Array('").append(rs.getString("reason")).append("'),");
			}
			int length = sb.toString().length() - 1;
			String res = sb.toString().substring(0, length) + ")";
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ģ����ѯԭ���б����" + e.getMessage());
			return null;
		}
	}

	/**
	 * ��ø��ԭ���б� <br>
	 * ����: <br>
	 * ����:��άid <br>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllReasons(String contractorid) {
		List reqPart = null;
		try {
			String sql = "select distinct reason,reid from line_cutinfo  " + " where contractorid='" + contractorid
					+ "' order by reid desc";
			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("��ø��ԭ���б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * ����:���ָ��������뵥��Ϣ�������. <br>
	 * ����:����id <br>
	 * ����ֵ:��óɹ����ض���,���򷵻� NULL;
	 */
	/*
	 * public LineCutBean getOneUseForD(String reid) { LineCutBean bean = new
	 * LineCutBean(); String sql = ""; String sublinename =
	 * this.getSubLineNameCon(reid); QueryUtil query = null; ResultSet rs =
	 * null; sql = "select
	 * l.REID,l.contractorid,l.reuserid,l.sublineid,con.CONTRACTORNAME,u.username,TO_CHAR(l.RETIME,'yyyy-MM-dd')
	 * retime,l.name,l.isarchive, l.updoc, " + "
	 * l.reason,l.ADDRESS,TO_CHAR(l.PROTIME,'yyyy-MM-dd HH24:MI') protime, " + "
	 * l.prousetime,l.PROVALUE,l.EFSYSTEM,l.REREMARK,l.REACCE,u2.username
	 * auusername,TO_CHAR(l.audittime,'yyyy-MM-dd HH24:MI') audittime,
	 * dept.deptname," + " l.auditresult,l.auditremark,l.auditacce" + " from
	 * line_cutinfo l,contractorinfo con,userinfo u,userinfo u2,deptinfo dept" + "
	 * where l.CONTRACTORID = con.CONTRACTORID and l.reuserid = u.userid and
	 * l.audituserid=u2.userid and l.deptid=dept.deptid" + " and l.reid='" +
	 * reid + "'"; System.out.println("linecutdao->getOneUseForD" + sql); try {
	 * query = new QueryUtil(); rs = query.executeQuery(sql); bean =
	 * this.popuLineCutBeanMulitSubLine3(rs, sublinename); } catch (Exception e) {
	 * e.printStackTrace(); return null; } finally { try { rs.close(); } catch
	 * (SQLException e) { e.printStackTrace(); return null; } query.close(); }
	 * return bean; }
	 */

	// ���ָ�����������Ϣ."select
	// l.REID,l.NAME,l.ADDRESS,l.ISARCHIVE,TO_CHAR(la.AUDITTIME,'yyyy-mm-dd'),l.EFSYSTEM,
	// "
	// + " l.PROVALUE,l.ENDVALUE,l.MANPOWER,TO_CHAR(l.ESSETIME,'yyyy-mm-dd
	// HH24:MI') essetime,la.AUDITACCE,la.AUDITREMARK,la.AUDITRESULT, "
	// + " TO_CHAR(la.AUDITTIME,'yyyy-mm-dd') audittime "
	/*
	 * private LineCutBean popuLineCutBeanMulitSubLine(ResultSet rs, String
	 * sublinename) throws SQLException { LineCutBean bean = new LineCutBean();
	 * if (rs.next()) { bean.setReid(rs.getString("reid"));
	 * bean.setName(rs.getString("name"));
	 * bean.setAddress(rs.getString("address"));
	 * bean.setIsarchive(rs.getString("isarchive"));
	 * bean.setEfsystem(rs.getString("efsystem"));
	 * bean.setProvalue(rs.getString("provalue"));
	 * bean.setEndvalue(rs.getString("endvalue"));
	 * bean.setManpower(rs.getString("manpower"));
	 * bean.setEssetime(rs.getString("essetime"));
	 * bean.setAuditacce(rs.getString("auditacce"));
	 * bean.setAuditremark(rs.getString("auditremark"));
	 * bean.setAuditresult(rs.getString("auditresult"));
	 * bean.setAudittime(rs.getString("audittime"));
	 * bean.setUpdoc(rs.getString("updoc")); bean.setSublinename(sublinename); }
	 * return bean; }
	 */

	// ���ָ��������뵥��������Ϣ
	// select l.reid,TO_CHAR(l.retime,'yyyy-MM-dd')
	// retime,l.name,l.REASON,l.ADDRESS,TO_CHAR(l.protime,'yyyy-MM-dd HH24:MI')
	// protime,"
	// + "
	// l.provalue,l.EFSYSTEM,l.REREMARK,l.REACCE,TO_CHAR(l.audittime,'yyyy-MM-dd')
	// audittime,l.auditresult,l.auditremark,auditacce,"
	// + " d.DEPTNAME,con.CONTRACTORNAME,u.USERNAME "
	/*
	 * private LineCutBean popuLineCutBeanMulitSubLine1(ResultSet rs, String
	 * sublinename) throws SQLException { LineCutBean linecutbean = new
	 * LineCutBean(); if (rs.next()) {
	 * linecutbean.setReid(rs.getString("reid"));
	 * linecutbean.setRetime(rs.getString("retime"));
	 * linecutbean.setName(rs.getString("name"));
	 * linecutbean.setReason(rs.getString("reason"));
	 * linecutbean.setAddress(rs.getString("address"));
	 * linecutbean.setProtime(rs.getString("protime"));
	 * linecutbean.setProvalue(rs.getString("provalue"));
	 * linecutbean.setEfsystem(rs.getString("efsystem"));
	 * linecutbean.setReremark(rs.getString("reremark"));
	 * linecutbean.setReacce(rs.getString("reacce"));
	 * linecutbean.setAudittime(rs.getString("audittime"));
	 * linecutbean.setAuditresult(rs.getString("auditresult"));
	 * linecutbean.setAuditremark(rs.getString("auditremark"));
	 * linecutbean.setAuditacce(rs.getString("auditacce"));
	 * linecutbean.setDeptname(rs.getString("deptname"));
	 * linecutbean.setContractorname(rs.getString("contractorname"));
	 * linecutbean.setUsername(rs.getString("username"));
	 * linecutbean.setUpdoc(rs.getString("updoc"));
	 * linecutbean.setSublinename(sublinename); } return linecutbean; }
	 */

	// ���ָ��������뵥��Ϣreid,contractorid,reuserid,sublineid,CONTRACTORNAME,username,retime
	// name,isarchive,reason,ADDRESS,protime,prousetime,PROVALUE,EFSYSTEM,REREMARK,REACCE
	/*
	 * private LineCutBean popuLineCutBeanMulitSubLine2(ResultSet rs, String
	 * sublinename) throws SQLException { LineCutBean linecutbean = new
	 * LineCutBean(); if (rs.next()) {
	 * linecutbean.setReid(rs.getString("reid"));
	 * linecutbean.setContractorid(rs.getString("contractorid"));
	 * linecutbean.setReuserid(rs.getString("reuserid"));
	 * linecutbean.setSublineid(rs.getString("sublineid"));
	 * linecutbean.setContractorname(rs.getString("contractorname"));
	 * linecutbean.setUsername(rs.getString("username"));
	 * linecutbean.setRetime(rs.getString("retime"));
	 * linecutbean.setName(rs.getString("name"));
	 * linecutbean.setReason(rs.getString("reason"));
	 * linecutbean.setAddress(rs.getString("address"));
	 * linecutbean.setProtime(rs.getString("protime"));
	 * linecutbean.setProusetime(rs.getString("prousetime"));
	 * linecutbean.setProvalue(rs.getString("provalue"));
	 * linecutbean.setEfsystem(rs.getString("efsystem"));
	 * linecutbean.setReremark(rs.getString("reremark"));
	 * linecutbean.setReacce(rs.getString("reacce"));
	 * linecutbean.setIsarchive(rs.getString("isarchive"));
	 * linecutbean.setUpdoc(rs.getString("updoc"));
	 * linecutbean.setSublinename(sublinename); } return linecutbean; }
	 */

	// ���ָ��������뵥��Ϣ�������
	// l.REID,l.contractorid,l.reuserid,l.sublineid,con.CONTRACTORNAME,u.username,TO_CHAR(l.RETIME,'yyyy-MM-dd')
	// retime,l.name,l.isarchive, "
	// + " l.reason,l.ADDRESS,TO_CHAR(l.PROTIME,'yyyy-MM-dd HH24:MI') protime, "
	// + " l.prousetime,l.PROVALUE,l.EFSYSTEM,l.REREMARK,l.REACCE,u2.username
	// auusername,TO_CHAR(l.audittime,'yyyy-MM-dd HH24:MI') audittime,
	// dept.deptname,"
	// s+ " l.auditresult,l.auditremark,l.auditacce"
	/*
	 * private LineCutBean popuLineCutBeanMulitSubLine3(ResultSet rs1, String
	 * sublinename) throws SQLException { LineCutBean bean = new LineCutBean();
	 * if (rs1.next()) { bean.setReid(rs1.getString("reid"));
	 * bean.setContractorid(rs1.getString("contractorid"));
	 * bean.setReuserid(rs1.getString("reuserid"));
	 * bean.setSublineid(rs1.getString("sublineid"));
	 * bean.setContractorname(rs1.getString("contractorname"));
	 * bean.setUsername(rs1.getString("username"));
	 * bean.setRetime(rs1.getString("retime"));
	 * bean.setName(rs1.getString("name"));
	 * bean.setReason(rs1.getString("reason"));
	 * bean.setAddress(rs1.getString("address"));
	 * bean.setProtime(rs1.getString("protime"));
	 * bean.setProusetime(rs1.getString("prousetime"));
	 * bean.setProvalue(rs1.getString("provalue"));
	 * bean.setEfsystem(rs1.getString("efsystem"));
	 * bean.setReremark(rs1.getString("reremark"));
	 * bean.setReacce(rs1.getString("reacce"));
	 * bean.setAudituserid(rs1.getString("auusername"));
	 * bean.setAudittime(rs1.getString("audittime"));
	 * bean.setDeptname(rs1.getString("deptname"));
	 * bean.setAuditresult(rs1.getString("auditresult"));
	 * bean.setAuditremark(rs1.getString("auditremark"));
	 * bean.setAuditacce(rs1.getString("auditacce"));
	 * bean.setIsarchive(rs1.getString("isarchive"));
	 * bean.setUpdoc(rs1.getString("updoc")); bean.setSublinename(sublinename); }
	 * return bean; }
	 */

	/*
	 * private String getSubLineIdCon(String reid) { String sublineidCon = "";
	 * String sql = "select sublineid from line_cutinfo where reid='" + reid +
	 * "'"; QueryUtil query = null; ResultSet rs = null; try { query = new
	 * QueryUtil(); rs = query.executeQuery(sql); if (rs.next()) { sublineidCon =
	 * rs.getString("sublineid"); } } catch (Exception e1) {
	 * e1.printStackTrace(); return null; } finally { query.close(); } return
	 * sublineidCon; }
	 */

	private String getSubLineNameCon(String reid) {
		String sublinenameCon = "";
		String sublineidCon = "";
		String sublineid = null;
		String sql = "select sublineid from line_cutinfo where reid='" + reid + "'";
		QueryUtil query = null;
		ResultSet rs = null;
		try {
			query = new QueryUtil();
			rs = query.executeQuery(sql);
			if (rs.next()) {
				sublineidCon = rs.getString("sublineid");
				if (sublineidCon.indexOf(",") > 0) {// ����漰��������߶�
					String[] strArr = sublineidCon.split(",");
					for (int i = 0; i < strArr.length; i++) {// ƴ���߶ε�ID
						if (sublineid == null) {
							sublineid = "'" + strArr[i] + "'";
						} else {
							sublineid = sublineid + ",'" + strArr[i] + "'";
						}
					}
					sql = "select sub.sublinename from sublineinfo sub where sub.sublineid in (" + sublineid
							+ ") order by sublinename";
					rs = query.executeQuery(sql);
					while (rs.next()) {
						if (sublinenameCon == "") {
							sublinenameCon = rs.getString("SUBLINENAME") + "<br>";
						} else {
							sublinenameCon = sublinenameCon + rs.getString("SUBLINENAME") + "<br>";
						}
					}
				} else {
					sql = "select sublinename from sublineinfo where sublineid =" + sublineidCon;
					rs = query.executeQuery(sql);
					if (rs.next()) {
						sublinenameCon = rs.getString("SUBLINENAME");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			query.close();
		}
		return sublinenameCon;
	}

	/**
	 * ���ݸ�����ͣ����� ͳ�Ƹ�Ӵ���
	 * 
	 * @param condition
	 * @return
	 */
	public List doStatQueryForCountByCutType(String condition) {
		String sql = "select tab1.newcut,tab1.optimizecut,tab1.changecut,tab1.repairecut,tab1.totalnum,con.contractorname from contractorinfo con ,"
				+ " (select max(case tb.cutType when '�½����' then tb.totalnum else 0 end) newcut,"
				+ " max(case tb.cutType when '�Ż����' then tb.totalnum else 0 end) optimizecut,"
				+ " max(case tb.cutType when 'Ǩ���Ը��' then tb.totalnum else 0 end) changecut,"
				+ " max(case tb.cutType when '�޸��Ը��' then tb.totalnum else 0 end) repairecut,"
				+ " sum(tb.totalnum) as totalnum,tb.contractorid from "
				+ " (select tab.totalnum,tab.cuttype,tab.contractorid,con.contractorname from "
				+ " (select count(lc.reid) as totalnum,lc.cuttype,lc.contractorid from line_cutinfo lc where"
				+ " lc.isarchive='�Ѿ��鵵' "
				+ condition
				+ " group by lc.cuttype,lc.contractorid) tab,"
				+ " contractorinfo con where tab.contractorid=con.contractorid) tb group by tb.contractorid) tab1 where tab1.contractorid=con.contractorid";
		QueryUtil qu = null;
		logger.info("LineCutDao:doStatQueryForCutType:" + sql);
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sql);
		} catch (Exception e) {
			logger.info("���ݸ�����ͣ�������ͳ�Ƹ�Ӵ��������쳣" + e.getMessage());
			return null;
		} finally {
			qu.close();
		}
	}

	/**
	 * ������·�������� ͳ�Ƹ�Ӵ���
	 * 
	 * @param condition
	 * @return
	 */
	public List doQueryForCountByLevel(String condition) {
		String sql = "select con.contractorname,tab2.one,tab2.two,tab2.three,tab2.four,tab2.five,tab2.totalnum from contractorinfo con ,"
				+ " (select max(case tab1.linetype when '1' then tab1.totalnum else 0 end) one,"
				+ " max(case tab1.linetype when '2' then tab1.totalnum else 0 end) two,"
				+ " max(case tab1.linetype when '3' then tab1.totalnum else 0 end) three,"
				+ " max(case tab1.linetype when '4' then tab1.totalnum else 0 end) four,"
				+ " max(case tab1.linetype when '5' then tab1.totalnum else 0 end) five,"
				+ " sum(tab1.totalnum) as totalnum,tab1.contractorid from "
				+ " (select count(lc.reid) as totalnum,tab.linetype,lc.contractorid from line_cutinfo lc,"
				+ " (select sub.sublineid,tb.linetype from sublineinfo sub,(select line.linetype,line.lineid from lineinfo line) tb "
				+ " where sub.lineid=tb.lineid) tab "
				+ "  where lc.isarchive='�Ѿ��鵵' and subStr(lc.sublineid,0,8)=tab.sublineid "
				+ condition
				+ " group by tab.linetype,lc.contractorid) tab1"
				+ " group by tab1.contractorid) tab2 where tab2.contractorid=con.contractorid";
		QueryUtil qu = null;
		logger.info("LineCutDao:doQueryForLevelByCount:" + sql);
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sql);
		} catch (Exception e) {
			logger.info("������·�������� ͳ�Ƹ�Ӵ��������쳣" + e.getMessage());
			return null;
		} finally {
			qu.close();
		}
	}

	/**
	 * ������·���� ���� ͳ�Ƹ��ʱ��
	 * 
	 * @param condition
	 * @return
	 */
	public List doQueryForTimeByLevel(String condition) {
		String sql = "select con.contractorname,tab2.one,tab2.two,tab2.three,tab2.four,tab2.five,tab2.totalnum from contractorinfo con ,"
				+ " (select max(case tab1.linetype when '1' then tab1.totalnum else 0 end) one,"
				+ " max(case tab1.linetype when '2' then tab1.totalnum else 0 end) two,"
				+ " max(case tab1.linetype when '3' then tab1.totalnum else 0 end) three,"
				+ " max(case tab1.linetype when '4' then tab1.totalnum else 0 end) four,"
				+ " max(case tab1.linetype when '5' then tab1.totalnum else 0 end) five,"
				+ " sum(tab1.totalnum) as totalnum,tab1.contractorid from "
				+ " (select sum(lc.usetime*60) as totalnum,tab.linetype,lc.contractorid from line_cutinfo lc,"
				+ " (select sub.sublineid,tb.linetype from sublineinfo sub,(select line.linetype,line.lineid from lineinfo line) tb "
				+ " where sub.lineid=tb.lineid) tab "
				+ "  where lc.isarchive='�Ѿ��鵵' and subStr(lc.sublineid,0,8)=tab.sublineid "
				+ condition
				+ " group by tab.linetype,lc.contractorid) tab1"
				+ " group by tab1.contractorid) tab2 where tab2.contractorid=con.contractorid";
		QueryUtil qu = null;
		try {
			qu = new QueryUtil();
			logger.info("LineCutDao:doQueryForLevelByTime:" + sql);
			return qu.queryBeans(sql);
		} catch (Exception e) {
			logger.info("������·���� ���� ͳ�Ƹ��ʱ������쳣" + e.getMessage());
			return null;
		} finally {
			qu.close();
		}
	}

	/**
	 * ���ݸ������ �� ������ѯ ��Ӻ�ʱ(��ʱû��)
	 * @param condition
	 * @return
	 */
	public List doQueryForTimeByType(String condition) {
		String sql = "select tab1.newcut,tab1.optimizecut,tab1.changecut,tab1.repairecut,tab1.totalnum,con.contractorname from contractorinfo con ,"
				+ " (select max(case tb.cutType when '�½����' then tb.totalnum else 0 end) newcut,"
				+ " max(case tb.cutType when '�Ż����' then tb.totalnum else 0 end) optimizecut,"
				+ " max(case tb.cutType when 'Ǩ���Ը��' then tb.totalnum else 0 end) changecut,"
				+ " max(case tb.cutType when '�޸��Ը��' then tb.totalnum else 0 end) repairecut,"
				+ " sum(tb.totalnum) as totalnum,tb.contractorid from "
				+ " (select tab.totalnum,tab.cuttype,tab.contractorid,con.contractorname from "
				+ " (select sum(lc.usetime*60) as totalnum,lc.cuttype,lc.contractorid from line_cutinfo lc where"
				+ " lc.isarchive='�Ѿ��鵵' "
				+ condition
				+ " group by lc.cuttype,lc.contractorid) tab,"
				+ " contractorinfo con where tab.contractorid=con.contractorid) tb group by tb.contractorid) tab1 where tab1.contractorid=con.contractorid";
		QueryUtil qu = null;
		logger.info("LineCutDao:doQueryForTimeByType:" + sql);
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sql);
		} catch (Exception e) {
			logger.info("���ݸ�����ͣ�������ͳ�Ƹ�Ӻ�ʱ�����쳣" + e.getMessage());
			return null;
		} finally {
			qu.close();
		}
	}

	//������������� ��Ӵ�����ͳ��
	public boolean exportCountByType(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "���ͳ����Ϣһ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.exportCountByType");
			StatTemplate template = new StatTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportForCountByType(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}
	}

	//��������·���� ��Ӵ�����ͳ��
	public boolean exportCountByLevel(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "���ͳ����Ϣһ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.exportCountByLevel");
			StatTemplate template = new StatTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportForCountByLevel(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}
	}

	//������ѯͳ���в�ѯ��������
	public boolean exportQueryStat(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "��Ӳ�ѯ������Ϣһ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.linecutdata");
			StatTemplate template = new StatTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportForQueryStat(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}
	}

	//��������·���� ��Ӻ�ʱ��ͳ��
	public boolean exportTimeByLevel(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "���ͳ����Ϣһ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.exportTimeByLevel");
			StatTemplate template = new StatTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportForTimeByLevel(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}
	}

	//������������� ��Ӻ�ʱ��ͳ��
	public boolean exportTimeByType(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "���ͳ����Ϣһ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "reprot.exportTimeByType");
			StatTemplate template = new StatTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportForTimeByType(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}
	}

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		logger.info("��ʼreset");
		response.reset();
		logger.info("reset");
		response.setContentType(CONTENT_TYPE);
		logger.info("setContentType");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));
		logger.info("setHeader");
	}

}
