package com.cabletech.partmanage.action;

import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import com.cabletech.power.CheckPower;
import org.apache.struts.action.ActionForm;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.partmanage.dao.*;
import com.cabletech.partmanage.beans.Part_requisitionBean;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import org.apache.log4j.Logger;
import java.text.DateFormat;
import java.sql.ResultSet;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.web.ClientException;

public class PartStockAction extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( PartStockAction.class.
                                   getName() );
    //��ʾ���д��������뵥
    public ActionForward doStockShow( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80401" ) ){
            return mapping.findForward( "powererror" );
        }

        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ���������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStockDao dao = new PartStockDao();
            List lReqInfo = dao.getAllReqForStock( userinfo.getDeptID() );
            request.getSession().setAttribute( "reqinfo", lReqInfo );
            request.getSession().setAttribute( "type", "stock2" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ���д��������뵥����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾһ�����뵥����ϸ��Ϣ����ʼ���
    public ActionForward doStockPartForOneReq( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80401" ) ){
            return mapping.findForward( "powererror" );
        }

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean reqinfo = new Part_requisitionBean(); //����ҳ������뵥������Ϣ
        List reqpartinfo = null;
        PartRequisitionDao dao = new PartRequisitionDao();
        String reid = request.getParameter( "reid" ); //���Ҫ�޸ĵ����뵥id

        try{
            //��õ�ǰ�û��ĵ�λ����
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ��ʵ�
                return forwardErrorPage( mapping, request, "partstockerror" );
            }

            String deptname = dao.getUserDeptName( userinfo ); //�����ⵥλ����
            request.setAttribute( "deptname", deptname );
            request.setAttribute( "deptid", userinfo.getDeptID() );
            request.setAttribute( "username", userinfo.getUserName() );
            request.setAttribute( "userid", userinfo.getUserID() );

            //��÷������ĵ�ǰʱ��
            Date nowDate = new Date();
            DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
            String data = df.format( nowDate );
            request.setAttribute( "date", data );

            //������뵥������Ϣ
            reqinfo = dao.getOneReq( reid );
            request.setAttribute( "reqinfo", reqinfo );

            //������뵥������Ĳ�����Ϣ
            PartStockDao stockdao = new PartStockDao();
            reqpartinfo = stockdao.getReqPartForStock( reid );
            request.setAttribute( "reqpartinfo", reqpartinfo );
            request.getSession().setAttribute( "type", "stock20" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "����ʾһ�����뵥����ϸ��Ϣ����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //ִ��������
    /**������:��һ�����뵥���ʱ,����������С����������,�����뵥Ҫ���������������º�ʵ,
     *        �ں�ʵ֮ǰ,�Ը����뵥���Լ������,
     * 		  �ں�ʵ֮��,Ҳ�����޸�����Ӧ����������֮��,�����뵥����������.
     * **/
    public ActionForward doStockPart( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80401" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStockDao stockDao = new PartStockDao();
            Part_requisitionBean bean = ( Part_requisitionBean )form;

            OracleIDImpl ora = new OracleIDImpl();
            String stockid = ora.getSeq( "Part_stock", 10 );
            bean.setStockid( stockid );

            String[] id = request.getParameterValues( "id" );
            String[] stocknumber = request.getParameterValues( "stocknumber" );
            //��д��ⵥ
            if( !stockDao.doAddStock( bean ) ){
                return forwardErrorPage( mapping, request, "error" );
            }
            //��д���_���϶�Ӧ��
            if( !stockDao.doAddStock_BaseForStock( id, stocknumber, bean.getReid(), bean.getStockid(),
                bean.getcontractorid() ) ){
                return forwardErrorPage( mapping, request, "error" );
            }
            log( request, "���Ϲ���", "����������" );
            return forwardInfoPage( mapping, request, "80402" );
        }
        catch( Exception e ){
            logger.error( "��ִ������������:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾ������ⵥ
    public ActionForward dokShowStockInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80402" )
        		|| CheckPower.checkPower( request.getSession(), "80403" )
            ||CheckPower.checkPower( request.getSession(), "80409" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );
        request.getSession().removeAttribute("bean");
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        //if( userinfo.getType().equals( "12" ) ){ //��������ƶ���˾�ǲ������
        if( userinfo.getDeptype().equals( "1" )
            &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //��������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStockDao stockDao = new PartStockDao();
            String contractorid=userinfo.getDeptID();
            List lStockInfo=null;
            if(request.getParameter("querytype")!=null
                &&request.getParameter("querytype").equals("1")){
                contractorid = request.getParameter( "contractorid" );
                request.getSession().setAttribute( "type", "showstock1" );
                lStockInfo = stockDao.getAllStock( request );
            }else{
                request.getSession().setAttribute( "type", "stock1" );
                lStockInfo = stockDao.getAllStock( contractorid );
            }

            request.getSession().setAttribute("querytype","1");
            request.getSession().setAttribute( "stockinfo", lStockInfo );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ��ʾ������ⵥ����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�鿴��ⵥ,��ʾһ����ⵥ����ϸ��Ϣ,
    public ActionForward doShowOneStock( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80402" )
        		|| CheckPower.checkPower( request.getSession(), "80403" )
            ||CheckPower.checkPower( request.getSession(), "80409" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );
        Part_requisitionBean stockinfo = new Part_requisitionBean(); //����ҳ�����ⵥ������Ϣ
        List lStockPart = null;
        PartStockDao stockDao = new PartStockDao();
        String stockid = request.getParameter( "stockid" ); //���Ҫ��ʾ����ⵥid

        try{
            //��õ�ǰ�û��ĵ�λ����
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            //if( userinfo.getType().equals( "12" ) ){ //��������ƶ���˾�ǲ������
            if( userinfo.getDeptype().equals( "1" )
                &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //��������ƶ���˾�ǲ������
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            stockinfo = stockDao.getOneStock( stockid ); //���һ����ⵥ�Ļ�����Ϣ
            lStockPart = stockDao.getStockPartInfo( stockid );
            request.setAttribute( "stockinfo", stockinfo );
            request.setAttribute( "stockpartinfo", lStockPart );

            request.getSession().setAttribute( "type", "stock10" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾһ����ⵥ����ϸ��Ϣ�г��ִ���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //����ۺϲ�ѯ��ʾ
    public ActionForward queryShowForSrock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80403" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStockDao stockdao = new PartStockDao();
            //������������
            List lUser = stockdao.getUsernameForStockQUery( userinfo.getDeptID() );
            request.setAttribute( "stockuser", lUser );
            //�������Ӧ���뵥���,����ԭ���б�
            List lReason_Reid = stockdao.getAllReidForStockQuery( userinfo.getDeptID() );
            request.setAttribute( "reqreid", lReason_Reid );

            request.getSession().setAttribute( "type", "stock3" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯ��ʾ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ⵥ�ۺϲ�ѯִ��
    public ActionForward queryExecForStock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80403" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );

        try{
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setContractorid( contractorid );
            PartStockDao stockDao = new PartStockDao();
            List lstockInfo = stockDao.getStockInfoForQuery( bean );

            request.getSession().setAttribute( "stockinfo", lstockInfo );
            request.getSession().setAttribute( "type", "stock1" );
            request.getSession().setAttribute( "bean", bean );
            super.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ⵥ�ۺϲ�ѯִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //////////////////////�²������ͳ��//////////////////
    //���ͳ�Ʋ�ѯ��ʾ
    public ActionForward queryShowForStat( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80404" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            String condition="";
            if(userinfo.getType().equals("11")){}
            if(userinfo.getType().equals("12")){
                condition+=" and regionid='"+userinfo.getRegionID()+"'";
            }
            if(userinfo.getType().equals("21")){
                condition+=" and con.contractorid in ("
                    +"         select contractorid from contractorinfo "
                    +"         where parentcontractorid='"+userinfo.getDeptID()+"' "
                    +"         or contractorid='"+userinfo.getDeptID()+"')";
            }
            if(userinfo.getType().equals("22")){
                condition+=" and con.contractorid='"+userinfo.getDeptID()+"'";
            }
            PartUseDao useDao = new PartUseDao();
            PartStockDao dao = new PartStockDao();
            //���ʹ�õ�λ�б�
            List lDept = dao.getDeptArrs( condition );
            //��ò��������б�
            PartBaseInfoDao baseDao = new PartBaseInfoDao();
            List lPartName = baseDao.getAllNames( condition );
            //��ò����ͺ��б�
            List lPartType = baseDao.getAllTypes( condition );
            //����������������б�
            List lFactory = baseDao.getAllFactorys( condition );
            //��ò�����;�б�
            List lReason = null;
            if( userinfo.getDeptype().equals( "2" ) ){ //�Ǵ�ά��λ,������ñ���˾����;�б�
                lReason = useDao.getReasonArr( contractorid );
            }
            else{ //���ƶ���˾,���������;�б�
                lReason = useDao.getAllReasonArr( userinfo.getRegionID() );
            }
            request.setAttribute( "deptinfo", lDept );
            request.setAttribute( "nameinfo", lPartName );
            request.setAttribute( "typeinfo", lPartType );
            request.setAttribute( "factoryinfo", lFactory );
            request.setAttribute( "usereason", lReason );

            request.setAttribute( "usedept", userinfo.getDeptype() );
            request.setAttribute( "usetype", userinfo.getType() );

            request.getSession().setAttribute( "type", "stock4" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "���ͳ�Ʋ�ѯ��ʾ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //ͳ�Ʋ�ѯִ��
    public ActionForward dostat( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80404" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setRegionid( userinfo.getRegionID() );
            PartStockDao dao = new PartStockDao();
            List lUseInfo = null;
            String condition="";
            if(userinfo.getType().equals("11")){}
            if(userinfo.getType().equals("12")){
                condition+=" and con.regionid='"+userinfo.getRegionID()+"'";
            }
            if(userinfo.getType().equals("21")){
                condition+=" and con.contractorid in ("
                    +"         select contractorid from contractorinfo "
                    +"         where parentcontractorid='"+userinfo.getDeptID()+"' "
                    +"         or contractorid='"+userinfo.getDeptID()+"')";
            }
            if(userinfo.getType().equals("22")){
                condition+=" and con.contractorid='"+userinfo.getDeptID()+"'";
            }
            //if( userinfo.getDeptype().equals( "1" ) ){ //���ƶ���˾�Ĳ�ѯ
            //    lUseInfo = dao.getAllPartUse( bean );
            //}
            //else{ //�Ǵ�ά��λ�Ĳ�ѯ
            //    lUseInfo = dao.getAllPartUse( bean, contractorid );
            //}
            lUseInfo=dao.getAllPartUse(condition,bean);
            request.getSession().setAttribute( "useinfo", lUseInfo );
            request.getSession().setAttribute( "type", "use70" );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "����ѯִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


//////////////////////////////////// For OldStock Action///////////////////////////////
    //������ɲ�����ʾ
    public ActionForward addOldShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80501" ) ){
            return mapping.findForward( "powererror" );
        }

        PartRequisitionDao dao = new PartRequisitionDao();
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ�������Ӳ��������
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

        request.getSession().setAttribute( "type", "old2" );
        return mapping.findForward( "success" );
    }


    //ִ��������ɲ�����ⵥ
    public ActionForward addOLdStock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80501" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStockDao stockDao = new PartStockDao();
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            OracleIDImpl ora = new OracleIDImpl();
            String oldid = ora.getSeq( "Part_oldstock", 10 );
            bean.setOldid( oldid );
            String[] id = request.getParameterValues( "id" );
            String[] oldnumber = request.getParameterValues( "oldnumber" );
            //�ж��Ƿ�û�����ɲ��ϵ����������һ�����ϵ�������û�У������
            boolean flag = true;
            for( int i = 0; i < oldnumber.length; i++ ){
                if( oldnumber[i].equals( "0" ) || ("".equals(id[i].trim()))) {
                    flag = false;
                    break;
                }
            }
            if( !flag ){
                return forwardErrorPage( mapping, request, "80502e" );
            }

            //д�������
            if( !stockDao.addOldStockInfo( bean, id, oldnumber ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "���Ϲ���", "������ɲ�����ⵥ" );
            return forwardInfoPage( mapping, request, "80502" );
        }
        catch( Exception e ){
            logger.error( "��������뵥�г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾ�������ɲ�����ⵥ
    public ActionForward showAllOldStock( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80502" )
        		|| CheckPower.checkPower( request.getSession(), "80503" )
            ||CheckPower.checkPower( request.getSession(), "80509" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        //if( userinfo.getType().equals( "12" ) ){ //��������ƶ���˾�ǲ������
        if( userinfo.getDeptype().equals( "1" )
            &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //��������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStockDao stockDao = new PartStockDao();
            String contractorid = userinfo.getDeptID();
            List lOldInfo=null;
            if(request.getParameter("querytype")!=null
                &&request.getParameter("querytype").equals("1")){
                contractorid = request.getParameter( "contractorid" );
                request.getSession().setAttribute( "type", "showold1" );
                lOldInfo = stockDao.getAllOldStock( request );
            }else{
                request.getSession().setAttribute( "type", "old1" );
                lOldInfo = stockDao.getAllOldStock( contractorid );
            }

            request.getSession().setAttribute( "oldinfo", lOldInfo );
            request.getSession().setAttribute("querytype","1");
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ���ϻ�����Ϣ����ʾ������Ϣ����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾһ�����ɲ�����ⵥ����ϸ��Ϣ
    public ActionForward showOneOfStock( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        boolean flag=false;
        if( CheckPower.checkPower( request.getSession(), "80502" )
        		|| CheckPower.checkPower( request.getSession(), "80503" )
            ||CheckPower.checkPower( request.getSession(), "80509" )){
            flag=true;
        }
        if(!flag)
            return mapping.findForward( "powererror" );

        Part_requisitionBean bean = ( Part_requisitionBean )form;
        Part_requisitionBean oldinfo = new Part_requisitionBean(); //����ҳ�����ⵥ������Ϣ
        List oldpartinfo = null;
        PartStockDao stockDao = new PartStockDao();
        String oldid = request.getParameter( "oldid" ); //���Ҫ�޸ĵ����뵥id

        try{
            //��õ�ǰ�û��ĵ�λ����
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            //if( userinfo.getType().equals( "12" ) ){ //��������ƶ���˾�ǲ������
            if( userinfo.getDeptype().equals( "1" )
                &&!userinfo.getRegionID().substring(2,6).equals("0000") ){ //��������ƶ���˾�ǲ������
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            //�����ⵥ������Ϣ
            oldinfo = stockDao.getOneOldStock( oldid );
            request.setAttribute( "oldinfo", oldinfo );

            //�����ⵥ�����Ĳ�����Ϣ
            oldpartinfo = stockDao.getPartOfOneOldStock( oldid );
            request.setAttribute( "oldpartinfo", oldpartinfo );
            request.getSession().setAttribute( "type", "old10" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "����ʾ��ϸ�г��ִ���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //���ɲ�������ۺϲ�ѯ��ʾ
    public ActionForward queryShowForOldSrock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80503" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            PartStockDao stockdao = new PartStockDao();
            //������������
            List lUser = stockdao.getOldStockUsername( userinfo.getDeptID() );
            request.setAttribute( "olduser", lUser );
            //������Դ�б�
            List lOldReason = stockdao.getOldStockOLdreason( userinfo.getDeptID() );
            request.setAttribute( "oldreason", lOldReason );

            request.getSession().setAttribute( "type", "old3" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯ��ʾ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //���ɲ�����ⵥ�ۺϲ�ѯִ��
    public ActionForward queryExecForOldStock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80503" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );
        try{
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setContractorid( contractorid );
            PartStockDao stockDao = new PartStockDao();
            List lOldInfo = stockDao.getOldStockInfoForQuery( bean );

            request.getSession().setAttribute( "oldinfo", lOldInfo );
            request.getSession().setAttribute( "type", "old1" );
            request.getSession().setAttribute( "bean", bean );
            super.setPageReset(request);
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "������ⵥ�ۺϲ�ѯִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //////////////////////���ɲ������ͳ��//////////////////
//�������ͳ�Ʋ�ѯ��ʾ
    public ActionForward queryShowForOldStat( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80504" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            String condition="";
            if(userinfo.getType().equals("11")){}
            if(userinfo.getType().equals("12")){
                condition+=" and regionid='"+userinfo.getRegionID()+"'";
            }
            if(userinfo.getType().equals("21")){
                condition+=" and con.contractorid in ("
                    +"         select contractorid from contractorinfo "
                    +"         where parentcontractorid='"+userinfo.getDeptID()+"' "
                    +"         or contractorid='"+userinfo.getDeptID()+"')";
            }
            if(userinfo.getType().equals("22")){
                condition+=" and con.contractorid='"+userinfo.getDeptID()+"'";
            }
            PartUseDao useDao = new PartUseDao();
            //���ʹ�õ�λ�б�
            List lDept = useDao.getDeptArrs( condition );
            //��ò��������б�
            PartBaseInfoDao baseDao = new PartBaseInfoDao();
            List lPartName = baseDao.getAllNames( condition );
            //��ò����ͺ��б�
            List lPartType = baseDao.getAllTypes( condition );
            //����������������б�
            List lFactory = baseDao.getAllFactorys( condition );
            //��ò�����;�б�
            List lReason = null;
            if( userinfo.getDeptype().equals( "2" ) ){ //�Ǵ�ά��λ,������ñ���˾����;�б�
                lReason = useDao.getReasonArr( contractorid );
            }
            else{ //���ƶ���˾,���������;�б�
                lReason = useDao.getAllReasonArr( userinfo.getRegionID() );
            }
            request.setAttribute( "deptinfo", lDept );
            request.setAttribute( "nameinfo", lPartName );
            request.setAttribute( "typeinfo", lPartType );
            request.setAttribute( "factoryinfo", lFactory );
            request.setAttribute( "usereason", lReason );

            request.setAttribute( "usedept", userinfo.getDeptype() );
            request.setAttribute( "usetype", userinfo.getType() );

            request.getSession().setAttribute( "type", "old4" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�������ͳ�Ʋ�ѯ��ʾ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


//ͳ�Ʋ�ѯִ��
    public ActionForward dostatOld( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "80504" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setRegionid( userinfo.getRegionID() );
            PartStockDao dao = new PartStockDao();
            List lUseInfo = null;
            String condition="";
            if(userinfo.getType().equals("11")){}
            if(userinfo.getType().equals("12")){
                condition+=" and con.regionid='"+userinfo.getRegionID()+"'";
            }
            if(userinfo.getType().equals("21")){
                condition+=" and con.contractorid in ("
                    +"         select contractorid from contractorinfo "
                    +"         where parentcontractorid='"+userinfo.getDeptID()+"' "
                    +"         or contractorid='"+userinfo.getDeptID()+"')";
            }
            if(userinfo.getType().equals("22")){
                condition+=" and con.contractorid='"+userinfo.getDeptID()+"'";
            }
            //if( userinfo.getDeptype().equals( "1" ) ){ //���ƶ���˾�Ĳ�ѯ
            //    lUseInfo = dao.getAllPartUseOld( bean );
            //}
            //else{ //�Ǵ�ά��λ�Ĳ�ѯ
            //    lUseInfo = dao.getAllPartUseOld( bean, contractorid );
            //}
            lUseInfo=dao.getAllPartUseOld(condition,bean);
            request.getSession().setAttribute( "useinfo", lUseInfo );
            request.getSession().setAttribute( "type", "old40" );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "��������ѯִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportStockResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "��ò�ѯ����bean������" );

                logger.info( "id��" + bean.getStockuserid() );
                logger.info( "��Ӧ���뵥��ţ�" + bean.getReid() );
                logger.info( "��ʼʱ�䣺" + bean.getBegintime() );
                logger.info( "����ʱ�䣺" + bean.getEndtime() );
                if( !bean.getStockuserid().equals( "" ) ){
                    String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getStockuserid() + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setUsername( rs.getString( 1 ) );
                    }
                    logger.info( "�����������" + bean.getUsername() );
                }
                request.getSession().removeAttribute( "bean" );
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "stockinfo" );
            logger.info( "�õ�list" );
            dao.exportStockResult( list, bean, response );
            logger.info( "���excel�ɹ�" );

            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportOldStockResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "��ò�ѯ����bean������" );

                logger.info( "id��" + bean.getOlduserid() );
                logger.info( "������Դ��" + bean.getReid() );
                logger.info( "��ʼʱ�䣺" + bean.getBegintime() );
                logger.info( "����ʱ�䣺" + bean.getEndtime() );
                if( !bean.getOlduserid().equals( "" ) ){
                    String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getOlduserid() + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setUsername( rs.getString( 1 ) );
                    }
                    logger.info( "�����������" + bean.getUsername() );
                }
                request.getSession().removeAttribute( "bean" );
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "oldinfo" );
            logger.info( "�õ�list" );
            dao.exportOldStockResult( list, bean, response );
            logger.info( "���excel�ɹ�" );

            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //����ά��������ⵥ��ϸ��Ϣһ����
    public ActionForward exportStorkList( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
//        if( !CheckPower.checkPower( request.getSession(), "80403" ) ){
//            return mapping.findForward( "powererror" );
//        }
//        //��õ�ǰ�û��ĵ�λ����
//        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
//                            "LOGIN_USER" );
//        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
//            return forwardErrorPage( mapping, request, "partstockerror" );
//        }

        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );

        try{

            String stockuserid = request.getParameter( "stockuserid" ); ;
            String reid = request.getParameter( "reid" );
            String begintime = request.getParameter( "begintime" );
            String endtime = request.getParameter( "endtime" );

            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setContractorid( contractorid );
            bean.setStockuserid( stockuserid );
            bean.setReid( reid );
            bean.setBegintime( begintime );
            bean.setEndtime( endtime );

            if( stockuserid != null ){
                String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getStockuserid() + "'";
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setUsername( rs.getString( 1 ) );
                }
                logger.info( "�����������" + bean.getUsername() );
            }
            PartStockDao stockDao = new PartStockDao();
            List list = stockDao.getStockList( bean );

            PartExportDao dao = new PartExportDao();
            dao.exportStockList( list, bean, response );

            return null;

        }
        catch( Exception e ){
            logger.error( "������Ϣ�����쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�������ɲ�����ⵥ��ϸ��Ϣһ����
    public ActionForward exportOldUseList( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
//        if( !CheckPower.checkPower( request.getSession(), "80403" ) ){
//            return mapping.findForward( "powererror" );
//        }
//        //��õ�ǰ�û��ĵ�λ����
//        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
//                            "LOGIN_USER" );
//        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
//            return forwardErrorPage( mapping, request, "partstockerror" );
//        }

        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );

        try{

            String olduserid = request.getParameter( "olduserid" ); ;
            String reid = request.getParameter( "reid" );
            String begintime = request.getParameter( "begintime" );
            String endtime = request.getParameter( "endtime" );

            Part_requisitionBean bean = ( Part_requisitionBean )form;
            bean.setContractorid( contractorid );
            bean.setOlduserid( olduserid );
            bean.setReid( reid );
            bean.setBegintime( begintime );
            bean.setEndtime( endtime );
            if( olduserid != null ){
                String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getOlduserid() + "'";
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setUsername( rs.getString( 1 ) );
                }
                logger.info( "�����������" + bean.getUsername() );
            }
            PartStockDao stockDao = new PartStockDao();
            List list = stockDao.getOldUseList( bean );

            PartExportDao dao = new PartExportDao();
            dao.exportOldUseList( list, bean, response );

            return null;

        }
        catch( Exception e ){
            logger.error( "������Ϣ�����쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * ��ѯ�����²������ı�
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryAllStock(ActionMapping mapping, ActionForm form,
        HttpServletRequest request,HttpServletResponse response)
        throws ClientException, Exception{
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );

        List regionList=null;
        List deptList=null;
        List conList=null;
        form = null;
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

        return mapping.findForward("queryallstock");
    }

    /**
     * ��ѯ�������ɲ������ı�
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryAllOldStock(ActionMapping mapping, ActionForm form,
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

        return mapping.findForward("queryalloldstock");
    }

    public ActionForward exportStock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "��ò�ѯ����bean������" );
                logger.info( "�������ƣ�" + bean.getName());
                logger.info( "�����ͺţ�" + bean.getType() );
                logger.info( "��ʼʱ�䣺" + bean.getBegintime() );
                logger.info( "����ʱ�䣺" + bean.getEndtime() );
                if( !bean.getcontractorid().equals( "" ) ){
                    String sql =
                        "select c.CONTRACTORNAME from contractorinfo c where c.CONTRACTORID = '" + bean.getcontractorid()
                        + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setContractorname( rs.getString( 1 ) );
                    }
                    logger.info( "��ⵥλ��" + bean.getContractorname() );
                }
                request.getSession().removeAttribute("bean");
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "useinfo" );
            logger.info( "�õ�list" );
            dao.ExportStock( list, bean, response );
            logger.info( "���excel�ɹ�" );

            return null;
        }
        catch( Exception e ){
            logger.error( "��������ʹ��һ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    public ActionForward exportOldStock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            Part_requisitionBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( Part_requisitionBean )request.getSession().getAttribute( "bean" );
                logger.info( "��ò�ѯ����bean������" );
                logger.info( "�������ƣ�" + bean.getName());
                logger.info( "�����ͺţ�" + bean.getType() );
                logger.info( "��ʼʱ�䣺" + bean.getBegintime() );
                logger.info( "����ʱ�䣺" + bean.getEndtime() );
                if( !bean.getcontractorid().equals( "" ) ){
                    String sql =
                        "select c.CONTRACTORNAME from contractorinfo c where c.CONTRACTORID = '" + bean.getcontractorid()
                        + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setContractorname( rs.getString( 1 ) );
                    }
                    logger.info( "ʹ�õ�λ��" + bean.getContractorname() );
                }
                request.getSession().removeAttribute("bean");
            }
            PartExportDao dao = new PartExportDao();
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "useinfo" );
            logger.info( "�õ�list" );
            dao.ExportOldStock( list, bean, response );
            logger.info( "���excel�ɹ�" );

            return null;
        }
        catch( Exception e ){
            logger.error( "��������ʹ��һ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
