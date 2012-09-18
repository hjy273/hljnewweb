package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.linepatrol.remedy.beans.RemedyItemBean;
import com.cabletech.linepatrol.remedy.domain.RemedyItem;

public class RemedyItemDao extends RemedyBaseDao {
    private static Logger logger = Logger.getLogger(RemedyItemDao.class.getName());

    /**
     * ��ѯ����
     * 
     * @param user
     * @return
     */
    public List getRegions(UserInfo user) {
        List list = new ArrayList();
        QueryUtil util = null;
        String sql = "select r.regionname,r.regionid from region r where r.state is null and r.regionid='"
                + user.getRegionid() + "'";
        // String sql = "select r.regionname,r.regionid from region r where
        // r.state is null";
        try {
            util = new QueryUtil();
            logger.info("��ѯ����" + sql);
            list = util.queryBeans(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ��ѯ����Name
     * 
     * @param id
     * @return
     */
    public String getRegionNameById(String regionId) {
        String regionName = "";
        QueryUtil util = null;
        String sql = "select r.regionname from region r where r.regionid='" + regionId + "'";
        try {
            util = new QueryUtil();
            logger.info("��ѯ����" + sql);
            List list = util.queryBeans(sql);
            if (list != null && list.size() > 0) {
                BasicDynaBean bean = (BasicDynaBean) list.get(0);
                regionName = (String) bean.get("regionname");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return regionName;
    }

    /**
     * ��������id��������Ŀ���Ʋ�ѯ����id
     * 
     * @param regionID
     * @param itemName
     * @return
     */
    public List getItemsByRIDAndIName(String regionID, String itemName) {
        List list = new ArrayList();
        QueryUtil util = null;
        String sql = "select * from linepatrol_remedyitem lr where lr.state=1 and lr.itemname='"
                + itemName + "' and lr.regionid='" + regionID + "'";
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
     * �޸�ʱ��������id��������Ŀ���Ʋ�ѯ����id
     * 
     * @param regionID
     * @param itemName
     * @return
     */
    public List getItemsByBean(RemedyItemBean bean) {
        List list = new ArrayList();
        QueryUtil util = null;
        String sql = "select * from linepatrol_remedyitem lr where lr.state=1 and lr.itemname='"
                + bean.getItemName()
                + "' "
                + " and lr.regionid='"
                + bean.getRegionID()
                + "' and lr.id !='" + bean.getTid() + "'";
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
	 * ����������Ŀ
	 * @param bean
	 * @return
	 */
	public boolean addItem(RemedyItem item){
		boolean flag = true;
		UpdateUtil update = null;
		try {
			String sql = "insert into LINEPATROL_REMEDYITEM(id,itemname,remark,state,regionid) values("+item.getTid()+",'"+item.getItemName()+"','"+
			item.getRemark()+"',"+1+",'"+item.getRegionID()+"')";
			update = new UpdateUtil();
			logger.info("����������Ŀ��"+sql);
			update.executeUpdate(sql);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

    /**
     * ����������ѯ������Ŀ
     */
    public List getItems(RemedyItemBean bean) {
        List list = new ArrayList();
        QueryUtil util = null;
        StringBuffer sb = new StringBuffer();
        String itemName = bean.getItemName();
        String regionID = bean.getRegionID();
        try {
            sb.append("select lr.id,lr.itemname,lr.regionid,r.regionname, ");
            sb
                    .append("(select count(*) from linepatrol_remedyitem_type lrt where lrt.remedyitemid=lr.id ) typenum");
            sb.append(" from linepatrol_remedyitem lr,region r");
            sb.append(" where lr.state=1 and r.state is null and r.regionid=lr.regionid");
            if (itemName != null && !itemName.trim().equals("")) {
                sb.append(" and lr.itemname like '%" + itemName + "%' ");
            }
            if (regionID != null && !regionID.trim().equals("")) {
                sb.append(" and lr.regionid='" + regionID + "'");
            }
            sb.append(" order by lr.regionid,lr.itemname");

            util = new QueryUtil();
            logger.info("��ѯ������Ŀ��" + sb.toString());
            list = util.queryBeans(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ����id��ѯ������
     * 
     * @param id
     * @return
     */
    public List getItemById(String id) {
        List list = new ArrayList();
        QueryUtil util = null;
        String sql = "select * from linepatrol_remedyitem lr where lr.id='" + id + "'";
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
     * �޸�������Ŀ
     * 
     * @param bean
     * @return
     */
    public boolean editItem(RemedyItem item) {
        boolean flag = true;
        UpdateUtil update = null;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("update  linepatrol_remedyitem ");
            sb.append("set itemname='" + item.getItemName() + "',regionid='" + item.getRegionID()
                    + "',");
            sb.append(" remark='" + item.getRemark() + "' ");
            sb.append(" where id='" + item.getTid() + "'");
            update = new UpdateUtil();
            logger.info("�޸�������Ŀ��" + sb.toString());
            update.executeUpdate(sb.toString());
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * ����idɾ��������Ŀ,ͬʱɾ���������
     * 
     * @param id
     * @return
     */
    public boolean deleteItem(String id) {
        boolean flag = true;
        UpdateUtil update = null;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("update  linepatrol_remedyitem ");
            sb.append("set state='0' ");
            sb.append(" where id='" + id + "'");
            update = new UpdateUtil();
            update.setAutoCommitFalse();
            logger.info("ɾ��������Ŀ��" + sb.toString());
            update.executeUpdate(sb.toString());
            StringBuffer sbtype = new StringBuffer();
            sbtype.append("update  linepatrol_remedyitem_type ");
            sbtype.append("set state='0' ");
            sbtype.append(" where remedyitemid='" + id + "'");
            update.executeUpdate(sbtype.toString());
            update.setAutoCommitTrue();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * �ж��������û��������
     * 
     * @param id
     * @return
     */
    public List getItemByApply(String id) {
        List list = new ArrayList();
        QueryUtil util = null;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("select lrt.remedyitemid ");
            sb
                    .append("from LINEPATROL_REMEDY_ITEM lri,LINEPATROL_REMEDY lr,LINEPATROL_REMEDYITEM_TYPE lrt ");
            sb
                    .append(" where lri.remedyid= lr.id and lri.remedyitemtypeid=lrt.id and lrt.remedyitemid='"
                            + id + "'");
            util = new QueryUtil();
            logger.info("����������ѯ������Ŀ��" + sb.toString());
            list = util.queryBeans(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ִ�и��ݲ�ѯ������ȡ�������б���Ϣ
     * 
     * @param condition
     *            String ��ѯ����
     * @return List �������б���Ϣ
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        String sql = "select distinct t.id,t.itemname,t.remark from LINEPATROL_REMEDYITEM t,region r,contractorinfo c ";
        sql = sql + " where t.regionid=r.regionid and r.regionid=c.regionid and t.state='1' ";
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
