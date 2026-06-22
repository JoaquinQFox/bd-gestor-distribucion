package unsa.bd.modules.shared.departamento;

public class Departamento {
    private String depCod;
    private String depNom;
    private String depEstReg;

    public Departamento() {
    }

    public Departamento(String depCod, String depNom, String depEstReg) {
        this.depCod = depCod;
        this.depNom = depNom;
        this.depEstReg = depEstReg;
    }

    public String getDepCod() {
        return depCod;
    }

    public void setDepCod(String depCod) {
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

    public String cog() {
        return null;
    }
}