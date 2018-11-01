package execução;

import java.util.List;

/**
 *
 * @author udesc
 */
public class Model {

    private List<String> files;
    private String resultValues;
    private String wordToFind;

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getResultValues() {
        return resultValues;
    }

    public void setResultValues(String resultValues) {
        this.resultValues = resultValues;
    }

    public String getWordToFind() {
        return wordToFind;
    }

    public void setWordToFind(String wordToFind) {
        this.wordToFind = wordToFind;
    }

}
