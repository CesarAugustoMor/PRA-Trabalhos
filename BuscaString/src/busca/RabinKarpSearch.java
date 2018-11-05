/**
 *  Codigo base retirado de:
 *  https://www.geeksforgeeks.org/rabin-karp-algorithm-for-pattern-searching/
 */
package busca;

import java.util.ArrayList;

/**
 * @author César Augusto Moro Furst
 *
 */
public class RabinKarpSearch implements ISearchStrategy {

	// d is the number of characters in the input alphabet
	private final static int d = 256; // Number of characters in the alphabet
	private final static int q = 101;
	private int h = 1;
	private ArrayList<Integer> tamanhoLinhas; // size of lines
	private int i = 0, j = 0;
	private int hashWord = 0; // hash value for pattern
	private int hashText = 0; // hash value for text
	private ArrayList<StringBuilder> linhas;

	@Override
	public void prepareSearch(String text) {
		this.tamanhoLinhas = new ArrayList<Integer>();
		this.linhas = prePrepareSearch(text);
		for (int i = 0; i < this.linhas.size(); i++) {
			this.tamanhoLinhas.add(i, this.linhas.get(i).length());
		}

	}

	@Override
	public WordLocation search(String word) {
		int M = word.length();
		iniciaHandHashWord(word);

		for (int index = 0; index < this.linhas.size(); index++) {

			// Calculate the hash value of pattern and first
			// window of text
			for (i = 0; i < M; i++) {
				geraPrimeiroHashText(index);
			}

			// Slide the pattern over text one by one
			for (i = 0; i <= this.tamanhoLinhas.get(index) - M; i++) {

				if (hashWord_Igual_HashText()) {
					/* Check for characters one by one */
					for (j = 0; j < M; j++) {
						if (letrasDiferentes(word, index))
							break;
					}

					if (quantidadeLetrasText_Igual_quantidadeLetrasWord(M)) {
						System.out.println("Pattern found at index " + i);
						return geraLocalizacaoWord(index);
					}
				}

				// Calculate hash value for next window of text: Remove
				// leading digit, add trailing digit
				if (aindaExisteTexto(M, index)) {
					calculaNovoHashText(M, index);

					transformaHashNegativoEmPositvo();
				}
			}
		}
		return null;
	}

	private void geraPrimeiroHashText(int index) {
		hashText = (d * hashText + linhas.get(index).charAt(i)) % q;
	}

	private boolean letrasDiferentes(String word, int index) {
		return linhas.get(index).charAt(i + j) != word.charAt(j);
	}

	private void calculaNovoHashText(int m, int index) {
		hashText = (d * (hashText - linhas.get(index).charAt(i) * h) + linhas.get(index).charAt(i + m)) % q;
	}

	private WordLocation geraLocalizacaoWord(int index) {
		WordLocation location = new WordLocation();
		location.setColumn(i);
		location.setLine(index);
		return location;
	}

	private boolean aindaExisteTexto(int m, int index) {
		return i < this.tamanhoLinhas.get(index) - m;
	}

	private void transformaHashNegativoEmPositvo() {
		if (hashText < 0)
			hashText = (hashText + q);
	}

	private boolean hashWord_Igual_HashText() {
		return hashWord == hashText;
	}

	private boolean quantidadeLetrasText_Igual_quantidadeLetrasWord(int M) {
		return j == M;
	}

	private void iniciaHandHashWord(String word) {
		h = (h * d) % q;
		for (int i = 0; i < word.length(); i++) {
			hashWord = (d * hashWord + word.charAt(i)) % q;
		}
	}
}
