package unsa.bd.model;

public class ClasificacionProducto {
    private int claProCod;
    private String claProNom;
    private String claProEstReg;

    public ClasificacionProducto() {
    }

    public ClasificacionProducto(String claProEstReg, String claProNom, int claProCod) {
        this.claProEstReg = claProEstReg;
        this.claProNom = claProNom;
        this.claProCod = claProCod;
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
