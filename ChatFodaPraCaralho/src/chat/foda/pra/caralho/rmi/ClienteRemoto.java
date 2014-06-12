package chat.foda.pra.caralho.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import chat.foda.pra.caralho.modelo.UsuarioLogado;

/**
 * Declaração de todos os métodos executados no servidor visíveis para o Cliente
 * 
 * @author luiznazari
 */
public interface ClienteRemoto extends Remote {
	
	public UsuarioLogado login(String nome, String senha) throws RemoteException;
	
	public boolean cadastrarUsuario(String nome, String senha) throws RemoteException;
	
	public String enviaMensagem(String mensagem) throws RemoteException;
}
