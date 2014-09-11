package chat.foda.pra.caralho.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.models.Pessoa;

public class PessoaJDBC implements PessoaDAO {
	
	private final String findAll = "select * from pessoa";
	
	private final String findByCodigo = "select * from pessoa p where p.codigo = ?";
	
	private final String save = "insert into pessoa(nome_completo, cpf, data_nascimento) values (?, ?, ?)";
	
	private final String update = "update pessoa set nome_completo = ?, cpf = ?, data_nascimento = ? where codigo = ?";
	
	private final String deleteByCodigo = "delete from pessoa p where p.codigo = ?";
	
	@Override
	public void save(Pessoa entidade) {
		QueryUtil.sqlParam(save, entidade.getNomeCompleto(), entidade.getCpf(), (entidade.getDataNascimento() == null ? null
		        : entidade.getDataNascimento().toString("yyyy-MM-dd")));
	}
	
	@Override
	public void delete(Pessoa entidade) {
		QueryUtil.sqlParam(deleteByCodigo, entidade.getCodigo().toString());
	}
	
	@Override
	public void update(Pessoa entidade) {
		QueryUtil.sqlParam(update, entidade.getNomeCompleto(), entidade.getCpf(), (entidade.getDataNascimento() == null ? null
		        : entidade.getDataNascimento().toString("yyyy-MM-dd")), entidade.getCodigo().toString());
	}
	
	@Override
	public List<Pessoa> findAll() {
		List<Pessoa> pessoas = new ArrayList<>();
		
		// TODO
		
		return pessoas;
	}
	
	@Override
	public Pessoa findOne(Long codigo) {
		QueryUtil.queryParam(findByCodigo, codigo.toString());
		List<String> valores;
		
		valores = QueryUtil.lerResult("nome_completo", "cpf", "data_nascimento");
		if (valores.size() == 0) {
			return null;
		}
		
		Pessoa pessoa = new Pessoa(codigo);
		pessoa.setNomeCompleto(valores.get(0));
		pessoa.setCpf(valores.get(1));
		pessoa.setDataNascimento(new LocalDate(valores.get(2)));
		
		return pessoa;
	}
	
}
