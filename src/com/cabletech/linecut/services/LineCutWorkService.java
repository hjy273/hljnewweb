package com.cabletech.linecut.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.linecut.beans.LineCutBean;
import com.cabletech.linecut.dao.LineCutWorkDao;

public class LineCutWorkService extends BaseBisinessObject{
    private LineCutWorkDao dao = new LineCutWorkDao();
    public LineCutWorkService(){
    }


    public List getAllReForWork( UserInfo userinfo ){
        return dao.getAllReForWork( userinfo );
    }


    public LineCutBean getOneReInfo( String reid ){
        return dao.getOneReInfo( reid );
    }


    public boolean addWorkInfo( LineCutBean bean ){
        return dao.addWorkInfo( bean );
    }


    public List getAllOwnWork( HttpServletRequest request ){
        return dao.getAllOwnWork( request );
    }


    public List getAllWork( HttpServletRequest request ){
        return dao.getAllWork( request );
    }


    public LineCutBean getOneWorkInfo( String reid ){
        return dao.getOneWorkInfo( reid );
    }


    public List getAllNamesForWork( String contractorid ){
        return dao.getAllNamesForWork( contractorid );
    }


    public List getAllReasonsForWork( String contractorid ){
        return dao.getAllReasonsForWork( contractorid );
    }


    public List getAllAddresssForWork( String contractorid ){
        return dao.getAllAddresssForWork( contractorid );
    }


    public List getAllEfsystemsForWork( String contractorid ){
        return dao.getAllEfsystemsForWork( contractorid );
    }


    public List getAllSublineidsForWork( String contractorid ){
        return dao.getAllSublineidsForWork( contractorid );
    }


    public List getAllOwnReForWorkSearch( LineCutBean bean, UserInfo userinfo, HttpSession session ){
        return dao.getAllOwnReForWorkSearch( bean, userinfo ,session);
    }
    
    public List getConditionsReForWorkSearch( LineCutBean bean, UserInfo userinfo, HttpServletRequest request ){
        return dao.getConditionsReForWorkSearch( bean, userinfo ,request);
    }
    
    /**
     * 按先前的查询条件返回结果
     * @param sql
     * @return
     */
    public List doQueryAfterMod(String sql) {
    	return dao.doQueryAfterMod(sql);
    }


//=======================================//
    public List getAllForArch( HttpServletRequest request ){
        return dao.getAllForArch( request );
    }

    public boolean WorkUp( LineCutBean bean ){
        return dao.WorkUp(bean);
    }

    public LineCutBean getOneForArch( String reid ){
        return dao.getOneForArch( reid );
    }


    public boolean addArchInfo( LineCutBean bean ){
        return dao.addArchInfo( bean );
    }


    public List getCutInfo( HttpServletRequest request ){
        return dao.getCutInfo( request );
    }


    public LineCutBean getOneCutAllInfo( String reid ){
        return dao.getOneCutAllInfo( reid );
    }


    public List getAllConName( String deptid ){
        return dao.getAllConName( deptid );
    }


    public List getCutInfoForWorking( HttpServletRequest request ){
        return dao.getCutInfoForWorking( request );
    }


    public List getCutInfoForSearch( LineCutBean bean, UserInfo userinfo, HttpSession session ){
        return dao.getCutInfoForSearch( bean, userinfo ,session);
    }
    
    public List getConditionsCutInfoForSearch( LineCutBean bean, UserInfo userinfo, HttpServletRequest rquest ){
        return dao.getConditionsCutInfoForSearch( bean, userinfo ,rquest);
    }
    
    public List queryAfterBack(String sql) {
    	return dao.queryAfterBack(sql);
    }


    public boolean ExportLineCut( List list, HttpServletResponse response ){
        return dao.ExportLineCut( list, response );
    }


    public boolean ExportReLineCut( List list, HttpServletResponse response ){
        return dao.ExportReLineCut( list, response );
    }


    public boolean ExportLineCutWork( List list, HttpServletResponse response ){
        return dao.ExportLineCutWork( list, response );
    }


    public boolean ExportLineCutRe( List list, HttpServletResponse response ){
        return dao.ExportLineCutRe( list, response );
    }


    public boolean ExportLineCutAcc( List list, HttpServletResponse response ){
        return dao.ExportLineCutAcc( list, response );
    }

}
