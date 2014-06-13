package chat.foda.pra.caralho.clienteServidor;

import java.rmi.Naming;

import chat.foda.pra.caralho.modelo.UsuarioLogado;
import chat.foda.pra.caralho.rmi.ServidorRemoto;

public class ClienteRmi {
	
	private ServidorRemoto service;
	private UsuarioLogado usuarioLogado;
	
	public ClienteRmi(String ip) {
		try {
			service = (ServidorRemoto) Naming.lookup("rmi://" + ip + ":5000/remoto");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ServidorRemoto getService() {
		return this.service;
	}

	public UsuarioLogado getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(UsuarioLogado usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
}
