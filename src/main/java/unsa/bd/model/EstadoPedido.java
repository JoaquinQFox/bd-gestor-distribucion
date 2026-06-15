package unsa.bd.model;

public class EstadoPedido {
    private int estPedCod;
    private String estPedNom;
    private String estPedEstReg;

    public EstadoPedido() {}

    public EstadoPedido(int estPedCod, String estPedNom, String estPedEstReg) {
        this.estPedCod = estPedCod;
        this.estPedNom = estPedNom;
        this.estPedEstReg = estPedEstReg;
    }

    public int getEstPedCod() {
        return estPedCod;
    }

    public void setEstPedCod(int estPedCod) {
        this.estPedCod = estPedCod;
    }

    public String getEstPedNom() {
        return estPedNom;
    }

    public void setEstPedNom(String estPedNom) {
        this.estPedNom = estPedNom;
    }

    public String getEstPedEstReg() {
        return estPedEstReg;
    }

    public void setEstPedEstReg(String estPedEstReg) {
        this.estPedEstReg = estPedEstReg;
    }
}
