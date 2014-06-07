package chat.foda.pra.caralho.modelo;

public class UsuarioLogado {

	private Usuario usuario;
	private Chat chatReservado;
	
	public UsuarioLogado(Usuario usuario) {
		this.usuario = usuario;
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
