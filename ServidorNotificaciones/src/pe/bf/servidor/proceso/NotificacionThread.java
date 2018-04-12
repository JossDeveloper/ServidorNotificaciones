package pe.bf.servidor.proceso;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import org.apache.log4j.Logger;
import pe.bf.servidor.facade.NotificacionWSfacade;

public class NotificacionThread implements Runnable
{
	private Logger logger = Logger.getLogger(NotificacionThread.class);
	private Socket socket;
	private NotificacionWSfacade ws;
	private InputStream in;
	private int recvMsgSize;
	private byte[] byteBuffer;
	
	public NotificacionThread(Socket socket) 
	{
		this.socket = socket;
		try 
		{
			ws = new NotificacionWSfacade();
			in = socket.getInputStream();
			byteBuffer = new byte[925];
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			logger.error("[ServidorNotificaciones] Error al interpretar paquete de datos.");
		}
	}

	@Override
	public void run() 
	{
		try 
		{
			while ((recvMsgSize = in.read(byteBuffer)) != -1)
			{
				logger.info("[ServidorNotificaciones] Recibiendo informacion a procesar");
				String cadena1 = new String(byteBuffer); 
				String cadena2 = "";
				byteBuffer = null;
				byteBuffer = new byte[925];
				cadena1 = cadena1.trim();
				logger.info("[ServidorNotificaciones] Recibiendo trama, size=" + recvMsgSize);
				logger.info("[ServidorNotificaciones] Recibiendo trama, text=" + cadena1);
				cadena2 = ws.procesarTrama(cadena1);
				logger.info("[ServidorNotificaciones] Recibiendo respuesta del servicio: " + cadena2);
				if (cadena2 == null || cadena2.isEmpty()) break;
			}
			socket.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			logger.error("[ServidorNotificaciones] Error al procesar la trama en Runnable.");
		}
	}

}
