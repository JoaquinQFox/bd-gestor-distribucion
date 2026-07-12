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

public class ConsultaUsuario extends BaseConsulta {

    public ConsultaUsuario(String title) {
        super(title);
    }

    @Override
    protected void loadTableData() {
        try {
            List<ConsultaObj> rs = getDatabaseData();
            for (ConsultaObj o : rs) {
                tableModel.addRow(new Object[]{
                        o.usuCod,
                        o.repVenNom,
                        o.usuNomUsu,
                        o.usuCon,
                        o.rolUsuNom,
                        o.usuEstReg
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private record ConsultaObj(String usuCod, String repVenNom, String usuNomUsu, String usuCon,
                               String rolUsuNom, String usuEstReg) { }

    private List<ConsultaObj> getDatabaseData() throws SQLException {
        List<ConsultaObj> list = new LinkedList<>();
        String sql = """
                 SELECT
                     usu.UsuCod,
                     rep.RepVenNom,
                     usu.UsuNomUsu,
                     usu.UsuCon,
                     rol.RolUsuNom,
                     usu.UsuEstReg
                 FROM USUARIO usu
                 JOIN REP_VENTA rep
                     ON usu.UsuRepCod = rep.RepVenCod
                 JOIN ROL_USUARIO rol
                     ON usu.UsuRolUsu = rol.RolUsuCod
                 ORDER BY usu.UsuCod ASC""";

        try (Connection connection = ConexionDB.getConnection()) {
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ConsultaObj obj = new ConsultaObj(
                        rs.getString("UsuCod"),
                        rs.getString("RepVenNom"),
                        rs.getString("UsuNomUsu"),
                        rs.getString("UsuCon"),
                        rs.getString("RolUsuNom"),
                        rs.getString("UsuEstReg")
                );
                list.add(obj);
            }
        }
        return list;
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Nombre", "Nombre de Usuario", "Contraseña", "Rol", "Estado de Registro"};
    }

    @Override
    protected String getHeaderTitle() {
        return "CONSULTA - USUARIO";
    }
}
