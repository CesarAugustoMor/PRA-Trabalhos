package busca;

/**
 *
 * @author C�sar Augusto Moro F�rst
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
