package busca;

import java.util.ArrayList;

/**
 *
 * @author C�sar Augusto Moro F�rst
 */
public class NaiveSearch implements ISearchStrategy {
	private ArrayList<StringBuilder> linhas;

	@Override
	public WordLocation search(String word) {
		if (this.linhas == null) {
			System.out.println("Texto Nullo, utilize primeiro a fun��o 'prepareSearch'");
			return null;
		}

		WordLocation location = new WordLocation();
		int M = word.length();

		for (int k = 0; k < this.linhas.size(); k++) {
			int N = this.linhas.get(k).length();

			/* A loop to slide pat one by one */
			for (int i1 = 0; i1 <= N - M; i1++) {

				int j1;

				/*
				 * For current index i, check for pattern match
				 */
				for (j1 = 0; j1 < M; j1++)
					if (this.linhas.get(k).charAt(i1 + j1) != word.charAt(j1))
						break;

				if (j1 == M) { // if pat[0...M-1] = txt[i, i+1, ...i+M-1]
					location.setColumn(i1);
					location.setLine(k);
					//System.out.println("Word found at index " + i1);
					return location;
				}
			}
		}
		WordLocation nul =new WordLocation();
		nul.setColumn(-1);
		nul.setLine(-1);
		return nul;

	}

	@Override
	public void prepareSearch(String text) {
		this.linhas = prePrepareSearch(text);
	}

	@Override
	public void limpa() {
		linhas=null;
		
	}

}
