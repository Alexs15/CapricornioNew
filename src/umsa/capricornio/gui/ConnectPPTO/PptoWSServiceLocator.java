/**
 * PptoWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package umsa.capricornio.gui.ConnectPPTO;

public class PptoWSServiceLocator extends org.apache.axis.client.Service implements umsa.capricornio.gui.ConnectPPTO.PptoWSService {

    public PptoWSServiceLocator() {
    }


    public PptoWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PptoWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PptoWS
    private java.lang.String PptoWS_address = "http://200.7.160.26/axis/PPTO/PptoWS.jws";

    public java.lang.String getPptoWSAddress() {
        return PptoWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PptoWSWSDDServiceName = "PptoWS";

    public java.lang.String getPptoWSWSDDServiceName() {
        return PptoWSWSDDServiceName;
    }

    public void setPptoWSWSDDServiceName(java.lang.String name) {
        PptoWSWSDDServiceName = name;
    }

    public umsa.capricornio.gui.ConnectPPTO.PptoWS_PortType getPptoWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PptoWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPptoWS(endpoint);
    }

    public umsa.capricornio.gui.ConnectPPTO.PptoWS_PortType getPptoWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            umsa.capricornio.gui.ConnectPPTO.PptoWSSoapBindingStub _stub = new umsa.capricornio.gui.ConnectPPTO.PptoWSSoapBindingStub(portAddress, this);
            _stub.setPortName(getPptoWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPptoWSEndpointAddress(java.lang.String address) {
        PptoWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (umsa.capricornio.gui.ConnectPPTO.PptoWS_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                umsa.capricornio.gui.ConnectPPTO.PptoWSSoapBindingStub _stub = new umsa.capricornio.gui.ConnectPPTO.PptoWSSoapBindingStub(new java.net.URL(PptoWS_address), this);
                _stub.setPortName(getPptoWSWSDDServiceName());
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
        if ("PptoWS".equals(inputPortName)) {
            return getPptoWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://200.7.160.26/axis/PPTO/PptoWS.jws", "PptoWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://200.7.160.26/axis/PPTO/PptoWS.jws", "PptoWS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PptoWS".equals(portName)) {
            setPptoWSEndpointAddress(address);
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
