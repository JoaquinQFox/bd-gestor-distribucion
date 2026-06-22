package unsa.bd.modules.inventario.almacen;

public class Almacen {
    private String almCod;
    private String almNom;
    private String almDir;
    private String almCiuCod;
    private String almRegCod;
    private String almEstReg;

    public Almacen() {}

    public Almacen(String almCod, String almNom, String almDir, String almCiuCod, String almRegCod, String almEstReg) {
        this.almCod = almCod;
        this.almNom = almNom;
        this.almDir = almDir;
        this.almCiuCod = almCiuCod;
        this.almRegCod = almRegCod;
        this.almEstReg = almEstReg;
    }

    public String getAlmCod() {
        return almCod;
    }

    public void setAlmCod(String almCod) {
        this.almCod = almCod;
    }

    public String getAlmNom() {
        return almNom;
    }

    public void setAlmNom(String almNom) {
        this.almNom = almNom;
    }

    public String getAlmDir() {
        return almDir;
    }

    public void setAlmDir(String almDir) {
        this.almDir = almDir;
    }

    public String getAlmCiuCod() {
        return almCiuCod;
    }

    public void setAlmCiuCod(String almCiuCod) {
        this.almCiuCod = almCiuCod;
    }

    public String getAlmRegCod() {
        return almRegCod;
    }

    public void setAlmRegCod(String almRegCod) {
        this.almRegCod = almRegCod;
    }

    public String getAlmEstReg() {
        return almEstReg;
    }

    public void setAlmEstReg(String almEstReg) {
        this.almEstReg = almEstReg;
    }
}
