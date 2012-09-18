/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author user
 */
public class Esfera {
    public int posX, posY;
    public int diametro;
    public int player; //1=humano, 0 =maquina

    public Esfera(int posX, int posY, int diametro, int player) {
        this.posX = posX;
        this.posY = posY;
        this.diametro = diametro;
        this.player = player;
    }
      
    
}
