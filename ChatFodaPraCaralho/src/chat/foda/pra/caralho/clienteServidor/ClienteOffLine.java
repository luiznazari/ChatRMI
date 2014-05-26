package chat.foda.pra.caralho.clienteServidor;

import chat.foda.pra.caralho.modelo.Chat;
import chat.foda.pra.caralho.modelo.Usuario;
import chat.foda.pra.caralho.telas.TelaCliente;

public class ClienteOffLine implements Cliente{
	
	private Usuario usuario;
	
	private ServidorOffLine servidor;
	
	public ClienteOffLine(Usuario usuario) {
		this.usuario = usuario;
		new TelaCliente(this);
	}

	@Override
	public void enviaMensagem(Chat chat, String mensagem) {
		
	}

	@Override
	public String recebeMensagem(Chat chat, String mensagem) {
		return null;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public ServidorOffLine getServidor() {
		return servidor;
	}

	public void setServidor(ServidorOffLine servidor) {
		this.servidor = servidor;
	}

	private class ServerListener implements Runnable {

		private String mensagem;
		
		@Override
		public void run() {
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
