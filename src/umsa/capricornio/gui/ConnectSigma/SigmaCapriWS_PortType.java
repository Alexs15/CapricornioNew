/**
 * SigmaCapriWS_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package umsa.capricornio.gui.ConnectSigma;

public interface SigmaCapriWS_PortType extends java.rmi.Remote {
    public java.util.HashMap[] getTipoIdentificacionCapriSigma() throws java.rmi.RemoteException;
    public java.util.HashMap[] getBeneficiariosDetalleCapriSigma(java.lang.String documento, java.lang.String tipo_id) throws java.rmi.RemoteException;
    public java.util.HashMap[] getBeneficiariosCapriSigma(java.lang.String documento, java.lang.String tipo_id, java.lang.String clase_ben, java.lang.String nombre, java.lang.String nombre_comercial, int numin, int numax) throws java.rmi.RemoteException;
    public java.util.HashMap[] getBeneficiariosCapriSigmaII(java.lang.String documento, java.lang.String nombre, java.lang.String nombre_comercial) throws java.rmi.RemoteException;
    public java.util.HashMap[] getEjecucionPptoSIGMA(java.lang.String gestion, java.lang.String entidad, java.lang.String da, java.lang.String ue, java.lang.String programa, java.lang.String proyecto, java.lang.String actividad) throws java.rmi.RemoteException;
    public java.util.HashMap[] getComprobantesGastoSIGMA(java.lang.String gestion, java.lang.String da, java.lang.String preventivo) throws java.rmi.RemoteException;
}
