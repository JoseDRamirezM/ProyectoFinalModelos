/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import java.awt.Toolkit;

/**
 *
 * @author USUARIO
 */
public class Enemigo extends Personaje {
    
     public Enemigo(){
        herramienta = Toolkit.getDefaultToolkit();
        sprite = herramienta.getImage(getClass().getResource("/img/spriteEnemigo.png")).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); 
    }
    
    public Enemigo(int posiInicialX, int posiInicialY, int posiSpriteX, int posiSpriteY, String tipo){        
        this.tipo = tipo;
        this.x = 0;
        this.y = 0;
        this.velX = 0;
        this.velY =0;
        this.posiInicialX = posiInicialX;
        this.posiIncialY = posiInicialY;
        this.posiSpriteX = posiSpriteX;
        this.posiSpriteY = posiSpriteY ;       
        herramienta = Toolkit.getDefaultToolkit();
        sprite = herramienta.getImage(getClass().getResource("/img/spriteEnemigo.png")).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); 
    }
    
    
}
