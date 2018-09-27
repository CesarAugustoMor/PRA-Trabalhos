package business;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

//import business.FatorialRandomAccessReaderIO;

public class SuperFatorialCachedDisc extends SuperFatorial{
	/**
	 * Cache em memória para o super fatorial
	 **/
	private Map<Integer, BigInteger> cache = new HashMap<Integer, BigInteger>();
	FatorialFileReader arquivo = null;

	protected BigInteger getFatorial( BigInteger numero ) {
		
		caregaArquivo(1);
		
		BigInteger result;
		// procura no cache primeiro se existir retorna o valor
		// se nao existir calcula e adiciona no cache
		if (cache.isEmpty()) {
			System.out.println("Vazio");
			result = calcFatEAdd(numero);
			return result;
		}else
			if (cache.containsKey(key(numero))) {
				System.out.println("No cache");
			return cache.get(key(numero));
		}else {
			System.out.println("Não tem no cache");
			result = calcFatEAdd(numero);
			return result;
		}
	}

	private void caregaArquivo(Integer key) {
		try {
			arquivo.reader("cacheSuperFat",key);
			cache=arquivo.getConteudo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private BigInteger calcFatEAdd(BigInteger numero) {
		BigInteger result;
		result=super.getFatorial(numero);
		cache.put(key(numero), result);
		return result;
	}
	
	private Integer key(BigInteger numero) {
		return Integer.parseInt(Long.toString(numero.longValue()));
	}
}
