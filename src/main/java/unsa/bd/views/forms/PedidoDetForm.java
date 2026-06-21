package unsa.bd.views.forms;

import unsa.bd.dao.PedidoDetDAO;
import unsa.bd.dao.ProductoDAO;
import unsa.bd.model.PedidoDet;
import unsa.bd.model.Producto;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDetForm extends BaseForm {

    private JTextField cabNumField;
    private JComboBox<Producto> proComboBox;
    private JTextField preField;
    private JTextField canField;
    private JTextField totField;
    private JTextField estRegField;

    private static final int COL_EST_REG = 5;

    public PedidoDetForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "PEDIDO DETALLE";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Núm. Pedido", "Producto", "Precio", "Cantidad", "Total", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        cabNumField = makeStyledField(10);
        addFieldRowToForm(form, "Núm. Pedido:", cabNumField);

        proComboBox = makeStyledComboBox(Producto::getProDes);
        loadProductosInComboBox(proComboBox);
        addFieldRowToForm(form, "Producto:", proComboBox);

        preField = makeStyledField(15);
        addFieldRowToForm(form, "Precio:", preField);

        canField = makeStyledField(10);
        addFieldRowToForm(form, "Cantidad:", canField);

        totField = makeStyledField(15);
        addFieldRowToForm(form, "Total:", totField);

        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    private void loadProductosInComboBox(JComboBox<Producto> box) {
        try {
            for (Producto p : new ProductoDAO().listarTodo()) box.addItem(p);
        } catch (Exception e) {
            System.err.println("Error al cargar productos en PedidoDetForm");
        }
    }

    @Override
    protected void loadTableData() {
        PedidoDetDAO dao = new PedidoDetDAO();
        try {
            List<PedidoDet> detalles = dao.listarTodo();
            for (PedidoDet d : detalles) {
                tableModel.addRow(new Object[]{
                        d.getPedDetCabNum(),
                        d.getPedDetProCod(),
                        d.getPedDetPre(),
                        d.getPedDetCan(),
                        d.getPedDetTot(),
                        d.getPedDetEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        PedidoDet d = buildFromFields();
        if (d.getPedDetCabNum().isEmpty()) throw new Exception("Llenar el número de pedido");
        if (d.getPedDetProCod().isEmpty()) throw new Exception("Llenar el código de producto");
        if (canField.getText().trim().isEmpty()) throw new Exception("Llenar el campo cantidad");
        new PedidoDetDAO().agregar(d);
    }

    @Override
    protected void onModify() throws Exception {
        new PedidoDetDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        PedidoDet d = buildFromFields();
        new PedidoDetDAO().eliminar(d.getPedDetCabNum(), d.getPedDetProCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        PedidoDet d = buildFromFields();
        new PedidoDetDAO().inactivar(d.getPedDetCabNum(), d.getPedDetProCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        PedidoDet d = buildFromFields();
        new PedidoDetDAO().reactivar(d.getPedDetCabNum(), d.getPedDetProCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        cabNumField.setText(tableModel.getValueAt(row, 0).toString());
        preField.setText(tableModel.getValueAt(row, 2).toString());
        canField.setText(tableModel.getValueAt(row, 3).toString());
        totField.setText(tableModel.getValueAt(row, 4).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());

        String nomPro = tableModel.getValueAt(row, 1).toString();
        for (int i = 0; i < proComboBox.getItemCount(); i++) {
            if (proComboBox.getItemAt(i).getProDes().equals(nomPro)) {
                proComboBox.setSelectedIndex(i); break;
            }
        }
    }

    @Override
    protected void clearFields() {
        cabNumField.setText("");
        if (proComboBox.getItemCount() > 0) proComboBox.setSelectedIndex(0);
        preField.setText("");
        canField.setText("");
        totField.setText("");
        estRegField.setText("");
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(cabNumField, editable && modo.equals(FormMode.ADD));
        setComboEditable(proComboBox, editable && modo.equals(FormMode.ADD));
        setFieldEditable(preField, editable);
        setFieldEditable(canField, editable);
        setFieldEditable(totField, editable);
        setFieldEditable(estRegField, false);
        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private PedidoDet buildFromFields() {
        String cabNum = cabNumField.getText().trim();
        BigDecimal pre = new BigDecimal(preField.getText().trim());
        String canStr = canField.getText().trim();
        BigDecimal totStr = new BigDecimal(totField.getText().trim());
        String estReg = estRegField.getText().trim();

        Producto  pSel = (Producto)  proComboBox.getSelectedItem();
        String proCod  = pSel != null ? pSel.getProCod()   : "";

        int        can = canStr.isEmpty() ? 0 : Integer.parseInt(canStr);

        return new PedidoDet(cabNum, proCod, can, pre, totStr, estReg);
    }
}