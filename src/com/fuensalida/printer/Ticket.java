/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.printer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;


public class Ticket implements Printable {

    public Ticket(HashMap<String, String> datosTicket) {
        this.datosTicket = datosTicket;
    }

    private HashMap<String, String> datosTicket;
    public int print(Graphics g, PageFormat f, int pageIndex) {
        //System.out.println("pageIndex: "+pageIndex);
        //String[] fontNames=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        //System.out.println(Arrays.toString(fontNames));
        if (pageIndex == 0) {
            g.setFont( new Font( "Calibri", Font.BOLD, 12 ) );
            //g.drawLine(0, 2, 350, 2);
            //g.drawString("Fuensalida - Casa de Cultura de Fuensalida 2017", 0, 15);
            //g.drawLine(0, 30, 200, 30);
            try {
                //Para poder pintar el escudo y las imagenes que necesitemos.
                Image ima=new ImageIcon(getClass().getResource("/com/fuensalida/images/CasaCulturaTicket.png")).getImage();
                g.drawImage(ima, 10, 0, null);

            } catch (Exception ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }
            int fila=65;
            g.drawString("Concejalía de Cultura", 60, fila);
            g.drawString("Ayuntamiento de Fuensalida", 55, fila+=12);
            g.drawLine(0, fila+=2, 350, fila);
            g.setFont( new Font( "Cambria", Font.BOLD, 12 ) );
            g.drawString(datosTicket.get("titulo").toUpperCase(), 30, fila+=20);
            //g.drawString(datosTicket.get("fecha")+" - "+datosTicket.get("hora")  , 20, fila+=15);
            g.setFont( new Font( "Cambria", Font.PLAIN, 10 ) );
            g.drawString("Fecha: ", 20, fila+=15);
            
            g.setFont( new Font( "Cambria", Font.BOLD, 14));
            g.drawString(datosTicket.get("fecha"), 70, fila);
            
            g.setFont( new Font( "Cambria", Font.PLAIN, 10 ) );
            g.drawString("Hora: ", 20, fila+=15);
            
            g.setFont( new Font( "Cambria", Font.BOLD, 14));
            g.drawString(datosTicket.get("hora"), 70, fila);
            
            g.setFont( new Font( "Cambria", Font.PLAIN, 10 ) );
            g.drawString("Fila: ", 20, fila+=15);
            
            g.setFont( new Font( "Cambria", Font.BOLD, 14));
            g.drawString(datosTicket.get("fila"), 70, fila);
            
            g.setFont( new Font( "Cambria", Font.PLAIN, 10 ) );
            g.drawString("Butaca: ", 20, fila+=15);
            g.setFont( new Font( "Cambria", Font.BOLD, 14));
            g.drawString(datosTicket.get("asiento"), 70, fila);
            
            g.setFont( new Font( "Cambria", Font.PLAIN, 10 ) );
            g.drawString("Tipo entrada: ", 20, fila+=20);
            g.setFont( new Font( "Cambria", Font.BOLD, 14));
            g.drawString(datosTicket.get("tipoEntrada"), 90, fila);
            
            
            g.setFont( new Font( "Cambria", Font.BOLD, 20));
            g.drawString(datosTicket.get("precio"), 150, fila+=25);
            
            g.setFont( new Font( "Calibri", Font.PLAIN, 7 ) );
            g.drawString(datosTicket.get("horaImpresion"), 10, fila+=25);
            g.drawLine(0, fila+=6, 350, fila);
            g.setFont( new Font( "Calibri", Font.BOLD, 8 ) );
            g.drawString("La presentación de esta entrada es imprescindible", 10, fila+=10);
            g.drawString("para el acceso al auditorio", 10, fila+=10);
            
            return PAGE_EXISTS;
        } else {
            return NO_SUCH_PAGE;
        }
    }
}
