/**
 * AdquiWSService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package umsa.capricornio.gui.ConnectADQUI;

public interface AdquiWSService extends javax.xml.rpc.Service {
    public java.lang.String getAdquiWSAddress();

    public umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType getAdquiWS() throws javax.xml.rpc.ServiceException;

    public umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType getAdquiWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
