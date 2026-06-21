package unsa.bd.views.forms;

import unsa.bd.dao.RolUsuarioDAO;
import unsa.bd.model.RolUsuario;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.util.List;

public class RolUsuarioForm extends BaseForm {

    private JTextField codField;
    private JTextField nomField;
    private JTextField estRegField;

    public RolUsuarioForm() {
        super("EG001 - ROL USUARIO");
    }

    @Override
    protected String getHeaderTitle() {
        return "ROL USUARIO";
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        // Código
        codField = makeStyledField(10);
        addFieldRowToForm(form, "Código:", codField);

        // Nombre
        nomField = makeStyledField(20);
        addFieldRowToForm(form, "Nombre:", nomField);

        // Estado de Registro
        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Nombre", "Estado de Registro"};
    }

    @Override
    protected void loadTableData() {
        RolUsuarioDAO dao = new RolUsuarioDAO();
        try {
            List<RolUsuario> roles = dao.listarTodo();

            for (RolUsuario r : roles) {
                tableModel.addRow(new Object[]{
                        r.getRolUsuCod(),
                        r.getRolUsuNom(),
                        r.getRolUsuEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        RolUsuario r = buildFromFields();
        if (r.getRolUsuCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!r.getRolUsuCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new RolUsuarioDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new RolUsuarioDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new RolUsuarioDAO().eliminar(buildFromFields().getRolUsuCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new RolUsuarioDAO().inactivar(buildFromFields().getRolUsuCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new RolUsuarioDAO().reactivar(buildFromFields().getRolUsuCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        codField.setText(tableModel.getValueAt(row, 0).toString());
        nomField.setText(tableModel.getValueAt(row, 1).toString());
        estRegField.setText(tableModel.getValueAt(row, 2).toString());
    }

    @Override
    protected void clearFields() {
        codField.setText("");
        nomField.setText("");
        estRegField.setText("");
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(codField, editable && modo.equals(FormMode.ADD));
        setFieldEditable(nomField, editable);
        setFieldEditable(estRegField, false);

        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        return table.getValueAt(row, 2).toString().trim();
    }

    private RolUsuario buildFromFields() {
        String cod = codField.getText().trim();
        String nom = nomField.getText().trim();
        String estReg = estRegField.getText().trim();
        return new RolUsuario(cod, nom, estReg);
    }
}