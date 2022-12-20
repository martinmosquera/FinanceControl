package com.trabalho.financecontrol.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trabalho.financecontrol.R;
import com.trabalho.financecontrol.model.Operacao;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private final List<Operacao> list;
    String dateString;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tipo, data, valor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tipo = itemView.findViewById(R.id.TipoTextView);
            data = itemView.findViewById(R.id.DataTextView);
            valor = itemView.findViewById(R.id.ValorTextView);
        }
    }

    public SearchAdapter(List<Operacao> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View operacaoItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search_data_line, parent, false);
        return new MyViewHolder(operacaoItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Operacao operacao = list.get(position);

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateString = dateFormat.format(operacao.getData());

        holder.tipo.setText(operacao.getTipo().getNome());
        holder.data.setText(dateString);

        if (Objects.equals(operacao.getTipo().getCategoria().getNome(), "Debito")) {
            holder.valor.setText("- " + formatValor(Double.parseDouble(operacao.getValor())));
            holder.valor.setTextColor(Color.parseColor("#c73131"));
        } else if (Objects.equals(operacao.getTipo().getCategoria().getNome(), "Credito")) {
            holder.valor.setText("+ " + formatValor(Double.parseDouble(operacao.getValor())));
            holder.valor.setTextColor(Color.parseColor("#53ae5b"));
        }
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static String formatValor(double valor){
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));

        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        return "R$ " + nf.format(valor);
    }
}
