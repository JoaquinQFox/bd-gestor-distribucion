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

public class ReporteFabricante extends BaseReporte {

    public ReporteFabricante(String title) {
        super(title);
    }

    @Override
    protected void loadTableData() {
        try {
            List<ReportObj> rs = getDatabaseData();
            for (ReportObj r : rs) {
                tableModel.addRow(new Object[]{
                        r.fabCod,
                        r.fabNom,
                        r.fabEstReg
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private record ReportObj(String fabCod, String fabNom, String fabEstReg) { }

    private List<ReportObj> getDatabaseData() throws SQLException {
        List<ReportObj> list = new LinkedList<>();
        String sql = """
                SELECT FabCod, FabNom, FabEstReg FROM FABRICANTE""";

        try (Connection connection = ConexionDB.getConnection()) {
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReportObj obj = new ReportObj(
                        rs.getString("FabCod"),
                        rs.getString("FabNom"),
                        rs.getString("FabEstReg")
                );
                list.add(obj);
            }
        }
        return list;
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Codigo", "Nombre", "Estado de Registro"};
    }

    @Override
    protected String getHeaderTitle() {
        return "REPORTE - FABRICANTE";
    }
}
