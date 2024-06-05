/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisni;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Materijali za predmet programski jezik JAVA *
 * @author Amel Džanić
 */
public class Util {
     public static String trenutniDatum(){
        String datum;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();  
        datum = dtf.format(now);
        return datum;
    }
      public static String datum (Date date){
        Format formater= new SimpleDateFormat("yyyy-MM-dd");
        return formater.format(date);
    }
    public static Date procitajDatum(String datum) throws ParseException{
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        df.setLenient(false);
        Date d = df.parse(datum);
        return d;
    }
    
}
