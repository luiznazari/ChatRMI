package chat.foda.pra.caralho.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import chat.foda.pra.caralho.bancoDados.HistoricoPorDia;

public class Chat implements Serializable {
	private static final long serialVersionUID = -6861975820907169849L;

	private Integer codigo;
	private List<Usuario> usuarios;
	private List<HistoricoPorDia> historico;
	
	public Chat() {
	}
	
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
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
