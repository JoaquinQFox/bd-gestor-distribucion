package unsa.bd.modules.ventas.pedido.estadopedido;

public class EstadoPedido {
    private String estPedCod;
    private String estPedNom;
    private String estPedEstReg;

    public EstadoPedido() {}

    public EstadoPedido(String estPedCod, String estPedNom, String estPedEstReg) {
        this.estPedCod = estPedCod;
        this.estPedNom = estPedNom;
        this.estPedEstReg = estPedEstReg;
    }

    public String getEstPedCod() {
        return estPedCod;
    }

    public void setEstPedCod(String estPedCod) {
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
