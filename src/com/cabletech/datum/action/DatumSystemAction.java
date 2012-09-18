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
import com.cabletech.datum.bean.DatumSystem;
import com.cabletech.datum.service.CreateIndex;
import com.cabletech.datum.service.DatumSystemService;
import com.cabletech.datum.service.impl.DatumSystemServiceImpl;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.UploadUtil;

public class DatumSystemAction extends BaseDispatchAction{
	private static Logger log = Logger.getLogger("DatumSystemAction");
	DatumSystemService service = new DatumSystemServiceImpl();
	CreateIndex createindex = new CreateIndex();
	 //�ϴ�
	public ActionForward addDatumSystem( ActionMapping mapping, ActionForm form, HttpServletRequest request,
		        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
		DatumSystem bean = ( DatumSystem )form;
		UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
//      �ϴ�����
		 String datumid = uploadFile( form, new ArrayList(),user );
		if(datumid==null || datumid.equals("")){
			return forwardInfoPage(mapping,request,"d130101f");
		}
        bean.setAdjunct(datumid);
        bean.setUserid(user.getUserID());
        bean.setRegionid(user.getRegionid());
		//д���ݿ�
        
        boolean result = service.saveDatumSystem(bean);
        createindex.createIndexClient();
		if(result){
			return forwardInfoPage( mapping, request, "d130101s");
		}else{
			return forwardInfoPage(mapping,request,"d130101f");
		}
	 }
	//�޸�
	public ActionForward updateDatumSystem( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
		DatumSystem bean = ( DatumSystem )form;
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
        boolean result = service.updateDatumSystem(bean);
        createindex.createIndexClient();
		if(result){
			return forwardInfoPage( mapping, request, "d130102s");
		}else{
			return forwardInfoPage(mapping,request,"d130102f");
		}
 }
	//ɾ��
	public ActionForward delDatumSystem( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
	 String id = request.getParameter("id");
	 boolean result = service.delDatumSystem(id);
	 createindex.createIndexClient();
	 if(result){
			return forwardInfoPage( mapping, request, "d130103s");
		}else{
			return forwardInfoPage(mapping,request,"d130103f");
		}
 }
	/**
     * �ļ��ϴ�
     * @param form ActionForm
     * @return String
     */
    public String uploadFile( ActionForm form, ArrayList fileIdList ,UserInfo user){
    	DatumSystem formbean = ( DatumSystem )form;
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
                fileId = SaveUploadFile.saveFile( file,user.getRegionid(),"��ά�ƶ�" );
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
    public ActionForward  queryDatumSystem( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
		DatumSystem bean = ( DatumSystem )form;
		UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" ); 
		String rootRegionid = (String)request.getSession().getAttribute("REGION_ROOT");
		List result = service.queryDatumSystem(bean,user,rootRegionid);
		request.getSession().setAttribute("resultlist", result);
		request.setAttribute("editurl", "DatumSystemAction.do?method=getDatumSystem");
		request.setAttribute("delurl", "DatumSystemAction.do?method=delDatumSystem");
		request.setAttribute("queryurl", "/WebApp/DatumSystemAction.do?method=queryDatumSystem");
		
		this.setPageReset(request);
		
    	return mapping.findForward("queryresult");
    }
    public ActionForward getDatumSystem( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
    	String id = request.getParameter("id");
    	String stat = request.getParameter("stat");
    	DatumSystem datumsystem = service.getDatumSystem(id);
    	request.setAttribute("datumsystem",datumsystem);
    	
    	if("look".equals(stat)){
    		return mapping.findForward("look");
    	}else{
    		return mapping.findForward("edit");
    	}
    }

}
