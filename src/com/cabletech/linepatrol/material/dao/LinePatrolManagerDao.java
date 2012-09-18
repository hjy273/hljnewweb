package com.cabletech.linepatrol.material.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.services.DBService;
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.beans.LinePatrolManagerBean;
import com.cabletech.linepatrol.material.domain.LinePatrolManager;

@Repository
public class LinePatrolManagerDao extends
		HibernateDao<LinePatrolManager, String> {
	private static Logger logger = Logger.getLogger(LinePatrolManagerDao.class
			.getName());

	/**
	 * �õ������ƶ��û�
	 * 
	 * @return
	 */
	public List getUserInfos() {
		String sql = "select userid,username from userinfo u,deptinfo d where u.deptid = d.deptid "
				+ "and u.state is null and d.state is null order by userid";
		logger.info("�õ������ƶ��û�:" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql);
		return list != null ? list : new ArrayList();
	}

	/**
	 * ��ȡ����λ
	 * 
	 * @return
	 */
	public List getContractList() {
		String sql = "select c.contractorid,c.contractorname from contractorinfo c where   c.depttype='3'";
		logger.info("��ȡ����λ:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @return
	 */
	public List getLinePatrolList() {
		String sql = "select t.id,t.typename from Lp_MT_TYPE t where t.state=1  order by  t.typename desc ";
		logger.info("��ȡ������Ϣ:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ��ȡ���Ϲ����Ϣ
	 * 
	 * @return
	 */
	public List getLinePatrolModelByID(String id) {
		String sql = "select m.id,m.modelname,m.unit from lp_mt_model m where m.state=1 and m.typeid=? order by m.modelname desc";
		logger.info("id:" + id);
		logger.info("��ȡ���Ϲ����Ϣ:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, id);
	}

	/**
	 * ��ȡĳһ���Ĳ�����Ϣ
	 * 
	 * @return
	 */
	public List getPatorlBaseById(String id) {
		String sql = "select b.id,b.name from lp_mt_base b where b.modelid=? order by b.name desc";
		logger.info("id:" + id);
		logger.info("��ȡĳһ���Ĳ�����Ϣ:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, id);
	}

	/**
	 * ��ô�ŵص���Ϣ
	 * 
	 * @return
	 */
	public List getAddressList(String contractorid) {
		String sql = "select a.id,a.address from lp_MT_ADDR a where a.contractorid=? order by a.address desc";
		logger.info("contractorid:" + contractorid);
		logger.info("��ô�ŵص���Ϣ:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, contractorid);
	}

	/**
	 * �����������
	 * 
	 * @param userids
	 * 
	 * @param bean
	 * @return
	 */
	public boolean addLinePatrolInfo(LinePatrolManager linePatrolManager,
			UserInfo user, String userids) {
		String sql = "insert into lp_MT_NEW (id,contractorid,creator,createdate,remark,type,title,state,approver_id) values ('"
				+ linePatrolManager.getId()
				+ "','"
				+ linePatrolManager.getContractorid()
				+ "','"
				+ linePatrolManager.getCerator()
				+ "',sysdate,'"
				+ linePatrolManager.getRemark()
				+ "','"
				+ linePatrolManager.getType()
				+ "','"
				+ linePatrolManager.getTitle() + "','1','" + userids + "')";
		String[] counts = linePatrolManager.getCount();
		String[] materialids = linePatrolManager.getMaterialid();
		String[] addressids = linePatrolManager.getAddressid();
		String msql = "";
		DBService ds = new DBService();
		String[] ids = ds.getSeqs(counts.length, "lp_MT_NEW_ITEM", 10);
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
					msql = "insert into lp_MT_NEW_ITEM(id,MATERIAL_NEW_ID,MATERIALID,ADDRESSID,COUNT,state) values ('" + ids[i]
							+ "','" + linePatrolManager.getId() + "','"
							+ materialids[i] + "','" + addressids[i] + "','"
							+ counts[i] + "','" + linePatrolManager.getType()
							+ "')";
					exec.executeUpdate(msql);
				}
				// sendConMsg(user, linePatrolManager.getContractorid(),
				// linePatrolManager.getTitle());
				if (userids != null && !"".equals(userids)) {
					String userIds = "";
					if (userids != null) {
						String[] userId = userids.split(",");
						for (int i = 0; userId != null && i < userId.length; i++) {
							if (userId != null) {
								userIds = userIds + "'" + userId + "'";
							}
							if (userId.length - 1 != i) {
								userIds = userIds + ",";
							}
						}
					}
					String title = linePatrolManager.getTitle();
					// sendMsgForUsers(user, userIds, title);
				}
				exec.commit();
				exec.setAutoCommitTrue();
				return true;
			} catch (Exception e1) {
				logger.error("����в�������ʱ����:" + e1.getMessage());
				logger.error("sql:" + msql);
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("�����������ʱ����:" + e.getMessage());
			return false;
		}

	}

	/**
	 * ������ѯ��������
	 * 
	 * @param linePatrolManager
	 * @return
	 */
	public List queryLinePatrol(LinePatrolManager linePatrolManager,
			String userid) {
		String sql = " select n.id,n.title,ui.username as creator,n.remark,decode(n.type,'1','��������','2','�Թ�����','0','���ɲ���','���ɲ���') type,n.state,"
				+ "to_char(n.createdate,'yyyy-mm-dd hh24:mi:ss') createdate from lp_MT_NEW n,userinfo ui "
				+ "where ui.userid=n.creator and n.creator='" + userid + "' ";
		if (linePatrolManager != null) {
			if (linePatrolManager.getType() != null
					&& !"".equals(linePatrolManager.getType())) {
				sql += " and n.type='" + linePatrolManager.getType() + "'";
			}
			if (linePatrolManager.getBegintime() != null
					&& !"".equals(linePatrolManager.getBegintime())) {
				sql += " and n.createdate >= to_date('"
						+ linePatrolManager.getBegintime() + "','yyyy-mm-dd')";
			}
			if (linePatrolManager.getEndtime() != null
					&& !"".equals(linePatrolManager.getEndtime())) {
				sql += " and n.createdate<= TO_DATE('"
						+ linePatrolManager.getEndtime()
						+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
			}

			sql += " order by n.createdate desc";
		}
		logger.info("������ѯ��������:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * �鿴����������ϸ��Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public LinePatrolManagerBean viewLinePatrolById(String id,
			LinePatrolManagerBean bean) {
		ResultSet rst = null;
		String sql = "select n.id,n.title,ui.username as creator,n.remark,n.type,to_char(n.createdate,'yyyy-mm-dd hh24:mi:ss') createdate,n.creator as creator_id from lp_MT_NEW n,userinfo ui where ui.userid=n.creator and n.id='"
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
				// bean.setContractorid(rst.getString("contractorname"));
				bean.setTitle(rst.getString("title"));
			}
			sql = "select i.count,i.state,b.name,m.modelname,t.typename,a.address,m.unit as modelunit from lp_MT_NEW_ITEM i,lp_MT_BASE b,lp_MT_MODEL m,lp_MT_TYPE t,lp_MT_ADDR a  where i.materialid=b.id and b.modelid=m.id and t.id=m.typeid and a.id=i.addressid and i.material_new_id='"
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
			logger.error("�鿴����������ϸ��Ϣʱ�쳣��" + e);
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
	 * ɾ����������
	 * 
	 * @param id
	 * @return
	 */
	public boolean delLinePatrolById(String id) {
		String sql = "delete from lp_MT_NEW where id='" + id + "'";
		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			try {
				exec.executeUpdate(sql);
				sql = "delete from lp_MT_NEW_ITEM where material_new_id='" + id
						+ "'";
				exec.executeUpdate(sql);
				exec.commit();
				exec.setAutoCommitTrue();
				return true;
			} catch (Exception e1) {
				logger.error("����в�������ʱ����:" + e1.getMessage());
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("�����������ʱ����:" + e.getMessage());
			return false;
		}
	}

	/**
	 * �õ�����������ϸ��Ϣ --�� �����޸�
	 * 
	 * @param id
	 * @return
	 */
	public LinePatrolManagerBean getLinePatrolById(String id,
			LinePatrolManagerBean bean) {
		ResultSet rst = null;
		String sql = "select n.id,n.title,ui.username as creator,n.remark,n.type,n.approver_id,u.username as approver_name,to_char(n.createdate,'yyyy-mm-dd hh24:mi:ss') createdate,n.contractorid from lp_MT_NEW n,userinfo ui,userinfo u where ui.userid=n.creator and u.userid=n.approver_id and n.id='"
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
				bean.setApproverId(rst.getString("approver_id"));
				bean.setApproverName(rst.getString("approver_name"));
			}
			sql = "select i.count,i.state,i.materialid,m.id as modelname,t.id as typename,i.addressid,m.unit as modelunit from lp_MT_NEW_ITEM i,lp_MT_BASE b,lp_MT_MODEL m,lp_MT_TYPE t where i.materialid=b.id and b.modelid=m.id and t.id=m.typeid and i.material_new_id='"
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
			logger.error("�鿴����������ϸ��Ϣʱ�쳣��" + e);
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
	 * �޸Ĳ�������
	 * 
	 * @param bean
	 * @param userids
	 * @return
	 */
	public boolean modLinePatrolInfo(LinePatrolManagerBean bean, UserInfo user,
			String userids) {
		String querysql = "select state from lp_MT_NEW where id='"
				+ bean.getId() + "'";

		String sql = "update  lp_MT_NEW set contractorid='"
				+ bean.getContractorid() + "',creator='" + bean.getCerator()
				+ "',createdate=sysdate,remark='" + bean.getRemark()
				+ "',type='" + bean.getType() + "',state='1',approver_id='"
				+ userids + "' where id='" + bean.getId() + "'";
		String[] counts = bean.getCount();
		String[] materialids = bean.getMaterialid();
		String[] addressids = bean.getAddressid();
		String msql = "";
		DBService ds = new DBService();
		String[] ids = ds.getSeqs(counts.length, "lp_MT_NEW_ITEM", 10);

		try {
			UpdateUtil exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			try {
				QueryUtil query = new QueryUtil();
				List list = query.queryBeans(querysql);

				exec.executeUpdate(sql);
				sql = "delete from lp_MT_NEW_ITEM where material_new_id='"
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
					msql = "insert into lp_MT_NEW_ITEM(id,MATERIAL_NEW_ID,MATERIALID,ADDRESSID,COUNT,state) values ('" + ids[i]
							+ "','" + bean.getId() + "','" + materialids[i]
							+ "','" + addressids[i] + "','" + counts[i] + "','"
							+ bean.getType() + "')";
					exec.executeUpdate(msql);
				}

				if (list != null && list.size() > 0) {
					BasicDynaBean basic = (BasicDynaBean) list.get(0);
					String state = (String) basic.get("state");
					if (state.equals("2")) {
						// this.sendConMsg(user, bean.getContractorid(), bean
						// .getTitle());
						if (userids != null && !"".equals(userids)) {
							String userIds = "";
							if (userids != null) {
								String[] userId = userids.split(",");
								for (int i = 0; userId != null
										&& i < userId.length; i++) {
									if (userId != null) {
										userIds = userIds + "'" + userId + "'";
									}
									if (userId.length - 1 != i) {
										userIds = userIds + ",";
									}
								}
							}
							String title = bean.getTitle();
							// sendMsgForUsers(user, userIds, title);
						}
					}
				}
				exec.commit();
				exec.setAutoCommitTrue();
				return true;
			} catch (Exception e1) {
				logger.error("����в�������ʱ����:" + e1.getMessage());
				logger.error("sql:" + msql);
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("�����������ʱ����:" + e.getMessage());
			return false;
		}

	}

	/**
	 * ������Ҫ���������Ĳ�������
	 * 
	 * @param departid
	 *            ����λid
	 * @return
	 */
	public List getAssessByDepart3(String departid) {
		String sql = "select n.id,n.title,ui.username as creator,to_char(n.createdate,'yyyy-mm-dd hh24:mi:ss') createdate,"
				+ "decode(n.type,'1','��������','2','�Թ�����','0','���ɲ���','���ɲ���') type "
				+ "from lp_MT_NEW n,  userinfo ui "
				+ "where ui.userid=n.creator and n.state in ('0','4') and  n.contractorid=? "
				+ "order by n.createdate desc";
		logger.info("departid:" + departid);
		logger.info("������Ҫ���������Ĳ�������:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, departid);
	}

	/**
	 * ��������
	 * 
	 * @param bean
	 * @return
	 */
	public boolean addLinePatrolAssessInfo(LinePatrolManagerBean bean,
			UserInfo user, String userids) {
		String userid = user.getUserID();
		String deptype = user.getDeptype();
		ResultSet rst = null;
		ResultSet rst2 = null;
		DBService ds = new DBService();
		String tid = "";
		String sql = "insert into lp_MT_ASSESS values��('" + bean.getId()
				+ "','" + bean.getMaterialaddid() + "','" + userid
				+ "',sysdate,'" + bean.getState() + "','" + bean.getRemark()
				+ "')";
		try {
			logger.info(sql);
			UpdateUtil exec = new UpdateUtil();
			QueryUtil query = new QueryUtil();
			QueryUtil query1 = new QueryUtil();
			exec.setAutoCommitFalse();
			try {
				logger.info(sql);
				exec.executeUpdate(sql);
				if (deptype.equals("1")) {
					if ("1".equals(bean.getState())) {
						sql = "select i.materialid,i.state,i.addressid,i.count,u.deptid,b.name from lp_MT_NEW_ITEM i,lp_MT_NEW n,userinfo u,lp_MT_BASE b "
								+ " where n.id=i.material_new_id and u.userid=n.creator and b.id=i.materialid and i.material_new_id='"
								+ bean.getMaterialaddid() + "'";
						logger.info("��ѯ���������Ŀsql��" + sql);
						rst = query.executeQuery(sql);// �������뵥����
						while (rst.next()) {
							sql = "SELECT s.id FROM lp_MT_ADDR_STOCK s where s.materialid='"
									+ rst.getString("materialid")
									+ "' and s.addressid='"
									+ rst.getString("addressid") + "' ";
							logger.info("��ѯ���ϵ�ַsql��" + sql);
							rst2 = query1.executeQuery(sql);// ���Ͽ���ַ�Ƿ����
							if (rst2.next()) {
								sql = "update lp_MT_ADDR_STOCK s set";
								sql+=" createtime=sysdate, ";
								if ("1".equals(rst.getString("state"))) {
									sql += " s.newstock=(NVL(s.newstock,0)+"
											+ rst.getString("count") + ")";
								} else {
									sql += " s.oldstock=(NVL(s.oldstock,0)+"
											+ rst.getString("count") + ")";
								}
								sql += " where s.id='" + rst2.getString("id")
										+ "'";
								System.out
										.println("�޸Ĳ��Ͽ��sql================== "
												+ sql);
							} else {
								tid = ds.getSeq("LP_MT_ADDR_STOCK", 10);
								System.out.println("okkk  tid " + tid);
								sql = "insert into lp_MT_ADDR_STOCK s (s.id,s.materialid,s.addressid,s.newstock,s.oldstock";
								/*
								 * if ("1".equals(rst.getString("state"))) { sql
								 * += " s.newstock"; } else { sql +=
								 * " s.oldstock"; }
								 */
								sql += ") values('" + tid + "','"
										+ rst.getString("materialid") + "','"
										+ rst.getString("addressid") + "','";
								if ("1".equals(rst.getString("state"))) {
									sql += rst.getString("count") + "'";
									sql += ",0)";
								} else {
									sql += "0','";
									sql += rst.getString("count") + "')";
								}

								logger.info("������ϴ�ŵص�������sql��" + sql);
							}
							exec.executeUpdate(sql);// �޸Ĳ��ϴ�ŵص���
							sql = "SELECT s.id FROM lp_MT_STOCK s where s.materialid='"
									+ rst.getString("materialid")
									+ "' and s.contractorid='"
									+ rst.getString("deptid") + "'";
							rst2 = query1.executeQuery(sql);// ���Ͽ���Ƿ����
							if (rst2.next()) {
								sql = "update lp_MT_STOCK s set";
								if ("1".equals(rst.getString("state"))) {
									sql += " s.newstock=(NVL(s.newstock,0)+"
											+ rst.getString("count") + ")";
								} else {
									sql += " s.oldstock=(NVL(s.oldstock,0)+"
											+ rst.getString("count") + ")";
								}
								sql += " where s.id='" + rst2.getString("id")
										+ "'";
							} else {
								tid = ds.getSeq("lp_MT_STOCK", 10);
								sql = "insert into lp_MT_STOCK s (s.id,s.materialid,s.materialname,s.contractorid,s.newstock,s.oldstock ";
								/*
								 * if ("1".equals(rst.getString("state"))) { sql
								 * += " s.newstock"; } else { sql +=
								 * " s.oldstock"; }
								 */
								sql += ") values('" + tid + "','"
										+ rst.getString("materialid") + "','"
										+ rst.getString("name") + "','"
										+ rst.getString("deptid") + "','";
								if ("1".equals(rst.getString("state"))) {
									sql += rst.getString("count") + "'";
									sql += ",0)";
								} else {
									sql += "0','";
									sql += rst.getString("count") + "')";
								}

								// + rst.getString("count") + "')";
							}
							logger.info(" �޸Ĳ��Ͽ��sql��" + sql);
							exec.executeUpdate(sql);// �޸Ĳ��Ͽ��
						}

						sql = "update lp_MT_NEW n set n.state='3' where n.id='"
								+ bean.getMaterialaddid() + "' ";
					} else {// �ƶ�������ͨ��
						sql = "update lp_MT_NEW n set n.state='2' where n.id='"
								+ bean.getMaterialaddid() + "' ";
						// BasicDynaBean basic = getAssessById(bean
						// .getMaterialaddid());
						// String assessor = (String) basic.get("assessor");
						// String title = (String) basic.get("title");
						String newid = bean.getMaterialaddid();
						String querysql = "select creator,title from lp_mt_new where id='"
								+ newid + "'";
						List news = query.queryBeans(querysql);
						if (news != null && news.size() > 0) {
							BasicDynaBean bas = (BasicDynaBean) news.get(0);
							String creator = (String) bas.get("creator");
							String title = (String) bas.get("title");
							bean.setCerator(creator);
							// this.sendOneMsg(user, creator, title);
						}
					}
					exec.executeUpdate(sql);

				} else {
					if ("1".equals(bean.getState())) {
						sql = "update lp_MT_NEW n set n.state='1' where n.id='"
								+ bean.getMaterialaddid() + "' ";
						if (userids != null && !"".equals(userids)) {
							BasicDynaBean basic = getAssessById(bean
									.getMaterialaddid());
							String title = (String) basic.get("title");
							// sendMsgForUsers(user, userids, title);
						}
					} else {// ����������ͨ��
						sql = "update lp_MT_NEW n set n.state='2' where n.id='"
								+ bean.getMaterialaddid() + "' ";

						String newid = bean.getMaterialaddid();
						String querysql = "select creator,title from lp_mt_new where id='"
								+ newid + "'";
						List news = query.queryBeans(querysql);
						if (news != null && news.size() > 0) {
							BasicDynaBean bas = (BasicDynaBean) news.get(0);
							String creator = (String) bas.get("creator");
							String title = (String) bas.get("title");
							bean.setCerator(creator);
							// this.sendOneMsg(user, creator, title);
						}

					}
					exec.executeUpdate(sql);
				}
				exec.commit();
				exec.setAutoCommitTrue();
				return true;
			} catch (Exception e1) {
				logger.error("����в�������ʱ����:", e1);
				logger.info("sql  " + sql);
				exec.rollback();
				exec.setAutoCommitTrue();
				return false;
			}
		} catch (Exception e) {
			logger.error("����������������ʱ����:", e);
			logger.info("sql :: " + sql);
			return false;
		}
	}

	/**
	 * ������Ҫ�ƶ������Ĳ�������
	 * 
	 * @param departid
	 *            ����λid
	 * @return
	 */
	public List getAssessByDep2(String userId) {
		List list = new ArrayList();
		String sql = "select n.id,n.title,ui.username as creator,to_char(n.createdate,'yyyy-mm-dd hh24:mi:ss') createdate,"
				+ "decode(n.type,'1','��������','2','�Թ�����','0','���ɲ���','���ɲ���') type,n.creator as creator_id "
				+ "from lp_MT_NEW n,  userinfo ui "
				+ "where ui.userid=n.creator and n.state='1' and n.approver_id='"
				+ userId + "' " + "order by n.createdate desc";
		logger.info("������Ҫ�ƶ������Ĳ�������:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ��ѯ�����������
	 * 
	 * @param bean
	 * @return
	 */
	public LinePatrolManagerBean getLineAssess(LinePatrolManagerBean bean,
			String id) {
		ResultSet rst = null;
		String sql = "select u.username,to_char(a.assessdate,'yyyy-mm-dd hh24:mi:ss') assessdate,a.remark,decode(a.state,'1','����ͨ��','0','��������','��������') state,c.contractorname "
				+ "from lp_MT_ASSESS a,userinfo u,contractorinfo c where a.assessor=u.userid and u.deptype='3' and c.contractorid=u.deptid and a.materialaddid='"
				+ id + "'";
		sql += " order by a.assessdate";
		System.out.println("sql " + sql);
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
			logger.error("�鿴������������������ϸ��Ϣʱ�쳣��" + e);
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
	 * ������������Ҫ������������Ϣ
	 * 
	 * @param departid
	 *            ����λid
	 * @return
	 */
	public List getListByDepart3(String departid, LinePatrolManagerBean bean) {
		List list = new ArrayList();
		String sql = "select n.id,n.title,ui.username as creator,to_char(n.createdate,'yyyy-mm-dd hh24:mi:ss') createdate,decode(n.type,'1','��������','2','�Թ�����','0','���ɲ���','���ɲ���') type,c.contractorname,n.state "
				+ "from lp_MT_NEW n,contractorinfo c ,userinfo ui "
				+ "where c.contractorid=n.contractorid  and ui.userid=n.creator and n.state in ('1','2','3') and n.contractorid='"
				+ departid + "'";

		if (bean != null) {
			if (bean.getType() != null && !"".equals(bean.getType())) {
				sql += " and n.type='" + bean.getType() + "'";
			}
			if (bean.getState() != null && !"".equals(bean.getState())) {
				if ("1".equals(bean.getState())) {
					sql += " and n.state in ('1','3')";
				} else {
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
		logger.info("����鿴��������" + sql);
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error("���Ҽ��������Ĳ���������Ϣ����:" + e.getMessage());
		}
		return list;
	}

	/**
	 * ��ѯ�����������
	 * 
	 * @param bean
	 * @return
	 */
	public LinePatrolManagerBean getLineAssessDept1(LinePatrolManagerBean bean,
			String id) {
		ResultSet rst = null;
		String sql = "select u.username,to_char(a.assessdate,'yyyy-mm-dd hh24:mi:ss') assessdate,a.remark,decode(a.state,'1','����ͨ��','0','��������','��������') state,d.deptname "
				+ "from lp_MT_ASSESS a,userinfo u,deptinfo d where a.assessor=u.userid and u.deptype='1' and d.deptid=u.deptid and a.materialaddid='"
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
			logger.error("�鿴������������������ϸ��Ϣʱ�쳣��" + e);
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
	 * �����������ƶ���������Ϣ
	 * 
	 * @param departid
	 *            ����λid
	 * @return
	 */
	public List getListByDepartFor1(LinePatrolManagerBean bean) {
		List list = new ArrayList();
		String sql = "select n.id,n.title,ui.username as creator,to_char(n.createdate,'yyyy-mm-dd hh24:mi:ss') createdate,decode(n.type,'1','��������','2','�Թ�����','0','���ɲ���','���ɲ���') type,n.state "
				+ "from lp_MT_NEW n,userinfo ui "
				+ "where ui.userid=n.creator and to_number(n.state)>2";

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
		logger.info("" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ��ȡ���Ϲ����Ϣ
	 * 
	 * @return
	 */
	public List getPatrolModellist() {
		String sql = "select m.id,m.modelname,m.typeid from lp_mt_model m";
		logger.info("" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ��ȡ���Ĳ�����Ϣ
	 * 
	 * @return
	 */
	public List getPatorlBaselist() {
		String sql = "select b.id,b.name,b.modelid from lp_mt_base b ";
		logger.info("��ȡ���Ĳ�����Ϣ:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ����id��ѯ�̵���˱�
	 * 
	 * @param id
	 * @return
	 */
	public BasicDynaBean getAssessById(String id) {
		String sql = "select ass.assessor,n.title from lp_mt_assess ass,lp_mt_new n "
				+ "where n.id = ass.materialaddid and ass.state='1' and ass.materialaddid=? order by ass.id desc";
		logger.info("id:" + id);
		logger.info("����id��ѯ�̵���˱�:" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql, id);
		if (list != null && list.size() > 0) {
			BasicDynaBean basic = (BasicDynaBean) list.get(0);
			return basic;
		}
		return null;
	}

	/**
	 * ���ŷ��͸�ָ���������û�
	 * 
	 * @param user
	 * @param conid
	 * @param title
	 */
	public void sendConMsg(UserInfo user, String conid, String title) {
		try {
			List simIds = getUserMobileByDeptid(conid);
			if (simIds == null || simIds.size() == 0) {
				return;
			}
			String msg = "[����ģ��]  " + title + " �ȴ����Ĵ��� �����ˣ�"
					+ user.getUserName() + " " + SendSMRMI.MSG_NOTE;
			logger.info("���Ϲ���Ķ������ݣ�" + msg);
			logger.info("���ŷ��͵Ĳ���id��" + conid);
			logger.info("���ŷ��͵�Ŀ���ֻ��ţ�" + simIds);
			for (int i = 0; i < simIds.size(); i++) {
				String simId = (String) simIds.get(i);
				logger.info("���ŷ��͵�Ŀ���ֻ��ţ�" + simId);
				SendSMRMI.sendNormalMessage(user.getUserID(), simId, msg, "00");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("���Ͷ���ҵ���쳣:", e);
		}
	}

	/**
	 * ���ŷ��͸�ָ���û�
	 * 
	 * @param user
	 * @param userid
	 * @param title
	 */
	public void sendOneMsg(UserInfo user, String userid, String title) {
		try {
			String simId = getUserMobile(userid);
			String msg = "[����ģ��]  " + title + " �ȴ����Ĵ��� �����ˣ�"
					+ user.getUserName() + " " + SendSMRMI.MSG_NOTE;
			logger.info("���Ϲ���Ķ������ݣ�" + msg);
			logger.info("���ŷ��͵��û�id��" + userid);
			logger.info("���ŷ��͵�Ŀ���ֻ��ţ�" + simId);
			if (simId == null || simId.equals("")) {
				return;
			}
			SendSMRMI.sendNormalMessage(user.getUserID(), simId, msg, "00");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("���Ͷ���ҵ���쳣:", e);
		}
	}

	/**
	 * ִ�и����û���Ż�ȡ�û����ֻ�����
	 * 
	 * @param conid
	 *            String ��ŵص���
	 * @return String �û����ֻ�����
	 * @throws Exception
	 */
	public List getUserMobileByDeptid(String conid) throws Exception {
		List mobiles = new ArrayList();
		String sql = "select u.phone from userinfo u where u.state is null and  u.deptid='"
				+ conid + "' and u.phone is not null";
		logger.info("ִ�и����û���Ż�ȡ�û����ֻ�����:" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				DynaBean bean = (DynaBean) list.get(i);
				String mobile = (String) bean.get("phone");
				System.out.println(mobile);
				mobiles.add(mobile);
			}
			System.out.println("mobiles==== " + mobiles.size());
			return mobiles;
		}
		return null;
	}

	/**
	 * ִ�и����û���Ż�ȡ�û����ֻ�����
	 * 
	 * @param userId
	 *            String ��ŵص���
	 * @return String �û����ֻ�����
	 * @throws Exception
	 */
	public String getUserMobile(String userId) {
		String sql = "select u.phone from userinfo u where u.userid=?";
		logger.info("userId:" + userId);
		logger.info("ִ�и����û���Ż�ȡ�û����ֻ�����:" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql, userId);
		if (list != null && list.size() > 0) {
			DynaBean bean = (DynaBean) list.get(0);
			String mobile = (String) bean.get("phone");
			return mobile;
		}
		return "";
	}

	/**
	 * ���ŷ��͸�ָ���û�
	 * 
	 * @param user
	 * @param userid
	 * @param title
	 */
	public void sendMsgForUsers(UserInfo user, String userids, String title) {
		try {
			String sql = "select u.phone from userinfo u where u.userid in(?) and u.phone is not null";
			logger.info(sql);
			List simIds = this.getJdbcTemplate().queryForBeans(sql, userids);
			String msg = "[����ģ��]  " + title + " �ȴ����Ĵ��� �����ˣ�"
					+ user.getUserName() + " " + SendSMRMI.MSG_NOTE;
			logger.info("���Ϲ���Ķ������ݣ�" + msg);
			logger.info("���ŷ��͵��û�id��" + userids);
			logger.info("���ŷ��͵�Ŀ���ֻ��ţ�" + simIds);
			if (simIds != null && simIds.size() > 0) {
				for (int i = 0; i < simIds.size(); i++) {
					DynaBean bean = (DynaBean) simIds.get(i);
					String simId = (String) bean.get("phone");
					logger.info("���ŷ��͵�Ŀ���ֻ��ţ�" + simId);
					// SendSMRMI.sendNormalMessage(user.getUserID(), simId, msg,
					// "00");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("���Ͷ���ҵ���쳣:", e);
		}
	}

}
