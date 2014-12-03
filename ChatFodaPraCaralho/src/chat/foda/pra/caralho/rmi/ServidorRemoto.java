package chat.foda.pra.caralho.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import chat.foda.pra.caralho.models.Chat;
import chat.foda.pra.caralho.models.Usuario;
import chat.foda.pra.caralho.models.UsuarioLogado;

/**
 * Declaração de todos os métodos executados no Servidor, visíveis para o Cliente
 * 
 * @author luiznazari
 */
public interface ServidorRemoto extends Remote {
	
	/**
	 * Realiza o login de um usuário, com base no e-mail e senha e grava a conexão com o host do usuário
	 * (ClienteRemoto), nos registros do servidor.
	 * 
	 * @param cliente
	 * @param email
	 * @param senha
	 * @return UsuarioLogado contendo o usuário
	 * @throws RemoteException
	 */
	public UsuarioLogado login(ClienteRemoto cliente, String email, String senha) throws RemoteException;
	
	/**
	 * Cria um novo registro de usuário no sistema.
	 * 
	 * @param usuario
	 * @return true se foi possível cadastrar, false caso contrário
	 * @throws RemoteException
	 */
	public boolean cadastrarUsuario(Usuario usuario) throws RemoteException;
	
	/**
	 * Remove o cadastro de um usuário no sistema.
	 * 
	 * @param usuario
	 * @throws RemoteException
	 */
	public void removerUsuario(Usuario usuario) throws RemoteException;
	
	/**
	 * Utilizado pelos usuários. Envia uma mensagem para todos os clientes que possuem um chat com o código espeficiado.
	 * 
	 * @param chatCodigo
	 * @param mensagem
	 * @throws RemoteException
	 */
	public void enviarMensagemParaAmigos(Long chatCodigo, String mensagem) throws RemoteException;
	
	/**
	 * Fecha e/ou inativa os chats em que o usuário faz parte, baseado na quantidade de usuários presentes no chat.
	 * Remove os registros da conexão do usuário nos registros do servidor.
	 * 
	 * @param codigosChats
	 * @param cliente
	 * @param usuario
	 * @throws RemoteException
	 */
	public void logout(Set<Long> codigosChats, ClienteRemoto cliente, Usuario usuario) throws RemoteException;
	
	/**
	 * Adiciona um novo registro de amigos para os usuários especificados. Um usuário é amigo de outro, mas este pode
	 * não tê-lo como amigo.
	 * 
	 * @param codUsuario
	 * @param codAmigo
	 * @throws RemoteException
	 */
	public void adicionaAmigo(Long codUsuario, Long codAmigo) throws RemoteException;
	
	/**
	 * Remove o registro de amigos para os usuários especificados.
	 * 
	 * @param codUsuario
	 * @param codAmigo
	 * @throws RemoteException
	 */
	public void removerAmigo(Long codUsuario, Long codAmigo) throws RemoteException;
	
	/**
	 * Cria um novo chat entre os usuários especificados. O pedido de abertura de chat é enviado pelo usuário
	 * solicitante para o usuário amigo. Grava os registros do novo chat no servidor.
	 * 
	 * @param solicitante
	 * @param amigo
	 * @return Novo chat caso ambos usuários estiverem logados. Null caso o usuário amigo não estiver logado ou ocorrer
	 *         um erro na conexão.
	 * @throws RemoteException
	 */
	public Chat criarChat(Usuario solicitante, Usuario amigo) throws RemoteException;
	
	/**
	 * Adiciona um novo participante para um chat e atualiza os chats dos participantes atuais.
	 * 
	 * @param chat
	 * @param userToInvite
	 * @return Verdadeiro caso o usuário estiver conectado, falso caso contrário
	 * @throws RemoteException
	 */
	public boolean convidarParaChat(Chat chat, Usuario userToInvite) throws RemoteException;
	
	/**
	 * Fecha e/ou desativa os chats onde o usuário que está deslogando participa.
	 * Se há mais de dois usuários no chat, o mesmo não é excluído, apenas retira o usuário deslogado.
	 * 
	 * @param codChat
	 * @param cliente
	 *            ClienteRemoto do usuário que deslogou
	 * @param nome
	 *            Nome do usuário que deslogou
	 * @throws RemoteException
	 */
	public void fecharChat(Long codChat, ClienteRemoto cliente, Usuario usuario) throws RemoteException;
	
	/**
	 * Troca a senha para o usuário especificado.
	 * 
	 * @param codUsuario
	 * @param novaSenha
	 * @throws RemoteException
	 */
	public void trocarSenha(Long codUsuario, String novaSenha) throws RemoteException;
	
	/**
	 * Troca o nickname para o usuário especificado.
	 * 
	 * @param codUsuario
	 * @param novoNickname
	 * @throws RemoteException
	 */
	public void trocarNickname(Long codUsuario, String novoNickname) throws RemoteException;
	
	/**
	 * Procura todos os usuários que não possuem amizade com o usuário solicitante.
	 * 
	 * @param codUsuario
	 * @return Lista de usuários que não são amigos do usuário soliciante.
	 * @throws RemoteException
	 */
	public Set<Usuario> getUsuariosDesconhecidos(Long codUsuario) throws RemoteException;
}
