package unsa.bd.views.forms;

import unsa.bd.dao.UsuarioDAO;
import unsa.bd.dao.RolUsuarioDAO;
import unsa.bd.model.Usuario;
import unsa.bd.model.RolUsuario; // Se asume que este es el modelo existente
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UsuarioForm extends BaseForm {

    private JTextField             codField;
    private JTextField             repCodField;
    private JTextField             nomUsuField;
    private JTextField             conField;
    private JComboBox<RolUsuario>  rolComboBox;
    private JTextField             estRegField;

    private static final int COL_EST_REG = 5;

    public UsuarioForm() {
        super("EG003-USUARIO");
    }

    @Override
    protected String getHeaderTitle() {
        return "USUARIO";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Cód. Reporta", "Nombre Usuario", "Contraseña", "Rol", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {

        // Código
        codField = makeStyledField(10);
        addFieldRowToForm(form, "Código:", codField);

        // Código de Reporta
        repCodField = makeStyledField(10);
        addFieldRowToForm(form, "Rep. Venta:", repCodField);

        // Nombre de Usuario
        nomUsuField = makeStyledField(20);
        addFieldRowToForm(form, "Usuario:", nomUsuField);

        // Contraseña
        conField = makeStyledField(20);
        addFieldRowToForm(form, "Contraseña:", conField);

        // Rol de Usuario
        rolComboBox = makeStyledComboBox(RolUsuario::getRolUsuNom);
        loadRolesInComboBox(rolComboBox);
        addFieldRowToForm(form, "Rol Usuario:", rolComboBox);

        // Estado de Registro
        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        UsuarioDAO    dao    = new UsuarioDAO();
        RolUsuarioDAO rolDao = new RolUsuarioDAO();
        try {
            List<Usuario> usuarios = dao.listarTodo();
            List<RolUsuario> roles = rolDao.listarTodo();

            for (Usuario usuario : usuarios) {
                String nomRol = "";
                for (RolUsuario r : roles) {
                    if (r.getRolUsuCod().equals(usuario.getUsuRolUsu())) {
                        nomRol = r.getRolUsuNom();
                        break;
                    }
                }
                tableModel.addRow(new Object[]{
                        usuario.getUsuCod(),
                        usuario.getUsuRepCod(),
                        usuario.getUsuNomUsu(),
                        usuario.getUsuCon(),
                        nomRol,
                        usuario.getUsuEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        Usuario u = buildUsuarioFromFields();
        if (u.getUsuCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!u.getUsuCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new UsuarioDAO().agregar(u);
    }

    @Override
    protected void onModify() throws Exception {
        new UsuarioDAO().modificar(buildUsuarioFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new UsuarioDAO().eliminar(buildUsuarioFromFields().getUsuCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new UsuarioDAO().inactivar(buildUsuarioFromFields().getUsuCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new UsuarioDAO().reactivar(buildUsuarioFromFields().getUsuCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        codField.setText(tableModel.getValueAt(row, 0).toString());
        repCodField.setText(tableModel.getValueAt(row, 1) != null ? tableModel.getValueAt(row, 1).toString() : "");
        nomUsuField.setText(tableModel.getValueAt(row, 2).toString());
        conField.setText(tableModel.getValueAt(row, 3).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());

        String nomRol = tableModel.getValueAt(row, 4).toString();
        for (int i = 0; i < rolComboBox.getItemCount(); i++) {
            if (rolComboBox.getItemAt(i).getRolUsuNom().equals(nomRol)) {
                rolComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    protected void clearFields() {
        codField.setText("");
        repCodField.setText("");
        nomUsuField.setText("");
        conField.setText("");
        estRegField.setText("");
        if (rolComboBox.getItemCount() > 0) rolComboBox.setSelectedIndex(0);
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(repCodField, editable);
        setFieldEditable(nomUsuField, editable);
        setFieldEditable(conField, editable);
        setComboEditable(rolComboBox, editable);
        setFieldEditable(codField, editable && modo.equals(FormMode.ADD));
        setFieldEditable(estRegField, false);
        if (editable && modo.equals(FormMode.ADD)) {
            estRegField.setText("A");
        }
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private Usuario buildUsuarioFromFields() {
        String cod    = codField.getText().trim();
        String repCod = repCodField.getText().trim();
        String nomUsu = nomUsuField.getText().trim();
        String con    = conField.getText().trim();
        String estReg = estRegField.getText().trim();

        RolUsuario rSel = (RolUsuario) rolComboBox.getSelectedItem();
        String rolCod = (rSel != null) ? rSel.getRolUsuCod() : "";

        return new Usuario(cod, repCod, nomUsu, con, rolCod, estReg);
    }

    private void loadRolesInComboBox(JComboBox<RolUsuario> box) {
        try {
            for (RolUsuario r : new RolUsuarioDAO().listarTodo()) {
                box.addItem(r);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar roles en UsuarioForm");
        }
    }
}
