/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralni_prace_zum;

/**
 *
 * @author parkhal1
 */
public class Coordinates {
    private Integer x ;
    private Integer y ; 

    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void swap () {
        Integer tmp = x ;
        this.x = this.y ;
        this.y = tmp ;
    }
    
    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
    
    
}
