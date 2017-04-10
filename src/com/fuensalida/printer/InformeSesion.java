/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.printer;

import com.fuensalida.utils.FechaExternaNTP;
import com.fuensalida.utils.PrecioUtils;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class InformeSesion implements Printable {

    public InformeSesion(HashMap<String, Object> datosInforme) {
        this.datosInforme = datosInforme;
    }

    private HashMap<String, Object> datosInforme;
    private int tamanoBloque=60;
    private int i=0;
    
    public int print(Graphics g, PageFormat f, int pageIndex) {
        //printCuadricula(g, f, pageIndex);
        
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss 'del' EEEE dd 'de' MMMM 'de' yyyy", new Locale("es","ES"));
        String fechaInforme=formateador.format(FechaExternaNTP.getNTPDate());
        System.out.println("FECHA: "+fechaInforme);
        System.out.println("Pagina: "+pageIndex);
        if (pageIndex == 0) {
            g.setFont( new Font( "Serif", Font.BOLD, 14 ) );
            g.drawString("Informe recaudación", 220, 50);
            g.drawString("Casa de Cultura de Fuensalida",190,70);
            g.drawLine(0, 77, 600, 77);
            
            
            g.setFont( new Font( "Serif", Font.PLAIN, 12 ) );
            g.drawString("Informe generado a las "+fechaInforme,50,90);
            g.drawString("- Datos actuación",50,110);
            g.drawString("Nombre:",70,130);
            g.drawString("Fecha:",70,150);
            g.drawString("Hora:",70,170);
            g.drawString("Precio:",70,190);
            g.drawString("Compañía:",70,210);
            g.setFont( new Font( "Serif", Font.BOLD, 12 ) );
            g.drawString((String) datosInforme.get("titulo"),140,130);
            g.drawString((String) datosInforme.get("fecha"),140,150);
            g.drawString((String) datosInforme.get("hora"),140,170);
            g.drawString((String) datosInforme.get("precio"),140,190);
            g.drawString((String) datosInforme.get("compania"),140,210);
            
            g.drawLine(0, 217, 600, 217);
            
            /* datos recaudacion */
            g.setFont( new Font( "Serif", Font.PLAIN, 12 ) );
            g.drawString("- Datos recaudación",50,240);
            g.drawString("Entradas vendidas:",70,255);
            g.drawString("Entradas anuladas:",70,270);
            g.drawString("Invitaciones:",70,285);
            g.drawString("Recaudación:",70,300);
            
            g.setFont( new Font( "Serif", Font.BOLD, 12 ) );
            System.out.println("datosInforme: "+datosInforme);
            g.drawString((String) datosInforme.get("ventas netas"),180,255);
            g.drawString((String) datosInforme.get("devoluciones"),180,270);
            g.drawString((String) datosInforme.get("invitaciones"),180,285);
            g.drawString((String) datosInforme.get("recaudacion"),180,300);

            /* Tipos de entradas agrupadas */
            g.setFont( new Font( "Serif", Font.PLAIN, 12 ) );
            g.drawString("- Tipos entradas",50,320);
            ArrayList lista=(ArrayList) datosInforme.get("cifrasImportes");
            
            
            for (Object lista1 : lista) {
                int anadir=tamanoBloque*i;
                System.out.println("Hay mas?:"+((330+anadir)>(850-tamanoBloque)));
                if((330+anadir)>(850-tamanoBloque)){
                    System.out.println("Hay más paginas");
                    return PAGE_EXISTS;
                }
                HashMap datos2=(HashMap) lista1;
                g.drawLine(70, 329+anadir, 270, 329+anadir);
                g.setFont( new Font( "Serif", Font.PLAIN, 12 ) );
                g.drawString("Nº Entradas:",70,340+anadir);
                g.drawString("Importe uni.:",70,355+anadir);
                g.drawString("Total:",70,370+anadir);
                g.drawString("Motivo:",70,385+anadir);
                g.setFont( new Font( "Serif", Font.BOLD, 12 ) );
                g.drawString((String) datos2.get("N Entradas"),150,340+anadir);
                g.drawString(PrecioUtils.getPrecioEuros((String) datos2.get("Importe Entrada")),150,355+anadir);
                g.drawString(PrecioUtils.getPrecioEuros((String) datos2.get("Total")),150,370+anadir);
                g.drawString((String) datos2.get("motivo"),150,385+anadir);
                i++;
            }
            /*try {
                //Para poder pintar el escudo y las imagenes que necesitemos.
                g.drawImage(ImageIO.read(new File("Recursos/Imagenes/ima.jpg")), 30, 0, null);
            } catch (IOException ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            System.out.println("Fin del documento");
            return PAGE_EXISTS;
        } else {
            ArrayList lista=(ArrayList) datosInforme.get("cifrasImportes");
            for (int j=i+1; j<lista.size();j++) {
                int anadir=tamanoBloque*j;
                if((30+anadir)>(850-tamanoBloque)){
                    return PAGE_EXISTS;
                }
                HashMap datos2=(HashMap) lista.get(i);
                g.drawLine(70, 29+anadir, 270, 29+anadir);
                g.setFont( new Font( "Serif", Font.PLAIN, 12 ) );
                g.drawString("Nº Entradas:",70,40+anadir);
                g.drawString("Importe uni.:",70,55+anadir);
                g.drawString("Total:",70,70+anadir);
                g.drawString("Motivo:",70,85+anadir);
                g.setFont( new Font( "Serif", Font.BOLD, 12 ) );
                g.drawString((String) datos2.get("N Entradas"),150,40+anadir);
                g.drawString(PrecioUtils.getPrecioEuros((String) datos2.get("Importe Entrada")),150,55+anadir);
                g.drawString(PrecioUtils.getPrecioEuros((String) datos2.get("Total")),150,70+anadir);
                g.drawString((String) datos2.get("motivo"),150,85+anadir);
                i++;
            }
            System.out.println("Fin del documento");
            return NO_SUCH_PAGE;
        }
    }
    public int printCuadricula(Graphics g, PageFormat f, int pageIndex) {
        System.out.println("pageIndex: "+pageIndex);
        if (pageIndex == 0) {
            //g.setFont( new Font( "Serif", Font.BOLD, 12 ) );
            //g.drawString("Fuensalida Casa de Cultura "+datosInforme.get("Titulo"), 0, 80);
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
