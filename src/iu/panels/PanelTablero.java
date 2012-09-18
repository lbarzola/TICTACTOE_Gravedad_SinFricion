/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelTablero.java
 *
 * Created on 31/08/2012, 06:55:07 PM
 */
package iu.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import objects.Esfera;
import objects.Logica;

/**
 *
 * @author user
 */
public class PanelTablero extends javax.swing.JPanel {

    private Color naranja = Color.ORANGE;
    private int filas = 8, columnas = 8;
    private int posX = 50, posY = 75, radio = 25, velocidadCaida = 6, posYLibre = 425;
    private boolean movimiento = false, choque = false;
    public boolean iniciaPartida = false, tuTurno = false;
    public int k = 3; //k en raya
    Esfera esferas[][]; //las esferas en pantalla
    int ocupados[][]; //posiciones ocupadas por las esferas
    Image humano = new ImageIcon(getClass().getResource("/images/esfera-red.png")).getImage();
    Image maquina = new ImageIcon(getClass().getResource("/images/esfera-blue.png")).getImage();
    
    Logica logica;
    int col_fdesplazado = 0; //columna de la ficha luego de desplazarse. Se usa cuando hay choque
    

    /** Creates new form PanelTablero */
    public PanelTablero() {
        initComponents();
        iniciarVariables();
    }

    public void iniciarVariables() {
        esferas = new Esfera[filas][columnas];
        ocupados = new int[filas][columnas];
        logica = new Logica(filas, columnas, tuTurno);
    }
    
    public void limpiarTodo(){
        movimiento = false;
        choque = false;
        iniciaPartida = false;
        tuTurno = false;
        esferas = new Esfera[filas][columnas];
        ocupados = new int[filas][columnas];
        logica = new Logica(filas, columnas, tuTurno);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(7.0f));

        pintarTablero2(g2d);
        pintarEsferas(g2d);

        if (iniciaPartida) {
            if (movimiento) {
                if (choque) {
                    movimiento = false;
                } else {
                    caida(g2d);
                }
            } else {
                eligeColumna(g2d); //antes de caer
            }
        }
    }
    
    public void pintarTablero2(Graphics2D g2d) {
        g2d.setColor(naranja);
        g2d.drawLine(25, 450, 425, 450); //Linea de abajo
        g2d.drawLine(25, 450, 25, 50); //izquierda
        g2d.drawLine(425, 450, 425, 50); //derecha
        
        g2d.setStroke(new BasicStroke(1.0f)); //tamanio de linea
        
        for (int i = 1; i < columnas; i++) {
            g2d.drawLine(25 + i*50, 450, 25 + i*50, 50);
        }

    }
    
    public void pintarTablero1(Graphics2D g2d) {
        g2d.setColor(naranja);
        g2d.drawRoundRect(25, 50, 400, 400, 10, 10); //contorno

        //puntos
        for (int y = 75; y < 450; y += 50) {
            for (int x = 50; x < 425; x += 50) {
                g2d.fillOval(x - 4, y - 4, 8, 8);
            }
        }
    }

    public void pintarEsferas(Graphics2D g2d) {
        for (int x = 0; x < columnas; x++) {
            int y = filas - 1;
            while (y >= 0 && ocupados[y][x] == 1) {
                if (esferas[y][x].player == 1) {
                    g2d.drawImage(humano, esferas[y][x].posX - radio, esferas[y][x].posY - radio, 50, 50, this);
                } else {
                    g2d.drawImage(maquina, esferas[y][x].posX - radio, esferas[y][x].posY - radio, 50, 50, this);
                }
                y--;
            }
        }
    }

    public void caida(Graphics2D g2d) {
        //g2d.setColor(naranja);
        if (tuTurno) {
            g2d.drawImage(humano, posX - 25, posY - 25, 50, 50, this);
        } else {
            g2d.drawImage(maquina, posX - 25, posY - 25, 50, 50, this);
        }
         
        //g2d.fillOval(posX, posY, 50, 50);

        if (movimiento) {
            posY = posY + velocidadCaida;
        }
        if (posY >= posYLibre) {
            if (guardaEsfera()) {//si se produce choque
                choque = true;
            } else {
                movimiento = false;
            }

        }
    }

    public void eligeColumna(Graphics2D g2d) {
        if (tuTurno) {
            g2d.drawImage(humano, posX - 25, 0, 50, 50, this);
        } else {
            g2d.drawImage(maquina, posX - 25, 0, 50, 50, this);
        }       
    }

    public boolean guardaEsfera() {
        int i = convertFila(posY);
        int j = convertColumna(posX);
        int auxI = i + 1, auxJ = j;

        if (i != filas - 2) {
            esferas[i][j] = new Esfera(posX, posY, 2 * radio, player());
            ocupados[i][j] = 1;
            return false;
        } else {
            //par la esfera de abajo
            if ((auxJ + 1) < columnas && ocupados[auxI][auxJ + 1] == 0) {
                esferas[auxI][auxJ + 1] = new Esfera(posX + 2 * radio, posY + 2 * radio, 2 * radio, esferas[auxI][auxJ].player);
                ocupados[auxI][auxJ + 1] = 1;
                choque = true;
                col_fdesplazado = auxJ + 1;
            } else if ((auxJ - 1) > -1 && ocupados[auxI][auxJ - 1] == 0) {
                esferas[auxI][auxJ - 1] = new Esfera(posX - 2 * radio, posY + 2 * radio, 2 * radio, esferas[auxI][auxJ].player);
                ocupados[auxI][auxJ - 1] = 1;
                choque = true;
                col_fdesplazado = auxJ - 1;
            }

            //para la esfera de encima
            if (choque) {
                esferas[i + 1][j] = new Esfera(posX, posY + 2 * radio, 2 * radio, player());
                ocupados[i + 1][j] = 1;
                return true;
            } else {
                esferas[i][j] = new Esfera(posX, posY, 2 * radio, player());
                ocupados[i][j] = 1;
                return false;
            }
        }
    }

    public int calibraPunto(int x) {
        int xcalib = x + 25;
        return (xcalib - xcalib % 50);
    }

    //Lo convierte a posicion en matriz
    public int convertColumna(int col) {
        return (col - radio) / (2 * radio);
    }

    //Lo convierte a posicion en matriz
    public int convertFila(int fil) {
        return (fil - 2 * radio) / (2 * radio);
    }

    /* Te devuelve la posicion libre en una columna, contando de abajo hacia arriba
     * y = -1 , No hay posiciones libres.
     */
    public int posicionLibre(int columna) {
        int pos = -1;

        for (int i = filas - 1; i >= 0; i--) {
            if (ocupados[i][columna] == 0) {
                pos = i;
                break;
            }
        }

        return pos;
    }

    private int player() {
        if (tuTurno) {
            return 1;
        } else {
            return 0;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        setPreferredSize(new java.awt.Dimension(450, 475));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 462, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        int column = convertColumna(posX);
        if (iniciaPartida && !movimiento) {
            int fila = posicionLibre(column);

            if (fila != -1) {
                posYLibre = (fila + 1) * (2 * radio) + radio;

                movimiento = true;
                choque = false;
                velocidadCaida = 4;
                posY = 75;
                while (movimiento) {
                    try {
                        Thread.sleep(10);
                        this.paintComponent(this.getGraphics());

                    } catch (InterruptedException ex) {
                        Logger.getLogger(HiloNuevo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                //aca va las busquedas de ganador
                logica.nuevoEstado(column, tuTurno);
                
                if (logica.hayEmpate()) {
                    iniciaPartida = false;
                    JOptionPane.showMessageDialog(this, "Empataron csm!!!");
                } else if(choque){
                    if(logica.hayGanador(0, column, k)){
                        iniciaPartida = false;
                        JOptionPane.showMessageDialog(this, "Ganaste!!! por fin. Te sientes realizado?");
                    }                   
                } else if (logica.hayGanador(fila, column, k)){
                    iniciaPartida = false;
                    JOptionPane.showMessageDialog(this, "Ganaste!!! por fin. Ahora puedes morir en paz.");
                } else{
                    tuTurno = !tuTurno; //cambia de turno
                }
                
            }
        }
    }//GEN-LAST:event_formMouseClicked

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        if (iniciaPartida && !movimiento) {
            if (evt.getX() > 25 && evt.getX() < 425) { //si el puntero esta dentro del tablero
                if (evt.getX() < (posX - 25) || evt.getX() > (posX + 25)) { //si el puntero esta fuera de la bola
                    posX = calibraPunto(evt.getX());
                    this.paintComponent(this.getGraphics());
                }
            }
        }
    }//GEN-LAST:event_formMouseMoved
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
