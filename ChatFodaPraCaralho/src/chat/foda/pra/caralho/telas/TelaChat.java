package chat.foda.pra.caralho.telas;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import chat.foda.pra.caralho.modelo.Chat;
import classes.Fodas.Pra.Caralho.GridConstraints;

public class TelaChat extends JFrame{

	/**
	 * @author luiznazari
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel pnlMain;
	private JTextArea jtaConversa;
	private JScrollPane jspConversa;
	private JTextArea jtaMensagem;
	private JScrollPane jspMensagem;
	private JButton jbtEnviar;
	
	private Chat chat;
	private TelaCliente telaCliente;
	
	public TelaChat(TelaCliente client) {
		this.telaCliente = client;
		pnlMain = new JPanel();
		pnlMain.setLayout(new GridBagLayout());
		
		jtaConversa = new JTextArea();
		jtaConversa.setEditable(false);
		jtaConversa.setLineWrap(true);
		jtaConversa.setWrapStyleWord(true);
		jspConversa = new JScrollPane(jtaConversa);
		pnlMain.add(jspConversa, new GridConstraints()
					.setAnchor(GridConstraints.CENTER).setInsets(5, 5, 0, 5).setFill(GridConstraints.BOTH)
					.setGridWeight(1, 1).setGridSize(GridConstraints.REMAINDER, 1));
		
		jtaMensagem = new JTextArea();
		jspMensagem = new JScrollPane(jtaMensagem);
		pnlMain.add(jspMensagem, new GridConstraints()
					.setAnchor(GridConstraints.WEST).setInsets(5).setGridSize(GridConstraints.RELATIVE, GridConstraints.REMAINDER)
					.setGridWeight(1, 0.1).setFill(GridConstraints.BOTH));
		
		jbtEnviar = new JButton("Enviar");		
		pnlMain.add(jbtEnviar, new GridConstraints()
					.setAnchor(GridConstraints.EAST).setInsets(5, 0, 5, 5).setFill(GridConstraints.BOTH)
					.setGridWeight(0, 0.1).setGridSize(GridConstraints.REMAINDER, GridConstraints.REMAINDER));
		
		actions();
		
		jtaMensagem.requestFocus();
	}
	
	private void actions() {
		
		jbtEnviar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				enviaMensagem();
			}
		});

		//Não aceita caractere "\n" na TextArea pressionando <enter>
		Action[] actions = jtaMensagem.getActions();
		for (Action currentAction : actions){
			if (currentAction.toString().contains("InsertBreakAction")) {
				currentAction.setEnabled(false);
		    }
		}
		
		//Quando <enter> envia a mensagem
		//Quando <enter> + <shift> envia "\n"  
		jtaMensagem.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			
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
			telaCliente.getCliente().recebeMensagem(this.chat, telaCliente.getNickName() + ": " + mensagem);
			jtaMensagem.setText("");
			jtaMensagem.requestFocus();
		}
	}
	
	public void recebeMensagem(String mensagem) {
		jtaConversa.append(mensagem +"\n");
	}
	
	private void testaEnviaMensagem() {
		String message = jtaMensagem.getText();
		if (!message.equals("")) {
			jtaConversa.append("Nameless: "+ message +"\n");
			jtaMensagem.requestFocus();
			jtaMensagem.setText("");
		}
	}
	
	private void createWindow() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setContentPane(pnlMain);
		setSize(550, 400);
		setVisible(true);
		
		jtaMensagem.requestFocus();
	}
	
	public void setMessageAreaEditable() {
		this.jtaMensagem.setText("");
		this.jtaMensagem.setEditable(true);
	}
	
	public void setFocus() {
		this.jtaMensagem.requestFocus();
	}
		
	public JPanel getChatPanel() {
		return this.pnlMain;
	}
	
	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public TelaCliente getClient() {
		return telaCliente;
	}

	public void setClient(TelaCliente client) {
		this.telaCliente = client;
	}

	public static void main(String[] arqs) {
		new TelaChat(null).createWindow();
	}
	
}
