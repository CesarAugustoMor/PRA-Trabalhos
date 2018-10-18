package business;

import static java.nio.file.StandardOpenOption.READ;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.EnumSet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Crc {
	private static final String CRC_ARQUIVO = "crc.txt";
	//private static final int DATA_INDEX = 0;
	//private static final String SEPARATOR = ",";
	private final String ENCODING = System.getProperty("file.encoding");

	// private static final Logger LOG = Logger.getLogger(Crc.class.getCanonicalName());

	public StringBuffer lerArquivoDeCacheDosFatoriais(String nomeArquivo) throws IOException {
		final int RECORD_SIZE_IN_BYTES = 101;

		// No buffer deve caber o registro inteiro
		//final int BUFFER_SIZE_IN_BYTES = RECORD_SIZE_IN_BYTES;

		Path path = Paths.get(nomeArquivo);
		SeekableByteChannel sbc = Files.newByteChannel(path, EnumSet.of(READ));
		ByteBuffer buff = ByteBuffer.allocate((int) (/*BUFFER_SIZE_IN_BYTES**/sbc.size()));
		System.out.println("sbc tamanho: " + sbc.size());
		StringBuffer conteudo = new StringBuffer();
		for (int i = 0; i < sbc.size()/RECORD_SIZE_IN_BYTES; i++) {
			Long offset = (long) (RECORD_SIZE_IN_BYTES * i);
			System.out.println("Arquivo localizado: " + nomeArquivo);
			sbc.position(offset);
			System.out.println("Ponteiro posicionado para leitura na posição: " + sbc.position());
			int rd = sbc.read(buff);
			System.out.println("Dados lidos do arquivo " + rd);
			buff.flip();
			System.out.println("Apresentando resultados");
			String line = Charset.forName(ENCODING).decode(buff).toString();
			System.out.println(line);
			conteudo.append(line);

			buff.rewind();
			buff.clear();
		}
		System.out.println("sbc tamanho: " + sbc.size());
		sbc.close();
		System.out.println("Conteudo " + conteudo.capacity());
		return conteudo;
	}

	public boolean gerarResumo(String nomeArquivo)  {
		try {
			StringBuffer conteudo = lerArquivoDeCacheDosFatoriais(nomeArquivo);
			System.out.println("Conteudo: " + conteudo.capacity());
			try {
				String  resumo = calcularResumo(conteudo);
				System.out.println("Resumo: " + resumo);
				boolean r = gravarResumoEmUmArquivoDeControleParaFatoriais(CRC_ARQUIVO, resumo);
				System.out.println(System.currentTimeMillis() + ": " + r);
				return true;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return false;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gera uma String de retorno que contem o resumo do conteudo recebido
	 *
	 * @param conteudo
	 * @return byte[]
	 */
	private String calcularResumo(StringBuffer conteudo) throws NoSuchAlgorithmException {
		MessageDigest messageDigest;
		byte[] messageDigestSHA = {};
		messageDigest = MessageDigest.getInstance("SHA-512");
		messageDigest.update(conteudo.toString().getBytes());
		messageDigestSHA = messageDigest.digest();
		
		return messageDigestSHA.toString();
	}

	public boolean verificarResumo(String nomeArquivo)  {
		try {
			StringBuffer conteudo = lerArquivoDeCacheDosFatoriais(nomeArquivo);
			String resumo;
			try {
				resumo = calcularResumo(conteudo);
				try {
					byte[] resumoGravado = reader(CRC_ARQUIVO);
					return resumo.equals(resumoGravado);
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
				return false;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	private boolean gravarResumoEmUmArquivoDeControleParaFatoriais(String arquivo, String resumo) {
		char[] charResumo=resumo.toCharArray();

		StringBuffer stringBuffer = new StringBuffer();
		/*for (byte bytes : resumo) {
			stringBuffer.append(String.format("%02x", bytes & 0xff));
		}*/
		for (int i = 0; i < charResumo.length; i++) {
			stringBuffer.append(String.format("%02x", charResumo[i] & 0xff));
		}
		System.out.println("stringBuffer " + stringBuffer);
		// GRAVAR O STRINGBUFFER NO ARQUIVO

		try {
			FileWriter write = new FileWriter(arquivo);
			BufferedWriter escreveArq = new BufferedWriter(write);
			//ByteBuffer line = Charset.forName(ENCODING).encode(result);
			String line = Charset.forName(ENCODING).encode(CharBuffer.wrap(stringBuffer.toString())).toString();
			System.out.print("Linha: " + line.toCharArray());
			escreveArq.write(line.toCharArray());//write(line);
			escreveArq.close();
			write.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Realiza a leitura usando as classes do JAVA IO
	 */
	public byte[] reader(String nome) throws IOException {
		File arq = new File(nome);
		FileReader read = new FileReader(arq);
		BufferedReader lerArq = new BufferedReader(read);
		byte[] linha = readLine(lerArq);
		lerArq.close();
		read.close();

		return linha;
	}

	private byte[] readLine(BufferedReader lerArq) throws IOException {
		String linha = lerArq.readLine();
		byte[] b = linha.getBytes(ENCODING);
		System.out.println("Quantidade de bytes na linha -> " + b.length + "\nConteudos dos bytes: " + b);
		/*
		 * ByteBuffer buff = ByteBuffer.allocate(b.length); buff.put(b);
		 * LOG.info("Quantidade de bytes na linha -> "+ b.length);
		 * 
		 * System.out.println("Apresentando resultados");
		 * String line =
		 * Charset.forName(ENCODING).decode(buff).toString();
		 * System.out.println(line);
		 * StringBuffer conteudo = new
		 * StringBuffer(line.split(SEPARATOR)[DATA_INDEX].trim());
		 * System.out.println(conteudo + "\n" + buff.toString());
		 */

		return b;
	}
}
