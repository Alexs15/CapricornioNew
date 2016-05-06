/**
 * AriesWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package umsa.capricornio.gui.ConnectARIES;

public class AriesWSServiceLocator extends org.apache.axis.client.Service implements umsa.capricornio.gui.ConnectARIES.AriesWSService {

    public AriesWSServiceLocator() {
    }


    public AriesWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AriesWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AriesWS
    private java.lang.String AriesWS_address = "http://200.7.160.28/axis/CAJA_PAG/AriesWS.jws";

    public java.lang.String getAriesWSAddress() {
        return AriesWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AriesWSWSDDServiceName = "AriesWS";

    public java.lang.String getAriesWSWSDDServiceName() {
        return AriesWSWSDDServiceName;
    }

    public void setAriesWSWSDDServiceName(java.lang.String name) {
        AriesWSWSDDServiceName = name;
    }

    public umsa.capricornio.gui.ConnectARIES.AriesWS_PortType getAriesWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AriesWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAriesWS(endpoint);
    }

    public umsa.capricornio.gui.ConnectARIES.AriesWS_PortType getAriesWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            umsa.capricornio.gui.ConnectARIES.AriesWSSoapBindingStub _stub = new umsa.capricornio.gui.ConnectARIES.AriesWSSoapBindingStub(portAddress, this);
            _stub.setPortName(getAriesWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAriesWSEndpointAddress(java.lang.String address) {
        AriesWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (umsa.capricornio.gui.ConnectARIES.AriesWS_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                umsa.capricornio.gui.ConnectARIES.AriesWSSoapBindingStub _stub = new umsa.capricornio.gui.ConnectARIES.AriesWSSoapBindingStub(new java.net.URL(AriesWS_address), this);
                _stub.setPortName(getAriesWSWSDDServiceName());
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
        if ("AriesWS".equals(inputPortName)) {
            return getAriesWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://200.7.160.28/axis/CAJA_PAG/AriesWS.jws", "AriesWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://200.7.160.28/axis/CAJA_PAG/AriesWS.jws", "AriesWS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AriesWS".equals(portName)) {
            setAriesWSEndpointAddress(address);
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
