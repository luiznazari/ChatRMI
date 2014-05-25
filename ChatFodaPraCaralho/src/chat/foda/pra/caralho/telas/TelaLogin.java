package chat.foda.pra.caralho.telas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

import chat.foda.pra.caralho.clienteServidor.ClienteOffLine;
import chat.foda.pra.caralho.modelo.Usuario;
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
	private JButton jbtEnter;
	
	private JLabel jlbRegistrar;
	private JLabel jlbLink;
	
	public TelaLogin() {
		super("Chat Foda Pra Caralho");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(getMainPanel());
		setLocationRelativeTo(null);
		setJMenuBar(getMenu());
		setResizable(false);
		setSize(300, 220);
		setVisible(true);
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
				new TelaCadastro();
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
	
	public void actions() {
		
		jbtEnter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!jtfId.getText().isEmpty()) {
					new TelaCliente(new ClienteOffLine(new Usuario(jtfId.getText(), jpfSenha.getPassword().toString())));
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Preencha o campo de login!");
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
				new TelaCadastro();
			}
		});		
	}
	
	private JPanel getLogin() {
		pnlLogin = new JPanel();
		pnlLogin.setLayout(new GridBagLayout());
		pnlLogin.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Login"));
		
		jlbId = new JLabel("ID: ");
		pnlLogin.add(jlbId, new GridConstraints()
					.setInsets(5).setAnchor(GridConstraints.WEST).setFill(GridConstraints.BOTH).setGridSize(GridConstraints.RELATIVE, 1));
		
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
		
		jbtEnter = new JButton("Confirmar");		
		pnlLogin.add(jbtEnter, new GridConstraints()
					.setInsets(5).setAnchor(GridConstraints.EAST).setGridSize(GridConstraints.REMAINDER, 1).setGridLocation(2, 1));
		
		return pnlLogin;
	}

	public static void main(String[] args) {
		new TelaLogin();
	}
}
