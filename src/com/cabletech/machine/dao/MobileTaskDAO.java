package com.cabletech.machine.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.machine.beans.MobileTaskBean;
import com.cabletech.machine.domainobjects.Property;

public class MobileTaskDAO {
	private static Logger logger = Logger.getLogger(MobileTaskDAO.class.getName());

	private static String CONTENT_TYPE = "application/vnd.ms-excel";


	/**
	 * �����ƶ��ƻ�
	 * 
	 * @param bean
	 * @return
	 */
	public String addMobileTask(MobileTaskBean bean) {
		OracleIDImpl ora = new OracleIDImpl();
		String tid = ora.getSeq("mobile_task", 10);
		UpdateUtil exec = null;
		String sql = "insert into mobile_task(tid,machinetype,title,contractorid,userid,checkuser,executetime,remark,maketime,makepeopleid,state,numerical) values('"
				+ tid
				+ "','"
				+ bean.getMachinetype()
				+ "','"
				+ bean.getTitle()
				+ "','"
				+ bean.getContractorid()
				+ "','"
				+ bean.getUserid()
				+ "','"
				+ bean.getCheckuser()
				+ "',to_date('"
				+ bean.getExecutetime()
				+ "','yyyy-mm-dd hh24:mi:ss'),'"
				+ bean.getRemark()
				+ "',to_char(sysdate)"
				+ ",'"
				+ bean.getMakepeopleid() + "','" + bean.getState() + "','" + this.getNumerical() + "')";
		System.out.println(sql);
		try {
			exec = new UpdateUtil();
			exec.executeUpdate(sql);
			return tid;
		} catch (Exception e) {
			logger.error("�ƶ�������������쳣:" + e.getMessage());
			return null;
		}
	}

	private synchronized String getNumerical() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String nowDateStr = format.format(date);
		QueryUtil query = null;
		String numerical = "";
		String sql = "select numerical from mobile_task where subStr(numerical,0,8) ='" + nowDateStr
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
		}
		return numerical;
	}

	/**
	 * ִ�в�ѯ
	 * 
	 * @param condition
	 * @return
	 */
	public List doQuery(String condition, UserInfo userinfo) {
		String sql = "";
		QueryUtil qu = null;
		StringBuffer sb = null;
		if ("1".equals(userinfo.getDeptype())) {// ������ƶ��û�
			sb = new StringBuffer();
			sb.append(
					"select mt.numerical, mt.tid, mt.machinetype as type, u.username as exename,con.contractorname as conname,")
					.append(" mt.title as tasktitle,to_char(mt.executetime,'YYYY-MM-DD') as exetime,u1.username as checkname ")
					.append(" from mobile_task mt,contractorinfo con,userinfo u, userinfo u1 ")
					.append(" where mt.contractorid=con.contractorid and mt.userid=u.userid(+) and u1.userid=mt.checkuser(+) ")
					.append(condition).append(" order by mt.numerical desc");
			sql = sb.toString();
		} else {// ����Ǵ�ά�û�����ѯ��½�û����ڲ��ŵ�����
			sb = new StringBuffer();
			sb.append(
					"select mt.numerical, mt.tid, mt.machinetype as type, u.username as exename,con.contractorname as conname,")
					.append(" mt.title as tasktitle ,to_char(mt.executetime,'YYYY-MM-DD') as exetime,u1.username as checkname ")
					.append(" from mobile_task mt,contractorinfo con,userinfo u, userinfo u1")
					.append(" where mt.userid=u.userid(+) and u1.userid=mt.checkuser(+) and mt.contractorid='"
							+ userinfo.getDeptID() + "' ").append(condition)
					.append(" and mt.contractorid=con.contractorid order by mt.numerical  desc");
			sql = sb.toString();
		}
		logger.info(sql);
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sql);
		} catch (Exception e) {
			logger.error("��ѯ�����б����:" + e.getMessage());
			return null;
		} finally {
			qu.close();
		}

	}

	/**
	 * ִ������ظ��Ĳ�ѯ
	 * 
	 * @param condidtion
	 * @return
	 */
	public List doQueryForRestore(String condition, UserInfo userinfo) {
		QueryUtil qu = null;
		StringBuffer sb = null;
		sb = new StringBuffer();
		sb.append(
				"select mt.numerical, mt.tid, mt.machinetype as type, u.username as exename,con.contractorname as conname,")
				.append(" mt.title as tasktitle ,to_char(mt.executetime,'YYYY-MM-DD') as exetime,u1.username as checkname ")
				.append(" from mobile_task mt,contractorinfo con,userinfo u, userinfo u1")
				.append(" where mt.userid=u.userid(+) and u1.userid=mt.checkuser(+) and mt.contractorid='"
						+ userinfo.getDeptID() + "' ").append(condition)
				.append(" and mt.contractorid=con.contractorid order by mt.numerical  desc");
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sb.toString());
		} catch (Exception e) {
			logger.error("execute query for restore task error:" + e.getMessage());
		}
		return null;
	}

	/*
	 * public MobileTaskBean getOneTaskById(String id) { StringBuffer sb = new
	 * StringBuffer(); MobileTaskBean bean = null; StringBuffer info = new
	 * StringBuffer(); sb.append("select u.username, con.contractorname,")
	 * .append("to_char(mt.executetime,'YYYY-MM-DD') as exetime, u1.username
	 * from mobile_task mt,contractorinfo con,userinfo u, userinfo u1 ")
	 * .append("where mt.contractorid=con.contractorid and mt.userid=u.userid
	 * and u1.userid=mt.checkuser and mt.tid='" + id + "'"); QueryUtil qu =
	 * null; ResultSet rs = null; try { qu = new QueryUtil(); rs =
	 * qu.executeQuery(sb.toString()); if(rs.next()) { bean = new
	 * MobileTaskBean(); } } catch (Exception e) { e.printStackTrace(); } return
	 * null; }
	 */

	/**
	 * ɾ���ƶ��ƶ�������
	 * 
	 * @param tid
	 * @return
	 */
	public boolean delMobileTask(String tid) {
		String sql = "delete from mobile_task mt where mt.tid='" + tid + "'";
		UpdateUtil excu = null;
		try {
			excu = new UpdateUtil();
			excu.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ɾ��ָ���ƶ��������:" + e.getMessage());
			return false;
		}
	}

	/**
	 * �޸��ƶ��ƶ�������
	 * 
	 * @param tid
	 * @return
	 */
	public boolean modMobileTask(String tid, MobileTaskBean bean) {

		return false;
	}

	/**
	 * ���ݵ�¼�����ڵĴ�ά��λ ��ȡ��ǩ�յ������б�
	 * 
	 * @param userinfo
	 * @return
	 */
	public List getAllTaskForSign(UserInfo userinfo) {
		QueryUtil qu = null;
		String deptid = userinfo.getDeptID();
		StringBuffer sb = new StringBuffer();
		sb.append(
				"select mt.numerical, mt.tid, mt.machinetype as type, u.username as exename,con.contractorname as conname,")
				.append(" mt.title as tasktitle,to_char(mt.executetime,'YYYY-MM-DD') as exetime,u1.username as checkname ")
				.append(" from mobile_task mt,contractorinfo con,userinfo u, userinfo u1 ")
				.append(" where mt.contractorid='" + deptid + "' and mt.state='" + Property.WAIT_SIGN_FOR + "'")
				.append(" and  mt.contractorid=con.contractorid and mt.userid=u.userid(+) and u1.userid=mt.checkuser(+) ")
				.append(" order by mt.numerical  desc");
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sb.toString());
		} catch (Exception e) {
			logger.error("��ȡ��ǰ�û����ڵ�λ��ǩ�յ���������쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * ��ʾ���ظ��������б�
	 * 
	 * @param userinfo
	 * @return
	 */
	public List showTaskForRestore(UserInfo userinfo) {
		QueryUtil qu = null;
		String deptid = userinfo.getDeptID();
		StringBuffer sb = new StringBuffer();
		sb.append(
				"select mt.numerical, mt.tid, mt.machinetype as type, u.username as exename,con.contractorname as conname,")
				.append(" mt.title as tasktitle,to_char(mt.executetime,'YYYY-MM-DD') as exetime,u1.username as checkname ")
				.append(" from mobile_task mt,contractorinfo con,userinfo u, userinfo u1 ")
				.append(" where mt.contractorid='" + deptid + "' and mt.state='" + Property.SIGN_FOR + "'")
				.append(" and  mt.contractorid=con.contractorid and mt.userid=u.userid(+) and u1.userid=mt.checkuser(+) ")
				.append(" order by mt.numerical  desc");
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sb.toString());
		} catch (Exception e) {
			logger.error("��ȡ��ǰ�û����ڵ�λ���ظ�����������쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * �ı�����״̬
	 * 
	 * @return
	 */
	public boolean modTaskState(String state, String tid) {
		String sql = "update mobile_task mt set mt.state='" + state + "' where mt.tid='" + tid + "'";
		UpdateUtil up = null;
		try {
			up = new UpdateUtil();
			up.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			logger.error("��������״̬�����쳣:" + e.getMessage());
			return false;
		}
	}

	/**
	 * ���ݴ�ά��ID���Ҹô�ά�µ��û�
	 * 
	 * @param conid
	 * @return
	 */
	public List getUserByConId(String conid) {
		String sql = "select u.username,u.userid,u.phone from userinfo u where u.deptid='" + conid
				+ "' and u.state is null order by u.userid";
		QueryUtil qu = null;
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sql);
		} catch (Exception e) {
			logger.error("���ݴ�ά��ID���Ҹô�ά�µ��û������쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * ��ȡ�ƶ��û�����Ϣ
	 * 
	 * @return
	 */
	public List getMobileUser() {
		String sql = "select u.username,u.userid,u.phone from userinfo u where u.deptype=1 and u.state is null order by u.userid";
		QueryUtil qu = null;
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sql);
		} catch (Exception e) {
			logger.error("��ȡ�ƶ��û������쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * ��ȡ�ò���� һ����ά���豸��Ϣ
	 * 
	 * @return
	 * @throws
	 */
	public List getEqu(String layer, String conid) {
		String sql = "select equ.eid,equ.equipment_name from equipment_info equ where equ.contractor_id='" + conid
				+ "' and equ.layer='" + layer + "'";
		QueryUtil qu = null;
		System.out.println(sql);
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sql);
		} catch (Exception e) {
			return null;
		} finally {
			qu.close();
		}
	}

	public MobileTaskBean getOneInfo(String tid) {
		MobileTaskBean bean = null;
		StringBuffer sb = new StringBuffer();
		sb.append(
				"select mt.numerical, mt.machinetype as type, u.username as exename,con.contractorname as conname,mt.remark,")
				.append(" mt.title as tasktitle,to_char(mt.executetime,'YYYY-MM-DD') as exetime,u1.username as checkname")
				.append(" from mobile_task mt,contractorinfo con,userinfo u, userinfo u1 where mt.tid='" + tid + "' ")
				.append(" and mt.contractorid=con.contractorid and mt.userid=u.userid(+) and u1.userid=mt.checkuser(+) ");
		QueryUtil qu = null;
		ResultSet rs = null;
		logger.info(sb.toString());
		try {
			qu = new QueryUtil();
			rs = qu.executeQuery(sb.toString());
			boolean flg = rs.next();
			if (flg) {
				bean = new MobileTaskBean();
				bean.setNumerical(rs.getString("numerical"));// ��ˮ��
				bean.setCheckusername(rs.getString("checkname"));// �˲�������
				bean.setConname(rs.getString("conname"));// ��ά��
				bean.setExecutetime(rs.getString("exetime"));// ִ��ʱ��
				bean.setTitle(rs.getString("tasktitle"));// ��������
				bean.setMachinetype(rs.getString("type"));
				bean.setRemark(rs.getString("remark"));
				bean.setUserconname(rs.getString("exename"));
			}
		} catch (Exception e) {
			return null;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("get one task error:" + e.getMessage());
				}
			}
			qu.close();
		}
		return bean;
	}

	/**
	 * ��ȡ���˲�������б�
	 * @return
	 */
	public List getTastForCheck() {
		StringBuffer sb = new StringBuffer();
		sb = new StringBuffer();
		sb.append(
				"select mt.checkuser,mt.numerical, mt.tid, mt.machinetype as type, u.username as exename,con.contractorname as conname,")
				.append(" mt.title as tasktitle,to_char(mt.executetime,'YYYY-MM-DD') as exetime,u1.username as checkname ")
				.append(" from mobile_task mt,contractorinfo con,userinfo u, userinfo u1 ")
				.append(" where mt.state='3' and mt.contractorid=con.contractorid and mt.userid=u.userid(+) and u1.userid=mt.checkuser(+) ")
				.append(" order by mt.numerical desc");
		QueryUtil qu = null;
		logger.info(sb.toString());
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sb.toString());
		} catch (Exception e) {
			logger.error("��ȡ���˲���б����:" + e.getMessage());
		} finally {
			qu.close();
		}
		return null;
	}

	public List getTaskForCon(UserInfo userinfo) {
		return null;
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
