package unsa.bd.views.forms;

import unsa.bd.dao.UnidadMedidaDAO;
import unsa.bd.model.UnidadMedida;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.util.List;

public class UnidadMedidaForm extends BaseForm {

    private JTextField codField;
    private JTextField nomField;
    private JTextField abrField;
    private JTextField estRegField;

    public UnidadMedidaForm() {
        super("EG001 - UNIDAD MEDIDA");
    }

    @Override
    protected String getHeaderTitle() {
        return "UNIDAD MEDIDA";
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        // Código
        codField = makeStyledField(10);
        addFieldRowToForm(form, "Código:", codField);

        // Nombre
        nomField = makeStyledField(20);
        addFieldRowToForm(form, "Nombre:", nomField);

        // Abreviatura
        abrField = makeStyledField(10);
        addFieldRowToForm(form, "Abreviación:", abrField);

        // Estado de Registro
        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Nombre", "Abreviación", "Estado de Registro"};
    }

    @Override
    protected void loadTableData() {
        UnidadMedidaDAO dao = new UnidadMedidaDAO();
        try {
            List<UnidadMedida> un = dao.listarTodo();

            for (UnidadMedida u : un) {
                tableModel.addRow(new Object[]{
                        u.getUniMedCod(),
                        u.getUniMedNom(),
                        u.getUniMedAbr(),
                        u.getUniEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        UnidadMedida u = buildFromFields();
        if (u.getUniMedCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!u.getUniMedCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new UnidadMedidaDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new UnidadMedidaDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new UnidadMedidaDAO().eliminar(buildFromFields().getUniMedCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new UnidadMedidaDAO().inactivar(buildFromFields().getUniMedCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new UnidadMedidaDAO().reactivar(buildFromFields().getUniMedCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        codField.setText(tableModel.getValueAt(row, 0).toString());
        nomField.setText(tableModel.getValueAt(row, 1).toString());
        abrField.setText(tableModel.getValueAt(row, 2).toString());
        estRegField.setText(tableModel.getValueAt(row, 3).toString());
    }

    @Override
    protected void clearFields() {
        codField.setText("");
        nomField.setText("");
        abrField.setText("");
        estRegField.setText("");
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(codField, editable && modo.equals(FormMode.ADD));
        setFieldEditable(nomField, editable);
        setFieldEditable(abrField, editable);
        setFieldEditable(estRegField, false);

        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        return table.getValueAt(row, 3).toString().trim();
    }

    private UnidadMedida buildFromFields() {
        String cod = codField.getText().trim();
        String nom = nomField.getText().trim();
        String abr = abrField.getText().trim();
        String estReg = estRegField.getText().trim();
        return new UnidadMedida(cod, nom, abr, estReg);
    }
}