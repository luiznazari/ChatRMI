package chat.foda.pra.caralho.telas.eventos;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;

import chat.foda.pra.caralho.clienteServidor.ClienteRmi;
import chat.foda.pra.caralho.telas.TelaCliente;

public class EventosTelaCliente implements WindowListener {
	
	private TelaCliente telaCliente;
	
	public EventosTelaCliente(TelaCliente telaCliente) {
		this.telaCliente = telaCliente;
	}
	
	@Override
	public void windowOpened(WindowEvent e) {}
	
	@Override
	public void windowClosing(WindowEvent e) {}
	
	@Override
	public void windowClosed(WindowEvent e) {
		ClienteRmi cliente = telaCliente.getCliente();
		
		try {
			cliente.getService().logout(telaCliente.getCodigosChat(), cliente.getClienteService(),
			        cliente.getUsuarioLogado().getUsuario());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
	
	@Override
	public void windowIconified(WindowEvent e) {}
	
	@Override
	public void windowDeiconified(WindowEvent e) {}
	
	@Override
	public void windowActivated(WindowEvent e) {}
	
	@Override
	public void windowDeactivated(WindowEvent e) {}
}
