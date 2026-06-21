package unsa.bd.views.forms;

import unsa.bd.dao.CargoDAO;
import unsa.bd.model.Cargo;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.util.List;

public class CargoForm extends BaseForm {

    private JTextField codField;
    private JTextField nomField;
    private JTextField estRegField;

    public CargoForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "CARGO";
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
        CargoDAO dao = new CargoDAO();
        try {
            List<Cargo> cargos = dao.listarTodo();

            for (Cargo c : cargos) {
                tableModel.addRow(new Object[]{
                        c.getCarCod(),
                        c.getCarNom(),
                        c.getCarEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        Cargo c = buildFromFields();
        if (c.getCarCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!c.getCarCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new CargoDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new CargoDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new CargoDAO().eliminar(buildFromFields().getCarCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new CargoDAO().inactivar(buildFromFields().getCarCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new CargoDAO().reactivar(buildFromFields().getCarCod());
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

    private Cargo buildFromFields() {
        String cod = codField.getText().trim();
        String nom = nomField.getText().trim();
        String estReg = estRegField.getText().trim();
        return new Cargo(cod, nom, estReg);
    }
}