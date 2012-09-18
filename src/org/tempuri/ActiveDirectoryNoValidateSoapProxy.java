package org.tempuri;

import java.rmi.RemoteException;

public class ActiveDirectoryNoValidateSoapProxy implements
		org.tempuri.ActiveDirectoryNoValidateSoap {
	private String _endpoint = null;
	private org.tempuri.ActiveDirectoryNoValidateSoap activeDirectoryNoValidateSoap = null;

	public ActiveDirectoryNoValidateSoapProxy() {
		_initActiveDirectoryNoValidateSoapProxy();
	}

	public ActiveDirectoryNoValidateSoapProxy(String endpoint) {
		_endpoint = endpoint;
		_initActiveDirectoryNoValidateSoapProxy();
	}

	private void _initActiveDirectoryNoValidateSoapProxy() {
		try {
			activeDirectoryNoValidateSoap = (new org.tempuri.ActiveDirectoryNoValidateLocator())
					.getActiveDirectoryNoValidateSoap();
			if (activeDirectoryNoValidateSoap != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) activeDirectoryNoValidateSoap)
							._setProperty(
									"javax.xml.rpc.service.endpoint.address",
									_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) activeDirectoryNoValidateSoap)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (activeDirectoryNoValidateSoap != null)
			((javax.xml.rpc.Stub) activeDirectoryNoValidateSoap)._setProperty(
					"javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public org.tempuri.ActiveDirectoryNoValidateSoap getActiveDirectoryNoValidateSoap() {
		if (activeDirectoryNoValidateSoap == null)
			_initActiveDirectoryNoValidateSoapProxy();
		return activeDirectoryNoValidateSoap;
	}

	public GetGroupInfoByUsernameResponseGetGroupInfoByUsernameResult getGroupInfoByUsername(
			String usernameConn) throws RemoteException {
		if (activeDirectoryNoValidateSoap == null)
			_initActiveDirectoryNoValidateSoapProxy();
		return activeDirectoryNoValidateSoap
				.getGroupInfoByUsername(usernameConn);
	}

	public GetUserInfoByUsernameResponseGetUserInfoByUsernameResult getUserInfoByUsername(
			String usernameConn) throws RemoteException {
		if (activeDirectoryNoValidateSoap == null)
			_initActiveDirectoryNoValidateSoapProxy();
		return activeDirectoryNoValidateSoap
				.getUserInfoByUsername(usernameConn);

	}

}