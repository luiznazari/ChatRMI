package chat.foda.pra.caralho.telas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.ConnectException;
import java.net.UnknownHostException;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import chat.foda.pra.caralho.clienteServidor.ClienteRmi;
import chat.foda.pra.caralho.models.UsuarioLogado;
import classes.Fodas.Pra.Caralho.GridConstraints;

import com.alee.laf.WebLookAndFeel;

public class TelaLogin extends JFrame {

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

	public TelaLogin() throws UnknownHostException, ConnectException {
		super("Chat Foda Pra Caralho");

		TelaConfiguracao config = new TelaConfiguracao();
		String ip = config.getIp();
		Integer porta = config.getPorta();

		try {
			cliente = new ClienteRmi(ip, porta);
			setContentPane(getMainPanel());
			setJMenuBar(getMenu());
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(400, 220);
			setLocationRelativeTo(null);
			setResizable(false);
			setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro na Conexão!", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
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
				System.exit(0);
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
				String nome = JOptionPane.showInputDialog(null,
						"Realmente deseja remover sua conta?", "Excluir conta");
				try {
					cliente.getService().removerUsuario(nome);
					JOptionPane.showMessageDialog(jmbMenuBar,
							"Conta removida com sucesso!", "Remover Usuário",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (RemoteException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Conexão - Erro ao remover conta.");
				}
			}
		});
	}

	private JPanel getMainPanel() {
		pnlMain = new JPanel(new GridBagLayout());
		pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));

		pnlMain.add(
				getLogin(),
				new GridConstraints().setInsets(5)
						.setAnchor(GridConstraints.WEST)
						.setFill(GridBagConstraints.HORIZONTAL)
						.setOccupiedSize(GridConstraints.REMAINDER, 1)
						.setGridWeight(1, 0));

		jlbRegistrar = new JLabel("Ainda não possui uma conta?");
		pnlMain.add(
				jlbRegistrar,
				new GridConstraints().setAnchor(GridConstraints.EAST)
						.setInsets(5)
						.setOccupiedSize(GridConstraints.RELATIVE, 1)
						.setGridWeight(1, 0));

		jlbLink = new JLabel("Clique aqui!");
		pnlMain.add(jlbLink,
				new GridConstraints().setAnchor(GridConstraints.WEST)
						.setInsets(5).setOccupiedSize(GridConstraints.REMAINDER, 1)
						.setGridWeight(1, 0));

		actions();

		return pnlMain;
	}

	private JPanel getLogin() {
		pnlLogin = new JPanel();
		pnlLogin.setLayout(new GridBagLayout());
		pnlLogin.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), "Login"));

		jlbId = new JLabel("ID:");
		pnlLogin.add(jlbId,
				new GridConstraints().setAnchor(GridConstraints.EAST)
				.setInsets(5));

		jtfId = new JTextField();
		pnlLogin.add(
				jtfId,
				new GridConstraints().setInsets(5)
						.setAnchor(GridConstraints.WEST)
						.setFill(GridConstraints.HORIZONTAL)
						.setOccupiedSize(GridConstraints.REMAINDER, 1)
						.setGridWeight(1, 0));

		jlbSenha = new JLabel("Senha:");
		pnlLogin.add(
				jlbSenha,new GridConstraints().setAnchor(GridConstraints.EAST)
				.setInsets(5));

		jpfSenha = new JPasswordField();
		pnlLogin.add(
				jpfSenha,
				new GridConstraints().setInsets(5)
						.setAnchor(GridConstraints.WEST)
						.setFill(GridConstraints.HORIZONTAL)
						.setOccupiedSize(GridConstraints.REMAINDER, 1)
						.setGridWeight(1, 0));

		jbtConfirmar = new JButton("Confirmar");
		pnlLogin.add(
				jbtConfirmar,
				new GridConstraints().setInsets(5)
						.setAnchor(GridConstraints.EAST)
						.setOccupiedSize(GridConstraints.REMAINDER, 1));

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
						UsuarioLogado usuarioLogado = cliente
								.getService()
								.login(cliente.getClienteService(), nome, senha);

						if (usuarioLogado != null) {
							cliente.setUsuarioLogado(usuarioLogado);
							new TelaCliente(cliente);
							dispose();
						} else {
							JOptionPane
									.showMessageDialog(null,
											"Usuário não cadastrado ou senha incorreta!");
						}
					} catch (RemoteException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Conexão - Erro ao realizar login.");
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Login ou senha inválidos ou vazios!");
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
				jlbLink.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				new TelaCadastro(cliente);
			}
		});
	}

	public static void main(String[] args) throws UnknownHostException, ConnectException {
		try {
			UIManager.setLookAndFeel(new WebLookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		new TelaLogin();
	}
}
