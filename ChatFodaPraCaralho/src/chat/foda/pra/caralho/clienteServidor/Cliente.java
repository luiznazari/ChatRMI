package chat.foda.pra.caralho.clienteServidor;

import chat.foda.pra.caralho.modelo.Chat;

public interface Cliente {
	
	void enviaMensagem(Chat chat, String mensagem);
	
	String recebeMensagem();
	
}
