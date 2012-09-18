package com.cabletech.menu.services;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.menu.dao.MenuDaoImpl;
import com.cabletech.menu.domainobjects.FirstMenu;
import com.cabletech.menu.domainobjects.Resource;
import com.cabletech.menu.domainobjects.SecondlyMenu;
import com.cabletech.menu.domainobjects.ThirdMenu;

public class MenuService{
	
	MenuDaoImpl dao = new MenuDaoImpl();
	
	public Vector getResources()throws Exception{
		return dao.getResources();
	}
	
    public Vector getFirstMenu( UserInfo userinfo,String businessType ) throws Exception{
        return dao.getFirstMenu( userinfo ,businessType);
    }
    public Vector getFirstMenu( List  ugs ) throws Exception{
        return dao.getFirstMenu( ugs );
    }
    public Vector<FirstMenu> getFirstMenu() throws Exception{
        return dao.getFirstMenu();
    }
    public Vector getSecondlyMenu( String parentID, UserInfo userinfo ) throws Exception{
        return dao.getSecondMenu( parentID, userinfo );
    }
    public Vector getSecondlyMenu(String parentID ,List ugs) throws Exception {
        return dao.getSecondMenu( parentID , ugs );
    }
    public Vector getSecondlyMenu(  UserInfo userinfo ) throws Exception{
        return dao.getSecondMenu(  userinfo );
    }
    public Vector<SecondlyMenu> getSecondlyMenu() throws Exception{
    	return dao.getSecondMenu();
    }
    public Vector getThirdMenu( String parentID, UserInfo userinfo ) throws Exception{
        return dao.getThirdMenu( parentID, userinfo );
    }
    public Vector getThirdMenu( String parentID, List ugs ) throws Exception{
        return dao.getThirdMenu( parentID, ugs );
    }
    public Vector<ThirdMenu> getThirdMenu(String type) throws Exception{
        return dao.getThirdMenu(type);
    }
    public String getMenuJsonForUserGroup(String type, String relative) throws Exception {
    	Vector<Resource> res = getResources();
    	Vector<FirstMenu> fMenu = getFirstMenu();                  //得到一级菜单
    	Vector<SecondlyMenu> sMenu = getSecondlyMenu();            //得到二级菜单
    	Vector<ThirdMenu> tMenu = getThirdMenu(type);              //得到三级菜单
    	
    	List<String> relatives = Arrays.asList(relative.split("&"));    //该用户组对应菜单列表串
    	JSONArray resJsonArray = new JSONArray();
    	for(Resource resource :res){
    		JSONArray menuJsonArray = new JSONArray();
    		String businessType = null;
	    	for (FirstMenu fm : fMenu) {
	    		boolean iflag =false;
	    		if (fm.getBusinessType().equals(resource.getCode())) {
		    		JSONArray secondMenuArray = new JSONArray();
					for (SecondlyMenu sm : sMenu) {
						
						JSONArray thirdMenuArray = new JSONArray();
						for (ThirdMenu tm : tMenu) {
							if (tm.getParentid().equals(sm.getId()) && sm.getParentid().equals(fm.getId())) {
								boolean flag = relatives.contains(tm.getId()) ? true : false;
								thirdMenuArray.add(getJOString(tm.getId(), tm.getLablename(), flag));
							}
						}
						if (sm.getParentid().equals(fm.getId())) {
							secondMenuArray.add(getJOString("_"+sm.getId(), sm.getLablename(), thirdMenuArray));
							iflag = true;
						}
					}
					if(iflag){
						menuJsonArray.add(getJOString("_"+fm.getId(), fm.getLablename(), secondMenuArray));
					}
	    		}
			}
		    resJsonArray.add(getJOString("ct_"+resource.getCode(),resource.getResourceName(),menuJsonArray));
	    	
    	}
    	
    	JSONObject allObject = new JSONObject();
		allObject.put("id", "0");
		allObject.put("item", resJsonArray);
		System.out.println(allObject.toString());
    	return allObject.toString();
    }
    
    public JSONObject getJOString(String id, String lablename, boolean flag){
    	JSONObject jo = new JSONObject();
    	jo.put("id", id);
    	jo.put("text", lablename);
    	jo.put("checked", flag);
    	return jo;
    }
    
    public JSONObject getJOString(String id, String lablename, JSONArray children){
    	JSONObject jo = new JSONObject();
    	jo.put("id", id);
    	jo.put("text", lablename);
    	jo.put("item", children);
    	return jo;
    }

}
