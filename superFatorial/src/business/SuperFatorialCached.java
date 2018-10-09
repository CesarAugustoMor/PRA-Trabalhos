package business;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * @author César Augusto Moro Fürst
 *
 */
public class SuperFatorialCached extends SuperFatorial {
	//private static final Logger LOG = Logger.getLogger(SuperFatorialCached.class.getCanonicalName());
	/**
	 * Cache em memória para o super fatorial
	 **/
	private HashMap<Integer, BigInteger> cache = new HashMap<Integer, BigInteger>();

	protected BigInteger getFatorial(BigInteger numero) {
		BigInteger result;
		// procura no cache primeiro se existir retorna o valor
		// se nao existir calcula e adiciona no cache
		if (cache.isEmpty()) {
			System.out.println("Cache vazio");
			result = calcFatEAdd(numero);
			return result;
		} else if (cache.containsKey(key(numero))) {
			System.out.println("A chave existe no cache");
			return cache.get(key(numero));
		} else {
			System.out.println("A chave não existe no cache");
			result = calcFatEAdd(numero);
			return result;
		}
	}

	private BigInteger calcFatEAdd(BigInteger numero) {
		BigInteger result;
		result = super.getFatorial(numero);
		cache.put(key(numero), result);
		return result;
	}

	private Integer key(BigInteger numero) {
		return Integer.parseInt(Long.toString(numero.longValue()));
	}
}