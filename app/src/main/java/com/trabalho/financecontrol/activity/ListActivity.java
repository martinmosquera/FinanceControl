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

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    double saldoD;
    double saldoC;
    private OperacaoDAO oDAO;
    private RecyclerView recyclerView;
    List<Operacao> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        oDAO = new OperacaoDAO(getApplicationContext());
        recyclerView = findViewById(R.id.listReciclerView);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Operacao selected = lista.get(position);
                Intent intent = new Intent(ListActivity.this,OperationActivity.class);
                intent.putExtra("operacao",selected);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Operacao selected = lista.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(ListActivity.this);
                dialog.setTitle("Confirmar Exclusão");
                dialog.setMessage("Deseja Excluir a Operação "+selected.getTipo().getNome()+"?");
                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(oDAO.deleteOperacao(selected)){
                            updatedRecyclerView();
                            Toast.makeText(ListActivity.this, "Operação excluida!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ListActivity.this, "Erro ao excluir a operação :(", Toast.LENGTH_SHORT).show();
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
        List<Operacao> lista = oDAO.getAllByCategoria();
        saldoD = 0;
        saldoC = 0;
        List<Operacao> listaD =  new ArrayList<>();
        List<Operacao> listaC =  new ArrayList<>();
        for (Operacao o : lista) {
            if(o.getCategoria().getNome().equalsIgnoreCase("Debito")){
                saldoD += Double.parseDouble(o.getValor());
                listaD.add(o);
            }else{
                saldoC += Double.parseDouble(o.getValor());
                listaC.add(o);
            }

        }
        listaD.addAll(listaC);
        this.lista = listaD;
        OperationAdapter operationAdapter = new OperationAdapter(listaD);
        TextView totalD = findViewById(R.id.totalDebitotextView);
        TextView totalC = findViewById(R.id.totalCreditotextView);
        totalD.setTextColor(Color.parseColor("#c73131"));
        totalC.setTextColor(Color.parseColor("#53ae5b"));
        totalD.setText(formatValor(saldoD));
        totalC.setText(formatValor(saldoC));

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