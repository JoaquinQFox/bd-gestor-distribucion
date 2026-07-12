package unsa.bd.reportes;

import unsa.bd.commons.utility.BaseReporte;
import unsa.bd.config.ConexionDB;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ReporteEscCre extends BaseReporte {

    public ReporteEscCre(String title) {
        super(title);
    }

    @Override
    protected void loadTableData() {
        try {
            List<ReportObj> rs = getDatabaseData();
            for (ReportObj r : rs) {
                tableModel.addRow(new Object[]{
                        r.escCreCod,
                        r.escCreNom,
                        r.escCreLimCre,
                        r.escCreEstReg
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private record ReportObj(String escCreCod, String escCreNom, BigDecimal escCreLimCre, String escCreEstReg) { }

    private List<ReportObj> getDatabaseData() throws SQLException {
        List<ReportObj> list = new LinkedList<>();
        String sql = """
                SELECT EscCreCod, EscCreNom, EscCreLimCre, EscCreEstReg FROM ESCALA_CREDITO""";

        try (Connection connection = ConexionDB.getConnection()) {
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReportObj obj = new ReportObj(
                        rs.getString("EscCreCod"),
                        rs.getString("EscCreNom"),
                        rs.getBigDecimal("EscCreLimCre"),
                        rs.getString("EscCreEstReg")
                );
                list.add(obj);
            }
        }
        return list;
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Codigo", "Nombre", "Escala Credito", "Estado de Registro"};
    }

    @Override
    protected String getHeaderTitle() {
        return "REPORTE - FABRICANTE";
    }
}
