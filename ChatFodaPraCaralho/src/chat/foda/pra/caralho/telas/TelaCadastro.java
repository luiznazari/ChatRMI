package chat.foda.pra.caralho.telas;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import chat.foda.pra.caralho.clienteServidor.ClienteRmi;
import classes.Fodas.Pra.Caralho.GridConstraints;

public class TelaCadastro extends JDialog {
	private static final long serialVersionUID = 8945739412692393477L;
	
	private JPanel pnlMain;
	
	private JLabel jlbNome;
	
	private JTextField jtfNome;
	
	private JLabel jlbSenha;
	
	private JPasswordField jpfSenha;
	
	private JLabel jlbConfirmaSenha;
	
	private JPasswordField jpfConfirmaSenha;
	
	private JLabel jlbSerial;
	
	private JTextField jtfSerial;
	
	private JButton jbtConfirmar;
	
	private JButton jbtCancelar;
	
	private ClienteRmi cliente;
	
	public TelaCadastro(ClienteRmi cliente) {
		this.cliente = cliente;
		setTitle("Cadastrar Conta");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setContentPane(getMain());
		setSize(350, 220);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setVisible(true);
	}
	
	public ClienteRmi getCliente() {
		return this.cliente;
	}
	
	public void setCliente(ClienteRmi cliente) {
		this.cliente = cliente;
	}
	
	public JPanel getMain() {
		pnlMain = new JPanel(new GridBagLayout());
		pnlMain.setBorder(new EmptyBorder(10, 5, 5, 5));
		
		jlbNome = new JLabel("Nome:");
		pnlMain.add(jlbNome, new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5));
		
		jtfNome = new JTextField();
		pnlMain.add(jtfNome,
		        new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(5).setOccupiedSize(GridConstraints.REMAINDER, 1)
		                .setFill(GridConstraints.HORIZONTAL).setGridWeight(1, 0));
		
		jlbSenha = new JLabel("Senha:");
		pnlMain.add(jlbSenha, new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5).setOccupiedSize(1, 1));
		
		jpfSenha = new JPasswordField();
		pnlMain.add(jpfSenha,
		        new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(5).setOccupiedSize(GridConstraints.REMAINDER, 1)
		                .setFill(GridConstraints.HORIZONTAL).setGridWeight(1, 0));
		
		jlbConfirmaSenha = new JLabel("<html><p align:right> Confirmar<br>Senha:");
		pnlMain.add(jlbConfirmaSenha, new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5).setOccupiedSize(1, 1));
		
		jpfConfirmaSenha = new JPasswordField();
		pnlMain.add(jpfConfirmaSenha,
		        new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(5).setOccupiedSize(GridConstraints.REMAINDER, 1)
		                .setFill(GridConstraints.HORIZONTAL).setGridWeight(1, 0));
		
		jlbSerial = new JLabel("Serial:");
		pnlMain.add(jlbSerial, new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5).setOccupiedSize(2, 1));
		
		jtfSerial = new JTextField();
		jtfSerial.setEnabled(false);
		pnlMain.add(jtfSerial,
		        new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(5).setOccupiedSize(GridConstraints.REMAINDER, 1)
		                .setFill(GridConstraints.HORIZONTAL).setGridWeight(1, 0));
		
		JPanel jpnBotoes = new JPanel(new BorderLayout(5, 5));
		
		jbtCancelar = new JButton("Cancelar");
		jpnBotoes.add(jbtCancelar, BorderLayout.WEST);
		
		jbtConfirmar = new JButton("Salvar");
		jbtConfirmar.requestFocus();
		jpnBotoes.add(jbtConfirmar, BorderLayout.EAST);
		
		pnlMain.add(jpnBotoes,
		        new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5).setOccupiedSize(GridConstraints.REMAINDER, 1));
		
		actions();
		
		return pnlMain;
	}
	
	public void actions() {
		
		jbtConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nome = jtfNome.getText();
				String senha = new String(jpfSenha.getPassword());
				String confirmaSenha = new String(jpfConfirmaSenha.getPassword());
				
				if (!nome.isEmpty() && !senha.isEmpty()) {
					if (senha.equals(confirmaSenha)) {
						try {
							if (getCliente().getService().cadastrarUsuario(nome, senha)) {
								JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!");
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, "Nome de usuário já cadastrado!");
							}
							
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Erro ao realizar cadastro.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "As senhas não conferem.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Nome ou senha inválidos ou vazios!");
				}
			}
		});
		
		jbtCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	}
	
}
