package chat.foda.pra.caralho.clienteServidor;

import java.rmi.Naming;

import chat.foda.pra.caralho.modelo.UsuarioLogado;
import chat.foda.pra.caralho.rmi.ClienteRemotoImpl;
import chat.foda.pra.caralho.rmi.ServidorRemoto;
import chat.foda.pra.caralho.telas.TelaCliente;

public class ClienteRmi {
	
	private ServidorRemoto service;
	private UsuarioLogado usuarioLogado;
	private ClienteRemotoImpl clienteService;
	
	public ClienteRmi(String ip) {
		try {
			service = (ServidorRemoto) Naming.lookup("rmi://" + ip + ":5000/remoto");
			
			clienteService = new ClienteRemotoImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ServidorRemoto getService() {
		return this.service;
	}
	
	public ClienteRemotoImpl getClienteService() {
		return this.clienteService;
	}

	public UsuarioLogado getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(UsuarioLogado usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
}
