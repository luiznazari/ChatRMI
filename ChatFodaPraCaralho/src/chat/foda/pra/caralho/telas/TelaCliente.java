package chat.foda.pra.caralho.telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
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
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import chat.foda.pra.caralho.clienteServidor.ClienteRmi;
import chat.foda.pra.caralho.modelo.Chat;
import chat.foda.pra.caralho.modelo.Usuario;
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

	private JSplitPane jspChat;
	private JScrollPane jspAmigos;
	private DefaultListModel<String> dlmAmigos;

	private ArrayList<TelaChat> chatList;
	private ClienteRmi cliente;
	private String nickName;

	private MyJTabbedPane jtpChat;

	private JPanel jpnAreaChat;
	private Dimension minDimensao;

	public TelaCliente(ClienteRmi cliente) {
		this.cliente = cliente;
		this.cliente.getClienteService().setTelaCliente(this);
		this.nickName = cliente.getUsuarioLogado().getUsuario().getNickName();
		this.chatList = new ArrayList<>();
		this.minDimensao = new Dimension(200, 400);

		setTitle(nickName);
		setContentPane(getMainPanel());
		setJMenuBar(getMenu());
		addWindowListener(new EventosTelaCliente(cliente));
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
				String nomeAmigo = JOptionPane.showInputDialog(null,
						"Digite o nome do amigo:");
				if (nomeAmigo != null && !dlmAmigos.contains(nomeAmigo)) {
					try {
						if (cliente.getService().adicionaAmigo(
								cliente.getUsuarioLogado().getUsuario(),
								nomeAmigo)) {
							dlmAmigos.addElement(nomeAmigo);
						} else {
							JOptionPane.showMessageDialog(null,
									"Usuário não cadastrado.");
						}
					} catch (RemoteException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Conexão - Erro ao adicionar novo amigo.");
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Nome digitado inválido ou amigo já adicionado.");
				}
			}
		});

		jmiRemoverAmigo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nomeAmigo = JOptionPane.showInputDialog(null,
						"Digite o nome do amigo:");
				if (nomeAmigo != null && !dlmAmigos.contains(nomeAmigo)) {
					try {
						cliente.getService().removerAmigo(
								cliente.getUsuarioLogado().getUsuario(),
								nomeAmigo);
						dlmAmigos.addElement(nomeAmigo);
						JOptionPane.showMessageDialog(null,
								"Amigo removido com sucesso!");
					} catch (RemoteException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Conexão - Erro ao remover amigo.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Amigo não encontrado");
				}
			}
		});

		jmiComandos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Enter = Enviar mensagem\n"
						+ "Shift + Enter = Quebrar linha", "Key Commands",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	private JPanel getMainPanel() {
		pnlMain = new JPanel(new BorderLayout());

		jspChat = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				getPainelDoUsuario(), getChatPanel());
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

		jlbNomeUsuario = new JLabel("<html>Bem Vindo,<br><b>" + nickName
				+ "</b></html>");

		pnlUsuario.add(jlbNomeUsuario,
				new GridConstraints().setAnchor(GridConstraints.WEST)
						.setInsets(5).setFill(GridConstraints.BOTH)
						.setGridSize(GridConstraints.REMAINDER, 1));

		jsepListaDeAmigos = new JSeparator();
		pnlUsuario.add(jsepListaDeAmigos,
				new GridConstraints().setAnchor(GridConstraints.CENTER)
						.setInsets(5).setFill(GridConstraints.HORIZONTAL)
						.setGridSize(GridConstraints.REMAINDER, 1));

		dlmAmigos = new DefaultListModel<String>();
		jlstAmigos = new JList<String>(getListaAmigos());
		jspAmigos = new JScrollPane(jlstAmigos);

		pnlUsuario.add(
				jspAmigos,
				new GridConstraints()
						.setAnchor(GridConstraints.WEST)
						.setInsets(0, 5, 5, 5)
						.setFill(GridConstraints.BOTH)
						.setGridSize(GridConstraints.REMAINDER,
								GridConstraints.REMAINDER).setGridWeight(1, 1));

		return pnlUsuario;
	}

	private JPanel getChatPanel() {
		jpnAreaChat = new JPanel(new BorderLayout());
		jpnAreaChat.setMinimumSize(new Dimension(minDimensao));
		jpnAreaChat.setBorder(new EmptyBorder(5, 5, 5, 5));
		jtpChat = new MyJTabbedPane();
		jpnAreaChat.add(jtpChat, BorderLayout.CENTER);

		return jpnAreaChat;
	}

	private void acoes() {

		jlstAmigos.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					String nomeAmigo = jlstAmigos.getSelectedValue();
					abrirChat(nomeAmigo);
				}

			}
		});
	}

	public void abrirChat(String nomeAmigo) {
		final TelaChat telaChat = new TelaChat(this);

		Chat chat = null;

		try {
			chat = cliente.getService().criarChat(
					cliente.getUsuarioLogado().getUsuario(), nomeAmigo);
		} catch (RemoteException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Conexão - Erro ao abrir chat");
		}

		if (chat != null) {
			telaChat.setChat(chat);
			chatList.add(telaChat);
			jtpChat.addTabWithButtonClose(nomeAmigo, telaChat.getChatPanel());
		} else {
			JOptionPane.showMessageDialog(this,
					"O usuário selecionado não está logado", "Conexão",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public void iniciarChatExistente(Chat chat, String nomeAmigo) {
		final TelaChat telaChat = new TelaChat(this);

		telaChat.setChat(chat);

		chatList.add(telaChat);
		jtpChat.addTabWithButtonClose(nomeAmigo, telaChat.getChatPanel());
	}

	public ClienteRmi getCliente() {
		return this.cliente;
	}

	public String getNickName() {
		return nickName;
	}

	public DefaultListModel<String> getListaAmigos() {
		try {
			for (Usuario u : cliente.getUsuarioLogado().getUsuario()
					.getAmigos()) {
				dlmAmigos.addElement(u.getNomeCompleto());
			}
		} catch (NullPointerException e) {
		}
		return dlmAmigos;
	}

	public void enviarParaTodos(String mensagem) {
		for (TelaChat tc : chatList) {
			tc.recebeMensagem(mensagem);
		}
	}

	public void enviarParaChat(Integer chatCodigo, String mensagem) {
		for (TelaChat tc : chatList) {
			if (tc.getChat().getCodigo().equals(chatCodigo)) {
				tc.recebeMensagem(mensagem);
				break;
			}
		}
	}

}