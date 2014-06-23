package chat.foda.pra.caralho.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import chat.foda.pra.caralho.modelo.Chat;
import chat.foda.pra.caralho.telas.TelaCliente;

/**
 * Classe que implementa as ações do Cliente
 * 
 * @author luiznazari
 */
public class ClienteRemotoImpl extends UnicastRemoteObject implements ClienteRemoto {
	private static final long serialVersionUID = 6754107487405504158L;
	
	private TelaCliente telaCliente;
	
	public ClienteRemotoImpl() throws RemoteException {
		super();
	}
	
	@Override
	public void enviarMensagem(Integer chatCodigo, String mensagem) throws RemoteException {
		telaCliente.enviarParaChat(chatCodigo, mensagem);
	}

	@Override
	public void enviarParaTodos(String mensagem) throws RemoteException {
		telaCliente.enviarParaTodos(mensagem);
	}
	
	@Override
	public void abrirChat(Chat chat, String nomeAmigo) throws RemoteException {
		telaCliente.iniciarChatExistente(chat, nomeAmigo);
	}
	
	@Override
	public void desativarChat(Integer chatCodigo) throws RemoteException {
		telaCliente.desativarChat(chatCodigo);	
	}

	@Override
	public void desativarTodosChats() throws RemoteException {
		telaCliente.desativarTodosChats();
	}

	public TelaCliente getTelaCliente() {
		return telaCliente;
	}

	public void setTelaCliente(TelaCliente telaCliente) {
		this.telaCliente = telaCliente;
	}

}
