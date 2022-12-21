package com.trabalho.financecontrol.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.trabalho.financecontrol.R;
import com.trabalho.financecontrol.helper.OperacaoDAO;
import com.trabalho.financecontrol.model.Categoria;
import com.trabalho.financecontrol.model.Operacao;
import com.trabalho.financecontrol.model.Tipo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static List<Operacao> operacoes = new ArrayList<>();
    OperacaoDAO operacaoDAO;
    Spinner CategoriesSpinner;
    TextView SimpleSpinnerItem;

    TextView Date1textView, Date2textView;
    Button Date1Button, Date2Button;
    String date1, date2, categoria;
    View.OnClickListener datePicker = view -> {
        final View vv = view;

        DatePickerDialog datePickerDialog = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                if (vv.getId() == R.id.Date1Button) {
                    date1 = day + "/" + (month + 1) + "/" + year;
                    Date1textView.setText(date1);
                } else {
                    date2 = day + "/" + (month + 1) + "/" + year;
                    Date2textView.setText(date2);
                }
            }
        }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        operacaoDAO = new OperacaoDAO(getApplicationContext());

        Date1textView = findViewById(R.id.Date1TextView);
        Date2textView = findViewById(R.id.Date2TextView);
        Date1Button = findViewById(R.id.Date1Button);
        Date2Button = findViewById(R.id.Date2Button);

        List<String> categorias = new ArrayList<>();
        categorias.add("Todas");
        categorias.add("Débito");
        categorias.add("Crédito");

        CategoriesSpinner = (Spinner) findViewById(R.id.CategoriesSpinner);
        ArrayAdapter<String> categoriaAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, categorias);
        CategoriesSpinner.setAdapter(categoriaAdapter);
        CategoriesSpinner.setOnItemSelectedListener(this);

        Date1Button.setOnClickListener(datePicker);
        Date2Button.setOnClickListener(datePicker);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoria = (String) adapterView.getSelectedItem();
        SimpleSpinnerItem = findViewById(R.id.SimpleSpinnerItem);

        if (categoria.equals("Débito")) {
            SimpleSpinnerItem.setTextColor(Color.parseColor("#c73131"));
        } else if (categoria.equals("Crédito")) {
            SimpleSpinnerItem.setTextColor(Color.parseColor("#53ae5b"));
        }

        Toast.makeText(SearchActivity.this, "Selecionado: " + categoria, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void search(View view) throws ParseException {
        if (date1 != null) {
            if (date2 != null) {
                if (categoria != null) {
                    try {
                        Intent i = new Intent(this, SearchDataActivity.class);
                        i.putExtra("d1",Date1textView.getText().toString());
                        i.putExtra("d2",Date2textView.getText().toString());
                        String cate= "";
                        if(categoria.equalsIgnoreCase("Todas"))
                            cate = "-1";
                        else if(categoria.equalsIgnoreCase("Débito"))
                            cate = "debito";
                        else if(categoria.equalsIgnoreCase("Crédito"))
                            cate = "credito";
                        i.putExtra("cat",cate);
                        startActivity(i);
                    } catch (Exception e) {
                        Toast.makeText(SearchActivity.this, "Selecione uma data válida.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(SearchActivity.this, "Selecione uma das categorias.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SearchActivity.this, "Selecione a data final.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SearchActivity.this, "Selecione a data inicial.", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}