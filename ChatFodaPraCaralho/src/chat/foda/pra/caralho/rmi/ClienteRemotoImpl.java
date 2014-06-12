package chat.foda.pra.caralho.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import chat.foda.pra.caralho.bancoDados.GerenciadorDoBanco;
import chat.foda.pra.caralho.modelo.Chat;
import chat.foda.pra.caralho.modelo.Usuario;
import chat.foda.pra.caralho.modelo.UsuarioLogado;

public class ClienteRemotoImpl extends UnicastRemoteObject implements ClienteRemoto {

	/**
	 * Classe que implementa as ações do Servidor
	 * 
	 * @author luiznazari
	 */
	private static final long serialVersionUID = -8382898850011577230L;
	
	private GerenciadorDoBanco banco = new GerenciadorDoBanco("BancoDeDados");
	private HashMap<UsuarioLogado, ArrayList<Chat>> usuariosLogados;
	
	public ClienteRemotoImpl() throws RemoteException {
		super();
		usuariosLogados = new HashMap<>();		
	}
	
	@Override
	public String enviaMensagem(String mensagem) throws RemoteException {
		System.out.println(mensagem);
		return mensagem;
	}
	
	@Override
	public UsuarioLogado login(String nome, String senha) throws RemoteException {
		banco.abrir();
		Usuario usuario = banco.getUsuario(nome);
		if (usuario != null) {
			if (usuario.getSenha().equals(senha)) {
				UsuarioLogado usuarioLogado = new UsuarioLogado(usuario);
				usuariosLogados.put(usuarioLogado, new ArrayList<Chat>());
				return usuarioLogado;
			}
		}
		banco.fechar();
		return null;
	}
	
	public Integer getNumeroUsuariosLogados() {
		return usuariosLogados.size();
	}

	@Override
	public boolean cadastrarUsuario(String nome, String senha) throws RemoteException {
		banco.abrir();
		boolean b;
		if (banco.getUsuario(nome) == null) {
			banco.salvar(new Usuario(nome, senha));
			b = true;
		} else {
			b = false;
		}
		banco.fechar();
		return b;
	}

}
