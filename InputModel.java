/**
 * Model for the input parameters.
 *
 * @author Aronee Dasgupta
 */
class InputModel {

    private String baseFile;
    private String comparisionFile;
    private String synonymsFile;
    private int tupleSize;

    private InputModel(final String baseFile, final String comparisionFile,
                       final String synonymsFile, final int tupleSize) {
        this.baseFile = baseFile;
        this.comparisionFile = comparisionFile;
        this.synonymsFile = synonymsFile;
        this.tupleSize = tupleSize;
    }


    String getBaseFile() {
        return baseFile;
    }

    String getComparisionFile() {
        return comparisionFile;
    }

    String getSynonymsFile() {
        return synonymsFile;
    }

    int getTupleSize() {
        return tupleSize;
    }

    /**
     * Builder for the {@link InputModel} class.
     */
    static class InputModelBuilder {
        private String baseFile;
        private String comparisionFile;
        private String synonymsFile;
        private int tupleSize;

        InputModelBuilder setBaseFile(String baseFile) {
            this.baseFile = baseFile;
            return this;
        }

        InputModelBuilder setComparisionFile(String comparisionFile) {
            this.comparisionFile = comparisionFile;
            return this;
        }

        InputModelBuilder setSynonymsFile(String synonymsFile) {
            this.synonymsFile = synonymsFile;
            return this;
        }

        InputModelBuilder setTupleSize(int tupleSize) {
            this.tupleSize = tupleSize;
            return this;
        }

        InputModel createInputModel() {
            return new InputModel(baseFile, comparisionFile, synonymsFile, tupleSize);
        }
    }
}