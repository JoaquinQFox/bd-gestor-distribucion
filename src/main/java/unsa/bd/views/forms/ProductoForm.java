package unsa.bd.views.forms;

import unsa.bd.dao.ClasificacionProductoDAO;
import unsa.bd.dao.FabricanteDAO;
import unsa.bd.dao.ProductoDAO;
import unsa.bd.dao.UnidadMedidaDAO;
import unsa.bd.model.ClasificacionProducto;
import unsa.bd.model.Fabricante;
import unsa.bd.model.Producto;
import unsa.bd.model.UnidadMedida;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class ProductoForm extends BaseForm {

    private JTextField                      codField;
    private JTextField                      desField;
    private JTextField                      preField;
    private JComboBox<Fabricante>           fabComboBox;
    private JComboBox<ClasificacionProducto> claComboBox;
    private JComboBox<UnidadMedida>         uniMedComboBox;
    private JTextField                      estRegField;

    private static final int COL_EST_REG = 6;

    public ProductoForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "PRODUCTO";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Descripción", "Precio", "Clasificación", "Uni. Med.", "Fabricante", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        // Código
        codField = makeStyledField(10);
        addFieldRowToForm(form, "Código:", codField);

        // Descripción
        desField = makeStyledField(50);
        addFieldRowToForm(form, "Descripción:", desField);

        // Precio
        preField = makeStyledField(10);
        addFieldRowToForm(form, "Precio:", preField);

        // Clasificación
        claComboBox = makeStyledComboBox(ClasificacionProducto::getClaProNom);
        loadClasificacionesInComboBox(claComboBox);
        addFieldRowToForm(form, "Clasificación:", claComboBox);
        claComboBox.setPreferredSize(new Dimension(250, claComboBox.getPreferredSize().height));

        // Unidad de Medida
        uniMedComboBox = makeStyledComboBox(UnidadMedida::getUniMedNom);
        loadUnidadesMedidaInComboBox(uniMedComboBox);
        addFieldRowToForm(form, "Uni. Medida:", uniMedComboBox);

        // Estado de Registro
        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        // Fabricante
        fabComboBox = makeStyledComboBox(Fabricante::getFabNom);
        loadFabricantesInComboBox(fabComboBox);
        addFieldRowToForm(form, "Fabricante:", fabComboBox);

        return form;
    }

    @Override
    protected void loadTableData() {
        ProductoDAO              dao    = new ProductoDAO();
        FabricanteDAO            fabDao = new FabricanteDAO();
        UnidadMedidaDAO          uniDao = new UnidadMedidaDAO();
        ClasificacionProductoDAO claDao = new ClasificacionProductoDAO();
        try {
            List<Producto>              productos       = dao.listarTodo();
            List<Fabricante>            fabricantes     = fabDao.listarTodo();
            List<UnidadMedida>          unidades        = uniDao.listarTodo();
            List<ClasificacionProducto> clasificaciones = claDao.listarTodo();

            for (Producto p : productos) {
                String nomFab = "";
                for (Fabricante f : fabricantes) {
                    if (f.getFabCod().equals(p.getProFabCod())) { nomFab = f.getFabNom(); break; }
                }
                String nomCla = "";
                for (ClasificacionProducto c : clasificaciones) {
                    if (c.getClaProCod().equals(p.getProClaCod())) { nomCla = c.getClaProNom(); break; }
                }
                String nomUni = "";
                for (UnidadMedida u : unidades) {
                    if (u.getUniMedCod().equals(p.getProUniMed())) { nomUni = u.getUniMedNom(); break; }
                }
                tableModel.addRow(new Object[]{
                        p.getProCod(),
                        p.getProDes(),
                        p.getProPre(),
                        nomCla,
                        nomUni,
                        nomFab,
                        p.getProEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        Producto p = buildFromFields();
        if (p.getProCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!p.getProCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new ProductoDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new ProductoDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new ProductoDAO().eliminar(buildFromFields().getProCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new ProductoDAO().inactivar(buildFromFields().getProCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new ProductoDAO().reactivar(buildFromFields().getProCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        codField.setText(tableModel.getValueAt(row, 0).toString());
        desField.setText(tableModel.getValueAt(row, 2).toString());
        preField.setText(tableModel.getValueAt(row, 3).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());

        String nomFab = tableModel.getValueAt(row, 1).toString();
        for (int i = 0; i < fabComboBox.getItemCount(); i++) {
            if (fabComboBox.getItemAt(i).getFabNom().equals(nomFab)) {
                fabComboBox.setSelectedIndex(i); break;
            }
        }

        String nomCla = tableModel.getValueAt(row, 4).toString();
        for (int i = 0; i < claComboBox.getItemCount(); i++) {
            if (claComboBox.getItemAt(i).getClaProNom().equals(nomCla)) {
                claComboBox.setSelectedIndex(i); break;
            }
        }

        String nomUni = tableModel.getValueAt(row, 5).toString();
        for (int i = 0; i < uniMedComboBox.getItemCount(); i++) {
            if (uniMedComboBox.getItemAt(i).getUniMedNom().equals(nomUni)) {
                uniMedComboBox.setSelectedIndex(i); break;
            }
        }
    }

    @Override
    protected void clearFields() {
        codField.setText("");
        desField.setText("");
        preField.setText("");
        estRegField.setText("");
        if (fabComboBox.getItemCount()    > 0) fabComboBox.setSelectedIndex(0);
        if (claComboBox.getItemCount()    > 0) claComboBox.setSelectedIndex(0);
        if (uniMedComboBox.getItemCount() > 0) uniMedComboBox.setSelectedIndex(0);
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(codField, editable && modo.equals(FormMode.ADD));
        setFieldEditable(desField, editable);
        setFieldEditable(preField, editable);
        setComboEditable(fabComboBox, editable);
        setComboEditable(claComboBox, editable);
        setComboEditable(uniMedComboBox, editable);
        setFieldEditable(estRegField, false);
        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private Producto buildFromFields() {
        String     cod    = codField.getText().trim();
        String     des    = desField.getText().trim();
        String     preStr = preField.getText().trim();
        String     estReg = estRegField.getText().trim();
        BigDecimal pre    = preStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(preStr);

        Fabricante           fSel = (Fabricante)           fabComboBox.getSelectedItem();
        ClasificacionProducto cSel = (ClasificacionProducto) claComboBox.getSelectedItem();
        UnidadMedida         uSel = (UnidadMedida)         uniMedComboBox.getSelectedItem();

        String fabCod = fSel != null ? fSel.getFabCod()    : "";
        String claCod = cSel != null ? cSel.getClaProCod() : "";
        String uniCod = uSel != null ? uSel.getUniMedCod() : "";

        return new Producto(cod, fabCod, des, pre, claCod, uniCod, estReg);
    }

    private void loadFabricantesInComboBox(JComboBox<Fabricante> box) {
        try {
            for (Fabricante f : new FabricanteDAO().listarTodo()) {
                if (!f.getFabEstReg().equals("*"))
                    box.addItem(f);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar fabricantes en ProductoForm");
        }
    }

    private void loadClasificacionesInComboBox(JComboBox<ClasificacionProducto> box) {
        try {
            for (ClasificacionProducto c : new ClasificacionProductoDAO().listarTodo()) {
                if (!c.getClaProEstReg().equals("*"))
                    box.addItem(c);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar clasificaciones en ProductoForm");
        }
    }

    private void loadUnidadesMedidaInComboBox(JComboBox<UnidadMedida> box) {
        try {
            for (UnidadMedida u : new UnidadMedidaDAO().listarTodo()) {
                if (!u.getUniEstReg().equals("*"))
                    box.addItem(u);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar unidades de medida en ProductoForm");
        }
    }
}