package com.cabletech.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.cabletech.commons.base.BaseDispatchAction;

public class UpFileAction extends BaseDispatchAction {

	public ActionForward upFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//       //System.out.println( "here22222 ............" );
		UpFileForm hform = (UpFileForm) form;
		//      //System.out.println( "here ............" );

		FormFile f = hform.getFile();
		//
		//      //System.out.println( "filename:" + f.getFileName() );

		//saveFile(hform);
		return mapping.findForward("success");

	}

	//    public boolean saveFile(UpFileForm hform){
	//    	String dir = servlet.getServletContext().getRealPath("/upload");
	//        FormFile file = hform.getFile();
	//        String filename = file.getFileName();
	//        String filesize = Integer.toString( file.getFileSize() ) + "bytes";
	//        if( file == null )
	//                return false;
	//        File temfile = new File(dir + "/temp.xls"  );
	//        if(temfile.exists()){
	//             temfile.delete();
	//        }
	//
	//        try{
	//            InputStream streamIn = file.getInputStream();
	//            OutputStream streamOut = new FileOutputStream(dir + "/temp.xls" );
	//
	//            int bytesRead = 0;
	//            byte[] buffer = new byte[Integer.parseInt(filesize)+1024];
	//            while( (bytesRead=streamIn.read(buffer,0,8192)) !=-1){
	//                streamOut.write(buffer,0,bytesRead);
	//            }
	//            streamOut.close();
	//            streamIn.close();
	//            return true;
	//        }catch(Exception e){
	//            //System.out.println( "upload error:" + e.getMessage() );
	//            return false;
	//        }
	//
	//    }

}
