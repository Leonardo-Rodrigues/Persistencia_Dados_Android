package com.example.armazenamento.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.armazenamento.dados.Dados;

public class DadosDAO {

	private SQLiteDatabase dataBase = null;

	public static final String NOME_TABELA = "Usuarios";
	public static final String COLUNA_ID = "id";
	public static final String COLUNA_NOME = "nome";
	public static final String COLUNA_TEL = "telefone";
	public static final String COLUNA_SEXO = "sexo";
	public static final String COLUNA_CHECK1 = "check1";
	public static final String COLUNA_CHECK2 = "check2";
	public static final String COLUNA_CHECK3 = "check3";
	public static final String COLUNA_CHECK4 = "check4";
	public static final String COLUNA_CHECK5 = "check5";

	public static final String SQL_TABELA = "CREATE TABLE " + NOME_TABELA + "("
			+ COLUNA_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT," + COLUNA_NOME
			+ " STRING," + COLUNA_TEL + " STRING," + COLUNA_SEXO + " STRING,"
			+ COLUNA_CHECK1 + " BOOLEAN," + COLUNA_CHECK2 + " BOOLEAN,"
			+ COLUNA_CHECK3 + " BOOLEAN," + COLUNA_CHECK4 + " BOOLEAN,"
			+ COLUNA_CHECK5 + " BOOLEAN" + ")";

	private static DadosDAO instancia;

	public static DadosDAO getInstance(Context context) {
		if (instancia == null)
			instancia = new DadosDAO(context);
		return instancia;
	}

	private DadosDAO(Context context) {
		BancoDados persistenceHelper = BancoDados.getInstance(context);
		dataBase = persistenceHelper.getWritableDatabase();
	}

	public long salvar(Dados dado) {
		ContentValues values = gerarDadosGravacao(dado);
		return dataBase.insert(NOME_TABELA, null, values);
	}

	private ContentValues gerarDadosGravacao(Dados dado) {
		ContentValues values = new ContentValues();
		values.put(COLUNA_NOME, dado.getNome());
		values.put(COLUNA_TEL, dado.getTelefone());
		values.put(COLUNA_SEXO, dado.getSexo());
		values.put(COLUNA_CHECK1, dado.getCheck1());
		values.put(COLUNA_CHECK2, dado.getCheck2());
		values.put(COLUNA_CHECK3, dado.getCheck3());
		values.put(COLUNA_CHECK4, dado.getCheck4());
		values.put(COLUNA_CHECK5, dado.getCheck5());
		return values;
	}

	public void fecharConexao() {
		if (dataBase != null && dataBase.isOpen())
			dataBase.close();
	}

	// Executar consulta
	public List<Dados> recuperarTodos() {
		String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
		Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
		List<Dados> contas = varrerListaConsulta(cursor);

		return contas;
	}

	public Dados consultarPorNome(String nome) {
		
		String[] args = new String[] {nome};
		String consulta = "SELECT * FROM " + NOME_TABELA + " WHERE "
				+ COLUNA_NOME + "=?";
		Cursor cursor = dataBase.rawQuery(consulta, args);

		if (cursor != null) {
			cursor.moveToFirst();
			Dados dadoRecuperado = recuperarDado(cursor);
			return dadoRecuperado;
		} else {
			return null;
		}
	}

	private Dados recuperarDado(Cursor cursor) {

		if (cursor.moveToFirst()) {
			int indexID = cursor.getColumnIndex(COLUNA_ID);
			int indexNome = cursor.getColumnIndex(COLUNA_NOME);
			int indexTel = cursor.getColumnIndex(COLUNA_TEL);
			int indexSexo = cursor.getColumnIndex(COLUNA_SEXO);
			int indexCheck1 = cursor.getColumnIndex(COLUNA_CHECK1);
			int indexCheck2 = cursor.getColumnIndex(COLUNA_CHECK2);
			int indexCheck3 = cursor.getColumnIndex(COLUNA_CHECK3);
			int indexCheck4 = cursor.getColumnIndex(COLUNA_CHECK4);
			int indexCheck5 = cursor.getColumnIndex(COLUNA_CHECK5);

			int id = cursor.getInt(indexID);
			String nome = cursor.getString(indexNome);
			String tel = cursor.getString(indexTel);
			String sexo = cursor.getString(indexSexo);
			int check1 = cursor.getInt(indexCheck1);
			int check2 = cursor.getInt(indexCheck2);
			int check3 = cursor.getInt(indexCheck3);
			int check4 = cursor.getInt(indexCheck4);
			int check5 = cursor.getInt(indexCheck5);

			boolean _check1 = false, _check2 = false, _check3 = false, _check4 = false, _check5 = false;

			if (check1 == 1)
				_check1 = true;
			if (check2 == 1)
				_check2 = true;
			if (check3 == 1)
				_check3 = true;
			if (check4 == 1)
				_check4 = true;
			if (check5 == 1)
				_check5 = true;

			Dados dado = new Dados(nome, tel, sexo, _check1, _check2, _check3,
					_check4, _check5);

			return dado;
		}

		return null;
	}

	public void editar(Dados dado) {
		ContentValues valores = gerarDadosGravacao(dado);

		String[] valoresParaSubstituir = { String.valueOf(dado.getNome()) };

		dataBase.update(NOME_TABELA, valores, COLUNA_NOME + " = ?",
				valoresParaSubstituir);
	}

	private List<Dados> varrerListaConsulta(Cursor cursor) {
		List<Dados> lista_dados = new ArrayList<Dados>();
		if (cursor == null)
			return lista_dados;

		try {

			if (cursor.moveToFirst()) {
				do {
					int indexID = cursor.getColumnIndex(COLUNA_ID);
					int indexNome = cursor.getColumnIndex(COLUNA_NOME);
					int indexTel = cursor.getColumnIndex(COLUNA_TEL);
					int indexSexo = cursor.getColumnIndex(COLUNA_SEXO);
					int indexCheck1 = cursor.getColumnIndex(COLUNA_CHECK1);
					int indexCheck2 = cursor.getColumnIndex(COLUNA_CHECK2);
					int indexCheck3 = cursor.getColumnIndex(COLUNA_CHECK3);
					int indexCheck4 = cursor.getColumnIndex(COLUNA_CHECK4);
					int indexCheck5 = cursor.getColumnIndex(COLUNA_CHECK5);

					int id = cursor.getInt(indexID);
					String nome = cursor.getString(indexNome);
					String tel = cursor.getString(indexTel);
					String sexo = cursor.getString(indexSexo);
					int check1 = cursor.getInt(indexCheck1);
					int check2 = cursor.getInt(indexCheck2);
					int check3 = cursor.getInt(indexCheck3);
					int check4 = cursor.getInt(indexCheck4);
					int check5 = cursor.getInt(indexCheck5);

					boolean _check1 = false, _check2 = false, _check3 = false, _check4 = false, _check5 = false;

					if (check1 == 1)
						_check1 = true;
					if (check2 == 1)
						_check2 = true;
					if (check3 == 1)
						_check3 = true;
					if (check4 == 1)
						_check4 = true;
					if (check5 == 1)
						_check5 = true;

					Dados dado = new Dados(nome, tel, sexo, _check1, _check2,
							_check3, _check4, _check5);

					lista_dados.add(dado);

				} while (cursor.moveToNext());
			}

		} finally {
			cursor.close();
		}
		return lista_dados;
	}
}
