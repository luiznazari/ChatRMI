package chat.foda.pra.caralho.telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import chat.foda.pra.caralho.models.Usuario;

public class TelaListaAmigos {
	
	/* - Elementos da Tela - */
	
	private JDialog jdgJanelaAmigos;
	
	private JPanel jpnPrincipal;
	
	private JLabel jlbLista;
	
	private JList<Usuario> jlstUsuarios;
	
	private DefaultListModel<Usuario> dlmUsuarios;
	
	private JScrollPane jspUsuarios;
	
	private JPanel jpnBotoes;
	
	private JButton jbtConfirmar;
	
	private JButton jbtCancelar;
	
	private TelaCliente telaCliente;
	
	/* --------------------- */
	
	private Usuario amigoToAdd;
	
	private String titulo;
	
	/**
	 * Utilizado apenas quando a tela é instanciada com uma lista pré-definida de usuários.
	 */
	private Set<Usuario> amigosToAdd;
	
	public TelaListaAmigos(TelaCliente telaCliente) {
		this.telaCliente = telaCliente;
		
		criaJDialog();
	}
	
	public TelaListaAmigos(TelaCliente telaCliente, Set<Usuario> usuarios) {
		this.telaCliente = telaCliente;
		this.amigosToAdd = usuarios;
		
		criaJDialog();
	}
	
	public TelaListaAmigos(TelaCliente telaCliente, Set<Usuario> usuarios, String titulo) {
		this.telaCliente = telaCliente;
		this.amigosToAdd = usuarios;
		this.titulo = titulo;
		
		criaJDialog();
	}
	
	private void criaJDialog() {
		jdgJanelaAmigos = new JDialog();
		jdgJanelaAmigos.setTitle(titulo == null ? "Adicionar amigo" : titulo);
		jdgJanelaAmigos.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jdgJanelaAmigos.setContentPane(getPanel());
		jdgJanelaAmigos.setModal(true);
		jdgJanelaAmigos.setSize(220, 330);
		jdgJanelaAmigos.setLocationRelativeTo(null);
		jdgJanelaAmigos.setResizable(false);
		jdgJanelaAmigos.setVisible(true);
	}
	
	private JPanel getPanel() {
		jpnPrincipal = new JPanel(new BorderLayout());
		jpnPrincipal.setBorder(new EmptyBorder(10, 5, 5, 5));
		
		jlbLista = new JLabel("Lista de usuários:");
		jlbLista.setBorder(new EmptyBorder(0, 50, 5, 0));
		jlbLista.setFont(new Font("Tahoma", Font.PLAIN, 13));
		jpnPrincipal.add(jlbLista, BorderLayout.NORTH);
		
		jlstUsuarios = new JList<Usuario>();
		jspUsuarios = new JScrollPane(jlstUsuarios);
		jpnPrincipal.add(jspUsuarios, BorderLayout.CENTER);
		
		jpnPrincipal.add(getPanelBotoes(), BorderLayout.SOUTH);
		
		jlstUsuarios.setModel(getListaUsuarios());
		
		return jpnPrincipal;
	}
	
	private JPanel getPanelBotoes() {
		jpnBotoes = new JPanel(new FlowLayout());
		
		jbtCancelar = new JButton("Cancelar");
		jpnBotoes.add(jbtCancelar);
		
		jbtConfirmar = new JButton("Confirmar");
		jpnBotoes.add(jbtConfirmar);
		
		jbtCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				jdgJanelaAmigos.dispose();
			}
		});
		
		jbtConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setAmigoToAdd(jlstUsuarios.getSelectedValue());
				jdgJanelaAmigos.dispose();
			}
		});
		
		return jpnBotoes;
	}
	
	private DefaultListModel<Usuario> getListaUsuarios() {
		dlmUsuarios = new DefaultListModel<Usuario>();
		
		// Caso não haja uma predefinição de usuários, pede ao servidor.
		if (amigosToAdd == null) {
			try {
				amigosToAdd = telaCliente.getCliente().getService()
				        .getUsuariosDesconhecidos(telaCliente.getCliente().getUsuarioLogado().getUsuario().getCodigo());
			} catch (RemoteException e) {
				jdgJanelaAmigos.dispose();
				JOptionPane.showMessageDialog(null, "Conexão - Erro ao listar usuários.");
				e.printStackTrace();
			}
		}
		
		// Se não encontrou nenhum amigo, desabilita os campos
		if (amigosToAdd.isEmpty()) {
			dlmUsuarios.addElement(new Usuario(0l, "Nenhum usuário encontrado."));
			jlstUsuarios.setEnabled(false);
			jbtConfirmar.setEnabled(false);
		}
		
		for (Usuario u : amigosToAdd) {
			dlmUsuarios.addElement(u);
		}
		
		return dlmUsuarios;
	}
	
	public Usuario getSelecionado() {
		return amigoToAdd;
	}
	
	public void setAmigoToAdd(Usuario amigoToAdd) {
		this.amigoToAdd = amigoToAdd;
	}
	
}
