package chat.foda.pra.caralho.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.joda.time.LocalTime;

import chat.foda.pra.caralho.bancoDados.GerenciadorDoBanco;
import chat.foda.pra.caralho.modelo.Usuario;
import chat.foda.pra.caralho.modelo.UsuarioLogado;

public class ServidorRemotoImpl extends UnicastRemoteObject implements ServidorRemoto {

	/**
	 * Classe que implementa as ações do Servidor
	 * 
	 * @author luiznazari
	 */
	private static final long serialVersionUID = -8382898850011577230L;
	
	private GerenciadorDoBanco banco = new GerenciadorDoBanco("BancoDeDados");
	private HashSet<ClienteRemoto> clientesConectados = new HashSet<ClienteRemoto>();
	
	public ServidorRemotoImpl() throws RemoteException {
		super();
	}
	
	public Integer getNumeroUsuariosLogados() {
		return clientesConectados.size();
	}
	
	@Override
	public void enviarMensagemParaServidor(Integer chatCodigo, String mensagem) throws RemoteException {
		for(ClienteRemoto cliente : clientesConectados) {
			cliente.enviarParaTodos(mensagem);
		}
	}
	
	@Override
	public synchronized UsuarioLogado login(ClienteRemoto cliente, String nome, String senha) throws RemoteException {
		banco.abrir();
		Usuario usuario = banco.getUsuario(nome);
		if (usuario != null) {
			if (usuario.getSenha().equals(senha)) {
				UsuarioLogado usuarioLogado = new UsuarioLogado(usuario);
				System.out.println("[" + new LocalTime() + "] O usuário '" + usuarioLogado.getUsuario().getNomeCompleto() + "' se conectou.");
				banco.fechar();
				clientesConectados.add(cliente);
				return usuarioLogado;
			}
		}
		banco.fechar();
		return null;
	}

	@Override
	public void logout(ClienteRemoto cliente, String nome) throws RemoteException {
		clientesConectados.remove(cliente);
		System.out.println("[" + new LocalTime() + "] O usuário '" + nome + "' se desconectou.");
	}
	
	@Override
	public synchronized boolean cadastrarUsuario(String nome, String senha) throws RemoteException {
		banco.abrir();
		boolean b;
		if (banco.getUsuario(nome) == null) {
			banco.salvarUsuario(new Usuario(nome, senha));
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
			usuario.adicionaAmigo(novoAmigo);
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
		Usuario novoAmigo = banco.getUsuario(nomeAmigo);
		if (novoAmigo != null) {
			usuario.removeAmigo(novoAmigo);
			banco.salvar(usuario);
		}
		banco.fechar();
	}
	
}
