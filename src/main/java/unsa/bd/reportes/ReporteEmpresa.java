package unsa.bd.reportes;

import unsa.bd.commons.utility.BaseReporte;
import unsa.bd.config.ConexionDB;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ReporteEmpresa extends BaseReporte {

    public ReporteEmpresa(String title) {
        super(title);
    }

    @Override
    protected void loadTableData() {
        try {
            List<ReportObj> rs = getDatabaseData();
            for (ReportObj r : rs) {
                tableModel.addRow(new Object[]{
                        r.cliCod,
                        r.empNom,
                        r.cliDir,
                        r.cliCor,
                        r.empRUC,
                        r.empEstReg
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private record ReportObj(String cliCod, String empNom, String cliDir, String cliCor, String empRUC, String empEstReg) { }

    private List<ReportObj> getDatabaseData() throws SQLException {
        List<ReportObj> list = new LinkedList<>();
        String sql = """
                     SELECT
                         cli.CliCod,
                         emp.EmpNom,
                         cli.CliDir,
                         cli.CliCor,
                         emp.EmpRUC,
                         emp.EmpEstReg
                     FROM EMPRESA emp
                     JOIN CLIENTE cli
                         ON cli.CliCod = emp.EmpCliCod""";

        try (Connection connection = ConexionDB.getConnection()) {
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReportObj obj = new ReportObj(
                        rs.getString("CliCod"),
                        rs.getString("EmpNom"),
                        rs.getString("CliDir"),
                        rs.getString("CliCor"),
                        rs.getString("EmpRUC"),
                        rs.getString("EmpEstReg")
                );
                list.add(obj);
            }
        }
        return list;
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Codigo", "Nombre", "Dirección", "Correo", "RUC", "Estado de Registro"};
    }

    @Override
    protected String getHeaderTitle() {
        return "REPORTE - FABRICANTE";
    }
}
