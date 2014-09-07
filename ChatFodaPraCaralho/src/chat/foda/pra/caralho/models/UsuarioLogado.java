package chat.foda.pra.caralho.models;

import java.io.Serializable;

public class UsuarioLogado implements Serializable {
	private static final long serialVersionUID = 9008220796230266293L;
	
	private Usuario usuario;
	
	public UsuarioLogado(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
