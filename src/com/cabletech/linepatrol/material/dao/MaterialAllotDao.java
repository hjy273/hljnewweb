package com.cabletech.linepatrol.material.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.beans.MaterialAllotBean;
import com.cabletech.linepatrol.material.domain.MaterialAllot;
import com.cabletech.linepatrol.material.domain.MaterialChangeItem;

@Repository
public class MaterialAllotDao extends HibernateDao<MaterialAllot, Integer> {
	private static Logger logger = Logger.getLogger(MaterialAllotDao.class.getName());
	private OracleIDImpl ora = new OracleIDImpl();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * �ƶ���ѯ��ά
	 * @param user
	 * @return
	 */
	public List getConsByDeptId(UserInfo user){
		String sql = "select c.contractorid,c.contractorname from contractorinfo c" +
			" where (c.state is null or c.state<>'1') and c.depttype=2 and c.regionid=?";
		logger.info("user.getRegionID():" + user.getRegionID());
		logger.info("�ƶ���ѯ��ά:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, user.getRegionID());
	}

	/**
	 *  ��ѯ���в������ڵص�
	 * @param user
	 * @return
	 */
	public List getAllMTAddr(){
		String sql = "select la.id,la.address,la.contractorid from LP_mt_addr la where la.state=1  order by contractorid";
		logger.info("��ѯ���в������ڵص�:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ���ݴ�άid ��ѯ�������ڵص�
	 * @param user
	 * @return
	 */
	public List getMTAddrByConId(String id){
		String sql = "select la.id,la.address from LP_mt_addr la where la.state=1 and contractorid=? order by address";
		logger.info("id:" + id);
		logger.info("���ݴ�άid ��ѯ�������ڵص�:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, id);
	}


	/**
	 * ��ѯ��������
	 * @param user
	 * @return
	 */
	public List getMaterialsByAddreId(UserInfo user,String addID){
		String deptid = user.getDeptID();
		String deptype = user.getDeptype();
		StringBuffer sb = new StringBuffer();
		sb.append("select base.id,base.name,stock.id stockid,stock.newstock,stock.oldstock,addr.id addrid,addr.address ");
		if(deptype.equals("1")){//�ƶ�
			sb.append(",mtstock.id mtstockid,mtstock.contractorid conid ");
		}
		sb.append(" from LP_mt_base base,LP_mt_addr_stock stock,LP_mt_addr addr");
		if(deptype.equals("1")){//�ƶ���ѯ����
			sb.append(", LP_mt_stock mtstock");
		}
		sb.append(" where base.state=1 and base.id=stock.materialid and stock.addressid=addr.id");
		if(deptype.equals("2")){
			sb.append(" and addr.contractorid='"+deptid+"' ");
		}
		if(deptype.equals("1")){
			sb.append(" and mtstock.materialid=base.id ");
		}
		if(addID!=null && !addID.trim().equals("")){
			sb.append(" and addr.id='"+addID+"'");
		}
		logger.info("��ѯ���ϣ�"+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString());
	}


	/**
	 * ���ݲ��ϵ�ַid ��ѯaddress
	 * @param user
	 * @return
	 */
	public List getAddrById(String id){
		String sql = "select la.id,la.address from LP_mt_addr la where la.state=1 and id=? order by address";
		logger.info("id:" + id);
		logger.info("���ݲ��ϵ�ַid ��ѯaddress:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, id.trim());
	}
	
	/**
	 * ���ݲ���id ��ѯ����
	 * @param user
	 * @return
	 */
	public List getMaterialInfo(int id){
		String sql = "select * from LP_mt_base where state=1 and id=?";
		logger.info("id:" + id);
		logger.info("���ݲ���id ��ѯ����:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, id);
	}

	public boolean addMaterialAllot(MaterialAllotBean bean,UserInfo user){
		boolean flag = false;
		UpdateUtil update = null;
		QueryUtil query = null;
		String deptype = user.getDeptype();
		Date date= new Date();
		String newTime = sdf.format(date);
		try{
			query = new QueryUtil();
			update = new UpdateUtil();
			update.setAutoCommitFalse();
			StringBuffer allot = new StringBuffer();
			allot.append("insert into LP_mt_allot(id,changer,changedate,remark) ");
			allot.append(" values("+bean.getTid()+",'"+user.getUserID()+"',to_date('"+newTime+"','yyyy-MM-dd'),'"+bean.getRemark()+"')");
			logger.info("�����������"+allot.toString());
			update.executeUpdate(allot.toString());
			String[] oldcontractorid = bean.getOldcontractorid();
			String[] newcontractorid = bean.getNewcontractorid();
			int[] oldAddress = bean.getOldaddressid();
			int[] newAddress = bean.getNewaddressid();
			int[] materialID = bean.getMaterialID();
			float [] newStock = bean.getNewstock();
			float [] oldStock = bean.getOldstock();
			int[] addrstockid = bean.getAddrstockid();
			//float[] newstockAddr = bean.getNewstockAddr();
			//float[] oldstockAddr = bean.getOldstockAddr();
			int[] mtstockid = bean.getMtstockid();
			if(oldAddress!=null && oldAddress.length>0){//������ϵ�����ϸ
				for(int i = 0 ;i<oldAddress.length;i++){
					StringBuffer changeItem = new StringBuffer();
					int id = ora.getIntSeq("mt_change_ite");
					changeItem.append("insert into LP_mt_change_ite ");
					changeItem.append(" (id,allotid,materialid,newstock,oldstock,oldaddressid,newaddressid,changer,");
					changeItem.append(" changedate,oldcontractorid,newcontractorid)");
					changeItem.append(" values("+id+","+bean.getTid()+","+materialID[i]+","+newStock[i]+","+oldStock[i]+",");
					changeItem.append( oldAddress[i]+","+newAddress[i]+",'"+user.getUserID()+"',to_date('"+newTime+"','yyyy-MM-dd')");
					if(deptype.equals("1")){
						changeItem.append(",'"+oldcontractorid[i]+"','"+newcontractorid[i]+"')");
					}
					if(deptype.equals("2")){
						changeItem.append(",'"+user.getDeptID()+"','"+user.getDeptID()+"')");
					}
					logger.info("����������ϸ:"+changeItem.toString());
					update.executeUpdate(changeItem.toString());
					//float news = newstockAddr[i]-newStock[i];
					//float olds = oldstockAddr[i]-oldStock[i];
					
					float news = 0;
					float olds = 0;
					//����
				//	String sql="update LP_mt_addr_stock set oldstock="+olds+",newstock="+news+" where id="+addrstockid[i];
					String sql="update LP_mt_addr_stock set oldstock=(NVL(oldstock,0)-"+oldStock[i]+"),newstock=(NVL(newstock,0)-"+newStock[i]+") where id="+addrstockid[i];
					logger.info("�޸ı��������ϴ�ŵص���:"+sql);
					update.executeUpdate(sql);
					String addstro = "select * from LP_mt_addr_stock ls " +
					"where materialid="+materialID[i]+" and addressid="+newAddress[i];
					logger.info("��ѯ���ϴ�ŵص���:"+addstro);
					List list = query.queryBeans(addstro);
					if(list==null || list.size()==0){//�������ص�����
						int stockid = ora.getIntSeq("LP_MT_ADDR_STOCK");
						String insertaddrStro = "insert into LP_mt_addr_stock(id,materialid,addressid,oldstock,newstock) " +
						"values("+stockid+","+materialID[i]+","+newAddress[i]+","+oldStock[i]+","+newStock[i]+")";
						logger.info("�������������ϴ�ŵص���:"+insertaddrStro);
						update.executeUpdate(insertaddrStro);
					}else{
						//��������
						String allotsql="update LP_mt_addr_stock set createtime=sysdate,oldstock=(NVL(oldstock,0)+"+oldStock[i]+"),newstock=(NVL(newstock,0)+"+newStock[i]+
						")  where materialid="+materialID[i]+" and addressid="+newAddress[i];
						logger.info("�޸ĵ��������ϴ�ŵص���:"+allotsql);
						update.executeUpdate(allotsql);
					}

					if(deptype.equals("1")){
						String stockSql="update LP_mt_stock set oldstock=(NVL(oldstock,0)-"+oldStock[i]+"),newstock=(NVL(newstock,0)-"+newStock[i]+
						") where id="+mtstockid[i];
						logger.info("�ƶ��޸ı��������Ͽ��:"+stockSql);
						String querystock="select * from LP_mt_stock ms " +
						"where ms.contractorid='"+newcontractorid[i]+"' and ms.materialid='"+materialID[i]+"'";
						logger.info("�ƶ�����ʱ���Ͽ��:"+querystock);
						List stocklist = query.queryBeans(querystock);
						if(stocklist==null || stocklist.size()==0){//����˴�ά�����ڸò��ϵļ�¼��������¼
							String materialName = "";
							List addrs = getMaterialInfo(materialID[i]);
							if(addrs!=null && addrs.size()>0){
								BasicDynaBean  basic = (BasicDynaBean) addrs.get(0);
								materialName = (String) basic.get("name");
								
							}
							String tid = ora.getSeq("LP_MT_STOCK", 10);
							String insertStock = "insert into LP_mt_stock(id,materialid,materialname,contractorid,oldstock,newstock) " +
							"values('"+tid+"','"+materialID[i]+"','"+materialName+"','"+newcontractorid[i]+"',"+oldStock[i]+","+newStock[i]+")";
							logger.info("�������Ͽ���¼:"+insertStock);
							update.executeUpdate(insertStock);
						}else{
							String stocksql="update LP_mt_stock set createtime=sysdate,oldstock=(NVL(oldstock,0)+"+oldStock[i]+"),newstock=(NVL(newstock,0)+"+newStock[i]+
							")  where contractorid='"+newcontractorid[i]+"' and materialid="+materialID[i];
							logger.info("�ƶ��޸ĵ��������Ͽ��:"+stocksql);
						}
					}
				}
			}			
			update.commit();
			update.setAutoCommitTrue();
			flag =true;
		}catch(Exception e){
			flag = false;
			logger.error(e);
		}
		return flag;
	}

	/**
	 * �õ���ѯ�Ĳ��ϵ�����
	 * @return
	 */
	public List getMaterialAllots(MaterialChangeItem changeItem ){
		String changedate = changeItem.getChangedate();
		String contraid = changeItem.getContractorid();
		String addreid = changeItem.getAddreid();
		String state = changeItem.getState();
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct allot.id,u.username,to_char(allot.changedate,'yyyy/MM/dd') changedate,allot.remark ");
		sb.append(" from LP_mt_change_ite ite ,LP_mt_base base ");
		sb.append(",contractorinfo con,contractorinfo con2, LP_mt_addr addr,LP_mt_addr addr2, ");
		sb.append(" LP_mt_allot allot,userinfo u");
		sb.append(" where base.id = ite.materialid ");
		sb.append(" and ite.oldcontractorid=con2.contractorid and ite.newcontractorid=con.contractorid");
		sb.append(" and ite.oldaddressid=addr2.id and ite.newaddressid = addr.id  ");
		sb.append(" and allot.id = ite.allotid and allot.changer=u.userid");
		if(state!=null && state.equals("0")){//����
			if(contraid !=null && !contraid.trim().equals("")){
				sb.append(" and (ite.newcontractorid ='"+contraid+"' or ite.oldcontractorid='"+contraid+"')");
			}
			if(addreid !=null && !addreid.trim().equals("")){
				sb.append(" and (ite.newaddressid ='"+addreid+"' or ite.oldaddressid ='"+addreid+"' )");
			}
		}
		if(state!=null && state.equals("1")){//����
			if(contraid !=null && !contraid.trim().equals("")){
				sb.append(" and ite.newcontractorid ='"+contraid+"'");
				sb.append(" and ite.newcontractorid=con.contractorid");
			}
			if(addreid !=null && !addreid.trim().equals("")){
				sb.append(" and ite.newaddressid ='"+addreid+"'");
				sb.append("");
			}
		}
		if(state!=null && state.equals("2")){//����
			if(contraid !=null && !contraid.trim().equals("")){
				sb.append(" and ite.oldcontractorid='"+contraid+"'");
				sb.append("");
			}
			if(addreid !=null && !addreid.trim().equals("")){
				sb.append(" and ite.oldaddressid ='"+addreid+"'");
				sb.append("");
			}
		}
		if(changedate !=null && !changedate.trim().equals("")){
			sb.append(" and ite.changedate=to_date('"+changedate+"','YYYY-MM-DD')");
		}
		sb.append(" order by changedate,username ");
		logger.info("��ѯ�Ĳ��ϵ�����:"+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString());
	}

	/**
	 * �õ���ѯ�Ĳ��ϵ�����ϸ
	 * @return
	 */
	public List getMaterialAllotItems(String allotid){
		List list = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("select base.name basename, to_char(ite.changedate,'yyyy-mm-dd') changedate, ite.oldstock,ite.newstock,");
		sb.append("newcontractorid,con.contractorname newconname,oldcontractorid,con2.contractorname oldconname,");
		sb.append("newaddressid,addr.address newaddr,oldaddressid,addr2.address oldaddr "); 
		sb.append("from LP_mt_change_ite ite ,LP_mt_base base ");
		sb.append(",contractorinfo con,contractorinfo con2, LP_mt_addr addr,LP_mt_addr addr2, ");
		sb.append(" LP_mt_allot allot ");
		sb.append(" where base.id = ite.materialid ");
		sb.append(" and ite.oldcontractorid=con2.contractorid and ite.newcontractorid=con.contractorid");
		sb.append(" and ite.oldaddressid=addr2.id and ite.newaddressid = addr.id  ");
		sb.append(" and allot.id = ite.allotid and ite.allotid='"+allotid+"'");
		logger.info("��ѯ�Ĳ��ϵ�����ϸ:" + sb.toString());
		list = this.getJdbcTemplate().queryForBeans(sb.toString());
		return list;
	}
}
