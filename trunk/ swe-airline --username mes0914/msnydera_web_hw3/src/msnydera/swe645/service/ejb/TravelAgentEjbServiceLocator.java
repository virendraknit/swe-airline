/**
 * TravelAgentEjbServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package msnydera.swe645.service.ejb;

public class TravelAgentEjbServiceLocator extends org.apache.axis.client.Service implements msnydera.swe645.service.ejb.TravelAgentEjbService {

    public TravelAgentEjbServiceLocator() {
    }


    public TravelAgentEjbServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TravelAgentEjbServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TravelAgentEjbPort
    private java.lang.String TravelAgentEjbPort_address = "http://127.0.0.1:8080/msnydera_hw3-msnydera_session_hw3/TravelAgentEjb";

    public java.lang.String getTravelAgentEjbPortAddress() {
        return TravelAgentEjbPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TravelAgentEjbPortWSDDServiceName = "TravelAgentEjbPort";

    public java.lang.String getTravelAgentEjbPortWSDDServiceName() {
        return TravelAgentEjbPortWSDDServiceName;
    }

    public void setTravelAgentEjbPortWSDDServiceName(java.lang.String name) {
        TravelAgentEjbPortWSDDServiceName = name;
    }

    public msnydera.swe645.service.ejb.TravelAgentEjb getTravelAgentEjbPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TravelAgentEjbPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTravelAgentEjbPort(endpoint);
    }

    public msnydera.swe645.service.ejb.TravelAgentEjb getTravelAgentEjbPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            msnydera.swe645.service.ejb.TravelAgentEjbBindingStub _stub = new msnydera.swe645.service.ejb.TravelAgentEjbBindingStub(portAddress, this);
            _stub.setPortName(getTravelAgentEjbPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTravelAgentEjbPortEndpointAddress(java.lang.String address) {
        TravelAgentEjbPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (msnydera.swe645.service.ejb.TravelAgentEjb.class.isAssignableFrom(serviceEndpointInterface)) {
                msnydera.swe645.service.ejb.TravelAgentEjbBindingStub _stub = new msnydera.swe645.service.ejb.TravelAgentEjbBindingStub(new java.net.URL(TravelAgentEjbPort_address), this);
                _stub.setPortName(getTravelAgentEjbPortWSDDServiceName());
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
        if ("TravelAgentEjbPort".equals(inputPortName)) {
            return getTravelAgentEjbPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ejb.service.swe645.msnydera/", "TravelAgentEjbService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ejb.service.swe645.msnydera/", "TravelAgentEjbPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TravelAgentEjbPort".equals(portName)) {
            setTravelAgentEjbPortEndpointAddress(address);
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
