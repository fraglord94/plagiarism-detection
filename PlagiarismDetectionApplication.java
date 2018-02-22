import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PlagiarismDetectionApplication class calls services which validate the user input, generate tuple and synonym map and
 * calculate the plagiarism ratio.
 *
 * @author Aronee Dasgupta
 */
public class PlagiarismDetectionApplication {
    private static final Logger logger = Logger.getLogger(PlagiarismDetectionApplication.class.getName());

    /**
     * Driver method of the application.
     *
     * @param args Contains the synonyms, base and comparision file and the optional tuple size.
     * @throws IOException
     */
    public static void main(final String... args) throws IOException {

        logger.setLevel(Level.FINER);

        InputValidationService inputValidationService = new InputValidationService();
        logger.entering(InputValidationService.class.getName(), "validateInputArguments");
        InputModel inputModel = inputValidationService.validateInputArguments(args);

        GenerateService generateService = new GenerateService();
        logger.entering(GenerateService.class.getName(), "generateSynonymsMap", new Object[]{""});
        Map<String, Set<String>> synonymsMap = generateService.generateSynonymsMap(inputModel.getSynonymsFile());


        logger.entering(GenerateService.class.getName(), "generateTupleList", new Object[]{""});
        List<TupleModel> baseFileTupleModelList = generateService.generateTupleList(inputModel.getBaseFile(),
                inputModel.getTupleSize());

        logger.entering(GenerateService.class.getName(), "generateTupleList", new Object[]{""});
        List<TupleModel> comparisionFileTupleModelList = generateService.generateTupleList(inputModel.getComparisionFile(),
                inputModel.getTupleSize());

        PlagiarismCalculationService plagiarismCalculationService = new PlagiarismCalculationService();
        logger.entering(PlagiarismCalculationService.class.getName(), "calculatePlagiarism",
                new Object[]{new HashMap<>(), new ArrayList<>(), new ArrayList<>()
                });
        double plagiarismRatio = plagiarismCalculationService.calculatePlagiarism(synonymsMap,
                baseFileTupleModelList, comparisionFileTupleModelList);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        plagiarismRatio = plagiarismRatio * 100;
        System.out.println("The plagiarism detected is " + decimalFormat.format(plagiarismRatio) + "%");
    }
}
