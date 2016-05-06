/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author julian
 */
public class Transaccion implements Serializable{
    private String nro;

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }
    private String nro_gestion;
    private Date fecha_creacion;
    private Date fecha_envio;
    private Date fecha_noti;
    private Date fecha_ing;

    public Date getFecha_ing() {
        return fecha_ing;
    }

    public void setFecha_ing(Date fecha_ing) {
        this.fecha_ing = fecha_ing;
    }

    public Date getFecha_noti() {
        return fecha_noti;
    }

    public void setFecha_noti(Date fecha_noti) {
        this.fecha_noti = fecha_noti;
    }
    private String unidad_sol;
    private String unidad_des;
    private String usuario_sol;
    private String unidad_medida;
    private int cantidad_pedido;
    private String tipo_item;
    private String articulo;
    private String detalle_solicitud;
    private String hoja_ruta;
    private String cbte;
    private String casa_comercial;
    private String direccion;
    private String telefono;
    private String nit;
    private double precio_unit;
    private double subtotal;
    private int nro_orden_compra;
    private Date fec_orden_compra;
    private String factura;
    private Date fecha_fact;
    private String memo;
    private int nro_transaccion;
    private String detalle;
    private String estado;
    private int nro_ingreso;
    
    private String nro_propuesta;
    private String fondo;
    private double monto_ppto;
    private String inf_comision;
    private String resolucion_adm;

    private String cuantia;
    private String tipo_contrato;
    private double del;
    private double hasta;
    private String cuce;
    private String almacen;
    
    private String cod_trans_detalle;
    private String indice;
    private String partida;
    private String obs;
    private String preventivo;
    private String num_ing;
    private String user_maker;
    
    
    //se adiciona en base a nuevo sabs
    private int cod_transaccion;
    private String proforma;
    private String empresa;
    private String fec_proforma;
    private String lugar_entrega;
    private String metodo;
    private String responsable;
    private String cargo;
    private String correo;
    private String inspeccion;
    private String consultas;
    private String reunion;
    private String dias1;
    private String dias2;
    private String descripcion_;
    private String forma_pago;

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }
    

    public String getDescripcion_() {
        return descripcion_;
    }

    public void setDescripcion_(String descripcion_) {
        this.descripcion_ = descripcion_;
    }
    
    

    public String getDias1() {
        return dias1;
    }

    public String getDias2() {
        return dias2;
    }

    public void setDias1(String dias1) {
        this.dias1 = dias1;
    }

    public void setDias2(String dias2) {
        this.dias2 = dias2;
    }
    

    public int getCod_transaccion() {
        return cod_transaccion;
    }

    public String getProforma() {
        return proforma;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getFec_proforma() {
        return fec_proforma;
    }

    public String getLugar_entrega() {
        return lugar_entrega;
    }

    public String getMetodo() {
        return metodo;
    }

    public String getResponsable() {
        return responsable;
    }

    public String getCargo() {
        return cargo;
    }

    public String getCorreo() {
        return correo;
    }

    public String getInspeccion() {
        return inspeccion;
    }

    public String getConsultas() {
        return consultas;
    }

    public String getReunion() {
        return reunion;
    }

    public void setCod_transaccion(int cod_transaccion) {
        this.cod_transaccion = cod_transaccion;
    }

    public void setProforma(String proforma) {
        this.proforma = proforma;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setFec_proforma(String fec_proforma) {
        this.fec_proforma = fec_proforma;
    }

    public void setLugar_entrega(String lugar_entrega) {
        this.lugar_entrega = lugar_entrega;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setInspeccion(String inspeccion) {
        this.inspeccion = inspeccion;
    }

    public void setConsultas(String consultas) {
        this.consultas = consultas;
    }

    public void setReunion(String reunion) {
        this.reunion = reunion;
    }
    
    
    
    public String getUser_maker() {
        return user_maker;
    }

    public void setUser_maker(String user_maker) {
        this.user_maker = user_maker;
    }
    public String getNum_ing() {
        return num_ing;
    }

    public void setNum_ing(String num_ing) {
        this.num_ing = num_ing;
    }

    public String getPreventivo() {
        return preventivo;
    }

    public void setPreventivo(String preventivo) {
        this.preventivo = preventivo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }
    
    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    public String getCod_trans_detalle() {
        return cod_trans_detalle;
    }

    public void setCod_trans_detalle(String cod_trans_detalle) {
        this.cod_trans_detalle = cod_trans_detalle;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getCuce() {
        return cuce;
    }

    public void setCuce(String cuce) {
        this.cuce = cuce;
    }

    /**
     * @return the nro_gestion
     */
    public String getNro_gestion() {
        return nro_gestion;
    }

    /**
     * @param nro_gestion the nro_gestion to set
     */
    public void setNro_gestion(String nro_gestion) {
        this.nro_gestion = nro_gestion;
    }

    /**
     * @return the fecha_creacion
     */
    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    /**
     * @param fecha_creacion the fecha_creacion to set
     */
    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    /**
     * @return the fecha_envio
     */
    public Date getFecha_envio() {
        return fecha_envio;
    }

    /**
     * @param fecha_envio the fecha_envio to set
     */
    public void setFecha_envio(Date fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    /**
     * @return the unidad_sol
     */
    public String getUnidad_sol() {
        return unidad_sol;
    }

    /**
     * @param unidad_sol the unidad_sol to set
     */
    public void setUnidad_sol(String unidad_sol) {
        this.unidad_sol = unidad_sol;
    }

    /**
     * @return the unidad_des
     */
    public String getUnidad_des() {
        return unidad_des;
    }

    /**
     * @param unidad_des the unidad_des to set
     */
    public void setUnidad_des(String unidad_des) {
        this.unidad_des = unidad_des;
    }

    /**
     * @return the usuario_sol
     */
    public String getUsuario_sol() {
        return usuario_sol;
    }

    /**
     * @param usuario_sol the usuario_sol to set
     */
    public void setUsuario_sol(String usuario_sol) {
        this.usuario_sol = usuario_sol;
    }

    /**
     * @return the unidad_medida
     */
    public String getUnidad_medida() {
        return unidad_medida;
    }

    /**
     * @param unidad_medida the unidad_medida to set
     */
    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    /**
     * @return the cantidad_pedido
     */
    public int getCantidad_pedido() {
        return cantidad_pedido;
    }

    /**
     * @param cantidad_pedido the cantidad_pedido to set
     */
    public void setCantidad_pedido(int cantidad_pedido) {
        this.cantidad_pedido = cantidad_pedido;
    }

    /**
     * @return the tipo_item
     */
    public String getTipo_item() {
        return tipo_item;
    }

    /**
     * @param tipo_item the tipo_item to set
     */
    public void setTipo_item(String tipo_item) {
        this.tipo_item = tipo_item;
    }

    /**
     * @return the articulo
     */
    public String getArticulo() {
        return articulo;
    }

    /**
     * @param articulo the articulo to set
     */
    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    /**
     * @return the detalle_solicitud
     */
    public String getDetalle_solicitud() {
        return detalle_solicitud;
    }

    /**
     * @param detalle_solicitud the detalle_solicitud to set
     */
    public void setDetalle_solicitud(String detalle_solicitud) {
        this.detalle_solicitud = detalle_solicitud;
    }

    /**
     * @return the hoja_ruta
     */
    public String getHoja_ruta() {
        return hoja_ruta;
    }

    /**
     * @param hoja_ruta the hoja_ruta to set
     */
    public void setHoja_ruta(String hoja_ruta) {
        this.hoja_ruta = hoja_ruta;
    }

    /**
     * @return the cbte
     */
    public String getCbte() {
        return cbte;
    }

    /**
     * @param cbte the cbte to set
     */
    public void setCbte(String cbte) {
        this.cbte = cbte;
    }

    /**
     * @return the casa_comercial
     */
    public String getCasa_comercial() {
        return casa_comercial;
    }

    /**
     * @param casa_comercial the casa_comercial to set
     */
    public void setCasa_comercial(String casa_comercial) {
        this.casa_comercial = casa_comercial;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the nit
     */
    public String getNit() {
        return nit;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * @return the precio_unit
     */
    public double getPrecio_unit() {
        return precio_unit;
    }

    /**
     * @param precio_unit the precio_unit to set
     */
    public void setPrecio_unit(double precio_unit) {
        this.precio_unit = precio_unit;
    }

    /**
     * @return the subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the nro_orden_compra
     */
    public int getNro_orden_compra() {
        return nro_orden_compra;
    }

    /**
     * @param nro_orden_compra the nro_orden_compra to set
     */
    public void setNro_orden_compra(int nro_orden_compra) {
        this.nro_orden_compra = nro_orden_compra;
    }

    /**
     * @return the fec_orden_compra
     */
    public Date getFec_orden_compra() {
        return fec_orden_compra;
    }

    /**
     * @param fec_orden_compra the fec_orden_compra to set
     */
    public void setFec_orden_compra(Date fec_orden_compra) {
        this.fec_orden_compra = fec_orden_compra;
    }

    /**
     * @return the factura
     */
    public String getFactura() {
        return factura;
    }

    /**
     * @param factura the factura to set
     */
    public void setFactura(String factura) {
        this.factura = factura;
    }

    /**
     * @return the memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo the memo to set
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * @param fecha_fact the fecha_fact to set
     */
    public void setFecha_fact(Date fecha_fact) {
        this.fecha_fact = fecha_fact;
    }

    /**
     * @return the fecha_fact
     */
    public Date getFecha_fact() {
        return fecha_fact;
    }

    /**
     * @return the nro_transaccion
     */
    public int getNro_transaccion() {
        return nro_transaccion;
    }

    /**
     * @param nro_transaccion the nro_transaccion to set
     */
    public void setNro_transaccion(int nro_transaccion) {
        this.nro_transaccion = nro_transaccion;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    /**
     * @return the nro_ingreso
     */
    public int getNro_ingreso() {
        return nro_ingreso;
    }

    /**
     * @param nro_ingreso the nro_ingreso to set
     */
    public void setNro_ingreso(int nro_ingreso) {
        this.nro_ingreso = nro_ingreso;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the nro_propuesta
     */
    public String getNro_propuesta() {
        return nro_propuesta;
    }

    /**
     * @param nro_propuesta the nro_propuesta to set
     */
    public void setNro_propuesta(String nro_propuesta) {
        this.nro_propuesta = nro_propuesta;
    }

    /**
     * @return the fondo
     */
    public String getFondo() {
        return fondo;
    }

    /**
     * @param fondo the fondo to set
     */
    public void setFondo(String fondo) {
        this.fondo = fondo;
    }

    /**
     * @return the monto_ppto
     */
    public double getMonto_ppto() {
        return monto_ppto;
    }

    /**
     * @param monto_ppto the monto_ppto to set
     */
    public void setMonto_ppto(double monto_ppto) {
        this.monto_ppto = monto_ppto;
    }

    /**
     * @return the inf_comision
     */
    public String getInf_comision() {
        return inf_comision;
    }

    /**
     * @param inf_comision the inf_comision to set
     */
    public void setInf_comision(String inf_comision) {
        this.inf_comision = inf_comision;
    }

    /**
     * @return the resolucion_adm
     */
    public String getResolucion_adm() {
        return resolucion_adm;
    }

    /**
     * @param resolucion_adm the resolucion_adm to set
     */
    public void setResolucion_adm(String resolucion_adm) {
        this.resolucion_adm = resolucion_adm;
    }

    /**
     * @return the cuantia
     */
    public String getCuantia() {
        return cuantia;
    }

    /**
     * @param cuantia the cuantia to set
     */
    public void setCuantia(String cuantia) {
        this.cuantia = cuantia;
    }

    /**
     * @return the tipo_contrato
     */
    public String getTipo_contrato() {
        return tipo_contrato;
    }

    /**
     * @param tipo_contrato the tipo_contrato to set
     */
    public void setTipo_contrato(String tipo_contrato) {
        this.tipo_contrato = tipo_contrato;
    }

    /**
     * @return the del
     */
    public double getDel() {
        return del;
    }

    /**
     * @param del the del to set
     */
    public void setDel(double del) {
        this.del = del;
    }

    /**
     * @return the hasta
     */
    public double getHasta() {
        return hasta;
    }

    /**
     * @param hasta the hasta to set
     */
    public void setHasta(double hasta) {
        this.hasta = hasta;
    }
}
