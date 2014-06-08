package chat.foda.pra.caralho.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ClienteRemotoImpl extends UnicastRemoteObject implements ClienteRemoto {

	/**
	 * Classe que implementa as ações do Servidor
	 * 
	 * @author luiznazari
	 */
	private static final long serialVersionUID = -8382898850011577230L;

	private HashMap<String, String> usuarios = new HashMap<>();
	private List<String> usuariosLogados = new ArrayList<String>();
	
	public ClienteRemotoImpl() throws RemoteException {
		super();
		
		usuarios.put("Luiz", "123");
		usuarios.put("Alessandro", "123");
	}
	
	@Override
	public String enviaMensagem(String mensagem) throws RemoteException {
		System.out.println(mensagem);
		return mensagem;
	}
	
	@Override
	public boolean login(String nome, String senha) throws RemoteException {
		if (usuarios.containsKey(nome)) {
			if (usuarios.get(nome).equals(senha)) {
				usuariosLogados.add(nome);
				return true;
			}
		}
		return false;
	}
	
	public Integer getNumeroUsuariosLogados() {
		return this.usuariosLogados.size();
	}

	@Override
	public boolean cadastrarUsuario(String nome, String senha) throws RemoteException {
		if (!usuarios.containsKey(nome)) {
			usuarios.put(nome, senha);
			return true;
		}
		return false;
	}

}
