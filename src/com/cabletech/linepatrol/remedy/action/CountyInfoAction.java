package com.cabletech.linepatrol.remedy.action;

import java.sql.ResultSet;
import java.util.*;

import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;

import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.baseinfo.services.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.web.*;
import com.cabletech.linepatrol.remedy.beans.CountyInfoBean;
import com.cabletech.linepatrol.remedy.domain.CountyInfo;
import com.cabletech.power.CheckPower;

public class CountyInfoAction extends RemedyBaseAction {
    private static Logger logger = Logger.getLogger(CountyInfoAction.class.getName());

    /**
     * ����һ����
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward addCounty(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        CountyInfoBean bean = (CountyInfoBean) form;
        CountyInfo data = new CountyInfo();
        try {
            BeanUtil.objectCopy(bean, data);
        } catch (Exception e) {
            logger.info("�����������ֵʧ�ܣ�");
            e.printStackTrace();
        }

        data.setId("-1");
        String backUrl = request.getContextPath() + "/linepatrol/remedy/addCounty.jsp";
        ;
        if (super.getService().judgeCountyExist(data)) {
            return super.forwardErrorPageWithUrl(mapping, request, "0011quxianerror", backUrl);
        }
        data.setId(super.getDbService().getSeq("linepatrol_towninfo", 10));

        try {
            super.getService().createCounty(data);
        } catch (Exception e) {
            logger.info("�������������Ϣʧ�ܣ�");
            e.printStackTrace();
        }

        log(request, " ��������������Ϣ ", " �������Ϲ��� ");

        return forwardInfoPage(mapping, request, "0011quxian");
    }

    /**
     * ������Ϣ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadCounty(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        if (!CheckPower.checkPower(request.getSession(), "70404")) {
            return mapping.findForward("powererror");
        }
        try {
            CountyInfo data = super.getService().loadCounty(request.getParameter("id"));
            CountyInfoBean bean = new CountyInfoBean();
            BeanUtil.objectCopy(data, bean);
            request.setAttribute("countyinfoBean", bean);
            request.getSession().setAttribute("countyinfoBean", bean);
        } catch (Exception e) {
            logger.info("��������������Ϣʧ�ܣ�");
            e.printStackTrace();
        }

        return mapping.findForward("updateCounty");
    }

    /**
     * ��ѯ�ؼ�������Ϣ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryCounty(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        CountyInfoBean bean = (CountyInfoBean) form;
        request.getSession().setAttribute("S_BACK_URL", null); //
        request.getSession().setAttribute("theQueryBean", bean);//

        String strRegion = ((CountyInfoBean) form).getRegionid();
        if (strRegion == null) {
            strRegion = super.getLoginUserInfo(request).getRegionid();
        }

        String sql = "select a.id, a.town, a.remark, b.regionname regionid from LINEPATROL_TOWNINFO a, region b";

        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);

        sqlBuild.addConstant("a.regionid = b.regionid(+)");
        sqlBuild.addConditionAnd("a.town like {0}", "%" + ((CountyInfoBean) form).getTown() + "%");
        sqlBuild.addConditionAnd("a.regionid={0}", ((CountyInfoBean) form).getRegionid());
        sqlBuild.addConditionAnd("a.id={0}", ((CountyInfoBean) form).getId());
        if (!strRegion.equals("")) {
            sqlBuild.addTableRegion("a.regionid", strRegion);
        }
        // super.getLoginUserInfo( request ).getRegionid();
        sql = sqlBuild.toSql();
        sql += " order by a.id desc";
        try {
            // logger.info("sql"+sql);
            List list = super.getDbService().queryBeans(sql);
            request.getSession().setAttribute("queryresult", list);
            logger.info("SQL:" + sql);
            super.setPageReset(request);
            return mapping.findForward("querycountyresult");
        } catch (Exception e) {
            logger.error("��ѯ����������Ϣ�쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * �޸��ؼ���Ϣ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward updateCounty(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        try {
            CountyInfo data = new CountyInfo();
            BeanUtil.objectCopy((CountyInfoBean) form, data);

            String backUrl = request.getContextPath()
                    + "/countyinfoAction.do?method=loadCounty&id=" + data.getId();
            if (super.getService().judgeCountyExist(data)) {
                return super.forwardErrorPageWithUrl(mapping, request, "0011quxianerror", backUrl);
            }
            super.getService().updateCounty(data);
            log(request, " ��������������Ϣ ", " �������Ϲ��� ");
            String strQueryString = getTotalQueryString("method=queryCounty&town=",
                    (CountyInfoBean) request.getSession().getAttribute("theQueryBean"));
            Object[] args = getURLArgs("/WebApp/countyinfoAction.do", strQueryString,
                    (String) request.getSession().getAttribute("S_BACK_URL"));
            return forwardInfoPage(mapping, request, "0022quxian", null, args);
        } catch (Exception e) {
            logger.info("�޸�����������Ϣʧ�ܣ�");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * ɾ��������
     * 
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward deleteCounty(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        ResultSet rst = null;
        String id = (String) request.getParameter("id");

        String sql = "delete from LINEPATROL_TOWNINFO where id='" + id + "'";
        try {
            UpdateUtil exec = new UpdateUtil();
            exec.executeUpdate(sql);
            log(request, " ɾ������������Ϣ ", " �������Ϲ��� ");
            String strQueryString = getTotalQueryString("method=queryCounty&town=",
                    (CountyInfoBean) request.getSession().getAttribute("theQueryBean"));
            Object[] args = getURLArgs("/WebApp/countyinfoAction.do", strQueryString,
                    (String) request.getSession().getAttribute("S_BACK_URL"));
            return forwardInfoPage(mapping, request, "0023quxian", null, args);
        } catch (Exception e) {
            if (rst != null) {
                rst.close();
            }
            logger.debug("ɾ������������Ϣ�쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 
     * @param queryString
     * @param bean
     * @return
     */
    public String getTotalQueryString(String queryString, CountyInfoBean bean) {
        if (bean.getTown() != null) {
            queryString = queryString + bean.getTown();
        }
        if (bean.getId() != null) {
            queryString = queryString + "&id=" + bean.getId();
        }
        if (bean.getRegionid() != null) {
            queryString = queryString + "&regionid=" + bean.getRegionid();
        }
        return queryString;
    }

    public void initRemedyBaseBo() {
        // TODO Auto-generated method stub

    }

}
