package chat.foda.pra.caralho.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import chat.foda.pra.caralho.modelo.Chat;

/**
 * Declaração de todos os métodos executados no Cliente visíveis para o Servidor
 * 
 * @author luiznazari
 */
public interface ClienteRemoto extends Remote {

	public void enviarMensagem(Integer chatCodigo, String mensagem) throws RemoteException;
	
	public void enviarParaTodos(String mensagem) throws RemoteException;
	
}
