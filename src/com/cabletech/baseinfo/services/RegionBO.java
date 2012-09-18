package com.cabletech.baseinfo.services;

import java.util.*;

import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.*;

public class RegionBO extends BaseBisinessObject {
    private static String regionID;

    /**
     * addRegion
     */
    RegionDAOImpl dao = new RegionDAOImpl();

    // DepartDAO dao=DaoFactory.createDao();

    public void addRegion(Region data) throws Exception {
        dao.addRegion(data);
    }

    public Region loadRegion(String id) throws Exception {
        return dao.findById(id);
    }

    public Region updateRegion(Region region) throws Exception {
        return dao.updateRegion(region);
    }

    public void removeRegion(Region region) throws Exception {
        dao.removeRegion(region);
    }

    // public String getRegionRoot(){
    // return dao.getRoot();
    // }

    /**
     * 删除所有下属区域
     * 
     * @param region
     *            Region
     * @throws Exception
     */
    public void removeSubRegion(String regionid) throws Exception {

        String sql = "select regionid from region where parentregionid= '" + regionid + "'";
        QueryUtil jutil = new QueryUtil();

        BaseInfoService service = new BaseInfoService();

        Vector vct = jutil.executeQueryGetVector(sql);
        for (int i = 0; i < vct.size(); i++) {
            Vector tempVct = (Vector) vct.get(i);
            String id = (String) tempVct.get(0);

            Region region = service.loadRegion(id);
            service.removeRegion(region);

        }

    }

    public List<Region> queryForList(String condition) throws Exception {
        String queryString = " from Region where 1=1 " + condition;
        return dao.getHibernateTemplate().find(queryString);
    }

    private void setRegionID(String regionID) {
        this.regionID = regionID;
    }

    public String getRegionID() {
        if (regionID == null) {
            this.setRegionID(dao.getRoot());
            // System.out.println("-----------------------------------"+regionID);
        }
        // System.out.println(regionID);
        return regionID;
    }
    /**
     * 获得region的Map  例如11000--北京
     * @param regionId
     * @return
     * @throws Exception
     */
	public Map<String,String> queryForMap(String regionId) throws Exception{
		Map<String,String> Regions=new HashMap<String, String>();
		RegionBO regionBO=new RegionBO();
		List<Region> list=regionBO.queryForList(" and parentregionid='"+regionId+"'");
		for(Region region:list){
			Regions.put(region.getRegionID(), region.getRegionName());
		}
		return Regions;
	}
}
