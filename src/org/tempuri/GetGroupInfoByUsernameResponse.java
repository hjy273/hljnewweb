/**
 * GetGroupInfoByUsernameResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class GetGroupInfoByUsernameResponse  implements java.io.Serializable {
    private org.tempuri.GetGroupInfoByUsernameResponseGetGroupInfoByUsernameResult getGroupInfoByUsernameResult;

    public GetGroupInfoByUsernameResponse() {
    }

    public GetGroupInfoByUsernameResponse(
           org.tempuri.GetGroupInfoByUsernameResponseGetGroupInfoByUsernameResult getGroupInfoByUsernameResult) {
           this.getGroupInfoByUsernameResult = getGroupInfoByUsernameResult;
    }


    /**
     * Gets the getGroupInfoByUsernameResult value for this GetGroupInfoByUsernameResponse.
     * 
     * @return getGroupInfoByUsernameResult
     */
    public org.tempuri.GetGroupInfoByUsernameResponseGetGroupInfoByUsernameResult getGetGroupInfoByUsernameResult() {
        return getGroupInfoByUsernameResult;
    }


    /**
     * Sets the getGroupInfoByUsernameResult value for this GetGroupInfoByUsernameResponse.
     * 
     * @param getGroupInfoByUsernameResult
     */
    public void setGetGroupInfoByUsernameResult(org.tempuri.GetGroupInfoByUsernameResponseGetGroupInfoByUsernameResult getGroupInfoByUsernameResult) {
        this.getGroupInfoByUsernameResult = getGroupInfoByUsernameResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetGroupInfoByUsernameResponse)) return false;
        GetGroupInfoByUsernameResponse other = (GetGroupInfoByUsernameResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getGroupInfoByUsernameResult==null && other.getGetGroupInfoByUsernameResult()==null) || 
             (this.getGroupInfoByUsernameResult!=null &&
              this.getGroupInfoByUsernameResult.equals(other.getGetGroupInfoByUsernameResult())));
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
        if (getGetGroupInfoByUsernameResult() != null) {
            _hashCode += getGetGroupInfoByUsernameResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetGroupInfoByUsernameResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetGroupInfoByUsernameResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getGroupInfoByUsernameResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetGroupInfoByUsernameResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">>GetGroupInfoByUsernameResponse>GetGroupInfoByUsernameResult"));
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
