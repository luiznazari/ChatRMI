package chat.foda.pra.caralho.models;

import org.joda.time.LocalDate;

import chat.foda.pra.caralho.jdbc.Entidade;

public class HistoricoChat implements Entidade {
	
	private Long codigo;
	
	private LocalDate data;
	
	private String conversa;
	
	private Chat chat;
	
	public HistoricoChat() {}
	
	public Chat getChat() {
		return chat;
	}
	
	public String getConversa() {
		return conversa;
	}
	
	public void setConversa(String conversa) {
		this.conversa = conversa;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public void setChat(Chat chat) {
		this.chat = chat;
	}
	
	public HistoricoChat(Long codigo) {
		this.codigo = codigo;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	@Override
	public Long getCodigo() {
		return codigo;
	}
	
	@Override
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
}
