/**
 * ServiceDeskNoValidateLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

import com.cabletech.commons.config.GisConInfo;

public class ServiceDeskNoValidateLocator extends org.apache.axis.client.Service implements org.tempuri.ServiceDeskNoValidate {

    public ServiceDeskNoValidateLocator() {
    }


    public ServiceDeskNoValidateLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServiceDeskNoValidateLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServiceDeskNoValidateSoap12
    private java.lang.String ServiceDeskNoValidateSoap12_address = GisConInfo.newInstance().getSdServiceUrl();//"http://websrv.nmc.ln.cmcc/BPM/ServiceDeskNoValidate.asmx";

    public java.lang.String getServiceDeskNoValidateSoap12Address() {
        return ServiceDeskNoValidateSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServiceDeskNoValidateSoap12WSDDServiceName = "ServiceDeskNoValidateSoap12";

    public java.lang.String getServiceDeskNoValidateSoap12WSDDServiceName() {
        return ServiceDeskNoValidateSoap12WSDDServiceName;
    }

    public void setServiceDeskNoValidateSoap12WSDDServiceName(java.lang.String name) {
        ServiceDeskNoValidateSoap12WSDDServiceName = name;
    }

    public org.tempuri.ServiceDeskNoValidateSoap getServiceDeskNoValidateSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServiceDeskNoValidateSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServiceDeskNoValidateSoap12(endpoint);
    }

    public org.tempuri.ServiceDeskNoValidateSoap getServiceDeskNoValidateSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.ServiceDeskNoValidateSoap12Stub _stub = new org.tempuri.ServiceDeskNoValidateSoap12Stub(portAddress, this);
            _stub.setPortName(getServiceDeskNoValidateSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServiceDeskNoValidateSoap12EndpointAddress(java.lang.String address) {
        ServiceDeskNoValidateSoap12_address = address;
    }


    // Use to get a proxy class for ServiceDeskNoValidateSoap
    private java.lang.String ServiceDeskNoValidateSoap_address = GisConInfo.newInstance().getSdServiceUrl();//"http://websrv.nmc.ln.cmcc/BPM/ServiceDeskNoValidate.asmx";

    public java.lang.String getServiceDeskNoValidateSoapAddress() {
        return ServiceDeskNoValidateSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServiceDeskNoValidateSoapWSDDServiceName = "ServiceDeskNoValidateSoap";

    public java.lang.String getServiceDeskNoValidateSoapWSDDServiceName() {
        return ServiceDeskNoValidateSoapWSDDServiceName;
    }

    public void setServiceDeskNoValidateSoapWSDDServiceName(java.lang.String name) {
        ServiceDeskNoValidateSoapWSDDServiceName = name;
    }

    public org.tempuri.ServiceDeskNoValidateSoap getServiceDeskNoValidateSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServiceDeskNoValidateSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServiceDeskNoValidateSoap(endpoint);
    }

    public org.tempuri.ServiceDeskNoValidateSoap getServiceDeskNoValidateSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.ServiceDeskNoValidateSoapStub _stub = new org.tempuri.ServiceDeskNoValidateSoapStub(portAddress, this);
            _stub.setPortName(getServiceDeskNoValidateSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServiceDeskNoValidateSoapEndpointAddress(java.lang.String address) {
        ServiceDeskNoValidateSoap_address = address;
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
            if (org.tempuri.ServiceDeskNoValidateSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.ServiceDeskNoValidateSoap12Stub _stub = new org.tempuri.ServiceDeskNoValidateSoap12Stub(new java.net.URL(ServiceDeskNoValidateSoap12_address), this);
                _stub.setPortName(getServiceDeskNoValidateSoap12WSDDServiceName());
                return _stub;
            }
            if (org.tempuri.ServiceDeskNoValidateSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.ServiceDeskNoValidateSoapStub _stub = new org.tempuri.ServiceDeskNoValidateSoapStub(new java.net.URL(ServiceDeskNoValidateSoap_address), this);
                _stub.setPortName(getServiceDeskNoValidateSoapWSDDServiceName());
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
        if ("ServiceDeskNoValidateSoap12".equals(inputPortName)) {
            return getServiceDeskNoValidateSoap12();
        }
        else if ("ServiceDeskNoValidateSoap".equals(inputPortName)) {
            return getServiceDeskNoValidateSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "ServiceDeskNoValidate");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "ServiceDeskNoValidateSoap12"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "ServiceDeskNoValidateSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServiceDeskNoValidateSoap12".equals(portName)) {
            setServiceDeskNoValidateSoap12EndpointAddress(address);
        }
        else 
if ("ServiceDeskNoValidateSoap".equals(portName)) {
            setServiceDeskNoValidateSoapEndpointAddress(address);
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
