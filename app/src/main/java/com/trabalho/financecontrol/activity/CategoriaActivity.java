package com.trabalho.financecontrol.activity;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trabalho.financecontrol.R;
import com.trabalho.financecontrol.helper.TipoDAO;
import com.trabalho.financecontrol.model.Categoria;
import com.trabalho.financecontrol.model.Tipo;

import java.util.ArrayList;
import java.util.List;

public class CategoriaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner CategoriesSpinner;
    String categoria;
    TextView nome;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        List<String> categorias = new ArrayList<>();
        categorias.add("Seleciona...");
        categorias.add("Débito");
        categorias.add("Crédito");
        nome = findViewById(R.id.categNomeEditText);
        CategoriesSpinner = (Spinner) findViewById(R.id.addCategoriespinner);
        ArrayAdapter<String> categoriaAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, categorias);
        CategoriesSpinner.setAdapter(categoriaAdapter);
        CategoriesSpinner.setOnItemSelectedListener(this);

    }



    public void voltar(View view){
            Intent intent = new Intent(this, OperationActivity.class);
            startActivity(intent);
            finish();
        }

    public void addCategorie(View view){
        TipoDAO tipoDAO = new TipoDAO(getApplicationContext());
        Tipo t = new Tipo();
        if(!nome.getText().toString().isEmpty()){
            if(pos == 1 || pos == 2){
                if(pos == 1) t.setCategoria(Categoria.DEBITO);
                else if(pos == 2) t.setCategoria(Categoria.CREDITO);
                t.setNome(nome.getText().toString());
                try{
                    tipoDAO.insertTipo(t);
                    Toast.makeText(this, "Categoria Salva com sucesso", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this,OperationActivity.class);
                    startActivity(i);
                    finish();
                }catch(Exception e){
                    Toast.makeText(this, "Erro ao salvar "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Escolhe um tipo de Categoria", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Digita o nome da Categoria", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoria = (String) adapterView.getSelectedItem();
        pos = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}