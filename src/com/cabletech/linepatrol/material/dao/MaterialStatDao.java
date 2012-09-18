package com.cabletech.linepatrol.material.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.domain.MaterialStock;

@Repository
public class MaterialStatDao extends HibernateDao<MaterialStock, String> {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public List getAllUsedMaterialNumberList(String condition) {
		String sql = "";
		sql += " select t.material_id,ci.contractorid,t.use_type,t.storage_type,used_number, ";
		sql += " mb.name||'£¨'||mm.modelname||'£©£¨'||mt.typename ||'£©' as material_name,ci.contractorname ";
		sql += " from lp_material_used t,lp_mt_addr addr,lp_mt_base mb,lp_mt_model mm,lp_mt_type mt,contractorinfo ci, ";
		sql += " ( ";
		sql += "   select cut.cut_id as id,'cut' as use_type,cut.endtime as use_time ";
		sql += "   from lp_cut_feedback cut,lp_approve_info approve ";
		sql += "   where cut.id=approve.object_id and approve.object_type='LP_CUT_FEEDBACK' ";
		sql += "   and approve.approve_result='1' ";
		sql += "   union ";
		sql += "   select trouble.id,'trouble' as use_type,trouble.return_time as use_time ";
		sql += "   from lp_trouble_reply trouble,lp_approve_info approve ";
		sql += "   where trouble.id=approve.object_id and approve.object_type='LP_TROUBLE_REPLY' ";
		sql += "   and approve.approve_result='1' ";
		// sql += "   union ";
		// sql += "   select remedy.id,'remedy' as use_type,remedy.remedydate ";
		// sql += "   from lp_remedy remedy,lp_approve_info approve ";
		// sql +=
		// "   where remedy.id=approve.object_id and approve.object_type='LP_REMEDY' ";
		// sql += "   and approve.approve_result='0' ";
		sql += " ) used_material_table ";
		sql += " where t.material_id=mb.id and mb.modelid=mm.id and mm.typeid=mt.id and t.storage_address_id=addr.id ";
		sql += " and addr.contractorid=ci.contractorid and t.object_id=used_material_table.id ";
		sql += " and t.use_type=used_material_table.use_type ";
		sql += condition;
		sql += " order by ci.contractorid,mb.id ";
		logger.info("" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}
}
