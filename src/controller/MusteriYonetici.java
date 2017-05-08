/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author User
 */
public class MusteriYonetici {
    private List musterilerList;
    private final JTable musteriTablo;
    private final static String SORGU_KALIP = "from Customer c where c.";
    private Session session;
    
    public MusteriYonetici(JTable musteriTablo){
        this.musteriTablo = musteriTablo;
    }
    public void musteriGetir(String aranan, String filtre){
        String sorguMetin = "";
        if(filtre.equalsIgnoreCase("ad"))
            sorguMetin = SORGU_KALIP+"name like '"+aranan+"%'";
        else if(filtre.equalsIgnoreCase("sehir"))
            sorguMetin = SORGU_KALIP +"city like '"+aranan+"%'";
        try{
            session.beginTransaction();
            Query sorgu = session.createQuery(sorguMetin);
            musterilerList = sorgu.list();
            session.getTransaction().commit();
            musteriGoster();
        }catch (HibernateException he){
            he.printStackTrace();
        }
    }
    public void ac(){
        session = HibernateUtil.getSessionFactory().openSession();
    }
    public void kapat(){
        session.close();
    }
    private void musteriGoster(){
        Vector<String> sutunlar = new Vector<>();
        Vector musteriVector = new Vector();
        sutunlar.add("Musteri ID");
        sutunlar.add("Ad Soyad");
        sutunlar.add("Şehir");
        
        for(Object gelenMusteri : musterilerList){
            Customer musteri = (Customer)gelenMusteri;
            Vector<Object> satir =new Vector<>();
            satir.add(musteri.getCustomerId());
            satir.add(musteri.getName());
            satir.add(musteri.getCity());
            musteriVector.add(satir);
            
        }
        musteriTablo.setModel(new DefaultTableModel(musteriVector,sutunlar));
    }
    
}
