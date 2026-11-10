/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Variantes;

/**
 *
 * @author jalem
 */
public class Knapsack01 {

    public static int resolverMochila(int[] pesos, int[] valores, int capacidadMaxima) {
        int n = pesos.length;
        int[][] M = new int[n + 1][capacidadMaxima + 1];

        // Construccion Bottom-Up de la matriz
        for (int i = 1; i <= n; i++) {
            int pesoActual = pesos[i - 1];
            int valorActual = valores[i - 1];

            for (int w = 1; w <= capacidadMaxima; w++) {
                if (pesoActual > w) {
                    M[i][w] = M[i - 1][w];
                } else {
                    int valorExcluyendo = M[i - 1][w];
                    int valorIncluyendo = valorActual + M[i - 1][w - pesoActual];

                    if (valorIncluyendo > valorExcluyendo) {
                        M[i][w] = valorIncluyendo;
                    } else {
                        M[i][w] = valorExcluyendo;
                    }
                }
            }
        }

        // Backtracking para reconstruir el vector de decision x_i
        int[] seleccionados = new int[n];
        int wResidual = capacidadMaxima;

        for (int i = n; i > 0; i--) {
            if (M[i][wResidual] != M[i - 1][wResidual]) {
                seleccionados[i - 1] = 1;
                wResidual = wResidual - pesos[i - 1];
            } else {
                seleccionados[i - 1] = 0;
            }
        }

        System.out.println("Beneficio Maximo (Z*): " + M[n][capacidadMaxima]);
        System.out.print("Vector de decision x_i: [ ");
        for (int i = 0; i < n; i++) {
            System.out.print(seleccionados[i] + " ");
        }
        System.out.println("]");

        return M[n][capacidadMaxima];
    }

    public static void main(String[] args) {
        int[] pesos = {10, 20, 30};
        int[] valores = {60, 100, 120};
        int capacidadMaxima = 50;

        resolverMochila(pesos, valores, capacidadMaxima);
    }
}
