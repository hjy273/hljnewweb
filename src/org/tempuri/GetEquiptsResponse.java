/**
 * GetEquiptsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class GetEquiptsResponse  implements java.io.Serializable {
    private org.tempuri.GetEquiptsResponseGetEquiptsResult getEquiptsResult;

    public GetEquiptsResponse() {
    }

    public GetEquiptsResponse(
           org.tempuri.GetEquiptsResponseGetEquiptsResult getEquiptsResult) {
           this.getEquiptsResult = getEquiptsResult;
    }


    /**
     * Gets the getEquiptsResult value for this GetEquiptsResponse.
     * 
     * @return getEquiptsResult
     */
    public org.tempuri.GetEquiptsResponseGetEquiptsResult getGetEquiptsResult() {
        return getEquiptsResult;
    }


    /**
     * Sets the getEquiptsResult value for this GetEquiptsResponse.
     * 
     * @param getEquiptsResult
     */
    public void setGetEquiptsResult(org.tempuri.GetEquiptsResponseGetEquiptsResult getEquiptsResult) {
        this.getEquiptsResult = getEquiptsResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetEquiptsResponse)) return false;
        GetEquiptsResponse other = (GetEquiptsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getEquiptsResult==null && other.getGetEquiptsResult()==null) || 
             (this.getEquiptsResult!=null &&
              this.getEquiptsResult.equals(other.getGetEquiptsResult())));
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
        if (getGetEquiptsResult() != null) {
            _hashCode += getGetEquiptsResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetEquiptsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">getEquiptsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getEquiptsResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "getEquiptsResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">>getEquiptsResponse>getEquiptsResult"));
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
