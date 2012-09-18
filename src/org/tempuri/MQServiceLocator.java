/**
 * MQServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

import com.cabletech.commons.config.GisConInfo;

public class MQServiceLocator extends org.apache.axis.client.Service implements org.tempuri.MQService {

    public MQServiceLocator() {
    }


    public MQServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MQServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MQServiceSoap
    private java.lang.String MQServiceSoap_address = GisConInfo.newInstance().getHiddenTroubleUrl();//"http://xt021.nmc.ln.cmcc/MQInfo/MQService.asmx";

    public java.lang.String getMQServiceSoapAddress() {
        return MQServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MQServiceSoapWSDDServiceName = "MQServiceSoap";

    public java.lang.String getMQServiceSoapWSDDServiceName() {
        return MQServiceSoapWSDDServiceName;
    }

    public void setMQServiceSoapWSDDServiceName(java.lang.String name) {
        MQServiceSoapWSDDServiceName = name;
    }

    public org.tempuri.MQServiceSoap getMQServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MQServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMQServiceSoap(endpoint);
    }

    public org.tempuri.MQServiceSoap getMQServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.MQServiceSoapStub _stub = new org.tempuri.MQServiceSoapStub(portAddress, this);
            _stub.setPortName(getMQServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMQServiceSoapEndpointAddress(java.lang.String address) {
        MQServiceSoap_address = address;
    }


    // Use to get a proxy class for MQServiceSoap12
    private java.lang.String MQServiceSoap12_address = GisConInfo.newInstance().getHiddenTroubleUrl();//"http://xt021.nmc.ln.cmcc/MQInfo/MQService.asmx";

    public java.lang.String getMQServiceSoap12Address() {
        return MQServiceSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MQServiceSoap12WSDDServiceName = "MQServiceSoap12";

    public java.lang.String getMQServiceSoap12WSDDServiceName() {
        return MQServiceSoap12WSDDServiceName;
    }

    public void setMQServiceSoap12WSDDServiceName(java.lang.String name) {
        MQServiceSoap12WSDDServiceName = name;
    }

    public org.tempuri.MQServiceSoap getMQServiceSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MQServiceSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMQServiceSoap12(endpoint);
    }

    public org.tempuri.MQServiceSoap getMQServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.MQServiceSoap12Stub _stub = new org.tempuri.MQServiceSoap12Stub(portAddress, this);
            _stub.setPortName(getMQServiceSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMQServiceSoap12EndpointAddress(java.lang.String address) {
        MQServiceSoap12_address = address;
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
            if (org.tempuri.MQServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.MQServiceSoapStub _stub = new org.tempuri.MQServiceSoapStub(new java.net.URL(MQServiceSoap_address), this);
                _stub.setPortName(getMQServiceSoapWSDDServiceName());
                return _stub;
            }
            if (org.tempuri.MQServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.MQServiceSoap12Stub _stub = new org.tempuri.MQServiceSoap12Stub(new java.net.URL(MQServiceSoap12_address), this);
                _stub.setPortName(getMQServiceSoap12WSDDServiceName());
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
        if ("MQServiceSoap".equals(inputPortName)) {
            return getMQServiceSoap();
        }
        else if ("MQServiceSoap12".equals(inputPortName)) {
            return getMQServiceSoap12();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "MQService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "MQServiceSoap"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "MQServiceSoap12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MQServiceSoap".equals(portName)) {
            setMQServiceSoapEndpointAddress(address);
        }
        else 
if ("MQServiceSoap12".equals(portName)) {
            setMQServiceSoap12EndpointAddress(address);
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
