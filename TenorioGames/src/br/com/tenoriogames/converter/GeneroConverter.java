package br.com.tenoriogames.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.tenoriogames.core.DAO.GeneroDAO;
import br.com.tenoriogames.domain.Genero;

@FacesConverter("generoConverter")
public class GeneroConverter implements Converter {

	@Override
	// recebo o ID e tem que montar o Objeto
	public Object getAsObject(FacesContext facesContext, UIComponent component, String valor) {
		try{
			Long codigo= Long.parseLong(valor);
			GeneroDAO GeneroDAO = new  GeneroDAO();
			Genero Genero = new  Genero();
			Genero= (Genero) GeneroDAO.buscarPorCodigo(codigo);
			return Genero;			
			
		}catch(RuntimeException ex){
			return null;
		}
		
	}

	@Override
	// serve para montar o combo , ele recebe um objeto e a partir do objeto retorna o ID
	
	public String getAsString(FacesContext facesContext, UIComponent component, Object Objeto) {
		try{
			Genero Genero = (Genero)Objeto;
			Long codigo = Genero.getCodigo();
			return codigo.toString();

		}catch(RuntimeException ex){
			return null;
		}

	}

}

