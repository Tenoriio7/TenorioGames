package br.com.tenoriogames.core.util;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import br.com.tenoriogames.core.DAO.UsuarioDAO;
import br.com.tenoriogames.domain.Usuario;
import br.com.tenoriogames.domain.Venda;

public class CalculoFrete {
	private static final String CEP_ORIGEM = "08567450";
	public static Double CalcularFrete(Venda venda){
		HttpURLConnection connection = null;
		Double shippingValue = null;
		try{
			URL url;
			// prepara URL do webservice de cálculo de frete
			StringBuilder urlBld = new StringBuilder("http://developers.agenciaideias.com.br/correios/frete/xml");
			urlBld.append("/").append(CEP_ORIGEM);
			urlBld.append("/").append(venda.getUsuario().getEndereco().getCEP());
			urlBld.append("/").append(venda.getPeso().toString());
			urlBld.append("/").append((venda.getValor()));
			url = new URL(urlBld.toString());
			
			// le o xml retornado pelo webservice
			connection = (HttpURLConnection) url.openConnection();
			InputStream content = connection.getInputStream();
			
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource(content);
			Document doc = db.parse(is);
			// Lê o nó sedex 
			NodeList nodes = doc.getElementsByTagName("sedex");
			// Recupera o elemento 0 (o nó não possui subnós)
			Element sedexNode = (Element) nodes.item(0);
			// Recupera o valor do nó <sedex>valor do nó</sedex?
			String sedexVal = sedexNode.getFirstChild().getNodeValue();
			shippingValue = Double.parseDouble(sedexVal);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			connection.disconnect();
		}
		System.out.println(shippingValue);
		return shippingValue;
	}
	
	@Test
	public void teste(){
		
	UsuarioDAO usuarioDAO =  new UsuarioDAO();
	Usuario usuario = 	 usuarioDAO.buscarPorCodigo(1L);
	Venda venda  = new  Venda();
	venda.setCodigo(1L);
	venda.setUsuario(usuario);
	//venda.setValor(new BigDecimal(100));
	venda.setPeso(5D);
	CalcularFrete(venda);
		
	}

}
