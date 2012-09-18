/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iu.panels;
//import tools.*;
//import prb.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HENRY
 */
public class Hilos {

    public static void main(String[] args) {
        System.out.println("Dentro de main...");
        HiloNuevo hn = new HiloNuevo();
        Thread nuevoHilo = new Thread(hn);
        nuevoHilo.start();
    }
}

class HiloNuevo extends Thread{

    public HiloNuevo() {
        System.out.println("Comenzando un HiloNuevo...");

    }

    public void run() {
        int contador = 0;
        System.out.println("Llamando al método run de HiloNuevo...");
        while (true) {
            if (contador == 10) {
                Thread.interrupted();
            } else {
                try {
                    System.out.println(contador++);
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(HiloNuevo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}