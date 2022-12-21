package com.trabalho.financecontrol.activity;

import static com.trabalho.financecontrol.adapter.OperationAdapter.formatValor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trabalho.financecontrol.R;
import com.trabalho.financecontrol.adapter.OperationAdapter;
import com.trabalho.financecontrol.helper.OperacaoDAO;
import com.trabalho.financecontrol.model.Operacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExtractActivity extends AppCompatActivity {

    double saldo;
    private OperacaoDAO oDAO;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract);
        oDAO = new OperacaoDAO(getApplicationContext());
        recyclerView = findViewById(R.id.extractReciclerView);
    }


    @Override
    protected  void onStart(){
        super.onStart();
        updateReciclerView();
    }

    public void updateReciclerView(){
        List<Operacao> lista = oDAO.getAllOperacoes();
        OperationAdapter operationAdapter;
        if(lista.size() < 15){
            operationAdapter = new OperationAdapter(lista);
        }else{
            List<Operacao> listaAux = new ArrayList<>();
            for(int i =0;i <15;i++){
                listaAux.add(lista.get(i));
            }
            operationAdapter = new OperationAdapter(listaAux);
        }

        for (Operacao o : lista) {
            if(o.getCategoria().getNome().equalsIgnoreCase("Debito"))
                saldo -= Double.parseDouble(o.getValor());
            else
                saldo += Double.parseDouble(o.getValor());
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