import java.util.ArrayList;

public class Teste1{

    public static void main(String[] args) throws Exception {
        ArrayList<Robot> robots = new ArrayList<>();
        robots.add(new Robot(new Ponto(100, 200)));
        ArrayList<FiguraGeometrica> figs = new ArrayList<>();
        figs.add(new Retangulo("Retangulo 0 100 100 100 0 0 100 0"));
        figs.add(new Triangulo("Triangulo 250 50 100 150 350 250"));
        figs.add(new Triangulo("Triangulo 300 0 500 0 500 200"));
        figs.add(new Triangulo("Triangulo 600 0 1000 0 800 200"));
        figs.add(new Triangulo("Triangulo 0 200 100 300 100 400"));
        figs.add(new Retangulo("Retangulo 200 200 300 300 200 600 100 500"));
        figs.add(new Circunferencia("Circunferencia 600 400 200"));
        figs.add(new Triangulo("Triangulo 800 200 1000 200 1000 500"));
        figs.add(new Triangulo("Triangulo 0 700 300 700 300 600"));
        figs.add(new Retangulo("Retangulo 300 500 400 500 400 600 300 600"));
        figs.add(new Circunferencia("Circunferencia 500 700 100"));
        figs.add(new Retangulo("Retangulo 700 600 900 600 800 1000 600 1000"));
        figs.add(new Retangulo("Retangulo 200 800 300 700 400 800 300 900"));
        figs.add(new Retangulo("Retangulo 400 900 500 900 500 1000 400 1000"));
        figs.add(new Triangulo("Triangulo 100 900 200 900 200 1000"));
        MapaInterface map = new Mapa(robots, figs);
        map.addPack(new Pacote(new Ponto(600, 100), new Ponto(900, 500)));
        map.addPack(new Pacote(new Ponto(900, 900), new Ponto(600, 45)));
//        map.addPack(new Pacote(new Ponto(800, 700), new Ponto(650, 897)));
        new MyFrame(map);

    }
}