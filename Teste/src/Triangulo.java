

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Triangulo extends Poligono {

    /**
     * Este construtor vai receber uma String que recebe os valores necessários para construir uma Triangulo. Por fim verifica
     * se é um Triangulo
     * @param s
     */
    public Triangulo(String s) {
        String[] split = s.split(" ");
        this.points.add(new Ponto(Double.parseDouble(split[1]), Double.parseDouble(split[2])));
        this.points.add(new Ponto(Double.parseDouble(split[3]), Double.parseDouble(split[4])));
        this.points.add(new Ponto(Double.parseDouble(split[5]), Double.parseDouble(split[6])));
        err();
        this.segs.add(new SegmentoReta(points.get(0), points.get(1)));
        this.segs.add(new SegmentoReta(points.get(1), points.get(2)));
        this.segs.add(new SegmentoReta(points.get(2), points.get(0)));
    }

    /** Construtor que irá ler 3 Pontos e criará um Triangulo através deles. Caso os Pontos não formem um
     * Triangulo irá ser retornado o erro.
     * @param in        Scanner que irá ler os pontos
     */
    public Triangulo(Scanner in){
        for(int i = 0; i < 3; i++) {
            Ponto add = new Ponto(in);
            this.points.add(add);
        }
        err();
        this.segs.add(new SegmentoReta(points.get(0), points.get(1)));
        this.segs.add(new SegmentoReta(points.get(1), points.get(2)));
        this.segs.add(new SegmentoReta(points.get(2), points.get(0)));
    }

    /** Construtor que irá receber 3 Pontose criará um Triangulo através deles. Caso os Pontos não formem um
     * Triangulo irá ser retornado o erro.
     *
     * @param p1    Primeiro Ponto do traingulo
     * @param p2    Segundo Ponto do traingulo
     * @param p3    Terceiro Ponto do traingulo
     */
    public Triangulo(Ponto p1, Ponto p2, Ponto p3){
        this.points.add(p1);
        this.points.add(p2);
        this.points.add(p3);
        this.segs.add(new SegmentoReta(points.get(0), points.get(1)));
        this.segs.add(new SegmentoReta(points.get(1), points.get(2)));
        this.segs.add(new SegmentoReta(points.get(2), points.get(0)));
        err();
    }



    /** Este método verifica se uma triângulo é válido caso esta não seja dará print
     * de "Traingulo:vi" e o programa será encerrado
     */
    private void err(){
        Reta r1 = new Reta(this.points.get(0), this.points.get(1));
        Reta r2 = new Reta(this.points.get(1), this.points.get(2));
        Reta r3 = new Reta(this.points.get(2), this.points.get(0));
        boolean result = false;
        if(this.points.get(0) == this.points.get(1) || this.points.get(0) == this.points.get(2) || this.points.get(1) == this.points.get(2))
            result = true;
        else if(r1.getM() == r2.getM() && r1.getM() == r3.getM() && r1.getB() == r2.getB() && r1.getB() == r3.getB())
            result = true;
        else if(this.points.get(0).get_x() == this.points.get(1).get_x() && this.points.get(0).get_x() == this.points.get(2).get_x())
            result = true;
        if(result){
            System.out.println("Triangulo:vi");
            System.exit(0);
        }
    }

}
