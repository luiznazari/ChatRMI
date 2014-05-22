package chat.foda.pra.caralho.telas;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import classes.Fodas.Pra.Caralho.GridConstraints;

public class TelaCadastro extends JFrame {

	/**
	 * @author luiznazari
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	public TelaCadastro() {
		super("Cadastrar Conta");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setContentPane(getMain());
		setVisible(true);
		setSize(300, 190);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	public JPanel getMain() {
		pnlMain = new JPanel();
		pnlMain.setLayout(new GridBagLayout());
		
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
		
		jlbConfirmaSenha = new JLabel("Confirmar Senha:");
		pnlMain.add(jlbConfirmaSenha, new GridConstraints()
					.setAnchor(GridConstraints.WEST).setInsets(5).setGridSize(2, 1));
		
		jpfConfirmaSenha = new JPasswordField();
		pnlMain.add(jpfConfirmaSenha, new GridConstraints()
					.setAnchor(GridConstraints.WEST).setInsets(5).setGridSize(GridConstraints.REMAINDER, 1)
					.setFill(GridConstraints.BOTH).setGridWeight(1, 0));
		
		jlbSerial = new JLabel("Serial:");
		pnlMain.add(jlbSerial, new GridConstraints()
					.setAnchor(GridConstraints.WEST).setInsets(5).setGridSize(2, 1));
		
		jtfSerial = new JTextField();
		pnlMain.add(jtfSerial, new GridConstraints()
					.setAnchor(GridConstraints.WEST).setInsets(5).setGridSize(GridConstraints.REMAINDER, 1)
					.setFill(GridConstraints.BOTH).setGridWeight(1, 0));
		
		jbtConfirmar = new JButton("Confirmar");
		pnlMain.add(jbtConfirmar, new GridConstraints()
					.setAnchor(GridConstraints.CENTER).setInsets(5, 30, 5, 15).setGridSize(3, 1));
		
		
		jbtCancelar = new JButton("Cancelar");
		pnlMain.add(jbtCancelar, new GridConstraints()
					.setAnchor(GridConstraints.CENTER).setInsets(5, 15, 5, 30).setGridSize(GridConstraints.REMAINDER, 1));
		
		actions();
		
		return pnlMain;
	}
	
	public void actions () {
		
		jbtConfirmar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		jbtCancelar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				jlbNome.setText("");
				jpfSenha.setText("");
				jpfConfirmaSenha.setText("");
				jtfSerial.setText("");
				dispose();
			}
		});
	}
	
	public static void main(String[] args) {
		new TelaCadastro();
	}
}
