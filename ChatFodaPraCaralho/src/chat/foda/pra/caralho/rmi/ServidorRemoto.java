package chat.foda.pra.caralho.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import chat.foda.pra.caralho.models.Chat;
import chat.foda.pra.caralho.models.Usuario;
import chat.foda.pra.caralho.models.UsuarioLogado;

/**
 * Declaração de todos os métodos executados no servidor visíveis para o Cliente
 * 
 * @author luiznazari
 */
public interface ServidorRemoto extends Remote {
	
	public UsuarioLogado login(ClienteRemoto cliente, String email, String senha) throws RemoteException;
	
	public boolean cadastrarUsuario(String nome, String senha) throws RemoteException;
	
	public void removerUsuario(String nome) throws RemoteException;
	
	public void enviarMensagemParaServidor(Long chatCodigo, String mensagem) throws RemoteException;
	
	public void logout(ArrayList<Long> codigos, ClienteRemoto cliente, String nome) throws RemoteException;
	
	public boolean adicionaAmigo(Usuario usuario, String nomeAmigo) throws RemoteException;
	
	public void removerAmigo(Usuario usuario, String nomeAmigo) throws RemoteException;
	
	public Chat criarChat(Usuario solicitante, String nomeAmigo) throws RemoteException;
	
	public void atualizarNickname(String nomeUsuario, String novoNickname) throws RemoteException;

	boolean cadastrarUsuario(String nome, String senha, String email) throws RemoteException;
}
