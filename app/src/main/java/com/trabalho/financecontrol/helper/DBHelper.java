package com.trabalho.financecontrol.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static int DB_VERSION = 1;
    public static String DB_NAME = "FINANCE.DB";
    public static String TABLE1_NAME = "tipo";
    public static String TABLE2_NAME = "operacao";

    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_SQL_TABLE1 = "CREATE TABLE IF NOT EXISTS "+TABLE1_NAME+"(id integer primary key autoincrement, name text not null,categoria text not null);";
        String CREATE_SQL_TABLE2 = "CREATE TABLE IF NOT EXISTS "+TABLE2_NAME+"(id integer primary key autoincrement, tipo integer not null,data date default CURRENT_TIME, valor text not null);";
        String CREATE_SQL_INSERT1 = " INSERT INTO "+TABLE1_NAME+"(name, categoria) VALUES ('Moradia','Debito'),('Saúde','Debito'),('Lazer','Debito'),('Educação','Debito'),('Doação','Debito'),('Alimentação','Debito'),('Salário','Credito'),('Benefícios','Credito'),('Aposta','Credito');";
        try{
            sqLiteDatabase.execSQL(CREATE_SQL_TABLE1);
            Log.i("INFO_DB","Tabela tipo Criada com sucesso");
            sqLiteDatabase.execSQL(CREATE_SQL_TABLE2);
            Log.i("INFO_DB","Tabela operacao Criada com sucesso");
            sqLiteDatabase.execSQL(CREATE_SQL_INSERT1);
            Log.i("INFO_DB","Inserção na tabela tipo feita com sucesso");
        }catch (Exception e){
            Log.i("INFO_DB","Erro ao criar as tabelas "+e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql1 = "DROP TABLE IF EXISTS "+ TABLE1_NAME+";";
        String sql2 = "DROP TABLE IF EXISTS "+ TABLE2_NAME+";";
        try{
            sqLiteDatabase.execSQL(sql1);
            sqLiteDatabase.execSQL(sql2);
            onCreate(sqLiteDatabase);
            Log.i("INFO_DB","Tabelas tipo e operacao Atualizadas");
        }catch(Exception e){
            Log.i("INFO_DB","Erro ao atualizar a tabela "+e.getMessage());
        }
    }
}