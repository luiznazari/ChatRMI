package chat.foda.pra.caralho.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.LocalDate;

import chat.foda.pra.caralho.dao.PessoaDAO;
import chat.foda.pra.caralho.models.Pessoa;

/**
 * @author Luiz Felipe Nazari
 */
public class PessoaJDBC implements PessoaDAO {
	
	private final String findAll = "select * from pessoa where codigo > ?";
	
	private final String findByCodigo = "select * from pessoa p where p.codigo = ?";
	
	private final String save = "insert into pessoa(nome_completo, data_nascimento) values (?, ?)";
	
	private final String update = "update pessoa set nome_completo = ?, data_nascimento = ? where codigo = ?";
	
	private final String deleteByCodigo = "delete from pessoa where codigo = ?";
	
	@Override
	public Pessoa save(Pessoa pessoa) {
		Long codigo = null;
		
		try {
			codigo = QueryUtil.sqlParamReturnKey(save, pessoa.getNomeCompleto(), (pessoa.getDataNascimento() == null ? null
					: pessoa.getDataNascimento().toString("yyyy-MM-dd")));
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
		QueryUtil.sqlParam(update, pessoa.getNomeCompleto(), (pessoa.getDataNascimento() == null ? null : pessoa
				.getDataNascimento().toString("yyyy-MM-dd")), pessoa.getCodigo().toString());
	}
	
	@Override
	public List<Pessoa> findAll() {
		List<Pessoa> pessoas = new ArrayList<>();
		QueryUtil.queryParam(findAll, "0");
		
		List<HashMap<String, String>> listaValores = QueryUtil.lerAllResult("codigo", "nome_completo", "data_nascimento");
		for (HashMap<String, String> valores : listaValores) {
			pessoas.add(criaPessoa(valores));
		}
		
		return pessoas;
	}
	
	@Override
	public Pessoa findOne(Long codigo) {
		QueryUtil.queryParam(findByCodigo, codigo.toString());
		HashMap<String, String> valores;
		
		valores = QueryUtil.lerResult("codigo", "nome_completo", "data_nascimento");
		if (valores.size() == 0) {
			return null;
		}
		
		return criaPessoa(valores);
	}
	
	private Pessoa criaPessoa(HashMap<String, String> valores) {
		Pessoa pessoa = new Pessoa(Long.valueOf(valores.get("codigo")));
		pessoa.setNomeCompleto(valores.get("nome_completo"));
		pessoa.setDataNascimento(new LocalDate(valores.get("data_nascimento")));
		
		return pessoa;
	}
}
