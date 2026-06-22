package unsa.bd.modules.ventas.factura.facturadet;

import java.math.BigDecimal;

public class FacturaDet {
    private String facDetCabNum;
    private String facDetProCod;
    private int facDetCan;
    private BigDecimal facDetPre;
    private BigDecimal facDetTot;
    private String facDetEstReg;

    public FacturaDet() {}

    public FacturaDet(String facDetCabNum, String facDetProCod, int facDetCan, BigDecimal facDetPre, BigDecimal facDetTot, String facDetEstReg) {
        this.facDetCabNum = facDetCabNum;
        this.facDetProCod = facDetProCod;
        this.facDetCan = facDetCan;
        this.facDetPre = facDetPre;
        this.facDetTot = facDetTot;
        this.facDetEstReg = facDetEstReg;
    }

    public String getFacDetCabNum() {
        return facDetCabNum;
    }

    public void setFacDetCabNum(String facDetCabNum) {
        this.facDetCabNum = facDetCabNum;
    }

    public String getFacDetProCod() {
        return facDetProCod;
    }

    public void setFacDetProCod(String facDetProCod) {
        this.facDetProCod = facDetProCod;
    }

    public int getFacDetCan() {
        return facDetCan;
    }

    public void setFacDetCan(int facDetCan) {
        this.facDetCan = facDetCan;
    }

    public BigDecimal getFacDetPre() {
        return facDetPre;
    }

    public void setFacDetPre(BigDecimal facDetPre) {
        this.facDetPre = facDetPre;
    }

    public BigDecimal getFacDetTot() {
        return facDetTot;
    }

    public void setFacDetTot(BigDecimal facDetTot) {
        this.facDetTot = facDetTot;
    }

    public String getFacDetEstReg() {
        return facDetEstReg;
    }

    public void setFacDetEstReg(String facDetEstReg) {
        this.facDetEstReg = facDetEstReg;
    }
}
