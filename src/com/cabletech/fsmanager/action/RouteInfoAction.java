package com.cabletech.fsmanager.action;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.beans.SublineBean;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;
import com.cabletech.commons.web.ClientException;
import com.cabletech.fsmanager.bean.RouteInfoBean;
import com.cabletech.fsmanager.dao.RouteInfoDao;
import com.cabletech.fsmanager.domainobjects.RouteInfo;
import com.cabletech.groupcustomer.bean.GroupCustomerBean;
import com.cabletech.groupcustomer.dao.GroupCustomerDao;
import com.cabletech.power.CheckPower;
import com.cabletech.watchinfo.templates.WatchDetailTemplate;

public class RouteInfoAction extends BaseInfoBaseDispatchAction{
	private static Logger logger =
        Logger.getLogger( RouteInfoAction.class.getName() );
	//增加路由信息
	 public ActionForward addRouteInfo( ActionMapping mapping,
		        ActionForm form,
		        HttpServletRequest request,
		        HttpServletResponse response ) throws
		        ClientException, Exception{
		 	RouteInfoBean bean = (RouteInfoBean)form;
		      RouteInfoDao dao = new RouteInfoDao();
		      bean.setId(this.getDbService().getSeq("routeinfo", 20));
		      boolean sucess = dao.addRouteInfo(bean);
		      if(sucess){
		    	  return forwardInfoPage( mapping, request, "0111" );
		      }
		      else{
		    	  log( request, " 增加路由信息 ", " 基础资料管理 " );
		    	  return forwardErrorPage(mapping, request, "error");
		      }
		    }
	 //载入路由信息
	 public ActionForward loadRouteInfo( ActionMapping mapping,
		        ActionForm form,
		        HttpServletRequest request,
		        HttpServletResponse response ) throws
		        ClientException, Exception{
		       RouteInfoDao dao = new RouteInfoDao();
		       RouteInfoBean bean = (RouteInfoBean)form;
		        try {
		            String id = request.getParameter("id");
		            bean = dao.getRouteInfoById(id, bean);
		            request.setAttribute("routeinfoBean", bean);
		        } catch (Exception e) {
		            logger.info("加载路由信息失败！");
		            e.printStackTrace();
		        }
		        return mapping.findForward("updateRouteInfo");
		    }
	
	 //删除路由信息
	 public ActionForward deleteRouteInfo( ActionMapping mapping,
		        ActionForm form,
		        HttpServletRequest request,
		        HttpServletResponse response ) throws
		        ClientException, Exception{
		        RouteInfoDao dao = new RouteInfoDao();
		        	String id = request.getParameter("id");
		        	boolean sucess = dao.deleteRouteInfoById(id);
		        	if(sucess){
		            log( request, " 删除路由信息 ", " 基础资料管理 " );
		            String strQueryString = getTotalQueryString("method=queryRouteInfo&routeName=",(RouteInfoBean)request.getSession().getAttribute("theQueryBean"));
		            Object[] args = getURLArgs("/WebApp/RouteInfoAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
		            return forwardInfoPage( mapping, request, "0333",null,args);
		        	}else{
		        		 return forwardErrorPage( mapping, request, "error" );
		        	}
		    }
	 //查询路由信息
	 public ActionForward queryRouteInfo( ActionMapping mapping,
		        ActionForm form,
		        HttpServletRequest request,
		        HttpServletResponse response ){
		   RouteInfoBean bean = (RouteInfoBean)form;
		   RouteInfoDao dao = new RouteInfoDao();
		   request.getSession().setAttribute("S_BACK_URL",null); //
	        request.getSession().setAttribute("theQueryBean", bean);//
	        try{
		            List list = dao.getRouteInfoBean(bean);
		            request.getSession().setAttribute( "queryresult", list );
		            super.setPageReset(request);
		            return mapping.findForward( "queryrouteresult" );
		        }
		        catch( Exception e ){
		            logger.error( "查询路由信息异常:" + e.getMessage() );
		            return forwardErrorPage( mapping, request, "error" );
		        }
		    }
		        
	 //修改路由信息
	 public ActionForward updateRouteInfo( ActionMapping mapping,
		        ActionForm form,
		        HttpServletRequest request,
		        HttpServletResponse response ) throws
		        ClientException, Exception{
		  RouteInfoBean bean = (RouteInfoBean)form;
	      RouteInfoDao dao = new RouteInfoDao();
	      boolean sucess = dao.updateRouteInfo(bean);
	      if(sucess){
		            log(request, " 更新路由信息 ", " 基础资料管理 ");
		            String strQueryString = getTotalQueryString("method=queryRouteInfo&routeName=",(RouteInfoBean)request.getSession().getAttribute("theQueryBean"));
		            Object[] args = getURLArgs("/WebApp/RouteInfoAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
		            return forwardInfoPage( mapping, request, "0222",null,args);
		           }
			           else{
			    	  return forwardErrorPage( mapping, request, "error" );
	              }
	          }
	 
			 public String getTotalQueryString(String queryString,RouteInfoBean bean){
			    	if (bean.getRouteName() != null){
			    		queryString = queryString + bean.getRouteName();
			    	}
			    	if (bean.getId()!= null){
			    		queryString = queryString + "&id=" + bean.getId();
			    	}
			    	if (bean.getRegionID() != null){
			    		queryString = queryString + "&regionID=" + bean.getRegionID();
			    	}
			    	return queryString;
			    }


	 		//导出路由信息列表
		    public ActionForward exportRouteInfoResult( ActionMapping mapping, ActionForm form,
		        HttpServletRequest request,
		        HttpServletResponse response ){
		        try{
		            logger.info( " 创建dao" );
		            List list = ( List )request.getSession().getAttribute( "queryresult" );
		            logger.info( "得到list" );
		            super.getService().exportRouteInfoResult( list, response );
		            logger.info( "输出excel成功" );
		            return null;
		        }
		        catch( Exception e ){
		            logger.error( "导出信息报表出现异常:" + e.getMessage() );
		            return forwardErrorPage( mapping, request, "error" );
		        }
		    }
		    //下载路由信息模板
		    public ActionForward downloadTemplet(ActionMapping mapping, ActionForm form,
		            HttpServletRequest request,
		            HttpServletResponse response ) {
		    	
		    	response.reset();
				response.setContentType("application/vnd.ms-excel");
				try {
					response.setHeader("Content-Disposition", "attachment;filename="
							+ new String("路由信息导入模板.xls".getBytes(), "iso-8859-1"));
					OutputStream out = response.getOutputStream();

					WatchDetailTemplate template = new WatchDetailTemplate(servlet
							.getServletContext().getRealPath("/upload")
							+ "/routeinfotemplate.xls");
					template.write(out);
					logger.info( "下载模板成功" );
			    	
				} catch (Exception e) {
					logger.warn("下载路由信息导入模板出错：" + e.getMessage());
					e.printStackTrace();
				}
				return null;
				
		    }
		    //导入路由信息
		    public ActionForward upLoadShow( ActionMapping mapping, ActionForm form,
		            HttpServletRequest request,
		            HttpServletResponse response ){

		            RouteInfoDao dao = new RouteInfoDao();
		        	RouteInfoBean bean = ( RouteInfoBean )form;
		        	
		        	// 判断导入文件类型
		            String filename = bean.getFile().getFileName();
		            if( filename.equals( "" ) || filename == null ){
		                return forwardErrorPage( mapping, request, "fileerror" );
		            }
		            if( !filename.substring( filename.length() - 3, filename.length() ).equals( "xls" ) ){
		                return forwardErrorPage( mapping, request, "structerror" );
		            }

		            // 取得导入临时文件的存入路径
		            String path = servlet.getServletContext().getRealPath( "/upload" );
		            // 保存路由资料
		            if(!dao.saveExcelRouteInfo(bean, path)){
		                return forwardErrorPage( mapping, request, "error" );
		            }
		            // 成功转向增加页面        
		            return forwardInfoPage( mapping, request, "0111up" );
		        }
		    
}