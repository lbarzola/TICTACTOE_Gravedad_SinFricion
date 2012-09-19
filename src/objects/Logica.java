/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author user
 */
public class Logica {

    public int[][] T; //tablero
    public boolean t = true;  //turno true=player1  false=player2
    public int f = 0;  //numero de fichas en el tablero
    public int[] a; //numero de fichas en una columna
    public int m;
    public int n;
    public int k;

    public Logica(int m, int n, boolean t) {
        this.T = new int[m][n];
        this.a = new int[n];
        this.t = t;
        this.m = m;
        this.n = n;
    }

    //------------------INFORMA SI SE LLEGO AL ESTADO META O NO-------------------------------
    
    //OJO con esto, que pasa si en la ultima jugada alguien gana
    public boolean hayEmpate(){
        if (f == m*n) {
            return true;
        } else {
            return false;
        }
    }
    //las coordenadas x e y seran la ubicacion de la ultima bolita que se puso en el tablero y 
    //k es el total de bolas del mismo tipo que se debe tener para ganar.
    public boolean hayGanador(int x, int y, int k) {
        int verificar_vertical = 0;
        int verificar_horizontal = 0;
        int verificar_diagonal1 = 0;
        int verificar_diagonal2 = 0;
        int i = 0;
        int valor;
        boolean senal = false;

        //identifica si evaluara para player 1 o player 2
        //Player 1->1, Player 2->-1 y casilla vacia->0  OK?
        if (t) {
            valor = 1;
        } else {
            valor = -1;
        }
        //*****verifica en el eje Vertical*********
        //Verifica arriba
        while (i < k && (x - i) >= 0 && !senal) {
            if (T[x-1][y] == valor) {
                verificar_vertical++;
            } else {
                senal = true;
            }
            i++;
        }

        //Verifica abajo
        senal = false;
        i = 0;
        while (i < k && (x + i) < m && !senal) {
            if (T[x+1][y] == valor) {
                verificar_vertical++;
            } else {
                senal = true;
            }
            i++;
        }
        //******************************************************* 

        //***********Verifica en el eje horizontal*************** 

        //Verifica derecha
        i = 0;
        senal = false;
        while (i < k && (y + i) < n && !senal) {
            if (T[x][y+1] == valor) {
                verificar_horizontal++;
            } else {
                senal = true;
            }
            i++;
        }

        //Verifica izquierda
        i = 0;
        senal = false;
        while (i < k && (y - i) >= 0 && !senal) {
            if (T[x][y-1] == valor) {
                verificar_horizontal++;
            } else {
                senal = true;
            }
            i++;
        }
        //***********************************************************

        //Verifica en la Diagonal 1  NO-SE**************************************

        i = 0;
        senal = false;
        while (i < k && (x - i) >= 0 && (y - i) >= 0 && !senal) {
            if (T[x -i][y -i] == valor) {
                verificar_diagonal1++;
            } else {
                senal = true;
            }
            i++;
        }

        i = 0;
        senal = false;
        while (i < k && (x + i) < n && (y + i) < m && !senal) {
            if (T[x + i][y + i] == valor) {
                verificar_diagonal1++;
            } else {
                senal = true;
            }
            i++;
        }

        //***************************************************************

        //Verifica en la Diagonal 2 NE-SO**************************************
        i = 0;
        senal = false;
        while (i < k && (x - i) < n && (y + i) >= 0 && !senal) {
            if (T[x - i][y + i] == valor) {
                verificar_diagonal1++;
            } else {
                senal = true;
            }
            i++;
        }

        i = 0;
        senal = false;
        while (i < k && (x + i) >= 0 && (y - i) < m && !senal) {
            if (T[x + i][y - i] == valor) {
                verificar_diagonal1++;
            } else {
                senal = true;
            }
            i++;
        }
        //****************************************************************

        // verifica si hay un ganador
        if (verificar_vertical == k || verificar_horizontal == k || verificar_diagonal1 == k || verificar_diagonal2 == k) {
            return true;
        } else {
            return false;//si ni un verifica llega a k entonces todavia no se ha conseguido un ganador
        }
    }

    
    //----------------------SE GENERA EL NUEVO ESTADO---------------------
    public void nuevoEstado(int columna, boolean turno) {
        //True f1, false f2
        //1 f1, 0 f2
        if (vacio(columna)) {
            if (t == turno) {
                colocar(columna, true);
            } else {
                colocar(columna, false);
            }
            
        } //Si columna esta llena
        else {
            //Mensaje?
        }

    }

    //Verifcar si hay espacio en columna para colocar ficha
    public boolean vacio(int c) {
        if (a[c] == n - 1) {
            return false;
        } else {
            return true;
        }
    }
    
    //La variable columna se debe pasar como parametro en 'formato java'
    public void colocar(int columna, boolean turno) {
        //boolean turnoColocar = turno;
        
        int valorC=0;
        if(turno)
            valorC=1;
        else
            valorC=-1;
        if (a[columna] == 1) {
            if (a[columna + 1] == 0) {
                T[m - 2][columna + 1] = T[m - 2][columna];
                a[columna + 1] = 1;
            }
            if (a[columna - 1] == 0) {
                T[m - 2][columna - 1] = T[m - 2][columna];
                a[columna - 1] = 1;
            }
        } else {
            T[(m-1) - a[columna]][columna] =valorC;
            a[columna]++;

        }

        t = !turno;
        f++;
        hayGanador((m-1) - a[columna], columna, 4);
    }
}
