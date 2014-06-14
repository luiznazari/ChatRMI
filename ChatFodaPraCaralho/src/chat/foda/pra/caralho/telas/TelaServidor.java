package chat.foda.pra.caralho.telas;

import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import classes.Fodas.Pra.Caralho.GridConstraints;

import chat.foda.pra.caralho.clienteServidor.ServidorRmi;

public class TelaServidor extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel pnlMain;
	private JLabel jlbConexoes;
	private JLabel jlbConexoesCount;
	private JTextArea jtaConsole;
	private JScrollPane jspConsole;
	
	private ServidorRmi servidor;
	
	public TelaServidor() {
		super("Servidor RMI");		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 200);
		setLocationRelativeTo(null);
		setContentPane(getMain());
		setVisible(true);

		String[] opcoes = {"192.168.1.100","192.168.1.101","192.168.1.102", "192.168.1.25", "172.18.33.99"};
		String ip = JOptionPane.showInputDialog(null, "Selecione o IP: ", "Configurar conexão", 
					JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]).toString();
		
		servidor = new ServidorRmi(ip);	
		servidor.getService().setTelaServidor(this);
	}
	
	private JPanel getMain() {
		pnlMain = new JPanel();
		pnlMain.setLayout(new GridBagLayout());
		
		jlbConexoes = new JLabel("Total de conexões: ");
		pnlMain.add(jlbConexoes, new GridConstraints()
				.setAnchor(GridConstraints.EAST).setInsets(5).setFill(GridConstraints.NONE)
				.setGridWeight(1, 0).setGridSize(GridConstraints.RELATIVE, 1));
		
		jlbConexoesCount = new JLabel("0");
		pnlMain.add(jlbConexoesCount, new GridConstraints()
				.setAnchor(GridConstraints.WEST).setInsets(5).setFill(GridConstraints.NONE)
				.setGridWeight(1, 0).setGridSize(GridConstraints.REMAINDER, 1));
		
		jtaConsole = new JTextArea();
		jtaConsole.setEditable(false);
		jtaConsole.setLineWrap(true);
		jtaConsole.setWrapStyleWord(true);
		jspConsole = new JScrollPane(jtaConsole);
		pnlMain.add(jspConsole, new GridConstraints()
				.setAnchor(GridConstraints.CENTER).setInsets(5).setGridSize(1, 1).setFill(GridConstraints.BOTH)
				.setGridSize(GridConstraints.REMAINDER, GridConstraints.REMAINDER));
				
		return pnlMain;				
	}
	
	public void atualizaContador(String numero) {
		jlbConexoesCount.setText(numero);
	}
	
	public void escreverNoConsole(String mensagem) {
		jtaConsole.append(mensagem + "\n");
	}
	
	public static void main(String[] args) {
		new TelaServidor();
	}

}
