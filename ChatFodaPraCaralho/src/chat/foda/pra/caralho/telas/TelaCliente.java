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
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
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
import classes.Fodas.Pra.Caralho.GridConstraints;

/**
 * @author luiznazari
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
	
	private JInternalFrame jifTelaChat;
	
	private Dimension minDimensao;
	
	/* --------------------- */
	
	private ClienteRmi cliente;
	
	private Usuario usuario;
	
	private ArrayList<TelaChat> chatList = new ArrayList<>();;
	
	public TelaCliente(ClienteRmi cliente) {
		
		this.cliente = cliente;
		this.cliente.getClienteService().setTelaCliente(this);
		this.usuario = cliente.getUsuarioLogado().getUsuario();
		this.minDimensao = new Dimension(200, 400);
		
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
				// TODO Auto-generated method stub
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
				try {
					TelaListaAmigos telaAmigos = new TelaListaAmigos(getTela());
					Usuario amigo = telaAmigos.getAmigoToAdd();
					
					cliente.getService().adicionaAmigo(usuario.getCodigo(), amigo.getCodigo());
					usuario.adicionaAmigo(amigo);
					dlmAmigos.addElement(amigo);
				} catch (RemoteException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Conexão - Erro ao adicionar amigo");
				}
				
			}
		});
		
		jmiRemoverAmigo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
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
	
	private JInternalFrame getTalkFrame(String nome, JComponent componente) {
		jifTelaChat = new JInternalFrame(nome, true, true, true, true);
		
		jifTelaChat.setSize(300, 300);
		jifTelaChat.setVisible(true);
		
		jifTelaChat.add(componente);
		
		return jifTelaChat;
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
					if (!novoNickname.isEmpty() && novoNickname.length() > 3) {
						usuario.setNickName(novoNickname);
						jlbNomeUsuario.setText(novoNickname);
						
						cliente.getService().trocarNickname(usuario.getCodigo(), novoNickname);
					} else {
						JOptionPane.showMessageDialog(null, "Nome está vazio ou é muito curto");
					}
				} catch (RemoteException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Conexão - Erro ao alterar nickname");
				} catch (NullPointerException e) {}
			}
		});
		
		jlstAmigos.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Usuario amigoToChat = jlstAmigos.getSelectedValue();
				jlstAmigos.clearSelection();
				
				if (e.getValueIsAdjusting()) {
					// abrirChat(codigoAmigo);
					System.out.println(amigoToChat.getCodigo() + " | " + amigoToChat.getNickName() + " | "
					        + amigoToChat.getPessoa().getCodigo() + " | " + amigoToChat.getPessoa().getNomeCompleto());
				}
				
			}
		});
	}
	
	public void abrirChat(Long codigoAmigo) {
		final TelaChat telaChat = new TelaChat(this);
		
		Chat chat = null;
		
		try {
			chat = cliente.getService().criarChat(usuario, codigoAmigo);
		} catch (RemoteException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Conexão - Erro ao abrir chat");
		}
		
		if (chat != null) {
			telaChat.setChat(chat);
			chatList.add(telaChat);
			jdpDesktopChat.add(this.getTalkFrame(chat.getNomesParticipantes(), telaChat.getChatPanel()));
		} else {
			JOptionPane.showMessageDialog(this, "O usuário selecionado não está logado", "Conexão",
			        JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	public void iniciarChatExistente(Chat chat, Long codigoAmigo) {
		final TelaChat telaChat = new TelaChat(this);
		
		telaChat.setChat(chat);
		
		chatList.add(telaChat);
		jdpDesktopChat.add(this.getTalkFrame(chat.getNomesParticipantes(), telaChat.getChatPanel()));
	}
	
	public DefaultListModel<Usuario> getListaAmigos() {
		dlmAmigos = new DefaultListModel<>();
		
		for (Usuario u : usuario.getAmigos()) {
			dlmAmigos.addElement(u);
		}
		
		return dlmAmigos;
	}
	
	public void enviarParaTodos(String mensagem) {
		for (TelaChat tc : chatList) {
			tc.recebeMensagem(mensagem);
		}
	}
	
	public void enviarParaChat(Long chatCodigo, String mensagem) {
		for (TelaChat tc : chatList) {
			if (tc.getChat().getCodigo().equals(chatCodigo)) {
				tc.recebeMensagem(mensagem);
				break;
			}
		}
	}
	
	public void desativarChat(Long chatCodigo) {
		for (TelaChat tc : chatList) {
			if (tc.getChat().getCodigo().equals(chatCodigo)) {
				tc.desativaChat("O amigo se desconectou.");
				break;
			}
		}
	}
	
	public void desativarTodosChats() {
		for (TelaChat tc : chatList) {
			tc.desativaChat("O servidor está offline.");
		}
	}
	
	public ArrayList<Long> getCodigosChat() {
		ArrayList<Long> codigos = new ArrayList<>();
		for (TelaChat tc : chatList) {
			codigos.add(tc.getChat().getCodigo());
		}
		return codigos;
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