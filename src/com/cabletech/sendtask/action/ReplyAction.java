package com.cabletech.sendtask.action;

import org.apache.log4j.Logger;
import com.cabletech.commons.base.BaseDispatchAction;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletRequest;
import com.cabletech.baseinfo.domainobjects.UserInfo;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletResponse;
import com.cabletech.power.CheckPower;
import org.apache.struts.action.ActionForm;
import com.cabletech.sendtask.dao.*;
import com.cabletech.sendtask.util.SendTaskUtil;
import com.cabletech.sendtask.beans.*;

import org.apache.struts.upload.FormFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.SaveUploadFile;
import java.util.ArrayList;
import com.cabletech.uploadfile.UploadUtil;
import com.cabletech.commons.sm.SendSMRMI;
import java.util.StringTokenizer;

public class ReplyAction extends BaseDispatchAction {
    Logger logger = Logger.getLogger(this.getClass().getName());

    SendTaskDao sendDao = new SendTaskDao();

    EndorseTaskDao endorseDao = new EndorseTaskDao();

    ReplyDao replyDao = new ReplyDao();

    /**
     * 显示所有待回复的派单
     */
    public ActionForward showTaskToReply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110301")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List replyList = replyDao.getTaskToReply(userinfo);

            // 取得待回复单的统计信息
            List countList = replyDao.getReplyCountList(userinfo);
            request.getSession().setAttribute("countlist", countList);
            request.getSession().setAttribute("replylist", replyList);
            request.getSession().setAttribute("type", "2");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("显示所有待回复的派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示登录人员所有待回复的派单
     */
    public ActionForward showLoginUserTaskToReply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110301")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List replyList = replyDao.getLoginUserTaskToReply(userinfo);

            // 取得待回复单的统计信息
            List countList = replyDao.getReplyCountList(userinfo);
            request.getSession().setAttribute("countlist", countList);
            request.getSession().setAttribute("replylist", replyList);
            request.getSession().setAttribute("type", "2");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("显示所有待回复的派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示一个待回复派单的详细信息
     */
    public ActionForward showOneToReply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110301")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            String workState = endorseDao.getOneSendTaskState(subid);
            SendTaskBean bean = null;
            SendTaskBean endorsebean = null;
            if ("2待重做".equals(workState)) {
                List beanList = endorseDao.getOneSendTaskInfo(subid);
                request.getSession().setAttribute("beanList", beanList);
                request.getSession().setAttribute("state", "1");
            } else {
                request.getSession().setAttribute("state", "2");
            }
            bean = endorseDao.getOneSendTask(id, userinfo, subid);
            endorsebean = sendDao.getEendorseOfSendTask(subid);
            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("type", "20");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示一个待回复派单的详细信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 保存派单回复信息
     */
    public ActionForward replyTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110301")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            bean.setReplyuserid(userinfo.getUserID());
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
                bean.setReplyacce(fileIdListStr);
            }
            // ======================

            if (!replyDao.saveReply(bean)) {
                // return forwardErrorPage(mapping, request, "110302e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "110302e", backUrl);
            }

            String sim = sendDao.getSendPhone(bean.getSenduserid());
            if (sim != null) {
                // String msg = "工单回复通知：你签发的" + bean.getSendtype() + "工单已经回复.";
                String msg = SendTaskUtil.SENDTASK_MODELNAME + " " + bean.getSendtopic()
                        + "工单已经回复 " + userinfo.getUserName() + " " + SendSMRMI.MSG_NOTE;
                logger.info("回复派单的短信内容:" + msg);
                SendSMRMI.sendNormalMessage(userinfo.getUserID(), sim, msg, "00");
            }

            log(request, "派单管理", "回复任务");
            // return forwardInfoPage(mapping, request, "110302");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "110302", backUrl);
        } catch (Exception e) {
            logger.error("保存派单回复信息出错:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * 显示已回复的派单
     */
    public ActionForward showAllReply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lreply = replyDao.getreplyList(userinfo);
            request.getSession().setAttribute("lreply", lreply);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("显示已回复的派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示一个回复派单的详细信息
     */
    public ActionForward showOneReply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110301")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            SendTaskBean endorsebean = sendDao.getEendorseOfSendTask(subid);
            SendTaskBean replybean = replyDao.getOneSendTask(subid, userinfo);
            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("replybean", replybean);
            request.getSession().setAttribute("type", "10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示一个回复派单的详细信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示一个待修改的回复派单详细信息
     */
    public ActionForward showOneToUp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110304")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            SendTaskBean endorsebean = sendDao.getEendorseOfSendTask(subid);
            SendTaskBean replybean = replyDao.getOneSendTask(subid, userinfo);
            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("replybean", replybean);
            request.getSession().setAttribute("type", "4");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示一个待修改的回复派单详细信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 执行修改派单回复
     */
    public ActionForward upReply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110304")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            bean.setReplyuserid(userinfo.getUserID());
            ArrayList fileIdList = new ArrayList();
            String[] delfileid = request.getParameterValues("delfileid"); // 要删除的文件id号数组
            // 组合新的附件串
            StringTokenizer st = new StringTokenizer(bean.getReplyacce(), ",");
            while (st.hasMoreTokens()) {
                fileIdList.add(st.nextToken());
            }
            if (delfileid != null) {
                for (int i = 0; i < delfileid.length; i++) {
                    fileIdList.remove(delfileid[i]);
                }
            }
            // 开始处理上传文件================================
            // List attachments = bean.getAttachments();
            // String fileId;
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
            // ArrayList fileIdList = new ArrayList();
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
                bean.setReplyacce(fileIdListStr);
            }
            // ======================

            if (!replyDao.upReply(bean)) {
                // return forwardErrorPage(mapping, request, "110304e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "110304e", backUrl);
            }

            String sim = sendDao.getSendPhone(bean.getSenduserid());
            if (sim != null && bean.getWorkstate().equals("待重做")) {
                String msg = "工单通知：有一重做的" + bean.getSendtype() + "工单等待您的验证.";
                SendSMRMI.sendNormalMessage(userinfo.getUserID(), sim, msg, "00");
            }

            log(request, "派单管理", "修改回复");
            // return forwardInfoPage(mapping, request, "110304");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "110304", backUrl);
        } catch (Exception e) {
            logger.error("修改回复派单出错:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * 条件查询显示
     */
    public ActionForward showQueryReply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110303")) {
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
        if (!CheckPower.checkPower(request.getSession(), "110303")) {
            return mapping.findForward("powererror");
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lreply = replyDao.queryReplyList(userinfo, bean, request.getSession());
            request.getSession().removeAttribute("lreply");
            request.getSession().setAttribute("lreply", lreply);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("条件查找回复派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward doQueryAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("replayQueryCon");
        if (sql.trim().length() != 0 && sql != null) {
            List lreply = replyDao.doQueryAfterMod(sql);
            request.getSession().setAttribute("lreply", lreply);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("按查询条件返回查找回复派单出错：session已过期");
            return forwardErrorPage(mapping, request, "error");
        }

    }

    /**
     * 导出回复派单一览表
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
    public ActionForward exportReplyResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" 创建dao");
            List list = (List) request.getSession().getAttribute("lreply");
            logger.info("得到list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportReplyResult(list, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("导出回复派单一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示转发页面
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
    public ActionForward showTaskForward(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List ldept = sendDao.getAcceptdept(userinfo);
            List luser = sendDao.getAcceptUser(userinfo);
            request.getSession().setAttribute("deptinfo", ldept);
            request.getSession().setAttribute("userinfo", luser);
            // 取得派单主表id及子表id
            String id = request.getParameter("id");
            String subid = request.getParameter("subid");
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("type", "forward");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // showTaskForward

    /**
     * 转发派单
     */
    public ActionForward forwardTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            bean.setSenduserid(userinfo.getUserID());
            bean.setSenddeptid(userinfo.getDeptID());
            bean.setRegionid(userinfo.getRegionID());

            String[] deptId = request.getParameterValues("acceptdeptid");
            String[] userId = request.getParameterValues("acceptuserid");

            // 如果不存在转发的部门
            if (deptId == null) {
                return forwardErrorPage(mapping, request, "110301e");
            }

            if (!sendDao.sendTask(bean, deptId, userId)) {
                return forwardErrorPage(mapping, request, "110301e");
            }

            // 转发多个人发多条短信
            for (int i = 0; i < userId.length; i++) {
                String sim = sendDao.getSendPhone(userId[i]);
                if (sim != null) {
                    String msg = "工单通知：有一" + bean.getSendtype() + "工单等待您的签收和处理.";
                    SendSMRMI.sendNormalMessage(userinfo.getUserID(), sim, msg, "00");
                }
            }

            log(request, "派单管理", "回复任务");
            return forwardInfoPage(mapping, request, "110301");
        } catch (Exception e) {
            logger.error("转发派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
