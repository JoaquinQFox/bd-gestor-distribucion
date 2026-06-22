package unsa.bd.modules.cliente.persona;

public class Persona {
    private String perCliCod;
    private String perNom;
    private String perTipDocCod;
    private String perNumDoc;
    private String perEstReg;

    public Persona() {}

    public Persona(String perCliCod, String perNom, String perTipDocCod, String perNumDoc, String perEstReg) {
        this.perCliCod = perCliCod;
        this.perNom = perNom;
        this.perTipDocCod = perTipDocCod;
        this.perNumDoc = perNumDoc;
        this.perEstReg = perEstReg;
    }

    public String getPerCliCod() {
        return perCliCod;
    }

    public void setPerCliCod(String perCliCod) {
        this.perCliCod = perCliCod;
    }

    public String getPerNom() {
        return perNom;
    }

    public void setPerNom(String perNom) {
        this.perNom = perNom;
    }

    public String getPerTipDocCod() {
        return perTipDocCod;
    }

    public void setPerTipDocCod(String perTipDocCod) {
        this.perTipDocCod = perTipDocCod;
    }

    public String getPerNumDoc() {
        return perNumDoc;
    }

    public void setPerNumDoc(String perNumDoc) {
        this.perNumDoc = perNumDoc;
    }

    public String getPerEstReg() {
        return perEstReg;
    }

    public void setPerEstReg(String perEstReg) {
        this.perEstReg = perEstReg;
    }
}
