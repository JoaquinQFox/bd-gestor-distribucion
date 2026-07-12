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

public class ConsultaRegion extends BaseConsulta {

    public ConsultaRegion(String title) {
        super(title);
    }


    @Override
    protected void loadTableData() {
        try {
            List<RegionResultado> rs = getDatabaseData();
            for (RegionResultado r : rs) {
                tableModel.addRow(new Object[]{
                        r.cod,
                        r.nom,
                        r.est
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private record RegionResultado(String cod, String nom, String est) { }

    private List<RegionResultado> getDatabaseData() throws SQLException {
        List<RegionResultado> list = new LinkedList<>();
        String sql = """
                SELECT RegCod, RegNom, RegEstReg FROM REGION""";

        try (Connection connection = ConexionDB.getConnection()) {
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RegionResultado obj = new RegionResultado(
                        rs.getString("RegCod"),
                        rs.getString("RegNom"),
                        rs.getString("RegEstReg")
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
        return "CONSULTA - REGION";
    }
}
