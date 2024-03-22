package LocadoraCarros.view.locacao;

import LocadoraCarros.classe.Uteis;
import LocadoraCarros.model.Carro;
import LocadoraCarros.model.Cliente;
import LocadoraCarros.model.DTO.CarroDTO;
import LocadoraCarros.model.LocacaoDTO;
import LocadoraCarros.model.Modelo;
import LocadoraCarros.model.Seguradora;
import LocadoraCarros.services.CarroService;
import LocadoraCarros.services.ClienteService;
import LocadoraCarros.services.LocacaoService;
import LocadoraCarros.services.ModeloService;
import LocadoraCarros.services.SeguradoraService;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class NovaLocacaoView extends javax.swing.JDialog {

    private List<Cliente> vCliente = new ArrayList<>();
    private List<CarroDTO> vCarro = new ArrayList();
    private List<Seguradora> vSeguradora = new ArrayList();
    private LocacaoDTO oLocacao = new LocacaoDTO();

    /**
     * *
     *
     * - Caso altere o valor de desconto - atualizar o valor total - caso altere
     * o valode de acrescimo - atualizar valor total - preencher a data de
     * Locacao, com a data atual - Atualizar a data PREV Devolução com a data
     * atual + qtd Dias (Desafio) - OBS - AS DATAS deve ser no padrão DD/MM/yyyy
     * (ex 01/03/2024)
     */
    public NovaLocacaoView(Frame parent, boolean modal) throws Exception {
        super(parent, modal);
        initComponents();

        //inicializa as mascaras dos campos de data e valor
//        inicializarMascaras();
        lblValorTotal.setText("0,00");
        txtQtdDias.setText("01");
        txtValorDesconto.setText("0");
        txtAcrescimo.setText("0");

        //inicializa os combosbox
        txtDataLocacao.setText(Uteis.getDataAtual());

        inicializarComboBox();
        carregarPlaca();
        calcularValorTotal();
        calcularDataDevolucao();
    }

    private void inicializarMascaras() throws Exception {
        MaskFormatter formatterDecimal = new MaskFormatter("###.##");
        MaskFormatter formatterData = new MaskFormatter("##/##/####");
        MaskFormatter qtdDias = new MaskFormatter("##");

        //formata campos para o tipo de data.
        txtDataLocacao.setFormatterFactory(new DefaultFormatterFactory(formatterData));
        txtDataDevolucao.setFormatterFactory(new DefaultFormatterFactory(formatterData));
        txtDataDevolvida.setFormatterFactory(new DefaultFormatterFactory(formatterData));

        //formata os campos para valores numéricos decimais
        txtValorDesconto.setFormatterFactory(new DefaultFormatterFactory(formatterDecimal));
        txtAcrescimo.setFormatterFactory(new DefaultFormatterFactory(formatterDecimal));

        //formata os campos para valores inteiros
        txtQtdDias.setFormatterFactory(new DefaultFormatterFactory(qtdDias));
    }

    private void inicializarComboBox() throws Exception {
        carregarComboBoxCliente();
        carregarComboBoxCarro();
        carregarComboBoxSeguradora();
    }

    public void carregarComboBoxCliente() throws Exception {
        vCliente = new ClienteService().consultar();

        Object[] valoresModel = new Object[vCliente.size()];

        int i = 0;

        for (Cliente oCliente : vCliente) {
            valoresModel[i] = oCliente.getId() + "-" + oCliente.getNome();

            i++;
        }

        DefaultComboBoxModel model = new DefaultComboBoxModel(valoresModel);

        cboCliente.setModel(model);
    }

    public void carregarComboBoxCarro() throws Exception {
        vCarro = new CarroService().consultarDisponiveis();

        Object[] valoresModel = new Object[vCarro.size()];

        int i = 0;

        for (CarroDTO oCarro : vCarro) {
            valoresModel[i] = oCarro.getFabricante() + "- " + oCarro.getModelo()
                    + ": " + oCarro.getCor();

            i++;
        }

        DefaultComboBoxModel model = new DefaultComboBoxModel(valoresModel);

        cboCarro.setModel(model);
    }

    public void carregarComboBoxSeguradora() throws Exception {
        vSeguradora = new SeguradoraService().consultar();
        vSeguradora.add(new Seguradora(-1L, "SEM SEGURO", 0D));

        Object[] valoresModel = new Object[vSeguradora.size()];

        int i = 0;

        for (Seguradora oSeguradora : vSeguradora) {
            valoresModel[i] = oSeguradora.getNome() + " - " + Uteis.decimal2(oSeguradora.getValor());

            i++;
        }

        DefaultComboBoxModel model = new DefaultComboBoxModel(valoresModel);

        cboSeguradora.setModel(model);
        cboSeguradora.setSelectedIndex(i - 1);
    }

    private void carregarPlaca() throws Exception {
        if (vCarro.isEmpty()) {
            return;
        }

        txtPlaca.setText(vCarro.get(cboCarro.getSelectedIndex()).getPlaca());
    }

    private void calcularValorTotal() throws Exception {
        Double valorCarro = vCarro.get(cboCarro.getSelectedIndex()).getValorLocacao();
        Double valorSeguro = vSeguradora.get(cboSeguradora.getSelectedIndex()).getValor();
        Double valorDesconto = txtValorDesconto.getText().isEmpty() ? 0D : Double.parseDouble(txtValorDesconto.getText().replace(",", "."));
        Double valorAcrescimo = txtAcrescimo.getText().isEmpty() ? 0D : Double.parseDouble(txtAcrescimo.getText().replace(",", "."));

        Integer qtdDias = Integer.valueOf(txtQtdDias.getText().trim());

        Double valorTotal = new LocacaoService().calcularValorTotal(qtdDias, valorCarro, valorSeguro, valorDesconto, valorAcrescimo);

        lblValorTotal.setText(Uteis.decimal2(valorTotal));
    }

    public void locar() throws Exception {
        oLocacao.setCliente(vCliente.get(cboCliente.getSelectedIndex()));
        new LocacaoService().locar(oLocacao);
    }

    public void devolver() throws Exception {

    }

    private void validarCampoDesconto() throws Exception {
        if (txtValorDesconto.getText().isEmpty()) {
            if ((!txtValorDesconto.getText().contains(".") || !txtValorDesconto.getText().contains(",")) && !Uteis.eNumero(txtValorDesconto.getText())) {
                txtValorDesconto.setText("0");
            }
        }
    }

    private void validarCampoAcrescimo() throws Exception {
        if (txtAcrescimo.getText().isEmpty()) {
            if ((!txtAcrescimo.getText().contains(".") || !txtAcrescimo.getText().contains(",")) && !Uteis.eNumero(txtAcrescimo.getText())) {
                txtAcrescimo.setText("0");
            }
        }
    }

    private void validarCampoQtdDias() throws Exception {
        if (txtQtdDias.getText().isEmpty()
                || !Uteis.eNumero(txtQtdDias.getText())) {
            txtQtdDias.setText("01");
        }
    }

    private void calcularDataDevolucao() throws Exception {
        validarCampoQtdDias();

        int qtdDias = Integer.parseInt(txtQtdDias.getText());

        txtDataDevolucao.setText(new LocacaoService().calcularDataDevolcao(qtdDias));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboCliente = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cboCarro = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cboSeguradora = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtDataLocacao = new javax.swing.JFormattedTextField();
        lblValorTotal = new javax.swing.JLabel();
        txtQtdDias = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDataDevolucao = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDataDevolvida = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        txtValorDesconto = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtAcrescimo = new javax.swing.JFormattedTextField();
        txtPlaca = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnLocar = new javax.swing.JButton();
        btnDevolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Cliente");

        jLabel2.setText("Carro");

        cboCarro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCarroActionPerformed(evt);
            }
        });

        jLabel3.setText("Seguradora");

        cboSeguradora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSeguradoraActionPerformed(evt);
            }
        });

        jLabel4.setText("Data Locação");

        txtDataLocacao.setEnabled(false);

        lblValorTotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblValorTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblValorTotal.setText("R$ 99.999,99");
        lblValorTotal.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        txtQtdDias.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQtdDiasFocusLost(evt);
            }
        });

        jLabel6.setText("Quantidade Diarias");

        txtDataDevolucao.setEnabled(false);

        jLabel7.setText("Data Prev. Devolução");

        txtDataDevolvida.setEnabled(false);

        jLabel8.setText("Data Devolução");

        txtValorDesconto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorDescontoFocusLost(evt);
            }
        });

        jLabel9.setText("Desconto (%)");

        jLabel10.setText("Multa");

        txtAcrescimo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAcrescimoFocusLost(evt);
            }
        });

        txtPlaca.setEnabled(false);

        jLabel11.setText("Placa");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDataLocacao, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblValorTotal))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(cboSeguradora, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtQtdDias, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtDataDevolucao)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboCarro, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(txtPlaca)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDataDevolvida, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtValorDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAcrescimo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataLocacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblValorTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboCarro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboSeguradora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDataDevolucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtQtdDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAcrescimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtValorDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDataDevolvida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnLocar.setText("Locar");
        btnLocar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocarActionPerformed(evt);
            }
        });

        btnDevolver.setText("Devolver");
        btnDevolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDevolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDevolver)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLocar)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLocar)
                    .addComponent(btnDevolver))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLocarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocarActionPerformed
        try {
            locar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnLocarActionPerformed

    private void btnDevolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDevolverActionPerformed
        try {
            devolver();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnDevolverActionPerformed

    private void cboCarroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCarroActionPerformed
        try {
            carregarPlaca();
            calcularValorTotal();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_cboCarroActionPerformed

    private void cboSeguradoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSeguradoraActionPerformed
        try {
            calcularValorTotal();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_cboSeguradoraActionPerformed

    private void txtQtdDiasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQtdDiasFocusLost
        try {
            validarCampoQtdDias();
            calcularValorTotal();
            calcularDataDevolucao();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_txtQtdDiasFocusLost

    private void txtValorDescontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorDescontoFocusLost
        try {
            validarCampoDesconto();
            calcularValorTotal();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_txtValorDescontoFocusLost

    private void txtAcrescimoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAcrescimoFocusLost
        try {
            validarCampoAcrescimo();
            calcularValorTotal();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_txtAcrescimoFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDevolver;
    private javax.swing.JButton btnLocar;
    private javax.swing.JComboBox<String> cboCarro;
    private javax.swing.JComboBox<String> cboCliente;
    private javax.swing.JComboBox<String> cboSeguradora;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblValorTotal;
    private javax.swing.JFormattedTextField txtAcrescimo;
    private javax.swing.JFormattedTextField txtDataDevolucao;
    private javax.swing.JFormattedTextField txtDataDevolvida;
    private javax.swing.JFormattedTextField txtDataLocacao;
    private javax.swing.JFormattedTextField txtPlaca;
    private javax.swing.JFormattedTextField txtQtdDias;
    private javax.swing.JFormattedTextField txtValorDesconto;
    // End of variables declaration//GEN-END:variables
}
