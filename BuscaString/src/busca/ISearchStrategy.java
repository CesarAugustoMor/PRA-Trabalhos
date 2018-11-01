package busca;

/**
 *
 * @author César Augusto Moro Fürst
 */
public interface ISearchStrategy {
	
	public abstract void prepareSearch(String text);
	
	public default void prepareSearch(char[] text){
		prepareSearch(text.toString());
	}

	public abstract WordLocation search(String text);
	
	public default WordLocation search(char[] text) {
		return search(text.toString());
	}
}
