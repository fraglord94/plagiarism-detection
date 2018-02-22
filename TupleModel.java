import java.util.ArrayList;
import java.util.List;

/**
 * Class to model a tuple in the text file.
 *
 * @author Aronee Dasgupta
 */
class TupleModel {
    private List<String> wordList;
    private int tupleSize;

    TupleModel(final int tupleSize) {
        this.tupleSize = tupleSize;
        this.wordList = new ArrayList<>(this.tupleSize);
    }

    int getTupleSize() {
        return tupleSize;
    }

    /**
     * Adds a single word to the wordList for the tuple.
     *
     * @param word Word to be added.
     */
    void addWord(final String word) {
        wordList.add(word);
    }

    List<String> getWordList() {
        return wordList;
    }

    @Override
    public String toString() {
        return wordList.toString();
    }
}
