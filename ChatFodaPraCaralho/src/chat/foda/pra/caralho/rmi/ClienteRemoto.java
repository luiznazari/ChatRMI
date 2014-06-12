package chat.foda.pra.caralho.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import chat.foda.pra.caralho.modelo.UsuarioLogado;

/**
 * Declara��o de todos os m�todos executados no servidor vis�veis para o Cliente
 * 
 * @author luiznazari
 */
public interface ClienteRemoto extends Remote {
	
	public UsuarioLogado login(String nome, String senha) throws RemoteException;
	
	public boolean cadastrarUsuario(String nome, String senha) throws RemoteException;
	
	public String enviaMensagem(String mensagem) throws RemoteException;
	
	public void logout(UsuarioLogado usuarioLogado) throws RemoteException;
}
