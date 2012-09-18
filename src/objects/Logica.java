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

    public Logica(int m, int n, boolean t) {
        this.T = new int[m][n];
        this.a = new int[n];
        this.t = t;
        this.m = m;
        this.n = n;
    }

    //------------------INFORMA SI SE LLEGO AL ESTADO META O NO-------------------------------
    
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
        if (t) {
            valor = 1;
        } else {
            valor = 0;
        }
        //*****verifica en el eje Vertical*********
        //Verifica arriba
        while ((y - i) > 0 && !senal) {
            if (T[x][y - i] == valor) {
                verificar_vertical++;
            } else {
                senal = true;
            }
            i++;
        }

        //Verifica abajo
        senal = false;
        i = 0;
        while ((y + i) < m && !senal) {
            if (T[x][y + (i + 1)] == valor) {
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
        while ((x + i) < n && !senal) {
            if (T[x + i][y] == valor) {
                verificar_horizontal++;
            } else {
                senal = true;
            }
            i++;
        }

        //Verifica izquierda
        i = 0;
        senal = false;
        while (i < k && (x - i) > 0) {
            if (T[x - i][y] == valor) {
                verificar_horizontal++;
            } else {
                senal = true;
            }
            i++;
        }
        //***********************************************************

        //Verifica en la Diagonal 1**************************************

        i = 0;
        senal = false;
        while ((x - i) > 0 && (y - i) > 0 && !senal) {
            if (T[x - i][y - i] == valor) {
                verificar_diagonal1++;
            } else {
                senal = true;
            }
            i++;
        }

        i = 0;
        senal = false;
        while ((x + i) < n && (y + i) < m && !senal) {
            if (T[x + (i + 1)][y + (i + 1)] == valor) {
                verificar_diagonal1++;
            } else {
                senal = true;
            }
            i++;
        }

        //***************************************************************

        //Verifica en la Diagonal 2**************************************
        i = 0;
        senal = false;
        while ((x + i) < n && (y - i) > 0 && !senal) {
            if (T[x + i][y - i] == valor) {
                verificar_diagonal1++;
            } else {
                senal = true;
            }
            i++;
        }

        i = 0;
        senal = false;
        while ((x - i) > 0 && (y + i) < m && !senal) {
            if (T[x - i][y + i] == valor) {
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

    public void colocar(int columna, boolean turno) {
        boolean turnoColocar = turno;
        if (a[columna] == 1) {
            if (a[columna + 1] == 0) {
                T[m - 1][columna + 1] = T[m - 1][columna];
                a[columna + 1] = 1;
            }
            if (a[columna - 1] == 0) {
                T[m - 1][columna - 1] = T[m - 1][columna];
                a[columna - 1] = 1;
            }
        } else {
            T[(m-1) - a[columna]][columna] = 1;
            a[columna]++;

        }

        t = !turnoColocar;
        f++;
    }
}
