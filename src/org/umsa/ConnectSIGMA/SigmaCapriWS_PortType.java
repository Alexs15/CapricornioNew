/**
 * SigmaCapriWS_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.umsa.ConnectSIGMA;

public interface SigmaCapriWS_PortType extends java.rmi.Remote {
    public java.util.HashMap[] getTipoIdentificacionCapriSigma() throws java.rmi.RemoteException;
    public java.util.HashMap[] getBeneficiariosDetalleCapriSigma(java.lang.String documento, java.lang.String tipo_id) throws java.rmi.RemoteException;
    public java.util.HashMap[] getBeneficiariosCapriSigma(java.lang.String documento, java.lang.String tipo_id, java.lang.String clase_ben, java.lang.String nombre, java.lang.String nombre_comercial, int numin, int numax) throws java.rmi.RemoteException;
    public java.util.HashMap[] getBeneficiariosCapriSigmaII(java.lang.String documento, java.lang.String nombre, java.lang.String nombre_comercial) throws java.rmi.RemoteException;
    public java.util.HashMap[] getEjecucionPptoSIGMA(int gestion, int entidad, int da, int ue, int programa, int proyecto, int actividad) throws java.rmi.RemoteException;
}
