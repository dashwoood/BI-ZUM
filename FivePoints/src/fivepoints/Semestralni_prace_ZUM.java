/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fivepoints;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author parkhal1
 */

public class Semestralni_prace_ZUM {

    public static void printResult ( ArrayList<Coordinates> result, ArrayList<int[][]> symbols ) {
        for ( int[][] symbol : symbols ) {
            for ( Coordinates xy : result ) {
                System.out.print( symbol[xy.getX()][xy.getY()]) ;
            }
            if ( symbols.indexOf(symbol) != symbols.size() - 1 )
                System.out.print(",") ;
            else 
                System.out.print("\n") ;
        }
    }
    
    public static void main(String[] args) throws IOException {
        InputHandler inputHandler = new InputHandler("src/resources") ;
        ArrayList<int[][]> symbols = inputHandler.getSymbols() ;
        System.out.println( inputHandler.getSize() ) ;
        
        Evolution evolution = new Evolution( 1000, 100, 0.2, 0.3, symbols ) ; 
        evolution.start() ;
        ArrayList<Coordinates> result = evolution.getResult() ;
        
        System.out.println( result ) ;
        printResult( result, symbols ) ;
    }  
}
