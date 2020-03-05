package br.com.dotofcodex.pontointeligente.dto;

import java.util.Optional;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

public class CadastroPFDTO {

	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres")
	private String nome;
	
	@NotEmpty(message = "E-mail não pode ser vazio")
	@Length(min = 5, max = 200, message = "E-mail deve conter entre 5 e 200 caracteres")
	@Email(message = "E-mail inválido")
	private String email;
	
	@NotEmpty(message = "Nome não pode ser vazio")
	private String senha;
	
	@NotEmpty(message = "CPF não pode ser vazio")
	@CPF(message = "CPF inválido")
	private String cpf;
	private Optional<String> valorHora;
	private Optional<String> quantidadeHorasTrabalhoDia;
	private Optional<String> quantidadeHorasAlmoco;
	
	@NotEmpty(message = "CNPJ não pode ser vazio")
	@CNPJ(message = "CNPJ inválido")
	private String cnpj;

	public CadastroPFDTO() {
		super();
		valorHora = Optional.empty();
		quantidadeHorasTrabalhoDia = Optional.empty();
		quantidadeHorasAlmoco = Optional.empty();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Optional<String> getValorHora() {
		return valorHora;
	}

	public void setValorHora(Optional<String> valorHora) {
		this.valorHora = valorHora;
	}

	public Optional<String> getQuantidadeHorasTrabalhoDia() {
		return quantidadeHorasTrabalhoDia;
	}

	public void setQuantidadeHorasTrabalhoDia(Optional<String> quantidadeHorasTrabalhoDia) {
		this.quantidadeHorasTrabalhoDia = quantidadeHorasTrabalhoDia;
	}

	public Optional<String> getQuantidadeHorasAlmoco() {
		return quantidadeHorasAlmoco;
	}

	public void setQuantidadeHorasAlmoco(Optional<String> quantidadeHorasAlmoco) {
		this.quantidadeHorasAlmoco = quantidadeHorasAlmoco;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CadastroPFDTO [id=").append(id).append(", nome=").append(nome).append(", email=").append(email)
				.append(", senha=").append(senha).append(", cpf=").append(cpf).append(", valorHora=").append(valorHora)
				.append(", quantidadeHorasTrabalhoDia=").append(quantidadeHorasTrabalhoDia)
				.append(", quantidadeHorasAlmoco=").append(quantidadeHorasAlmoco).append(", cnpj=").append(cnpj)
				.append("]");
		return builder.toString();
	}

}
