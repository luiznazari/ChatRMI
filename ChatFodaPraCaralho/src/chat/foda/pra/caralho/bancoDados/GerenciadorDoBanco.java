package chat.foda.pra.caralho.bancoDados;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oRecoverableException;

import chat.foda.pra.caralho.modelo.Usuario;

public class GerenciadorDoBanco {

	private ObjectContainer db;
	private String nomeBanco;
	private Integer autoIncrement;

	public GerenciadorDoBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco + ".db4o";
		this.autoIncrement = this.getUltimoCodigo()+1;
	}
	
	public void abrir() {
		this.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
				this.nomeBanco);
	}

	public void fechar() {
		this.db.close();
	}
	
	public ObjectSet<Usuario> getListaUsuarios() {
		try {
			ObjectSet<Usuario> lista = db.query(Usuario.class);	
			return lista;
		} catch(Db4oRecoverableException e) {
			return null;
		}
	}

	public Usuario getUsuario(Integer codigo) {
		try {
			Usuario usuario = new Usuario(codigo);
			ObjectSet<Usuario> usuarioBanco = db.queryByExample(usuario);
			return usuarioBanco.get(0);
		} catch(Db4oRecoverableException e) {
			return null;
		}
	}

	public Usuario getUsuario(String nome) {
		try {
			Usuario usuario = new Usuario();
			usuario.setNomeCompleto(nome);
			ObjectSet<Usuario> usuarioBanco = db.queryByExample(usuario);
			return usuarioBanco.get(0);
		} catch(Db4oRecoverableException e) {
			return null;
		}
	}

	public void salvar(Object objeto) {
		this.db.store(objeto);
	}
	
	public void remover(Object objeto) {
		this.db.delete(objeto);
	}
	
	public void salvarUsuario(Usuario usuario) {
		usuario.setCodigo(autoIncrement++);
		this.db.store(usuario);
	}

	public Integer getQuantidadeUsuarios() {
		return this.getListaUsuarios().size();
	}
	
	public Integer getUltimoCodigo() {
		try {
			ObjectSet<Usuario> lista = getListaUsuarios();
			return lista.get(lista.size()-1).getCodigo();
		} catch (NullPointerException e) {
			return 0;
		}
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public static void main(String[] args) {
		//GerenciadorDoBanco b = new GerenciadorDoBanco("BancoTeste");
		GerenciadorDoBanco b = new GerenciadorDoBanco("BancoDeDados");
		b.abrir();
		for (Usuario u : b.getListaUsuarios()) {
			System.out.println(u.getCodigo() + " | Nome: " + u.getNomeCompleto() + " | Senha: " + u.getSenha());
		}	
		b.fechar();
	}
}
