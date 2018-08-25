package udescpra.bucketsort;

import java.util.LinkedList;

public class BucketSortStrategy extends AbstractSortStrategy{

	private InsertionSort insert = new InsertionSort();
	
	@Override
	public void sort() {
		int[] elementos = this.getElements();
		int tamanhoBucket = elementos.length / 5;
		
		LinkedList[] Bucket = new LinkedList[tamanhoBucket];
		
		for(int i = 0; i < tamanhoBucket; i++){
			Bucket[i] = new LinkedList<Integer>();
		}
		
		for (int i = 0; i < elementos.length; i++) {
			int j = tamanhoBucket - 1;
			
			while(true){
				if(j<0){
					break;
				}
				if(elementos[i] >= j*5){
					Bucket[j].add(elementos[i]);
					break;
				}
				j--;
			}
		}
		
		int indice = 0;
		
		for (int i = 0; i < tamanhoBucket; i++) {
			int[] aux = new int[Bucket[i].size()];
			
			for (int j = 0; j < aux.length; j++) {
				aux[j] = (Integer) Bucket[i].get(j);
			}
			
			aux = insert.insertionSort(aux);
			
			for (int j = 0; j < aux.length; j++, indice++) {
				this.getElements()[indice] = aux[j];
			}
			
		}
		print();
		
	}

	
}
