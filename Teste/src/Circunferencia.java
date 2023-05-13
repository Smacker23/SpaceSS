

import java.util.ArrayList;
import java.util.Scanner;
/**
 * @author Alexandre Carvalho
 * @version correto-17 Amazon correto version 17.0.4 23/02/2023
 * @inv radius > 0
 * */
public class Circunferencia extends FiguraGeometrica{
    private double radius;
    private Ponto center;

    /** Este construtor vai receber uma String que recebe os valores necessários para construir uma Circunferencia. Por fim verifica
     * se é um Circunferencia
     * @param s        String que contem os valores da circunferencia
     */
    public Circunferencia(String s){
        String [] split = s.split(" ");
        this.center = new Ponto(Double.parseDouble(split[1]), Double.parseDouble(split[2]));
        this.radius = Double.parseDouble(split[3]);
        err();
    }

    /** Este construtor vai dar scan dá de um ponto e um double, que representam o ponto do centro e o raio da circunferencia
     * respetivamente. Por fim o construtor verifica se o raio é menor ou igual a 0, caso seja encerra o programa.
     * @param in        Scanner(System.in) - Utilizado para ler os valores do terminal
     */
    public Circunferencia(Scanner in){
        this.center = new Ponto(in);
        this.radius = in.nextDouble();
        err();
    }


    /** Este construtor vai receber um Ponto e um double que representam respetivamente o ponto(centro) e o raio da
     * circunferencia. Por fim o construtor verifica se o raio é menor ou igual a 0, caso seja encerra o programa.
     * @param center        Ponto que representa o centro da circunferencia.
     * @param rad           Double que representa o raio da circunferencia.
     */
    public Circunferencia(Ponto center, double rad){
        this.center = center;
        this.radius = rad;
        err();
    }

    /** Este método verifica se um segmento de reta interseta uma circunferencia, utilizando outros dois métodos
     * @param srHit     SegmentoReta que vai ser verificada na interseção
     * @return          True se eles se intersetarem. False caso não se intersetem.
     */
    public boolean check_hit(SegmentoReta srHit) {
        boolean hit = false;
        if(srHit.get_first().get_x() == srHit.get_second().get_x())
            hit = checkHitVertical(srHit);
        else
            hit = checkHit(srHit);
        return hit;
    }

    /** Este método recebe um SegmentoReta que interseta a circunferência e irá gerar n Pontos até que o SegmentoReta
     * consiga contornar a Crincuferência sem a intersetar, gerando um conjunto de Pontos para duas trajetórias possíveis,
     * pois é possível a contornar de duas formas diferentes
     *
     * @param srHit
     * @return
     */
    public Ponto[] check_hitSegment(SegmentoReta srHit) {
        Reta rect = new Reta(srHit.get_first(), srHit.get_second());
        String print42;
        int size = 0;
        int sizeFirst = 0;
        int sizeSecond = 0;
        Ponto start = null;
        if(rect.getType() == 0 || rect.getType() == 1){
            size++;
            start = srHit.get_first();
            srHit = new SegmentoReta(new Ponto(srHit.get_first().get_x()+0.5, srHit.get_first().get_y()+0.5), srHit.get_second());
            if(!checkHit(srHit)){
                Ponto[] points = new Ponto[6];
                Ponto[] pointsIntersection = this.getIntersectionPoints(srHit);
                points[0] = pointsIntersection[0];
                points[1] = start;
                points[2] = srHit.get_first();
                points[3] = srHit.get_second();
                points[4] = start;
                points[5] = srHit.get_second();
                return points;
            }
        }
        if(!this.checkHit(srHit))
            return null;
        Ponto[] pointsIntersection = this.getIntersectionPoints(srHit);
        int firstQuadrant = this.checkQuadrant(pointsIntersection[0]);
        int secondQuadrant = this.checkQuadrant(pointsIntersection[1]);
        if(secondQuadrant == 0|| firstQuadrant == 0){
            size++;
            start = srHit.get_first();
            srHit = new SegmentoReta(new Ponto(srHit.get_first().get_x()+0.5, srHit.get_first().get_y()+0.5), srHit.get_second());
            firstQuadrant = this.checkQuadrant(pointsIntersection[0]);
            secondQuadrant = this.checkQuadrant(pointsIntersection[1]);
        }
        Ponto[][] paths = this.checkPath(srHit, firstQuadrant, secondQuadrant);
        Trajetoria pathFirst = this.createPath(paths[0]);
        if(pathFirst == null || pathFirst.killPath()) {
            pathFirst = new Trajetoria();
            pathFirst.addOnePoint(srHit.get_first());
            pathFirst.addOnePoint(srHit.get_second());
        }
        sizeFirst = pathFirst.getPoints().size();
        Trajetoria pathSecond = this.createPath(paths[1]);
        if(pathSecond == null || pathSecond.killPath()) {
            pathSecond = new Trajetoria();
            pathSecond.addOnePoint(srHit.get_first());
            pathSecond.addOnePoint(srHit.get_second());
        }
        sizeSecond = pathSecond.getPoints().size();
        size += sizeFirst + sizeSecond + 1;
        Ponto[] points = new Ponto[size];
        if(start != null)
            points[1] = start;
        points[0] = pointsIntersection[0];
        if(start == null) {
                for (int i = 1; i < sizeFirst+1; i++) {
                    points[i] = pathFirst.getPoints().get(i - 1);
            }
            int j = 0;
                for (int i = sizeFirst+1; i < size; i++) {
                    points[i] = pathSecond.getPoints().get(j);
                    j++;
            }
        } else{
                for (int i = 2; i < sizeFirst+2; i++) {
                    points[i] = pathFirst.getPoints().get(i - 2);
            }
            int j = 0;
            for (int i = sizeFirst + 2; i < size; i++) {
                points[i] = pathSecond.getPoints().get(j);
                j++;
            }
        }
        return points;
    }

    /** Este método verifica se um segmento de reta interseta uma circunferencia, para o caso de segmentos
     * diagonais e horizontais.
     * @param srHit     SegmentoReta que vai ser verificada na interseção
     * @return          True se eles se intersetarem. False caso não se intersetem.
     */
    private boolean checkHit(SegmentoReta srHit){
        Reta rHit = new Reta(srHit.get_first(),srHit.get_second());
        double a = 1 + Math.pow(rHit.getM(),2);
        double b = 2 * -1 * this.center.get_x() + ((rHit.getB() - this.center.get_y()) * 2 * rHit.getM());
        double c = Math.pow(this.center.get_x(), 2) + Math.pow(rHit.getB() - this.center.get_y(), 2) - Math.pow(this.radius,2);
        double raiz = Math.pow(b,2) - (4*a*c);
        if(raiz < 0)
            return false;
        double x1 = (-b - Math.sqrt(raiz))/(2*a);
        double x2 = (-b + Math.sqrt(raiz))/(2*a);
        if((x1 >= Math.min(srHit.get_first().get_x(),srHit.get_second().get_x()) && x1 <= Math.max(srHit.get_first().get_x(),srHit.get_second().get_x()))|| x2 >= Math.min(srHit.get_first().get_x(),srHit.get_second().get_x()) && x2 <= Math.max(srHit.get_first().get_x(),srHit.get_second().get_x()))
            return true;
        else
            return false;
    }

    /** Este método verifica se um segmento de reta interseta uma circunferencia, para o caso de segmentos
     * verticais.
     * @param srHit     SegmentoReta que vai ser verificada na interseção
     * @return          True se eles se intersetarem. False caso não se intersetem.
     */
    private boolean checkHitVertical(SegmentoReta srHit){
        double x = srHit.get_first().get_x();
        double a = 1;
        double b = -2 * this.center.get_y();
        double c = Math.pow(x,2) + Math.pow(this.center.get_x(),2) - (2 * x * this.center.get_x()) + Math.pow(this.center.get_y(),2) - Math.pow(this.radius,2);
        double raiz = Math.pow(b,2) - (4*a*c);
        if(raiz < 0)
            return false;
        double y1 = (-b - Math.sqrt(raiz))/(2*a);
        double y2 = (-b + Math.sqrt(raiz))/(2*a);
        if((y1 >= Math.min(srHit.get_first().get_y(),srHit.get_second().get_y()) && y1 <= Math.max(srHit.get_first().get_y(),srHit.get_second().get_y()))|| y2 >= Math.min(srHit.get_first().get_y(),srHit.get_second().get_y()) && y2 <= Math.max(srHit.get_first().get_y(),srHit.get_second().get_y()))
            return true;
        else
            return false;
    }

    /** Este método dá print de fail caso não se intersetem e ok caso não se intersetem.
     * @param srHit     SegmentoReta que vai ser verificada na interseção
     */
    public String stringToPrint(SegmentoReta srHit){
        if(check_hit(srHit))
            return "Fail";
        else
            return "Ok";
    }

    /** Este construtor verifica se uma circunferência é válido caso esta não seja dará print
     * de "Circunferencia:vi" e o programa será encerrado
     */
    private void err(){
        if(this.radius < 0){
            System.out.println("Circunferencia:vi");
            System.exit(0);
        }
    }

    /**
     * Este método serve para verificar se o ponto p está dentro da figura geométrica.
     * @param p é o ponto dado para a verificacao
     * @return um booleano que retorna true caso o ponto esteja dentro da figura e falso caso contrário.
     */
    public boolean figVerification(Ponto p){
        if(Math.pow(p.get_x()-this.center.get_x(),2) +Math.pow(p.get_y()-this.center.get_y(),2) <= Math.pow(this.radius,2))
            return true;
        return false;
    }

    /**
     * Este método serve para mostrar a circunferencia nesta formatacao.
     * @return retorna uma string.
     */
    public String toString(){
        String print = "<Circunferencia: (";
        print += this.center.get_x() + ";" + this.center.get_y() + ") " + this.radius + ">";
        return print;
    }

    /**
     * Este método serve para retornar os pontos(num array de pontos) de intersecao de um segmento de reta com a circunferencia
     * através da ajuda dos métodos"getintersectionpointsOthers" e " getIntersectionPointsVertical".
     * @param hit é o segmento de reta usado.
     * @return um array de pontos que representam os pontos de intersecao.
     */

    private Ponto[] getIntersectionPoints(SegmentoReta hit){
        Ponto [] points = null;
        Reta rectHit = new Reta(hit.get_first(), hit.get_second());
        if (rectHit.getType() == 1)
            points = getIntersectionPointsVertical(hit);
        else
            points = getIntersectionPointsOther(hit);
        if(points[1].dist(hit.get_first()) < points[0].dist(hit.get_first())){
            Ponto aux = points[0];
            points[0] = points[1];
            points[1] = aux;
        }
        return points;
    }


    /**
     * Este método serve para retornar os pontos em que um segmeto de reta horizontal ou obliquo interseta uma reta.
     * @param srHit é o segmento de reta utilizado para testar(é horizontal ou obliquo).
     * @return retorna os pontos interceptados
     */
    private Ponto[] getIntersectionPointsOther(SegmentoReta srHit){
        Ponto [] points = new Ponto[2];
        Reta rHit = new Reta(srHit.get_first(),srHit.get_second());
        double a = 1 + Math.pow(rHit.getM(),2);
        double b = 2 * -1 * this.center.get_x() + ((rHit.getB() - this.center.get_y()) * 2 * rHit.getM());
        double c = Math.pow(this.center.get_x(), 2) + Math.pow(rHit.getB() - this.center.get_y(), 2) - Math.pow(this.radius,2);
        double raiz = Math.pow(b,2) - (4*a*c);
        double x1 = (-b - Math.sqrt(raiz))/(2*a);
        double x2 = (-b + Math.sqrt(raiz))/(2*a);
        Reta sr = new Reta(srHit.get_first(),srHit.get_second());
        double y1 = sr.getM() * x1 + sr.getB();
        double y2 = sr.getM() * x2 + sr.getB();
        points[0] = new Ponto(x1, y1);
        points[1] = new Ponto(x2, y2);
        return points;
    }



    /**
     * Este método serve para retornar os pontos em que um segmeto de reta vertical interseta uma reta.
     * @param srHit é o segmento de reta utilizado para testar(vertical).
     * @return  retorna os pontos interceptados
     */
    private Ponto[] getIntersectionPointsVertical(SegmentoReta srHit) {
        Ponto [] points = new Ponto[2];
        double x = srHit.get_first().get_x();
        double a = 1;
        double b = -2 * this.center.get_y();
        double c = Math.pow(x, 2) + Math.pow(this.center.get_x(), 2) - (2 * x * this.center.get_x()) + Math.pow(this.center.get_y(), 2) - Math.pow(this.radius, 2);
        double raiz = Math.pow(b, 2) - (4 * a * c);
        double y1 = (-b - Math.sqrt(raiz)) / (2 * a);
        double y2 = (-b + Math.sqrt(raiz)) / (2 * a);
        Reta sr = new Reta(srHit.get_first(),srHit.get_second());
        double x1 = (y1 - sr.getB()) / sr.getM();
        double x2 = (y2 - sr.getB()) / sr.getM();
        points[0] = new Ponto(x1, y1);
        points[1] = new Ponto(x2, y2);
        return points;
    }


    /**
     * Serve para verificar em que "quadrante" da circunferencia o ponto está, ou seja se ele está no canto superior direito
     * ou esquerdo, ou canto infereior esquerdo.
     * @param point é o ponto usado para ver o quadrante.
     * @return retorna um numero entre 1 e 4.
     */
    private int checkQuadrant(Ponto point){
        if(point.get_x() >= this.center.get_x() && point.get_y() >= this.center.get_y())
            return 1;
        if(point.get_x() < this.center.get_x() && point.get_y() > this.center.get_y())
            return 2;
        if(point.get_x() <= this.center.get_x() && point.get_y() <= this.center.get_y())
            return 3;
        if(point.get_x() > this.center.get_x() && point.get_y() < this.center.get_y())
            return 4;
        return 0;
    }


    /**
     * Este método vai verificar se o segmento de reta bate na circunferencia, e caso isso seja verdade cria
     * novos pontos para a trajétoria adjacentes(próximo aos pontos de intersecao,mas sem tocar na circunferencia)
     * E vai voltar a fazer a verificacao , e vai adicionando pontos novos que contornam a circunferencia até o segmento
     * deixar de bater na mesma.
     * @param seg é o segmento de reta utilizado inicialmente que poderá ser modificado para não bater na circunferencia.
     * @param start é o ponto inicial.
     * @param end é o ponto de destino.
     * @return retorna um array de 2 dimensões que serve como estrutura base , de onde as trajétorias poderão passar
     * por exemplo o caminho principal divide-se em 2, e serve para mostrar como se comporta as 2 trajetórias novas.
     */
    private Ponto[][] checkPath(SegmentoReta seg, int start, int end){
        Ponto[][] points = new Ponto[2][5];
        Ponto[] pointsFirst = new Ponto[5];
        Ponto[] pointsSecond = new Ponto[6];
        double distance = this.radius / 10;
        Ponto up = new Ponto(this.center.get_x(), this.center.get_y() + radius + distance);
        Ponto right = new Ponto(this.center.get_x() + radius + distance, this.center.get_y());
        Ponto down = new Ponto(this.center.get_x(), this.center.get_y() - radius - distance);
        Ponto left = new Ponto(1,this.center.get_x() - radius - distance, this.center.get_y());
        if(start == 1 && end == 2){
            pointsFirst[0] = seg.get_first(); pointsFirst[1] = up; pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = right; pointsSecond[2] = down; pointsSecond[3] = left; pointsSecond[4] = seg.get_second();
        } else if(start == 2 && end == 1){
            pointsFirst[0] = seg.get_first(); pointsFirst[1] = up; pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = left; pointsSecond[2] = down; pointsSecond[3] = right; pointsSecond[4] = seg.get_second();
        }  else if(start == 2 && end == 3){
            pointsFirst[0] = seg.get_first(); pointsFirst[1] = left; pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = up; pointsSecond[2] = right; pointsSecond[3] = down; pointsSecond[4] = seg.get_second();
        } else if(start == 3 && end == 2){
            pointsFirst[0] = seg.get_first(); pointsFirst[1] = left; pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = down; pointsSecond[2] = right; pointsSecond[3] = up; pointsSecond[4] = seg.get_second();
        }  else if(start == 3 && end == 4){
            pointsFirst[0] = seg.get_first(); pointsFirst[1] = down; pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = left; pointsSecond[2] = up; pointsSecond[3] = right; pointsSecond[4] = seg.get_second();
        }  else if(start == 4 && end == 3){
            pointsFirst[0] = seg.get_first(); pointsFirst[1] = down; pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = right; pointsSecond[2] = up; pointsSecond[3] = left; pointsSecond[4] = seg.get_second();
        }else if(start == 4 && end == 1){
            pointsFirst[0] = seg.get_first(); pointsFirst[1] = right; pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = down; pointsSecond[2] = left; pointsSecond[3] = up; pointsSecond[4] = seg.get_second();
        } else if(start == 1 && end == 4){
            pointsFirst[0] = seg.get_first(); pointsFirst[1] = right; pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = up; pointsSecond[2] = left; pointsSecond[3] = down; pointsSecond[4] = seg.get_second();
        } else if(start == end && start == 1 && seg.get_first().get_y() < seg.get_second().get_y()){
            pointsFirst[0] = seg.get_first(); pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = right; pointsSecond[2] = down; pointsSecond[3] = left; pointsSecond[4] = up; pointsSecond[5] = seg.get_second();
        }else if(start == end && start == 1 && seg.get_first().get_y() > seg.get_second().get_y()){
            pointsFirst[0] = seg.get_first(); pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = up; pointsSecond[2] = left; pointsSecond[3] = down; pointsSecond[4] = right; pointsSecond[5] = seg.get_second();
        } else if(start == end && start == 2 && seg.get_first().get_y() < seg.get_second().get_y()){
            pointsFirst[0] = seg.get_first(); pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = left; pointsSecond[2] = down; pointsSecond[3] = right; pointsSecond[4] = up; pointsSecond[5] = seg.get_second();
        }else if(start == end && start == 2 && seg.get_first().get_y() > seg.get_second().get_y()){
            pointsFirst[0] = seg.get_first(); pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = up; pointsSecond[2] = right; pointsSecond[3] = down; pointsSecond[4] = left; pointsSecond[5] = seg.get_second();
        } else if(start == end && start == 3 && seg.get_first().get_y() < seg.get_second().get_y()){
            pointsFirst[0] = seg.get_first(); pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = down; pointsSecond[2] = right; pointsSecond[3] = up; pointsSecond[4] = left; pointsSecond[5] = seg.get_second();
        } else if(start == end && start == 3 && seg.get_first().get_y() > seg.get_second().get_y()){
            pointsFirst[0] = seg.get_first(); pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = left; pointsSecond[2] = up; pointsSecond[3] = right; pointsSecond[4] = down; pointsSecond[5] = seg.get_second();
        } else if(start == end && start == 4 && seg.get_first().get_y() < seg.get_second().get_y()){
            pointsFirst[0] = seg.get_first(); pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = down; pointsSecond[2] = left; pointsSecond[3] = up; pointsSecond[4] = right; pointsSecond[5] = seg.get_second();
        } else if(start == end && start == 4 && seg.get_first().get_y() > seg.get_second().get_y()){
            pointsFirst[0] = seg.get_first(); pointsFirst[2] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = right; pointsSecond[2] = up; pointsSecond[3] = left; pointsSecond[4] = down; pointsSecond[5] = seg.get_second();
        } else if(start == 3 && end == 1){
            pointsFirst[0] = seg.get_first(); pointsFirst[1] = down; pointsFirst[2] = right; pointsFirst[3] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = left; pointsSecond[2] = up; pointsSecond[3] = seg.get_second();
        } else if(start == 1 && end == 3){
            pointsFirst[0] = seg.get_first(); pointsFirst[1] = right; pointsFirst[2] = down; pointsFirst[3] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = up; pointsSecond[2] = left; pointsSecond[3] = seg.get_second();
        } else if(start == 2 && end == 4){
            pointsFirst[0] = seg.get_first(); pointsFirst[1] = left; pointsFirst[2] = down; pointsFirst[3] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = up; pointsSecond[2] = right; pointsSecond[3] = seg.get_second();
        } else if(start == 4 && end == 2){
            pointsFirst[0] = seg.get_first(); pointsFirst[1] = right; pointsFirst[2] = up; pointsFirst[3] = seg.get_second();
            pointsSecond[0] = seg.get_first(); pointsSecond[1] = down; pointsSecond[2] = left; pointsSecond[3] = seg.get_second();
        }
        points[0] = pointsFirst;
        points[1] = pointsSecond;
        return points;
    }


    /**
     * Este método serve para criar uma trajétoria , com um array de pontos
     * @param pathPoints é o array de pontos dados.
     * @return retorna uma trajétoria.
     */
    private Trajetoria createPath(Ponto[] pathPoints){
        Trajetoria result = null;
        for(int i = 1; i < pathPoints.length ; i++){
            if(pathPoints[i] == null)
                return result;
            Trajetoria path = new Trajetoria();
            path.addOnePoint(pathPoints[i-1]);
            if(path.checkPathVI(pathPoints[i]))
                return null;
            path.addOnePoint(pathPoints[i]);
            path.pathSegs();
            this.makePath(path);
            if(result == null)
                result = path;
            else
                result.concatPaths(path);
        }
        return result;
    }

    /**
     * Este método void ,serve para criar uma trajetória ,com todos os pontos novos criados a volta da circunferencia de
     * forma a  conseguir não bater na circunferencia.
     * @param path é a trajetoria dada.
     */
    private void makePath(Trajetoria path){
        while(check_hit(path.getSegs().get(path.getSegs().size()-1))){
            SegmentoReta seg = path.getSegs().get(path.getSegs().size()-1);
            Ponto[] pointsIntersection = this.getIntersectionPoints(seg);
            Ponto intersection;
            if(pointsIntersection[0].dist(seg.get_first()) < pointsIntersection[1].dist(seg.get_first()))
                intersection = pointsIntersection[0];
            else
                intersection = pointsIntersection[1];
            int quadrant = this.checkQuadrant(intersection);
            int x = this.xValues(quadrant);
            path.getPoints().add(path.getPoints().size()-1,new Ponto(1,intersection.get_x() + (x * (this.radius / 10)), intersection.get_y()));
            path.pathSegs();
        }
    }

    /**
     * Este método serve para ver se o x é positivo no 1 ou 4 quadrante ou vice-versa.
     * @param quadrant é um numero entre 1 e 4.
     * @return retorna um -1 ou 1.
     */
    private int xValues(int quadrant){
        if(quadrant == 1 || quadrant == 4)
            return 1;
        else
            return -1;
    }

    /**
     * Este método serve para dizer que a circunferencia é presentada pelo numero 2 ou seja o seu tipo é o 2.
     * @return 2.
     */
    public int returType(){
        return 2;
    }

    public double getRadius() {
        return radius;
    }

    public Ponto getCenter() {
        return center;
    }
}
