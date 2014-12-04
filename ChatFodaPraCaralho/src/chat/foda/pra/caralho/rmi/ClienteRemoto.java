package chat.foda.pra.caralho.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import chat.foda.pra.caralho.models.Chat;
import chat.foda.pra.caralho.models.Usuario;

/**
 * Declaração de todos os métodos executados no Cliente, visíveis para o Servidor
 * 
 * @author luiznazari
 */
public interface ClienteRemoto extends Remote {
	
	/**
	 * Envia a mensagem para o cliente, a mesma será entregue para o chat que possuir o código especificado.
	 * 
	 * @param codChat
	 *            Chat destino
	 * @param mensagem
	 * @throws RemoteException
	 */
	public void enviarMensagem(Long codChat, String mensagem) throws RemoteException;
	
	/**
	 * Envia a mensagem para todos os chats abertos que o cliente possui.
	 * 
	 * @param mensagem
	 * @throws RemoteException
	 */
	public void enviarParaTodos(String mensagem) throws RemoteException;
	
	/**
	 * Abre um novo chat para o cliente.
	 * 
	 * @param chat
	 * @throws RemoteException
	 */
	public void abrirChat(Chat chat) throws RemoteException;
	
	/**
	 * Inativa um chat do cliente que possua o código especificado. Não será mais possível enviar mensagens através do
	 * mesmo, a menos que seja reativado.
	 * 
	 * @param codChat
	 * @param mensagem
	 * @throws RemoteException
	 */
	public void desativarChat(Long codChat, String mensagem) throws RemoteException;
	
	/**
	 * Inativa todos os chats que o cliente possui.
	 * 
	 * @throws RemoteException
	 */
	public void desativarTodosChats() throws RemoteException;
	
	/**
	 * Adiciona um participante para o chat, enviando uma mensagem para notificar e atualiza o chat e a respectiva tela
	 * do cliente.
	 * 
	 * @param chat
	 * @throws RemoteException
	 */
	public void atualizaChat(Chat chat, String mensagem) throws RemoteException;
	
	/**
	 * Remove um participante do chat, enviando uma mensagem para notificar e atualiza o chat e a respectiva tela do
	 * cliente.
	 * 
	 * @param codChat
	 * @param usuario
	 * @throws RemoteException
	 */
	public void removeAmigoDoChat(Long codChat, Usuario usuario, String mensagem) throws RemoteException;
	
}
