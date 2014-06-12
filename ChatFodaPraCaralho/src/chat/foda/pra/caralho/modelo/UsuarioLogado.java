package chat.foda.pra.caralho.modelo;

import java.io.Serializable;

public class UsuarioLogado implements Serializable {
	private static final long serialVersionUID = 9008220796230266293L;
	
	private Usuario usuario;
	private Chat chatReservado;
	
	public UsuarioLogado(Usuario usuario) {
		this.usuario = usuario;
		this.chatReservado = new Chat();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Chat getChatReservado() {
		return chatReservado;
	}

	public void setChatReservado(Chat chatReservado) {
		this.chatReservado = chatReservado;
	}
	
}
