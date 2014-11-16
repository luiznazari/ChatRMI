package chat.foda.pra.caralho.telas;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import chat.foda.pra.caralho.models.Chat;
import classes.Fodas.Pra.Caralho.GridConstraints;

/**
 * @author luiznazari
 */
public class TelaChatBuilder {
	
	private JInternalFrame jif;
	
	private JMenuBar jmbMenuBar;
	
	private JMenu jmnParticipantes;
	
	private JMenuItem jmiConvidar;
	
	private JMenuItem jmiAdicionarAmigo;
	
	private JPanel pnlMain;
	
	private JTextArea jtaConversa;
	
	private JScrollPane jspConversa;
	
	private JTextArea jtaMensagem;
	
	private JScrollPane jspMensagem;
	
	private JButton jbtEnviar;
	
	private Chat chat;
	
	private TelaCliente telaPai;
	
	public TelaChatBuilder(TelaCliente telaPai, Chat chat) {
		this.telaPai = telaPai;
		this.chat = chat;
	}
	
	public JInternalFrame getInternalFrame() {
		if (jif == null) {
			jif = new JInternalFrame(getChat().getNomesParticipantes(), true, true, true, true);
			
			jif.setJMenuBar(getMenu());
			jif.setContentPane(getPainelPrincipal());
			jif.setSize(300, 300);
			jif.setVisible(true);
		}
		
		return jif;
	}
	
	private JMenuBar getMenu() {
		jmbMenuBar = new JMenuBar();
		jmnParticipantes = new JMenu("Participantes");
		
		jmiConvidar = new JMenuItem("Convidar");
		jmnParticipantes.add(jmiConvidar);
		jmiAdicionarAmigo = new JMenuItem("Adicionar como amigo");
		jmnParticipantes.add(jmiAdicionarAmigo);
		
		acoesMenu();
		
		jmbMenuBar.add(jmnParticipantes);
		return jmbMenuBar;
	}
	
	private void acoesMenu() {
		jmiConvidar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				telaPai.convidaParticipante(getChat());
			}
		});
		
		jmiAdicionarAmigo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				telaPai.adicionaAmigosDoChat(getChat());
			}
		});
	}
	
	private JPanel getPainelPrincipal() {
		pnlMain = new JPanel();
		pnlMain.setLayout(new GridBagLayout());
		
		jtaConversa = new JTextArea();
		jtaConversa.setEditable(false);
		jtaConversa.setLineWrap(true);
		jtaConversa.setWrapStyleWord(true);
		jtaConversa.setAutoscrolls(true);
		jspConversa = new JScrollPane(jtaConversa);
		jspConversa.setAutoscrolls(true);
		pnlMain.add(jspConversa,
		        new GridConstraints().setAnchor(GridConstraints.CENTER).setInsets(5, 5, 0, 5).setFill(GridConstraints.BOTH)
		                .setGridWeight(1, 1).setOccupiedSize(GridConstraints.REMAINDER, 1));
		
		jtaMensagem = new JTextArea();
		jspMensagem = new JScrollPane(jtaMensagem);
		jspMensagem.setAutoscrolls(true);
		pnlMain.add(
		        jspMensagem,
		        new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(5)
		                .setOccupiedSize(GridConstraints.RELATIVE, GridConstraints.REMAINDER).setGridWeight(1, 0.1)
		                .setFill(GridConstraints.BOTH));
		
		jbtEnviar = new JButton("Enviar");
		pnlMain.add(jbtEnviar,
		        new GridConstraints().setAnchor(GridConstraints.EAST).setInsets(5, 5, 5, 5).setFill(GridConstraints.BOTH)
		                .setGridWeight(0, 0.1).setOccupiedSize(GridConstraints.REMAINDER, GridConstraints.REMAINDER));
		
		actions();
		
		jtaMensagem.requestFocus();
		
		return pnlMain;
	}
	
	private void actions() {
		
		jbtEnviar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				enviaMensagem();
			}
		});
		
		// Não aceita caractere "\n" na TextArea pressionando <enter>
		Action[] actions = jtaMensagem.getActions();
		for (Action currentAction : actions) {
			if (currentAction.toString().contains("InsertBreakAction")) {
				currentAction.setEnabled(false);
			}
		}
		
		// Quando <enter> envia a mensagem
		// Quando <enter> + <shift> envia "\n"
		jtaMensagem.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {}
			
			@Override
			public void keyReleased(KeyEvent arg0) {}
			
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER && evt.isShiftDown()) {
					jtaMensagem.append("\n");
				} else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					enviaMensagem();
				}
			}
		});
	}
	
	private void enviaMensagem() {
		String mensagem = jtaMensagem.getText();
		
		if (!mensagem.isEmpty()) {
			try {
				telaPai.getCliente()
				        .getService()
				        .enviarMensagemParaAmigos(this.getChat().getCodigo(),
				                telaPai.getUsuario().getNickName() + ": " + mensagem);
				
			} catch (RemoteException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Conexão - Erro ao enviar mensagem.");
			}
			
			jtaMensagem.setText("");
			jtaMensagem.requestFocus();
		}
	}
	
	public void recebeMensagem(String mensagem) {
		jtaConversa.append(mensagem + "\n");
	}
	
	public void setFocus() {
		this.jtaMensagem.requestFocus();
	}
	
	public void desativaChat(String mensagem) {
		this.jtaMensagem.setText(mensagem);
		this.jtaMensagem.setEnabled(false);
		this.jbtEnviar.setEnabled(false);
	}
	
	public void ativarChat() {
		this.jtaMensagem.setText("");
		this.jtaMensagem.setEnabled(true);
		this.jbtEnviar.setEnabled(true);
	}
	
	public Chat getChat() {
		return chat;
	}
	
	public void setChat(Chat chat) {
		this.chat = chat;
		this.jif.setTitle(chat.getNomesParticipantes());
	}
	
	public TelaCliente getClient() {
		return telaPai;
	}
	
	public void setClient(TelaCliente client) {
		this.telaPai = client;
	}
	
	public static void main(String[] args) {
		JFrame jf = new JFrame();
		jf.setTitle("chat");
		jf.setContentPane(new TelaChatBuilder(null, null).getInternalFrame());
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setMinimumSize(new Dimension(550, 400));
		jf.setSize(550, 400);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		
	}
}
