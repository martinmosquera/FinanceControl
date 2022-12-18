package com.trabalho.financecontrol.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.trabalho.financecontrol.model.Operacao;
import com.trabalho.financecontrol.model.Tipo;

import java.sql.Date;
import java.util.ArrayList;
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
        Cursor cursor = read.query(DBHelper.TABLE2_NAME,new String[]{"id","tipo","data","valor"},null,null,null,null,null);
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
            operacao.setData(Date.valueOf(data));
            operacao.setValor(valor);
            lista.add(operacao);
        }
        return lista;
    }

    public Operacao getById(long i){
        String[] args = {String.valueOf(i)};
        Cursor cursor = read.query(DBHelper.TABLE2_NAME,new String[]{"id","tipo","data","valor"},"id=?",args,null,null,null);
        Operacao operacao = new Operacao();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            int indexTipo = cursor.getColumnIndex("tipo");
            int indexData = cursor.getColumnIndex("data");
            int indexValor = cursor.getColumnIndex("valor");
            long tipo = cursor.getLong(indexTipo);
            String data = cursor.getString(indexData);
            String valor = cursor.getString(indexValor);
            operacao.setId(i);
            TipoDAO tipoDAO = new TipoDAO(context);
            Tipo t = tipoDAO.getById(tipo);
            operacao.setTipo(t);
            operacao.setData(Date.valueOf(data));
            operacao.setValor(valor);
        }
        return operacao;
    }
}