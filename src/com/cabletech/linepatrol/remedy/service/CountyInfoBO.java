package com.cabletech.linepatrol.remedy.service;

import java.util.*;

import com.cabletech.baseinfo.services.BaseInfoService;
import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.*;
import com.cabletech.linepatrol.remedy.dao.CountyInfoDAOImpl;
import com.cabletech.linepatrol.remedy.domain.CountyInfo;

public class CountyInfoBO extends BaseBisinessObject {

    /**
     * addRegion
     */
    CountyInfoDAOImpl dao = new CountyInfoDAOImpl();

    // DepartDAO dao=DaoFactory.createDao();

    public void addCounty(CountyInfo countyInfo) throws Exception {
        dao.addCounty(countyInfo);
    }

    public CountyInfo loadCounty(String id) throws Exception {
        return dao.findById(id);
    }

    public CountyInfo updateCounty(CountyInfo countyInfo) throws Exception {
        return dao.updateCounty(countyInfo);
    }

    public void removeCounty(CountyInfo countyInfo) throws Exception {
        dao.removeCounty(countyInfo);
    }

    // public String getRegionRoot(){
    // return dao.getRoot();
    // }

    /**
     * 删除所有
     * 
     * @param countyid
     *            String
     * @throws Exception
     */
    public void removeCounty(String countyid) throws Exception {

        String sql = "select countyid from countyinfo where countyid= '" + countyid + "'";
        QueryUtil jutil = new QueryUtil();

        BaseInfoService service = new BaseInfoService();

        Vector vct = jutil.executeQueryGetVector(sql);
        for (int i = 0; i < vct.size(); i++) {
            Vector tempVct = (Vector) vct.get(i);
            String id = (String) tempVct.get(0);

            CountyInfo countyInfo = service.loadCounty(id);
            service.removeCounty(countyInfo);

        }

    }

    /**
     * 判断区县是否存在
     * 
     * @param county
     * @return
     * @throws Exception
     */
    public boolean judgeCountyExist(CountyInfo county) throws Exception {
        String sql = "select id from linepatrol_towninfo where id<>'" + county.getId()
                + "' and town='" + county.getTown() + "' and regionid='" + county.getRegionid()
                + "'";
        QueryUtil jutil = new QueryUtil();
        List list = jutil.queryBeans(sql);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }
}
