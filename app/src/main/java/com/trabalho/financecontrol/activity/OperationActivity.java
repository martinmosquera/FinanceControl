package com.trabalho.financecontrol.activity;

import androidx.appcompat.app.AppCompatActivity;

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
import com.trabalho.financecontrol.model.Category;
import com.trabalho.financecontrol.model.Operation;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OperationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    Category categoria;
    OperationDAO operacaoDAO;

    Spinner CategoriesSpinner;
    TextView SimpleSpinnerItem;

    EditText ValueEditText;

    TextView DateTextView;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        CategoryDAO categoriaDAO = new CategoryDAO(getApplicationContext());
        List<Category> categorias = categoriaDAO.getAllCategorias();

        CategoriesSpinner = (Spinner) findViewById(R.id.CategoriesSpinner);
        ValueEditText = findViewById(R.id.ValueEditText);
        DateTextView = findViewById(R.id.DateTextView);

        ArrayAdapter<Category> categoriaAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, categorias);
        CategoriesSpinner.setAdapter(categoriaAdapter);
        CategoriesSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoria = (Category) adapterView.getSelectedItem();
        SimpleSpinnerItem = findViewById(R.id.SimpleSpinnerItem);

        if (categoria.getNome() == 1) {
            SimpleSpinnerItem.setTextColor(Color.parseColor("#53ae5b"));
        } else {
            SimpleSpinnerItem.setTextColor(Color.parseColor("#c73131"));
        }

        Toast.makeText(OperationActivity.this,"Selecionado: " + categoria, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    public void cadastrar(View view) throws ParseException {
        if (categoria != null) {
            if (ValueEditText.getText().toString().length() != 0) {
                if (date != null) {
                    Date data;

                    try {
                        data = Utils.stringToDate(date);
                    } catch (Exception e) {
                        Toast.makeText(OperationActivity.this,"Selecione uma data válida.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Operation operation = new Operation();

                    operation.setDate(data);
                    operation.setValue(Double.parseDouble(ValueEditText.getText().toString()));
                    operation.setCategory(categoria);

                    operacaoDAO.insertOperation(operation);

                    Toast.makeText(OperationActivity.this,"Operação cadastrada. ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OperationActivity.this,"Selecione uma data.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(OperationActivity.this,"Selecione um valor.", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(OperationActivity.this,"Selecione uma categoria.", Toast.LENGTH_SHORT).show();
            return;
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