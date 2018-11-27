/**
 * Codigo base retirado de:
 * https://github.com/raymanrt/aho-corasick/
 * talvez:
 * https://sites.google.com/site/indy256/algo/aho_corasick
 */
package busca;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author César Augusto Moro Fürst
 *
 */
public class AhoCorasick implements ISearchStrategy {

	private static final int ALPHABET_SIZE = 256; // Alphabet size (# of symbols)
	private ArrayList<StringBuilder> linhas;
	private Node[] nodes;
	private int nodeCount;
	private int maxNodes = 1000;

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.ISearchStrategy#prepareSearch(java.lang.String)
	 */
	@Override
	public void prepareSearch(String text) {
		this.nodes = new Node[maxNodes];
		// create root
		this.nodes[0] = new Node();
		this.nodes[0].suffLink = 0;
		this.nodes[0].parent = -1;
		this.nodeCount = 1;
		this.linhas = prePrepareSearch(text);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.ISearchStrategy#search(java.lang.String)
	 */
	@Override
	public WordLocation search(String word) {
		addString(word);
		int node = 0;
		int linha = 0;
		for (int i = 0; i < linhas.size(); i++) {

			for (char ch : linhas.get(i).toString().toCharArray()) {
				node = transition(node, ch);
			}
			if (nodes[node].leaf) {
				linha = i;
				break;
			} else if (i == (linhas.size() - 1)) {
				WordLocation nul = new WordLocation();
				nul.setColumn(-1);
				nul.setLine(-1);
				return nul;
			}

		}
		WordLocation location = new WordLocation();
		location.setLine(linha);
		for (int i = 0; i < linhas.get(linha).length(); i++) {
			if (linhas.get(linha).charAt(i) == word.charAt(0)) {
				location.setColumn(i);
				break;
			}
		}
		return location;
	}

	public static class Node {
		int parent;
		char charFromParent;
		int suffLink = -1;
		int[] children = new int[ALPHABET_SIZE];
		int[] transitions = new int[ALPHABET_SIZE];
		boolean leaf;
		{
			Arrays.fill(children, -1);
			Arrays.fill(transitions, -1);
		}
	}

	public void addString(String s) {
		int cur = 0;
		for (char ch : s.toCharArray()) {
			int c = ch - 'a';
			if (nodes[cur].children[c] == -1) {
				nodes[nodeCount] = new Node();
				nodes[nodeCount].parent = cur;
				nodes[nodeCount].charFromParent = ch;
				nodes[cur].children[c] = nodeCount++;
			}
			cur = nodes[cur].children[c];
		}
		nodes[cur].leaf = true;
	}

	public int suffLink(int nodeIndex) {
		Node node = nodes[nodeIndex];
		if (node.suffLink == -1)
			node.suffLink = node.parent == 0 ? 0 : transition(suffLink(node.parent), node.charFromParent);
		return node.suffLink;
	}

	public int transition(int nodeIndex, char ch) {
		int c = ch - 'a';
		Node node = nodes[nodeIndex];
		if (node.transitions[c] == -1)
			node.transitions[c] = node.children[c] != -1 ? node.children[c]
					: (nodeIndex == 0 ? 0 : transition(suffLink(nodeIndex), ch));
		return node.transitions[c];
	}

	@Override
	public void limpa() {
		linhas=null;
		nodes=null;
	}

}
