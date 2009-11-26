/**
 * SearchFilters.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package msnydera.swe645.service.ejb;

public class SearchFilters  implements java.io.Serializable {
    private java.util.Calendar dateOfTrip;

    private java.lang.String departureLocation;

    private java.lang.String destinationLocation;

    public SearchFilters() {
    }

    public SearchFilters(
           java.util.Calendar dateOfTrip,
           java.lang.String departureLocation,
           java.lang.String destinationLocation) {
           this.dateOfTrip = dateOfTrip;
           this.departureLocation = departureLocation;
           this.destinationLocation = destinationLocation;
    }


    /**
     * Gets the dateOfTrip value for this SearchFilters.
     * 
     * @return dateOfTrip
     */
    public java.util.Calendar getDateOfTrip() {
        return dateOfTrip;
    }


    /**
     * Sets the dateOfTrip value for this SearchFilters.
     * 
     * @param dateOfTrip
     */
    public void setDateOfTrip(java.util.Calendar dateOfTrip) {
        this.dateOfTrip = dateOfTrip;
    }


    /**
     * Gets the departureLocation value for this SearchFilters.
     * 
     * @return departureLocation
     */
    public java.lang.String getDepartureLocation() {
        return departureLocation;
    }


    /**
     * Sets the departureLocation value for this SearchFilters.
     * 
     * @param departureLocation
     */
    public void setDepartureLocation(java.lang.String departureLocation) {
        this.departureLocation = departureLocation;
    }


    /**
     * Gets the destinationLocation value for this SearchFilters.
     * 
     * @return destinationLocation
     */
    public java.lang.String getDestinationLocation() {
        return destinationLocation;
    }


    /**
     * Sets the destinationLocation value for this SearchFilters.
     * 
     * @param destinationLocation
     */
    public void setDestinationLocation(java.lang.String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SearchFilters)) return false;
        SearchFilters other = (SearchFilters) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dateOfTrip==null && other.getDateOfTrip()==null) || 
             (this.dateOfTrip!=null &&
              this.dateOfTrip.equals(other.getDateOfTrip()))) &&
            ((this.departureLocation==null && other.getDepartureLocation()==null) || 
             (this.departureLocation!=null &&
              this.departureLocation.equals(other.getDepartureLocation()))) &&
            ((this.destinationLocation==null && other.getDestinationLocation()==null) || 
             (this.destinationLocation!=null &&
              this.destinationLocation.equals(other.getDestinationLocation())));
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
        if (getDateOfTrip() != null) {
            _hashCode += getDateOfTrip().hashCode();
        }
        if (getDepartureLocation() != null) {
            _hashCode += getDepartureLocation().hashCode();
        }
        if (getDestinationLocation() != null) {
            _hashCode += getDestinationLocation().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SearchFilters.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ejb.service.swe645.msnydera/", "searchFilters"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateOfTrip");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dateOfTrip"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departureLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departureLocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destinationLocation"));
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

}
