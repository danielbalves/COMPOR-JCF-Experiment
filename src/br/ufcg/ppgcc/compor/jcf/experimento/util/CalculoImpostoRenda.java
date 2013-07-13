package br.ufcg.ppgcc.compor.jcf.experimento.util;

import java.util.List;

import br.ufcg.ppgcc.compor.jcf.experimento.fachada.Dependente;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.FontePagadora;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.GastoDedutivel;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.GastoDedutivel.TipoGasto;

public class CalculoImpostoRenda {
	
	public double impostoAPagar(double impostoDevido, double impostoPago) {
		return impostoDevido - impostoPago;
	}

	public double totalRecebido(List<FontePagadora> fontes) {
		double soma = 0.0;
		
		for (FontePagadora fontePagadora : fontes) {
			soma += fontePagadora.getRendimentoRecebidos();
		}
		
		return soma;
	}	
	
	public double totalPago(List<FontePagadora> fontes) {
		double soma = 0.0;
		
		for (FontePagadora fontePagadora : fontes) {
			soma += fontePagadora.getImpostoPago();
		}
		
		return soma;
	}	

	public double descontoDependentes(double totalRecebido,
			List<Dependente> dependentes) {
		return Math.max(0, totalRecebido - (dependentes.size() * 1974.72));
	}
	
	public double descontoSaude(double totalRecebido, List<GastoDedutivel> gastos) {
		double somaSaude = 0.0;
		
		for (GastoDedutivel gasto : gastos) {
			if (TipoGasto.Saude.equals(gasto.getTipo())) {
				somaSaude += gasto.getValor();
			}
		}
		
		return Math.max(0, totalRecebido - somaSaude);
	}

	public double descontoEducacao(double totalRecebido, List<GastoDedutivel> gastos) {
		double somaEducacao = 0.0;
		
		for (GastoDedutivel gasto : gastos) {
			if (TipoGasto.Educacao.equals(gasto.getTipo())) {
				somaEducacao += gasto.getValor();
			}
		}
		
		somaEducacao = Math.min(3091.35, somaEducacao);
		
		return Math.max(0, totalRecebido - somaEducacao);
	}
	
	public double deducaoSimplificada(double totalRecebido) {
		double deducao = totalRecebido * 0.2;
		deducao = Math.min(14542.60, deducao);
		
		return totalRecebido - deducao;
	}

	public double impostoDevido(double baseCalculo) {
		
		if (baseCalculo < 1637.11 * 12) { //isento
			return 0.0; 
		} 
		
		if (baseCalculo < 2453.51 * 12) {
			return impostoDevidoFaixa2(baseCalculo);
		} 

		if (baseCalculo < 3271.39 * 12) {
			return impostoDevidoFaixa3(baseCalculo);
		} 

		if (baseCalculo < 4087.66 * 12) {
			return impostoDevidoFaixa4(baseCalculo);
		} 
		
		return impostoDevidoFaixa5(baseCalculo);
	}
	
	private double impostoDevidoFaixa2(double totalRecebido) {
		return calculoGenerico(totalRecebido, 0.07500, 122.78 * 12);
	}

	private double impostoDevidoFaixa3(double totalRecebido) {
		return calculoGenerico(totalRecebido, 0.1500, 306.80 * 12);
	}

	private double impostoDevidoFaixa4(double totalRecebido) {
		return calculoGenerico(totalRecebido, 0.22500, 552.15 * 12);
	}

	private double impostoDevidoFaixa5(double totalRecebido) {
		return calculoGenerico(totalRecebido, 0.27500, 756.53 * 12);
	}

	private double calculoGenerico(double totalRecebido, double taxa,
			double parcelaADeduzir) {
		return (totalRecebido * taxa) - parcelaADeduzir;
	}

}
