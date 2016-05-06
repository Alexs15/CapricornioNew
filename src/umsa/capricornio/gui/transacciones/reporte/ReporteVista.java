/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.gui.transacciones.reporte;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.view.JRViewer;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ReporteVista extends JRViewer 
{
    public static int intbtnPresionado=0;
    public ReporteVista(final JasperPrint jrPrint) 
    {
        super(jrPrint);
        btnSave.removeActionListener(btnSave.getActionListeners()[0]);
        btnSave.addActionListener(new ActionListener()
        {
        @Override
        //Metodo para controlar el evento click del boton guarar. 
        public void actionPerformed(ActionEvent arg0) 
        {
            System.out.println(btnSave.getActionListeners());
            //Creamos el objeto JFileChooser
            JFileChooser fc=new JFileChooser();
            //Creamos el filtro
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.PDF", "pdf");
            //Le indicamos el filtro
            fc.setFileFilter(filtro);
            //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
            int seleccion=fc.showSaveDialog(btnSave);

            //Si el usuario, pincha en aceptar
            if(seleccion==JFileChooser.APPROVE_OPTION){
                File fichero=fc.getSelectedFile();
                System.out.println("fffffffff "+fichero.getAbsolutePath());
                try{
                    JRExporter exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jrPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(fichero.getAbsolutePath()+".pdf"));
                    exporter.exportReport();
                    System.out.println("Ya está guardado");
                }catch(JRException e)
                {
                    System.out.println("error en guardar en temporal "+e);
                }
                //Seleccionamos el fichero
                /*File fichero=fc.getSelectedFile();

                try(
                    FileWriter fw=new FileWriter(fichero)){
                    //Escribimos el texto en el fichero
                    fw.write(textArea.getText());

                } catch (IOException e1) {
                    e1.printStackTrace();
                }*/

            }           
        
            /*System.out.println("presiono");
            intbtnPresionado=1;
            System.out.println("Valor del la variable intbtnPresionado: "+intbtnPresionado);
            try{
                JRExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jrPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("reportePDF.pdf"));
                exporter.exportReport();
                System.out.println("Ya está guardado");
            }catch(JRException e)
            {
                System.out.println("error en guardar en temporal "+e);
            }*/
        }
        });
    }
    ///Metodo para habilitar o deshabilitar el boton guardar
    public void setGuardarEnabled(boolean enabled,JasperPrint jrPrint1)
    {
        btnSave.setEnabled(enabled);       
    }
    private static final long serialVersionUID = 1271367514255520348L;
    }