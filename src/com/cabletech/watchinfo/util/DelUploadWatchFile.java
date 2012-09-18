package com.cabletech.watchinfo.util;

import java.io.File;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import com.cabletech.commons.config.GisConInfo;
import com.cabletech.uploadfile.dao.UploadDAO;

public class DelUploadWatchFile {
    private static Logger logger = Logger.getLogger( "DelUploadWatchFile" );
    public static boolean delFile( String fileid ){
        String uploadRoot = GisConInfo.newInstance().getUploadRoot();
        if(fileid != null && !fileid.equals("")){
        	UploadWatchFile uploadfile = UploadDAO.getWatchFileInfo( fileid );
	        if(uploadfile == null){
	        	return false;
	        }
	        logger.info("fileid "+fileid +"  filepath "+uploadfile.getRelativeURL());
	        logger.info("*****:" + uploadRoot + "\\" + uploadfile.getRelativeURL());
	        File file = new File( uploadRoot + "\\" + uploadfile.getRelativeURL() );
	        //UploadDAO.delFileInfo(fileid);
	        if( file.exists() ){
	            file.delete();
	            logger.info( "delete file success,the file : " + uploadfile.getRelativeURL() );
	            return true;
	        }
	        else{
	            logger.info( "File Not Found ! delete file faile ,the file : " + uploadfile.getRelativeURL() );
	            return false;
	        }
	        
        }else{
        	return false;
        }
    }
    public static int delFiles(String [] fileid){
    	int count = 0;
    	for(int i=0;i<fileid.length;i++){
    		boolean b = delFile(fileid[i]);
    		if(b){
    			count ++;
    		}
    	}
    	return count;
    }
    public static int delFiles(String fileids){
    	int count = 0;
    	if(fileids!= null && fileids.equals("")){
	    	StringTokenizer st = new StringTokenizer( fileids, "," );
	        while( st.hasMoreTokens() ){
	        	delFile( st.nextToken() );
	        	count++;
	        }
    	}
    	return count;
    }
}
