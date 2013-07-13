package br.ufcg.ppgcc.compor.jcf.experimento.fachada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.ufcg.ppgcc.compor.jcf.experimento.util.CalculoImpostoRenda;

public class FachadaDaniel implements FachadaExperimento {

	List<Titular> titulares = new LinkedList<Titular>();
	
	CalculoImpostoRenda calculo = new CalculoImpostoRenda();
	
	Map<Titular, List<FontePagadora>> mapaFonte = new HashMap<Titular, List<FontePagadora>>() ;
	Map<Titular, List<Dependente>> mapaDependente = new HashMap<Titular, List<Dependente>>();

	@Override
	public void criarNovoTitular(Titular titular) {
		
		titulares.add(titular);
		
	}

	@Override
	public List<Titular> listarTitulares() {
		
		
		return titulares;
	}

	@Override
	public void criarFontePagadora(Titular titular, FontePagadora fonte) {
	
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

}
