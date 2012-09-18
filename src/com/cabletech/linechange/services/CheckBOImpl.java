package com.cabletech.linechange.services;

import java.util.*;

import org.apache.log4j.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linechange.bean.*;
import com.cabletech.linechange.dao.*;
import com.cabletech.linechange.domainobjects.*;

public class CheckBOImpl implements CheckBO {
    private Logger logger = Logger.getLogger("CheckBOImpl");

    private ChangeCheckDAOImpl dao;

    private ChangeInfoDAOImpl changedao;

    private ChangeBuildDAOImpl builddao;

    public CheckBOImpl() {
        dao = new ChangeCheckDAOImpl();
        changedao = new ChangeInfoDAOImpl();
        builddao = new ChangeBuildDAOImpl();
    }

    public ChangeInfo getChangeInfo(String id) {
        return changedao.getChange(id);
    }

    public void addCheckInfo(ChangeCheck checkinfo, ChangeInfo changeinfo) {

        if (checkinfo.getCheckresult().equals("通过验收")) {
            changeinfo.setStep("F");
        } else {
            // checkinfo.setId(checkinfo.getId()+"M");
            checkinfo.setState("false");
            builddao.blankOut(changeinfo.getId());
            changeinfo.setStep("D");
        }
        dao.insertCheck(checkinfo, changeinfo);
    }

    public void updateCheckInfo(ChangeCheck checkinfo, ChangeInfo changeinfo) {
        // changeinfo.setStep("E");
        if (checkinfo.getCheckresult().equals("通过验收")) {
            changeinfo.setStep("F");
        } else {
            // checkinfo.setId(checkinfo.getId()+"M");
            checkinfo.setState("false");
            builddao.blankOut(changeinfo.getId());
            changeinfo.setStep("D");
        }
        dao.updateCheck(checkinfo, changeinfo);
    }

    public ChangeCheck getCheckInfo(String id) {
        return dao.getCheckInfo(id);
    }

    public List getChangeInfo(UserInfo user, ChangeCheckBean bean) {
        String hql = "select changeinfo.id,changeinfo.changename,changeinfo.changepro,changeinfo.budget,changeinfo.changeaddr,lineclass.name lineclass,decode(changeinfo.step,'A','待审定','B1','通过监理审定','B2','通过移动审定','C','施工准备','D','开始施工','E','施工完毕','F','验收完毕','G','已经归档') state,to_char(changeinfo.applytime,'YYYY-MM-DD') applytime,changeinfo.step,changeinfo.approveresult from ChangeInfo  changeinfo,lineclassdic lineclass where lineclass.code(+)=changeinfo.lineclass  ";
        hql += " and changeinfo.step='E' ";
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
        hql += " order by changeinfo.applytime desc,changeinfo.id desc";
        logger.info("hql" + hql);
        List list = dao.getCheckInfoList(hql);
        return list;

    }

    public List getCheckInfoList(UserInfo user, ChangeCheckBean bean) {
        String hql = "SELECT CHANGE.changename, CHANGE.step, c.id,TO_CHAR (c.checkdate, 'YYYY-MM-DD') checkdate, c.checkperson, c.checkresult,TO_CHAR (c.fillintime, 'YYYY-MM-DD') fillintime,decode(change.step,'A','待审定','B1','通过监理审定','B2','通过移动审定','C','施工准备','D','开始施工','E','施工完毕','F','验收完毕','G','已经归档') state FROM changecheck c, changeinfo CHANGE WHERE CHANGE.ID = c.changeid";
        hql += " and change.step in('F','G') and c.state is null ";
        // 市移动
        if (user.getDeptype().equals("1") && !user.getRegionID().substring(2, 6).equals("0000")) {
            hql += " and change.regionid in (" + user.getRegionID() + ")";
        }
        // 市代维
        if (user.getDeptype().equals("2") && !user.getRegionID().substring(2, 6).equals("0000")) {
            hql += " and change.applyunit ='" + user.getDeptID() + "'";
        }
        // 省代维
        if (user.getDeptype().equals("2") && user.getRegionID().substring(2, 6).equals("0000")) {
            hql += " and change.applyunit in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
                    + user.getDeptID() + "')";
        }
        hql += bean.getChangename() == null ? "" : " and change.changename like '%"
                + bean.getChangename() + "%' ";
        hql += bean.getChangepro() == null ? "" : " and change.changepro like '%"
                + bean.getChangepro() + "%' ";
        hql += (bean.getLineclass() == null || bean.getLineclass().equals("")) ? ""
                : "and change.lineclass like '%" + bean.getLineclass() + "%' ";
        // hql += (bean.getStep() == null || bean.getStep().equals(""))? "":
        // "and change.step='"+bean.getStep()+"'";
        hql += bean.getChangeaddr() == null ? "" : " and change.changeaddr like '%"
                + bean.getChangeaddr() + "%' ";

        if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
            hql += " and c.checkdate >= TO_DATE('" + bean.getBegintime() + "','YYYY-MM-DD')";
        }
        if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
            hql += " and c.checkdate <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
        }
        // hql +=" order by change.step";
        hql += " order by c.checkdate desc,change.id desc";
        logger.info("hql" + hql);
        List list = dao.getCheckInfoList(hql);
        // System.out.println( "list" + list.size() );
        return list;
    }

    /**
     * getChCheckInfoByChangeId
     * 
     * @param changeid
     *            String
     * @return ChangeInfo
     */
    public ChangeCheck getChCheckInfoByChangeId(String changeid) {
        String hql = "from com.cabletech.linechange.domainobjects.ChangeCheck changecheck where changecheck.changeid=?";
        return dao.getChCheckInfoByChangeId(hql, changeid);

    }

    public List getChangeCheckHistoryList(String changeId, String showType) {
        String hql = "select checkinfo.checkperson,checkinfo.checkresult,checkinfo.checkremark,checkinfo.checkdatum,to_char(checkinfo.checkdate,'YYYY-MM-DD hh24:mi:ss') check_date from changecheck checkinfo where checkinfo.changeid='"
                + changeId + "'";
        if (!"all".equals(showType)) {
            hql += " and checkinfo.state='false'";
        }
        hql += " order by checkinfo.id";
        try {
            QueryUtil query = new QueryUtil();
            return query.queryBeans(hql);
        } catch (Exception ex) {
            logger.error("查看详细信息时异常：" + ex.getMessage());
        }
        return null;
    }
}
