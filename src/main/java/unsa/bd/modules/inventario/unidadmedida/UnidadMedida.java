package unsa.bd.modules.inventario.unidadmedida;

public class UnidadMedida {
    private String uniMedCod;
    private String uniMedNom;
    private String uniMedAbr;
    private String uniEstReg;

    public UnidadMedida() {
    }

    public UnidadMedida(String uniMedCod, String uniMedNom, String uniMedAbr, String uniEstReg) {
        this.uniMedCod = uniMedCod;
        this.uniMedNom = uniMedNom;
        this.uniMedAbr = uniMedAbr;
        this.uniEstReg = uniEstReg;
    }

    public String getUniMedCod() {
        return uniMedCod;
    }

    public void setUniMedCod(String uniMedCod) {
        this.uniMedCod = uniMedCod;
    }

    public String getUniMedNom() {
        return uniMedNom;
    }

    public void setUniMedNom(String uniMedNom) {
        this.uniMedNom = uniMedNom;
    }

    public String getUniMedAbr() {
        return uniMedAbr;
    }

    public void setUniMedAbr(String uniMedAbr) {
        this.uniMedAbr = uniMedAbr;
    }

    public String getUniEstReg() {
        return uniEstReg;
    }

    public void setUniEstReg(String uniEstReg) {
        this.uniEstReg = uniEstReg;
    }
}
