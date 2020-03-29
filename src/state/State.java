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
public interface State {
    
    final int ARMA_1 = 0;
    final int ARMA_2 =1;
    
    public int handle(int state);
    
}
