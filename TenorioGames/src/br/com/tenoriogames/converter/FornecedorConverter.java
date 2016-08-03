package br.com.tenoriogames.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.tenoriogames.core.DAO.FornecedorDAO;
import br.com.tenoriogames.domain.Fornecedor;

@FacesConverter("fornecedorConverter")
public class FornecedorConverter implements Converter {

	@Override
	// recebo o ID e tem que montar o Objeto
	public Object getAsObject(FacesContext facesContext, UIComponent component, String valor) {
		try{
			Long codigo= Long.parseLong(valor);
			FornecedorDAO FornecedorDAO = new  FornecedorDAO();
			Fornecedor Fornecedor = new  Fornecedor();
			Fornecedor=(br.com.tenoriogames.domain.Fornecedor) FornecedorDAO.buscarPorCodigo(codigo);
			System.out.println(Fornecedor.getCodigo()+"converter");
			return Fornecedor;			
			
		}catch(RuntimeException ex){
			return null;
		}
		
	}

	@Override
	// serve para montar o combo , ele recebe um objeto e a partir do objeto retorna o ID
	
	public String getAsString(FacesContext facesContext, UIComponent component, Object Objeto) {
		try{
			Fornecedor Fornecedor = (Fornecedor)Objeto;
			Long codigo = Fornecedor.getCodigo();
			System.out.println(codigo.toString()+"teste");
			return codigo.toString();

		}catch(RuntimeException ex){
			return null;
		}

	}

}
