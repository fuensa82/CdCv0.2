/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
package com.fuensalida;

import com.fuensalida.BaseDatos.GestionEntradasBD;
import com.fuensalida.beans.ButacaSesion;
import com.fuensalida.beans.OptionCombo;
import com.fuensalida.beans.SesionBean;
import com.fuensalida.printer.Ticket;
import com.fuensalida.utils.FechaExternaNTP;
import com.fuensalida.utils.PrecioUtils;
import java.awt.Window;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.PrinterResolution;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author vPalomo
 */
public class VentaTicketPanel extends javax.swing.JPanel {

    private ArrayList<ButacaSesion> butacas;
    private SesionBean sesion;
    private int precioDto;
    private String motivoPrecio;
    
    
    /**
     * Creates new form VentaEntradasPanel
     */
    public VentaTicketPanel(ArrayList<ButacaSesion> butacas, SesionBean sesion) {
        initComponents();
        this.butacas=butacas;
        this.sesion=sesion;
        this.sesion.cargaPrecios();
        cargarComboDescuetos();
        jLabelObra.setText(sesion.getDescripcion());
        jLabelFecha.setText(sesion.getFecha());
        jLabelHora.setText(sesion.getHora());
        jLabelNEntrdas.setText(""+butacas.size());
        jLabelPrecio.setText(""+PrecioUtils.getPrecioEuros(sesion.getPrecio()));
        int total=butacas.size()*sesion.getPrecio();
        jLabelTotal.setText(PrecioUtils.getPrecioEuros(total));
        if(butacas.get(0).getIdEstado()==3){ //Todas las butacas deben tener el mismo estado
            cargarMotivos(butacas, sesion);
        }else{
            cargarButacas(butacas);
        }
        
    }
    private void cargarMotivos(ArrayList<ButacaSesion> butacas, SesionBean sesion) {
        ArrayList<String> motivos=GestionEntradasBD.getMotivos(butacas, sesion);
        String texto="<html><body>";
        for (String motivo : motivos) {
            texto=texto+motivo+"<BR/>";
        }
        texto=texto+"</body></html>";
        jLabel7.setText(texto);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonAceptar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        principal = new javax.swing.JPanel();
        listaButacas = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabelObra = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabelFecha = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabelHora = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabelNEntrdas = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelPrecio = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabelTotal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jButtonAceptar.setText("Aceptar");
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });

        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jLabel7.setText("jLabel7");

        javax.swing.GroupLayout listaButacasLayout = new javax.swing.GroupLayout(listaButacas);
        listaButacas.setLayout(listaButacasLayout);
        listaButacasLayout.setHorizontalGroup(
            listaButacasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listaButacasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        listaButacasLayout.setVerticalGroup(
            listaButacasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listaButacasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        jLabelObra.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelObra.setText("jLabel2");

        jPanel1.setLayout(new java.awt.GridLayout(5, 0, 10, 0));

        jLabel3.setText("Fecha:");
        jPanel1.add(jLabel3);

        jLabelFecha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelFecha.setText("jLabel5");
        jPanel1.add(jLabelFecha);

        jLabel4.setText("Hora:");
        jPanel1.add(jLabel4);

        jLabelHora.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelHora.setText("jLabel6");
        jPanel1.add(jLabelHora);

        jLabel5.setText("Entradas:");
        jPanel1.add(jLabel5);

        jLabelNEntrdas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelNEntrdas.setText("jLabel7");
        jPanel1.add(jLabelNEntrdas);

        jLabel6.setText("Precio uni.:");
        jPanel1.add(jLabel6);

        jLabelPrecio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelPrecio.setText("jLabel8");
        jPanel1.add(jLabelPrecio);

        javax.swing.GroupLayout principalLayout = new javax.swing.GroupLayout(principal);
        principal.setLayout(principalLayout);
        principalLayout.setHorizontalGroup(
            principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(principalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelObra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(principalLayout.createSequentialGroup()
                        .addGroup(principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(listaButacas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, principalLayout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        principalLayout.setVerticalGroup(
            principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, principalLayout.createSequentialGroup()
                .addComponent(jLabelObra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listaButacas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Descuento:");

        jLabelTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelTotal.setText("jLabel7");

        jLabel2.setText("Total:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 208, Short.MAX_VALUE)
                        .addComponent(jButtonCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonAceptar))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabelTotal)
                .addGap(59, 59, 59))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelTotal)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancelar)
                    .addComponent(jButtonAceptar))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        OptionCombo dto=jComboBox1.getItemAt(jComboBox1.getSelectedIndex());
        this.precioDto=dto.getValue();
        this.motivoPrecio=dto.getTexto();
        //System.out.println("Dto: "+dto);
        //System.out.println("idDto: "+idDto);
        int total=(int)(butacas.size()*this.precioDto);
        
        //System.out.println("Dto: "+total);
        jLabelTotal.setText(PrecioUtils.getPrecioEuros(""+total));
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed
        int result=GestionEntradasBD.ventaButacas(butacas, sesion, precioDto, motivoPrecio);
        imprimirTicket();
        if (result==1){
            //System.out.println("Saliendo de la venta");
            Window w = SwingUtilities.getWindowAncestor(this);
            w.setVisible(false);
        }else if(result==-1){
            JOptionPane.showMessageDialog(null, "Error, no se han podido vender las entradas", "Error en la venta", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAceptarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        Window w = SwingUtilities.getWindowAncestor(this);
            w.setVisible(false);
    }//GEN-LAST:event_jButtonCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JComboBox<OptionCombo> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelFecha;
    private javax.swing.JLabel jLabelHora;
    private javax.swing.JLabel jLabelNEntrdas;
    private javax.swing.JLabel jLabelObra;
    private javax.swing.JLabel jLabelPrecio;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel listaButacas;
    private javax.swing.JPanel principal;
    // End of variables declaration//GEN-END:variables

    private void cargarComboDescuetos() {
        jComboBox1.addItem(new OptionCombo(sesion.getPrecio(), "Normal"));
        
        if(!"".equals(sesion.getDescPrecio1()) && sesion.getDescPrecio1()!=null){
            jComboBox1.addItem(new OptionCombo(sesion.getPrecio1(), sesion.getDescPrecio1()));
        }
        if(!"".equals(sesion.getDescPrecio2()) && sesion.getDescPrecio2()!=null){
            jComboBox1.addItem(new OptionCombo(sesion.getPrecio2(), sesion.getDescPrecio2()));
        }
        if(!"".equals(sesion.getDescPrecio3()) && sesion.getDescPrecio3()!=null){
            jComboBox1.addItem(new OptionCombo(sesion.getPrecio3(), sesion.getDescPrecio3()));
        }
    }

    private void cargarButacas(ArrayList<ButacaSesion> butacas) {
        String texto="<html><body>";
        for (ButacaSesion butaca : butacas) {
            texto=texto+butaca.getNombreButaca()+"<BR/>";
        }
        texto=texto+"</body></html>";
        jLabel7.setText(texto);
    }

    private void imprimirTicket() {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;

        for(int i = 0; i < services.length;i++){
            
            //if(services[i].getName().toUpperCase().contains("EPSON")){
            if(services[i].getName().toUpperCase().contains("Planta 2")){
                //System.out.println("Impresoras: "+services[i]);
                selectedService = i;
            }
        }

        try {
            for(int i=0;i<butacas.size();i++){
                
                PrinterJob job = PrinterJob.getPrinterJob();
                HashMap<String, String> datosTicket;            
                datosTicket=cargarDatosTicket(i);            
                job.setPrintable(new Ticket(datosTicket));
                //Configurar papel
                PrintRequestAttributeSet atributos = new HashPrintRequestAttributeSet();
                //Comentado solo para pruebas                
                atributos.add(new PrinterResolution(203, 203, PrinterResolution.DPI));
                atributos.add(new MediaPrintableArea(0, 0, 100, 200, MediaPrintableArea.MM)); 
                //Seleccionar impresora
                job.setPrintService(services[selectedService]);
                
                //Numero de copias
                job.setCopies(1);
                //Imprimimos con los atributos creados
                
                job.print(atributos);
                
                
            }
            
        } catch (PrinterException ex) {
            Logger.getLogger(InformesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private HashMap<String, String> cargarDatosTicket(int i) {
        HashMap<String, String> datos=new HashMap();
        datos.put("titulo",sesion.getDescripcion());
        datos.put("fecha",sesion.getFecha());
        datos.put("hora",sesion.getHora());
        datos.put("precio",PrecioUtils.getPrecioEuros(precioDto));
        datos.put("tipoEntrada",motivoPrecio);
        datos.put("fila",butacas.get(i).getFilaButaca());
        datos.put("asiento",butacas.get(i).getNumAsientoButaca());
        datos.put("fecha",sesion.getFecha());
        datos.put("hora",sesion.getHoraCorta());
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss '-' dd 'de' MMMM 'de' yyyy", new Locale("es","ES"));
        String fechaImpresion=formateador.format(FechaExternaNTP.getNTPDateSYS());
        datos.put("horaImpresion",fechaImpresion);
        
        return datos;
    }
}
