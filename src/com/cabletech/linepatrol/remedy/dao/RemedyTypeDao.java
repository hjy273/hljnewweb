package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.linepatrol.remedy.beans.RemedyTypeBean;
import com.cabletech.linepatrol.remedy.domain.RemedyType;

public class RemedyTypeDao extends RemedyBaseDao {
    private static Logger logger = Logger.getLogger(RemedyTypeDao.class.getName());

    /**
     * �����û�regionid�õ�������
     * 
     * @param user
     * @return
     */
    public List getItemsByRegionID(UserInfo user) {
        List list = new ArrayList();
        QueryUtil util = null;
        String sql = "select lr.id,lr.itemname from linepatrol_remedyitem lr where lr.state=1 "
                + "and lr.regionid='" + user.getRegionid() + "' order by lr.itemname";
        try {
            util = new QueryUtil();
            logger.info("��ѯ������Ŀ��" + sql);
            list = util.queryBeans(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ����������ѯ�������
     * 
     * @param itemID
     *            ������ĿID
     * @param typeName
     *            �����������
     * @return
     */
    public List getTypessByIIDAndTName(int itemID, String typeName) {
        List list = new ArrayList();
        QueryUtil util = null;
        String sql = " select lt.id"
                + " from linepatrol_remedyitem_type lt where lt.state=1 and lt.typename='"
                + typeName + "' and lt.remedyitemid=" + itemID + "";
        try {
            util = new QueryUtil();
            logger.info("��ѯ�������" + sql);
            list = util.queryBeans(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    /**
     * �޸�ʱ��������id������������Ʋ�ѯ����id
     * 
     * @return
     */
    public List getTypeByBean(RemedyTypeBean bean) {
        List list = new ArrayList();
        QueryUtil util = null;
        String sql = "select lt.id from linepatrol_remedyitem_type lt where lt.state=1 and lt.typename='"
                + bean.getTypeName()
                + "' "
                + " and lt.remedyitemid="
                + bean.getItemID()
                + " and lt.id !='" + bean.getTid() + "'";
        try {
            util = new QueryUtil();
            logger.info("��ѯ�������" + sql);
            list = util.queryBeans(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
  

    /**
     * ����������ѯ�������
     */
    public List getTypes(RemedyTypeBean bean) {
        List list = new ArrayList();
        QueryUtil util = null;
        StringBuffer sb = new StringBuffer();
        String typeName = bean.getTypeName();
        int itemID = bean.getItemID();
        try {
            sb.append("select lt.id,lt.typename,lt.unit,to_char(lt.price) price,lr.itemname ");
            sb.append(" from linepatrol_remedyitem_type lt,linepatrol_remedyitem lr ");
            sb.append(" where lt.remedyitemid = lr.id and lt.state=1 ");
            if (typeName != null && !typeName.trim().equals("")) {
                sb.append(" and lt.typename like '%" + typeName + "%' ");
            }
            if (itemID != -1) {
                sb.append(" and lt.remedyitemid='" + itemID + "'");
            }
            sb.append(" order by lt.remedyitemid,lt.typename");

            util = new QueryUtil();
            logger.info("��ѯ�������" + sb.toString());
            list = util.queryBeans(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

	/**
	 * �����������
	 * @param bean
	 * @return
	 */
	public boolean addType(RemedyType type){
		boolean flag = true;
		UpdateUtil update = null;
		try {
			String sql = "insert into linepatrol_remedyitem_type(id,remedyitemid,typename,price,unit,remark,state) values("+type.getTid()+","+type.getItemID()+",'"+
			type.getTypeName()+"',"+type.getPrice()+",'"+type.getUnit()+"','"+type.getRemark()+"',"+1+")";
			update = new UpdateUtil();
			logger.info("�����������"+sql);
			update.executeUpdate(sql);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}


    /**
     * ����������id��ѯ�������
     */

    public List getTypeByItemID(String id) {
        List list = new ArrayList();
        QueryUtil util = null;
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("select lt.id,lt.typename,lt.unit,to_char(lt.price) price,lr.itemname ");
            sb.append(" from linepatrol_remedyitem_type lt,linepatrol_remedyitem lr ");
            sb.append(" where lt.remedyitemid = lr.id and lt.state=1 ");
            sb.append(" and lt.remedyitemid='" + id + "'");
            sb.append(" order by lt.typename");
            util = new QueryUtil();
            logger.info("��ѯ�������" + sb.toString());
            list = util.queryBeans(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ����id��ѯ�������
     * 
     * @param id
     * @return
     */
    public List getTypeById(String id) {
        List list = new ArrayList();
        QueryUtil util = null;
        String sql = "select lt.id,to_char(lt.price) price,lt.typename,lt.unit,lt.remark,lt.remedyitemid"
                + " from linepatrol_remedyitem_type lt where lt.id='" + id + "'";
        try {
            util = new QueryUtil();
            logger.info("��ѯ�������" + sql);
            list = util.queryBeans(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * �޸�������Ŀ
     * 
     * @param bean
     * @return
     */
    public boolean editType(RemedyType type) {
        boolean flag = true;
        UpdateUtil update = null;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("update  linepatrol_remedyitem_type ");
            sb.append("set typename='" + type.getTypeName() + "',remedyitemid=" + type.getItemID()
                    + ",");
            sb.append(" remark='" + type.getRemark() + "',price=" + type.getPrice() + ",unit='"
                    + type.getUnit() + "' ");
            sb.append(" where id='" + type.getTid() + "'");
            update = new UpdateUtil();
            logger.info("�޸��������" + sb.toString());
            update.executeUpdate(sb.toString());
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * ����idɾ���������
     * 
     * @param id
     * @return
     */
    public boolean deleteType(String id) {
        boolean flag = true;
        UpdateUtil update = null;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("update  linepatrol_remedyitem_type ");
            sb.append("set state='0' ");
            sb.append(" where id='" + id + "'");
            update = new UpdateUtil();
            logger.info("ɾ���������" + sb.toString());
            update.executeUpdate(sb.toString());
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * ִ�и��ݲ�ѯ������ȡ�����������б���Ϣ
     * 
     * @param condition
     *            String ��ѯ����
     * @return List �����������б���Ϣ
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        String sql = "select distinct t.id,t.remedyitemid,t.typename,to_char(t.price) as price,t.unit ";
        sql = sql
                + " from LINEPATROL_REMEDYITEM_TYPE t,LINEPATROL_REMEDYITEM tr,region r,contractorinfo c ";
        sql = sql
                + " where t.remedyitemid=tr.id and tr.regionid=r.regionid and r.regionid=c.regionid and t.state='1' ";
        sql = sql + condition;
        try {
            logger.info("Execute sql:" + sql);
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

}
