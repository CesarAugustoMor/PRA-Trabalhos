package business;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Classe base para os programas de leitura
 * 
 * @author udesc
 *
 */
public abstract class FatorialFileReader {
	protected static final int DATA_INDEX = 0;
	protected static final String SEPARATOR = ",";
	protected BigInteger value = null;

	/**
	 * @return the value
	 */
	public BigInteger getValue() {
		return value;
	}

	/**
	 * Método que realiza a leitura dos arquivos
	 * 
	 * @param nome
	 * @throws IOException
	 */
	public void reader(String nome) throws IOException {
		this.reader(nome, 1);
	}

	/**
	 * Método que realiza a leitura dos arquivos
	 * 
	 * @param nome
	 * @throws IOException
	 */
	public abstract void reader(String nome, int key) throws IOException;

	public abstract void escreveArquivo(String arquivo, Integer key, String result);

	/**
	 * @param value the value to set
	 */
	void setValue(BigInteger value) {
		this.value = value;
	}

}
