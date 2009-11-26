/**
 * Flight.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package msnydera.swe645.service.ejb;

public class Flight  implements java.io.Serializable {
    private msnydera.swe645.service.ejb.Airplane airplane;

    private int availableSeats;

    private double cost;

    private msnydera.swe645.service.ejb.Airport departureAirport;

    private java.util.Calendar departureDate;

    private msnydera.swe645.service.ejb.Airport destinationAirport;

    private int id;

    public Flight() {
    }

    public Flight(
           msnydera.swe645.service.ejb.Airplane airplane,
           int availableSeats,
           double cost,
           msnydera.swe645.service.ejb.Airport departureAirport,
           java.util.Calendar departureDate,
           msnydera.swe645.service.ejb.Airport destinationAirport,
           int id) {
           this.airplane = airplane;
           this.availableSeats = availableSeats;
           this.cost = cost;
           this.departureAirport = departureAirport;
           this.departureDate = departureDate;
           this.destinationAirport = destinationAirport;
           this.id = id;
    }


    /**
     * Gets the airplane value for this Flight.
     * 
     * @return airplane
     */
    public msnydera.swe645.service.ejb.Airplane getAirplane() {
        return airplane;
    }


    /**
     * Sets the airplane value for this Flight.
     * 
     * @param airplane
     */
    public void setAirplane(msnydera.swe645.service.ejb.Airplane airplane) {
        this.airplane = airplane;
    }


    /**
     * Gets the availableSeats value for this Flight.
     * 
     * @return availableSeats
     */
    public int getAvailableSeats() {
        return availableSeats;
    }


    /**
     * Sets the availableSeats value for this Flight.
     * 
     * @param availableSeats
     */
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }


    /**
     * Gets the cost value for this Flight.
     * 
     * @return cost
     */
    public double getCost() {
        return cost;
    }


    /**
     * Sets the cost value for this Flight.
     * 
     * @param cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }


    /**
     * Gets the departureAirport value for this Flight.
     * 
     * @return departureAirport
     */
    public msnydera.swe645.service.ejb.Airport getDepartureAirport() {
        return departureAirport;
    }


    /**
     * Sets the departureAirport value for this Flight.
     * 
     * @param departureAirport
     */
    public void setDepartureAirport(msnydera.swe645.service.ejb.Airport departureAirport) {
        this.departureAirport = departureAirport;
    }


    /**
     * Gets the departureDate value for this Flight.
     * 
     * @return departureDate
     */
    public java.util.Calendar getDepartureDate() {
        return departureDate;
    }


    /**
     * Sets the departureDate value for this Flight.
     * 
     * @param departureDate
     */
    public void setDepartureDate(java.util.Calendar departureDate) {
        this.departureDate = departureDate;
    }


    /**
     * Gets the destinationAirport value for this Flight.
     * 
     * @return destinationAirport
     */
    public msnydera.swe645.service.ejb.Airport getDestinationAirport() {
        return destinationAirport;
    }


    /**
     * Sets the destinationAirport value for this Flight.
     * 
     * @param destinationAirport
     */
    public void setDestinationAirport(msnydera.swe645.service.ejb.Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }


    /**
     * Gets the id value for this Flight.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this Flight.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Flight)) return false;
        Flight other = (Flight) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.airplane==null && other.getAirplane()==null) || 
             (this.airplane!=null &&
              this.airplane.equals(other.getAirplane()))) &&
            this.availableSeats == other.getAvailableSeats() &&
            this.cost == other.getCost() &&
            ((this.departureAirport==null && other.getDepartureAirport()==null) || 
             (this.departureAirport!=null &&
              this.departureAirport.equals(other.getDepartureAirport()))) &&
            ((this.departureDate==null && other.getDepartureDate()==null) || 
             (this.departureDate!=null &&
              this.departureDate.equals(other.getDepartureDate()))) &&
            ((this.destinationAirport==null && other.getDestinationAirport()==null) || 
             (this.destinationAirport!=null &&
              this.destinationAirport.equals(other.getDestinationAirport()))) &&
            this.id == other.getId();
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
        if (getAirplane() != null) {
            _hashCode += getAirplane().hashCode();
        }
        _hashCode += getAvailableSeats();
        _hashCode += new Double(getCost()).hashCode();
        if (getDepartureAirport() != null) {
            _hashCode += getDepartureAirport().hashCode();
        }
        if (getDepartureDate() != null) {
            _hashCode += getDepartureDate().hashCode();
        }
        if (getDestinationAirport() != null) {
            _hashCode += getDestinationAirport().hashCode();
        }
        _hashCode += getId();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Flight.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ejb.service.swe645.msnydera/", "flight"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("airplane");
        elemField.setXmlName(new javax.xml.namespace.QName("", "airplane"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ejb.service.swe645.msnydera/", "airplane"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("availableSeats");
        elemField.setXmlName(new javax.xml.namespace.QName("", "availableSeats"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departureAirport");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departureAirport"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ejb.service.swe645.msnydera/", "airport"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departureDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "departureDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationAirport");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destinationAirport"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ejb.service.swe645.msnydera/", "airport"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
