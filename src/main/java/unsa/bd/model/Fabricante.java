package unsa.bd.model;

public class Fabricante {

    private String fabCod;
    private String fabNom;
    private String fabEstReg;

    public Fabricante() {}

    public Fabricante(String fabCod, String fabNom, String fabEstReg) {
        this.fabCod = fabCod;
        this.fabNom = fabNom;
        this.fabEstReg = fabEstReg;
    }

    public String getFabCod() {
        return fabCod;
    }

    public void setFabCod(String fabCod) {
        this.fabCod = fabCod;
    }

    public String getFabNom() {
        return fabNom;
    }

    public void setFabNom(String fabNom) {
        this.fabNom = fabNom;
    }

    public String getFabEstReg() {
        return fabEstReg;
    }

    public void setFabEstReg(String fabEstReg) {
        this.fabEstReg = fabEstReg;
    }
}
