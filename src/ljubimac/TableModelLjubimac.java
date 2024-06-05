/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ljubimac;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author MojLap
 */
public class TableModelLjubimac extends AbstractTableModel{
    private List<ljubimac> ljubimci;
    private String[] nazivi={"ID","Ime","Vrsta","Datum udomljavanja","Starost","Status"};
    
    public TableModelLjubimac(List<ljubimac> ljubimci){
        super();
        this.ljubimci=ljubimci;
    }

    @Override
    public int getRowCount() {
        return ljubimci.size();
    }

    @Override
    public int getColumnCount() {
        return nazivi.length;
    }
    
    public String getColumnName(int col){
        return nazivi[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ljubimac lj = ljubimci.get(rowIndex);
        switch(columnIndex){
            case 0: return lj.getId();
            case 1: return lj.getIme();
            case 2: return lj.getVrsta();
            case 3: return lj.getDatum_udomljavanja();
            case 4: return lj.getStarost();
            case 5: return lj.getStatus();
            default: return null;
        }
    }
    
}
