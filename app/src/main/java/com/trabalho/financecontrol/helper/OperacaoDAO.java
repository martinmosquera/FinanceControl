package com.trabalho.financecontrol.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.trabalho.financecontrol.model.Categoria;
import com.trabalho.financecontrol.model.Operacao;
import com.trabalho.financecontrol.model.Tipo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class OperacaoDAO {

    private SQLiteDatabase write;
    private SQLiteDatabase read;
    private Context context;

    public OperacaoDAO(Context con) {
        context = con;
        DBHelper db = new DBHelper(con);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    public long insertOperacao(Operacao operacao) {
        ContentValues values = new ContentValues();
        values.put("tipo", operacao.getTipo().getId());
        values.put("valor", operacao.getValor());
        String inDate = new SimpleDateFormat("dd/MM/yyyy").format(operacao.getData());
        values.put("data", inDate);
        values.put("categoria", operacao.getCategoria().getNome());
        long id = -1;
        try {
            id = write.insert(DBHelper.TABLE2_NAME, null, values);
            Log.i("INFO", "Operacao salva com sucesso!");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao salvar o nova operacao" + e.getMessage());
        }
        return id;
    }

    public boolean updateOperacao(Operacao operacao) {
        ContentValues values = new ContentValues();
        values.put("tipo", operacao.getTipo().getId());
        values.put("valor", operacao.getValor());
        String inDate = new SimpleDateFormat("dd/MM/yyyy").format(operacao.getData());
        values.put("data", inDate);
        values.put("categoria", operacao.getCategoria().getNome());

        try {
            String[] args = {String.valueOf(operacao.getId())};
            write.update(DBHelper.TABLE2_NAME, values, "id=?", args);
            Log.i("INFO", "Operacao Atualizada com sucesso!");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao atualizar a operacao" + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteOperacao(Operacao operacao) {
        try {
            String[] args = {String.valueOf(operacao.getId())};
            write.delete(DBHelper.TABLE2_NAME, "id=?", args);
            Log.i("INFO", "Operacao removida com sucesso!");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao remover a operacao" + e.getMessage());
            return false;
        }
        return true;
    }

    public List<Operacao> getAllOperacoes() {
        List<Operacao> lista = new ArrayList<>();
        try{
            Cursor cursor = read.query(DBHelper.TABLE2_NAME, new String[]{"id", "tipo", "data", "valor", "categoria"}, null, null, null, null, null);
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                Operacao operacao = new Operacao();
                int indexId = cursor.getColumnIndex("id");
                int indexTipo = cursor.getColumnIndex("tipo");
                int indexData = cursor.getColumnIndex("data");
                int indexValor = cursor.getColumnIndex("valor");
                int indexCategoria = cursor.getColumnIndex("categoria");
                Long id = cursor.getLong(indexId);
                long tipo = cursor.getLong(indexTipo);
                String data = cursor.getString(indexData);
                String valor = cursor.getString(indexValor);
                String categoria = cursor.getString(indexCategoria);
                operacao.setId(id);
                TipoDAO tipoDAO = new TipoDAO(context);
                Tipo t = tipoDAO.getById(tipo);
                operacao.setTipo(t);
                Date dat = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                operacao.setData(dat);
                operacao.setValor(valor);
                if (categoria.equalsIgnoreCase("Debito"))
                    operacao.setCategoria(Categoria.DEBITO);
                else operacao.setCategoria(Categoria.CREDITO);
                lista.add(operacao);
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Collections.sort(lista,Collections.reverseOrder());
        return lista;
    }

    public Operacao getById(long i) {
        String[] args = {String.valueOf(i)};
        Cursor cursor = read.query(DBHelper.TABLE2_NAME, new String[]{"id", "tipo", "data", "valor", "categoria"}, "id=?", args, null, null, null);
        Operacao operacao = new Operacao();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int indexTipo = cursor.getColumnIndex("tipo");
            int indexData = cursor.getColumnIndex("data");
            int indexValor = cursor.getColumnIndex("valor");
            int indexCategoria = cursor.getColumnIndex("categoria");
            long tipo = cursor.getLong(indexTipo);
            String data = cursor.getString(indexData);
            String valor = cursor.getString(indexValor);
            String categoria = cursor.getString(indexCategoria);
            operacao.setId(i);
            TipoDAO tipoDAO = new TipoDAO(context);
            Tipo t = tipoDAO.getById(tipo);
            operacao.setTipo(t);
            Date dat = null;
            try {
                dat = new SimpleDateFormat("dd/MM/yyyy").parse(data);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            operacao.setData(dat);
            operacao.setValor(valor);
            if (categoria.equalsIgnoreCase("Debito"))
                operacao.setCategoria(Categoria.DEBITO);
            else operacao.setCategoria(Categoria.CREDITO);
        }
        return operacao;
    }

    public List<Operacao> getByData(java.util.Date d1, java.util.Date d2) {
        List<Operacao> lista = new ArrayList<>();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        String d1String = sdf1.format(d1);
        String d2String = sdf1.format(d2);
        String[] args = {d1String, d2String};
        Cursor cursor = read.query(DBHelper.TABLE2_NAME, new String[]{"id", "tipo", "data", "valor", "categoria"}, "(data BETWEEN ? AND ?)", args, null, null, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Operacao operacao = new Operacao();
            int indexId = cursor.getColumnIndex("id");
            int indexTipo = cursor.getColumnIndex("tipo");
            int indexData = cursor.getColumnIndex("data");
            int indexValor = cursor.getColumnIndex("valor");
            int indexCategoria = cursor.getColumnIndex("categoria");
            Long id = cursor.getLong(indexId);
            long tipo = cursor.getLong(indexTipo);
            String data = cursor.getString(indexData);
            String valor = cursor.getString(indexValor);
            String cat = cursor.getString(indexCategoria);
            operacao.setId(id);
            TipoDAO tipoDAO = new TipoDAO(context);
            Tipo t = tipoDAO.getById(tipo);
            operacao.setTipo(t);
            Date dat = null;
            try {
                dat = new SimpleDateFormat("dd/MM/yyyy").parse(data);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            operacao.setData(dat);
            operacao.setValor(valor);
            if (cat.equalsIgnoreCase("Debito"))
                operacao.setCategoria(Categoria.DEBITO);
            else operacao.setCategoria(Categoria.CREDITO);
            if(dat.compareTo(d1) > 0 || dat.compareTo(d1) == 0 ){
                if(dat.compareTo(d2) < 0 || dat.compareTo(d2) == 0){
                    lista.add(operacao);
                }
            }

        }
        Collections.sort(lista);
        return lista;
    }

    public List<Operacao> getByDataCategoria(java.util.Date d1, java.util.Date d2, String c) {
        List<Operacao> lista = new ArrayList<>();
        Cursor cursor = read.query(DBHelper.TABLE2_NAME, new String[]{"id", "tipo", "data", "valor", "categoria"}, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Operacao operacao = new Operacao();
            int indexId = cursor.getColumnIndex("id");
            int indexTipo = cursor.getColumnIndex("tipo");
            int indexData = cursor.getColumnIndex("data");
            int indexValor = cursor.getColumnIndex("valor");
            int indexCategoria = cursor.getColumnIndex("categoria");
            Long id = cursor.getLong(indexId);
            long tipo = cursor.getLong(indexTipo);
            String data = cursor.getString(indexData);
            String valor = cursor.getString(indexValor);
            String cat = cursor.getString(indexCategoria);
            operacao.setId(id);
            TipoDAO tipoDAO = new TipoDAO(context);
            Tipo t = tipoDAO.getById(tipo);
            operacao.setTipo(t);
            if (cat.equalsIgnoreCase("Debito"))
                operacao.setCategoria(Categoria.DEBITO);
            else operacao.setCategoria(Categoria.CREDITO);
            Date dat = null;
            try {
                dat = new SimpleDateFormat("dd/MM/yyyy").parse(data);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            operacao.setData(dat);
            operacao.setValor(valor);


            if(dat.compareTo(d1) > 0 || dat.compareTo(d1) == 0 ){
                if(dat.compareTo(d2) < 0 || dat.compareTo(d2) == 0){
                    if(cat.equalsIgnoreCase(c)){
                        lista.add(operacao);
                    }
                }
            }
        }
        Collections.sort(lista);
        return lista;
    }

    public List<Operacao> getAllByCategoria() {
        List<Operacao> lista = new ArrayList<>();
        try{
            Cursor cursor = read.query(DBHelper.TABLE2_NAME, new String[]{"id", "tipo", "data", "valor", "categoria"}, null, null, null, null, "categoria DESC");
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                Operacao operacao = new Operacao();
                int indexId = cursor.getColumnIndex("id");
                int indexTipo = cursor.getColumnIndex("tipo");
                int indexData = cursor.getColumnIndex("data");
                int indexValor = cursor.getColumnIndex("valor");
                int indexCategoria = cursor.getColumnIndex("categoria");
                Long id = cursor.getLong(indexId);
                long tipo = cursor.getLong(indexTipo);
                String data = cursor.getString(indexData);
                String valor = cursor.getString(indexValor);
                String categoria = cursor.getString(indexCategoria);
                operacao.setId(id);
                TipoDAO tipoDAO = new TipoDAO(context);
                Tipo t = tipoDAO.getById(tipo);
                operacao.setTipo(t);
                Date dat = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                operacao.setData(dat);
                operacao.setValor(valor);
                if (categoria.equalsIgnoreCase("Debito"))
                    operacao.setCategoria(Categoria.DEBITO);
                else operacao.setCategoria(Categoria.CREDITO);
                lista.add(operacao);
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Collections.sort(lista);
        return lista;
    }

    public void deleteAllOperations(){
        try {
            String[] args = {String.valueOf(0)};
            write.delete(DBHelper.TABLE2_NAME, "id>?", args);
        } catch (Exception e) {
            Log.e("INFO", "Erro ao remover as categorias" + e.getMessage());
        }
    }
}