/**
 * 
 */
package business;

import java.math.BigInteger;

import exceptions.InputException;
import exceptions.NegativeValueEnteredException;
//import exceptions.ZeroEnteredException;;


/**
 * Implementação do super fatorial
 * @author César Augusto Moro Fürst
 *
 */


public class SuperFatorial implements ISuperFatorial {

	
	public BigInteger getSuperFatorial(BigInteger numero)  throws InputException{
		if( isZero(numero) ) 
			return BigInteger.ONE;
		else if( isNegativo(numero) ) 
			throw new NegativeValueEnteredException();
		else {
			BigInteger output = BigInteger.ONE; 
        	for(int i = 2; i <= numero.intValue(); i++){
        		BigInteger indice = BigInteger.valueOf(i);
        		BigInteger fat=getFatorial(indice);
        		if( !isNegativo(fat) ) {
        			output = output.multiply(fat);
        		}else {
        			output=BigInteger.ONE.negate();
        		}
        	}
    		return output;
		}
	}

	private boolean isZero(BigInteger numero) {
		return BigInteger.ZERO==numero;
	}
	
	private boolean isNegativo(BigInteger numero) {
		if (numero.compareTo(BigInteger.ZERO)==-1) {
			return true;
		}
		return false;
	}

	protected BigInteger getFatorial( BigInteger numero ) {
		if(numero == BigInteger.ZERO) return BigInteger.ONE; 
        BigInteger output = BigInteger.ONE; 
        for(int i = 2; i <= numero.intValue(); i++){
        	BigInteger indice = BigInteger.valueOf(i);
        	output = output.multiply(indice);
        }
        return output;
	}
	

}

