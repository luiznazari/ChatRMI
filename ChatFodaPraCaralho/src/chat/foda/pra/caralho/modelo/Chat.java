package chat.foda.pra.caralho.modelo;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import chat.foda.pra.caralho.service.HistoricoPorDia;

public class Chat {

	private Integer codigo;
	private List<Usuario> usuarios;
	private List<HistoricoPorDia> historico;
	
	public void adicionaUsuario(Usuario usuario) {
		if (this.usuarios == null) {
			usuarios = new ArrayList<Usuario>();
		}
		
		usuarios.add(usuario);
	}
	
	public boolean removeUsuario(Usuario usuario) {
		if (this.usuarios == null) {
			return false;
		}
		
		usuarios.remove(usuario);
		return true;
	}

	public HistoricoPorDia procuraHistoricoPorDia(LocalDate data) {
		for (HistoricoPorDia h : historico) {
			if (h.getData().equals(data)) {
				return h;
			}
		}
		return null;
	}
	
	public Integer getCodigo() {
		return codigo;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<HistoricoPorDia> getHistorico() {
		return historico;
	}
}
