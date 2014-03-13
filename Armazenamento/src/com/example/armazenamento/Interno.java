package com.example.armazenamento;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;
import android.widget.Toast;

import com.example.armazenamento.dados.Dados;

/**
 * Classe responsável pelo controle do armazenamento interno dos dados Classe
 * implementa Serializable por estar gravando objetos em arquivo
 * 
 * @author leonardo.rodrigues
 * 
 */
public class Interno implements Serializable {

	private static final long serialVersionUID = 1639573833321161199L;
	private Context mContext;
	private static Interno instancia;
	ObjectOutput gravacao;
	ObjectInputStream leitura;

	public static Interno getInstance(Context context) {
		if (instancia == null)
			instancia = new Interno(context);
		return instancia;
	}

	public Interno(Context context) {
		this.mContext = context;
	}

	/**
	 * 
	 * @param nomeArquivo nome do arquivo a ser salvo
	 * @param dados objeto do tipo Dados
	 */
	public void gravarDados(String nomeArquivo, Dados dados) {
		try {
			File arquivo = mContext.getFilesDir();
			File outFile = new File(arquivo, nomeArquivo);

			gravacao = new ObjectOutputStream(new FileOutputStream(outFile));
			gravacao.writeObject(dados);
			gravacao.close();
		} catch (Exception e) {
			Toast.makeText(mContext, "Erro Gravação", Toast.LENGTH_SHORT)
					.show();
			throw new RuntimeException("Não foi possível gravar os dados "
					+ e.toString());
		}
		Toast.makeText(mContext, "Arquivo Salvo", Toast.LENGTH_SHORT).show();
	}

	public Dados leituraDados(String nomeArquivo) {

		Dados dado = null;
		try {
			FileInputStream file = mContext.openFileInput(nomeArquivo);
			leitura = new ObjectInputStream(file);
			dado = (Dados) leitura.readObject();
			leitura.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dado;
	}

	public String[] listarDados() {
		String[] listaArquivos = mContext.fileList();
		return listaArquivos;
	}

}
