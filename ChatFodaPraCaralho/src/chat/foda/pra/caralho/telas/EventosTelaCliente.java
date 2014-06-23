package chat.foda.pra.caralho.telas;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.ConnectException;
import java.rmi.RemoteException;

import javax.sound.midi.SysexMessage;

import org.omg.CORBA.SystemException;

import chat.foda.pra.caralho.clienteServidor.ClienteRmi;
import chat.foda.pra.caralho.rmi.ClienteRemoto;

public class EventosTelaCliente implements WindowListener {

	private TelaCliente telaCliente;

	public EventosTelaCliente(TelaCliente telaCliente) {
		this.telaCliente = telaCliente;
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		ClienteRmi cliente = telaCliente.getCliente();
		
		try {
			cliente.getService().logout(telaCliente.getCodigosChat(), cliente.getClienteService(),
					cliente.getUsuarioLogado().getUsuario().getNomeCompleto());
		} catch (RemoteException e1) {

		} finally {
			System.exit(0);
		}
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
