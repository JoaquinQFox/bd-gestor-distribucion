package unsa.bd.modules.compras.compra.compradet;

import unsa.bd.modules.inventario.producto.ProductoDAO;
import unsa.bd.modules.inventario.producto.Producto;
import unsa.bd.commons.utility.BaseForm;
import unsa.bd.commons.utility.FormMode;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class CompraDetForm extends BaseForm
{

    private JTextField cabNumField;
    private JComboBox<Producto> proComboBox;
    private JTextField canField;
    private JTextField preField;
    private JTextField totField;
    private JTextField estRegField;

    private static final int COL_EST_REG = 5;

    public CompraDetForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "COMPRA - DETALLES";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Núm. Compra", "Producto", "Cantidad", "Precio", "Total", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        cabNumField = makeStyledField(10);
        addFieldRowToForm(form, "Núm. Compra:", cabNumField);

        proComboBox = makeStyledComboBox(Producto::getProDes);
        loadProductosInComboBox(proComboBox);
        addFieldRowToForm(form, "Producto:", proComboBox);
        proComboBox.setPreferredSize(new Dimension(500, proComboBox.getPreferredSize().height));
        proComboBox.addActionListener(e -> actualizarPrecioPorProducto());

        canField = makeStyledField(10);
        addFieldRowToForm(form, "Cantidad:", canField);
        canField.getDocument().addDocumentListener(new DocumentListenerTest());

        preField = makeStyledField(15);
        addFieldRowToForm(form, "Precio:", preField);
        preField.getDocument().addDocumentListener(new DocumentListenerTest());

        totField = makeStyledField(15);
        addFieldRowToForm(form, "Total:", totField);

        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    public class DocumentListenerTest implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            actualizarCampoTotal();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            actualizarCampoTotal();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            actualizarCampoTotal();
        }
    }

    public void actualizarCampoTotal() {
        try {
            BigDecimal cantidad = new BigDecimal(canField.getText().trim());
            BigDecimal precio = new BigDecimal(preField.getText().trim());
            BigDecimal total = cantidad.multiply(precio);
            totField.setText(total.toString());
        } catch (Exception e) {
            totField.setText("");
        }
    }


    private void loadProductosInComboBox(JComboBox<Producto> box) {
        try {
            Producto n = new Producto();
            n.setProDes("");
            box.addItem(n);
            for (Producto p : new ProductoDAO().listarTodo()) box.addItem(p);
        } catch (Exception e) {
            System.err.println("Error al cargar productos en CompraDetForm");
        }
    }

    private void actualizarPrecioPorProducto() {
        if (modo.equals(FormMode.ADD)) {
            Producto producto = (Producto) proComboBox.getSelectedItem();
            if (producto != null && producto.getProPre() != null) {
                preField.setText(producto.getProPre().toString());
            } else {
                preField.setText("");
            }
        }
    }

    @Override
    protected void loadTableData() {
        CompraDetDAO dao = new CompraDetDAO();
        try {
            List<CompraDet> detalles = dao.listarTodo();
            for (CompraDet d : detalles) {
                String proDes = "";
                ProductoDAO proDao = new ProductoDAO();
                for (Producto p : proDao.listarTodo()) {
                    if (p.getProCod().equals(d.getComDetProCod())) {
                        proDes = p.getProDes();
                        break;
                    }
                }

                tableModel.addRow(new Object[]{
                        d.getComDetCabNum(),
                        proDes,
                        d.getComDetCan(),
                        d.getComDetPre(),
                        d.getComDetTot(),
                        d.getComDetEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        CompraDet d = buildFromFields();
        if (d.getComDetCabNum().isEmpty()) throw new Exception("Llenar el número de compra");
        if (d.getComDetProCod().isEmpty()) throw new Exception("Seleccionar un producto");
        if (canField.getText().trim().isEmpty()) throw new Exception("Llenar el campo cantidad");
        new CompraDetDAO().agregar(d);
    }

    @Override
    protected void onModify() throws Exception {
        new CompraDetDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        CompraDet d = buildFromFields();
        new CompraDetDAO().eliminar(d.getComDetCabNum(), d.getComDetProCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        CompraDet d = buildFromFields();
        new CompraDetDAO().inactivar(d.getComDetCabNum(), d.getComDetProCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        CompraDet d = buildFromFields();
        new CompraDetDAO().reactivar(d.getComDetCabNum(), d.getComDetProCod());
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
        proComboBox.setSelectedIndex(0);
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
        setFieldEditable(preField, editable && modo.equals(FormMode.MODIFY));
        setFieldEditable(estRegField, false);
        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private CompraDet buildFromFields() {
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

        return new CompraDet(cabNum, proCod, can, pre, tot, estReg);
    }
}
