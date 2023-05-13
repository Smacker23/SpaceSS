/**
 * @author Alexandre Carvalho
 * @version correto-17 Amazon correto version 17.0.4 23/02/2023
 * @inv (p1.dist(med) == p3.dist(med)) && (p2.dist(med) == p4.dist(med)) && (p1.dist(med) != 0 || p2.dist(med) != 0))
 * */
import java.util.ArrayList;
import java.util.Scanner;

public class Retangulo extends Poligono{
    /** Este construtor vai receber uma String que recebe os valores necessários para construir uma Retangulo. Por fim verifica
     * se é um Retangulo
     * @param s        String que contem os valores da circunferencia
     */
    public Retangulo(String s){
        String[] split = s.split(" ");
        this.points.add(new Ponto(Double.parseDouble(split[1]), Double.parseDouble(split[2])));
        this.points.add(new Ponto(Double.parseDouble(split[3]), Double.parseDouble(split[4])));
        this.points.add(new Ponto(Double.parseDouble(split[5]), Double.parseDouble(split[6])));
        this.points.add(new Ponto(Double.parseDouble(split[7]), Double.parseDouble(split[8])));
        err();
        this.segs.add(new SegmentoReta(points.get(0), points.get(1)));
        this.segs.add(new SegmentoReta(points.get(1), points.get(2)));
        this.segs.add(new SegmentoReta(points.get(2), points.get(3)));
        this.segs.add(new SegmentoReta(points.get(3), points.get(0)));
    }

    /** Este construtor vai dar scan a quatro pontos, que, vão se tornar 1 retangulo. Através desses pontos é também construído
     * os 4 segmentos de reta, que forma o retangulo. Por fim é verificado se os 4 Pontos formam um retangulo
     * @param in        Scanner(System.in) - Utilizado para ler os valores do terminal
     */
    public Retangulo(Scanner in){
        for(int i = 0; i < 4; i++){
            this.points.add(Ponto.scanner_to_point(in));
        }
        err();
        this.segs.add(new SegmentoReta(points.get(0), points.get(1)));
        this.segs.add(new SegmentoReta(points.get(1), points.get(2)));
        this.segs.add(new SegmentoReta(points.get(2), points.get(3)));
        this.segs.add(new SegmentoReta(points.get(3), points.get(0)));
    }

    /** Este construtor vai receber quatro pontos, que, vão se tornar 1 retangulo. Através desses pontos é também construído
     * os 4 segmentos de reta, que forma o retangulo. Por fim é verificado se os 4 Pontos formam um retangulo
     * @param p1        Primeiro Ponto do Retangulo
     * @param p2        Segundo Ponto do Retangulo
     * @param p3        Terceiro Ponto do Retangulo
     * @param p4        Quarto Ponto do Retangulo
     */
    public Retangulo(Ponto p1, Ponto p2, Ponto p3, Ponto p4){
        this.points.add(p1);
        this.points.add(p2);
        this.points.add(p3);
        this.points.add(p4);
        err();
        this.segs.add(new SegmentoReta(points.get(0), points.get(1)));
        this.segs.add(new SegmentoReta(points.get(1), points.get(2)));
        this.segs.add(new SegmentoReta(points.get(2), points.get(3)));
        this.segs.add(new SegmentoReta(points.get(3), points.get(0)));

    }
    /** Método que calcula o ponto central do retangulo e depois verifica se a distância de um ponto ao centro
     * é a mesma do que a do seu oposto ao centro(p1 == p3 && p2 == p4) e também que todas as distâncias, sejam
     * diferentes de 0.
     * @return true se os quatro pontos formarem um retangulo e False caso não formem
     */
    private boolean checks(){
        double x = (this.points.get(0).get_x() + this.points.get(1).get_x() + this.points.get(2).get_x() + this.points.get(3).get_x()) / 4;
        double y = (this.points.get(0).get_y() + this.points.get(1).get_y() + this.points.get(2).get_y() + this.points.get(3).get_y()) / 4;
        Ponto med = new Ponto(x,y);
        if((this.points.get(0).dist(med) == this.points.get(2).dist(med)) && (this.points.get(1).dist(med) == this.points.get(3).dist(med)) && (this.points.get(0).dist(med) != 0 || this.points.get(1).dist(med) != 0))
            return true;
        return false;
    }


    /** Método utilizado para returnar o primeiro Segmento do Retangulo.
     * @return       sr1 -  O primeiro SegmentoReta
     */
    public SegmentoReta getSegm1(){
        return segs.get(0);
    }
    /** Método utilizado para returnar o segundo Segmento do Retangulo.
     * @return       sr2 -  O segundo SegmentoReta
     */
    public SegmentoReta getSegm2(){
        return segs.get(1);
    }
    /** Método utilizado para returnar o terceiro Segmento do Retangulo.
     * @return       sr3 -  O terceiro SegmentoReta
     */
    public SegmentoReta getSegm3(){
        return segs.get(2);
    }
    /** Método utilizado para returnar o quarto Segmento do Retangulo.
     * @return       sr4 -  O quarto SegmentoReta
     */
    public SegmentoReta getSegm4(){
        return segs.get(3);
    }


    /** Este método dá print de "Retangulo:vi" e encerra o programa caso, não seja um Retangulo
     *
     */
    private void err() {
        if(!checks()) {
            System.out.println("Retangulo:vi");
            System.exit(0);
        }
    }



}
