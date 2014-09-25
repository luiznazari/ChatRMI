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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import chat.foda.pra.caralho.clienteServidor.ClienteRmi;
import chat.foda.pra.caralho.models.Usuario;

import com.alee.laf.WebLookAndFeel;

public class TelaListaAmigos {
	
	private static JDialog jdgJanelaAmigos;
	
	private static JPanel jpnPrincipal;
	
	private static JLabel jlbLista;
	
	private static JList<Usuario> jlstUsuarios;
	
	private static DefaultListModel<Usuario> dlmUsuarios;
	
	private static JScrollPane jspUsuarios;
	
	private static JPanel jpnBotoes;
	
	private static JButton jbtAdicionar;
	
	private static JButton jbtCancelar;
	
	private static ClienteRmi cliente;
	
	public static void getTela(ClienteRmi cliente) {
		TelaListaAmigos.cliente = cliente;
		
		jdgJanelaAmigos = new JDialog();
		
		jdgJanelaAmigos.setTitle("Adicionar amigo");
		jdgJanelaAmigos.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jdgJanelaAmigos.setContentPane(getPanel());
		jdgJanelaAmigos.setModal(true);
		jdgJanelaAmigos.setSize(220, 330);
		jdgJanelaAmigos.setLocationRelativeTo(null);
		jdgJanelaAmigos.setResizable(false);
		jdgJanelaAmigos.setVisible(true);
	}
	
	public static JPanel getPanel() {
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
	
	private static JPanel getPanelBotoes() {
		jpnBotoes = new JPanel(new FlowLayout());
		
		jbtCancelar = new JButton("Cancelar");
		jpnBotoes.add(jbtCancelar);
		
		jbtAdicionar = new JButton("Adicionar");
		jpnBotoes.add(jbtAdicionar);
		
		jbtCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				jdgJanelaAmigos.dispose();
			}
		});
		
		jbtAdicionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// return jlstUsuarios.getSelectedValue();
			}
		});
		
		return jpnBotoes;
	}
	
	private static DefaultListModel<Usuario> getListaUsuarios() {
		dlmUsuarios = new DefaultListModel<Usuario>();
		
		Set<Usuario> desconhecidos;
		try {
			desconhecidos = cliente.getService().getUsuariosDesconhecidos(cliente.getUsuarioLogado().getUsuario().getCodigo());
			
			for (Usuario u : desconhecidos) {
				dlmUsuarios.addElement(u);
			}
		} catch (RemoteException e) {
			jdgJanelaAmigos.dispose();
			JOptionPane.showMessageDialog(null, "Conexão - Erro ao listar usuários.");
			e.printStackTrace();
		}
		
		if (dlmUsuarios.isEmpty()) {
			dlmUsuarios.addElement(new Usuario(0l, "Nenhum usuário encontrado."));
			jlstUsuarios.setEnabled(false);
			jbtAdicionar.setEnabled(false);
		}
		
		return dlmUsuarios;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new WebLookAndFeel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TelaListaAmigos.getTela(null);
	}
}
