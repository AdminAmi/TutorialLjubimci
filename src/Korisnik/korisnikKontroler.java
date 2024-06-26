
package Korisnik;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Materijali za predmet programski jezik JAVA
 * Klasa koja obavlja logiku sa bazom podataka
 * @author Amel Džanić
 */
@Getter   
@Setter
public class korisnikKontroler extends korisni.Kontroler{
    private final korisnik Korisnik = new korisnik();

    /**
     * tip korisnika predstavlja vrstu korisnika i može biti
     * ADMINISTRATOR ili KORISNIK
     */
    public static final String tipKorisnika[]={"ADMINISTRATOR","KORISNIK"};
    
    /**
     * Prazan konstruktor
     */
    public korisnikKontroler() {    }
    /**
     * Metoda koja vrši izmjenu korisnicke sifre
     * @param temp objekat korisnika koji mjenja sifru
     * @param New nova sifra
     * @param Rep ponovni unos nove sifre
     * @throws SQLException ukoliko nije bilo uspjesno povezivanje sa bazom podataka
     */
    public void promjenaPassworda(korisnik temp, String New, String Rep) throws SQLException {
        String sql = "UPDATE korisnik SET pass ='"+New+"' WHERE id =" + temp.getId();
        InsDelUpd(sql);        
    }

    /**
     * Metoda <code>azurirajKorisnika</code> vrši azuiriranje podataka korisnka
     * i to ime i prezime korisnika
     * @param temp objekat korisnika nad kojim se vrsi azuriranje
     * @throws SQLException ukoliko nije bilo uspjesno povezivanje sa bazom podataka
     */
    public void azurirajKorisnika(korisnik temp) throws SQLException {
        String sql = "UPDATE korisnik SET imeiprezime = '"+ temp.getImeiprezime()+
                "', tip = '"+temp.getTip()+"' WHERE id = " + temp.getId();
        System.out.println(sql);
        InsDelUpd(sql);
    }

    /**
     *Metoda unesiKorisnika unosi sve podatke o novom korisniku sistema
     * @param temp predstavlja objekat korisnik koji se unosi u bazu
     * @throws SQLException ukoliko nije bilo uspjesno povezivanje sa bazom podataka
     */
    public void UnesiKorisnika(korisnik temp) throws SQLException {
        String sql = "INSERT INTO korisnik (imeiprezime,user,pass,tip) VALUES ('" + temp.getImeiprezime()+"','"
                +temp.getUser()+"','"+temp.getPass()+"','"+temp.getTip()+"')";
        InsDelUpd(sql);
       // this.Korisnik=temp;
    }

    /**
     * Metoda vraca objekat korisnika koji ima trazeni ID
     * @param ID primarni kljuc objekta korisnik
     * @return objekat korisnik koji ima taj ID, ukoliko ga ne nadje vraca null
     * @throws SQLException ukoliko nije bilo uspjesno povezivanje sa bazom podataka
     */
    public korisnik VratiKorisnikaPoID(int ID) throws SQLException {
        String sql = "SELECT * FROM korisnik WHERE id='" + Integer.toString(ID) + "'";
        korisnik kor = new korisnik();         
        Statement st = getKon().createStatement();        
        ResultSet rs1 = st.executeQuery(sql);
        while (rs1.next()) 
        {
            kor.setId(rs1.getInt("id"));
            kor.setImeiprezime(rs1.getString("imeiprezime"));
            kor.setUser(rs1.getString("user"));
            kor.setPass(rs1.getString("pass"));
            kor.setTip(rs1.getString("tip"));
        }
        zatvoriKonekciju();
        return kor;
    } 
    
     /**
     * Vraca listu korisnika koji zadovoljavaju uvijet pretrazivanja
     * @param uvjet jeste string prema kojem se vrsi pretrazivanje
     * @param opcija ukoliko je 0 pretrazivanje je po imenu, a ukoliko je 1 pretrazivanje je po prezimenu
     * @return lista korisnika koji zadovoljavaju uvijet pretrage
     * @throws SQLException ukoliko nije bilo uspjesno (greška) povezivanje sa bazom podataka
     */
    public ArrayList<korisnik> vratiKojiZadovoljavajuUvjet(String uvjet, int opcija)
            throws SQLException {
        if(uvjet.length()<1) throw new SQLException("Ime nema ni jedan karakter!!!");
        ArrayList<korisnik> rezultat = new ArrayList();        
        String sql =  "SELECT * FROM korisnik WHERE imeiprezime LIKE '" +
                uvjet + "%'" ;       
        Statement st = getKon().createStatement();
        ResultSet rs1 = st.executeQuery(sql);
        while (rs1.next()) 
        {
            korisnik kor = new korisnik();
            kor.setId(rs1.getInt("id"));
            kor.setImeiprezime(rs1.getString("imeiprezime"));
            kor.setUser(rs1.getString("user"));
            kor.setPass(rs1.getString("pass"));
            kor.setTip(rs1.getString("tip"));
            rezultat.add(kor);
        }        
        zatvoriKonekciju();
        return rezultat;
    }

    /**
     * metoda login je zaduzena za logovanje na sistem ukoliko su uneseni parametri tacni
     * pored toga ucitava iz baze sve podatke o zadanom korisniku
     * @param log predstavlja korisnicko ime
     * @param pass predstavlja korisnicku sifru
     * @return true ukoliko su podaci ispravni
     * @throws SQLException ukoliko nije bilo uspjesno povezivanje sa bazom podataka
     */
    public boolean login(String log, String pass) throws SQLException {
        boolean zastavica = false;
        String sql = "SELECT * FROM korisnik WHERE user = ? AND pass = ? ";
        PreparedStatement st = getKon().prepareStatement(sql);
        st.setString(1, log);
        st.setString(2, pass);
        ResultSet rs1 = st.executeQuery();
        while (rs1.next()) 
        {
            Korisnik.setId(rs1.getInt("id"));
            Korisnik.setImeiprezime(rs1.getString("imeiprezime"));
            Korisnik.setUser(rs1.getString("user"));
            Korisnik.setPass(rs1.getString("pass"));
            Korisnik.setTip(rs1.getString("tip"));
            zastavica = true;
        }
        zatvoriKonekciju();
        return zastavica;
    }
    
    /**
     * Metoda služi za konverziju niza String u Listu
     * @return Listu tipova korisnika
     */
    public List vratiTipoveKorisnikaLista(){
        return Arrays.asList(tipKorisnika);
    }
    
    
}
