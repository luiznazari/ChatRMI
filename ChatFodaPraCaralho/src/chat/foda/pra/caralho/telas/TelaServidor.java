package chat.foda.pra.caralho.telas;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import chat.foda.pra.caralho.clienteServidor.ServidorRmi;
import classes.Fodas.Pra.Caralho.GridConstraints;

import com.alee.laf.WebLookAndFeel;

public class TelaServidor extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel pnlMain;
	private JLabel jlbConexoes;
	private JLabel jlbConexoesCount;
	private JTextArea jtaConsole;
	private JScrollPane jspConsole;
	private ServidorRmi servidor;

	public ServidorRmi getServidor() {
		return this.servidor;
	}

	public TelaServidor() throws UnknownHostException, ConnectException {
		super("Servidor RMI");
		setContentPane(getMain());

		TelaConfiguracao config = new TelaConfiguracao();
		String ip = config.getIp();
		Integer porta = config.getPorta();
		try {
			servidor = new ServidorRmi(ip, porta);
			servidor.getService().setTelaServidor(this);

			jtaConsole.append("Servidor conectado em [" + ip + "] na porta "
					+ porta + ".\n");

			addWindowListener(new EventosTelaServidor(this));
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			setSize(500, 300);
			setLocationRelativeTo(null);
			setResizable(false);
			setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro na Conexão!");
			System.exit(0);
		}
	}

	private JPanel getMain() {
		pnlMain = new JPanel();
		pnlMain.setLayout(new GridBagLayout());

		jlbConexoes = new JLabel("Total de conexões: ");
		pnlMain.add(
				jlbConexoes,
				new GridConstraints().setAnchor(GridConstraints.EAST)
						.setInsets(5).setFill(GridConstraints.NONE)
						.setGridWeight(1, 0)
						.setGridSize(GridConstraints.RELATIVE, 1));

		jlbConexoesCount = new JLabel("0");
		pnlMain.add(
				jlbConexoesCount,
				new GridConstraints().setAnchor(GridConstraints.WEST)
						.setInsets(5).setFill(GridConstraints.NONE)
						.setGridWeight(1, 0)
						.setGridSize(GridConstraints.REMAINDER, 1));

		jtaConsole = new JTextArea();
		jtaConsole.setEditable(false);
		jtaConsole.setLineWrap(true);
		jtaConsole.setWrapStyleWord(true);
		jtaConsole.setBackground(Color.BLACK);
		jtaConsole.setForeground(Color.WHITE);

		jspConsole = new JScrollPane(jtaConsole);
		jspConsole.setAutoscrolls(true);
		pnlMain.add(
				jspConsole,
				new GridConstraints()
						.setAnchor(GridConstraints.CENTER)
						.setInsets(5)
						.setGridSize(GridConstraints.REMAINDER,
								GridConstraints.REMAINDER)
						.setFill(GridConstraints.BOTH).setGridWeight(1, 1));

		return pnlMain;
	}

	public void atualizaContador(String numero) {
		jlbConexoesCount.setText(numero);
	}

	public void escreverNoConsole(String mensagem) {
		jtaConsole.append(mensagem + "\n");
		jtaConsole.revalidate();
	}

	public static void main(String[] args) throws ConnectException,
			UnknownHostException {
		try {
			UIManager.setLookAndFeel(new WebLookAndFeel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new TelaServidor();
	}

}
