package com.trabalho.financecontrol.activity;

import static com.trabalho.financecontrol.adapter.OperationAdapter.formatValor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.trabalho.financecontrol.R;
import com.trabalho.financecontrol.helper.OperacaoDAO;
import com.trabalho.financecontrol.helper.TipoDAO;
import com.trabalho.financecontrol.model.Categoria;
import com.trabalho.financecontrol.model.Operacao;
import com.trabalho.financecontrol.model.Tipo;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class OperationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    Tipo tipo;
    OperacaoDAO operacaoDAO;
    Operacao operacao;
    Spinner CategoriesSpinner;
    TextView SimpleSpinnerItem;
    EditText ValueEditText;
    TextView DateTextView;
    String date;
    boolean edited;
    private String current = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        edited = false;
        TipoDAO tipoDAO = new TipoDAO(getApplicationContext());
        List<String> tiposNames = new ArrayList<>();
        List<Tipo> tipos = tipoDAO.getAllTipos();
        if(tipos.size() == 0){
            Tipo t = new Tipo();
            t.setNome("Moradia");
            t.setCategoria(Categoria.DEBITO);
            tipoDAO.insertTipo(t);
            tiposNames.add(t.getNome());
            t.setNome("Saúde");
            t.setCategoria(Categoria.DEBITO);
            tipoDAO.insertTipo(t);
            tiposNames.add(t.getNome());
            t.setNome("Outros -");
            t.setCategoria(Categoria.DEBITO);
            tipoDAO.insertTipo(t);
            tiposNames.add(t.getNome());
            t.setNome("Salário");
            t.setCategoria(Categoria.CREDITO);
            tipoDAO.insertTipo(t);
            tiposNames.add(t.getNome());
            t.setNome("Outros +");
            t.setCategoria(Categoria.CREDITO);
            tipoDAO.insertTipo(t);
            tiposNames.add(t.getNome());
            tipos = tipoDAO.getAllTipos();
        }else{
            for(Tipo t : tipos){
                tiposNames.add(t.getNome());
            }
        }
        operacaoDAO = new OperacaoDAO(getApplicationContext());
        CategoriesSpinner = (Spinner) findViewById(R.id.CategoriesSpinner);
        ValueEditText = findViewById(R.id.ValueEditText);
        ValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    ValueEditText.removeTextChangedListener(this);
                    String numberString = charSequence.toString().replaceAll("[R$,.]", "");
                    double parsed = Double.parseDouble(numberString);
                    String formatted = formatValor((parsed/100));
                    current = formatted;
                    ValueEditText.setText(formatted);
                    ValueEditText.setSelection(formatted.length());
                    ValueEditText.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        DateTextView = findViewById(R.id.DateTextView);
        operacao = new Operacao();
        Operacao selected = (Operacao) getIntent().getSerializableExtra("operacao");
        if(selected != null){
            date = new SimpleDateFormat("dd/MM/yyyy").format(selected.getData());
            DateTextView.setText(date);
            double parsed = Double.parseDouble(selected.getValor());
            String formatted = formatValor((parsed));
            current = formatted;
            ValueEditText.setText(formatted);
            int indexCat = tiposNames.indexOf(selected.getTipo().getNome());
            CategoriesSpinner.post(new Runnable() {
                @Override
                public void run() {
                    CategoriesSpinner.setSelection(indexCat);
                }
            });
            operacao.setId(selected.getId());
            edited = true;
        }
        ArrayAdapter<Tipo> tipoAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, tipos);
        CategoriesSpinner.setAdapter(tipoAdapter);
        CategoriesSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        tipo = (Tipo) adapterView.getSelectedItem();
        SimpleSpinnerItem = findViewById(R.id.SimpleSpinnerItem);
        operacao.setTipo(tipo);
        if (Objects.equals(tipo.getCategoria().getNome(), "Debito")) {
            SimpleSpinnerItem.setTextColor(Color.parseColor("#c73131"));
            operacao.setCategoria(Categoria.DEBITO);
        } else if (Objects.equals(tipo.getCategoria().getNome(), "Credito")) {
            SimpleSpinnerItem.setTextColor(Color.parseColor("#53ae5b"));
            operacao.setCategoria(Categoria.CREDITO);
        }

        Toast.makeText(OperationActivity.this, "Selecionado: " + tipo.getNome(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void cadastrar(View view) throws ParseException {
        if(edited){
            if(tipo != null){
                if(ValueEditText.getText().toString().length() != 0){
                    if (date != null){
                        try {
                            Date dat = new SimpleDateFormat("dd/MM/yyyy").parse(DateTextView.getText().toString());
                            operacao.setData(dat);
                        } catch (Exception e) {
                            Toast.makeText(OperationActivity.this, "Selecione uma data válida.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String valorS = ValueEditText.getText().toString().replaceAll("[R$,.]", "");
                        Double valorD = Double.parseDouble(valorS)/100;
                        operacao.setValor(String.valueOf(valorD));
                        if(operacaoDAO.updateOperacao(operacao)){
                            Toast.makeText(OperationActivity.this, "Operação Atualizada.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(OperationActivity.this, "Erro na Atualização .", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        }else{
            if (tipo != null) {
                if (ValueEditText.getText().toString().length() != 0) {
                    if (date != null) {

                        try {
                            Date dat = new SimpleDateFormat("dd/MM/yyyy").parse(DateTextView.getText().toString());
                            operacao.setData(dat);
                        } catch (Exception e) {
                            Toast.makeText(OperationActivity.this, "Selecione uma data válida.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String valorS = ValueEditText.getText().toString().replaceAll("[R$,.]", "");
                        Double valorD = Double.parseDouble(valorS)/100;
                        operacao.setValor(String.valueOf(valorD));
                        operacaoDAO.insertOperacao(operacao);
                        Toast.makeText(OperationActivity.this, "Operação cadastrada.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(OperationActivity.this, "Selecione uma data.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OperationActivity.this, "Selecione um valor.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(OperationActivity.this, "Selecione uma categoria.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void datePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        date = day+ "/"+(month+1)+"/" + year;
        DateTextView.setText(date);
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void loadAddCategoria(View view){
        Intent intent = new Intent(this, CategoriaActivity.class);
        startActivity(intent);
        finish();
    }
}