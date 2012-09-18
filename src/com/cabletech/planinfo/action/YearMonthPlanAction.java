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
     * 创建年\月计划,将其保存在session中
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
        // 清除session中的无用数据.
        session.removeAttribute("EditS");
        session.removeAttribute("plantype");
        session.removeAttribute("YMplan");
        session.removeAttribute("taskList");
        // 组合业务对象
        String planidold = "";
        if (_bean.getPlantype().equals("1")) {
            session.setAttribute("plantype", "1");
            planidold = super.getLoginUserInfo(request).getRegionid() + _bean.getYear();
            _bean.setId(super.getLoginUserInfo(request).getRegionid()
                    + super.getLoginUserInfo(request).getDeptID() + _bean.getYear()); // 年度计划编号为区域编号＋单位编号＋年度
            boolean b = pbs.isOldYMPlan(_bean.getYear(), _bean.getMonth());
            if (b) {
                return forwardInfoPage(mapping, request, "warn0301");
            }
        } else {
            session.setAttribute("plantype", "2");
            /* 验证是否当年计划已制定 */
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
                    + _bean.getMonth()); // 月度计划编号为区域＋单位编号＋年份+月份
            planidold = super.getLoginUserInfo(request).getRegionid() + _bean.getYear()
                    + _bean.getMonth();
        }
        // _bean.setPlantype("1"); // 1,年度计划 2，月度计划
        _bean.setRegionid(super.getLoginUserInfo(request).getRegionid());
        _bean.setCreator(super.getLoginUserInfo(request).getUserName());
        _bean.setCreatedate(DateUtil.getNowDateString());
        _bean.setDeptid(super.getLoginUserInfo(request).getDeptID());
        _bean.setIfinnercheck("1");
        _bean.setCreateuserid(userinfo.getUserID());
        session.setAttribute("EditS", "add");
        session.setAttribute("taskList", new ArrayList());
        // 检查计划是否存在.
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
     * 保存年月计划信息
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
                log(request, "新建年计划（计划名称为：" + ymPlan.getPlanname() + "）", "巡检管理");
                strFor = "s20101";
            } else {
                log(request, "新建月计划（计划名称为：" + ymPlan.getPlanname() + "）", "巡检管理");
                strFor = "s20201";
            }
            RmiSmProxyService smSendProxy = (RmiSmProxyService) ctx.getBean("rmiSmProxyService");
            SmHistoryDAO historyDAO = (SmHistoryDAO) ctx.getBean("smHistoryDAO");
            if (phone != null && !phone.equals("")) {
                String msg = "【线路巡检】您有一个名称为“" + ymPlan.getPlanname() + "”的计划等待您的审批！";
                logger.info(msg);
                try {
                    smSendProxy.simpleSend(phone, msg, null, null, true);
                } catch (Exception ex) {
                    logger.error("短信发送失败:", ex);
                }
                // SMHistory history = new SMHistory();
                // logger.info("短信开始发送，写入数据库短信发送历史。。。");
                // history.setSimIds(phone);
                // history.setSendContent(msg);
                // history.setSendTime(new Date());
                // // history.setSendState(sendState);
                // history.setSmType("YEAR_MONTH_PLAN");
                // history.setObjectId(ymPlan.getId());
                // history.setBusinessModule("yearmonthplan");
                // historyDAO.save(history);
                // logger.info("写入数据库短信发送历史完成。");
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
        // 清除session
        session.removeAttribute("YMplan");
        session.removeAttribute("taskList");
        session.removeAttribute("EditS");
        session.removeAttribute("plantype");
        return forwardInfoPage(mapping, request, strFor);
    }

    /**
     * 载入一个 年/月 计划
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
        UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");// 获取用户信息
        String fID = request.getParameter("fID");// fID为1,年度计划 2，月度计划
        YearMonthPlanBO ymbo = new YearMonthPlanBO();
        YearMonthPlanBean bean = new YearMonthPlanBean();
        // YearMonthPlan data =
        // ymbo.getYMPlanInfo(request.getParameter("id"));//获取年度计划.
        bean = ymbo.getYMPlanInfo(request.getParameter("id"));// 获取年度计划.
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
     * 更新年月计划
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
        ymPlan.setStatus("0");// 待审批
        boolean b = ymbo.saveOrUpdateYMPlan(ymPlan, taskList);
        logger.info("--=-" + b);
        if (b) {
            if (ymPlan.getPlantype().equals("1")) {
                log(request, "更新年计划（计划名称为：" + ymPlan.getPlanname() + "）", "巡检管理");
                strFor = "s20102";
            } else {
                log(request, "更新月计划（计划名称为：" + ymPlan.getPlanname() + "）", "巡检管理");
                strFor = "s20202";
            }
            RmiSmProxyService smSendProxy = (RmiSmProxyService) ctx.getBean("rmiSmProxyService");
            SmHistoryDAO historyDAO = (SmHistoryDAO) ctx.getBean("smHistoryDAO");
            if (phone != null && !phone.equals("")) {
                String msg = "【线路巡检】您有一个名称为“" + ymPlan.getPlanname() + "”的计划等待您的审批！";
                logger.info("修改年计划:" + msg + " phone:" + phone);
                try {
                    smSendProxy.simpleSend(phone, msg, null, null, true);
                } catch (Exception ex) {
                    logger.error("短信发送失败:", ex);
                }
                // SMHistory history = new SMHistory();
                // logger.info("短信开始发送，写入数据库短信发送历史。。。");
                // history.setSimIds(phone);
                // history.setSendContent(msg);
                // history.setSendTime(new Date());
                // // history.setSendState(sendState);
                // history.setSmType("YEAR_MONTH_PLAN");
                // history.setObjectId(ymPlan.getId());
                // history.setBusinessModule("yearmonthplan");
                // historyDAO.save(history);
                // logger.info("写入数据库短信发送历史完成。");
            }
            // if (request.getSession().getAttribute("isSendSm").equals("send"))
            // {
            // String objectman = request.getParameter("phone");
            // if (objectman != null && !objectman.equals("")) {
            // String msg = "申请通知："
            // + request.getSession().getAttribute(
            // "LOGIN_USER_DEPT_NAME")
            // + "有新增巡检计划等待您的审批. 申请人：" + userinfo.getUserName();
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
        // 清除session
        session.removeAttribute("YMplan");
        session.removeAttribute("taskList");
        session.removeAttribute("EditS");
        session.removeAttribute("plantype");
        return forwardInfoPage(mapping, request, strFor);
    }

    /**
     * 删除一个年月计划
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
        if (plantype.equals("1")) { // 删除年计划
            // Log
            log(request, " 删除年度巡检计划 （计划名称为：" + ympbo.getYMPlanInfo(planid).getPlanname() + "）",
                    " 巡检计划管理 ");
            YearMonthPlanBO ym = new YearMonthPlanBO();
            ym.deleSplithTask();
            ympbo.removeYMPlan(data);
            return forwardInfoPage(mapping, request, "0210");
        } else { // 删除月计划
            ympbo.removeYMPlan(data);
            log(request, " 删除月度巡检计划（计划名称为：" + ympbo.getYMPlanInfo(planid).getPlanname() + "） ",
                    " 巡检计划管理 ");
            return forwardInfoPage(mapping, request, "0211");
        }
    }

    /**
     * 更新一个年计划
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
    // // 获取返回后的id
    // String id = data.getId();
    // // /
    // if (!thisplan.getStatus().equals("0")) { // 不能直接修改
    //
    // data.setId(data.getId() + "M"); // 年度计划编号为年份
    // data.setApprovedate(null);
    // data.setApprover(null);
    // data.setRemark("2"); // 标志：被修改的计划, M 副本
    // data.setStatus(null);
    //
    // ymplanid4Task = bean.getId() + "M";
    //
    // // 检查唯一性
    // if (super.getService().checkYMPlanUnique(data.getId()) == 1) {
    // // 保存年度计划
    // super.getService().createYMPlan(data);
    // thisplan.setRemark("0"); // 被审核时候被过滤掉
    // super.getService().updateYMPlan(thisplan);
    //
    // } else {
    // String errmsg = "该计划已经被修改过,但目前尚未得到批复, 要在此基础上修改吗?";
    // request.setAttribute("errmsg", errmsg);
    // // 返回备份后的id
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
    // // 将计划任务关联表中与该计划对应的条目删除
    // // super.getService().removeRelatedTask( ymplanid4Task );
    // // 保存计划任务列表
    // String[] taskAr = request.getParameterValues("taskcheck");
    // YearMonthPlanBO ym = new YearMonthPlanBO();
    // // --先清计划任务列表
    // if (!ym.delePlanTask(bean.getId())) {
    // return forwardErrorPage(mapping, request, "error"); // 出错
    // }
    // // --写入计划任务数据
    // for (int i = 0; i < taskAr.length; i++) {
    // PlanTaskList plantasklist = new PlanTaskList();
    // plantasklist.setId(super.getDbService().getSeq("plantasklist", 50));
    // plantasklist.setTaskid(taskAr[i]);
    // plantasklist.setPlanid(ymplanid4Task);
    // super.getService().createPlanTaskList(plantasklist);
    // }
    //
    // if (bean.getPlantype().equals("1")) { // 更新年计划
    // // Log
    // log(request, " 更新年度巡检计划 ", " 巡检计划管理 ");
    // return forwardInfoPage(mapping, request, "0207");
    // } else { // 更新月计划
    // // Log
    // log(request, " 更新月度巡检计划 ", " 巡检计划管理 ");
    // return forwardInfoPage(mapping, request, "0208");
    // }
    // }
    /**
     * 新增年度计划
     */
    public ActionForward addYearPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        log(request, " 增加年度巡检计划 ", " 巡检计划管理 ");

        YearMonthPlanBean bean = (YearMonthPlanBean) form;
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String planidold = super.getLoginUserInfo(request).getRegionid() + bean.getYear();
        bean.setId(super.getLoginUserInfo(request).getRegionid()
                + super.getLoginUserInfo(request).getDeptID() + bean.getYear()); // 年度计划编号为区域编号＋单位编号＋年度
        bean.setPlantype("1"); // 1,年度计划 2，月度计划
        bean.setRegionid(super.getLoginUserInfo(request).getRegionid());
        bean.setCreator(super.getLoginUserInfo(request).getUserName());
        bean.setCreatedate(DateUtil.getNowDateString());
        bean.setDeptid(super.getLoginUserInfo(request).getDeptID());
        bean.setIfinnercheck("1");
        bean.setCreateuserid(userinfo.getUserID());

        YearMonthPlan data = new YearMonthPlan();
        BeanUtil.objectCopy(bean, data);
        // 检查唯一性
        if (super.getService().checkYMPlanUnique(bean.getId()) == 1
                && super.getService().checkYMPlanUnique(planidold) == 1) {
            // 发送短信
            if (request.getSession().getAttribute("isSendSm").equals("send")) {
                String objectman = request.getParameter("phone");
                if (objectman != null && !objectman.equals("")) {
                    String msg = "申请通知："
                            + request.getSession().getAttribute("LOGIN_USER_DEPT_NAME")
                            + "有新增年度巡检计划等待您的审批.  申请人：" + userinfo.getUserName();
                    SendSMRMI.sendNormalMessage(userinfo.getUserID(), objectman, msg, "00");
                    // System.out.println( msg );
                }
            }
            // 保存年度计划
            super.getService().createYMPlan(data);
            log(request, "添加年计划（计划名称为：" + bean.getPlanname() + "）", "巡检管理");
        } else {
            String errmsg = "该年的年度计划已经存在, 要在此基础上修改吗?";
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
            // );//弹出警告，提示用户该年计划已经存在。
        }
        // 将计划任务关联表中与该计划对应的条目删除
        // super.getService().removeRelatedTask( bean.getId() );
        // 保存计划任务列表
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
     * 根据年度计划自动分配 月度计划 任务
     */
    public ActionForward preSetMPByYP(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        // System.out.println( "preSetMPByYP" );

        String ypId = super.getLoginUserInfo(request).getRegionID()
                + super.getLoginUserInfo(request).getDeptID() + request.getParameter("year");
        // 该语句纯粹是为安徽写的,为了兼容安徽以前的计划
        String ypIdOld = super.getLoginUserInfo(request).getRegionID()
                + request.getParameter("year");

        // System.out.println( "年度计划id" + ypId );
        String regionid = super.getLoginUserInfo(request).getRegionid();
        // System.out.println("年度计划区域" + regionid);

        Vector taskListVct = new Vector();

        // 检查唯一性 1,不存在.-1,存在
        if (super.getService().checkYMPlanUnique(ypId) == 1
                && super.getService().checkYMPlanUnique(ypIdOld) == 1) { // 不存在该计划
            request.setAttribute("taskListVct", taskListVct);
        } else {
            // 该年度计划存在，分配默认任务
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
     * 增加一个月计划
     */
    public ActionForward addMonthPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
        // Log
        log(request, " 增加月度巡检计划 ", " 巡检计划管理");
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        YearMonthPlanBean bean = (YearMonthPlanBean) form;
        String planidold = super.getLoginUserInfo(request).getRegionid() + bean.getYear()
                + bean.getMonth();
        bean.setId(super.getLoginUserInfo(request).getRegionid()
                + super.getLoginUserInfo(request).getDeptID() + bean.getYear() + bean.getMonth()); // 月度计划编号为区域＋单位编号＋年份+月份
        // System.out.println( "planid " + bean.getId() );
        bean.setPlantype("2"); // 1,年度计划 2，月度计划
        bean.setRegionid(super.getLoginUserInfo(request).getRegionid());
        bean.setCreator(super.getLoginUserInfo(request).getUserName());
        bean.setCreatedate(DateUtil.getNowDateString());
        bean.setDeptid(super.getLoginUserInfo(request).getDeptID());
        bean.setIfinnercheck("1");
        bean.setCreateuserid(userinfo.getUserID());

        YearMonthPlan data = new YearMonthPlan();
        BeanUtil.objectCopy(bean, data);
        // 检查唯一性
        if (super.getService().checkYMPlanUnique(bean.getId()) == 1
                && super.getService().checkYMPlanUnique(planidold) == 1) {
            // 发送短信
            if (request.getSession().getAttribute("isSendSm").equals("send")) {
                String objectman = request.getParameter("phone");
                if (objectman != null && !objectman.equals("")) {
                    String msg = "申请通知："
                            + request.getSession().getAttribute("LOGIN_USER_DEPT_NAME")
                            + "有新增月度巡检计划等待您的审批  申请人：" + userinfo.getUserName();
                    SendSMRMI.sendNormalMessage(userinfo.getUserID(), objectman, msg, "00");
                    // System.out.println( msg );
                }
            }
            // 保存该月计划
            super.getService().createYMPlan(data);
            log(request, "添加月计划（计划名称为：" + bean.getPlanname() + "）", "巡检管理");
        } else {
            String errmsg = "该月的月度计划已经存在, 要在此基础上修改吗?";
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
        // 将计划任务关联表中与该计划对应的条目删除
        // super.getService().removeRelatedTask( bean.getId() );

        // 保存计划任务列表
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
     * 取得计划对应任务列表
     */
    public ActionForward getTaskListByPlanid(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        String planid = request.getParameter("planid");

        // System.out.println("计划id" + planid);
        // String bdate = request.getParameter("bdate");

        String weekNumber = ""; // super.getService().getWeekOfYear(bdate);
        Vector taskListVct = new Vector();

        taskListVct = super.getService().getTasklistByPlanID(planid, "YMPLAN");
        request.setAttribute("taskListVct", taskListVct);
        request.setAttribute("weekNumber", weekNumber);

        return mapping.findForward("preSetMPResult");
    }

    /**
     * 检索 计划
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

        String sql = "select id, planname, year, month, decode(ifinnercheck,'1','通过','2','待审批','3','未通过','通过') ifinnercheck, decode(status, '1','通过','-1','未通过','待审批') approvestatus   from yearmonthplan   ";

        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);

        if (fID.equals("2")) {
            sqlBuild.addConditionAnd("plantype = {0}", "2");
        } else {
            sqlBuild.addConditionAnd("plantype = {0}", "1");
        }
        sqlBuild.addConditionAnd("year = {0}", bean.getYear());
        sqlBuild.addConditionAnd("month = {0}", bean.getMonth());
        sqlBuild.addConditionAnd("status = {0}", bean.getStatus());
        sqlBuild.addConditionAnd("remark != {0}", "2"); // 过滤修改的副本

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
        // 省移动
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
        // 市移动
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
        // 省代维
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
        logger.info("查找年月计划: " + sql);

        List list = super.getDbService().queryBeans(sql);
        // request.setAttribute("queryplan", list);
        request.getSession().setAttribute("queryresult", list);
        request.getSession().setAttribute("yplanbean", bean);
        request.getSession().setAttribute("contractorid", contractorId);

        this.setPageReset(request);
        return mapping.findForward(forwardPage);
    }

    /**
     * 检索 待审批 计划
     */
    public ActionForward getUnapprovedYMPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {

        String forwardPage = "getUnapprovedYMPlanResult";
        String sql = "select id, planname, decode(plantype,'1','年度计划','2','月度计划','年度计划') plantype,"
                + "decode(status,'1','通过','0','待审批','待审批') stat, decode(remark, '1','新计划','2','修改计划','新计划') status "
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
     * 载入一个计划表格
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
     * 导出任务信息报表
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
     * 载入一个 年/月 计划 ， 审批用
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
     * 审批一个计划
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
        /* 开始处理 计划审批细节表 */
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
            String msg = "【线路巡检】您有一个名称为“" + bean.getPlanname() + "”的计划";
            if (status != null && status.equals("1")) {
                msg += "已经通过审批。  审批人：" + userinfo.getUserName();
            } else {
                msg += "未通过审批。 审批人：" + userinfo.getUserName();
            }
            logger.info("年计划审核信息：" + msg + "   phone:" + phone);
            try {
                smSendProxy.simpleSend(phone, msg, null, null, true);
            } catch (Exception ex) {
                logger.error("短信发送失败:", ex);
            }
            if (status != null && status.equals("-1")) {
                if (bean.getPlantype().equals("1")) {
                    log(request, "审核年计划未通过（计划名称为：" + bean.getPlanname() + "）", "巡检管理");
                    return forwardInfoPage(mapping, request, "yearPlanNotPass");
                } else {
                    log(request, "审核月计划未通过（计划名称为：" + bean.getPlanname() + "）", "巡检管理");
                    return forwardInfoPage(mapping, request, "monthPlanNotPass");
                }
            }
            // SMHistory history = new SMHistory();
            // logger.info("短信开始发送，写入数据库短信发送历史。。。");
            // history.setSimIds(phone);
            // history.setSendContent(msg);
            // history.setSendTime(new Date());
            // // history.setSendState(sendState);
            // history.setSmType("YEAR_MONTH_PLAN");
            // history.setObjectId(data.getId());
            // history.setBusinessModule("yearmonthplan");
            // historyDAO.save(history);
            // logger.info("写入数据库短信发送历史完成。");
        }
        // 发送短信
        // System.out.println( bean.getId() );
        // if (request.getSession().getAttribute("isSendSm").equals("send")) {
        // String objectman = phone;
        // if (objectman != null && !objectman.equals("")) {
        // String msg = "";
        // if (bean.getStatus().equals("1")) {
        // msg = "审批结果：您提交的申请已经通过审批. 审批人：" + userinfo.getUserName();
        // } else {
        // msg = "审批结果：您提交的申请未通过审批," + bean.getApprovecontent()
        // + " 审批人：" + userinfo.getUserName();
        // }
        // SendSMRMI.sendNormalMessage(userinfo.getUserID(), objectman,
        // msg, "00");
        // // System.out.println( msg );
        // }
        // }

        /* 结束处理 计划审批细节表 */
        log(request, "审核计划通过（计划名称为：" + bean.getPlanname() + "）", "巡检管理");
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
                logger.error("读取区域信息出错：" + ex.getMessage());
                lRegion = null;
            }
            try {
                lContractor = qu.queryBeans(sql2);
                logger.info("sql2" + sql2);
            } catch (Exception ex) {
                logger.error("读取代维信息出错：" + ex.getMessage());
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
