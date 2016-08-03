package br.com.tenoriogames.core.util;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class FacesUtil {
	public static void adicionarMSGInfo(String mensagem) {
		// cria uma mensagem do tido informação
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,mensagem,mensagem);
		
		// pega a instancia atual
		FacesContext facesContext = FacesContext.getCurrentInstance();
		// insere a mensagem no contexto
		//pega o cotexto
		ExternalContext context =facesContext.getExternalContext();
		// pega a mesmoria flash
		Flash flash=context.getFlash();
		// deixa a mensagem permanecer
		flash.setKeepMessages(true);
		facesContext.addMessage(null, facesMessage);
	}

	public static void adicionarMSGError(String mensagem) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,mensagem);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		//pega o cotexto
				ExternalContext context =facesContext.getExternalContext();
				// pega a mesmoria flash
				Flash flash=context.getFlash();
				// deixa a mensagem permanecer
				flash.setKeepMessages(true);
		facesContext.addMessage(null, facesMessage);
	}
	
	public static String  getParam (String nome) {
		// cria um contexto pegando o atual
		FacesContext facesContext = FacesContext.getCurrentInstance();
		// pega o contexto externo
		ExternalContext externalContext = facesContext.getExternalContext();
		// cria um mapa de paramtros 
		Map<String, String> paramtros =externalContext.getRequestParameterMap();
		// insere o nome no valor e retorna o valor
		String valor = paramtros.get(nome);
		return valor;
		
	}

}
