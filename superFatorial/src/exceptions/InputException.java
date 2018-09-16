/**
 * 
 */
package exceptions;

/**
 * @author udesc
 *
 */
public abstract class InputException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Mensagem do erro
	 * @param msg
	 */
	public InputException(String msg) {
		super(msg);
	}

}

