package chat.foda.pra.caralho.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import chat.foda.pra.caralho.modelo.UsuarioLogado;

/**
 * Declaração de todos os métodos executados no servidor visíveis para o Cliente
 * 
 * @author luiznazari
 */
public interface ServidorRemoto extends Remote {
	
	public UsuarioLogado login(ClienteRemoto cliente, String nome, String senha) throws RemoteException;
	
	public boolean cadastrarUsuario(String nome, String senha) throws RemoteException;
	
	public void removerUsuario(String nome) throws RemoteException;
	
	public void enviarMensagemParaServidor(Integer chatCodigo, String mensagem) throws RemoteException;
	
	public void logout(ClienteRemoto cliente, String nome) throws RemoteException;
}
