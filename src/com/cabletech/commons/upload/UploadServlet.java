package com.cabletech.commons.upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.config.SpringContext;
import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.commons.upload.service.UploadFileService;

/**
 * @author zhj
 * 
 * �ϴ�����
 */
public class UploadServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(UploadServlet.class);
	private static boolean debug = false;// �Ƿ�debugģʽ
	private static final long serialVersionUID = 1L;
	public static String UPLOADTMP = "/tmp";
	public static int SIZETHRESHOLD=  2048000;//ָ�����ڴ��л������ݴ�С
	public static long SIZEMAX = 90000000; //����������ϴ��ļ��ߴ�
	public static long IMAGEMAXSIZE = 307200;//ͼƬ�ļ��ϴ���С
	public static long OTHERMAXSIZE = 10240000;//�ϴ��ļ���С
	
	private static Hashtable deniedExtensions;// ��ֹ���ϴ��ļ���չ��
	private static Hashtable allowedExtensions;// ������ϴ��ļ���չ��
	private List list = null;
	File tmpDir = null;//��ʼ���ϴ��ļ�����ʱ���Ŀ¼
	

	/* ��ʼ��*/
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		UPLOADTMP = GisConInfo.newInstance().getUploadTmp();
		tmpDir = new File(UPLOADTMP);
		if (!tmpDir.isDirectory()){
			tmpDir.mkdir();
		}
		allowedExtensions = new Hashtable(2);
		deniedExtensions = new Hashtable(2);
		allowedExtensions.put("File",stringToArrayList(getInitParameter("AllowedExtensionsFile")));
		deniedExtensions.put("File",stringToArrayList(getInitParameter("DeniedExtensionsFile")));//ʧЧ���������ļ�
		allowedExtensions.put("Image",stringToArrayList(getInitParameter("AllowedExtensionsImage")));
		deniedExtensions.put("Image",stringToArrayList(getInitParameter("DeniedExtensionsImage")));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		upload(request, response);  
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		upload(request, response);
	}

	protected void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("�������ͣ�"+request.getParameter("type"));
		String type = request.getParameter("type");
		if(type.equals("upload")){
			List<FileItem> files = (List)request.getSession().getAttribute("FILES");
	        if(files == null){
	        	files = new ArrayList<FileItem>();
	        }
			try{
		        if(ServletFileUpload.isMultipartContent(request)){
		          DiskFileItemFactory factory = new DiskFileItemFactory();//�����ö���
		          factory.setRepository(tmpDir);//ָ���ϴ��ļ�����ʱĿ¼
		          factory.setSizeThreshold(SIZETHRESHOLD);//ָ�����ڴ��л������ݴ�С,��λΪbyte
		          ServletFileUpload upload = new ServletFileUpload(factory);//�����ö���
		          upload.setSizeMax(SIZEMAX);//ָ��һ���ϴ�����ļ����ܳߴ�
		          List items = upload.parseRequest(request);//����request ����,������FileItemIterator����
		          
		          Iterator iter = items.iterator();
		          while(iter.hasNext()){
		           //�Ӽ����л��һ���ļ���
		            FileItem item = (FileItem) iter.next();
		            if(!item.isFormField() && item.getName().length()>0 ){//���˵����з��ļ���
		            	logger.info("�ļ����ƣ�"+item.getName());
		            	logger.info("filesize:"+item.getSize());
		            	String fileName = item.getName();
		            	fileName = fileName.replace('\\', '/');
		            	String[] pathParts = fileName.split("/");
						fileName = pathParts[pathParts.length - 1];
						// ��ȡ�ļ���չ��
						String ext = getExtension(fileName);
						if (extIsAllowed("File", ext)) {
			            	if(extIsAllowed("Image", ext)){
			            		if(item.getSize()<=IMAGEMAXSIZE){
			            			files.add(item);
			            		}else{
			            			outprint(response,"msg:ͼƬ��С���ܴ���300k");
			            		}
			            	}else{
			            		if(item.getSize()<=OTHERMAXSIZE){
			            			files.add(item);
			            		}else{
			            			outprint(response,"msg:�ļ���С���ܴ���10M");
			            		}
			            	}
						}else{
							if (debug){
								logger.info("��Ч���ļ����ͣ� " + ext);
							}
							outprint(response,"msg:��Ч�ļ����ͣ�"+ext);
						}
		            	
		            }
		          }
		          request.getSession().setAttribute("FILES", files);
		          StringBuilder builder = new StringBuilder();
			        int i = 0;
			        for(FileItem item: files){
			        	String fileName = item.getName();
			        	fileName = fileName.replace('\\', '/');
		            	String[] pathParts = fileName.split("/");
		            	fileName = pathParts[pathParts.length - 1];
			        	builder.append("<a href='javascript:;' onclick='removefile(\""+i+"\")'><img src=\"/WebApp/js/uploader/del.gif\"/  border=\"0\" alt=\"�Ƴ�\"></a>  "+fileName+"<br>");//���ڳɹ���,����������ϴ��ļ��п���,��Ҫ�Ķ�������������
			        	i++;
			        }
			        outprint(response,builder.toString());
		        }
		    }catch(Exception e){
		        e.printStackTrace();
		        System.out.println("error:"+e.getMessage());
		        outprint(response,"msg:�ϴ����ļ��������趨��С");
		    }
		    
		   
		}else if(type.equals("remove")){
			int index = Integer.parseInt(request.getParameter("delid"));
			List<FileItem> files = (List<FileItem>)request.getSession().getAttribute("FILES");
			FileItem fileitem = files.remove(index);
			System.out.println("ɾ���ɹ�"+fileitem.getName());
			System.out.println("list"+files.size());
			StringBuilder builder = new StringBuilder();
			int i = 0;
	        for(FileItem item: files){
	        	builder.append("<a href=\"javascript:;\" onclick='removefile(\""+i+"\")'><img src=\"/WebApp/js/uploader/del.gif\" border=\"0\"  alt=\"�Ƴ�\"/></a> "+item.getName()+"<br>");//���ڳɹ���,����������ϴ��ļ��п���,��Ҫ�Ķ�������������
	        	i++;
	        }
	        outprint(response,builder.toString());
	        
			
		}else if(type.equals("del")){
			int index = Integer.parseInt(request.getParameter("delid"));
			List<UploadFileInfo> oldfiles = (List)request.getSession().getAttribute("OLDFILE");
			UploadFileInfo file = oldfiles.remove(index);
			UploadFileService  uploadFileService = (UploadFileService)SpringContext.getApplicationContext().getBean("uploadFileService");
			uploadFileService.delById(file.getFileId());
			System.out.println("ɾ���ɹ�"+file.getOriginalName());
			System.out.println("list"+oldfiles.size());
			StringBuilder builder = new StringBuilder();
			int i = 0;
			for(UploadFileInfo upfile:oldfiles){
				builder.append("<a href='javascript:;' onclick='del(\""+i+"\")'><img src=\"/WebApp/js/uploader/del.gif\" border=\"0\" alt=\"�Ƴ�\"/></a>");
				builder.append("<a href='/WebApp/download.do?method=download&fileid="+upfile.getFileId()+ "'><img border='0' src='/WebApp/images/attachment.gif' width='15' height='15'>"+ upfile.getOriginalName() + "</a><br>");
				i++;
			}
			outprint(response,builder.toString());
		}
			
	}
	/**
	 * �ַ�����ArrayListת���ķ���
	 */
	private ArrayList stringToArrayList(String str) {
		if (debug)
			logger.info(str);
		String[] strArr = str.split("\\|");
		ArrayList tmp = new ArrayList();
		if (str.length() > 0) {
			for (int i = 0; i < strArr.length; ++i) {
				if (debug)
					logger.info(i + " - " + strArr[i]);
				tmp.add(strArr[i].toLowerCase());
			}
		}
		return tmp;
	}
	
	/**
	 * ��ȡ�ļ����ķ���
	 */
	private static String getNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
	/**
	 * ��ȡ��չ���ķ���
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	/**
	 * �ж���չ���Ƿ�����ķ���
	 */
	private boolean extIsAllowed(String fileType, String ext) {
		ext = ext.toLowerCase();
		ArrayList allowList = (ArrayList) allowedExtensions.get(fileType);
		ArrayList denyList = (ArrayList) deniedExtensions.get(fileType);
		if (allowList.size() == 0) {
			if (denyList.contains(ext)) {
				return false;
			} else {
				return true;
			}
		}
		if (denyList.size() == 0) {
			if (allowList.contains(ext)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	private void outprint(HttpServletResponse response,String text) throws IOException{
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.print(text);
		out.flush();
	}
}
