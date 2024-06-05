/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ljubimac;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author home
 */
@Data @AllArgsConstructor
public class ljubimac {
    private int id;
    private String ime,vrsta,datum_udomljavanja,path,status;
    private float starost;
    private int idK;
    private String datum;
   

    public ljubimac() {
    }
    
    
}
