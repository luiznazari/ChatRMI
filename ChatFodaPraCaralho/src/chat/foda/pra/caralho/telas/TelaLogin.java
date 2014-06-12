package chat.foda.pra.caralho.telas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import chat.foda.pra.caralho.clienteServidor.ClienteRmi;
import chat.foda.pra.caralho.modelo.Usuario;
import chat.foda.pra.caralho.modelo.UsuarioLogado;
import classes.Fodas.Pra.Caralho.GridConstraints;

public class TelaLogin extends JFrame{
	
	/**
	 * @author luiznazari
	 */
	private static final long serialVersionUID = 1L;
	
	private JMenuBar jmbMenuBar;
	private JMenu jmnArquivo;
	private JMenuItem jmiSair;
	private JMenu jmnConta;
	private JMenuItem jmiNovaConta;
	private JMenuItem jmiRemoverConta;
	
	private JPanel pnlMain;
	private JPanel pnlLogin;
	private JLabel jlbId;
	private JTextField jtfId;
	private JLabel jlbSenha;
	private JPasswordField jpfSenha;
	private JButton jbtConfirmar;
	
	private JLabel jlbRegistrar;
	private JLabel jlbLink;
	
	private ClienteRmi cliente;
	
	public TelaLogin() {
		super("Chat Foda Pra Caralho");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(getMainPanel());
		setLocationRelativeTo(null);
		setJMenuBar(getMenu());
		setResizable(false);
		setSize(300, 220);
		setVisible(true);
		
		String[] opcoes = {"192.168.1.100","192.168.1.101","192.168.1.102","172.18.33.99"};
		String ip = JOptionPane.showInputDialog(null, "Selecione o IP: ", "Configurar conexão", 
					JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]).toString();
		
		cliente = new ClienteRmi(ip);
	}
	
	public JMenuBar getMenu() {
		jmbMenuBar = new JMenuBar();
		
		jmnArquivo = new JMenu("Arquivo");
		jmbMenuBar.add(jmnArquivo);
		
		jmiSair = new JMenuItem("Sair");
		jmnArquivo.add(jmiSair);
		
		jmnConta = new JMenu("Conta");
		jmbMenuBar.add(jmnConta);
		
		jmiNovaConta = new JMenuItem("Nova");
		jmnConta.add(jmiNovaConta);
		
		jmiRemoverConta = new JMenuItem("Remover");
		jmnConta.add(jmiRemoverConta);
		
		getMenuActions();
		
		return jmbMenuBar;
	}
	
	private void getMenuActions() {
		
		jmiSair.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		jmiNovaConta.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new TelaCadastro(cliente);
			}
		});
		
		jmiRemoverConta.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
	}
	
	private JPanel getMainPanel() {
		pnlMain = new JPanel();
		pnlMain.setLayout(new GridBagLayout());
		
		pnlMain.add(getLogin(), new GridConstraints()
					.setInsets(5).setAnchor(GridConstraints.WEST).setFill(GridBagConstraints.HORIZONTAL).setGridSize(GridConstraints.REMAINDER, 1)
					.setGridWeight(1, 0));
		
		jlbRegistrar = new JLabel("Ainda não possui uma conta?");
		pnlMain.add(jlbRegistrar, new GridConstraints()
				 	.setAnchor(GridConstraints.EAST).setInsets(5, 0, 5, 0).setGridSize(GridConstraints.RELATIVE, 1).setGridWeight(1, 0));
		
		jlbLink = new JLabel("Clique aqui!");
		pnlMain.add(jlbLink, new GridConstraints()
					.setAnchor(GridConstraints.WEST).setInsets(5).setGridSize(GridConstraints.REMAINDER, 1).setGridWeight(1, 0));
		
		actions();
		
		return pnlMain;
	}
	
	private JPanel getLogin() {
		pnlLogin = new JPanel();
		pnlLogin.setLayout(new GridBagLayout());
		pnlLogin.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Login"));
		
		jlbId = new JLabel("ID: ");
		pnlLogin.add(jlbId, new GridConstraints()
					.setInsets(5).setAnchor(GridConstraints.EAST).setFill(GridConstraints.BOTH).setGridSize(GridConstraints.RELATIVE, 1));
		
		jtfId = new JTextField();
		pnlLogin.add(jtfId, new GridConstraints()
					.setInsets(0, 0, 0, 5).setAnchor(GridConstraints.WEST).setFill(GridConstraints.HORIZONTAL).setGridSize(GridConstraints.REMAINDER, 1)
					.setGridWeight(1, 1));
		
		jlbSenha = new JLabel("Senha: ");
		pnlLogin.add(jlbSenha, new GridConstraints()
					.setInsets(5).setAnchor(GridConstraints.WEST).setFill(GridConstraints.BOTH).setGridSize(GridConstraints.RELATIVE, 1));
		
		jpfSenha = new JPasswordField();
		pnlLogin.add(jpfSenha, new GridConstraints()
					.setInsets(5, 0, 5, 5).setAnchor(GridConstraints.WEST).setFill(GridConstraints.HORIZONTAL).setGridSize(GridConstraints.REMAINDER, 1)
					.setGridWeight(1, 1));
		
		jbtConfirmar = new JButton("Confirmar");		
		pnlLogin.add(jbtConfirmar, new GridConstraints()
					.setInsets(5).setAnchor(GridConstraints.EAST).setGridSize(GridConstraints.REMAINDER, 1).setGridLocation(2, 1));
		
		return pnlLogin;
	}

public void actions() {
		
		jbtConfirmar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nome = jtfId.getText();
				String senha = new String(jpfSenha.getPassword());
				if (!nome.isEmpty() && !senha.isEmpty()) {
					try {
						UsuarioLogado usuarioLogado = cliente.getService().login(nome, senha);
						if (usuarioLogado != null) {
							cliente.setUsuarioLogado(usuarioLogado);
							new TelaCliente(cliente);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "Usuário não cadastrado ou senha incorreta!");
						}
					} catch (RemoteException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Conexão - Erro ao realizar login.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Login ou senha inválidos ou vazios!");
				}
			}
		});
		
		jlbLink.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				jlbLink.setForeground(Color.DARK_GRAY);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				jlbLink.setForeground(Color.blue);
				jlbLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new TelaCadastro(cliente);
			}
		});		
	}
	
	public static void main(String[] args) {
		new TelaLogin();
	}
}
