/**
 * 
 */
package exceptions;

/**
 * Lançada quando o valor é negativo
 * @author udesc
 *
 */
public class NegativeValueEnteredException extends InputException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String NEGATIVE_EXCEPTION = "Valores negativos não possuem fatorial";
	
	/**
	 * Construtor da classe com a mensagem padrão
	 */
	public NegativeValueEnteredException() {
		super(NEGATIVE_EXCEPTION);
	}

}

