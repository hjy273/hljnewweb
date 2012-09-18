package com.cabletech.linepatrol.material.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.domain.MaterialInfo;
import com.cabletech.linepatrol.remedy.dao.MaterialDao;

@Repository
public class MaterialInfoDao extends HibernateDao<MaterialInfo, Integer> {
	private static Logger logger = Logger.getLogger(MaterialInfoDao.class
			.getName());

	/**
	 * ִ���ж�������������б��в��Ϸ����
	 * 
	 * @param materialList
	 *            List ������������б�
	 * @return boolean ������������б��в����Ƿ����
	 * @throws Exception
	 */
	public boolean judgeExistMaterial(List materialList) throws Exception {
		String baseSql = "select id from LP_MT_BASE where state='1' ";
		String sql = "";
		List list;
		for (int i = 0; materialList != null && i < materialList.size(); i++) {
			sql = baseSql + " and id='" + materialList.get(i) + "' ";
			logger.info("Execute sql:" + sql);
			list = super.getJdbcTemplate().queryForBeans(sql);
			if (list == null || list.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ִ�и��ݲ�ѯ������ȡ������Ϣ�б�
	 * 
	 * @param condition
	 *            String ��ѯ����
	 * @return List ������Ϣ�б�
	 * @throws Exception
	 */
	public List queryList(String condition) {
		// TODO Auto-generated method stub
		String sql = "select distinct t.id,t.name,t.modelid,to_char(t.price) as price,t.factory,t.state,tt.id, ";
		sql += "t.name||'��'||mt.modelname||'����'||tt.typename ||'��' as material_name ";
		sql = sql + " from LP_MT_BASE t,LP_MT_MODEL mt,LP_MT_TYPE tt, ";
		sql = sql + " contractorinfo c,region r ";
		sql = sql
				+ " where t.modelid=mt.id and mt.typeid=tt.id and tt.regionid=r.regionid ";
		sql = sql + " and r.regionid=c.regionid and t.state='1' ";
		sql = sql + condition;
		logger.info("Execute sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ִ�и��ݲ�ѯ������ȡ������Ϣ�б�
	 * 
	 * @param materialId
	 *            String ��ѯ����
	 * @return List ������Ϣ�б�
	 * @throws Exception
	 */
	public String getMaterialName(String materialId) {
		// TODO Auto-generated method stub
		String sql = "select distinct t.id,t.name,t.modelid,to_char(t.price) as price,t.factory,t.state,tt.id, ";
		sql += "t.name||'��'||mt.modelname||'����'||tt.typename ||'��' as material_name";
		sql = sql + " from LP_MT_BASE t,LP_MT_MODEL mt,LP_MT_TYPE tt ";
		sql = sql + " where t.modelid=mt.id and mt.typeid=tt.id ";
		sql = sql + " and t.id='" + materialId + "' ";
		logger.info("Execute sql:" + sql);
		List list = super.getJdbcTemplate().queryForBeans(sql);
		String materialName = "";
		if (list != null && !list.isEmpty()) {
			materialName = (String) ((DynaBean) list.get(0))
					.get("material_name");
		}
		return materialName;
	}

	/**
	 * �������ͻ�ȡ���
	 * 
	 * @param user
	 * 
	 * @return
	 */
	public List getModelByTypeId(String id, UserInfo user) {
		String sql = "select a.id,a.modelname from LP_mt_model a ,LP_mt_type b where a.typeid=b.id and a.state='1' and b.id=?";
		sql += " and b.regionid='" + user.getRegionid() + "' ";
		logger.info("id:" + id);
		logger.info("SQL:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, id);
	}

	/**
	 * ��ȡ�����Ϣ
	 * 
	 * @param user
	 * 
	 * @return
	 */
	public List getModelList(UserInfo user) {
		String sql = "select a.id,a.modelname from LP_mt_model a,lp_mt_type lt ";
		sql += " where a.state = '1' and a.typeid=lt.id ";
		sql += " and lt.regionid='" + user.getRegionid() + "' ";
		logger.info("sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ��ȡ����
	 * 
	 * @param user
	 * 
	 * @return
	 */
	public List getTypeList(UserInfo user) {
		String sql = "select a.id,a.typename from LP_mt_type a where a.state = '1'";
		sql += " and a.regionid='" + user.getRegionid() + "' ";
		logger.info("sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * �޸�ʱ��������id������������Ʋ�ѯ����
	 * 
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public List getMaterialTypesByBean(MaterialInfo materialInfo) {
		String sql = "select * from LP_mt_base lt where lt.state=1 and lt.name='"
				+ materialInfo.getName()
				+ "' and lt.modelid='"
				+ materialInfo.getModelid()
				+ "' and lt.id !='"
				+ materialInfo.getId() + "'";
		logger.info("��ѯ�������ͣ�" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * �޸�ʱ�жϲ������������Ƿ��ظ�
	 * 
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public boolean isHaveMaterialType(MaterialInfo materialInfo) {
		boolean flag = false;
		List list = getMaterialTypesByBean(materialInfo);
		if (list == null || list.size() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * ���ݲ�����������Ϲ�����Ʋ�ѯ���Ϲ��
	 * 
	 * @param regionID
	 * @param typeName
	 *            ������������
	 * @return
	 */
	public List getMByMAndMName(String name, String modelid) {
		String sql = "select * from LP_mt_base lm where lm.state=1 and lm.name='"
				+ name + "' and lm.modelid='" + modelid + "'";
		logger.info("sql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ����ʱ�жϲ�����Ϣ�����Ƿ��ظ�
	 * 
	 * @param regionID
	 * @param itemName
	 * @return
	 */
	public boolean isHaveMaterialName(String name, String modelid) {
		boolean flag = false;
		List list = getMByMAndMName(name, modelid);
		if (list == null || list.size() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * ��Ӳ�����Ϣ
	 * 
	 * @param bean
	 * @return
	 */
	public void addPartBase(MaterialInfo materialInfo) {
		String sql = "insert into lp_mt_base(id,name,modelid,factory,remark,state) values('"
				+ materialInfo.getId()
				+ "','"
				+ materialInfo.getName()
				+ "','"
				+ materialInfo.getModelid()
				+ "','"
				+ materialInfo.getFactory()
				+ "','" + materialInfo.getRemark() + "','1')";
		logger.info("��Ӳ�����Ϣ��" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * ��ѯ���в�����Ϣ��
	 * 
	 * @param user
	 * 
	 * @param bean
	 * @return
	 */
	public List getPartBaseBean(MaterialInfo materialInfo, UserInfo user) {
		String sql = "select a.id,a.name,a.modelid,b.modelname,a.factory,a.remark,B.typeid,LT.typename ";
		sql += " from LP_mt_base a,LP_mt_model b,lp_mt_type lt ";
		sql += " where b.id = a.modelid and b.typeid=lt.id ";
		if (materialInfo != null) {
			if (materialInfo.getId() != null
					&& !"".equals(materialInfo.getId())) {
				sql += " and a.id='" + materialInfo.getId() + "'";
			}
			if (materialInfo.getName() != null
					&& !"".equals(materialInfo.getName())) {
				sql += " and a.name like '" + "%" + materialInfo.getName()
						+ "%" + "'";
			}
			if (materialInfo.getFactory() != null
					&& !"".equals(materialInfo.getFactory())) {
				sql += " and a.factory like '" + "%"
						+ materialInfo.getFactory() + "%" + "'";
			}
			if (materialInfo.getModelid() != 0) {
				sql += " and a.modelid='" + materialInfo.getModelid() + "'";
			}
		}
		sql += " and lt.regionid='" + user.getRegionid() + "' ";
		sql += " and a.state ='1' order by a.id desc";
		logger.info("��ѯ������Ϣsql:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ɾ��������Ϣ��
	 * 
	 * @param id
	 * @return
	 */
	public void deletePartbaseById(String id) {
		String sql = "update LP_mt_base a set a.state = '0' where id='" + id
				+ "'";
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * ���������Ϣ
	 * 
	 * @param id
	 * @param bean
	 * @return
	 */
	public MaterialInfo getPartBaseById(String id, MaterialInfo materialInfo) {
		String sql = "select lt.id typeid, a.id,a.name,a.modelid,a.factory,a.remark from LP_mt_base a,LP_mt_type lt,LP_mt_model lm "
				+ " where lt.id=lm.typeid and a.modelid=lm.id and a.id='"
				+ id
				+ "'";
		ResultSet rs = null;
		try {
			QueryUtil query = new QueryUtil();
			logger.info("��ѯ������Ϣ��" + sql);
			rs = query.executeQuery(sql);
			if (rs.next()) {
				materialInfo.setId(rs.getString("id"));
				materialInfo.setName(rs.getString("name"));
				materialInfo.setModelid(rs.getInt("modelid"));
				materialInfo.setFactory(rs.getString("factory"));
				materialInfo.setRemark(rs.getString("remark"));
				materialInfo.setPrice(rs.getString("typeid"));
			}
			rs.close();
		} catch (Exception e) {
			logger.error(e);
			try {
				rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return materialInfo;
	}

	/**
	 * �޸Ĳ�����Ϣ
	 * 
	 * @param bean
	 * @return
	 */
	public void updatePartBase(MaterialInfo materialInfo) {
		String sql = "update LP_mt_base set name='" + materialInfo.getName()
				+ "',modelid='" + materialInfo.getModelid() + "',factory='"
				+ materialInfo.getFactory() + "',remark='"
				+ materialInfo.getRemark() + "' where id='"
				+ materialInfo.getId() + "'";
		this.getJdbcTemplate().execute(sql);
	}
}
