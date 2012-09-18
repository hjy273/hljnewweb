/**
 * ActiveDirectoryNoValidateLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

import com.cabletech.commons.config.GisConInfo;

public class ActiveDirectoryNoValidateLocator extends org.apache.axis.client.Service implements org.tempuri.ActiveDirectoryNoValidate {

    public ActiveDirectoryNoValidateLocator() {
    }


    public ActiveDirectoryNoValidateLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ActiveDirectoryNoValidateLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ActiveDirectoryNoValidateSoap12
    private java.lang.String ActiveDirectoryNoValidateSoap12_address = GisConInfo.newInstance().getAdServiceUrl();//"http://websrv.nmc.ln.cmcc/BPM/ActiveDirectoryNoValidate.asmx";

    public java.lang.String getActiveDirectoryNoValidateSoap12Address() {
        return ActiveDirectoryNoValidateSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ActiveDirectoryNoValidateSoap12WSDDServiceName = "ActiveDirectoryNoValidateSoap12";

    public java.lang.String getActiveDirectoryNoValidateSoap12WSDDServiceName() {
        return ActiveDirectoryNoValidateSoap12WSDDServiceName;
    }

    public void setActiveDirectoryNoValidateSoap12WSDDServiceName(java.lang.String name) {
        ActiveDirectoryNoValidateSoap12WSDDServiceName = name;
    }

    public org.tempuri.ActiveDirectoryNoValidateSoap getActiveDirectoryNoValidateSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ActiveDirectoryNoValidateSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getActiveDirectoryNoValidateSoap12(endpoint);
    }

    public org.tempuri.ActiveDirectoryNoValidateSoap getActiveDirectoryNoValidateSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.ActiveDirectoryNoValidateSoap12Stub _stub = new org.tempuri.ActiveDirectoryNoValidateSoap12Stub(portAddress, this);
            _stub.setPortName(getActiveDirectoryNoValidateSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setActiveDirectoryNoValidateSoap12EndpointAddress(java.lang.String address) {
        ActiveDirectoryNoValidateSoap12_address = address;
    }


    // Use to get a proxy class for ActiveDirectoryNoValidateSoap
    private java.lang.String ActiveDirectoryNoValidateSoap_address = GisConInfo.newInstance().getAdServiceUrl();//"http://websrv.nmc.ln.cmcc/BPM/ActiveDirectoryNoValidate.asmx";

    public java.lang.String getActiveDirectoryNoValidateSoapAddress() {
        return ActiveDirectoryNoValidateSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ActiveDirectoryNoValidateSoapWSDDServiceName = "ActiveDirectoryNoValidateSoap";

    public java.lang.String getActiveDirectoryNoValidateSoapWSDDServiceName() {
        return ActiveDirectoryNoValidateSoapWSDDServiceName;
    }

    public void setActiveDirectoryNoValidateSoapWSDDServiceName(java.lang.String name) {
        ActiveDirectoryNoValidateSoapWSDDServiceName = name;
    }

    public org.tempuri.ActiveDirectoryNoValidateSoap getActiveDirectoryNoValidateSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ActiveDirectoryNoValidateSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getActiveDirectoryNoValidateSoap(endpoint);
    }

    public org.tempuri.ActiveDirectoryNoValidateSoap getActiveDirectoryNoValidateSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.ActiveDirectoryNoValidateSoapStub _stub = new org.tempuri.ActiveDirectoryNoValidateSoapStub(portAddress, this);
            _stub.setPortName(getActiveDirectoryNoValidateSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setActiveDirectoryNoValidateSoapEndpointAddress(java.lang.String address) {
        ActiveDirectoryNoValidateSoap_address = address;
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
            if (org.tempuri.ActiveDirectoryNoValidateSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.ActiveDirectoryNoValidateSoap12Stub _stub = new org.tempuri.ActiveDirectoryNoValidateSoap12Stub(new java.net.URL(ActiveDirectoryNoValidateSoap12_address), this);
                _stub.setPortName(getActiveDirectoryNoValidateSoap12WSDDServiceName());
                return _stub;
            }
            if (org.tempuri.ActiveDirectoryNoValidateSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.ActiveDirectoryNoValidateSoapStub _stub = new org.tempuri.ActiveDirectoryNoValidateSoapStub(new java.net.URL(ActiveDirectoryNoValidateSoap_address), this);
                _stub.setPortName(getActiveDirectoryNoValidateSoapWSDDServiceName());
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
        if ("ActiveDirectoryNoValidateSoap12".equals(inputPortName)) {
            return getActiveDirectoryNoValidateSoap12();
        }
        else if ("ActiveDirectoryNoValidateSoap".equals(inputPortName)) {
            return getActiveDirectoryNoValidateSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "ActiveDirectoryNoValidate");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "ActiveDirectoryNoValidateSoap12"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "ActiveDirectoryNoValidateSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ActiveDirectoryNoValidateSoap12".equals(portName)) {
            setActiveDirectoryNoValidateSoap12EndpointAddress(address);
        }
        else 
if ("ActiveDirectoryNoValidateSoap".equals(portName)) {
            setActiveDirectoryNoValidateSoapEndpointAddress(address);
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
