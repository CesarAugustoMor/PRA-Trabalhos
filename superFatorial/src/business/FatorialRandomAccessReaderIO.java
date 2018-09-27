package business;

import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

import static java.nio.file.StandardOpenOption.READ;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 * Leitura Randomica
 * 
 * @author udesc
 *
 */
class FatorialRandomAccessReaderIO extends FatorialFileReader {

	/**
	 * Realiza a leitura usando as classes do JAVA IO
	 */
	@Override
	public void reader(String nome, int key) throws IOException {

		final int RECORD_SIZE_IN_BYTES = 100;
		
		// No buffer deve caber o registro inteiro
		final int BUFFER_SIZE_IN_BYTES = RECORD_SIZE_IN_BYTES ;
		
		Path                path     = Paths.get(nome);
		ByteBuffer          buff     = ByteBuffer.allocate(BUFFER_SIZE_IN_BYTES);
		String              encoding = System.getProperty("file.encoding");
		SeekableByteChannel sbc      = Files.newByteChannel(path, EnumSet.of(READ));
		Long                offset   = (long) (RECORD_SIZE_IN_BYTES * key);
		
		sbc.position(offset);
		sbc.read(buff);
		buff.flip();
		
		String line = Charset.forName(encoding).decode(buff).toString();
		this.setKey(Integer.parseInt(line.split(SEPARATOR)[KEY_INDEX]));
		this.setValue(new BigInteger(line.split(SEPARATOR)[DATA_INDEX].trim()));
		
		buff.rewind();
		buff.clear();

	}

}
