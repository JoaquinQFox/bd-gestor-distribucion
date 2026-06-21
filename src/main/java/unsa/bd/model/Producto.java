package unsa.bd.model;

import java.math.BigDecimal;

public class Producto {

    private String proCod;
    private String proFabCod;
    private String proDes;
    private BigDecimal proPre;
    private String proClaCod;
    private String proUniMed;
    private String proEstReg;

    public Producto() {}

    public Producto(String proCod, String proFabCod, String proDes, BigDecimal proPre, String proClaCod, String proUniMed, String proEstReg) {
        this.proCod = proCod;
        this.proFabCod = proFabCod;
        this.proDes = proDes;
        this.proPre = proPre;
        this.proClaCod = proClaCod;
        this.proUniMed = proUniMed;
        this.proEstReg = proEstReg;
    }

    public String getProCod() {
        return proCod;
    }

    public void setProCod(String proCod) {
        this.proCod = proCod;
    }

    public String getProFabCod() {
        return proFabCod;
    }

    public void setProFabCod(String proFabCod) {
        this.proFabCod = proFabCod;
    }

    public String getProDes() {
        return proDes;
    }

    public void setProDes(String proDes) {
        this.proDes = proDes;
    }

    public BigDecimal getProPre() {
        return proPre;
    }

    public void setProPre(BigDecimal proPre) {
        this.proPre = proPre;
    }

    public String getProClaCod() {
        return proClaCod;
    }

    public void setProClaCod(String proClaCod) {
        this.proClaCod = proClaCod;
    }

    public String getProUniMed() {
        return proUniMed;
    }

    public void setProUniMed(String proUniMed) {
        this.proUniMed = proUniMed;
    }

    public String getProEstReg() {
        return proEstReg;
    }

    public void setProEstReg(String proEstReg) {
        this.proEstReg = proEstReg;
    }
}
