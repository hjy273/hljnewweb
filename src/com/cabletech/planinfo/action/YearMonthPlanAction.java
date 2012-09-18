package com.cabletech.planinfo.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import cabletech.sm.rmi.RmiSmProxyService;

import com.cabletech.baseinfo.dao.UserInfoDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.planinfo.beans.YearMonthPlanBean;
import com.cabletech.planinfo.domainobjects.PlanTaskList;
import com.cabletech.planinfo.domainobjects.Planapprove;
import com.cabletech.planinfo.domainobjects.YearMonthPlan;
import com.cabletech.planinfo.services.PlanBaseService;
import com.cabletech.planinfo.services.YearMonthPlanBO;

public class YearMonthPlanAction extends PlanInfoBaseDispatchAction {
    private WebApplicationContext ctx;

    private Logger logger = Logger.getLogger("YearMonthPlanAction");

    PlanBaseService pbs = new PlanBaseService();

    public YearMonthPlanAction() {
    }

    /**
     * ������\�¼ƻ�,���䱣����session��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward createYMPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        YearMonthPlanBean _bean = (YearMonthPlanBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

        HttpSession session = request.getSession();
        // ���session�е���������.
        session.removeAttribute("EditS");
        session.removeAttribute("plantype");
        session.removeAttribute("YMplan");
        session.removeAttribute("taskList");
        // ���ҵ�����
        String planidold = "";
        if (_bean.getPlantype().equals("1")) {
            session.setAttribute("plantype", "1");
            planidold = super.getLoginUserInfo(request).getRegionid() + _bean.getYear();
            _bean.setId(super.getLoginUserInfo(request).getRegionid()
                    + super.getLoginUserInfo(request).getDeptID() + _bean.getYear()); // ��ȼƻ����Ϊ�����ţ���λ��ţ����
            boolean b = pbs.isOldYMPlan(_bean.getYear(), _bean.getMonth());
            if (b) {
                return forwardInfoPage(mapping, request, "warn0301");
            }
        } else {
            session.setAttribute("plantype", "2");
            /* ��֤�Ƿ���ƻ����ƶ� */
            boolean b = pbs.isInstituteYPlan(_bean.getYear(), userinfo.getDeptID());
            if (!b) {
                return forwardInfoPage(mapping, request, "warn03");
            }
            b = pbs.isOldYMPlan(_bean.getYear(), _bean.getMonth());
            if (b) {
                return forwardInfoPage(mapping, request, "warn0301");
            }
            _bean.setId(super.getLoginUserInfo(request).getRegionid()
                    + super.getLoginUserInfo(request).getDeptID() + _bean.getYear()
                    + _bean.getMonth()); // �¶ȼƻ����Ϊ���򣫵�λ��ţ����+�·�
            planidold = super.getLoginUserInfo(request).getRegionid() + _bean.getYear()
                    + _bean.getMonth();
        }
        // _bean.setPlantype("1"); // 1,��ȼƻ� 2���¶ȼƻ�
        _bean.setRegionid(super.getLoginUserInfo(request).getRegionid());
        _bean.setCreator(super.getLoginUserInfo(request).getUserName());
        _bean.setCreatedate(DateUtil.getNowDateString());
        _bean.setDeptid(super.getLoginUserInfo(request).getDeptID());
        _bean.setIfinnercheck("1");
        _bean.setCreateuserid(userinfo.getUserID());
        session.setAttribute("EditS", "add");
        session.setAttribute("taskList", new ArrayList());
        // ���ƻ��Ƿ����.
        if (super.getService().checkYMPlanUnique(_bean.getId()) == 1
                && super.getService().checkYMPlanUnique(planidold) == 1) {
            YearMonthPlan _data = new YearMonthPlan();
            BeanUtil.objectCopy(_bean, _data);
            request.getSession().setAttribute("YMplan", _data);
            String _strFor = "addYearMonthTask";
            return mapping.findForward(_strFor);
        } else {
            if (_bean.getPlantype().equals("1")) {
                return forwardInfoPage(mapping, request, "warn01");
            } else {
                return forwardInfoPage(mapping, request, "warn02");
            }
        }

    }

    /**
     * �������¼ƻ���Ϣ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward saveYMPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        ctx = getWebApplicationContext();
        HttpSession session = request.getSession();
        UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
        YearMonthPlan ymPlan = (YearMonthPlan) session.getAttribute("YMplan");
        List taskList = (List) session.getAttribute("taskList");
        logger.info("size:" + taskList.size());
        YearMonthPlanBO ymbo = new YearMonthPlanBO();
        if (taskList.size() == 0) {
            return forwardInfoPage(mapping, request, "f20304");
        }
        String strFor = "f20101";
        String phone = request.getParameter("phone");

        boolean b = ymbo.saveYMPlan(ymPlan, taskList);
        if (b) {
            if (ymPlan.getPlantype().equals("1")) {
                log(request, "�½���ƻ����ƻ�����Ϊ��" + ymPlan.getPlanname() + "��", "Ѳ�����");
                strFor = "s20101";
            } else {
                log(request, "�½��¼ƻ����ƻ�����Ϊ��" + ymPlan.getPlanname() + "��", "Ѳ�����");
                strFor = "s20201";
            }
            RmiSmProxyService smSendProxy = (RmiSmProxyService) ctx.getBean("rmiSmProxyService");
            SmHistoryDAO historyDAO = (SmHistoryDAO) ctx.getBean("smHistoryDAO");
            if (phone != null && !phone.equals("")) {
                String msg = "����·Ѳ�졿����һ������Ϊ��" + ymPlan.getPlanname() + "���ļƻ��ȴ�����������";
                logger.info(msg);
                try {
                    smSendProxy.simpleSend(phone, msg, null, null, true);
                } catch (Exception ex) {
                    logger.error("���ŷ���ʧ��:", ex);
                }
                // SMHistory history = new SMHistory();
                // logger.info("���ſ�ʼ���ͣ�д�����ݿ���ŷ�����ʷ������");
                // history.setSimIds(phone);
                // history.setSendContent(msg);
                // history.setSendTime(new Date());
                // // history.setSendState(sendState);
                // history.setSmType("YEAR_MONTH_PLAN");
                // history.setObjectId(ymPlan.getId());
                // history.setBusinessModule("yearmonthplan");
                // historyDAO.save(history);
                // logger.info("д�����ݿ���ŷ�����ʷ��ɡ�");
            }
            // if (request.getSession().getAttribute("isSendSm").equals("send"))
            // {
            // String objectman = request.getParameter("phone");
            // if (objectman != null && !objectman.equals("")) {
            // String msg = "";
            // //SendSMRMI.sendNormalMessage(userinfo.getUserID(),
            // // objectman, msg, "00");
            // // System.out.println( msg );
            // }
            // }
        } else {
            if (ymPlan.getPlantype().equals("1")) {
                strFor = "f20101";
            } else {
                strFor = "f20201";
            }
        }
        // ���session
        session.removeAttribute("YMplan");
        session.removeAttribute("taskList");
        session.removeAttribute("EditS");
        session.removeAttribute("plantype");
        return forwardInfoPage(mapping, request, strFor);
    }

    /**
     * ����һ�� ��/�� �ƻ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadYMPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        HttpSession session = request.getSession();
        UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");// ��ȡ�û���Ϣ
        String fID = request.getParameter("fID");// fIDΪ1,��ȼƻ� 2���¶ȼƻ�
        YearMonthPlanBO ymbo = new YearMonthPlanBO();
        YearMonthPlanBean bean = new YearMonthPlanBean();
        // YearMonthPlan data =
        // ymbo.getYMPlanInfo(request.getParameter("id"));//��ȡ��ȼƻ�.
        bean = ymbo.getYMPlanInfo(request.getParameter("id"));// ��ȡ��ȼƻ�.
        // BeanUtil.objectCopy(data, bean);
        List taskList = ymbo.getTaskList(bean.getId());
        session.setAttribute("YMplan", bean);
        session.setAttribute("taskList", taskList);
        session.setAttribute("plantype", bean.getPlantype());
        session.setAttribute("EditS", "edit");
        String forwardPage = "updateYearPlan";
        if (fID.equals("2")) {
            forwardPage = "updateMonthPlan";
        }

        return mapping.findForward(forwardPage);
    }

    /**
     * �������¼ƻ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward updateYMPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        ctx = getWebApplicationContext();
        HttpSession session = request.getSession();
        UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
        YearMonthPlanBean bean = (YearMonthPlanBean) session.getAttribute("YMplan");
        YearMonthPlan ymPlan = new YearMonthPlan();
        ymPlan.setRemark("2");
        BeanUtil.objectCopy(bean, ymPlan);
        List taskList = (List) session.getAttribute("taskList");
        logger.info("Llist" + taskList.size());
        if (taskList.size() == 0) {
            return forwardInfoPage(mapping, request, "f20304");
        }
        YearMonthPlanBO ymbo = new YearMonthPlanBO();
        String strFor = "f20102";
        String phone = request.getParameter("phone");
        ymPlan.setStatus("0");// ������
        boolean b = ymbo.saveOrUpdateYMPlan(ymPlan, taskList);
        logger.info("--=-" + b);
        if (b) {
            if (ymPlan.getPlantype().equals("1")) {
                log(request, "������ƻ����ƻ�����Ϊ��" + ymPlan.getPlanname() + "��", "Ѳ�����");
                strFor = "s20102";
            } else {
                log(request, "�����¼ƻ����ƻ�����Ϊ��" + ymPlan.getPlanname() + "��", "Ѳ�����");
                strFor = "s20202";
            }
            RmiSmProxyService smSendProxy = (RmiSmProxyService) ctx.getBean("rmiSmProxyService");
            SmHistoryDAO historyDAO = (SmHistoryDAO) ctx.getBean("smHistoryDAO");
            if (phone != null && !phone.equals("")) {
                String msg = "����·Ѳ�졿����һ������Ϊ��" + ymPlan.getPlanname() + "���ļƻ��ȴ�����������";
                logger.info("�޸���ƻ�:" + msg + " phone:" + phone);
                try {
                    smSendProxy.simpleSend(phone, msg, null, null, true);
                } catch (Exception ex) {
                    logger.error("���ŷ���ʧ��:", ex);
                }
                // SMHistory history = new SMHistory();
                // logger.info("���ſ�ʼ���ͣ�д�����ݿ���ŷ�����ʷ������");
                // history.setSimIds(phone);
                // history.setSendContent(msg);
                // history.setSendTime(new Date());
                // // history.setSendState(sendState);
                // history.setSmType("YEAR_MONTH_PLAN");
                // history.setObjectId(ymPlan.getId());
                // history.setBusinessModule("yearmonthplan");
                // historyDAO.save(history);
                // logger.info("д�����ݿ���ŷ�����ʷ��ɡ�");
            }
            // if (request.getSession().getAttribute("isSendSm").equals("send"))
            // {
            // String objectman = request.getParameter("phone");
            // if (objectman != null && !objectman.equals("")) {
            // String msg = "����֪ͨ��"
            // + request.getSession().getAttribute(
            // "LOGIN_USER_DEPT_NAME")
            // + "������Ѳ��ƻ��ȴ���������. �����ˣ�" + userinfo.getUserName();
            // SendSMRMI.sendNormalMessage(userinfo.getUserID(),
            // objectman, msg, "00");
            // // System.out.println( msg );
            // }
            // }
        } else {
            if (ymPlan.getPlantype().equals("1")) {
                strFor = "f20102";
            } else {
                strFor = "f20202";
            }
        }
        // ���session
        session.removeAttribute("YMplan");
        session.removeAttribute("taskList");
        session.removeAttribute("EditS");
        session.removeAttribute("plantype");
        return forwardInfoPage(mapping, request, strFor);
    }

    /**
     * ɾ��һ�����¼ƻ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward deleteYMPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        String planid = request.getParameter("id");
        YearMonthPlan data = super.getService().loadymPlan(planid);
        YearMonthPlanBO ympbo = new YearMonthPlanBO();

        String plantype = data.getPlantype();
        if (plantype.equals("1")) { // ɾ����ƻ�
            // Log
            log(request, " ɾ�����Ѳ��ƻ� ���ƻ�����Ϊ��" + ympbo.getYMPlanInfo(planid).getPlanname() + "��",
                    " Ѳ��ƻ����� ");
            YearMonthPlanBO ym = new YearMonthPlanBO();
            ym.deleSplithTask();
            ympbo.removeYMPlan(data);
            return forwardInfoPage(mapping, request, "0210");
        } else { // ɾ���¼ƻ�
            ympbo.removeYMPlan(data);
            log(request, " ɾ���¶�Ѳ��ƻ����ƻ�����Ϊ��" + ympbo.getYMPlanInfo(planid).getPlanname() + "�� ",
                    " Ѳ��ƻ����� ");
            return forwardInfoPage(mapping, request, "0211");
        }
    }

    /**
     * ����һ����ƻ�
     */
    // public ActionForward updateYMPlan(ActionMapping mapping, ActionForm form,
    // HttpServletRequest request, HttpServletResponse response)
    // throws ClientException, Exception {
    // YearMonthPlanBean bean = (YearMonthPlanBean) form;
    // bean.setIfinnercheck("1");
    // // //zhj//////
    // UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
    // "LOGIN_USER");
    // bean.setCreateuserid(userinfo.getUserID());
    // // //zhj//////
    // YearMonthPlan data = new YearMonthPlan();
    // BeanUtil.objectCopy(bean, data);
    //
    // String ymplanid4Task = bean.getId();
    // YearMonthPlan thisplan = super.getService().loadymPlan(bean.getId());
    // // ��ȡ���غ��id
    // String id = data.getId();
    // // /
    // if (!thisplan.getStatus().equals("0")) { // ����ֱ���޸�
    //
    // data.setId(data.getId() + "M"); // ��ȼƻ����Ϊ���
    // data.setApprovedate(null);
    // data.setApprover(null);
    // data.setRemark("2"); // ��־�����޸ĵļƻ�, M ����
    // data.setStatus(null);
    //
    // ymplanid4Task = bean.getId() + "M";
    //
    // // ���Ψһ��
    // if (super.getService().checkYMPlanUnique(data.getId()) == 1) {
    // // ������ȼƻ�
    // super.getService().createYMPlan(data);
    // thisplan.setRemark("0"); // �����ʱ�򱻹��˵�
    // super.getService().updateYMPlan(thisplan);
    //
    // } else {
    // String errmsg = "�üƻ��Ѿ����޸Ĺ�,��Ŀǰ��δ�õ�����, Ҫ�ڴ˻������޸���?";
    // request.setAttribute("errmsg", errmsg);
    // // ���ر��ݺ��id
    // request.setAttribute("objectid", id);
    // // request.setAttribute("objectid", data.getId());
    // request.setAttribute("fID", "1");
    //
    // // System.out.println( errmsg );
    // return mapping.findForward("ymplanError");
    // }
    // } else {
    // thisplan.setPlanname(bean.getPlanname());
    // thisplan.setIfinnercheck(bean.getIfinnercheck());
    //
    // super.getService().updateYMPlan(thisplan);
    //
    // }
    //
    // // ���ƻ��������������üƻ���Ӧ����Ŀɾ��
    // // super.getService().removeRelatedTask( ymplanid4Task );
    // // ����ƻ������б�
    // String[] taskAr = request.getParameterValues("taskcheck");
    // YearMonthPlanBO ym = new YearMonthPlanBO();
    // // --����ƻ������б�
    // if (!ym.delePlanTask(bean.getId())) {
    // return forwardErrorPage(mapping, request, "error"); // ����
    // }
    // // --д��ƻ���������
    // for (int i = 0; i < taskAr.length; i++) {
    // PlanTaskList plantasklist = new PlanTaskList();
    // plantasklist.setId(super.getDbService().getSeq("plantasklist", 50));
    // plantasklist.setTaskid(taskAr[i]);
    // plantasklist.setPlanid(ymplanid4Task);
    // super.getService().createPlanTaskList(plantasklist);
    // }
    //
    // if (bean.getPlantype().equals("1")) { // ������ƻ�
    // // Log
    // log(request, " �������Ѳ��ƻ� ", " Ѳ��ƻ����� ");
    // return forwardInfoPage(mapping, request, "0207");
    // } else { // �����¼ƻ�
    // // Log
    // log(request, " �����¶�Ѳ��ƻ� ", " Ѳ��ƻ����� ");
    // return forwardInfoPage(mapping, request, "0208");
    // }
    // }
    /**
     * ������ȼƻ�
     */
    public ActionForward addYearPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        log(request, " �������Ѳ��ƻ� ", " Ѳ��ƻ����� ");

        YearMonthPlanBean bean = (YearMonthPlanBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String planidold = super.getLoginUserInfo(request).getRegionid() + bean.getYear();
        bean.setId(super.getLoginUserInfo(request).getRegionid()
                + super.getLoginUserInfo(request).getDeptID() + bean.getYear()); // ��ȼƻ����Ϊ�����ţ���λ��ţ����
        bean.setPlantype("1"); // 1,��ȼƻ� 2���¶ȼƻ�
        bean.setRegionid(super.getLoginUserInfo(request).getRegionid());
        bean.setCreator(super.getLoginUserInfo(request).getUserName());
        bean.setCreatedate(DateUtil.getNowDateString());
        bean.setDeptid(super.getLoginUserInfo(request).getDeptID());
        bean.setIfinnercheck("1");
        bean.setCreateuserid(userinfo.getUserID());

        YearMonthPlan data = new YearMonthPlan();
        BeanUtil.objectCopy(bean, data);
        // ���Ψһ��
        if (super.getService().checkYMPlanUnique(bean.getId()) == 1
                && super.getService().checkYMPlanUnique(planidold) == 1) {
            // ���Ͷ���
            if (request.getSession().getAttribute("isSendSm").equals("send")) {
                String objectman = request.getParameter("phone");
                if (objectman != null && !objectman.equals("")) {
                    String msg = "����֪ͨ��"
                            + request.getSession().getAttribute("LOGIN_USER_DEPT_NAME")
                            + "���������Ѳ��ƻ��ȴ���������.  �����ˣ�" + userinfo.getUserName();
                    SendSMRMI.sendNormalMessage(userinfo.getUserID(), objectman, msg, "00");
                    // System.out.println( msg );
                }
            }
            // ������ȼƻ�
            super.getService().createYMPlan(data);
            log(request, "�����ƻ����ƻ�����Ϊ��" + bean.getPlanname() + "��", "Ѳ�����");
        } else {
            String errmsg = "�������ȼƻ��Ѿ�����, Ҫ�ڴ˻������޸���?";
            request.setAttribute("errmsg", errmsg);
            if (super.getService().checkYMPlanUnique(bean.getId()) != 1) {
                request.setAttribute("objectid", bean.getId());
            }
            if (super.getService().checkYMPlanUnique(planidold) != 1) {
                request.setAttribute("objectid", planidold);
            }
            request.setAttribute("fID", "1");
            return mapping.findForward("ymplanError");
            // return forwardInfoPage( mapping, request, "0204"
            // );//�������棬��ʾ�û�����ƻ��Ѿ����ڡ�
        }
        // ���ƻ��������������üƻ���Ӧ����Ŀɾ��
        // super.getService().removeRelatedTask( bean.getId() );
        // ����ƻ������б�
        String[] taskAr = request.getParameterValues("taskcheck");
        for (int i = 0; i < taskAr.length; i++) {
            // System.out.println(" Now Is : " + operAr[i]);
            PlanTaskList plantasklist = new PlanTaskList();
            plantasklist.setId(super.getDbService().getSeq("plantasklist", 50));
            plantasklist.setTaskid(taskAr[i]);
            plantasklist.setPlanid(bean.getId());

            super.getService().createPlanTaskList(plantasklist);
        }

        return forwardInfoPage(mapping, request, "0204");
    }

    /**
     * ������ȼƻ��Զ����� �¶ȼƻ� ����
     */
    public ActionForward preSetMPByYP(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        // System.out.println( "preSetMPByYP" );

        String ypId = super.getLoginUserInfo(request).getRegionID()
                + super.getLoginUserInfo(request).getDeptID() + request.getParameter("year");
        // ����䴿����Ϊ����д��,Ϊ�˼��ݰ�����ǰ�ļƻ�
        String ypIdOld = super.getLoginUserInfo(request).getRegionID()
                + request.getParameter("year");

        // System.out.println( "��ȼƻ�id" + ypId );
        String regionid = super.getLoginUserInfo(request).getRegionid();
        // System.out.println("��ȼƻ�����" + regionid);

        Vector taskListVct = new Vector();

        // ���Ψһ�� 1,������.-1,����
        if (super.getService().checkYMPlanUnique(ypId) == 1
                && super.getService().checkYMPlanUnique(ypIdOld) == 1) { // �����ڸüƻ�
            request.setAttribute("taskListVct", taskListVct);
        } else {
            // ����ȼƻ����ڣ�����Ĭ������
            if (super.getService().checkYMPlanUnique(ypId) != 1) {
                // System.out.println( "new plan: " + ypId );
                taskListVct = super.getService().preGetTaskByParentPlan(ypId, regionid, 1, "");
            } else {
                if (super.getService().checkYMPlanUnique(ypIdOld) != 1) {
                    // System.out.println( "OLD plan: " + ypIdOld );
                    taskListVct = super.getService().preGetTaskByParentPlan(ypIdOld, regionid, 1,
                            "");
                }
            }
            request.setAttribute("taskListVct", taskListVct);
        }
        return mapping.findForward("preSetMPResult");
    }

    /**
     * ����һ���¼ƻ�
     */
    public ActionForward addMonthPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        // Log
        log(request, " �����¶�Ѳ��ƻ� ", " Ѳ��ƻ�����");
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        YearMonthPlanBean bean = (YearMonthPlanBean) form;
        String planidold = super.getLoginUserInfo(request).getRegionid() + bean.getYear()
                + bean.getMonth();
        bean.setId(super.getLoginUserInfo(request).getRegionid()
                + super.getLoginUserInfo(request).getDeptID() + bean.getYear() + bean.getMonth()); // �¶ȼƻ����Ϊ���򣫵�λ��ţ����+�·�
        // System.out.println( "planid " + bean.getId() );
        bean.setPlantype("2"); // 1,��ȼƻ� 2���¶ȼƻ�
        bean.setRegionid(super.getLoginUserInfo(request).getRegionid());
        bean.setCreator(super.getLoginUserInfo(request).getUserName());
        bean.setCreatedate(DateUtil.getNowDateString());
        bean.setDeptid(super.getLoginUserInfo(request).getDeptID());
        bean.setIfinnercheck("1");
        bean.setCreateuserid(userinfo.getUserID());

        YearMonthPlan data = new YearMonthPlan();
        BeanUtil.objectCopy(bean, data);
        // ���Ψһ��
        if (super.getService().checkYMPlanUnique(bean.getId()) == 1
                && super.getService().checkYMPlanUnique(planidold) == 1) {
            // ���Ͷ���
            if (request.getSession().getAttribute("isSendSm").equals("send")) {
                String objectman = request.getParameter("phone");
                if (objectman != null && !objectman.equals("")) {
                    String msg = "����֪ͨ��"
                            + request.getSession().getAttribute("LOGIN_USER_DEPT_NAME")
                            + "�������¶�Ѳ��ƻ��ȴ���������  �����ˣ�" + userinfo.getUserName();
                    SendSMRMI.sendNormalMessage(userinfo.getUserID(), objectman, msg, "00");
                    // System.out.println( msg );
                }
            }
            // ������¼ƻ�
            super.getService().createYMPlan(data);
            log(request, "����¼ƻ����ƻ�����Ϊ��" + bean.getPlanname() + "��", "Ѳ�����");
        } else {
            String errmsg = "���µ��¶ȼƻ��Ѿ�����, Ҫ�ڴ˻������޸���?";
            request.setAttribute("errmsg", errmsg);
            if (super.getService().checkYMPlanUnique(bean.getId()) != 1) {
                request.setAttribute("objectid", bean.getId());
            }
            if (super.getService().checkYMPlanUnique(planidold) != 1) {
                request.setAttribute("objectid", planidold);
            }
            request.setAttribute("fID", "2"); // month
            return mapping.findForward("ymplanError");
        }
        // ���ƻ��������������üƻ���Ӧ����Ŀɾ��
        // super.getService().removeRelatedTask( bean.getId() );

        // ����ƻ������б�
        String[] taskAr = request.getParameterValues("taskcheck");
        for (int i = 0; i < taskAr.length; i++) {
            // System.out.println(" Now Is : " + operAr[i]);
            PlanTaskList plantasklist = new PlanTaskList();
            plantasklist.setId(super.getDbService().getSeq("plantasklist", 50));
            plantasklist.setTaskid(taskAr[i]);
            plantasklist.setPlanid(bean.getId());

            super.getService().createPlanTaskList(plantasklist);
        }

        return forwardInfoPage(mapping, request, "0205");
    }

    /**
     * ȡ�üƻ���Ӧ�����б�
     */
    public ActionForward getTaskListByPlanid(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        String planid = request.getParameter("planid");

        // System.out.println("�ƻ�id" + planid);
        // String bdate = request.getParameter("bdate");

        String weekNumber = ""; // super.getService().getWeekOfYear(bdate);
        Vector taskListVct = new Vector();

        taskListVct = super.getService().getTasklistByPlanID(planid, "YMPLAN");
        request.setAttribute("taskListVct", taskListVct);
        request.setAttribute("weekNumber", weekNumber);

        return mapping.findForward("preSetMPResult");
    }

    /**
     * ���� �ƻ�
     */
    public ActionForward queryYMPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        ActionForm queryForm = form;
        if (request.getParameter("flag") == null && request.getParameter("flag1") != null
                && request.getSession().getAttribute("yplanbean") != null) {
            form = (ActionForm) request.getSession().getAttribute("yplanbean");
        }
        String contractorId;
        if (request.getParameter("flag") != null && request.getParameter("flag1") != null
                && request.getSession().getAttribute("contractorid") != null) {
            contractorId = (String) request.getSession().getAttribute("contractorid");
        } else {
            contractorId = request.getParameter("workID");
        }
        if ("1".equals(request.getParameter("isQuery"))) {
            contractorId = request.getParameter("workID");
            form = queryForm;
        }
        YearMonthPlanBean bean = (YearMonthPlanBean) form;
        String fID = request.getParameter("fID"); // 1, year 2, month
        String forwardPage = "queryYearPlanResult";
        if (fID.equals("2")) {
            forwardPage = "queryMonthPlanResult";
        }

        String sql = "select id, planname, year, month, decode(ifinnercheck,'1','ͨ��','2','������','3','δͨ��','ͨ��') ifinnercheck, decode(status, '1','ͨ��','-1','δͨ��','������') approvestatus   from yearmonthplan   ";

        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);

        if (fID.equals("2")) {
            sqlBuild.addConditionAnd("plantype = {0}", "2");
        } else {
            sqlBuild.addConditionAnd("plantype = {0}", "1");
        }
        sqlBuild.addConditionAnd("year = {0}", bean.getYear());
        sqlBuild.addConditionAnd("month = {0}", bean.getMonth());
        sqlBuild.addConditionAnd("status = {0}", bean.getStatus());
        sqlBuild.addConditionAnd("remark != {0}", "2"); // �����޸ĵĸ���

        // sqlBuild.addConstant( "and yearmonthplan.regionid IN (SELECT regionid
        // "
        // + " FROM region "
        // + " CONNECT BY PRIOR regionid = parentregionid "
        // + " START WITH regionid = '" + super.getLoginUserInfo( request
        // ).getRegionid() + "')" );
        //
        // if( super.getLoginUserInfo( request ).getDeptype().equals( "2" ) ){
        // sqlBuild.addConstant( " and yearmonthplan.deptid in( SELECT
        // contractorid FROM contractorinfo CONNECT BY PRIOR
        // contractorid=PARENTCONTRACTORID START WITH contractorid='" +
        // super.getLoginUserInfo( request ).getDeptID() + "')" );
        // }
        // ʡ�ƶ�
        if (super.getLoginUserInfo(request).getType().equals("11")) {
            if (request.getParameter("regionid") != null
                    && !request.getParameter("regionid").equals("")) {
                sqlBuild.addConstant(" and  yearmonthplan.regionid IN (SELECT     regionid "
                        + " FROM region " + " CONNECT BY PRIOR regionid = parentregionid "
                        + " START WITH regionid = '" + request.getParameter("regionid") + "')");
                if (contractorId != null && !contractorId.equals("")) {
                    sqlBuild
                            .addConstant(" and yearmonthplan.deptid in( SELECT contractorid "
                                    + " FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID "
                                    + " START WITH contractorid='" + contractorId + "')");
                }
            }
        }
        // ���ƶ�
        if (super.getLoginUserInfo(request).getType().equals("12")) {
            sqlBuild.addConstant(" and  yearmonthplan.regionid IN (SELECT     regionid "
                    + " FROM region " + " CONNECT BY PRIOR regionid = parentregionid "
                    + " START WITH regionid = '" + super.getLoginUserInfo(request).getRegionid()
                    + "')");
            logger.info("workID" + contractorId);
            if (contractorId != null && !contractorId.equals("")) {
                sqlBuild.addConstant(" and yearmonthplan.deptid in( SELECT contractorid "
                        + " FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID "
                        + " START WITH contractorid='" + contractorId + "')");
            }
        }
        // ʡ��ά
        if (super.getLoginUserInfo(request).getType().equals("21")) {
            if (request.getParameter("regionid") != null
                    && !request.getParameter("regionid").equals("")) {
                sqlBuild.addConstant(" and  yearmonthplan.regionid IN (SELECT     regionid "
                        + " FROM region " + " CONNECT BY PRIOR regionid = parentregionid "
                        + " START WITH regionid = '" + request.getParameter("regionid") + "')");
                if (contractorId != null && !contractorId.equals("")) {
                    sqlBuild
                            .addConstant(" and yearmonthplan.deptid in( SELECT contractorid "
                                    + " FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID "
                                    + " START WITH contractorid='" + contractorId + "')");
                } else {
                    sqlBuild.addConstant(" and yearmonthplan.deptid in( SELECT contractorid FROM "
                            + "contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID "
                            + "START WITH contractorid='"
                            + super.getLoginUserInfo(request).getDeptID() + "')");
                }
            }
        }
        if (super.getLoginUserInfo(request).getType().equals("22")) {
            sqlBuild.addConstant("and  yearmonthplan.regionid IN (SELECT     regionid "
                    + " FROM region " + " CONNECT BY PRIOR regionid = parentregionid "
                    + " START WITH regionid = '" + super.getLoginUserInfo(request).getRegionid()
                    + "')");
            sqlBuild
                    .addConstant(" and yearmonthplan.deptid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
                            + super.getLoginUserInfo(request).getDeptID() + "')");
        }
        sqlBuild.addConstant(" order by year desc,regionid ,planname desc ");// id
        // desc,

        sql = sqlBuild.toSql();
        logger.info("�������¼ƻ�: " + sql);

        List list = super.getDbService().queryBeans(sql);
        // request.setAttribute("queryplan", list);
        request.getSession().setAttribute("queryresult", list);
        request.getSession().setAttribute("yplanbean", bean);
        request.getSession().setAttribute("contractorid", contractorId);

        this.setPageReset(request);
        return mapping.findForward(forwardPage);
    }

    /**
     * ���� ������ �ƻ�
     */
    public ActionForward getUnapprovedYMPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        String forwardPage = "getUnapprovedYMPlanResult";
        String sql = "select id, planname, decode(plantype,'1','��ȼƻ�','2','�¶ȼƻ�','��ȼƻ�') plantype,"
                + "decode(status,'1','ͨ��','0','������','������') stat, decode(remark, '1','�¼ƻ�','2','�޸ļƻ�','�¼ƻ�') status "
                + " from yearmonthplan  ";
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
        sqlBuild
        // .addConstant(" ifinnercheck = '1' and (APPROVER is null or status =
        // '-1') and remark != '0'");
                .addConstant(" ifinnercheck =  '1'  and status='0' and remark != '0'");

        sqlBuild.addRegion(super.getLoginUserInfo(request).getRegionid());
        sqlBuild.addConstant("  order by plantype,planname");

        sql = sqlBuild.toSql();
        System.out.println("sql===================   " + sql);

        List list = super.getDbService().queryBeans(sql);
        request.getSession().setAttribute("queryresult", list);

        this.setPageReset(request);
        return mapping.findForward(forwardPage);
    }

    /**
     * ����һ���ƻ����
     */
    public ActionForward loadYMPlanForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        String fID = request.getParameter("fID");
        YearMonthPlanBO ymbo = new YearMonthPlanBO();
        YearMonthPlanBean bean = ymbo.getYMPlanInfo(request.getParameter("id"));
        request.getSession().setAttribute("YearMonthPlanBean", bean);
        String createtime = bean.getCreatedate();
        if (createtime != null && !"".equals(createtime)) {
            Date time = DateUtil.parseDate(createtime);
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            String ctime = format.format(time);
            request.setAttribute("ctime", ctime);
        }

        Vector taskListVct = super.getService().getTasklistByPlanID(bean.getId(), "YMPLAN");
        request.getSession().setAttribute("tasklist", taskListVct);

        request.setAttribute("fID", fID);

        return mapping.findForward("ymplanform");
    }

    /**
     * ����������Ϣ����
     */
    public ActionForward ExportYMPlanform(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        try {
            YearMonthPlanBean planbean = (YearMonthPlanBean) request.getSession().getAttribute(
                    "YearMonthPlanBean");
            Vector taskVct = (Vector) request.getSession().getAttribute("tasklist");

            super.getService().ExportYMPlanform(planbean, taskVct, response);
            return null;
        } catch (Exception e) {
            // System.out.println("ActionForward ExportYMPlanform ERROR:" +
            // e.getMessage());
            return null;
        }
    }

    /**
     * ����һ�� ��/�� �ƻ� �� ������
     */
    public ActionForward loadYMPlan4Approve(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        YearMonthPlan data = super.getService().loadymPlan(request.getParameter("id"));
        YearMonthPlanBean bean = new YearMonthPlanBean();
        BeanUtil.objectCopy(data, bean);
        request.setAttribute("YearMonthPlanBean", bean);

        return mapping.findForward("approveYMPlan");
    }

    /**
     * ����һ���ƻ�
     */
    public ActionForward ApprovePlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        ctx = getWebApplicationContext();
        YearMonthPlanBean bean = (YearMonthPlanBean) form;
        YearMonthPlan data = new YearMonthPlan();
        BeanUtil.objectCopy(bean, data);
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String planid = bean.getId();
        String status = bean.getStatus();
        if (planid.substring(planid.length() - 1).equalsIgnoreCase("m")) {

            if (status != null && status.equals("1")) {
                super.getService().replaceTask(planid);

                super.getService().delYMPlan(data);
                super.getService().removeRelatedTask(bean.getId());
            }
            bean.setId(planid.substring(0, planid.length() - 1));
        }
        bean.setApprover(super.getLoginUserInfo(request).getUserName());
        String sql = "update YEARMONTHPLAN set status = '" + bean.getStatus() + "' , APPROVER='"
                + bean.getApprover() + "' , APPROVEDATE=SYSDATE WHERE ID='" + bean.getId() + "'";
        super.getDbService().dbUpdate(sql);
        /* ��ʼ���� �ƻ�����ϸ�ڱ� */
        Planapprove planapprove = new Planapprove();

        planapprove.setId(super.getDbService().getSeq("planapprovemaster", 20));
        planapprove.setPlanid(bean.getId());
        planapprove.setApprover(bean.getApprover());
        planapprove.setApprovedate(DateUtil.StringToDate(DateUtil.getNowDateString()));
        planapprove.setApprovecontent(bean.getApprovecontent());
        planapprove.setStatus(bean.getStatus());

        // super.getService().addPlanapprove(planapprove);
        String phone = new UserInfoDAOImpl().findById(
                super.getService().loadymPlan(bean.getId()).getCreateuserid()).getPhone();
        super.getService().addPlanapproveWithSql(planapprove);
        RmiSmProxyService smSendProxy = (RmiSmProxyService) ctx.getBean("rmiSmProxyService");
        // SmHistoryDAO historyDAO=(SmHistoryDAO)ctx.getBean("smHistoryDAO");
        if (phone != null && !phone.equals("")) {
            String msg = "����·Ѳ�졿����һ������Ϊ��" + bean.getPlanname() + "���ļƻ�";
            if (status != null && status.equals("1")) {
                msg += "�Ѿ�ͨ��������  �����ˣ�" + userinfo.getUserName();
            } else {
                msg += "δͨ�������� �����ˣ�" + userinfo.getUserName();
            }
            logger.info("��ƻ������Ϣ��" + msg + "   phone:" + phone);
            try {
                smSendProxy.simpleSend(phone, msg, null, null, true);
            } catch (Exception ex) {
                logger.error("���ŷ���ʧ��:", ex);
            }
            if (status != null && status.equals("-1")) {
                if (bean.getPlantype().equals("1")) {
                    log(request, "�����ƻ�δͨ�����ƻ�����Ϊ��" + bean.getPlanname() + "��", "Ѳ�����");
                    return forwardInfoPage(mapping, request, "yearPlanNotPass");
                } else {
                    log(request, "����¼ƻ�δͨ�����ƻ�����Ϊ��" + bean.getPlanname() + "��", "Ѳ�����");
                    return forwardInfoPage(mapping, request, "monthPlanNotPass");
                }
            }
            // SMHistory history = new SMHistory();
            // logger.info("���ſ�ʼ���ͣ�д�����ݿ���ŷ�����ʷ������");
            // history.setSimIds(phone);
            // history.setSendContent(msg);
            // history.setSendTime(new Date());
            // // history.setSendState(sendState);
            // history.setSmType("YEAR_MONTH_PLAN");
            // history.setObjectId(data.getId());
            // history.setBusinessModule("yearmonthplan");
            // historyDAO.save(history);
            // logger.info("д�����ݿ���ŷ�����ʷ��ɡ�");
        }
        // ���Ͷ���
        // System.out.println( bean.getId() );
        // if (request.getSession().getAttribute("isSendSm").equals("send")) {
        // String objectman = phone;
        // if (objectman != null && !objectman.equals("")) {
        // String msg = "";
        // if (bean.getStatus().equals("1")) {
        // msg = "������������ύ�������Ѿ�ͨ������. �����ˣ�" + userinfo.getUserName();
        // } else {
        // msg = "������������ύ������δͨ������," + bean.getApprovecontent()
        // + " �����ˣ�" + userinfo.getUserName();
        // }
        // SendSMRMI.sendNormalMessage(userinfo.getUserID(), objectman,
        // msg, "00");
        // // System.out.println( msg );
        // }
        // }

        /* �������� �ƻ�����ϸ�ڱ� */
        log(request, "��˼ƻ�ͨ�����ƻ�����Ϊ��" + bean.getPlanname() + "��", "Ѳ�����");
        return forwardInfoPage(mapping, request, "0206");
    }

    public ActionForward SearchYMPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String sql1 = "";
        String sql2 = "";
        if (userinfo.getType().equals("11")) {
            sql1 = "SELECT r.REGIONID,r.REGIONNAME FROM REGION r where state is null and substr(r.REGIONID,3,4) != '1111'";
            sql2 = "SELECT c.CONTRACTORID,c.CONTRACTORNAME,c.REGIONID FROM CONTRACTORINFO c WHERE c.state IS NULL";
        }
        if (userinfo.getType().equals("12")) {
            sql1 = "SELECT r.REGIONID,r.REGIONNAME FROM REGION r where state is null and regionid="
                    + userinfo.getRegionid() + " and substr(r.REGIONID,3,4) != '1111'";
            sql2 = "SELECT c.CONTRACTORID,c.CONTRACTORNAME,c.REGIONID FROM CONTRACTORINFO c WHERE c.state IS NULL and c.regionid='"
                    + userinfo.getRegionid() + "'";

        }
        if (userinfo.getType().equals("21")) {
            sql1 = "SELECT r.REGIONID,r.REGIONNAME FROM REGION r where state is null and substr(r.REGIONID,3,4) != '1111'";
            sql2 = "SELECT c.CONTRACTORID,c.CONTRACTORNAME,c.REGIONID FROM CONTRACTORINFO c WHERE c.state IS NULL and c.parentcontractorid='"
                    + userinfo.getDeptID() + "'";
        }
        List lRegion = null;
        List lContractor = null;
        QueryUtil qu = new QueryUtil();
        if (!sql1.equals("") && !sql2.equals("")) {
            try {
                logger.info("userinfo.getType()" + userinfo.getType());
                lRegion = qu.queryBeans(sql1);
                logger.info("sql1" + sql1);
            } catch (Exception ex) {
                logger.error("��ȡ������Ϣ����" + ex.getMessage());
                lRegion = null;
            }
            try {
                lContractor = qu.queryBeans(sql2);
                logger.info("sql2" + sql2);
            } catch (Exception ex) {
                logger.error("��ȡ��ά��Ϣ����" + ex.getMessage());
                lContractor = null;
            }
        }
        String fID = request.getParameter("fID"); // 1, year 2, month
        String strforward = "searchYearPlan";
        if (fID.equals("2")) {
            strforward = "searchMonthPlan";
        }
        request.setAttribute("lRegion", lRegion);
        request.setAttribute("lContractor", lContractor);
        return mapping.findForward(strforward);

    }

}
