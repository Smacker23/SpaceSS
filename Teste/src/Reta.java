
/**
 * @author Alexandre Carvalho
 * @version correto-17 Amazon correto version 17.0.4 23/02/2023
 * */
public class Reta {
    private static final double DISTANCE = 0.1;
    private SegmentoReta sr;
    private double m, b;
    private int type;  // 0 - horizontal  1  - vertical  2  - diagonal para cima  3  - diagonal para baixoa

    /** Este construtor vai receber dois pontos, que, vão se tornar 1 segmento de reta. Através desse segmento
     * é também calculado o declive da reta(m) e o ponto de origem (b)
     * @param p1        Primeiro Ponto do SegmentoReta
     * @param p2        Segundo Ponto do SegmentoReta
     */
    public Reta(Ponto p1, Ponto p2){

        this.sr = new SegmentoReta(p1, p2);
        this.m = (sr.get_second().get_y() - sr.get_first().get_y()) / (sr.get_second().get_x() - sr.get_first().get_x());
        this.b = sr.get_first().get_y() - this.m * sr.get_first().get_x();
        type();
    }

    private void type(){
        if (this.m == 0)
            this.type = 0;
        else if (sr.get_second().get_x() - sr.get_first().get_x() == 0)
            this.type = 1;
        else if((this.sr.get_first().get_y() > this.sr.get_second().get_y() && this.sr.get_first().get_x() > this.sr.get_second().get_x() )
                || (this.sr.get_first().get_y() < this.sr.get_second().get_y() && this.sr.get_first().get_x() < this.sr.get_second().get_x()))
            this.type = 2;
        else
            this.type = 3;
    }
//1 - 2 horizontas   2 - 2 diagonais   3 - horizontal diagonal

    /** Este método vai ver se duas retas se intersetam. Caso elas se intersetem retorna um inteiro consoante o tipo de retas que forem.
     * retorna 1, se for 2 Horizontais. 2, se for 2 Diagonais. 3, se for uma HorizontalDiagonal. Por fim 0 se não intersetarem
     *
     * @param aux       Reta auxiliar que vai ser comparada, com a do construtor
     * @return          Um inteiro consoante o tipo de retas
     */
    private int horH_diagD_horD(Reta aux){
        int i = 0;
        if(this.m == aux.m && this.b == aux.b) {
            if(this.m == 0)
                i = 1;
        }
        else if((i == 0) && ((this.m == 0 && aux.m != 0) || (this.m != 0 && aux.m == 0)) && (this.sr.get_second().get_x() - this.sr.get_first().get_x() != 0 && aux.sr.get_second().get_x() - aux.sr.get_first().get_x() != 0))
            i = 3;
        else if(this.m == 0 && aux.m == 0 && this.b != aux.b)
            i = 0;
        else if((this.m == aux.m && this.b == aux.b) || (((this.m != 0 && this.sr.get_first().get_x() - this.sr.get_second().get_x() != 0) && (aux.m != 0 && aux.sr.get_first().get_x() - aux.sr.get_second().get_x() != 0)) && this.m != aux.m))
            i = 2;
        return i;
    }
//4   - 2 verticais  5  -  vertical horizontal  6 - vertical diagonal
    /** Este método vai ver se duas retas se intersetam. Caso elas se intersetem retorna um inteiro consoante o tipo de retas que forem.
     * retorna 4, se for 2 Verticais. 5, se for VerticalHorizontals. 6, se for uma VerticalDiagonal. Por fim 0 se não intersetarem
     *
     * @param aux       Reta auxiliar que vai ser comparada, com a do construtor
     * @return          Um inteiro consoante o tipo de retas
     */
    private int vertV_vertH_vertD(Reta aux){
        int i = 0;
        if(this.sr.get_second().get_x() - this.sr.get_first().get_x() == 0 && aux.sr.get_second().get_x() - aux.sr.get_first().get_x() == 0){
            if(this.sr.get_first().get_x() == aux.sr.get_first().get_x())
                i = 4;
        } else if(this.sr.get_second().get_x() - this.sr.get_first().get_x() == 0 || aux.sr.get_second().get_x() - aux.sr.get_first().get_x() == 0) {
            if(this.m == 0 || aux.m == 0)
                i = 5;
            else
                i = 6;
        }
        return i;
    }

    /** Este método retorn um inteiro consoante o tipo de retas, através de outros dois métodos.
     *
     * @param aux       Reta auxiliar que vai ser comparada, com a do construtor
     * @return          Um inteiro consoante o tipo de retas
     */
    public int retaInterseta(Reta aux){
        int i = 0;
        i = horH_diagD_horD(aux);
        if(i == 0)
            i = vertV_vertH_vertD(aux);
        return i;
    }

    //if Ponto(-1 -1) Significa que nao intersecta
    //A reta que chama a funcao tem de ser diagonal

    /** Este método retorno o ponto onde duas retas se intersetam, para o caso de duas diagonais
     *
     * @param aux       Reta a ser comparada
     * @return          Um Ponto que representa o Ponto de interseção
     */
    public Ponto pontoIntersectDD(Reta aux){
        Reta first;
        Reta second;
        if((this.type == 2 || this.type == 3) && (aux.type == 2 || aux.type == 3)) {
            first = this;
            second = aux;
        } else
            return null;
        double m = first.m - second.m;
        if(m == 0)
            throw new IllegalArgumentException("m cant be 0");
        double point_intersectionX = (second.b - first.b) / m;
        double point_intersectionY = first.m * point_intersectionX + first.b;
        return new Ponto(1, point_intersectionX, point_intersectionY);
    }


    /**  Este método retorno o ponto de interseção de um Reta Horizontal e Diagonal
     *
     * @param aux       Reta a ser comparada
     * @return          Um Ponto que representa o Ponto de interseção
     */
    public Ponto pontoIntersectHD(Reta aux){
        Reta first;
        Reta second;
        if(this.type == 0 && (aux.type == 2 || aux.type == 3)) {
            first = this;
            second = aux;
        }
        else if((this.type == 2 || this.type == 3) && aux.type == 0){
            first = aux;
            second = this;
        } else
            return null;
        double m = first.m - second.m;
        double x = (second.b - first.b) / m;
        double y = first.m * x + first.b;
        return new Ponto(1, x, y);
    }

    /**  Este método retorno o ponto de interseção de duas Retas Verticais
     *
     * @param x         Double que representa o eixo do x da Reta auxiliar
     * @return          Um Ponto que representa o Ponto de interseção
     */
    public Ponto pontoIntersectVertical(double x){
        double y = this.m * x + this.b;
        return new Ponto(1, x, y);
    }
    public Ponto pontoIntersect(Reta aux){
        double m = this.m - aux.m;
        if(m == 0)
            throw new IllegalArgumentException("m cant be 0");
        double point_intersectionX = (aux.b - this.b) / m;
        double point_intersectionY = this.m * point_intersectionX + this.b;
        return new Ponto(1, point_intersectionX, point_intersectionY);
    }



    /**  Este método retorno o ponto de interseção de uma Reta Horizontal e outra Vertical
     *
     * @param aux       Reta a ser comparada
     * @return          Um Ponto que representa o Ponto de interseção
     */
    public Ponto pontoIntersectHV(Reta aux){
        Reta first;
        Reta second;
        if(this.type == 0 && aux.type == 1) {
            first = this;
            second = aux;
        }
        else if(this.type == 1 && aux.type == 0){
            first = aux;
            second = this;
        } else
            return null;
        return new Ponto(second.sr.get_first().get_x(), first.sr.get_second().get_y());
    }

    /** Este método retorna o Ponto de interseção para uma Reta vertical e outra diagonal
     *
     * @param aux   Reta auxliar
     * @return  Ponto de interseção, ou null caso não se intersetem
     */
    public Ponto pontoIntersectVD(Reta aux) {
        Reta first;
        Reta second;
        if ((this.type == 2 || this.type == 3) && aux.type == 1) {
            first = aux;
            second = this;
        } else if ((aux.type == 2 || aux.type == 3) && this.type == 1) {
            first = this;
            second = aux;
        } else
            return null;
        double y = second.m * first.sr.get_first().get_x() + second.b;
        return new Ponto(first.sr.get_first().get_x(), y);
    }

    /** Este método retorna o declive da reta
     *
     * @return  Double que representa o declive
     */

    public double getM(){
        return this.m;
    }

    /**  Este método retorna o ponto de origem da reta
     *
     * @return  Double que representa o Ponto de origem
     */
    public double getB(){
        return this.b;
    }

    /** Este método dá print da equação da reta
     */
    public void print_equation(){
        System.out.println("m: " + this.m + " b:" + this.b);
    }

    /** Este método retorna o tipo de reta (0 == horizontal, 1 == vertical, 2 == diagonal a subir no eixo do y, 3  == diagonal a descer no eixo do y)
     *
     * @return  inteiro que representa o tipo
     */
    public int getType() {
        return type;
    }

    /** Este método vai retornar o Ponto de interseção de duas retas utilizando os diferentes métodos para os diferentes casos
     *
     * @param aux Reta auxiliar
     * @return Ponto de interseção, ou null caso não se intersetem
     */
    public Ponto pointInteserction(Reta aux){
        Ponto intersection = null;
        if(this.type == 0 && aux.type == 0)
            return null;
        if(this.type == 1 && aux.type == 1)
            return null;
        if(intersection == null)
            intersection = this.pontoIntersectHD(aux);
        if(intersection == null)
            intersection = this.pontoIntersectHV(aux);
        if(intersection == null)
            intersection = this.pontoIntersectDD(aux);
        if(intersection == null)
            intersection = this.pontoIntersectVD(aux);
        return intersection;
    }

    /** Este método retorna os extremos para o caso de uma reta diagonal
     *
     * @return  Array de Pontos com os dois extremos
     */
    private Ponto[] getExtremesD(){
        double dist = DISTANCE;
        double y = (this.sr.get_second().get_y() - this.sr.get_first().get_y());
        double x = (this.sr.get_second().get_x() - this.sr.get_first().get_x());
        Ponto aux = new Ponto(1, x, y);
        Double angle = Math.atan(y / x);
        double[] quadrant = quadrant = aux.checkQuadrant();
        double x_increment = Math.cos(angle) * dist * quadrant[0];
        double y_increment = Math.sin(angle) * dist * quadrant[1];
        Ponto first;
        Ponto second;
        first = new Ponto(1, this.sr.get_first().get_x() - x_increment, this.sr.get_first().get_y() - y_increment);
        second = new Ponto(1, this.sr.get_second().get_x() + x_increment, this.sr.get_second().get_y() + y_increment);
        Ponto[] result = new Ponto[2];
        result[0] = first;
        result[1] = second;
        return result;
    }

    /** Este método retorna os extremos para o caso de uma reta horizontal
     *
     * @return  Array de Pontos com os dois extremos
     */
    public Ponto[] getExtremesH(){
        Ponto[] points = new Ponto[2];
        Ponto first = null;
        Ponto second = null;
        if(this.sr.get_first().get_x() > this.sr.get_second().get_x()){
            first = new Ponto(1,this.sr.get_first().get_x()+DISTANCE, this.sr.get_first().get_y());
            second = new Ponto(1,this.sr.get_second().get_x()-DISTANCE, this.sr.get_second().get_y());
        } else {
            first = new Ponto(1,this.sr.get_first().get_x()-DISTANCE, this.sr.get_first().get_y());
            second = new Ponto(1,this.sr.get_second().get_x()+DISTANCE, this.sr.get_second().get_y());
        }
        points[0] = first;
        points[1] = second;
        return points;
    }

    /** Este método retorna os extremos para o caso de uma reta vertical
     *
     * @return  Array de Pontos com os dois extremos
     */
    public Ponto[] getExtremesV(){
        Ponto[] points = new Ponto[2];
        Ponto first = null;
        Ponto second = null;
        if(this.sr.get_first().get_y() > this.sr.get_second().get_y()){
            first = new Ponto(1, this.sr.get_first().get_x(), this.sr.get_first().get_y()+DISTANCE);
            second = new Ponto(1, this.sr.get_second().get_x(), this.sr.get_second().get_y()-DISTANCE);
        } else {
            first = new Ponto(1, this.sr.get_first().get_x(), this.sr.get_first().get_y()-DISTANCE);
            second = new Ponto(1, this.sr.get_second().get_x(), this.sr.get_second().get_y()+DISTANCE);
        }
        points[0] = first;
        points[1] = second;
        return points;
    }

    /** Este método retorna os Extremos do SegmentoReta com a diferença da variável "DISTANCE", fazendo assim com que os segmentosReta
     * já não se intersetem
     *
     * @return  Array com os dois possíveis pontos para se desviar do SegmentoReta
     */
    public Ponto[] getExtremes(){
        Ponto[] points = new Ponto[2];
        if (this.type == 0){
           points = getExtremesH();
        } else if (this.type == 1){
            points = getExtremesV();
        }else if(this.type == 2 || this.type == 3)
            points = getExtremesD();
        return points;
    }
}
