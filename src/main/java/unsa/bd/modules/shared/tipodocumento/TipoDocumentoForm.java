package unsa.bd.modules.shared.tipodocumento;

import unsa.bd.commons.utility.BaseForm;
import unsa.bd.commons.utility.FormMode;

import javax.swing.*;
import java.util.List;

public class TipoDocumentoForm extends BaseForm
{

    private JTextField codField;
    private JTextField nomField;
    private JTextField estRegField;

    private static final int COL_EST_REG = 2;

    public TipoDocumentoForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "TIPO DOCUMENTO";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Nombre", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        codField = makeStyledField(10);
        addFieldRowToForm(form, "Código:", codField);

        nomField = makeStyledField(20);
        addFieldRowToForm(form, "Nombre:", nomField);

        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        TipoDocumentoDAO dao = new TipoDocumentoDAO();
        try {
            List<TipoDocumento> tipos = dao.listarTodo();
            for (TipoDocumento t : tipos) {
                tableModel.addRow(new Object[]{
                        t.getTipDocCod(),
                        t.getTipDocNom(),
                        t.getTipDocEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        TipoDocumento t = buildFromFields();
        if (t.getTipDocCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!t.getTipDocCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new TipoDocumentoDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new TipoDocumentoDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new TipoDocumentoDAO().eliminar(buildFromFields().getTipDocCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new TipoDocumentoDAO().inactivar(buildFromFields().getTipDocCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new TipoDocumentoDAO().reactivar(buildFromFields().getTipDocCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        codField.setText(tableModel.getValueAt(row, 0).toString());
        nomField.setText(tableModel.getValueAt(row, 1).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());
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
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private TipoDocumento buildFromFields() {
        return new TipoDocumento(
                codField.getText().trim(),
                nomField.getText().trim(),
                estRegField.getText().trim()
        );
    }
}