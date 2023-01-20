class Kluis {
    private final int nummer;
    private String code;

    public Kluis(int nummer, String code) {
        this.nummer = nummer;
        this.code = code;
    }

    public int getNummer() {
        return nummer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
