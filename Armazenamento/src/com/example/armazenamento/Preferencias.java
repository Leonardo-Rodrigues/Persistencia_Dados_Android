package com.example.armazenamento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.armazenamento.dados.Dados;

/**
 * Classe respons�vel pelo controle do armazenamento dos dados no formato de
 * Preferencias Classe que utiliza "SharedPreferences" para armazenamento
 * 
 * @author leonardo.rodrigues
 * 
 */
public class Preferencias {
	// string statica de identifica��o do bloco de preferencias
	public static final String PREF_ID = "preferencias";

	// variavel para acesso ao armazenamento as preferencias
	private SharedPreferences pref;

	private Context mContext;
	private Dados dados;

	private static Preferencias instancia;

	/**
	 * M�todo respons�vel por criar nova inst�ncia da classe Preferencias para
	 * ter acesso aos demais m�todos
	 * 
	 * @param context
	 *            : context da classe Incial_Activity
	 * @return instancia da classe Preferencias
	 */
	public static Preferencias getInstance(Context context) {
		if (instancia == null)
			instancia = new Preferencias(context);
		return instancia;
	}

	/**
	 * Construtor da classe atribuindo o context recebido
	 * 
	 * @param context
	 *            : context da classe Inicial_Activity
	 */
	public Preferencias(Context context) {
		this.mContext = context;
	}

	/**
	 * M�todo respons�vel por gravar os dados nas preferencias Gravando dados em
	 * formato de String Pode-se gravar nos seguintes formatos: int; float;
	 * string; long; boolean;
	 * 
	 * @param dados
	 *            : Object Dados
	 */
	public void gravarPreferencias(Dados dados) {
		this.dados = dados;

		// Nome do arquivo identificador das preferencias
		pref = mContext.getSharedPreferences(PREF_ID, 0);

		/*
		 * pode-se criar uma vari�vel do tipo Editor para ter acesso as fun��es
		 * de gravar/alterar/excluir preferencias
		 */
		SharedPreferences.Editor editor = pref.edit();

		// Concatenando os dados em uma string
		String valores = dados.getNome() + "/" + dados.getTelefone() + "/"
				+ dados.getSexo() + "/" + dados.getCheck1() + "/"
				+ dados.getCheck2() + "/" + dados.getCheck3() + "/"
				+ dados.getCheck4() + "/" + dados.getCheck5();

		// utilizando o editor para gravar uma string
		editor.putString(dados.getNome(), valores);
		// fechando a grava��o da informa��o
		editor.commit();

		// outra forma mais r�pida de gravar dados sem necessidade de criar
		// vari�vel editor
		// pref.edit().putString(dados.getNome(), valores).commit();
	}

	/**
	 * M�todo para consultar e listar todas chaves armazenadas
	 * 
	 * @return strkeys string contendo todas as chaves salvas
	 */
	public String[] consultaListaChaves() {

		String[] strkeys = null;
		// capturas todas as chaves
		Set<String> key = mContext.getSharedPreferences(PREF_ID, 0).getAll()
				.keySet();
		if (key.size() > 0) {
			// adiciona na string cada chave encontrada
			strkeys = (String[]) key.toArray(new String[key.size()]);
			Arrays.sort(strkeys);
		}
		return strkeys;
	}

	/**
	 * M�todo respons�vel por consultar determinada chave
	 * 
	 * @param chave
	 *            : string da chave a ser pesquisada
	 * @return String com todos valores salvos na chave
	 */
	public String selecionarPreferencias(String chave) {
		pref = mContext.getSharedPreferences(PREF_ID, 0);
		return pref.getString(chave, "");
	}
}
