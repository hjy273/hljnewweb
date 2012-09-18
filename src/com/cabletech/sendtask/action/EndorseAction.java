package com.cabletech.sendtask.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.power.CheckPower;
import com.cabletech.sendtask.beans.SendTaskBean;
import com.cabletech.sendtask.dao.EndorseTaskDao;
import com.cabletech.sendtask.dao.ExportDao;
import com.cabletech.sendtask.dao.SendTaskDao;
import com.cabletech.sendtask.util.SendTaskUtil;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.UploadUtil;

public class EndorseAction extends BaseDispatchAction {
    Logger logger = Logger.getLogger(this.getClass().getName());

    SendTaskDao sendDao = new SendTaskDao();

    EndorseTaskDao endorseDao = new EndorseTaskDao();

    /**
     * 显示所有待签收的派单
     */
    public ActionForward showTaskToEndorse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110201")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lEndorseTask = endorseDao.getTaskToEndorse(userinfo);

            // 取得待签单的统计信息
            List countList = endorseDao.getEndorseCountList(userinfo);
            request.getSession().setAttribute("countlist", countList);
            request.getSession().setAttribute("endorselist", lEndorseTask);
            request.getSession().setAttribute("type", "2");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("显示所有待签收的派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示所有登录人员的待签收的派单
     */
    public ActionForward showLoginUserTaskToEndorse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110201")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lEndorseTask = endorseDao.getLoginUserTaskToEndorse(userinfo);

            // 取得待签单的统计信息
            List countList = endorseDao.getEndorseCountList(userinfo);
            request.getSession().setAttribute("countlist", countList);
            request.getSession().setAttribute("endorselist", lEndorseTask);
            request.getSession().setAttribute("type", "2");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("显示所有待签收的派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示签收派单详细信息
     */
    public ActionForward showEndorseTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110201")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        // 派郑受理部门子表id 2008-12-01 add by guixy
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("type", "20");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示签收派单详细信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 保存派单签收信息
     */
    public ActionForward endorseTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110201")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            bean.setDeptid(userinfo.getDeptID());
            bean.setUserid(userinfo.getUserID());
            bean.setSendtaskid(bean.getId());

            // 开始处理上传文件================================
            // List attachments = bean.getAttachments();
            // String fileId;
            // ArrayList fileIdList = new ArrayList();
            // for( int i = 0; i < attachments.size(); i++ ){
            // UploadFile uploadFile = ( UploadFile )attachments.get( i );
            // FormFile file = uploadFile.getFile();
            // if( file == null ){
            // logger.error( "file is null" );
            // }
            // else{
            // //将文件存储到服务器并将路径写入数据库，返回记录ID
            // fileId = SaveUploadFile.saveFile( file );
            // if( fileId != null ){
            // fileIdList.add( fileId );
            // }
            // }
            // }
            ArrayList fileIdList = new ArrayList();
            FormFile file = null;
            String fileId;
            // 取得上传的文件
            Hashtable files = bean.getMultipartRequestHandler().getFileElements();
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
            if (fileIdListStr != null) {
                // System.out.println( "FILE:" + fileIdListStr );
                bean.setAcce(fileIdListStr);
            }
            // ======================

            if (!endorseDao.saveEndorse(bean)) {
                // return forwardErrorPage(mapping, request, "110202e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "110202e", backUrl);
            }

            String sim = sendDao.getSendPhone(bean.getSenduserid());
            if (sim != null) {
                // String msg = "工单签收通知：你签发的" + bean.getSendtype() + "工单已经签收.";
                String msg = SendTaskUtil.SENDTASK_MODELNAME + " " + bean.getSendtopic()
                        + "工单已经签收 " + userinfo.getUserName() + " " + SendSMRMI.MSG_NOTE;
                logger.info("签收派单的短信内容:" + msg);
                SendSMRMI.sendNormalMessage(userinfo.getUserID(), sim, msg, "00");
            }

            log(request, "派单管理", "签收任务");
            // return forwardInfoPage(mapping, request, "110202");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "110202", backUrl);
        } catch (Exception e) {
            logger.error("保存派单签收信息出错:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * 显示已签收的派单
     */
    public ActionForward showAllEndorse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110202")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lendorsetask = endorseDao.getendorseList(userinfo);
            request.getSession().setAttribute("endorselist", lendorsetask);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("显示已签收的派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示一个派单签收的详细信息
     */
    public ActionForward showOneEndorse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // if( !CheckPower.checkPower( request.getSession(), "110202" ) ){
        // return mapping.findForward( "powererror" );
        // }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            SendTaskBean endorsebean = sendDao.getEendorseOfSendTask(subid);
            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("type", "10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示一个派单签收的详细信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 条件查询显示
     */
    public ActionForward showQueryEndorse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110203")) {
            return mapping.findForward("powererror");
        }
        try {
            request.getSession().setAttribute("type", "3");
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("条件查询显示出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 条件查找派单
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110203")) {
            return mapping.findForward("powererror");
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List endorselist = endorseDao.queryendorseList(userinfo, bean, request.getSession());
            request.getSession().removeAttribute("endorselist");
            request.getSession().setAttribute("endorselist", endorselist);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("条件查找签收派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward doQueryAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("endorseTaskCon");
        if (sql.trim().length() != 0 && sql != null) {
            List endorselist = endorseDao.doQueryAfterMod(sql);
            request.getSession().setAttribute("endorselist", endorselist);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("按查询条件返回查找签收派单出错：session已过期");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 导出签收派单一览表
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
     */
    public ActionForward exportendorseResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" 创建dao");
            List list = (List) request.getSession().getAttribute("endorselist");
            logger.info("得到list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportEndorseResult(list, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("导出签收派单一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
