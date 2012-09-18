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
import org.tempuri.ServiceDeskNoValidateLocator;
import org.tempuri.ServiceDeskNoValidateSoap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cabletech.lnsso.GetUserGroup;

public class GetUserGroupImpl implements GetUserGroup {
	private Logger logger = Logger.getLogger("GetUserGroupImpl");
	public List getUserGroupList(String username, String usertype) {
		if(usertype.equals("ad")){
			List ugroups = new ArrayList();
			try {
				ugroups = getADUserGroup(username);
			} catch (RemoteException e) {
				e.printStackTrace();
				logger.error("获得AD用户的组信息时，连接远程服务异常："+e);
				return ugroups;
			} catch (ServiceException e) {
				e.printStackTrace();
				logger.error("获得AD用户的组信息时，Service服务异常："+e);
				return ugroups;
			}
			return ugroups;
		}else if(usertype.equals("sd")){
			List ugroups = new ArrayList();
			try {
				ugroups = getADUserGroup(username);
			} catch (RemoteException e) {
				e.printStackTrace();
				logger.error("获得SD用户的组信息时，连接远程服务异常："+e);
				return ugroups;
			} catch (ServiceException e) {
				e.printStackTrace();
				logger.error("获得SD用户的组信息时，Service服务异常："+e);
				return ugroups;
			}
			return ugroups;
		}else if(usertype.equals("t")){
			List ugroups = new ArrayList();
			ugroups = getTUserGroup(username);
			return ugroups;
		}else{
			return null;
		}
	}
	
	/**
	 * 获得AD用户组
	 * @param userId
	 * @return
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	private List getADUserGroup(String userId) throws RemoteException, ServiceException {
		ActiveDirectoryNoValidateSoap adsoap = new ActiveDirectoryNoValidateLocator().getActiveDirectoryNoValidateSoap12();
		GetGroupInfoByUsernameResponseGetGroupInfoByUsernameResult res = adsoap.getGroupInfoByUsername(userId);
		MessageElement []userg_Msg = res.get_any();
		MessageElement aduserMsgData = userg_Msg[1];
		NodeList dataSetNodeList = aduserMsgData.getChildNodes();
		List userGroups = new ArrayList();
		for(int i=0;i<dataSetNodeList.getLength();i++){
			Node dataSetNode = dataSetNodeList.item(i);
			NodeList tableNodeList = dataSetNode.getChildNodes();
			for(int j=0;j<tableNodeList.getLength();j++){
				Node dataNode = tableNodeList.item(j);
				System.out.println("dataNode :"+dataNode);
				NodeList childNode = dataNode.getChildNodes();
				userGroups.add(childNode.item(0).getFirstChild().toString());
				System.out.println(childNode.item(0).getFirstChild().toString());
				//System.out.println(childNode.item(1).getFirstChild().toString());
				System.out.println(childNode.item(2).getFirstChild().toString());
				//System.out.println(childNode.item(3).getFirstChild().toString());
			}
		}
		logger.info("AD UserGroup "+userGroups);
		return userGroups;
	}
	/**
	 * 获得SD用户组
	 * @param userid
	 * @return
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	private List getSDUserGroup(String userid)throws RemoteException, ServiceException{
		ServiceDeskNoValidateSoap  sdsoap = new ServiceDeskNoValidateLocator().getServiceDeskNoValidateSoap12();
		GetGroupInfoByPerSearchCodeResponseGetGroupInfoByPerSearchCodeResult res = sdsoap.getGroupInfoByPerSearchCode(userid);
		MessageElement []userg_Msg = res.get_any();
		MessageElement aduserMsgData = userg_Msg[1];
		NodeList dataSetNodeList = aduserMsgData.getChildNodes();
		List userGroups = new ArrayList();
		for(int i=0;i<dataSetNodeList.getLength();i++){
			Node dataSetNode = dataSetNodeList.item(i);
			NodeList tableNodeList = dataSetNode.getChildNodes();
			for(int j=0;j<tableNodeList.getLength();j++){
				Node dataNode = tableNodeList.item(j);
				logger.info("dataNode :"+dataNode);
				NodeList childNode = dataNode.getChildNodes();
				userGroups.add(childNode.item(0).getFirstChild().toString());
				logger.info(childNode.item(0).getFirstChild().toString());
				//System.out.println(childNode.item(1).getFirstChild().toString());
				logger.info(childNode.item(2).getFirstChild().toString());
				//System.out.println(childNode.item(3).getFirstChild().toString());
			}
		}
		return userGroups;
	}
	private List getTUserGroup(String username){
		List userGroups = new ArrayList();
		if("zzy".equals(username)){
			userGroups.add("省公司系统管理组");
		}
		if("zhang".equals(username)){
			userGroups.add("分公司系统管理组");
		}
		if("sy".equals(username)){
			userGroups.add("工程局维护中心用户组");
		}
		return userGroups;
	}
}
