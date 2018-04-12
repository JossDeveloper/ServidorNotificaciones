package pe.bf.servidor.main;

import java.io.IOException;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import org.apache.log4j.Logger;
import pe.bf.servidor.proceso.NotificacionThread;

public class ServidorMain 
{
	private static String FS = System.getProperty("file.separator");
	private Logger logger = Logger.getLogger(ServidorMain.class);
	
	public static void main(String[] args) 
	{
		new ServidorMain(2490, true);
	}
	
	public ServidorMain(int port, boolean turn)
	{
		ServerSocket serverSocket = null;
		try 
		{
			logger.info("[ServidorNotificaciones] Iniciando proceso, el socket esta encendido");
			serverSocket = new ServerSocket(port);
			//Bucle que mantiene encendido el socket
			while(turn)
			{
				logger.info("[ServidorNotificaciones] Puerto " + port + " en disposicion...");
				Socket receptor = serverSocket.accept();
				Runnable nuevoCliente = new NotificacionThread(receptor);
                Thread hilo = new Thread(nuevoCliente);
                logger.info("[ServidorNotificaciones] Runnable, ejecucion del hilo");
                hilo.start();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			turn = false;
			logger.error("[ServidorNotificaciones] Servidor apagado #");
		} 
	}
	
	public static String getIP() throws IOException
	{
	    String versionString = null;
	    Properties mainProperties = new Properties();
	    FileInputStream file;
	    String path = "." + FS + "ServidorNotificaciones.properties";
	    file = new FileInputStream(path);
	    mainProperties.load(file);
	    file.close();
	    versionString = mainProperties.getProperty("app.ip");
	    return versionString;
	}
	
}
