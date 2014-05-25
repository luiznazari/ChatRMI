package chat.foda.pra.caralho.telas;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import classes.Fodas.Pra.Caralho.GridConstraints;

import chat.foda.pra.caralho.clienteServidor.ClienteOffLine;
import chat.foda.pra.caralho.clienteServidor.ServidorOffLine;
import chat.foda.pra.caralho.modelo.Usuario;

public class TelaServidor extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel pnlMain;
	private JLabel jlbConexoes;
	private JButton jbtAtualizar;
	private JLabel jlbNome;
	private JTextField jtfNome;
	private JLabel jlbSenha;
	private JPasswordField jpfSenha;
	private JButton jbtNovoCliente;
	
	private ServidorOffLine servidor;
	
	public TelaServidor(ServidorOffLine server) {
		super("Servidor OffLine");		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setContentPane(getMain());
		setVisible(true);
		setSize(300, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		
		this.servidor = server;
	}
	
	private JPanel getMain() {
		pnlMain = new JPanel();
		pnlMain.setLayout(new GridBagLayout());
		
		jlbConexoes = new JLabel("Total de conexões: " + 0);
		pnlMain.add(jlbConexoes, new GridConstraints()
				.setAnchor(GridConstraints.WEST).setInsets(5).setGridSize(2, 1));
		
		jbtAtualizar = new JButton("Atualizar");
		pnlMain.add(jbtAtualizar, new GridConstraints()
				.setAnchor(GridConstraints.EAST).setInsets(5).setGridSize(GridConstraints.REMAINDER, 1));
		
		jlbNome = new JLabel("Nome:");
		pnlMain.add(jlbNome, new GridConstraints()
					.setAnchor(GridConstraints.WEST).setInsets(5).setGridSize(1, 1));
		
		jtfNome = new JTextField();
		pnlMain.add(jtfNome, new GridConstraints()
					.setAnchor(GridConstraints.WEST).setInsets(5).setGridSize(GridConstraints.REMAINDER, 1)
					.setFill(GridConstraints.BOTH).setGridWeight(1, 0));
		
		jlbSenha = new JLabel("Senha:");
		pnlMain.add(jlbSenha, new GridConstraints()
					.setAnchor(GridConstraints.WEST).setInsets(5).setGridSize(1, 1));
		
		jpfSenha = new JPasswordField();
		pnlMain.add(jpfSenha, new GridConstraints()
					.setAnchor(GridConstraints.WEST).setInsets(5).setGridSize(GridConstraints.REMAINDER, 1)
					.setFill(GridConstraints.BOTH).setGridWeight(1, 0));
		
		jbtNovoCliente = new JButton("Novo Cliente");
		pnlMain.add(jbtNovoCliente, new GridConstraints()
			.setAnchor(GridConstraints.EAST).setInsets(5).setGridSize(GridConstraints.REMAINDER, 1));
		
		actions();
		
		return pnlMain;				
	}
	
	private void actions() {
		jbtAtualizar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					jlbConexoes.setText("Total de conexões: "+ servidor.getClientes().size());
				} catch (NullPointerException e) {
					jlbConexoes.setText("Total de conexões: "+ 0);
				}
			}
		});
		
		jbtNovoCliente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String nome = jtfNome.getText();
					if (!nome.equals("")) {						
						jtfNome.setText("");
						jpfSenha.setText("");
						
						Usuario usuario = new Usuario(nome, "123");
						servidor.adicionaCliente(new ClienteOffLine(usuario));
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Erro ao criar cliente");
				}
			}
		});
	}
	
	public static void main(String[] args) {
		new TelaServidor(new ServidorOffLine());
	}

}
