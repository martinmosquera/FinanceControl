package com.trabalho.financecontrol.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trabalho.financecontrol.R;
import com.trabalho.financecontrol.adapter.OperationAdapter;
import com.trabalho.financecontrol.helper.OperacaoDAO;
import com.trabalho.financecontrol.model.Operacao;

import java.util.List;

public class SearchDataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_data);
        List<Operacao> lista = new OperacaoDAO(getApplicationContext()).getAllOperacoes();
        OperationAdapter operationAdapter = new OperationAdapter(lista);

        RecyclerView recyclerView = findViewById(R.id.SearchRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        recyclerView.setAdapter(operationAdapter);
    }
}
