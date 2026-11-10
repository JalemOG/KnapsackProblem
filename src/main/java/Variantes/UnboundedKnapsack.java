/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Variantes;

/**
 *
 * @author jalem
 */
public class UnboundedKnapsack {

    public static int resolverNoAcotada(int[] pesos, int[] valores, int capacidadMaxima) {
        int n = pesos.length;
        int[] U = new int[capacidadMaxima + 1];
        int[] ultimoObjeto = new int[capacidadMaxima + 1];

        for (int w = 0; w <= capacidadMaxima; w++) {
            ultimoObjeto[w] = -1;
        }

        // Fase 1: Poblamiento del arreglo unidimensional U[w] en O(nW)
        for (int w = 1; w <= capacidadMaxima; w++) {
            U[w] = U[w - 1]; // Garantiza monotonia creciente si ningun objeto cabe exacto
            for (int i = 0; i < n; i++) {
                if (pesos[i] <= w) {
                    int beneficioCandidato = U[w - pesos[i]] + valores[i];
                    if (beneficioCandidato > U[w]) {
                        U[w] = beneficioCandidato;
                        ultimoObjeto[w] = i;
                    }
                }
            }
        }

        // Fase 2: Reconstruccion del vector entero x_i (conteo de copias)
        int[] copiasSeleccionadas = new int[n];
        int wResidual = capacidadMaxima;

        while (wResidual > 0) {
            // Si en la capacidad exacta wResidual no se inserto un objeto nuevo,
            // significa que el valor se heredo de wResidual - 1
            if (ultimoObjeto[wResidual] == -1) {
                wResidual--;
            } else {
                int idx = ultimoObjeto[wResidual];
                copiasSeleccionadas[idx]++;
                wResidual -= pesos[idx];
            }
        }

        // Fase 3: Despliegue de resultados
        System.out.println("Beneficio Maximo No Acotado (Z*): " + U[capacidadMaxima]);
        System.out.print("Vector de decision entero x_i (copias): [ ");
        for (int i = 0; i < n; i++) {
            System.out.print(copiasSeleccionadas[i] + " ");
        }
        System.out.println("]");

        return U[capacidadMaxima];
    }

    public static void main(String[] args) {
        int[] pesos = {10, 20, 30};
        int[] valores = {60, 100, 120};
        int capacidadMaxima = 50;

        resolverNoAcotada(pesos, valores, capacidadMaxima);
    }
}
