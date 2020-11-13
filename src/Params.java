public enum Params {
    inputFile("INPUT_FILE"),
    outputFile("OUTPUT_FILE"),
    mode("MODE"),
    delimiter("="),
    bufSize("BUF_SIZE");
    private String value;
    Params(String val) { value = val; }
    public String getValue() {
        return value;
    }
}
