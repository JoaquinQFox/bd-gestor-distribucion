package unsa.bd.modules.cliente.tipocliente;

import unsa.bd.commons.utility.BaseForm;
import unsa.bd.commons.utility.FormMode;

import javax.swing.*;
import java.util.List;

public class TipoClienteForm extends BaseForm
{

    private JTextField codField;
    private JTextField nomField;
    private JTextField estRegField;

    public TipoClienteForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "TIPO CLIENTE";
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
        TipoClienteDAO dao = new TipoClienteDAO();
        try {
            List<TipoCliente> tipos = dao.listarTodo();

            for (TipoCliente t : tipos) {
                tableModel.addRow(new Object[]{
                        t.getTipCliCod(),
                        t.getTipCliNom(),
                        t.getTipCliEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        TipoCliente t = buildFromFields();
        if (t.getTipCliCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!t.getTipCliCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new TipoClienteDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new TipoClienteDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new TipoClienteDAO().eliminar(buildFromFields().getTipCliCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new TipoClienteDAO().inactivar(buildFromFields().getTipCliCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new TipoClienteDAO().reactivar(buildFromFields().getTipCliCod());
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

    private TipoCliente buildFromFields() {
        String cod = codField.getText().trim();
        String nom = nomField.getText().trim();
        String estReg = estRegField.getText().trim();
        return new TipoCliente(cod, nom, estReg);
    }
}