package br.com.primeirojsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.dao.DaoGeneric;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoLancamento;
import br.com.repository.IDaoLancamentoImpl;

@ViewScoped
@ManagedBean(name= "lancamentoBean")
public class LancamentoBean {
	
	private Lancamento lancamento = new Lancamento();
	private DaoGeneric<Lancamento> daoGeneric = new DaoGeneric<Lancamento>();
	private List<Lancamento> lancamentosUser = new ArrayList<Lancamento>();
	private List<Lancamento> lancamentosGeral = new ArrayList<Lancamento>();
	private IDaoLancamento daoLancamento = new IDaoLancamentoImpl();
	
	//Methods
	
	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoa = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		if(lancamento.getUsuario() == null) { // Validação para não mudar o autor quando admin for editar
			lancamento.setUsuario(pessoa);
		}
		daoGeneric.merge(lancamento);
		carregarLancamentosUser();
		carregarLancamentos();
		return "";
	}
	
	public String deletar() {
		daoGeneric.deletarPorId(lancamento);
		lancamento = new Lancamento();
		carregarLancamentosUser();
		carregarLancamentos();
		return "";
	}
	
	public String novo() {
		lancamento = new Lancamento();
		return "";
	}
	
	@PostConstruct
	public void carregarLancamentos() {
		lancamentosGeral = daoGeneric.getListEntity(new Lancamento());
	}
	
	@PostConstruct
	public void carregarLancamentosUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoa = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		lancamentosUser = daoLancamento.consultar(pessoa.getId());
	}
	
	public boolean permiteAcesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		
		return pessoaUser.getPerfilUser().equals(acesso);
	}
	
	// Setters and Getters
	public Lancamento getLancamento() {
		return lancamento;
	}
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	public DaoGeneric<Lancamento> getDaoGeneric() {
		return daoGeneric;
	}
	public void setDaoGeneric(DaoGeneric<Lancamento> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}
	public List<Lancamento> getLancamentosGeral() {
		return lancamentosGeral;
	}
	public void setLancamentosGeral(List<Lancamento> lancamentos) {
		this.lancamentosGeral = lancamentos;
	}
	public void setLancamentoUser(List<Lancamento> lancamentoUser) {
		this.lancamentosUser = lancamentoUser;
	}
	public List<Lancamento> getLancamentoUser() {
		return lancamentosUser;
	}
	
}
