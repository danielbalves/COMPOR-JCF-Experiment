package br.ufcg.ppgcc.compor.jcf.experimento.fachada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.ufcg.ppgcc.compor.jcf.experimento.util.CalculoImpostoRenda;
import br.ufcg.ppgcc.compor.jcf.experimento.util.Validacao;

public class FachadaDaniel implements FachadaExperimento {

	List<Titular> titulares = new LinkedList<Titular>();
	
	CalculoImpostoRenda calculo = new CalculoImpostoRenda();
	
	Map<Titular, List<FontePagadora>> mapaFonte = new HashMap<Titular, List<FontePagadora>>() ;
	Map<Titular, List<Dependente>> mapaDependente = new HashMap<Titular, List<Dependente>>();
	Validacao ehValido = new Validacao();
	
	//etapa2 16:20
	@Override
	public void criarNovoTitular(Titular titular) {
		if (!ehValido.obrigatorio(titular.getNome())||!ehValido.obrigatorio(titular.getCpf())){
		 throw new ExcecaoImpostoDeRenda("campo incompleto");
		}
	
		titulares.add(titular);
		
	}

	@Override
	public List<Titular> listarTitulares() {
		
		
		return titulares;
	}

	@Override
	public void criarFontePagadora(Titular titular, FontePagadora fonte) {
	
		if (!ehValido.obrigatorio(fonte.getNome())||!ehValido.obrigatorio(fonte.getCpfCnpj())){
			throw new ExcecaoImpostoDeRenda("campo incompleto");
		}
		
		if (fonte.getRendimentoRecebidos()<=0){
			throw new ExcecaoImpostoDeRenda("campo incompleto");
		}
		if (mapaFonte.get(titular)==null){
		
			mapaFonte.put(titular,  new ArrayList<FontePagadora>());
		}
		mapaFonte.get(titular).add(fonte);
	
		
	}

	@Override
	public Resultado declaracaoCompleta(Titular titular) {
		Resultado result = new Resultado();
	
		double totalRecebidos =  calculo.totalRecebido(mapaFonte.get(titular));
		
		if (mapaDependente.get(titular)==null){
		result.setImpostoDevido(calculo.impostoDevido(totalRecebidos));
		}else{
			
			totalRecebidos = calculo.descontoDependentes(totalRecebidos, mapaDependente.get(titular));
			result.setImpostoDevido(calculo.impostoDevido(totalRecebidos));
		}
		
		
		return result;
	}

	@Override
	public void criarDependente(Titular titular, Dependente dependente) {
		
		if (!ehValido.obrigatorio(dependente.getNome())||!ehValido.obrigatorio(dependente.getCpf())){
			throw new ExcecaoImpostoDeRenda("campo incompleto");
		}
		
		if(dependente.getTipo()<=0){
			throw new ExcecaoImpostoDeRenda("campo incompleto");
		}
		
		if (mapaDependente.get(titular)==null){
			
			mapaDependente.put(titular,  new ArrayList<Dependente>());
		}
		mapaDependente.get(titular).add(dependente);
	}

	@Override
	public List<FontePagadora> listarFontes(Titular titular) {
		// TODO Auto-generated method stub
		return mapaFonte.get(titular);
	}

	@Override
	public List<Dependente> listarDependentes(Titular titular) {
		// TODO Auto-generated method stub
		return mapaDependente.get(titular);
	}

	@Override
	public void criarGastoDedutivel(Titular titular, Pessoa realizador,
			GastoDedutivel gastoDedutivel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GastoDedutivel> listarGastosDedutiveis(Titular titular,
			Pessoa realizador) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado relatorioSimplificado(Titular titular) {
		// TODO Auto-generated method stub
		return null;
	}

}
