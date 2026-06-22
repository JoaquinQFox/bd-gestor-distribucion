package unsa.bd.modules.personal.repventa;

import java.math.BigDecimal;

public class RepVenta {
    private String repVenCod;
    private String repVenNom;
    private int repVenEda;
    private String repVenOfiCod;
    private String repVenCarCod;
    private BigDecimal repVenObjVen;
    private String repVenEstReg;

    public RepVenta() {}

    public RepVenta(String repVenCod, String repVenNom, int repVenEda, String repVenOfiCod, String repVenCarCod, BigDecimal repVenObjVen, String repVenEstReg) {
        this.repVenCod = repVenCod;
        this.repVenNom = repVenNom;
        this.repVenEda = repVenEda;
        this.repVenOfiCod = repVenOfiCod;
        this.repVenCarCod = repVenCarCod;
        this.repVenObjVen = repVenObjVen;
        this.repVenEstReg = repVenEstReg;
    }

    public String getRepVenCod() {
        return repVenCod;
    }

    public void setRepVenCod(String repVenCod) {
        this.repVenCod = repVenCod;
    }

    public String getRepVenNom() {
        return repVenNom;
    }

    public void setRepVenNom(String repVenNom) {
        this.repVenNom = repVenNom;
    }

    public int getRepVenEda() {
        return repVenEda;
    }

    public void setRepVenEda(int repVenEda) {
        this.repVenEda = repVenEda;
    }

    public String getRepVenOfiCod() {
        return repVenOfiCod;
    }

    public void setRepVenOfiCod(String repVenOfiCod) {
        this.repVenOfiCod = repVenOfiCod;
    }

    public String getRepVenCarCod() {
        return repVenCarCod;
    }

    public void setRepVenCarCod(String repVenCarCod) {
        this.repVenCarCod = repVenCarCod;
    }

    public BigDecimal getRepVenObjVen() {
        return repVenObjVen;
    }

    public void setRepVenObjVen(BigDecimal repVenObjVen) {
        this.repVenObjVen = repVenObjVen;
    }

    public String getRepVenEstReg() {
        return repVenEstReg;
    }

    public void setRepVenEstReg(String repVenEstReg) {
        this.repVenEstReg = repVenEstReg;
    }
}
