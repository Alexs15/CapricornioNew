/**
 * SigmaCapriWSService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package umsa.capricornio.gui.ConnectSigma;

public interface SigmaCapriWSService extends javax.xml.rpc.Service {
    public java.lang.String getSigmaCapriWSAddress();

    public umsa.capricornio.gui.ConnectSigma.SigmaCapriWS_PortType getSigmaCapriWS() throws javax.xml.rpc.ServiceException;

    public umsa.capricornio.gui.ConnectSigma.SigmaCapriWS_PortType getSigmaCapriWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
