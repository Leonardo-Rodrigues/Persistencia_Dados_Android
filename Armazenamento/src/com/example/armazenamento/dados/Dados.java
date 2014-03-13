package com.example.armazenamento.dados;

import java.io.Serializable;


public class Dados  implements Serializable{

	private String nome, telefone, sexo;
	private Boolean check1, check2, check3, check4, check5;

	public Dados(String nome, String telefone, String sexo, boolean check1,
			boolean check2, boolean check3, boolean check4, boolean check5) {
		this.nome = nome;
		this.telefone = telefone;
		this.sexo = sexo;
		this.check1 = check1;
		this.check2 = check2;
		this.check3 = check3;
		this.check4 = check4;
		this.check5 = check5;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone
	 *            the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo
	 *            the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the check1
	 */
	public Boolean getCheck1() {
		return check1;
	}

	/**
	 * @param check1
	 *            the check1 to set
	 */
	public void setCheck1(Boolean check1) {
		this.check1 = check1;
	}

	/**
	 * @return the check2
	 */
	public Boolean getCheck2() {
		return check2;
	}

	/**
	 * @param check2
	 *            the check2 to set
	 */
	public void setCheck2(Boolean check2) {
		this.check2 = check2;
	}

	/**
	 * @return the check3
	 */
	public Boolean getCheck3() {
		return check3;
	}

	/**
	 * @param check3
	 *            the check3 to set
	 */
	public void setCheck3(Boolean check3) {
		this.check3 = check3;
	}

	/**
	 * @return the check4
	 */
	public Boolean getCheck4() {
		return check4;
	}

	/**
	 * @param check4
	 *            the check4 to set
	 */
	public void setCheck4(Boolean check4) {
		this.check4 = check4;
	}

	/**
	 * @return the check5
	 */
	public Boolean getCheck5() {
		return check5;
	}

	/**
	 * @param check5
	 *            the check5 to set
	 */
	public void setCheck5(Boolean check5) {
		this.check5 = check5;
	}

}
