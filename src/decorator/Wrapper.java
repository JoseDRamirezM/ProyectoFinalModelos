/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator;

import Personajes.Personaje;

/**
 *
 * @author USUARIO
 */
public abstract class Wrapper implements Component {
    
    protected Personaje c;
    
    public Wrapper(Personaje p){
        this.c = p;
    
    }        
}
