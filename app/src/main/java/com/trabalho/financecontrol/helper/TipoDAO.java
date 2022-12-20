package com.trabalho.financecontrol.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.trabalho.financecontrol.model.Categoria;
import com.trabalho.financecontrol.model.Tipo;

import java.util.ArrayList;
import java.util.List;

public class TipoDAO {

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public TipoDAO(Context context) {
        DBHelper db = new DBHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    public long insertTipo(Tipo tipo) {
        ContentValues values = new ContentValues();
        values.put("name", tipo.getNome());
        values.put("categoria", tipo.getCategoria().getNome());
        long id = -1;
        try {
            id = write.insert(DBHelper.TABLE1_NAME, null, values);
            Log.i("INFO", "Tipo salvo com sucesso!");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao salvar o novo tipo" + e.getMessage());
        }
        return id;
    }

    public boolean updateTipo(Tipo tipo) {
        ContentValues values = new ContentValues();
        values.put("name", tipo.getNome());
        values.put("categoria", tipo.getCategoria().getNome());

        try {
            String[] args = {String.valueOf(tipo.getId())};
            write.update(DBHelper.TABLE1_NAME, values, "id=?", args);
            Log.i("INFO", "Tipo Atualizado com sucesso!");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao atualizar o tipo" + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteTipo(Tipo tipo) {
        try {
            String[] args = {String.valueOf(tipo.getId())};
            write.delete(DBHelper.TABLE1_NAME, "id=?", args);
            Log.i("INFO", "Tipo removido com sucesso!");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao remover o tipo" + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteAllTipos() {
        try {
            Cursor cursor = read.query(DBHelper.TABLE1_NAME, new String[]{"id", "name", "categoria"}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Tipo tipo = new Tipo();
                int indexId = cursor.getColumnIndex("id");
                Long id = cursor.getLong(indexId);
                String[] args = {String.valueOf(id)};
                write.delete(DBHelper.TABLE1_NAME, "id=?", args);
            }
        } catch (Exception e) {
            Log.e("INFO", "Erro ao remover o tipo" + e.getMessage());
            return false;
        }
        return true;
    }

    public List<Tipo> getAllTipos() {
        List<Tipo> lista = new ArrayList<>();
        Cursor cursor = read.query(DBHelper.TABLE1_NAME, new String[]{"id", "name", "categoria"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Tipo tipo = new Tipo();
            int indexId = cursor.getColumnIndex("id");
            int indexName = cursor.getColumnIndex("name");
            int indexCategoria = cursor.getColumnIndex("categoria");
            Long id = cursor.getLong(indexId);
            String name = cursor.getString(indexName);
            String categoria = cursor.getString(indexCategoria);
            tipo.setId(id);
            tipo.setNome(name);
            Categoria c;
            if (categoria.equalsIgnoreCase("Debito")) c = Categoria.DEBITO;
            else c = Categoria.CREDITO;
            tipo.setCategoria(c);
            lista.add(tipo);
        }
        return lista;
    }

    public Tipo getById(long id) {
        Tipo tipo = new Tipo();
        String[] args = {String.valueOf(id)};
        Cursor cursor = read.query(DBHelper.TABLE1_NAME, new String[]{"id", "name", "categoria"}, "id=?", args, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int indexName = cursor.getColumnIndex("name");
            int indexCategoria = cursor.getColumnIndex("categoria");
            String name = cursor.getString(indexName);
            String categoria = cursor.getString(indexCategoria);
            tipo.setId(id);
            tipo.setNome(name);
            Categoria c;
            if (categoria.equalsIgnoreCase("Debito")) tipo.setCategoria(Categoria.DEBITO);
            else tipo.setCategoria(Categoria.CREDITO);
        }
        return tipo;
    }

    public Tipo getByName(String name) {
        Tipo tipo = new Tipo();
        try{
            String[] args = {name};
            Cursor cursor = read.query(DBHelper.TABLE1_NAME, new String[]{"id", "name","categoria"}, "name=?", args, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int indexId = cursor.getColumnIndex("id");
                int indexCategoria = cursor.getColumnIndex("categoria");
                long idLong = Long.getLong(cursor.getString(indexId));
                String categoria = cursor.getString(indexCategoria);
                tipo.setId(idLong);
                tipo.setNome(name);
                if (categoria.equalsIgnoreCase("Debito")) tipo.setCategoria(Categoria.DEBITO);
                else tipo.setCategoria(Categoria.CREDITO);
            }
        }catch(Exception e){
            Log.e("INFO_DB","Erro ao consultar o tipo pelo nome "+e.getMessage());
        }

        return tipo;
    }
}