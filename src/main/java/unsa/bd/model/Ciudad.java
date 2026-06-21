package unsa.bd.model;

public class Ciudad {
    private String ciuCod;
    private String ciuNom;
    private String ciuRegCod;
    private String ciuEstReg;

    public Ciudad() {
    }

    public Ciudad(String ciuCod, String ciuNom, String ciuRegCod, String ciuEstReg) {
        this.ciuCod = ciuCod;
        this.ciuNom = ciuNom;
        this.ciuRegCod = ciuRegCod;
        this.ciuEstReg = ciuEstReg;
    }

    public String getCiuCod() {
        return ciuCod;
    }

    public void setCiuCod(String ciuCod) {
        this.ciuCod = ciuCod;
    }

    public String getCiuNom() {
        return ciuNom;
    }

    public void setCiuNom(String ciuNom) {
        this.ciuNom = ciuNom;
    }

    public String getCiuRegCod() {
        return ciuRegCod;
    }

    public void setCiuRegCod(String ciuRegCod) {
        this.ciuRegCod = ciuRegCod;
    }

    public String getCiuEstReg() {
        return ciuEstReg;
    }

    public void setCiuEstReg(String ciuEstReg) {
        this.ciuEstReg = ciuEstReg;
    }

    @Override
    public String toString() {
        return "Ciudad{" +
               "ciuCod=" + ciuCod +
               ", ciuNom='" + ciuNom + '\'' +
               ", ciuRegCod=" + ciuRegCod +
               ", ciuEstReg='" + ciuEstReg + '\'' +
               '}';
    }
}