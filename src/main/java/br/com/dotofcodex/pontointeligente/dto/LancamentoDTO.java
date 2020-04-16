package br.com.dotofcodex.pontointeligente.dto;

import java.util.Optional;

import org.hibernate.validator.constraints.NotEmpty;

public class LancamentoDTO {

	private Optional<Long> id;
	private String data;
	private String tipo;
	private String descricao;
	private String localizacao;
	private Long funcionarioId;

	public LancamentoDTO() {
		super();
		this.id = Optional.empty();
	}

	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	@NotEmpty(message = "Data não pode ser vazia")
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LancamentoDTO [id=").append(id).append(", data=").append(data).append(", tipo=").append(tipo)
				.append(", descricao=").append(descricao).append(", localizacao=").append(localizacao)
				.append(", funcionarioId=").append(funcionarioId).append("]");
		return builder.toString();
	}

}
