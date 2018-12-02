/**
 * 
 */
package arquivo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author César Augusto Moro Fürst
 *
 */
public class LeituraArquivo {

	public StringBuilder leArquivo(String nome) throws IOException{
		
		Path path=Paths.get(nome);
		List<String> linhasArquivo=Files.readAllLines(path);
		StringBuilder retorno=new StringBuilder();
		for (int i = 0; i < linhasArquivo.size(); i++) {
			retorno.append(linhasArquivo.get(i));
			retorno.append("\n");
		}
		return retorno;
		
	}
}
