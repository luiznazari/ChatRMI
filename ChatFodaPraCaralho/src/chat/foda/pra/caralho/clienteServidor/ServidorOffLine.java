package chat.foda.pra.caralho.clienteServidor;

import java.util.ArrayList;
import java.util.List;

import chat.foda.pra.caralho.modelo.Chat;

public class ServidorOffLine {

	private List<Cliente> clientes;
	
	public synchronized void enviaMensagem(Chat chat, String mensagem) {
		
	}
	
	public void adicionaCliente(ClienteOffLine cliente) {
		if (clientes == null) {
			clientes = new ArrayList<Cliente>();
		}
		
		cliente.setServidor(this);
		clientes.add(cliente);
	}
	
	public boolean removeCliente(Cliente cliente) {
		try {
			clientes.remove(cliente);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}
	
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
}
