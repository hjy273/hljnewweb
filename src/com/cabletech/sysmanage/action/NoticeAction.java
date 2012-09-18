package com.cabletech.sysmanage.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import cabletech.sm.rmi.RmiSmProxyService;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.InitDisplayBO;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.web.ClientException;
import com.cabletech.sysmanage.beans.NoticeBean;
import com.cabletech.sysmanage.domainobjects.Notice;
import com.cabletech.sysmanage.services.NoticeBo;
import com.cabletech.sysmanage.services.SendMessageBO;

public class NoticeAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger("NoticeAction");
	private RmiSmProxyService smSendProxy;

	// private NoticeBo bo = new NoticeBo();
	// ����༭��
	public ActionForward addForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		return mapping.findForward("addform");
	}

	// ��ӹ���
	public ActionForward addNotice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		NoticeBean noticebean = (NoticeBean) form;
		if ("����".equals(noticebean.getType())) {
			if(noticebean.getMeetPerson() == null || noticebean.getMeetPerson().equals("")  ){
				return forwardInfoPage(mapping, request, "f723012");
			}
			noticebean.setAcceptUserIds(noticebean.getMeetPerson());
		}
		// �ϴ��ļ�
		// String fileid = uploadFile( form, new ArrayList() );
		Notice notice = new Notice();
		BeanUtil.objectCopy(noticebean, notice);
		List<FileItem> files = (List) session.getAttribute("FILES");

		notice.setRegionid(userinfo.getRegionID());
		// notice.setFileinfo(fileid);
		notice.setIssuedate(new Date());
		notice.setIssueperson(userinfo.getUserName());
		notice.setIsread("n");
		logger.info("������ " + notice.getIssueperson() + "  " + userinfo.getUserName());
		logger.info("���� " + notice.getRegionid() + "  " + userinfo.getRegionID());
		WebApplicationContext ctx = getWebApplicationContext();
		NoticeBo bo = (NoticeBo) ctx.getBean("noticeBo");
		
		notice = bo.saveNotice(notice, userinfo, files);
		boolean b = true;
		if (notice.getId() != null) {
			b = true;
		} else {
			b = false;
		}
		if (notice.getIsissue().equals("y")) {
			// smSendProxy = (RmiSmProxyService)
			// ctx.getBean("rmiSmProxyService");
			String sim = noticebean.getMobileIds();
			String content = "";
			String sendObjectName = "";
			if ("����".equals(noticebean.getType())) {
				content += "������"+noticebean.getMeetTime() ;
				content += "�μ�" + noticebean.getTitle() + "����";
//				content += "����" + noticebean.getMeetTime() + "��" + noticebean.getMeetEndTime();
//				content += "��" + noticebean.getMeetAddress();
//				content += "�μ�" + noticebean.getTitle() + "����";
				sendObjectName = noticebean.getTitle() + "���鶨ʱ���Ͷ���";
			} else {
				content = "���棺" + noticebean.getTitle();
				sendObjectName = noticebean.getTitle() + "���涨ʱ���Ͷ���";
			}
			logger.info(sim + ":" + content);
			// smSendProxy.simpleSend(sim, content, null, null, true);
			// sendMessage(content, sim);
			if (sim != null && !"".equals(sim)) {
				String sendMethod = noticebean.getSendMethod();
				String beginDate = noticebean.getBeginDate();
				String endDate = noticebean.getEndDate();
				String intervalType = noticebean.getSendIntervalType();
				String interval = noticebean.getSendTimeSpace();
				String sendCycleRule = request.getParameter("sendCycleRule");
				String[] inputDate = request.getParameterValues("inputDate");
				SendMessageBO sendMessageBo = (SendMessageBO) ctx.getBean("sendMessageBO");
				sendMessageBo.sendMessage("notice." + notice.getId(), content, sim, sendMethod, beginDate, endDate,
						intervalType, interval, userinfo.getUserID(), inputDate, sendObjectName, sendCycleRule);
			}
			return forwardInfoPage(mapping, request, assertResult(b, "s72301f", "f72301f"));
		} else {
			return forwardInfoPage(mapping, request, assertResult(b, "s72301", "f72301"));
		}
	}

	// ����༭��
	public ActionForward editForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String id = request.getParameter("id");
		WebApplicationContext ctx = getWebApplicationContext();
		NoticeBo bo = (NoticeBo) ctx.getBean("noticeBo");
		com.cabletech.linepatrol.commons.services.UserInfoBO userBo = (com.cabletech.linepatrol.commons.services.UserInfoBO) ctx
				.getBean("userInfoBo");
		Notice notice = bo.loadNotice(id);
		NoticeBean noticebean = new NoticeBean();
		BeanUtil.objectCopy(notice, noticebean);
		if (noticebean.getMeetTime() != null) {
			String[] meetTime = noticebean.getMeetTime().split(" ");
			if (meetTime != null && meetTime.length == 2) {
				noticebean.setMeetTimeDate(meetTime[0]);
				noticebean.setMeetTimeTime(meetTime[1]);
			}
		}
		if (noticebean.getMeetEndTime() != null) {
			String[] meetEndTime = noticebean.getMeetEndTime().split(" ");
			if (meetEndTime != null && meetEndTime.length == 2) {
				noticebean.setMeetEndTimeDate(meetEndTime[0]);
				noticebean.setMeetEndTimeTime(meetEndTime[1]);
			}
		}
		if (noticebean.getAcceptUserIds() != null) {
			String[] acceptUserId = noticebean.getAcceptUserIds().split(",");
			String acceptUserNames = "";
			String acceptUserTels = "";
			String userName;
			String userTel;
			DynaBean userInfoBean;
			for (int i = 0; acceptUserId != null && i < acceptUserId.length; i++) {
				if (acceptUserId[i] != null && !acceptUserId[i].equals("")) {
					userInfoBean = userBo.loadUserAndContractorPersonInfo(acceptUserId[i]);
					if (userInfoBean != null) {
						userName = (String) userInfoBean.get("name");
						userTel = (String) userInfoBean.get("mobile");
						if (userName != null && !userName.equals("")) {
							acceptUserNames += userName;
							acceptUserNames += ",";
						}
						if (userTel != null && !userTel.equals("")) {
							acceptUserTels += userTel;
							acceptUserTels += ",";
						}
					}
				}
			}
			if (acceptUserNames != null && !"".equals(acceptUserNames)) {
				noticebean.setAcceptUserNames(acceptUserNames);
			}
			if (acceptUserTels != null && !"".equals(acceptUserTels)) {
				noticebean.setAcceptUserTels(acceptUserTels);
			} else {
				noticebean.setAcceptUserTels("000");
			}
		}
		request.setAttribute("notice", noticebean);
		return mapping.findForward("editform");
	}

	// ����notice
	public ActionForward updateNotice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		NoticeBean noticebean = (NoticeBean) form;
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		Notice notice = new Notice();
		BeanUtil.objectCopy(noticebean, notice);
		List<FileItem> files = (List) session.getAttribute("FILES");
		notice.setRegionid(userinfo.getRegionID());
		notice.setIssuedate(new Date());
		notice.setIssueperson(userinfo.getUserName());
		notice.setIsread("n");
		WebApplicationContext ctx = getWebApplicationContext();
		NoticeBo bo = (NoticeBo) ctx.getBean("noticeBo");
		bo.saveNotice(notice, userinfo, files);
		boolean b = true;
		if (notice.getId() != null) {
			b = true;
		} else {
			b = false;
		}
		if (notice.getIsissue().equals("y")) {
			// smSendProxy = (RmiSmProxyService)
			// ctx.getBean("rmiSmProxyService");
			String sim = noticebean.getMobileIds();
			String content = "";
			String sendObjectName = "";
			if ("����".equals(noticebean.getType())) {
				content += "������"+noticebean.getMeetTime() ;
				content += "����" + noticebean.getTitle() + "����";
//				content += "����" + noticebean.getMeetTime() + "��" + noticebean.getMeetEndTime();
//				content += "��" + noticebean.getMeetAddress();
//				content += "�μ�" + noticebean.getTitle() + "���顣";
				sendObjectName = noticebean.getTitle() + "���鶨ʱ���Ͷ���";
			} else {
				content = "���棺" + noticebean.getTitle();
				sendObjectName = noticebean.getTitle() + "���涨ʱ���Ͷ���";
			}
			logger.info(sim + ":" + content);
			// smSendProxy.simpleSend(sim, content, null, null, true);
			// sendMessage(content, sim);
			if (sim != null && !"".equals(sim) && !noticebean.judgeMeetSame()) {
				String sendMethod = noticebean.getSendMethod();
				String beginDate = noticebean.getBeginDate();
				String endDate = noticebean.getEndDate();
				String intervalType = noticebean.getSendIntervalType();
				String interval = noticebean.getSendTimeSpace();
				String sendCycleRule = request.getParameter("sendCycleRule");
				String[] inputDate = request.getParameterValues("inputDate");
				SendMessageBO sendMessageBo = (SendMessageBO) ctx.getBean("sendMessageBO");
				sendMessageBo.cancelSendMessageSchedule("notice." + notice.getId());
				sendMessageBo.sendMessage("notice." + notice.getId(), content, sim, sendMethod, beginDate, endDate,
						intervalType, interval, userinfo.getUserID(), inputDate, sendObjectName, sendCycleRule);
			}
			return forwardInfoPage(mapping, request, assertResult(b, "s72302", "f72302"));
		} else {
			return forwardInfoPage(mapping, request, assertResult(b, "s72302", "f72302"));
		}
	}

	// ɾ��notice
	public ActionForward delNotice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String id = request.getParameter("id");
		WebApplicationContext ctx = getWebApplicationContext();
		NoticeBo bo = (NoticeBo) ctx.getBean("noticeBo");
		boolean b = bo.delNotice(id);
		SendMessageBO sendMessageBo = (SendMessageBO) ctx.getBean("sendMessageBO");
		sendMessageBo.cancelSendMessageSchedule("notice." + id);
		return forwardInfoPage(mapping, request, assertResult(b, "s72303", "f72303"));
	}

	// ���ر�
	public ActionForward queryNoticeForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		return mapping.findForward("queryform");
	}

	// ��ѯnotice
	public ActionForward queryNotice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		NoticeBean noticebean = (NoticeBean) form;
		HttpSession session = request.getSession();
		WebApplicationContext ctx = getWebApplicationContext();
		NoticeBo bo = (NoticeBo) ctx.getBean("noticeBo");
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		noticebean.setRegionid(userinfo.getRegionID());
		// noticebean.setBegindate("");
		List list = bo.query(noticebean);
		session.setAttribute("noticelist", list);
		return mapping.findForward("querynotice");
	}

	// ��ѯnotice
	public ActionForward queryAllIssueNotice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		NoticeBean noticebean = (NoticeBean) form;
		HttpSession session = request.getSession();
		WebApplicationContext ctx = getWebApplicationContext();
		NoticeBo bo = (NoticeBo) ctx.getBean("noticeBo");
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		noticebean.setRegionid(userinfo.getRegionID());
		// noticebean.setBegindate("");
		//request.setCharacterEncoding("UTF-8");
		noticebean.setIsissue("y");
		noticebean.setType(request.getParameter("type"));
		List list = bo.query(noticebean);
		session.setAttribute("noticelist", list);
		return mapping.findForward("queryallissuenotice");
	}

	// ��ѯnotice
	public ActionForward queryNoticeMeet(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		NoticeBean noticebean = (NoticeBean) form;
		HttpSession session = request.getSession();
		WebApplicationContext ctx = getWebApplicationContext();
		NoticeBo bo = (NoticeBo) ctx.getBean("noticeBo");
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		noticebean.setRegionid(userinfo.getRegionID());
		// noticebean.setBegindate("");
		noticebean.setType("����");
		List list = bo.query(noticebean);
		request.setAttribute("noticelist", list);
		if (list != null && !list.isEmpty()) {
			request.setAttribute("list_size", list.size());
		} else {
			request.setAttribute("list_size", 0);
		}
		return mapping.findForward("querynoticemeet");
	}

	// ������Ϣ
	public ActionForward issueNotice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String id = request.getParameter("id");
		WebApplicationContext ctx = getWebApplicationContext();
		NoticeBo bo = (NoticeBo) ctx.getBean("noticeBo");
		boolean b = bo.issueNotice(id);
		if (b) {

		}
		return null;
	}

	// ������Ϣ
	public ActionForward cancelNoticeMeet(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String id = request.getParameter("id");
		WebApplicationContext ctx = getWebApplicationContext();
		NoticeBo bo = (NoticeBo) ctx.getBean("noticeBo");
		bo.cancelNoticeMeet(id);
		SendMessageBO sendMessageBo = (SendMessageBO) ctx.getBean("sendMessageBO");
		sendMessageBo.cancelSendMessageSchedule("notice." + id);
		return forwardInfoPage(mapping, request, "SS72303");
	}

	/**
	 * ���Բ����ɹ�ʧ�ܡ�
	 * 
	 * @param b
	 *            boolean �����ɹ�true ������ false
	 * @param s_msg
	 *            �ɹ���Ϣ�������ǳɹ� ��Ϣ�ַ�����������msgid
	 * @param f_msg
	 *            ʧ����Ϣ��������ʧ�� ��Ϣ�ַ�����������msgid
	 * @return msg
	 */
	private String assertResult(boolean b, String s_msg, String f_msg) {
		if (b) {
			return s_msg;
		} else {
			return f_msg;
		}
	}

	/**
	 * �鿴������ϸ��Ϣ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward showNotice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String id = request.getParameter("id");
		boolean preview = new Boolean(request.getParameter("preview")).booleanValue();
		String model = request.getParameter("model");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		NoticeBo bo = (NoticeBo) ctx.getBean("noticeBo");
		Notice notice = bo.readNotice(id, user.getUserID(), preview);
		com.cabletech.linepatrol.commons.services.UserInfoBO userBo = (com.cabletech.linepatrol.commons.services.UserInfoBO) ctx
				.getBean("userInfoBo");
		if (notice.getAcceptUserIds() != null) {
			String[] acceptUserId = notice.getAcceptUserIds().split(",");
			String acceptUserNames = "";
			String userName;
			DynaBean userInfoBean;
			for (int i = 0; acceptUserId != null && i < acceptUserId.length; i++) {
				if (acceptUserId[i] != null && !acceptUserId[i].equals("")) {
					userInfoBean = userBo.loadUserAndContractorPersonInfo(acceptUserId[i]);
					if (userInfoBean != null) {
						userName = (String) userInfoBean.get("name");
						if (userName != null && !userName.equals("")) {
							acceptUserNames += userName;
							acceptUserNames += ",";
						}
					}
				}
			}
			// notice.setAcceptUserNames(acceptUserNames);
			request.setAttribute("accept_user_names", acceptUserNames);
		}
		if (notice.getMeetPerson() != null) {
			String[] acceptUserId = notice.getMeetPerson().split(",");
			String acceptUserNames = "";
			String userName;
			DynaBean userInfoBean;
			for (int i = 0; acceptUserId != null && i < acceptUserId.length; i++) {
				if (acceptUserId[i] != null && !acceptUserId[i].equals("")) {
					userInfoBean = userBo.loadUserAndContractorPersonInfo(acceptUserId[i]);
					if (userInfoBean != null) {
						userName = (String) userInfoBean.get("name");
						if (userName != null && !userName.equals("")) {
							acceptUserNames += userName;
							acceptUserNames += ",";
						}
					}
				}
			}
			// notice.setAcceptUserNames(acceptUserNames);
			request.setAttribute("accept_user_names", acceptUserNames);
		}
		request.setAttribute("model", model);
		request.setAttribute("notice", notice);
		return mapping.findForward("shownotice");

	}

	public ActionForward showNewNotices(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		HttpSession session = request.getSession();
		String type = request.getParameter("type");
		// type = new String( type.getBytes( "GBK" ), "UTF-8" );
		String shownum = request.getParameter("shownum");
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String rootregionid = (String) session.getAttribute("REGION_ROOT");
		WebApplicationContext ctx = getWebApplicationContext();
		NoticeBo bo = (NoticeBo) ctx.getBean("noticeBo");
		String noticeStr = bo.showNewNotice(rootregionid, userinfo.getRegionID(), shownum, type, "yes");
		this.outPrint(response, noticeStr);
		return null;
	}

	// ������еĹ�����Ϣ
	public ActionForward getAllNotice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		InitDisplayBO listnoticebo = new InitDisplayBO();
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		String rootregionid = (String) session.getAttribute("REGION_ROOT");
		String str = listnoticebo.getNoticeInfo(rootregionid, userinfo.getRegionID(), "all");
		request.setAttribute("notice", str);
		return mapping.findForward("allnoticelist");
	}

}
