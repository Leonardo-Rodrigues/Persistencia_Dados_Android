package com.example.armazenamento;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.armazenamento.dados.Dados;

public class Externo {

	private Context mContext;
	private static Externo instancia;
	private ObjectOutput gravacao;
	private ObjectInputStream leitura;
	private boolean flag = false;

	public static Externo getInstance(Context context) {
		if (instancia == null)
			instancia = new Externo(context);
		return instancia;
	}

	public Externo(Context context) {
		this.mContext = context;
	}

	public boolean verificaStatusCartao() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			Toast.makeText(mContext, "Cartão está montado", Toast.LENGTH_SHORT)
					.show();
			flag = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			Toast.makeText(mContext, "Cartão modo Leitura", Toast.LENGTH_SHORT)
					.show();
		} else if (Environment.MEDIA_REMOVED.equals(state)) {
			Toast.makeText(mContext, "Cartão não encontrado",
					Toast.LENGTH_SHORT).show();
		} else if (Environment.MEDIA_UNMOUNTED.equals(state)) {
			Toast.makeText(mContext, "Cartão NÃO está montado",
					Toast.LENGTH_SHORT).show();
		}
		return flag;
	}

	public void gravarDados(String nomeArquivo, Dados dados) {
		if (flag) {
			try {
				File sdCard = Environment.getExternalStorageDirectory();
				File dir = new File(sdCard.getAbsolutePath() + "/Dir_Utfpr");
				dir.mkdirs();
				File arquivo = new File(dir, nomeArquivo);

				FileOutputStream file = new FileOutputStream(arquivo);
				ObjectOutputStream oos = new ObjectOutputStream(file);
				oos.writeObject(dados);
				oos.flush();

				oos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			Toast.makeText(mContext, "Arquivo Salvo", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public Dados leituraDados(String nomeArquivo) {
		Dados dado = null;
		try {
			File sdCard = Environment.getExternalStorageDirectory();
			File dir = new File(sdCard.getAbsolutePath() + "/Dir_Utfpr");

			File arquivo = new File(dir, nomeArquivo);

			FileInputStream file = new FileInputStream(arquivo);

			ObjectInputStream ois = new ObjectInputStream(file);
			dado = (Dados) ois.readObject();
			ois.close();
			return dado;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public List<String> listarDados() {
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath() + "/Dir_Utfpr");

		File list[] = dir.listFiles();

		List<String> listaArquivos = new ArrayList<String>();

		for (int i = 0; i < list.length; i++) {

			listaArquivos.add(list[i].getName());
		}
		return listaArquivos;
	}

}
