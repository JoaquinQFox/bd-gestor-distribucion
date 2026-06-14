package unsa.bd.model;

public class Region {
    private int regCod;
    private String regNom;
    private String regEstReg;

    public Region() {
    }

    public Region(int regCod, String regNom, String regEstReg) {
        this.regCod = regCod;
        this.regNom = regNom;
        this.regEstReg = regEstReg;
    }

    public int getRegCod() {
        return regCod;
    }

    public void setRegCod(int regCod) {
        this.regCod = regCod;
    }

    public String getRegNom() {
        return regNom;
    }

    public void setRegNom(String regNom) {
        this.regNom = regNom;
    }

    public String getRegEstReg() {
        return regEstReg;
    }

    public void setRegEstReg(String regEstReg) {
        this.regEstReg = regEstReg;
    }

    @Override
    public String toString() {
        return String.format("Region: codigo=%d, nombre=%s, estado=%s", getRegCod(), getRegNom(), getRegEstReg());
    }
}