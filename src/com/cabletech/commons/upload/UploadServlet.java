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
 * 上传附件
 */
public class UploadServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(UploadServlet.class);
	private static boolean debug = false;// 是否debug模式
	private static final long serialVersionUID = 1L;
	public static String UPLOADTMP = "/tmp";
	public static int SIZETHRESHOLD=  2048000;//指定在内存中缓存数据大小
	public static long SIZEMAX = 90000000; //服务器最大上传文件尺寸
	public static long IMAGEMAXSIZE = 307200;//图片文件上传大小
	public static long OTHERMAXSIZE = 10240000;//上传文件大小
	
	private static Hashtable deniedExtensions;// 阻止的上传文件扩展名
	private static Hashtable allowedExtensions;// 允许的上传文件扩展名
	private List list = null;
	File tmpDir = null;//初始化上传文件的临时存放目录
	

	/* 初始化*/
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
		deniedExtensions.put("File",stringToArrayList(getInitParameter("DeniedExtensionsFile")));//失效的扩张名文件
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
		System.out.println("操作类型："+request.getParameter("type"));
		String type = request.getParameter("type");
		if(type.equals("upload")){
			List<FileItem> files = (List)request.getSession().getAttribute("FILES");
	        if(files == null){
	        	files = new ArrayList<FileItem>();
	        }
			try{
		        if(ServletFileUpload.isMultipartContent(request)){
		          DiskFileItemFactory factory = new DiskFileItemFactory();//创建该对象
		          factory.setRepository(tmpDir);//指定上传文件的临时目录
		          factory.setSizeThreshold(SIZETHRESHOLD);//指定在内存中缓存数据大小,单位为byte
		          ServletFileUpload upload = new ServletFileUpload(factory);//创建该对象
		          upload.setSizeMax(SIZEMAX);//指定一次上传多个文件的总尺寸
		          List items = upload.parseRequest(request);//解析request 请求,并返回FileItemIterator集合
		          
		          Iterator iter = items.iterator();
		          while(iter.hasNext()){
		           //从集合中获得一个文件流
		            FileItem item = (FileItem) iter.next();
		            if(!item.isFormField() && item.getName().length()>0 ){//过滤掉表单中非文件域
		            	logger.info("文件名称："+item.getName());
		            	logger.info("filesize:"+item.getSize());
		            	String fileName = item.getName();
		            	fileName = fileName.replace('\\', '/');
		            	String[] pathParts = fileName.split("/");
						fileName = pathParts[pathParts.length - 1];
						// 获取文件扩展名
						String ext = getExtension(fileName);
						if (extIsAllowed("File", ext)) {
			            	if(extIsAllowed("Image", ext)){
			            		if(item.getSize()<=IMAGEMAXSIZE){
			            			files.add(item);
			            		}else{
			            			outprint(response,"msg:图片大小不能大于300k");
			            		}
			            	}else{
			            		if(item.getSize()<=OTHERMAXSIZE){
			            			files.add(item);
			            		}else{
			            			outprint(response,"msg:文件大小不能大于10M");
			            		}
			            	}
						}else{
							if (debug){
								logger.info("无效的文件类型： " + ext);
							}
							outprint(response,"msg:无效文件类型："+ext);
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
			        	builder.append("<a href='javascript:;' onclick='removefile(\""+i+"\")'><img src=\"/WebApp/js/uploader/del.gif\"/  border=\"0\" alt=\"移除\"></a>  "+fileName+"<br>");//终于成功了,还不到你的上传文件中看看,你要的东西都到齐了吗
			        	i++;
			        }
			        outprint(response,builder.toString());
		        }
		    }catch(Exception e){
		        e.printStackTrace();
		        System.out.println("error:"+e.getMessage());
		        outprint(response,"msg:上传的文件超过了设定大小");
		    }
		    
		   
		}else if(type.equals("remove")){
			int index = Integer.parseInt(request.getParameter("delid"));
			List<FileItem> files = (List<FileItem>)request.getSession().getAttribute("FILES");
			FileItem fileitem = files.remove(index);
			System.out.println("删除成功"+fileitem.getName());
			System.out.println("list"+files.size());
			StringBuilder builder = new StringBuilder();
			int i = 0;
	        for(FileItem item: files){
	        	builder.append("<a href=\"javascript:;\" onclick='removefile(\""+i+"\")'><img src=\"/WebApp/js/uploader/del.gif\" border=\"0\"  alt=\"移除\"/></a> "+item.getName()+"<br>");//终于成功了,还不到你的上传文件中看看,你要的东西都到齐了吗
	        	i++;
	        }
	        outprint(response,builder.toString());
	        
			
		}else if(type.equals("del")){
			int index = Integer.parseInt(request.getParameter("delid"));
			List<UploadFileInfo> oldfiles = (List)request.getSession().getAttribute("OLDFILE");
			UploadFileInfo file = oldfiles.remove(index);
			UploadFileService  uploadFileService = (UploadFileService)SpringContext.getApplicationContext().getBean("uploadFileService");
			uploadFileService.delById(file.getFileId());
			System.out.println("删除成功"+file.getOriginalName());
			System.out.println("list"+oldfiles.size());
			StringBuilder builder = new StringBuilder();
			int i = 0;
			for(UploadFileInfo upfile:oldfiles){
				builder.append("<a href='javascript:;' onclick='del(\""+i+"\")'><img src=\"/WebApp/js/uploader/del.gif\" border=\"0\" alt=\"移除\"/></a>");
				builder.append("<a href='/WebApp/download.do?method=download&fileid="+upfile.getFileId()+ "'><img border='0' src='/WebApp/images/attachment.gif' width='15' height='15'>"+ upfile.getOriginalName() + "</a><br>");
				i++;
			}
			outprint(response,builder.toString());
		}
			
	}
	/**
	 * 字符串向ArrayList转化的方法
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
	 * 获取文件名的方法
	 */
	private static String getNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
	/**
	 * 获取扩展名的方法
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	/**
	 * 判断扩展名是否允许的方法
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
