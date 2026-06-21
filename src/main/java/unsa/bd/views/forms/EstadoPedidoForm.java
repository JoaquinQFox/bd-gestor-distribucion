package unsa.bd.views.forms;

import unsa.bd.dao.EstadoPedidoDAO;
import unsa.bd.model.EstadoPedido;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.util.List;

public class EstadoPedidoForm extends BaseForm {

    private JTextField codField;
    private JTextField nomField;
    private JTextField estRegField;

    public EstadoPedidoForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "ESTADO PEDIDO";
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
        EstadoPedidoDAO dao = new EstadoPedidoDAO();
        try {
            List<EstadoPedido> es = dao.listarTodo();

            for (EstadoPedido e : es) {
                tableModel.addRow(new Object[]{
                        e.getEstPedCod(),
                        e.getEstPedNom(),
                        e.getEstPedEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        EstadoPedido r = buildFromFields();
        if (r.getEstPedCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!r.getEstPedCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new EstadoPedidoDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new EstadoPedidoDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new EstadoPedidoDAO().eliminar(buildFromFields().getEstPedCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new EstadoPedidoDAO().inactivar(buildFromFields().getEstPedCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new EstadoPedidoDAO().reactivar(buildFromFields().getEstPedCod());
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

    private EstadoPedido buildFromFields() {
        String cod = codField.getText().trim();
        String nom = nomField.getText().trim();
        String estReg = estRegField.getText().trim();
        return new EstadoPedido(cod, nom, estReg);
    }
}