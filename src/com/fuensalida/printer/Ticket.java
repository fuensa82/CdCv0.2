/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.printer;

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

    public Ticket(HashMap<String, String> datosInforme) {
        this.datosInforme = datosInforme;
    }

    private HashMap<String, String> datosInforme;
    public int print(Graphics g, PageFormat f, int pageIndex) {
        System.out.println("pageIndex: "+pageIndex);
        if (pageIndex == 0) {
            g.drawString("Fuensalida Casa de Cultura "+datosInforme.get("Titulo"), 0, 80);
            g.drawLine(0, 20, 300, 20);
            for(int i=1;i<100;i++){
                g.drawString(""+(i+1), 0,(10*i));
                g.drawLine(0, (10*i)+1, 600+i, (10*i)+1);
            }
            for(int i=1;i<100;i++){
                g.drawString(""+(i+1),(20*i),10);
                g.drawLine((10*i)+1, 0, (10*i)+1,600+i);
            }
            
            /*try {
                //Para poder pintar el escudo y las imagenes que necesitemos.
                g.drawImage(ImageIO.read(new File("Recursos/Imagenes/ima.jpg")), 30, 0, null);
            } catch (IOException ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            return PAGE_EXISTS;
        } else {
            return NO_SUCH_PAGE;
        }
    }
}
