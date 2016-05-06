/**
 * PptoWSService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package umsa.capricornio.gui.ConnectPPTO;

public interface PptoWSService extends javax.xml.rpc.Service {
    public java.lang.String getPptoWSAddress();

    public umsa.capricornio.gui.ConnectPPTO.PptoWS_PortType getPptoWS() throws javax.xml.rpc.ServiceException;

    public umsa.capricornio.gui.ConnectPPTO.PptoWS_PortType getPptoWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
