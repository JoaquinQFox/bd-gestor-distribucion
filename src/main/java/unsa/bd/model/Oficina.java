package unsa.bd.model;

import java.math.BigDecimal;

public class Oficina {
    private String ofiCod;
    private String ofiRegCod;
    private String ofiCiuCod;
    private BigDecimal ofiObjPro;
    private BigDecimal ofiVenRea;
    private String ofiEstReg;

    public Oficina() {}

    public Oficina(String ofiCod, String ofiRegCod, String ofiCiuCod, BigDecimal ofiObjPro, BigDecimal ofiVenRea, String ofiEstReg) {
        this.ofiCod = ofiCod;
        this.ofiRegCod = ofiRegCod;
        this.ofiCiuCod = ofiCiuCod;
        this.ofiObjPro = ofiObjPro;
        this.ofiVenRea = ofiVenRea;
        this.ofiEstReg = ofiEstReg;
    }

    public String getOfiCod() {
        return ofiCod;
    }

    public void setOfiCod(String ofiCod) {
        this.ofiCod = ofiCod;
    }

    public String getOfiRegCod() {
        return ofiRegCod;
    }

    public void setOfiRegCod(String ofiRegCod) {
        this.ofiRegCod = ofiRegCod;
    }

    public String getOfiCiuCod() {
        return ofiCiuCod;
    }

    public void setOfiCiuCod(String ofiCiuCod) {
        this.ofiCiuCod = ofiCiuCod;
    }

    public BigDecimal getOfiObjPro() {
        return ofiObjPro;
    }

    public void setOfiObjPro(BigDecimal ofiObjPro) {
        this.ofiObjPro = ofiObjPro;
    }

    public BigDecimal getOfiVenRea() {
        return ofiVenRea;
    }

    public void setOfiVenRea(BigDecimal ofiVenRea) {
        this.ofiVenRea = ofiVenRea;
    }

    public String getOfiEstReg() {
        return ofiEstReg;
    }

    public void setOfiEstReg(String ofiEstReg) {
        this.ofiEstReg = ofiEstReg;
    }
}
