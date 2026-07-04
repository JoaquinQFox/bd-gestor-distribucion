package unsa.bd.modules.seguridad.movusuario;

import unsa.bd.modules.seguridad.accion.AccionDAO;
import unsa.bd.modules.seguridad.usuario.UsuarioDAO;
import unsa.bd.modules.seguridad.accion.Accion;
import unsa.bd.modules.seguridad.usuario.Usuario;
import unsa.bd.commons.utility.BaseForm;
import unsa.bd.commons.utility.FormMode;

import javax.swing.*;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MovUsuarioForm extends BaseForm
{

    private JTextField         codField;
    private JTextField         desField;
    private JTextField         diaField;
    private JTextField         mesField;
    private JTextField         yeaField;
    private JSpinner           horSpinner;
    private JComboBox<Accion>  accComboBox;
    private JComboBox<Usuario> usuComboBox;
    private JTextField         estRegField;

    public MovUsuarioForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "MOVIMIENTO DE USUARIO";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Descripción", "Día", "Mes", "Año", "Hora", "Acción", "Uuario", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {

        // Código
        codField = makeStyledField(10);
        addFieldRowToForm(form, "Código:", codField);

        // Descripción
        desField = makeStyledField(30);
        addFieldRowToForm(form, "Descripción:", desField);

        // Día
        diaField = makeStyledField(5);
        addFieldRowToForm(form, "Día:", diaField);

        // Mes
        mesField = makeStyledField(5);
        addFieldRowToForm(form, "Mes:", mesField);

        // Año
        yeaField = makeStyledField(5);
        addFieldRowToForm(form, "Año:", yeaField);

        // Hora
        horSpinner = makeStyledSpinner();
        addFieldRowToForm(form, "Hora:", horSpinner);

        // Acción
        accComboBox = makeStyledComboBox(Accion::getAccNom);
        loadAccionesInComboBox(accComboBox);
        addFieldRowToForm(form, "Acción:", accComboBox);

        // Usuario
        usuComboBox = makeStyledComboBox(Usuario::getUsuNomUsu);
        loadUsuariosInComboBox(usuComboBox);
        addFieldRowToForm(form, "Usuario:", usuComboBox);

        // Estado de Registro
        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        MovUsuarioDAO dao = new MovUsuarioDAO();
        AccionDAO daoAcc = new AccionDAO();
        UsuarioDAO daoUsu = new UsuarioDAO();

        try {
            List<MovUsuario> movs = dao.listarTodo();
            List<Accion> accions = daoAcc.listarTodo();
            List<Usuario> usuarios = daoUsu.listarTodo();

            for (MovUsuario m : movs) {
                String nomAcc = "";
                for (Accion a : accions) {
                    if (m.getMovUsuAccCod().equals(a.getAccCod())) {
                        nomAcc = a.getAccNom();
                        break;
                    }
                }

                String nomUsu = "";
                for (Usuario u : usuarios) {
                    if (m.getMovUsuUsuCod().equals(u.getUsuCod())) {
                        nomUsu = u.getUsuNomUsu();
                        break;
                    }
                }

                tableModel.addRow(new Object[]{
                        m.getMovUsuCod(),
                        m.getMovUsuDes(),
                        m.getMovUsuDia(),
                        m.getMovUsuMes(),
                        m.getMovUsuYea(),
                        m.getMovUsuHor(),
                        nomAcc,
                        nomUsu,
                        m.getMovUsuEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        MovUsuario m = buildMovUsuarioFromFields();
        if (m.getMovUsuCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!m.getMovUsuCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new MovUsuarioDAO().agregar(m);
    }

    @Override
    protected void onModify() throws Exception {
        new MovUsuarioDAO().modificar(buildMovUsuarioFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new MovUsuarioDAO().eliminar(buildMovUsuarioFromFields().getMovUsuCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new MovUsuarioDAO().inactivar(buildMovUsuarioFromFields().getMovUsuCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new MovUsuarioDAO().reactivar(buildMovUsuarioFromFields().getMovUsuCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        codField.setText(tableModel.getValueAt(row, 0).toString());
        desField.setText(tableModel.getValueAt(row, 1).toString());
        diaField.setText(tableModel.getValueAt(row, 2).toString());
        mesField.setText(tableModel.getValueAt(row, 3).toString());
        yeaField.setText(tableModel.getValueAt(row, 4).toString());
        estRegField.setText(tableModel.getValueAt(row, 8).toString());

        LocalTime horaBD = (LocalTime) tableModel.getValueAt(row, 5);
        if (horaBD != null) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, horaBD.getHour());
            cal.set(Calendar.MINUTE, horaBD.getMinute());
            cal.set(Calendar.SECOND, horaBD.getSecond());
            horSpinner.setValue(cal.getTime());
        }

        String nomAcc = tableModel.getValueAt(row, 6).toString();
        for (int i = 0; i < accComboBox.getItemCount(); i++) {
            if (accComboBox.getItemAt(i).getAccNom().equals(nomAcc)) {
                accComboBox.setSelectedIndex(i);
                break;
            }
        }

        String nomUsu = tableModel.getValueAt(row, 7).toString();
        for (int i = 0; i < usuComboBox.getItemCount(); i++) {
            if (usuComboBox.getItemAt(i).getUsuNomUsu().equals(nomUsu)) {
                usuComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    protected void clearFields() {
        codField.setText("");
        desField.setText("");
        diaField.setText("");
        mesField.setText("");
        yeaField.setText("");
        estRegField.setText("");
        horSpinner.setValue(new Date());
        if (accComboBox.getItemCount() > 0) accComboBox.setSelectedIndex(0);
        if (usuComboBox.getItemCount() > 0) usuComboBox.setSelectedIndex(0);
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(desField, editable);
        setFieldEditable(diaField, editable);
        setFieldEditable(mesField, editable);
        setFieldEditable(yeaField, editable);
        setComboEditable(accComboBox, editable);
        setComboEditable(usuComboBox, editable);
        setSpinnerEditable(horSpinner, editable);

        setFieldEditable(codField, editable && modo.equals(FormMode.ADD));
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
        Object val = tableModel.getValueAt(row, 6);
        return val != null ? val.toString().trim() : "";
    }

    private MovUsuario buildMovUsuarioFromFields() {
        String cod = codField.getText().trim();
        String des = desField.getText().trim();
        String estReg = estRegField.getText().trim();
        int dia = diaField.getText().trim().isEmpty() ? 0 : Integer.parseInt(diaField.getText().trim());
        int mes = mesField.getText().trim().isEmpty() ? 0 : Integer.parseInt(mesField.getText().trim());
        int yea = yeaField.getText().trim().isEmpty() ? 0 : Integer.parseInt(yeaField.getText().trim());

        Date dateVal = (Date) horSpinner.getValue();
        LocalTime soloHora = dateVal.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalTime();

        Accion aSel = (Accion) accComboBox.getSelectedItem();
        String aCod = aSel.getAccCod();

        Usuario uSel = (Usuario) usuComboBox.getSelectedItem();
        String uCod = uSel.getUsuCod();

        return new MovUsuario(cod, uCod, soloHora, des, dia, mes, yea, aCod, estReg);
    }

    private void loadAccionesInComboBox(JComboBox<Accion> box) {
        try {
            Accion n = new Accion();
            n.setAccNom("");
            box.addItem(n);
            for (Accion a : new AccionDAO().listarTodo()) {
                box.addItem(a);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar acciones en MovUsuarioForm");
        }
    }

    private void loadUsuariosInComboBox(JComboBox<Usuario> box) {
        try {
            for (Usuario u : new UsuarioDAO().listarTodo()) {
                box.addItem(u);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar usuarios en MovUsuarioForm");
        }
    }
}