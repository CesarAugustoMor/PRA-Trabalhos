package test.java;

import java.util.Scanner;

import busca.AhoCorasick;//não funciona -41
import busca.BoyerMooreSearch;//51, 160
import busca.ISearchStrategy;
import busca.KMPSearch;//51, 160
import busca.NaiveSearch;//51, 160
import busca.RabinKarpSearch;//fecha sem retornar resultado, 160
import busca.RadixTree;//não funciona -41
import busca.WordLocation;

public class StringMatchingTest {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Type Main String : ");
		String str = in.nextLine();

		System.out.println("Type String Pattern to search : ");
		String P = in.nextLine();
		in.close();

		ISearchStrategy sc = new BoyerMooreSearch();
		//ISearchStrategy sc = new KMPSearch();
		//ISearchStrategy sc = new NaiveSearch();
		//ISearchStrategy sc = new RabinKarpSearch();
		long tempoInicial = System.currentTimeMillis();
		sc.prepareSearch(str);
		WordLocation location = sc.search(P);
		long tempoFinal=System.currentTimeMillis();

			if (colunaValida(location) && linhaValida(location)) {
				System.out.println("pattern found at line: " + location.getLine() + " and collum: " + location.getColumn());
				System.out.println("Tempo inical: " + tempoInicial + "\nTempo Final: "+ tempoFinal);
				System.out.println("\nTempo de execução: " + (tempoFinal - tempoInicial));
			}else
			System.out.println("Palavra não encontrada.");
	}

	private static boolean linhaValida(WordLocation location) {
		return location.getLine() >= 0;
	}

	private static boolean colunaValida(WordLocation location) {
		return location.getColumn() >= 0;
	}
}