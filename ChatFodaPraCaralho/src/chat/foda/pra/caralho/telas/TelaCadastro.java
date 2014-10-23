package chat.foda.pra.caralho.telas;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import chat.foda.pra.caralho.clienteServidor.ClienteRmi;
import chat.foda.pra.caralho.models.Pessoa;
import chat.foda.pra.caralho.models.Usuario;
import classes.Fodas.Pra.Caralho.GridConstraints;

public class TelaCadastro extends JDialog {
	private static final long serialVersionUID = 8945739412692393477L;
	
	private JPanel jpnMain;
	
	private JPanel jpnPessoa;
	
	private JPanel jpnUsuario;
	
	private JLabel jlbNome;
	
	private JTextField jtfNome;
	
	private JLabel jlbDataNasc;
	
	private JFormattedTextField jffDataNasc;
	
	private JLabel jlbSenha;
	
	private JPasswordField jpfSenha;
	
	private JLabel jlbConfirmaSenha;
	
	private JPasswordField jpfConfirmaSenha;
	
	private JLabel jlbEmail;
	
	private JTextField jtfEmail;
	
	private JButton jbtConfirmar;
	
	private JButton jbtCancelar;
	
	private ClienteRmi cliente;
	
	private boolean enableFieldNome;
	
	private boolean enableFieldData;
	
	private boolean enableFieldEmail;
	
	private boolean telaSalvar;
	
	private Usuario user;
	
	public TelaCadastro(ClienteRmi cliente) {
		this.cliente = cliente;
		
		this.enableFieldNome = true;
		this.enableFieldData = true;
		this.enableFieldEmail = true;
		this.telaSalvar = true;
		
		setTitle("Cadastrar Conta");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(getMain());
		setSize(400, 331);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setVisible(true);
	}
	
	public TelaCadastro(ClienteRmi cliente, boolean enableFieldNome, boolean enableFieldData, boolean enableFieldEmail) {
		this.cliente = cliente;
		this.user = getCliente().getUsuarioLogado().getUsuario();
		
		this.enableFieldNome = enableFieldNome;
		this.enableFieldData = enableFieldData;
		this.enableFieldEmail = enableFieldEmail;
		this.telaSalvar = false;
		
		setTitle("Alterar Conta");
		setContentPane(getMain());
		
		jtfNome.setText(getUser().getPessoa().getNomeCompleto());
		jffDataNasc.setText(getUser().getPessoa().getDataNascimento().toString("dd/MM/yyyy"));
		jtfEmail.setText(getUser().getEmail());
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(400, 331);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setVisible(true);
	}
	
	public JPanel getUserPane() {
		jpnUsuario = new JPanel(new GridBagLayout());
		jpnUsuario.setBorder(new TitledBorder("Dados do Usuário"));
		
		jlbEmail = new JLabel("E-mail: *");
		jpnUsuario.add(jlbEmail, new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5));
		
		jtfEmail = new JTextField();
		jtfEmail.setEnabled(enableFieldEmail);
		jpnUsuario.add(jtfEmail,
		        new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(5).setOccupiedSize(GridConstraints.REMAINDER, 1)
		                .setFill(GridConstraints.HORIZONTAL).setGridWeight(1, 0));
		
		jlbSenha = new JLabel("Senha: *");
		jpnUsuario.add(jlbSenha, new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5).setOccupiedSize(1, 1));
		
		jpfSenha = new JPasswordField();
		jpnUsuario.add(jpfSenha,
		        new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(5).setOccupiedSize(GridConstraints.REMAINDER, 1)
		                .setFill(GridConstraints.HORIZONTAL).setGridWeight(1, 0));
		
		jlbConfirmaSenha = new JLabel("<html><p align:right>Confirmar<br>Senha: *");
		jpnUsuario
		        .add(jlbConfirmaSenha, new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5).setOccupiedSize(1, 1));
		
		jpfConfirmaSenha = new JPasswordField();
		jpnUsuario.add(jpfConfirmaSenha,
		        new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(5).setOccupiedSize(GridConstraints.REMAINDER, 1)
		                .setFill(GridConstraints.HORIZONTAL).setGridWeight(1, 0));
		
		return jpnUsuario;
	}
	
	public JPanel getPersonPane() {
		jpnPessoa = new JPanel(new GridBagLayout());
		jpnPessoa.setBorder(new TitledBorder("Dados Pessoais"));
		
		jlbNome = new JLabel("Nome: *");
		jpnPessoa.add(jlbNome, new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5));
		
		jtfNome = new JTextField();
		jtfNome.setEnabled(enableFieldNome);
		jpnPessoa.add(jtfNome,
		        new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(5).setOccupiedSize(GridConstraints.REMAINDER, 1)
		                .setFill(GridConstraints.HORIZONTAL).setGridWeight(1, 0));
		
		jlbDataNasc = new JLabel("<html><p align:right> Data de <br>Nascimento:");
		jpnPessoa.add(jlbDataNasc, new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5));
		
		try {
			jffDataNasc = new JFormattedTextField(new MaskFormatter("##/##/####"));
			jffDataNasc.setEnabled(enableFieldData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		jpnPessoa.add(jffDataNasc,
		        new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(5).setOccupiedSize(GridConstraints.REMAINDER, 1)
		                .setFill(GridConstraints.HORIZONTAL).setGridWeight(1, 0));
		
		return jpnPessoa;
	}
	
	public JPanel getMain() {
		jpnMain = new JPanel(new GridBagLayout());
		jpnMain.setBorder(new EmptyBorder(10, 5, 5, 5));
		
		jpnMain.add(this.getPersonPane(),
		        new GridConstraints().setInsets(5).setAnchor(GridConstraints.CENTER).setFill(GridConstraints.HORIZONTAL)
		                .setOccupiedSize(GridConstraints.REMAINDER, 1).setGridWeight(1, 0));
		
		jpnMain.add(this.getUserPane(),
		        new GridConstraints().setInsets(5).setAnchor(GridConstraints.CENTER).setFill(GridConstraints.HORIZONTAL)
		                .setOccupiedSize(GridConstraints.REMAINDER, 1).setGridWeight(1, 0));
		
		JPanel jpnBotoes = new JPanel(new BorderLayout(5, 5));
		
		jbtCancelar = new JButton("Cancelar");
		jpnBotoes.add(jbtCancelar, BorderLayout.WEST);
		
		jbtConfirmar = new JButton("Salvar");
		jbtConfirmar.requestFocus();
		jpnBotoes.add(jbtConfirmar, BorderLayout.EAST);
		
		jpnMain.add(jpnBotoes,
		        new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5).setOccupiedSize(GridConstraints.REMAINDER, 1));
		
		actions();
		
		return jpnMain;
	}
	
	public void salvar() {
		String nome = jtfNome.getText();
		String senha = new String(jpfSenha.getPassword());
		String confirmaSenha = new String(jpfConfirmaSenha.getPassword());
		String email = jtfEmail.getText();
		
		String dataNasc = null;
		if (jffDataNasc.isEditValid()) {
			dataNasc = jffDataNasc.getText();
		}
		
		if (!nome.isEmpty() && !senha.isEmpty() && !email.isEmpty()) {
			if (senha.equals(confirmaSenha)) {
				try {
					Pessoa pessoa = new Pessoa();
					pessoa.setNomeCompleto(nome);
					if (dataNasc != null) {
						pessoa.setDataNascimento(LocalDate.parse(dataNasc, DateTimeFormat.forPattern("dd/MM/yyyy")));
					}
					
					Usuario usuario = new Usuario();
					usuario.setEmail(email);
					usuario.setSenha(senha);
					usuario.setPessoa(pessoa);
					
					if (getCliente().getService().cadastrarUsuario(usuario)) {
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
			JOptionPane.showMessageDialog(null, "Campos nome, senha ou e-mail podem estarem vazios ou serem inválidos!");
		}
		
	}
	
	public void alterar() {
		String senha = new String(jpfSenha.getPassword());
		String confirmaSenha = new String(jpfConfirmaSenha.getPassword());
		Long codigo = getUser().getCodigo();
		
		if (!senha.isEmpty() && !confirmaSenha.isEmpty()) {
			if (senha.equals(confirmaSenha)) {
				try {
					getCliente().getService().trocarSenha(codigo, senha);
					JOptionPane.showMessageDialog(null, "Alteração efetuada com sucesso!");
					dispose();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "Erro ao alterar o cadastro!");
				}
			} else {
				JOptionPane.showMessageDialog(this, "As senhas não conferem.");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Campos de senha estão vazios!");
		}
	}
	
	public void actions() {
		
		jbtConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (telaSalvar) {
					salvar();
				} else {
					alterar();
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
	
	public ClienteRmi getCliente() {
		return this.cliente;
	}
	
	public void setCliente(ClienteRmi cliente) {
		this.cliente = cliente;
	}
	
	public Usuario getUser() {
		return user;
	}
	
	public void setUser(Usuario user) {
		this.user = user;
	}
	
}
