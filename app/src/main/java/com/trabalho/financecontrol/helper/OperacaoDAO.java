package com.trabalho.financecontrol.helper;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import com.trabalho.financecontrol.activity.MainActivity;
import com.trabalho.financecontrol.model.Categoria;
import com.trabalho.financecontrol.model.Operacao;
import com.trabalho.financecontrol.model.Tipo;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperacaoDAO {

    private SQLiteDatabase write;
    private SQLiteDatabase read;
    private Context context;

    public OperacaoDAO(Context con){
        context = con;
        DBHelper db = new DBHelper(con);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    public long insertOperacao(Operacao operacao){
        ContentValues values = new ContentValues();
        values.put("tipo",operacao.getTipo().getId());
        values.put("valor",operacao.getValor());
        SimpleDateFormat sdf1= new SimpleDateFormat("dd/MM/yyyy");
        values.put("data",sdf1.format(operacao.getData()));
        values.put("categoria",operacao.getCategoria().getNome());
        long id =-1;
        try{
            id = write.insert(DBHelper.TABLE2_NAME,null,values);
            Log.i("INFO","Operacao salva com sucesso!");
        }catch(Exception e){
            Log.e("INFO","Erro ao salvar o nova operacao"+e.getMessage());
        }
        return id;
    }
    public boolean updateOperacao(Operacao operacao){
        ContentValues values = new ContentValues();
        values.put("tipo",operacao.getTipo().getId());
        values.put("valor",operacao.getValor());
        values.put("categoria",operacao.getCategoria().getNome());
        try{
            String[] args = {String.valueOf(operacao.getId())};
            write.update(DBHelper.TABLE2_NAME,values,"id=?",args);
            Log.i("INFO","Operacao Atualizada com sucesso!");
        }catch(Exception e){
            Log.e("INFO","Erro ao atualizar a operacao"+e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteOperacao(Operacao operacao){
        try{
            String[] args = {String.valueOf(operacao.getId())};
            write.delete(DBHelper.TABLE2_NAME,"id=?",args);
            Log.i("INFO","Operacao removida com sucesso!");
        }catch(Exception e){
            Log.e("INFO","Erro ao remover a operacao"+e.getMessage());
            return false;
        }
        return true;
    }

    public List<Operacao> getAllOperacoes(){
        List<Operacao> lista = new ArrayList<>();
        Cursor cursor = read.query(DBHelper.TABLE2_NAME,new String[]{"id","tipo","data","valor","categoria"},null,null,null,null,null);
        while(cursor.moveToNext()){
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
            Tipo t = tipoDAO.getById(id);
            operacao.setTipo(t);
            operacao.setData(new Date(data));
            operacao.setValor(valor);
            if(categoria.equalsIgnoreCase("Debito"))
            operacao.setCategoria(Categoria.DEBITO);
            else operacao.setCategoria(Categoria.CREDITO);
            lista.add(operacao);
        }
        return lista;
    }

    public Operacao getById(long i){
        String[] args = {String.valueOf(i)};
        Cursor cursor = read.query(DBHelper.TABLE2_NAME,new String[]{"id","tipo","data","valor","categoria"},"id=?",args,null,null,null);
        Operacao operacao = new Operacao();
        if(cursor.getCount() > 0){
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
            operacao.setData(new Date(data));
            operacao.setValor(valor);
            if(categoria.equalsIgnoreCase("Debito"))
                operacao.setCategoria(Categoria.DEBITO);
            else operacao.setCategoria(Categoria.CREDITO);
        }
        return operacao;
    }

    public List<Operacao> getByData(java.util.Date d1, java.util.Date d2){
        List<Operacao> lista = new ArrayList<>();
        SimpleDateFormat sdf1= new SimpleDateFormat("dd/MM/yyyy");
        String d1String = sdf1.format(d1);
        String d2String = sdf1.format(d2);
        String[] args = {d1String, d2String};
        Cursor cursor = read.query(DBHelper.TABLE2_NAME,new String[]{"id","tipo","data","valor","categoria"},"(data BETWEEN ? AND ?)",args,null,null,"data");
        while(cursor.moveToNext()){
            Operacao operacao = new Operacao();
            int indexId = cursor.getColumnIndex("id");
            int indexTipo = cursor.getColumnIndex("tipo");
            int indexData = cursor.getColumnIndex("data");
            int indexValor = cursor.getColumnIndex("valor");
            Long id = cursor.getLong(indexId);
            long tipo = cursor.getLong(indexTipo);
            String data = cursor.getString(indexData);
            String valor = cursor.getString(indexValor);
            operacao.setId(id);
            TipoDAO tipoDAO = new TipoDAO(context);
            Tipo t = tipoDAO.getById(id);
            operacao.setTipo(t);
           operacao.setData(new Date(data));
            operacao.setValor(valor);
            lista.add(operacao);
        }
        return lista;
    }

    public List<Operacao> getByDataCategoria(java.util.Date d1, java.util.Date d2,String c){
        List<Operacao> lista = new ArrayList<>();
        SimpleDateFormat sdf1= new SimpleDateFormat("dd/MM/yyyy");
        String d1String = sdf1.format(d1);
        String d2String = sdf1.format(d2);
        String[] args = {d1String, d2String,c};
        Cursor cursor = read.query(DBHelper.TABLE2_NAME,new String[]{"id","tipo","data","valor","categoria"},"(data BETWEEN ? AND ?) AND categoria=?",args,null,null,"data");
        while(cursor.moveToNext()){
            Operacao operacao = new Operacao();
            int indexId = cursor.getColumnIndex("id");
            int indexTipo = cursor.getColumnIndex("tipo");
            int indexData = cursor.getColumnIndex("data");
            int indexValor = cursor.getColumnIndex("valor");
            Long id = cursor.getLong(indexId);
            long tipo = cursor.getLong(indexTipo);
            String data = cursor.getString(indexData);
            String valor = cursor.getString(indexValor);
            operacao.setId(id);
            TipoDAO tipoDAO = new TipoDAO(context);
            Tipo t = tipoDAO.getById(id);
            operacao.setTipo(t);
            operacao.setData(new Date(data));
            operacao.setValor(valor);
            lista.add(operacao);
        }
        return lista;
    }
}