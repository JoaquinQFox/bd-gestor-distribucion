package unsa.bd.views.forms;

import unsa.bd.dao.AccionDAO;
import unsa.bd.model.Accion;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.util.List;

public class AccionForm extends BaseForm {

    private JTextField codField;
    private JTextField nomField;
    private JTextField estRegField;

    public AccionForm() {
        super("EG003 - ACCION");
    }

    @Override
    protected String getHeaderTitle() {
        return "ACCION";
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
        AccionDAO dao = new AccionDAO();
        try {
            List<Accion> acciones = dao.listarTodo();

            for (Accion a : acciones) {
                tableModel.addRow(new Object[]{
                        a.getAccCod(),
                        a.getAccNom(),
                        a.getAccEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        Accion a = buildFromFields();
        if (a.getAccCod().isEmpty()) throw new Exception("Llenar el campo de código");
        new AccionDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new AccionDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new AccionDAO().eliminar(buildFromFields().getAccCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new AccionDAO().inactivar(buildFromFields().getAccCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new AccionDAO().reactivar(buildFromFields().getAccCod());
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

    private Accion buildFromFields() {
        String cod = codField.getText().trim();
        String nom = nomField.getText().trim();
        String estReg = estRegField.getText().trim();
        return new Accion(cod, nom, estReg);
    }
}