package business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

//import business.FatorialRandomAccessReaderIO;

public class SuperFatorialCachedDisc extends SuperFatorial{
	/**
	 * Cache em memória para o super fatorial
	 **/
	private Map<Integer, BigInteger> cache = new HashMap<Integer, BigInteger>();
	FatorialFileReader arquivo = new FatorialRandomAccessReaderIO();

	protected BigInteger getFatorial( BigInteger numero ) {
		//RandomAccessFile tmp =new RandomAccessFile(new File("superFatorial.txt"), "rw");
		//tmp.close();
		caregaArquivo(key(numero));
		
		BigInteger result;
		// procura no cache primeiro se existir retorna o valor
		// se nao existir calcula e adiciona no cache
		if (cache.isEmpty()) {
			System.out.println("Vazio");
			result = calcFatEAdd(numero);
			return result;
		}else
			if (cache.containsKey(key(numero))) {
				System.out.println("No cache");
			return cache.get(key(numero));
		}else {
			System.out.println("Não tem no cache");
			result = calcFatEAdd(numero);
			return result;
		}
	}

	private void caregaArquivo(Integer key) {
		try {
			arquivo.reader("superFatorial.txt",key);
			cache=arquivo.getConteudo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private BigInteger calcFatEAdd(BigInteger numero) {// Utilizar https://docs.oracle.com/javase/8/docs/api/java/io/RandomAccessFile.html
		BigInteger result;
		result=super.getFatorial(numero);
		if(result.toString().length()<100) {
			RandomAccessFile arquivo2 = null;
			try {
				 arquivo2= new RandomAccessFile(new File("superFatorial.txt"), "rw");
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				arquivo2.write(geraFatDesp(result, 100).getBytes(), key(numero)*100, 100);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cache.put(key(numero), result);
		}else {
			result=BigInteger.ONE.negate();
		}
		return result;
	}
	
	private Integer key(BigInteger numero) {
		return Integer.parseInt(Long.toString(numero.longValue()));
	}
	
	private int calculaDesperdicio(BigInteger fat, int tam) {
		int desperdicio=0;
		String sFat=fat.toString();
		if(sFat.length()<tam) {
			desperdicio=tam-sFat.length();
		}
		return desperdicio;
	}
	
	private String geraDesperdicio(int tam) {
		StringBuilder desperdicio= new StringBuilder();
		desperdicio.setLength(tam);
		for (int i = 0; i < tam; i++) {
			desperdicio.append(' ');
		}
		desperdicio.append(',');
		return desperdicio.toString();
	}
	
	private String geraFatDesp(BigInteger num, int tam) {
		StringBuilder retorno=new StringBuilder(num.toString());
		retorno.append(geraDesperdicio(calculaDesperdicio(num, tam)));
		return retorno.toString();
	}
}

