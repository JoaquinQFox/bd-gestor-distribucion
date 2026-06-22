package unsa.bd.modules.cliente.empresa;

public class Empresa {
    private String empCliCod;
    private String empNom;
    private String empRUC;
    private String empRazSoc;
    private String empEstReg;

    public Empresa() {}

    public Empresa(String empCliCod, String empNom, String empRUC, String empRazSoc, String empEstReg) {
        this.empCliCod = empCliCod;
        this.empNom = empNom;
        this.empRUC = empRUC;
        this.empRazSoc = empRazSoc;
        this.empEstReg = empEstReg;
    }

    public String getEmpCliCod() {
        return empCliCod;
    }

    public void setEmpCliCod(String empCliCod) {
        this.empCliCod = empCliCod;
    }

    public String getEmpNom() {
        return empNom;
    }

    public void setEmpNom(String empNom) {
        this.empNom = empNom;
    }

    public String getEmpRUC() {
        return empRUC;
    }

    public void setEmpRUC(String empRUC) {
        this.empRUC = empRUC;
    }

    public String getEmpRazSoc() {
        return empRazSoc;
    }

    public void setEmpRazSoc(String empRazSoc) {
        this.empRazSoc = empRazSoc;
    }

    public String getEmpEstReg() {
        return empEstReg;
    }

    public void setEmpEstReg(String empEstReg) {
        this.empEstReg = empEstReg;
    }
}
