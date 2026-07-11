package unsa.bd.modules.ventas.factura.facturadet;

import unsa.bd.modules.inventario.producto.ProductoDAO;
import unsa.bd.modules.inventario.producto.Producto;
import unsa.bd.commons.utility.BaseForm;
import unsa.bd.commons.utility.FormMode;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class FacturaDetForm extends BaseForm
{

    private JTextField cabNumField;
    private JComboBox<Producto> proComboBox;
    private JTextField canField;
    private JTextField preField;
    private JTextField totField;
    private JTextField estRegField;

    private static final int COL_EST_REG = 5;

    public FacturaDetForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "FACTURA - DETALLES";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Núm. Factura", "Producto", "Cantidad", "Precio", "Total", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        cabNumField = makeStyledField(10);
        addFieldRowToForm(form, "Núm. Factura:", cabNumField);

        proComboBox = makeStyledComboBox(Producto::getProDes);
        loadProductosInComboBox(proComboBox);
        addFieldRowToForm(form, "Producto:", proComboBox);
        proComboBox.setPreferredSize(new Dimension(300, proComboBox.getPreferredSize().height));

        canField = makeStyledField(10);
        addFieldRowToForm(form, "Cantidad:", canField);

        preField = makeStyledField(15);
        addFieldRowToForm(form, "Precio:", preField);

        totField = makeStyledField(15);
        addFieldRowToForm(form, "Total:", totField);

        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    private void loadProductosInComboBox(JComboBox<Producto> box) {
        try {
            Producto n = new Producto();
            n.setProDes("");
            box.addItem(n);
            for (Producto p : new ProductoDAO().listarTodo()) box.addItem(p);
        } catch (Exception e) {
            System.err.println("Error al cargar productos en FacturaDetForm");
        }
    }

    @Override
    protected void loadTableData() {
        FacturaDetDAO dao = new FacturaDetDAO();
        try {
            List<FacturaDet> detalles = dao.listarTodo();
            for (FacturaDet d : detalles) {
                tableModel.addRow(new Object[]{
                        d.getFacDetCabNum(),
                        d.getFacDetProCod(),
                        d.getFacDetCan(),
                        d.getFacDetPre(),
                        d.getFacDetTot(),
                        d.getFacDetEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        FacturaDet d = buildFromFields();
        if (d.getFacDetCabNum().isEmpty()) throw new Exception("Llenar el número de factura");
        if (d.getFacDetProCod().isEmpty()) throw new Exception("Seleccionar un producto");
        if (canField.getText().trim().isEmpty()) throw new Exception("Llenar el campo cantidad");
        new FacturaDetDAO().agregar(d);
    }

    @Override
    protected void onModify() throws Exception {
        new FacturaDetDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        FacturaDet d = buildFromFields();
        new FacturaDetDAO().eliminar(d.getFacDetCabNum(), d.getFacDetProCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        FacturaDet d = buildFromFields();
        new FacturaDetDAO().inactivar(d.getFacDetCabNum(), d.getFacDetProCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        FacturaDet d = buildFromFields();
        new FacturaDetDAO().reactivar(d.getFacDetCabNum(), d.getFacDetProCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        cabNumField.setText(tableModel.getValueAt(row, 0).toString());
        canField.setText(tableModel.getValueAt(row, 2).toString());
        preField.setText(tableModel.getValueAt(row, 3).toString());
        totField.setText(tableModel.getValueAt(row, 4).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());

        String codPro = tableModel.getValueAt(row, 1).toString();
        for (int i = 1; i < proComboBox.getItemCount(); i++) {
            if (proComboBox.getItemAt(i).getProCod().equals(codPro)) {
                proComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    protected void clearFields() {
        cabNumField.setText("");
        if (proComboBox.getItemCount() > 0) proComboBox.setSelectedIndex(0);
        canField.setText("");
        preField.setText("");
        totField.setText("");
        estRegField.setText("");
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(cabNumField, editable && modo.equals(FormMode.ADD));
        setComboEditable(proComboBox, editable && modo.equals(FormMode.ADD));
        setFieldEditable(canField, editable);
        setFieldEditable(preField, editable);
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

    private FacturaDet buildFromFields() {
        String cabNum = cabNumField.getText().trim();
        String canStr = canField.getText().trim();
        String preStr = preField.getText().trim();
        String totStr = totField.getText().trim();
        String estReg = estRegField.getText().trim();

        int can = canStr.isEmpty() ? 0 : Integer.parseInt(canStr);
        BigDecimal pre = preStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(preStr);
        BigDecimal tot = totStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(totStr);

        Producto pSel = (Producto) proComboBox.getSelectedItem();
        String proCod = pSel != null ? pSel.getProCod() : "";

        return new FacturaDet(cabNum, proCod, can, pre, tot, estReg);
    }
}