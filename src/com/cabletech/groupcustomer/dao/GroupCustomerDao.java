package com.cabletech.groupcustomer.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.exceltemplates.ReadExcle;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.groupcustomer.bean.GroupCustomerBean;
import com.cabletech.partmanage.action.PartRequisitionAction;

public class GroupCustomerDao {

	// ��־
	private static Logger logger = Logger.getLogger(GroupCustomerDao.class
			.getName());

	/**
	 * ����ϴ��ļ��ļ��ſͻ�����(��Ч����)
	 * 
	 * @param hform
	 *            ҳ���ύ������
	 * @param path
	 *            ��ŵ���ʱ·��
	 * @return List �ļ�������
	 */
	private List getUpInfo(GroupCustomerBean hform, String path) {
		// ���ļ����뵽ָ������ʱ·��
		if (!this.saveFile(hform, path)) {
			return null;
		}

		// ȡ��Excel�ļ��пͻ�����
		ReadExcle read = new ReadExcle(path + "\\groupcustomer.xls");
		return read.getExcleGroupCustomer();

	}

	/**
	 * ���ϴ����ļ�����Ϊ��ʱ�ļ�
	 * 
	 * @param hform
	 *            ҳ���ύ������
	 * @param path
	 *            ��ŵ���ʱ·��
	 * @return boolean ����ɹ�������,���򷵻ؼ�
	 */
	private boolean saveFile(GroupCustomerBean hform, String path) {
		// �ж��ļ��Ƿ����
		String dir = path;
		FormFile file = hform.getFile();
		if (file == null) {
			return false;
		}
		// �ж��ļ��Ƿ���ڣ�����ɾ��
		File temfile = new File(dir + "\\groupcustomer.xls");
		if (temfile.exists()) {
			temfile.delete();
		}
		// �����ļ�
		try {
			InputStream streamIn = file.getInputStream();
			OutputStream streamOut = new FileOutputStream(dir
					+ "\\groupcustomer.xls");
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
				streamOut.write(buffer, 0, bytesRead);
			}
			streamOut.close();
			streamIn.close();
			return true;
		} catch (Exception e) {
			logger.error("����ͻ����ϱ����ļ�ʱ����:" + e.getMessage());
			return false;
		}
	}

	/**
	 * ����Excel�����ݵ����ݿ�
	 * 
	 * @param hform
	 *            ҳ���ύ������
	 * @param path
	 *            ��ŵ���ʱ·��
	 * @return
	 */
	public boolean saveExcelGroupcustomerInfo(GroupCustomerBean hform,
			String path) {
		// ��ŷ���ֵ
		boolean returnFlg = false;
		// ȡ�õ����Excel�ļ�������
		List upDataInfo = getUpInfo(hform, path);
		if (upDataInfo == null) {
			return returnFlg;
		}

		String sql = null;
		Map rowMap = null;
		UpdateUtil up = null;
		OracleIDImpl ora = new OracleIDImpl();
		String[] id;
		try {
			up = new UpdateUtil();
			// ����
			up.setAutoCommitFalse();
			// ����ÿһ���ͻ���Ϣ�����뵽���ݿ�
			id = ora.getSeqs("groupcustomer", 18, upDataInfo.size());

			for (int i = 0; i < upDataInfo.size(); i++) {
				// ȡ�ü��ſͻ����ϱ�id������ֵ
				rowMap = (HashMap) upDataInfo.get(i);
				sql = "insert into groupcustomer (id, groupid, city, town, "
						+ " grouptype, groupname, groupaddress, x, y, "
						+ "regionid,operationtype,customermanager,tel,grouptel"
						+ " ) values ( " + "'" + id[i] + "', '"
						+ rowMap.get("groupid") + "' , '" + rowMap.get("city")
						+ "' , '" + rowMap.get("town") + "' , '"
						+ rowMap.get("grouptype") + "' , '"
						+ rowMap.get("groupname") + "' , '"
						+ rowMap.get("groupaddr") + "' , '" + rowMap.get("x")
						+ "' , '" + rowMap.get("y") + "' ,'"
						+ rowMap.get("regionid") + "' , '"
						+ rowMap.get("operationtype") + "' , '"
						+ rowMap.get("customermanager") + "' , '"
						+ rowMap.get("tel") + "' , '" + rowMap.get("grouptel")
						+ "')";
				up.executeUpdate(sql);
			}
			up.commit();
			up.setAutoCommitTrue();
			returnFlg = true;
		} catch (Exception e) {
			System.out.println("ERROR sql : " + sql);
			if (up != null) {
				up.rollback();
				up.setAutoCommitTrue();
			}
			logger.warn("���浼��ļ��ſͻ���Ϣ����:" + e.getMessage());
		}

		return returnFlg;
	}

	/**
	 * ȡ��������Ϣ
	 * 
	 * @return List
	 */
	public List getRegionList() {
		// sql��
		String sql = "select regionid, regionname from region "
				+ " where SUBSTR(regionid,3,6) != '0000'";

		List regionList = null;
		try {
			QueryUtil qu = new QueryUtil();
			regionList = qu.queryBeans(sql);
		} catch (Exception ex) {
			logger.warn("����������б����:" + ex.getMessage());
		}

		return regionList;
	}

	/**
	 * ���浥���ͻ�����
	 * 
	 * @param bean
	 *            ҳ���ύ������
	 * @return boolean ���ڳɹ�����true,ʧ�ܷ���false
	 */
	public boolean addCustomer(GroupCustomerBean bean) {
		// ����ֵ���
		boolean returnFlg = false;
		OracleIDImpl ora = new OracleIDImpl();
		// ȡ�ü��ſͻ����ϱ��ֶ�id������ֵ
		String id = ora.getSeq("groupcustomer", 18);

		// ���sql���
		String sql = "insert into groupcustomer (id, groupid, city, town, "
				+ " grouptype, groupname, groupaddress,  x, y, regionid, "
				+ " operationtype, customermanager, tel, grouptel "
				+ " ) values ( " + "'" + id + "', '" + bean.getGroupid()
				+ "' , '" + bean.getCity() + "' , '" + bean.getTown() + "' , '"
				+ bean.getGrouptype() + "' , '" + bean.getGroupname() + "' , '"
				+ bean.getGroupaddr() + "' , '" + bean.getX() + "' , '"
				+ bean.getY() + "' , '" + bean.getRegionid() + "' , '"
				+ bean.getOperationtype() + "' , '" + bean.getCustomermanager()
				+ "' , '" + bean.getTel() + "' , '" + bean.getGrouptel() + "')";

		UpdateUtil up;
		try {
			up = new UpdateUtil();
			up.executeUpdate(sql);
			returnFlg = true;
		} catch (Exception e) {
			logger.warn("����ͻ����ϳ���:" + e.getMessage());
		}
		return returnFlg;
	}

	/**
	 * ������ѯ���ſͻ�����
	 * 
	 * @param bean
	 *            ҳ���ύ������
	 * @param userinfo
	 *            ��¼��Ա��Ϣ
	 * @param session
	 *            session�Ự
	 * @return List
	 */
	public List queryGroupCustomer(GroupCustomerBean bean, UserInfo userinfo,
			HttpSession session) {

		String sql = "select id, groupid,city,town,grouptype, groupname, x, y,"
				+ "groupaddress,operationtype,customermanager,tel,grouptel"
				+ " from groupcustomer";
		String con = "";

		// �ͻ�����
		String name = bean.getGroupname();
		if (name != null && !"".equals(name)) {
			con = " where groupname like '%" + name + "%' ";
		}
		// ��������
		String type = bean.getGrouptype();
		if (type != null && !"".equals(type)) {
			if ("".equals(con)) {
				con += " where (grouptype = '" + type + "' or grouptype = '"
						+ type.toLowerCase() + "') ";
			} else {
				con += " and (grouptype = '" + type + "' or grouptype = '"
						+ type.toLowerCase() + "') ";
			}
		}

		// ��������
		String city = bean.getCity();
		if (city != null && !"".equals(city)) {
			if ("".equals(con)) {
				con += " where city = '" + city + "' ";
			} else {
				con += " and city = '" + city + "' ";
			}
		}

		// ҵ������
		String operationtype = bean.getOperationtype();
		if (operationtype != null && !"".equals(operationtype)) {
			if ("".equals(con)) {
				con += " where operationtype = '" + operationtype + "' ";
			} else {
				con += " and operationtype = '" + operationtype + "' ";
			}
		}

		// ����
		if (UserInfo.PROVINCE_MUSER_TYPE.equals(userinfo.getType())) {
			// ʡ�ƶ�
			String regionid = bean.getRegionid();
			if (regionid != null && !"".equals(regionid)) {
				if ("".equals(con)) {
					con += " where regionid = '" + regionid + "' ";
				} else {
					con += " and regionid = '" + regionid + "' ";
				}
			}
		} else if (UserInfo.CITY_MUSER_TYPE.equals(userinfo.getType())) {
			// ���ƶ�
			if ("".equals(con)) {
				con += " where regionid = '" + userinfo.getRegionID() + "' ";
			} else {
				con += " and regionid = '" + userinfo.getRegionID() + "' ";
			}
		}

		sql = sql + con + " order by groupid";
		session.setAttribute("querysql", sql);

		List customerList = null;
		try {
			QueryUtil qu = new QueryUtil();
			customerList = qu.queryBeans(sql);
		} catch (Exception ex) {
			logger.warn("��ÿͻ����ϳ���:" + ex.getMessage());
		}

		return customerList;
	}

	/**
	 * ȡ�ÿͻ����ϵ���ϸ��Ϣ
	 * 
	 * @param customerId
	 *            �ͻ�Id
	 * @return GroupCustomerBean
	 */
	public GroupCustomerBean getOneCustomer(String customerId) {

		String sql = "select g.id, g.groupid,g.city,g.town,g.grouptype, g.groupname, "
				+ " g.groupaddress, g.x, g.y, g.operationtype, g.customermanager, "
				+ " g.tel,g.grouptel , g.regionid, r.regionname "
				+ " from groupcustomer g, region r "
				+ " where g.regionid = r.regionid(+) and g.id = '"
				+ customerId
				+ "'";

		ResultSet rst = null;
		QueryUtil qu = null;
		GroupCustomerBean bean = null;
		try {
			qu = new QueryUtil();
			rst = qu.executeQuery(sql);

			if (rst != null && rst.next()) {
				bean = new GroupCustomerBean();
				bean.setId(rst.getString("id"));
				bean.setGroupid(rst.getString("groupid"));
				bean.setCity(rst.getString("city"));
				bean.setTown(rst.getString("town"));
				bean.setGroupname(rst.getString("groupname"));
				bean.setGroupaddr(rst.getString("groupaddress"));
				bean.setX(rst.getString("x"));
				bean.setY(rst.getString("y"));
				bean.setOperationtype(rst.getString("operationtype"));
				bean.setCustomermanager(rst.getString("customermanager"));
				bean.setTel(rst.getString("tel"));
				bean.setGrouptel(rst.getString("grouptel"));
				bean.setRegionid(rst.getString("regionid"));
				bean.setRegionname(rst.getString("regionname"));
				bean.setGrouptype(rst.getString("grouptype"));
			}
			rst.close();
		} catch (Exception e) {
			logger.warn("��õ����ͻ�������ϸ��Ϣ����:" + e.getMessage());
			if (rst != null) {
				try {
					rst.close();
				} catch (SQLException e1) {
				}
			}
		} finally {
			if (rst != null) {
				try {
					rst.close();
				} catch (SQLException e1) {
				}
			}
		}

		return bean;
	}

	/**
	 * ִ���޸ĵĿͻ�����
	 * 
	 * @param hform
	 *            ҳ���ύ����
	 * @return boolean ִ�гɹ�����true,ʧ�ܷ���false
	 */
	public boolean updateCustomer(GroupCustomerBean hform) {
		// ����ֵ
		boolean returnFlg = false;
		// sql���
		String sql = "update groupcustomer set groupid = '"
				+ hform.getGroupid() + "' , " + " city = '" + hform.getCity()
				+ "' , town = '" + hform.getTown() + "', " + "grouptype = '"
				+ hform.getGrouptype() + "', groupname = '"
				+ hform.getGroupname() + "', groupaddress = '"
				+ hform.getGroupaddr() + "', x = '" + hform.getX() + "', y = '"
				+ hform.getY() + "', operationtype = '"
				+ hform.getOperationtype() + "', customermanager = '"
				+ hform.getCustomermanager() + "', tel = '" + hform.getTel()
				+ "', grouptel = '" + hform.getGrouptel() + "', regionid = '"
				+ hform.getRegionid() + "' where id = '" + hform.getId() + "'";
		UpdateUtil up;
		try {
			up = new UpdateUtil();
			up.executeUpdate(sql);
			returnFlg = true;
		} catch (Exception e) {
			logger.warn("�޸Ŀͻ����ϳ���:" + e.getMessage());
			e.printStackTrace();
		}

		return returnFlg;
	}

	/**
	 * ɾ���ͻ�����
	 * 
	 * @param hform
	 *            ҳ���ύ����
	 * @return boolean ɾ���ɹ�����true, ʧ�ܷ���false
	 */
	public boolean delCustomer(String id) {
		// ����ֵ
		boolean returnFlg = false;
		String sql = "delete from  groupcustomer " + " where id = '" + id + "'";
		UpdateUtil up;
		try {
			up = new UpdateUtil();
			up.executeUpdate(sql);
			returnFlg = true;
		} catch (Exception e) {
			logger.warn("ɾ�������ͻ����ϳ���:" + e.getMessage());
		}

		return returnFlg;
	}

	/**
	 * ����ɾ���ͻ�����
	 * 
	 * @param id
	 *            ��Ҫɾ���Ŀͻ���id
	 * @return boolean ɾ���ɹ�����true, ʧ�ܷ���false
	 */
	public boolean delCustomers(String[] id) {
		// ����ֵ
		boolean returnFlg = false;
		// sql��
		String sql = "delete from  groupcustomer " + " where id in (";
		for (int i = 0; i < id.length; i++) {
			sql += "'" + id[i] + "',";
		}
		sql = sql.substring(0, sql.length() - 1) + ")";
		UpdateUtil up;
		try {
			up = new UpdateUtil();
			up.executeUpdate(sql);
			returnFlg = true;
		} catch (Exception e) {
			logger.warn("����ɾ���ͻ����ϳ���:" + e.getMessage());
		}

		return returnFlg;
	}

}
