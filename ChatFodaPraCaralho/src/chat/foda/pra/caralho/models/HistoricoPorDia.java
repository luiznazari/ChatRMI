package chat.foda.pra.caralho.models;

import java.util.List;

import java.time.LocalDate;

import chat.foda.pra.caralho.jdbc.Entidade;

public class HistoricoPorDia implements Entidade {
	
	private Long codigo;
	
	private LocalDate data;
	
	private List<String> conversas;
	
	private Chat chat;
	
	public HistoricoPorDia() {}
	
	public Chat getChat() {
		return chat;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public void setConversas(List<String> conversas) {
		this.conversas = conversas;
	}
	
	public void setChat(Chat chat) {
		this.chat = chat;
	}
	
	public HistoricoPorDia(Long codigo) {
		this.codigo = codigo;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public List<String> getConversas() {
		return conversas;
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
