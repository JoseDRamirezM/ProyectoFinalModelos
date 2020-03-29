/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

/**
 *
 * @author USUARIO
 */
public class EstadoArma1 implements State {

    @Override
    public int handle(int state) {
        state = State.ARMA_1;
        return state;
    }
    
}
