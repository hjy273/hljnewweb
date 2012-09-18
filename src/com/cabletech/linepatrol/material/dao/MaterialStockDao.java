package com.cabletech.linepatrol.material.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.HibernateSession;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.domain.MaterialStock;
import com.cabletech.linepatrol.remedy.constant.RemedyConstant;

@Repository
public class MaterialStockDao extends HibernateDao<MaterialStock, Integer> {
	private static Logger logger = Logger.getLogger(MaterialStockDao.class
			.getName());

	private OracleIDImpl ora = new OracleIDImpl();

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * ִ���ж�������������б��в����Ƿ�����㹻���
	 * 
	 * @param materialIdList
	 *            List ����������ϱ���б�
	 * @param materialUseNumberList
	 *            List ����������������б�
	 * @param materialAddrList
	 *            List ����������ϴ�ŵص��б�
	 * @param storageTypeList
	 *            List ��������ʹ�õĲ��������б�
	 * @return boolean ������������б��в����Ƿ�����㹻���
	 * @throws Exception
	 */
	public boolean judgeEnoughStorage(List materialIdList,
			List materialUseNumberList, List materialAddrList,
			List storageTypeList) throws Exception {
		String baseSql = "select id from LP_MT_ADDR_STOCK where 1=1 ";
		String sql = "";
		List list;
		for (int i = 0; materialIdList != null && i < materialIdList.size(); i++) {
			sql = baseSql + " and materialid='" + materialIdList.get(i) + "' ";
			sql = sql + " and addressid='" + materialAddrList.get(i) + "' ";
			if (RemedyConstant.OLD_STORAGE_MATERIAL.equals(storageTypeList
					.get(i))) {
				sql = sql + " and oldstock>=" + materialUseNumberList.get(i)
						+ " ";
			}
			if (RemedyConstant.NEW_STORAGE_MATERIAL.equals(storageTypeList
					.get(i))) {
				sql = sql + " and newstock>=" + materialUseNumberList.get(i)
						+ " ";
			}
			logger.info("Execute sql:" + sql);
			list = super.getJdbcTemplate().queryForBeans(sql);
			if (list == null || list.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ִ�и��ݲ�ѯ������ȡ���Ͽ����Ϣ�б�
	 * 
	 * @param condition
	 *            String ��ѯ����
	 * @return List ���Ͽ����Ϣ�б�
	 * @throws Exception
	 */
	public List queryList(String condition) {
		// TODO Auto-generated method stub
//		String sql = "select distinct t.id,t.materialid,t.addressid,a.address, ";
//		sql = sql + " decode(t.oldstock,null,0,t.oldstock) as oldstock, ";
//		sql = sql + " decode(t.newstock,null,0,t.newstock) as newstock ";
//		sql = sql + " from LP_MT_ADDR_STOCK t,LP_MT_ADDR a, ";
//		sql = sql + " contractorinfo c,region r ";
//		sql = sql
//				+ " where t.addressid=a.id and a.contractorid=c.contractorid ";
//		sql = sql + " and c.regionid=r.regionid ";
//		sql = sql + condition;
//		logger.info("Execute sql:" + sql);
		
		String sql = "select distinct t.id,t.materialid,t.addressid,a.address,  " +
				"case when cast(decode(t.oldstock,null,0,t.oldstock) as decimal) - (select decode(sum(tony.used_number),null,0,sum(tony.used_number)) from lp_material_used tony where tony.material_id = t.materialid and tony.object_id in (select id from LP_REMEDY where state='30')) is null then 0 " +
				"when cast(decode(t.oldstock,null,0,t.oldstock) as decimal) - (select decode(sum(tony.used_number),null,0,sum(tony.used_number)) from lp_material_used tony where tony.material_id = t.materialid and tony.object_id in (select id from LP_REMEDY where state='30')) < 0 then 0 " +
				"else cast(decode(t.oldstock,null,0,t.oldstock) as decimal) - (select decode(sum(tony.used_number),null,0,sum(tony.used_number)) from lp_material_used tony where tony.material_id = t.materialid and tony.object_id in (select id from LP_REMEDY where state='30')) end as oldstock,  " +
				"case when cast(decode(t.newstock,null,0,t.newstock) as decimal) - (select decode(sum(tony.used_number),null,0,sum(tony.used_number)) from lp_material_used tony where tony.material_id = t.materialid and tony.object_id in (select id from LP_REMEDY where state='30')) is null then 0 " +
				"when cast(decode(t.newstock,null,0,t.newstock) as decimal) - (select decode(sum(tony.used_number),null,0,sum(tony.used_number)) from lp_material_used tony where tony.material_id = t.materialid and tony.object_id in (select id from LP_REMEDY where state='30')) < 0 then 0 " +
				"else cast(decode(t.newstock,null,0,t.newstock) as decimal) - (select decode(sum(tony.used_number),null,0,sum(tony.used_number)) from lp_material_used tony where tony.material_id = t.materialid and tony.object_id in (select id from LP_REMEDY where state='30')) end  as newstock  " +
				"from LP_MT_ADDR_STOCK t,LP_MT_ADDR a,  contractorinfo c,region r  " +
				"where t.addressid=a.id and a.contractorid=c.contractorid  and c.regionid=r.regionid ";
		
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * �ƶ���ѯ��ά
	 * 
	 * @param user
	 * @return
	 */
	public List getConsByDeptId(UserInfo user) {
		String sql = "select c.contractorid,c.contractorname from contractorinfo c"
				+ " where c.state is null and c.depttype=2 and c.regionid=?";
		logger.info("regionid:" + user.getRegionID());
		logger.info("�ƶ���ѯ��ά:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, user.getRegionID());
	}

	/**
	 * ��ѯ���в������ڵص�
	 * 
	 * @param user
	 * @return
	 */
	public List getAllMTAddr() {
		String sql = "select la.id,la.address,la.contractorid from LP_mt_addr la where la.state=1  order by contractorid";
		logger.info("��ѯ���в��ϵ�ַ��" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ���ݴ�άid ��ѯ�������ڵص�
	 * 
	 * @param user
	 * @return
	 */
	public List getMTAddrByConId(String id) {
		String sql = "select la.id,la.address from LP_mt_addr la where la.state=1 and contractorid=? order by address";
		logger.info("id:" + id);
		logger.info("��ά��ѯ���ϵ�ַ��" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, id);
	}

	/**
	 * ����ά��ѯ���Ͽ��
	 * 
	 * @param user
	 * @return
	 */
	public List getMarterialStocksByCon(String deptype, String contraid,
			String addrID) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("select lt.typename,lm.modelname,base.name,addr.address,NVL(stock.newstock,0)newstock,NVL(stock.oldstock,0) oldstock,");
		sb.append(" (NVL(stock.newstock,0)+NVL(stock.oldstock,0)) sum");
		if (deptype.equals("1")) {// �ƶ�
			sb.append(",con.contractorname ");
		}
		sb
				.append(" from LP_mt_base base,LP_mt_addr_stock stock,LP_mt_addr addr,");
		sb.append(" LP_mt_model lm,LP_mt_type lt ");
		if (deptype.equals("1")) {// �ƶ���ѯ����
			// sb.append(", LP_mt_stock mtstock,contractorinfo con ");
			sb.append(", contractorinfo con ");
		}
		sb
				.append(" where base.state=1 and base.id=stock.materialid and stock.addressid=addr.id");
		sb.append(" and lm.typeid=lt.id and base.modelid=lm.id ");
		if (deptype.equals("2")) {
			sb.append(" and addr.contractorid='" + contraid + "' ");
		}
		if (deptype.equals("1")) {
			if (contraid != null && !contraid.equals("-1")) {
				sb.append(" and addr.contractorid='" + contraid + "' ");
			}
			// sb.append(" and mtstock.materialid=base.id  and con.contractorid=addr.contractorid ");
			sb.append(" and con.contractorid=addr.contractorid ");
		}
		if (addrID != null && !addrID.equals("-1")) {
			sb.append(" and addr.id='" + addrID + "'");
		}
		if (deptype.equals("1")) {
			sb
					.append(" order by stock.createtime,con.contractorid,lt.typename");
		} else {
			sb.append(" order by stock.createtime,lt.typename");
		}
		logger.info("����ά��ѯ���Ͽ�棺" + sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString());
	}

	/**
	 * ���ݴ�άid ��ѯaddress
	 * 
	 * @param user
	 * @return
	 */
	public List getAddrById(String id) {
		String sql = "select la.id,la.address from LP_mt_addr la where la.state=1 and id=? order by address";
		logger.info("id:" + id);
		logger.info("���ݴ�άid ��ѯaddress:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, id);
	}

	/**
	 * ��ѯ��������
	 * 
	 * @param regionid
	 * @return
	 */
	public List getMTTypes(String regionid) {
		String sql = "select lt.id,lt.typename from LP_mt_type lt where lt.state=1 and lt.regionid=?";
		logger.info("regionid:" + regionid);
		logger.info("��ѯ��������:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, regionid);
	}

	/**
	 * ���ݲ������Ͳ�ѯ���Ϲ��
	 * 
	 * @param regionid
	 * @return
	 */
	public List getModelByType(String typeid) {
		String sql = "select lm.id,lm.modelname from LP_mt_model lm where lm.state=1 and lm.typeid=?";
		logger.info("typeid:" + typeid);
		logger.info("���ݲ������Ͳ�ѯ���Ϲ��:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, typeid);
	}

	/**
	 * ���ݲ��Ϲ���ѯ����
	 * 
	 * @param regionid
	 * @return
	 */
	public List getMTByModel(String modelid) {
		String sql = "select lb.id,lb.name from LP_mt_base lb where lb.state=1 and lb.modelid=?";
		logger.info("modelid:" + modelid);
		logger.info("���ݲ��Ϲ���ѯ����" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, modelid);
	}

	/**
	 * �����ϲ�ѯ���Ͽ��
	 * 
	 * @param user
	 * @return
	 */
	public List getMarterialStocksByMT(UserInfo user, String typeid,
			String modelid, String mtid) {
		String deptype = user.getDeptype();
		StringBuffer sb = new StringBuffer();
		sb
				.append("select lt.typename,lm.modelname,base.name,addr.address,NVL(stock.newstock,0)newstock,NVL(stock.oldstock,0) oldstock,");
		sb.append(" (NVL(stock.newstock,0)+NVL(stock.oldstock,0)) sum");
		if (deptype.equals("1")) {// �ƶ�
			sb.append(",con.contractorname ");
		}
		sb
				.append(" from LP_mt_base base,LP_mt_addr_stock stock,LP_mt_addr addr,");
		sb.append(" LP_mt_model lm,LP_mt_type lt ");
		if (deptype.equals("1")) {// �ƶ���ѯ����
			// sb.append(", LP_mt_stock mtstock,contractorinfo con ");
			sb.append(",contractorinfo con ");
		}
		sb
				.append(" where base.state=1 and base.id=stock.materialid and stock.addressid=addr.id");
		sb.append(" and lm.typeid=lt.id and base.modelid=lm.id ");
		if (deptype.equals("2")) {
			sb.append(" and addr.contractorid='" + user.getDeptID() + "' ");
		}
		if (deptype.equals("1")) {
			// sb.append(" and mtstock.materialid=base.id  and con.contractorid=addr.contractorid");
			sb.append(" and con.contractorid=addr.contractorid");
		}
		if (typeid != null && !typeid.equals("")) {
			sb.append(" and lt.id='" + typeid + "'");
		}
		if (modelid != null && !modelid.equals("")) {
			sb.append(" and lm.id='" + modelid + "'");
		}
		if (mtid != null && !mtid.equals("")) {
			sb.append(" and base.id='" + mtid + "'");
		}
		if (deptype.equals("1")) {
			sb
					.append(" order by stock.createtime,con.contractorid,lt.typename");
		} else {
			sb.append(" order by stock.createtime,lt.typename");
		}
		logger.info("�����ϲ�ѯ���Ͽ�棺" + sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString());
	}

	public boolean updateMaterialAddrStorage(List mtUsedStockAddrList) {
		boolean flag = false;
		DynaBean bean;
		String sql = "";
		try {
			for (int i = 0; mtUsedStockAddrList != null
					&& i < mtUsedStockAddrList.size(); i++) {
				bean = (DynaBean) mtUsedStockAddrList.get(i);
				if (bean != null) {
					sql = "update LP_MT_ADDR_STOCK set ";
					sql = sql + " oldstock=" + bean.get("old_stock") + ", ";
					sql = sql + " newstock=" + bean.get("new_stock") + " ";
					sql = sql + " where materialid=" + bean.get("materialid")
							+ " ";
					sql = sql + " and addressid=" + bean.get("addressid") + " ";
					logger.info("ִ��sql��" + sql);
					this.getJdbcTemplate().execute(sql);
				}
			}
			flag = true;
		} catch (Exception e) {
			try {
				HibernateSession.rollbackTransaction();
			} catch (HibernateException e1) {
			}
			logger.error("ִ�и��³���", e);
		}
		return flag;
	}

	public boolean updateMaterialStorage(List mtUsedStockList) {
		boolean flag = false;
		DynaBean bean;
		String sql = "";
		try {
			for (int i = 0; mtUsedStockList != null
					&& i < mtUsedStockList.size(); i++) {
				bean = (DynaBean) mtUsedStockList.get(i);
				if (bean != null) {
					sql = "update LP_MT_STOCK set ";
					sql = sql + " oldstock=( ";
					sql = sql
							+ " select decode(sum(old_stock),null,0,sum(old_stock)) ";
					sql = sql
							+ " from LP_MT_USED_DETAIL detail,LP_MT_ADDR addr ";
					sql = sql + " where detail.addressid=addr.id ";
					sql = sql + " and detail.mtusedid='" + bean.get("mtusedid")
							+ "' ";
					sql = sql + " and detail.materialid='"
							+ bean.get("materialid") + "' ";
					sql = sql + " and addr.contractorid='"
							+ bean.get("contractorid") + "' ";
					sql = sql + " ), ";
					sql = sql + " newstock=( ";
					sql = sql
							+ " select decode(sum(new_stock),null,0,sum(new_stock)) ";
					sql = sql
							+ " from LP_MT_USED_DETAIL detail,LP_MT_ADDR addr ";
					sql = sql + " where detail.addressid=addr.id ";
					sql = sql + " and detail.mtusedid='" + bean.get("mtusedid")
							+ "' ";
					sql = sql + " and detail.materialid='"
							+ bean.get("materialid") + "' ";
					sql = sql + " and addr.contractorid='"
							+ bean.get("contractorid") + "' ";
					sql = sql + " ) ";
					sql = sql + " where materialid=" + bean.get("materialid")
							+ " ";
					sql = sql + " and contractorid=" + bean.get("contractorid")
							+ " ";
					logger.info("ִ��sql��" + sql);
					this.getJdbcTemplate().execute(sql);
				}
			}
			flag = true;
		} catch (Exception e) {
			try {
				HibernateSession.rollbackTransaction();
			} catch (HibernateException e1) {
				// TODO Auto-generated catch block
			}
			logger.error("ִ�и��³���", e);
		}
		return flag;
	}

	public boolean updateMaterialStorage(List mtUsedStockList,
			List mtUsedStockAddrList) {
		boolean flag = false;
		DynaBean bean;
		DynaBean tmpBean;
		String sql = "";
		try {
			for (int j = 0; mtUsedStockAddrList != null
					&& j < mtUsedStockAddrList.size(); j++) {
				tmpBean = (DynaBean) mtUsedStockAddrList.get(j);
				if (tmpBean != null) {
					for (int i = 0; mtUsedStockList != null
							&& i < mtUsedStockList.size(); i++) {
						bean = (DynaBean) mtUsedStockList.get(i);
						if (bean != null) {
							if (bean.get("materialid") != null
									&& bean.get("materialid").equals(
											tmpBean.get("materialid"))) {
								sql = "update LP_MT_STOCK set ";
								sql = sql + " oldstock=( ";
								sql = sql
										+ " select decode(sum(old_stock),null,0,sum(old_stock)) ";
								sql = sql
										+ " from LP_MT_USED_DETAIL detail,LP_MT_ADDR addr ";
								sql = sql + " where detail.addressid=addr.id ";
								sql = sql + " and detail.mtusedid='"
										+ bean.get("mtusedid") + "' ";
								sql = sql + " and detail.materialid='"
										+ bean.get("materialid") + "' ";
								sql = sql + " and addr.contractorid='"
										+ bean.get("contractorid") + "' ";
								sql = sql + " ), ";
								sql = sql + " newstock=( ";
								sql = sql
										+ " select decode(sum(new_stock),null,0,sum(new_stock)) ";
								sql = sql
										+ " from LP_MT_USED_DETAIL detail,LP_MT_ADDR addr ";
								sql = sql + " where detail.addressid=addr.id ";
								sql = sql + " and detail.mtusedid='"
										+ bean.get("mtusedid") + "' ";
								sql = sql + " and detail.materialid='"
										+ bean.get("materialid") + "' ";
								sql = sql + " and addr.contractorid='"
										+ bean.get("contractorid") + "' ";
								sql = sql + " ) ";
								sql = sql + " where materialid="
										+ bean.get("materialid") + " ";
								sql = sql + " and contractorid="
										+ bean.get("contractorid") + " ";
								logger.info("ִ��sql��" + sql);
								this.getJdbcTemplate().execute(sql);
							}
						}
					}
				}
			}
			flag = true;
		} catch (Exception e) {
			try {
				HibernateSession.rollbackTransaction();
			} catch (HibernateException e1) {
				// TODO Auto-generated catch block
			}
			logger.error("ִ�и��³���", e);
		}
		return flag;
	}

	public List getMaterialInList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select material_stock_record.*,ci.contractorname, ";
		sql += " to_char(material_stock_record.record_date,'yyyy-mm-dd hh24:mi:ss') as record_date_string, ";
		sql += " to_char(material_stock_record.record_date,'yyyy-mm-dd') as record_date_simple_string from ( ";
		// ������������ѯ
		sql += " select item.count,'�������' as source,item.state as storage_type,item.addressid,item.materialid, ";
		sql += " lmt.id as typeid,lmm.id as modelid,apply.createdate as record_date,apply.contractorid, ";
		sql += " lmb.name||'��'||lmt.typename||'����'||lmm.modelname||'��' as material_name,lma.address,";
		sql += " decode(item.state,'0','����','1','����','����') as storage_type_name ";
		sql += " from lp_mt_storage_item item,lp_mt_storage storage,lp_mt_new apply,lp_approve_info approve, ";
		sql += " lp_mt_type lmt,lp_mt_model lmm,lp_mt_base lmb,lp_mt_addr lma ";
		sql += " where item.storageid=storage.id and storage.applyid=apply.id ";
		sql += " and lmt.id=lmm.typeid and lmm.id=lmb.modelid and lmb.id=item.materialid ";
		sql += " and lma.id=item.addressid ";
		sql += " and apply.id=approve.object_id and approve.object_type='LP_MT_STORAGE' and approve.approve_result='1' ";
		sql += " union ";
		// ���ղ�������ѯ
		sql += " select item.return_number as count, ";
		sql += " lp_material_use_table.use_source||decode(lp_material_use_table.object_type,'cut','���','trouble','����','')||'--�������' as source, ";
		sql += " item.storage_type,lma.id as addressid,lmb.id as materialid, ";
		sql += " lmt.id as typeid,lmm.id as modelid,lp_material_use_table.create_date as record_date,lp_material_use_table.contractorid, ";
		sql += " lmb.name||'��'||lmt.typename||'����'||lmm.modelname||'��' as material_name,lma.address,";
		sql += " decode(item.storage_type,'0','����','1','����','����') as storage_type_name ";
		sql += " from lp_material_return item,( ";
		sql += " select cut.id as object_id,'cut' as object_type,u.deptid as contractorid,feedback.begintime as create_date, ";
		sql += " cut.cut_name as use_source ";
		sql += " from lp_cut cut,lp_cut_feedback feedback,userinfo u,lp_approve_info approve ";
		sql += " where cut.proposer=u.userid and cut.id=feedback.cut_id ";
		sql += " and approve.object_id=feedback.id and approve.approve_result='1' and approve.object_type='LP_CUT_FEEDBACK' ";
		sql += " union ";
		sql += " select trouble_reply.id as object_id,'trouble' as object_type,u.deptid as contractorid, ";
		sql += " trouble_reply.complete_time as create_date,trouble.trouble_name as use_source ";
		sql += " from lp_trouble_reply trouble_reply,lp_trouble_info trouble,userinfo u,lp_approve_info approve ";
		sql += " where trouble_reply.trouble_id=trouble.id and trouble_reply.reply_man_id=u.userid ";
		sql += " and approve.object_id=trouble_reply.id and approve.approve_result='1' and approve.object_type='LP_TROUBLE_REPLY' ";
		sql += " ) lp_material_use_table, ";
		sql += " lp_mt_type lmt,lp_mt_model lmm,lp_mt_base lmb,lp_mt_addr lma ";
		sql += " where item.object_id=lp_material_use_table.object_id and item.use_type=lp_material_use_table.object_type ";
		sql += " and lmt.id=lmm.typeid and lmm.id=lmb.modelid and lmb.id=item.material_id ";
		sql += " and lma.id=item.storage_address_id ";
		sql += " union ";
		// ���ϵ�������ѯ
		sql += " select decode(item.oldstock,0,item.newstock,item.oldstock) as count,'�������' as source, ";
		sql += " decode(item.oldstock,0,'0','1') as storage_type, ";
		sql += " item.newaddressid as addressid,item.materialid,lmt.id as typeid,lmm.id as modelid, ";
		sql += " trunc(allot.changedate,'dd') as record_date,item.newcontractorid as contractorid, ";
		sql += " lmb.name||'��'||lmt.typename||'����'||lmm.modelname||'��' as material_name,lma.address,";
		sql += " decode(item.oldstock,0,'����','����') as storage_type_name ";
		sql += " from lp_mt_change_ite item,lp_mt_allot allot, ";
		sql += " lp_mt_type lmt,lp_mt_model lmm,lp_mt_base lmb,lp_mt_addr lma ";
		sql += " where item.allotid=allot.id ";
		sql += " and lmt.id=lmm.typeid and lmm.id=lmb.modelid and lmb.id=item.materialid ";
		sql += " and lma.id=item.newaddressid and item.newaddressid<>item.oldaddressid ";
		sql += " ) material_stock_record,contractorinfo ci ";
		sql += " where material_stock_record.contractorid=ci.contractorid ";
		sql += condition;
		sql += " order by material_stock_record.record_date,material_stock_record.materialid,material_stock_record.addressid ";
		logger.info("��������¼��ѯSQL:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	public List getMaterialOutList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select material_stock_record.*,ci.contractorname, ";
		sql += " to_char(material_stock_record.record_date,'yyyy-mm-dd hh24:mi:ss') as record_date_string, ";
		sql += " to_char(material_stock_record.record_date,'yyyy-mm-dd') as record_date_simple_string from ( ";
		// ʹ�ò��ϳ����ѯ
		sql += " select item.used_number as count, ";
		sql += " lp_material_use_table.use_source||decode(lp_material_use_table.object_type,'cut','���','trouble','����','')||'--ʹ�ó���' as source, ";
		sql += " item.storage_type,lmb.id as materialid, ";
		sql += " lmt.id as typeid,lmm.id as modelid,lma.id as addressid,lp_material_use_table.create_date as record_date, ";
		sql += " lp_material_use_table.contractorid,lmb.name||'��'||lmt.typename||'����'||lmm.modelname||'��' as material_name,lma.address,";
		sql += " decode(item.storage_type,'0','����','1','����','����') as storage_type_name ";
		sql += " from lp_material_used item,( ";
		sql += " select cut.id as object_id,'cut' as object_type,u.deptid as contractorid,feedback.begintime as create_date, ";
		sql += " cut.cut_name as use_source ";
		sql += " from lp_cut cut,lp_cut_feedback feedback,userinfo u,lp_approve_info approve ";
		sql += " where cut.proposer=u.userid and cut.id=feedback.cut_id ";
		sql += " and approve.object_id=feedback.id and approve.approve_result='1' and approve.object_type='LP_CUT_FEEDBACK' ";
		sql += " union ";
		sql += " select trouble_reply.id as object_id,'trouble' as object_type,u.deptid as contractorid, ";
		sql += " trouble_reply.complete_time as create_date,trouble.trouble_name as use_source ";
		sql += " from lp_trouble_reply trouble_reply,lp_trouble_info trouble,userinfo u,lp_approve_info approve ";
		sql += " where trouble_reply.trouble_id=trouble.id and trouble_reply.reply_man_id=u.userid ";
		sql += " and approve.object_id=trouble_reply.id and approve.approve_result='1' and approve.object_type='LP_TROUBLE_REPLY' ";
		sql += " ) lp_material_use_table, ";
		sql += " lp_mt_type lmt,lp_mt_model lmm,lp_mt_base lmb,lp_mt_addr lma ";
		sql += " where item.object_id=lp_material_use_table.object_id and item.use_type=lp_material_use_table.object_type ";
		sql += " and lmt.id=lmm.typeid and lmm.id=lmb.modelid and lmb.id=item.material_id ";
		sql += " and lma.id=item.storage_address_id ";
		sql += " union ";
		// ���ϵ��������ѯ
		sql += " select decode(item.oldstock,0,item.newstock,item.oldstock) as count,'��������' as source, ";
		sql += " decode(item.oldstock,0,'0','1') as storage_type, ";
		sql += " item.oldaddressid as addressid,item.materialid,lmt.id as typeid,lmm.id as modelid, ";
		sql += " trunc(allot.changedate,'dd') as record_date,item.oldcontractorid as contractorid, ";
		sql += " lmb.name||'��'||lmt.typename||'����'||lmm.modelname||'��' as material_name,lma.address, ";
		sql += " decode(item.oldstock,0,'����','����') as storage_type_name ";
		sql += " from lp_mt_change_ite item,lp_mt_allot allot, ";
		sql += " lp_mt_type lmt,lp_mt_model lmm,lp_mt_base lmb,lp_mt_addr lma ";
		sql += " where item.allotid=allot.id ";
		sql += " and lmt.id=lmm.typeid and lmm.id=lmb.modelid and lmb.id=item.materialid ";
		sql += " and lma.id=item.oldaddressid and item.newaddressid<>item.oldaddressid ";
		sql += " ) material_stock_record,contractorinfo ci ";
		sql += " where material_stock_record.contractorid=ci.contractorid ";
		sql += condition;
		sql += " order by material_stock_record.record_date,material_stock_record.materialid,material_stock_record.addressid ";
		logger.info("���ϳ����¼��ѯSQL:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

}
