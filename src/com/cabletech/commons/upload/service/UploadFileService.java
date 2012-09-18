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
	//RECYCLEΪ����վ�ļ�·��
	public static String RECYCLE = UPLOADROOT+File.separator+"recycle";
	/**
	 * �ϴ��������������¼�����������Ϣ��ANNEX_ADD_ONE��FILEPATHINFO���С�
	 * @param files  �ϴ�����
	 * @param delIds ɾ������id
	 * @param module ģ�鳣�� 
	 * @param regionName ��������
	 * @param entityId ����������ʵ��id
	 * @param entityType  ���������ı���
	 * @param uploader �����ϴ���
	 * @throws ServiceException �׳�������ʱ�쳣��
	 * @see ModuleCatalog
	 * @deprecated
	 */
	public void saveFiles(List<UploadFile> files,List<String> delIds,String module,String regionName,String entityId,String entityType,String uploader) throws ServiceException{
		UploadFileInfo uploadFile = null;
		AnnexAddOne addOne = null;
        //Ŀ¼�ṹ����  ����+ҵ������+���+�·�+�ļ����ļ���ʽ ������+xx��˾xx�ļ�+�ĵ���ţ�fileseq��.doc��
        String fileSptr = File.separator;
       
        String relativePath = regionName+fileSptr+ModuleCatalog.get(module)+fileSptr+DateUtil.getNowDateString("yyyy��")+fileSptr+DateUtil.getNowDateString("MM��");
        String absolutePath = UPLOADROOT+File.separator+relativePath;
        createCatalog(absolutePath);
        //�����ļ��ϴ�
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
        	 //���ļ����ƽ��зָ�������зָ���������ļ����� ���磺xx��**��˾�ӵص�����Թ����ƻ�.doc
        	 //�ָ�Ϊ xx��**��˾�ӵص�����Թ����ƻ� �� .doc
        	 Integer separatorsIndex = fileName.lastIndexOf(".");
        	 String name = fileName.substring(0, separatorsIndex);
        	 String fileType = fileName.substring(separatorsIndex);
        	 String saveAsName = DateUtil.getNowDateString("yyyy��MM��dd��HHmmssSSS")+"_"+name;//���ļ�����ǰ����ʱ���+�ļ�����+����룻��ֹ�ļ��ظ�
        	 
        	 uploadFile = new UploadFileInfo();
        	 uploadFile.setCatlog("");
        	 uploadFile.setDescription(module);
        	 uploadFile.setFileType(fileType);
        	 uploadFile.setOriginalName(name+fileType);
        	 uploadFile.setSavePath(relativePath+File.separator+saveAsName);//���·��
        	 
        	 transfersFile(formFile,uploadFile);
        	 uploadDao.save(uploadFile);
        	 addOne = new AnnexAddOne();
        	 addOne.setFileId(uploadFile.getFileId());
        	 addOne.setEntityId(entityId);//��������
        	 addOne.setEntityType(entityType);
        	 addOne.setModule(module);
        	 addOne.setModuleCatalog(ModuleCatalog.get(module));
        	 addOne.setUploader(uploader);
        	 addOne.setTimeStamp(new Date());
        	 addOne.setUploadDate(new Date());
        	 annexAddOneDao.save(addOne);
        	 
        }
        File file =  null;
        //ɾ������;�����addOne��uploadFile �����Ѿ������ı���������
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
        logger.info("�ļ�����·����"+absolutePathFile);
        try{
        	//��ȡ������
        	InputStream stream = formFile.getInputStream();
	        //���ϴ��ļ�дָ��·��
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
	            	logger.info( "���ܹ������ļ���" );
	        		throw new  ServiceException("��Ŀ¼���ܹ������ļ���");
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
		            	logger.info( "���ܹ������ļ���" );
		        		throw new  ServiceException("��Ŀ¼���ܹ������ļ���");
		            }
	        	}else{
	        		logger.info( "Ŀ¼�д�����ͬ�ļ������ܹ�������" );
	        		throw new  ServiceException("Ŀ¼�д�����ͬ�ļ������ܹ�������");
	        	}
	        }
	        uploadFile.setSavePath(uploadFile.getSavePath()+uploadFile.getFileType());
	        //���ļ�·�����������д�����ݿ��
            
        }catch(Exception ex){
        	throw new  ServiceException(ex);
        }
	}
	/***
	 * �ϴ��������������¼�����������Ϣ��ANNEX_ADD_ONE��FILEPATHINFO���С�
	 * <l>uploadFile.saveFiles(files, ModuleCatalog.HIDDTROUBLEWATCH, userInfo.getRegionName(), entityId, "LP_HIDDANGER_REPORT", userInfo.getUserID(),"0");</l>
	 * @param files �ϴ��ļ��б��洢��session��FILES�����С�
	 * @param module ģ�鳣�� 
	 * @param regionName ��������
	 * @param entityId ����������ʵ��id
	 * @param entityType  ���������ı���
	 * @param uploader �����ϴ���
	 * @param isUsable ��Դ�Ƿ���Ч 1����Ч��0����ʾ��Ч
	 * @throws ServiceException �׳�������ʱ�쳣����������ع�
	 */
	public void saveFiles(List<FileItem> files,String module,String regionName,String entityId,String entityType,String uploader,String isUsable) throws ServiceException{
		UploadFileInfo uploadFile = null;
		AnnexAddOne addOne = null;
        //Ŀ¼�ṹ����  ����+ҵ������+���+�·�+�ļ����ļ���ʽ ������+xx��˾xx�ļ�+�ĵ���ţ�fileseq��.doc��
        String fileSptr = File.separator;
       
        String relativePath = regionName+fileSptr+ModuleCatalog.get(module)+fileSptr+DateUtil.getNowDateString("yyyy��")+fileSptr+DateUtil.getNowDateString("MM��");
        String absolutePath = UPLOADROOT+File.separator+relativePath;
        createCatalog(absolutePath);
        if(files != null){
	        //�����ļ��ϴ�
	        for(int i=0 ;i<files.size();i++){
				FileItem item = (FileItem)files.get(i);
				String fileName = item.getName();
				if(fileName.indexOf(File.separator) != -1){
					fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
				}
		       	 //���ļ����ƽ��зָ�������зָ���������ļ����� ���磺xx��**��˾�ӵص�����Թ����ƻ�.doc
		       	 //�ָ�Ϊ xx��**��˾�ӵص�����Թ����ƻ� �� .doc
		       	Integer separatorsIndex = fileName.lastIndexOf(".");
		       	String name = fileName.substring(0, separatorsIndex);
		       	String fileType = fileName.substring(separatorsIndex);
		       	String saveAsName = DateUtil.getNowDateString("yyyy��MM��dd��HHmmssSSS")+"_"+name;//���ļ�����ǰ����ʱ���+�ļ�����+����룻��ֹ�ļ��ظ�
		       	 
		       	 uploadFile = new UploadFileInfo();
		       	 uploadFile.setCatlog("");
		       	 uploadFile.setDescription(module);
		       	 uploadFile.setFileType(fileType);
		       	 uploadFile.setOriginalName(name+fileType);
		       	 uploadFile.setSavePath(relativePath+File.separator+saveAsName);//���·��
		       	 
		       	 transfersFile(item,uploadFile);
		       	 uploadDao.save(uploadFile);
		       	 addOne = new AnnexAddOne();
		       	 addOne.setFileId(uploadFile.getFileId());
		       	 addOne.setEntityId(entityId);//��������
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
        	logger.info("�������κθ���");
        }
	}
	/***
	 * �ϴ��������������¼�����������Ϣ��ANNEX_ADD_ONE��FILEPATHINFO���С�
	 * <l>uploadFile.saveFiles(files, ModuleCatalog.HIDDTROUBLEWATCH, userInfo.getRegionName(), entityId, "LP_HIDDANGER_REPORT", userInfo.getUserID());</l>
	 * @param files �ϴ��ļ��б��洢��session��FILES�����С�
	 * @param module ģ�鳣�� 
	 * @param regionName ��������
	 * @param entityId ����������ʵ��id
	 * @param entityType  ���������ı���
	 * @param uploader �����ϴ���
	 * @throws ServiceException �׳�������ʱ�쳣����������ع�
	 */
	public void saveFiles(List<FileItem> files,String module,String regionName,String entityId,String entityType,String uploader) throws ServiceException{
		saveFiles(files, module, regionName, entityId, entityType, uploader,"1");
        
	}

	private void transfersFile(FileItem item, UploadFileInfo uploadFile) {
		Long fileSize =  item.getSize();
        uploadFile.setFileSize(fileSize);
        String absolutePathFile = UPLOADROOT+File.separator+uploadFile.getSavePath()+uploadFile.getFileType();
        logger.info("�ļ�����·����"+absolutePathFile);
        try{
        	//��ȡ������
        	InputStream stream = item.getInputStream();
	        //���ϴ��ļ�дָ��·��
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
	            	logger.info( "���ܹ������ļ���" );
	        		throw new  ServiceException("��Ŀ¼���ܹ������ļ���");
	            }
	        }else{//����ļ��ظ�
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
		            	logger.info( "���ܹ������ļ���" );
		        		throw new  ServiceException("��Ŀ¼���ܹ������ļ���");
		            }
	        	}else{
	        		logger.info( "Ŀ¼�д�����ͬ�ļ������ܹ�������" );
	        		throw new  ServiceException("Ŀ¼�д�����ͬ�ļ������ܹ�������");
	        	}
	        }
	        uploadFile.setSavePath(uploadFile.getSavePath()+uploadFile.getFileType());
	        //���ļ�·�����������д�����ݿ��
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
    	logger.info("ɾ���ļ�:"+absolutePathFile);
	}
	
	/**
	 * ͨ���ļ�id��õ����ļ�����
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
	 * ͨ��ʵ��id��ʵ�����ͣ������ظ�����Ϣ��
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
	 * ͨ��ʵ��id��ʵ�����ͻ����صĸ�����
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
	 * �ļ��ƶ������ļ���ԭ·���ƶ�����Ӧ·���µ�upload/recycle��
	 * @param srcFile
	 * @return
	 */
	private boolean moveFile4Backup(String srcFile) {
		try {
			if (!(new File(RECYCLE).isDirectory())) {
				new File(RECYCLE).mkdir();
			}
		} catch (SecurityException e) {
			logger.error("���ܴ���recyleĿ¼�������Ƿ���Ŀ¼Ȩ��");
		}
		File file = new File(UPLOADROOT+File.separator+srcFile);
		File dir = new File(RECYCLE);
		boolean success = file.renameTo(new File(dir, file.getName()));
		return success;
	}
	/**
	 * �������Ƴ���ϵͳ����վ������Ǹ�����״̬�����������ݿ��¼����ɾ����
	 * @param id  ָ����������Ϣ��AnnexAddOne��ID
	 * @return �����Ƿ�ɹ�
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
