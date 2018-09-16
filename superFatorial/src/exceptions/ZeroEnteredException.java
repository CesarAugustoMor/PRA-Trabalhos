/**
 * 
 */
package exceptions;

/**
 * Lan�ada quando o valor � ZERO
 * @author udesc
 *
 */
public class ZeroEnteredException extends InputException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ZERO_EXCEPTION = "Zero n�o possui fatorial";

	/**
	 * Construtor da classe
	 */
	public ZeroEnteredException() {
		super(ZERO_EXCEPTION);
	}

}

