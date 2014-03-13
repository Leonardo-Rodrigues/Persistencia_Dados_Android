package com.example.armazenamento;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.armazenamento.dados.Dados;
import com.example.armazenamento.sqlite.DadosDAO;

/**
 * Este projeto foi criado para exemplicar as formas de armazenamento de dados
 * Neste exemplo são aplicadas as seguintes formas de persistência: Gravação de
 * preferências; Gravação de arquivos na memória Interna; Gravação de arquivos
 * na memória Externa (se existente); Gravação em banco de dados usando SQLITE;
 * 
 * @author leonardo.rodrigues
 * @since 01/2014
 */
public class Inicial_Activity extends Activity {

	private EditText txtNome, txtFone;
	private RadioGroup grupoSexo;
	private CheckBox check1, check2, check3, check4, check5;
	private Dialog dialog;
	private String sexo = "";
	private ListView lista;
	private Preferencias pref;
	private Interno interno;
	private Externo externo;
	private Dados dados;
	private List<String> listDados;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Definindo visual da Activity conforme layout criado
		setContentView(R.layout.activity_inicial);

		// Criação das variáveis para controle dos elementos da tela
		txtNome = (EditText) findViewById(R.id.txtNome);
		txtFone = (EditText) findViewById(R.id.txtFone);
		grupoSexo = (RadioGroup) findViewById(R.id.radioGroupSexo);
		check1 = (CheckBox) findViewById(R.id.checkBox1);
		check2 = (CheckBox) findViewById(R.id.checkBox2);
		check3 = (CheckBox) findViewById(R.id.checkBox3);
		check4 = (CheckBox) findViewById(R.id.checkBox4);
		check5 = (CheckBox) findViewById(R.id.checkBox5);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_inicial, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_preferencias:
			exibirConsultaPreferencias();
			break;

		case R.id.menu_interno:
			exibirConsultaInterno();
			break;

		case R.id.menu_externo:
			exibirConsultaExterno();
			break;

		case R.id.menu_bd:
			exibirConsultaBD();
			break;
		}
		return true;
	}

	/**
	 * Método que cria uma janela de Dialog com as opções de gravação
	 * 
	 * @param v
	 *            Botão da tela "GRAVAR"
	 */
	public void gravarDados(View v) {

		if (txtNome.getText().toString().equals("")) {
			Toast.makeText(Inicial_Activity.this, "Preencha os dados",
					Toast.LENGTH_SHORT).show();
		} else {
			dialog = new Dialog(Inicial_Activity.this);
			dialog.setTitle("Armazenar Dados");
			dialog.setContentView(R.layout.layout_dialog);
			dialog.show();// Exibir as janelas
			btnsGravacao(dialog);
		}
	}

	/**
	 * Método que adiciona ações aos botões da janela de dialogo Responsável por
	 * controlar qual método de gravação será criado
	 * 
	 * @param dialog
	 *            janela de dialogo criada no método "gravarDados"
	 */
	private void btnsGravacao(final Dialog dialog) {

		// Atribuir qual opção de sexo foi marcada
		if (grupoSexo.getCheckedRadioButtonId() == R.id.radioMasc) {
			sexo = "masculino";
		} else if (grupoSexo.getCheckedRadioButtonId() == R.id.radioFem) {
			sexo = "feminino";
		}

		// variáveis para controle dos elementos na janela de dialogo
		Button btnPref = (Button) dialog.findViewById(R.id.btnPreferencias);
		Button btnInt = (Button) dialog.findViewById(R.id.btnInterno);
		Button btnExt = (Button) dialog.findViewById(R.id.btnExterno);
		Button btnBD = (Button) dialog.findViewById(R.id.btnBancoDados);

		// Evento de CLICK no botão "Preferências"
		btnPref.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Instância um novo objeto do tipo DADOS
				// recebendo os valores preenchidos na tela inicial
				dados = new Dados(txtNome.getText().toString(), txtFone
						.getText().toString(), sexo, check1.isChecked(), check2
						.isChecked(), check3.isChecked(), check4.isChecked(),
						check5.isChecked());
				// instância a classe de preferências para acesso aos metodos
				pref = new Preferencias(Inicial_Activity.this);
				// chama o metodo de gravar preferencias
				// passando o objeto do tipo Dados como parametro
				pref.gravarPreferencias(dados);
				dialog.dismiss();// fecha a janela de dialogo
				limparCampos();
			}
		});

		// Evento de CLICK no botão "Interno"
		btnInt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dados = new Dados(txtNome.getText().toString(), txtFone
						.getText().toString(), sexo, check1.isChecked(), check2
						.isChecked(), check3.isChecked(), check4.isChecked(),
						check5.isChecked());
				interno = new Interno(Inicial_Activity.this);
				interno.gravarDados(txtNome.getText().toString(), dados);
				dialog.dismiss();
				limparCampos();
			}
		});

		// Evento de CLICK no botão "Externo"
		btnExt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dados = new Dados(txtNome.getText().toString(), txtFone
						.getText().toString(), sexo, check1.isChecked(), check2
						.isChecked(), check3.isChecked(), check4.isChecked(),
						check5.isChecked());
				externo = new Externo(Inicial_Activity.this);

				if (externo.verificaStatusCartao()) {
					externo.gravarDados(txtNome.getText().toString(), dados);
					dialog.dismiss();
					limparCampos();
				} else {
					Toast.makeText(Inicial_Activity.this,
							"Cartão SD não está Montado", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		btnBD.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dados = new Dados(txtNome.getText().toString(), txtFone
						.getText().toString(), sexo, check1.isChecked(), check2
						.isChecked(), check3.isChecked(), check4.isChecked(),
						check5.isChecked());
				DadosDAO dadosDAO = DadosDAO.getInstance(Inicial_Activity.this);

				if (dadosDAO.salvar(dados) != -1) {
					Toast.makeText(Inicial_Activity.this, "Registro Salvo",
							Toast.LENGTH_SHORT).show();
					dialog.dismiss();
					limparCampos();
				} else {
					Toast.makeText(Inicial_Activity.this,
							"ERRO Ao Gravar no Banco", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});
	}

	// #####################################################
	// # METODOS PARA GRAVAR E CONSULTAR PREFERÊNCIAS #
	// #####################################################

	// Exibir Dialog com lista de elementos a ser consultado
	private void exibirConsultaPreferencias() {
		pref = new Preferencias(Inicial_Activity.this);

		// cria uma lista com todas as preferencias salvas
		String[] listaDados = pref.consultaListaChaves();

		// Se a lista estiver nula, não existem registros salvos
		if (listaDados != null) {
			dialog = new Dialog(Inicial_Activity.this);
			dialog.setContentView(R.layout.layout_listagem);
			dialog.setTitle("Armazenar Dados");
			lista = (ListView) dialog.findViewById(R.id.lista);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, android.R.id.text1,
					listaDados);
			lista.setAdapter(adapter);
			dialog.show();

			// Evento de CLICK em algum item da lista
			lista.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// Captura o valor do item selecionado na lista
					String item = (String) parent.getItemAtPosition(position);
					// Recebe uma string contendo os dados salvos na
					// preferencias
					String[] dadoRecuperado = pref.selecionarPreferencias(item)
							.split("/");
					dialog.dismiss();
					preencherCampos(dadoRecuperado);
				}
			});
		} else {
			Toast.makeText(Inicial_Activity.this, "Nenhum dado Armazenado",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Método responsável por exibir janela com lista de elementos a serem
	 * consultados
	 */
	private void exibirConsultaInterno() {

		interno = new Interno(Inicial_Activity.this);
		dialog = new Dialog(Inicial_Activity.this);
		dialog.setContentView(R.layout.layout_listagem);
		dialog.setTitle("Armazenamento Interno");
		lista = (ListView) dialog.findViewById(R.id.lista);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				interno.listarDados());
		lista.setAdapter(adapter);
		dialog.show();
		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String item = (String) parent.getItemAtPosition(position);
				Dados dadoRecuperado = interno.leituraDados(item);
				dialog.dismiss();
				preencherCampos(dadoRecuperado);
			}
		});

	}

	/**
	 * Método responsável por exibir janela com lista de elementos a serem
	 * consultados
	 */
	private void exibirConsultaExterno() {
		externo = new Externo(Inicial_Activity.this);
		externo.verificaStatusCartao();
		dialog = new Dialog(Inicial_Activity.this);
		dialog.setContentView(R.layout.layout_listagem);
		dialog.setTitle("Armazenamento Externo");
		lista = (ListView) dialog.findViewById(R.id.lista);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				externo.listarDados());
		lista.setAdapter(adapter);
		dialog.show();
		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String item = (String) parent.getItemAtPosition(position);

				Dados dadoRecuperado = externo.leituraDados(item);
				dialog.dismiss();
				preencherCampos(dadoRecuperado);
			}
		});
	}

	public void exibirConsultaBD() {

		final DadosDAO dadosDAO = DadosDAO.getInstance(Inicial_Activity.this);
		if (dadosDAO.recuperarTodos().size() > 0) {

			listDados = new ArrayList<String>();
			;
			for (int i = 0; i < dadosDAO.recuperarTodos().size(); i++) {

				listDados.add(dadosDAO.recuperarTodos().get(i).getNome());

			}
			dialog = new Dialog(Inicial_Activity.this);
			dialog.setContentView(R.layout.layout_listagem);
			dialog.setTitle("Consulta Banco de Dados");
			lista = (ListView) dialog.findViewById(R.id.lista);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, android.R.id.text1,
					listDados);
			lista.setAdapter(adapter);
			dialog.show();
			lista.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String item = (String) parent.getItemAtPosition(position);
					Dados dadoRecuperado = dadosDAO.consultarPorNome(item);
					dialog.dismiss();
					preencherCampos(dadoRecuperado);
				}
			});
		} else {
			Toast.makeText(Inicial_Activity.this, "Nenhum dado Armazenado",
					Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * Função responsavel por preencher os campos após a consulta
	 * 
	 * @param dadoRecuperado
	 *            : Strin[] com as informações do dado salvo como Preferências
	 */
	public void preencherCampos(String[] dadoRecuperado) {
		txtNome.setText(dadoRecuperado[0].toString());
		txtFone.setText(dadoRecuperado[1].toString());
		if (dadoRecuperado[2].toString().equals("masculino"))
			grupoSexo.check(R.id.radioMasc);
		else
			grupoSexo.check(R.id.radioFem);

		if (dadoRecuperado[3].toString().equals("true"))
			check1.setChecked(true);

		if (dadoRecuperado[4].toString().equals("true"))
			check2.setChecked(true);

		if (dadoRecuperado[5].toString().equals("true"))
			check3.setChecked(true);

		if (dadoRecuperado[6].toString().equals("true"))
			check4.setChecked(true);

		if (dadoRecuperado[7].toString().equals("true"))
			check5.setChecked(true);
	}

	/**
	 * Método responsável por preencher os campos com os dados de armazenamento
	 * interno externo ou BD
	 * 
	 * @param dadoRecuperado
	 *            : Object Dados
	 */
	public void preencherCampos(Dados dadoRecuperado) {
		if (dadoRecuperado != null) {
			txtNome.setText(dadoRecuperado.getNome());
			txtFone.setText(dadoRecuperado.getTelefone());
			check1.setChecked(dadoRecuperado.getCheck1());
			check2.setChecked(dadoRecuperado.getCheck2());
			check3.setChecked(dadoRecuperado.getCheck3());
			check4.setChecked(dadoRecuperado.getCheck4());
			check5.setChecked(dadoRecuperado.getCheck5());
			if (dadoRecuperado.getSexo().equals("masculino"))
				grupoSexo.check(R.id.radioMasc);
			else
				grupoSexo.check(R.id.radioFem);
		} else {
			System.out.println("dado é nulo");
		}
	}

	// Limpar dados após gravar ou a cada nova consulta
	private void limparCampos() {
		txtNome.setText("");
		txtFone.setText("");
		check1.setChecked(false);
		check2.setChecked(false);
		check3.setChecked(false);
		check4.setChecked(false);
		check5.setChecked(false);
		grupoSexo.clearCheck();
	}
}
