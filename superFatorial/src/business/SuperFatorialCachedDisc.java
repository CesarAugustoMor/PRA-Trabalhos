package business;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

//import business.FatorialRandomAccessReaderIO;

public class SuperFatorialCachedDisc extends SuperFatorial {
	private static final int FATORIAL_SIZE = 1000;
	private static final String NOMEARQUIVO = "Fatorial.txt";
	// private static final Logger LOG =
	// Logger.getLogger(SuperFatorialCachedDisc.class.getCanonicalName());
	/**
	 * Cache em memória para o super fatorial
	 **/
	private Map<Integer, BigInteger> cache = new HashMap<Integer, BigInteger>();
	FatorialFileReader arquivo = new FatorialRandomAccessReaderIO();

	protected BigInteger getFatorial(BigInteger numero) {


		// Verifica se foi carregado algum dado do arquivo;
		if (cache.isEmpty()) {
			System.out.println("O cache esta vazio");
			return calcFatEAdd(numero);
		} else if (cache.containsKey(key(numero))) {
			System.out.println("O valor foi encontrado no cache");
			return cache.get(key(numero));
		} else if (!arquivoValueNull()) {
			System.out.println("O cache esta vazio");
			boolean file = carregaArquivo(key(numero));
			System.out.println("Após carga do arquivo " + file);
			getValueAndSetNullValue(numero);
			return cache.get(key(numero));
		} else {
			System.out.println("O valor não foi encontrado no cache");
			return calcFatEAdd(numero);
		}
	}

	private void getValueAndSetNullValue(BigInteger numero) {
		cache.put(key(numero), arquivo.getValue());
		arquivo.setValue(null);
	}

	private boolean arquivoValueNull() {
		return arquivo.getValue() == null;
	}

	public boolean carregaArquivo(Integer key) {
		try {
			arquivo.reader(NOMEARQUIVO, key);
			System.out.println("Arquivo lido com sucesso");
			return true;
		} catch (IOException e) {
			System.out.println("Problema na leitura do arquivo" + e);
		}
		return false;
	}

	private BigInteger calcFatEAdd(BigInteger numero) {
		BigInteger result;
		result = super.getFatorial(numero);
		if (isMenor(result.toString(), FATORIAL_SIZE)) {
			System.out.println("Fatorial menor que " + FATORIAL_SIZE);
			arquivo.escreveArquivo(NOMEARQUIVO, key(numero), geraFatDesp(result, FATORIAL_SIZE + 1));
			cache.put(key(numero), result);
		} else if (isIgual(result.toString(), FATORIAL_SIZE)) {
			System.out.println("Fatorial igual " + FATORIAL_SIZE);
			arquivo.escreveArquivo(NOMEARQUIVO, key(numero), geraFatDesp(result, 1));
			cache.put(key(numero), result);
		} else {
			System.out.println("Fatorial muito grande");
			result = BigInteger.ONE.negate();
		}
		return result;
	}

	private boolean isIgual(String sFat, int tam) {
		return sFat.length() == tam;
	}

	private Integer key(BigInteger numero) {
		return Integer.parseInt(Long.toString(numero.longValue()));
	}

	private int calculaDesperdicio(BigInteger fat, int tam) {
		int desperdicio = 0;
		String sFat = fat.toString();
		if (isMenor(sFat, tam)) {
			desperdicio = tam - sFat.length();
		}
		return desperdicio;
	}

	private boolean isMenor(String sFat, int tam) {
		return sFat.length() < tam;
	}

	private String geraDesperdicio(int tam) {
		StringBuilder desperdicio = new StringBuilder(",");
		for (int i = 1; i < tam; i++) {
			desperdicio.append(' ');
		}
		return desperdicio.toString();
	}

	private String geraFatDesp(BigInteger num, int tam) {
		StringBuilder retorno = new StringBuilder(num.toString());
		retorno.append(geraDesperdicio(calculaDesperdicio(num, tam)));
		return retorno.toString();
	}
}
