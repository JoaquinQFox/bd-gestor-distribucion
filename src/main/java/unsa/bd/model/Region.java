package unsa.bd.model;

public class Region {
    private String regCod;
    private String regNom;
    private String regEstReg;

    public Region() {
    }

    public Region(String regCod, String regNom, String regEstReg) {
        this.regCod = regCod;
        this.regNom = regNom;
        this.regEstReg = regEstReg;
    }

    public String getRegCod() {
        return regCod;
    }

    public void setRegCod(String regCod) {
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
}