package com.cabletech.linecut.action;

import java.util.*;

import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.apache.struts.upload.*;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.linecut.beans.*;
import com.cabletech.linecut.services.*;
import com.cabletech.power.*;
import com.cabletech.uploadfile.*;

public class LineCutWorkAction extends BaseDispatchAction {
    private LineCutWorkService service = new LineCutWorkService();

    private static Logger logger = Logger.getLogger(LineCutWorkAction.class.getName());

    /**
     * 添加施工信息显示
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
    public ActionForward workShow(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), "30301")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // 如果是移动公司是不允许的
            return forwardErrorPage(mapping, request, "partauditerror");
        }
        try {
            List lReqInfo = service.getAllReForWork(userinfo);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "reforwork1");
            this.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("添加施工信息显示出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }

    }

    /**
     * 显示一个待添加施工信息的割接详细信息
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
    public ActionForward showOneInfoForWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30301")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // 如果是移动单位是不允许的
            return forwardErrorPage(mapping, request, "partauditerror");
        }
        String reid = request.getParameter("reid"); // 获得要添加施工信息的申请单id
        try {
            LineCutBean reqinfo = service.getOneReInfo(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "work2");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示一个待添加施工信息的割接详细信息错误:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 执行添加施工信息
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
    public ActionForward addWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30301")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("1")) { // 如果是移动公司是不允许的
            // return forwardErrorPage(mapping, request, "partauditerror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "partauditerror", backUrl);
        }
        try {
            LineCutBean bean = (LineCutBean) form;
            userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
            bean.setWorkuserid(userinfo.getUserID());

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
                bean.setWorkacce(fileIdListStr);
            }
            // ======================
            if (!service.addWorkInfo(bean)) {
                // return forwardErrorPage(mapping, request, "30302e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "30302e", backUrl);
            }
            log(request, "割接管理", "添加施工信息");
            // return forwardInfoPage(mapping, request, "30302");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "30302", backUrl);
        } catch (Exception e) {
            logger.error("执行添加施工信息异常:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * 显示所有已经施工的割接信息
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
    public ActionForward showAllWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30301")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List lReqInfo = null;
        try {
            if (userinfo.getDeptype().equals("2")) {
                lReqInfo = service.getAllOwnWork(request);
            } else {
                lReqInfo = service.getAllWork(request);
            }

            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "work1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示所有已经施工的割接信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示一个割接施工的详细信息
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
    public ActionForward showOneWorkInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        /*
         * if( !CheckPower.checkPower( request.getSession(), "30301" ) ){ return
         * mapping.findForward( "powererror" ); }
         */
        String reid = request.getParameter("reid"); // 获得要显示的申请单id
        try {
            // 获得申请单基本信息
            LineCutBean reqinfo = service.getOneWorkInfo(reid);

            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "work10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("在显示详细中出现错误:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // 割接施工修改显示
    public ActionForward showWorkUp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30304")) {
            return mapping.findForward("powererror");
        }
        String reid = request.getParameter("reid"); // 获得要修改的申请单id
        try {
            // 获得申请单基本信息
            LineCutBean reqinfo = service.getOneWorkInfo(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "work4");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("在割接施工修改显示中出现错误:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // 割接施工修改提交
    public ActionForward WorkUp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30304")) {
            // return mapping.findForward("powererror");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "powererror", backUrl);
        }
        try {
            LineCutBean bean = (LineCutBean) form;
            bean.setWorkuserid(((UserInfo) request.getSession().getAttribute("LOGIN_USER"))
                    .getUserID());
            ;
            /** *文件上传 修改* */
            ArrayList fileIdList = new ArrayList();
            String[] delfileid = request.getParameterValues("delfileid"); // 要删除的文件id号数组
            // 组合新的附件串
            StringTokenizer st = new StringTokenizer(bean.getWorkacce(), ",");
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
            // int size = attachments.size();
            // for( int i = 0; i < size; i++ ){
            // UploadFile uploadFile = ( UploadFile )attachments.get( i );
            // FormFile file = uploadFile.getFile();
            //
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
            // 获取ID字符串列表(以逗号分隔的文件ID字符串)=================
            String fileIdListStr = UploadUtil.getFileIdList(fileIdList);
            if (fileIdListStr != null) {
                bean.setWorkacce(fileIdListStr);
            }
            // ======================
            if (!service.WorkUp(bean)) {
                // return forwardErrorPage(mapping, request, "30304e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "30304e", backUrl);
            }
            log(request, "割接管理", "修改施工信息");
            // return forwardInfoPage(mapping, request, "30304");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "30304", backUrl);
        } catch (Exception e) {
            logger.error("在割接施工修改中出现错误:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    // 施工综合查询显示
    public ActionForward queryShowForWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30303")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

        try {
            String deptid = userinfo.getDeptID();
            // 获取线路级别
            List levelList = new LineCutReService().getLineLevle();
            request.setAttribute("lineLevelList", levelList);

            // 获得割接名称列表
            List lnames = service.getAllNamesForWork(deptid);
            request.setAttribute("lnames", lnames);
            // 获得割接原因列表
            List lreasons = service.getAllReasonsForWork(deptid);
            request.setAttribute("lreasons", lreasons);
            // 获得割接地点列表
            List laddresss = service.getAllAddresssForWork(deptid);
            request.setAttribute("laddresss", laddresss);
            // 获得受影响系统列表
            List lefsystem = service.getAllEfsystemsForWork(deptid);
            request.setAttribute("lefsystem", lefsystem);
            // 获得涉及光缆段
            /*
             * List lsublines = service.getAllSublineidsForWork( deptid );
             * request.setAttribute( "lsublines", lsublines );
             */

            request.getSession().setAttribute("type", "work3");
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("施工综合查询显示异常:" + e.getMessage());
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
    public ActionForward doQueryForWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30303")) {
            return mapping.findForward("powererror");
        }

        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        LineCutBean bean = (LineCutBean) form;
        String deptid = userinfo.getDeptID();
        bean.setDeptid(deptid);
        try {
            // List lReqInfo = service.getAllOwnReForWorkSearch( bean, userinfo
            // ,request.getSession());
            List lReqInfo = service.getConditionsReForWorkSearch(bean, userinfo, request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "work1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("执行综合查询异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward doQueryForWorkAfterMod(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("lcwQueryCon");
        if (sql.trim().length() != 0 && sql != null) {
            List lReqInfo = service.doQueryAfterMod(sql);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "work1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("返回综合查询异常: 原因：session过期");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // ============================归档与割接查询===================//

    /**
     * 待归档割接信息显示
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
    public ActionForward waitArShow(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!CheckPower.checkPower(request.getSession(), "30402")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // 如果是代维单位是不允许的
            return forwardErrorPage(mapping, request, "partauditerror");
        }
        try {
            List lReqInfo = service.getAllForArch(request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "reforAr1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("待归档割接信息显示出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }

    }

    /**
     * 显示一个待归档的割接详细信息
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
    public ActionForward showOneInfoForAr(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30402")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (userinfo.getDeptype().equals("2")) { // 如果是代维单位是不允许的
            return forwardErrorPage(mapping, request, "partauditerror");
        }
        String reid = request.getParameter("reid"); // 获得要添加施工信息的申请单id
        try {
            LineCutBean reqinfo = service.getOneForArch(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "ar2");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示一个待归档的割接详细信息错误:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 执行添加归档信息
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
    public ActionForward addAr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30402")) {
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
            bean.setWorkuserid(userinfo.getUserID());

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
                bean.setArchives(fileIdListStr);
            }
            // ======================
            if (!service.addArchInfo(bean)) {
                // return forwardErrorPage(mapping, request, "30402e");
                String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
                return super.forwardErrorPageWithUrl(mapping, request, "30402e", backUrl);
            }
            log(request, "割接管理", "添加归档信息");
            // return forwardInfoPage(mapping, request, "30402");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardInfoPageWithUrl(mapping, request, "30402", backUrl);
        } catch (Exception e) {
            logger.error("执行添加归档信息异常:" + e.getMessage());
            // return forwardErrorPage(mapping, request, "error");
            String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
            return super.forwardErrorPageWithUrl(mapping, request, "error", backUrl);
        }
    }

    /**
     * 显示所有割接信息
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
    public ActionForward showAllAr(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30401")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List lReqInfo = null;
        try {
            lReqInfo = service.getCutInfo(request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "ar1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示所有割接信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示一个割接的详细信息
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
    public ActionForward showOneWorkArInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        /*
         * if( !CheckPower.checkPower( request.getSession(), "30402" ) ){ return
         * mapping.findForward( "powererror" ); }
         */
        String reid = request.getParameter("reid"); // 获得要显示的申请单id
        try {
            // 获得申请单基本信息
            LineCutBean reqinfo = service.getOneCutAllInfo(reid);
            request.setAttribute("reqinfo", reqinfo);
            request.getSession().setAttribute("type", "ar10");
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示一个割接的详细信息错误:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 割接综合查询显示
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
    public ActionForward queryShowForAr(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30403")) {
            return mapping.findForward("powererror");
        }
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

        try {
            String deptid = userinfo.getDeptID();

            List levelList = new LineCutReService().getLineLevle();
            request.setAttribute("linelevelList", levelList);

            // 获得割接名称列表
            List lnames = service.getAllNamesForWork(deptid);
            request.setAttribute("lnames", lnames);
            // 获得割接原因列表
            List lreasons = service.getAllReasonsForWork(deptid);
            request.setAttribute("lreasons", lreasons);
            // 获得割接地点列表
            List laddresss = service.getAllAddresssForWork(deptid);
            request.setAttribute("laddresss", laddresss);
            // 获得受影响系统列表
            List lefsystem = service.getAllEfsystemsForWork(deptid);
            request.setAttribute("lefsystem", lefsystem);

            // 获得涉及光缆段
            /*
             * List lsublines = service.getAllSublineidsForWork( deptid );
             * request.setAttribute( "lsublines", lsublines );
             */

            // 获得割接单位列表
            List lconname = service.getAllConName(deptid);
            request.setAttribute("lconname", lconname);

            request.setAttribute("depttype", userinfo.getDeptype());
            request.getSession().setAttribute("type", "au3");
            return mapping.findForward("success");

        } catch (Exception e) {
            logger.error("施工综合查询显示异常:" + e.getMessage());
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
    public ActionForward doQueryForAr(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!CheckPower.checkPower(request.getSession(), "30403")) {
            return mapping.findForward("powererror");
        }

        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        LineCutBean bean = (LineCutBean) form;
        String deptid = userinfo.getDeptID();
        bean.setDeptid(deptid);
        List lReqInfo = null;
        try {
            // lReqInfo = service.getCutInfoForSearch( bean, userinfo
            // ,request.getSession());
            lReqInfo = service.getConditionsCutInfoForSearch(bean, userinfo, request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "ar1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("执行综合查询异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    public ActionForward doQueryForArAfter(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String sql = (String) request.getSession().getAttribute("lcwArQueryCob");

        if (sql.trim().length() != 0 && sql != null) {
            List lReqInfo = service.queryAfterBack(sql);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "ar1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } else {
            logger.error("执行综合查询异常:session过期");
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 显示所有处于施工阶段的割接信息
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
    public ActionForward showAllCutForWorking(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // 获得当前用户的单位名称
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List lReqInfo = null;
        try {
            lReqInfo = service.getCutInfoForWorking(request);
            request.getSession().setAttribute("reqinfo", lReqInfo);
            request.getSession().setAttribute("type", "working1");
            super.setPageReset(request);
            return mapping.findForward("success");
        } catch (Exception e) {
            logger.error("显示所有割接信息出错:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 割接一览表查询结果输出到Excel表
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
    public ActionForward exportLineCut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {

            logger.info(" 创建dao");
            List list = (List) request.getSession().getAttribute("reqinfo");
            logger.info("得到list");
            if (service.ExportLineCut(list, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("导出割接一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    // ************2006-07-14 pzj 申请审批一览表
    /**
     * 导出审批一览表
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
    public ActionForward exportReLineCut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" 创建dao");
            List list = (List) request.getSession().getAttribute("reqinfo");
            logger.info("得到list");
            if (service.ExportReLineCut(list, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("导出申请审批一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 导出已经施工割接一览表
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
    public ActionForward exportLineCutWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" 创建dao");
            List list = (List) request.getSession().getAttribute("reqinfo");
            logger.info("得到list");
            if (service.ExportLineCutWork(list, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("导出已经施工割接一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }

    /**
     * 导出割接申请一览表
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
    public ActionForward exportLineCutRe(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info(" 创建dao");
            List list = (List) request.getSession().getAttribute("reqinfo");
            logger.info("得到list");
            if (service.ExportLineCutRe(list, response)) {
                logger.info("输出excel成功");
            }
            return null;
        } catch (Exception e) {
            logger.error("导出割接申请一览表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
