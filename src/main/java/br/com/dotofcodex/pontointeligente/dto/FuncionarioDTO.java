package br.com.dotofcodex.pontointeligente.dto;

import java.util.Optional;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class FuncionarioDTO {

	private String id;

	@NotEmpty(message = "Nome não pode ser vazio")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres")
	private String nome;

	@NotEmpty(message = "Email não pode ser vazio")
	@Length(min = 3, max = 200, message = "Email deve conter entre 5 e 200 caracteres")
	@Email(message = "E-mail inválido")
	private String email;

	private Optional<String> senha;
	private Optional<String> valorHora;
	private Optional<String> quantidadeHorasTrabalhoDia;
	private Optional<String> quantidadeHorasAlmoco;

	public FuncionarioDTO() {
		super();
		this.senha = Optional.empty();
		this.valorHora = Optional.empty();
		this.quantidadeHorasTrabalhoDia = Optional.empty();
		this.quantidadeHorasAlmoco = Optional.empty();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Optional<String> getSenha() {
		return senha;
	}

	public void setSenha(Optional<String> senha) {
		this.senha = senha;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FuncionarioDTO [id=").append(id).append(", nome=").append(nome).append(", email=").append(email)
				.append(", senha=").append(senha).append(", valorHora=").append(valorHora)
				.append(", quantidadeHorasTrabalhoDia=").append(quantidadeHorasTrabalhoDia)
				.append(", quantidadeHorasAlmoco=").append(quantidadeHorasAlmoco).append("]");
		return builder.toString();
	}

}
