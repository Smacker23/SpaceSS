import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Grupo7
 * @version correto-17 Amazon correto version 17.0.4 23/02/2023
 * */
public class Cliente {
    private static MapaInterface map;
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        InputStreamReader in2 = new InputStreamReader(System.in);
        ArrayList<FiguraGeometrica> figs = new ArrayList<>();
        ArrayList<Robot> robots = new ArrayList<>();
        robots.add(new Robot(new Ponto(50, 50)));
        map = new Mapa(robots, figs);
        map.addPackages(in);
        map.updateMap(in, in2);
        Window window = new Window(map);
    }
}