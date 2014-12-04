package chat.foda.pra.caralho.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JDesktopPane;
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
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import chat.foda.pra.caralho.clienteServidor.ClienteRmi;
import chat.foda.pra.caralho.models.Chat;
import chat.foda.pra.caralho.models.Usuario;
import chat.foda.pra.caralho.telas.eventos.EventosTelaCliente;
import classes.Fodas.Pra.Caralho.GridConstraints;

/**
 * @author Luiz Felipe Nazari
 * @author Alessandro Beleboni Belini
 */
public class TelaCliente extends JFrame {
	private static final long serialVersionUID = 1L;
	
	/* - Elementos da Tela - */
	
	private JMenuBar jmbMenuBar;
	
	private JMenu jmnArquivo;
	
	private JMenu jmnConta;
	
	private JMenuItem jmiTrocarSenha;
	
	private JMenuItem jmiRemoverConta;
	
	private JMenuItem jmiSair;
	
	private JMenu jmnAmigo;
	
	private JMenuItem jmiAdicionarAmigo;
	
	private JMenuItem jmiRemoverAmigo;
	
	private JMenu jmnAjuda;
	
	private JMenuItem jmiComandos;
	
	private JPanel pnlMain;
	
	private JPanel pnlUsuario;
	
	private JLabel jlbBemVindo;
	
	private JLabel jlbNomeUsuario;
	
	private JSeparator jsepListaDeAmigos;
	
	private JList<Usuario> jlstAmigos;
	
	private DefaultListModel<Usuario> dlmAmigos;
	
	private JSplitPane jspChat;
	
	private JScrollPane jspAmigos;
	
	private JPanel jpnAreaChat;
	
	private JDesktopPane jdpDesktopChat;
	
	private Dimension minDimensao = new Dimension(200, 400);
	
	/* --------------------- */
	
	private ClienteRmi cliente;
	
	private Usuario usuario;
	
	private Map<Long, TelaChatBuilder> chatMap = new HashMap<>();
	
	public TelaCliente(ClienteRmi cliente) {
		
		this.cliente = cliente;
		this.cliente.getClienteService().setTelaCliente(this);
		this.usuario = cliente.getUsuarioLogado().getUsuario();
		
		setTitle(usuario.getNickName());
		setContentPane(getMainPanel());
		setJMenuBar(getMenu());
		addWindowListener(new EventosTelaCliente(this));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(550, 400));
		setSize(550, 400);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JMenuBar getMenu() {
		jmbMenuBar = new JMenuBar();
		
		jmnArquivo = new JMenu("Arquivo");
		jmbMenuBar.add(jmnArquivo);
		
		jmnConta = new JMenu("Conta");
		jmiTrocarSenha = new JMenuItem("Trocar senha");
		jmnConta.add(jmiTrocarSenha);
		jmiRemoverConta = new JMenuItem("Remover Conta");
		jmnConta.add(jmiRemoverConta);
		jmnArquivo.add(jmnConta);
		
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
		
		jmiTrocarSenha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new TelaCadastro(cliente, false, false, false);
			}
		});
		
		jmiRemoverConta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int remover = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover sua conta?");
				
				if (remover == JOptionPane.YES_OPTION) {
					try {
						cliente.getService().removerUsuario(usuario);
						dispose();
					} catch (RemoteException e) {
						JOptionPane.showMessageDialog(null, "Conexão - Erro ao remover usuário");
						e.printStackTrace();
					}
				}
			}
		});
		
		jmiAdicionarAmigo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abreTelaAddAmigo(new TelaListaAmigos(getTela()));
			}
		});
		
		jmiRemoverAmigo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abreTelaRemoveAmigo(new TelaListaAmigos(getTela(), usuario.getAmigos(), "Remover amigo"));
			}
		});
		
		jmiComandos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Enter = Enviar mensagem\n" + "Shift + Enter = Quebrar linha",
				        "Key Commands", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	private JPanel getMainPanel() {
		pnlMain = new JPanel(new BorderLayout());
		
		jspChat = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, getPainelDoUsuario(), getChatPanel());
		jspChat.setContinuousLayout(true);
		jspChat.setOneTouchExpandable(true);
		jspChat.setDividerLocation(200);
		pnlMain.add(jspChat, BorderLayout.CENTER);
		
		acoes();
		return pnlMain;
	}
	
	private JPanel getPainelDoUsuario() {
		pnlUsuario = new JPanel(new GridBagLayout());
		pnlUsuario.setMinimumSize(minDimensao);
		
		jlbBemVindo = new JLabel("Bem Vindo,");
		pnlUsuario.add(jlbBemVindo,
		        new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(5, 5, 0, 5).setFill(GridConstraints.BOTH)
		                .setOccupiedSize(GridConstraints.REMAINDER, 1));
		
		jlbNomeUsuario = new JLabel(usuario.getNickName());
		pnlUsuario.add(jlbNomeUsuario,
		        new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(0, 5, 5, 5).setFill(GridConstraints.BOTH)
		                .setOccupiedSize(GridConstraints.REMAINDER, 1));
		
		jsepListaDeAmigos = new JSeparator();
		pnlUsuario.add(jsepListaDeAmigos,
		        new GridConstraints().setAnchor(GridConstraints.CENTER).setInsets(5).setFill(GridConstraints.HORIZONTAL)
		                .setOccupiedSize(GridConstraints.REMAINDER, 1));
		
		jlstAmigos = new JList<>(getListaAmigos());
		jspAmigos = new JScrollPane(jlstAmigos);
		
		pnlUsuario.add(jspAmigos,
		        new GridConstraints().setAnchor(GridConstraints.WEST).setInsets(0, 5, 5, 5).setFill(GridConstraints.BOTH)
		                .setOccupiedSize(GridConstraints.REMAINDER, GridConstraints.REMAINDER).setGridWeight(1, 1));
		
		return pnlUsuario;
	}
	
	private JPanel getChatPanel() {
		jpnAreaChat = new JPanel(new BorderLayout());
		jpnAreaChat.setMinimumSize(new Dimension(minDimensao));
		jdpDesktopChat = new JDesktopPane();
		jpnAreaChat.add(jdpDesktopChat, BorderLayout.CENTER);
		
		return jpnAreaChat;
	}
	
	private void acoes() {
		
		jlbNomeUsuario.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				jlbNomeUsuario.setForeground(Color.BLACK);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				jlbNomeUsuario.setForeground(Color.blue);
				jlbNomeUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					String novoNickname = JOptionPane.showInputDialog(null, "Digite o novo nickname:", usuario.getNickName());
					
					if (isValidNick(novoNickname)) {
						if (novoNickname.length() > 3) {
							
							usuario.setNickName(novoNickname);
							jlbNomeUsuario.setText(novoNickname);
							
							cliente.getService().trocarNickname(usuario.getCodigo(), novoNickname);
							
						} else {
							JOptionPane.showMessageDialog(null, "O Nickname '" + novoNickname
							        + "' é muito curto.\nTamanho mínimo: 4 caracteres.");
						}
					}
				} catch (RemoteException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Conexão - Erro ao alterar nickname");
				}
			}
		});
		
		jlstAmigos.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Usuario amigoToChat = jlstAmigos.getSelectedValue();
				jlstAmigos.clearSelection();
				
				if (e.getValueIsAdjusting() && amigoToChat != null) {
					TelaChatBuilder tcbBuilder = getChatAbertoCom(amigoToChat);
					
					if (tcbBuilder == null) {
						abrirChat(amigoToChat);
					} else {
						tcbBuilder.getInternalFrame().requestFocus();
					}
				}
			}
		});
	}
	
	private void abrirChat(Usuario amigo) {
		Chat chat = null;
		
		try {
			chat = cliente.getService().criarChat(usuario, amigo);
		} catch (RemoteException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Conexão - Erro ao abrir chat");
		}
		
		if (chat != null) {
			TelaChatBuilder tcBuilder = new TelaChatBuilder(this, chat);
			chatMap.put(chat.getCodigo(), tcBuilder);
			jdpDesktopChat.add(tcBuilder.getInternalFrame());
		} else {
			JOptionPane.showMessageDialog(this, "O usuário selecionado não está logado", "Conexão",
			        JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	/**
	 * Cria uma nova janela de coversa para um chat já existente (proveniente de outro usuário). Caso já houver uma
	 * janela de chat aberta com os mesmos usuários do chat recebido, a mesma é substituída.
	 * 
	 * @param chat
	 */
	public void iniciarChatExistente(Chat chat) {
		TelaChatBuilder telaChat = getChatAbertoCom(chat.getUsuarios());
		
		if (telaChat != null) {
			chatMap.remove(telaChat);
			
			telaChat.setChat(chat);
			telaChat.ativarChat();
			chatMap.put(chat.getCodigo(), telaChat);
		} else {
			telaChat = new TelaChatBuilder(this, chat);
			chatMap.put(telaChat.getChat().getCodigo(), telaChat);
			jdpDesktopChat.add(telaChat.getInternalFrame());
		}
	}
	
	public void atualizaTelaChat(Chat chat) {
		chatMap.get(chat.getCodigo()).setChat(chat);
	}
	
	public void atualizaTelaChatRemoveUsuario(Long codChat, Usuario usuario) {
		chatMap.get(codChat).getChat().removeUsuario(usuario);
	}
	
	public void desativarChat(Long chatCodigo, String mensagem) {
		chatMap.get(chatCodigo).desativaChat(mensagem);
	}
	
	public void desativarTodosChats() {
		for (TelaChatBuilder tc : chatMap.values()) {
			tc.desativaChat("O servidor está offline.");
		}
	}
	
	public void fechaChat(TelaChatBuilder tcBuilder) {
		chatMap.remove(tcBuilder.getChat().getCodigo());
		
		try {
			cliente.getService().fecharChat(tcBuilder.getChat().getCodigo(), cliente.getClienteService(), getUsuario());
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, "Conexão - Houve um erro ao sincronizar com o servidor.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Método chamado por um TelaChatBuilder para adicionar participantes do chat como amigos.
	 * 
	 * @param chat
	 */
	public void adicionaAmigosDoChat(Chat chat) {
		Set<Usuario> usuariosToAdd = new HashSet<>();
		
		for (Usuario u : chat.getUsuarios()) {
			if (!dlmAmigos.contains(u) && !u.equals(this.getUsuario())) {
				usuariosToAdd.add(u);
			}
		}
		
		abreTelaAddAmigo(new TelaListaAmigos(getTela(), usuariosToAdd, "Adicionar amigo"));
		
	}
	
	/**
	 * Método chamado por um TelaChatBuilder para convidar participantes ao chat.
	 * 
	 * @param chat
	 */
	public void convidaParticipante(Chat chat) {
		Set<Usuario> usuariosToInvite = usuario.getAmigos();
		
		for (Usuario u : chat.getUsuarios()) {
			if (chat.getUsuarios().contains(u)) {
				usuariosToInvite.remove(u);
			}
		}
		
		abreTelaConvidar(chat, new TelaListaAmigos(getTela(), usuariosToInvite, "Convidar à conversa"));
		
	}
	
	private void abreTelaConvidar(Chat chat, TelaListaAmigos telaListaAmigos) {
		try {
			Usuario amigo = telaListaAmigos.getSelecionado();
			
			if (!cliente.getService().convidarParaChat(chat, amigo)) {
				JOptionPane.showMessageDialog(null, "O usuário não está conectado!");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Conexão - Erro ao adicionar amigo");
		} catch (NullPointerException e) {
			// Usuário clicou em cancelar e não retornou um usuário.
		}
	}
	
	private void abreTelaAddAmigo(TelaListaAmigos telaListaAmigos) {
		try {
			Usuario amigo = telaListaAmigos.getSelecionado();
			
			cliente.getService().adicionaAmigo(usuario.getCodigo(), amigo.getCodigo());
			usuario.adicionaAmigo(amigo);
			dlmAmigos.addElement(amigo);
		} catch (RemoteException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Conexão - Erro ao adicionar amigo");
		} catch (NullPointerException e) {
			// Usuário clicou em cancelar e não retornou um usuário.
		}
	}
	
	private void abreTelaRemoveAmigo(TelaListaAmigos telaListaAmigos) {
		try {
			Usuario amigo = telaListaAmigos.getSelecionado();
			
			cliente.getService().removerAmigo(usuario.getCodigo(), amigo.getCodigo());
			usuario.removeAmigo(amigo);
			dlmAmigos.removeElement(amigo);
		} catch (RemoteException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Conexão - Erro ao adicionar amigo");
		} catch (NullPointerException e) {
			// Usuário clicou em cancelar e não retornou um usuário.
		}
	}
	
	public void enviarParaTodos(String mensagem) {
		for (TelaChatBuilder tc : chatMap.values()) {
			tc.recebeMensagem(mensagem);
		}
	}
	
	public void enviarParaChat(Long chatCodigo, String mensagem) {
		for (TelaChatBuilder tc : chatMap.values()) {
			if (tc.getChat().getCodigo().equals(chatCodigo)) {
				tc.ativarChat();
				tc.recebeMensagem(mensagem);
				break;
			}
		}
	}
	
	public DefaultListModel<Usuario> getListaAmigos() {
		dlmAmigos = new DefaultListModel<>();
		
		for (Usuario u : usuario.getAmigos()) {
			dlmAmigos.addElement(u);
		}
		
		return dlmAmigos;
	}
	
	/**
	 * Preciso retornar como um 'new HashSet' pois o Set retornado pelo método keyset() do HashMap não é
	 * serializável, não podendo ser passado por um canal do RMI.
	 * 
	 * @return Lista de códigos dos chats abertos para este usuário
	 */
	public Set<Long> getCodigosChat() {
		return new HashSet<Long>(chatMap.keySet());
	}
	
	/**
	 * Procura se já há um chat aberto apenas com um usuário
	 * 
	 * @param amigo
	 * @return telaChatBuilder do respectivo chat
	 */
	public TelaChatBuilder getChatAbertoCom(Usuario amigo) {
		for (TelaChatBuilder tcBuilder : chatMap.values()) {
			Set<Usuario> users = tcBuilder.getChat().getUsuarios();
			if (users.size() == 2 && users.contains(amigo)) {
				return tcBuilder;
			}
		}
		
		return null;
	}
	
	/**
	 * Procura se já há um chat aberto com determinados usuários
	 * 
	 * @param amigos
	 * @return telaChatBuilder do respectivo chat
	 */
	public TelaChatBuilder getChatAbertoCom(Set<Usuario> amigos) {
		for (TelaChatBuilder tcBuilder : chatMap.values()) {
			if (tcBuilder.getChat().getUsuarios().equals(amigos)) {
				return tcBuilder;
			}
		}
		
		return null;
	}
	
	public boolean isValidNick(String nick) {
		nick = nick.toLowerCase();
		
		if (nick.equals("avisos do servidor")) {
			JOptionPane.showMessageDialog(null, "Este nickname é inválido!");
			return false;
		}
		
		String[] nomesObscenosMasc = new String[] { "pinto", "penis", "pênis", "caralho", "saco", "pau" };
		
		String[] nomesObscenosFem = new String[] { "xana", "vagina", "boceta", "buceta", "periquita", "piriquita", "ânus",
		    "anus", "cu" };
		
		if (containsString(nick, nomesObscenosMasc,
		        "é muito curto.\nAconselhamos a utilização de viagra e tente novamente mais tarde.")) {
			return false;
		}
		
		if (containsString(nick, nomesObscenosFem,
		        "está pegando fogo.\nAconselhamos a utilização de um extintor e tente novamente mais tarde.")) {
			return false;
		}
		
		return true;
	}
	
	private boolean containsString(String toCompare, String[] strings, String msg) {
		for (String s : strings) {
			if (toCompare.contains(s)) {
				JOptionPane.showMessageDialog(null, "Erro, seu(sua) '" + s + "' " + msg);
				return true;
			}
		}
		
		return false;
	}
	
	public TelaCliente getTela() {
		return this;
	}
	
	public ClienteRmi getCliente() {
		return this.cliente;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}
	
}