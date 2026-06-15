package unsa.bd.model;

public class Departamento {
    private int depCod;
    private String depNom;
    private String depEstReg;

    public Departamento() {
    }

    public Departamento(int depCod, String depNom, String depEstReg) {
        this.depCod = depCod;
        this.depNom = depNom;
        this.depEstReg = depEstReg;
    }

    public int getDepCod() {
        return depCod;
    }

    public void setDepCod(int depCod) {
        this.depCod = depCod;
    }

    public String getDepNom() {
        return depNom;
    }

    public void setDepNom(String depNom) {
        this.depNom = depNom;
    }

    public String getDepEstReg() {
        return depEstReg;
    }

    public void setDepEstReg(String depEstReg) {
        this.depEstReg = depEstReg;
    }

    @Override
    public String toString() {
        return "Departamento{" +
               "depCod=" + depCod +
               ", depNom='" + depNom + '\'' +
               ", depEstReg='" + depEstReg + '\'' +
               '}';
    }
}