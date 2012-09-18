package com.cabletech.linechange.services;

import java.util.List;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import com.cabletech.linechange.dao.ChangeInfoDao;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linechange.dao.ChangeInfoDAOImpl;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import com.cabletech.uploadfile.DelUploadFile;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.linechange.bean.ChangeInfoBean;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * <p>
 * Company: Cable tech
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class ChangeApplyBOImpl implements ChangeApplyBO {
    private ChangeInfoDao dao;

    private Logger logger = Logger.getLogger("ChangeApplyBOImpl");

    public ChangeApplyBOImpl() {
        dao = new ChangeInfoDAOImpl();
    }

    public ChangeInfo getChangeInfo(String changeid) {
        return dao.getChange(changeid);
    }

    public ChangeInfo saveChangeInfo(ChangeInfo changeinfo) {
        dao.insertChange(changeinfo);
        return changeinfo;
    }

    public boolean removeChangeInfo(String changeid) {
        ChangeInfo changeinfo = dao.getChange(changeid);
        if ("A".equals(changeinfo.getStep())) {
            dao.removeChange(changeinfo);
            return true;
        } else {
            return false;
        }
    }

    public List getChangeInfo(UserInfo user, ChangeInfoBean bean) {
        String hql = "select changeinfo.id,changeinfo.changename,changeinfo.changepro,changeinfo.changeaddr,lineclass.name lineclass,decode(changeinfo.step,'A','待审定','B1','通过监理审定','B2','通过移动审定','C','施工准备','D','开始施工','E','施工完毕','F','验收完毕','G','已经归档') state,to_char(changeinfo.applytime,'YYYY-MM-DD') applytime,changeinfo.step,changeinfo.approveresult from ChangeInfo  changeinfo,lineclassdic lineclass where lineclass.code(+)=changeinfo.lineclass  ";
        // 市移动
        if (user.getDeptype().equals("1") && !user.getRegionID().substring(2, 6).equals("0000"))
            hql += " and changeinfo.regionid in (" + user.getRegionID() + ")";
        // 市代维
        if (user.getDeptype().equals("2") && !user.getRegionID().substring(2, 6).equals("0000")
                && "0".equals(user.getIsSuperviseUnit()))
            hql += " and changeinfo.applyunit ='" + user.getDeptID() + "'";
        // 省代维
        if (user.getDeptype().equals("2") && user.getRegionID().substring(2, 6).equals("0000"))
            hql += " and changeinfo.applyunit in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
                    + user.getDeptID() + "')";
        // 市监理公司
        if (user.getDeptype().equals("2") && !user.getRegionID().substring(2, 6).equals("0000")
                && "1".equals(user.getIsSuperviseUnit())) {
            hql += " and changeinfo.supervise_unit_id='" + user.getDeptID() + "'";
        }

        // 以上为按不同用户查询条件
        if (bean.getOp() != null && "survey".equals(bean.getOp())) {
            if ("2".equals(user.getDeptype())) {
                hql += " and changeinfo.step='A'";
            }
            if ("1".equals(user.getDeptype())) {
                hql += "and changeinfo.step='B1'";
            }
        } else {
            hql += "and changeinfo.step='A'";
        }
        hql += bean.getChangename() == null ? "" : " and changeinfo.changename like '%"
                + bean.getChangename() + "%' ";
        hql += bean.getChangepro() == null ? "" : " and changeinfo.changepro like '%"
                + bean.getChangepro() + "%' ";
        hql += bean.getApproveresult() == null ? "" : "and changeinfo.approveresult like '%"
                + bean.getApproveresult() + "%' ";
        hql += (bean.getLineclass() == null || bean.getLineclass().equals("")) ? ""
                : "and changeinfo.lineclass like '%" + bean.getLineclass() + "%' ";
        hql += (bean.getStep() == null || bean.getStep().equals("")) ? "" : "and changeinfo.step='"
                + bean.getStep() + "'";
        if (bean.getBegintime() != null && !bean.getBegintime().equals("")) {
            hql += " and changeinfo.applytime >= TO_DATE('" + bean.getBegintime()
                    + "','YYYY-MM-DD')";
        }
        if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
            hql += " and changeinfo.applytime <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
        }
        hql += " order by changeinfo.applytime desc,changeinfo.id desc";
        logger.info("sql" + hql);
        // hql= "from ChangeInfo changeinfo where changeinfo.regionid in
        // ("+user.getRegionID()+")";
        return dao.getChangeInfo(hql);
    }

    public ChangeInfo updateChangeInfo(ChangeInfo changeinfo, String[] delfileid) {
        DelUploadFile delfile = new DelUploadFile();
        try {
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            if (delfileid != null) {
                for (int i = 0; i < delfileid.length; i++) {
                    String sql2 = "delete from filepathinfo where fileid='" + delfileid[i] + "'";
                    // 删除文件
                    delfile.delFile(delfileid[i]);

                    exec.executeUpdate(sql2);

                }
            }
            exec.commit();
            exec.setAutoCommitTrue();
        } catch (Exception ex) {
            logger.error("del File Exception" + ex.getMessage());
        }
        dao.updateChange(changeinfo);
        return changeinfo;
    }

}
