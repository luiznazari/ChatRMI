package chat.foda.pra.caralho.telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.Fodas.Pra.Caralho.GridConstraints;

/**
 * @author Alessandro Beleboni Belini
 */
public class TelaConfiguracao extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private Integer porta;
	
	private String ip;
	
	private JPanel jpnMain;
	
	private JLabel jlbIP;
	
	private JLabel jlbPorta;
	
	private JComboBox<Integer> jcbPorta;
	
	private JComboBox<String> jcbIP;
	
	private DefaultComboBoxModel<Integer> dcbmPorta;
	
	private DefaultComboBoxModel<String> dcbmIP;
	
	private JButton jbtOK;
	
	private JButton jbtCancelar;
	
	public TelaConfiguracao() {
		setTitle("Conexão");
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setContentPane(getMainPane());
		setModal(true);
		setSize(300, 148);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public List<String> getEnderecos() {
		List<String> enderecos = null;
		try {
			enderecos = new ArrayList<>();
			for (NetworkInterface interfaces : Collections.list(NetworkInterface.getNetworkInterfaces())) {
				ArrayList<InetAddress> inetAddresses = Collections.list(interfaces.getInetAddresses());
				
				String address = getIPv4(inetAddresses);
				if (!inetAddresses.isEmpty() && address != null) {
					enderecos.add(getIPv4(inetAddresses));
				}
			}
		} catch (SocketException e) {
			JOptionPane.showMessageDialog(this,
					"Não foi possível encontrar seu endereço de IP,\nPor favor, verifique sua conexão.", "Endereco de IP",
					JOptionPane.ERROR_MESSAGE);
		}
		
		return enderecos;
	}
	
	private String getIPv4(List<InetAddress> inetAddresses) {
		for (int i = 0; i < inetAddresses.size(); i++) {
			if (inetAddresses.get(i) instanceof Inet4Address) {
				return inetAddresses.get(i).getHostAddress();
			}
		}
		
		return null;
	}
	
	public JPanel getMainPane() {
		jpnMain = new JPanel(new GridBagLayout());
		jpnMain.setBorder(new EmptyBorder(10, 5, 5, 5));
		
		jlbIP = new JLabel("IP:");
		jpnMain.add(jlbIP, new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5));
		
		dcbmIP = new DefaultComboBoxModel<>();
		for (String e : this.getEnderecos()) {
			dcbmIP.addElement(e);
		}
		
		jcbIP = new JComboBox<>(dcbmIP);
		jcbIP.setEditable(true);
		jpnMain.add(jcbIP, new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(5).setFill(GridConstraints.HORIZONTAL)
				.setOccupiedSize(GridConstraints.REMAINDER, 1).setGridWeight(1, 0));
		
		jlbPorta = new JLabel("Porta:");
		jpnMain.add(jlbPorta, new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5));
		
		dcbmPorta = new DefaultComboBoxModel<>();
		dcbmPorta.addElement(5000);
		
		jcbPorta = new JComboBox<>(dcbmPorta);
		jcbPorta.setEditable(true);
		jpnMain.add(jcbPorta, new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(5).setFill(GridConstraints.BOTH)
				.setOccupiedSize(GridConstraints.REMAINDER, 1).setGridWeight(1, 0));
		
		JPanel jpnBotoes = new JPanel(new BorderLayout(5, 5));
		
		jbtCancelar = new JButton("Cancelar");
		jbtCancelar.setPreferredSize(new Dimension(73, 25));
		jpnBotoes.add(jbtCancelar, BorderLayout.WEST);
		
		jbtOK = new JButton("OK");
		jbtOK.setPreferredSize(new Dimension(73, 25));
		jbtOK.requestFocusInWindow();
		jpnBotoes.add(jbtOK, BorderLayout.EAST);
		
		jpnMain.add(jpnBotoes, new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5).setOccupiedSize(
				GridConstraints.REMAINDER, 1));
		
		actionButton();
		return jpnMain;
	}
	
	public void actionButton() {
		jbtOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					setPorta(Integer.parseInt(jcbPorta.getSelectedItem().toString()));
					setIp(jcbIP.getSelectedItem().toString());
					dispose();
				} catch (Exception e) {}
			}
		});
		
		jbtCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	public Integer getPorta() {
		return porta;
	}
	
	public void setPorta(Integer porta) {
		this.porta = porta;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}