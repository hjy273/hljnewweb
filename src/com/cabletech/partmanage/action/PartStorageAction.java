package com.cabletech.partmanage.action;

import java.text.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.partmanage.beans.*;
import com.cabletech.partmanage.dao.*;
import com.cabletech.power.*;
import com.cabletech.commons.hb.QueryUtil;
import java.sql.ResultSet;
import com.cabletech.commons.web.ClientException;

public class PartStorageAction extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( PartStorageAction.class.
                                   getName() );
    public PartStorageAction(){
    }


    //�鿴���
    public ActionForward showAllStorage( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80601" )
        	|| CheckPower.checkPower( request.getSession(), "80603")
            ||CheckPower.checkPower( request.getSession(), "80609" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        List lStorageInfo = null;
        try{
            PartStorageDao dao = new PartStorageDao();
            if(request.getParameter("querytype")!=null
                &&request.getParameter("querytype").equals("1")){
                if( request.getParameter("deptype").equals( "2" ) ){ //��ά��λ
                    lStorageInfo = dao.getAllStorageForContractor( request );
                }
                else{ //�ƶ���˾
                    lStorageInfo = dao.getAllStorageForDepart( request );
                }
                request.getSession().setAttribute( "type", "showst1" );
            }else{
                if( userinfo.getDeptype().equals( "2" ) ){ //��ά��λ
                    lStorageInfo = dao.getAllStorageForCon( request );
                }
                else{ //�ƶ���˾
                    lStorageInfo = dao.getAllStorageForDept( userinfo.getRegionID() );
                }
                request.getSession().setAttribute( "type", "st1" );
            }
            request.getSession().setAttribute("querytype","1");
            request.getSession().setAttribute( "storageinfo", lStorageInfo );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            e.printStackTrace();
            logger.error( "��ʾ���г��ⵥ��Ϣ����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�ۺϲ�ѯ��ʾ
    public ActionForward queryShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	
        if( !CheckPower.checkPower( request.getSession(), "80603" ) 
        		&& !CheckPower.checkPower( request.getSession(), "80601")){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        request.setAttribute( "unittype", userinfo.getDeptype() );

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            PartStorageDao stDao = new PartStorageDao();
            //��ò��������б��id�б�
            List partName = stDao.getPartNameArr( userinfo.getRegionID() );
            request.setAttribute( "partname", partName );
            //��ò��������б�
            List partType = stDao.getPartTypeArr( userinfo.getRegionID() );
            request.setAttribute( "parttype", partType );
            //��ô�ά��λ���ƺ�id
            List contractorname = stDao.getContractorNameArr( request );
            request.setAttribute( "contractorname", contractorname );

            request.getSession().setAttribute( "type", "st3" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯ��ʾ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�ۺϲ�ѯִ��
    public ActionForward queryExec( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80603" )
        		&& !CheckPower.checkPower( request.getSession(), "80601")){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        String newlownumber = request.getParameter( "newlownumber" );
        String newhignumber = request.getParameter( "newhignumber" );
        String oldlownumber = request.getParameter( "oldlownumber" );
        String oldhignumber = request.getParameter( "oldhignumber" );

        try{
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setRegionid( userinfo.getRegionID() );
            PartStorageDao dao = new PartStorageDao();
            List lStorageInfo = null;
            if( userinfo.getDeptype().equals( "1" ) ){ //���ƶ���˾
                lStorageInfo = dao.getStorageForDept( bean, newlownumber, newhignumber, oldlownumber, oldhignumber );
            }
            else{ //�Ǵ�ά��λ
                String contractorid = ( String )request.getSession().getAttribute(
                                      "LOGIN_USER_DEPT_ID" );
                bean.setContractorid( contractorid );
                lStorageInfo = dao.getStorageForContractor( bean, newlownumber, newhignumber, oldlownumber,
                               oldhignumber, contractorid );
            }

            request.getSession().setAttribute( "storageinfo", lStorageInfo );
            request.getSession().setAttribute( "type", "st1" );
            request.getSession().setAttribute( "newlownumber", newlownumber );
            request.getSession().setAttribute( "newhignumber", newhignumber );
            request.getSession().setAttribute( "oldlownumber", oldlownumber );
            request.getSession().setAttribute( "oldhignumber", oldhignumber );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʼ�������ʾ
    public ActionForward initStorageShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80602" ) ){
            return mapping.findForward( "powererror" );
        }

        PartRequisitionDao dao = new PartRequisitionDao();
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        String deptname = dao.getUserDeptName( userinfo );
        request.setAttribute( "deptname", deptname );
        request.setAttribute( "deptid", userinfo.getDeptID() );
        request.setAttribute( "username", userinfo.getUserName() );
        request.setAttribute( "userid", userinfo.getUserID() );

        //��÷������ĵ�ǰʱ��
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
        String data = df.format( nowDate );
        request.setAttribute( "date", data );

        //��ò�����Ϣ,�Թ��û�ѡ��
        List lBaseInfo = dao.getAllInfo( userinfo.getRegionID() );
        request.setAttribute( "baseinfo", lBaseInfo );

        request.getSession().setAttribute( "type", "st2" );
        return mapping.findForward( "success" );
    }


    //ִ�г�ʼ��
    public ActionForward initStorage( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80602" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStorageDao dao = new PartStorageDao();
            Part_requisitionBean bean = ( Part_requisitionBean )form;

            String[] id = request.getParameterValues( "id" );
            String[] newesse = request.getParameterValues( "newesse" );
            String[] oldnumber = request.getParameterValues( "oldnumber" );

            if( !dao.initStorage( bean, id, oldnumber, newesse ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "���Ϲ���", "��ʼ�����" );
            return forwardInfoPage( mapping, request, "80602" );
        }
        catch( Exception e ){
            logger.error( "��ִ�г��ⵥ�г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportStorageResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            String newlownumber = "";
            String newhignumber = "";
            String oldlownumber = "";
            String oldhignumber = "";

            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "��ò�ѯ����bean������" );

                 newlownumber = ( String )request.getSession().getAttribute( "newlownumber" );
                 newhignumber = ( String )request.getSession().getAttribute( "newhignumber" );
                 oldlownumber = ( String )request.getSession().getAttribute( "oldlownumber" );
                 oldhignumber = ( String )request.getSession().getAttribute( "oldhignumber" );

                logger.info( "newlownumber" + newlownumber );
                logger.info( "newhignumber" + newhignumber );
                logger.info( "oldlownumber" + oldlownumber );
                logger.info( "oldhignumber" + oldhignumber );
                //��õ�ǰ�û��ĵ�λ����
                UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
                if( userinfo.getDeptype().equals( "1" ) ){ //���ƶ���˾
                    String sql =
                        "select c.CONTRACTORNAME from contractorinfo c where c.CONTRACTORID = '"
                        + bean.getId() + "'";
                    logger.info( sql );
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setContractorname( rs.getString( 1 ) );
                    }
                 logger.info( "contractorid��" + bean.getId() );
                 logger.info( "��λ���ƣ�" + bean.getContractorname() );
                }
                logger.info( "�������ƣ�" + bean.getName() );
                logger.info( "�������ͣ�" + bean.getType() );
                request.getSession().removeAttribute("bean");
            }

            PartExportDao dao = new PartExportDao();
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "storageinfo" );
            logger.info( "�õ�list" );
            dao.exportStorageResult( list, bean, newlownumber, newhignumber, oldlownumber, oldhignumber, response);
            logger.info( "���excel�ɹ�" );

            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * ��ѯ���в��Ͽ����Ϣ�ı�
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryAllStorage(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,HttpServletResponse response)
        throws ClientException, Exception{
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        List regionList=null;
        List deptList=null;
        List conList=null;
        String region="select r.regionid,r.regionname "
                     +"from region r "
                     +"where (r.state is null or r.state<>'1') and substr(regionid,3,4)<>'1111'";
        String dept="select d.deptid,d.deptname,d.regionid "
                    +"from deptinfo d "
                    +"where (d.state is null or d.state<>'1')";
        String con="select c.contractorid ,c.contractorname ,c.regionid "
                   +"from contractorinfo c "
                   +"where (c.state is null or c.state<>'1')";
        if(userinfo.getType().equals("21")){
            con+=" and contractorid in ("
                +"   select contractorid from contractorinfo "
                +"   where parentcontractorid='"+userinfo.getDeptID()+"' or contractorid='"+userinfo.getDeptID()+"'"
                +" )";
        }
        if(userinfo.getType().equals("12")){
            con+=" and regionid='"+userinfo.getRegionID()+"'";
            dept+=" and regionid='"+userinfo.getRegionID()+"'";
        }
        if(userinfo.getType().equals("22")){
            con+=" and contractorid='"+userinfo.getDeptID()+"'";
        }
        dept+=" order by regionid";
        con+=" order by regionid";
        regionList = super.getDbService().queryBeans( region );
        deptList = super.getDbService().queryBeans( dept );
        conList = super.getDbService().queryBeans( con );
        request.setAttribute("regionlist",regionList);
        request.setAttribute("deptlist",deptList);
        request.setAttribute("conlist",conList);

        PartBaseInfoDao dao = new PartBaseInfoDao();
        List lName = dao.getAllName();
        List lType = dao.getAllType();
        request.setAttribute( "nameList", lName );
        request.setAttribute( "typeList", lType );

        return mapping.findForward("queryallstorage");
    }
}
