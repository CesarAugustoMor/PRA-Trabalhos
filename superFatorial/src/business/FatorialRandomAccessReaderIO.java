package business;

import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 * Leitura e escrita Randomica
 * 
 * @author César Augusto Moro Fürst
 * 
 */
class FatorialRandomAccessReaderIO extends FatorialFileReader {
	//private static final Logger LOG = Logger.getLogger(FatorialRandomAccessReaderIO.class.getCanonicalName());
	private final String ENCODING = System.getProperty("file.encoding");

	/**
	 * Realiza a leitura usando as classes do JAVA IO
	 */
	@Override
	public void reader(String nome, int key) throws IOException {

		final int RECORD_SIZE_IN_BYTES = 101;

		// No buffer deve caber o registro inteiro
		final int BUFFER_SIZE_IN_BYTES = RECORD_SIZE_IN_BYTES;

		Path path = Paths.get(nome);
		ByteBuffer buff = ByteBuffer.allocate(BUFFER_SIZE_IN_BYTES);
		String encoding = System.getProperty("file.encoding");
		SeekableByteChannel sbc = null;
		try {
			sbc = Files.newByteChannel(path, EnumSet.of(READ));
		} catch (IOException e) {
			System.out.println("Problema na leitura do arquivo" + e);
		}
		Long offset = (long) (RECORD_SIZE_IN_BYTES * key);
		System.out.println("Arquivo localizado");
		sbc.position(offset);
		System.out.println("Ponteiro posicionado para leitura na posição: " + sbc.position());
		if (leituraValida(sbc, offset)) {
			sbc.read(buff);
			System.out.println("Dados lidos do arquivo");
			buff.flip();
			System.out.println("Apresentando resultados");
			String line = Charset.forName(encoding).decode(buff).toString();
			System.out.println(line);
			this.setValue(new BigInteger(line.split(SEPARATOR)[DATA_INDEX].trim()));
			System.out.println(new BigInteger(line.split(SEPARATOR)[DATA_INDEX].trim()));

			buff.rewind();
			buff.clear();

		} else
			System.out.println("Nada lido do arquivo/Não encontrado");
		sbc.close();
		System.out.println("Arquivo fechado (leitura)");
	}

	private boolean leituraValida(SeekableByteChannel sbc, Long offset) throws IOException {
		return (sbc != null) && (sbc.size() > offset);
	}

	/**
	 * Realiza a escrita usando as classes do JAVA IO
	 */
	public void escreveArquivo(String arquivo, Integer key, String result) {

		final int FATORIAL_SIZE = 100;
		final int LINE_FEED_SIZE = 1;
		final int RECORD_SIZE_IN_BYTES = FATORIAL_SIZE + LINE_FEED_SIZE;

		Path path = Paths.get(arquivo);
		System.out.println("preparando para escrita");
		Long offset = (long) (RECORD_SIZE_IN_BYTES * key );
		try {
			SeekableByteChannel sbc = Files.newByteChannel(path, EnumSet.of(WRITE));
			System.out.println("Definindo o canal: " + offset + "\nTamanho do result: " + result.length() + "\nConteudo de result: " + result);
			sbc.position(offset);
			System.out.println("Ponteiro posicionado para escrita na posição: " + sbc.position());
			ByteBuffer line = Charset.forName(ENCODING).encode(result);
			sbc.write(line);
			System.out.println("Ponteiro posicionado apos escrita na posição: " + sbc.position());
			sbc.close();
			System.out.println("Arquivo fechado (escrita)");
		} catch (IOException e) {
			System.out.println("Problema na leitura do arquivo" + e);
		}
	}
}
