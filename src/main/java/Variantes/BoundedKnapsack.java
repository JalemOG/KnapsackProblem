/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Variantes;

/**
 *
 * @author jalem
 */
public class BoundedKnapsack {

    public static int resolverAcotada(int[] pesos, int[] valores, int[] copias, int capacidadMaxima) {
        int n = pesos.length;
        int[][] M = new int[n + 1][capacidadMaxima + 1];
        int[][] decisionK = new int[n + 1][capacidadMaxima + 1];

        // Fase 1: Poblamiento Bottom-Up evaluando multiples copias k
        for (int i = 1; i <= n; i++) {
            int pesoActual = pesos[i - 1];
            int valorActual = valores[i - 1];
            int limiteCopias = copias[i - 1];

            for (int w = 1; w <= capacidadMaxima; w++) {
                // Caso base para k = 0 (excluir el objeto i)
                M[i][w] = M[i - 1][w];
                decisionK[i][w] = 0;

                // Evaluar la inclusion de k copias dentro de los limites permitidos
                for (int k = 1; k <= limiteCopias && (k * pesoActual) <= w; k++) {
                    int beneficioCandidato = M[i - 1][w - (k * pesoActual)] + (k * valorActual);

                    if (beneficioCandidato > M[i][w]) {
                        M[i][w] = beneficioCandidato;
                        decisionK[i][w] = k;
                    }
                }
            }
        }

        // Fase 2: Backtracking mediante la matriz de decisiones k
        int[] copiasSeleccionadas = new int[n];
        int wResidual = capacidadMaxima;

        for (int i = n; i > 0; i--) {
            int kTomadas = decisionK[i][wResidual];
            copiasSeleccionadas[i - 1] = kTomadas;
            wResidual -= (kTomadas * pesos[i - 1]);
        }

        // Fase 3: Despliegue de resultados
        System.out.println("Beneficio Maximo Acotado (Z*): " + M[n][capacidadMaxima]);
        System.out.print("Vector de decision acotado x_i: [ ");
        for (int i = 0; i < n; i++) {
            System.out.print(copiasSeleccionadas[i] + " ");
        }
        System.out.println("]");

        return M[n][capacidadMaxima];
    }

    public static void main(String[] args) {
        int[] pesos = {10, 20, 30};
        int[] valores = {60, 100, 120};
        int[] copias = {2, 2, 1}; // Inventario disponible c_i para cada elemento
        int capacidadMaxima = 50;

        resolverAcotada(pesos, valores, copias, capacidadMaxima);
    }
}
