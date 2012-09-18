package com.cabletech.lnsso.ws;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.axis.message.MessageElement;
import org.apache.log4j.Logger;
import org.tempuri.ActiveDirectoryNoValidateLocator;
import org.tempuri.ActiveDirectoryNoValidateSoap;
import org.tempuri.GetGroupInfoByPerSearchCodeResponseGetGroupInfoByPerSearchCodeResult;
import org.tempuri.GetGroupInfoByUsernameResponseGetGroupInfoByUsernameResult;
import org.tempuri.GetUserInfoByUsernameResponseGetUserInfoByUsernameResult;
import org.tempuri.ServiceDeskNoValidateLocator;
import org.tempuri.ServiceDeskNoValidateSoap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cabletech.baseinfo.dao.UserInfoDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.lnsso.LoginService;
import com.cabletech.lnsso.User;

public class LoginServiceImpl  extends BaseBisinessObject implements LoginService {
	private static Logger logger = Logger.getLogger( LoginServiceImpl.class );
	UserInfoDAOImpl dao = new UserInfoDAOImpl();
	
	public UserInfo getUserInfo(String username, String usertype) {
		if(usertype.equals("ad")){
			UserInfo userinfo = new UserInfo();
			try {
				User user;
				user = getADUser(username);
				if("01".equals(user.getUserType())){
					userinfo.setDeptype("1");
				}else if ("02".equals(user.getUserType())){
					userinfo.setDeptype("1");
				}else{
					userinfo.setDeptype("1");
				}
				userinfo.setUserID(user.getUserId());
				userinfo.setUserName(user.getUserName());
				userinfo.setDeptName(user.getDeptName());
				userinfo.setRegionID(user.getRegionName());
				logger.info("登录用户信息"+userinfo.toString());
				return userinfo;
			} catch (RemoteException e) {
				e.printStackTrace();
				logger.error("获得AD用户信息时，连接远程服务异常："+e);
				return userinfo;
			} catch (ServiceException e) {
				e.printStackTrace();
				logger.error("获得AD用户信息时，Service服务异常："+e);
				return userinfo;
			}
			
		}else if(usertype.equals("sd")){
			UserInfo userinfo = new UserInfo();
			try {
				User user;
				user = getSDUser(username);
				if("01".equals(user.getUserType())){
					userinfo.setDeptype("1");
				}else if ("02".equals(user.getUserType())){
					userinfo.setDeptype("1");
				}else if ("03".equals(user.getUserType())){
					userinfo.setDeptype("2");
				}else{
					userinfo.setDeptype("2");
				}
				
				userinfo.setUserID(user.getUserId());
				userinfo.setUserName(user.getUserName());
				userinfo.setDeptName(user.getDeptName());
				userinfo.setRegionID(user.getRegionName());
				logger.info("登录用户信息"+userinfo.toString());
				return userinfo;
			} catch (RemoteException e) {
				e.printStackTrace();
				logger.error("获得SD用户信息时，连接远程服务异常："+e);
				return userinfo;
			} catch (ServiceException e) {
				e.printStackTrace();
				logger.error("获得SD用户信息时，Service服务异常："+e);
				return userinfo;
			}
		}else if(usertype.equals("t")){
			UserInfo userinfo = new UserInfo();
			User user;
			user = getTUser(username);
			if("01".equals(user.getUserType())){
				userinfo.setDeptype("1");
			}else if ("02".equals(user.getUserType())){
				userinfo.setDeptype("1");
			}else if ("03".equals(user.getUserType())){
				userinfo.setDeptype("2");
			}else{
				userinfo.setDeptype("2");
			}
			userinfo.setUserID(user.getUserId());
			userinfo.setUserName(user.getUserName());
			userinfo.setDeptName(user.getDeptName());
			userinfo.setRegionID(user.getRegionName());
			return userinfo;
		}else{
			return null;
		}
	}
	
	/**
	 * 获得AD用户信息
	 * @param username
	 * @return
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	private User getADUser(String username) throws ServiceException, RemoteException{
		ActiveDirectoryNoValidateSoap adsoap = new ActiveDirectoryNoValidateLocator().getActiveDirectoryNoValidateSoap12();
		User user = new User();
		GetUserInfoByUsernameResponseGetUserInfoByUsernameResult aduser = adsoap.getUserInfoByUsername(username);
		MessageElement [] aduser_Msg = aduser.get_any();
		MessageElement aduserMsgData = aduser_Msg[1];
		NodeList dataSetNodeList = aduserMsgData.getChildNodes();
		for(int i=0;i<dataSetNodeList.getLength();i++){
			Node dataSetNode = dataSetNodeList.item(i);
			NodeList tableNodeList = dataSetNode.getChildNodes();
			for(int j=0;j<tableNodeList.getLength();j++){
				Node dataNode = tableNodeList.item(j);
				System.out.println("dataNode :"+dataNode);
				NodeList childNode = dataNode.getChildNodes();
				System.out.println("childNode :"+childNode.item(5).getFirstChild().toString());
			
				user.setUserName(childNode.item(0).getFirstChild().toString());//displayname
				//System.out.println(childNode.item(1).getFirstChild().toString());//mobile
				//System.out.println(childNode.item(2).getFirstChild().toString());//mail
				user.setRegionName(childNode.item(3).getFirstChild().toString());//city
				user.setDeptName(childNode.item(4).getFirstChild().toString());//Company
				user.setUserType(childNode.item(5).getFirstChild().toString());//companyType
				user.setUserId(childNode.item(6).getFirstChild().toString());
				//user.setDeptId(childNode.item(6).getFirstChild().toString());
			}
		}
		logger.info("AD 用户信息："+user.toString());
		return user;
	}
	/**
	 * 获得SD用户组信息
	 * @param username
	 * @return
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	private User getSDUser(String username) throws ServiceException, RemoteException{
		ServiceDeskNoValidateSoap  sdsoap = new ServiceDeskNoValidateLocator().getServiceDeskNoValidateSoap12();
		User user = new User();
		GetGroupInfoByPerSearchCodeResponseGetGroupInfoByPerSearchCodeResult sduser = sdsoap.getGroupInfoByPerSearchCode(username);
		MessageElement [] sduser_Msg = sduser.get_any();
		MessageElement sduserMsgData = sduser_Msg[1];
		//System.out.println("aduserMsgData :"+sduserMsgData);
		NodeList dataSetNodeList = sduserMsgData.getChildNodes();
		for(int i=0;i<dataSetNodeList.getLength();i++){
			Node dataSetNode = dataSetNodeList.item(i);
			//System.out.println("dataSetNode :"+dataSetNode);
			NodeList tableNodeList = dataSetNode.getChildNodes();
			for(int j=0;j<tableNodeList.getLength();j++){
				Node dataNode = tableNodeList.item(j);
				//System.out.println("dataNode :"+dataNode);
				NodeList childNode = dataNode.getChildNodes();
				//System.out.println(childNode.item(1).getFirstChild().toString());//PER_OID
				user.setUserId(childNode.item(1).getFirstChild().toString());//PER_SEARCHCODE
				user.setUserName(childNode.item(2).getFirstChild().toString());//PER_NAME
				System.out.println(childNode.item(3).getFirstChild().toString());
				user.setRegionName(childNode.item(3).getFirstChild().toString());//所属城市
				//System.out.println(childNode.item(4).getFirstChild().toString());//PER_PRIMARYTELNR/mobile
				user.setDeptName(childNode.item(5).getFirstChild().toString());//Company
				user.setUserType(childNode.item(6).getFirstChild().toString());//companyType
				//user.setDeptId(childNode.item(7).getFirstChild().toString());
			}
		}
		
		logger.info("SD 用户信息："+user.toString());
		return user;
	}
	private User getTUser(String username){
		User user = new User();
		if("zzy".equals(username)){
			user.setUserId("zzy");
			user.setUserName("张志远");
			user.setRegionName("辽宁");
			user.setDeptName("辽宁移动公司");
			user.setUserType("01");
		}
		if("zhang".equals(username)){
			user.setUserId("zhang");
			user.setUserName("曹志强");
			user.setRegionName("沈阳");
			user.setDeptName("沈阳");
			user.setUserType("02");
		}
		if("sy".equals(username)){
			user.setUserId("sy");
			user.setUserName("李宝平");
			user.setRegionName("沈阳");
			user.setDeptName("沈阳");
			user.setUserType("03");
		}
		return user;
	}
	
}
