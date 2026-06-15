package unsa.bd.model;

public class EstadoFactura {
    private int estFacCod;
    private int estFacNom;
    private String estFacEstReg;

    public EstadoFactura() {
    }

    public EstadoFactura(int estFacCod, int estFacNom, String estFacEstReg) {
        this.estFacCod = estFacCod;
        this.estFacNom = estFacNom;
        this.estFacEstReg = estFacEstReg;
    }

    public int getEstFacCod() {
        return estFacCod;
    }

    public void setEstFacCod(int estFacCod) {
        this.estFacCod = estFacCod;
    }

    public int getEstFacNom() {
        return estFacNom;
    }

    public void setEstFacNom(int estFacNom) {
        this.estFacNom = estFacNom;
    }

    public String getEstFacEstReg() {
        return estFacEstReg;
    }

    public void setEstFacEstReg(String estFacEstReg) {
        this.estFacEstReg = estFacEstReg;
    }
}
