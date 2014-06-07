package chat.foda.pra.caralho.clienteServidor;

import java.rmi.Naming;

import chat.foda.pra.caralho.rmi.ClienteRemoto;
import chat.foda.pra.caralho.rmi.ClienteRemotoImpl;

public class ServidorRmi {

	/*
	 * Para rodar o server RMI precisa dar o comando "rmiregistry" na classe de implementação:
	 * rmiregistry 5000 chat.foda.pra.caralho.ClienteRemotoImpl
	 * 
	 * Para gerar um novo STUB:
	 * rmic chat.foda.pra.caralho.ClienteRemotoImpl
	 */
	
	private ClienteRemotoImpl service;
	
	public ServidorRmi(String ip) {
		try {
			service = new ClienteRemotoImpl();
			Naming.rebind("rmi://" + ip + ":5000/remoto", service);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ClienteRemotoImpl getService() {
		return service;
	}
}
