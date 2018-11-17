package test.java;

import java.util.Scanner;

import busca.ISearchStrategy;
import busca.RabinKarpSearch;
import busca.WordLocation;

public class StringMatchingTest {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Type Main String : ");
		String str = in.nextLine();

		System.out.println("Type String Pattern to search : ");
		String P = in.nextLine();
		in.close();

		ISearchStrategy sc = new RabinKarpSearch();
		sc.prepareSearch(str);
		WordLocation location = sc.search(P);

		if (location != null) {
			if (colunaValida(location) && linhaValida(location)) {
				System.out.println("pattern found at line: " + location.getLine() + " and collum: " + location.getColumn());
			}
		}else
			System.out.println(location);
	}

	private static boolean linhaValida(WordLocation location) {
		return location.getLine() >= 0;
	}

	private static boolean colunaValida(WordLocation location) {
		return location.getColumn() >= 0;
	}
}