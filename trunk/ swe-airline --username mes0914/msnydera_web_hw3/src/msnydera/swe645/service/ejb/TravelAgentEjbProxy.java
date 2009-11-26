package msnydera.swe645.service.ejb;

public class TravelAgentEjbProxy implements msnydera.swe645.service.ejb.TravelAgentEjb {
  private String _endpoint = null;
  private msnydera.swe645.service.ejb.TravelAgentEjb travelAgentEjb = null;
  
  public TravelAgentEjbProxy() {
    _initTravelAgentEjbProxy();
  }
  
  public TravelAgentEjbProxy(String endpoint) {
    _endpoint = endpoint;
    _initTravelAgentEjbProxy();
  }
  
  private void _initTravelAgentEjbProxy() {
    try {
      travelAgentEjb = (new msnydera.swe645.service.ejb.TravelAgentEjbServiceLocator()).getTravelAgentEjbPort();
      if (travelAgentEjb != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)travelAgentEjb)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)travelAgentEjb)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (travelAgentEjb != null)
      ((javax.xml.rpc.Stub)travelAgentEjb)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public msnydera.swe645.service.ejb.TravelAgentEjb getTravelAgentEjb() {
    if (travelAgentEjb == null)
      _initTravelAgentEjbProxy();
    return travelAgentEjb;
  }
  
  public msnydera.swe645.service.ejb.Flight[] search(msnydera.swe645.service.ejb.SearchFilters arg0) throws java.rmi.RemoteException, msnydera.swe645.service.ejb.ValidationException, msnydera.swe645.service.ejb.DataAccessException{
    if (travelAgentEjb == null)
      _initTravelAgentEjbProxy();
    return travelAgentEjb.search(arg0);
  }
  
  
}