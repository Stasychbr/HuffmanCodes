public enum Params {
    inputFile("INPUT_FILE"),
    outputFile("OUTPUT_FILE"),
    bufSize("BUF_SIZE");
    private String value;
    private Params(String val) {
        value = val;
    }
    public String getValue() {
        return value;
    }
}
