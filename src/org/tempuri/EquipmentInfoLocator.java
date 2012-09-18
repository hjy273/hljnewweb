/**
 * EquipmentInfoLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class EquipmentInfoLocator extends org.apache.axis.client.Service implements org.tempuri.EquipmentInfo {

    public EquipmentInfoLocator() {
    }


    public EquipmentInfoLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EquipmentInfoLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for equipmentInfoSoap12
    private java.lang.String equipmentInfoSoap12_address = "http://websrv.nmc.ln.cmcc/equipmentService/equipmentInfo.asmx";

    public java.lang.String getequipmentInfoSoap12Address() {
        return equipmentInfoSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String equipmentInfoSoap12WSDDServiceName = "equipmentInfoSoap12";

    public java.lang.String getequipmentInfoSoap12WSDDServiceName() {
        return equipmentInfoSoap12WSDDServiceName;
    }

    public void setequipmentInfoSoap12WSDDServiceName(java.lang.String name) {
        equipmentInfoSoap12WSDDServiceName = name;
    }

    public org.tempuri.EquipmentInfoSoap getequipmentInfoSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(equipmentInfoSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getequipmentInfoSoap12(endpoint);
    }

    public org.tempuri.EquipmentInfoSoap getequipmentInfoSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.EquipmentInfoSoap12Stub _stub = new org.tempuri.EquipmentInfoSoap12Stub(portAddress, this);
            _stub.setPortName(getequipmentInfoSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setequipmentInfoSoap12EndpointAddress(java.lang.String address) {
        equipmentInfoSoap12_address = address;
    }


    // Use to get a proxy class for equipmentInfoSoap
    private java.lang.String equipmentInfoSoap_address = "http://websrv.nmc.ln.cmcc/equipmentService/equipmentInfo.asmx";

    public java.lang.String getequipmentInfoSoapAddress() {
        return equipmentInfoSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String equipmentInfoSoapWSDDServiceName = "equipmentInfoSoap";

    public java.lang.String getequipmentInfoSoapWSDDServiceName() {
        return equipmentInfoSoapWSDDServiceName;
    }

    public void setequipmentInfoSoapWSDDServiceName(java.lang.String name) {
        equipmentInfoSoapWSDDServiceName = name;
    }

    public org.tempuri.EquipmentInfoSoap getequipmentInfoSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(equipmentInfoSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getequipmentInfoSoap(endpoint);
    }

    public org.tempuri.EquipmentInfoSoap getequipmentInfoSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.EquipmentInfoSoapStub _stub = new org.tempuri.EquipmentInfoSoapStub(portAddress, this);
            _stub.setPortName(getequipmentInfoSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setequipmentInfoSoapEndpointAddress(java.lang.String address) {
        equipmentInfoSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.EquipmentInfoSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.EquipmentInfoSoap12Stub _stub = new org.tempuri.EquipmentInfoSoap12Stub(new java.net.URL(equipmentInfoSoap12_address), this);
                _stub.setPortName(getequipmentInfoSoap12WSDDServiceName());
                return _stub;
            }
            if (org.tempuri.EquipmentInfoSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.EquipmentInfoSoapStub _stub = new org.tempuri.EquipmentInfoSoapStub(new java.net.URL(equipmentInfoSoap_address), this);
                _stub.setPortName(getequipmentInfoSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("equipmentInfoSoap12".equals(inputPortName)) {
            return getequipmentInfoSoap12();
        }
        else if ("equipmentInfoSoap".equals(inputPortName)) {
            return getequipmentInfoSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "equipmentInfo");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "equipmentInfoSoap12"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "equipmentInfoSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("equipmentInfoSoap12".equals(portName)) {
            setequipmentInfoSoap12EndpointAddress(address);
        }
        else 
if ("equipmentInfoSoap".equals(portName)) {
            setequipmentInfoSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
