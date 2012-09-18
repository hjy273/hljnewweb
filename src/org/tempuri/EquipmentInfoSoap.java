/**
 * EquipmentInfoSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface EquipmentInfoSoap extends java.rmi.Remote {
    public java.lang.String helloWorld() throws java.rmi.RemoteException;
    public org.tempuri.GetSiteInstResponseGetSiteInstResult getSiteInst(java.lang.String siteid) throws java.rmi.RemoteException;
    public org.tempuri.GetEquiptsResponseGetEquiptsResult getEquipts(java.lang.String siteid) throws java.rmi.RemoteException;
    public org.tempuri.GetEquiptMsgResponseGetEquiptMsgResult getEquiptMsg(java.lang.String equipid) throws java.rmi.RemoteException;
}
