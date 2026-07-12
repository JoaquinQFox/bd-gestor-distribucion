package unsa.bd.consultas;

import unsa.bd.commons.utility.BaseConsulta;
import unsa.bd.config.ConexionDB;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConsultaUniMed extends BaseConsulta {

    public ConsultaUniMed(String title) {
        super(title);
    }


    @Override
    protected void loadTableData() {
        try {
            List<ConsultaObj> rs = getDatabaseData();
            for (ConsultaObj o : rs) {
                tableModel.addRow(new Object[]{
                        o.cod,
                        o.nom,
                        o.abr,
                        o.est
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private record ConsultaObj(String cod, String nom, String abr, String est) { }

    private List<ConsultaObj> getDatabaseData() throws SQLException {
        List<ConsultaObj> list = new LinkedList<>();
        String sql = """
                SELECT UniMedCod, UniMedNom, UniMedAbr,UniMedEstReg FROM UNIDAD_MEDIDA
                ORDER BY UniMedNom""";

        try (Connection connection = ConexionDB.getConnection()) {
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ConsultaObj obj = new ConsultaObj(
                        rs.getString("UniMedCod"),
                        rs.getString("UniMedNom"),
                        rs.getString("UniMedAbr"),
                        rs.getString("UniMedEstReg")
                );
                list.add(obj);
            }
        }
        return list;
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Codigo", "Nombre", "Abreviatura", "Estado de Registro"};
    }

    @Override
    protected String getHeaderTitle() {
        return "CONSULTA - UNIDAD MEDIDA";
    }
}
