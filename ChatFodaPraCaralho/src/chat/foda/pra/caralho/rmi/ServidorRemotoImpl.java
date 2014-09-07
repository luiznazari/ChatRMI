package chat.foda.pra.caralho.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalTime;

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
	
	private Map<String, ClienteRemoto> clientesConectados = new HashMap<String, ClienteRemoto>();
	
	private Map<Long, ArrayList<ClienteRemoto>> chatsAbertos = new HashMap<Long, ArrayList<ClienteRemoto>>();
	
	private Long autoIncrementChatId = 0l;
	
	private UsuarioDAO usuarioDAO;
	
	public ServidorRemotoImpl() throws RemoteException {
		super();
		
		usuarioDAO = DaoFactory.get().usuarioDAO();
	}
	
	public void setTelaServidor(TelaServidor telaServidor) {
		this.telaServidor = telaServidor;
	}
	
	public Integer getNumeroUsuariosLogados() {
		return clientesConectados.size();
	}
	
	public void enviarMensagemParaTodosClientes(String mensagem) throws RemoteException {
		for (ClienteRemoto cliente : clientesConectados.values()) {
			cliente.enviarParaTodos(mensagem);
		}
	}
	
	@Override
	public void enviarMensagemParaServidor(Long chatCodigo, String mensagem) throws RemoteException {
		for (ClienteRemoto cliente : chatsAbertos.get(chatCodigo)) {
			cliente.enviarMensagem(chatCodigo, mensagem);
		}
	}
	
	public void encerrarServicos() throws RemoteException {
		for (ClienteRemoto cliente : clientesConectados.values()) {
			cliente.desativarTodosChats();
		}
	}
	
	@Override
	public synchronized UsuarioLogado login(ClienteRemoto cliente, String nome, String senha) throws RemoteException {
		Usuario usuario = usuarioDAO.findOneByNomePessoa(nome);
		if (usuario != null && !clientesConectados.containsKey(nome)) {
			if (usuario.getSenha().equals(senha)) {
				UsuarioLogado usuarioLogado = new UsuarioLogado(usuario);
				clientesConectados.put(usuario.getPessoa().getNomeCompleto(), cliente);
				telaServidor.escreverNoConsole("[" + new LocalTime() + "] O usuário '"
				        + usuarioLogado.getUsuario().getPessoa().getNomeCompleto() + "' se conectou.");
				telaServidor.atualizaContador(this.getNumeroUsuariosLogados().toString());
				return usuarioLogado;
			}
		}
		return null;
	}
	
	@Override
	public void logout(ArrayList<Long> codigos, ClienteRemoto cliente, String nome) throws RemoteException {
		// Fecha todos os chats do usuário
		try {
			for (Long codigo : codigos) {
				fecharChat(codigo, cliente, nome);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		clientesConectados.remove(nome);
		telaServidor.escreverNoConsole("[" + new LocalTime() + "] O usuário '" + nome + "' se desconectou.");
		telaServidor.atualizaContador(this.getNumeroUsuariosLogados().toString());
	}
	
	@Override
	public boolean cadastrarUsuario(String nome, String senha) throws RemoteException {
		boolean b;
		if (usuarioDAO.findOneByNomePessoa(nome) == null) {
			usuarioDAO.save(new Usuario(nome, senha));
			b = true;
		} else {
			b = false;
		}
		return b;
	}
	
	@Override
	public void removerUsuario(String nome) throws RemoteException {
		// TODO banco.remover(banco.getUsuario(nome));
	}
	
	@Override
	public synchronized boolean adicionaAmigo(Usuario usuario, String nomeAmigo) throws RemoteException {
		Usuario novoAmigo = usuarioDAO.findOneByNomePessoa(nomeAmigo);
		if (novoAmigo != null) {
			// TODO banco.remover(banco.getUsuario(usuario.getNomeCompleto()));
			usuario.adicionaAmigo(nomeAmigo);
			usuarioDAO.update(usuario);
			return true;
		}
		return false;
	}
	
	@Override
	public void removerAmigo(Usuario usuario, String nomeAmigo) throws RemoteException {
		Usuario usuarioAmigo = usuarioDAO.findOneByNomePessoa(nomeAmigo);
		if (usuarioAmigo != null) {
			// banco.remover(banco.getUsuario(usuario.getNomeCompleto()));
			usuario.removeAmigo(nomeAmigo);
			usuarioDAO.update(usuario);
		}
	}
	
	@Override
	public Chat criarChat(Usuario solicitante, String nomeAmigo) {
		Usuario amigo = usuarioDAO.findOneByNomePessoa(nomeAmigo);
		if (amigo != null && clientesConectados.containsKey(nomeAmigo)) {
			Chat novoChat = new Chat(autoIncrementChatId++);
			novoChat.adicionaUsuario(solicitante);
			novoChat.adicionaUsuario(amigo);
			
			ClienteRemoto clienteAmigo = clientesConectados.get(nomeAmigo);
			try {
				clienteAmigo.abrirChat(novoChat, solicitante.getPessoa().getNomeCompleto());
			} catch (RemoteException e) {
				e.printStackTrace();
				return null;
			}
			
			chatsAbertos.put(
			        novoChat.getCodigo(),
			        new ArrayList<ClienteRemoto>(Arrays.asList(clientesConectados.get(solicitante.getPessoa().getNomeCompleto()),
			                clienteAmigo)));
			return novoChat;
		}
		return null;
	}
	
	public void fecharChat(Long codigo, ClienteRemoto cliente, String nome) throws RemoteException {
		ArrayList<ClienteRemoto> clientesNoChat = chatsAbertos.get(codigo);
		
		switch (clientesNoChat.size()) {
		
			case 1: {
				chatsAbertos.remove(codigo);
				break;
			}
			case 2: {
				for (ClienteRemoto cliente2 : clientesNoChat) {
					cliente2.desativarChat(codigo);
				}
				break;
			}
			default: {
				clientesNoChat.remove(cliente);
				for (ClienteRemoto cliente2 : clientesNoChat) {
					cliente2.enviarMensagem(codigo, "O usuário " + nome + " saiu da conversa.");
				}
				break;
			}
			
		}
		/*
		 * if (clientesNoChat.size() < 3) {
		 * for (ClienteRemoto cliente2 : clientesNoChat) {
		 * cliente2.desativarChat(codigo);
		 * }
		 * chatsAbertos.remove(codigo);
		 * } else {
		 * clientesNoChat.remove(cliente);
		 * for (ClienteRemoto cliente2 : clientesNoChat) {
		 * cliente2.enviarMensagem(codigo, "O usuário " + nome + " saiu da conversa.");
		 * }
		 * }
		 */
	}
	
	@Override
	public synchronized void atualizarNickname(String nomeUsuario, String novoNickname) throws RemoteException {
		Usuario usuario = usuarioDAO.findOneByNomePessoa(nomeUsuario);
		usuario.setNickName(novoNickname);
		usuarioDAO.update(usuario);
	}
	
}
