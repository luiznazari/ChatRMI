package chat.foda.pra.caralho.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClienteRemotoImpl extends UnicastRemoteObject implements ClienteRemoto {

	/**
	 * Classe que implementa as ações do Servidor
	 * 
	 * @author luiznazari
	 */
	private static final long serialVersionUID = -8382898850011577230L;

	public ClienteRemotoImpl() throws RemoteException {
		super();
	}
	
	public String escuta() {
		return "Servidor disse: Funcionou!!";
	}

	@Override
	public void enviaMensagem(String mensagem) throws RemoteException {
		System.out.println("Cliente disse: " + mensagem);
	}

}
