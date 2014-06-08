package chat.foda.pra.caralho.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;

import chat.foda.pra.caralho.clienteServidor.ClienteRmi;
import classes.Fodas.Pra.Caralho.GridConstraints;

public class TelaCliente extends JFrame {
	
	/**
	 * @author luiznazari
	 */
	private static final long serialVersionUID = 1L;
	
	private JMenuBar jmbMenuBar;
	private JMenu jmnArquivo;
	private JMenuItem jmiSair;
	private JMenu jmnAmigo;
	private JMenuItem jmiAdicionarAmigo;
	private JMenuItem jmiRemoverAmigo;
	private JMenu jmnAjuda;
	private JMenuItem jmiComandos;
	
	private JPanel pnlMain;
	private JPanel pnlUsuario;
	private JLabel jlbNomeUsuario;
	private JSeparator jsepListaDeAmigos;
	private JList<String> jlstAmigos;
	private JButton jbtAbrirChat;
	private JScrollPane jspAmigos;
	private DefaultListModel<String> dlmAmigos;
	
	private JPanel pnlChat;
	private JPanel pnlChatList;
	private JScrollPane jspChatList;
	private JPanel pnlTemp;
	private JLabel jlbTemp;
	private JPanel pnlMensagem;
	
	private ArrayList<TelaChat> chatList = new ArrayList<TelaChat>();
	private ArrayList<JToggleButton> chatListButtons = new ArrayList<JToggleButton>();
	private ClienteRmi cliente;
	private String nickName;
	
	public TelaCliente(ClienteRmi cliente) {
		super("Chat Foda Pra Caralho");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(550, 400));
		setContentPane(getMainPanel());
		setLocationRelativeTo(null);
		setJMenuBar(getMenu());
		setSize(550, 400);
		setVisible(true);
		
		this.cliente = cliente;
		nickName = cliente.getUsuarioLogado().getUsuario().getNickName();
	}
	
	private JMenuBar getMenu() {
		jmbMenuBar = new JMenuBar();
		
		jmnArquivo = new JMenu("Arquivo");
		jmbMenuBar.add(jmnArquivo);
		
		jmiSair = new JMenuItem("Sair");
		jmnArquivo.add(jmiSair);
		
		jmnAmigo = new JMenu("Amigos");
		jmbMenuBar.add(jmnAmigo);
		
		jmiAdicionarAmigo = new JMenuItem("Adicionar");
		jmnAmigo.add(jmiAdicionarAmigo);
		
		jmiRemoverAmigo = new JMenuItem("Remover");
		jmnAmigo.add(jmiRemoverAmigo);
		
		jmnAjuda = new JMenu("Ajuda");
		jmbMenuBar.add(jmnAjuda);
		
		jmiComandos = new JMenuItem("Comandos");
		jmnAjuda.add(jmiComandos);
		
		getAcoesMenu();
		
		return jmbMenuBar;
	}
	
	private void getAcoesMenu() {
		jmiSair.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		jmiAdicionarAmigo.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		jmiRemoverAmigo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dlmAmigos.removeElement(JOptionPane.showInputDialog(null, "Nome: "))) {
					JOptionPane.showMessageDialog(null, "Amigo removido com sucesso!");
				} else {
					JOptionPane.showMessageDialog(null, "Amigo não encontrado");
				}
			}
		});
		
		jmiComandos.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Enter = Enviar mensagem\n" +
													"Shift + Enter = Quebrar linha",
													"Key Commands", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	private JPanel getMainPanel() {
		pnlMain = new JPanel();
		pnlMain.setLayout(new BorderLayout());
		
		pnlMain.add(getPainelDoUsuario(), BorderLayout.WEST);
		
		pnlMain.add(getChatPanel(), BorderLayout.CENTER);
		
		return pnlMain;
	}
	
	private JPanel getPainelDoUsuario() {
		pnlUsuario = new JPanel();
		pnlUsuario.setLayout(new GridBagLayout());
		pnlUsuario.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		jlbNomeUsuario = new JLabel("<html>Bem Vindo, <br>"+ nickName +"</html>");
		pnlUsuario.add(jlbNomeUsuario, new GridConstraints()
					.setAnchor(GridConstraints.WEST).setInsets(5, 5, 0, 5).setFill(GridConstraints.BOTH).setGridSize(GridConstraints.REMAINDER, 1));
		
		jsepListaDeAmigos = new JSeparator();
		pnlUsuario.add(jsepListaDeAmigos, new GridConstraints()
					.setAnchor(GridConstraints.CENTER).setInsets(5).setFill(GridConstraints.HORIZONTAL).setGridSize(GridConstraints.REMAINDER, 1));
		
		dlmAmigos = new DefaultListModel<String>();
		jlstAmigos = new JList<String>(dlmAmigos);
		jspAmigos = new JScrollPane(jlstAmigos);
		jspAmigos.setPreferredSize(new Dimension(150, 0));		
		pnlUsuario.add(jspAmigos, new GridConstraints()
					.setAnchor(GridConstraints.WEST).setInsets(0, 5, 5, 5).setFill(GridConstraints.VERTICAL)
					.setGridSize(GridConstraints.REMAINDER, GridConstraints.RELATIVE).setGridWeight(1, 1));
		
		jbtAbrirChat = new JButton("Abrir chat");
		pnlUsuario.add(jbtAbrirChat, new GridConstraints()
					.setAnchor(GridConstraints.WEST).setInsets(0, 5, 10, 5).setGridSize(GridConstraints.REMAINDER, GridConstraints.REMAINDER)
					.setGridWeight(0, 0).setFill(GridConstraints.HORIZONTAL));
		
		return pnlUsuario;
	}
	
	private JPanel getChatPanel() {
		pnlChat = new JPanel();
		pnlChat.setLayout(new GridBagLayout());
		pnlChat.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		//Painel ChatList COMEÇO
		pnlChatList = new JPanel();
		pnlChatList.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 3));
		pnlChatList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2), "Chats"));
		pnlChatList.setPreferredSize(new Dimension(353, 54));
		
		//jspChatList = new JScrollPane(pnlChatList);
		//jspChatList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//jspChatList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		//jspChatList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnlChat.add(pnlChatList, new GridConstraints()
					.setAnchor(GridConstraints.WEST).setInsets(5).setFill(GridConstraints.BOTH).setGridSize(GridConstraints.REMAINDER, 10)
					.setGridWeight(1, 0.12));
		//Painel ChatList FIM
		
		//Painel Mensagem COMEÇO		
		pnlTemp = new JPanel();
		pnlTemp.setLayout(new GridBagLayout());
		pnlTemp.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		pnlTemp.setBackground(Color.WHITE);
		
		jlbTemp = new JLabel("<html><center>Selecione um amigo e clique no botão \"Abrir chat\" <br> para começar uma nova conversa.</center></html>");
		pnlTemp.add(jlbTemp);
		
		pnlChat.add(pnlTemp, new GridConstraints()
					.setAnchor(GridConstraints.NORTH).setInsets(5, 7, 9, 7).setFill(GridConstraints.BOTH).setGridWeight(1, 1)
					.setGridSize(GridConstraints.REMAINDER, GridConstraints.REMAINDER));
		
		pnlMensagem = new JPanel();
		pnlMensagem.setVisible(false);		
		//Painel Mensagem FIM
		
		acoes();
		
		return pnlChat;
	}
	
	private void acoes() {
		jbtAbrirChat.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!jlstAmigos.isSelectionEmpty()) {
					if (!amigoTemChatAberto(jlstAmigos.getSelectedValue())) {
						novoChat();
					} else {
						JOptionPane.showMessageDialog(null, "Não é permitido abrir dois chats para o mesmo amigo.");
					}
				} else {
					JOptionPane.showMessageDialog(null,"Para abrir um chat, selecione um amigo!");
				}
			}
		});
	}
	
	private void novoChat() {
		final TelaChat c = new TelaChat(this);
		chatList.add(c);
		
		final JToggleButton jtb = new JToggleButton("---");
		chatListButtons.add(jtb);
		pnlChatList.add(jtb);
		
		jtb.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (jtb.isSelected()) {
					TelaChat c = chatList.get(getToggleButtonIndex(jtb));
					fecharChatAberto();
					pnlTemp.setVisible(false);
					pnlChat.remove(pnlMensagem);
					pnlMensagem = c.getChatPanel();
					pnlChat.add(pnlMensagem, new GridConstraints()
								.setAnchor(GridConstraints.NORTH).setGridSize(GridConstraints.REMAINDER, GridConstraints.REMAINDER).setInsets(0, 2, 2, 1)
								.setFill(GridConstraints.BOTH).setGridWeight(1, 1));
					pnlMensagem.setVisible(true);
					c.setFocus();
				} else {					
					pnlMensagem.setVisible(false);
					pnlTemp.setVisible(true);
				}
			}
		});
	
		pnlChat.revalidate();
	}
	
	private void fecharChatAberto() {
		for (JToggleButton b : chatListButtons) {
			if (b.isSelected()) {
				b.setSelected(false);
				pnlMensagem.setVisible(false);
				break;
			}			
		}
	}
	
	private int getChatAbertoIndex() {
		for (JToggleButton jtb : chatListButtons) {
			if (jtb.isSelected()) {
				return getToggleButtonIndex(jtb);
			}
		}
		return 0;
	}
	
	private int getToggleButtonIndex(JToggleButton jtb) {
		for (int i=0; i<this.chatListButtons.size(); i++) {
			if (this.chatListButtons.get(i) == jtb) {
				return i;
			}
		}
		return 0;
	}

	public ClienteRmi getCliente() {
		return this.cliente;
	}
	
	public String getNickName() {
		return nickName;
	}
}