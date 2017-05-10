/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.printer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class Ticket implements Printable {

    public Ticket(HashMap<String, Object> datosInforme) {
        this.datosInforme = datosInforme;
    }

    private HashMap<String, Object> datosInforme;
    public int print(Graphics g, PageFormat f, int pageIndex) {
        System.out.println("pageIndex: "+pageIndex);
        if (pageIndex == 0) {
            g.setFont( new Font( "Serif", Font.BOLD, 10 ) );
            g.drawLine(0, 2, 350, 2);
            g.drawString("Fuensalida - Casa de Cultura de Fuensalida 2017", 0, 15);
            g.drawLine(0, 30, 200, 30);
            try {
                //Para poder pintar el escudo y las imagenes que necesitemos.
                g.drawImage(ImageIO.read(new File("Recursos/Imagenes/CasaCulturaTicket.png")), 0, 50, null);
            } catch (Exception ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }
            return PAGE_EXISTS;
        } else {
            return NO_SUCH_PAGE;
        }
    }
}
