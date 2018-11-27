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
	private final static int ALPHABET_SIZE = 256; // Number of characters in the alphabet
	private final static int q = 101; // Prime Number
	private int h = 1;
	private ArrayList<StringBuilder> linhas;
	private ArrayList<Integer> tamanhoLinhas; // size of lines
	private int i = 0, j = 0;
	private int hashWord = 0; // hash value for pattern
	private int hashText = 0; // hash value for text

	@Override
	public void prepareSearch(String text) {
		this.tamanhoLinhas = new ArrayList<Integer>();
		this.linhas = prePrepareSearch(text);
		for (int i = 0; i < this.linhas.size(); i++)
			this.tamanhoLinhas.add(i, this.linhas.get(i).length());

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
					j=0;
					boolean letras = false;
					while (!letras && j < M) {
						if (letrasDiferentes(word, index)) {
							letras = true;
						}
						j++;
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
		WordLocation nul =new WordLocation();
		nul.setColumn(-1);
		nul.setLine(-1);
		return nul;
	}

	private void geraPrimeiroHashText(int index) {
		hashText = (ALPHABET_SIZE * hashText + linhas.get(index).charAt(i)) % q;
	}

	private boolean letrasDiferentes(String word, int index) {
		return linhas.get(index).charAt(i + j) != word.charAt(j);
	}

	private void calculaNovoHashText(int m, int index) {
		hashText = (ALPHABET_SIZE * (hashText - linhas.get(index).charAt(i) * h) + linhas.get(index).charAt(i + m)) % q;
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
		for (i = 0; i < word.length()-1; i++) 
			h = (h * ALPHABET_SIZE) % q;
		for (i = 0; i < word.length(); i++) {
			hashWord = (ALPHABET_SIZE * hashWord + word.charAt(i)) % q;
		}
	}

	@Override
	public void limpa() {
		h = 1;
		linhas=null;
		tamanhoLinhas=null; // size of lines
		i = 0;
		j = 0;
		hashWord = 0; // hash value for pattern
		hashText = 0;
		
	}
}
