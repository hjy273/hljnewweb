/**
 * GetGroupInfoByPerSearchCodeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class GetGroupInfoByPerSearchCodeResponse  implements java.io.Serializable {
    private org.tempuri.GetGroupInfoByPerSearchCodeResponseGetGroupInfoByPerSearchCodeResult getGroupInfoByPerSearchCodeResult;

    public GetGroupInfoByPerSearchCodeResponse() {
    }

    public GetGroupInfoByPerSearchCodeResponse(
           org.tempuri.GetGroupInfoByPerSearchCodeResponseGetGroupInfoByPerSearchCodeResult getGroupInfoByPerSearchCodeResult) {
           this.getGroupInfoByPerSearchCodeResult = getGroupInfoByPerSearchCodeResult;
    }


    /**
     * Gets the getGroupInfoByPerSearchCodeResult value for this GetGroupInfoByPerSearchCodeResponse.
     * 
     * @return getGroupInfoByPerSearchCodeResult
     */
    public org.tempuri.GetGroupInfoByPerSearchCodeResponseGetGroupInfoByPerSearchCodeResult getGetGroupInfoByPerSearchCodeResult() {
        return getGroupInfoByPerSearchCodeResult;
    }


    /**
     * Sets the getGroupInfoByPerSearchCodeResult value for this GetGroupInfoByPerSearchCodeResponse.
     * 
     * @param getGroupInfoByPerSearchCodeResult
     */
    public void setGetGroupInfoByPerSearchCodeResult(org.tempuri.GetGroupInfoByPerSearchCodeResponseGetGroupInfoByPerSearchCodeResult getGroupInfoByPerSearchCodeResult) {
        this.getGroupInfoByPerSearchCodeResult = getGroupInfoByPerSearchCodeResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetGroupInfoByPerSearchCodeResponse)) return false;
        GetGroupInfoByPerSearchCodeResponse other = (GetGroupInfoByPerSearchCodeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getGroupInfoByPerSearchCodeResult==null && other.getGetGroupInfoByPerSearchCodeResult()==null) || 
             (this.getGroupInfoByPerSearchCodeResult!=null &&
              this.getGroupInfoByPerSearchCodeResult.equals(other.getGetGroupInfoByPerSearchCodeResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getGetGroupInfoByPerSearchCodeResult() != null) {
            _hashCode += getGetGroupInfoByPerSearchCodeResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetGroupInfoByPerSearchCodeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetGroupInfoByPerSearchCodeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getGroupInfoByPerSearchCodeResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetGroupInfoByPerSearchCodeResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">>GetGroupInfoByPerSearchCodeResponse>GetGroupInfoByPerSearchCodeResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
