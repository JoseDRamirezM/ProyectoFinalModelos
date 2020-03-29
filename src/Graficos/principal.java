/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import Armas.Arma;
import Control.Teclado;
import Personajes.Personaje;
import Recursos.cargarFuentes;
import decorator.Component;
import decorator.ComponenteReal;
import decorator.WrapperArma;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import state.EstadoArma1;
import state.EstadoArma2;
import state.State;

/**
 *
 * @author USUARIO
 */
public class principal extends JFrame implements Runnable{
    
    private final int ANCHO_VENTANA = 600;
    private final int ALTO_VENTANA = 400;
    private final String puntos = "Puntos: ";
    cargarFuentes tipoFuente;
    private Personaje p;
    private final Image imgPersonaje;
    
    Personaje e;
    private final Image imgEnemigo;
    
    private Image imgArma1;
    private Image imgArma2;
    
    private Thread hilo;
    private Image fondo;
    private int incremento = 0;
       
    private boolean colision;
    private boolean colisionProyectil;
        
    private Arma arma;
    private Arma arma2;
    private int velProyectil = 50;
    
    private int i = 0;
    private int numPuntos = 0;
    private int mx;
    private int my;    
    Graphics g2d;
    Toolkit herramienta;
    Teclado t;
    private int velX, velY, posiSpriteX, posiSpriteY, xPersonaje, frames = 8, posiEnemigoX, posiEnemigoY, xProyectil, yProyectil, xCajaArma, yCajaArma;       
    
    private Component wrapper;
    private Component real;
    
    private EstadoArma1 estado1;
    private EstadoArma2 estado2;
    private int estadoArmaPersonaje;
    
    
    public principal(Personaje p, Personaje e, Arma a2){
        this.setSize(ANCHO_VENTANA, ALTO_VENTANA);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Juego");
        this.setResizable(false);
        t = new Teclado();
        addKeyListener(t);
        this.p = p;
        velX = p.getVelX();
        velY = p.getVelY();
        posiSpriteX = p.getPosiSpriteX();
        posiSpriteY = p.getPosiSpriteY();    
        xPersonaje = p.getX();
              
        this.e = e;
        definirPosiEnemigo();
        //definirPosiCaja();
        xCajaArma=550;
        yCajaArma=300;
        
                
        colisionProyectil = false;
               
        wrapper = new WrapperArma(p);
        real = new ComponenteReal();
        
        arma = p.getArma();
        arma2 = a2;
        xProyectil = arma.getX();
        yProyectil = arma.getY();
        arma.setVel(velProyectil);
                
        hilo = new Thread(this);
        fondo = new ImageIcon(getClass().getResource("/img/uno.jpg")).getImage();
        imgPersonaje = p.getSprite();
        imgEnemigo = e.getSprite();
        
        imgArma1 = p.getArma().getSprite();
        imgArma2 = arma2.getSprite();
                
        estado1 = new EstadoArma1();
        estado2 = new EstadoArma2();
        estadoArmaPersonaje = estado1.handle(estadoArmaPersonaje);
        
        hilo.start();                      
        this.setVisible(true);
    }
    
    @Override
    public void update(Graphics g) {
        paint(g);
    }
    @Override
    public void paint(Graphics g){
     
        Dimension d = this.getSize();
        Image imagen = createImage(d.width, d.height);
        Graphics buf = imagen.getGraphics();
        buf.drawImage(fondo,0,0,600,400,this);
        g2d = (Graphics2D) imagen.getGraphics();
         
        mx = (incremento%frames)*64;
        my = i;
        
        pintarSpriteInicialPersonaje(imgPersonaje, p.getPosiInicialX());
        pintarEnemigo();
        pintarCajaArma();
        pintarPuntos();
        
        this.repaint();
        
        if(t.derecha){
            p.setVelX(8);
            p.setPosiSpriteX(192);
            p.setPosiSpriteY(256);
            
            i = 448;
            
            velX = p.getVelX();            
            posiSpriteX = p.getPosiSpriteX();            
            posiSpriteY = p.getPosiSpriteY();
            
            pintarSpritePostAccion(imgPersonaje, p.getPosiInicialX());
            
        }else if(t.izquierda){
            p.setVelX(-8);
            p.setPosiSpriteX(64);
            p.setPosiSpriteY(128);
            
            i = 320;
            
            velX = p.getVelX();            
            posiSpriteX = p.getPosiSpriteX();            
            posiSpriteY = p.getPosiSpriteY();
            
            pintarSpritePostAccion(imgPersonaje, p.getPosiInicialX());
            
        }else if(t.atacar){            
            frames = 6;                  
            p.setPosiSpriteX(0);
            p.setPosiSpriteY(64);            
            i = 768;                                    
            posiSpriteX = p.getPosiSpriteX();            
            posiSpriteY = p.getPosiSpriteY();
            pintarSpritePostAccion(imgPersonaje, p.getPosiInicialX());            
            
            pintarProyectil();
//            Clip bang = CargarRecursos.cargarSonido("../Recursos/Atacar.wav");
//            bang.start();
//            bang.close();
            
        }else if(t.cambiarArma){
           cambiarArma(); 
        }     
        
        g.drawImage(imagen,0,0,null);
    }
    
    public void pintarSpriteInicialPersonaje(Image img, int posiInicial){
        
        g2d.drawImage(img, posiInicial+p.getX(), posiInicial, posiInicial+64+p.getX() , posiInicial+64, posiSpriteX, posiSpriteX, posiSpriteY, posiSpriteY, this);
        g2d.setColor(Color.RED);
        //g2d.drawRect( posiInicial+p.getX(), posiInicial, 64, 64);
    }
    
    public void pintarSpritePostAccion(Image img, int posiInicial){
        
        g2d.drawImage(img, posiInicial+p.getX(), posiInicial, posiInicial+64+p.getX() , posiInicial+64 ,mx ,my ,mx+64 ,my+64 ,this);
    }
    
    public void pintarEnemigo(){
        //g2d.setColor(Color.RED);
        g2d.drawImage(imgEnemigo, posiEnemigoX, posiEnemigoY, this);
        //g2d.fillRect(posiEnemigoX, posiEnemigoY, 50, 50);
        //g2d.drawRect(posiEnemigoX, posiEnemigoY, 50, 50);
    }
        
    public void definirPosiEnemigo(){
        
        posiEnemigoX = new Random().nextInt(560); 
        posiEnemigoY = ThreadLocalRandom.current().nextInt(0, 230 ); 
    }                                         
    
    public void pintarProyectil(){
        
        arma.setX(p.getPosiInicialX()+ xPersonaje);
        arma.setY(p.getPosiInicialX()-50);
        xProyectil = arma.getX();
        yProyectil = arma.getY();
        
        for(int j = 0; j < ALTO_VENTANA; j++){            
            arma.draw(g2d);            
            arma.mover();
            yProyectil = arma.getY();
            arma.draw(g2d);                        
            colisionProyectil();
        }                
    }
    
    public void cambiarArma(){
                                   
        if(estadoArmaPersonaje == State.ARMA_1){
            
            estadoArmaPersonaje = estado2.handle(estadoArmaPersonaje);
            arma2 = arma;
            p.setArma(wrapper.doThis(p, p.getTipo()));
            
        }else {  
            estadoArmaPersonaje = estado1.handle(estadoArmaPersonaje);
            arma2 = arma;
            p.setArma(real.doThis(p, p.getTipo()));
            
        }
        pintarCajaArma();           
        arma = p.getArma();
        arma.setVel(velProyectil);           
        
    }
    
    public void colisionProyectil(){
        
        Rectangle rect1 = new Rectangle(xProyectil, yProyectil, 50, 50 );
        Rectangle rect2 = new Rectangle(posiEnemigoX, posiEnemigoY, 20, 20);
        if(rect1.intersects(rect2)){
            colisionProyectil = true;
            numPuntos += 10;
            definirPosiEnemigo();              
        }else colisionProyectil = false;     
        
    }
    
    public void pintarPuntos(){
        tipoFuente = new cargarFuentes();
        g2d.setFont(tipoFuente.fuente(tipoFuente.MDV3, Font.ITALIC , 25));
        g2d.setColor(Color.black);
        g2d.drawString(puntos + String.valueOf(numPuntos) , 470, 80);
    }
    
    public void pintarCajaArma(){        
        if(estadoArmaPersonaje == State.ARMA_1){
            g2d.drawImage(imgArma2, xCajaArma, yCajaArma, this);
            this.repaint();
        }else {
            g2d.drawImage(imgArma1, xCajaArma, yCajaArma, this);
            this.repaint();
        }
        
    }
    
    public void definirPosiCaja(){
        xCajaArma = new Random().nextInt(560) ;
        yCajaArma = 300;
    }

    @Override
    public void run() {
        requestFocusInWindow();
        while(true){
            try{
                t.actualizar();
             
                colisionProyectil();                
                if(t.estadoPersonaje && !t.atacar){
                    xPersonaje += velX; 
                    p.setX(xPersonaje);
                }               
            Thread.sleep(30);                        
            } catch (InterruptedException ex){
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            incremento++; 
               
            
            if(incremento > 221){
                incremento = 0;
            }
            
            }
        }
}
    
