package br.ufcg.ppgcc.compor.jcf.experimento.fachada;

import java.util.List;

public interface FachadaExperimento {

	void criarNovoTitular(Titular titular);

	List<Titular> listarTitulares();

	void criarFontePagadora(Titular titular, FontePagadora fonte);

	Resultado declaracaoCompleta(Titular titular);

	void criarDependente(Titular titular, Dependente dependente);

	List<FontePagadora> listarFontes(Titular titular);

	List<Dependente> listarDependentes(Titular titular);

	void criarGastoDedutivel(Titular titular, Pessoa realizador, GastoDedutivel gastoDedutivel);

	List<GastoDedutivel> listarGastosDedutiveis(Titular titular, Pessoa realizador);

	Resultado relatorioSimplificado(Titular titular);

}
