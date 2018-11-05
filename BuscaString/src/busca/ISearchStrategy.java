package busca;

import java.util.ArrayList;

/**
 *
 * @author C�sar Augusto Moro F�rst
 */
public interface ISearchStrategy {

	public abstract void prepareSearch(String text);

	public default void prepareSearch(char[] text) {
		prepareSearch(text.toString());
	}

	public abstract WordLocation search(String word);

	public default WordLocation search(char[] word) {
		return search(word.toString());
	}
	
	public default WordLocation search(String text, String word) {
		prepareSearch(text);
		return search(word);
	}
	
	public default WordLocation search(char[] text, char[] word) {
		prepareSearch(text);
		return search(word);
	}

	public default ArrayList<StringBuilder> prePrepareSearch(String text) {
		int linhasIndex = 0;
		ArrayList<StringBuilder> linhas = new ArrayList<StringBuilder>();
		linhas.add(linhasIndex, new StringBuilder());
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '\n') {
				linhasIndex++;
				linhas.add(linhasIndex, new StringBuilder());
				System.out.println("Linha :" + linhasIndex + " atual da prepara��o para busca");
			} else {
				StringBuilder tmp = new StringBuilder(linhas.get(linhasIndex).toString());
				tmp.append(text.charAt(i));
				linhas.set(linhasIndex, tmp);
			}
		}
		// linhas.trimToSize();
		return linhas;
	}
}
