/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Variantes;

/**
 *
 * @author jalem
 */
public class FractionalKnapsack {

    public static double resolverFraccional(int[] pesos, int[] valores, int capacidadMaxima) {
        int n = pesos.length;
        double[] ratios = new double[n];
        int[] indicesOriginales = new int[n];

        // Fase 1: Calculo de densidades r_i = v_i / w_i en O(n)
        for (int i = 0; i < n; i++) {
            ratios[i] = (double) valores[i] / pesos[i];
            indicesOriginales[i] = i;
        }

        // Fase 2: Ordenamiento descendente mediante Merge Sort en O(n log n)
        mergeSortDescendente(ratios, indicesOriginales, 0, n - 1);

        // Fase 3: Llenado voraz lineal en O(n)
        double[] fracciones = new double[n];
        double beneficioTotal = 0.0;
        int capacidadResidual = capacidadMaxima;

        for (int i = 0; i < n; i++) {
            if (capacidadResidual == 0) {
                break;
            }

            int idx = indicesOriginales[i];
            int pesoObjeto = pesos[idx];
            int valorObjeto = valores[idx];

            if (pesoObjeto <= capacidadResidual) {
                fracciones[idx] = 1.0;
                beneficioTotal += valorObjeto;
                capacidadResidual -= pesoObjeto;
            } else {
                double fraccion = (double) capacidadResidual / pesoObjeto;
                fracciones[idx] = fraccion;
                beneficioTotal += valorObjeto * fraccion;
                capacidadResidual = 0;
            }
        }

        // Despliegue de resultados
        System.out.printf("Beneficio Maximo Fraccional (Z*): %.2f\n", beneficioTotal);
        System.out.print("Vector de decision continua x_i: [ ");
        for (int i = 0; i < n; i++) {
            System.out.printf("%.4f ", fracciones[i]);
        }
        System.out.println("]");

        return beneficioTotal;
    }

    private static void mergeSortDescendente(double[] ratios, int[] indices, int izq, int der) {
        if (izq < der) {
            int medio = izq + (der - izq) / 2;
            mergeSortDescendente(ratios, indices, izq, medio);
            mergeSortDescendente(ratios, indices, medio + 1, der);
            merge(ratios, indices, izq, medio, der);
        }
    }

    private static void merge(double[] ratios, int[] indices, int izq, int medio, int der) {
        int n1 = medio - izq + 1;
        int n2 = der - medio;

        double[] ratiosIzq = new double[n1];
        int[] indicesIzq = new int[n1];
        double[] ratiosDer = new double[n2];
        int[] indicesDer = new int[n2];

        for (int i = 0; i < n1; i++) {
            ratiosIzq[i] = ratios[izq + i];
            indicesIzq[i] = indices[izq + i];
        }
        for (int j = 0; j < n2; j++) {
            ratiosDer[j] = ratios[medio + 1 + j];
            indicesDer[j] = indices[medio + 1 + j];
        }

        int i = 0, j = 0, k = izq;
        while (i < n1 && j < n2) {
            if (ratiosIzq[i] >= ratiosDer[j]) {
                ratios[k] = ratiosIzq[i];
                indices[k] = indicesIzq[i];
                i++;
            } else {
                ratios[k] = ratiosDer[j];
                indices[k] = indicesDer[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            ratios[k] = ratiosIzq[i];
            indices[k] = indicesIzq[i];
            i++;
            k++;
        }

        while (j < n2) {
            ratios[k] = ratiosDer[j];
            indices[k] = indicesDer[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        int[] pesos = {10, 20, 30};
        int[] valores = {60, 100, 120};
        int capacidadMaxima = 50;

        resolverFraccional(pesos, valores, capacidadMaxima);
    }
}
