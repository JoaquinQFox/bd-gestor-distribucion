package unsa.bd.modules.ventas.pedido.pedidocab;

import unsa.bd.modules.cliente.cliente.Cliente;
import unsa.bd.modules.cliente.cliente.ClienteDAO;
import unsa.bd.modules.personal.repventa.RepVenta;
import unsa.bd.modules.personal.repventa.RepVentaDAO;
import unsa.bd.modules.shared.ciudad.Ciudad;
import unsa.bd.modules.shared.ciudad.CiudadDAO;
import unsa.bd.modules.shared.region.Region;
import unsa.bd.modules.shared.region.RegionDAO;
import unsa.bd.modules.ventas.pedido.estadopedido.EstadoPedido;
import unsa.bd.modules.ventas.pedido.estadopedido.EstadoPedidoDAO;
import unsa.bd.commons.utility.BaseForm;
import unsa.bd.commons.utility.FormMode;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public class PedidoCabForm extends BaseForm
{

    private JTextField                  numField;
    private JComboBox<Cliente>          cliComboBox;
    private JComboBox<RepVenta>         repComboBox;
    private JTextField                  diaField;
    private JTextField                  mesField;
    private JTextField                  yeaField;
    private JComboBox<EstadoPedido>     estPedComboBox;
    private JTextField                  totField;
    private JComboBox<Region>           regComboBox;
    private JComboBox<Ciudad>           ciuComboBox;
    private JTextField                  estRegField;

    private boolean actualizando = false;
    private static final int COL_EST_REG = 10;

    public PedidoCabForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "PEDIDO CABECERA";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Número", "Cliente", "Rep. Ventas", "Día", "Mes", "Año", "Estado Pedido", "Total", "Región", "Ciudad", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        numField = makeStyledField(10);
        addFieldRowToForm(form, "Número:", numField);

        cliComboBox = makeStyledComboBox(Cliente::getCliCod);
        loadClientesInComboBox(cliComboBox);
        addFieldRowToForm(form, "Cliente:", cliComboBox);

        repComboBox = makeStyledComboBox(RepVenta::getRepVenNom);
        loadRepVentasInComboBox(repComboBox);
        addFieldRowToForm(form, "Rep. Ventas:", repComboBox);

        diaField = makeStyledField(5);
        addFieldRowToForm(form, "Día:", diaField);

        mesField = makeStyledField(5);
        addFieldRowToForm(form, "Mes:", mesField);

        yeaField = makeStyledField(5);
        addFieldRowToForm(form, "Año:", yeaField);

        estPedComboBox = makeStyledComboBox(EstadoPedido::getEstPedNom);
        loadEstadosPedidoInComboBox(estPedComboBox);
        addFieldRowToForm(form, "Estado Pedido:", estPedComboBox);

        totField = makeStyledField(15);
        addFieldRowToForm(form, "Total:", totField);

        regComboBox = makeStyledComboBox(Region::getRegNom);
        loadRegionesInComboBox(regComboBox);
        addFieldRowToForm(form, "Región:", regComboBox);

        ciuComboBox = makeStyledComboBox(Ciudad::getCiuNom);
        addFieldRowToForm(form, "Ciudad:", ciuComboBox);

        actualizarCiudadesPorRegion();
        regComboBox.addActionListener(e -> actualizarCiudadesPorRegion());

        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        PedidoCabDAO dao       = new PedidoCabDAO();
        ClienteDAO cliDao    = new ClienteDAO();
        RepVentaDAO repDao    = new RepVentaDAO();
        EstadoPedidoDAO estPedDao = new EstadoPedidoDAO();
        RegionDAO regDao    = new RegionDAO();
        CiudadDAO ciuDao    = new CiudadDAO();
        try {
            List<PedidoCab>    pedidos   = dao.listarTodo();
            List<Cliente>      clientes  = cliDao.listarTodo();
            List<RepVenta>     repVentas = repDao.listarTodo();
            List<EstadoPedido> estados   = estPedDao.listarTodo();
            List<Region>       regiones  = regDao.listarTodo();
            List<Ciudad>       ciudades  = ciuDao.listarTodo();

            for (PedidoCab p : pedidos) {
                String codCli = p.getPedCabCliCod();

                String nomRep = "";
                for (RepVenta r : repVentas) {
                    if (r.getRepVenCod().equals(p.getPedCabRepCod())) { nomRep = r.getRepVenNom(); break; }
                }
                String nomEst = "";
                for (EstadoPedido e : estados) {
                    if (e.getEstPedCod().equals(p.getPedCabEstCod())) { nomEst = e.getEstPedNom(); break; }
                }
                String nomReg = "";
                for (Region r : regiones) {
                    if (r.getRegCod().equals(p.getPedCabRegCod())) { nomReg = r.getRegNom(); break; }
                }
                String nomCiu = "";
                for (Ciudad c : ciudades) {
                    if (c.getCiuCod().equals(p.getPedCabCiuCod())) { nomCiu = c.getCiuNom(); break; }
                }
                tableModel.addRow(new Object[]{
                        p.getPedCabNum(),
                        codCli,
                        nomRep,
                        p.getPedCabDia(),
                        p.getPedCabMes(),
                        p.getPedCabYea(),
                        nomEst,
                        p.getPedCabTot(),
                        nomReg,
                        nomCiu,
                        p.getPedCabEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        PedidoCab p = buildFromFields();
        if (p.getPedCabNum().isEmpty()) throw new Exception("Llenar el campo número");
        if (!p.getPedCabNum().matches("\\d+")) throw new Exception("El número solo acepta dígitos");
        if (p.getPedCabCliCod().isEmpty()) throw new Exception("Seleccionar un cliente");
        new PedidoCabDAO().agregar(p);
    }

    @Override
    protected void onModify() throws Exception {
        new PedidoCabDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new PedidoCabDAO().eliminar(buildFromFields().getPedCabNum());
    }

    @Override
    protected void onInactivate() throws Exception {
        new PedidoCabDAO().inactivar(buildFromFields().getPedCabNum());
    }

    @Override
    protected void onReactivate() throws Exception {
        new PedidoCabDAO().reactivar(buildFromFields().getPedCabNum());
    }

    @Override
    protected void fillFormFromRow(int row) {
        numField.setText(tableModel.getValueAt(row, 0).toString());
        diaField.setText(tableModel.getValueAt(row, 3).toString());
        mesField.setText(tableModel.getValueAt(row, 4).toString());
        yeaField.setText(tableModel.getValueAt(row, 5).toString());
        totField.setText(tableModel.getValueAt(row, 7).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());

        String codCli = tableModel.getValueAt(row, 1).toString();
        for (int i = 0; i < cliComboBox.getItemCount(); i++) {
            if (cliComboBox.getItemAt(i).getCliCod().equals(codCli)) {
                cliComboBox.setSelectedIndex(i); break;
            }
        }

        String nomRep = tableModel.getValueAt(row, 2).toString();
        for (int i = 0; i < repComboBox.getItemCount(); i++) {
            if (repComboBox.getItemAt(i).getRepVenNom().equals(nomRep)) {
                repComboBox.setSelectedIndex(i); break;
            }
        }

        String nomEst = tableModel.getValueAt(row, 6).toString();
        for (int i = 0; i < estPedComboBox.getItemCount(); i++) {
            if (estPedComboBox.getItemAt(i).getEstPedNom().equals(nomEst)) {
                estPedComboBox.setSelectedIndex(i); break;
            }
        }

        String nomReg = tableModel.getValueAt(row, 8).toString();
        for (int i = 0; i < regComboBox.getItemCount(); i++) {
            if (regComboBox.getItemAt(i).getRegNom().equals(nomReg)) {
                regComboBox.setSelectedIndex(i); break;
            }
        }

        actualizarCiudadesPorRegion();
        String nomCiu = tableModel.getValueAt(row, 9).toString();
        for (int i = 0; i < ciuComboBox.getItemCount(); i++) {
            if (ciuComboBox.getItemAt(i).getCiuNom().equals(nomCiu)) {
                ciuComboBox.setSelectedIndex(i); break;
            }
        }
    }

    @Override
    protected void clearFields() {
        numField.setText("");
        diaField.setText("");
        mesField.setText("");
        yeaField.setText("");
        totField.setText("");
        estRegField.setText("");
        if (cliComboBox.getItemCount()    > 0) cliComboBox.setSelectedIndex(0);
        if (repComboBox.getItemCount()    > 0) repComboBox.setSelectedIndex(0);
        if (estPedComboBox.getItemCount() > 0) estPedComboBox.setSelectedIndex(0);
        if (regComboBox.getItemCount()    > 0) regComboBox.setSelectedIndex(0);
        actualizarCiudadesPorRegion();
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(numField, editable && modo.equals(FormMode.ADD));
        setComboEditable(cliComboBox, editable);
        setComboEditable(repComboBox, editable);
        setFieldEditable(diaField, editable);
        setFieldEditable(mesField, editable);
        setFieldEditable(yeaField, editable);
        setComboEditable(estPedComboBox, editable);
        setFieldEditable(totField, editable);
        setComboEditable(regComboBox, editable);
        setComboEditable(ciuComboBox, editable);
        setFieldEditable(estRegField, false);
        if (editable && modo.equals(FormMode.ADD)) {
            estRegField.setText("A");
            Calendar cal = Calendar.getInstance();
            diaField.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
            mesField.setText(String.valueOf(cal.get(Calendar.MONTH) + 1));
            yeaField.setText(String.valueOf(cal.get(Calendar.YEAR)));
        }
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private PedidoCab buildFromFields() {
        String     num    = numField.getText().trim();
        String     totStr = totField.getText().trim();
        BigDecimal tot    = totStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(totStr);
        String     estReg = estRegField.getText().trim();
        int dia = diaField.getText().trim().isEmpty() ? 0 : Integer.parseInt(diaField.getText().trim());
        int mes = mesField.getText().trim().isEmpty() ? 0 : Integer.parseInt(mesField.getText().trim());
        int yea = yeaField.getText().trim().isEmpty() ? 0 : Integer.parseInt(yeaField.getText().trim());

        Cliente      cSel = (Cliente)      cliComboBox.getSelectedItem();
        RepVenta     rSel = (RepVenta)     repComboBox.getSelectedItem();
        EstadoPedido eSel = (EstadoPedido) estPedComboBox.getSelectedItem();
        Region       rgSel = (Region)      regComboBox.getSelectedItem();
        Ciudad       ciSel = (Ciudad)      ciuComboBox.getSelectedItem();

        String cliCod = cSel  != null ? cSel.getCliCod()      : "";
        String repCod = rSel  != null ? rSel.getRepVenCod()   : "";
        String estCod = eSel  != null ? eSel.getEstPedCod()   : "";
        String regCod = rgSel != null ? rgSel.getRegCod()     : "";
        String ciuCod = ciSel != null ? ciSel.getCiuCod()     : "";

        return new PedidoCab(num, cliCod, repCod, dia, mes, yea, estCod, tot, ciuCod, regCod, estReg);
    }

    private void actualizarCiudadesPorRegion() {
        if (actualizando) return;
        actualizando = true;
        try {
            Region regSel = (Region) regComboBox.getSelectedItem();
            if (regSel == null) return;
            List<Ciudad> ciudades = new CiudadDAO().listarTodo();
            ciuComboBox.removeAllItems();
            Ciudad n = new Ciudad();
            n.setCiuNom("");
            ciuComboBox.addItem(n);
            for (Ciudad c : ciudades) {
                if (c.getCiuRegCod().equals(regSel.getRegCod()))
                    ciuComboBox.addItem(c);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar ciudades");
        } finally {
            actualizando = false;
        }
    }

    private void loadClientesInComboBox(JComboBox<Cliente> box) {
        try {
            Cliente n = new Cliente();
            n.setCliCod("Elegir cliente");
            box.addItem(n);
            for (Cliente c : new ClienteDAO().listarTodo()) box.addItem(c);
        } catch (Exception e) {
            System.err.println("Error al cargar clientes en PedidoCabForm");
        }
    }

    private void loadRepVentasInComboBox(JComboBox<RepVenta> box) {
        try {
            RepVenta n = new RepVenta();
            n.setRepVenNom("");
            box.addItem(n);
            for (RepVenta r : new RepVentaDAO().listarTodo()) box.addItem(r);
        } catch (Exception e) {
            System.err.println("Error al cargar rep. ventas en PedidoCabForm");
        }
    }

    private void loadEstadosPedidoInComboBox(JComboBox<EstadoPedido> box) {
        try {
            EstadoPedido n = new EstadoPedido();
            n.setEstPedNom("");
            box.addItem(n);
            for (EstadoPedido e : new EstadoPedidoDAO().listarTodo()) box.addItem(e);
        } catch (Exception e) {
            System.err.println("Error al cargar estados de pedido en PedidoCabForm");
        }
    }

    private void loadRegionesInComboBox(JComboBox<Region> box) {
        try {
            for (Region r : new RegionDAO().listarTodo()) box.addItem(r);
        } catch (Exception e) {
            System.err.println("Error al cargar regiones en PedidoCabForm");
        }
    }
}
