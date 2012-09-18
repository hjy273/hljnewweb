package com.cabletech.sendtask.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
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
import com.cabletech.sendtask.dao.ReplyDao;
import com.cabletech.sendtask.dao.SendTaskDao;
import com.cabletech.sendtask.dao.ValidateDao;
import com.cabletech.sendtask.util.SendTaskUtil;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.UploadUtil;

public class ValidateAction extends BaseDispatchAction {
    Logger logger = Logger.getLogger(this.getClass().getName());

    SendTaskDao sendDao = new SendTaskDao();

    EndorseTaskDao endorseDao = new EndorseTaskDao();

    ReplyDao replyDao = new ReplyDao();

    ValidateDao valiDao = new ValidateDao();

    /**
     * 显示所有待验证的派单
     */
    public ActionForward showTaskToValidate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110401")) {
            return mapping.findForward("powererror");
        }

        // 2009-1-5 add by guixy 添加为查看所建的连接
        String loginuserFlg = request.getParameter("loginuserflg");
        // 2009-1-5 add by guixy 添加为查看所建的连接

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List valiList = valiDao.getTaskToVali(userinfo, loginuserFlg);
            // 对验证的处理时限去掉周六周日的处理 2008-12-18
            DynaBean rowBean = null;
            String replytime;
            float processterm;
            int weeks;
            int weekNum;
            for (int i = 0; i < valiList.size(); i++) {
                weekNum = 0;
                weeks = 0;
                rowBean = (DynaBean) valiList.get(i);
                replytime = String.valueOf(rowBean.get("replytime"));
                if (rowBean.get("processterm") != null) {
                    processterm = Float.parseFloat(rowBean.get("processterm").toString());
                    // 取得跨周数
                    weeks = SendTaskUtil.getWeeks(replytime);
                    if (processterm != 0 && Math.abs(processterm) > 48) {

                        if (weeks != 0) {
                            weekNum = SendTaskUtil.getWeekNum(replytime);
                            if (processterm > 0) {
                                processterm = processterm - 48 * weekNum;
                            } else {
                                processterm = processterm + 48 * weekNum;
                            }
                            rowBean.set("processterm", new BigDecimal(String
                                    .valueOf(((int) (processterm * 10)) / 10.0)));
                        }
                    } else if (weeks == 1 && processterm != 0 && Math.abs(processterm) < 48) {
                        rowBean.set("processterm", new BigDecimal("0"));
                    }
                }

            }
            // 对验证的处理时限去掉周六周日的处理 2008-12-18

            // 取得待回复单的统计信息
            List countList = valiDao.getValidateCountList(userinfo);
            request.getSession().setAttribute("countlist", countList);
            request.getSession().setAttribute("valiList", valiList);
            request.getSession().setAttribute("type", "2");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("显示所有待验证的派单出错:" + e.getMessage());
            e.printStackTrace();
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示一个验证派单的详细信息,进行验证
     */
    public ActionForward showOneValidate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110401")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            SendTaskBean endorsebean = sendDao.getEendorseOfSendTask(subid);
            SendTaskBean replybean = replyDao.getOneSendTask(subid, userinfo);
            SendTaskBean info = valiDao.getInfo(id, userinfo);
            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("replybean", replybean);
            request.getSession().setAttribute("info", info);
            request.getSession().setAttribute("type", "20");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示一个验证派单的详细信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 保存派单验证信息
     */
    public ActionForward validateTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110401")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            bean.setValidateuserid(userinfo.getUserID());
            bean.setSendtaskid(bean.getId());
            bean.setReplyid(bean.getReplyid());

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
                bean.setValidateacce(fileIdListStr);
            }
            // ======================

            if (!valiDao.saveValidate(bean)) {
                // return forwardErrorPage(mapping, request, "110402e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "110402e", backUrl);
            }

            String sim = sendDao.getSendPhone(bean.getReplyuserid());
            if (sim != null) {
                // String msg = "工单验证通知：你回复的" + bean.getSendtype() + "工单" +
                // bean.getValidateresult() + ".";
                String msg = SendTaskUtil.SENDTASK_MODELNAME + " " + bean.getSendtopic()
                        + "工单已经验证 " + userinfo.getUserName() + " " + SendSMRMI.MSG_NOTE;
                logger.info("验证派单的短信内容:" + msg);
                SendSMRMI.sendNormalMessage(userinfo.getUserID(), sim, msg, "00");
            }

            log(request, "派单管理", "验证任务");
            // return forwardInfoPage(mapping, request, "110402");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "110402", backUrl);
        } catch (Exception e) {
            logger.error("保存派单验证信息出错:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * 显示已经过验证的派单
     */
    public ActionForward showAllValidate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // if( !CheckPower.checkPower( request.getSession(), "110402" ) ){
        // return mapping.findForward( "powererror" );
        // }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lvali = valiDao.getvalidateList(userinfo);
            request.getSession().setAttribute("lvali", lvali);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("显示已回复的派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示一个已经经过验证的派单详细信息
     */
    public ActionForward showValidate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // if( !CheckPower.checkPower( request.getSession(), "110402" ) ){
        // return mapping.findForward( "powererror" );
        // }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            SendTaskBean endorsebean = sendDao.getEendorseOfSendTask(subid);
            SendTaskBean replybean = replyDao.getOneSendTask(subid, userinfo);
            SendTaskBean valibean = valiDao.getOneValidate(subid, userinfo);
            SendTaskBean info = valiDao.getInfo(id, userinfo);
            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("replybean", replybean);
            request.getSession().setAttribute("valibean", valibean);
            request.getSession().setAttribute("info", info);

            request.getSession().setAttribute("type", "10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示一个已经经过验证的派单详细信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 条件查询显示
     */
    public ActionForward showQueryValidate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110403")) {
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
        if (!CheckPower.checkPower(request.getSession(), "110403")) {
            return mapping.findForward("powererror");
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lvali = valiDao.queryValiList(userinfo, bean, request.getSession());
            // 对验证的处理时限去掉周六周日的处理 2008-12-18
            DynaBean rowBean = null;
            String replytime;
            String validatetime;
            float processterm;
            int weeks;
            int weekNum;
            for (int i = 0; i < lvali.size(); i++) {
                weeks = 0;
                weekNum = 0;
                rowBean = (DynaBean) lvali.get(i);
                replytime = String.valueOf(rowBean.get("replytime"));
                validatetime = String.valueOf(rowBean.get("validatetime"));
                processterm = Float.parseFloat(rowBean.get("processterm").toString());

                // 取得跨周数
                weeks = SendTaskUtil.getWeeks(replytime, validatetime);
                if (processterm != 0 && Math.abs(processterm) > 48) {

                    if (weeks != 0) {
                        weekNum = SendTaskUtil.getWeekNum(replytime, validatetime);
                        if (processterm > 0) {
                            processterm = processterm - 48 * weekNum;
                        } else {
                            processterm = processterm + 48 * weekNum;
                        }
                        rowBean.set("processterm", new BigDecimal(String
                                .valueOf(((int) (processterm * 10)) / 10.0)));
                    }
                } else if (weeks == 1 && processterm != 0 && Math.abs(processterm) < 48) {
                    rowBean.set("processterm", new BigDecimal("0"));
                }
            }
            // 对验证的处理时限去掉周六周日的处理 2008-12-18

            request.getSession().setAttribute("lvali", lvali);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("条件查找验证派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward doQueryAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("vlQueryCon");
        if (sql.trim().length() != 0 && sql != null) {
            List lvali = valiDao.doQueryAfterMod(sql);
            request.getSession().setAttribute("lvali", lvali);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("条件查找验证派单出错:session过期");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 导出验证派单一览表
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
    public ActionForward exportValidateResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" 创建dao");
            List list = (List) request.getSession().getAttribute("lvali");
            logger.info("得到list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportValidateResult(list, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("导出验证派单一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
