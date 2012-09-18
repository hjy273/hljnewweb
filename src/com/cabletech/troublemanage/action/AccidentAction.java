package com.cabletech.troublemanage.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.apache.struts.upload.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.generatorID.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.web.*;
import com.cabletech.troublemanage.beans.*;
import com.cabletech.uploadfile.*;
import com.cabletech.utils.*;

public class AccidentAction extends AccidentInfoBaseDispatchAction{
    private static Logger logger = Logger.getLogger( "AccidentAction" );
    /**
     * ����������¹��б�
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadUndoenAccident( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String regionid = request.getParameter( "regionid" );
        UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String userregionid = userInfo.getRegionID();
        String deptid = userInfo.getDeptID();
        String type = userInfo.getDeptype();

        //2005-10-08
        request.getSession().setAttribute( "selectedRegion", regionid );
        //2005-10-08

        String sql = " select a.KEYID id, to_char(a.SENDTIME,'yy/mm/dd hh24:mi:ss') sendtime, "
                     +"nvl(b.pointname,'') point, nvl(c.sublinename,'') subline, "
                     +"nvl(f.operationdes,'') reason, "
                     +"nvl(decode(f.emergencylevel,"
                     +"           '1','��΢',"
                     +"           '2','�ж�',"
                     +"           '3','����',"
                     +"           '��΢'),"
                     +"           '') emlevel, nvl(e.ContractorName,'') contractor "
                     +"from ACCIDENT a, pointinfo b, sublineinfo c, contractorinfo e, "
                     +"table(cast(getAllTroubleCodes as tabletypes)) f, patrolmaninfo g ";
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
        sqlBuild.addConstant( " a.pid = b.pointid(+) and a.lid = c.sublineid(+) and a.patrolid = g.patrolid(+) and g.parentid = e.contractorid(+) and a.operationcode = f.operationcode(+) " );
        sqlBuild.addConstant( " and a.status = '0' " );
        sqlBuild.addAnd();

        sqlBuild.addConstant( "a.type='1'" );
        //zsh 20050803 added
        sqlBuild.addTableRegion( "a.regionid", regionid );
        //end zsh 20050803 added
        if( type.equals( "2" ) || type == "2" ){
            sqlBuild.addConstant( " and e.CONTRACTORID='" + deptid + "'" );

        }

        logger.info( "sql --> " + sqlBuild.toSql() );
        List list = super.getDbService().queryBeans( sqlBuild.toSql() );

        request.getSession().setAttribute( "queryresult", list );
        return mapping.findForward( "unDoneAccidentListpage" );
    }
    /**
      * ���й����б�
     */

     public ActionForward loadAllAccident( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String regionid = request.getParameter( "regionid" );
        UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String userregionid = userInfo.getRegionID();
        String deptid = userInfo.getDeptID();
        String type = userInfo.getDeptype();
        request.getSession().setAttribute( "selectedRegion", regionid );
        String sql = "SELECT a.keyid ID, TO_CHAR (a.sendtime, 'yy/mm/dd hh24:mi:ss') sendtime,"
                    + "        NVL (b.pointname, '') point, NVL (c.sublinename, '') subline,"
                    + "        NVL (f.operationdes, '') reason,"
                    + "        NVL (DECODE (f.emergencylevel,"
                    + "                     '1', '��΢',"
                    + "                     '2', '�ж�',"
                    + "                     '3', '����',"
                    + "                     '��΢'"
                    + "                    ),"
                    + "             ''"
                    + "            ) emlevel,"
                    + "        NVL (e.contractorname, '') contractor,"
                    + "        NVL (DECODE (a.STATUS,"
                    + "                     '0', '������',"
                    + "                     '2', '������',"
                    + "                     '3', '�Ѵ���',"
                    + " 					'9', '�Ѻ���'"
                    + "                    ),"
                    + "             '  '"
                    + "            ) status"
                    + "   FROM ACCIDENT a,"
                    + "        POINTINFO b,"
                    + "        SUBLINEINFO c,"
                    + "        CONTRACTORINFO e,"
                    + "        table(cast(getAllTroubleCodes as tabletypes)) f,"
                    + "       PATROLMANINFO g"
                    + "  WHERE a.pid = b.pointid(+)"
                    + "    AND a.lid = c.sublineid(+)"
                    + "    AND a.patrolid = g.patrolid(+)"
                    + "    AND g.parentid = e.contractorid(+)"
                    + "    AND a.operationcode = f.operationcode(+)"
                    + "    AND a.TYPE = '1'";
        if(userInfo.getDeptype().equals("1")){
             sql +=  "        AND( a.regionid IN( SELECT regionid"
                     + "                FROM REGION"
                     + "                CONNECT BY PRIOR regionid = parentregionid"
                     + "                                           START WITH regionid = '" +  regionid + "') )";

        }else{
            sql += " and e.CONTRACTORID='" + userInfo.getDeptID() + "' ";
        }
        sql += " ORDER BY e.contractorname DESC, a.status, a.sendtime DESC";
        logger.info( "sql --> " + sql );
        List list = super.getDbService().queryBeans( sql );
        request.getSession().setAttribute( "queryresult", list );
        return mapping.findForward( "allAccidentListpage" );
   }

    /**
     * ���������б�
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */

    public ActionForward loadAllTrouble( ActionMapping mapping,
       ActionForm form,
       HttpServletRequest request,
       HttpServletResponse response ) throws
       ClientException, Exception{

       String regionid = request.getParameter( "regionid" );
       UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
       String userregionid = userInfo.getRegionID();
       String deptid = userInfo.getDeptID();
       String type = userInfo.getDeptype();
       request.getSession().setAttribute( "selectedRegion", regionid );
       String sql = "SELECT   a.keyid ID, TO_CHAR (a.sendtime, 'yy/mm/dd hh24:mi:ss') sendtime,"
                    + "          NVL (b.pointname, '') point, NVL (c.sublinename, '') subline,"
                    + "          NVL (f.operationdes, '') reason,"
                    + "          NVL (DECODE (f.emergencylevel,"
                    + "                       '1', '��΢',"
                    + "                       '2', '�ж�',"
                    + "                       '3', '����',"
                    + "                       '��΢'"
                    + "                      ),"
                    + "               ''"
                    + "              ) emlevel,"
                    + "          NVL (e.contractorname, '') contractor,"
                    + "          NVL (DECODE (a.STATUS, '0', '������',"
                    + "                       '2', '������',"
                    + "                       '3', '�Ѵ���',"
                    + "                       '9','�Ѻ���'"
                    + "                      ),"
                    + "               ''"
                    + "              ) status"

                    + "     FROM ACCIDENT a,"
                    + "          POINTINFO b,"
                    + "          SUBLINEINFO c,"
                    + "          CONTRACTORINFO e,"
                    + "          table(cast(getAllTroubleCodes as tabletypes)) f,"
                    + "          PATROLMANINFO g"
                    + "    WHERE a.pid = b.pointid(+)"
                    + "      AND a.lid = c.sublineid(+)"
                    + "      AND a.patrolid = g.patrolid(+)"
                    + "      AND g.parentid = e.contractorid(+)"
                    + "      AND a.operationcode = f.operationcode(+)";
                    //+ "      AND a.TYPE = '0'";
       if(userInfo.getDeptype().equals("1")){
            sql +=  "        AND( a.regionid IN( SELECT regionid"
                    + "                FROM REGION"
                    + "                CONNECT BY PRIOR regionid = parentregionid"
                    + "                                           START WITH regionid = '" +  regionid + "') )";

       }else{
           sql += " and e.CONTRACTORID='" + userInfo.getDeptID() + "' ";
       }
       sql += " ORDER BY e.contractorname DESC, a.status, a.sendtime DESC";
       logger.info( "sql --> " + sql );
       List list = super.getDbService().queryBeans( sql );
       request.getSession().setAttribute( "queryresult", list );
       return mapping.findForward( "allTroubleListpage" );
   }

       public ActionForward loadAllDoingAccident( ActionMapping mapping,
       ActionForm form,
       HttpServletRequest request,
       HttpServletResponse response ) throws
       ClientException, Exception{

       String regionid = request.getParameter( "regionid" );
       UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
       String userregionid = userInfo.getRegionID();
       String deptid = userInfo.getDeptID();
       String type = userInfo.getDeptype();
       request.getSession().setAttribute( "selectedRegion", regionid );
       String sql = "SELECT a.keyid ID, TO_CHAR (a.sendtime, 'yy/mm/dd hh24:mi:ss') sendtime,"
                   + "        NVL (b.pointname, '') point, NVL (c.sublinename, '') subline,"
                   + "        NVL (f.operationdes, '') reason,"
                   + "        NVL (DECODE (f.emergencylevel,"
                   + "                     '1', '��΢',"
                   + "                     '2', '�ж�',"
                   + "                     '3', '����',"
                   + "                     '��΢'"
                   + "                    ),"
                   + "             ''"
                   + "            ) emlevel,"
                   + "        NVL (e.contractorname, '') contractor,"
                   + "        NVL (DECODE (a.STATUS,"
                   + "                     '0', '������',"
                   + "                     '2', '������',"
                   + "                     '3', '�Ѵ���'"
                   + "                    ),"
                   + "             '  '"
                   + "            ) status"
                   + "   FROM ACCIDENT a,"
                   + "        POINTINFO b,"
                   + "        SUBLINEINFO c,"
                   + "        CONTRACTORINFO e,"
                   + "        table(cast(getAllTroubleCodes as tabletypes)) f,"
                   + "       PATROLMANINFO g"
                   + "  WHERE a.pid = b.pointid(+)"
                   + "    AND a.lid = c.sublineid(+)"
                   + "    AND a.patrolid = g.patrolid(+)"
                   + "    AND g.parentid = e.contractorid(+)"
                   + "    AND a.operationcode = f.operationcode(+)"
                   + "    AND a.TYPE = '1'"
                   + "	  AND a.status = '2'";
       if(userInfo.getDeptype().equals("1")){
            sql +=  "        AND( a.regionid IN( SELECT regionid"
                    + "                FROM REGION"
                    + "                CONNECT BY PRIOR regionid = parentregionid"
                    + "                                           START WITH regionid = '" +  regionid + "') )";

       }else{
           sql += " and e.CONTRACTORID='" + userInfo.getDeptID() + "' ";
       }
       sql += " ORDER BY e.contractorname DESC, a.status, a.sendtime DESC";
       logger.info( "sql --> " + sql );
       List list = super.getDbService().queryBeans( sql );
       request.getSession().setAttribute( "queryresult", list );
       return mapping.findForward( "allAccidentListpage" );
  }

   /**
    * ���д����е������б�
    * @param mapping ActionMapping
    * @param form ActionForm
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    * @return ActionForward
    * @throws ClientException
    * @throws Exception
    */

   public ActionForward loadAllDoingTrouble( ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response ) throws
      ClientException, Exception{

      String regionid = request.getParameter( "regionid" );
      UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
      String userregionid = userInfo.getRegionID();
      String deptid = userInfo.getDeptID();
      String type = userInfo.getDeptype();
      request.getSession().setAttribute( "selectedRegion", regionid );
      String sql = "SELECT   a.keyid ID, TO_CHAR (a.sendtime, 'yy/mm/dd hh24:mi:ss') sendtime,"
                   + "          NVL (b.pointname, '') point, NVL (c.sublinename, '') subline,"
                   + "          NVL (f.operationdes, '') reason,"
                   + "          NVL (DECODE (f.emergencylevel,"
                   + "                       '1', '��΢',"
                   + "                       '2', '�ж�',"
                   + "                       '3', '����',"
                   + "                       '��΢'"
                   + "                      ),"
                   + "               ''"
                   + "              ) emlevel,"
                   + "          NVL (e.contractorname, '') contractor,"
                   + "          NVL (DECODE (a.STATUS, '0', '������',"
                   + "                       '2', '������',"
                   + "                       '3', '�Ѵ���',"
                   + "                       '9','�Ѻ���'"
                   + "                      ),"
                   + "               ''"
                   + "              ) status"

                   + "     FROM ACCIDENT a,"
                   + "          POINTINFO b,"
                   + "          SUBLINEINFO c,"
                   + "          CONTRACTORINFO e,"
                   + "          table(cast(getAllTroubleCodes as tabletypes)) f,"
                   + "          PATROLMANINFO g"
                   + "    WHERE a.pid = b.pointid(+)"
                   + "      AND a.lid = c.sublineid(+)"
                   + "      AND a.patrolid = g.patrolid(+)"
                   + "      AND g.parentid = e.contractorid(+)"
                   + "      AND a.operationcode = f.operationcode(+)"
             //      + "      AND a.TYPE = '0'"
                   + "      AND a.status = '2'";
      if(userInfo.getDeptype().equals("1")){
           sql +=  "        AND( a.regionid IN( SELECT regionid"
                   + "                FROM REGION"
                   + "                CONNECT BY PRIOR regionid = parentregionid"
                   + "                                           START WITH regionid = '" +  regionid + "') )";

      }else{
          sql += " and e.CONTRACTORID='" + userInfo.getDeptID() + "' ";
      }
      sql += " ORDER BY e.contractorname DESC, a.status, a.sendtime DESC";
      logger.info( "sql --> " + sql );
      List list = super.getDbService().queryBeans( sql );
      request.getSession().setAttribute( "queryresult", list );
      return mapping.findForward( "allTroubleListpage" );
  }

    /**
     * �����������б�
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadUndoenTrouble( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String regionid = request.getParameter( "regionid" );
        UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String userregionid = userInfo.getRegionID();
        String deptid = userInfo.getDeptID();
        String type = userInfo.getDeptype();

        //2005-10-08
        request.getSession().setAttribute( "selectedRegion", regionid );
        //2005-10-08

        String sql = " select a.KEYID id, to_char(a.SENDTIME,'yy/mm/dd hh24:mi:ss') sendtime, "
                     +"nvl(b.pointname,'') point, nvl(c.sublinename,'') subline, "
                     +"nvl(f.operationdes,'') reason, "
                     +"nvl(decode(f.emergencylevel,"
                     +"           '1','��΢',"
                     +"           '2','�ж�',"
                     +"           '3','����',"
                     +"           '��΢'),"
                     +"           '') emlevel, nvl(e.ContractorName,'') contractor "
                     + "from ACCIDENT a, pointinfo b, sublineinfo c, contractorinfo e, "
                     +"table(cast(getAllTroubleCodes as tabletypes)) f, patrolmaninfo g ";
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
        sqlBuild.addConstant( " a.pid = b.pointid(+) and a.lid = c.sublineid(+) and a.patrolid = g.patrolid(+) and g.parentid = e.contractorid(+) and a.operationcode = f.operationcode(+) " );
        sqlBuild.addConstant( " and a.status = '0' " );
        //sqlBuild.addConstant( " and a.type = '0' " );
        //zsh 20050803 added
        sqlBuild.addTableRegion( "a.regionid", regionid );
        //end zsh 20050803 added
        if( type.equals( "2" ) || type == "2" ){
            sqlBuild.addConstant( " and e.CONTRACTORID='" + deptid + "'" );
        }
        sqlBuild.addConstant( " order by f.emergencylevel,sendtime desc" );
        logger.info( "sql --> " + sqlBuild.toSql() );
        List list = super.getDbService().queryBeans( sqlBuild.toSql() );

        request.getSession().setAttribute( "queryresult", list );
        return mapping.findForward( "unDoneTroubleListpage" );
    }


    /**
     * ����һ���¹��ɷ�֪ͨ��
     * statusFlag ��ֵΪ 0,2,3 ;����0 :�·�֪ͨ 2:��д�¹ʻ������������, 3:�鿴��������������Ϣ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadAccident( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String id = request.getParameter( "id" );
        String statusFlag = request.getParameter( "statusFlag" );
        int iFlag = Integer.parseInt( statusFlag );

        AccidentBean bean = new AccidentBean();

        String sql = " select nvl(a.RESONANDFIX,'  ') resonandfix, a.BREPORTMAN breportman, "
                     +"a.BCONFIRMMAN bconfirmman, a.year year, a.month month, "
                     +"to_char(a.TESTTIME,'yyyy/mm/dd hh24:mi:ss') testtime, "
                     +"nvl(a.TESTMAN,'') testman , nvl(a.DISTANCE,'0') distance, "
                     +"nvl(a.REALDISTANCE, '0') realdistance, nvl(a.FIXEDMAN,'') fixedman, "
                     +"nvl(a.MONITOR, '') monitor, nvl(a.COMMANDER, '') commander, "
                     +"to_char(a.NOTICETIME,'yyyy/mm/dd hh24:mi:ss') noticetime, "
                     +"to_char(a.BEFORTIME,'yyyy/mm/dd hh24:mi:ss') befortime, "
                     +"nvl(a.COOPERATEMAN,'') cooperateman,to_char(a.SENDTIME,'yyyy/mm/dd hh24:mi:ss') senddate, "
                     +"b.pointname point, c.sublinename subline, nvl(f.operationdes,'') reason, d.regionname region ,"
                     +" e.ContractorName contractor,a.datumid datumid "
                     +"from ACCIDENT a, pointinfo b, sublineinfo c, region d, "
                     +"contractorinfo e, table(cast(getAllTroubleCodes as tabletypes)) f, patrolmaninfo g ";
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
        sqlBuild.addConstant( " a.pid = b.pointid(+) and a.lid = c.sublineid(+) and a.regionid = d.regionid(+) and a.patrolid = g.patrolid(+) and g.parentid = e.contractorid(+) and a.operationcode = f.operationcode(+) " );
        sqlBuild.addConstant( " and a.keyid = '" + id + "' " );
        sqlBuild.addAnd();
        sqlBuild.addConstant( "a.type='1'" );
//System.out.println( "SQL:" + sqlBuild.toSql());
        List list = super.getDbService().queryBeans( sqlBuild.toSql() );

        bean.setKeyid( id );
        if( list.size() > 0 ){
            BasicDynaBean innerbean = ( BasicDynaBean )list.get( 0 );

            bean.setYear( ( String )innerbean.get( "year" ) );
            bean.setMonth( ( String )innerbean.get( "month" ) );
            bean.setBreportman( ( String )innerbean.get( "breportman" ) );
            bean.setBconfirmman( ( String )innerbean.get( "bconfirmman" ) );
            bean.setSendtime( ( String )innerbean.get( "senddate" ) );
            bean.setTesttime( ( String )innerbean.get( "testtime" ) );
            bean.setTestman( ( String )innerbean.get( "testman" ) );
            bean.setDistance( ( String )innerbean.get( "distance" ) );
            bean.setRealdistance( ( String )innerbean.get( "realdistance" ) );
            bean.setFixedman( ( String )innerbean.get( "fixedman" ) );
            bean.setMonitor( ( String )innerbean.get( "monitor" ) );
            bean.setCommander( ( String )innerbean.get( "commander" ) );
            bean.setNoticetime( ( String )innerbean.get( "noticetime" ) );
            bean.setBefortime( ( String )innerbean.get( "befortime" ) );
            bean.setCooperateman( ( String )innerbean.get( "cooperateman" ) );
            bean.setPid( ( String )innerbean.get( "point" ) );
            bean.setLid( ( String )innerbean.get( "subline" ) );
            bean.setResonandfix( ( String )innerbean.get( "reason" ) ) ;
            bean.setContractorid( ( String )innerbean.get( "contractor" ) );
            bean.setDatumid( ( String )innerbean.get( "datumid" ) );
        }

        //if (bean.getResonandfix().equals("null") || bean.getResonandfix() == null) {
        //    bean.setResonandfix(" ");
        //}

        //logger.info("::::::::::::::::" + bean.getResonandfix());

        String forwardPageName = "sendWorkForm"; //�·�֪ͨ��

        if( iFlag == 1 ){
            forwardPageName = "acceptWorkForm"; //����֪ͨ��
        }
        else{
            if( iFlag == 2 ){
                forwardPageName = "completeWorkForm"; // ���������д�¹�ά����
            }
            else{
                if( iFlag == 3 ){
                    forwardPageName = "viewWorkForm"; //�鿴�¹�ά����

                    Vector detailVct = getDetailVct( id );
                    request.getSession().setAttribute( "tasklist", detailVct );
                    bean.setDetailvct( detailVct );
                }
            }
        }

        request.setAttribute( "AccidentBean", bean );
        HttpSession session = request.getSession();
        session.setAttribute( "AccidentBean", bean );

        return mapping.findForward( forwardPageName );
    }


    /**
     * ����һ�������ɷ�֪ͨ��
     * statusFlag ��ֵΪ 0,2,3 ;����0 :�·�֪ͨ 2:��д�¹ʻ������������, 3:�鿴��������������Ϣ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadTrouble( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String id = request.getParameter( "id" );
        HttpSession session = request.getSession();
        String statusFlag = request.getParameter( "statusFlag" );
        UserInfo userinfo = ( UserInfo )session.getAttribute( "LOGIN_USER" );
        int iFlag = Integer.parseInt( statusFlag );
        AccidentBean bean = new AccidentBean();
        String sql = " select a.whosend,decode(a.sendto,null,'',h.patrolname) sendto, nvl(a.resonandfix,'') resonandfix, "
                     + " a.BREPORTMAN breportman, a.BCONFIRMMAN bconfirmman, a.year year,"
                     + " a.month month, to_char(a.TESTTIME,'yyyy/mm/dd hh24:mi:ss') testtime,to_char(a.reporttime,'yyyy/mm/dd hh24:mi:ss') reporttime, "
                     + " nvl(a.TESTMAN,'') testman , nvl(a.DISTANCE,'0') distance, nvl(a.REALDISTANCE, '0') realdistance, nvl(a.FIXEDMAN,'') fixedman, nvl(a.MONITOR, '') monitor, nvl(a.COMMANDER, '') commander, to_char(a.NOTICETIME,'yyyy/mm/dd hh24:mi:ss') noticetime, to_char(a.BEFORTIME,'yyyy/mm/dd hh24:mi:ss') befortime, nvl(a.COOPERATEMAN,'') cooperateman,  to_char(a.SENDTIME,'yyyy/mm/dd hh24:mi:ss') senddate, b.pointname point, c.sublinename subline, nvl(f.operationdes,'') reason, d.regionname region , e.ContractorName contractor,a.datumid datumid "
                     +"from ACCIDENT a, pointinfo b, sublineinfo c, region d, contractorinfo e, "
                     +"table(cast(getAllTroubleCodes as tabletypes)) f, patrolmaninfo g,"
                     +"(select * from patrolmaninfo where regionid='" +userinfo.getRegionID() + "') h";
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
        sqlBuild.addConstant( " a.pid = b.pointid(+) and a.lid = c.sublineid(+) and a.regionid = d.regionid(+) and a.patrolid = g.patrolid(+) and g.parentid = e.contractorid(+) and a.operationcode = f.operationcode(+) " );
        sqlBuild.addConstant( " and a.keyid = '" + id + "' " );
        sqlBuild.addConstant( " and a.sendto = h.patrolid(+)" );
        //sqlBuild.addAnd();
       // sqlBuild.addConstant( "a.type='0'" );
        logger.info("sql:" + sqlBuild.toSql());
        List list = super.getDbService().queryBeans( sqlBuild.toSql() );
        
        bean.setKeyid( id );
        if( list.size() > 0 ){
            BasicDynaBean innerbean = ( BasicDynaBean )list.get( 0 );
            bean.setYear( ( String )innerbean.get( "year" ) );
            bean.setMonth( ( String )innerbean.get( "month" ) );
            bean.setBreportman( ( String )innerbean.get( "breportman" ) );
            bean.setBconfirmman( ( String )innerbean.get( "bconfirmman" ) );
            bean.setSendtime( ( String )innerbean.get( "senddate" ) );
            bean.setTesttime( ( String )innerbean.get( "testtime" ) );
            bean.setTestman( ( String )innerbean.get( "testman" ) );
            bean.setDistance( ( String )innerbean.get( "distance" ) );
            bean.setRealdistance( ( String )innerbean.get( "realdistance" ) );
            bean.setFixedman( ( String )innerbean.get( "fixedman" ) );
            bean.setMonitor( ( String )innerbean.get( "monitor" ) );
            bean.setCommander( ( String )innerbean.get( "commander" ) );
            bean.setNoticetime( ( String )innerbean.get( "noticetime" ) );
            bean.setBefortime( ( String )innerbean.get( "befortime" ) );
            bean.setCooperateman( ( String )innerbean.get( "cooperateman" ) );
            bean.setPid( ( String )innerbean.get( "point" ) );
            bean.setLid( ( String )innerbean.get( "subline" ) );
            bean.setDatumid( ( String )innerbean.get( "datumid" ) );
            bean.setReprottime((String)innerbean.get( "reporttime" ));

            if( ( String )innerbean.get( "resonandfix" ) == null ||
                ( ( String )innerbean.get( "resonandfix" ) ).length() == 0 ){
                bean.setResonandfix( changeNullStr( ( String )innerbean.get( "reason" ) ) );

            }
            else{
                bean.setResonandfix( changeNullStr( ( String )innerbean.get( "resonandfix" ) ) );
            }

            //logger.info("::::::::::::::::" + bean.getResonandfix());

            //if (bean.getResonandfix().equals("null") || bean.getResonandfix() == null) {
            //    bean.setResonandfix(" ");
            //}

            bean.setContractorid( ( String )innerbean.get( "contractor" ) );
            bean.setWhosend( ( String )innerbean.get( "whosend" ) );
            bean.setSendto( ( String )innerbean.get( "sendto" ) );
            //bean.setSendtime((String) innerbean.get("sendtime"));
        }

        String forwardPageName = "sendTroubleForm"; //�·�֪ͨ��

        if( iFlag == 1 ){
            forwardPageName = "acceptTroubleForm"; //����֪ͨ��
        }
        else{
            if( iFlag == 2 ){
                forwardPageName = "completeTroubleForm"; // ���������д�¹�ά����
            }
            else{
                if( iFlag == 3 ){
                    forwardPageName = "viewTroubleForm"; //�鿴�¹�ά����

                    Vector detailVct = getDetailVct( id );
                    request.getSession().setAttribute( "tasklist", detailVct );
                    bean.setDetailvct( detailVct );
                }
            }
        }

        request.setAttribute( "AccidentBean", bean );
        session.setAttribute( "AccidentBean", bean );
        return mapping.findForward( forwardPageName );
    }


    /**
     * ���ַ���Ϊnullֵ��ת��Ϊ""
     * @param str String
     * @return String
     */
    public String changeNullStr( String str ){
        if( str == null ){
            str = "";
        }
        if( str.equalsIgnoreCase( "null" ) ){
            str = "";
        }
        return str;
    }


    /**
     * ��ȡ���ϻ�������Ϣ
     * @param id String
     * @return Vector
     * @throws Exception
     */
    public Vector getDetailVct( String id ) throws Exception{
        Vector deatailVct = new Vector();
        String sqlStr = "select id, accidentid, corecode, "
                        +"      to_char(tempfixedtime,'yyyy/mm/dd hh24:mi:ss') tempfixedtime, "
                        +"      to_char(fixedtime,'yyyy/mm/dd hh24:mi:ss') fixedtime, "
                        +"      diachronic from ACCIDENTDETAIL where accidentid = '" +id + "'";

        QueryUtil queryutil = new QueryUtil();
        List subList = queryutil.queryBeans( sqlStr );

        for( int i = 0; i < subList.size(); i++ ){
            BasicDynaBean tempBean = ( BasicDynaBean )subList.get( i );

            AccidentDetailBean dBean = new AccidentDetailBean();

            dBean.setId( ( String )tempBean.get( "id" ) );
            dBean.setAccidentid( id );
            dBean.setCorecode( ( String )tempBean.get( "corecode" ) );
            dBean.setDiachronic( ( String )tempBean.get( "diachronic" ) );
            dBean.setFixedtime( ( String )tempBean.get( "fixedtime" ) );
            dBean.setTempfixedtime( ( String )tempBean.get( "tempfixedtime" ) );

            deatailVct.add( dBean );

        }

        return deatailVct;
    }


    /**
     * �·�һ���¹ʴ���
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward postAccidentTask( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String id = request.getParameter( "id" );
        String cooperateman = request.getParameter( "cooperateman" );
        String bconfirman = request.getParameter( "bconfirman" );
        String breportman = request.getParameter( "breportman" );
        String flag = request.getParameter("flag");

        String sql = "";
        UpdateUtil upUtil = new UpdateUtil();

        if(flag.equals("2")){
            sql = "update accident set  status='9'  where keyid='" + id + "'";
            upUtil.executeUpdate( sql );
            return forwardInfoPage( mapping, request, "02130" );

        }


        if( breportman == "undefined" ){
            breportman = "";
        }
        if( bconfirman == "undefined" ){
            bconfirman = "";
        }
        AccidentBean bean = new AccidentBean();

         sql = "update accident set breportman='" + userInfo.getUserName() + "'," +
                     "cooperateman='" + cooperateman +
                     "', bconfirmman='" + bconfirman + "'," +
                     "NOTICETIME = sysdate, status='2',year=to_char(sysdate,'YYYY')," +
                     "month=to_char(sysdate,'MM') where keyid='" + id + "'";
        upUtil.executeUpdate( sql );
        logger.info( sql );

        return forwardInfoPage( mapping, request, "0213" );
    }


    /**
     * �·�һ����������
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward postTroubleTask( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        String regionid = (String)request.getSession().getAttribute("hiddentroubleregion");
        String id = request.getParameter( "id" );
        String sendto = request.getParameter( "sendto" );
        String whosend = request.getParameter( "whosend" );
        String breportman = request.getParameter( "breportman" );
        String bconfirman = request.getParameter( "bconfirman" );
        String flag = request.getParameter("flag");

        String sql = null;
        UpdateUtil upUtil = new UpdateUtil();
        if(flag.equals("2")){
            sql = "update accident set  status='9'  where keyid='" + id + "'";
            upUtil.executeUpdate( sql );
            return forwardInfoPage( mapping, request, "02230",regionid);

        }
        if( breportman == "undefined" ){
            breportman = "";
        }
        if( bconfirman == "undefined" ){
            bconfirman = "";
        }
        AccidentBean bean = new AccidentBean();

        if( breportman != null || bconfirman != null ){
            sql = "update accident set sendto='" + sendto +
                  "',NOTICETIME = sysdate, status='2' ,whosend='" + whosend +
                  "' ,year=to_char(sysdate,'YYYY')," +
                  "breportman='" + breportman + "',bconfirmman='" + bconfirman +
                  "'," +
                  "month=to_char(sysdate,'MM') where keyid='" + id + "'";

        }
        else{
            sql = "update accident set sendto='" + sendto +
                  "',NOTICETIME = sysdate, status='2' ,whosend='" + whosend +
                  "' ,year=to_char(sysdate,'YYYY')," +
                  "month=to_char(sysdate,'MM') where keyid='" + id + "'";
        }
        logger.info( sql );

        upUtil.executeUpdate( sql );
        return forwardInfoPage( mapping, request, "0223",regionid);
    }


    /**
     * ����һ������֪ͨ������д���ϴ���֪ͨ����
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward acceptAccidentTask( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String id = request.getParameter( "id" );
        String monitor = request.getParameter( "monitor" );
        String commander = request.getParameter( "commander" );
        AccidentBean bean = new AccidentBean();

        String sql = "update accident set commander='" + commander +
                     "',monitor = '" + monitor + "', status='2' where keyid='" + id +
                     "'";
        logger.info( sql );
        UpdateUtil upUtil = new UpdateUtil();
        upUtil.executeUpdate( sql );

        return forwardInfoPage( mapping, request, "0214" );
    }


    /**
     * ����һ������֪ͨ������д��������֪ͨ����
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward acceptTroubleTask( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String id = request.getParameter( "id" );
        String monitor = request.getParameter( "monitor" );
        String commander = request.getParameter( "commander" );

        AccidentBean bean = new AccidentBean();

        String sql = "update accident set status='2' where keyid='" + id +
                     "'";
        UpdateUtil upUtil = new UpdateUtil();
        upUtil.executeUpdate( sql );

        return forwardInfoPage( mapping, request, "0224" );
    }


    /**
     * д�����������
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward completeAccidentForm( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );

        String id = request.getParameter( "id" );
        String testtime = request.getParameter( "testtime" );
        String testman = request.getParameter( "testman" );
        String distance = request.getParameter( "distance" );
        String realdistance = request.getParameter( "realdistance" );
        String fixedman = request.getParameter( "fixedman" );
        String resonandfix = request.getParameter( "resonandfix" );
        String breportman = request.getParameter( "breportman" );
        String bconfirmman = request.getParameter( "bconfirmman" );
        String befortime = request.getParameter( "befortime" );
        String reson = request.getParameter( "reson" );
        String year = request.getParameter( "year" );
        String month = request.getParameter( "month" );
        String cooperateman = request.getParameter( "cooperateman" );
        String monitor = request.getParameter( "monitor" );
        String commander = request.getParameter( "commander" );
        resonandfix = reson + resonandfix;

        String[] corecodeArr = request.getParameterValues( "corecode" );
        String[] tempfixedtimeArr = request.getParameterValues( "tempfixedtime" );
        String[] fixedtimeArr = request.getParameterValues( "fixedtime" );
        String[] diachronicArr = request.getParameterValues( "diachronic" );

        AccidentBean bean = new AccidentBean();
        String datumid = uploadFile( form );
        String sql = "update accident set cooperateman='" + cooperateman + "'," +
                     "monitor='" + monitor + "'," +
                     "commander='" + commander + "'," +
                     "year='" + year + "', month = '" +
                     month + "', testman='" + testman +
                    "', bconfirmman = '" + userinfo.getUserName() +
                     "', breportman = '" + breportman + "', resonandfix = '" +
                     resonandfix + "', distance = '" + distance + "', realdistance = '" +
                     realdistance + "', fixedman = '" + fixedman + "',reporttime=sysdate" +
                     ",status='3',datumid='" + datumid + "' where keyid='" + id +
                     "'";
        UpdateUtil upUtil = new UpdateUtil();
        logger.info( "sql-->:" + sql );
        upUtil.executeUpdate( sql );
/*
        CustomID idFactory = new CustomID();
        idFactory.doMakeSeq( "accidentdetail", 20 );

        for( int i = 0; i < corecodeArr.length; i++ ){
            if( corecodeArr[i] != null && corecodeArr[i].trim().length() > 0 ){
                sql = "insert into accidentdetail (ID, ACCIDENTID, CORECODE, TEMPFIXEDTIME, FIXEDTIME, DIACHRONIC) values (SEQ_ACCIDENTDETAIL_ID.nextval, '" +
                      id + "','" + corecodeArr[i] + "',to_date('" +
                      tempfixedtimeArr[i] +
                      "','yyyy/mm/dd hh24:mi:ss'), to_date('" +
                      fixedtimeArr[i] + "','yyyy/mm/dd hh24:mi:ss'), '" +
                      diachronicArr[i] + "')";

                UpdateUtil updateutil = new UpdateUtil();
                logger.info( "sql-->:" + sql );
                updateutil.executeUpdateWithCloseStmt( sql );
            }
        }*/

        return forwardInfoPage( mapping, request, "0215");
    }


    /**
     * ���һ������֪ͨ��
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward completeTroubleForm( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String id = request.getParameter( "id" );
        String reson = request.getParameter( "reson" );
        String bconfirman = request.getParameter("bconfirman");
        //�ļ��ϴ�
        String datumid = uploadFile( form );

        String sql = "update accident set status='3',resonandfix='" + reson +
                     "', datumid='" + datumid + "',reporttime=sysdate,bconfirmman='" + bconfirman + "' where keyid='" + id +
                     "'";
        logger.info( "SQL: " + sql );
        UpdateUtil upUtil = new UpdateUtil();
        upUtil.executeUpdate( sql );
        String regionid = (String)request.getSession().getAttribute("hiddentroubleregion");
        return forwardInfoPage( mapping, request, "0225",regionid);
    }


    /**
     * ��ѯ�¹�
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryAccident( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String regionid = request.getParameter( "regionid" );

        QueryAcciBean bean = ( QueryAcciBean )form;

//        if( !bean.getCyc().equals( "1" ) ){
//            bean.setBegintime( bean.getYear() + bean.getMonth() );
//        }

        if( bean.getQueryby().equals( "subline" ) ){
            bean.setContractorid( null );
        }
        else{
            bean.setSublineid( null );
        }

        String sql = " select nvl(a.status,'0') statusflag, "
                     +"       decode(a.status,"
                     +"              '0','������',"
                     +"              '1','����Ӧ',"
                     +"              '2','������',"
                     +"              '3','�Ѵ���',"
                     +"              '������') status, a.KEYID id, "
                     +"      to_char(a.SENDTIME,'yy/mm/dd hh24:mi:ss') sendtime, "
                     +"      b.pointname point, c.sublinename subline, f.operationdes reason, "
                     +"      decode(f.emergencylevel,"
                     +"             '1','��΢',"
                     +"             '2','�ж�',"
                     +"             '3','����',"
                     +"             '��΢') emlevel, d.regionname region , "
                     +"      e.ContractorName contractor "
                     +"from ACCIDENT a, pointinfo b, sublineinfo c, region d, "
                     +"contractorinfo e, table(cast(getAllTroubleCodes as tabletypes)) f, patrolmaninfo g ";
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
        sqlBuild.addConstant( "a.type='1'" );
        sqlBuild.addAnd();
        sqlBuild.addConstant( " a.pid = b.pointid(+) and a.lid = c.sublineid(+) and a.regionid = d.regionid(+) and a.patrolid = g.patrolid(+) and g.parentid = e.contractorid(+) and a.operationcode = f.operationcode(+) " );
        //zsh 20050803 added
        sqlBuild.addConstant(
            "and  (   a.regionid IN (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='" +
            regionid + "')   ) " );
        //end zsh 20050803 added
        sqlBuild.addConditionAnd( "a.lid = {0}", bean.getSublineid() );
        sqlBuild.addConditionAnd( "e.contractorid = {0}", bean.getContractorid() );

        sqlBuild.addAnd();
        sqlBuild.addConstant( "a.type='1'" );

        if( bean.getCyc().equals( "1" ) ){
            sqlBuild.addConditionAnd( "a.SENDTIME >= {0}",
                DateUtil.StringToDate( bean.getBegintime() ) );

            sqlBuild.addConditionAnd( "a.SENDTIME <= {0}",
                DateUtil.StringToDate( bean.getEndtime() ) );

        }
        else{
            if( bean.getYear().length() > 0 && bean.getMonth().length() > 0 ){
                sqlBuild.addConstant( "and to_char(a.SENDTIME,'yyyymm') = '" +
                    bean.getYear() + bean.getMonth() + "'" );
            }
        }

        sqlBuild.addConditionAnd( "a.status = {0}", bean.getStatus() );
        //logger.info("sql: "+sqlBuild.toSql());
        List list = super.getDbService().queryBeans( sqlBuild.toSql() );
        request.getSession().setAttribute( "queryresult", list );
        return mapping.findForward( "queryAcciResult" );
    }


    /**
     * ������ѯ
     */
    public ActionForward queryTrouble( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String regionid = request.getParameter( "regionid" );
        UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        QueryTroubleBean bean = ( QueryTroubleBean )form;

        String sql = " select h.username whosend,nvl(a.status,'0') statusflag, "
                     +"       decode(a.status,"
                     +"              '0','������',"
                     +"              '1','����Ӧ',"
                     +"              '2','������',"
                     +"              '3','�Ѵ���',"
                     +"              '������') status, a.KEYID id, "
                     +"       to_char(a.SENDTIME,'yy/mm/dd hh24:mi:ss') sendtime, "
                     +"       b.pointname point, c.sublinename subline, f.operationdes reason, "
                     +"       decode(f.emergencylevel,"
                     +"              '1','��΢',"
                     +"              '2','�ж�',"
                     +"              '3','����',"
                     +"              '��΢') emlevel, d.regionname region , "
                     +"       e.ContractorName contractor "
                     +"from ACCIDENT a, pointinfo b, sublineinfo c, region d, contractorinfo e, "
                     +"table(cast(getAllTroubleCodes as tabletypes)) f, patrolmaninfo g,userinfo h ";
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

        sqlBuild.addConstant( " a.pid = b.pointid(+) and a.lid = c.sublineid(+) and a.regionid = d.regionid(+) and a.patrolid = g.patrolid(+) and g.parentid = e.contractorid(+) and a.operationcode = f.operationcode(+) " );
       // sqlBuild.addAnd();
       // sqlBuild.addConstant( "a.type='0'" );
        sqlBuild.addAnd();
        sqlBuild.addConstant( "a.whosend=h.userid(+)" );
        sqlBuild.addConstant(
            "and  (   a.regionid IN (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='" +
            regionid + "')   ) " );
        sqlBuild.addConditionAnd( "a.lid = {0}", bean.getSublineid() );
        sqlBuild.addConditionAnd( "e.contractorid = {0}", bean.getContractorid() );

        if( bean.getCyc().equals( "1" ) ){
            sqlBuild.addConditionAnd( "a.SENDTIME >= {0}",
                DateUtil.StringToDate( bean.getBegintime() ) );
            sqlBuild.addConditionAnd( "a.SENDTIME <= {0}",
                DateUtil.StringToDate( bean.getEndtime() ) );
        }
        else{
            if( bean.getYear().length() > 0 && bean.getMonth().length() > 0 ){
                sqlBuild.addConstant( "and to_char(a.SENDTIME,'yyyymm') = '" +
                    bean.getYear() + bean.getMonth() + "'" );
            }
        }

        sqlBuild.addConditionAnd( "a.status = {0}", bean.getStatus() );
        sql = sqlBuild.toSql();
        if(userInfo.getDeptype().equals("2"))
            sql += " and e.contractorid = '" + userInfo.getDeptID() + "'";
        sql += " ORDER BY e.contractorname DESC, a.status, a.sendtime DESC";
        List list = super.getDbService().queryBeans( sql);
        request.getSession().setAttribute( "queryresult", list );
        return mapping.findForward( "queryTroubleResult" );
    }


    /**
     * ��������֪ͨ��
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward ExportTroubleNoticeform( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        Enumeration e = request.getSession().getAttributeNames();
        while( e.hasMoreElements() ){
            logger.info( "session:" + ( String )e.nextElement() );
        }
        AccidentBean bean = ( AccidentBean )request.getSession().getAttribute(
                            "AccidentBean" );
        Vector taskVct = ( Vector )request.getSession().getAttribute( "tasklist" );
        //logger.info("***test:" + bean.getContractorid());
        //logger.info(bean.getResonandfix());
        super.getService().ExportTroubleNoticeform( bean, taskVct, response );

        return null;
    }


    /**
     * �����ϰ�֪ͨ��
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward ExportAccidentNoticeform( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        Enumeration e = request.getSession().getAttributeNames();
        while( e.hasMoreElements() ){
            logger.info( "session:" + ( String )e.nextElement() );
        }
        AccidentBean bean = ( AccidentBean )request.getSession().getAttribute(
                            "AccidentBean" );
        Vector taskVct = ( Vector )request.getSession().getAttribute( "tasklist" );

        super.getService().ExportAccidentNoticeform( bean, taskVct, response );

        return null;
    }

    /**
     * �ļ��ϴ�
     * @param form ActionForm
     * @return String
     */
    public String uploadFile( ActionForm form ){
        AccidentBean formbean = ( AccidentBean )form;
        //��ʼ�����ϴ��ļ�================================
        List attachments = formbean.getAttachments();
        String fileId;
        ArrayList fileIdList = new ArrayList();
        for( int i = 0; i < attachments.size(); i++ ){
            UploadFile uploadFile = ( UploadFile )attachments.get( i );
            FormFile file = uploadFile.getFile();
            if( file == null ){
                logger.info( "file is null" );
            }
            else{
                //���ļ��洢������������·��д�����ݿ⣬���ؼ�¼ID
                fileId = SaveUploadFile.saveFile( file );
                if( fileId != null ){
                    fileIdList.add( fileId );
                }
            }
        }
        //�����ϴ��ļ�����=======================================

        //��ȡID�ַ����б�(�Զ��ŷָ����ļ�ID�ַ���)======================
        String fileIdListStr = UploadUtil.getFileIdList( fileIdList );
        //String fileIdListStr =processFileUpload(request);
        String datumid = "";
        if( fileIdListStr != null ){
            // logger.info( "FileIdListStr=" + fileIdListStr );
            datumid = fileIdListStr;
        }
        return datumid;
    }


    public ActionForward exportUndoneTrouble( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "�õ�list" );
            super.getService().exportUndoneTrouble( list, response );
            logger.info( "���excel�ɹ�" );
            return null;
        }
        catch( Exception e ){
            logger.error( "����δ���������б�����쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportTroubleResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "�õ�list" );
            if(super.getService().exportTroubleResult( list, response )){
                logger.info( "���excel�ɹ�" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "����δ���������б�����쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportUndoneAccident( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "�õ�list" );
            super.getService().exportUndoneAccident( list, response );
            logger.info( "���excel�ɹ�" );
            return null;
        }
        catch( Exception e ){
            logger.error( "����δ�����ϰ��б�����쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportAccidentResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "�õ�list" );

            if(super.getService().exportAccidentResult( list, response )){
                logger.info( "���excel�ɹ�" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "����δ�����ϰ��б�����쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
