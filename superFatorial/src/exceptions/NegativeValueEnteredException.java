/**
 * 
 */
package exceptions;

/**
 * Lan�ada quando o valor � negativo
 * @author udesc
 *
 */
public class NegativeValueEnteredException extends InputException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String NEGATIVE_EXCEPTION = "Valores negativos n�o possuem fatorial";
	
	/**
	 * Construtor da classe com a mensagem padr�o
	 */
	public NegativeValueEnteredException() {
		super(NEGATIVE_EXCEPTION);
	}

}

