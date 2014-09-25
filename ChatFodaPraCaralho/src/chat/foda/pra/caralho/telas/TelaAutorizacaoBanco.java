package chat.foda.pra.caralho.telas;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import classes.Fodas.Pra.Caralho.GridConstraints;

@SuppressWarnings("serial")
public class TelaAutorizacaoBanco extends JDialog {

	private JPanel jpnMain;

	private JLabel jlbUsuario;
	private JTextField jtfUsuario;

	private JLabel jlbSenha;
	private JPasswordField jpfSenha;

	private JButton jbtCancelar;
	private JButton jbtOK;

	private String usuario;
	private String senha;

	public TelaAutorizacaoBanco() {
		setTitle("Autorização para o Banco de Dados");
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setContentPane(getMainPane());
		setModal(true);
		setSize(300, 150);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public JPanel getMainPane() {
		jpnMain = new JPanel(new GridBagLayout());
		jpnMain.setBorder(new EmptyBorder(10, 5, 5, 5));

		jlbUsuario = new JLabel("Usuário: ");
		jpnMain.add(jlbUsuario,
				new GridConstraints().setAnchor(GridConstraints.EAST)
						.setInsets(5));

		jtfUsuario = new JTextField("root");
		jpnMain.add(jtfUsuario,
				new GridConstraints().setAnchor(GridConstraints.WEST)
						.setInsets(5).setFill(GridConstraints.HORIZONTAL)
						.setOccupiedSize(GridConstraints.REMAINDER, 1)
						.setGridWeight(1, 0));

		jlbSenha = new JLabel("Senha: ");
		jpnMain.add(jlbSenha,
				new GridConstraints().setAnchor(GridConstraints.EAST)
						.setInsets(5));

		jpfSenha = new JPasswordField();
		jpnMain.add(jpfSenha,
				new GridConstraints().setAnchor(GridConstraints.WEST)
						.setInsets(5).setFill(GridConstraints.HORIZONTAL)
						.setOccupiedSize(GridConstraints.REMAINDER, 1)
						.setGridWeight(1, 0));

		JPanel jpnBotoes = new JPanel(new BorderLayout(5, 5));
		Dimension btSize = new Dimension(73, 25);

		jbtCancelar = new JButton("Cancelar");
		jbtCancelar.setPreferredSize(btSize);
		jpnBotoes.add(jbtCancelar, BorderLayout.WEST);

		jbtOK = new JButton("OK");
		jbtOK.setPreferredSize(btSize);
		jpnBotoes.add(jbtOK, BorderLayout.EAST);

		jpnMain.add(
				jpnBotoes,
				new GridConstraints().setAnchor(GridConstraints.EAST)
						.setInsets(5)
						.setOccupiedSize(GridConstraints.REMAINDER, 1));

		this.actionButtons();
		return jpnMain;
	}

	public void actionButtons() {
		jbtOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setUsuario(jtfUsuario.getText());
				setSenha(new String(jpfSenha.getPassword()));
				dispose();
			}
		});

		jbtCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
