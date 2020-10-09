public enum Params {
    inputFile("INPUT_FILE"),
    outputFile("OUTPUT_FILE"),
    bufSize("BUF_SIZE"),
    treeFile("TREE_FILE");
    private String value;
    private Params(String val) {
        value = val;
    }
    public String getValue() {
        return value;
    }
}
