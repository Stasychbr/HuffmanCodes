public enum Mode {
    Encode("ENCODE"),
    Decode("DECODE");
    private String value;
    Mode(String val) { value = val; }
    public String getValue() {
        return value;
    }
}
