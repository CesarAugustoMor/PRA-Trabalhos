package busca;

import java.util.ArrayList;

/**
 *
 * @author César Augusto Moro Fürst
 */
public class NaiveSearch implements ISearchStrategy {
	private ArrayList<StringBuilder> linhas;

	@Override
	public WordLocation search(String word) {
		if (this.linhas != null) {
			System.out.println("Texto Nullo, utilize primeiro a função 'prepareSearch'");
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
					System.out.println("Word found at index " + i1);
					return location;
				}
			}
		}
		return null;

	}

	@Override
	public void prepareSearch(String text) {
		this.linhas=new ArrayList<>();
		int linhas = 0;
		this.linhas.add(linhas, new StringBuilder());
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '\n') {
				linhas++;
				this.linhas.add(linhas, new StringBuilder());
				System.out.println("Linha :" + linhas + " atual da preparação para busca");
			} else {
				StringBuilder tmp=new StringBuilder(this.linhas.get(linhas).toString());
				tmp.append(text.charAt(i));
				this.linhas.set(linhas, tmp);
			}
		}
		this.linhas.trimToSize();
	}

}
