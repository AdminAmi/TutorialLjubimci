/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Korisnik;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Klasa popunjava TableModel za komponentu JTable
 * @author Amel Džanić
 */
public class korisnikTableModel extends AbstractTableModel {
    private final List<korisnik> korisnici;
    private final String[] nazivKolona;

    /**
     * Konstruktor <code>korisnikTableModel</code> puni model sa potrebnim podacima
     * @param korisnici predstavlja listu korisnika koji će se prikazati u tabeli
     */
    public korisnikTableModel(List<korisnik> korisnici) {
        super();
        this.korisnici = korisnici;
        nazivKolona = new String[]{"ID","Ime i prezime","User","Password","Tip"};  
    }
    
    @Override
    public int getRowCount() {
        return korisnici.size();
    }

    @Override
    public int getColumnCount() {
        return nazivKolona.length;
    }
    @Override
    public String getColumnName(int col) {
        return nazivKolona[col] ;
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        korisnik k= korisnici.get(row);
        switch(col) {
          case 0: return k.getId();
          case 1: return k.getImeiprezime();
          case 2: return k.getUser();
          case 3: return k.getPass();
          case 4: return k.getTip();          
          default: return null;
        }
    }
     
     
    
}
