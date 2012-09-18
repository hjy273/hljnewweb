package com.cabletech.commons.upload.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.dao.AnnexAddOneDao;
import com.cabletech.commons.upload.dao.UploadFileDao;
import com.cabletech.commons.upload.module.AnnexAddOne;
import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.uploadfile.UploadFile;

@Service
public class UploadFileService {
	
	@Resource(name="uploadFileDao")
	private UploadFileDao uploadDao;
	@Resource(name="annexAddOneDao")
	private AnnexAddOneDao annexAddOneDao;
	private Logger logger = Logger.getLogger(UploadFileService.class);
	 
	
	public static String UPLOADROOT = GisConInfo.newInstance().getUploadRoot();
	//RECYCLE为回收站文件路径
	public static String RECYCLE = UPLOADROOT+File.separator+"recycle";
	/**
	 * 上传多个附件，并记录附件的相关信息到ANNEX_ADD_ONE，FILEPATHINFO表中。
	 * @param files  上传附件
	 * @param delIds 删除附件id
	 * @param module 模块常量 
	 * @param regionName 区域名称
	 * @param entityId 附件关联的实体id
	 * @param entityType  附件关联的表名
	 * @param uploader 附件上传人
	 * @throws ServiceException 抛出的运行时异常，
	 * @see ModuleCatalog
	 * @deprecated
	 */
	public void saveFiles(List<UploadFile> files,List<String> delIds,String module,String regionName,String entityId,String entityType,String uploader) throws ServiceException{
		UploadFileInfo uploadFile = null;
		AnnexAddOne addOne = null;
        //目录结构设置  区域+业务名称+年份+月份+文件（文件格式 年月日+xx公司xx文件+文档编号（fileseq）.doc）
        String fileSptr = File.separator;
       
        String relativePath = regionName+fileSptr+ModuleCatalog.get(module)+fileSptr+DateUtil.getNowDateString("yyyy年")+fileSptr+DateUtil.getNowDateString("MM月");
        String absolutePath = UPLOADROOT+File.separator+relativePath;
        createCatalog(absolutePath);
        //单个文件上传
        for(UploadFile file:files){
        	 FormFile formFile = file.getFile();
        	 if(formFile == null){
        		 break;
        	 }
        	 int fileSize = formFile.getFileSize();
             if( fileSize < 1 ){
                 break;
             }
        	 String fileName = formFile.getFileName();
        	 //对文件名称进行分割，从名称中分割出名称与文件类型 例如：xx年**公司接地电阻测试工作计划.doc
        	 //分割为 xx年**公司接地电阻测试工作计划 与 .doc
        	 Integer separatorsIndex = fileName.lastIndexOf(".");
        	 String name = fileName.substring(0, separatorsIndex);
        	 String fileType = fileName.substring(separatorsIndex);
        	 String saveAsName = DateUtil.getNowDateString("yyyy年MM月dd日HHmmssSSS")+"_"+name;//在文件名称前增加时间戳+文件名称+随机码；防止文件重复
        	 
        	 uploadFile = new UploadFileInfo();
        	 uploadFile.setCatlog("");
        	 uploadFile.setDescription(module);
        	 uploadFile.setFileType(fileType);
        	 uploadFile.setOriginalName(name+fileType);
        	 uploadFile.setSavePath(relativePath+File.separator+saveAsName);//相对路径
        	 
        	 transfersFile(formFile,uploadFile);
        	 uploadDao.save(uploadFile);
        	 addOne = new AnnexAddOne();
        	 addOne.setFileId(uploadFile.getFileId());
        	 addOne.setEntityId(entityId);//参数传入
        	 addOne.setEntityType(entityType);
        	 addOne.setModule(module);
        	 addOne.setModuleCatalog(ModuleCatalog.get(module));
        	 addOne.setUploader(uploader);
        	 addOne.setTimeStamp(new Date());
        	 addOne.setUploadDate(new Date());
        	 annexAddOneDao.save(addOne);
        	 
        }
        File file =  null;
        //删除附件;这里的addOne，uploadFile 利用已经创建的变量索引，
        for(String id:delIds){
        	addOne = annexAddOneDao.getAnnexAddOneByFileId(id);
        	uploadFile = uploadDao.get(addOne.getFileId());
        	String absolutePathFile = UPLOADROOT+File.separator+uploadFile.getSavePath();
        	file = new File( absolutePathFile );
        	System.out.println("absolutePathFile:"+absolutePathFile);
        	file.delete();
        	uploadDao.delete(uploadFile);
        	annexAddOneDao.delete(addOne);
        }
	}

	private void transfersFile(FormFile formFile,UploadFileInfo uploadFile) {
		
		Integer fileSize = formFile.getFileSize();
        uploadFile.setFileSize(fileSize);
        String absolutePathFile = UPLOADROOT+File.separator+uploadFile.getSavePath()+uploadFile.getFileType();
        logger.info("文件绝对路径："+absolutePathFile);
        try{
        	//获取输入流
        	InputStream stream = formFile.getInputStream();
	        //将上传文件写指定路径
	        File file = new File( absolutePathFile );
	        if(!file.exists()){
	        	
	    		boolean success = file.createNewFile();
	    		OutputStream bos = null;
	            if( success ){
	            	
	                bos = new FileOutputStream( absolutePathFile );
	                int bytesRead = 0;
	                byte[] buffer = new byte[8192];
	                while( ( bytesRead = stream.read( buffer, 0, 8192 ) ) != -1 ){
	                    bos.write( buffer, 0, bytesRead );
	                }
	                bos.close();
	                logger.info( "The file has been written to \"" + absolutePathFile + "\"" );
	                //close the stream
	                stream.close();
	             
	            }else{
	            	logger.info( "不能够创建文件！" );
	        		throw new  ServiceException("该目录不能够创建文件！");
	            }
	            
	        }else{
	        	uploadFile.setSavePath(uploadFile.getSavePath()+"as");
	        	absolutePathFile = UPLOADROOT+uploadFile.getSavePath()+uploadFile.getFileType();
	        	file = new File(absolutePathFile);
	        	if(!file.exists()){
	        		boolean success = file.createNewFile();
	        		OutputStream bos = null;
	        		if( success ){
		                bos = new FileOutputStream( absolutePathFile );
		                int bytesRead = 0;
		                byte[] buffer = new byte[8192];
		                while( ( bytesRead = stream.read( buffer, 0, 8192 ) ) != -1 ){
		                    bos.write( buffer, 0, bytesRead );
		                }
		                bos.close();
		               
		                //close the stream
		                stream.close();
		             
		            }else{
		            	logger.info( "不能够创建文件！" );
		        		throw new  ServiceException("该目录不能够创建文件！");
		            }
	        	}else{
	        		logger.info( "目录中存在相同文件，不能够创建！" );
	        		throw new  ServiceException("目录中存在相同文件，不能够创建！");
	        	}
	        }
	        uploadFile.setSavePath(uploadFile.getSavePath()+uploadFile.getFileType());
	        //将文件路径及相关属性写入数据库表
            
        }catch(Exception ex){
        	throw new  ServiceException(ex);
        }
	}
	/***
	 * 上传多个附件，并记录附件的相关信息到ANNEX_ADD_ONE，FILEPATHINFO表中。
	 * <l>uploadFile.saveFiles(files, ModuleCatalog.HIDDTROUBLEWATCH, userInfo.getRegionName(), entityId, "LP_HIDDANGER_REPORT", userInfo.getUserID(),"0");</l>
	 * @param files 上传文件列表，存储在session的FILES变量中。
	 * @param module 模块常量 
	 * @param regionName 区域名称
	 * @param entityId 附件关联的实体id
	 * @param entityType  附件关联的表名
	 * @param uploader 附件上传人
	 * @param isUsable 资源是否有效 1：有效；0：表示无效
	 * @throws ServiceException 抛出的运行时异常，进行事务回滚
	 */
	public void saveFiles(List<FileItem> files,String module,String regionName,String entityId,String entityType,String uploader,String isUsable) throws ServiceException{
		UploadFileInfo uploadFile = null;
		AnnexAddOne addOne = null;
        //目录结构设置  区域+业务名称+年份+月份+文件（文件格式 年月日+xx公司xx文件+文档编号（fileseq）.doc）
        String fileSptr = File.separator;
       
        String relativePath = regionName+fileSptr+ModuleCatalog.get(module)+fileSptr+DateUtil.getNowDateString("yyyy年")+fileSptr+DateUtil.getNowDateString("MM月");
        String absolutePath = UPLOADROOT+File.separator+relativePath;
        createCatalog(absolutePath);
        if(files != null){
	        //单个文件上传
	        for(int i=0 ;i<files.size();i++){
				FileItem item = (FileItem)files.get(i);
				String fileName = item.getName();
				if(fileName.indexOf(File.separator) != -1){
					fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
				}
		       	 //对文件名称进行分割，从名称中分割出名称与文件类型 例如：xx年**公司接地电阻测试工作计划.doc
		       	 //分割为 xx年**公司接地电阻测试工作计划 与 .doc
		       	Integer separatorsIndex = fileName.lastIndexOf(".");
		       	String name = fileName.substring(0, separatorsIndex);
		       	String fileType = fileName.substring(separatorsIndex);
		       	String saveAsName = DateUtil.getNowDateString("yyyy年MM月dd日HHmmssSSS")+"_"+name;//在文件名称前增加时间戳+文件名称+随机码；防止文件重复
		       	 
		       	 uploadFile = new UploadFileInfo();
		       	 uploadFile.setCatlog("");
		       	 uploadFile.setDescription(module);
		       	 uploadFile.setFileType(fileType);
		       	 uploadFile.setOriginalName(name+fileType);
		       	 uploadFile.setSavePath(relativePath+File.separator+saveAsName);//相对路径
		       	 
		       	 transfersFile(item,uploadFile);
		       	 uploadDao.save(uploadFile);
		       	 addOne = new AnnexAddOne();
		       	 addOne.setFileId(uploadFile.getFileId());
		       	 addOne.setEntityId(entityId);//参数传入
		       	 addOne.setEntityType(entityType);
		       	 addOne.setModule(module);
		       	 addOne.setModuleCatalog(ModuleCatalog.get(module));
		       	 addOne.setUploader(uploader);
		       	 addOne.setTimeStamp(new Date());
		       	 addOne.setUploadDate(new Date());
		       	 addOne.setIsUsable(isUsable);
		       	 annexAddOneDao.save(addOne);
	        }
        }else{
        	logger.info("不包含任何附件");
        }
	}
	/***
	 * 上传多个附件，并记录附件的相关信息到ANNEX_ADD_ONE，FILEPATHINFO表中。
	 * <l>uploadFile.saveFiles(files, ModuleCatalog.HIDDTROUBLEWATCH, userInfo.getRegionName(), entityId, "LP_HIDDANGER_REPORT", userInfo.getUserID());</l>
	 * @param files 上传文件列表，存储在session的FILES变量中。
	 * @param module 模块常量 
	 * @param regionName 区域名称
	 * @param entityId 附件关联的实体id
	 * @param entityType  附件关联的表名
	 * @param uploader 附件上传人
	 * @throws ServiceException 抛出的运行时异常，进行事务回滚
	 */
	public void saveFiles(List<FileItem> files,String module,String regionName,String entityId,String entityType,String uploader) throws ServiceException{
		saveFiles(files, module, regionName, entityId, entityType, uploader,"1");
        
	}

	private void transfersFile(FileItem item, UploadFileInfo uploadFile) {
		Long fileSize =  item.getSize();
        uploadFile.setFileSize(fileSize);
        String absolutePathFile = UPLOADROOT+File.separator+uploadFile.getSavePath()+uploadFile.getFileType();
        logger.info("文件绝对路径："+absolutePathFile);
        try{
        	//获取输入流
        	InputStream stream = item.getInputStream();
	        //将上传文件写指定路径
	        File file = new File( absolutePathFile );
	        if(!file.exists()){
	    		boolean success = file.createNewFile();
	    		OutputStream bos = null;
	            if( success ){
	                bos = new FileOutputStream( absolutePathFile );
	                int bytesRead = 0;
	                byte[] buffer = new byte[8192];
	                while( ( bytesRead = stream.read( buffer, 0, 8192 ) ) != -1 ){
	                    bos.write( buffer, 0, bytesRead );
	                }
	                bos.close();
	                //close the stream
	                stream.close();
	                logger.info( "The file has been written to \"" + absolutePathFile + "\"" );
	            }else{
	            	logger.info( "不能够创建文件！" );
	        		throw new  ServiceException("该目录不能够创建文件！");
	            }
	        }else{//如果文件重复
	        	uploadFile.setSavePath(uploadFile.getSavePath()+"as_");
	        	absolutePathFile = UPLOADROOT+uploadFile.getSavePath()+uploadFile.getFileType();
	        	file = new File(absolutePathFile);
	        	if(!file.exists()){
	        		boolean success = file.createNewFile();
	        		OutputStream bos = null;
	        		if( success ){
		                bos = new FileOutputStream( absolutePathFile );
		                int bytesRead = 0;
		                byte[] buffer = new byte[8192];
		                while( ( bytesRead = stream.read( buffer, 0, 8192 ) ) != -1 ){
		                    bos.write( buffer, 0, bytesRead );
		                }
		                bos.close();
		                //close the stream
		                stream.close();
		            }else{
		            	logger.info( "不能够创建文件！" );
		        		throw new  ServiceException("该目录不能够创建文件！");
		            }
	        	}else{
	        		logger.info( "目录中存在相同文件，不能够创建！" );
	        		throw new  ServiceException("目录中存在相同文件，不能够创建！");
	        	}
	        }
	        uploadFile.setSavePath(uploadFile.getSavePath()+uploadFile.getFileType());
	        //将文件路径及相关属性写入数据库表
        }catch(Exception ex){
        	throw new  ServiceException(ex);
        }
	}
	
	private void createCatalog(String absolutePath){
		File file = new File( absolutePath );
		if(!file.exists()){
			file.mkdirs();
		}
	}
	@Transactional
	public void delById(String id){
		AnnexAddOne  addOne = annexAddOneDao.getAnnexAddOneByFileId(id);
		UploadFileInfo uploadFile = uploadDao.get(addOne.getFileId());
    	String absolutePathFile = UPLOADROOT+File.separator+uploadFile.getSavePath();
    	File file = new File( absolutePathFile );
    	file.delete();
    	uploadDao.delete(uploadFile);
    	annexAddOneDao.delete(addOne);
    	logger.info("删除文件:"+absolutePathFile);
	}
	
	/**
	 * 通过文件id获得单个文件对象
	 * @param fileId
	 * @return
	 */
	@Transactional(readOnly=true)
	public UploadFileInfo getFileId(String fileId) {
		UploadFileInfo uploadFile = uploadDao.get(fileId);
		uploadDao.initObject(uploadFile);
		return uploadFile;
	}
	/**
	 * 通过实体id，实体类型，获得相关附件信息。
	 * @param entityId
	 * @param entityType
	 * @param useable
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<FileItem> getFiles(String entityId, String entityType, String useable){
		List<FileItem> files = new ArrayList<FileItem>();
		FileItem fileItem = null;
		List<UploadFileInfo> fileInfos = uploadDao.getFilesByIds(entityId,entityType,useable);
		for(UploadFileInfo fileInfo:fileInfos){
			String fieldName = "file";
		    String contentType = null;
		    boolean isFormField = false;
		    String fileName = fileInfo.getOriginalName();
		    int sizeThreshold = 0;
		    File repository = new File(UPLOADROOT+File.separator+fileInfo.getSavePath());
		    fileItem = new DiskFileItem(fieldName,contentType,isFormField,fileName,sizeThreshold,repository);
		    files.add(fileItem);
		}
		return files;
	}
	
	/**
	 * 通过实体id，实体类型获得相关的附件。
	 * @param entityId
	 * @param entityType
	 */
	@Transactional(readOnly=true)
	public List<UploadFileInfo> getFilesByIds(String entityId, String entityType, String useable) {
		return uploadDao.getFilesByIds(entityId,entityType,useable);
	}
	@Transactional(readOnly=true)
	public List<UploadFileInfo> getImageFile(String entityId,String entityType){
		return uploadDao.getImageFiles(entityId,entityType);
	}
 
	/**
	 * 文件移动，将文件从原路径移动到相应路径下的upload/recycle里
	 * @param srcFile
	 * @return
	 */
	private boolean moveFile4Backup(String srcFile) {
		try {
			if (!(new File(RECYCLE).isDirectory())) {
				new File(RECYCLE).mkdir();
			}
		} catch (SecurityException e) {
			logger.error("不能创建recyle目录，请检查是否有目录权限");
		}
		File file = new File(UPLOADROOT+File.separator+srcFile);
		File dir = new File(RECYCLE);
		boolean success = file.renameTo(new File(dir, file.getName()));
		return success;
	}
	/**
	 * 将附件移除到系统回收站，并标记附件的状态，并不对数据库记录进行删除。
	 * @param id  指附件附加信息表AnnexAddOne的ID
	 * @return 返回是否成功
	 */
	@Transactional(readOnly=true)	
	public boolean delAnnexFile(String id){
		String path=uploadDao.getSavePathByAnnexId(id);
		System.out.println(path);
		boolean success=false;
		success=this.moveFile4Backup(path);
		if(success){
			uploadDao.updateState(id);
		}
		return success;
	}
}
