package chat.foda.pra.caralho.clienteServidor;

import java.rmi.Naming;

import chat.foda.pra.caralho.modelo.UsuarioLogado;
import chat.foda.pra.caralho.rmi.ClienteRemoto;

public class ClienteRmi {
	
	private ClienteRemoto service;
	private UsuarioLogado usuarioLogado;
	
	public ClienteRmi(String ip) {
		try {
			service = (ClienteRemoto) Naming.lookup("rmi://" + ip + ":5000/remoto");
						
			service.enviaMensagem("Oi");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ClienteRemoto getService() {
		return this.service;
	}

	public UsuarioLogado getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(UsuarioLogado usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
}
