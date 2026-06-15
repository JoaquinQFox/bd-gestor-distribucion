package unsa.bd.model;

import java.math.BigDecimal;

public class EscalaCredito {
    private int escCreCod;
    private String escCreNom;
    private BigDecimal escCreLimCre;
    private String escCreEstReg;

    public EscalaCredito() {}

    public EscalaCredito(int escCreCod, String escCreNom, BigDecimal escCreLimCre, String escCreEstReg) {
        this.escCreCod = escCreCod;
        this.escCreNom = escCreNom;
        this.escCreLimCre = escCreLimCre;
        this.escCreEstReg = escCreEstReg;
    }

    public int getEscCreCod() {
        return escCreCod;
    }

    public void setEscCreCod(int escCreCod) {
        this.escCreCod = escCreCod;
    }

    public String getEscCreNom() {
        return escCreNom;
    }

    public void setEscCreNom(String escCreNom) {
        this.escCreNom = escCreNom;
    }

    public BigDecimal getEscCreLimCre() {
        return escCreLimCre;
    }

    public void setEscCreLimCre(BigDecimal escCreLimCre) {
        this.escCreLimCre = escCreLimCre;
    }

    public String getEscCreEstReg() {
        return escCreEstReg;
    }

    public void setEscCreEstReg(String escCreEstReg) {
        this.escCreEstReg = escCreEstReg;
    }
}
