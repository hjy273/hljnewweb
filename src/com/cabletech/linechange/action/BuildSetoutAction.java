package com.cabletech.linechange.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForward;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;
import com.cabletech.power.CheckPower;
import javax.servlet.http.HttpServletResponse;
import com.cabletech.commons.base.BaseDispatchAction;
import org.apache.struts.action.ActionForm;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.LineBO;

import com.cabletech.linechange.services.BuildSetoutBOImpl;
import com.cabletech.linechange.bean.ChangeInfoBean;
import org.apache.struts.upload.FormFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.SaveUploadFile;
import java.util.ArrayList;
import com.cabletech.uploadfile.UploadUtil;
import java.util.StringTokenizer;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import java.util.Date;
import com.cabletech.linechange.dao.ExportDao;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linechange.domainobjects.ChangeSurvey;
import com.cabletech.linechange.services.ChangeSurveyBOImpl;
import com.cabletech.linechange.bean.ChangeSurveyBean;

public class BuildSetoutAction extends BaseDispatchAction {
    private static Logger logger = Logger.getLogger("BuildSetoutAction");

    public ActionForward updateDeliverTo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        String type = request.getParameter("type");
        String code = "";
        if (!"edit".equals(type)) {
            /** 权限校验 */
            if (!CheckPower.checkPower(request.getSession(), "50301")) {
                return mapping.findForward("powererror");
            }
            code = "50302s1";
        } else
            code = "50304s1";
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeInfoBean bean = (ChangeInfoBean) form;
        ChangeInfo data = new ChangeInfo();
        BuildSetoutBOImpl bo = new BuildSetoutBOImpl();
        ArrayList fileIdList = new ArrayList();
        String[] delfileid = request.getParameterValues("delfileid"); // 要删除的文件id号数组
        StringTokenizer st = new StringTokenizer(bean.getSetoutdatum(), ",");
        while (st.hasMoreTokens()) {
            fileIdList.add(st.nextToken());
        }
        if (delfileid != null) {
            for (int i = 0; i < delfileid.length; i++) {
                fileIdList.remove(delfileid[i]);
            }
        }
        String datumid = uploadFile(form, fileIdList);
        data = bo.getChangeInfo(bean.getId());
        data.setEname(bean.getEname());
        data.setEaddr(bean.getEaddr());
        data.setEperson(bean.getEperson());
        data.setEphone(bean.getEphone());
        data.setSetoutdatum(datumid);
        data.setSetoutperson(userinfo.getUserName());
        data.setSetoutremark(bean.getSetoutremark());
        data.setSetouttime(new Date());
        data.setStep("C");
        bo.addOrUpdateDeliverTo(data);
        // return forwardInfoPage( mapping, request, code );
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, code, backUrl);

    }

    public ActionForward updateEngage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        String type = request.getParameter("type");
        String code = "";
        if (!"edit".equals(type)) {
            /** 权限校验 */
            if (!CheckPower.checkPower(request.getSession(), "50301")) {
                return mapping.findForward("powererror");
            }
            code = "50302s2";
        } else
            code = "50304s2";

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeInfoBean bean = (ChangeInfoBean) form;
        ChangeInfo data = new ChangeInfo();
        BuildSetoutBOImpl bo = new BuildSetoutBOImpl();
        ArrayList fileIdList = new ArrayList();
        String[] delfileid = request.getParameterValues("delfileid"); // 要删除的文件id号数组
        StringTokenizer st = new StringTokenizer(bean.getSetoutdatum(), ",");
        while (st.hasMoreTokens()) {
            fileIdList.add(st.nextToken());
        }
        if (delfileid != null) {
            for (int i = 0; i < delfileid.length; i++) {
                fileIdList.remove(delfileid[i]);
            }
        }
        String datumid = uploadFile(form, fileIdList);
        // System.out.println("id"+bean.getId());
        data = bo.getChangeInfo(bean.getId());
        data.setSname(bean.getSname());
        data.setSaddr(bean.getSaddr());
        data.setSperson(bean.getSperson());
        data.setSphone(bean.getSphone());
        data.setSgrade(bean.getSgrade());
        data.setSexpense(bean.getSexpense());
        data.setDname(bean.getDname());
        data.setDaddr(bean.getDaddr());
        data.setDperson(bean.getDperson());
        data.setDphone(bean.getDphone());
        data.setDgrade(bean.getDgrade());
        data.setDexpense(bean.getDexpense());
        data.setSetoutdatum(datumid);
        data.setSetoutperson(userinfo.getUserName());
        data.setSetoutremark(bean.getSetoutremark());
        data.setSetouttime(new Date());
        data.setStep("C");
        bo.addOrUpdateEngage(data);
        // return forwardInfoPage( mapping, request, code );
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, code, backUrl);
    }

    public ActionForward listChangeInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50301")) {
            return mapping.findForward("powererror");
        }
        BuildSetoutBOImpl bo = new BuildSetoutBOImpl();
        // UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
        // "LOGIN_USER" );
        // //ChangeInfoBean bean = ( ChangeInfoBean )form;
        List listchangeinfo = bo.getChangeInfo();
        if (listchangeinfo.isEmpty())
            listchangeinfo = null;
        request.getSession().setAttribute("queryresult", listchangeinfo);
        super.setPageReset(request);
        return mapping.findForward("listChangeInfo");
    }

    public ActionForward listBuildSetout(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        // if( !CheckPower.checkPower( request.getSession(), "50302" ) ){
        // return mapping.findForward( "powererror" );
        // }
        BuildSetoutBOImpl bo = new BuildSetoutBOImpl();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeInfoBean bean = (ChangeInfoBean) form;
        List listchangeinfo = bo.getSetoutInfo(userinfo, bean);
        if (listchangeinfo.isEmpty())
            listchangeinfo = null;
        request.getSession().setAttribute("queryresult", listchangeinfo);
        request.getSession().setAttribute("bean", bean);
        super.setPageReset(request);
        return mapping.findForward("loadbuildsetoutList");
    }

    public ActionForward loadAddForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50301")) {
            return mapping.findForward("powererror");
        }
        float budget = Float.parseFloat(request.getParameter("budget"));
        String type = request.getParameter("type");
        String flow = "";
        if (budget >= 10 && budget <= 30)
            flow = "engage";
        if (budget > 30)
            flow = "deliverto";
        String id = request.getParameter("id");
        BuildSetoutBOImpl bo = new BuildSetoutBOImpl();
        ChangeInfo changeinfo = bo.getChangeInfo(id);
        ChangeInfoBean bean = new ChangeInfoBean();
        BeanUtil.objectCopy(changeinfo, bean);
        request.setAttribute("flow", flow);
        request.setAttribute("id", id);
        bean.setType(type);
        ChangeSurveyBOImpl sbo = new ChangeSurveyBOImpl();
        ChangeSurvey survey = sbo.getChangeSurveyForChangeID(id);
        ChangeSurveyBean surveybean = new ChangeSurveyBean();
        BeanUtil.objectCopy(survey, surveybean);
        request.setAttribute("survey", surveybean);

        request.setAttribute("changeinfo", bean);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) changeinfo.getLineclass());
        request.setAttribute("line_class_name", lineClassName);

        return mapping.findForward("loadAddForm");

    }

    public ActionForward loadLookForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        // if( !CheckPower.checkPower( request.getSession(), "50302" ) ){
        // return mapping.findForward( "powererror" );
        // }
        float budget = Float.parseFloat(request.getParameter("budget"));
        String type = "";
        if (budget >= 10 && budget < 30)
            type = "engage";
        if (budget >= 30)
            type = "deliverto";
        String id = request.getParameter("id");
        BuildSetoutBOImpl bo = new BuildSetoutBOImpl();
        ChangeInfo changeinfo = bo.getChangeInfo(id);
        request.setAttribute("type", type);
        request.setAttribute("id", id);
        ChangeSurveyBOImpl sbo = new ChangeSurveyBOImpl();
        ChangeSurvey survey = sbo.getChangeSurveyForChangeID(id);
        ChangeSurveyBean surveybean = new ChangeSurveyBean();
        BeanUtil.objectCopy(survey, surveybean);
        request.setAttribute("survey", surveybean);
        request.setAttribute("changeinfo", changeinfo);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) changeinfo.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        return mapping.findForward("lookForm");

    }

    public ActionForward loadQueryForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        if (!CheckPower.checkPower(request.getSession(), "50303")) {
            return mapping.findForward("powererror");
        }
        return mapping.findForward("loadQueryForm");
    }

    /**
     * 文件上传
     * 
     * @param form
     *            ActionForm
     * @return String
     */
    public String uploadFile(ActionForm form, ArrayList fileIdList) {
        ChangeInfoBean formbean = (ChangeInfoBean) form;
        // 开始处理上传文件================================
        // List attachments = formbean.getAttachments();
        // String fileId;
        // for( int i = 0; i < attachments.size(); i++ ){
        // UploadFile uploadFile = ( UploadFile )attachments.get( i );
        // FormFile file = uploadFile.getFile();
        // if( file == null ){
        // logger.info( "file is null" );
        // }
        // else{
        // //将文件存储到服务器并将路径写入数据库，返回记录ID
        // fileId = SaveUploadFile.saveFile( file );
        // if( fileId != null ){
        // fileIdList.add( fileId );
        // }
        // }
        // }
        FormFile file = null;
        String fileId;
        // 取得上传的文件
        Hashtable files = formbean.getMultipartRequestHandler().getFileElements();
        if (files != null && files.size() > 0) {
            // 得到files的keys
            Enumeration enums = files.keys();
            String fileKey = null;
            int i = 0;
            // 遍历枚举
            while (enums.hasMoreElements()) {
                // 取得key
                fileKey = (String) (enums.nextElement());
                // 初始化每一个FormFile(接口)
                file = (FormFile) files.get(fileKey);

                if (file == null) {
                    logger.error("file is null");
                } else {
                    // 将文件存储到服务器并将路径写入数据库，返回记录ID
                    fileId = SaveUploadFile.saveFile(file);
                    if (fileId != null) {
                        fileIdList.add(fileId);
                    }
                }
                i++;
            }

        }
        // 处理上传文件结束=======================================

        // 获取ID字符串列表(以逗号分隔的文件ID字符串)======================
        String fileIdListStr = UploadUtil.getFileIdList(fileIdList);
        // String fileIdListStr =processFileUpload(request);
        String datumid = "";
        if (fileIdListStr != null) {
            // logger.info( "FileIdListStr=" + fileIdListStr );
            datumid = fileIdListStr;
        }
        return datumid;
    }

    public ActionForward exportBuildSetoutResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            ChangeInfoBean bean = null;
            if (request.getSession().getAttribute("bean") != null) {
                bean = (ChangeInfoBean) request.getSession().getAttribute("bean");
                logger.info("获得查询条件bean。。。");
                logger.info("开始时间：" + bean.getBegintime());
                logger.info("结束时间：" + bean.getEndtime());

            }
            List list = (List) request.getSession().getAttribute("queryresult");
            logger.info("得到list");
            logger.info("输出excel成功");
            ExportDao ed = new ExportDao();
            ed.exportBuildSetoutResult(list, bean, response);
            logger.info("end.....");
            return null;
        } catch (Exception e) {
            logger.error("导出信息报表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
