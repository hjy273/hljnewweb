package com.cabletech.baseinfo.services;

import java.util.*;

import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.*;

public class ContractorBO extends BaseBisinessObject {
    /**
     * addDepart
     */
    ContractorDAOImpl dao = new ContractorDAOImpl();

    // DepartDAO dao=DaoFactory.createDao();

    public void addContractor(Contractor data) throws Exception {
        dao.addContractor(data);
    }

    public void removeContractor(Contractor data) throws Exception {
        dao.removeContractor(data);
    }

    public Contractor loadContractor(String id) throws Exception {
        return dao.findById(id);
    }

    public Contractor updateContractor(Contractor contractor) throws Exception {
        return dao.updateContractor(contractor);
    }

    /**
     * 删除下属单位
     * 
     * @param id
     *            String
     * @throws Exception
     */
    public void removeSubContractor(String conid) throws Exception {
        String sql = "select contractorid from contractorinfo where parentcontractorid= '" + conid
                + "'";
        QueryUtil jutil = new QueryUtil();

        BaseInfoService service = new BaseInfoService();

        Vector vct = jutil.executeQueryGetVector(sql);
        for (int i = 0; i < vct.size(); i++) {
            Vector tempVct = (Vector) vct.get(i);
            String id = (String) tempVct.get(0);

            Contractor contractor = service.loadContractor(id);
            service.removeContractor(contractor);

        }
    }

    /**
     * 获取监理公司
     * 
     * @return List
     * @throws Exception
     */
    public List getSuperviseUnits() throws Exception {
        String sql = "select contractorid,contractorname from contractorinfo where contractorid in ( "
                + " select deptid from userinfo u,usergourpuserlist ug "
                + "where u.userid=ug.userid and ug.usergroupid in ( "
                + "select usergroupid from usergourpuserlist where userid in( "
                + "select userid from userinfo where is_supervise_unit='1')))";
        QueryUtil util = new QueryUtil();
        return util.queryBeans(sql);
    }
    
    /**
     * 获得所有的代维单位
     * @param userInfo
     * @return
     */
    public List<Contractor> getAllContractor(UserInfo userInfo){
    	return dao.getAllContractor(userInfo);
    }
    
    public Contractor getContractorById(String contractorId){
    	try {
			return dao.findById(contractorId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
    }
    public String getContractorNameByContractorById(String contractorId) throws Exception{
    	Contractor contractor=loadContractor(contractorId);
    	return contractor.getContractorName();
    }
    /**
     * 获得代维单位的map
     * @param userInfo
     * @return
     */
    public Map getContractorForMap(UserInfo userInfo){
    	List<Contractor> contractors=dao.getAllContractor(userInfo);
    	Map<String,String> map=new HashMap<String,String>();
    	for(Contractor contractor:contractors){
    		map.put(contractor.getContractorID(), contractor.getContractorName());
    	}
    	return map;
    }
}
