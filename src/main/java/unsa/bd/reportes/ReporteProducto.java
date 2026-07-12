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

public class ReporteProducto extends BaseReporte {

    public ReporteProducto(String title) {
        super(title);
    }

    @Override
    protected void loadTableData() {
        try {
            List<ReportObj> rs = getDatabaseData();
            for (ReportObj r : rs) {
                tableModel.addRow(new Object[]{
                        r.proCod,
                        r.fabNom,
                        r.proDes,
                        r.proPre,
                        r.claProNom,
                        r.uniMedNom,
                        r.proEstReg
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private record ReportObj(String proCod, String fabNom, String proDes, BigDecimal proPre,
                             String claProNom, String uniMedNom, String proEstReg) { }

    private List<ReportObj> getDatabaseData() throws SQLException {
        List<ReportObj> list = new LinkedList<>();
        String sql = """
                     SELECT
                         pro.ProCod,
                         fab.FabNom,
                         pro.ProDes,
                         pro.ProPre,
                         cla.ClaProNom,
                         uni.UniMedNom,
                         pro.ProEstReg
                     FROM PRODUCTO pro
                     JOIN FABRICANTE fab
                         ON fab.FabCod = pro.ProFabCod
                     JOIN CLASIFICACION_PRODUCTO cla
                         ON cla.ClaProCod = pro.ProClaCod
                     JOIN UNIDAD_MEDIDA uni
                         ON uni.UniMedCod = pro.ProUniMed""";


        try (Connection connection = ConexionDB.getConnection()) {
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReportObj obj = new ReportObj(
                        rs.getString("ProCod"),
                        rs.getString("FabNom"),
                        rs.getString("ProDes"),
                        rs.getBigDecimal("ProPre"),
                        rs.getString("ClaProNom"),
                        rs.getString("UniMedNom"),
                        rs.getString("ProEstReg")
                );
                list.add(obj);
            }
        }
        return list;
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Fabricante", "Descripcion", "Precio", "Clasificación", "Unidad de Medida", "Estado de Registro"};
    }

    @Override
    protected String getHeaderTitle() {
        return "REPORTE - PRODUCTO";
    }
}
