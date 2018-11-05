/**
 * Codigo base retirado de:
 * https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/
 */
package busca;

import java.util.ArrayList;

/**
 * @author César Augusto Moro Furst
 *
 */
public class KMPSearch implements ISearchStrategy {
	private int j = 0; // index for pat[]
	private int i = 0; // index for text[]
	private ArrayList<StringBuilder> linhas;
	private ArrayList<Integer> tamanhoLinhas; // size of lines

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.ISearchStrategy#prepareSearch(java.lang.String)
	 */
	@Override
	public void prepareSearch(String text) {
		this.tamanhoLinhas = new ArrayList<Integer>();
		this.linhas = prePrepareSearch(text);
		for (int i = 0; i < this.linhas.size(); i++) {
			this.tamanhoLinhas.add(i, this.linhas.get(i).length());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.ISearchStrategy#search(java.lang.String)
	 */
	@Override
	public WordLocation search(String word) {
		int m = word.length();

		// create lps[] that will hold the longest
		// prefix suffix values for pattern
		// Preprocess the pattern (calculate lps[]
		// array)
		int lps[] = computeLPSArray(word, m);
		for (int index = 0; index < this.linhas.size(); index++) {
			while (i < this.tamanhoLinhas.get(index)) {
				if (letrasIguais(word, index)) {
					j++;
					i++;
				}
				if (quantidadeLetrasText_Igual_quantidadeLetrasWord(m)) {
					System.out.println("Found pattern at index " + (i - j));
					j = lps[j - 1];
					return geraLocalizacaoWord(index, i - j);
				}
				// mismatch after j matches
				else if (existeText_e_letrasDiferentes(word, index)) {
					// Do not match lps[0..lps[j-1]] characters,
					// they will match anyway
					if (j != 0)
						j = lps[j - 1];
					else
						i = i + 1;
				}
			}
			i = 0;
			j = 0;
		}

		return null;
	}

	private boolean existeText_e_letrasDiferentes(String word, int index) {
		return aindaExisteTexto(index) && !letrasIguais(word, index);
	}

	private WordLocation geraLocalizacaoWord(int linha, int coluna) {
		WordLocation location = new WordLocation();
		location.setColumn(coluna);
		location.setLine(linha);
		return location;
	}

	private boolean quantidadeLetrasText_Igual_quantidadeLetrasWord(int M) {
		return j == M;
	}

	private boolean letrasIguais(String word, int index) {
		return this.linhas.get(index).charAt(i) == word.charAt(j);
	}

	private boolean aindaExisteTexto(int index) {
		return i < this.tamanhoLinhas.get(index);
	}

	private int[] computeLPSArray(String pat, int m) {
		int lps[] = new int[m];
		// length of the previous longest prefix suffix
		int len = 0;
		int i = 1;
		lps[0] = 0; // lps[0] is always 0

		// the loop calculates lps[i] for i = 1 to M-1
		while (i < m) {
			if (pat.charAt(i) == pat.charAt(len)) {
				len++;
				lps[i] = len;
				i++;
			} else { // (pat[i] != pat[len])

				// This is tricky. Consider the example.
				// AAACAAAA and i = 7. The idea is similar
				// to search step.
				if (len != 0) {
					len = lps[len - 1];

					// Also, note that we do not increment
					// i here
				} else{ // if (len == 0)
					lps[i] = len;
					i++;
				}
			}
		}
		return lps;
	}
}
