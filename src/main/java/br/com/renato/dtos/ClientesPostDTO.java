package br.com.renato.dtos;

public class ClientesPostDTO {

	private Integer idCLiente;
	private String nome;
	private String cpf;
	private String email;

	public Integer getIdCLiente() {
		return idCLiente;
	}

	public void setIdCLiente(Integer idCLiente) {
		this.idCLiente = idCLiente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
