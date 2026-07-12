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

public class ConsultaRepVenta extends BaseConsulta {

    public ConsultaRepVenta(String title) {
        super(title);
    }

    @Override
    protected void loadTableData() {
        try {
            List<ConsultaObj> rs = getDatabaseData();
            for (ConsultaObj o : rs) {
                tableModel.addRow(new Object[]{
                        o.nom,
                        o.eda,
                        o.ofiCod,
                        o.carCod,
                        o.estReg
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private record ConsultaObj(String nom, int eda, String ofiCod, String carCod, String estReg) { }

    private List<ConsultaObj> getDatabaseData() throws SQLException {
        List<ConsultaObj> list = new LinkedList<>();
        String sql = """
                 SELECT RepVenNom, RepVenEda, RepVenOfiCod, RepVenCarCod, RepVenEstReg
                 FROM REP_VENTA
                 ORDER BY RepVenNom""";

        try (Connection connection = ConexionDB.getConnection()) {
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ConsultaObj obj = new ConsultaObj(
                        rs.getString("RepVenNom"),
                        rs.getInt("RepVenEda"),
                        rs.getString("RepVenOfiCod"),
                        rs.getString("RepVenCarCod"),
                        rs.getString("RepVenEStReg")
                );
                list.add(obj);
            }
        }
        return list;
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Nombre", "Edad", "Codigo de Oficina", "Codigo de Cargo", "Estado de Registro"};
    }

    @Override
    protected String getHeaderTitle() {
        return "CONSULTA - REPRESENTANTE DE VENTAS";
    }
}
