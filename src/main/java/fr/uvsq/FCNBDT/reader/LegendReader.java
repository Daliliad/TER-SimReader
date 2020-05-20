package fr.uvsq.FCNBDT.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LegendReader {

    public static Map<Integer, String> readLegend(String path) {
        HashMap<Integer, String> legend = new HashMap<>();
        File f = new File(path);
        if(f.exists()) {
            try (Scanner s = new Scanner(f,"UTF-8")) {
                String str;
                String decoupe[];
                int i;
                while (s.hasNext()) {
                    str = s.nextLine();
                    decoupe = str.split(" ", 2);
                    try {
                        i = Integer.parseInt(decoupe[0]);
                        legend.put(i, decoupe[1].trim());
                    } catch (NumberFormatException e) {}
                }
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
        return legend;
    }
}
