package unsa.bd.model;

public class ClasificacionProducto {
    private String claProCod;
    private String claProNom;
    private String claProEstReg;

    public ClasificacionProducto() {
    }

    public ClasificacionProducto(String claProCod, String claProNom, String claProEstReg) {
        this.claProCod = claProCod;
        this.claProNom = claProNom;
        this.claProEstReg = claProEstReg;
    }

    public String getClaProCod() {
        return claProCod;
    }

    public void setClaProCod(String claProCod) {
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
