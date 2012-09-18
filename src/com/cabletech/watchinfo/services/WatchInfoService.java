package com.cabletech.watchinfo.services;

import java.util.*;
import javax.servlet.http.*;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.exterior.beans.WatchExeStaCondtionBean;
import com.cabletech.exterior.beans.WatchExeStaResultBean;
import com.cabletech.watchinfo.beans.*;
import com.cabletech.watchinfo.domainobjects.*;

public class WatchInfoService extends BaseService{

    //���������ֳ�
    WatchBO bow;
    WatchexeBO bowe;
    WatchStaBO bosta;

    public WatchInfoService(){
        bow = new WatchBO();
        bowe = new WatchexeBO();
        bosta = new WatchStaBO();
    }


    //���������ֳ�
    public void createWatch( Watch data ) throws Exception{
        //System.out.println("createWatch begin");
        bow.addWatch( data );
    }


    public void createSubWatch( SubWatch data ) throws Exception{
        //System.out.println("createWatch begin");
        bow.addSubWatch( data );
    }


    public String getLineIdByPoint( String pointid ) throws Exception{
        return bow.getLineIdByPoint( pointid );
    }


    public Watch loadWatch( String id ) throws Exception{
        return bow.loadWatch( id );
    }


    public SubWatch loadSubWatch( String id ) throws Exception{
        return bow.loadSubWatch( id );
    }


    public Watch updateWatch( Watch watch ) throws Exception{
        return bow.updateWatch( watch );
    }


    public SubWatch updateSubWatch( SubWatch watch ) throws Exception{
        return bow.updateSubWatch( watch );
    }


    public void removeWatch( Watch data ) throws Exception{
        bow.removeWatch( data );
    }


    public void removeSubWatch( SubWatch data ) throws Exception{
        bow.removeSubWatch( data );
    }


    public List watchReport() throws Exception{
        return bow.queryReport();
    }


    public void removeWatchSubList( Watch data ) throws Exception{
        bow.removeWatchSubList( data );
    }


    public void addNewWatchSubList( Watch data, String[] cableIdArr ) throws
        Exception{
        bow.addNewWatchSubList( data, cableIdArr );
    }


    public Vector getWatch_cable_fiber_list( WatchBean bean ) throws Exception{
        return bow.getWatch_cable_fiber_list( bean );
    }


    //�����ֳ�����ִ�������Ϣ
    public void createWatchexe( Watchexe data ) throws Exception{
        bowe.addWatchexe( data );
    }


    public Watchexe loadWatchexe( String id ) throws Exception{
        return bowe.loadWatchexe( id );
    }


    public Watchexe updateWatchexe( Watchexe data ) throws Exception{
        return bowe.updateWatchexe( data );
    }


    public void removeWatchexe( Watchexe data ) throws Exception{
        bowe.removeWatchexe( data );
    }


    //����������Ϣ��
    public void exportWatchInfo( WatchBean bean, HttpServletResponse response ) throws
        Exception{
        bow.exportWatchInfo( bean, response );
    }


    //****************************pzj �޸�
     public void exportWatchConstructInfo( Vector checkvec, WatchBean bean, Vector vct,
         HttpServletResponse response ) throws
         Exception{
         bow.exportWatchConstructInfo( checkvec, bean, vct, response );
     }


    public void exportWatchConstructInfo( WatchBean bean, Vector vct,
        HttpServletResponse response ) throws
        Exception{
        bow.exportWatchConstructInfo( bean, vct, response );
    }


//
//    public void exportWatchConstructInfo( WatchBean bean, Vector vct,
//        HttpServletResponse response ) throws
//        Exception{
//        bow.exportWatchConstructInfo( bean, vct, response );
//    }
//

    public void exportWatchConstructInfoNoCable( WatchBean bean,
        HttpServletResponse response ) throws
        Exception{
        bow.exportWatchConstructInfoNoCable( bean, response );
    }


    //��������ִ����Ϣ��
    public void exportWatchDetail( List lst, HttpServletResponse response ) throws
        Exception{
        bowe.exportWatchDetail( lst, response );
    }


    //��������ִ��ͳ����Ϣ��
    public void exportWatchSta( WatchStaResultBean bean,
        HttpServletResponse response ) throws
        Exception{
        bowe.exportWatchSta( bean, response );
    }
    public void exportWatchPointSta( WatchStaResultBean bean,
        HttpServletResponse response ) throws
        Exception{
        bowe.exportWatchPointSta( bean, response );
    }

    //����������������Ϣ�ܱ�
    //****************************pzj �޸�
     public void esportWatchList( List lstbean, List lstvct, List listcheck, HttpServletResponse response ) throws
         Exception{
         bowe.exportWatchList( lstbean, lstvct, listcheck, response );
     }


//    public void esportWatchList( List lstbean, List lstvct, HttpServletResponse response ) throws
//        Exception{
//        bowe.exportWatchList( lstbean, lstvct, response );
//    }

    public void esportWatchList( List lstbean, HttpServletResponse response ) throws
        Exception{
        bowe.exportWatchList( lstbean, response );
    }


    public void exportTempWatchResult( List list, HttpServletResponse response ) throws
        Exception{
        bowe.exportTempWatchResult( list, response );
    }


    public void exportWatchResult( List list, HttpServletResponse response ) throws
        Exception{
        bowe.exportWatchResult( list, response );
    }


    /**
     * ͳ�������������
     * @param conditionBean WatchStaBean
     * @return WatchStaResultBean
     * @throws Exception
     */
    public WatchStaResultBean getStaResultBean( WatchStaBean conditionBean, UserInfo userinfo ) throws
        Exception{
        return bosta.getStaResultBean( conditionBean, userinfo );
    }
    
    /**
     * ͳ�ƶ������(ʡ�ƶ��û� by steven)
     * @param conditionBean WatchExeStaCondtionBean
     * @return List
     * @throws Exception
     */
    public List getStaResultBeanForAA( WatchExeStaCondtionBean conditionBean) throws
        Exception{
        return bosta.getStaResultBeanForAA(conditionBean);
    }

}
