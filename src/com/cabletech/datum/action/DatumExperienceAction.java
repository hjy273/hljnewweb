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
import com.cabletech.datum.bean.DatumExperience;
import com.cabletech.datum.bean.DatumSystem;
import com.cabletech.datum.service.CreateIndex;
import com.cabletech.datum.service.DatumExperienceService;
import com.cabletech.datum.service.impl.DatumExperienceServiceImpl;
import com.cabletech.uploadfile.DelUploadFile;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.UploadUtil;

public class DatumExperienceAction extends BaseDispatchAction{ 
	private static Logger log = Logger.getLogger("DatumCriterionAction");
	DatumExperienceService service = new DatumExperienceServiceImpl();
	CreateIndex createindex = new CreateIndex();
	 //上传
	public ActionForward addDatumExperience( ActionMapping mapping, ActionForm form, HttpServletRequest request,
		        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
		BaseDatum bean = ( DatumExperience )form;
		UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		//get 附件
		
//      上传附件
		 String datumid = uploadFile( form, new ArrayList(),user );
		 if(datumid==null || datumid.equals("")){
				return forwardInfoPage(mapping,request,"d130101f");
		}
        bean.setAdjunct(datumid);
        bean.setUserid(user.getUserID());
        bean.setRegionid(user.getRegionid());
		//写数据库
        
        boolean result = service.saveDatumExperience((DatumExperience) bean);
        createindex.createIndexClient();
		if(result){
			return forwardInfoPage( mapping, request, "d130301s");
		}else{
			return forwardInfoPage(mapping,request,"d130301f");
		}
	 }
	//修改
	public ActionForward updateDatumExperience( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
		BaseDatum bean = ( DatumExperience )form;
		UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		//get 附件
		
        ArrayList fileIdList = new ArrayList();
        String[] delfileid = request.getParameterValues( "delfileid" ); //要删除的文件id号数组
        if(bean.getAdjunct()!=null && !bean.getAdjunct().equals("")){
	        StringTokenizer st = new StringTokenizer( bean.getAdjunct(), "," );
	        while( st.hasMoreTokens() ){
	            fileIdList.add( st.nextToken() );
	        }
	        if( delfileid != null ){
	            for( int i = 0; i < delfileid.length; i++ ){
	                fileIdList.remove( delfileid[i] );
	                DelUploadFile.delFile(delfileid[i]);//删除附件
	            }
	        }
	    }
        
//      上传附件
        String datumid = uploadFile( form, fileIdList ,user);
        
        bean.setAdjunct(datumid);
        bean.setUserid(user.getUserID());
        bean.setRegionid(user.getRegionid());
        boolean result = service.updateDatumExperience((DatumExperience) bean);
        createindex.createIndexClient();
		if(result){
			return forwardInfoPage( mapping, request, "d130302s");
		}else{
			return forwardInfoPage(mapping,request,"d130302f");
		}
 }
	//删除
	public ActionForward delDatumExperience( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
	 String id = request.getParameter("id");
	 boolean result = service.delDatumExperience(id);
	 createindex.createIndexClient();
	 if(result){
			return forwardInfoPage( mapping, request, "d130303s");
		}else{
			return forwardInfoPage(mapping,request,"d130303f");
		}
 }
	/**
     * 文件上传
     * @param form ActionForm
     * @return String
     */
    public String uploadFile( ActionForm form, ArrayList fileIdList ,UserInfo user){
    	BaseDatum formbean = ( BaseDatum )form;
        //开始处理上传文件================================
        List attachments = formbean.getAttachments();
        String fileId;
        for( int i = 0; i < attachments.size(); i++ ){
            UploadFile uploadFile = ( UploadFile )attachments.get( i );
            FormFile file = uploadFile.getFile();
            if( file == null ){
                log.info( "file is null" );
            }
            else{
                //将文件存储到服务器并将路径写入数据库，返回记录ID
                fileId = SaveUploadFile.saveFile( file,user.getRegionid(),"代维经验" );
                if( fileId != null ){
                    fileIdList.add( fileId );
                }
            }
        }
        //处理上传文件结束=======================================
        //获取ID字符串列表(以逗号分隔的文件ID字符串)======================
        String fileIdListStr = UploadUtil.getFileIdList( fileIdList );
        //String fileIdListStr =processFileUpload(request);
        String datumid = "";
        if( fileIdListStr != null ){
            // logger.info( "FileIdListStr=" + fileIdListStr );
            datumid = fileIdListStr;
        }
        return datumid;
    }
    public ActionForward  queryDatumExperience( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
		DatumExperience bean = ( DatumExperience )form;
		UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" ); 
		String rootRegionid = (String)request.getSession().getAttribute("REGION_ROOT");
		List result = service.queryDatumExperience(bean,user,rootRegionid);
		request.getSession().setAttribute("resultlist", result);
    	request.setAttribute("editurl", "DatumExperienceAction.do?method=getDatumExperience");
    	request.setAttribute("delurl", "DatumExperienceAction.do?method=delDatumExperience");
    	request.setAttribute("queryurl", "/WebApp/DatumExperienceAction.do?method=queryDatumExperience");
    	this.setPageReset(request);
    	return mapping.findForward("queryresult");
    }
    public ActionForward getDatumExperience( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	        HttpServletResponse response ) throws IllegalAccessException, InvocationTargetException{
    	String id = request.getParameter("id");
    	String stat = request.getParameter("stat");
    	DatumExperience datumexperience = service.getDatumExperience(id);
    	request.setAttribute("datumexperience", datumexperience);

    	if("look".equals(stat)){
    		return mapping.findForward("look");
    	}else{
    		return mapping.findForward("edit");
    	}
    }
}
