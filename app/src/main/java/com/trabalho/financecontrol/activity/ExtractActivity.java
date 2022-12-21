package com.trabalho.financecontrol.activity;

import static com.trabalho.financecontrol.adapter.OperationAdapter.formatValor;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trabalho.financecontrol.R;
import com.trabalho.financecontrol.adapter.OperationAdapter;
import com.trabalho.financecontrol.helper.OperacaoDAO;
import com.trabalho.financecontrol.helper.RecyclerItemClickListener;
import com.trabalho.financecontrol.model.Operacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExtractActivity extends AppCompatActivity {

    double saldo;
    private OperacaoDAO oDAO;
    private RecyclerView recyclerView;
    List<Operacao> listaView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract);
        oDAO = new OperacaoDAO(getApplicationContext());
        recyclerView = findViewById(R.id.extractReciclerView);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Operacao selected = listaView.get(position);
                Intent intent = new Intent(ExtractActivity.this,OperationActivity.class);
                intent.putExtra("operacao",selected);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Operacao selected = listaView.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(ExtractActivity.this);
                dialog.setTitle("Confirmar Exclusão");
                dialog.setMessage("Deseja Excluir a Operação "+selected.getTipo().getNome()+"?");
                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(oDAO.deleteOperacao(selected)){
                            updatedRecyclerView();
                            Toast.makeText(ExtractActivity.this, "Operação excluida!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ExtractActivity.this, "Erro ao excluir a operação :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("Não",null);
                dialog.create();
                dialog.show();
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));

    }


    @Override
    protected  void onStart(){
        super.onStart();
        updatedRecyclerView();
    }

    public void updatedRecyclerView(){
        List<Operacao> lista = oDAO.getAllOperacoes();
        saldo =0;
        OperationAdapter operationAdapter = null;
        if(lista.size() > 0){

            if(lista.size() < 15){
                operationAdapter = new OperationAdapter(lista);
                listaView = lista;
            }else{
                List<Operacao> listaAux = new ArrayList<>();
                for(int i =0;i <15;i++){
                    listaAux.add(lista.get(i));
                }
                operationAdapter = new OperationAdapter(listaAux);
                listaView = listaAux;
            }

            for (Operacao o : lista) {
                if(o.getCategoria().getNome().equalsIgnoreCase("Debito"))
                    saldo -= Double.parseDouble(o.getValor());
                else
                    saldo += Double.parseDouble(o.getValor());
            }
        }else{
            lista = new ArrayList<Operacao>();
            operationAdapter = new OperationAdapter(lista);
        }

        TextView total = findViewById(R.id.totaltextView);
        total.setText(formatValor(saldo));
        if(saldo <= 0){
            total.setTextColor(Color.parseColor("#c73131"));
        }else{
            total.setTextColor(Color.parseColor("#53ae5b"));
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        recyclerView.setAdapter(operationAdapter);
    }

    public void loadMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}