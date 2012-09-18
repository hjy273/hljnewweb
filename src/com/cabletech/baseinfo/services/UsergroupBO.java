package com.cabletech.baseinfo.services;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.dao.UsergroupDAOImpl;
import com.cabletech.baseinfo.dao.UsergroupModuleListDAOImpl;
import com.cabletech.baseinfo.dao.UsergroupUserListDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.domainobjects.Usergroup;
import com.cabletech.baseinfo.domainobjects.UsergroupModuleList;
import com.cabletech.baseinfo.domainobjects.UsergroupUserList;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.generatorID.CustomID;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.util.JsonUtil;
import com.cabletech.menu.services.MenuService;

/**
 * �û������
 * @author Administrator
 *
 */
public class UsergroupBO extends BaseBisinessObject{
    private Logger logger = Logger.getLogger( UsergroupBO.class );
    public UsergroupBO(){
    }


    UsergroupDAOImpl dao = new UsergroupDAOImpl();
    /**
     * ����û�����Ϣ
     * @param data Usergroup
     * @throws Exception
     */
    public void addUsergroup( Usergroup data ) throws Exception{
        dao.addUsergroup( data );
    }

    /**
     * ����û����û�
     * @param data UsergroupUserList
     * @throws Exception
     */
    public void addUgUList( UsergroupUserList data ) throws
        Exception{
        UsergroupUserListDAOImpl dao = new UsergroupUserListDAOImpl();
        dao.addUgUList( data );
    }


    /**
     * ����û���Ȩ��
     * @param data UsergroupModuleList
     * @throws Exception
     */
    public void addUgMList( UsergroupModuleList data ) throws Exception{
        UsergroupModuleListDAOImpl dao = new UsergroupModuleListDAOImpl();
        dao.addUgMList( data );
    }

    /**
     * ����ָ���û�����Ϣ
     * @param id
     * @return Usergroup����
     * @throws Exception
     */
    public Usergroup loadUsergroup( String id ) throws Exception{
        return dao.findById( id );
    }

    /**
     * �Ƴ��û���
     * @param data
     * @throws Exception
     */
    public void removeUsergroup( Usergroup data ) throws Exception{
        dao.removeUsergroup( data );
    }

    /**
     * �����û���
     * @param data
     * @return
     * @throws Exception
     */
    public Usergroup updateUsergroup( Usergroup data ) throws Exception{
        return dao.updateUsergroup( data );
    }

    /**
     * ���û�ɾ���û��û����Ӧ��Ϣ��
     * @param ugid �û���id
     * @param userinfo  UserInfo �û���Ϣ
     * @return ɾ���ɹ����� true ɾ��ʧ�ܷ��� false
     */
    public boolean delUserRelative( String ugid, UserInfo userinfo ){
        String sql = "";
        UpdateUtil upUtil = null;
        sql = "delete from USERGOURPUSERLIST where USERGROUPID ='" + ugid + "'";
        if( "2".equals( userinfo.getDeptype() ) ){
            if( userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
                sql += " and userid in (select userid from userinfo where   deptid in(select CONTRACTORID from contractorinfo CONNECT BY PRIOR CONTRACTORID  = parentcontractorid START WITH CONTRACTORID='"
                    + userinfo.getDeptID() + "'))";
            }
            else{
                sql += " and userid in (select userid from userinfo where  deptid='" + userinfo.getDeptID() + "')";
            }
        }
        else{
            sql += " and userid in (select userid from userinfo where regionid in (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"
                + userinfo.getRegionID() + "'))";
        }

        try{
            upUtil = new UpdateUtil();
            upUtil.executeUpdateWithCloseStmt( sql );
            return true;
        }
        catch( Exception ex ){
            logger.error( "ɾ���û��û����Ӧ����û�����Ϣʱ����" + ex.getMessage() );
            return false;
        }
    }

    /**
     * ɾ��ָ���û��û����Ӧ��
     * @param ugId
     * @throws Exception
     */
    public void deleteUgRelative( String ugId ) throws Exception{
        UpdateUtil upUtil = new UpdateUtil();
        String sql = "";
        sql = "delete from USERGROUPMOUDULELIST where USERGROUPID='" + ugId +
              "'";
        upUtil = new UpdateUtil();
        upUtil.executeUpdateWithCloseStmt( sql );
    }

    /**
     * ���ָ��usergroupid���û�����û�ID
     * @param usergroupId
     * @return  String ��userid��ɵ��ַ���������userid��&������
     * @throws Exception QueryUtil�����׳�Exception
     */
    public String getRelativeUsers( String usergroupId ) throws Exception{
        String usersStr = "";

        String sql = "select userid from USERGOURPUSERLIST where usergroupid = '" + usergroupId + "'";
        QueryUtil qUtil = new QueryUtil();

        Vector tempVct = qUtil.executeQueryGetVector( sql );
        for( int i = 0; i < tempVct.size(); i++ ){
            Vector innerTempVct = ( Vector )tempVct.get( i );
            if( i == 0 ){
                usersStr = usersStr + ( String )innerTempVct.get( 0 );
            }
            else{
                usersStr = usersStr + "&" + ( String )innerTempVct.get( 0 );
            }
        }

        return usersStr;
    }

    /**
     * ���ָ���û����ģ��ID
     * @param usergroupId
     * @return String ��SONMENUID��ɵ��ַ���������SONMENUID��&������
     * @throws Exception QueryUtil�����׳�Exception
     */
    public String getRelativeModules( String usergroupId ) throws Exception{
        String menusStr = "";

        String sql =
            "select SONMENUID from USERGROUPMOUDULELIST where usergroupid = '" +
            usergroupId + "'";

        QueryUtil qUtil = new QueryUtil();

        Vector tempVct = qUtil.executeQueryGetVector( sql );
        for( int i = 0; i < tempVct.size(); i++ ){
            Vector innerTempVct = ( Vector )tempVct.get( i );
            if( i == 0 ){
                menusStr = menusStr + ( String )innerTempVct.get( 0 );
            }
            else{
                menusStr = menusStr + "&" + ( String )innerTempVct.get( 0 );
            }
        }
        return menusStr;
    }


    /**
     * ȡ�����������������û�
     * @return Vector
     * @throws Exception
     * ���ļ�¼:
     * 		ʱ��:2006��6��8��
     * 		����ԭ��:���ص��û�̫��,û�б�Ҫ,��ɲ�ѯ���ٶ���,
     * 		��������:�ݽ��û��ֳ�������,ʡ���û�,���ƶ��û�,��ά�û�,����ʡ���û�����ԭ�еĴ��벻��.
     *              �м��û����ظ�����������û�,��ά�û�ֻ�Ƿ��ظõ�λ���û�,
     */
    public String getRegionAndUsers2(UserInfo userinfo ,String type ,String userGroupId) throws Exception{
    	Map<String, List> regionUsers = getRegionUser(userinfo,type);
    	Map<String,String> relativeUser = getRelativeUser(userGroupId);
    	Map<String,String> userRegion = getUserRegion(userinfo.getRegionid());
    	return userJson(userRegion,regionUsers,relativeUser);
    }
    private String userJson(Map<String,String> userRegion,Map<String, List> regionUsers,Map<String,String> relativeUser){
    	String regionId;
    	String regionName;
    	JSONArray regionArray = new JSONArray();
    	for(Map.Entry entry:userRegion.entrySet()){
    		regionId = (String) entry.getKey();
    		regionName = (String) entry.getValue();
    		List<String[]> users = regionUsers.get(regionId);
    		if(users !=null){
    			JSONArray userArray = new JSONArray();
	    		for(String [] user:users){
	    			boolean flag = false;
	    			if(relativeUser.get(user[0]) != null){
	    				flag = true;
	    			}
	    			userArray.add(JsonUtil.createLeafObject(user[0], user[1], flag));
	    		}
	    		regionArray.add(JsonUtil.createBranchObject(regionId, regionName, userArray));
    		}
    	}
    	JSONObject allObject = JsonUtil.createBranchObject("0", null, regionArray);
		System.out.println(allObject.toString());
    	return allObject.toString();
    }
    private Map<String,String> getRelativeUser(String userGroupId) throws Exception{
    	 String sql = "select userid from USERGOURPUSERLIST where usergroupid = '" + userGroupId + "'";
         QueryUtil qUtil = new QueryUtil();
         ResultSet rs = qUtil.executeQuery(sql);
         Map<String, String> regionMap = new HashMap<String, String>();
 		while (rs.next()) {
 			regionMap.put(rs.getString("userid"), rs.getString("userid"));
 		}
 		return regionMap;
    }
	private Map<String, List> getRegionUser(UserInfo userinfo ,String type) throws Exception {
		String sql_user = "select userid,username,r.regionid,r.regionname from userinfo u ,region r "
				+ " where u.regionid = r.regionid and r.regionid in ("
				+ " select regionid from region CONNECT BY PRIOR regionid=parentregionid START WITH regionid='"
				+ userinfo.getRegionid() + "') order by regionid";
		QueryUtil qUtil = new QueryUtil();
		ResultSet rs = qUtil.executeQuery(sql_user);
		Map<String, List> userMap = new HashMap<String, List>();
		String userId = "";
		String userName = "";
		String regionId = "";
		String regionName = "";
		String preRegionId = "";
		List<String[]> userArray = new ArrayList<String[]>();
		while (rs.next()) {
			userId = rs.getString("userId");
			userName = rs.getString("username");
			regionId = rs.getString("regionid");
			String[] user = { userId, userName };
			if ("".equals(preRegionId) || regionId.equals(preRegionId)) {
				userArray.add(user);
				preRegionId = regionId;
			} else {
				userMap.put(preRegionId, userArray);
				userArray = new ArrayList<String[]>();
				userArray.add(user);
				preRegionId = regionId;
			}
		}
		userMap.put(preRegionId, userArray);
		return userMap;
    }
	private Map<String,String> getUserRegion(String regionid) throws Exception {
		String sql_region = "select regionid,regionname from region CONNECT BY PRIOR regionid  = parentregionid START WITH regionid='"+regionid+"'";
		QueryUtil qUtil = new QueryUtil();
		ResultSet rs = qUtil.executeQuery(sql_region);
		Map<String, String> regionMap = new HashMap<String, String>();
		while (rs.next()) {
			regionMap.put(rs.getString("regionid"), rs.getString("regionname"));
		}
		return regionMap;
	}
    
    
    public Vector getRegionAndUsers( HttpServletRequest request ) throws Exception{
    	
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String type = request.getParameter("type");
        
        Vector resultVct = new Vector();
        String sql = "";
        Vector regionVct = new Vector();
        Vector userVct = new Vector();
        QueryUtil qUtil = new QueryUtil();

        if( userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){ //ʡ���û�,ԭ�д���
            sql = "select distinct regionid from region where substr(regionid,3,4) = '0000' ";

            String[][] topRegionArr = qUtil.executeQueryGetArray( sql,"" );
            String topRegionid = topRegionArr[0][0];
    //        System.out.println( "��������" + topRegionid );
            sql = "select distinct regionname,regionid from region WHERE (  RegionID IN (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='" +
                  topRegionid + "')   ) order by regionid ";
            qUtil = new QueryUtil();
            regionVct = qUtil.executeQueryGetVector( sql );
            if( userinfo.getDeptype().equals( "1" ) ){
                for( int i = 0; i < regionVct.size(); i++ ){
                    Vector tempVct = ( Vector )regionVct.get( i );
                    String regionid = ( String )tempVct.get( 1 );
                    sql =
                        "select distinct username, userid from userinfo where state is null and regionid = '" +
                        regionid + "'";
                    qUtil = new QueryUtil();
                    Vector oneUnitVct = qUtil.executeQueryGetVector( sql );
                    userVct.add( oneUnitVct );
                }
            }
            else{
                for( int i = 0; i < regionVct.size(); i++ ){
                    Vector tempVct = ( Vector )regionVct.get( i );
                    String regionid = ( String )tempVct.get( 1 );
                    sql = "select distinct username, userid from userinfo where state is null and regionid='"
                          + regionid + "' and "
                          + " deptid in(select CONTRACTORID from contractorinfo CONNECT BY PRIOR CONTRACTORID  = parentcontractorid START WITH CONTRACTORID='"
                          + userinfo.getDeptID() + "')";
                    qUtil = new QueryUtil();
                    Vector oneUnitVct = qUtil.executeQueryGetVector( sql );
                    userVct.add( oneUnitVct );
                }
            }
            resultVct = remakeVct( regionVct, userVct );

        }
        else{
            sql = "select distinct regionname,regionid from region WHERE regionid='" + userinfo.getRegionID() + "'";
            qUtil = new QueryUtil();
            regionVct = qUtil.executeQueryGetVector( sql );

            if( userinfo.getDeptype().equals( "1" ) ){ //�ƶ���˾
                sql = "select distinct username, userid from userinfo where state is null and regionid = '"
                      + userinfo.getRegionID() + "' and deptype='"+type+"'";
                qUtil = new QueryUtil();
                Vector oneUnitVct1 = qUtil.executeQueryGetVector( sql );
                userVct.add( oneUnitVct1 );

            }
            else{ //��ά��λ
                sql = "select distinct username, userid from userinfo where state is null and regionid = '"
                      + userinfo.getRegionID() + "' and deptid = '" + userinfo.getDeptID() + "' and deptype='"+type+"'";
                qUtil = new QueryUtil();
                Vector oneUnitVct2 = qUtil.executeQueryGetVector( sql );
                userVct.add( oneUnitVct2 );
            }
            resultVct = remakeVct( regionVct, userVct );
        }
        return resultVct;
    }


    /**
     * remakeVct
     * @param regionVct Vector
     * @param userVct Vector
     * @return Vector
     */
    private Vector remakeVct( Vector regionVct, Vector userVct ){
        Vector resultVct = new Vector();
        for( int i = 0; i < regionVct.size(); i++ ){
            Vector oneUnitVct = new Vector();

            Vector tempRVct = ( Vector )regionVct.get( i );
            Vector tempUVct = ( Vector )userVct.get( i );

            String regionid = ( String )tempRVct.get( 1 );
            String regionname = ( String )tempRVct.get( 0 );

            oneUnitVct.add( regionid );
            oneUnitVct.add( regionname );

            for( int k = 0; k < tempUVct.size(); k++ ){
                Vector inUVct = ( Vector )tempUVct.get( k );

                String userid = ( String )inUVct.get( 1 );
                String username = ( String )inUVct.get( 0 );

                oneUnitVct.add( userid );
                oneUnitVct.add( username );
            }

            resultVct.add( oneUnitVct );

        }

        return resultVct;
    }


//    /**
//     * ȡ�ò˵�
//     * @return Vector
//     * @throws Exception
//     * @deprecated Ч��̫�������
//     */
//    public Vector getMenuVct( String type) throws Exception{
//        Vector resultVct = new Vector();
//
//        String L1_sql = "select distinct id, lablename from mainmodulemenu  order by id";
//        QueryUtil qUtil = new QueryUtil();
//        ResultSet rs = qUtil.executeQuery( L1_sql );
//
//        while( rs.next() ){
//
//            Vector topVct = new Vector();
//
//            String topId = rs.getString( 1 );
//            String topName = rs.getString( 2 );
//
//            topVct.add( topId );
//            topVct.add( topName );
//
//            String L2_sql =
//                "select distinct id, lablename from submenu where parentid='" +
//                topId + "' and (type !='9' or type is null ) order by id";
//
//            Vector subVct = new Vector();
//
//            QueryUtil qUtil2 = new QueryUtil();
//            ResultSet rs2 = qUtil2.executeQuery( L2_sql );
//            while( rs2.next() ){
//
//                subVct = new Vector();
//
//                String subId = rs2.getString( 1 );
//                String subName = rs2.getString( 2 );
//
//                subVct.add( subId );
//                subVct.add( subName );
//
//                String L3_sql =   " select distinct id, lablename "
//                               +  " from sonmenu "
//                               + " where parentid='" + subId + "'"
//                               + "  and (type !='9' or type is null ) ";
//               if(type.length()==1)
//                   L3_sql += "      and (instr(sonmenu.power,'"+type+"1')+instr(sonmenu.power,'"+type+"2')!=0)";
//               else
//                   L3_sql+= "       AND (INSTR(SONMENU.POWER,'" + type + "') !=0)";
//                L3_sql+= "          order by id";
//                QueryUtil qUtil3 = new QueryUtil();
//                ResultSet rs3 = qUtil3.executeQuery( L3_sql );
//                while( rs3.next() ){
//                    subVct.add( rs3.getString( 1 ) );
//                    subVct.add( rs3.getString( 2 ) );
//                }
//                if(subVct.size()>2)
//                    topVct.add( subVct );
//                rs3.close();
//
//            }
//            if(topVct.size()>2)
//                resultVct.add( topVct );
//            rs2.close();
//        }
//        rs.close();
//        return resultVct;
//    }
    
    public String getMenuVct(String type, String relative) throws Exception{
    	return new MenuService().getMenuJsonForUserGroup(type, relative);
    }
    
    public void saveUserGroupModule(String menuString, String id) throws Exception{
    	List<String> allMenus = Arrays.asList(menuString.split(","));
    	List<String> menus = new ArrayList<String>();
    	for(String s : allMenus){
    		if( s.indexOf("_")== -1 && !s.equals("0")){
    			menus.add(s);
    		}
    	}
    	CustomID idFactory = new CustomID();
        String[] idArr = idFactory.getStrSeqs( menus.size(),"usergroupmoudulelist", 20 );
    	for(int i=0 ; i < menus.size() ; i++){
    		UsergroupModuleList ugMList = new UsergroupModuleList();
    		System.out.println("sonmenu: "+menus.get(i));
    		ugMList.setId(idArr[i]);
    		ugMList.setUsergroupid(id);
    		ugMList.setSonmenuid(menus.get(i));
            addUgMList(ugMList);
    	}
    }
    
    public void saveUserGroupUser(String userString, String id) throws Exception{
    	List<String> users = Arrays.asList(userString.split(","));
    	CustomID idFactory = new CustomID();
        String[] idArr = idFactory.getStrSeqs( users.size(),"usergourpuserlist", 20 );
    	for(int i=0 ; i < users.size() ; i++){
            UsergroupUserList UgUList = new UsergroupUserList();
            UgUList.setId(idArr[i]);
            UgUList.setUserid(users.get(i));
            UgUList.setUsergroupid(id);

            addUgUList( UgUList );
    	}
    }


    /**
     * clearUgUList
     *
     * @param userid String
     */
    public void clearUgUList( String userid ){
        UsergroupUserListDAOImpl uguldao = new UsergroupUserListDAOImpl();
        try{
            uguldao.clearUgUList( userid );
        }
        catch( Exception ex ){
            logger.error("����û���usergroupuserlist���еļ�¼�쳣��"+ex.getMessage());
        }
	}

	public boolean validate(Usergroup usergroup) {
		return dao.validate(usergroup);
	}
}
