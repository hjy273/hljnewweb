/**
 * GetEquiptMsgResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class GetEquiptMsgResponse  implements java.io.Serializable {
    private org.tempuri.GetEquiptMsgResponseGetEquiptMsgResult getEquiptMsgResult;

    public GetEquiptMsgResponse() {
    }

    public GetEquiptMsgResponse(
           org.tempuri.GetEquiptMsgResponseGetEquiptMsgResult getEquiptMsgResult) {
           this.getEquiptMsgResult = getEquiptMsgResult;
    }


    /**
     * Gets the getEquiptMsgResult value for this GetEquiptMsgResponse.
     * 
     * @return getEquiptMsgResult
     */
    public org.tempuri.GetEquiptMsgResponseGetEquiptMsgResult getGetEquiptMsgResult() {
        return getEquiptMsgResult;
    }


    /**
     * Sets the getEquiptMsgResult value for this GetEquiptMsgResponse.
     * 
     * @param getEquiptMsgResult
     */
    public void setGetEquiptMsgResult(org.tempuri.GetEquiptMsgResponseGetEquiptMsgResult getEquiptMsgResult) {
        this.getEquiptMsgResult = getEquiptMsgResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetEquiptMsgResponse)) return false;
        GetEquiptMsgResponse other = (GetEquiptMsgResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getEquiptMsgResult==null && other.getGetEquiptMsgResult()==null) || 
             (this.getEquiptMsgResult!=null &&
              this.getEquiptMsgResult.equals(other.getGetEquiptMsgResult())));
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
        if (getGetEquiptMsgResult() != null) {
            _hashCode += getGetEquiptMsgResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetEquiptMsgResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">getEquiptMsgResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getEquiptMsgResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "getEquiptMsgResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">>getEquiptMsgResponse>getEquiptMsgResult"));
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
