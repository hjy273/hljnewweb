package com.cabletech.linechange.services;

import com.cabletech.linechange.dao.ChangeInfoDao;
import org.apache.log4j.Logger;
import com.cabletech.linechange.dao.ChangeInfoDAOImpl;
import java.util.List;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linechange.bean.ChangeInfoBean;
import org.apache.commons.lang.*;
import com.cabletech.commons.hb.*;
import java.sql.*;

public class PageonholeBOImpl {
    private ChangeInfoDao dao;

    private Logger logger = Logger.getLogger("PageonholeBOImpl");

    public PageonholeBOImpl() {
        dao = new ChangeInfoDAOImpl();
    }

    public List getChangeInfo(ChangeInfoBean changeinfo, UserInfo user) {
        String hql = "select changeinfo.id,changeinfo.changename,changeinfo.changepro,changeinfo.budget,changeinfo.changeaddr,lineclass.name lineclass,decode(changeinfo.step,'A','待审定','B1','通过监理审定','B2','通过移动审定','C','施工准备','D','开始施工','E','施工完毕','F','验收完毕','G','已经归档') state,to_char(changeinfo.applytime,'YYYY-MM-DD') applytime,changeinfo.step,changeinfo.approveresult from ChangeInfo  changeinfo,lineclassdic lineclass where lineclass.code(+)=changeinfo.lineclass  ";
        hql += " and changeinfo.step='F' ";
        // 市移动
        if (user.getDeptype().equals("1") && !user.getRegionID().substring(2, 6).equals("0000")) {
            hql += " and changeinfo.regionid in (" + user.getRegionID() + ")";
        }
        // 市代维
        if (user.getDeptype().equals("2") && !user.getRegionID().substring(2, 6).equals("0000")) {
            hql += " and changeinfo.applyunit ='" + user.getDeptID() + "'";
        }
        // 省代维
        if (user.getDeptype().equals("2") && user.getRegionID().substring(2, 6).equals("0000")) {
            hql += " and changeinfo.applyunit in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
                    + user.getDeptID() + "')";
        }
        logger.info("sql" + hql);
        return dao.getChangeInfo(hql);
    }

    public List getPageonhole(ChangeInfoBean changeinfo, UserInfo user) {
        String hql = "select changeinfo.id,changeinfo.changename,changeinfo.budget,changeinfo.square,lineclass.name lineclass,decode(changeinfo.step,'A','待审定','B1','通过监理审定','B2','通过移动审定','C','施工准备','D','开始施工','E','施工完毕','F','验收完毕','G','已经归档') state,to_char(changeinfo.pageonholedate,'YYYY-MM-DD') pageonholedate,changeinfo.step,changeinfo.ischangedatum from ChangeInfo  changeinfo,lineclassdic lineclass where lineclass.code(+)=changeinfo.lineclass  ";
        hql += " and changeinfo.step in ('G') ";
        // 市移动
        if (user.getDeptype().equals("1") && !user.getRegionID().substring(2, 6).equals("0000")) {
            hql += " and changeinfo.regionid in (" + user.getRegionID() + ")";
        }
        // 市代维
        if (user.getDeptype().equals("2") && !user.getRegionID().substring(2, 6).equals("0000")) {
            hql += " and changeinfo.applyunit ='" + user.getDeptID() + "'";
        }
        // 省代维
        if (user.getDeptype().equals("2") && user.getRegionID().substring(2, 6).equals("0000")) {
            hql += " and changeinfo.applyunit in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
                    + user.getDeptID() + "')";
        }
        // 查询条件
        hql += changeinfo.getChangename() == null ? "" : " and changeinfo.changename like '%"
                + changeinfo.getChangename() + "%' ";
        hql += changeinfo.getChangepro() == null ? "" : " and changeinfo.changepro like '%"
                + changeinfo.getChangepro() + "%' ";
        hql += (changeinfo.getLineclass() == null || changeinfo.getLineclass().equals("")) ? ""
                : "and changeinfo.lineclass like '%" + changeinfo.getLineclass() + "%' ";
        hql += (changeinfo.getStep() == null || changeinfo.getStep().equals("")) ? ""
                : "and changeinfo.step='" + changeinfo.getStep() + "'";
        hql += changeinfo.getChangeaddr() == null ? "" : " and changeinfo.changeaddr like '%"
                + changeinfo.getChangeaddr() + "%' ";

        if (changeinfo.getBegintime() != null && !changeinfo.getBegintime().equals("")) {
            hql += " and changeinfo.pageonholedate >= TO_DATE('" + changeinfo.getBegintime()
                    + "','YYYY-MM-DD')";
        }
        if (changeinfo.getEndtime() != null && !changeinfo.getEndtime().equals("")) {
            hql += " and changeinfo.pageonholedate <= TO_DATE('" + changeinfo.getEndtime()
                    + "','YYYY-MM-DD')";
        }
        hql += " order by changeinfo.step";
        logger.info("sql" + hql);
        return dao.getChangeInfo(hql);
    }

    public ChangeInfo getChangeInfo(String id) {
        return dao.getChange(id);
    }

    public void updatePageonhole(ChangeInfo changeinfo) {
        dao.updateChange(changeinfo);
    }

    public List getsquare() {
        List slist = null;
        String sql = "SELECT   c.changename, c.changeaddr, c.changepro, c.ID, l.NAME lineclass,"
                + "          c.square, TO_CHAR (c.pageonholedate, 'yyyy-mm-dd') pageonholedate,"
                + "          c.squared, NVL(TO_CHAR (c.sqdate, 'yyyy-mm-dd'),'------') sqdate"
                + " FROM CHANGEINFO c, LINECLASSDIC l"
                + " WHERE c.lineclass = l.code(+) AND c.square != 0  and c.square > c.squared"
                + " ORDER BY c.pageonholedate desc,c.id desc";
        logger.info("sql " + sql);
        try {
            QueryUtil qu = new QueryUtil();
            slist = qu.queryBeans(sql);
            return slist;
        } catch (Exception ex) {
            return null;
        }
    }

    public ChangeInfoBean getOnesquare(String id) {
        ChangeInfoBean bean = new ChangeInfoBean();
        ResultSet rst = null;
        String sql = "SELECT   c.*, l.NAME lineclassname,"
                + "           TO_CHAR (c.pageonholedate, 'yyyy-mm-dd') pageonholedate,"
                + "           TO_CHAR (c.sqdate, 'yyyy-mm-dd') sqdate"
                + " FROM CHANGEINFO c, LINECLASSDIC l" + " WHERE c.ID ='" + id
                + "' and c.lineclass=l.code";
        logger.info("sql " + sql);
        try {
            QueryUtil qu = new QueryUtil();
            rst = qu.executeQuery(sql);
            try {
                if (rst != null && rst.next()) {
                    bean.setChangename(rst.getString("changename"));
                    bean.setChangeaddr(rst.getString("changeaddr"));
                    bean.setChangepro(rst.getString("changepro"));
                    bean.setLineclass(rst.getString("lineclassname"));
                    bean.setInvolvedSystem(rst.getString("involved_system"));
                    bean.setChangelength(Float
                            .valueOf(String.valueOf(rst.getFloat("changelength"))));
                    bean.setStarttime(rst.getString("starttime"));
                    bean.setPlantime(Float.valueOf(String.valueOf(rst.getFloat("plantime"))));
                    bean.setBudget(Float.valueOf(String.valueOf(rst.getFloat("budget"))));
                    bean.setCost(Float.valueOf(String.valueOf(rst.getFloat("cost"))));
                    bean.setSquare(Float.valueOf(String.valueOf(rst.getFloat("square"))));
                    bean.setPageonholedate(rst.getString("pageonholedate"));
                    bean.setSquared(String.valueOf(rst.getFloat("squared")));
                    bean.setSqdate(rst.getString("sqdate"));
                    bean.setId(rst.getString("id"));
                    rst.close();
                    return bean;
                }
            } catch (Exception e) {
                rst.close();
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    public boolean saveSquare(ChangeInfoBean bean) {
        String sql = "update changeinfo set sqdate = sysdate,squared = squared + "
                + (Float.valueOf(bean.getSquared())).floatValue() + " where id = '" + bean.getId()
                + "'";
        logger.info("sql " + sql);
        try {
            UpdateUtil up = new UpdateUtil();
            up.executeUpdate(sql);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
