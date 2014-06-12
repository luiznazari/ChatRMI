package chat.foda.pra.caralho.telas;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import classes.Fodas.Pra.Caralho.GridConstraints;

import chat.foda.pra.caralho.clienteServidor.ServidorRmi;

public class TelaServidor extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel pnlMain;
	private JLabel jlbConexoes;
	private JLabel jlbConexoesCount;
	private JButton jbtAtualizar;
	
	private ServidorRmi servidor;
	
	public TelaServidor() {
		super("Servidor RMI");		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(getMain());
		setVisible(true);
		setSize(300, 200);
		setResizable(false);
		setLocationRelativeTo(null);

		String[] opcoes = {"192.168.1.100","192.168.1.101","192.168.1.102", "192.168.1.25", "172.18.33.99"};
		String ip = JOptionPane.showInputDialog(null, "Selecione o IP: ", "Configurar conexão", 
					JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]).toString();
		
		servidor = new ServidorRmi(ip);		
	}
	
	private JPanel getMain() {
		pnlMain = new JPanel();
		pnlMain.setLayout(new GridBagLayout());
		
		jlbConexoes = new JLabel("Total de conexões: ");
		pnlMain.add(jlbConexoes, new GridConstraints()
				.setAnchor(GridConstraints.EAST).setInsets(5).setGridSize(GridConstraints.RELATIVE, 1));
		
		jlbConexoesCount = new JLabel("0");
		pnlMain.add(jlbConexoesCount, new GridConstraints()
				.setAnchor(GridConstraints.WEST).setInsets(5).setGridSize(GridConstraints.REMAINDER, 1));
		
		jbtAtualizar = new JButton("Atualizar");
		pnlMain.add(jbtAtualizar, new GridConstraints()
				.setAnchor(GridConstraints.CENTER).setInsets(5).setGridSize(GridConstraints.REMAINDER, 1));
		
		actions();
		
		return pnlMain;				
	}
	
	private void actions() {
		jbtAtualizar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				jlbConexoesCount.setText(Integer.toString(servidor.getService().getNumeroUsuariosLogados()));
			}
		});
	}
	
	public static void main(String[] args) {
		new TelaServidor();
	}

}
