package com.cabletech.datum.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.datum.bean.BaseDatum;
import com.cabletech.datum.bean.DatumCriterion;
import com.cabletech.datum.service.CreateIndex;
import com.cabletech.datum.service.DatumCriterionService;
import com.cabletech.datum.service.impl.DatumCriterionServiceImpl;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.UploadUtil;

public class DatumCriterionAction extends BaseDispatchAction{
	private static Logger log = Logger.getLogger("DatumCriterionAction");
	DatumCriterionService service = new DatumCriterionServiceImpl();
	CreateIndex createindex = new CreateIndex();
	 //�ϴ�
	public ActionForward addDatumCriterion( ActionMapping mapping, ActionForm form, HttpServletRequest request,
		        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
		BaseDatum bean = ( DatumCriterion )form;
		UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		//get ����
		
       
//      �ϴ�����
		 String datumid = uploadFile( form, new ArrayList(),user );
		 if(datumid==null || datumid.equals("")){
				return forwardInfoPage(mapping,request,"d130101f");
			}
        bean.setAdjunct(datumid);
        bean.setUserid(user.getUserID());
        bean.setRegionid(user.getRegionid());
		//д���ݿ�
        boolean result = service.saveDatumCriterion((DatumCriterion) bean);
        createindex.createIndexClient();
		if(result){
			return forwardInfoPage( mapping, request, "d130201s");
		}else{
			return forwardInfoPage(mapping,request,"d130201f");
		}
	 }
	//�޸�
	public ActionForward updateDatumCriterion( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
		BaseDatum bean = ( DatumCriterion )form;
		UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		//get ����
		
        ArrayList fileIdList = new ArrayList();
        String[] delfileid = request.getParameterValues( "delfileid" ); //Ҫɾ�����ļ�id������
        if(bean.getAdjunct()!=null && !bean.getAdjunct().equals("")){
	        StringTokenizer st = new StringTokenizer( bean.getAdjunct(), "," );
	        while( st.hasMoreTokens() ){
	            fileIdList.add( st.nextToken() );
	        }
	        if( delfileid != null ){
	            for( int i = 0; i < delfileid.length; i++ ){
	                fileIdList.remove( delfileid[i] );
	            }
	        }
        }
//      �ϴ�����
        String datumid = uploadFile( form, fileIdList,user );

        bean.setAdjunct(datumid);
        bean.setUserid(user.getUserID());
        bean.setRegionid(user.getRegionid());
        boolean result = service.updateDatumCriterion((DatumCriterion) bean);
        createindex.createIndexClient();
		if(result){
			return forwardInfoPage( mapping, request, "d130202s");
		}else{
			return forwardInfoPage(mapping,request,"d130202f");
		}
 }
	//ɾ��
	public ActionForward delDatumCriterion( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
	 String id = request.getParameter("id");
	 boolean result = service.delDatumCriterion(id);
	 createindex.createIndexClient();
	 if(result){
			return forwardInfoPage( mapping, request, "d130203s");
		}else{
			return forwardInfoPage(mapping,request,"d130203f");
		}
 }
	/**
     * �ļ��ϴ�
     * @param form ActionForm
     * @return String
     */
    public String uploadFile( ActionForm form, ArrayList fileIdList ,UserInfo user){
    	DatumCriterion formbean = ( DatumCriterion )form;
        //��ʼ�����ϴ��ļ�================================
        List attachments = formbean.getAttachments();
        String fileId;
        for( int i = 0; i < attachments.size(); i++ ){
            UploadFile uploadFile = ( UploadFile )attachments.get( i );
            FormFile file = uploadFile.getFile();
            if( file == null ){
                log.info( "file is null" );
            }
            else{
                //���ļ��洢������������·��д�����ݿ⣬���ؼ�¼ID
                fileId = SaveUploadFile.saveFile( file,user.getRegionid(),"�����淶" );
                if( fileId != null ){
                    fileIdList.add( fileId );
                }
            }
        }
        //�����ϴ��ļ�����=======================================
        //��ȡID�ַ����б�(�Զ��ŷָ����ļ�ID�ַ���)======================
        String fileIdListStr = UploadUtil.getFileIdList( fileIdList );
        //String fileIdListStr =processFileUpload(request);
        String datumid = "";
        if( fileIdListStr != null ){
            // logger.info( "FileIdListStr=" + fileIdListStr );
            datumid = fileIdListStr;
        }
        return datumid;
    }
    public ActionForward  queryDatumCriterion( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
		DatumCriterion bean = ( DatumCriterion )form;
		UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" ); 
		String rootRegionid = (String)request.getSession().getAttribute("REGION_ROOT");
		List result = service.queryDatumCriterion(bean,user,rootRegionid);
		request.getSession().setAttribute("resultlist", result);
		request.setAttribute("editurl", "DatumCriterionAction.do?method=getDatumCriterion");
		request.setAttribute("delurl", "DatumCriterionAction.do?method=delDatumCriterion");
		this.setPageReset(request);
		request.setAttribute("queryurl", "/WebApp/DatumCriterionAction.do?method=queryDatumCriterion");
    	return mapping.findForward("queryresult");
    }
    public ActionForward getDatumCriterion( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
    	String id = request.getParameter("id");
    	String stat = request.getParameter("stat");
    	DatumCriterion datumcriterion = service.getDatumCriterion(id);
    	request.setAttribute("datumcriterion", datumcriterion);
    	
    	if("look".equals(stat)){
    		return mapping.findForward("look");
    	}else{
    		return mapping.findForward("edit");
    	}
    }
}
