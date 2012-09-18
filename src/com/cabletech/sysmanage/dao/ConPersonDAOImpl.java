package com.cabletech.sysmanage.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.sysmanage.beans.ConPersonBean;
import com.cabletech.sysmanage.domainobjects.ConPerson;

@Repository
public class ConPersonDAOImpl extends HibernateDao<ConPerson, String> {
	private static Logger logger = Logger.getLogger(ConPersonDAOImpl.class
			.getName());
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/* ���Ѳ����Ա */
	public void addConPerson(ConPerson conPerson) {
		OracleIDImpl ora = new OracleIDImpl();
		/*
		 * String id = ora.getSeq("contractorperson", 10); conPerson.setId(id);
		 */
		// logger.info("*******************conPersonId:" + conPerson.getId());
		this.save(conPerson);
	}

	/* ͨ��Ѳ����Ա��Ų�ѯѲ����Ա��ϸ��Ϣ */
	public ConPerson findConPersonById(String id) {
		String sql = " select cp.*,c.contractorname from  contractorperson cp,contractorinfo c where cp.CONTRACTORID= c.CONTRACTORID and id='"
				+ id + "'";
		logger.info("findConPersonById::" + sql);
		List lists = this.getJdbcTemplate().queryForBeans(sql);
		BasicDynaBean bean = (BasicDynaBean) lists.get(0);
		ConPerson conPerson = new ConPerson();
		try {
			BeanUtil.objectCopy(bean, conPerson);
			conPerson.setEnterTime((Date) bean.get("enter_time"));
			conPerson.setPostOffice((String) bean.get("post_office"));
			conPerson.setLeaveTime((Date) bean.get("leave_time"));
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return conPerson;
	}

	/* ɾ��Ѳ����Ա��Ϣ */
	public void removeConPerson(ConPerson conPerson) {
		this.delete(conPerson);
	}

	/* ����Ѳ����Ա��Ϣ */
	public ConPerson updateConPerson(ConPerson conPerson) {
		this.save(conPerson);
		return conPerson;
	}

	/**
	 * <p>
	 * ����:��ָ��������õ�ǰ��½��ά��λ��������Ա��Ϣ
	 * <p>
	 * ����:��ά��λid
	 * <p>
	 * ����ֵ:��óɹ�����List,���򷵻� NULL;
	 */
	public List getAllPersonForSearch(ConPerson bean, UserInfo userinfo) {
		try {
			String sql = " select c.jobinfo,c.phone,c.mobile,d.CONTRACTORNAME,c.culture,c.id ,c.name,c.sex,c.familyaddress,c.residantarea, c.enter_time,c.leave_time,c.conditions from contractorperson c ,contractorinfo d"
					+ " where c.CONTRACTORID=d.CONTRACTORID ";
			// ���ƶ�
			if (userinfo.getDeptype().equals("1")
					&& !userinfo.getType().equals("11")) {
				sql += "  and c.regionid IN ('" + userinfo.getRegionID()
						+ "') ";
			}
			// ʡ�ƶ�
			if (userinfo.getDeptype().equals("1")
					&& userinfo.getType().equals("11")) {
				sql += " ";
			}
			// ��ά
			if (userinfo.getDeptype().equals("2")) {
				sql += "  and c.contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
						+ userinfo.getDeptID() + "')";
			}

			if (bean != null) {
				if (!bean.getName().equals("") && bean.getName() != null) {
					sql = sql + " and c.name like '" + bean.getName() + "%' ";
				}
				if (!bean.getSex().equals("") && bean.getSex() != null) {
					sql = sql + " and c.sex = '" + bean.getSex() + "'  ";
				}
				if (!bean.getCulture().equals("") && bean.getCulture() != null) {
					sql = sql + " and c.culture = '" + bean.getCulture()
							+ "'  ";
				}
				if (!bean.getIsmarriage().equals("")
						&& bean.getIsmarriage() != null) {
					sql = sql + " and c.ismarriage = '" + bean.getIsmarriage()
							+ "'  ";
				}
				if (!bean.getContractorid().equals("")
						&& bean.getContractorid() != null) {
					sql = sql + " and c.contractorid like '"
							+ bean.getContractorid() + "'  ";
				}
				if (!bean.getPhone().equals("") && bean.getPhone() != null) {
					sql = sql + " and c.phone like '" + bean.getPhone()
							+ "%'  ";
				}
				if (!bean.getMobile().equals("") && bean.getMobile() != null) {
					sql = sql + " and c.mobile like '" + bean.getMobile()
							+ "%'  ";
				}
				if (!bean.getConditions().equals("")
						&& bean.getConditions() != null) {
					sql = sql + " and c.conditions = '" + bean.getConditions()
							+ "'  ";
				}
			}
			sql = sql + " order by  name ";
			logger.info("��ѯѲ����Ա��Ϣsql:" + sql);
			return this.getJdbcTemplate().queryForBeans(sql);
		} catch (Exception e) {
			logger.error("��ָ��������õ�ǰ��½��ά��λ��������Ա��Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/* ����һ��ConPerson�İ�װ�� */
	protected class ConPersonMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ConPersonBean conPerson = new ConPersonBean();
			conPerson.setId(rs.getString("id"));
			conPerson.setName(rs.getString("name"));
			conPerson.setRegionid(rs.getString("regionid"));
			conPerson.setContractorid(rs.getString("contractorid"));
			conPerson.setContractorname(rs.getString("contractorname"));
			conPerson.setPhone(rs.getString("phone"));
			conPerson.setSex(rs.getString("sex"));
			conPerson.setJobinfo(rs.getString("jobinfo"));
			conPerson.setJobstate(rs.getString("jobstate"));
			conPerson.setCulture(rs.getString("culture"));
			conPerson.setIsmarriage(rs.getString("ismarriage"));
			conPerson.setMobile(rs.getString("mobile"));
			conPerson.setPostalcode(rs.getString("postalcode"));
			conPerson.setIdentitycard(rs.getString("identitycard"));
			conPerson.setFamilyaddress(rs.getString("familyaddress"));
			conPerson.setWorkrecord(rs.getString("workrecord"));
			conPerson.setState(rs.getString("state"));
			conPerson.setPersontype(rs.getString("persontype"));
			conPerson.setRemark(rs.getString("remark"));
			conPerson.setResidantarea(rs.getString("residantarea"));
			return conPerson;
		}
	}
}
