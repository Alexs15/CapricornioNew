/**
 * AriesWSService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package umsa.capricornio.gui.ConnectARIES;

public interface AriesWSService extends javax.xml.rpc.Service {
    public java.lang.String getAriesWSAddress();

    public umsa.capricornio.gui.ConnectARIES.AriesWS_PortType getAriesWS() throws javax.xml.rpc.ServiceException;

    public umsa.capricornio.gui.ConnectARIES.AriesWS_PortType getAriesWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
