package br.com.tenoriogames.core.util;

import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.tenoriogames.core.web.bean.AutenticacaoBean;
import br.com.tenoriogames.domain.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoPhaseListener implements PhaseListener{

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		//uiViewRoot serve para navegar entre as paginas
		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		String paginaAtual = uiViewRoot.getViewId();
		//System.out.println(paginaAtual);
		boolean ehPaginaAutenticacao = paginaAtual.contains("autenticacao.xhtml");
		System.out.println();
//		if(!ehPaginaAutenticacao){
//			ExternalContext externalContext = facesContext.getExternalContext();
//			Map<String, Object> mapa= externalContext.getSessionMap();
//			AutenticacaoBean autenticacaoBean = (AutenticacaoBean)mapa.get("autenticaoBean");
//			Usuario funcionario = autenticacaoBean.getUsuarioLogado(); 
//			if(funcionario.getStatus() == null){
//				FacesUtil.adicionarMSGError("Usuario nao autenticado");
//				Application application= facesContext.getApplication();
//				NavigationHandler  handler=  application.getNavigationHandler();
//				handler.handleNavigation(facesContext, null, "/pages/autenticacao.xhtml?faces-redirect=true");
//			}
//		}
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		
	}

	@Override
	public PhaseId getPhaseId() {
		
		return PhaseId.RESTORE_VIEW;
	}

}
