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
import com.cabletech.linechange.services.PageonholeBOImpl;
import com.cabletech.linechange.domainobjects.ChangeInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linechange.bean.ChangeInfoBean;
import java.util.List;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.LineBO;

import java.util.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import org.apache.struts.upload.FormFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadUtil;
import com.cabletech.linechange.dao.ExportDao;

public class PageonholeAction extends BaseDispatchAction {
    private static Logger logger = Logger.getLogger("BuildSetoutAction");

    public ActionForward updatePageonhole(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50702")) {
            return mapping.findForward("powererror");
        }
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        ChangeInfoBean bean = (ChangeInfoBean) form;
        ChangeInfo data = new ChangeInfo();
        PageonholeBOImpl bo = new PageonholeBOImpl();
        ArrayList fileIdList = new ArrayList();
        String[] delfileid = request.getParameterValues("delfileid"); // 要删除的文件id号数组
        StringTokenizer st = new StringTokenizer(bean.getPageonholedatum(), ",");
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
        data.setPageonholedate(new Date());
        data.setPageonholedatum(datumid);
        data.setPageonholeperson(userinfo.getUserName());
        data.setSquare(bean.getSquare());
        data.setIschangedatum(bean.getIschangedatum());
        data.setStep("G");
        data.setPageonholeremark(bean.getPageonholeremark());
        bo.updatePageonhole(data);
        // return forwardInfoPage( mapping, request, "50702s" );
        String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");
        return super.forwardInfoPageWithUrl(mapping, request, "50702s", backUrl);
    }

    public ActionForward getChangeInfoList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50702")) {
            return mapping.findForward("powererror");
        }
        PageonholeBOImpl bo = new PageonholeBOImpl();
        ChangeInfoBean changeinfo = (ChangeInfoBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List list = bo.getChangeInfo(changeinfo, userinfo);
        request.getSession().setAttribute("queryresult", list);
        super.setPageReset(request);
        return mapping.findForward("loadChangeInfo");
    }

    public ActionForward getPageonholeList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50702")) {
            return mapping.findForward("powererror");
        }
        PageonholeBOImpl bo = new PageonholeBOImpl();
        ChangeInfoBean changeinfo = (ChangeInfoBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List list = bo.getPageonhole(changeinfo, userinfo);
        request.getSession().setAttribute("queryresult", list);
        request.getSession().setAttribute("bean", changeinfo);
        super.setPageReset(request);
        return mapping.findForward("loadPageonholeInfo");
    }

    public ActionForward loadQueryForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50703")) {
            return mapping.findForward("powererror");
        }
        return mapping.findForward("loadQueryForm");
    }

    public ActionForward loadAddForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50702")) {
            return mapping.findForward("powererror");
        }
        String id = request.getParameter("id");
        request.setAttribute("id", id);
        PageonholeBOImpl bo = new PageonholeBOImpl();
        ChangeInfo changeinfo = bo.getChangeInfo(id);
        // ChangeInfoBean bean = null;
        // BeanUtil.objectCopy(changeinfo,bean);
        request.setAttribute("changeinfo", changeinfo);

        return mapping.findForward("loadAddForm");
    }

    public ActionForward loadLookForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        // if( !CheckPower.checkPower( request.getSession(), "50703" ) ){
        // return mapping.findForward( "powererror" );
        // }
        String id = request.getParameter("id");
        PageonholeBOImpl bo = new PageonholeBOImpl();
        ChangeInfo changeinfo = bo.getChangeInfo(id);
        // ChangeInfoBean bean = null;
        // BeanUtil.objectCopy(changeinfo,bean);
        request.setAttribute("changeinfo", changeinfo);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) changeinfo.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        return mapping.findForward("lookForm");
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

    // 显示所有未付清的修缮
    public ActionForward showSquare(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50704")) {
            return mapping.findForward("powererror");
        }
        PageonholeBOImpl bo = new PageonholeBOImpl();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        List slist = bo.getsquare();
        request.getSession().setAttribute("slist", slist);
        this.setPageReset(request);
        return mapping.findForward("showSquare");
    }

    // 显示付款页面

    public ActionForward showOneSquare(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50704")) {
            return mapping.findForward("powererror");
        }
        String id = request.getParameter("id");
        PageonholeBOImpl bo = new PageonholeBOImpl();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

        ChangeInfoBean bean = bo.getOnesquare(id);
        request.setAttribute("bean", bean);
        LineBO lbo = new LineBO();
        String lineClassName = lbo.getLineClassName((String) bean.getLineclass());
        request.setAttribute("line_class_name", lineClassName);
        return mapping.findForward("showOneSquare");
    }

    // 保存付款

    public ActionForward saveSquare(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException {
        /** 权限校验 */
        if (!CheckPower.checkPower(request.getSession(), "50704")) {
            return mapping.findForward("powererror");
        }
        ChangeInfoBean bean = (ChangeInfoBean) form;
        PageonholeBOImpl bo = new PageonholeBOImpl();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        if (!bo.saveSquare(bean)) {
            // baochun bu cheng gong
            return forwardErrorPage(mapping, request, "50704e");
        }
        return forwardInfoPage(mapping, request, "50704");
    }

    public ActionForward exportPageonholeResult(ActionMapping mapping, ActionForm form,
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
            ed.exportPageonholeResult(list, bean, response);
            logger.info("end.....");
            return null;
        } catch (Exception e) {
            logger.error("导出信息报表出现异常:" + e.getMessage());
            return forwardErrorPage(mapping, request, "error");
        }
    }
}
