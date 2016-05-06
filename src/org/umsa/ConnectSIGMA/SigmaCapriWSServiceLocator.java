/**
 * SigmaCapriWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.umsa.ConnectSIGMA;

public class SigmaCapriWSServiceLocator extends org.apache.axis.client.Service implements org.umsa.ConnectSIGMA.SigmaCapriWSService {

    public SigmaCapriWSServiceLocator() {
    }


    public SigmaCapriWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SigmaCapriWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SigmaCapriWS
    private java.lang.String SigmaCapriWS_address = "http://200.7.160.26/axis/CAPRICORNIO/SigmaCapriWS.jws";

    public java.lang.String getSigmaCapriWSAddress() {
        return SigmaCapriWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SigmaCapriWSWSDDServiceName = "SigmaCapriWS";

    public java.lang.String getSigmaCapriWSWSDDServiceName() {
        return SigmaCapriWSWSDDServiceName;
    }

    public void setSigmaCapriWSWSDDServiceName(java.lang.String name) {
        SigmaCapriWSWSDDServiceName = name;
    }

    public org.umsa.ConnectSIGMA.SigmaCapriWS_PortType getSigmaCapriWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SigmaCapriWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSigmaCapriWS(endpoint);
    }

    public org.umsa.ConnectSIGMA.SigmaCapriWS_PortType getSigmaCapriWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.umsa.ConnectSIGMA.SigmaCapriWSSoapBindingStub _stub = new org.umsa.ConnectSIGMA.SigmaCapriWSSoapBindingStub(portAddress, this);
            _stub.setPortName(getSigmaCapriWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSigmaCapriWSEndpointAddress(java.lang.String address) {
        SigmaCapriWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.umsa.ConnectSIGMA.SigmaCapriWS_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.umsa.ConnectSIGMA.SigmaCapriWSSoapBindingStub _stub = new org.umsa.ConnectSIGMA.SigmaCapriWSSoapBindingStub(new java.net.URL(SigmaCapriWS_address), this);
                _stub.setPortName(getSigmaCapriWSWSDDServiceName());
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
        if ("SigmaCapriWS".equals(inputPortName)) {
            return getSigmaCapriWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://200.7.160.26/axis/CAPRICORNIO/SigmaCapriWS.jws", "SigmaCapriWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://200.7.160.26/axis/CAPRICORNIO/SigmaCapriWS.jws", "SigmaCapriWS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SigmaCapriWS".equals(portName)) {
            setSigmaCapriWSEndpointAddress(address);
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
