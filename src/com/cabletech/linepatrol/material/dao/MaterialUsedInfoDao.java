package com.cabletech.linepatrol.material.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.domain.MaterialUsedInfo;

@Repository
public class MaterialUsedInfoDao extends HibernateDao<MaterialUsedInfo, String> {
	private static Logger logger = Logger.getLogger(MaterialUsedInfoDao.class.getName());
	private OracleIDImpl ora = new OracleIDImpl();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 根据代维与材料id、获取材料信息
	 * @param conid
	 * @param mtid
	 * @return
	 */
	public List getMarterialInfos(String conid,String mtid){
		StringBuffer sb = new StringBuffer();
		sb.append("select addr.address,nvl(addstock.oldstock,0) oldstock,nvl(addstock.newstock,0) newstock,base.name,base.id baseid,addr.id addrid ");
		sb.append(" from LP_mt_addr_stock addstock,LP_mt_addr addr,LP_mt_base base ");
		sb.append("");
		sb.append(" where addstock.addressid=addr.id and addr.state=1 ");
		sb.append(" and addstock.materialid=base.id and base.state=1 ");
		if(conid!=null && !"".equals(conid.trim())){
			sb.append(" and addr.contractorid='"+conid+"'");
		}
		//	if(mtid!=null && !"".equals(mtid.trim())){
		sb.append(" and addstock.materialid='"+mtid+"'");
		//	}
		sb.append("");
		logger.info("查询材料信息sql："+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString());
	}

	/**
	 * 根据材料id与地址id获取材料信息
	 * @param conid
	 * @param mtid
	 * @return
	 */
	public List getMarterialInfo(String mtid,String addrid){
		StringBuffer sb = new StringBuffer();
		sb.append("select type.typename,model.modelname,base.name,addr.address,");
		sb.append("nvl(astock.oldstock,0) oldstock,nvl(astock.newstock,0) newstock,base.id baseid,addr.id addrid ");
		sb.append(" from LP_mt_base base,LP_mt_model model,LP_mt_type type, ");
		sb.append(" LP_mt_addr addr,LP_mt_addr_stock astock");
		sb.append(" where base.modelid=model.id and model.typeid = type.id and addr.id=astock.addressid ");
		sb.append(" and astock.materialid=base.id and base.state=1 and addr.state=1 ");
		sb.append(" and astock.materialid='"+mtid+"'");
		sb.append(" and addr.id='"+addrid+"'");
		logger.info("根据材料地址与材料id查询材料sql："+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString());
	}



}
