package com.fuensalida.vista;

import com.fuensalida.BaseDatos.GestionAuditorioBD;
import com.fuensalida.BaseDatos.GestionEntradasBD;
import com.fuensalida.BaseDatos.GestionEstadosBD;
import com.fuensalida.MiHasMap2;
import com.fuensalida.PatioButacasPanel;
import com.fuensalida.beans.ButacaSesion;
import com.fuensalida.beans.EstadoBean;
import com.fuensalida.beans.SesionBean;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JToggleButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vPalomo
 */
public class VistaPantalla extends javax.swing.JFrame {

    private PatioButacasPanel patioButacas;
    private MiHasMap2 allButacas;
    private SesionBean sesionSeleccionada;
    /**
     * Creates new form VistaPantalla
     */
    public VistaPantalla() {
        initComponents();
        initPatioButacas();
        //setTimeout((name) -> name.initPatioButacas(), 1000);
        //setTimeout(() -> initPatioButacas(), 1000);
        
    }
    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    public VistaPantalla(SesionBean sesionSelecionada) {
        this.sesionSeleccionada=sesionSelecionada;
        initComponents();
        initPatioButacas();
    }

    public void actualizar(SesionBean sesion){
        System.out.println("Actualizar 3");
        if(sesion==null){
            sesion=sesionSeleccionada;
        }
        System.out.println("Coloreando en Actualizar");
        coloreaButacas(sesionSeleccionada);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 822, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaPantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaPantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaPantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaPantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaPantalla().setVisible(true);
            }
        });
    }

    private boolean initPatioButacas() {
        patioButacas=new PatioButacasPanel();
        jPanel1.add(patioButacas);
        allButacas=patioButacas.getAllButacas();
        patioButacas.setSize(777, 434);
        coloreaButacas(sesionSeleccionada);

        return true;
        
    }
    private void coloreaButacas(SesionBean sesion) {
        //System.out.println("Colorea butacas: "+sesion.getIdActividad()+" "+sesion.getIdSesion() );

        ArrayList estadoButacas = GestionAuditorioBD.getEstadoButacas(sesion.getIdActividad(),sesion.getIdSesion());
        for (int i = 0; i < estadoButacas.size(); i++) {
            ButacaSesion b = (ButacaSesion) estadoButacas.get(i);
            allButacas.put(b);
            //System.out.println("Butaca " + b.getIdButaca() + " está " + GestionEstadosBD.getEstado(b.getIdEstado()));
            JToggleButton bJT = allButacas.getButacaJT(b.getIdButaca());
            if(b.getIdEstado()==3 || b.getIdEstado()==5 ){
                System.out.println("Estado b="+b);
                bJT.setToolTipText(GestionEntradasBD.getMotivo(b.getIdButaca(), sesion));
            }else{
                bJT.setToolTipText("");
            }
            ponColorButaca(b, bJT);
        }
        jPanel1.repaint();
        return;
    }
    /**
     * Colorea el jButton con respecto al estado de la butaca que se le pasa
     * @param b Butaca, se necesita para poder saber el estado de la misma
     * @param j Boton que se va a cambiar de color.
     */
    private void ponColorButaca(ButacaSesion b, JToggleButton j) {
        j.setForeground(Color.BLACK);
        EstadoBean estado= GestionEstadosBD.getEstado(b.getIdEstado());
        j.setBackground(new Color(255, 0,0));
//                new Color(
//                        Integer.valueOf(estado.getColor().substring(0, 2), 16),
//                        Integer.valueOf(estado.getColor().substring(2, 4), 16),
//                        Integer.valueOf(estado.getColor().substring(4, 6), 16)));
        return;
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
