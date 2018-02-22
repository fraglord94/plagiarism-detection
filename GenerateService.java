import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.regex.Pattern.compile;

/**
 * This class generates the synonym map and a list of tuples of a specific size from a text file.
 *
 * @author Aronee Dasgupta
 */
class GenerateService {

    private static final Logger logger = Logger.getLogger(GenerateService.class.getName());

    /**
     * Takes a single line and the tuple size and returns a list of tuples in that line.
     *
     * @param line      The line in the text file which needs to be convert to  a list of TupleModel.
     * @param tupleSize The predefined tuple size.
     * @return An instance of {@link List< TupleModel >}.
     */
    private static List<TupleModel> convertLineToTuple(final String line, final int tupleSize) {
        List<TupleModel> tupleModelList = new ArrayList<>();
        String[] words = extractWordsFromLine(line);
        if (words.length < tupleSize)
            return tupleModelList;
        IntStream.rangeClosed(0, words.length - tupleSize).forEachOrdered(i -> {
            TupleModel tupleModel = new TupleModel(tupleSize);
            IntStream.range(i, i + tupleSize).mapToObj(j -> words[j]).forEachOrdered(tupleModel::addWord);
            tupleModelList.add(tupleModel);
        });
        return tupleModelList;
    }

    /**
     * Extracts words from a single line.
     *
     * @param line A single line of the text file.
     * @return An instance of {@link String[]}.
     */
    private static String[] extractWordsFromLine(final String line) {
        List<String> wordList = new ArrayList<>();
        Pattern pattern = compile("[\\w']+");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = line.substring(matcher.start(), matcher.end());
            wordList.add(word);
        }
        String[] words = new String[wordList.size()];
        wordList.toArray(words);
        return words;
    }

    /**
     * Generates a map with a word and a set of synonyms as the key value pair.
     *
     * @param synonymsFileName The name of the synonym file.
     * @return An instance of {@link Map<String, Set<String>>}.
     * @throws IOException
     */
    Map<String, Set<String>> generateSynonymsMap(final String synonymsFileName) throws IOException {
        Map<String, Set<String>> map = new HashMap<>();
        logger.log(INFO, "Generating the synonym map for the synonyms. ");
        AtomicReference<BufferedReader> bufferedReader = new AtomicReference<>(null);
        try {
            bufferedReader.set(new BufferedReader(new FileReader(synonymsFileName)));
            logger.log(INFO, "Reading the synonyms file from the stream. ");
            bufferedReader.get().lines().map(line -> line.split("\\s+")).forEachOrdered(synonyms -> {
                Set<String> set = new HashSet<>(Arrays.asList(synonyms));
                Arrays.stream(synonyms).filter(word -> !map.containsKey(word)).forEachOrdered(word -> map.put(word, set));
            });
        } catch (FileNotFoundException e) {
            logger.log(SEVERE, "The synonyms filename is invalid. ", e);
            System.exit(-1);
        } finally {
            if (bufferedReader.get() != null) {
                bufferedReader.get().close();
            }
        }
        logger.log(INFO, "Synonyms map generated. ");
        return map;
    }

    /**
     * Generates the list of tuples in a file.
     *
     * @param fileName  The path to the file.
     * @param tupleSize The size of the tuple.
     * @return An instance of {@link List< TupleModel >}.
     * @throws IOException
     */
    List<TupleModel> generateTupleList(final String fileName, final int tupleSize) throws IOException {
        List<TupleModel> tupleModelList = new ArrayList<>();
        logger.log(INFO, "Generating the tuple list for the file. ");
        AtomicReference<BufferedReader> bufferedReader = new AtomicReference<>(null);
        try {
            bufferedReader.set(new BufferedReader(new FileReader(fileName)));
            logger.log(INFO, "Reading the text file from the stream. ");
            bufferedReader.get().lines().map(line -> convertLineToTuple(line, tupleSize)).forEachOrdered(tupleModelList::addAll);
        } catch (FileNotFoundException e) {
            logger.log(SEVERE, "The text filename is invalid. ", e);
            System.exit(-1);
        } finally {
            if (bufferedReader.get() != null) {
                bufferedReader.get().close();
            }
        }
        logger.log(INFO, "The tuple list for the text file generated. ");
        return tupleModelList;
    }
}