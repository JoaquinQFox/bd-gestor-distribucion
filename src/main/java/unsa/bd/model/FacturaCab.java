package unsa.bd.model;

import java.math.BigDecimal;

public class FacturaCab {
    private String facCabNum;
    private String facCabCliCod;
    private String facCabRepCod;
    private String facCabPedNum;
    private String facCabTipCod;
    private int facCabDia;
    private int facCabMes;
    private int facCabYea;
    private String facCabEstCod;
    private BigDecimal facCabSubTot;
    private BigDecimal facCabIGV;
    private BigDecimal facCabImp;
    private int facCabDiaVen;
    private int facCabMesVen;
    private int facCabYeaVen;
    private String facCabEstReg;

    public FacturaCab() {}

    public FacturaCab(String facCabNum, String facCabCliCod, String facCabRepCod, String facCabPedNum, String facCabTipCod, int facCabDia, int facCabMes, int facCabYea, String facCabEstCod, BigDecimal facCabSubTot, BigDecimal facCabIGV, BigDecimal facCabImp, int facCabDiaVen, int facCabMesVen, int facCabYeaVen, String facCabEstReg) {
        this.facCabNum = facCabNum;
        this.facCabCliCod = facCabCliCod;
        this.facCabRepCod = facCabRepCod;
        this.facCabPedNum = facCabPedNum;
        this.facCabTipCod = facCabTipCod;
        this.facCabDia = facCabDia;
        this.facCabMes = facCabMes;
        this.facCabYea = facCabYea;
        this.facCabEstCod = facCabEstCod;
        this.facCabSubTot = facCabSubTot;
        this.facCabIGV = facCabIGV;
        this.facCabImp = facCabImp;
        this.facCabDiaVen = facCabDiaVen;
        this.facCabMesVen = facCabMesVen;
        this.facCabYeaVen = facCabYeaVen;
        this.facCabEstReg = facCabEstReg;
    }

    public String getFacCabNum() {
        return facCabNum;
    }

    public void setFacCabNum(String facCabNum) {
        this.facCabNum = facCabNum;
    }

    public String getFacCabCliCod() {
        return facCabCliCod;
    }

    public void setFacCabCliCod(String facCabCliCod) {
        this.facCabCliCod = facCabCliCod;
    }

    public String getFacCabRepCod() {
        return facCabRepCod;
    }

    public void setFacCabRepCod(String facCabRepCod) {
        this.facCabRepCod = facCabRepCod;
    }

    public String getFacCabPedNum() {
        return facCabPedNum;
    }

    public void setFacCabPedNum(String facCabPedNum) {
        this.facCabPedNum = facCabPedNum;
    }

    public String getFacCabTipCod() {
        return facCabTipCod;
    }

    public void setFacCabTipCod(String facCabTipCod) {
        this.facCabTipCod = facCabTipCod;
    }

    public int getFacCabDia() {
        return facCabDia;
    }

    public void setFacCabDia(int facCabDia) {
        this.facCabDia = facCabDia;
    }

    public int getFacCabMes() {
        return facCabMes;
    }

    public void setFacCabMes(int facCabMes) {
        this.facCabMes = facCabMes;
    }

    public int getFacCabYea() {
        return facCabYea;
    }

    public void setFacCabYea(int facCabYea) {
        this.facCabYea = facCabYea;
    }

    public String getFacCabEstCod() {
        return facCabEstCod;
    }

    public void setFacCabEstCod(String facCabEstCod) {
        this.facCabEstCod = facCabEstCod;
    }

    public BigDecimal getFacCabSubTot() {
        return facCabSubTot;
    }

    public void setFacCabSubTot(BigDecimal facCabSubTot) {
        this.facCabSubTot = facCabSubTot;
    }

    public BigDecimal getFacCabIGV() {
        return facCabIGV;
    }

    public void setFacCabIGV(BigDecimal facCabIGV) {
        this.facCabIGV = facCabIGV;
    }

    public BigDecimal getFacCabImp() {
        return facCabImp;
    }

    public void setFacCabImp(BigDecimal facCabImp) {
        this.facCabImp = facCabImp;
    }

    public int getFacCabDiaVen() {
        return facCabDiaVen;
    }

    public void setFacCabDiaVen(int facCabDiaVen) {
        this.facCabDiaVen = facCabDiaVen;
    }

    public int getFacCabMesVen() {
        return facCabMesVen;
    }

    public void setFacCabMesVen(int facCabMesVen) {
        this.facCabMesVen = facCabMesVen;
    }

    public int getFacCabYeaVen() {
        return facCabYeaVen;
    }

    public void setFacCabYeaVen(int facCabYeaVen) {
        this.facCabYeaVen = facCabYeaVen;
    }

    public String getFacCabEstReg() {
        return facCabEstReg;
    }

    public void setFacCabEstReg(String facCabEstReg) {
        this.facCabEstReg = facCabEstReg;
    }
}
