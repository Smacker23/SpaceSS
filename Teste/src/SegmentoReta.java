
import java.util.ArrayList;
import java.util.Scanner;
/**
 * @author Alexandre Carvalho
 * @version correto-17 Amazon correto version 17.0.4 23/02/2023
 * @inv p1.dist(p2) != 0
 * */

public class SegmentoReta {
    private Ponto p1, p2;

    /** Este construtor vai dar scan a dois pontos, que por sua vez vão se tornar 1 segmento de reta. Por fim é verificado se pertence
     * os dois pontos dados não são iguais
     * @param in        Scanner(System.in) - Utilizado para ler os valores do terminal
     */
    public SegmentoReta(Scanner in){
        this.p1 = Ponto.scanner_to_point(in);
        this.p2 = Ponto.scanner_to_point(in);
        err();
    }

    /** Este construtor vai receber dois pontos, que por sua vez vão se tornar 1 segmento de reta. Por fim é verificado se pertence
     * os dois pontos dados não são iguais
     * @param p1        Primeiro Ponto do SegmentoReta
     * @param p2        Segundo Ponto do SegmentoReta
     */

    public SegmentoReta(Ponto p1, Ponto p2){
        this.p1 = p1;
        this.p2 = p2;
        err();
    }
    //1 = HH  2 - DD  3 - HD  4 - VV  - 5 VH  6  VD

    /** Este método retorna um inteiro verifica se dois SegmentoReta se intersetam através de outros dois métodos que esta função chama
     *
     * @param sr    SegmentoReta que irá ser verificado se interseta
     * @return      True se intersetar. False caso não intersete
     */
    public boolean intersecta(SegmentoReta sr){
        boolean hit = false;
        int i = 0;
        Reta r1 = new Reta(this.p1, this.p2);
        Reta r2 = new Reta(sr.p1,sr.p2);
        i = r1.retaInterseta(r2);
        if(i == 0)
            return false;
        if(i == 2 || i == 3 || i == 5 || i == 6) {
            hit = diagD_diagH_vertH_diagV(r1, r2, sr, i);
        }
        else {
            hit = horzH_vertV(sr, i);
        }
        return hit;
    }

    /** Método utilizado para dois SegmentoReta horizontais, verticais
     *
     * @param sr    SegmentoReta que irá ser verificado se interseta
     * @param i     Inteiro que representa o caso que vamos estudar 1 HoriznotalHorizontal, 4 VericalVertical
     * @return      True se intersetarem. False caso não
     */
    private boolean horzH_vertV(SegmentoReta sr, int i){
        if((i == 1) &&(
                ((this.p1.get_x() >= Math.min(sr.p1.get_x(),sr.p2.get_x())) && this.p1.get_x() <= Math.max(sr.p1.get_x(), sr.p2.get_x())) ||
                        (this.p2.get_x() >= Math.min(sr.p1.get_x(),sr.p2.get_x())) && this.p2.get_x() <= Math.max(sr.p1.get_x(), sr.p2.get_x())))
            return true;
        else if(i == 4 && ((this.p1.get_y() > Math.min(sr.p1.get_y(),sr.p1.get_y())) && (this.p1.get_y() < Math.max(sr.p1.get_y(),sr.p1.get_y()))))
            return true;
        return false;
    }
//1 = HH  2 - DD  3 - HD  4 - VV  - 5 VH  6  VD
    /** Método utilizado para dois SegmentoReta diagonais, DiagonalHorizontal, verticalHorizontal, diagonalVertical
     *
     * @param sr    SegmentoReta que irá ser verificado se interseta
     * @param i     Inteiro que representa o caso que vamos estudar 2 diagonalDiagonal, 3 DiagonalHorizontal 5 verticalHorizontal, 6 diagonalVertical
     * @return      True se intersetarem. False caso não
     */
    private boolean diagD_diagH_vertH_diagV(Reta r1, Reta r2, SegmentoReta sr, int i){
        Ponto intersect;
        if(i == 2){
            intersect = r1.pontoIntersect(r2);
            if(((intersect.get_y() >= Math.min(sr.p1.get_y(), sr.p2.get_y()) && intersect.get_y() <= Math.max(sr.p1.get_y(), sr.p2.get_y())) && (intersect.get_x() >= Math.min(sr.p1.get_x(), sr.p2.get_x()) && intersect.get_x() <= Math.max(sr.p1.get_x(), sr.p2.get_x()))) && ((intersect.get_y() >= Math.min(this.p1.get_y(), this.p2.get_y()) && intersect.get_y() <= Math.max(this.p1.get_y(), this.p2.get_y())) && (intersect.get_x() >= Math.min(this.p1.get_x(), this.p2.get_x()) && intersect.get_x() <= Math.max(this.p1.get_x(), this.p2.get_x()))))
                return true;
            else if((r1.getM() == r2.getM() && r1.getB() == r2.getB()) && ((this.p1.get_x() >= Math.min(sr.p1.get_x(),sr.p2.get_x()) && this.p1.get_x() <= Math.max(sr.p1.get_x(),sr.p2.get_x())) || (this.p2.get_x() >= Math.min(sr.p1.get_x(),sr.p2.get_x()) && this.p2.get_x() <= Math.max(sr.p1.get_x(),sr.p2.get_x()))))
                return true;
            else
                return false;
        }
        if(i == 3) {
            if(r1.getM() == 0) {
                intersect = r2.pontoIntersectHD(r1);
                return (((intersect.get_y() >= Math.min(sr.p1.get_y(), sr.p2.get_y()) && intersect.get_y() <= Math.max(sr.p1.get_y(), sr.p2.get_y())) &&
                        (intersect.get_x() >= Math.min(sr.p1.get_x(), sr.p2.get_x()) && intersect.get_x() <= Math.max(sr.p1.get_x(), sr.p2.get_x()))) &&
                        ((intersect.get_y() >= Math.min(this.p1.get_y(), this.p2.get_y()) && intersect.get_y() <= Math.max(this.p1.get_y(), this.p2.get_y())) &&
                                (intersect.get_x() >= Math.min(this.p1.get_x(), this.p2.get_x()) && intersect.get_x() <= Math.max(this.p1.get_x(), this.p2.get_x()))));
            }
            else {
                intersect = r1.pontoIntersectHD(r2);
                return (((intersect.get_y() >= Math.min(sr.p1.get_y(), sr.p2.get_y()) && intersect.get_y() <= Math.max(sr.p1.get_y(), sr.p2.get_y())) &&
                        (intersect.get_x() >= Math.min(sr.p1.get_x(), sr.p2.get_x()) && intersect.get_x() <= Math.max(sr.p1.get_x(), sr.p2.get_x()))) &&
                        ((intersect.get_y() >= Math.min(this.p1.get_y(), this.p2.get_y()) && intersect.get_y() <= Math.max(this.p1.get_y(), this.p2.get_y())) &&
                                (intersect.get_x() >= Math.min(this.p1.get_x(), this.p2.get_x()) && intersect.get_x() <= Math.max(this.p1.get_x(), this.p2.get_x()))));
            }
        } else if(i == 5){
            if(r1.getM() == 0) {
                intersect = r2.pontoIntersectHV(r1);
                return (intersect.get_x() >= Math.min(this.p1.get_x(),this.p2.get_x()) & intersect.get_x() <= Math.max(this.p1.get_x(),this.p2.get_x())&&
                        intersect.get_y() >= Math.min(sr.p1.get_y(),sr.p2.get_y()) & intersect.get_y() <= Math.max(sr.p1.get_y(),sr.p2.get_y()));
            }
            else {
                intersect = r1.pontoIntersectHV(r2);
                return (intersect.get_x() >= Math.min(sr.p1.get_x(),sr.p2.get_x()) & intersect.get_x() <= Math.max(sr.p1.get_x(),sr.p2.get_x())&&
                        intersect.get_y() >= Math.min(this.p1.get_y(),this.p2.get_y()) & intersect.get_y() <= Math.max(this.p1.get_y(),this.p2.get_y()));
            }
    }
        else if(i == 6) {
            if(this.p1.get_x() - this.p2.get_x() == 0) {
                intersect = r2.pontoIntersectVertical(this.p1.get_x());
                return  (((intersect.get_y() >= Math.min(this.p1.get_y(), this.p2.get_y()) && intersect.get_y() <= Math.max(this.p1.get_y(), this.p2.get_y()))
                        && intersect.get_x() >= Math.min(this.p1.get_x(), this.p2.get_x()) && intersect.get_x() <= Math.max(this.p1.get_x(), this.p2.get_x())) &&
                        (intersect.get_y() >= Math.min(sr.p1.get_y(), sr.p2.get_y()) && intersect.get_y() <= Math.max(sr.p1.get_y(), sr.p2.get_y()))
                        && intersect.get_x() >= Math.min(sr.p1.get_x(), sr.p2.get_x()) && intersect.get_x() <= Math.max(sr.p1.get_x(), sr.p2.get_x()));
            }
            else {
                intersect = r1.pontoIntersectVertical(sr.p1.get_x());
                return ((intersect.get_y() >= Math.min(sr.p1.get_y(), sr.p2.get_y()) && intersect.get_y() <= Math.max(sr.p1.get_y(), sr.p2.get_y()))
                        && intersect.get_x() >= Math.min(sr.p1.get_x(), sr.p2.get_x()) && intersect.get_x() <= Math.max(sr.p1.get_x(), sr.p2.get_x()) &&
                        ((intersect.get_y() >= Math.min(this.p1.get_y(), this.p2.get_y()) && intersect.get_y() <= Math.max(this.p1.get_y(), this.p2.get_y()))
                                && intersect.get_x() >= Math.min(this.p1.get_x(), this.p2.get_x()) && intersect.get_x() <= Math.max(this.p1.get_x(), this.p2.get_x())));
            }
        }
        return false;
    }




    /** Método que verifica se distância entre os pontos p1 e p2 é diferente que 0. Caso esta seja 0, os dois pontos são iguais.
     * @return True se os dois pontos forem diferentes. False se os dois pontos forem iguais
     */
    private boolean check(){
        if(p1.dist(p2) == 0)
            return false;
        return true;
    }

    /** Método que dá print de "Segmento:vi" e finaliza o programa, caso este não passe no teste "check"
     */
    private void err(){
        if(!check()){
            System.out.println("Segmento:vi");
            System.exit(0);
        }
    }
    /** Método utilizado para returnar o primeiro Ponto do SegmentoReta.
     * @return       p1 -  O primeiro ponto do SegmentoReta
     */
    public Ponto get_first(){
        return p1;
    }
    /** Método utilizado para returnar o segundo Ponto do SegmentoReta.
     * @return       p2 -  O segudno ponto do SegmentoReta
     */
    public Ponto get_second(){
        return p2;
    }




}
