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
	public ServidorRmi() {
		try {
			ClienteRemoto remoto = new ClienteRemotoImpl();
			//Naming.rebind("rmi://172.18.33.99:5000/remoto", remoto);
			Naming.rebind("rmi://192.168.1.101:5000/remoto", remoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ServidorRmi();
	}
}
