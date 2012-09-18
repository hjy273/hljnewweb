package com.cabletech.linechange.services;

import java.util.*;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linechange.bean.*;
import com.cabletech.linechange.dao.*;
import com.cabletech.linechange.domainobjects.*;

public class ChangeSurveyBOImpl implements ChangeSurveyBO {
    private ChangeSurveyDao dao;

    private Logger logger = Logger.getLogger("ChangeSurveyBOImpl");

    public ChangeSurveyBOImpl() {
        dao = new ChangeSurveyDAOImpl();
    }

    public void saveChangeSurvey(ChangeSurvey surveyinfo, ChangeInfo changeinfo, String deptType) {
        if (surveyinfo.getApproveresult().trim().equals("通过审定")) {
            if ("2".equals(deptType)) {
                changeinfo.setStep("B1");
            }
            if ("1".equals(deptType)) {
                changeinfo.setStep("B2");
            }
            if (surveyinfo.getBudget().floatValue() < 10.0f && "1".equals(deptType)) {
                changeinfo.setStep("C");
            }
        } else {
            // surveyinfo.setId(surveyinfo.getId()+"M");
            surveyinfo.setState("false");
            if ("2".equals(deptType)) {
                changeinfo.setStep("A");
            }
            if ("1".equals(deptType)) {
                changeinfo.setStep("B1");
            }
            // surveyinfo.setId(surveyinfo.getId()+);
        }
        changeinfo.setSaddr(surveyinfo.getSaddr());
        changeinfo.setSexpense(surveyinfo.getSexpense());
        changeinfo.setSgrade(surveyinfo.getSgrade());
        changeinfo.setSname(surveyinfo.getSname());
        changeinfo.setSperson(surveyinfo.getSperson());
        changeinfo.setSphone(surveyinfo.getSphone());
        changeinfo.setBudget(surveyinfo.getBudget());
        changeinfo.setApproveresult(surveyinfo.getApproveresult());
        dao.saveChangeSurvey(surveyinfo, changeinfo);
    }

    public void updateChangeSurvey(ChangeSurvey surveyinfo, ChangeInfo changeinfo) {
        if (surveyinfo.getApproveresult().trim().equals("通过审定")) {
            changeinfo.setStep("B");
            if (surveyinfo.getBudget().floatValue() < 10.0f) {
                changeinfo.setStep("C");
            }
        } else {
            surveyinfo.setState("false");
            changeinfo.setStep("A");
        }
        changeinfo.setSaddr(surveyinfo.getSaddr());
        changeinfo.setSexpense(surveyinfo.getSexpense());
        changeinfo.setSgrade(surveyinfo.getSgrade());
        changeinfo.setSname(surveyinfo.getSname());
        changeinfo.setSperson(surveyinfo.getSperson());
        changeinfo.setSphone(surveyinfo.getSphone());
        changeinfo.setBudget(surveyinfo.getBudget());
        changeinfo.setApproveresult(surveyinfo.getApproveresult());
        logger.info("结果： " + surveyinfo.getApproveresult());
        dao.updateChangeSurvey(surveyinfo, changeinfo);
    }

    public ChangeSurvey getChangeSurvey(String id) {
        return dao.getChangeSurvey(id);
    }

    public ChangeSurvey getChangeSurveyForChangeID(String changeid) {
        String hql = "from com.cabletech.linechange.domainobjects.ChangeSurvey changesurvey where changesurvey.changeid=? order by changesurvey.id";
        return dao.getChangeSurveyForChangeID(hql, changeid);
    }

    public List getChangeSurveyList(UserInfo user, ChangeSurveyBean surveyinfo) {
        String hql = "select info.id AS change_id,info.changename,survey.budget,survey.principal,to_char(survey.surveydate,'YYYY-MM-DD') surveydate,info.approveresult,decode(info.step,'A','待审定','B1','通过监理审定','B2','通过移动审定','C','施工准备','D','开始施工','E','施工完毕','F','验收完毕','G','已经归档') state,to_char(survey.approvedate,'YYYY-MM-DD') approvedate,survey.id,survey.approvedept  from changesurvey survey,changeinfo info where survey.changeid=info.id and survey.state is null ";// survey.approveresult
        if (user.getDeptype().equals("1") && !user.getRegionID().substring(2, 6).equals("0000")) {
            hql += " and info.regionid in ('" + user.getRegionID() + "')";
        }
        hql += surveyinfo.getPrincipal() == null ? " " : " and survey.principal like '%"
                + surveyinfo.getPrincipal() + "%'";
        hql += surveyinfo.getApproveresult() == null ? " " : " and survey.approveresult like '%"
                + surveyinfo.getApproveresult() + "%'";
        hql += surveyinfo.getChangename() == null ? "" : " and changeinfo.changename like '%"
                + surveyinfo.getChangename() + "%' ";
        hql += surveyinfo.getChangepro() == null ? "" : " and changeinfo.changepro like '%"
                + surveyinfo.getChangepro() + "%' ";
        hql += (surveyinfo.getLineclass() == null || surveyinfo.getLineclass().equals("")) ? ""
                : "and changeinfo.lineclass like '%" + surveyinfo.getLineclass() + "%' ";
        hql += surveyinfo.getChangeaddr() == null ? "" : " and changeinfo.changeaddr like '%"
                + surveyinfo.getChangeaddr() + "%' ";
        // hql += (surveyinfo.getStep() == null ||
        // surveyinfo.getStep().equals("")) ? ""
        // : "and info.step='" + surveyinfo.getStep() + "'";
        if (surveyinfo.getBudget() != null && surveyinfo.getBudget().floatValue() != 0.0f) {
            hql += " and survey.budget >= " + surveyinfo.getBudget();
        }
        if (surveyinfo.getMaxbudget() != null && surveyinfo.getMaxbudget().floatValue() != 0.0f) {
            hql += " and survey.budget  <= " + surveyinfo.getMaxbudget();
        }

        if (surveyinfo.getBegintime() != null && !surveyinfo.getBegintime().equals("")) {
            hql += " and survey.approvedate >= TO_DATE('" + surveyinfo.getBegintime()
                    + "','YYYY-MM-DD')";
        }
        if (surveyinfo.getEndtime() != null && !surveyinfo.getEndtime().equals("")) {
            hql += " and survey.approvedate <= TO_DATE('" + surveyinfo.getEndtime()
                    + "','YYYY-MM-DD')";
        }
        hql += " and (info.step=survey.surveytype)";
        // " or (info.step in ('C','D','E','F','G') and
        // survey.surveytype='B2'))";
        hql += " order by survey.approvedate desc,info.id desc";
        logger.info("sql:" + hql);
        return dao.getChangeSurveyList(hql);
    }

    public List getChangeSurveyList(String id, String surveyType) {
        String hql = "select info.changename,survey.budget,survey.surveyremark,survey.surveydatum,survey.approvedept,survey.approver,survey.approveremark,survey.principal,to_char(survey.surveydate,'YYYY-MM-DD') surveydate,survey.approveresult,decode(info.step,'A','待审定','B1','通过监理审定','B2','通过移动审定','C','施工准备','D','开始施工','E','施工完毕','F','验收完毕','G','已经归档') state,to_char(survey.approvedate,'YYYY-MM-DD') approvedate,survey.id  from changesurvey survey,changeinfo info where survey.changeid=info.id and survey.state is null ";
        hql += " and info.id='" + id + "' and surveytype='" + surveyType
                + "' order by survey.id desc";
        try {
            QueryUtil query = new QueryUtil();
            return query.queryBeans(hql);
        } catch (Exception ex) {
            logger.error("查看详细信息时异常：" + ex.getMessage());
        }
        return null;
    }

    public List getChangeSurveyList(String id) {
        String hql = "select info.changename,survey.budget,survey.surveyremark,survey.surveydatum,survey.approvedept,survey.approver,survey.approveremark,survey.principal,to_char(survey.surveydate,'YYYY-MM-DD') surveydate,survey.approveresult,decode(info.step,'A','待审定','B1','通过监理审定','B2','通过移动审定','C','施工准备','D','开始施工','E','施工完毕','F','验收完毕','G','已经归档') state,to_char(survey.approvedate,'YYYY-MM-DD') approvedate,survey.id  from changesurvey survey,changeinfo info where survey.changeid=info.id and survey.state is null ";
        hql += " and info.id='" + id
                + "' and survey.surveytype='B1' and survey.state is null order by survey.id desc";
        try {
            QueryUtil query = new QueryUtil();
            return query.queryBeans(hql);
        } catch (Exception ex) {
            logger.error("查看详细信息时异常：" + ex.getMessage());
        }
        return null;
    }

    public List getChangeSurveyHistoryList(String changeId, String showType) {
        String hql = "select survey.budget,survey.surveyremark,survey.surveydatum,survey.approvedept,survey.approver,survey.approveremark,survey.principal,to_char(survey.surveydate,'YYYY-MM-DD hh24:mi:ss') surveydate,survey.approveresult,to_char(survey.approvedate,'YYYY-MM-DD') approvedate,survey.id  from changesurvey survey,changeinfo info where survey.changeid=info.id ";
        hql += " and info.id='" + changeId + "'";
        if (!"all".equals(showType)) {
            hql += " and survey.state='false'";
        }
        hql += " order by survey.id";
        try {
            QueryUtil query = new QueryUtil();
            return query.queryBeans(hql);
        } catch (Exception ex) {
            logger.error("查看详细信息时异常：" + ex.getMessage());
        }
        return null;
    }

}
