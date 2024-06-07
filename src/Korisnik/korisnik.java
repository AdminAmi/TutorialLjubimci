package Korisnik;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Materijali za predmet programski jezik JAVA
 * Klasa koja mapira tabelu korisnik
 * @author Amel Džanić
 */
@Getter @Setter @ToString
public class korisnik {
    private int id;
    private String      imeiprezime,
                        user,
                        pass,
                        tip;

   
    public korisnik() {    }

    /**
     *
     * @param id
     * @param imeiprezime
     * @param user
     * @param pass
     * @param tip
     */
    public korisnik(int id, String imeiprezime, String user, String pass, String tip) {
        this.id = id;
        this.imeiprezime = imeiprezime;
        this.user = user;
        this.pass = pass;
        this.tip = tip;
    } 
}
