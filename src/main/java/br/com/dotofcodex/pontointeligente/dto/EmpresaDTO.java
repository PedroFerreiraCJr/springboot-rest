package br.com.dotofcodex.pontointeligente.dto;

public class EmpresaDTO {

	private Long id;
	private String razaoSocial;
	private String cnpj;

	public EmpresaDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		builder.append("EmpresaDTO [id=").append(id).append(", razaoSocial=").append(razaoSocial).append(", cnpj=")
				.append(cnpj).append("]");
		return builder.toString();
	}

}
