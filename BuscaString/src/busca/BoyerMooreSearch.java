/**
 * Codigo base retirado de:
 * http://www.codecodex.com/wiki/Boyer-Moore_Algorithm_Examples
 */
package busca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author César Augusto Moro Furst
 *
 */
public class BoyerMooreSearch implements ISearchStrategy {
	private ArrayList<Integer> tamanhoLinhas;
	private ArrayList<StringBuilder> linhas;
	private int alignedAt = 0;

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
		int m = word.length();
		Map<Character, Integer> rightMostIndexes = preprocessForBadCharacterShift(word);

		for (int index = 0; index < this.linhas.size(); index++) {
			while (alignedAt + (m - 1) < this.tamanhoLinhas.get(index)) {
				for (int indexWord = m - 1; indexWord >= 0; indexWord--) {
					int indexText = alignedAt + indexWord;
					char x = this.linhas.get(index).charAt(indexText);
					char y = word.charAt(indexWord);
					if (indexText >= this.tamanhoLinhas.get(index))
						break;
					if (letrasDiferentes(x, y)) {
						Integer r = rightMostIndexes.get(x);
						if (r == null) {
							alignedAt = indexText + 1;
						} else {
							int shift = indexText - (alignedAt + r);
							alignedAt += shift > 0 ? shift : 1;
						}
						break;
					} else if (indexWord == 0) {
						return geraLocalizacaoWord(index, alignedAt);
					}
				}
			}
		}
		WordLocation nul =new WordLocation();
		nul.setColumn(-1);
		nul.setLine(-1);

		return nul;
	}

	private boolean letrasDiferentes(char a, char b) {
		return a != b;
	}

	private WordLocation geraLocalizacaoWord(int linha, int coluna) {
		WordLocation location = new WordLocation();
		location.setColumn(coluna);
		location.setLine(linha);
		return location;
	}

	private static Map<Character, Integer> preprocessForBadCharacterShift(String word) {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = word.length() - 1; i >= 0; i--) {
			char c = word.charAt(i);
			if (!map.containsKey(c))
				map.put(c, i);
		}
		return map;
	}

	@Override
	public void limpa() {
		tamanhoLinhas=null;
		linhas=null;
		
	}
}