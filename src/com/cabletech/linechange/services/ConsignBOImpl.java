package com.cabletech.linechange.services;

import java.util.*;

import org.apache.log4j.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.linechange.bean.*;
import com.cabletech.linechange.dao.*;
import com.cabletech.linechange.domainobjects.*;

public class ConsignBOImpl implements ConsignBO {
    private ChangeInfoDao dao;

    private Logger logger = Logger.getLogger("ConsignBOImpl");

    public ConsignBOImpl() {
        dao = new ChangeInfoDAOImpl();
    }

    public void addOrUpdateConsign(ChangeInfo changeinfo) {
        dao.updateChange(changeinfo);
    }

    public List getChangeInfo() {
        String hql = "select changeinfo.id,changeinfo.changename,changeinfo.changepro,changeinfo.budget,changeinfo.changeaddr,lineclass.name lineclass,decode(changeinfo.step,'A','����','B1','ͨ��������','B2','ͨ���ƶ���','C','ʩ��׼��','D','��ʼʩ��','E','ʩ�����','F','�������','G','�Ѿ��鵵') state,to_char(changeinfo.applytime,'YYYY-MM-DD') applytime,changeinfo.step,changeinfo.approveresult from ChangeInfo  changeinfo,lineclassdic lineclass where lineclass.code(+)=changeinfo.lineclass  ";
        hql += " and (changeinfo.step = 'C' or (changeinfo.step='B2' and changeinfo.budget <= 10)) ";
        hql += " order by changeinfo.applytime desc,changeinfo.id desc";
        logger.info("hql" + hql);
        List list = dao.getChangeInfo(hql);
        return list;

    }

    public ChangeInfo getChangeInfo(String changeid) {
        return dao.getChange(changeid);
    }

    public List getChangeInfo(UserInfo user, ChangeInfoBean changeinfo) {
        return null;
    }

    public List getConsignInfo(UserInfo user, ChangeInfoBean changeinfo) {
        String hql = "select changeinfo.id,changeinfo.changename,changeinfo.changepro,changeinfo.budget,changeinfo.changeaddr,lineclass.name lineclass,decode(changeinfo.step,'A','����','B1','ͨ��������','B2','ͨ���ƶ���','C','ʩ��׼��','D','��ʼʩ��','E','ʩ�����','F','�������','G','�Ѿ��鵵') state,to_char(changeinfo.entrusttime,'YYYY-MM-DD') entrusttime,changeinfo.step,changeinfo.approveresult from ChangeInfo  changeinfo,lineclassdic lineclass where lineclass.code(+)=changeinfo.lineclass  ";
        // hql += " and changeinfo.step in ('D','E','F','G') ";
        hql += " and changeinfo.step='D' ";
        // ���ƶ�
        if (user.getDeptype().equals("1") && !user.getRegionID().substring(2, 6).equals("0000")) {
            hql += " and changeinfo.regionid in (" + user.getRegionID() + ")";
        }
        // �д�ά
        if (user.getDeptype().equals("2") && !user.getRegionID().substring(2, 6).equals("0000")) {
            hql += " and changeinfo.applyunit ='" + user.getDeptID() + "'";
        }
        // ʡ��ά
        if (user.getDeptype().equals("2") && user.getRegionID().substring(2, 6).equals("0000")) {
            hql += " and changeinfo.applyunit in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
                    + user.getDeptID() + "')";
        }
        // ��ѯ����
        hql += changeinfo.getChangename() == null ? "" : " and changeinfo.changename like '%"
                + changeinfo.getChangename() + "%' ";
        hql += changeinfo.getChangepro() == null ? "" : " and changeinfo.changepro like '%"
                + changeinfo.getChangepro() + "%' ";
        hql += (changeinfo.getLineclass() == null || changeinfo.getLineclass().equals("")) ? ""
                : "and changeinfo.lineclass like '%" + changeinfo.getLineclass() + "%' ";
        // hql += (changeinfo.getStep() == null ||
        // changeinfo.getStep().equals("")) ? ""
        // : "and changeinfo.step='" + changeinfo.getStep() + "'";
        hql += changeinfo.getChangeaddr() == null ? "" : " and changeinfo.changeaddr like '%"
                + changeinfo.getChangeaddr() + "%' ";
        hql += (changeinfo.getEntrustunit() == null || changeinfo.getEntrustunit().equals("")) ? ""
                : " and changeinfo.entrustunit like '%" + changeinfo.getEntrustunit() + "%' ";
        if (changeinfo.getBegintime() != null && !changeinfo.getBegintime().equals("")) {
            hql += " and changeinfo.entrusttime >= TO_DATE('" + changeinfo.getBegintime()
                    + "','YYYY-MM-DD')";
        }
        if (changeinfo.getEndtime() != null && !changeinfo.getEndtime().equals("")) {
            hql += " and changeinfo.entrusttime <= TO_DATE('" + changeinfo.getEndtime()
                    + "','YYYY-MM-DD')";
        }
        hql += " order by changeinfo.entrusttime desc,changeinfo.id desc";
        logger.info("sql" + hql);

        return dao.getChangeInfo(hql);

    }
}
