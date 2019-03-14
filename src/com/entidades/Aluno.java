package com.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.config.DataBase;

@Entity
@Table(name = "aluno")
public class Aluno extends DataBase implements Serializable {

	
	  public Aluno(Long id, String nome, String email) { this.setNome(nome);
	  this.setEmail(email); this.setId(id); }
	 
	  public Aluno() {}
	  
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "aluno_nome", nullable = false)
	private String nome;

	@Column(nullable = false, unique = true)
	private String email;

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

}
