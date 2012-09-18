package com.cabletech.linecut.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.DefaultCategoryDataset;

import com.cabletech.baseinfo.dao.UserInfoDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.linecut.beans.LineCutBean;
import com.cabletech.linecut.property.Property;
import com.cabletech.linecut.services.LineCutReService;
import com.cabletech.power.CheckPower;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.UploadUtil;

public class LineCutReAction extends BaseDispatchAction {
    private LineCutReService service = new LineCutReService();

    private static final String STAT_COUNT = "1"; // 统计割接次数

    private static final String STAT_TIME = "2";// 统计割接时间

    private static final String BY_LEVEL = "1";// 按线路级别统计

    private static final String BY_TYPE = "2";// 按割接类型统计

    private static Logger logger = Logger.getLogger(LineCutReAction.class.getName());

    /**
     * 添加申请显示
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
     * @throws Exception
     */
    public ActionForward addShow(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), "30101")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // 如果是移动公司是不允许的
            return forwardErrorPage(mapping, request, "partstockerror");
        }
        /*
         * List lLine = service.getLineList(userinfo); List lSubLine =
         * service.getSubLineList(userinfo); request.setAttribute("line",lLine);
         * request.setAttribute("subline",lSubLine);
         */
        request.getSession().setAttribute("type", "r2");
        return mapping.findForward("success");
    }

    /**
     * 获取线路级别
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getLineLevle(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List lineLevelList = service.getLineLevle();
        request.setAttribute("lineLevelList", lineLevelList);
        return mapping.findForward("lineLevel");
    }

    /**
     * 根据用户，线路级别查找线路
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getLineByLevelId(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/json; charset=GBK");
        String levelId = request.getParameter("levelId");
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List lineList = service.getLineByLevel(userinfo, levelId);

        JSONArray ja = JSONArray.fromObject(lineList);
        PrintWriter out = response.getWriter();
        out.write(ja.toString());
        out.flush();
        return null;
    }

    /**
     * 根据用户，线路查找线段
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getClewByLineId(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/json; charset=GBK");
        String lineId = request.getParameter("lineId");
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List subLineList = service.getSubLineByLineId(userinfo, lineId);
        JSONArray ja = JSONArray.fromObject(subLineList);
        PrintWriter out = response.getWriter();
        out.write(ja.toString());
        return null;
    }

    /**
     * 执行添加申请
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
    public ActionForward addRe(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30101")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // 如果是移动公司是不允许的
            return forwardErrorPage(mapping, request, "partstockerror");
        }

        try {
            LineCutBean bean = (LineCutBean) form;
            userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
            bean.setContractorid(userinfo.getDeptID());
            bean.setReuserid(userinfo.getUserID());

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
            // ArrayList fileIdList = new ArrayList();
            // for (int i = 0; i < attachments.size(); i++) {
            // UploadFile uploadFile = (UploadFile) attachments.get(i);
            // FormFile file = uploadFile.getFile();
            // if (file == null) {
            // logger.error("file is null");
            // } else {
            // //将文件存储到服务器并将路径写入数据库，返回记录ID
            // fileId = SaveUploadFile.saveFile(file);
            // if (fileId != null) {
            // fileIdList.add(fileId);
            // }
            // }
            // }
            // 处理上传文件结束=======================================
            // 获取ID字符串列表(以逗号分隔的文件ID字符串)======================
            String fileIdListStr = UploadUtil.getFileIdList(fileIdList);
            if (fileIdListStr != null) {
                bean.setReacce(fileIdListStr);
            }
            // ======================
            String updoc = request.getParameter("UPDOC");
            System.out.println(bean.getCutType());
            System.out.println(updoc + "资料是否需要修改");
            if (!service.addInfo(bean, updoc)) {
                return forwardErrorPage(mapping, request, "30102e");
            }
            // 发送短信
            if (request.getSession().getAttribute("isSendSm").equals("send")) {
                String objectman = request.getParameter("phone");
                if (objectman != null && !objectman.equals("")) {
                    String msg = Property.LINE_CUT_MODULE
                            + request.getSession().getAttribute("LOGIN_USER_DEPT_NAME")
                            + "有新的割接申请等待您的审批  申请人：" + userinfo.getUserName() + SendSMRMI.MSG_NOTE;
                    SendSMRMI.sendNormalMessage(userinfo.getUserID(), objectman, msg, "00");
                    logger.info(msg);
                }
            }
            log(request, "割接管理", "添加割接申请");
            return forwardInfoPage(mapping, request, "30102");
        } catch (Exception e) {
            logger.error("添加申请异常:" + e.getMessage());
            log(request, "添加线路割接申请 ::" + e.getMessage(), "割接申请");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示所有申请单
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
    public ActionForward showAllRe(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30102")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // 如果是移动公司是不允许的
            return forwardErrorPage(mapping, request, "partstockerror");
        }

        try {
            List lReqInfo = service.getAllOwnRe(request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "r1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示所有申请单信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示一个申请单的详细信息
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
    public ActionForward showOneInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30101")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // 如果是移动公司是不允许的
            return forwardErrorPage(mapping, request, "partstockerror");
        }
        String reid = request.getParameter("reid"); // 获得要修改的申请单id
        String apphavemore = "0";
        try {
            // 获得申请单基本信息
            LineCutBean reqinfo = new LineCutBean();
            List reApprove = service.getReApprove(reid);
            if (service.valiApprove(reid)) {// 已经审批过.
                reqinfo = service.getOneUseForDMod(reid);
                if (reApprove.size() > 0)// 多次审批
                    apphavemore = "1";
            } else {// 尚未审批过
                reqinfo = service.getOneUseMod(reid);
            }

            request.setAttribute("apphavemore", apphavemore);
            request.setAttribute("reapprove", reApprove);

            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "r10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("在显示详细中出现错误:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 修改显示
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
    public ActionForward upshow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30104")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // 如果是移动公司是不允许的
            return forwardErrorPage(mapping, request, "partstockerror");
        }
        LineCutBean reqinfo = new LineCutBean(); // 传回页面的申请单基本信息
        String reid = request.getParameter("reid"); // 获得要修改的申请单id

        try {
            // 能否修改
            if (!service.valiCanUp(reid)) {
                return forwardErrorPage(mapping, request, "30104e");
            }
            reqinfo = service.getOneUseMod(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "r4");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("在修改显示中出现错误:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 执行修改
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
    public ActionForward doUp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        // 权限检查
        if (!CheckPower.checkPower(request.getSession(), "30104")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // 如果是移动公司是不允许的
            // return forwardErrorPage(mapping, request, "partstockerror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "partstockerror", backUrl);
        }
        try {
            LineCutBean bean = (LineCutBean) form;
            userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
            bean.setContractorid(userinfo.getDeptID());
            bean.setReuserid(userinfo.getUserID());

            ArrayList fileIdList = new ArrayList();
            String[] delfileid = request.getParameterValues("delfileid"); // 要删除的文件id号数组
            // 组合新的附件串
            StringTokenizer st = new StringTokenizer(bean.getReacce(), ",");
            while (st.hasMoreTokens()) {
                fileIdList.add(st.nextToken());
            }
            if (delfileid != null) {
                for (int i = 0; i < delfileid.length; i++) {
                    fileIdList.remove(delfileid[i]);
                }
            }
            // 开始处理上传文件================================
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

            // List attachments = bean.getAttachments();
            // String fileId;
            // for (int i = 0; i < attachments.size(); i++) {
            // UploadFile uploadFile = (UploadFile) attachments.get(i);
            // FormFile file = uploadFile.getFile();
            // if (file == null) {
            // logger.error("file is null");
            // } else {
            // //将文件存储到服务器并将路径写入数据库，返回记录ID
            // fileId = SaveUploadFile.saveFile(file);
            // if (fileId != null) {
            // fileIdList.add(fileId);
            // }
            // }
            // }
            // 处理上传文件结束=======================================
            // 获取ID字符串列表(以逗号分隔的文件ID字符串)======================
            String fileIdListStr = UploadUtil.getFileIdList(fileIdList);
            if (fileIdListStr != null) {
                bean.setReacce(fileIdListStr);
            }
            // ======================
            if (!service.doUp(delfileid, bean)) {
                // return forwardErrorPage(mapping, request, "30104ee");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "30104ee", backUrl);
            }
            log(request, "割接管理", "更新割接申请");
            // return forwardInfoPage(mapping, request, "30104");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "30104", backUrl);
        } catch (Exception e) {
            logger.error("更新申请异常:" + e.getMessage());
            log(request, "更新线路割接申请 ::" + e.getMessage(), "割接申请");
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * 执行删除一个申请单
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
    public ActionForward doDel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        // 权限检查
        if (!CheckPower.checkPower(request.getSession(), "30105")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // 如果是移动公司是不允许的
            // return forwardErrorPage(mapping, request, "partstockerror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "partstockerror", backUrl);
        }
        try {
            String reid = (String) request.getParameter("reid");
            String reacce = (String) request.getParameter("reacce");
            if (!service.valiCanUp(reid)) { // 检查能否删除,能删除
                // return forwardErrorPage(mapping, request, "30105e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "30105e", backUrl);
            }
            if (!service.doDel(reacce, reid)) {
                // return forwardErrorPage(mapping, request, "error");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
            }
            // return forwardInfoPage(mapping, request, "30105");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "30105", backUrl);
        } catch (Exception e) {
            logger.error("在删除申请单中出错:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * 综合查询显示
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
    public ActionForward queryShow(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30103")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // 如果是移动公司是不允许的
            return forwardErrorPage(mapping, request, "partstockerror");
        }
        try {
            String contractorid = (String) request.getSession().getAttribute("LOGIN_USER_DEPT_ID");
            // 获取线路级别
            List levelList = service.getLineLevle();
            request.setAttribute("linelevelList", levelList);

            // 获得操作人列表
            List lusers = service.getAllUsers(contractorid);
            request.setAttribute("lusers", lusers);

            // 获得割接名称列表
            /*
             * List lnames = service.getAllNames( contractorid );
             * request.setAttribute( "lnames", lnames );
             */

            // 获得割接原因列表
            List lreasons = service.getAllReasons(contractorid);
            request.setAttribute("lreasons", lreasons);

            // 获得割接地点列表
            List laddresss = service.getAllAddresss(contractorid);
            request.setAttribute("laddresss", laddresss);

            // 获得受影响系统列表
            List lefsystem = service.getAllEfsystems(contractorid);
            request.setAttribute("lefsystem", lefsystem);

            // 获得涉及光缆段
            /*
             * List lsublines = service.getAllSublineids( contractorid );
             * request.setAttribute( "lsublines", lsublines );
             */

            request.getSession().setAttribute("type", "r3");
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("综合查询显示异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 执行综合查询
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
    public ActionForward doQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30103")) {
            return mapping.findForward("powererror");
        }

        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // 如果是移动公司是不允许的
            return forwardErrorPage(mapping, request, "partstockerror");
        }

        LineCutBean bean = (LineCutBean) form;
        String contractorid = (String) request.getSession().getAttribute("LOGIN_USER_DEPT_ID");
        bean.setContractorid(contractorid);
        try {

            // List lReqInfo = service.getAllOwnReForSearch(bean,
            // request.getSession());
            List lReqInfo = service.getConditionsReForSearch(bean, request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "r1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("执行综合查询异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward doQueryAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("lcAuQueryCon");// lcQueryCon
        if (sql != null && sql.trim().length() != 0) {
            List lReqInfo = service.doQueryAfterMod(sql);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "r1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("返回综合查询结果异常:原因：session过期");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // ===========================审批管理=============================//

    /**
     * 显示所有待审批申请单信息
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
     * @throws Exception
     */
    public ActionForward auditShow(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        LineCutBean bean = (LineCutBean) form;
        if (!CheckPower.checkPower(request.getSession(), "30201")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // 如果是代维单位是不允许的
            return forwardErrorPage(mapping, request, "partauditerror");
        }
        try {
            List lReqInfo = service.getAllReForAu(userinfo.getRegionID());
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "reforAu1");

            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示所有待审批申请单信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }

    }

    /**
     * 显示一个待审批的申请单的详细信息
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
    public ActionForward showOneInfoForAu(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30201")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        System.out.println(userinfo.getDeptype() + "单位类别");
        if (userinfo.getDeptype().equals("2")) { // 如果是代维单位是不允许的
            return forwardErrorPage(mapping, request, "partauditerror");
        }
        String reid = request.getParameter("reid"); // 获得要审批的申请单id

        try {
            // 获得要审批申请单基本信息
            LineCutBean reqinfo = service.getOneUseMod(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "au2");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示一个待审批的申请单的详细信息错误:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 执行审批
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
    public ActionForward addAu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30201")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // 如果是代维单位是不允许的
            // return forwardErrorPage(mapping, request, "partauditerror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "partauditerror", backUrl);
        }
        try {
            LineCutBean bean = (LineCutBean) form;
            userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
            bean.setDeptid(userinfo.getDeptID());
            bean.setAudituserid(userinfo.getUserID());

            // 开始处理上传文件================================
            // List attachments = bean.getAttachments();
            // String fileId;
            // ArrayList fileIdList = new ArrayList();
            // for (int i = 0; i < attachments.size(); i++) {
            // UploadFile uploadFile = (UploadFile) attachments.get(i);
            // FormFile file = uploadFile.getFile();
            // if (file == null) {
            // logger.error("file is null");
            // } else {
            // //将文件存储到服务器并将路径写入数据库，返回记录ID
            // fileId = SaveUploadFile.saveFile(file);
            // if (fileId != null) {
            // fileIdList.add(fileId);
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
                bean.setAuditacce(fileIdListStr);
            }
            // ======================
            if (!service.addAuInfo(bean)) {
                // return forwardErrorPage(mapping, request, "30202e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "30202e", backUrl);
            }
            if (request.getSession().getAttribute("isSendSm").equals("send")) {
                String phone = new UserInfoDAOImpl().findById(
                        service.getOneUseMod(bean.getReid()).getReuserid()).getPhone();
                String objectman = phone;
                if (objectman != null && !objectman.equals("")) {
                    // String msg = "";

                    String linename = bean != null ? bean.getName() : "";
                    String audittime = bean != null ? bean.getAudittime() : "";
                    StringBuffer sb = new StringBuffer();
                    sb.append(Property.LINE_CUT_MODULE).append(linename).append("于").append(
                            audittime).append("审批").append(bean.getAuditresult()).append(" 验收人:")
                            .append(userinfo.getUserName()).append(SendSMRMI.MSG_NOTE);
                    SendSMRMI.sendNormalMessage(userinfo.getUserID(), userinfo.getUserName(), sb
                            .toString(), "00");

                    // if (bean.getAuditresult().equals("通过审批")) {
                    // msg = "审批结果：您提交的申请通过审批. 审批人：" + userinfo.getUserName();
                    // } else {
                    // msg = "审批结果：您提交的申请未通过审批," + bean.getAuditremark()
                    // + " . 审批人：" + userinfo.getUserName();
                    // }
                    // SendSMRMI.sendNormalMessage(userinfo.getUserID(),
                    // objectman, msg+SendSMRMI.MSG_NOTE, "00");
                    logger.info("短信内容:" + sb.toString());

                }
            }

            log(request, "割接管理", "审批割接申请");
            // return forwardInfoPage(mapping, request, "30202");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "30202", backUrl);
        } catch (Exception e) {
            logger.error("执行审批异常:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * 显示所有审批单
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
    public ActionForward showAllAu(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30202")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // 如果是代维单位是不允许的
            return forwardErrorPage(mapping, request, "partauditerror");
        }

        try {
            List lReqInfo = service.getAllOwnAu(request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "au1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示所有审批单出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示一个审批单的详细信息
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
    public ActionForward showOneAuInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // if (!CheckPower.checkPower(request.getSession(), "30201")) {
        // return mapping.findForward("powererror");
        // }
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        // if (userinfo.getDeptype().equals("2")) { //如果是代维单位是不允许的
        // return forwardErrorPage(mapping, request, "partauditerror");
        // }
        String reid = request.getParameter("reid"); // 获得要显示的申请单id
        String flg = request.getParameter("flg");
        if (flg == null) {
            request.setAttribute("flg", "1");
        } else {
            request.setAttribute("flg", "2");
        }
        try {
            // 获得申请单基本信息
            LineCutBean reqinfo = service.getOneAuInfoMod(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "au10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("在显示详细中出现错误:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 审批综合查询显示
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
    public ActionForward queryShowForAu(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30203")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // 如果是代维单位是不允许的
            return forwardErrorPage(mapping, request, "partauditerror");
        }
        try {
            String deptid = userinfo.getDeptID();

            // 获取线路级别
            List levelList = service.getLineLevle();
            request.setAttribute("linelevelList", levelList);

            // 获得割接名称列表
            List lnames = service.getAllNamesForAu(deptid);
            request.setAttribute("lnames", lnames);
            // 获得割接原因列表
            List lreasons = service.getAllReasonsForAu(deptid);
            request.setAttribute("lreasons", lreasons);
            // 获得割接地点列表
            List laddresss = service.getAllAddresssForAu(deptid);
            request.setAttribute("laddresss", laddresss);
            // 获得受影响系统列表
            List lefsystem = service.getAllEfsystemsForAu(deptid);
            request.setAttribute("lefsystem", lefsystem);
            // 获得涉及光缆段
            /*
             * List lsublines = service.getAllSublineidsForAu( deptid );
             * request.setAttribute( "lsublines", lsublines );
             */
            // 获得审批人列表
            List lusers = service.getAllUsersForAu(deptid);
            request.setAttribute("lusers", lusers);
            request.getSession().setAttribute("type", "au3");
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("审批综合查询显示异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 执行综合查询
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
    public ActionForward doQueryForAu(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30203")) {
            return mapping.findForward("powererror");
        }

        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // 如果是代维单位是不允许的
            return forwardErrorPage(mapping, request, "partauditerror");
        }

        LineCutBean bean = (LineCutBean) form;
        String deptid = userinfo.getDeptID();
        bean.setDeptid(deptid);
        try {
            List lReqInfo = service.getConditionsReForSearch(bean, request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "au1");
            super.setPageReset(request);

            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("执行综合查询异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward doQueryForAuAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("lcAuQueryCon");
        if (sql != null && sql.trim().length() != 0) {
            List lReqInfo = service.getAllOwnReForAuSearchAfterMod(sql);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "au1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("执行综合查询异常:session过期");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward getNameByClewId(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/json; charset=GBK");
        String sublineid = request.getParameter("sublineId");
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String deptid = userinfo.getDeptID();
        // List sublinename = service.getNameByClewId(sublineid, deptid);

        List sublinename = service.getNameByClewId(sublineid);

        JSONArray ja = JSONArray.fromObject(sublinename);
        try {
            PrintWriter out = response.getWriter();
            out.write(ja.toString());
            out.flush();
            out.close();
            System.out.println(ja.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ActionForward getNameBySublineid(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/json; charset=GBK");
        String sublineid = request.getParameter("sublineId");
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List sublinename = service.getNameBySublineid(sublineid, userinfo);
        JSONArray ja = JSONArray.fromObject(sublinename);
        try {
            PrintWriter out = response.getWriter();
            out.write(ja.toString());
            out.flush();
            out.close();
            System.out.println(ja.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ActionForward getNameBySublineidAndDeptid(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/json; charset=GBK");
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String deptid = userinfo.getDeptID();
        String sublineid = request.getParameter("sublineId");
        List sublinename = service.getNameBySublineidAndDeptid(sublineid, deptid, userinfo);
        JSONArray ja = JSONArray.fromObject(sublinename);
        try {
            PrintWriter out = response.getWriter();
            out.write(ja.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ActionForward getCutNameBySublineid(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/json; charset=GBK");
        String contractorid = (String) request.getSession().getAttribute("LOGIN_USER_DEPT_ID");
        String sublineid = request.getParameter("sublineId");
        List sublinename = service.getCutNameBySublineid(sublineid, contractorid);
        JSONArray ja = JSONArray.fromObject(sublinename);
        try {
            PrintWriter out = response.getWriter();
            out.write(ja.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ActionForward showStatQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        /*
         * List levelList = service.getLineLevle();
         * System.out.println(levelList.size());
         * request.setAttribute("line_level_list", levelList);
         */
        return mapping.findForward("showStatQuery");
    }

    // 统计
    public ActionForward doStatQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        String statType = request.getParameter("statType");
        String statCon = request.getParameter("statCon");
        String rest_query = request.getParameter("rest_query");
        if ("1".equals(rest_query)) {
            if (STAT_COUNT.equals(statType)) {// 如果是数量统计
                if (BY_LEVEL.equals(statCon)) {// 如果是按线路级别来统计
                    return this.doQueryForCountByLevel(mapping, form, request, response);
                }
                if (BY_TYPE.equals(statCon)) {// 如果是按割接类型来统计
                    return this.doQueryForCountByType(mapping, form, request, response);
                }
            } else if (STAT_TIME.equals(statType)) {// 如果是时间统计
                if (BY_LEVEL.equals(statCon)) {// 如果是按线路级别来统计
                    return this.doQueryForTimeByLevel(mapping, form, request, response);
                } else if (BY_TYPE.equals(statCon)) {// 如果是按割接类型来统计
                    return this.doQueryForTimeByType(mapping, form, request, response);
                }
            }
        }
        return null;
    }

    /**
     * 根据割接类型，条件 统计割接次数
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward doQueryForCountByType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        List queryRes = null;
        String begintime = request.getParameter("begintime");
        String endtime = request.getParameter("endtime");
        String condition = "";
        if (begintime != null && !begintime.equals("")) {
            condition = condition + " and lc.essetime >=TO_DATE('" + begintime + "','YYYY-MM-DD')";
        }
        if (endtime != null && !endtime.equals("")) {
            condition = condition + " and lc.essetime <= TO_DATE('" + endtime
                    + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
        }
        queryRes = service.doStatQueryForCountByCutType(condition);
        request.getSession().setAttribute("queryRes", queryRes);
        return mapping.findForward("typeForCount");
    }

    private void getReportForCountByLevel(List statList, HttpServletRequest request)
            throws IOException {
        DefaultCategoryDataset dataset = null;
        DynaBean bean = null;
        if (statList != null && statList.size() != 0) {
            dataset = new DefaultCategoryDataset();
            for (int i = 0; i < statList.size(); i++) {
                bean = (DynaBean) statList.get(i);
                String conname = (String) bean.get("contractorname");
                String one = String.valueOf(bean.get("one"));
                String two = String.valueOf(bean.get("two"));
                String three = String.valueOf(bean.get("three"));
                String four = String.valueOf(bean.get("four"));
                String five = String.valueOf(bean.get("five"));
                String totalnum = String.valueOf(bean.get("totalnum"));
                dataset.addValue(Integer.parseInt(one), conname, "一干");
                dataset.addValue(Integer.parseInt(two), conname, "二干");
                dataset.addValue(Integer.parseInt(three), conname, "汇聚环");
                dataset.addValue(Integer.parseInt(four), conname, "接入网");
                dataset.addValue(Integer.parseInt(five), conname, "骨干层");
                dataset.addValue(Integer.parseInt(totalnum), conname, "割接总数");
            }
        }
        JFreeChart chart = ChartFactory.createBarChart3D("割接统计图",// 图表标题
                "线路级别", // 目录轴显示标签
                "割接次数",// 数值轴显示标签
                dataset,// 数据集
                PlotOrientation.VERTICAL,// 图表方向：水平，垂直
                true,// 是否生成图列
                false,// 是否生成工具
                false// 是否生成URL链接
                );
        String filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, null, request
                .getSession());
        String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;
        request.setAttribute("graphURL", graphURL);
        request.setAttribute("filename", filename);
    }

    /**
     * 根据线路级别，条件 统计割接次数
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward doQueryForCountByLevel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        List queryRes = null;
        String begintime = request.getParameter("begintime");
        String endtime = request.getParameter("endtime");
        String condition = "";
        if (begintime != null && !begintime.equals("")) {
            condition = condition + " and lc.essetime >=TO_DATE('" + begintime + "','YYYY-MM-DD')";
        }
        if (endtime != null && !endtime.equals("")) {
            condition = condition + " and lc.essetime <= TO_DATE('" + endtime
                    + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
        }
        queryRes = service.doQueryForCountByLevel(condition);
        request.getSession().setAttribute("queryRes", queryRes);
        super.setPageReset(request);
        return mapping.findForward("levelForCount");
    }

    /**
     * 根据线路级别 条件 统计割接时间
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryForTimeByLevel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List queryRes = null;
        String begintime = request.getParameter("begintime");
        String endtime = request.getParameter("endtime");
        String condition = "";
        if (begintime != null && !begintime.equals("")) {
            condition = condition + " and lc.essetime >=TO_DATE('" + begintime + "','YYYY-MM-DD')";
        }
        if (endtime != null && !endtime.equals("")) {
            condition = condition + " and lc.essetime <= TO_DATE('" + endtime
                    + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
        }
        queryRes = service.doQueryForTimeByLevel(condition);
        request.getSession().setAttribute("queryRes", queryRes);
        super.setPageReset(request);
        return mapping.findForward("levelForTime");
    }

    /**
     * 根据割接类型 条件 统计割接时间(暂时没用)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryForTimeByType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List queryRes = null;
        String begintime = request.getParameter("begintime");
        String endtime = request.getParameter("endtime");
        String condition = "";
        if (begintime != null && !begintime.equals("")) {
            condition = condition + " and lc.essetime >=TO_DATE('" + begintime + "','YYYY-MM-DD')";
        }
        if (endtime != null && !endtime.equals("")) {
            condition = condition + " and lc.essetime <= TO_DATE('" + endtime
                    + " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
        }
        queryRes = service.doQueryForTimeByType(condition);
        request.getSession().setAttribute("queryRes", queryRes);
        super.setPageReset(request);
        return mapping.findForward("typeForTime");
    }

    // 导出统计的excel
    // 导出按 割接类型与 条件查询的割接次数结果
    public ActionForward exportCountByType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List list = (List) request.getSession().getAttribute("queryRes");
        if (service.exportCountByType(list, response)) {
            logger.info("导出EXCEL成功");
        } else {
            logger.info("导出EXCEL出现异常");
        }
        return null;
    }

    // 导出按 线路级别与 条件查询的割接次数结果
    public ActionForward exportCountByLevel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List list = (List) request.getSession().getAttribute("queryRes");
        if (service.exportCountByLevel(list, response)) {
            logger.info("导出EXCEL成功");
        } else {
            logger.info("导出EXCEL出现异常");
        }
        return null;
    }

    // 导出按 线路级别与 条件查询的割接耗时结果
    public ActionForward exportTimeByLevel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List list = (List) request.getSession().getAttribute("queryRes");
        if (service.exportTimeByLevel(list, response)) {
            logger.info("导出EXCEL成功");
        } else {
            logger.info("导出EXCEL出现异常");
        }
        return null;
    }

    // 导出按割接类型 条件 统计割接时间(暂时没用)
    public ActionForward exportTimeByType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List list = (List) request.getSession().getAttribute("queryRes");
        if (service.exportTimeByType(list, response)) {
            logger.info("导出EXCEL成功");
        } else {
            logger.info("导出EXCEL出现异常");
        }
        return null;
    }

    public ActionForward lineCutSearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String contractorid = (String) request.getSession().getAttribute("LOGIN_USER_DEPT_ID");
        // 获取线路级别
        List levelList = service.getLineLevle();
        request.setAttribute("linelevelList", levelList);

        // 获得割接名称列表
        /*
         * List lnames = service.getAllNames( contractorid );
         * request.setAttribute( "lnames", lnames );
         */

        // 获得割接原因列表
        List lreasons = service.getAllReasons(contractorid);
        request.setAttribute("lreasons", lreasons);

        // 获得割接地点列表
        List laddresss = service.getAllAddresss(contractorid);
        request.setAttribute("laddresss", laddresss);

        // 获得受影响系统列表
        List lefsystem = service.getAllEfsystems(contractorid);
        request.setAttribute("lefsystem", lefsystem);
        return mapping.findForward("lineCutSearch");
    }

    /**
     * 割接查询统计部分->割接查询
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
    public ActionForward queryCutBeanStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

        String state = request.getParameter("state");

        LineCutBean bean = (LineCutBean) form;
        String contractorid = (String) request.getSession().getAttribute("LOGIN_USER_DEPT_ID");
        bean.setContractorid(contractorid);
        try {

            // List lReqInfo = service.getAllOwnReForSearch(bean,
            // request.getSession());
            List lReqInfo = null;
            if ("detail".equalsIgnoreCase(state)) {
                lReqInfo = (List) request.getSession().getAttribute("lineCutQueryList");
            } else {
                lReqInfo = service.getConditionsReForQuery(bean, request);
            }
            request.getSession().setAttribute("lineCutQueryList", lReqInfo);
            this.setPageReset(request);
            return mapping.findForward("lineCutQueryList");
        } catch (Exception e) {
            logger.error("执行综合查询异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // 导出查询统计中查询部分数据
    public ActionForward exportQueryStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        List list = (List) request.getSession().getAttribute("lineCutQueryList");
        if (service.exportQueryStat(list, response)) {
            logger.info("导出EXCEL成功");
        } else {
            logger.info("导出EXCEL出现异常");
        }
        return null;
    }

    public ActionForward getOneQueryDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        String reid = request.getParameter("reid"); // 获得要显示的申请单id
        try {
            // 获得申请单基本信息
            LineCutBean detailInfo = service.getOneCutQueryInfo(reid);
            request.setAttribute("detailInfo", detailInfo);
            return mapping.findForward("lineCutDetail");
        } catch (Exception e) {
            logger.error("显示一个割接的详细信息错误:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
    /*
     * public ActionForward getReasonByInput( ActionMapping mapping, ActionForm
     * form, HttpServletRequest request, HttpServletResponse response ) throws
     * Exception{ request.setCharacterEncoding("UTF-8");
     * response.setHeader("Cache-Control","no-store");
     * response.setHeader("Pragma","no-cache");
     * response.setDateHeader("Expires",0); String contractorid = ( String
     * )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" ); List
     * reasonList = null; String searchCt= request.getParameter("search");
     * System.out.println(searchCt + "!!!!!!!!!!!!"); if(searchCt != null &&
     * searchCt.trim().length() !=0) { PrintWriter out = response.getWriter();
     * String s = service.getReasonByInputMod(searchCt, contractorid);
     * System.out.println(s + "~~~~~~~~~"); out.println(s);
     * System.out.println(URLEncoder.encode(s,"utf-8")); out.flush();
     * out.close(); } return null; }
     */

    /*
     * public ActionForward getAllName(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response) {
     * 
     * return null; }
     * 
     * public ActionForward getAllReason(ActionMapping mapping, ActionForm form,
     * HttpServletRequest request, HttpServletResponse response) {
     * response.setContentType("text/html; charset=utf-8"); UserInfo userinfo =
     * (UserInfo) request.getSession().getAttribute( "LOGIN_USER"); String
     * deptid = userinfo.getDeptID(); String reason =
     * service.getAllReason(deptid); PrintWriter out = null; try { out =
     * response.getWriter(); } catch (IOException e) { e.printStackTrace(); }
     * System.out.println(reason + "!"); out.write(reason); out.flush();
     * out.close(); return null; }
     * 
     * public ActionForward getAllAddress(ActionMapping mapping, ActionForm
     * form, HttpServletRequest request, HttpServletResponse response) {
     * 
     * return null; }
     * 
     * public ActionForward getAllEfsystem(ActionMapping mapping, ActionForm
     * form, HttpServletRequest request, HttpServletResponse response) {
     * 
     * return null; }
     */
}
