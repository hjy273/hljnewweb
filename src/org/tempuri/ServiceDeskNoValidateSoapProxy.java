package org.tempuri;

import java.rmi.RemoteException;

public class ServiceDeskNoValidateSoapProxy implements
		org.tempuri.ServiceDeskNoValidateSoap {
	private String _endpoint = null;
	private org.tempuri.ServiceDeskNoValidateSoap serviceDeskNoValidateSoap = null;

	public ServiceDeskNoValidateSoapProxy() {
		_initServiceDeskNoValidateSoapProxy();
	}

	public ServiceDeskNoValidateSoapProxy(String endpoint) {
		_endpoint = endpoint;
		_initServiceDeskNoValidateSoapProxy();
	}

	private void _initServiceDeskNoValidateSoapProxy() {
		try {
			serviceDeskNoValidateSoap = (new org.tempuri.ServiceDeskNoValidateLocator())
					.getServiceDeskNoValidateSoap();
			if (serviceDeskNoValidateSoap != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) serviceDeskNoValidateSoap)
							._setProperty(
									"javax.xml.rpc.service.endpoint.address",
									_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) serviceDeskNoValidateSoap)
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
		if (serviceDeskNoValidateSoap != null)
			((javax.xml.rpc.Stub) serviceDeskNoValidateSoap)._setProperty(
					"javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public org.tempuri.ServiceDeskNoValidateSoap getServiceDeskNoValidateSoap() {
		if (serviceDeskNoValidateSoap == null)
			_initServiceDeskNoValidateSoapProxy();
		return serviceDeskNoValidateSoap;
	}

	public GetGroupInfoByPerSearchCodeResponseGetGroupInfoByPerSearchCodeResult getGroupInfoByPerSearchCode(
			String perSearchCodeConn) throws RemoteException {
		if (serviceDeskNoValidateSoap == null)
			_initServiceDeskNoValidateSoapProxy();
		return serviceDeskNoValidateSoap
				.getGroupInfoByPerSearchCode(perSearchCodeConn);
	}

	public GetPerInfoByPerSearchCodeResponseGetPerInfoByPerSearchCodeResult getPerInfoByPerSearchCode(
			String perSearchCodeConn) throws RemoteException {
		if (serviceDeskNoValidateSoap == null)
			_initServiceDeskNoValidateSoapProxy();
		return serviceDeskNoValidateSoap
				.getPerInfoByPerSearchCode(perSearchCodeConn);
	}

}