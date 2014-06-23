package chat.foda.pra.caralho.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import org.joda.time.LocalTime;

import chat.foda.pra.caralho.clienteServidor.ServidorRmi;
import classes.Fodas.Pra.Caralho.GridConstraints;

import com.alee.laf.WebLookAndFeel;

public class TelaServidor extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel pnlMain;

	private JPanel pnlConsole;
	private JLabel jlbConexoes;
	private JLabel jlbConexoesCount;
	private JToggleButton jtbEnviarMensagem;
	private JTextArea jtaConsole;
	private JScrollPane jspConsole;
	private ServidorRmi servidor;

	private JPanel pnlMensagem;
	private JLabel jlbMensagemEnviar;
	private JTextArea jtaMensagem;
	private JScrollPane jspMensagem;
	private JButton jbtEnviar;

	public ServidorRmi getServidor() {
		return this.servidor;
	}

	public TelaServidor() throws UnknownHostException, ConnectException {
		super("Servidor RMI");
		setContentPane(getMain());

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		/*
		 * TelaConfiguracao config = new TelaConfiguracao(); String ip =
		 * config.getIp(); Integer porta = config.getPorta(); try { servidor =
		 * new ServidorRmi(ip, porta);
		 * servidor.getService().setTelaServidor(this);
		 * 
		 * jtaConsole.append("Servidor conectado em [" + ip + "] na porta " +
		 * porta + ".\n");
		 * 
		 * addWindowListener(new EventosTelaServidor(this));
		 * setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); setSize(500, 300);
		 * setLocationRelativeTo(null); setResizable(false); setVisible(true); }
		 * catch (Exception e) { JOptionPane.showMessageDialog(this,
		 * "Erro na Conexão!"); System.exit(0); }
		 */
	}

	private JPanel getMain() {
		pnlMain = new JPanel();
		pnlMain.setLayout(new BorderLayout());

		pnlMain.add(getConsole(), BorderLayout.CENTER);

		pnlMain.add(getMessagePanel(), BorderLayout.EAST);

		getAcoes();

		return pnlMain;
	}

	private JPanel getConsole() {
		pnlConsole = new JPanel();
		pnlConsole.setLayout(new GridBagLayout());

		jlbConexoes = new JLabel("Total de conexões: ");
		pnlConsole.add(
				jlbConexoes,
				new GridConstraints().setAnchor(GridConstraints.WEST)
						.setInsets(5).setFill(GridConstraints.NONE)
						.setGridWeight(1, 0)
						.setGridSize(GridConstraints.RELATIVE, 1));

		jlbConexoesCount = new JLabel("0");
		pnlConsole.add(
				jlbConexoesCount,
				new GridConstraints().setAnchor(GridConstraints.WEST)
						.setInsets(5).setFill(GridConstraints.NONE)
						.setGridWeight(1, 0)
						.setGridSize(GridConstraints.RELATIVE, 1));

		jtbEnviarMensagem = new JToggleButton(">");
		pnlConsole.add(
				jtbEnviarMensagem,
				new GridConstraints().setAnchor(GridConstraints.EAST)
						.setInsets(5).setFill(GridConstraints.NONE)
						.setGridWeight(1, 0)
						.setGridSize(GridConstraints.REMAINDER, 1));

		jtaConsole = new JTextArea();
		jtaConsole.setLineWrap(true);
		jtaConsole.setEditable(false);
		jtaConsole.setAutoscrolls(true);
		jtaConsole.setWrapStyleWord(true);
		jtaConsole.setBackground(Color.BLACK);
		jtaConsole.setForeground(Color.WHITE);

		jspConsole = new JScrollPane(jtaConsole);
		jspConsole.setAutoscrolls(true);
		pnlConsole.add(
				jspConsole,
				new GridConstraints()
						.setAnchor(GridConstraints.CENTER)
						.setInsets(5)
						.setGridSize(GridConstraints.REMAINDER,
								GridConstraints.REMAINDER)
						.setFill(GridConstraints.BOTH).setGridWeight(1, 1));

		return pnlConsole;
	}

	private JPanel getMessagePanel() {
		pnlMensagem = new JPanel();
		pnlMensagem.setLayout(new GridBagLayout());

		jlbMensagemEnviar = new JLabel("Enviar mensagem para todos:");
		pnlMensagem.add(
				jlbMensagemEnviar,
				new GridConstraints().setAnchor(GridConstraints.CENTER)
						.setInsets(10, 0, 15, 5).setFill(GridConstraints.HORIZONTAL)
						.setGridWeight(1, 0)
						.setGridSize(GridConstraints.REMAINDER, 1));

		jtaMensagem = new JTextArea();
		jtaMensagem.setLineWrap(true);
		jtaMensagem.setAutoscrolls(true);
		jtaMensagem.setWrapStyleWord(true);
		jspMensagem = new JScrollPane(jtaMensagem);
		pnlMensagem.add(
				jspMensagem,
				new GridConstraints().setAnchor(GridConstraints.CENTER)
						.setInsets(0, 0, 0, 5).setFill(GridConstraints.BOTH)
						.setGridWeight(1, 1)
						.setGridSize(GridConstraints.REMAINDER, 1));

		jbtEnviar = new JButton("Enviar");
		pnlMensagem.add(
				jbtEnviar,
				new GridConstraints()
						.setAnchor(GridConstraints.CENTER)
						.setInsets(0, 0, 5, 5).setFill(GridConstraints.HORIZONTAL)						
						.setGridWeight(1, 0)
						.setGridSize(GridConstraints.REMAINDER,
								GridConstraints.REMAINDER));

		pnlMensagem.setVisible(false);

		return pnlMensagem;
	}

	private void getAcoes() {

		jbtEnviar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String mensagem = jtaMensagem.getText();
				try {
					servidor.getService().enviarMensagemParaTodosClientes(
							"Aviso do Servidor: " + mensagem);
					escreverNoConsole("[" + LocalTime.now()
							+ "] Mensagem enviada com sucesso!");
					jtaMensagem.setText("");
				} catch (RemoteException e1) {
					e1.printStackTrace();
					escreverNoConsole("[" + LocalTime.now()
							+ "] Conexão - Erro ao enviar mensagem.");
				}

			}
		});

		jtbEnviarMensagem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (jtbEnviarMensagem.isSelected()) {
					jtbEnviarMensagem.setText(">");
					pnlMensagem.setVisible(false);
					setSize(500, 300);
				} else {
					jtbEnviarMensagem.setText("<");
					pnlMensagem.setVisible(true);
					setSize(660, 300);
				}

			}
		});

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
