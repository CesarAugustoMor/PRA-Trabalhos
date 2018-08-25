package udescpra.bucketsort;

import java.util.Random;

public class InsertionSort{
	
	/**
	 * Implementa a ordenação pelo método da inserção
	 */
	public int[] insertionSort(int[] elementos) {
        int tamanho = elementos.length;
        int k;
        int valor;
        for (int i = 1; i < tamanho; i++) {
        	valor = elementos[i];
        	k = i - 1;
			while(k >= 0 && elementos[k] > valor) {
				elementos[k+1] = elementos[k];
				k--;
			}
			elementos[k+1] = valor;
		}
        
        return elementos;
	}
}
