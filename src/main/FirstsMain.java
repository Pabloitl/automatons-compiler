package main;

import java.util.Arrays;

import tables.Tables;
import utils.Firsts;
import utils.Firsts.Entry;

public class FirstsMain {
    public static void main (String[] args) throws Exception {
        Tables tables = new Tables("resources/grammar.txt");
        Firsts firsts = new Firsts(tables);

        System.out.println(firsts);
    }
}
