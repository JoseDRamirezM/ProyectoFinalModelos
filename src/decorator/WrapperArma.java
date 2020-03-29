/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decorator;

import Armas.Arma;
import Fabricas.FabricaAbstracta;
import Fabricas.FabricaElfo;
import Fabricas.FabricaHumano;
import Personajes.Personaje;

/**
 *
 * @author USUARIO
 */
public class WrapperArma extends Wrapper {
    
    protected FabricaAbstracta fp;
    
    public WrapperArma(Personaje p){
        super(p);
    }

    @Override
    public Arma doThis(Personaje p, String tipo) {
        if(tipo.equals("Humano")){
            fp = new FabricaHumano();           
            return  fp.crearArma2();
        }else {
            fp = new FabricaElfo();
            return fp.crearArma2();
        }
    }
}
    

