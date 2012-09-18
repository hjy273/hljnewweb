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
     * 根据用户regionid得到修缮项
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
            logger.info("查询修缮项目：" + sql);
            list = util.queryBeans(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据条件查询修缮类别
     * 
     * @param itemID
     *            修缮项目ID
     * @param typeName
     *            修缮类别名称
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
            logger.info("查询修缮类别：" + sql);
            list = util.queryBeans(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    /**
     * 修改时根据区域id与修缮类别名称查询修缮id
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
            logger.info("查询修缮类别：" + sql);
            list = util.queryBeans(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
  

    /**
     * 根据条件查询修缮类别
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
            logger.info("查询修缮类别：" + sb.toString());
            list = util.queryBeans(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

	/**
	 * 保存修缮类别
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
			logger.info("增加修缮类别："+sql);
			update.executeUpdate(sql);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}


    /**
     * 根据修缮项id查询修缮类别
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
            logger.info("查询修缮类别：" + sb.toString());
            list = util.queryBeans(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据id查询修缮类别
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
            logger.info("查询修缮类别：" + sql);
            list = util.queryBeans(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 修改修缮项目
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
            logger.info("修改修缮类别：" + sb.toString());
            update.executeUpdate(sb.toString());
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据id删除修缮类别
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
            logger.info("删除修缮类别：" + sb.toString());
            update.executeUpdate(sb.toString());
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 执行根据查询条件获取定额项类型列表信息
     * 
     * @param condition
     *            String 查询条件
     * @return List 定额项类型列表信息
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
