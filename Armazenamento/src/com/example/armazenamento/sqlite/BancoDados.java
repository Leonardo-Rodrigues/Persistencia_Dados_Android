package com.example.armazenamento.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDados extends SQLiteOpenHelper {

	public static final String NOME_BANCO = "Utfpr_Teste";
	public static final int VERSAO = 1;

	private static BancoDados instance;

	BancoDados(Context context) {
		super(context, NOME_BANCO, null, VERSAO);
	}

	public static BancoDados getInstance(Context context) {
		if (instance == null)
			instance = new BancoDados(context);

		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DadosDAO.SQL_TABELA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
