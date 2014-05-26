package chat.foda.pra.caralho.bancoDados;

import java.util.List;

import org.joda.time.LocalDate;

public class HistoricoPorDia {
	
	private Integer codigo;
	private LocalDate data;
	private List<String> conversas;
	
	public Integer getCodigo() {
		return codigo;
	}
	public LocalDate getData() {
		return data;
	}
	public List<String> getConversas() {
		return conversas;
	}
}
