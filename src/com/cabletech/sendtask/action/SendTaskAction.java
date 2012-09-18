package com.cabletech.sendtask.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

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

public class SendTaskAction extends BaseDispatchAction {
    Logger logger = Logger.getLogger(this.getClass().getName());

    SendTaskDao dao = new SendTaskDao();

    EndorseTaskDao endorseDao = new EndorseTaskDao();

    ReplyDao replyDao = new ReplyDao();

    ValidateDao valiDao = new ValidateDao();

    /**
     * 显示填写派单
     */
    public ActionForward showSendTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110101")) {
            return mapping.findForward("powererror");
        }
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List ldept = dao.getAcceptdept(userinfo);
            List luser = dao.getAcceptUser(userinfo);
            request.getSession().setAttribute("deptinfo", ldept);
            request.getSession().setAttribute("userinfo", luser);
            request.getSession().setAttribute("type", "2");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 保存派单
     */
    public ActionForward sendTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110101")) {
            return mapping.findForward("powererror");
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            bean.setSenduserid(userinfo.getUserID());
            bean.setSenddeptid(userinfo.getDeptID());
            bean.setRegionid(userinfo.getRegionID());

            // 开始处理上传文件================================
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

            // List attachments = bean.getAttachments();
            // String fileId;
            //            
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
            // 处理上传文件结束=======================================
            // 获取ID字符串列表(以逗号分隔的文件ID字符串)======================
            String fileIdListStr = UploadUtil.getFileIdList(fileIdList);
            if (fileIdListStr != null) {
                System.out.println("FILE:" + fileIdListStr);
                bean.setSendacce(fileIdListStr);
            }
            // ======================

            // add by guixy 2008-11-28添加了多派单部门
            String[] deptId = request.getParameterValues("acceptdeptid");
            String[] userId = request.getParameterValues("acceptuserid");

            if (!dao.sendTask(bean, deptId, userId)) {
                return forwardErrorPage(mapping, request, "110102e");
            }

            // modify by guixy 2008-12-4 多个受量人应该就是多条短信
            for (int i = 0; i < userId.length; i++) {
                String sim = dao.getSendPhone(userId[i]);
                if (sim != null) {
                    // String msg = "工单通知：有一" + bean.getSendtype() +
                    // "工单等待您的签收和处理.";
                    String msg = SendTaskUtil.SENDTASK_MODELNAME + " " + bean.getSendtopic()
                            + "工单等待您的处理 " + userinfo.getUserName() + " " + SendSMRMI.MSG_NOTE;
                    logger.info("派单的短信内容:" + msg);
                    SendSMRMI.sendNormalMessage(userinfo.getUserID(), sim, msg, "00");
                }
            }

            log(request, "派单管理", "指派任务");
            return forwardInfoPage(mapping, request, "110102");
        } catch (Exception e) {
            logger.error("保存派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示所有的派单
     */
    public ActionForward showAllSendTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), "110101")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lsendtask = dao.getSendList(userinfo);
            request.getSession().setAttribute("sendlist", lsendtask);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("显示所有的派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示一个派单详细信息
     */
    public ActionForward showOneSendTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110101")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = dao.getOneSendTask(id, userinfo, subid);
            SendTaskBean endorsebean = dao.getEendorseOfSendTask(subid);

            // guixy by 2008-11-30
            // List deptList = dao.getAcceptDept(id);
            // 用于标志是增加子表的新数据，还是旧表数据0:表示新数据，1：表示旧数据
            // if (deptList.size() != 0) {
            // if(((DynaBean)deptList.get(0)).get("deptid") != null) {
            // request.setAttribute("newOldFlg", "0");
            // } else {
            // request.setAttribute("newOldFlg", "1");
            // }
            // } else {
            request.setAttribute("newOldFlg", "1");

            // }
            // request.getSession().setAttribute( "deptlist", deptList);

            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("type", "10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示一个派单详细信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 条件查询显示
     */
    public ActionForward showQuerySendTask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110103")) {
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
        if (!CheckPower.checkPower(request.getSession(), "110103")) {
            return mapping.findForward("powererror");
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List lsendtask = dao.querySendList(userinfo, bean, request.getSession());
            request.getSession().setAttribute("sendlist", lsendtask);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("条件查找派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示修改后的查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("sendTaskQueryCon");
        try {
            List lsendtask = dao.queryAfterMod(sql);
            request.getSession().setAttribute("sendlist", lsendtask);
            request.getSession().setAttribute("type", "1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("返回查询派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 修改一个派单信息显示
     */
    public ActionForward showOneToUp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110104")) {
            return mapping.findForward("powererror");
        }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        try {
            SendTaskBean bean = dao.getOneSendTask(id, userinfo, subid);

            SendTaskBean endorsebean = dao.getEendorseOfSendTask(subid);
            List ldept = dao.getAcceptdept(userinfo);
            List luser = dao.getAcceptUser(userinfo);

            // guixy by 2008-11-29
            // List accDeptList = dao.getAcceptDept(id);
            // 用于标志是增加子表的新数据，还是旧表数据0:表示新数据，1：表示旧数据
            // if (accDeptList.size() != 0) {
            // if(((DynaBean)accDeptList.get(0)).get("deptid") != null) {
            // request.setAttribute("newOldFlg", "0");
            // } else {
            // request.setAttribute("newOldFlg", "1");
            // }
            // } else {
            request.setAttribute("newOldFlg", "1");

            // }

            // request.getSession().setAttribute( "deptList", accDeptList);

            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("deptinfo", ldept);
            request.getSession().setAttribute("userinfo", luser);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("type", "4");

            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("修改一个派单信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 执行修改派单
     */
    public ActionForward upSendtask(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "110104")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        SendTaskBean bean = (SendTaskBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            bean.setSenduserid(userinfo.getUserID());
            bean.setSenddeptid(userinfo.getDeptID());
            bean.setRegionid(userinfo.getRegionID());

            ArrayList fileIdList = new ArrayList();
            String[] delfileid = request.getParameterValues("delfileid"); // 要删除的文件id号数组
            // 组合新的附件串
            StringTokenizer st = new StringTokenizer(bean.getSendacce(), ",");
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
            // modify by guixy 2009-3-16
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
                bean.setSendacce(fileIdListStr);
            }
            // ======================

            // guixy add by 2008-11-28
            // String[] deptId = request.getParameterValues( "acceptdeptid" );
            // String[] userId = request.getParameterValues( "acceptuserid" );

            if (!dao.upSendTask(bean)) {
                // return forwardErrorPage(mapping, request, "110104e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "110104e", backUrl);
            }

            String sim = dao.getSendPhone(bean.getAcceptuserid());
            if (sim != null && !bean.getResult().equals("待签收")) {
                String msg = "工单通知：有一" + bean.getSendtype() + "工单等待您的签收和处理.";
                SendSMRMI.sendNormalMessage(userinfo.getUserID(), sim, msg, "00");
            }

            log(request, "派单管理", "修改指派任务");
            // return forwardInfoPage(mapping, request, "110104");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "110104", backUrl);
        } catch (Exception e) {
            logger.error("修改派单出错:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * 导出派单信息一览表
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
    public ActionForward exportSendTaskResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" 创建dao");
            List list = (List) request.getSession().getAttribute("sendlist");
            logger.info("得到list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportSendTaskResult(list, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("导出派单信息一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 打开查询统计页面
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
    public ActionForward showQueryTotal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // if( !CheckPower.checkPower( request.getSession(), "110101" ) ){
        // return mapping.findForward( "powererror" );
        // }
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List ldept = dao.getAcceptdept(userinfo);
            List luser = dao.getAcceptUser(userinfo);
            request.getSession().setAttribute("deptinfo", ldept);
            request.getSession().setAttribute("userinfo", luser);
            request.getSession().setAttribute("type", "1");
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("显示查询统计页面出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 进行查询统计操作
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
    public ActionForward doQueryToTotal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        SendTaskBean bean = (SendTaskBean) form;
        if ("total".equals(bean.getWorkstate())) {
            request.getSession().setAttribute("overflg", "1");
        }
        try {
            List allTotalList = dao.allQueryTotalList(userinfo, bean, request.getSession());
            // 对验证的处理时限去掉周六周日的处理 2008-12-26
            DynaBean rowBean = null;
            String replytime;
            String validatetime;
            float validovertime;
            int weeks;
            int weekNum;
            for (int i = 0; i < allTotalList.size(); i++) {
                weeks = 0;
                weekNum = 0;
                rowBean = (DynaBean) allTotalList.get(i);
                replytime = String.valueOf(rowBean.get("replytime"));
                validatetime = String.valueOf(rowBean.get("validatetime"));
                validovertime = Float.parseFloat(rowBean.get("validovertime").toString());

                // 取得跨周数
                weeks = SendTaskUtil.getWeeks(replytime, validatetime);
                if (validovertime != 0 && Math.abs(validovertime) > 48) {
                    if (weeks != 0) {
                        weekNum = SendTaskUtil.getWeekNum(replytime, validatetime);
                        if (validovertime > 0) {
                            validovertime = validovertime - 48 * weekNum;
                        } else {
                            validovertime = validovertime + 48 * weekNum;
                        }
                        rowBean.set("validovertime", new BigDecimal(
                                ((int) validovertime * 10) / 10.0));
                    }
                } else if (weeks == 1 && validovertime != 0 && Math.abs(validovertime) < 48) {
                    rowBean.set("validovertime", new BigDecimal("0"));
                }
            }
            // 对验证的处理时限去掉周六周日的处理 2008-12-26

            request.getSession().setAttribute("totallist", allTotalList);
            request.getSession().setAttribute("type", "2");
            super.setPageReset(request);
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("显示派单出错:" + e.getMessage());
            e.printStackTrace();
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示一个已经经过验证的派单详细信息
     */
    public ActionForward showOneToTatal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // if( !CheckPower.checkPower( request.getSession(), "110402" ) ){
        // return mapping.findForward( "powererror" );
        // }

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String id = request.getParameter("id");
        String subid = request.getParameter("subid");
        String flg = request.getParameter("flg");
        try {
            SendTaskBean bean = endorseDao.getOneSendTask(id, userinfo, subid);
            SendTaskBean endorsebean = dao.getEendorseOfSendTask(subid);
            SendTaskBean replybean = replyDao.getOneSendTask(subid, userinfo);
            SendTaskBean valibean = valiDao.getOneValidate(subid, userinfo);
            SendTaskBean info = valiDao.getInfo(id, userinfo);
            request.getSession().setAttribute("flg", flg);
            request.getSession().setAttribute("endorsebean", endorsebean);
            request.getSession().setAttribute("bean", bean);
            request.getSession().setAttribute("replybean", replybean);
            request.getSession().setAttribute("valibean", valibean);
            request.getSession().setAttribute("info", info);

            request.getSession().setAttribute("type", "3");
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("显示一个已经经过验证的派单详细信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 查询统计导出信息一览表
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
    public ActionForward exportSendTotalResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" 创建dao");
            List list = (List) request.getSession().getAttribute("totallist");
            logger.info("得到list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportQuerytotalResult(list, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("查询统计导出信息一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 个人工单信息导出信息一览表
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
    public ActionForward exportSendPersonTotalResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" 创建dao");
            List list = (List) request.getSession().getAttribute("totallist");
            String queryFlg = String.valueOf(request.getSession().getAttribute("queryflg"));
            String username = String.valueOf(request.getSession().getAttribute("username"));
            SendTaskBean bean = (SendTaskBean) request.getSession().getAttribute("querycon");
            String dataCount = String.valueOf(request.getSession().getAttribute("datacount"));
            logger.info("得到list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportQueryPersontotalResult(list, queryFlg, username, bean, dataCount,
                    response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("个人工单导出信息一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 个人工单数量统计导出信息一览表
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
    public ActionForward exportPersonNumTotalResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" 创建dao");
            List list = (List) request.getSession().getAttribute("numtotallist");
            SendTaskBean querybean = (SendTaskBean) request.getSession().getAttribute("querycon");
            logger.info("得到list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportPersonnumtotalResult(list, querybean, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("个人工单导出信息一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 打开个人工单统计页面
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
    public ActionForward showQueryPersonTotal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        // UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
        // "LOGIN_USER" );

        // 2009-1-5添加的取部门信息和人员信息
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List ldept = dao.getAcceptdept(userinfo);
            List luser = dao.getAcceptUser(userinfo);
            request.getSession().setAttribute("deptinfo", ldept);
            request.getSession().setAttribute("userinfo", luser);

            request.getSession().setAttribute("type", "11");
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("显示查询统计页面出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 进行个人工单数量查询统计操作
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
    public ActionForward doQueryPersonToTotal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
        // "LOGIN_USER" );
        // SendTaskBean bean = ( SendTaskBean )form;
        //  
        // try{
        // List personList = dao.QueryPersonList(userinfo, bean,
        // request.getSession());
        // List personCountList = dao.personTotal(userinfo, bean,
        // request.getSession());
        // request.getSession().setAttribute("personcountList",
        // personCountList);
        // request.getSession().setAttribute("totallist", personList);
        // request.getSession().setAttribute( "type", "12" );
        // return mapping.findForward( "querytotal" );
        // }
        // catch( Exception e ){
        // logger.error( "显示派单出错:" + e.getMessage() );
        // return forwardErrorPage( mapping, request, "error" );
        // }

        try {
            SendTaskBean bean = (SendTaskBean) form;
            List personNumList = dao.personTotalNum(bean, request.getSession());
            // 对验证超时的一个处理，排除那些去掉周六周日没有超时的 add by guixy 2008-1-8
            String overtimeva;
            DynaBean rowBean;
            String userid;
            for (int i = 0; i < personNumList.size(); i++) {
                rowBean = (DynaBean) personNumList.get(i);
                overtimeva = String.valueOf(rowBean.get("overtimevalid"));
                userid = String.valueOf(rowBean.get("userid"));
                if (!"0".equals(overtimeva) || !"null".equals(overtimeva)) {
                    // 取得sql计算出来的超时记录
                    List personList = dao.getPersonTaskInfo(bean, "22", userid, request
                            .getSession());
                    DynaBean rowBean2 = null;
                    String replytime;
                    float overtimenum;
                    String workstate = null;
                    String validatetime;
                    int weeks;
                    // 去除中间的周六周日没有超时的
                    for (int j = 0; j < personList.size(); j++) {
                        weeks = 0;
                        rowBean2 = (DynaBean) personList.get(j);
                        replytime = String.valueOf(rowBean2.get("replytime"));
                        workstate = String.valueOf(rowBean2.get("workstate"));
                        if ("待验证".equals(workstate)) {
                            // 待验证的情况
                            overtimenum = Float.parseFloat(rowBean2.get("overtimenum").toString());
                            // 取得跨周数
                            weeks = SendTaskUtil.getWeeks(replytime);
                            if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                                personList.remove(j);
                            }
                        } else {
                            // 已验证过的情况
                            validatetime = String.valueOf(rowBean2.get("validatetime"));
                            overtimenum = Float.parseFloat(rowBean2.get("overtimenum").toString());
                            // 取得跨周数
                            weeks = SendTaskUtil.getWeeks(replytime, validatetime);
                            if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                                personList.remove(j);
                            }
                        }
                    }

                    rowBean.set("overtimevalid", new BigDecimal(String.valueOf(personList.size())));
                }
            }
            // 对验证超时的一个处理，排除那些去掉周六周日没有超时的 add by guixy 2008-1-8

            request.getSession().setAttribute("numtotallist", personNumList);
            request.getSession().setAttribute("querycon", bean);
            request.getSession().setAttribute("type", "112");
            super.setPageReset(request);
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("个人工单数量查询统计操作出错:" + e.getMessage());
            e.printStackTrace();
            return forwardErrorPage(mapping, request, "error");
        }

    }

    /**
     * 显示个人工单信息
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
    public ActionForward ShowPersonTaskInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // 取得查询统计条件
        SendTaskBean bean = (SendTaskBean) request.getSession().getAttribute("querycon");
        // 标志
        String queryFlg = request.getParameter("flg");
        String userid = request.getParameter("userid");
        String username = request.getParameter("username");

        try {
            List personList = dao.getPersonTaskInfo(bean, queryFlg, userid, request.getSession());
            // 对验证的处理时限去掉周六周日的处理 2009-1-7
            if ("3".equals(queryFlg) || "13".equals(queryFlg) || "22".equals(queryFlg)) {
                DynaBean rowBean = null;
                String replytime;
                float overtimenum;
                String workstate = null;
                String validatetime;
                // 这个是取到开始日期到结束时间跨几周
                int weeks;
                // 这个是取到开始日期+2到结束时间跨几周（因为sql语句中取得的差值是开始日期+2的情况）
                int weekNum;
                for (int i = 0; i < personList.size(); i++) {
                    weeks = 0;
                    weekNum = 0;
                    rowBean = (DynaBean) personList.get(i);
                    replytime = String.valueOf(rowBean.get("replytime"));
                    workstate = String.valueOf(rowBean.get("workstate"));
                    if ("待验证".equals(workstate)) {
                        // 待验证的情况
                        overtimenum = Float.parseFloat(rowBean.get("overtimenum").toString());
                        // 取得跨周数
                        weeks = SendTaskUtil.getWeeks(replytime);
                        if (overtimenum != 0 && Math.abs(overtimenum) > 48) {
                            if (weeks != 0) {
                                weekNum = SendTaskUtil.getWeekNum(replytime);
                                if (overtimenum > 0) {
                                    overtimenum = overtimenum - 48 * weekNum;
                                } else {
                                    overtimenum = overtimenum + 48 * weekNum;
                                }
                                rowBean.set("overtimenum", new BigDecimal(String
                                        .valueOf(((int) (overtimenum * 10)) / 10.0)));
                            }
                        } else if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                            // rowBean.set("overtimenum", new BigDecimal("0"));
                            if ("22".equals(queryFlg)) {
                                personList.remove(i);
                            } else {
                                rowBean.set("overtimenum", new BigDecimal("0"));
                            }

                        }
                    } else {
                        // 已验证过的情况
                        validatetime = String.valueOf(rowBean.get("validatetime"));
                        overtimenum = Float.parseFloat(rowBean.get("overtimenum").toString());
                        // 取得跨周数
                        weeks = SendTaskUtil.getWeeks(replytime, validatetime);
                        if (overtimenum != 0 && Math.abs(overtimenum) > 48) {
                            if (weeks != 0) {
                                weekNum = SendTaskUtil.getWeekNum(replytime, validatetime);
                                if (overtimenum > 0) {
                                    overtimenum = overtimenum - 48 * weekNum;
                                } else {
                                    overtimenum = overtimenum + 48 * weekNum;
                                }
                                rowBean.set("overtimenum", new BigDecimal(String
                                        .valueOf(((int) (overtimenum * 10)) / 10.0)));
                            }
                        } else if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                            // rowBean.set("overtimenum", new BigDecimal("0"));
                            if ("22".equals(queryFlg)) {
                                personList.remove(i);
                            } else {
                                rowBean.set("overtimenum", new BigDecimal("0"));
                            }
                        }
                    }
                }
            }
            // 对验证的处理时限去掉周六周日的处理 2009-1-7

            request.getSession().setAttribute("totallist", personList);
            request.getSession().setAttribute("type", "12");
            request.getSession().setAttribute("queryflg", queryFlg);
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("datacount", personList.size() + "");
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("显示个人工单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 打开部门工单统计页面
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
    public ActionForward showQueryDeptTotal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        try {
            List ldept = dao.getAcceptdept(userinfo);
            request.getSession().setAttribute("deptinfo", ldept);
            request.getSession().setAttribute("type", "13");
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("显示查询统计页面出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 部门工单统计查询操作
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
    public ActionForward doQueryDeptTotal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        SendTaskBean bean = (SendTaskBean) form;

        try {
            List deptTotalList = dao.deptTotal(userinfo, bean, request.getSession());
            request.getSession().setAttribute("deptTotalList", deptTotalList);
            request.getSession().setAttribute("deptquerybean", bean);
            request.getSession().setAttribute("type", "14");
            super.setPageReset(request);
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("显示派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 导出部门工单统计信息一览表
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
    public ActionForward exportDeptTotalResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" 创建dao");
            List list = (List) request.getSession().getAttribute("deptTotalList");
            SendTaskBean deptquerybean = (SendTaskBean) request.getSession().getAttribute(
                    "deptquerybean");
            logger.info("得到list");
            ExportDao exdao = new ExportDao();
            if (exdao.exportDepttotalResult(list, deptquerybean, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("导出派单信息一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 查询统计菜单查询后点详细页面的返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryTotalAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        try {
            String flg = request.getParameter("flg");
            List totalList = null;
            String sql = null;
            if ("1".equals(flg)) {
                // 查询统计
                sql = (String) request.getSession().getAttribute("alltotalsql");
                totalList = dao.queryAfterMod(sql);

                // 对验证的处理时限去掉周六周日的处理 2009-1-7
                DynaBean rowBean = null;
                String replytime;
                String validatetime;
                float validovertime;
                int weeks;
                int weekNum;
                for (int i = 0; i < totalList.size(); i++) {
                    weeks = 0;
                    weekNum = 0;
                    rowBean = (DynaBean) totalList.get(i);
                    replytime = String.valueOf(rowBean.get("replytime"));
                    validatetime = String.valueOf(rowBean.get("validatetime"));
                    validovertime = Float.parseFloat(rowBean.get("validovertime").toString());

                    // 取得跨周数
                    weeks = SendTaskUtil.getWeeks(replytime, validatetime);
                    if (validovertime != 0 && Math.abs(validovertime) > 48) {
                        if (weeks != 0) {
                            weekNum = SendTaskUtil.getWeekNum(replytime, validatetime);
                            if (validovertime > 0) {
                                validovertime = validovertime - 48 * weekNum;
                            } else {
                                validovertime = validovertime + 48 * weekNum;
                            }
                            rowBean.set("validovertime", new BigDecimal(String
                                    .valueOf(((int) (validovertime * 10)) / 10.0)));
                        }
                    } else if (weeks == 1 && validovertime != 0 && Math.abs(validovertime) < 48) {
                        rowBean.set("validovertime", new BigDecimal("0"));
                    }
                }
                // 对验证的处理时限去掉周六周日的处理 2009-1-7

                request.getSession().setAttribute("type", "2");
                request.getSession().setAttribute("totallist", totalList);
            } else if ("2".equals(flg)) {
                // 个人工单信息
                // sql = (String)
                // request.getSession().getAttribute("persontotalsql");
                // personCountList = dao.queryAfterMod(sql);
                sql = (String) request.getSession().getAttribute("personsql");
                totalList = dao.queryAfterMod(sql);

                // 对验证的处理时限去掉周六周日的处理 2009-1-7
                String queryFlg = String.valueOf(request.getSession().getAttribute("queryflg"));
                if ("3".equals(queryFlg) || "13".equals(queryFlg) || "22".equals(queryFlg)) {
                    DynaBean rowBean = null;
                    String replytime;
                    float overtimenum;
                    String workstate = null;
                    String validatetime;
                    int weeks;
                    int weekNum;
                    for (int i = 0; i < totalList.size(); i++) {
                        weekNum = 0;
                        weeks = 0;
                        rowBean = (DynaBean) totalList.get(i);
                        replytime = String.valueOf(rowBean.get("replytime"));
                        workstate = String.valueOf(rowBean.get("workstate"));
                        if ("待验证".equals(workstate)) {
                            // 待验证的情况
                            overtimenum = Float.parseFloat(rowBean.get("overtimenum").toString());
                            // 取得跨周数
                            weeks = SendTaskUtil.getWeeks(replytime);
                            if (overtimenum != 0 && Math.abs(overtimenum) > 48) {
                                if (weeks != 0) {
                                    weekNum = SendTaskUtil.getWeekNum(replytime);
                                    if (overtimenum > 0) {
                                        overtimenum = overtimenum - 48 * weekNum;
                                    } else {
                                        overtimenum = overtimenum + 48 * weekNum;
                                    }

                                    rowBean.set("overtimenum", new BigDecimal(String
                                            .valueOf(((int) (overtimenum * 10)) / 10.0)));
                                }
                            } else if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                                if ("22".equals(queryFlg)) {
                                    totalList.remove(i);
                                } else {
                                    rowBean.set("overtimenum", new BigDecimal("0"));
                                }
                            }

                        } else {
                            // 已验证过的情况
                            validatetime = String.valueOf(rowBean.get("validatetime"));
                            overtimenum = Float.parseFloat(rowBean.get("overtimenum").toString());
                            // 取得跨周数
                            weeks = SendTaskUtil.getWeeks(replytime, validatetime);
                            if (overtimenum != 0 && Math.abs(overtimenum) > 48) {
                                if (weeks != 0) {
                                    weekNum = SendTaskUtil.getWeekNum(replytime, validatetime);
                                    if (overtimenum > 0) {
                                        overtimenum = overtimenum - 48 * weekNum;
                                    } else {
                                        overtimenum = overtimenum + 48 * weekNum;
                                    }

                                    rowBean.set("overtimenum", new BigDecimal(String
                                            .valueOf(((int) (overtimenum * 10)) / 10.0)));
                                }
                            } else if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                                if ("22".equals(queryFlg)) {
                                    totalList.remove(i);
                                } else {
                                    rowBean.set("overtimenum", new BigDecimal("0"));
                                }
                            }
                        }
                    }
                }
                // 对验证的处理时限去掉周六周日的处理 2009-1-7

                request.getSession().setAttribute("type", "12");
                // request.getSession().setAttribute("personcountList",
                // personCountList);
                request.getSession().setAttribute("totallist", totalList);
            } else {
                // 个人工单统计
                sql = (String) request.getSession().getAttribute("personnumtotalsql");
                totalList = dao.queryAfterMod(sql);
                SendTaskBean bean = (SendTaskBean) request.getSession().getAttribute("querycon");
                // 对验证超时的一个处理，排除那些去掉周六周日没有超时的 add by guixy 2008-1-8
                String overtimeva;
                DynaBean rowBean;
                String userid;
                for (int i = 0; i < totalList.size(); i++) {
                    rowBean = (DynaBean) totalList.get(i);
                    overtimeva = String.valueOf(rowBean.get("overtimevalid"));
                    userid = String.valueOf(rowBean.get("userid"));
                    if (!"0".equals(overtimeva) || !"null".equals(overtimeva)) {
                        // 取得sql计算出来的超时记录
                        List personList = dao.getPersonTaskInfo(bean, "22", userid, request
                                .getSession());
                        DynaBean rowBean2 = null;
                        String replytime;
                        float overtimenum;
                        String workstate = null;
                        String validatetime;
                        int weeks;
                        // 去除中间的周六周日没有超时的
                        for (int j = 0; j < personList.size(); j++) {
                            weeks = 0;
                            rowBean2 = (DynaBean) personList.get(j);
                            replytime = String.valueOf(rowBean2.get("replytime"));
                            workstate = String.valueOf(rowBean2.get("workstate"));
                            if ("待验证".equals(workstate)) {
                                // 待验证的情况
                                overtimenum = Float.parseFloat(rowBean2.get("overtimenum")
                                        .toString());
                                // 取得跨周数
                                weeks = SendTaskUtil.getWeeks(replytime);
                                if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                                    personList.remove(j);
                                }
                            } else {
                                // 已验证过的情况
                                validatetime = String.valueOf(rowBean2.get("validatetime"));
                                overtimenum = Float.parseFloat(rowBean2.get("overtimenum")
                                        .toString());
                                // 取得跨周数
                                weeks = SendTaskUtil.getWeeks(replytime, validatetime);
                                if (weeks == 1 && overtimenum != 0 && Math.abs(overtimenum) < 48) {
                                    personList.remove(j);
                                }
                            }
                        }

                        rowBean.set("overtimevalid", new BigDecimal(String.valueOf(personList
                                .size())));
                    }
                }
                // 对验证超时的一个处理，排除那些去掉周六周日没有超时的 add by guixy 2008-1-8
                request.getSession().setAttribute("type", "112");
                // request.getSession().setAttribute("personcountList",
                // personCountList);
                request.getSession().setAttribute("querycon", bean);

                request.getSession().setAttribute("numtotallist", totalList);
            }

            super.setPageReset(request);
            return mapping.findForward("querytotal");
        } catch (Exception e) {
            logger.error("返回查询派单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public static void main(String[] args) {
        System.out.println(new BigDecimal(String.valueOf(((int) (-0.889994 * 10)))));
    }

}
