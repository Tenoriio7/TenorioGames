package br.com.tenoriogames.core;
import java.sql.SQLException;
import java.util.List;

import br.com.tenoriogames.domain.EntidadeDominio;

public interface IDAO2 {

	public Long Salvar(EntidadeDominio entidade) throws SQLException;
	public void Editar(EntidadeDominio entidade)throws SQLException;
	public void Excluir(EntidadeDominio entidade)throws SQLException;
	public EntidadeDominio buscarPorCodigo(Long codigo);
	public List<EntidadeDominio> listar();
	
	
	
}
