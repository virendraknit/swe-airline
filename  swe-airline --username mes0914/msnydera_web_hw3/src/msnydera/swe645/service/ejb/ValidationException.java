/**
 * ValidationException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package msnydera.swe645.service.ejb;

public class ValidationException  extends org.apache.axis.AxisFault  implements java.io.Serializable {
    private java.lang.Object[] errorMessages;

    private java.lang.String message1;

    public ValidationException() {
    }

    public ValidationException(
           java.lang.Object[] errorMessages,
           java.lang.String message1) {
        this.errorMessages = errorMessages;
        this.message1 = message1;
    }


    /**
     * Gets the errorMessages value for this ValidationException.
     * 
     * @return errorMessages
     */
    public java.lang.Object[] getErrorMessages() {
        return errorMessages;
    }


    /**
     * Sets the errorMessages value for this ValidationException.
     * 
     * @param errorMessages
     */
    public void setErrorMessages(java.lang.Object[] errorMessages) {
        this.errorMessages = errorMessages;
    }

    public java.lang.Object getErrorMessages(int i) {
        return this.errorMessages[i];
    }

    public void setErrorMessages(int i, java.lang.Object _value) {
        this.errorMessages[i] = _value;
    }


    /**
     * Gets the message1 value for this ValidationException.
     * 
     * @return message1
     */
    public java.lang.String getMessage1() {
        return message1;
    }


    /**
     * Sets the message1 value for this ValidationException.
     * 
     * @param message1
     */
    public void setMessage1(java.lang.String message1) {
        this.message1 = message1;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ValidationException)) return false;
        ValidationException other = (ValidationException) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.errorMessages==null && other.getErrorMessages()==null) || 
             (this.errorMessages!=null &&
              java.util.Arrays.equals(this.errorMessages, other.getErrorMessages()))) &&
            ((this.message1==null && other.getMessage1()==null) || 
             (this.message1!=null &&
              this.message1.equals(other.getMessage1())));
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
        if (getErrorMessages() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrorMessages());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrorMessages(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMessage1() != null) {
            _hashCode += getMessage1().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ValidationException.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ejb.service.swe645.msnydera/", "ValidationException"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorMessages");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errorMessages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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


    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, this);
    }
}
