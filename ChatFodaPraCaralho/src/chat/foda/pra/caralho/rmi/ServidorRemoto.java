package chat.foda.pra.caralho.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Set;

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
	
	public boolean cadastrarUsuario(Usuario usuario) throws RemoteException;
	
	public void removerUsuario(Usuario usuario) throws RemoteException;
	
	public void enviarMensagemParaServidor(Long chatCodigo, String mensagem) throws RemoteException;
	
	public void logout(ArrayList<Long> codigos, ClienteRemoto cliente, String nome) throws RemoteException;
	
	public void adicionaAmigo(Long codUsuario, Long codAmigo) throws RemoteException;
	
	public void removerAmigo(Long codUsuario, Long codAmigo) throws RemoteException;
	
	public Chat criarChat(Usuario solicitante, Long codAmigo) throws RemoteException;
	
	public void trocarSenha(Long codigo, String novaSenha) throws RemoteException;
	
	public void trocarNickname(Long codigo, String novoNickname) throws RemoteException;
	
	public Set<Usuario> getUsuariosDesconhecidos(Long codUsuario) throws RemoteException;
}
