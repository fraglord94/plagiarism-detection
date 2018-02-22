import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

/**
 * Performs validation of the arguments passed from the command line and creates the {@link InputModel}.
 *
 * @author Aronee Dasgupta
 */
class InputValidationService {

    private static final Logger logger = Logger.getLogger(InputValidationService.class.getName());

    private final int MAX_ARGS = 4;
    private final int MIN_ARGS = 3;
    private final int DEFAULT_TUPLE_SIZE = 3;

    /**
     * Validates the user arguments and constructs and returns the input model.
     *
     * @param args The input arguments fed from the command line.
     * @return An instance of {@link InputModel}.
     */
    InputModel validateInputArguments(final String... args) {
        if (!(!(args.length > MAX_ARGS) && !(args.length < MIN_ARGS))) {
            System.err.println("Usage java PlagiarismDetectionApplication <synonym_filename> <base_filename> <comparision_filename> <tuple_size>");
            logger.log(SEVERE, "The number of arguments entered is incorrect. ");
            System.exit(-1);
        }
        InputModel inputModel = null;
        if (args.length == MIN_ARGS) {
            logger.log(Level.WARNING, "TupleModel size not specified, setting it to 3");
            inputModel = new InputModel.InputModelBuilder().
                    setSynonymsFile(args[0])
                    .setBaseFile(args[1])
                    .setComparisionFile(args[2])
                    .setTupleSize(DEFAULT_TUPLE_SIZE)
                    .createInputModel();
        }
        if (args.length == MAX_ARGS) {
            try {
                logger.log(Level.INFO, "Setting the tuple size to the user specified tuple size. ");
                inputModel = new InputModel.InputModelBuilder().
                        setSynonymsFile(args[0])
                        .setBaseFile(args[1])
                        .setComparisionFile(args[2])
                        .setTupleSize(Integer.valueOf(args[3]))
                        .createInputModel();
            } catch (NumberFormatException e) {
                logger.log(SEVERE, "The tuple size entered is invalid. ");
                System.exit(-1);
            }
        }

        if (inputModel != null) {
            return inputModel;
        }
        logger.log(SEVERE, "Critical error when creating input model. ");
        System.exit(-1);
        return inputModel;
    }

}
