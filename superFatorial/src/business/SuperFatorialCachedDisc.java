package business;

import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

//import business.FatorialRandomAccessReaderIO;

public class SuperFatorialCachedDisc extends SuperFatorial {
	private static final String nomeArquivo = "superFatorial.txt";
	/**
	 * Cache em memória para o super fatorial
	 **/
	private Map<Integer, BigInteger> cache = new HashMap<Integer, BigInteger>();
	FatorialFileReader arquivo = new FatorialRandomAccessReaderIO();

	protected BigInteger getFatorial(BigInteger numero) {
		// RandomAccessFile tmp =new RandomAccessFile(new File("superFatorial.txt"),
		// "rw");
		// tmp.close();
		caregaArquivo(key(numero));
		if (arquivo.getValue()!=null) {
			cache.put(arquivo.getKey(), arquivo.getValue());
		}
		if (!arquivo.getConteudo().isEmpty()) {
			cache.put(key(numero), arquivo.getConteudo().get(key(numero)));
		}

		BigInteger result;
		// procura no cache primeiro se existir retorna o valor
		// se nao existir calcula e adiciona no cache
		if (cache.isEmpty()) {
			System.out.println("Vazio");
			result = calcFatEAdd(numero);
			return result;
		} else if (cache.containsKey(key(numero))) {
			System.out.println("No cache");
			return cache.get(key(numero));
		} else {
			System.out.println("Não tem no cache");
			result = calcFatEAdd(numero);
			return result;
		}
	}

	private void caregaArquivo(Integer key) {
		try {
			arquivo.reader(nomeArquivo, key);
			cache = arquivo.getConteudo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private BigInteger calcFatEAdd(BigInteger numero) {
		BigInteger result;
		result = super.getFatorial(numero);
		if (result.toString().length() < 100) {
			escreveArquivo(numero, geraFatDesp(result, 100));
			//cache.put(key(numero), result);
		} else if (result.toString().length() == 100) {
			escreveArquivo(numero, geraFatDesp(result, 0));
			//cache.put(key(numero), result);
		} else
			result = BigInteger.ONE.negate();
		return result;
	}

	private void escreveArquivo(BigInteger numero, String result) {
		StringBuilder tmp = new StringBuilder(",");
		tmp.append(result);
		result=tmp.toString();

		final int RECORD_SIZE_IN_BYTES = 101;
		final int BUFFER_SIZE_IN_BYTES = RECORD_SIZE_IN_BYTES+1;

		Path path = Paths.get(nomeArquivo);
		ByteBuffer buff = ByteBuffer.allocate(BUFFER_SIZE_IN_BYTES);
		Long offset = (long) (RECORD_SIZE_IN_BYTES * (key(numero)-1) );
		try {
			SeekableByteChannel sbc = Files.newByteChannel(path, EnumSet.of(WRITE));
			sbc.position(offset);
			if (!(sbc.size() < offset)) {
				buff.put(result.getBytes());
				sbc.write(buff);
				buff.flip();

				buff.rewind();
				buff.clear();
			}
		} catch (IOException e) {
			System.out.println(nomeArquivo);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Integer key(BigInteger numero) {
		return Integer.parseInt(Long.toString(numero.longValue()));
	}

	private int calculaDesperdicio(BigInteger fat, int tam) {
		int desperdicio = 0;
		String sFat = fat.toString();
		if (sFat.length() < tam) {
			desperdicio = tam - sFat.length();
		}
		return desperdicio;
	}

	private String geraDesperdicio(int tam) {
		StringBuilder desperdicio = new StringBuilder();
		for (int i = 0; i < tam; i++) {
			desperdicio.append(' ');
		}
		desperdicio.append(',');
		return desperdicio.toString();
	}

	private String geraFatDesp(BigInteger num, int tam) {
		StringBuilder retorno = new StringBuilder(num.toString());
		retorno.append(geraDesperdicio(calculaDesperdicio(num, tam)));
		return retorno.toString();
	}
}
