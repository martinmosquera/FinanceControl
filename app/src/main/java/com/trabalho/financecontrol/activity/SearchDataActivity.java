package com.trabalho.financecontrol.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trabalho.financecontrol.R;
import com.trabalho.financecontrol.adapter.OperationAdapter;
import com.trabalho.financecontrol.helper.OperacaoDAO;
import com.trabalho.financecontrol.model.Operacao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kotlin.jvm.Throws;

public class SearchDataActivity extends AppCompatActivity {

    List<Operacao> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_data);
        Bundle b = getIntent().getExtras();
        OperacaoDAO oDao = new OperacaoDAO(getApplicationContext());
        lista = oDao.getAllOperacoes();
        try{
            Date dat1 = new SimpleDateFormat("dd/MM/yyyy").parse(b.getString("d1"));
            Date dat2 = new SimpleDateFormat("dd/MM/yyyy").parse(b.getString("d2"));
            if(b.getString("cat").equalsIgnoreCase("-1")){
                lista = oDao.getByData(dat1,dat2);
            }else{
                lista = oDao.getByDataCategoria(dat1,dat2,b.getString("cat"));
            }
        }catch(Exception e){
            Log.e("ERRO","Erro ao carregar a lista"+e.getMessage());
        }

        OperationAdapter operationAdapter = new OperationAdapter(lista);

        RecyclerView recyclerView = findViewById(R.id.SearchRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        recyclerView.setAdapter(operationAdapter);
    }
}
