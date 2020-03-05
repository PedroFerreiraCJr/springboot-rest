package br.com.dotofcodex.pontointeligente.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

public class CadastroPJDTO {

	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres")
	private String nome;
	
	@NotEmpty(message = "E-mail não pode ser vazio")
	@Length(min = 5, max = 200, message = "E-mail deve conter entre 5 e 200 caracteres")
	@Email(message = "E-mail inválido")
	private String email;
	
	@NotEmpty(message = "Senha não pode ser vazio")
	private String senha;
	
	@NotEmpty(message = "CPF não pode ser vazio")
	@CPF(message = "CPF inválido")
	private String cpf;
	
	@NotEmpty(message = "Razão Social não pode ser vazio")
	@Length(min = 5, max = 200, message = "Razão Social deve conter entre 5 e 200 caracteres")
	private String razaoSocial;
	
	@NotEmpty(message = "CNPJ não pode ser vazio")
	@CNPJ(message = "CNPJ inválido")
	private String cnpj;

	public CadastroPJDTO() {
		super();
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

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
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
		builder.append("CadastroPJDTO [id=").append(id).append(", nome=").append(nome).append(", email=").append(email)
				.append(", senha=").append(senha).append(", cpf=").append(cpf).append(", razaoSocial=")
				.append(razaoSocial).append(", cnpj=").append(cnpj).append("]");
		return builder.toString();
	}

}
