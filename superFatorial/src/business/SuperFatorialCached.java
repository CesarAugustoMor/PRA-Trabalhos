package business;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * @author C�sar Augusto Moro F�rst
 *
 */
public class SuperFatorialCached extends SuperFatorial {
	/**
	 * Cache em mem�ria para o super fatorial
	 **/
	private HashMap<Integer,BigInteger> cache = new HashMap<Integer, BigInteger>();
	
	protected BigInteger getFatorial( BigInteger numero ) {
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
			System.out.println("N�o tem no cache");
			result = calcFatEAdd(numero);
			return result;
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