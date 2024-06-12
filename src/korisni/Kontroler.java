
package korisni;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import lombok.Getter;


/**
 * Materijali za predmet programski jezik JAVA * 
 * @author Amel Dzanic
 */
@Getter
public abstract class Kontroler {
    protected static String     dbPath ;
    protected Statement         stmt;   
    protected PreparedStatement ps;
    protected Connection        kon;
    public Kontroler() {       
        dbPath = "jdbc:sqlite:" + PathFromApp()+"\\db\\tutorial.db";         
    }
    /**
     * Metoda koja služi za povezivanje sa bazom podataka. Ako se ne poveže
     * izbacuje izuzetak. Kao argument za povezivanje koristi se varijabla 
     * <code>dbPath</code>
     * @return objecat Connection
     * @throws SQLException ukoliko nije uspješno povezivanje sa bazom podataka
     */
    protected Connection getKon() throws SQLException {        
        kon=DriverManager.getConnection(dbPath);
        //System.out.println("Konekcija otvorena" );
        return kon;        
    }  
   
    /**
     * Metoda <b><code>InsDelUpd</code></b> obavlja sve operacije sa
     * bazom što se tiče - isključivo sa alfanumeričkim podacima:
     * insertovanja sloga,
     * brisanja sloga,
     * te ažuriranja sloga u bazi podataka.
     * <p><b>Bitan je samo tacan sql upit!!!</b>
     * Treba još napomenuti da ova metoda sama sebi otvara i zatvara
     * konekciju sa bazom,tako da je programer oslobođen toga.
     * <p>Evo primjer:<br>
     * Neka imamo bazu sa samo dva polja jedno int id, a drugo varchar(45)
     * koje mi sluzi za unos imena. Tada bi za insert bio sljedeci kod:<br>
     * <code><br>
     * utilDB db = new utilDB();<br>
     * db.InsDelUpd("insert into test values(2,'Neko ime')");</code>
     * @param sql upit z Bazu
     * @throws SQLException
     */
    public void InsDelUpd (String sql) throws SQLException{
        try{ 
            kon=DriverManager.getConnection(dbPath);
            kon.setAutoCommit(false);
            stmt= kon.createStatement();           
            stmt.execute(sql);
            try{
                stmt.close();             
            }
            finally{stmt=null;}
            kon.commit();
        }
        catch (SQLException ex){
            kon.rollback();           
            throw ex;
        }
        finally {
            if(stmt!=null){
                try{stmt.close();}
                catch (SQLException ex){} //do nothing                
            }
            //System.out.println("U konekciji sam terminiranja!");           
            kon.close();
        }
    }
    
        
    /**
     *
     * @throws SQLException
     */
    public void zatvoriKonekciju() throws SQLException{
        kon.close();
       // System.out.println("zatvaram konekciju sa DB!");
    }
    
    private String Path(){
         try (InputStream input = new FileInputStream("konekcija.properties")) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            // get the property value           
            return prop.getProperty("Path");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static String PathFromApp(){
        String path= null;
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return path;
    }
    

    
}