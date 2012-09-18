/***
 *
 * MtUsedAction.java
 * @auther <a href="kww@mail.cabletech.com.cn">kww</a>
 * 2009-10-10
 **/

package com.cabletech.linepatrol.material.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.material.beans.MaterialUsedInfoBean;
import com.cabletech.linepatrol.material.service.MaterialUsedInfoBo;

/**
 * 材料盘点信息查询
 * 
 * @author cable
 * 
 */
public class MaterialUsedInfoAction extends BaseInfoBaseDispatchAction {
    private static Logger logger = Logger.getLogger(MaterialUsedInfoAction.class.getName());

    private MaterialUsedInfoBo getMaterialUsedInfoService(){
		WebApplicationContext ctx = getWebApplicationContext();
		return (MaterialUsedInfoBo)ctx.getBean("materialUsedInfoBo");
	}

    /**
     * 根据代维以及材料id查询材料信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward getMarterialInfos(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        String deptype = user.getDeptype();
        String deptid = user.getDeptID();
        String contraid = request.getParameter("contractor_id");
        String mtid = request.getParameter("material_id");
        String seqId = request.getParameter("seq_id");
        if (deptype.equals("2")) {// 代维
            contraid = deptid;
        }
        List list = getMaterialUsedInfoService().getMarterialInfos(contraid, mtid);
        request.setAttribute("marterinfos", list);
        request.setAttribute("seq_id", seqId);
        String act = request.getParameter("act");
        if(act!=null && act.equals("view")){
        	return mapping.findForward("materialinfoview");
        }
        return mapping.findForward("listmaterials");
    }

    /**
     * 根据材料id与材料存放地址id查询材料基本信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward getMarterialInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String mtid = request.getParameter("mtid");
        String addrid = request.getParameter("addrid");
        String stockstate = request.getParameter("state");// 库存状态1 表示新增 2表示利旧
        String num = request.getParameter("num");
        BasicDynaBean bean = getMaterialUsedInfoService().getMarterialInfo(mtid, addrid);
        request.setAttribute("bean", bean);
        request.setAttribute("state", stockstate);
        if (num == null || num == "") {
            num = "0";
        }
        BigDecimal big = new BigDecimal(num);
        if (stockstate.equals("1")) {
            bean.set("newstock", big);
        }
        if (stockstate.equals("2")) {
            bean.set("oldstock", big);
        }
        return mapping.findForward("materialinfo");
    }

    /**
     * 修改材料库存信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward updateMaterialInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        MaterialUsedInfoBean bean = (MaterialUsedInfoBean) form;
        String mtid = bean.getMtid();
        String addrid[] = bean.getAddrid();
        String seqId = request.getParameter("seq_id");
        float[] newstock = bean.getNewstock();
        float[] oldstock = bean.getOldstock();
        float totalnum = 0;
        for (int i = 0; i < addrid.length; i++) {
            totalnum += newstock[i] + oldstock[i];
        }
        List list;
        if (request.getSession().getAttribute("STOCK_LIST") != null) {
            list = (List) request.getSession().getAttribute("STOCK_LIST");
        } else {
            list = new ArrayList();
        }
        // list.add(totalnum+"");
        list.add(bean);
        request.getSession().setAttribute("STOCK_LIST", list);
        PrintWriter out = response.getWriter();
        out.print("<script type='text/javascript'>");
        out.print("window.opener.document.getElementById('real_stock_" + seqId + "').innerText="
                + totalnum + ";");
        out.print("window.opener.document.getElementById('realStock_" + seqId + "').value="
                + totalnum + ";");
        out.print("window.close();");
        out.print("</script>");
        out.close();
        log(request,"修改材料库信息","材料管理");
        return null;
    }

}
