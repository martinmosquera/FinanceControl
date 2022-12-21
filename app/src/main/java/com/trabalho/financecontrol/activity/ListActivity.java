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

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    double saldoD;
    double saldoC;
    private OperacaoDAO oDAO;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        oDAO = new OperacaoDAO(getApplicationContext());
        recyclerView = findViewById(R.id.listReciclerView);

    }


    @Override
    protected  void onStart(){
        super.onStart();
        updateReciclerView();
    }

    public void updateReciclerView(){
        List<Operacao> lista = oDAO.getAllByCategoria();
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