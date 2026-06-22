package unsa.bd.modules.cliente.cliente;

public class Cliente {
    private String cliCod;
    private String cliDir;
    private String cliCor;
    private String cliRepCod;
    private String cliDep;
    private String cliTipCliCod;
    private String cliEscCreCod;
    private String cliEstReg;

    public Cliente() {}

    public Cliente(String cliCod, String cliDir, String cliCor, String cliRepCod, String cliDep, String cliTipCliCod, String cliEscCreCod, String cliEstReg) {
        this.cliCod = cliCod;
        this.cliDir = cliDir;
        this.cliCor = cliCor;
        this.cliRepCod = cliRepCod;
        this.cliDep = cliDep;
        this.cliTipCliCod = cliTipCliCod;
        this.cliEscCreCod = cliEscCreCod;
        this.cliEstReg = cliEstReg;
    }

    public String getCliCod() {
        return cliCod;
    }

    public void setCliCod(String cliCod) {
        this.cliCod = cliCod;
    }

    public String getCliDir() {
        return cliDir;
    }

    public void setCliDir(String cliDir) {
        this.cliDir = cliDir;
    }

    public String getCliCor() {
        return cliCor;
    }

    public void setCliCor(String cliCor) {
        this.cliCor = cliCor;
    }

    public String getCliRepCod() {
        return cliRepCod;
    }

    public void setCliRepCod(String cliRepCod) {
        this.cliRepCod = cliRepCod;
    }

    public String getCliDep() {
        return cliDep;
    }

    public void setCliDep(String cliDep) {
        this.cliDep = cliDep;
    }

    public String getCliTipCliCod() {
        return cliTipCliCod;
    }

    public void setCliTipCliCod(String cliTipCliCod) {
        this.cliTipCliCod = cliTipCliCod;
    }

    public String getCliEscCreCod() {
        return cliEscCreCod;
    }

    public void setCliEscCreCod(String cliEscCreCod) {
        this.cliEscCreCod = cliEscCreCod;
    }

    public String getCliEstReg() {
        return cliEstReg;
    }

    public void setCliEstReg(String cliEstReg) {
        this.cliEstReg = cliEstReg;
    }
}
