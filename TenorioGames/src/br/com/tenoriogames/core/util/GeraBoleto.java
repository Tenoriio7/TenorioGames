package br.com.tenoriogames.core.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoletoHTML;
import br.com.tenoriogames.domain.Usuario;
import br.com.tenoriogames.domain.Venda;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Leticia
 */
public class GeraBoleto extends HttpServlet {


    public  static void GerarBoleto(Venda venda,Usuario usuario)
            throws ServletException, IOException {
        
        
            Datas datas = Datas.novasDatas()
                .comDocumento(02, 12, 2015)
                .comProcessamento(02, 12, 2015)
                .comVencimento(10, 12, 2015);  

        Endereco enderecoBeneficiario = Endereco.novoEndereco()
                .comLogradouro("Av das Empresas, 555")  
                .comBairro("Bairro Grande")  
                .comCep("01234-555")  
                .comCidade("São Paulo")  
                .comUf("SP");  

        //Quem emite o boleto
        Beneficiario beneficiario = Beneficiario.novoBeneficiario()  
                .comNomeBeneficiario("Fulano de Tal")  
                .comAgencia("1824").comDigitoAgencia("4")  
                .comCodigoBeneficiario("76000")  
                .comDigitoCodigoBeneficiario("5")  
                .comNumeroConvenio("1207113")  
                .comCarteira("18")  
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero("9000206")
                .comDigitoNossoNumero("2");
              //  .comDocumento("AB");  

        Endereco enderecoPagador = Endereco.novoEndereco()
                .comLogradouro(usuario.getEndereco().getRua()+", "+usuario.getEndereco().getNumero())  
                .comBairro(usuario.getEndereco().getBairro())  
                .comCep(usuario.getEndereco().getCEP())  
                .comCidade(usuario.getEndereco().getCidade())  
                .comUf(usuario.getEndereco().getEstado());  

        //Quem paga o boleto
        Pagador pagador = Pagador.novoPagador()  
                .comNome(usuario.getNome())  
                .comDocumento(usuario.getCPF())
                .comEndereco(enderecoPagador);

        Banco banco = new BancoDoBrasil();  

        br.com.caelum.stella.boleto.Boleto boleto = br.com.caelum.stella.boleto.Boleto.novoBoleto()  
                .comBanco(banco)  
                .comDatas(datas)  
                .comBeneficiario(beneficiario)  
                .comPagador(pagador)  
                .comValorBoleto(venda.getValor())  
                .comNumeroDoDocumento("1234")  
                .comInstrucoes("instrucao 1", "instrucao 2", "instrucao 3", "instrucao 4", "instrucao 5")  
                .comLocaisDePagamento("local 1", "local 2");
                
              //  .comQuantidadeMoeda(BigDecimal.ZERO)
              //  .comValorMoeda(BigDecimal.ZERO);  

        //GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);  
//gerar web
         GeradorDeBoletoHTML gerador = new GeradorDeBoletoHTML(boleto);
       // gerador.geraPDF(response.getOutputStream());
         //gerador.geraHTML("BancoDoBrasil.pdf");
                
        gerador.geraPDF("BancoDoBrasil.pdf");  
 
        // Para gerar um boleto em PNG  
      //  gerador.geraPNG("BancoDoBrasil.png");  

        // Para gerar um array de bytes a partir de um PDF  
        byte[] bPDF = gerador.geraPDF();  

        // Para gerar um array de bytes a partir de um PNG  
        byte[] bPNG = gerador.geraPNG();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
