package com.cabletech.partmanage.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.services.DBService;
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.partmanage.beans.LinePatrolManagerBean;

public class LinePatrolManagerDao {
	private static Logger logger = Logger.getLogger(PartUseDao.class.getName());



	/**
	 * 得到所有移动用户
	 * @return
	 */
	public List getUserInfos(){
		String sqlString = "select userid,username from userinfo u,deptinfo d where u.deptid = d.deptid and u.state is null and d.state is null order by userid";
		try {
			QueryUtil util = new QueryUtil();
			List list = util.queryBeans(sqlString);
			return list != null ? list : new ArrayList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("MtUsedDao->getAlllContractor error!", e);
			return new ArrayList();
		}
	}

	/**
	 * 获取监理单位
	 * 
	 * @return
	 */
	public List getContractList() {
		List list = new ArrayList();
		String sql = "select c.contractorid,c.contractorname from contractorinfo c where   c.depttype='3'";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("获取监理单位出错:" + e.getMessage());
			return null;
		}
		return list;
	}

	/**
	 * 获取类型信息
	 * 
	 * @return
	 */
	public List getLinePatrolList() {
		List list = new ArrayList();
		String sql = "select t.id,t.typename from LINEPATROL_MT_TYPE t where t.state=1  order by  t.typename desc ";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("获取材料类型出错:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 获取材料规格信息
	 * 
	 * @return
	 */
	public List getLinePatrolModelByID(String id) {
		List list = new ArrayList();
		String sql = "select m.id,m.modelname,m.unit from linepatrol_mt_model m where m.state=1 and m.typeid='"
			+ id + "' order by m.modelname desc";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("获取材料规格出错:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 获取某一规格的材料信息
	 * 
	 * @return
	 */
	public List getPatorlBaseById(String id) {
		List list = new ArrayList();
		String sql = "select b.id,b.name from linepatrol_mt_base b where b.modelid='"
			+ id + "' order by b.name desc";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("获取某一规格的材料信息出错:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 获得存放地点信息
	 * 
	 * @return
	 */
	public List getAddressList(String contractorid) {
		List list = new ArrayList();
		String sql = "select a.id,a.address from LINEPATROL_MT_ADDR a where a.contractorid='"
			+ contractorid + "' order by a.address desc";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("获取材料信息出错:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 保存材料申请
	 * 
	 * @param bean
	 * @return
	 */
	public boolean addLinePatrolInfo(LinePatrolManagerBean bean,UserInfo user) {
		String sql = "insert into LINEPATROL_MT_NEW (id,contractorid,creator,createdate,remark,type,title,state) values ('"
			+ bean.getId()
			+ "','"
			+ bean.getContractorid()
			+ "','"
			+ bean.getCerator()
			+ "',sysdate,'"
			+ bean.getRemark()
			+ "','"
			+ bean.getType() + "','" + bean.getTitle() + "','0')";
		String[] counts = bean.getCount();
		String[] materialids = bean.getMaterialid();
		String[] addressids = bean.getAddressid();
		String msql = "";
		DBService ds = new DBService();
		String[] ids = ds.getSeqs(counts.length, "LINEPATROL_MT_NEW_ITEM", 10);
		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			try {
				exec.executeUpdate(sql);
				for (int i = 0; i < counts.length; i++) {
					if (materialids[i] == null || materialids[i].equals("")
							|| addressids[i] == null
							|| addressids[i].equals("")) {
						exec.rollback();
						exec.setAutoCommitTrue();
						return false;
					}
					msql = "insert into LINEPATROL_MT_NEW_ITEM values ('"
						+ ids[i] + "','" + bean.getId() + "','"
						+ materialids[i] + "','" + addressids[i] + "','"
						+ counts[i] + "','" + bean.getType() + "')";
					exec.executeUpdate(msql);
				}
				sendConMsg(user,bean.getContractorid(),bean.getTitle());
				exec.commit();
				exec.setAutoCommitTrue();
				return true;
			} catch (Exception e1) {
				logger.error("向表中插入数据时出错:" + e1.getMessage());
				logger.error("sql:" + msql);
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("保存材料申请时出错:" + e.getMessage());
			return false;
		}

	}

	/**
	 * 条件查询材料申请
	 * 
	 * @param bean
	 * @return
	 */
	public List queryLinePatrol(LinePatrolManagerBean bean, String userid) {
		List list = new ArrayList();
		String sql = " select n.id,n.title,ui.username as creator,n.remark,decode(n.type,'1','新增材料','0','利旧材料','利旧材料') type,n.state,"
			+ "to_char(n.createdate,'yyyy-mm-dd hh24:mi:ss') createdate,c.contractorname from LINEPATROL_MT_NEW n,contractorinfo c,userinfo ui "
			+ "where c.contractorid=n.contractorid and ui.userid=n.creator and n.creator='"
			+ userid + "' ";
		if (bean != null) {
			if (bean.getType() != null && !"".equals(bean.getType())) {
				sql += " and n.type='" + bean.getType() + "'";
			}
			if (bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
				sql += " and n.createdate >= to_date('" + bean.getBegintime()
				+ "','yyyy-mm-dd')";
			}
			if (bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
				sql += " and n.createdate<= TO_DATE('" + bean.getEndtime()
				+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
			}

			sql += " order by n.createdate desc";
		}
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("获取材料申请信息出错:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 查看材料申请详细信息
	 * 
	 * @param id
	 * @return
	 */
	public LinePatrolManagerBean viewLinePatrolById(String id,
			LinePatrolManagerBean bean) {
		ResultSet rst = null;
		String sql = "select n.id,n.title,ui.username as creator,n.remark,n.type,to_char(n.createdate,'yyyy-mm-dd hh24:mi:ss') createdate,c.contractorname from LINEPATROL_MT_NEW n,contractorinfo c,userinfo ui where c.contractorid=n.contractorid and ui.userid=n.creator and n.id='"
			+ id + "'";
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQuery(sql);
			if (rst.next()) {
				bean.setId(rst.getString("id"));
				bean.setCerator(rst.getString("creator"));
				bean.setRemark(rst.getString("remark"));
				bean.setType(rst.getString("type"));
				bean.setCreatedata(rst.getString("createdate"));
				bean.setContractorid(rst.getString("contractorname"));
				bean.setTitle(rst.getString("title"));
			}
			sql = "select i.count,i.state,b.name,m.modelname,t.typename,a.address,m.unit as modelunit from LINEPATROL_MT_NEW_ITEM i,LINEPATROL_MT_BASE b,LINEPATROL_MT_MODEL m,LINEPATROL_MT_TYPE t,LINEPATROL_MT_ADDR a  where i.materialid=b.id and b.modelid=m.id and t.id=m.typeid and a.id=i.addressid and i.material_new_id='"
				+ id + "'";
			rst = query.executeQuery(sql);
			rst.last();
			int num = rst.getRow();
			rst.beforeFirst();
			String[] materialid = new String[num];
			String[] addressid = new String[num];
			String[] count = new String[num];
			String[] modelname = new String[num];
			String[] modelunit = new String[num];
			String[] typename = new String[num];

			int i = 0;
			while (rst.next()) {
				materialid[i] = rst.getString("name");
				addressid[i] = rst.getString("address");
				count[i] = rst.getString("count");
				typename[i] = rst.getString("typename");
				modelname[i] = rst.getString("modelname");
				modelunit[i] = rst.getString("modelunit");
				bean.setState(rst.getString("state"));
				i++;
			}
			bean.setMaterialid(materialid);
			bean.setAddressid(addressid);
			bean.setCount(count);
			bean.setTypename(typename);
			bean.setModelname(modelname);
			bean.setModelunit(modelunit);
			rst.close();

		} catch (Exception e) {
			logger.error("查看材料申请详细信息时异常：" + e);
			try {
				rst.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();

		}
		return bean;
	}

	/**
	 * 删除材料申请
	 * 
	 * @param id
	 * @return
	 */
	public boolean delLinePatrolById(String id) {
		String sql = "delete from LINEPATROL_MT_NEW where id='" + id + "'";
		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			try {
				exec.executeUpdate(sql);
				sql = "delete from LINEPATROL_MT_NEW_ITEM where material_new_id='"
					+ id + "'";
				exec.executeUpdate(sql);
				exec.commit();
				exec.setAutoCommitTrue();
				return true;
			} catch (Exception e1) {
				logger.error("向表中插入数据时出错:" + e1.getMessage());
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("保存材料申请时出错:" + e.getMessage());
			return false;
		}
	}

	/**
	 * 得到材料申请详细信息 --》 用于修改
	 * 
	 * @param id
	 * @return
	 */
	public LinePatrolManagerBean getLinePatrolById(String id,
			LinePatrolManagerBean bean) {
		ResultSet rst = null;
		String sql = "select n.id,n.title,ui.username as creator,n.remark,n.type,to_char(n.createdate,'yyyy-mm-dd hh24:mi:ss') createdate,n.contractorid from LINEPATROL_MT_NEW n,userinfo ui where ui.userid=n.creator and n.id='"
			+ id + "'";
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQuery(sql);
			if (rst.next()) {
				bean.setId(rst.getString("id"));
				bean.setCerator(rst.getString("creator"));
				bean.setRemark(rst.getString("remark"));
				bean.setType(rst.getString("type"));
				bean.setCreatedata(rst.getString("createdate"));
				bean.setContractorid(rst.getString("contractorid"));
				bean.setTitle(rst.getString("title"));
			}
			sql = "select i.count,i.state,i.materialid,m.id as modelname,t.id as typename,i.addressid,m.unit as modelunit from LINEPATROL_MT_NEW_ITEM i,LINEPATROL_MT_BASE b,LINEPATROL_MT_MODEL m,LINEPATROL_MT_TYPE t where i.materialid=b.id and b.modelid=m.id and t.id=m.typeid and i.material_new_id='"
				+ id + "'";
			rst = query.executeQuery(sql);
			rst.last();
			int num = rst.getRow();
			rst.beforeFirst();
			String[] materialid = new String[num];
			String[] addressid = new String[num];
			String[] count = new String[num];
			String[] modelname = new String[num];
			String[] modelunit = new String[num];
			String[] typename = new String[num];

			int i = 0;
			while (rst.next()) {
				materialid[i] = rst.getString("materialid");
				addressid[i] = rst.getString("addressid");
				count[i] = rst.getString("count");
				typename[i] = rst.getString("typename");
				modelname[i] = rst.getString("modelname");
				modelunit[i] = rst.getString("modelunit");
				bean.setState(rst.getString("state"));
				i++;
			}
			bean.setMaterialid(materialid);
			bean.setAddressid(addressid);
			bean.setCount(count);
			bean.setTypename(typename);
			bean.setModelname(modelname);
			bean.setModelunit(modelunit);
			rst.close();

		} catch (Exception e) {
			logger.error("查看材料申请详细信息时异常：" + e);
			try {
				rst.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();

		}
		return bean;
	}

	/**
	 * 修改材料申请
	 * 
	 * @param bean
	 * @return
	 */
	public boolean modLinePatrolInfo(LinePatrolManagerBean bean,UserInfo user) {
		String querysql = "select state from LINEPATROL_MT_NEW where id='"+bean.getId()+"'";


		String sql = "update  LINEPATROL_MT_NEW set contractorid='"
			+ bean.getContractorid() + "',creator='" + bean.getCerator()
			+ "',createdate=sysdate,remark='" + bean.getRemark()
			+ "',type='" + bean.getType() + "',state='0' where id='"
			+ bean.getId() + "'";
		String[] counts = bean.getCount();
		String[] materialids = bean.getMaterialid();
		String[] addressids = bean.getAddressid();
		String msql = "";
		DBService ds = new DBService();
		String[] ids = ds.getSeqs(counts.length, "LINEPATROL_MT_NEW_ITEM", 10);

		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			try {
				QueryUtil query = new QueryUtil();
				List list = query.queryBeans(querysql);

				exec.executeUpdate(sql);
				sql = "delete from LINEPATROL_MT_NEW_ITEM where material_new_id='"
					+ bean.getId() + "'";
				exec.executeUpdate(sql);
				for (int i = 0; i < counts.length; i++) {
					if (materialids[i] == null || materialids[i].equals("")
							|| addressids[i] == null
							|| addressids[i].equals("")) {
						exec.rollback();
						exec.setAutoCommitTrue();
						return false;
					}
					msql = "insert into LINEPATROL_MT_NEW_ITEM values ('"
						+ ids[i] + "','" + bean.getId() + "','"
						+ materialids[i] + "','" + addressids[i] + "','"
						+ counts[i] + "','" + bean.getType() + "')";
					exec.executeUpdate(msql);
				}

				if(list !=null && list.size()>0){
					BasicDynaBean basic = (BasicDynaBean) list.get(0);
					String state = (String) basic.get("state");
					if(state.equals("2")){
						this.sendConMsg(user, bean.getContractorid(),bean.getTitle());
					}
				}
				exec.commit();
				exec.setAutoCommitTrue();
				return true;
			} catch (Exception e1) {
				logger.error("向表中插入数据时出错:" + e1.getMessage());
				logger.error("sql:" + msql);
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("保存材料申请时出错:" + e.getMessage());
			return false;
		}

	}

	/**
	 * 查找需要监理审批的材料申请
	 * 
	 * @param departid
	 *            监理单位id
	 * @return
	 */
	public List getAssessByDepart3(String departid) {
		List list = new ArrayList();
		String sql = "select n.id,n.title,ui.username as creator,to_char(n.createdate,'yyyy-mm-dd hh24:mi:ss') createdate,decode(n.type,'1','新增材料','0','利旧材料','利旧材料') type,c.contractorname "
			+ "from LINEPATROL_MT_NEW n, contractorinfo c , userinfo ui where ui.userid=n.creator and c.contractorid=n.contractorid and n.state in ('0','4') and  n.contractorid='"
			+ departid + "'";
		sql += " order by n.createdate desc";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("查找需要监理审批的材料申请信息出错:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 保存审批
	 * 
	 * @param bean
	 * @return
	 */
	public boolean addLinePatrolAssessInfo(LinePatrolManagerBean bean,
			UserInfo user,String userids) {
		String userid = user.getUserID();
		String deptype = user.getDeptype();
		ResultSet rst = null;
		ResultSet rst2 = null;
		DBService ds = new DBService();
		String tid = "";
		String sql = "insert into LINEPATROL_MT_ASSESS values　('"
			+ bean.getId() + "','" + bean.getMaterialaddid() + "','"
			+ userid + "',sysdate,'" + bean.getState() + "','"
			+ bean.getRemark() + "')";
		try {
			UpdateUtil exec = new UpdateUtil();
			QueryUtil query = new QueryUtil();
			QueryUtil query1 = new QueryUtil();
			exec.setAutoCommitFalse();
			try {
				exec.executeUpdate(sql);
				if (deptype.equals("1")) {
					if ("1".equals(bean.getState())) {
						sql = "select i.materialid,i.state,i.addressid,i.count,u.deptid,b.name from LINEPATROL_MT_NEW_ITEM i,LINEPATROL_MT_NEW n,userinfo u,LINEPATROL_MT_BASE b "
							+ " where n.id=i.material_new_id and u.userid=n.creator and b.id=i.materialid and i.material_new_id='"
							+ bean.getMaterialaddid() + "'";
						logger.info("查询材料入库项目sql："+sql);
						rst = query.executeQuery(sql);// 查找申请单材料
						while (rst.next()) {
							sql = "SELECT s.id FROM LINEPATROL_MT_ADDR_STOCK s where s.materialid='"
								+ rst.getString("materialid")
								+ "' and s.addressid='"
								+ rst.getString("addressid") + "' ";
							logger.info("查询材料地址sql："+sql);
							rst2 = query1.executeQuery(sql);// 材料库存地址是否存在
							if (rst2.next()) {
								sql = "update LINEPATROL_MT_ADDR_STOCK s set";
								if ("1".equals(rst.getString("state"))) {
									sql += " s.newstock=(NVL(s.newstock,0)+"
										+ rst.getString("count")+")";
								}else{
									sql += " s.oldstock=(NVL(s.oldstock,0)+"
										+ rst.getString("count")+")";
								}
								sql += " where s.id='" + rst2.getString("id")
								+ "'";
								System.out.println("修改材料库存sql================== "+sql);
							} else {
								tid = ds.getSeq("LP_MT_ADDR_STOCK", 10);
								System.out.println("okkk  tid " + tid);
								sql = "insert into LINEPATROL_MT_ADDR_STOCK s (s.id,s.materialid,s.addressid,s.newstock,s.oldstock";
								/*if ("1".equals(rst.getString("state"))) {
									sql += " s.newstock";
								} else {
									sql += " s.oldstock";
								}*/
								sql += ") values('" + tid + "','"
								+ rst.getString("materialid") + "','"
								+ rst.getString("addressid") + "','";
								if ("1".equals(rst.getString("state"))) {
									sql+=rst.getString("count") + "'";
									sql+=",0)";
								} else {
									sql+="0','";
									sql+=rst.getString("count") + "')";
								}

								logger.info("插入材料存放地点库存新行sql："+sql);
							}
							exec.executeUpdate(sql);// 修改材料存放地点库存
							sql = "SELECT s.id FROM LINEPATROL_MT_STOCK s where s.materialid='"
								+ rst.getString("materialid") + "' and s.contractorid='"+rst.getString("deptid")+"'" ;
							rst2 = query1.executeQuery(sql);// 材料库存是否存在
							if (rst2.next()) {
								sql = "update LINEPATROL_MT_STOCK s set";
								if ("1".equals(rst.getString("state"))) {
									sql += " s.newstock=(NVL(s.newstock,0)+"
										+ rst.getString("count")+")";
								} else {
									sql += " s.oldstock=(NVL(s.oldstock,0)+"
										+ rst.getString("count")+")";
								}
								sql += " where s.id='" + rst2.getString("id")
								+ "'";
							} else {
								tid = ds.getSeq("LINEPATROL_MT_STOCK", 10);
								sql = "insert into LINEPATROL_MT_STOCK s (s.id,s.materialid,s.materialname,s.contractorid,s.newstock,s.oldstock ";
								/*if ("1".equals(rst.getString("state"))) {
									sql += " s.newstock";
								} else {
									sql += " s.oldstock";
								}*/
								sql += ") values('" + tid + "','"
								+ rst.getString("materialid") + "','"
								+ rst.getString("name") + "','"
								+ rst.getString("deptid") + "','";
								if ("1".equals(rst.getString("state"))) {
									sql+=rst.getString("count") + "'";
									sql+=",0)";
								} else {
									sql+="0','";
									sql+=rst.getString("count") + "')";
								}

								//+ rst.getString("count") + "')";
							}
							logger.info(" 修改材料库存sql："+sql);
							exec.executeUpdate(sql);// 修改材料库存
						}

						sql = "update LINEPATROL_MT_NEW n set n.state='3' where n.id='"
							+ bean.getMaterialaddid() + "' ";
					} else {//移动审批不通过
						sql = "update LINEPATROL_MT_NEW n set n.state='4' where n.id='"
							+ bean.getMaterialaddid() + "' ";
						BasicDynaBean basic = getAssessById(bean.getMaterialaddid());
						String assessor = (String) basic.get("assessor");
						String title = (String) basic.get("title");
						sendOneMsg(user, assessor, title);
					}
					exec.executeUpdate(sql);

				} else {
					if ("1".equals(bean.getState())) {
						sql = "update LINEPATROL_MT_NEW n set n.state='1' where n.id='"
							+ bean.getMaterialaddid() + "' ";
						if(userids!=null && !"".equals(userids)){
							BasicDynaBean basic = getAssessById(bean.getMaterialaddid());
							String title = (String) basic.get("title");
							sendMsgForUsers(user, userids, title);
						}
					} else {//监理审批不通过
						sql = "update LINEPATROL_MT_NEW n set n.state='2' where n.id='"
							+ bean.getMaterialaddid() + "' ";

						String newid = bean.getMaterialaddid();
						String querysql = "select creator,title from linepatrol_mt_new where id='"+newid+"'";
						List news = query.queryBeans(querysql);
						if(news !=null && news.size()>0){
							BasicDynaBean bas = (BasicDynaBean) news.get(0);
							String creator = (String) bas.get("creator");
							String title = (String) bas.get("title");
							this.sendOneMsg(user,creator,title);
						}

					}
					exec.executeUpdate(sql);
				}
				exec.commit();
				exec.setAutoCommitTrue();
				return true;
			} catch (Exception e1) {
				logger.error("向表中插入数据时出错:" + e1.getMessage());
				logger.info("sql  " + sql);
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("保存审批材料申请时出错:" + e.getMessage());
			logger.info("sql :: " + sql);
			return false;
		}
	}

	/**
	 * 查找需要移动审批的材料申请
	 * 
	 * @param departid
	 *            监理单位id
	 * @return
	 */
	public List getAssessByDep2() {
		List list = new ArrayList();
		String sql = "select n.id,n.title,ui.username as creator,to_char(n.createdate,'yyyy-mm-dd hh24:mi:ss') createdate,decode(n.type,'1','新增材料','0','利旧材料','利旧材料') type,c.contractorname "
			+ "from LINEPATROL_MT_NEW n, contractorinfo c , userinfo ui where ui.userid=n.creator and c.contractorid=n.contractorid and n.state='1'";
		sql += " order by n.createdate desc";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("查找需要移动审批的材料申请信息出错:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 查询监理审批意见
	 * 
	 * @param bean
	 * @return
	 */
	public LinePatrolManagerBean getLineAssess(LinePatrolManagerBean bean,
			String id) {
		ResultSet rst = null;
		String sql = "select u.username,to_char(a.assessdate,'yyyy-mm-dd hh24:mi:ss') assessdate,a.remark,decode(a.state,'1','审批通过','0','不予审批','不予审批') state,c.contractorname "
			+ "from LINEPATROL_MT_ASSESS a,userinfo u,contractorinfo c where a.assessor=u.userid and u.deptype='3' and c.contractorid=u.deptid and a.materialaddid='"
			+ id + "'";
		sql += " order by a.assessdate";
		System.out.println("sql "+sql);
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQuery(sql);
			rst.last();
			int num = rst.getRow();
			rst.beforeFirst();
			String[] assesors = new String[num];
			String[] dates = new String[num];
			String[] remarks = new String[num];
			String[] states = new String[num];
			String[] cames = new String[num];
			int i = 0;
			while (rst.next()) {
				assesors[i] = rst.getString("username");
				dates[i] = rst.getString("assessdate");
				remarks[i] = rst.getString("remark");
				states[i] = rst.getString("state");
				cames[i] = rst.getString("contractorname");

				i++;
			}
			bean.setAssesor(assesors);
			bean.setAssessdate(dates);
			bean.setAremark(remarks);
			bean.setAstate(states);
			bean.setContractorname(cames);
		} catch (Exception e) {
			logger.error("查看监理审批材料申请详细信息时异常：" + e);
			System.out.println("sql  " + sql);
			try {
				rst.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();

		}
		return bean;
	}

	/**
	 * 按条件查找需要监理审批的信息
	 * 
	 * @param departid
	 *            监理单位id
	 * @return
	 */
	public List getListByDepart3(String departid, LinePatrolManagerBean bean) {
		List list = new ArrayList();
		String sql = "select n.id,n.title,ui.username as creator,to_char(n.createdate,'yyyy-mm-dd hh24:mi:ss') createdate,decode(n.type,'1','新增材料','0','利旧材料','利旧材料') type,c.contractorname,n.state "
			+ "from LINEPATROL_MT_NEW n,contractorinfo c ,userinfo ui "
			+ "where c.contractorid=n.contractorid  and ui.userid=n.creator and n.state in ('1','2','3') and n.contractorid='"
			+ departid + "'";

		if (bean != null) {
			if (bean.getType() != null && !"".equals(bean.getType())) {
				sql += " and n.type='" + bean.getType() + "'";
			}
			if (bean.getState() != null && !"".equals(bean.getState())) {
				if("1".equals(bean.getState())){
					sql += " and n.state in ('1','3')";
				}else{
					sql += " and n.state='" + bean.getState() + "'";
				}
			}
			if (bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
				sql += " and n.createdate >= to_date('" + bean.getBegintime()
				+ "','yyyy-mm-dd')";
			}
			if (bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
				sql += " and n.createdate<= TO_DATE('" + bean.getEndtime()
				+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
			}
		}
		sql += " order by n.createdate desc";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("查找监理审批的材料审批信息出错:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 查询监理审批意见
	 * 
	 * @param bean
	 * @return
	 */
	public LinePatrolManagerBean getLineAssessDept1(LinePatrolManagerBean bean,
			String id) {
		ResultSet rst = null;
		String sql = "select u.username,to_char(a.assessdate,'yyyy-mm-dd hh24:mi:ss') assessdate,a.remark,decode(a.state,'1','审批通过','0','不予审批','不予审批') state,d.deptname "
			+ "from LINEPATROL_MT_ASSESS a,userinfo u,deptinfo d where a.assessor=u.userid and u.deptype='1' and d.deptid=u.deptid and a.materialaddid='"
			+ id + "'";
		sql += " order by a.assessdate";
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQuery(sql);
			rst.last();
			int num = rst.getRow();
			rst.beforeFirst();
			String[] assesors = new String[num];
			String[] dates = new String[num];
			String[] remarks = new String[num];
			String[] states = new String[num];
			String[] cames = new String[num];
			int i = 0;
			while (rst.next()) {
				assesors[i] = rst.getString("username");
				dates[i] = rst.getString("assessdate");
				remarks[i] = rst.getString("remark");
				states[i] = rst.getString("state");
				cames[i] = rst.getString("deptname");

				i++;
			}
			bean.setAssesor(assesors);
			bean.setAssessdate(dates);
			bean.setAremark(remarks);
			bean.setAstate(states);
			bean.setContractorname(cames);
		} catch (Exception e) {
			logger.error("查看监理审批材料申请详细信息时异常：" + e);
			try {
				rst.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();

		}
		return bean;
	}

	/**
	 * 按条件查找移动审批的信息
	 * 
	 * @param departid
	 *            监理单位id
	 * @return
	 */
	public List getListByDepartFor1(LinePatrolManagerBean bean) {
		List list = new ArrayList();
		String sql = "select n.id,n.title,ui.username as creator,to_char(n.createdate,'yyyy-mm-dd hh24:mi:ss') createdate,decode(n.type,'1','新增材料','0','利旧材料','利旧材料') type,c.contractorname,n.state "
			+ "from LINEPATROL_MT_NEW n,contractorinfo c ,userinfo ui "
			+ "where c.contractorid=n.contractorid  and ui.userid=n.creator and to_number(n.state)>2";

		if (bean != null) {
			if (bean.getType() != null && !"".equals(bean.getType())) {
				sql += " and n.type='" + bean.getType() + "'";
			}
			if (bean.getState() != null && !"".equals(bean.getState())) {
				sql += " and n.state='" + bean.getState() + "'";
			}
			if (bean.getBegintime() != null && !"".equals(bean.getBegintime())) {
				sql += " and n.createdate >= to_date('" + bean.getBegintime()
				+ "','yyyy-mm-dd')";
			}
			if (bean.getEndtime() != null && !"".equals(bean.getEndtime())) {
				sql += " and n.createdate<= TO_DATE('" + bean.getEndtime()
				+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
			}
		}
		sql += " order by n.createdate desc";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("查找移动审批的材料审批信息出错:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 获取材料规格信息
	 * 
	 * @return
	 */
	public List getPatrolModellist() {
		List list = new ArrayList();
		String sql = "select m.id,m.modelname,m.typeid from linepatrol_mt_model m";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("获取材料规格出错:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 获取规格的材料信息
	 * 
	 * @return
	 */
	public List getPatorlBaselist() {
		List list = new ArrayList();
		String sql = "select b.id,b.name,b.modelid from linepatrol_mt_base b ";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("获取规格的材料信息出错:" + e.getMessage());
		}
		return list;
	}



	/**
	 * 根据id查询盘点审核表
	 * @param id
	 * @return
	 */
	public BasicDynaBean getAssessById(String id){
		StringBuffer sb = new StringBuffer();
		sb.append(" select ass.assessor,n.title   ");
		sb.append(" from linepatrol_mt_assess ass,linepatrol_mt_new n ");
		sb.append(" where n.id = ass.materialaddid and ass.state='1' and ass.materialaddid='"+id+"' ");
		sb.append(" order by ass.id desc");
		QueryUtil query;
		try {
			query = new QueryUtil();
			List list = query.queryBeans(sb.toString());
			if(list!=null && list.size()>0){
				BasicDynaBean basic = (BasicDynaBean) list.get(0);
				return basic;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 短信发送给指定监理下用户
	 * @param user
	 * @param conid
	 * @param title
	 */
	public void sendConMsg(UserInfo user,String conid,String title) {
		try {
			List simIds = getUserMobileByDeptid(conid);
			if(simIds==null || simIds.size()==0){
				return;
			}
			String msg = "[材料模块]  " +title
			+ " 等待您的处理 发送人：" + user.getUserName() + " " + SendSMRMI.MSG_NOTE;
			logger.info("材料管理的短信内容：" + msg);
			logger.info("短信发送的部门id：" +conid);
			logger.info("短信发送的目标手机号：" + simIds);
			for(int i = 0;i<simIds.size();i++){
				String simId = (String) simIds.get(i);
				logger.info("短信发送的目标手机号：" + simId);
				SendSMRMI.sendNormalMessage(user.getUserID(), simId, msg, "00");
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("发送短信业务异常:", e);
		}
	}

	/**
	 * 短信发送给指定用户
	 * @param user
	 * @param userid
	 * @param title
	 */
	public void sendOneMsg(UserInfo user,String userid,String title) {
		try {
			String simId = getUserMobile(userid);
			String msg = "[材料模块]  " +title
			+ " 等待您的处理 发送人：" + user.getUserName() + " " + SendSMRMI.MSG_NOTE;
			logger.info("材料管理的短信内容：" + msg);
			logger.info("短信发送的用户id：" + userid);
			logger.info("短信发送的目标手机号：" + simId);
			if(simId==null || simId.equals("")){
				return;
			}
			SendSMRMI.sendNormalMessage(user.getUserID(), simId, msg, "00");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("发送短信业务异常:", e);
		}
	}




	/**
	 * 执行根据用户编号获取用户的手机号码
	 * 
	 * @param conid
	 *            String 存放地点编号
	 * @return String 用户的手机号码
	 * @throws Exception
	 */
	public List getUserMobileByDeptid(String conid) throws Exception {
		List mobiles= new ArrayList();
		String sql="select u.phone from userinfo u where u.state is null and  u.deptid='"+conid+"' and u.phone is not null";
		try {
			QueryUtil queryUtil = new QueryUtil();
			List list = queryUtil.queryBeans(sql);
			if (list != null && list.size() > 0) {
				for(int i = 0;i<list.size();i++){
					DynaBean bean = (DynaBean) list.get(i);
					String mobile = (String) bean.get("phone");
					System.out.println(mobile);
					mobiles.add(mobile);
				}
				System.out.println("mobiles==== "+mobiles.size());
				return mobiles;
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw e;
		}
	}

	/**
	 * 执行根据用户编号获取用户的手机号码
	 * 
	 * @param userId
	 *            String 存放地点编号
	 * @return String 用户的手机号码
	 * @throws Exception
	 */
	public String getUserMobile(String userId) throws Exception {
		String sql = "select u.phone ";
		sql = sql + " from userinfo u ";
		sql = sql + " where u.userid='" + userId + "'";
		try {
			QueryUtil queryUtil = new QueryUtil();
			List list = queryUtil.queryBeans(sql);
			if (list != null && list.size() > 0) {
				DynaBean bean = (DynaBean) list.get(0);
				String mobile = (String) bean.get("phone");
				return mobile;
			}
			return "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw e;
		}
	}



	/**
	 * 短信发送给指定用户
	 * @param user
	 * @param userid
	 * @param title
	 */
	public void sendMsgForUsers(UserInfo user,String userids,String title) {
		try {
			QueryUtil query = new QueryUtil();
			String sql = "select u.phone ";
			sql = sql + " from userinfo u ";
			sql = sql + " where u.userid in(" + userids+ ") and u.phone is not null";
			List simIds = query.queryBeans(sql);
			String msg = "[材料模块]  " +title
			+ " 等待您的处理 发送人：" + user.getUserName() + " " + SendSMRMI.MSG_NOTE;
			logger.info("材料管理的短信内容：" + msg);
			logger.info("短信发送的用户id：" + userids);
			logger.info("短信发送的目标手机号：" + simIds);
			if(simIds!=null && simIds.size()>0){
				for(int i = 0;i<simIds.size();i++){
					DynaBean bean = (DynaBean) simIds.get(i);
					String simId = (String)bean.get("phone");
					logger.info("短信发送的目标手机号：" + simId);
					SendSMRMI.sendNormalMessage(user.getUserID(), simId, msg, "00");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("发送短信业务异常:", e);
		}
	}

}
