package chat.foda.pra.caralho.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.models.Pessoa;

public class PessoaJDBC implements PessoaDAO {
	
	private final String findByCodigo = "select * from pessoa p where p.codigo = ?";
	
	private final String save = "insert into pessoa(nome_completo, cpf, data_nascimento) values (?, ?, ?)";
	
	private final String update = "update pessoa set nome_completo = ?, cpf = ?, data_nascimento = ? where codigo = ?";
	
	private final String deleteByCodigo = "delete from pessoa where codigo = ?";
	
	@Override
	public Pessoa save(Pessoa pessoa) {
		Long codigo = null;
		
		try {
			codigo = QueryUtil.sqlParamReturnKey(save, pessoa.getNomeCompleto(), pessoa.getCpf(),
			        (pessoa.getDataNascimento() == null ? null : pessoa.getDataNascimento().toString("yyyy-MM-dd")));
		} catch (SQLException e) {
			delete(new Pessoa(codigo));
		}
		
		pessoa.setCodigo(codigo);
		
		return pessoa;
	}
	
	@Override
	public void delete(Pessoa pessoa) {
		if (pessoa.getCodigo() != null) {
			QueryUtil.sqlParam(deleteByCodigo, pessoa.getCodigo().toString());
		}
	}
	
	@Override
	public void update(Pessoa pessoa) {
		QueryUtil.sqlParam(update, pessoa.getNomeCompleto(), pessoa.getCpf(), (pessoa.getDataNascimento() == null ? null : pessoa
		        .getDataNascimento().toString("yyyy-MM-dd")), pessoa.getCodigo().toString());
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
