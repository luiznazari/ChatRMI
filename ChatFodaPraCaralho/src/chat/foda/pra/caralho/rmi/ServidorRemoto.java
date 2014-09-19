package chat.foda.pra.caralho.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import chat.foda.pra.caralho.models.Chat;
import chat.foda.pra.caralho.models.Usuario;
import chat.foda.pra.caralho.models.UsuarioLogado;

/**
 * Declara��o de todos os m�todos executados no servidor vis�veis para o Cliente
 * 
 * @author luiznazari
 */
public interface ServidorRemoto extends Remote {
	
	public UsuarioLogado login(ClienteRemoto cliente, String email, String senha) throws RemoteException;
	
	public boolean cadastrarUsuario(Usuario usuario) throws RemoteException;
	
	public void removerUsuario(Usuario usuario) throws RemoteException;
	
	public void enviarMensagemParaServidor(Long chatCodigo, String mensagem) throws RemoteException;
	
	public void logout(ArrayList<Long> codigos, ClienteRemoto cliente, String nome) throws RemoteException;
	
	public boolean adicionaAmigo(Usuario usuario, Long codigoAmigo) throws RemoteException;
	
	public void removerAmigo(Usuario usuario, Long codigoAmigo) throws RemoteException;
	
	public Chat criarChat(Usuario solicitante, Long codigoAmigo) throws RemoteException;
	
	public void trocarSenha(Long codigo, String novaSenha) throws RemoteException;
	
	public void trocarNickname(Long codigo, String novoNickname) throws RemoteException;
}
