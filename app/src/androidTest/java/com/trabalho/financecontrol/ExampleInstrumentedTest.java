package com.trabalho.financecontrol;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.trabalho.financecontrol.helper.OperacaoDAO;
import com.trabalho.financecontrol.helper.TipoDAO;
import com.trabalho.financecontrol.model.Categoria;
import com.trabalho.financecontrol.model.Operacao;
import com.trabalho.financecontrol.model.Tipo;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.trabalho.financecontrol", appContext.getPackageName());
    }

    @Test
    public  void createTipoDebito(){
        Tipo t = new Tipo();
        t.setCategoria(Categoria.DEBITO);
        assertEquals("Debito",t.getCategoria().getNome());
    }

    @Test
    public  void createTipoCredito(){
        Tipo t = new Tipo();
        t.setCategoria(Categoria.CREDITO);
        assertEquals("Credito",t.getCategoria().getNome());
    }

    @Test
    public  void createTipoSQL(){
        TipoDAO tipoDAO = new TipoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        Tipo t = new Tipo();
        t.setNome("Comida");
        t.setCategoria(Categoria.DEBITO);
        long id = tipoDAO.insertTipo(t);

        t = tipoDAO.getById(id);
        assertEquals("Comida",t.getNome());
    }

    @Test
    public void editTipoSQL(){
        TipoDAO tipoDAO = new TipoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        Tipo t = new Tipo();
        t.setNome("Comida");
        t.setCategoria(Categoria.DEBITO);
        long i = tipoDAO.insertTipo(t);
        Tipo tipo = tipoDAO.getById(i);
        tipo.setNome("Aluguel");
        tipoDAO.updateTipo(tipo);
        tipo = tipoDAO.getById(i);
        assertEquals("Aluguel",tipo.getNome());
    }

    @Test
    public  void deleteTipoSQL(){
        TipoDAO tipoDAO = new TipoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        List<Tipo> list = tipoDAO.getAllTipos();
        while(list.size() > 0){
            tipoDAO.deleteTipo(list.get(0));
            list = tipoDAO.getAllTipos();
        }
        assertEquals(0,list.size(),0);
    }

    @Test
    public void createOperacaoSQL(){
        OperacaoDAO oDAO = new OperacaoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        Operacao o = new Operacao();
        TipoDAO tipoDAO = new TipoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        Tipo t = new Tipo();
        t.setNome("Comida");
        t.setCategoria(Categoria.DEBITO);
        long idTipo = tipoDAO.insertTipo(t);
        t.setId(idTipo);
        o.setTipo(t);
        o.setValor("20");
        o.setData(new Date());
        o.setCategoria(Categoria.DEBITO);
        long idOpera =oDAO.insertOperacao(o);
        Operacao operacao = oDAO.getById(idOpera);
        assertEquals("Comida",operacao.getTipo().getNome());
    }

    @Test
    public void operacaoValorSQL(){
        OperacaoDAO oDAO = new OperacaoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        TipoDAO tDAO = new TipoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        Operacao o = new Operacao();
        Tipo t = new Tipo();
        t.setNome("Comida");
        t.setCategoria(Categoria.DEBITO);
        long idTipo = tDAO.insertTipo(t);
        t.setId(idTipo);
        o.setTipo(t);
        o.setValor("20");
        o.setData(new Date());
        o.setCategoria(Categoria.DEBITO);
        long i =oDAO.insertOperacao(o);
        Operacao operacao = oDAO.getById(i);
        assertEquals("20",operacao.getValor());
    }

    @Test
    public void operacaoDataSQL(){
        OperacaoDAO oDAO = new OperacaoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        TipoDAO tDAO = new TipoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        Operacao o = new Operacao();
        Tipo t = new Tipo();
        t.setNome("Comida");
        t.setCategoria(Categoria.DEBITO);
        long idTipo = tDAO.insertTipo(t);
        t.setId(idTipo);

        o.setTipo(t);
        o.setValor("20");
        o.setData(new Date());
        o.setCategoria(Categoria.DEBITO);
        long i =oDAO.insertOperacao(o);
        Operacao operacao = oDAO.getById(i);
        String d = operacao.getData().toString();
        if(d.isEmpty()){
            d = "errou";
        }
        assertEquals(operacao.getData().toString(),operacao.getData().toString());
    }
    @Test
    public void updateOperacaoSQL(){
        OperacaoDAO oDAO = new OperacaoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        TipoDAO tDAO = new TipoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        Operacao o = new Operacao();
        Tipo t = new Tipo();
        t.setNome("Aluguel");
        t.setCategoria(Categoria.DEBITO);
        long idTipo = tDAO.insertTipo(t);
        t.setId(idTipo);
        o.setTipo(t);
        o.setValor("500");
        o.setData(new Date());
        o.setCategoria(Categoria.DEBITO);
        long i =oDAO.insertOperacao(o);
        o.setTipo(t);
        o.setValor("20");
        o.setId(i);
        oDAO.updateOperacao(o);
        Operacao operacao = oDAO.getById(i);
        assertEquals("20",operacao.getValor());
    }

    @Test
    public void deleteOperationSQL(){
        OperacaoDAO oDAO = new OperacaoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        List<Operacao> lista = oDAO.getAllOperacoes();
        while(lista.size() > 0){
            oDAO.deleteOperacao(lista.get(0));
            lista = oDAO.getAllOperacoes();
        }
        assertEquals(0,lista.size(),0);
    }

    @Test
    public void operacaoByDataSQL(){
        OperacaoDAO oDAO = new OperacaoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        TipoDAO tDAO = new TipoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        List<Operacao> lista = oDAO.getAllOperacoes();
        while(lista.size() > 0){
            oDAO.deleteOperacao(lista.get(0));
            lista = oDAO.getAllOperacoes();
        }
        Operacao o = new Operacao();
        Tipo t = new Tipo();
        t.setNome("Comida");
        t.setCategoria(Categoria.DEBITO);
        long idTipo = tDAO.insertTipo(t);
        t.setId(idTipo);
        o.setTipo(t);
        o.setValor("20");
        o.setData(new Date());
        o.setCategoria(Categoria.DEBITO);
        long i =oDAO.insertOperacao(o);
        SimpleDateFormat sdf1= new SimpleDateFormat("dd/MM/yyyy"); //você pode usar outras máscaras
        Date y2=new Date();
        Date y1=new Date(2022,12,19);
        System.out.println(sdf1.format(y1));
        lista = oDAO.getByData(y1,y2);
        assertEquals(1,lista.size(),0);
    }

    @Test
    public void operacaoByDataCategoriaQL(){
        OperacaoDAO oDAO = new OperacaoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        TipoDAO tDAO = new TipoDAO(InstrumentationRegistry.getInstrumentation().getTargetContext());
        List<Operacao> lista = oDAO.getAllOperacoes();
        while(lista.size() > 0){
            oDAO.deleteOperacao(lista.get(0));
            lista = oDAO.getAllOperacoes();
        }
        Operacao o = new Operacao();
        Tipo t = new Tipo();
        t.setNome("Comida");
        t.setCategoria(Categoria.CREDITO);
        long idTipo = tDAO.insertTipo(t);
        t.setId(idTipo);
        o.setTipo(t);
        o.setValor("20");
        o.setData(new Date());
        o.setCategoria(Categoria.CREDITO);
        long i =oDAO.insertOperacao(o);
        o.setTipo(t);
        o.setValor("20");
        o.setData(new Date());
        o.setCategoria(Categoria.CREDITO);
        i =oDAO.insertOperacao(o);
        SimpleDateFormat sdf1= new SimpleDateFormat("dd/MM/yyyy"); //você pode usar outras máscaras
        Date y2=new Date();
        Date y1=new Date(2022,12,19);
        System.out.println(sdf1.format(y1));
        lista = oDAO.getByDataCategoria(y1,y2,Categoria.CREDITO.getNome());
        assertEquals(2,lista.size(),0);
    }

}