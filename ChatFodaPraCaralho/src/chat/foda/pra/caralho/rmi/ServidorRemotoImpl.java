package chat.foda.pra.caralho.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalTime;

import chat.foda.pra.caralho.bancoDados.GerenciadorDoBanco;
import chat.foda.pra.caralho.modelo.Chat;
import chat.foda.pra.caralho.modelo.Usuario;
import chat.foda.pra.caralho.modelo.UsuarioLogado;
import chat.foda.pra.caralho.telas.TelaServidor;

public class ServidorRemotoImpl extends UnicastRemoteObject implements ServidorRemoto {

	/**
	 * Classe que implementa as ações do Servidor
	 * 
	 * @author luiznazari
	 */
	private static final long serialVersionUID = -8382898850011577230L;
	
	private GerenciadorDoBanco banco = new GerenciadorDoBanco("BancoDeDados");
	private TelaServidor telaServidor;
	private Map<String, ClienteRemoto> clientesConectados = new HashMap<String, ClienteRemoto>();
	private Map<Integer, ArrayList<ClienteRemoto>> chatsAbertos = new HashMap<Integer, ArrayList<ClienteRemoto>>();
	private Integer autoIncrementChatId = 0;
	
	public ServidorRemotoImpl() throws RemoteException {
		super();
	}
	
	public void setTelaServidor(TelaServidor telaServidor) {
		this.telaServidor = telaServidor;
	}
	
	public Integer getNumeroUsuariosLogados() {
		return clientesConectados.size();
	}
	
	public void enviarMensagemParaTodosClientes(String mensagem) throws RemoteException {
		for(ClienteRemoto cliente : clientesConectados.values()) {
			cliente.enviarParaTodos(mensagem);
		}		
	}
	
	@Override
	public void enviarMensagemParaServidor(Integer chatCodigo, String mensagem) throws RemoteException {
		for (ClienteRemoto cliente : chatsAbertos.get(chatCodigo)) {
			cliente.enviarMensagem(chatCodigo, mensagem);
		}
	}
	
	@Override
	public synchronized UsuarioLogado login(ClienteRemoto cliente, String nome, String senha) throws RemoteException {
		banco.abrir();
		Usuario usuario = banco.getUsuario(nome);
		if (usuario != null && !clientesConectados.containsKey(nome)) {
			if (usuario.getSenha().equals(senha)) {
				UsuarioLogado usuarioLogado = new UsuarioLogado(usuario);
				clientesConectados.put(usuario.getNomeCompleto(), cliente);
				telaServidor.escreverNoConsole("[" + new LocalTime() + "] O usuário '" + usuarioLogado.getUsuario().getNomeCompleto() + "' se conectou.");
				telaServidor.atualizaContador(this.getNumeroUsuariosLogados().toString());
				banco.fechar();
				return usuarioLogado;
			}
		}
		banco.fechar();
		return null;
	}

	@Override
	public void logout(ClienteRemoto cliente, String nome) throws RemoteException {
		clientesConectados.remove(nome);
		telaServidor.escreverNoConsole("[" + new LocalTime() + "] O usuário '" + nome + "' se desconectou.");
		telaServidor.atualizaContador(this.getNumeroUsuariosLogados().toString());
	}
	
	@Override
	public synchronized boolean cadastrarUsuario(String nome, String senha) throws RemoteException {
		banco.abrir();
		boolean b;
		if (banco.getUsuario(nome) == null) {
			banco.salvar(new Usuario(nome, senha));
			b = true;
		} else {
			b = false;
		}
		banco.fechar();
		return b;
	}
	
	@Override
	public synchronized void removerUsuario(String nome) throws RemoteException {
		banco.abrir();
		banco.remover(banco.getUsuario(nome));
		banco.fechar();
	}

	@Override
	public synchronized boolean adicionaAmigo(Usuario usuario, String nomeAmigo) throws RemoteException {
		banco.abrir();
		Usuario novoAmigo = banco.getUsuario(nomeAmigo);
		if (novoAmigo != null) {
			banco.remover(banco.getUsuario(usuario.getNomeCompleto()));
			usuario.adicionaAmigo(nomeAmigo);
			banco.salvar(usuario);
			banco.fechar();
			return true;
		}
		banco.fechar();
		return false;
	}
	
	@Override
	public synchronized void removerAmigo(Usuario usuario, String nomeAmigo) throws RemoteException {
		banco.abrir();
		Usuario usuarioAmigo = banco.getUsuario(nomeAmigo);
		if (usuarioAmigo != null) {
			banco.remover(banco.getUsuario(usuario.getNomeCompleto()));
			usuario.removeAmigo(nomeAmigo);
			banco.salvar(usuario);
		}
		banco.fechar();
	}
	
	@Override
	public synchronized Chat criarChat(Usuario solicitante, String nomeAmigo) {
		banco.abrir();
		Usuario amigo = banco.getUsuario(nomeAmigo);
		banco.fechar();
		if (amigo != null && clientesConectados.containsKey(nomeAmigo)) {
			Chat novoChat = new Chat(autoIncrementChatId++);
			novoChat.adicionaUsuario(solicitante);
			novoChat.adicionaUsuario(amigo);
			
			ClienteRemoto clienteAmigo = clientesConectados.get(nomeAmigo);
			try {
				clienteAmigo.abrirChat(novoChat, solicitante.getNomeCompleto());
			} catch (RemoteException e) {
				e.printStackTrace();
				return null;
			}
			
			chatsAbertos.put(novoChat.getCodigo(), new ArrayList<ClienteRemoto>(
					Arrays.asList(clientesConectados.get(solicitante.getNomeCompleto()), clienteAmigo)));
			return novoChat;
		}
		return null;
	}

	@Override
	public synchronized void atualizarNickname(String nomeUsuario, String novoNickname) throws RemoteException {
		banco.abrir();
		
		Usuario usuario = banco.getUsuario(nomeUsuario);
		banco.remover(usuario);
		usuario.setNickName(novoNickname);
		banco.salvar(usuario);
		
		banco.fechar();
	}
	
}
