package business;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe base para os programas de leitura
 * @author udesc
 *
 */
public abstract class FatorialFileReader {
	protected static final int    KEY_INDEX  =   0;
	protected static final int    DATA_INDEX =   1;
	protected static final String SEPARATOR  = ",";
	
	protected Map<Integer, BigInteger> conteudo = new HashMap<Integer, BigInteger>();
	
	protected int key;
	protected BigInteger value;
	
	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public BigInteger getValue() {
		return value;
	}

	/**
	 * @return the conteudo
	 */
	public Map<Integer, BigInteger> getConteudo() {
		return conteudo;
	}

	/**
	 * Método que realiza a leitura dos arquivos
	 * @param nome
	 * @throws IOException
	 */
	public void reader(String nome ) throws IOException {
		this.setKey(1);
		this.reader(nome,this.getKey());
	}
	
	/**
	 * Método que realiza a leitura dos arquivos
	 * @param nome
	 * @throws IOException
	 */
	public abstract void reader(String nome, int key) throws IOException;

	/**
	 * @param value the value to set
	 */
    void setValue(BigInteger value) {
		this.value = value;
	}
    
    @Override
    public int hashCode() {
    	
    	int hash = 1;
        hash = hash * 17 + (conteudo == null ? 0 : conteudo.hashCode());
        hash = hash * 31 + key;
        hash = hash * 13 + (value == null ? 0 : value.hashCode());
        return hash;
    	
    }
   
}
