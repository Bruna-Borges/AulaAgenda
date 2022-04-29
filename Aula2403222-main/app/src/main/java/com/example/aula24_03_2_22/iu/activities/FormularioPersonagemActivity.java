package com.example.aula24_03_2_22.iu.activities;

import static com.example.aula24_03_2_22.iu.activities.ConstatesActivities.CHAVE_PERSONAGEM;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aula24_03_2_22.R;
import com.example.aula24_03_2_22.dao.PersonagemDAO;
import com.example.aula24_03_2_22.model.Personagem;

public class FormularioPersonagemActivity extends AppCompatActivity {
    private static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar o Personagem"; //
    private static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo Personagem";

    //variaveis que absorvem o que usuario vai ultilizar e enviar para o dao
    private EditText campoNome;
    private EditText campoNascimento;
    private EditText campoAltura;
    //instanciando o obejto que eu estou criando
    private final PersonagemDAO dao = new PersonagemDAO();
    private Personagem personagem;

    @Override
    //criando o oncreate para usar o menu item e para ele funcionar eu passo o parametro menu
    public boolean onCreateOptionsMenu(Menu menu) {
        //para pegar algo dentro do item que esta fazendo referencia
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        // aqui ele chama metodo base do menu salvar
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    //
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        //digo que meu ItemId que ele tem como referencia o a arquivo abaixo
        if (itemId == R.id.activity_formulario_personagem_menu_salvar){
            //chamando um metodo pra finalizar o formulario
            finalizarFormulario();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    //
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        inicializacaoCampos();
        //configuraBotaoSalvar();
        carregaPersonagem();
    }
    //metodo para carregar o personagem
    private void carregaPersonagem(){
        //comando que coleta uma informacao
        Intent dados = getIntent();
        // o intent vai verificar se tem alguma informacao para carregar
        if(dados.hasExtra(CHAVE_PERSONAGEM)){ //<- hasExtra retorna a informacao que tenha dentro do chave
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);
            //aqui eu coloco a referencia que eu vou trazer meu personagem
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preencheCampos();
        }else {
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);
            personagem = new Personagem(); // aqui eu seleciono meu personagem com todos os itens que ele possui

        }
    }
    private void preencheCampos() {
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
        personagem.setAltura(altura);
        personagem.setNome(nome);
        personagem.setNascimento(nascimento);
    }
    private void finalizarFormulario() {
        preencherPersonagem();
        if(personagem.idValido())
        {
            dao.edita(personagem);
            finish();
        }else {
            dao.salvar(personagem);
        }
        finish();
    }

    //aqui estou pegando a referencia do meu campo e colocando na minha variavel
    private void inicializacaoCampos() {
        campoNome = findViewById(R.id.ediText_nome);
        campoNascimento = findViewById(R.id.editText_nascimento);
        campoAltura = findViewById(R.id.editText_altura);

    }
    private void preencherPersonagem() {
        String nome = campoNome.getText().toString();
        String nascimento = campoNascimento.getText().toString();
        String altura = campoAltura.getText().toString();

        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);
    }
}
