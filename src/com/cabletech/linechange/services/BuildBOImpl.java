package com.cabletech.linechange.services;

import com.cabletech.linechange.domainobjects.ChangeBuild;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import java.util.List;
import com.cabletech.linechange.dao.ChangeBuildDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linechange.bean.ChangeBuildBean;
import org.apache.log4j.Logger;
import com.cabletech.linechange.dao.ChangeInfoDAOImpl;

public class BuildBOImpl implements BuildBO {
    private ChangeBuildDAOImpl dao;

    private ChangeInfoDAOImpl changedao;

    private Logger logger = Logger.getLogger("BuildBOImpl");

    public BuildBOImpl() {
        dao = new ChangeBuildDAOImpl();
        changedao = new ChangeInfoDAOImpl();
    }

    public ChangeInfo getChangeInfo(String id) {
        return changedao.getChange(id);
    }

    public void addBuildInfo(ChangeBuild buildinfo, ChangeInfo changeinfo) {
        changeinfo.setStep("E");

        changeinfo.setBuildunit(buildinfo.getBuildunit());
        changeinfo.setStarttime(buildinfo.getStarttime());

        dao.insertBuild(buildinfo, changeinfo);
    }

    public void updateBuildInfo(ChangeBuild buildinfo, ChangeInfo changeinfo) {
        changeinfo.setStep("E");

        changeinfo.setBuildunit(buildinfo.getBuildunit());
        changeinfo.setStarttime(buildinfo.getStarttime());

        dao.updateBuild(buildinfo, changeinfo);
    }

    public ChangeBuild getBuildInfoByChangeId(String changeid) {
        String hql = "from com.cabletech.linechange.domainobjects.ChangeBuild changebuild where changebuild.changeid=?";
        return dao.getBuildInfoByChangeId(hql, changeid);

    }

    public ChangeBuild getBuildInfo(String id) {
        return dao.getBuildInfo(id);
    }

    public List getChangeInfo(UserInfo user, ChangeBuildBean bean) {
        String hql = "select changeinfo.id,changeinfo.changename,changeinfo.changepro,changeinfo.budget,changeinfo.changeaddr,lineclass.name lineclass,decode(changeinfo.step,'A','待审定','B1','通过监理审定','B2','通过移动审定','C','施工准备','D','开始施工','E','施工完毕','F','验收完毕','G','已经归档') state,to_char(changeinfo.applytime,'YYYY-MM-DD') applytime,changeinfo.step,changeinfo.approveresult from ChangeInfo  changeinfo,lineclassdic lineclass where lineclass.code(+)=changeinfo.lineclass  ";
        hql += " and changeinfo.step='D' ";
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
        List list = dao.getBuildInfoList(hql);
        return list;

    }

    public List getBuildInfoList(UserInfo user, ChangeBuildBean bean) {
        String hql = "select change.changename,change.changepro,change.step,build.id,to_char(build.starttime,'YYYY-MM-DD') starttime,to_char(build.endtime,'YYYY-MM-DD') endtime,build.buildunit,to_char(build.fillindate,'YYYY-MM-DD') fillindate,decode(change.step,'A','待审定','B1','通过监理审定','B2','通过移动审定','C','施工准备','D','开始施工','E','施工完毕','F','验收完毕','G','已经归档') state from changebuild build,changeinfo change,lineclassdic lineclass where lineclass.code(+)=change.lineclass and change.id=build.changeid ";
        // hql+=" and change.step in ('E','F','G') and build.state is null";
        hql += " and change.step='E' and build.state is null";
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
        hql += (bean.getBuildunit() == null || bean.getBuildunit().equals("")) ? ""
                : "and change.buildunit='" + bean.getBuildunit() + "'";
        if (bean.getStarttime() != null && !bean.getStarttime().equals("")) {
            hql += " and build.fillindate >= TO_DATE('" + bean.getStarttime() + "','YYYY-MM-DD')";
        }
        if (bean.getEndtime() != null && !bean.getEndtime().equals("")) {
            hql += " and build.fillindate <= TO_DATE('" + bean.getEndtime() + "','YYYY-MM-DD')";
        }
        hql += " order by build.fillindate desc,change.id desc";
        logger.info("hql" + hql);
        List list = dao.getBuildInfoList(hql);
        // System.out.println("list"+list.size());
        return list;
    }
}
