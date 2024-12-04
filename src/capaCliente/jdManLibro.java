/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.ValidationManager;
import capaLogica.clsAutor;
import capaLogica.clsCategoria;
import capaLogica.clsEditorial;
import capaLogica.clsFormato;
import capaLogica.clsIdioma;
import capaLogica.clsLibro;
import capaLogica.clsTipoLibro;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Edgar
 */
public class jdManLibro extends javax.swing.JDialog {

    /**
     * Creates new form jdManLibro
     */
    public static ArrayList listaDeIdCategorias = new ArrayList();
    public static ArrayList listaDeIdAutores = new ArrayList();
    clsEditorial objEditorial = new clsEditorial();
    clsIdioma objIdioma = new clsIdioma();
    clsFormato objFormato = new clsFormato();
    clsTipoLibro objTipoLibro = new clsTipoLibro();
    clsLibro objLibro = new clsLibro();
    public static clsCategoria objCategoria = new clsCategoria();
    public static clsAutor objAutor = new clsAutor();

    /**
     * Creates new form jdManLibro
     */
    private void llenarCajas(String ISBN) {

        ResultSet rsLibro = null;
        ResultSet rs_autores = null;
        ResultSet rs_categorias = null;
        limpiar();
        try {
            rsLibro = objLibro.listarLibro(ISBN);
            rs_autores = objLibro.buscarCodigosAutoresPorISBN(ISBN);
            rs_categorias = objLibro.buscarCodigosCategoriasPorISBN(ISBN);

            if (rsLibro.next()) {
                txtISBN.setText(ISBN);
                cmbEditorial.setSelectedItem(rsLibro.getString("editorial"));
                txtNombre.setText(rsLibro.getString("libro_nombre"));
                cmbIdioma.setSelectedItem(rsLibro.getString("idioma"));
                spnEdicion.setValue(rsLibro.getInt("edicion"));
                while (rs_autores.next()) {
                    agregarAutor(rs_autores.getInt("autorcodigo"));
                }

                while (rs_categorias.next()) {
                    agregarCategoria(rs_categorias.getInt("cod_categoria"));
                }
                txtNumPags.setText(rsLibro.getString("num_paginas"));
                cmbFormato.setSelectedItem(rsLibro.getString("formato"));
                cmbTipoLibro.setSelectedItem(rsLibro.getString("tipo"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
//        rsLibro.getString("autores");
//        rsLibro.getString("categorias");
    }

    private void limpiar() {
        listaDeIdAutores.clear();
        listaDeIdCategorias.clear();
        txtISBN.setText("");
        txtNombre.setText("");
        cmbEditorial.setSelectedIndex(0);
        cmbFormato.setSelectedIndex(0);
        cmbIdioma.setSelectedIndex(0);
        cmbTipoLibro.setSelectedIndex(0);
        listAutores.setModel(new DefaultListModel());
        listCategorias.setModel(new DefaultListModel());
        spnEdicion.setValue(0);
        txtNumPags.setText("");
    }

    public void listarTabla() {
        ResultSet rsLibro = null;
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        modelo.addColumn("ISBN");
        modelo.addColumn("Editorial");
        modelo.addColumn("Nombre");
        modelo.addColumn("N° de paginas");
        modelo.addColumn("Edicion");
        modelo.addColumn("Tipo");
        modelo.addColumn("Formato");
        modelo.addColumn("Idioma");
        modelo.addColumn("Autores");
        modelo.addColumn("Categorias");

        try {
            rsLibro = objLibro.listarLibro();
            while (rsLibro.next()) {
                modelo.addRow(new Object[]{rsLibro.getString("isbn"),
                    rsLibro.getString("editorial"),
                    rsLibro.getString("libro_nombre"),
                    rsLibro.getString("num_paginas"),
                    rsLibro.getString("edicion"),
                    rsLibro.getString("tipo"),
                    rsLibro.getString("formato"),
                    rsLibro.getString("idioma"),
                    rsLibro.getString("autores"),
                    rsLibro.getString("categorias")});
            }
            tblDatos.setModel(modelo);
            tblDatos.getTableHeader().setReorderingAllowed(false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error al listar en tabla" + e.getMessage());
        }
    }

    public static String agregarCategoria(int id) throws Exception {
        DefaultListModel modelo = (DefaultListModel) listCategorias.getModel();
        ResultSet rs_listaCat = objCategoria.buscarCategoria(id);
        if (rs_listaCat.next()) {
            if (jdManLibro.listaDeIdCategorias.contains(id)) {
                return "repetido";
            } else {
                jdManLibro.listaDeIdCategorias.add(id);
                modelo.addElement(rs_listaCat.getString("nombre"));
                listCategorias.setModel(modelo);
                return "agregado";
            }
        }
        return "vacio";
    }

    public static String agregarAutor(int id) throws Exception {
        DefaultListModel modelo = (DefaultListModel) listAutores.getModel();
        ResultSet rs_listaAut = objAutor.buscarAutor(id);
        if (rs_listaAut.next()) {
            if (jdManLibro.listaDeIdAutores.contains(id)) {
                return "repetido";
            } else {
                jdManLibro.listaDeIdAutores.add(id);
                modelo.addElement(rs_listaAut.getString("nombre"));
                listAutores.setModel(modelo);
                return "agregado";
            }
        }
        return "vacio";
    }

    private void combearEditorial() {
        ResultSet rsEditorial = null;
        DefaultComboBoxModel modeloEditorial = new DefaultComboBoxModel();
        cmbEditorial.setModel(modeloEditorial);
        try {
            rsEditorial = objEditorial.listarEditorial();
            while (rsEditorial.next()) {
                modeloEditorial.addElement(rsEditorial.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void combearIdioma() {
        ResultSet rsIdioma = null;
        DefaultComboBoxModel modeloIdioma = new DefaultComboBoxModel();
        cmbIdioma.setModel(modeloIdioma);
        try {
            rsIdioma = objIdioma.listarIdiomas();
            while (rsIdioma.next()) {
                modeloIdioma.addElement(rsIdioma.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void combearFormato() {
        ResultSet rsFormato = null;
        DefaultComboBoxModel modeloFormato = new DefaultComboBoxModel();
        cmbFormato.setModel(modeloFormato);
        try {
            rsFormato = objFormato.listarFormato();
            while (rsFormato.next()) {
                modeloFormato.addElement(rsFormato.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void combearTipoLibro() {
        ResultSet rsTipoLibro = null;
        DefaultComboBoxModel modeloFormato = new DefaultComboBoxModel();
        cmbTipoLibro.setModel(modeloFormato);
        try {
            rsTipoLibro = objTipoLibro.listarTipoLibro();
            while (rsTipoLibro.next()) {
                modeloFormato.addElement(rsTipoLibro.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public jdManLibro(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        listarTabla();
        combearEditorial();
        combearFormato();
        combearIdioma();
        combearTipoLibro();
        ValidationManager.setImageInLabel(lblImagen, "libro_label.png");
        DefaultListModel modelo = new DefaultListModel();
        listCategorias.setModel(modelo);
        DefaultListModel modelo2 = new DefaultListModel();
        listAutores.setModel(modelo2);
        setTitle("Menú - Mantenimiento Libro");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listCategorias = new javax.swing.JList<>();
        btnAgregarAutores = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        listAutores = new javax.swing.JList<>();
        btnEliminarAutores = new javax.swing.JButton();
        btnAgregarCategorias = new javax.swing.JButton();
        btnEliminarCategorias = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbTipoLibro = new javax.swing.JComboBox<>();
        txtNumPags = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        txtISBN = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNombre = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        cmbEditorial = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        cmbIdioma = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        spnEdicion = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        cmbFormato = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JSeparator();
        lblImagen = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(243, 226, 210));

        jPanel3.setBackground(new java.awt.Color(243, 226, 210));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setText("Categorias:");

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setText("Autores:");

        listCategorias.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        listCategorias.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jScrollPane3.setViewportView(listCategorias);

        btnAgregarAutores.setBackground(new java.awt.Color(243, 226, 210));
        btnAgregarAutores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add_icon.png"))); // NOI18N
        btnAgregarAutores.setBorder(null);
        btnAgregarAutores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarAutoresActionPerformed(evt);
            }
        });

        listAutores.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        listAutores.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(listAutores);

        btnEliminarAutores.setBackground(new java.awt.Color(243, 226, 210));
        btnEliminarAutores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/remove_icon.png"))); // NOI18N
        btnEliminarAutores.setBorder(null);
        btnEliminarAutores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarAutoresActionPerformed(evt);
            }
        });

        btnAgregarCategorias.setBackground(new java.awt.Color(243, 226, 210));
        btnAgregarCategorias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add_icon.png"))); // NOI18N
        btnAgregarCategorias.setBorder(null);
        btnAgregarCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCategoriasActionPerformed(evt);
            }
        });

        btnEliminarCategorias.setBackground(new java.awt.Color(243, 226, 210));
        btnEliminarCategorias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/remove_icon.png"))); // NOI18N
        btnEliminarCategorias.setBorder(null);
        btnEliminarCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCategoriasActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setText("Tipo de libro:");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Numero de paginas");

        cmbTipoLibro.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cmbTipoLibro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbTipoLibro.setBorder(null);

        txtNumPags.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtNumPags.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtNumPags.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumPagsKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel12)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEliminarAutores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAgregarAutores)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(btnEliminarCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnAgregarCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(12, 12, 12))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbTipoLibro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNumPags))))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAgregarAutores, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminarAutores, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAgregarCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarCategorias))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNumPags, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTipoLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(243, 226, 210));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 353, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 448, Short.MAX_VALUE)
        );

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel2.setBackground(new java.awt.Color(243, 226, 210));

        txtISBN.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtISBN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtISBN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtISBNActionPerformed(evt);
            }
        });
        txtISBN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtISBNKeyTyped(evt);
            }
        });

        txtNombre.setColumns(20);
        txtNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtNombre.setLineWrap(true);
        txtNombre.setRows(5);
        txtNombre.setWrapStyleWord(true);
        txtNombre.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setViewportView(txtNombre);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Editorial:");

        cmbEditorial.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cmbEditorial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEditorial.setBorder(null);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("ISBN:");

        btnBuscar.setBackground(new java.awt.Color(113, 49, 18));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/buscar_24px.png"))); // NOI18N
        btnBuscar.setBorder(null);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setText("Idioma:");

        cmbIdioma.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cmbIdioma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbIdioma.setBorder(null);

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setText("Edicion:");

        spnEdicion.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        spnEdicion.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        JFormattedTextField txtField = ((JSpinner.DefaultEditor) spnEdicion.getEditor()).getTextField();
        txtField.setEditable(false);

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setText("Formato:");

        cmbFormato.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cmbFormato.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFormato.setBorder(null);
        cmbFormato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFormatoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spnEdicion)
                            .addComponent(cmbFormato, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(cmbIdioma, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbEditorial, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel4)
                        .addGap(63, 63, 63)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cmbIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(spnEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cmbFormato, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel1.setBackground(new java.awt.Color(255, 173, 133));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setText("MANTENIMIENTO DE LIBRO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1)))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(251, 230, 211));

        btnNuevo.setBackground(new java.awt.Color(113, 49, 18));
        btnNuevo.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/nuevo_32px.png"))); // NOI18N
        btnNuevo.setText(" NUEVO");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(113, 49, 18));
        btnModificar.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/editar_32px.png"))); // NOI18N
        btnModificar.setText(" MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(113, 49, 18));
        btnEliminar.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar_32px.png"))); // NOI18N
        btnEliminar.setText(" ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(113, 49, 18));
        btnLimpiar.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/limpiar_32px.png"))); // NOI18N
        btnLimpiar.setText(" LIMPIAR");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(113, 49, 18));
        btnSalir.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/salir_32px.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        tblDatos.setBackground(new java.awt.Color(245, 224, 206));
        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblDatos);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 776, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1066, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbFormatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFormatoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbFormatoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtISBNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtISBNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtISBNActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        ResultSet rsGuardar = null;
        ResultSet rs_edit = null;
        ResultSet rs_idioma = null;
        ResultSet rs_format = null;
        ResultSet rs_tipo = null;
        try {
            if (btnNuevo.getText().equalsIgnoreCase(" nuevo")) {
                limpiar();
                btnNuevo.setText(" Guardar");
            } else if (btnNuevo.getText().equalsIgnoreCase(" guardar")) {
                rs_edit = objEditorial.buscarEditorialPorNombre(cmbEditorial.getSelectedItem().toString());
                rs_format = objFormato.buscarFormatoPorNombre(cmbFormato.getSelectedItem().toString());
                rs_tipo = objTipoLibro.buscarTipoLibroPorNombre(cmbTipoLibro.getSelectedItem().toString());
                rs_idioma = objIdioma.buscarIdiomaPorNombre(cmbIdioma.getSelectedItem().toString());
                if (rs_edit.next() && rs_idioma.next() && rs_format.next() && rs_tipo.next()) {
                    rsGuardar = objLibro.registrarLibro(txtISBN.getText(),
                            rs_edit.getInt("codigo"),
                            txtNombre.getText(), Integer.valueOf(txtNumPags.getText()), (int) spnEdicion.getValue(),
                            rs_format.getInt("codigo"), rs_tipo.getInt("codigo"), rs_idioma.getInt("codigo"), listaDeIdAutores, listaDeIdCategorias);;
                    if (rsGuardar.next()) {
                        switch (rsGuardar.getInt("resultado")) {
                            case 0:
                                JOptionPane.showMessageDialog(this, "Libro registrado con exito");
                                limpiar();
                                listarTabla();
                                break;
                            case -1:
                                JOptionPane.showMessageDialog(this, "Error del servidor al registrar el libro");
                                break;
                            default:
                                JOptionPane.showMessageDialog(this, "Libro no registrado");
                                break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar los combos");
                }
                limpiar();
                btnNuevo.setText(" Nuevo");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar el libro el libro: " + e.getMessage());
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnAgregarCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCategoriasActionPerformed
        // TODO add your handling code here:
        jdConsultarCategorias consultarCategorias = new jdConsultarCategorias(new FrmMenuPrincipal(), true);
        consultarCategorias.setVisible(true);
    }//GEN-LAST:event_btnAgregarCategoriasActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        ResultSet rs_resultado = null;
        try {
            int opc = JOptionPane.showConfirmDialog(this, "¿Deseas eliminar el libro?", "Confirmacion", JOptionPane.OK_OPTION);
            if (opc == 0) {
                if (!txtISBN.getText().isBlank()) {
                    rs_resultado = objLibro.eliminarLibro(txtISBN.getText());
                    if (rs_resultado.next()) {
                        switch (rs_resultado.getInt("resultado")) {
                            case 0:
                                JOptionPane.showMessageDialog(this, "Libro eliminado con exito");
                                limpiar();
                                listarTabla();
                                break;
                            case -1:
                                JOptionPane.showMessageDialog(this, "Error del servidor al eliminar el libro");
                                break;
                            default:
                                JOptionPane.showMessageDialog(this, "Libro no elimiando");
                                break;
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Debe ingresar un código ISBN");
                }
            }
            // TODO add your handling code here:
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        ResultSet rsEditar = null;
        ResultSet rs_edit = null;
        ResultSet rs_idioma = null;
        ResultSet rs_format = null;
        ResultSet rs_tipo = null;
        try {
            int opc = JOptionPane.showConfirmDialog(this, "¿Deseas editar el libro?", "Confirmacion", JOptionPane.OK_OPTION);
            if (opc == 0) {
                rs_edit = objEditorial.buscarEditorialPorNombre(cmbEditorial.getSelectedItem().toString());
                rs_format = objFormato.buscarFormatoPorNombre(cmbFormato.getSelectedItem().toString());
                rs_tipo = objTipoLibro.buscarTipoLibroPorNombre(cmbTipoLibro.getSelectedItem().toString());
                rs_idioma = objIdioma.buscarIdiomaPorNombre(cmbIdioma.getSelectedItem().toString());
                if (rs_edit.next() && rs_idioma.next() && rs_format.next() && rs_tipo.next()) {
                    for (Object categoria : listaDeIdCategorias) {
                        System.out.println(categoria);
                    }
                    rsEditar = objLibro.actualizarLibro(txtISBN.getText(),
                            rs_edit.getInt("codigo"),
                            txtNombre.getText(), Integer.valueOf(txtNumPags.getText()), (int) spnEdicion.getValue(),
                            rs_format.getInt("codigo"), rs_tipo.getInt("codigo"), rs_idioma.getInt("codigo"), listaDeIdAutores, listaDeIdCategorias);
                    
                    if (rsEditar.next()) {
                        switch (rsEditar.getInt("resultado")) {
                            case 0:
                                JOptionPane.showMessageDialog(this, "Libro editado con exito");
                                limpiar();
                                listarTabla();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error al updatear los combos");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al editar el libro: " + e.getMessage());
        }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        llenarCajas(txtISBN.getText());
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatosMouseClicked
        // TODO add your handling code here:
        txtISBN.setText(String.valueOf(tblDatos.getValueAt(tblDatos.getSelectedRow(), 0)));
        btnBuscarActionPerformed(null);
    }//GEN-LAST:event_tblDatosMouseClicked

    private void btnEliminarAutoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarAutoresActionPerformed
        // TODO add your handling code here:
        int selectedIndex = listAutores.getSelectedIndex();
        if (selectedIndex != -1) { // Verifica que hay un elemento seleccionado
            listaDeIdAutores.remove(selectedIndex); // Remueve de la lista de IDs
            DefaultListModel modelo = (DefaultListModel) listAutores.getModel();
            modelo.remove(selectedIndex); // Remueve del modelo de la lista
        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un autor para eliminar.");
        }
    }//GEN-LAST:event_btnEliminarAutoresActionPerformed

    private void btnEliminarCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCategoriasActionPerformed
        // TODO add your handling code here:
        int selectedIndex = listCategorias.getSelectedIndex();
        if (selectedIndex != -1) { // Verifica que hay un elemento seleccionado
            listaDeIdCategorias.remove(selectedIndex); // Remueve de la lista de IDs
            DefaultListModel modelo = (DefaultListModel) listCategorias.getModel();
            modelo.remove(selectedIndex); // Remueve del modelo de la lista
        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecciona una categoría para eliminar.");
        }
    }//GEN-LAST:event_btnEliminarCategoriasActionPerformed

    private void btnAgregarAutoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarAutoresActionPerformed
        // TODO add your handling code here:
        jdConsultarAutores consultarAutores = new jdConsultarAutores(new FrmMenuPrincipal(), true);
        consultarAutores.setVisible(true);
    }//GEN-LAST:event_btnAgregarAutoresActionPerformed

    private void txtISBNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtISBNKeyTyped
        // TODO add your handling code here:
        if (txtISBN.getText().length() > 12) {
            evt.consume();
        }
        
        Character objTecla = evt.getKeyChar();
        if (!Character.isDigit(objTecla)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtISBNKeyTyped

    private void txtNumPagsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumPagsKeyTyped
        // TODO add your handling code here:
        Character objTecla = evt.getKeyChar();
        if (!Character.isDigit(objTecla)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumPagsKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarAutores;
    private javax.swing.JButton btnAgregarCategorias;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarAutores;
    private javax.swing.JButton btnEliminarCategorias;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cmbEditorial;
    private javax.swing.JComboBox<String> cmbFormato;
    private javax.swing.JComboBox<String> cmbIdioma;
    private javax.swing.JComboBox<String> cmbTipoLibro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblImagen;
    public static javax.swing.JList<String> listAutores;
    public static javax.swing.JList<String> listCategorias;
    private javax.swing.JSpinner spnEdicion;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextArea txtNombre;
    private javax.swing.JTextField txtNumPags;
    // End of variables declaration//GEN-END:variables
}
