
package ljubimac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import korisni.Util;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Amel Džanić
 * 30.01.2023
 */
@Getter @Setter
public class ljubimacKontroler extends korisni.Kontroler{
    public ljubimac Ljubimac = new ljubimac();
    public static final String[] vrstaLjubimca={"Pas","Mačka"};
    public static final String[] statusLjubimca={"Slobodan","Rezervisan","Udomljen"};
    
    /**
     *
     * @param temp
     * @throws SQLException
     */
    public void azurirajLjubimca(ljubimac temp) throws SQLException {
        String sql = "UPDATE ljubimac SET ime = '"+ temp.getIme()+
                "', vrsta = '"+temp.getVrsta()+
                "', datum_udomljavanja = '"+temp.getDatum_udomljavanja()+
                "', path = '"+temp.getPath()+"', status = '"+temp.getStatus()+
                "', starost= " + temp.getStarost() +
                ", datum ='"+temp.getDatum()+"'"+
                " WHERE id = " + temp.getId();
        InsDelUpd(sql);
    }
    
    /**
     *
     * @param temp
     * @throws SQLException
     */
    public void unesiLjubimca(ljubimac temp) throws SQLException{
         String sql = "INSERT INTO ljubimac (ime,vrsta,datum_udomljavanja,starost,path,status,datum) "
                 + "VALUES ('" + temp.getIme()+"','"+temp.getVrsta()+"','"+temp.getDatum_udomljavanja()+"',"+
                 temp.getStarost()+",'"+temp.getPath()+"','"+temp.getStatus()+"','"+temp.getDatum()+"')";
         System.out.println(sql);
        InsDelUpd(sql);
    }
    
    /**
     *
     * @param ID
     * @return
     * @throws SQLException
     */
    public ljubimac vratiLjubimcaPoID(int ID) throws SQLException{
        String sql = "SELECT * FROM ljubimac WHERE id='" + Integer.toString(ID) + "'";
        ljubimac kor = null;
        Statement st = getKon().createStatement();
        ResultSet rs1 = st.executeQuery(sql);
        while (rs1.next()) 
        {
            kor = new ljubimac(rs1.getInt(1), rs1.getString(2),
                    rs1.getString(3), rs1.getString(4), rs1.getString(5),rs1.getString(6),
                    rs1.getFloat(7),rs1.getInt(8), rs1.getString(9));           
        }
        zatvoriKonekciju();
        return kor;
    }
    
    /**
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public ArrayList vratiArrayListLjubimaca(String sql) throws SQLException{
        ArrayList<ljubimac> ljubimci = new ArrayList();
        ljubimac kor = null;
        Statement st = getKon().createStatement();
        ResultSet rs1= st.executeQuery(sql);        
        while(rs1.next()){
            kor = new ljubimac(rs1.getInt(1), rs1.getString(2),
                    rs1.getString(3), rs1.getString(4), rs1.getString(5),rs1.getString(6),
                    rs1.getFloat(7),rs1.getInt(8),rs1.getString(9));
            ljubimci.add(kor);
        }
        zatvoriKonekciju();
        return ljubimci;
    }
    
    public ArrayList pretraga(String args) throws SQLException{
        //if (args=="") return null;
        String sql = "SELECT * FROM ljubimac WHERE ime LIKE '" + args + "%'";
        return vratiArrayListLjubimaca(sql);        
    }
    
    /**
     *
     * @param opcija 
     * <br> (1) Vraća sve ljubimce koji su slobodni
     * <br> (2) Vraća sve ljubimce koji su rezervisani
     * <br> (3) Vraća sve ljubimce koji su udomljeni
     * <br> (4) Vraća sve ljubimce koji su slobodni i koji su vrste pas
     * <br> (5) Vraća sve ljubimce koji su slobodni i koji su vrste mačka
     * <br> (6) Vraća sve ljubimce 
     * <br> (7) Vraća sve ljubimce kojima je istekla rezervacija
     * @return ArrayList ljubimaca koji su definisani opcijom
     * @throws SQLException
     */
    public ArrayList vratiLjubimce1(int opcija) throws SQLException{
        String sql=null;
        //Ako je opcija 1 onda slobodne
        if (opcija==1)
            sql="SELECT * FROM ljubimac WHERE status = 'SLOBODAN'";
        //Ako je opcija 2 onda je rezervisan
        if (opcija==2)
            sql="SELECT * FROM ljubimac WHERE status = 'REZERVISAN'";
        //Ako je opcija 3 onda je udomljen
        if (opcija==3)
            sql="SELECT * FROM ljubimac WHERE status = 'UDOMLJEN'";
        //Ako je opcija 4 vrati PSE
        if (opcija==4)
            sql="SELECT * FROM ljubimac WHERE status = 'SLOBODAN' AND vrsta = 'PAS'";
        //Ako je opcija 5 vrati MACKE
        if (opcija==5)
            sql="SELECT * FROM ljubimac WHERE status = 'SLOBODAN' AND vrsta = 'MACKA'";
         if (opcija==6)
            sql="SELECT * FROM ljubimac ";
         if (opcija==7)
             sql = "SELECT * from ljubimac where julianday(date('now')) - julianday (ljubimac.datum) > 3 and ljubimac.status='REZERVISAN' "; 
        return vratiArrayListLjubimaca(sql);
    }    
    
    /**
     *
     * @param idK
     * @return
     * @throws SQLException
     */
    public ArrayList vratiRezervisaneOdKlijenta (long idK) throws SQLException{
        String sql="SELECT * FROM ljubimac WHERE status = 'REZERVISAN' and idK ="+ String.valueOf(idK); 
        return vratiArrayListLjubimaca(sql);        
    }

    /**
     *
     * @param pathSlike
     * @param id
     * @throws SQLException
     */
    public void unesiSliku(String pathSlike, long id) throws SQLException{
        String sql="UPDATE ljubimac SET path='"+pathSlike+"' WHERE ID=" + id;
        InsDelUpd(sql); 
    }

    /**
     *
     * @param idK
     * @param idLj
     * @throws SQLException
     */
    public void unesiRezervacija( long idK, long idLj) throws SQLException{
        String sql="UPDATE ljubimac SET status='REZERVISAN', idK="+idK+", datum='"+Util.trenutniDatum()+"'"+
                " WHERE ID=" + idLj;
        InsDelUpd(sql); 
    }

    /**
     *
     * @param idLj
     * @throws SQLException
     */
    public void ukloniRezervacija( long idLj) throws SQLException{
        String sql="UPDATE ljubimac SET status='SLOBODAN', idK=0 WHERE ID=" + idLj;
        InsDelUpd(sql); 
    }

    /**
     *
     * @param idLj
     * @param idKlijenta
     * @throws SQLException
     */
    public void unesiUdomljenje(  long idLj, int idKlijenta) throws SQLException{
        String sql="UPDATE ljubimac SET status='UDOMLJEN' , datum='"+Util.trenutniDatum()+
                "',idK="+idKlijenta+" WHERE ID=" + idLj;
        InsDelUpd(sql); 
    }

    /**
     *
     * @param idLj
     * @throws SQLException
     */
    public void ukloniUdomljenje(long idLj) throws SQLException{
        String sql="UPDATE ljubimac SET status='SLOBODAN', idk=0 WHERE ID=" + idLj;
        InsDelUpd(sql); 
    }

    /**
     *
     * @param ime
     * @return
     * @throws SQLException
     */
   
    public ArrayList pretragaSlobodnihPoImenu(String ime) throws SQLException{
        if (ime.length()<1) throw new SQLException("Ime nema nijedan karakter!!!");
        String sql = "SELECT * FROM ljubimac WHERE ime LIKE '"+ime+
                "%' COLLATE NOCASE AND status = 'SLOBODAN' COLLATE NOCASE";
        return vratiArrayListLjubimaca(sql);
    }
}
