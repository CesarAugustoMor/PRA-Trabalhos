/**
 * 
 */
package business;

import java.math.BigInteger;

import exceptions.InputException;
import exceptions.NegativeValueEnteredException;
//import exceptions.ZeroEnteredException;;

/**
 * Implementa��o do super fatorial
 * 
 * @author C�sar Augusto Moro F�rst
 *
 */

public class SuperFatorial implements ISuperFatorial {

	public BigInteger getSuperFatorial(BigInteger numero) throws InputException {
		if (isZero(numero))
			return BigInteger.ONE;
		else if (isNegativo(numero))
			throw new NegativeValueEnteredException();
		else {
			BigInteger output = BigInteger.ONE;
			for (int i = 2; i <= numero.intValue(); i++) {
				BigInteger indice = BigInteger.valueOf(i);
				BigInteger fat = getFatorial(indice);
				System.out.println("Fatorial � momento: " + fat + " Do numero: " + indice);
				if (!isNegativo(fat)) {
					output = output.multiply(fat);
					System.out.println("Super fatorial at� o momento: " + output + " Do numero: " + numero);
				} else {
					output = fat;
					break;
				}
			}
			return output;
		}
	}

	private boolean isZero(BigInteger numero) {
		return BigInteger.ZERO == numero;
	}

	private boolean isNegativo(BigInteger numero) {
		if (numero.compareTo(BigInteger.ZERO) == -1) {
			return true;
		}
		return false;
	}

	protected BigInteger getFatorial(BigInteger numero) {
		if (isZero(numero))
			return BigInteger.ONE;
		BigInteger output = BigInteger.ONE;
		for (int i = 2; i <= numero.intValue(); i++) {
			BigInteger indice = BigInteger.valueOf(i);
			output = output.multiply(indice);
		}
		return output;
	}

}
