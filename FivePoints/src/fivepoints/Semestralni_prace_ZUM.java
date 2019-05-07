/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fivepoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author parkhal1
 */

public class Semestralni_prace_ZUM {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public static void printResult ( ArrayList<Coordinates> result, ArrayList<int[][]> symbols ) {
        for ( int[][] symbol : symbols ) {
            for ( Coordinates xy : result ) {
                System.out.print( symbol[xy.getX()][xy.getY()]) ;
            }
            if ( symbols.indexOf(symbol) != symbols.size() - 1 )
                System.out.print(", ") ;
            else 
                System.out.print("\n") ;
        }
    }
    
    public static void changeSymbols ( ArrayList<Coordinates> result, ArrayList<int[][]> symbols ) {
        for ( int[][] symbol : symbols ) {
            for ( Coordinates xy : result ) {
                symbol[xy.getX()][xy.getY()] = 8 ;
            }
        }
    }
    
    public static void printSymbols ( ArrayList<int[][]> symbols ) {
        
            for (int xPixel = 0 ; xPixel < 16 ; xPixel++ ) {
                for ( int[][] symbol: symbols ) {
                for (int yPixel = 0 ; yPixel < 16 ; yPixel++ ) {
                    if ( symbol[xPixel][yPixel] == 1 ) {
                        System.out.print("■") ;
                    } else if ( symbol[xPixel][yPixel] == 0 ) {
                        System.out.print( ANSI_WHITE + "■" + ANSI_RESET ) ;
                    } else 
                        System.out.print( ANSI_RED + "■" + ANSI_RESET ) ;
                }
                System.out.print("  ") ;
                }
                System.out.print("\n") ;
              
        }
    }
    
    public static void main(String[] args) throws IOException {
        InputHandler inputHandler = new InputHandler("src/resources") ;
        ArrayList<int[][]> symbols = inputHandler.getSymbols() ;
        
        Comparator<Coordinates> comparator = new Comparator<Coordinates>() {
            @Override
            public int compare(Coordinates o1, Coordinates o2) {
                   return o1.getY().compareTo(o2.getY());
            }
        };
            
        Evolution evolution = new Evolution( 1000, 1000, 0.2, 0.5, symbols ) ; 
        evolution.start() ;
        ArrayList<Coordinates> result = evolution.getResult() ;
        
        result.sort( comparator ) ;

        System.out.println( result ) ;
        printResult(result, symbols) ;
        changeSymbols(result, symbols ) ;
        printSymbols(symbols) ;
    }  
}
