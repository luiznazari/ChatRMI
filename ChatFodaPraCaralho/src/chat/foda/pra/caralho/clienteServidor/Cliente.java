package chat.foda.pra.caralho.clienteServidor;

import chat.foda.pra.caralho.modelo.Usuario;

public class Cliente {
	
	private Usuario usuario;
	
	public Cliente(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
