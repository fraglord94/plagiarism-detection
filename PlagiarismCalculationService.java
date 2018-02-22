import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * PlagiarismCalculationService class calculates the percentage of plagiarism between the two files.
 *
 * @author Aronee Dasgupta
 */
class PlagiarismCalculationService {
    private static final Logger logger = Logger.getLogger(PlagiarismCalculationService.class.getName());

    /**
     * Function to check if the tuples match or not.
     *
     * @param synonymsMap      Map containing a word as key and it's synonyms in a set.
     * @param baseTupleModel        A single tuple of the base file.
     * @param comparisionTupleModel A single tuple of the comparision file.
     * @return True if the tuples match.
     */
    private static boolean tupleMatcher(Map<String, Set<String>> synonymsMap, TupleModel baseTupleModel, TupleModel comparisionTupleModel) {
        List<String> baseFileTupleList = baseTupleModel.getWordList();
        List<String> comparisionFileTupleList = comparisionTupleModel.getWordList();
        if (baseFileTupleList.size() != comparisionFileTupleList.size())
            return false;
        int tupleSize = baseTupleModel.getTupleSize();
        for (int i = 0; i < tupleSize; i++) {
            String baseFileWord = baseFileTupleList.get(i);
            String comparisionFileWord = comparisionFileTupleList.get(i);
            if (!baseFileWord.equals(comparisionFileWord)) {
                if (!synonymsMap.containsKey(baseFileWord))
                    return false;
                Set<String> synonymsSet = synonymsMap.get(baseFileWord);
                if (!synonymsSet.contains(comparisionFileWord))
                    return false;
            }
        }
        return true;
    }

    /**
     * Calculates and returns the percentage of plagiarism between a list of base and comparision files tuples.
     *
     * @param synonymsMap              Map containing a word as key and it's synonyms in a set.
     * @param baseFileTupleModelList        A list of tuples of the base file.
     * @param comparisionFileTupleModelList A list of tuples of the comparision file.
     * @return The percentage plagiarism between the two files.
     */
    double calculatePlagiarism(Map<String, Set<String>> synonymsMap, List<TupleModel> baseFileTupleModelList, List<TupleModel> comparisionFileTupleModelList) {
        logger.info("Calculating the plagiarism of the text files. ");
        double plagiarismCount = 0;
        if (baseFileTupleModelList.size() == 0) {
            return plagiarismCount;
        }
        plagiarismCount = baseFileTupleModelList.stream().mapToLong(baseTupleModel -> comparisionFileTupleModelList.stream().filter(comparisionTupleModel ->
                tupleMatcher(synonymsMap, baseTupleModel, comparisionTupleModel)).count()).asDoubleStream().sum();
        return plagiarismCount / baseFileTupleModelList.size();
    }
}
