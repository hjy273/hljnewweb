package com.cabletech.linechange.action;

import com.cabletech.commons.base.BaseDispatchAction;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForward;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;
import com.cabletech.power.CheckPower;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import java.lang.reflect.InvocationTargetException;

import com.cabletech.linechange.services.ChangeSurveyBOImpl;
import com.cabletech.linechange.services.PageonholeBOImpl;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linechange.bean.ChangeInfoBean;
import java.util.List;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.LineBO;

import java.util.Date;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.apache.struts.upload.FormFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadUtil;
import com.cabletech.linechange.dao.ExportDao;
import com.cabletech.linechange.services.ChangeApplyBOImpl;
import org.apache.commons.beanutils.DynaBean;

public class StatAction extends BaseDispatchAction {
    private static Logger logger = Logger.getLogger("StatAction");

    public ActionForward loadQueryForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** Ȩ��У�� */
        if (!CheckPower.checkPower(request.getSession(), "50801")) {
            return mapping.findForward("powererror");
        }

        return mapping.findForward("loadstaqueryform");
    }

    public ActionForward getStatInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        ChangeApplyBOImpl bo = new ChangeApplyBOImpl();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeInfoBean bean = (ChangeInfoBean) form;

        ExportDao ed = new ExportDao();
        List listchangeinfo = ed.getChangeStat(userinfo, bean);

        if (listchangeinfo.isEmpty()) {
            listchangeinfo = null;
        }
        request.getSession().setAttribute("queryresult", listchangeinfo);

        super.setPageReset(request);

        return mapping.findForward("loadstatlist");
    }

    public ActionForward showOne(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        if (!CheckPower.checkPower(request.getSession(), "50801")) {
            return mapping.findForward("powererror");
        }
        ExportDao ed = new ExportDao();
        String id = request.getParameter("id");
        DynaBean bean = ed.getOne(id);
        request.setAttribute("changeinfo", bean);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) bean.get("lineclass"));
        request.setAttribute("line_class_name", lineClassName);

        ChangeSurveyBOImpl bo = new ChangeSurveyBOImpl();
        List surveyList = bo.getChangeSurveyList(id, "B1");
        List list = new ArrayList();
        if (surveyList != null && surveyList.size() > 0) {
            list.add(surveyList.get(0));
        }
        request.setAttribute("survey_list", list);
        request.setAttribute("source", request.getParameter("source"));

        return mapping.findForward("loadstatlist");
    }

    public ActionForward exportChangeStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            ChangeInfoBean bean = null;
            if (request.getSession().getAttribute("bean") != null) {
                bean = (ChangeInfoBean) request.getSession().getAttribute("bean");
                logger.info("��ò�ѯ����bean������");
                logger.info("��ʼʱ�䣺" + bean.getBegintime());
                logger.info("����ʱ�䣺" + bean.getEndtime());

            }
            List list = (List) request.getSession().getAttribute("queryresult");
            logger.info("�õ�list");
            logger.info("���excel�ɹ�");
            ExportDao ed = new ExportDao();
            ed.exportChangeStat(list, bean, response);
            logger.info("end.....");
            return null;
        } catch (Exception e) {
            logger.error("������Ϣ��������쳣:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
