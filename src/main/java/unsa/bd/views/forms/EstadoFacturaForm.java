package unsa.bd.views.forms;

import unsa.bd.dao.EstadoFacturaDAO;
import unsa.bd.model.EstadoFactura;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.util.List;

public class EstadoFacturaForm extends BaseForm {

    private JTextField codField;
    private JTextField nomField;
    private JTextField estRegField;

    public EstadoFacturaForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "ESTADO FACTURA";
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
        EstadoFacturaDAO dao = new EstadoFacturaDAO();
        try {
            List<EstadoFactura> es = dao.listarTodo();

            for (EstadoFactura e : es) {
                tableModel.addRow(new Object[]{
                        e.getEstFacCod(),
                        e.getEstFacNom(),
                        e.getEstFacEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        EstadoFactura e = buildFromFields();
        if (e.getEstFacCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!e.getEstFacCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new EstadoFacturaDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new EstadoFacturaDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new EstadoFacturaDAO().eliminar(buildFromFields().getEstFacCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new EstadoFacturaDAO().inactivar(buildFromFields().getEstFacCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new EstadoFacturaDAO().reactivar(buildFromFields().getEstFacCod());
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

    private EstadoFactura buildFromFields() {
        String cod = codField.getText().trim();
        String nom = nomField.getText().trim();
        String estReg = estRegField.getText().trim();
        return new EstadoFactura(cod, nom, estReg);
    }
}