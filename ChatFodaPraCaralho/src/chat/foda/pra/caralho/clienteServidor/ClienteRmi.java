package chat.foda.pra.caralho.clienteServidor;

import java.rmi.Naming;

import chat.foda.pra.caralho.rmi.ClienteRemoto;

public class ClienteRmi {
	
	public static void main(String[] args) {		
		try {
			//ClienteRemoto service = (ClienteRemoto) Naming.lookup("rmi://172.18.33.99:5000/remoto");
			ClienteRemoto service = (ClienteRemoto) Naming.lookup("rmi://192.168.1.101:5000/remoto");
			
			System.out.println(service.escuta());			
			service.enviaMensagem("Oi");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
