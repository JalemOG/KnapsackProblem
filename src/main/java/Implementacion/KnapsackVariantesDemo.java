/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementacion;

/**
 *
 * @author jalem
 */
public class KnapsackVariantesDemo {

    // 1. VARIANTE 0/1: Programacion Dinamica M[i][w] (x_i en {0, 1})
    public static int mochila01(int[] w, int[] v, int W) {
        int n = w.length;
        int[][] M = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int cap = 1; cap <= W; cap++) {
                if (w[i - 1] > cap) {
                    M[i][cap] = M[i - 1][cap];
                } else {
                    int excluir = M[i - 1][cap];
                    int incluir = v[i - 1] + M[i - 1][cap - w[i - 1]];
                    M[i][cap] = (incluir > excluir) ? incluir : excluir;
                }
            }
        }

        int[] x = new int[n];
        int capRes = W;
        for (int i = n; i > 0; i--) {
            if (M[i][capRes] != M[i - 1][capRes]) {
                x[i - 1] = 1;
                capRes -= w[i - 1];
            }
        }
        imprimirVectorEntero("Mochila 0/1", M[n][W], x);
        return M[n][W];
    }

    // 2. VARIANTE FRACCIONAL: Algoritmo Voraz por densidad r_i = v_i / w_i
    public static double mochilaFraccional(int[] w, int[] v, int W) {
        int n = w.length;
        double[] r = new double[n];
        int[] idx = new int[n];

        for (int i = 0; i < n; i++) {
            r[i] = (double) v[i] / w[i];
            idx[i] = i;
        }

        // Ordenamiento descendente simple por seleccion para instancias pequenas
        for (int i = 0; i < n - 1; i++) {
            int max = i;
            for (int j = i + 1; j < n; j++) {
                if (r[j] > r[max]) {
                    max = j;
                }
            }
            double tempR = r[i]; r[i] = r[max]; r[max] = tempR;
            int tempIdx = idx[i]; idx[i] = idx[max]; idx[max] = tempIdx;
        }

        double[] x = new double[n];
        double zOpt = 0.0;
        int capRes = W;

        for (int i = 0; i < n && capRes > 0; i++) {
            int obj = idx[i];
            if (w[obj] <= capRes) {
                x[obj] = 1.0;
                zOpt += v[obj];
                capRes -= w[obj];
            } else {
                x[obj] = (double) capRes / w[obj];
                zOpt += v[obj] * x[obj];
                capRes = 0;
            }
        }

        System.out.printf("Mochila Fraccional -> Z* = %.2f | x = [ ", zOpt);
        for (int i = 0; i < n; i++) {
            System.out.printf("%.2f ", x[i]);
        }
        System.out.println("]");
        return zOpt;
    }

    // 3. VARIANTE ACOTADA: Programacion Dinamica con limite de copias c_i
    public static int mochilaAcotada(int[] w, int[] v, int[] c, int W) {
        int n = w.length;
        int[][] M = new int[n + 1][W + 1];
        int[][] decK = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int cap = 1; cap <= W; cap++) {
                M[i][cap] = M[i - 1][cap];
                for (int k = 1; k <= c[i - 1] && (k * w[i - 1]) <= cap; k++) {
                    int cand = M[i - 1][cap - (k * w[i - 1])] + (k * v[i - 1]);
                    if (cand > M[i][cap]) {
                        M[i][cap] = cand;
                        decK[i][cap] = k;
                    }
                }
            }
        }

        int[] x = new int[n];
        int capRes = W;
        for (int i = n; i > 0; i--) {
            x[i - 1] = decK[i][capRes];
            capRes -= (x[i - 1] * w[i - 1]);
        }
        imprimirVectorEntero("Mochila Acotada", M[n][W], x);
        return M[n][W];
    }

    // 4. VARIANTE NO ACOTADA: Programacion Dinamica 1D con copias infinitas
    public static int mochilaNoAcotada(int[] w, int[] v, int W) {
        int n = w.length;
        int[] U = new int[W + 1];
        int[] ultimo = new int[W + 1];

        for (int cap = 0; cap <= W; cap++) {
            ultimo[cap] = -1;
        }

        for (int cap = 1; cap <= W; cap++) {
            U[cap] = U[cap - 1];
            for (int i = 0; i < n; i++) {
                if (w[i] <= cap) {
                    int cand = U[cap - w[i]] + v[i];
                    if (cand > U[cap]) {
                        U[cap] = cand;
                        ultimo[cap] = i;
                    }
                }
            }
        }

        int[] x = new int[n];
        int capRes = W;
        while (capRes > 0) {
            if (ultimo[capRes] == -1) {
                capRes--;
            } else {
                int obj = ultimo[capRes];
                x[obj]++;
                capRes -= w[obj];
            }
        }
        imprimirVectorEntero("Mochila No Acotada", U[W], x);
        return U[W];
    }

    private static void imprimirVectorEntero(String nombre, int zOpt, int[] x) {
        System.out.print(nombre + " -> Z* = " + zOpt + " | x = [ ");
        for (int i = 0; i < x.length; i++) {
            System.out.print(x[i] + " ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        // Ejemplo simple unificado: n = 3 elementos, capacidad W = 6
        int[] pesos   = {2, 4, 3};
        int[] valores = {13, 20, 12};
        int[] copias  = {2, 1, 1}; // Limite c_i para la variante acotada
        int W = 6;

        System.out.println("=== COMPARATIVA DE VARIANTES (W = 6) ===");
        mochila01(pesos, valores, W);
        mochilaFraccional(pesos, valores, W);
        mochilaAcotada(pesos, valores, copias, W);
        mochilaNoAcotada(pesos, valores, W);
    }
}
