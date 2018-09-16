package business;

import java.math.BigInteger;

import exceptions.InputException;
//import java.math.BigInteger;

/**
 * Assinatura para o SuperFatorial
 * @author César Augusto Moro Fürst
 *
 */
public interface ISuperFatorial {
	
	/**
	 * Recebe o fatorial do número e retorna o superfatorial dele
	 * @param numero para o qual calcularemos o superfatorial
	 * @return super fatorial
	 * @throws InputException indicando que o valor digitado é inválido
	 */
	public BigInteger getSuperFatorial(BigInteger numero) throws InputException;

}
