package unsa.bd.model;

public class Ciudad {
    private int ciuCod;
    private String ciuNom;
    private int ciuRegCod;
    private String ciuEstReg;

    public Ciudad() {
    }

    public Ciudad(int ciuCod, String ciuNom, int ciuRegCod, String ciuEstReg) {
        this.ciuCod = ciuCod;
        this.ciuNom = ciuNom;
        this.ciuRegCod = ciuRegCod;
        this.ciuEstReg = ciuEstReg;
    }

    public int getCiuCod() {
        return ciuCod;
    }

    public void setCiuCod(int ciuCod) {
        this.ciuCod = ciuCod;
    }

    public String getCiuNom() {
        return ciuNom;
    }

    public void setCiuNom(String ciuNom) {
        this.ciuNom = ciuNom;
    }

    public int getCiuRegCod() {
        return ciuRegCod;
    }

    public void setCiuRegCod(int ciuRegCod) {
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