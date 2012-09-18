package org.tempuri;

import java.rmi.RemoteException;

public class EquipmentInfoSoapProxy implements org.tempuri.EquipmentInfoSoap {
	private String _endpoint = null;
	private org.tempuri.EquipmentInfoSoap equipmentInfoSoap = null;

	public EquipmentInfoSoapProxy() {
		_initEquipmentInfoSoapProxy();
	}

	public EquipmentInfoSoapProxy(String endpoint) {
		_endpoint = endpoint;
		_initEquipmentInfoSoapProxy();
	}

	private void _initEquipmentInfoSoapProxy() {
		try {
			equipmentInfoSoap = (new org.tempuri.EquipmentInfoLocator())
					.getequipmentInfoSoap();
			if (equipmentInfoSoap != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) equipmentInfoSoap)
							._setProperty(
									"javax.xml.rpc.service.endpoint.address",
									_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) equipmentInfoSoap)
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
		if (equipmentInfoSoap != null)
			((javax.xml.rpc.Stub) equipmentInfoSoap)._setProperty(
					"javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public org.tempuri.EquipmentInfoSoap getEquipmentInfoSoap() {
		if (equipmentInfoSoap == null)
			_initEquipmentInfoSoapProxy();
		return equipmentInfoSoap;
	}

	public GetEquiptMsgResponseGetEquiptMsgResult getEquiptMsg(String equipid)
			throws RemoteException {
		if (equipmentInfoSoap == null)
			_initEquipmentInfoSoapProxy();
		return equipmentInfoSoap.getEquiptMsg(equipid);
	}

	public GetEquiptsResponseGetEquiptsResult getEquipts(String siteid)
			throws RemoteException {
		if (equipmentInfoSoap == null)
			_initEquipmentInfoSoapProxy();
		return equipmentInfoSoap.getEquipts(siteid);
	}

	public GetSiteInstResponseGetSiteInstResult getSiteInst(String siteid)
			throws RemoteException {
		if (equipmentInfoSoap == null)
			_initEquipmentInfoSoapProxy();
		return equipmentInfoSoap.getSiteInst(siteid);
	}

	public String helloWorld() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}