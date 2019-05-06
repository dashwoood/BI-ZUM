/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralni_prace_zum;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author parkhal1
 */

public class Semestralni_prace_ZUM {

    public static void main(String[] args) throws IOException {
        InputHandler inputHandler = new InputHandler("src/main/resources") ;
        System.out.println( inputHandler.getSize() ) ;
        
        Evolution evolution = new Evolution( 100, 100, 0.2, 0.3 ) ; 
        evolution.start() ;
        ArrayList<Coordinates> result = evolution.getResult() ;
        
        System.out.println( result ) ;
    }  
}
