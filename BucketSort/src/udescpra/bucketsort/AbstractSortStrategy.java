package udescpra.bucketsort;
/**
 * Implementa a lógica que deve ser usada pelas classes que realizam as trocas
 * procura diminuir a redundãncia no código
 * @author udesc
 *
 */
abstract class AbstractSortStrategy implements IStrategy {
		/**
		 * Dados que serão ordenados
		 */
	    private int[] elements;
	    
	    public AbstractSortStrategy() {
	    	super();
	    }
	    
		public AbstractSortStrategy( int[] elements ) {
			this.elements = elements;
		}
		
	    public abstract void sort();

		public int[] getElements() {
			return elements;
		}

		public void setElements(int[] elements) {
			this.elements = elements;
		}
		
		/**
		 * Troca um elemento pelo outro dentro do array - recebe a posicao dos
		 * elementos que serão trocados
		 * @param atual posicao do primeiro elemento dentro do array
		 * @param prox  posicao do elemento seguinte dentro do array
		 */
		public void swap(int atual, int prox) {
			int tmp=this.getElements()[atual];
			this.getElements()[atual] = this.getElements()[prox];
			this.getElements()[prox] = tmp;
		}
		
		public void print() {
			for (int i = 0; i < elements.length; i++) {
				int j = elements[i];
				System.out.println(j);
			}
		}

}