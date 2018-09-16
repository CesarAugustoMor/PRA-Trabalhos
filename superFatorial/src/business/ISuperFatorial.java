package business;

import java.math.BigInteger;

import exceptions.InputException;
//import java.math.BigInteger;

/**
 * Assinatura para o SuperFatorial
 * @author C�sar Augusto Moro F�rst
 *
 */
public interface ISuperFatorial {
	
	/**
	 * Recebe o fatorial do n�mero e retorna o superfatorial dele
	 * @param numero para o qual calcularemos o superfatorial
	 * @return super fatorial
	 * @throws InputException indicando que o valor digitado � inv�lido
	 */
	public BigInteger getSuperFatorial(BigInteger numero) throws InputException;

}
