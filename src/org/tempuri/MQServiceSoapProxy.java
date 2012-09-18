package org.tempuri;

public class MQServiceSoapProxy implements org.tempuri.MQServiceSoap {
  private String _endpoint = null;
  private org.tempuri.MQServiceSoap mQServiceSoap = null;
  
  public MQServiceSoapProxy() {
    _initMQServiceSoapProxy();
  }
  
  public MQServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initMQServiceSoapProxy();
  }
  
  private void _initMQServiceSoapProxy() {
    try {
      mQServiceSoap = (new org.tempuri.MQServiceLocator()).getMQServiceSoap();
      if (mQServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mQServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mQServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mQServiceSoap != null)
      ((javax.xml.rpc.Stub)mQServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.MQServiceSoap getMQServiceSoap() {
    if (mQServiceSoap == null)
      _initMQServiceSoapProxy();
    return mQServiceSoap;
  }
  
  public boolean sendMessage(java.lang.String title, java.lang.String body) throws java.rmi.RemoteException{
    if (mQServiceSoap == null)
      _initMQServiceSoapProxy();
    return mQServiceSoap.sendMessage(title, body);
  }
  
  
}