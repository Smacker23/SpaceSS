import java.util.ArrayList;

/**
 * @author Alexandre Carvalho
 * @version correto-17 Amazon correto version 17.0.4 23/02/2023
 * */
public class Poligono extends FiguraGeometrica{
    protected ArrayList<Ponto> points = new  ArrayList<Ponto>();
    protected ArrayList<SegmentoReta> segs = new ArrayList<SegmentoReta>();


    /** Método que confirma se um SegmentoReta interseta pelo menos um dos lados do Retangulo
     * @param srHit Segmento de reta de poderá intersetar o triangulo
     * @return True se o SegmentoReta intersetar e False caso não intersete
     */
    public boolean check_hit(SegmentoReta srHit){
        for(int i = 0; i < this.segs.size(); i++){
            if(this.segs.get(i).intersecta(srHit))
                return true;
        }
        return false;
    }


    /** Este método retorna 3 pontos, sendo eles: o Ponto de interseção do segmentoReta ao Poligono, e os dois
     * Pontos que representam os extremos do segmentoReta do Poligono que foi intersetado, fazendo assim com que
     * a trajetória já não intersete o Poligono
     *
     * @param srHit SegmentoReta que intersetou o Poligono
     * @return  Array de Pontos
     */
    public Ponto[] check_hitSegment(SegmentoReta srHit){
            Ponto[] resultFinal = new Ponto[3];
            Ponto[] teste = new Ponto[2];
            Reta rect = new Reta(srHit.get_first(), srHit.get_second());
            Reta resultRect = null;
            Reta aux = null;
            Ponto current = null;
            Ponto result = null;
            for (int i = 0; i < this.segs.size(); i++) {
                if (this.segs.get(i).intersecta(srHit)) {
                    aux = new Reta(this.segs.get(i).get_first(), this.segs.get(i).get_second());
                    current = rect.pointInteserction(aux);
                    if(current != null) {
                        if (result == null || current.dist(srHit.get_first()) < result.dist(srHit.get_first())) {
                            result = current;
                            resultRect = aux;

                        }
                    }
                }
            }
            if (resultRect != null) {
                teste = resultRect.getExtremes();
                resultFinal[0] = result;
                resultFinal[1] = teste[0];
                resultFinal[2] = teste[1];
                return resultFinal;
            }
            return null;
        }

    /** Este método retorna o tipo de FiguraGeométrica, no caso 1 se for Triângulo e 0 se for Retângulo
     *
     * @return
     */
    @Override
    public int returType() {
        if(this.points.size() == 3)
            return 1;
        else
            return 0;
    }

    /** Este método após verificar se um segmento de reta interseta o Triangulo, dá print de "Fail", caso seja
     * intersetado e "Ok" caso não
     * @param srHit
     */
    public String stringToPrint(SegmentoReta srHit){
        if(check_hit(srHit))
            return "Fail";
        else
            return "Ok";
    }


    /** Este método calcula a área de um Triângulo
     *
     * @param x1 x do Ponto1
     * @param y1 y do Ponto1
     * @param x2 x do Ponto2
     * @param y2 y do Ponto2
     * @param x3 x do Ponto3
     * @param y3 y do Ponto3
     * @return  double
     */
    private static double area(double x1, double y1, double x2, double y2, double x3, double y3) {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2))/2.0);
    }

    /** Este método verifica se um Ponto está dentro de um Polígono. O método utilizado em com base das áreas. Inicialmente é calculado a área
     * do Poligono e Por fim será calculado a área dos Triângulos formados pelo ponto. Isto é através de dois pontos do polígono e o Ponto p, para
     * todos os extremos do Poligono (no caso do Polígono ser Retangulo irá haver 4 Triângulos e de ser Triângulo apenas 3 Triângulos) por fim
     * se a soma das áreas do triângulos formados pelo o Ponto P for a mesma qque a área do Polígono, signfica que o Ponto está dentro do
     * Polígono.
     *
     * @param p Ponto
     * @return True se o Ponto P estiver dentro do Polígo e False caso contrário
     */
    public boolean figVerification(Ponto p){
        double areaTriangle = 0;
        double areaTotal = 0;
        if(segs.size() == 3){
            areaTotal = area(points.get(0).get_x(),points.get(0).get_y(),points.get(1).get_x(),points.get(1).get_y(),points.get(2).get_x(),points.get(2).get_y());
        } else {
            areaTotal = area(points.get(0).get_x(),points.get(0).get_y(),points.get(1).get_x(),points.get(1).get_y(),points.get(2).get_x(),points.get(2).get_y());
            areaTotal += area(points.get(0).get_x(),points.get(0).get_y(),points.get(2).get_x(),points.get(2).get_y(),points.get(3).get_x(),points.get(3).get_y());
        }
        for (int i = 0; i < points.size(); i++){
            if(i == points.size()-1){
                areaTriangle += area(points.get(i).get_x(),points.get(i).get_y(),points.get(0).get_x(),points.get(0).get_y(),p.get_x(),p.get_y());
            } else {
                areaTriangle += area(points.get(i).get_x(),points.get(i).get_y(),points.get(i+1).get_x(),points.get(i+1).get_y(),p.get_x(),p.get_y());
            }
        }
        if(areaTriangle == areaTotal){
            return true;
        }
        return false;
    }

    /** Método que retorna uma String do Polígono com a informação do tipo de obstáculo e os seus pontos
     *
     * @return String
     */
    public String toString(){
        String print = "";
        if(this.points.size() == 3)
            print += "<Triangulo: ";
        else
            print += "<Retangulo: ";
        for(Ponto point: this.points){
            print += "(" + point.get_x() + ";" + point.get_y() + ") ";
        }
        print = print.substring(0, print.length()-1);
        print += ">";
        return print;
    }

    public int[] getAllX(){
        int[] result = new int[this.points.size()];
        for(int i = 0; i < this.points.size(); i++){
            result [i] = (int) this.points.get(i).get_x();
        }
        return result;
    }

    public int[] getAllY(){
        int[] result = new int[this.points.size()];
        for(int i = 0; i < this.points.size(); i++){
            result [i] = (int) this.points.get(i).get_y();
        }
        return result;
    }

    public int getSize(){
        return this.points.size();
    }

}
