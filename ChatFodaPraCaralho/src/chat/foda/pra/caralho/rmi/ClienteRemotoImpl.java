package chat.foda.pra.caralho.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import chat.foda.pra.caralho.telas.TelaCliente;

/**
 * Classe que implementa as a��es do Cliente
 * 
 * @author luiznazari
 */
public class ClienteRemotoImpl extends UnicastRemoteObject implements ClienteRemoto {
	private static final long serialVersionUID = 6754107487405504158L;
	
	private TelaCliente telaCliente;
	
	protected ClienteRemotoImpl(TelaCliente telaCliente) throws RemoteException {
		super();
		
		this.telaCliente = telaCliente;
	}
	
	@Override
	public void enviarMensagem(Integer chatCodigo, String mensagem) throws RemoteException {
		telaCliente.getTelaChat(chatCodigo).recebeMensagem(mensagem);
	}

	@Override
	public void enviarParaTodos(String mensagem) throws RemoteException {
		telaCliente.enviarParaTodos(mensagem);
	}
}
