/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida;

import com.fuensalida.BaseDatos.GestionAuditorioBD;
import com.fuensalida.BaseDatos.GestionEntradasBD;
import com.fuensalida.BaseDatos.GestionFuncionesBD;
import com.fuensalida.BaseDatos.GestionInformesBD;
import com.fuensalida.beans.SesionBean;
import com.fuensalida.printer.InformeSesion;
import com.fuensalida.utils.FechasUtils;
import com.fuensalida.utils.PrecioUtils;
import java.awt.Window;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.PrinterResolution;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vPalomo
 */
public class InformesPanel extends javax.swing.JPanel {

    private SesionBean sesionSelecionada;
    /**
     * Creates new form Informes
     */
    public InformesPanel() {
        initComponents();
        cargarTablaSesiones();
        cargaAnos();
        selectMesActual();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jAnos = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        jMes = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tActividades = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel33.setText("Año:");

        jAnos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jAnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAnosActionPerformed(evt);
            }
        });

        jLabel35.setText("Mes:");

        jMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));

        jButton3.setText("Cargar Sesiones");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tActividades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Actividad", "Fecha", "Hora", "Compañía", "Precio", "idActividad", "idSesion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tActividades.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tActividades.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tActividades);

        jButton2.setText("Imprimir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jAnos, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jMes, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jAnos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(jMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jAnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAnosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jAnosActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            String ano=jAnos.getItemAt(jAnos.getSelectedIndex());
            String mes=FechasUtils.getNumMes(jMes.getItemAt(jMes.getSelectedIndex()));
            this.cargarTablaSesiones(ano, mes);

        } catch (Exception ex) {
            Logger.getLogger(VentaEntradasFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Window w = SwingUtilities.getWindowAncestor(this);
        w.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        imprimir();
    }//GEN-LAST:event_jButton2ActionPerformed

     /**
      * Carga la tabla con las sesiones del mes actual.
      */
    private void cargarTablaSesiones() {
        String ano=FechasUtils.dameAnoFechaActual();
        String mes=FechasUtils.dameMesFechaActual();
        //System.out.println("fecha por defecto: "+mes+" "+ano);
        cargarTablaSesiones(ano, mes);
    }
    /**
     * Carga la tabla se sesiones con las del mes y año que se pasan por parámetro
     * @param ano Año de las sesiones que se quieren cargar
     * @param mes Mes de las sesiones que se quieren cargar
     */
    private void cargarTablaSesiones(String ano, String mes) {
        ArrayList<SesionBean> listaSesiones=GestionFuncionesBD.getSesiones(ano, mes);
        DefaultTableModel datosTabla=(DefaultTableModel) tActividades.getModel();
        for (int i = datosTabla.getRowCount(); i >0 ; i--) {
            datosTabla.removeRow(i-1);
            
        }
       
        for (int i=0;i<listaSesiones.size();i++){
            datosTabla.addRow(new String[]{
                listaSesiones.get(i).getDescripcion(),
                listaSesiones.get(i).getFecha(),
                listaSesiones.get(i).getHora(),
                listaSesiones.get(i).getCompania(),
                ""+listaSesiones.get(i).getPrecio(),
                ""+listaSesiones.get(i).getIdActividad(),
                ""+listaSesiones.get(i).getIdSesion()
            });
        }     
        // Añadimos los listener a los botones de las butacas.
        tActividades.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                int indice=lsm.getMinSelectionIndex();
                if(!e.getValueIsAdjusting() && indice!=-1){
                    String idActividad=(String) tActividades.getModel().getValueAt(indice, 5);
                    String idSesion= (String) tActividades.getModel().getValueAt(indice, 6);
                    sesionSelecionada=GestionFuncionesBD.getSesion(idActividad, idSesion);
                    System.out.println("1"+sesionSelecionada);
                }
            }
        });
       
        
    }
    /**
     * Carga los años en el combo de años. Se cargan todos los años existentes entre la mayor de las fechas y la menos de las fechas de las sesiones.
     */
    private void cargaAnos(){
        int mayor=Integer.parseInt(GestionFuncionesBD.getMaxMinAnoSesiones(true));
        int menor=Integer.parseInt(GestionFuncionesBD.getMaxMinAnoSesiones(false));
        String[] modelo = new String[(mayor-menor)+1];
        int indice=0;
        while(menor<=mayor){
            modelo[indice]=""+menor;
            menor++;
            indice++;
        }
        jAnos.setModel(new DefaultComboBoxModel<String>(modelo));
    }
    
    private void selectMesActual(){
        String mes=FechasUtils.dameMesFechaActual();
        jMes.setSelectedIndex(Integer.parseInt(mes)-1);
        tActividades.getSelectionModel().setSelectionInterval(0, 0);
    }

    private SesionBean selecionaSesion() {
        return GestionFuncionesBD.getSesionDefault();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jAnos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JComboBox<String> jMes;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tActividades;
    // End of variables declaration//GEN-END:variables

    private void imprimir() {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            System.out.println("Impresora: "+services[i].getName().toUpperCase());
            if(services[i].getName().toUpperCase().contains("GESTDOC")){
                selectedService = i;
            }
        }

        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            HashMap<String, Object> datosInforme;            
            datosInforme=cargarDatosInforme();            
            job.setPrintable(new InformeSesion(datosInforme));
            
            //Configurar papel
            PrintRequestAttributeSet atributos = new HashPrintRequestAttributeSet();
            atributos.add(new PrinterResolution(300, 300, PrinterResolution.DPI));
            atributos.add(new MediaPrintableArea(0, 0, 210, 297, MediaPrintableArea.MM)); 
            
            //Seleccionar impresora
            job.setPrintService(services[selectedService]);
            
            //Numero de copias
            job.setCopies(1);
            
            //Imprimimos con los atributos creados
            job.print(atributos);
            
        } catch (PrinterException ex) {
            Logger.getLogger(InformesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

    private HashMap<String, Object> cargarDatosInforme() {
        HashMap<String, Object> datos=new HashMap();
        datos.put("titulo",sesionSelecionada.getDescripcion());
        datos.put("fecha",sesionSelecionada.getFecha());
        datos.put("hora",sesionSelecionada.getHora());
        datos.put("precio",PrecioUtils.getPrecioEuros(sesionSelecionada.getPrecio()));
        datos.put("compania",sesionSelecionada.getCompania());
        datos.put("recaudacion", PrecioUtils.getPrecioEuros(GestionEntradasBD.getRecaudacionSesion(sesionSelecionada)));
        datos.put("invitaciones",""+GestionAuditorioBD.getButacasInvitaciones(sesionSelecionada.getIdActividad(), sesionSelecionada.getIdSesion()));
        HashMap<String,String> cifrasSesion=GestionInformesBD.getCifrasSesion(sesionSelecionada);
        datos.putAll(cifrasSesion);
        ArrayList<HashMap> cifrasImportes=GestionInformesBD.getImportesSesion(sesionSelecionada);
        datos.put("cifrasImportes", cifrasImportes);
        
        return datos;
    }
}
