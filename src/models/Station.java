package models;

import java.util.*;
import java.awt.*;

public class Station {
    private Player owner;
    private City city;
    
    Station(Player p, City city){
        owner = p;
        this.city = city;
    }
}
