/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.reporte;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.domain.Transaccion;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.utilitarios.herramientas.i_formatterDate;

/**
 *
 * @author UMSA-JES
 */
public class GeneraRepo {
   
    public void MostrarReporteSolicitudCompra(int cod_transaccion,String cod_estado,int cod_tramite,String titulo){
        List list=new ArrayList();
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getReporteOrden(cod_transaccion,cod_estado,cod_tramite);
            int i=0;
            String cod_trans_detalle,aux = null;
            if (datos!=null){                 
                Map map = new HashMap();
                for (int f=0;f<datos.length;f++){
                    Transaccion trans = new Transaccion();
                    trans.setNro_gestion(datos[f].get("NRO_GESTION").toString());
                    trans.setFecha_creacion(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_CREACION").toString()));
                    trans.setFecha_envio(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_ENVIO").toString()));
                    trans.setUnidad_sol(datos[f].get("UNIDAD_SOL").toString());
                    trans.setUnidad_des(datos[f].get("UNIDAD_DES").toString());
                    trans.setUsuario_sol(datos[f].get("USUARIO_SOL").toString());
                    trans.setDetalle(datos[f].get("DETALLE").toString());
                    trans.setUnidad_medida(datos[f].get("UNIDAD_MEDIDA").toString());
                    trans.setCantidad_pedido(Integer.parseInt(datos[f].get("CANTIDAD_PEDIDO").toString()));
                    trans.setTipo_item(datos[f].get("TIPO_ITEM").toString());
                    trans.setArticulo(datos[f].get("ARTICULO").toString());
                    trans.setDetalle_solicitud(datos[f].get("DETALLE_SOLICITUD").toString());
                    //hoja_ruta,cbte,casa_comercial,direccion,telefono,nit,precio_unit
                    trans.setHoja_ruta(datos[f].get("HOJA_RUTA").toString());
                    trans.setCbte(datos[f].get("CBTE").toString());
                    trans.setCasa_comercial(datos[f].get("CASA_COMERCIAL").toString());
                    trans.setDireccion(datos[f].get("DIRECCION").toString());
                    trans.setTelefono(datos[f].get("TELEFONO").toString());
                    trans.setNit(datos[f].get("NIT").toString());
                    trans.setPrecio_unit(Double.parseDouble(datos[f].get("PRECIO_UNIT").toString()));
                    trans.setPartida(datos[f].get("PARTIDA").toString());
                    //trans.setSubtotal(Double.parseDouble(datos[f].get("SUBTOTAL").toString()));
                    
                    /*trans.setNro_orden_compra(Integer.parseInt(datos[f].get("NRO_ORDEN_COMPRA").toString()));
                    if (!(datos[f].get("FEC_ORDEN_COMPRA")==null || "".equals(datos[f].get("FEC_ORDEN_COMPRA"))))
                        trans.setFec_orden_compra(i_formatterDate.i_conveterStandardToDate(datos[f].get("FEC_ORDEN_COMPRA").toString()));
                    trans.setFactura(datos[f].get("FACTURA").toString());
                    if (!(datos[f].get("FECHA_FACT")==null || "".equals(datos[f].get("FECHA_FACT"))))
                        trans.setFecha_fact(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_FACT").toString()));
                    trans.setMemo(datos[f].get("MEMO").toString());*/
                    trans.setNro_transaccion(Integer.parseInt(datos[f].get("NRO_TRANSACCION").toString()));
                    cod_trans_detalle=datos[f].get("COD_TRANS_DETALLE").toString();
                    if(!cod_trans_detalle.equals(aux)){
                        i++;
                        System.out.println("El indice es wujuuu: "+i);
                        trans.setIndice(String.valueOf(i));
                    }
                    aux=cod_trans_detalle;
                    trans.setCod_trans_detalle(cod_trans_detalle);
                    list.add(trans);
                } 
                RepTransaccion rep = new RepTransaccion();
                System.out.println("titulo: "+titulo);
                System.out.println("cod_tramite: "+cod_tramite);
                System.out.println("cod_trans_nro: "+cod_transaccion);
                
                //rep.Reporte(list,titulo,cod_tramite,cod_transaccion,cod_almacen);
            }                         
            /*for (int i = 0; i < list.size(); i++) {
                Transaccion aux = (Transaccion) list.get(i);t           
                System.out.println(aux.getNro_gestion()+" "+aux.getFecha_creacion()+" "+ aux.getFecha_envio()+" "+aux.getUnidad_sol()+" "+aux.getUnidad_des()+" "+aux.getUsuario_sol()+" "+aux.getUnidad_medida()+" "+aux.getCantidad_pedido()+" "+aux.getTipo_item()+" "+aux.getArticulo()+" "+aux.getDetalle_solicitud());
            }  */      
        }
        catch (RemoteException e){
            /*javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);*/
            System.out.println("Error --> "+e);
        }
        catch (ServiceException e){ 
            System.out.println("Error --> "+e);
        } 
        catch (NumberFormatException e) {
            /*javax.swing.JOptionPane.showMessageDialog(this,"LA ORDEN NO FUE APROBADA O \n NO ELIJIO UNA FILA PARA PODER IMPRIMIR EL REPORTE","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);*/
            System.out.println("Error --> "+e);
        }  
    }
}
