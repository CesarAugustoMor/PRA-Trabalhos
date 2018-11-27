/**
 * 
 */
package busca;

import java.util.ArrayList;

/**
 * @author César Augusto Moro Fürst
 *
 */
public class RadixTree implements ISearchStrategy {
	private static final int ALPHABET_SIZE = 256; // Alphabet size (# of symbols)
	private static TrieNode root;
	private ArrayList<StringBuilder> linhas;
	private ArrayList<Integer> tamanhoLinhas; // size of lines
	String[][] palavras;

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.ISearchStrategy#prepareSearch(java.lang.String)
	 */
	@Override
	public void prepareSearch(String text) {
		this.tamanhoLinhas = new ArrayList<Integer>();
		this.linhas = prePrepareSearch(text);
		for (int i = 0; i < this.linhas.size(); i++)
			this.tamanhoLinhas.add(i, this.linhas.get(i).length());

		this.palavras = new String[linhas.size()][];

		for (int i = 0; i < linhas.size(); i++) {
			palavras[i] = linhas.get(i).toString().split(" ");
		}
		root = new TrieNode();

		// Construct tree
		for (int i = 0; i < palavras.length; i++)
			for (int j = 0; j < palavras[i].length; j++)
				insert(palavras[i][j]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.ISearchStrategy#search(java.lang.String)
	 */
	@Override
	public WordLocation search(String word) {
		char inicio = word.charAt(0);
		if (existePalavra(word)) {
			int linha = 0;
			int colunaTmp = 0;
			for (int i = 0; i < palavras.length; i++)
				for (int j = 0; j < palavras[i].length; j++)
					if (inicio == palavras[i][0].charAt(0)) {
						linha = i;
						colunaTmp = j;
					}
			int coluna = 1;
			for (int i = 0; i < colunaTmp; i++) {
				coluna += palavras[linha][i].length();
			}
			WordLocation location = new WordLocation();
			location.setColumn(coluna);
			location.setLine(linha);
			return location;
		}
		WordLocation nul =new WordLocation();
		nul.setColumn(-1);
		nul.setLine(-1);

		return nul;
	}

	// Tree node
	static class TrieNode {
		TrieNode[] children = new TrieNode[ALPHABET_SIZE];

		// isEndOfWord is true if the node represents end of a word
		boolean isEndOfWord;

		TrieNode() {
			isEndOfWord = false;
			for (int i = 0; i < ALPHABET_SIZE; i++)
				children[i] = null;
		}
	};

	// If not present, inserts key into tree
	// If the key is prefix of tree node,
	// just marks leaf node
	private static void insert(String key) {
		int level;
		int length = key.length();
		int index;

		TrieNode pCrawl = root;

		for (level = 0; level < length; level++) {
			index = key.charAt(level) - 'a';
			if (pCrawl.children[index] == null)
				pCrawl.children[index] = new TrieNode();

			pCrawl = pCrawl.children[index];
		}

		// mark last node as leaf
		pCrawl.isEndOfWord = true;
	}

	// Returns true if key presents in tree, else false
	static boolean existePalavra(String key) {
		int level;
		int length = key.length();
		int index;
		TrieNode pCrawl = root;

		for (level = 0; level < length; level++) {
			index = key.charAt(level) - 'a';

			if (pCrawl.children[index] == null)
				return false;

			pCrawl = pCrawl.children[index];
		}

		return (pCrawl != null && pCrawl.isEndOfWord);
	}
/*
	// Driver
	public static void main(String args[]) {
		// Input keys (use only 'a' through 'z' and lower case)
		String keys[] = { "the", "a", "there", "answer", "any", "by", "bye", "their" };

		String output[] = { "Not present in trie", "Present in trie" };

		root = new TrieNode();

		// Construct tree
		for (int i = 0; i < keys.length; i++)
			insert(keys[i]);

		// Search for different keys
		if (existePalavra("the") == true)
			System.out.println("the --- " + output[1]);
		else
			System.out.println("the --- " + output[0]);

		if (existePalavra("these") == true)
			System.out.println("these --- " + output[1]);
		else
			System.out.println("these --- " + output[0]);

		if (existePalavra("their") == true)
			System.out.println("their --- " + output[1]);
		else
			System.out.println("their --- " + output[0]);

		if (existePalavra("thaw") == true)
			System.out.println("thaw --- " + output[1]);
		else
			System.out.println("thaw --- " + output[0]);

	}*/

	@Override
	public void limpa() {
		root=null;
		linhas=null;
		tamanhoLinhas=null; // size of lines
		palavras=null;
		
	}
}
