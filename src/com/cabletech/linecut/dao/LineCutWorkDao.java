package com.cabletech.linecut.dao;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.linecut.Templates.LineCutTemplate;
import com.cabletech.linecut.beans.LineCutBean;

public class LineCutWorkDao {
	private static Logger logger = Logger.getLogger(LineCutWorkDao.class.getName());
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	/**
	 * <br>����:������д����ʩ����Ϣ�ĸ��
	 * <br>����:
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllReForWork(UserInfo userinfo) {
		List work = null;
		try {
			String sql = " select l.numerical ,l.name,l.reacce,l.ADDRESS,TO_CHAR(l.PROTIME,'YYYY-MM-DD HH24:MI') protime,sub.SUBLINENAME,u.USERNAME,l.REID,l.PROUSETIME,TO_CHAR(l.retime,'YYYY-MM-DD ')  retime,con.contractorname,l.isarchive "
					+ " from line_cutinfo l,contractorinfo con,userinfo u,sublineinfo sub "
					+ " where l.CONTRACTORID = con.CONTRACTORID and l.REUSERID = u.USERID and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID "
					+ " and (l.isarchive='�Ѿ�����' or l.isarchive='δͨ������') and auditresult='ͨ������' "
					+ " and l.contractorid='" + userinfo.getDeptID() + "'" + " order by l.retime desc";
			QueryUtil query = new QueryUtil();
			work = query.queryBeans(sql);
			logger.info("getAllReForWork:" + sql);
			return work;
		} catch (Exception e) {
			logger.error("������д����ʩ����Ϣ�ĸ���쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>����:д����ʩ����Ϣ
	 * <br>����:���bean
	 * <br>����ֵ:��óɹ�����true,���򷵻� false;
	 */
	public boolean addWorkInfo(LineCutBean bean) {
		String sql = ""; //д��������
		String remark = "";
		if (bean.getReremark() != null) {
			remark = bean.getWorkremark().length() > 249 ? bean.getWorkremark().substring(0, 249) : bean
					.getWorkremark();
		}
		OracleIDImpl ora = new OracleIDImpl();
		String id = ora.getSeq("line_cutwork", 10);

		String str = "ʩ�����";
		if (bean.getFlag().equals("0"))
			str = "����ʩ��";

		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			try {
				sql = "update line_cutinfo set workuserid='" + bean.getWorkuserid() + "',endvalue="
						+ Float.parseFloat(bean.getEndvalue()) + ",manpower=" + Float.parseFloat(bean.getManpower())
						+ ",usetime=" + Float.parseFloat(bean.getUsetime()) + ",essetime=" + "TO_DATE('"
						+ bean.getEssetime() + "','YYYY-MM-DD HH24:MI:SS'),workremark='" + remark + "',workacce='"
						+ bean.getWorkacce() + "',isarchive='" + str + "',workid='" + id + "'" + " where reid='"
						+ bean.getReid() + "'";
				exec.executeUpdate(sql); //д�������
				sql = "insert into line_cutwork (id,reid,workuserid,endvalue,manpower,usetime,essetime,workremark,workacce) "
						+ " values ('"
						+ id
						+ "','"
						+ bean.getReid()
						+ "','"
						+ bean.getWorkuserid()
						+ "',"
						+ Float.parseFloat(bean.getEndvalue())
						+ ","
						+ Float.parseFloat(bean.getManpower())
						+ ","
						+ Float.parseFloat(bean.getUsetime())
						+ ",TO_DATE('"
						+ bean.getEssetime()
						+ "','YYYY-MM-DD HH24:MI:SS'),'" + remark + "','" + bean.getWorkacce() + "')";
				exec.executeUpdate(sql);

				logger.info("addWorkInfo:" + sql);
				exec.close();
				return true;
			} catch (Exception ex) {
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("д����ʩ����Ϣ����:" + e.getMessage());
			return false;
		}
	}

	/**
	 * <br>����:�޸ĸ��ʩ����Ϣ
	 * <br>����:���bean
	 * <br>����ֵ:��óɹ�����true,���򷵻� false;
	 */
	public boolean WorkUp(LineCutBean bean) {
		String sql = ""; //д��������
		String remark = "";
		if (bean.getReremark() != null) {
			remark = bean.getWorkremark().length() > 249 ? bean.getWorkremark().substring(0, 249) : bean
					.getWorkremark();
		}

		String str = "ʩ�����";
		if (bean.getFlag().equals("0"))
			str = "����ʩ��";

		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			try {
				sql = "update line_cutinfo set workuserid='" + bean.getWorkuserid() + "',endvalue="
						+ Float.parseFloat(bean.getEndvalue()) + ",manpower=" + Float.parseFloat(bean.getManpower())
						+ ",usetime=" + Float.parseFloat(bean.getUsetime()) + ",essetime=" + "TO_DATE('"
						+ bean.getEssetime() + "','YYYY-MM-DD HH24:MI:SS'),workremark='" + remark + "',workacce='"
						+ bean.getWorkacce() + "',isarchive='" + str + "' " + " where reid='" + bean.getReid() + "'";
				exec.executeUpdate(sql); //���»�����
				//         System.out.println( "SQL:" + sql );
				sql = "update line_cutwork set workuserid='" + bean.getWorkuserid() + "',endvalue="
						+ Float.parseFloat(bean.getEndvalue()) + ",manpower=" + Float.parseFloat(bean.getManpower())
						+ ",usetime=" + Float.parseFloat(bean.getUsetime()) + ",essetime=" + "TO_DATE('"
						+ bean.getEssetime() + "','YYYY-MM-DD HH24:MI:SS'),workremark='" + remark + "',workacce='"
						+ bean.getWorkacce() + "' " + " where id='" + bean.getWorkid() + "'";
				exec.executeUpdate(sql);
				logger.info("WorkUp:" + sql);
				exec.commit();
				return true;
			} catch (Exception ex) {
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("д����ʩ����Ϣ����:" + e.getMessage());
			return false;
		}
	}

	/**
	 * <br>����:��õ�ǰ��λ���Ѿ�ʩ���ĸ����Ϣ
	 * <br>����:request
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllOwnWork(HttpServletRequest request) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List useinfo = null;
		String sql = "";
		String contractorid = userinfo.getDeptID();
		try {
			sql = " select l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,l.endvalue,l.usetime,l.isarchive, "
					+ " TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime"
					+ " from line_cutinfo l,deptinfo d,sublineinfo sub "
					+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID "
					+ " and l.contractorid='" + contractorid + "' and  (l.isarchive !='������' and l.isarchive !='�Ѿ�����' )"
					//����ʱ����ʾ
					+ " order by l.essetime desc";
			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			logger.info("LineCutWorkDao.getAllOwnWork:" + sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��õ�ǰ��λ���Ѿ�ʩ���ĸ����Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>����:��������������Ѿ�ʩ���ĸ����Ϣ(�ƶ���˾�鿴���ʩ����ʱ��)
	 * <br>����:request
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllWork(HttpServletRequest request) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List useinfo = null;
		String sql = "";
		String deptid = userinfo.getDeptID();
		try {
			sql = " select l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,l.endvalue,l.usetime,l.isarchive, "
					+ " TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime"
					+ " from line_cutinfo l,deptinfo d,sublineinfo sub "
					+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID " + " and l.deptid='"
					+ deptid + "' and (l.isarchive !='������' and l.isarchive !='�Ѿ�����' )"
					//����ʱ����ʾ
					+ " order by l.essetime desc";

			logger.info("LineCutWorkDao.getAllWork:" + sql);
			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��������������Ѿ�ʩ���ĸ����Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**��ø�������б�
	 * <br>����:
	 * <br>����:�ƶ�id
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 **/
	public List getAllNamesForWork(String deptid) {
		List reqPart = null;
		try {
			String sql = "select distinct name from line_cutinfo  " + " where deptid='" + deptid
					+ "' or contractorid='" + deptid + "' order by name";

			logger.info("LineCutWorkDao.getAllNamesForWork:" + sql);
			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("��ø�������б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**��ø��ԭ���б�
	 * <br>����:
	 * <br>����:id
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 **/
	public List getAllReasonsForWork(String deptid) {
		List reqPart = null;
		try {
			String sql = "select distinct reason from line_cutinfo  " + " where deptid='" + deptid
					+ "' or contractorid='" + deptid + "' order by reason";

			logger.info("LineCutWorkDao.getAllReasonsForWork:" + sql);

			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("��ø��ԭ���б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**��ø�ӵص��б�
	 * <br>����:
	 * <br>����:id
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 **/
	public List getAllAddresssForWork(String deptid) {
		List reqPart = null;
		try {
			String sql = "select distinct address from line_cutinfo  " + " where deptid='" + deptid
					+ "' or contractorid='" + deptid + "' order by address";

			logger.info("LineCutWorkDao.getAllAddresssForWork:" + sql);

			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("��ø�ӵص��б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**�����Ӱ��ϵͳ�б�
	 * <br>����:
	 * <br>����:id
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 **/
	public List getAllEfsystemsForWork(String deptid) {
		List reqPart = null;
		try {
			String sql = "select distinct efsystem from line_cutinfo  " + " where deptid='" + deptid
					+ "' or contractorid='" + deptid + "' order by efsystem";

			logger.info("LineCutWorkDao.getAllEfsystemsForWork:" + sql);

			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("�����Ӱ��ϵͳ�б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**����漰���¶��б�
	 * <br>����:
	 * <br>����:��άid
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 **/
	public List getAllSublineidsForWork(String deptid) {
		List reqPart = null;
		try {
			String sql = "select distinct l.SUBLINEID,s.SUBLINENAME from line_cutinfo l,sublineinfo s "
					+ " where subStr(l.SUBLINEID,0,8) = s.SUBLINEID and "
					//�ϼ����ſ��Բ鿴�������ŵ����յ���Ѳ���߶�
					+ "(l.deptid ='" + deptid + "' or (l.deptid in (select deptid from deptinfo where parentid='"
					+ deptid + "')) or l.contractorid='" + deptid + "')";

			logger.info("LineCutWorkDao.getAllSublineidsForWork:" + sql);

			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("����漰���¶��б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>����:��������������Ѿ�ʩ���ĸ����Ϣ(�ƶ���˾�鿴���ʩ����ʱ��)
	 * <br>����:request
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllOwnReForWorkSearch(LineCutBean bean, UserInfo userinfo, HttpSession session) {
		List useinfo = null;
		String sql = "";
		String deptid = userinfo.getDeptID();
		try {
			if (userinfo.getDeptype().equals("1")) {
				sql = " select l.numerical,l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,l.endvalue,l.usetime,l.isarchive, "
						+ " TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime"
						+ " from line_cutinfo l,deptinfo d,sublineinfo sub "
						+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8)= sub.SUBLINEID "
						+ " and l.deptid='"
						+ deptid + "' and  (l.isarchive !='������' and l.isarchive !='�Ѿ�����' )";
			} else {
				sql = " select l.numerical,l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,l.endvalue,l.usetime,l.isarchive, "
						+ " TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime"
						+ " from line_cutinfo l,deptinfo d,sublineinfo sub "
						+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID "
						+ " and l.contractorid='" + deptid + "' and (l.isarchive !='������' and l.isarchive !='�Ѿ�����' )";
			}

			if (bean.getSublineid() != null && !bean.getSublineid().equals("")) {
				sql = sql + "  and subStr(l.sublineid,0,8)='" + bean.getSublineid() + "'  ";
			}
			if (bean.getName() != null && !bean.getName().equals("")) {
				sql = sql + "  and l.name='" + bean.getName() + "'  ";
			}
			if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
				sql = sql + " and l.essetime >=TO_DATE('" + bean.getBegintime() + " 00:00:00','YYYY-MM-DD HH24:MI:SS')";
			}
			if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
				sql = sql + " and l.essetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			//����ʱ����ʾ
			sql = sql + " order by l.essetime desc";
			logger.info("LineCutWorkDao.getAllOwnReForWorkSearch:" + sql);
			session.setAttribute("lcwQueryCon", sql);
			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��������������Ѿ�ʩ���ĸ����Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>����:��������������Ѿ�ʩ���ĸ����Ϣ(�ƶ���˾�鿴���ʩ����ʱ��)
	 * <br>����:request
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getConditionsReForWorkSearch(LineCutBean bean, UserInfo userinfo, HttpServletRequest request) {
		List useinfo = null;
		String sql = "";

		String lineLevel = request.getParameter("level");
		String linename = request.getParameter("line");

		String deptid = userinfo.getDeptID();
		try {
			if (userinfo.getDeptype().equals("1")) {
				sql = " select l.numerical,l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,l.endvalue,l.usetime,l.isarchive, "
						+ " TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime"
						+ " from line_cutinfo l,deptinfo d,sublineinfo sub,lineinfo lio "
						+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8)= sub.SUBLINEID "
						+ " and l.deptid='"
						+ deptid
						+ "' and  (l.isarchive !='������' and l.isarchive !='�Ѿ�����' )"
						+ "  and sub.lineid = lio.lineid";
			} else {
				sql = " select l.numerical,l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,l.endvalue,l.usetime,l.isarchive, "
						+ " TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime"
						+ " from line_cutinfo l,deptinfo d,sublineinfo sub ,lineinfo lio"
						+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID "
						+ " and l.contractorid='"
						+ deptid
						+ "' and (l.isarchive !='������' and l.isarchive !='�Ѿ�����' )"
						+ "  and sub.lineid = lio.lineid";
			}

			if (bean.getSublineid() != null && !bean.getSublineid().equals("")) {
				sql = sql + "  and subStr(l.sublineid,0,8)='" + bean.getSublineid() + "'  ";
			}
			if (bean.getName() != null && !bean.getName().equals("")) {
				sql = sql + "  and l.name='" + bean.getName() + "'  ";
			}
			if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
				sql = sql + " and l.essetime >=TO_DATE('" + bean.getBegintime() + " 00:00:00','YYYY-MM-DD HH24:MI:SS')";
			}
			if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
				sql = sql + " and l.essetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD HH24:MI:SS')";
			}
			if (lineLevel != null && !"".equals(lineLevel) && !"mention".equals(lineLevel)) {
				sql = sql + " and lio.linetype='" + lineLevel + "'";
			}
			if (linename != null && !"".equals(linename) && !"mention".equals(linename)) {
				sql = sql + " and lio.lineid = '" + linename + "'";
			}
			//����ʱ����ʾ
			sql = sql + " order by l.essetime desc";
			logger.info("LineCutWorkDao.getConditionsReForWorkSearch:" + sql);
			request.getSession().setAttribute("lcwQueryCon", sql);
			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��������������Ѿ�ʩ���ĸ����Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * ����ǰ�Ĳ�ѯ�������ؽ��
	 * @param sql
	 * @return
	 */
	public List doQueryAfterMod(String sql) {
		QueryUtil query = null;
		try {
			logger.info("LineCutWorkDao.doQueryAfterMod:" + sql);
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			query.close();
		}
	}

	//============================�鵵���Ӳ�ѯ===================//
	/**
	 * <br>����:��õ�ǰ�ƶ���˾��Ͻ��,���鵵�����и����Ϣ
	 * <br>����:request
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllForArch(HttpServletRequest request) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List useinfo = null;
		String sql = "";
		String deptid = userinfo.getDeptID();
		try {
			sql = " select l.contractorid,con.contractorname,l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.endvalue,l.usetime,l.isarchive,l.updoc"
					+ " from line_cutinfo l,deptinfo d,sublineinfo sub,contractorinfo con "
					+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID and l.contractorid= con.contractorid"
					+ " and l.deptid='" + deptid + "' and (l.isarchive='�Ѿ�����' or l.isarchive='���ڹ鵵') "
					//����ʱ����ʾ
					+ " order by l.retime desc,con.contractorname desc";

			logger.info("LineCutWorkDao.getAllForArch:" + sql);

			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��õ�ǰ�ƶ���˾��Ͻ��,���鵵�����и����Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>����:д���ӹ鵵��Ϣ
	 * <br>����:���bean
	 * <br>����ֵ:��óɹ�����true,���򷵻� false;
	 */
	public boolean addArchInfo(LineCutBean bean) {
		String sql = ""; //д��������
		try {
			UpdateUtil exec = new UpdateUtil();
			if (bean.getIsarchive().equals("�Ѿ��鵵")) {
				sql = "update line_cutinfo set archives= concat(archives,'," + bean.getArchives()
						+ "'),isarchive='�Ѿ��鵵',updoc='" + bean.getFlag() + "' " + " where reid='" + bean.getReid()
						+ "'";
			} else {
				sql = "update line_cutinfo set archives= concat(archives,'," + bean.getArchives()
						+ "'),isarchive='���ڹ鵵',updoc='" + bean.getFlag() + "' " + " where reid='" + bean.getReid()
						+ "'";

			}
			logger.info("LineCutWorkDao.addArchInfo:" + sql);

			exec.executeUpdate(sql); //д�������
			return true;
		} catch (Exception e) {
			logger.error("д���ӹ鵵��Ϣ����:" + e.getMessage());
			return false;
		}
	}

	/**
	 * <br>����:��ø�Ӳ�����Ϣ
	 * <br>����:request
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getCutInfo(HttpServletRequest request) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List useinfo = null;
		String sql = "";
		String deptid = userinfo.getDeptID();
		try {
			if (userinfo.getDeptype().equals("1")) { //���ƶ���˾
				sql = " select l.contractorid,con.contractorname,l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.isarchive,l.updoc "
						+ " from line_cutinfo l,deptinfo d,sublineinfo sub,contractorinfo con "
						+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID and l.contractorid= con.contractorid"
						//�ϼ����ſ��Կ����������ŵĹ鵵��
						+ "  and (l.deptid='"
						+ userinfo.getDeptID()
						+ "' or l.deptid in (select deptid from deptinfo where parentid='"
						+ userinfo.getDeptID()
						+ "')) "
						//+ " and l.deptid='" + deptid + "' "
						+ "  and (l.isarchive = '�Ѿ��鵵' or l.isarchive = '���ڹ鵵') "
						//����ʱ����ʾ
						+ " order by l.retime desc ";
			} else { //�Ǵ�ά��λ
				sql = " select l.contractorid,con.contractorname,l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.isarchive,l.updoc"
						+ " from line_cutinfo l,deptinfo d,sublineinfo sub,contractorinfo con "
						+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID and l.contractorid= con.contractorid"
						+ " and l.contractorid='" + deptid + "' and (l.isarchive = '�Ѿ��鵵' or l.isarchive = '���ڹ鵵') "
						//����ʱ����ʾ
						+ " order by l.retime desc";
			}
			logger.info("LineCutWorkDao.getCutInfo:" + sql);

			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��ø�Ӳ�����Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**����и����Ϣ�Ĵ�ά��λ�б�
	 * <br>����:
	 * <br>����:��άid
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 **/
	public List getAllConName(String deptid) {
		List reqPart = null;
		try {
			String sql = "select distinct l.contractorid,con.contractorname from line_cutinfo l,contractorinfo con "
					+ " where l.contractorid = con.contractorid and l.deptid ='" + deptid + "'";

			logger.info("LineCutWorkDao.getAllConName:" + sql);

			QueryUtil query = new QueryUtil();
			reqPart = query.queryBeans(sql);
			return reqPart;
		} catch (Exception e) {
			logger.error("����漰���¶��б��쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>����:�������Ҹ�Ӳ�����Ϣ
	 * <br>����:request
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getCutInfoForSearch(LineCutBean bean, UserInfo userinfo, HttpSession session) {
		List useinfo = null;
		String sql = "";
		String deptid = userinfo.getDeptID();

		try {
			if (userinfo.getDeptype().equals("1")) { //���ƶ���˾
				sql = " select l.numerical,l.contractorid,con.contractorname,l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.isarchive,l.updoc"
						+ " from line_cutinfo l,deptinfo d,sublineinfo sub,contractorinfo con "
						+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID and l.contractorid= con.contractorid"
						//�ϼ����ſ��Բ鿴�������ŵĹ鵵��
						+ " and (l.deptid='"
						+ deptid
						+ "' or l.deptid in (select deptid from deptinfo where parentid='"
						+ deptid
						+ "')) "
						+ "  and l.isarchive !='������'";
			} else { //�Ǵ�ά��λ
				sql = " select l.numerical,l.contractorid,con.contractorname,l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.isarchive,l.updoc "
						+ " from line_cutinfo l,deptinfo d,sublineinfo sub,contractorinfo con "
						+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID and l.contractorid= con.contractorid"
						+ " and l.contractorid='" + deptid + "'";
			}

			if (bean.getSublineid() != null && !bean.getSublineid().equals("")) {
				sql = sql + "  and subStr(l.sublineid,0,8)='" + bean.getSublineid() + "'  ";
			}
			if (bean.getContractorid() != null && !bean.getContractorid().equals("")) {
				sql = sql + "  and l.contractorid='" + bean.getContractorid() + "'  ";
			}
			if (bean.getName() != null && !bean.getName().equals("")) {
				sql = sql + "  and l.name like '" + bean.getName() + "%'  ";
			}
			if (bean.getIsarchive() != null && !bean.getIsarchive().equals("")) {
				sql = sql + "  and l.isarchive='" + bean.getIsarchive() + "'  ";
			}
			if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
				sql = sql + " and l.essetime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
			}
			if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
				sql = sql + " and l.essetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
			}

			//����ʱ����ʾ
			sql = sql + " order by l.retime desc,con.contractorname desc";
			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			logger.info("LineCutWorkDao.getCutInfoForSearch:" + sql);
			session.setAttribute("lcwArQueryCob", sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��ø�Ӳ�����Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>����:�������Ҹ�Ӳ�����Ϣ
	 * <br>����:request
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getConditionsCutInfoForSearch(LineCutBean bean, UserInfo userinfo, HttpServletRequest request) {
		List useinfo = null;
		String sql = "";
		String deptid = userinfo.getDeptID();

		String lineLevel = request.getParameter("level").trim();
		String linename = request.getParameter("line").trim();

		try {
			if (userinfo.getDeptype().equals("1")) { //���ƶ���˾
				sql = " select l.numerical,l.contractorid,con.contractorname,l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.isarchive,l.updoc"
						+ " from line_cutinfo l,deptinfo d,sublineinfo sub,contractorinfo con,lineinfo lio "
						+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID and l.contractorid= con.contractorid"
						//�ϼ����ſ��Բ鿴�������ŵĹ鵵��
						+ " and (l.deptid='"
						+ deptid
						+ "' or l.deptid in (select deptid from deptinfo where parentid='"
						+ deptid
						+ "')) "
						+ " and sub.lineid = lio.lineid " + "  and l.isarchive !='������'";
			} else { //�Ǵ�ά��λ
				sql = " select l.numerical,l.contractorid,con.contractorname,l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.isarchive,l.updoc "
						+ " from line_cutinfo l,deptinfo d,sublineinfo sub,contractorinfo con lineinfo lio "
						+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID and l.contractorid= con.contractorid"
						+ " and l.contractorid='" + deptid + "'" + " and sub.lineid = lio.lineid ";
			}

			if (bean.getSublineid() != null && !bean.getSublineid().equals("")) {
				sql = sql + "  and subStr(l.sublineid,0,8)='" + bean.getSublineid() + "'  ";
			}
			if (bean.getContractorid() != null && !bean.getContractorid().equals("")) {
				sql = sql + "  and l.contractorid='" + bean.getContractorid() + "'  ";
			}
			if (bean.getName() != null && !bean.getName().equals("")) {
				sql = sql + "  and l.name like '" + bean.getName() + "%'  ";
			}
			if (bean.getIsarchive() != null && !bean.getIsarchive().equals("")) {
				sql = sql + "  and l.isarchive='" + bean.getIsarchive() + "'  ";
			}
			if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
				sql = sql + " and l.essetime >=TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
			}
			if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
				sql = sql + " and l.essetime <= TO_DATE('" + bean.getEndtime() + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
			}
			if (lineLevel != null && !"".equals(lineLevel) && !"mention".equals(lineLevel)) {
				sql = sql + " and lio.linetype='" + lineLevel + "'";
			}
			if (linename != null && !"".equals(linename) && !"mention".equals(linename)) {
				sql = sql + " and lio.lineid = '" + linename + "'";
			}

			//����ʱ����ʾ
			sql = sql + " order by l.retime desc,con.contractorname desc";
			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			logger.info("LineCutWorkDao.getConditionsCutInfoForSearch:" + sql);
			request.getSession().setAttribute("lcwArQueryCob", sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��ø�Ӳ�����Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	public List queryAfterBack(String sql) {
		QueryUtil query = null;
		try {
			logger.info("LineCutWorkDao.queryAfterBack:" + sql);

			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <br>����:��ô���ʩ���׶εĸ�ӵĲ�����Ϣ
	 * <br>����:request
	 * <br>����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getCutInfoForWorking(HttpServletRequest request) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List useinfo = null;
		String sql = "";
		String deptid = userinfo.getDeptID();
		try {
			if (userinfo.getDeptype().equals("1")) { //���ƶ���˾
				sql = " select l.contractorid,con.contractorname,l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.isarchive "
						+ " from line_cutinfo l,deptinfo d,sublineinfo sub,contractorinfo con "
						+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID and l.contractorid= con.contractorid"
						+ " and l.deptid='" + deptid + "' and isarchive='�Ѿ�����'"
						//����ʱ����ʾ
						+ " order by l.essetime desc,con.contractorname desc";
			} else { //�Ǵ�ά��λ
				sql = " select l.contractorid,con.contractorname,l.reid,l.name,l.ADDRESS,sub.SUBLINENAME,l.reason,TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.isarchive "
						+ " from line_cutinfo l,deptinfo d,sublineinfo sub,contractorinfo con "
						+ " where l.deptid = d.deptid and subStr(l.SUBLINEID,0,8) = sub.SUBLINEID and l.contractorid= con.contractorid"
						+ " and l.contractorid='" + deptid + "' and isarchive='�Ѿ�����'"
						//����ʱ����ʾ
						+ " order by l.essetime desc,con.contractorname desc";
			}
			logger.info("LineCutWorkDao.getCutInfoForWorking:" + sql);
			QueryUtil query = new QueryUtil();
			useinfo = query.queryBeans(sql);
			return useinfo;
		} catch (Exception e) {
			logger.error("��ô�ʩ����ӵĲ�����Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * ������������
	 * @param planform PlanStatisticForm
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public boolean ExportLineCut(List list, HttpServletResponse response) {
		try {

			OutputStream out;
			initResponse(response, "���һ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.linecutshow");
			LineCutTemplate template = new LineCutTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExport(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	public boolean ExportReLineCut(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "�����������һ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.relinecut");
			LineCutTemplate template = new LineCutTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportReLineCut(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	public boolean ExportLineCutWork(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "�Ѿ�ʩ�����һ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.linecutwork");
			LineCutTemplate template = new LineCutTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportLineCutWork(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	public boolean ExportLineCutRe(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "�������һ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.linecutre");
			LineCutTemplate template = new LineCutTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportLineCutRe(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	public boolean ExportLineCutAcc(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "���ʩ������һ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.linecutacc");
			LineCutTemplate template = new LineCutTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportLineCutAcc(list, excelstyle);
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

	/**
	 * <br>����:���ָ���ĸ��ȫ����Ϣ
	 * <br>����:����id
	 * <br>����ֵ:��óɹ����ض���,���򷵻� NULL;
	 */
	public LineCutBean getOneCutAllInfo(String reid) {
		LineCutBean bean = new LineCutBean();
		String sublinename = this.getSubLineNameCon(reid);
		QueryUtil query1 = null;
		ResultSet rs1 = null;
		try {
			query1 = new QueryUtil();
			String sql = "select l.cutType,l.reid,l.contractorid,l.reuserid,TO_CHAR(l.retime,'yyyy-MM-dd') retime,"
					+ "       l.name,l.reason,l.address,TO_CHAR(l.protime,'yyyy-MM-dd HH24:MI') protime,"
					+ "       l.prousetime,l.provalue,l.efsystem,l.reremark,l.reacce,l.audituserid,TO_CHAR(l.audittime,'yyyy-MM-dd') audittime,"
					+ "       l.deptid,l.auditresult,l.auditremark,l.auditacce,l.workuserid,l.endvalue,l.manpower,l.usetime,l.updoc,"
					+ "       TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.workremark,l.workacce,l.archives,l.isarchive,"
					+ "       con.contractorname,de.deptname,lc.AUDITACCE acceptacce,lc.AUDITRESULT acceptresult,lc.AUDITREMARK acceptremark "
					+ " from  line_cutinfo l,contractorinfo con,deptinfo de,line_cutapprove lc"
					+ " where l.contractorid=con.contractorid and l.deptid=de.deptid and l.ACCEPTID = lc.ID "
					+ "       and l.reid='" + reid + "'";

			System.out.println("linecutworkdao->getOneCutAllInfo:" + sql);
			rs1 = query1.executeQuery(sql);
			System.out.println(rs1 == null);
			if (rs1.next()) {
				bean.setCutType(rs1.getString("cutType"));
				bean.setReid(rs1.getString("reid"));
				bean.setContractorid(rs1.getString("contractorid"));
				bean.setReuserid(rs1.getString("reuserid"));
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
				bean.setAudituserid(rs1.getString("audituserid"));
				bean.setDeptid(rs1.getString("deptid"));
				bean.setAuditresult(rs1.getString("auditresult"));
				bean.setAuditremark(rs1.getString("auditremark"));
				bean.setAuditacce(rs1.getString("auditacce"));
				bean.setWorkuserid(rs1.getString("workuserid"));
				bean.setEndvalue(rs1.getString("endvalue"));
				bean.setManpower(rs1.getString("manpower"));
				bean.setUsetime(rs1.getString("usetime"));
				bean.setEssetime(rs1.getString("essetime"));
				bean.setWorkremark(rs1.getString("workremark"));
				bean.setWorkacce(rs1.getString("workacce"));
				bean.setArchives(rs1.getString("archives"));
				bean.setIsarchive(rs1.getString("isarchive"));
				bean.setDeptname(rs1.getString("deptname"));
				bean.setContractorname(rs1.getString("contractorname"));
				bean.setUpdoc(rs1.getString("updoc"));
				bean.setAcceptacce(rs1.getString("acceptacce"));
				bean.setAcceptremark(rs1.getString("acceptremark"));
				bean.setAcceptresult(rs1.getString("acceptresult"));
				bean.setSublinename(sublinename);
			}

			logger.info("LineCutWorkDao.getOneCutAllInfo:" + sql);

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
	 * <br>����:���ָ��������뵥����Ϣ
	 * <br>����:����id
	 * <br>����ֵ:��óɹ����ض���,���򷵻� NULL;
	 */
	public LineCutBean getOneReInfo(String reid) {
		LineCutBean bean = new LineCutBean();
		String sublinename = this.getSubLineNameCon(reid);
		QueryUtil query1 = null;
		ResultSet rs1 = null;
		try {
			query1 = new QueryUtil();
			String sql = "select l.cutType,l.reid,l.name,l.REASON,l.ADDRESS,TO_CHAR(l.protime,'yyyy-MM-dd HH24:MI') protime,"
					+ " l.provalue,l.EFSYSTEM,l.REREMARK,l.REACCE,l.auditresult,l.auditremark,l.prousetime,l.workid, "
					+ " d.DEPTNAME,con.CONTRACTORNAME,u.USERNAME, sub.SUBLINENAME, l.usetime, l.manpower, l.endvalue"
					+ " from line_cutinfo l,deptinfo d, contractorinfo con,userinfo u,sublineinfo sub,line_cutwork lc"
					+ " where l.DEPTID=d.DEPTID and l.CONTRACTORID = con.CONTRACTORID and l.AUDITUSERID=u.USERID  and l.reid='"
					+ reid + "'";
			rs1 = query1.executeQuery(sql);
			if (rs1.next()) {
				bean.setCutType(rs1.getString("cutType"));
				bean.setReid(rs1.getString("reid"));
				bean.setName(rs1.getString("name"));
				bean.setReason(rs1.getString("reason"));
				bean.setAddress(rs1.getString("address"));
				bean.setProtime(rs1.getString("protime"));
				bean.setProvalue(rs1.getString("provalue"));
				bean.setEfsystem(rs1.getString("efsystem"));
				bean.setReremark(rs1.getString("reremark"));
				bean.setReacce(rs1.getString("reacce"));
				bean.setAuditresult(rs1.getString("auditresult"));
				bean.setAuditremark(rs1.getString("auditremark"));
				bean.setProusetime(rs1.getString("prousetime"));
				bean.setDeptname(rs1.getString("deptname"));
				bean.setContractorname(rs1.getString("contractorname"));
				bean.setUsername(rs1.getString("username"));
				bean.setWorkid(rs1.getString("workid"));
				bean.setUsetime(String.valueOf(rs1.getFloat("usetime")));
				bean.setManpower(String.valueOf(rs1.getInt("manpower")));
				bean.setEndvalue(String.valueOf(rs1.getFloat("endvalue")));
				bean.setSublinename(sublinename);
			}

			logger.info("LineCutWorkDao.getOneReInfo:" + sql);
			return bean;
		} catch (Exception e) {
			logger.error("���ָ��������뵥����Ϣ�쳣:" + e.getMessage());
			return null;
		} finally {
			try {
				rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			query1.close();
		}
	}

	/**
	 * <br>����:���ָ��������뵥����Ϣ
	 * <br>����:����id
	 * <br>����ֵ:��óɹ����ض���,���򷵻� NULL;
	 */
	public LineCutBean getOneWorkInfo(String reid) {
		LineCutBean bean = new LineCutBean();
		String sql = "";
		String sublinename = this.getSubLineNameCon(reid);
		QueryUtil query1 = null;
		ResultSet rs1 = null;
		try {
			query1 = new QueryUtil();
			sql = "select l.cutType, l.reid,l.name,l.REASON,l.ADDRESS,TO_CHAR(l.protime,'yyyy-MM-dd HH24:MI') protime,"
					+ " l.provalue,l.EFSYSTEM,l.prousetime,l.endvalue,l.manpower,l.usetime,TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime,l.workid,l.updoc, "
					+ " l.workremark,l.workacce, d.DEPTNAME,con.CONTRACTORNAME,u.USERNAME"
					+ " from line_cutinfo l,deptinfo d, contractorinfo con,userinfo u"
					+ " where l.DEPTID=d.DEPTID and l.CONTRACTORID = con.CONTRACTORID and l.AUDITUSERID=u.USERID  "
					+ "       and reid='" + reid + "'";
			rs1 = query1.executeQuery(sql);
			if (rs1.next()) {
				if (rs1.getString("cutType") == null) {
					bean.setCutType("");
				} else {
					bean.setCutType(rs1.getString("cutType"));
				}
				bean.setReid(rs1.getString("reid"));
				bean.setName(rs1.getString("name"));
				bean.setReason(rs1.getString("reason"));
				bean.setAddress(rs1.getString("address"));
				bean.setProtime(rs1.getString("protime"));
				bean.setProvalue(rs1.getString("provalue"));
				bean.setEfsystem(rs1.getString("efsystem"));
				bean.setProusetime(rs1.getString("prousetime"));
				bean.setEndvalue(rs1.getString("endvalue"));
				bean.setManpower(rs1.getString("manpower"));
				bean.setUsetime(rs1.getString("usetime"));
				bean.setEssetime(rs1.getString("essetime"));
				bean.setWorkremark(rs1.getString("workremark"));
				bean.setWorkacce(rs1.getString("workacce"));
				bean.setDeptname(rs1.getString("deptname"));
				bean.setContractorname(rs1.getString("contractorname"));
				bean.setUsername(rs1.getString("username"));
				bean.setWorkid(rs1.getString("workid"));
				bean.setUpdoc(rs1.getString("updoc"));
				bean.setSublinename(sublinename);
			}

			logger.info("LineCutWorkDao.getOneWorkInfo:" + sql);

		} catch (Exception e) {
			e.printStackTrace();
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
	 * <br>����:���ָ���Ĵ��鵵�ĸ����Ϣ
	 * <br>����:����id
	 * <br>����ֵ:��óɹ����ض���,���򷵻� NULL;
	 */
	public LineCutBean getOneForArch(String reid) {
		String sublinename = this.getSubLineNameCon(reid);
		ResultSet rst = null;
		LineCutBean bean = new LineCutBean();
		String sql = "select l.reid,l.name,l.REASON,l.ADDRESS,TO_CHAR(l.protime,'yyyy-MM-dd HH24:MI') protime,"
				+ " l.provalue,l.EFSYSTEM,l.prousetime,l.endvalue,l.manpower,l.usetime,TO_CHAR(l.essetime,'yyyy-MM-dd HH24:MI') essetime,"
				+ " l.workacce, l.reacce,l.archives," + " d.DEPTNAME,con.CONTRACTORNAME, sub.SUBLINENAME"
				+ " from line_cutinfo l,deptinfo d, contractorinfo con,userinfo u,sublineinfo sub"
				+ " where l.DEPTID=d.DEPTID and l.CONTRACTORID = con.CONTRACTORID and reid='" + reid + "'";
		logger.info("linecutworkdao->getOneFoeArch:" + sql);
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQuery(sql);
			if (rst.next()) {
				bean.setReid(rst.getString("reid"));
				bean.setName(rst.getString("name"));
				bean.setReason(rst.getString("reason"));
				bean.setAddress(rst.getString("address"));
				bean.setProtime(rst.getString("protime"));
				bean.setProvalue(rst.getString("provalue"));
				bean.setEfsystem(rst.getString("efsystem"));
				bean.setProusetime(rst.getString("prousetime"));
				bean.setEndvalue(rst.getString("endvalue"));
				bean.setManpower(rst.getString("manpower"));
				bean.setUsetime(rst.getString("usetime"));
				bean.setEssetime(rst.getString("essetime"));
				bean.setWorkacce(rst.getString("workacce"));
				bean.setReacce(rst.getString("reacce"));
				bean.setArchives(rst.getString("archives"));
				bean.setDeptname(rst.getString("deptname"));
				bean.setContractorname(rst.getString("contractorname"));
				bean.setSublinename(sublinename);
			}
			return bean;
		} catch (Exception e) {
			try {
				rst.close();
			} catch (Exception er) {
				logger.error("getOneForArch()�رս�����쳣��" + er.getMessage());
			}
			logger.error("���ָ���Ĵ��鵵�ĸ����Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

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
					sql = "select sub.sublinename from sublineinfo sub where sub.sublineid in(" + sublineid
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
}
