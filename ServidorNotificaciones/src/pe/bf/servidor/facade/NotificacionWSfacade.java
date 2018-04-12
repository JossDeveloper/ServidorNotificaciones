package pe.bf.servidor.facade;

import java.io.IOException;
import org.apache.log4j.Logger;
import pe.bf.backend.notificacion.ws.Notificaciones;
import pe.bf.backend.notificacion.ws.NotificacionesService;
import pe.bf.backend.notificacion.ws.ProcesarTrama;
import pe.bf.servidor.main.ServidorMain;

public class NotificacionWSfacade 
{
	private Logger logger = Logger.getLogger(NotificacionWSfacade.class);
	public String procesarTrama(String trama)
	{
		try 
		{
			System.setProperty("SERVER_WS", "" + ServidorMain.getIP());
				//"http://127.0.0.1:8001"
			logger.info("[ServidorNotificaciones] IP: " + ServidorMain.getIP());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			logger.error("[ServidorNotificaciones] Error al cargar la Ip desde el properties.");
		}
		logger.info("[ServidorNotificaciones] Facade del servicio NotificacionesWS... ");
		String msgRespuesta = "";
		ProcesarTrama req = new ProcesarTrama();
		req.setTrama(trama);
		
		NotificacionesService service = new NotificacionesService();
		Notificaciones notWS = service.getNotificacionServicePort();
				//Map<String, Object> reqContext = ((BindingProvider) notWS).getRequestContext();
				//reqContext.put("javax.xml.ws.security.auth.username", "service");
				//reqContext.put("javax.xml.ws.security.auth.password", "service");
		msgRespuesta = notWS.procesarTrama(req.getTrama());
		logger.info("[ServidorNotificaciones] Respuesta WS = " + msgRespuesta);
		return msgRespuesta;
	}
}
