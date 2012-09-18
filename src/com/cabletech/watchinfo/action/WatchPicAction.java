package com.cabletech.watchinfo.action;

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
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.commons.web.ClientException;
import com.cabletech.datum.bean.DatumCriterion;
import com.cabletech.power.CheckPower;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.UploadUtil;
import com.cabletech.watchinfo.beans.WatchPicConBean;
import com.cabletech.watchinfo.services.WatchBO;
import com.cabletech.watchinfo.services.WatchPicBO;
import com.cabletech.watchinfo.util.DelUploadWatchFile;
import com.cabletech.watchinfo.util.UploadWatchFile;

public class WatchPicAction extends BaseDispatchAction{
	private static Logger logger = Logger.getLogger("WatchPicAction");
	private WatchPicBO bo = new WatchPicBO();
	
    public ActionForward queryUploadPic( ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ){
    	String watchid = request.getParameter( "watchid" );
    	request.getSession().setAttribute( "watchidforuploadpic", watchid );
    	return mapping.findForward( "queryUploadWatchPic" );
    }
    
    //��Ա�������ͼƬ
    public ActionForward addWatchPic( ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ){
    	UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    	WatchPicConBean bean = (WatchPicConBean)form;
//      �ϴ�����
		String[] relativePathFile = uploadFile( form, new ArrayList(),user);
		if(relativePathFile==null || relativePathFile.equals("")){
			return forwardInfoPage(mapping,request,"save40101attachfail");
		}
		//String placeid= (String)request.getSession().getAttribute("watchidforuploadpic");
		String placeid = (String)request.getSession().getAttribute("watchidforlinkpic");
		//logger.info("placeid:" + placeid);
		//д���ݿ�
        boolean result = bo.saveWatchInfoAttach(bean,placeid,relativePathFile,null);
//        createindex.createIndexClient();
		if(result){
			return forwardInfoPage( mapping, request, "save40101attachsuccess");
		}else{
			return forwardInfoPage(mapping,request,"save40101attachfail");
		}
    }

    //��Ա������Ӳ�ɾ��ͼƬ
    public ActionForward addDelWatchPic( ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ){
    	UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    	WatchPicConBean bean = (WatchPicConBean)form;
    	String[] delfileid = request.getParameterValues( "delfileid" ); //Ҫɾ�����ļ�id������
    	//String[] 
    	//�����ļ��ϴ��ļ�
		String[] relativePathFile = uploadFile( form, new ArrayList(),user);
		if (relativePathFile == null && delfileid == null){
			return forwardInfoPage(mapping,request,"save40101attachnochoice");
		}
		//ɾ���Ѿ��ϴ����ļ�
		DelUploadWatchFile file = new DelUploadWatchFile();
		if (delfileid != null){
			 int delCount = file.delFiles(delfileid);
			if(delCount>0){
				logger.info("you have deleted " + delCount + "records");
			}
		}
		String placeid = (String)request.getSession().getAttribute("watchidforviewlinkpic");
		//д���ݿ�
        boolean result = bo.saveWatchInfoAttach(bean,placeid,relativePathFile,delfileid);
		if(result){
			return forwardInfoPage( mapping, request, "save40101attachsuccess");
		}else{
			return forwardInfoPage(mapping,request,"save40101attachfail");
		}
    }
    
	/**
     * �ļ��ϴ�
     * @param form ActionForm
     * @return String
     */
    public String[] uploadFile( ActionForm form, ArrayList fileIdList ,UserInfo user){
    	WatchPicConBean formbean = ( WatchPicConBean )form;
        //��ʼ�����ϴ��ļ�================================
        List attachments = formbean.getAttachments();
        //logger.info("remarks:" + formbean.getRemarks().toString());
        int size = attachments.size();
        String relativePathFile[] = new String[size] ;
        for( int i = 0; i < size; i++ ){
            UploadFile uploadFile = ( UploadFile )attachments.get( i );
            FormFile file = uploadFile.getFile();
            String remark = uploadFile.getRemark();
            if( file == null ){
                log.info( "file is null" );
                return null;
            }
            else{
                //���ļ��洢������������·��д�����ݿ⣬���ؼ�¼ID
            	relativePathFile[i] = SaveUploadFile.saveFile( file,"watchinfo_attach");
                if( relativePathFile[i] != null ){
                	logger.info("relativePathFile*****************:" + relativePathFile[i]);
                    fileIdList.add( relativePathFile );
                }
            }
        }
        //�����ϴ��ļ�����=======================================
        return relativePathFile;
    }
    
    /**
     * ��ʾ����ͼƬ���Խ���ͼƬ��ѡ�������Ĺ�ϵ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward showLinkUploadPic( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
    	 UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
         if( "11".equals(user.getType()) || "21".equals(user.getType())){  //�����ʡ�ƶ�����ʡ��ά
            return mapping.findForward( "powererror" );
        }
        WatchPicConBean bean = (WatchPicConBean)form;
        String watchID = (String)request.getSession().getAttribute("watchidforviewlinkpic");
        List picList = bo.watchPicList(bean,watchID);
    	 
    	request.getSession().setAttribute( "picListForWatch", picList );
   	    return mapping.findForward( "showPicsForWatchID" );
    }
 
    public ActionForward queryLinkPic( ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    	    UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    	    if( "11".equals(user.getType()) || "21".equals(user.getType())){  //�����ʡ�ƶ�����ʡ��ά
                return mapping.findForward( "powererror" );
            }
            String watchID = request.getParameter("watchid");
            request.getSession().setAttribute( "watchidforlinkpic", watchID );
            //WatchPicConBean bean = new WatchPicConBean();
    		//String attachFileID = bo.getAttachFilesID(watchID);
    		//bean.setFileIDs(attachFileID);
    		//logger.info("attachFileID:" + attachFileID);
    		//request.setAttribute("watchconbeanforattach", bean);
    		//request.setAttribute("thewatchattach", attachFileID);
       	    return mapping.findForward( "queryLinkPic" );
            
   }
    
    //��ʾ��������Ӧ�ĸ���
    public ActionForward viewLinkPic( ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    	    UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    	    if( "11".equals(user.getType()) || "21".equals(user.getType())){  //�����ʡ�ƶ�����ʡ��ά
                return mapping.findForward( "powererror" );
            }
            String watchID = request.getParameter("watchid");
            request.getSession().setAttribute( "watchidforviewlinkpic", watchID );
            WatchPicConBean bean = new WatchPicConBean();
    		String attachFileID = bo.getAttachFilesID(watchID);
//    		if ("".equals(attachFileID) || attachFileID ==null){
//    			return forwardInfoPage( mapping, request, "save40205noviewattach");
//    		}
    		bean.setFileIDs(attachFileID);
    		//logger.info("attachFileID:" + attachFileID);
    		request.setAttribute("viewwatchattach", attachFileID);
//    		WatchPicConBean newbean = new WatchPicConBean();
//    		newbean.setFileIDs(attachFileID+"*");
//    		request.setAttribute("newbean", newbean);
       	    return mapping.findForward( "viewLinkPic" );
            
   }
    
    //��Բ��Ŷ���ͼƬ
    public ActionForward doLinkUploadPic( ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
            String picNames = request.getParameter("picnamestring").trim();
            if ("".equals(picNames) || picNames == null){
            	return forwardInfoPage(mapping,request,"save40205noattach");
            }
            String watchID = (String)request.getSession().getAttribute("watchidforviewlinkpic");
            if (picNames.endsWith("|")){
            	picNames = picNames.substring(0, picNames.length()-1);
            }
            StringTokenizer token=new StringTokenizer(picNames,"|");
            String[] picNamesList=new String[token.countTokens()];
            int i=0;
            while(token.hasMoreTokens()){
            	picNamesList[i]=token.nextToken(); 
                i++; 
            }
//            for (int j=0;j<picNamesList.length;j++){
//        		String attachName = picNamesList[j].substring(0, picNamesList[j].indexOf("*"));
//        		String simID_Remark = picNamesList[j].substring(picNamesList[j].indexOf("*")+1);
//        		String simID = simID_Remark.substring(0, simID_Remark.indexOf("*"));
//        		String remark = simID_Remark.substring(simID_Remark.indexOf("*")+1);
//        		logger.info(attachName + "," + simID + "," + remark);
//            }
//            for ( int j=0;j<picNamesList.length;j++){
//            	logger.info(picNamesList[j]);
//            }
    		//д���ݿ�
            boolean result = bo.saveWatchInfoAttachNew(watchID,picNamesList);
//            createindex.createIndexClient();
    		if(result){
    			return forwardInfoPage( mapping, request, "save40205attachsuccess");
    		}else{
    			return forwardInfoPage(mapping,request,"save40205attachfail");
    		}
        }
    
    
    //��ʾ��������Ӧ�ĸ������Ľ��棩
    public ActionForward viewLinkPicEx( ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ) throws
            ClientException, Exception{
    	    UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    	    
    	    
    	    if( "11".equals(user.getType()) || "21".equals(user.getType())){  //�����ʡ�ƶ�����ʡ��ά
                return mapping.findForward( "powererror" );
            }
    	    System.out.println(request.getParameterMap());
            String watchID = request.getParameter("watchid");
            
            if(watchID==null||watchID.length()<1){
            	Object objWatchId =request.getAttribute("watchid");
            	if(objWatchId!=null){
            		watchID=(String)objWatchId; 
            		System.out.println("WatchId in request=["+watchID+"]");
            	}else{
            		objWatchId=request.getSession().getAttribute("watchid");
            		if(objWatchId!=null){
            			watchID=(String)objWatchId;
            			System.out.println("WatchId in session=["+watchID+"]");
            		}            		
            	}
            }else{
            	System.out.println("watchId="+watchID);
            }
            
            //request.getSession().setAttribute( "watchidforviewlinkpic", watchID );
            List attachList=bo.getAttachListByID(watchID);
            
            request.setAttribute("attachList", attachList);
            
            request.setAttribute("watchid",watchID);
            request.getSession().setAttribute("watchid",watchID);
       	    return mapping.findForward( "viewLinkPicEx" );
            
   }     

    
}
