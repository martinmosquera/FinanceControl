package com.trabalho.financecontrol.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trabalho.financecontrol.R;
import com.trabalho.financecontrol.helper.OperacaoDAO;
import com.trabalho.financecontrol.helper.TipoDAO;
import com.trabalho.financecontrol.model.Operacao;
import com.trabalho.financecontrol.model.Tipo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class OperationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    Tipo tipo;
    OperacaoDAO operacaoDAO;

    Spinner CategoriesSpinner;
    TextView SimpleSpinnerItem;

    EditText ValueEditText;

    TextView DateTextView;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        TipoDAO tipoDAO = new TipoDAO(getApplicationContext());
        List<Tipo> tipos = tipoDAO.getAllTipos();

        operacaoDAO = new OperacaoDAO(getApplicationContext());

        CategoriesSpinner = (Spinner) findViewById(R.id.CategoriesSpinner);
        ValueEditText = findViewById(R.id.ValueEditText);
        DateTextView = findViewById(R.id.DateTextView);

        ArrayAdapter<Tipo> tipoAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, tipos);
        CategoriesSpinner.setAdapter(tipoAdapter);
        CategoriesSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        tipo = (Tipo) adapterView.getSelectedItem();
        SimpleSpinnerItem = findViewById(R.id.SimpleSpinnerItem);

        if (Objects.equals(tipo.getCategoria().getNome(), "Debito")) {
            SimpleSpinnerItem.setTextColor(Color.parseColor("#c73131"));
        } else if (Objects.equals(tipo.getCategoria().getNome(), "Credito")) {
            SimpleSpinnerItem.setTextColor(Color.parseColor("#53ae5b"));
        }

        Toast.makeText(OperationActivity.this,"Selecionado: " + tipo.getNome(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    public void cadastrar(View view) throws ParseException {
        if (tipo != null) {
            if (ValueEditText.getText().toString().length() != 0) {
                if (date != null) {
                    Date data;

                    try {
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        data = format.parse(date);
                    } catch (Exception e) {
                        Toast.makeText(OperationActivity.this,"Selecione uma data válida.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Operacao operacao = new Operacao();

                    operacao.setData(data);
                    operacao.setValor(ValueEditText.getText().toString());
                    operacao.setTipo(tipo);

                    operacaoDAO.insertOperacao(operacao);

                    Toast.makeText(OperationActivity.this,"Operação cadastrada.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OperationActivity.this,"Selecione uma data.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(OperationActivity.this,"Selecione um valor.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(OperationActivity.this,"Selecione uma categoria.", Toast.LENGTH_SHORT).show();
        }
    }

    public void datePicker(View view){
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
        date = day + "/" + (month + 1) + "/" + year;
        DateTextView.setText(date);
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}