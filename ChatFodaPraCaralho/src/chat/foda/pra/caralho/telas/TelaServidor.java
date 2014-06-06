package chat.foda.pra.caralho.telas;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import classes.Fodas.Pra.Caralho.GridConstraints;

import chat.foda.pra.caralho.clienteServidor.ServidorRmi;

public class TelaServidor extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel pnlMain;
	private JLabel jlbConexoes;
	private JButton jbtAtualizar;
	
	private ServidorRmi servidor;
	
	public TelaServidor(ServidorRmi servidor) {
		super("Servidor RMI");		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setContentPane(getMain());
		setVisible(true);
		setSize(300, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		
		this.servidor = servidor;
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
		
		actions();
		
		return pnlMain;				
	}
	
	private void actions() {
		jbtAtualizar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
	}

}
