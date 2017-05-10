/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.printer;

import com.fuensalida.utils.FechaExternaNTP;
import com.fuensalida.utils.PrecioUtils;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class InformeSesion implements Printable {

    public InformeSesion(HashMap<String, Object> datosInforme) {
        this.datosInforme = datosInforme;
    }
    
    String fechaInforme;
    private final HashMap<String, Object> datosInforme;
    
    private final int tamanoBloque=60; //lo que ocupa un grupo de datos de entradas
    private final int tamanoHoja=840;
    private final int tamanoCabecera=220;
    private final int tamanoCabeceraRecaudacion=110;
    private final int bloquesPrimera=8;
    private final int bloquesResto=14;
    private ArrayList lista;
    private final ArrayList<Integer> cortePagina=new ArrayList();
    
    public void generaCabecera(Graphics g, PageFormat pf, int pageIndex){
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
    }
    
    private void generaCabeceraRecaudacion(Graphics g, PageFormat pf, int pageIndex){
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
        g.setFont( new Font( "Serif", Font.PLAIN, 12 ) );
        g.drawString("- Tipos entradas",50,320);
    }
    
    public void cargaDatos(){
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss 'del' EEEE dd 'de' MMMM 'de' yyyy", new Locale("es","ES"));
        fechaInforme=formateador.format(FechaExternaNTP.getNTPDate());
        lista=(ArrayList) datosInforme.get("cifrasImportes");
    }
    
    public int print(Graphics g, PageFormat f, int pageIndex) {
        //printCuadricula(g, f, pageIndex);
        if(lista==null){
            cargaDatos();
        }
        if(lista.size()<(bloquesPrimera+bloquesResto*(pageIndex-1))){
            return NO_SUCH_PAGE;
        }
        if (pageIndex == 0) {
            generaCabecera(g, f, pageIndex);
            generaCabeceraRecaudacion(g, f, pageIndex);
            
            for (int i=0;i<lista.size();i++) {
                int anadir=tamanoBloque*i;
                if((tamanoCabecera+tamanoCabeceraRecaudacion+anadir)>(tamanoHoja-tamanoBloque)){
                    cortePagina.add(i);
                    return PAGE_EXISTS;
                }
                HashMap datos2=(HashMap) lista.get(i);
                g.drawLine(70, 329+anadir, 270, 329+anadir);
                g.setFont( new Font( "Serif", Font.PLAIN, 12 ) );
                g.drawString("Nº Entradas:",70,340+anadir);
                g.drawString("Importe uni.:",70,355+anadir);
                g.drawString("Total:",70,370+anadir);
                if(Integer.parseInt((String)datos2.get("Total"))>0){
                    g.drawString("Tipo Ent.:",70,385+anadir);
                }else{
                    g.drawString("Motivo dev.:",70,385+anadir);
                }
                g.setFont( new Font( "Serif", Font.BOLD, 12 ) );
                g.drawString((String) datos2.get("N Entradas"),150,340+anadir);
                g.drawString(PrecioUtils.getPrecioEuros((String) datos2.get("Importe Entrada")),150,355+anadir);
                
                g.drawString(PrecioUtils.getPrecioEuros((String) datos2.get("Total")),150,370+anadir);
                g.drawString((String) datos2.get("motivo"),150,385+anadir);
            }
            return PAGE_EXISTS;
        } else {
            int i=cortePagina.get(pageIndex-1);
            int iter=0;
            for (;i<lista.size();i++) {
                if(iter==0){
                    g.setFont( new Font( "Serif", Font.PLAIN, 12 ) );
                    g.drawString("Informe generado a las "+fechaInforme,50,20);
                    g.drawLine(0, 22, 600, 22);
                }
                int anadir=tamanoBloque*iter;
                iter++;
                if((30+anadir)>(tamanoHoja-tamanoBloque)){
                    if(cortePagina.size()==pageIndex){
                        cortePagina.add(i);
                    }else{
                        cortePagina.add(pageIndex, i);
                    }
                    return PAGE_EXISTS;
                }
                HashMap datos2=(HashMap) lista.get(i);
                g.drawLine(70, 29+anadir, 270, 29+anadir);
                g.setFont( new Font( "Serif", Font.PLAIN, 12 ) );
                g.drawString("Nº Entradas:",70,40+anadir);
                g.drawString("Importe uni.:",70,55+anadir);
                g.drawString("Total:",70,70+anadir);
                g.drawString("Motivo:",70,85+anadir);
                if(Integer.parseInt((String)datos2.get("Total"))>0){
                    g.drawString("Tipo Ent.:",70,85+anadir);
                }else{
                    g.drawString("Motivo dev.:",70,85+anadir);
                }
                g.setFont( new Font( "Serif", Font.BOLD, 12));
                g.drawString((String) datos2.get("N Entradas"),150,40+anadir);
                g.drawString(PrecioUtils.getPrecioEuros((String) datos2.get("Importe Entrada")),150,55+anadir);
                g.drawString(PrecioUtils.getPrecioEuros((String) datos2.get("Total")),150,70+anadir);
                g.drawString((String) datos2.get("motivo"),150,85+anadir);
            }
            System.out.println("Fin del documento");
            return PAGE_EXISTS;
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
    int[] pageBreaks;  // array of page break line positions.

    /* Synthesise some sample lines of text */
    String[] textLines;
    private void initTextLines() {
        if (textLines == null) {
            int numLines=23;
            textLines = new String[numLines];
            for (int i=0;i<numLines;i++) {
                textLines[i]= "This is line number " + i;
            }
        }
    }

    public int print1(Graphics g, PageFormat pf, int pageIndex){

        Font font = new Font("Serif", Font.PLAIN, 10);
        FontMetrics metrics = g.getFontMetrics(font);
        int lineHeight = metrics.getHeight();

        if (pageBreaks == null) {
            initTextLines();
            int linesPerPage = (int)(pf.getImageableHeight()/lineHeight);
            int numBreaks = (textLines.length-1)/linesPerPage;
            pageBreaks = new int[numBreaks];
            for (int b=0; b<numBreaks; b++) {
                pageBreaks[b] = (b+1)*linesPerPage; 
            }
        }

        if (pageIndex > pageBreaks.length) {
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         * Since we are drawing text we
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Draw each line that is on this page.
         * Increment 'y' position by lineHeight for each line.
         */
        int y = 0; 
        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
        int end   = (pageIndex == pageBreaks.length)
                         ? textLines.length : pageBreaks[pageIndex];
        for (int line=start; line<end; line++) {
            y += lineHeight;
            g.drawString(textLines[line], 0, y);
        }

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
    
}
