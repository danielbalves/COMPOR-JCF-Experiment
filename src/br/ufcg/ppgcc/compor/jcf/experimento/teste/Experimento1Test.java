package br.ufcg.ppgcc.compor.jcf.experimento.teste;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.ufcg.ppgcc.compor.jcf.experimento.fachada.Dependente;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.Endereco;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.FachadaDaniel;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.FachadaExperimento;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.FontePagadora;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.Resultado;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.Titular;

//inicio 13:51

public class Experimento1Test {

	private FachadaExperimento fachada;

	@Before
	public void iniciar() {
		//Coloque sua Fachada aqui.
		fachada = new FachadaDaniel();
	}

	@Test
	public void novoTitular() {
		Titular titular = criarTitularMinimo();
		verificaCriacaoTitular(titular);
	}

	@Test
	public void titularCompleta() {
		Titular titular = criarTitularPadrao();
		verificaCriacaoTitular(titular);
	}

	@Test
	public void doisTitulares() {
		Titular titular1 = criarTitularPadrao();
		fachada.criarNovoTitular(titular1);
		Titular titular2 = criarTitularMinimo();
		fachada.criarNovoTitular(titular2);

		List<Titular> titulares = fachada.listarTitulares();
		assertEquals(2, titulares.size());
		assertEquals(titular1, titulares.get(0));
		assertEquals(titular2, titulares.get(1));
	}

	@Test
	public void novaFontePagadora() {
		FontePagadora fonte = criarFontePagadoraPadrao1();
		Titular titularSalvo = salvarTitularComUmaFonte(fonte);

		List<FontePagadora> fontes = fachada.listarFontes(titularSalvo);

		assertEquals(fonte, fontes.get(0));
	}

	@Test
	public void duasFontesPagadorasEmUmTitular() {
		Titular titular = criarTitularPadrao();
		fachada.criarNovoTitular(titular);
		FontePagadora fonte1 = criarFontePagadoraPadrao1();
		fachada.criarFontePagadora(titular, fonte1);
		FontePagadora fonte2 = criarFontePagadoraPadrao2();
		fachada.criarFontePagadora(titular, fonte2);

		List<FontePagadora> fontes = fachada.listarFontes(titular);
		assertEquals(2, fontes.size());
		assertEquals(fonte1, fontes.get(0));
		assertEquals(fonte2, fontes.get(1));
	}

	@Test
	public void duasFontesPagadorasUmaEmCadaTitular() {
		Titular titular1 = criarTitularPadrao();
		fachada.criarNovoTitular(titular1);
		FontePagadora fonte1 = criarFontePagadoraPadrao1();
		fachada.criarFontePagadora(titular1, fonte1);

		Titular titular2 = criarTitularMinimo();
		fachada.criarNovoTitular(titular2);
		FontePagadora fonte2 = criarFontePagadoraPadrao2();
		fachada.criarFontePagadora(titular2, fonte2);

		List<FontePagadora> fontes1 = fachada.listarFontes(titular1);
		assertEquals(1, fontes1.size());
		assertEquals(fonte1, fontes1.get(0));

		List<FontePagadora> fontes2 = fachada.listarFontes(titular2);
		assertEquals(1, fontes2.size());
		assertEquals(fonte2, fontes2.get(0));
	}

	@Test
	public void novoDependente() {
		Titular titular = criarTitularPadrao();
		fachada.criarNovoTitular(titular);

		Dependente dependente = criarDependentePadrao1();
		fachada.criarDependente(titular, dependente);

		List<Dependente> dependentes = fachada.listarDependentes(titular);
		assertEquals(1, dependentes.size());
		assertEquals(dependente, dependentes.get(0));
	}

	@Test
	public void doisDependentesEmUmTitular() {
		Titular titular = criarTitularPadrao();
		fachada.criarNovoTitular(titular);
		Dependente dependente1 = criarDependentePadrao1();
		fachada.criarDependente(titular, dependente1);
		Dependente dependente2 = criarDependentePadrao2();
		fachada.criarDependente(titular, dependente2);

		List<Dependente> dependentes = fachada.listarDependentes(titular);
		assertEquals(2, dependentes.size());
		assertEquals(dependente1, dependentes.get(0));
		assertEquals(dependente2, dependentes.get(1));
	}

	@Test
	public void doisDependentesUmEmCadaTitular() {
		Titular titular1 = criarTitularPadrao();
		fachada.criarNovoTitular(titular1);
		Dependente dependente1 = criarDependentePadrao1();
		fachada.criarDependente(titular1, dependente1);

		Titular titular2 = criarTitularMinimo();
		fachada.criarNovoTitular(titular2);
		Dependente dependente2 = criarDependentePadrao2();
		fachada.criarDependente(titular2, dependente2);

		List<Dependente> dependentes1 = fachada.listarDependentes(titular1);
		assertEquals(1, dependentes1.size());
		assertEquals(dependente1, dependentes1.get(0));
		
		List<Dependente> dependentes2 = fachada.listarDependentes(titular2);
		assertEquals(1, dependentes2.size());
		assertEquals(dependente2, dependentes2.get(0));
	}

	@Test
	public void calculoImpostoIsento_1() {
		FontePagadora fonte = criarFontePagadoraPorRenda(15000);
		Titular titular = salvarTitularComUmaFonte(fonte);

		Resultado completo = fachada.declaracaoCompleta(titular);
		assertEquals(0, completo.getImpostoDevido(), 0.1);
	}

	@Test
	public void calculoImpostoIsento_2() {
		FontePagadora fonte = criarFontePagadoraPorRenda(19000);
		Titular titular = salvarTitularComUmaFonte(fonte);

		Resultado completo = fachada.declaracaoCompleta(titular);
		assertEquals(0, completo.getImpostoDevido(), 0.1);
	}

	@Test
	public void calculoImpostoFaixa2_1() {
		FontePagadora fonte = criarFontePagadoraPorRenda(20000);
		Titular titular = salvarTitularComUmaFonte(fonte);

		Resultado completo = fachada.declaracaoCompleta(titular);
		assertEquals(26.60, completo.getImpostoDevido(), 0.1);
	}

	@Test
	public void calculoImpostoFaixa2_2() {
		FontePagadora fonte = criarFontePagadoraPorRenda(39000);
		Titular titular = salvarTitularComUmaFonte(fonte);

		Resultado completo = fachada.declaracaoCompleta(titular);
		assertEquals(2168.45, completo.getImpostoDevido(), 0.1);
	}

	@Test
	public void calculoImpostoFaixa3_1() {
		FontePagadora fonte = criarFontePagadoraPorRenda(40000);
		Titular titular = salvarTitularComUmaFonte(fonte);

		Resultado completo = fachada.declaracaoCompleta(titular);
		assertEquals(2374.21, completo.getImpostoDevido(), 0.1);
	}

	@Test
	public void calculoImpostoFaixa3_2() {
		FontePagadora fonte = criarFontePagadoraPorRenda(49000);
		Titular titular = salvarTitularComUmaFonte(fonte);

		Resultado completo = fachada.declaracaoCompleta(titular);
		assertEquals(4399.21, completo.getImpostoDevido(), 0.1);
	}

	@Test
	public void calculoImpostoFaixa4() {
		FontePagadora fonte = criarFontePagadoraPorRenda(50000);
		Titular titular = salvarTitularComUmaFonte(fonte);

		Resultado completo = fachada.declaracaoCompleta(titular);
		assertEquals(4671.62, completo.getImpostoDevido(), 0.1);
	}

	@Test
	public void calculoImpostoFaixa4ComDependente() {
		FontePagadora fonte = criarFontePagadoraPorRenda(50000);
		Titular titular = salvarTitularComUmaFonte(fonte);
		Dependente dependente1 = criarDependentePadrao1();
		fachada.criarDependente(titular, dependente1);

		Resultado completo = fachada.declaracaoCompleta(titular);
		assertEquals(4179.89, completo.getImpostoDevido(), 0.1);
	}

	private Titular criarTitularMinimo() {
		Titular titular = new Titular();
		titular.setNome("Jose");
		titular.setCpf("000.000.000-00");
		return titular;
	}

	private Titular criarTitularPadrao() {
		Titular titular = criarTitularMinimo();
		titular.setDataNascimento(Calendar.getInstance());
		titular.setTituloEleitoral("000");
		titular.setEndereco(criarEnderecoPadrao());
		titular.setNaturezaOcupacao(10);
		titular.setOcupacaoPrincipal(100);
		return titular;
	}

	private Titular salvarTitularComUmaFonte(FontePagadora fonte) {
		Titular titular = criarTitularPadrao();
		fachada.criarNovoTitular(titular);
		fachada.criarFontePagadora(titular, fonte);
		List<Titular> titulares = fachada.listarTitulares();
		Titular titularSalva = titulares.get(0);
		return titularSalva;
	}

	private void verificaCriacaoTitular(Titular titular) {
		fachada.criarNovoTitular(titular);
		List<Titular> titulares = fachada.listarTitulares();

		assertEquals(1, titulares.size());
		assertEquals(titular, titulares.get(0));
	}

	private Endereco criarEnderecoPadrao() {
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Log");
		endereco.setNumero(0);
		endereco.setComplemento("A");
		endereco.setBairro("Bai");
		endereco.setMunicipio("Mun");
		endereco.setEstado("Est");
		endereco.setCEP("00000-000");
		return endereco;
	}

	private FontePagadora criarFontePagadora(String nome, String cpfCnpj,
			int rendimentoRecebidos) {
		FontePagadora fonte = new FontePagadora();
		fonte.setNome(nome);
		fonte.setCpfCnpj(cpfCnpj);
		fonte.setRendimentoRecebidos(rendimentoRecebidos);
		return fonte;
	}

	private FontePagadora criarFontePagadoraPorRenda(int renda) {
		return criarFontePagadora("UFCG", "000.000.000/0000-00", renda);
	}

	private FontePagadora criarFontePagadoraPadrao1() {
		return criarFontePagadora("UFCG", "000.000.000/0000-00", 50000);
	}

	private FontePagadora criarFontePagadoraPadrao2() {
		return criarFontePagadora("UFPB", "000.000.000/0000-00", 20000);
	}

	private Dependente criarDependentePadrao1() {
		return criarDependente("000", Calendar.getInstance(), "Filho 1", 21);
	}

	private Dependente criarDependentePadrao2() {
		return criarDependente("111", Calendar.getInstance(), "Filho 2", 21);
	}

	private Dependente criarDependente(String cpf, Calendar dataNascimento,
			String nome, int tipo) {
		Dependente dependente = new Dependente();
		dependente.setCpf(cpf);
		dependente.setDataNascimento(dataNascimento);
		dependente.setNome(nome);
		dependente.setTipo(tipo);
		return dependente;
	}

}
