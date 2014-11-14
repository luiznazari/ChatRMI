package chat.foda.pra.caralho.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.joda.time.LocalTime;

import chat.foda.pra.caralho.dao.AmigosDAO;
import chat.foda.pra.caralho.dao.UsuarioDAO;
import chat.foda.pra.caralho.dao.factory.DaoFactory;
import chat.foda.pra.caralho.models.Chat;
import chat.foda.pra.caralho.models.Usuario;
import chat.foda.pra.caralho.models.UsuarioLogado;
import chat.foda.pra.caralho.telas.TelaServidor;

public class ServidorRemotoImpl extends UnicastRemoteObject implements ServidorRemoto {
	
	/**
	 * Classe que implementa as ações do Servidor
	 * 
	 * @author luiznazari
	 */
	private static final long serialVersionUID = -8382898850011577230L;
	
	private TelaServidor telaServidor;
	
	private Map<Long, ClienteRemoto> clientesConectados = new HashMap<>();
	
	private Map<Long, ArrayList<ClienteRemoto>> chatsAbertos = new HashMap<>();
	
	private Long autoIncrementChatId = 0l;
	
	private UsuarioDAO usuarioDAO;
	
	private AmigosDAO amigosDAO;
	
	public ServidorRemotoImpl() throws RemoteException {
		super();
		
		usuarioDAO = DaoFactory.get().usuarioDao();
		amigosDAO = DaoFactory.get().amigosDao();
	}
	
	public void setTelaServidor(TelaServidor telaServidor) {
		this.telaServidor = telaServidor;
	}
	
	/**
	 * @return Quantidade de usuários conectados.
	 */
	public Integer getNumeroUsuariosLogados() {
		return clientesConectados.size();
	}
	
	/**
	 * Envia uma mensagem para todos os chats abertos de todos os clientes.
	 * 
	 * @param mensagem
	 * @throws RemoteException
	 */
	public void enviarMensagemParaTodosClientes(String mensagem) throws RemoteException {
		for (ClienteRemoto cliente : clientesConectados.values()) {
			cliente.enviarParaTodos(mensagem);
		}
	}
	
	/**
	 * Envia mensagem para todos os clientes que possuem o chat com o código especificado
	 * 
	 * @param codChat
	 * @param mensagem
	 */
	@Override
	public void enviarMensagemParaAmigos(Long codChat, String mensagem) throws RemoteException {
		for (ClienteRemoto cliente : chatsAbertos.get(codChat)) {
			cliente.enviarMensagem(codChat, mensagem);
		}
	}
	
	/**
	 * Percorre todos os clientes, inativando todos os chats para os mesmos. Utilizado apenas quando o servidor irá ser
	 * desligado.
	 * 
	 * @throws RemoteException
	 */
	public void encerrarServicos() throws RemoteException {
		for (ClienteRemoto cliente : clientesConectados.values()) {
			cliente.desativarTodosChats();
		}
	}
	
	@Override
	public UsuarioLogado login(ClienteRemoto cliente, String email, String senha) throws RemoteException {
		Usuario usuario = usuarioDAO.findOneByEmail(email);
		if (usuario != null && !clientesConectados.containsKey(usuario.getCodigo())) {
			if (usuario.getSenha().equals(senha)) {
				UsuarioLogado usuarioLogado = new UsuarioLogado(usuario);
				clientesConectados.put(usuario.getCodigo(), cliente);
				telaServidor.escreverNoConsole("[" + LocalTime.now() + "] O usuário '"
				        + usuarioLogado.getUsuario().getPessoa().getNomeCompleto() + "' se conectou.");
				telaServidor.atualizaContador(this.getNumeroUsuariosLogados().toString());
				return usuarioLogado;
			}
		}
		return null;
	}
	
	@Override
	public void logout(Set<Long> codigosChats, ClienteRemoto cliente, Usuario usuario) throws RemoteException {
		// Fecha todos os chats do usuário
		try {
			for (Long codigo : codigosChats) {
				fecharChat(codigo, cliente, usuario.getPessoa().getNomeCompleto());
			}
		} catch (NullPointerException e) {
			// TODO ver por que está recebendo NPE ao fechar chat/servidor
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		clientesConectados.remove(usuario.getCodigo());
		telaServidor.escreverNoConsole("[" + LocalTime.now() + "] O usuário '" + usuario.getPessoa().getNomeCompleto()
		        + "' se desconectou.");
		telaServidor.atualizaContador(this.getNumeroUsuariosLogados().toString());
	}
	
	@Override
	public boolean cadastrarUsuario(Usuario usuario) throws RemoteException {
		if (usuarioDAO.findOneByEmail(usuario.getEmail()) == null) {
			usuarioDAO.save(usuario);
			return true;
		}
		
		return false;
	}
	
	@Override
	public void removerUsuario(Usuario usuario) throws RemoteException {
		usuarioDAO.delete(usuario);
	}
	
	@Override
	public void adicionaAmigo(Long codUsuario, Long codAmigo) throws RemoteException {
		amigosDAO.save(codUsuario, codAmigo);
	}
	
	@Override
	public void removerAmigo(Long codUsuario, Long codAmigo) throws RemoteException {
		amigosDAO.deleteOne(codUsuario, codAmigo);
	}
	
	@Override
	public Set<Usuario> getUsuariosDesconhecidos(Long codUsuario) throws RemoteException {
		return amigosDAO.findAllDesconhecidosByCodUsiario(codUsuario);
	}
	
	@Override
	public Chat criarChat(Usuario solicitante, Usuario amigo) {
		try {
			ClienteRemoto amigoCliente = clientesConectados.get(amigo.getCodigo());
			
			Chat chat = new Chat(autoIncrementChatId++);
			chat.adicionaUsuario(solicitante);
			chat.adicionaUsuario(amigo);
			
			ArrayList<ClienteRemoto> participantes = new ArrayList<>();
			participantes.add(clientesConectados.get(solicitante.getCodigo()));
			participantes.add(amigoCliente);
			
			amigoCliente.abrirChat(chat);
			chatsAbertos.put(chat.getCodigo(), participantes);
			return chat;
		} catch (RemoteException e) {
			telaServidor.escreverNoConsole("Ocorreu um erro ao abrir o char para os usuários: "
			        + solicitante.getPessoa().getNomeCompleto() + ", " + amigo.getPessoa().getNomeCompleto() + ".");
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * Fecha e/ou desativa os chats onde o usuário que está deslogando participa.
	 * Se há mais de dois usuários no chat, o mesmo não é excluído, apenas retira o usuário deslogado.
	 * 
	 * @param codChat
	 * @param cliente
	 *            ClienteRemoto do usuário que deslogou
	 * @param nome
	 *            Nome do usuário que deslogou
	 * @throws RemoteException
	 */
	public void fecharChat(Long codChat, ClienteRemoto cliente, String nome) throws RemoteException {
		ArrayList<ClienteRemoto> clientesNoChat = chatsAbertos.get(codChat);
		clientesNoChat.remove(cliente);
		
		if (clientesNoChat.size() < 3) {
			for (ClienteRemoto cliente2 : clientesNoChat) {
				cliente2.desativarChat(codChat);
			}
			
			// TODO Quando implementar a funcionalidade de reabrir o chat, deverá ser retirado este comando e deixar
			// apenas para quando há um usuário restante (logado)
			chatsAbertos.remove(codChat);
		} else {
			for (ClienteRemoto cliente2 : clientesNoChat) {
				cliente2.enviarMensagem(codChat, "O usuário " + nome + " saiu da conversa.");
			}
		}
		
	}
	
	@Override
	public void trocarNickname(Long codigo, String novoNickname) throws RemoteException {
		Usuario usuario = usuarioDAO.findOne(codigo);
		usuario.setNickName(novoNickname);
		usuarioDAO.update(usuario);
	}
	
	@Override
	public void trocarSenha(Long codigo, String novaSenha) throws RemoteException {
		Usuario usuario = usuarioDAO.findOne(codigo);
		usuario.setSenha(novaSenha);
		usuarioDAO.update(usuario);
	}
	
}
