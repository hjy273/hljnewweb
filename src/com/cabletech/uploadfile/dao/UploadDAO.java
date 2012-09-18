package com.cabletech.uploadfile.dao;

import java.sql.*;
import java.util.*;

import org.apache.log4j.*;
import com.cabletech.commons.generatorID.*;
import com.cabletech.commons.hb.*;
import com.cabletech.uploadfile.model.*;
import com.cabletech.watchinfo.util.UploadWatchFile;

public class UploadDAO{
    private static Logger logger = Logger.getLogger( "UploadDAO" );
    /**
     *
     * @param path String  存储路径
     * @param name String  上传文件的名
     * @param type String  文件类型
     * @param size int     文件大小
     * @param desc String  描述
     * @param catlog String 文件分类
     * @return String
     */
    public static String saveFileInfo( String path, String name, String type,
        long size, String desc, String catlog ){
        GeneratorID gId = GeneratorFactory.createGenerator();
        String id = gId.getSeq( "FILEPATHINFO", 15 );
        String sql = "insert into FILEPATHINFO(FILEID,SAVEPATH,"
                     + "FILETYPE,FILESIZE,ORIGINALNAME,DESCRIPTION,CATLOG) "
                     + "values(?,?,?,?,?,?,?)";
        ConnectionManager connManager = new ConnectionManager();
        Connection conn = connManager.getCon();
        PreparedStatement stmt;
        try{
            stmt = conn.prepareStatement( sql );
            stmt.setString( 1, id );
            stmt.setString( 2, path );
            stmt.setString( 3, type );
            stmt.setLong( 4, size );
            stmt.setString( 5, name );
            stmt.setString( 6, desc );
            stmt.setString( 7, catlog );
            stmt.execute();
            stmt.close();
            conn.commit();
            connManager.closeCon( conn );
            return id;

        }
        catch( Exception ex ){
            logger.error( "saveFile", ex );
            return null;
        }
    }


    public static List getFileInfos( String fileIdList ){
        ArrayList fileList = new ArrayList();
        if( fileIdList == null || fileIdList.equals( "" ) ){
            return fileList;
        }

        String[] ids = fileIdList.split( "," );
        StringBuffer strBuf = new StringBuffer();
        for( int i = 0; i < ids.length; i++ ){
            if( i < 1 ){
                strBuf.append( "'" + ids[i] + "'" );
            }
            else{
                strBuf.append( ",'" + ids[i] + "'" );
            }
        }
        String idCollection = strBuf.toString();
        logger.info("idCollection***:" + idCollection);
        String sql = "select FILEID,SAVEPATH,FILETYPE,FILESIZE,ORIGINALNAME,"
                     + "DESCRIPTION,CATLOG from FilePathInfo "
                     + "where FILEID in (" + idCollection + ")";
        ConnectionManager connManager = new ConnectionManager();
        Connection conn = connManager.getCon();
        Statement stmt;
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( sql );
            UploadFileInfo fileInfo;
            while( rs.next() ){
                fileInfo = new UploadFileInfo();
                fileInfo.setFileId( rs.getString( "FILEID" ) );
                fileInfo.setSavePath( rs.getString( "SAVEPATH" ) );
                fileInfo.setFileType( rs.getString( "FILETYPE" ) );
                fileInfo.setFileSize( rs.getLong( "FILESIZE" ) );
                fileInfo.setOriginalName( rs.getString( "ORIGINALNAME" ) );
                fileInfo.setDescription( rs.getString( "DESCRIPTION" ) );
                fileInfo.setCatlog( rs.getString( "CATLOG" ) );
                fileList.add( fileInfo );

            }
            rs.close();
            stmt.close();
            connManager.closeCon( conn );
            if( fileList.size() < 1 ){
                logger.info( "数据库中找不到FileId为：" + fileIdList + "的纪录！！！" );
            }
            return fileList;

        }
        catch( Exception ex ){
            logger.error( "getFileInfos", ex );
            return fileList;
        }
    }


    public static UploadFileInfo getFileInfo( String fileId ){
        UploadFileInfo fileInfo = null;
        if( fileId == null || fileId.equals( "" ) ){
            return fileInfo;
        }
        String sql = "select FILEID,SAVEPATH,FILETYPE,FILESIZE,ORIGINALNAME,"
                     + "DESCRIPTION,CATLOG from FilePathInfo "
                     + "where FILEID='" + fileId + "'";
        ConnectionManager connManager = new ConnectionManager();
        Connection conn = connManager.getCon();
        Statement stmt;
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( sql );
            if( rs.next() ){
                fileInfo = new UploadFileInfo();
                fileInfo.setFileId( rs.getString( "FILEID" ) );
                fileInfo.setSavePath( rs.getString( "SAVEPATH" ) );
                fileInfo.setFileType( rs.getString( "FILETYPE" ) );
                fileInfo.setFileSize( rs.getLong( "FILESIZE" ) );
                fileInfo.setOriginalName( rs.getString( "ORIGINALNAME" ) );
                fileInfo.setDescription( rs.getString( "DESCRIPTION" ) );
                fileInfo.setCatlog( rs.getString( "CATLOG" ) );
            }
            else{
                logger.info( "找不到FileId=" + fileId + "的纪录！！！" );
            }
            rs.close();
            stmt.close();
            connManager.closeCon( conn );
            return fileInfo;

        }
        catch( Exception ex ){
            logger.error( "getFileInfo", ex );
            return null;
        }
    }


	public static void delFileInfo(String fileid) {
		String sql = "delete FilePathInfo where FILEID='" + fileid + "'";
		UpdateUtil del;
		try {
			del = new UpdateUtil();
			del.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//专门针对盯防图片处理
    public static List getWatchFileInfos( String fileIdList ){
    	logger.info("fielIdList*****:" + fileIdList);
        ArrayList fileList = new ArrayList();
        if( fileIdList == null || fileIdList.equals( "" ) ){
            return fileList;
        }

        String[] ids = fileIdList.split( "," );
        StringBuffer strBuf = new StringBuffer();
        for( int i = 0; i < ids.length; i++ ){
            if( i < 1 ){
                strBuf.append( "'" + ids[i] + "'" );
            }
            else{
                strBuf.append( ",'" + ids[i] + "'" );
            }
        }
        String idCollection = strBuf.toString();
        logger.info("idCollection***:" + idCollection);
        String sql = "select ID,URL,FLAG from watchinfo_attach "
                     + "where id in (" + idCollection + ")";
        ConnectionManager connManager = new ConnectionManager();
        Connection conn = connManager.getCon();
        Statement stmt;
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( sql );
            UploadWatchFile watchFileInfo;
            while( rs.next() ){
            	watchFileInfo = new UploadWatchFile();
            	watchFileInfo.setFileId(rs.getString( "ID" ) );
            	watchFileInfo.setRelativeURL( rs.getString( "URL" ) );
            	watchFileInfo.setFlag( rs.getInt("FLAG") );
                fileList.add( watchFileInfo );

            }
            rs.close();
            stmt.close();
            connManager.closeCon( conn );
            if( fileList.size() < 1 ){
                logger.info( "数据库中找不到FileId为：" + fileIdList + "的纪录！！！" );
            }
            return fileList;

        }
        catch( Exception ex ){
            logger.error( "getFileInfos", ex );
            return fileList;
        }
    }
    
//  专门针对盯防图片处理
    public static UploadWatchFile getWatchFileInfo( String fileId ){
    	UploadWatchFile fileInfo = null;
        if( fileId == null || fileId.equals( "" ) ){
            return fileInfo;
        }
        
        String sql = "select ID,URL,FLAG from watchinfo_attach "
                     + "where ID='" + fileId + "'";
        //System.out.println(sql);
        ConnectionManager connManager = new ConnectionManager();
        Connection conn = connManager.getCon();
        Statement stmt;
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( sql );
            if( rs.next() ){
                fileInfo = new UploadWatchFile();
                fileInfo.setFileId( rs.getString( "ID" ) );
                fileInfo.setRelativeURL( rs.getString( "URL" ) );
                fileInfo.setFlag( rs.getInt( "FLAG" ) );
            }
            else{
                logger.info( "找不到FileId=" + fileId + "的纪录！！！" );
            }
            rs.close();
            stmt.close();
            connManager.closeCon( conn );
            return fileInfo;

        }
        catch( Exception ex ){
            logger.error( "getFileInfo", ex );
            return null;
        }
    }
}
