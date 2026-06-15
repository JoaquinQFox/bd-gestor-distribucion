package unsa.bd.model;

public class ClasificacionProducto {
    private int claProCod;
    private String claProNom;
    private String claProEstReg;

    public ClasificacionProducto() {
    }

    public ClasificacionProducto(int claProCod, String claProNom, String claProEstReg) {
        this.claProNom = claProNom;
        this.claProCod = claProCod;
        this.claProEstReg = claProEstReg;
    }

    public int getClaProCod() {
        return claProCod;
    }

    public void setClaProCod(int claProCod) {
        this.claProCod = claProCod;
    }

    public String getClaProNom() {
        return claProNom;
    }

    public void setClaProNom(String claProNom) {
        this.claProNom = claProNom;
    }

    public String getClaProEstReg() {
        return claProEstReg;
    }

    public void setClaProEstReg(String claProEstReg) {
        this.claProEstReg = claProEstReg;
    }
}
