

import java.util.Random;
import java.util.Scanner;
/**
 * @author Alexandre Carvalho
 * @version correto-17 Amazon correto version 17.0.4 23/02/2023
 * @inv double x >= 0 && y >= 0
 * */
public class Ponto {
    private double x, y;

    /** Este construtor vai dar scan a dois eixos, que por sua vez vão se tornar 1 ponto. Por fim é verificado se pertence ao primeiro quadrante
     * @param in        Scanner(System.in) - Utilizado para ler os valores do terminal
     */
    public Ponto(Scanner in){
        this.x = in.nextDouble();
        this.y = in.nextDouble();
        first_quadrant();
    }

    /** Método que cria um Ponto aleatório com um alcance de 100 x 100
     *
     * @param randomGenerator Random utilizado para criar o Ponto aleatório
     */
    public Ponto(Random randomGenerator){
        this.x = randomGenerator.nextInt(100);
        this.y = randomGenerator.nextInt(100);
        first_quadrant();
    }

    /** Este construtor através de dois valores cria um novo ponto. Por fim é verificado se pertence ao primeiro quadrante
     * @param x       double Positivo  -  Utilizado para o eixo x do ponto
     * @param y       double Positivo  -  Utilizado para o eixo y do ponto
     */
    public Ponto(double x, double y){
        this.x = x;
        this.y = y;
        first_quadrant();
    }
    /** Este construtor através de dois valores cria um novo ponto.
     * @param i       Inteiro (1) para confirmar que o Ponto pode não pertencer ao primeiro quadrante
     * @param x       double Positivo  -  Utilizado para o eixo x do ponto
     * @param y       double Positivo  -  Utilizado para o eixo y do ponto
     */
    public Ponto(int i, double x, double y){
        if(i == 1){
            this.x = x;
            this.y = y;
        }
    }

    /** Calculo da distância eucladiana de dois pontos
     * @param p     Ponto pertencente ao primeiro quadrante
     * @return      Distância eucladiana do Ponto p com o segundo Ponto (o do construtor)
     */

    public double dist (Ponto p) {
        double dx = x - p.x;
        double dy = y - p.y;
        return Math.abs(Math.sqrt(dx*dx + dy*dy));
    }

    /** Método utilizado para dar scan a dois doubles, que iram ser utilizado para criar um Ponto. Está função irá ser chamada no construtor.
     * @param in     Scanner(System.in) - Utilizado para ler os valores do terminal
     * @return       p - Ponto pertencente ao primeiro quadrante
     */
    public static Ponto scanner_to_point(Scanner in){
        double x = Double.parseDouble(in.next());
        double y = Double.parseDouble(in.next());
        Ponto p = new Ponto(x, y);
        return p;
    }
    /** Método que retorna o eixo x do Ponto
     * @return       x  Double positivo.
     */
    public double get_x(){
        return x;
    }

    /** Método que retorna o eixo y do Ponto
     * @return       y  Double positivo.
     */
    public double get_y(){
        return y;
    }

     /** Método que verifica se o Ponto pertence ao primeiro quadrante, caso não pertence o programa irá ser finalizado após dar
     * print "Ponto:vi". Este método é utilizado nos dois construtores
     */

    private void first_quadrant(){
        if(x < 0 || y < 0 ){
            System.out.println("Ponto:vi");
            System.exit(0);
        }
    }

    /** Método utilizado que consoante o quadrante que o Ponto estiver retorna dois números double, que irão ser necessárias para saber
     * a direção que o Robot precisa de se mover
     *
     * @return  Array de double com os dois números
     */
    public double[] checkQuadrant(){
        double [] values = new double[2];
        if(this.x >= 0 && this.y >= 0){     //first
            values[0] = 1;
            values[1] = 1;
        } else if(this.x < 0 && this.y >= 0){       //second
            values[0] = -1;
            values[1] = -1;
        } else if(this.x < 0 && this.y < 0){        //third
            values[0] = -1;
            values[1] = -1;
        } else if(this.x >= 0 && this.y < 0){       //fourth
            values[0] = 1;
            values[1] = 1;
        }
        return values;
    }

    /** Método que confirma se o Ponto está dentro dos limties do Mapa
     *
     * @return True se estiver. False caso não
     */
    public boolean checkFirstQuadrant(){
        return (this.x >= 0 && this.x < 1000 && this.y >= 0 && this.y < 1000);
    }

    /** Método que retorna uma String com a localização do Ponto
     *
     * @return String
     */
    public String toString(){
        return "["+ this.x + ";" + this.y +"]";
    }



}
