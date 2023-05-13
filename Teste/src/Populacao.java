import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 * @author Alexandre Carvalho
 * @version correto-17 Amazon correto version 17.0.4 23/02/2023
 * */
public class Populacao {
    public ArrayList<Trajetoria> trajetorias = new ArrayList<Trajetoria>();

    /** Este método adiciona uma Trajetória aleatória à População
     * @param in                Scanner para ler o número de Pontos que a trajetória vai ter
     * @param randomGenerator   Random para gerar os Pontos aleatórios
     */
    public void addOnePath(Scanner in, Random randomGenerator){
        Trajetoria pt = new Trajetoria();
        pt.addMultiPoints(randomGenerator, in.nextInt());
        trajetorias.add(pt);
    }

    /** Método que adiciona uma Trajetória à Populção
     *
     * @param pt Trajetória
     */

    public void addOnePath(Trajetoria pt){
        trajetorias.add(pt);
    }

    /** Este método adiciona várias Trajetórias aleatórias à População
     *
     * @param in                Scanner para ler o número de Trajetórias
     * @param randomGenerator   Random para gerar os Pontos aleatórios
     */
    public void addMultiPath(Scanner in, Random randomGenerator){
        int n = in.nextInt();
        while (n > 0) {
            Trajetoria pt = new Trajetoria();
            pt.addMultiPoints(randomGenerator, in.nextInt());
            trajetorias.add(pt);
            n--;
        }
    }

    /** Este método adiciona vários Trajetórias, com um Ponto inicial e final dado os restantes são aleatórias, à População
     *
     * @param in                Scanner para ler o número de Trajetórias
     * @param randomGenerator   Random para gerar os Pontos aleatórios
     * @param start             Ponto inicial para todas as trajetórias aleatórias geradas
     * @param end               Ponto final para todas as trajetórias aleatórias geradas
     */
    public void addMultiPath(Scanner in, Random randomGenerator, Ponto start, Ponto end){
        int n = in.nextInt();
        while (n > 0) {
            Trajetoria pt = new Trajetoria();
            pt.addMultiPoints(randomGenerator, in.nextInt(), start, end);
            trajetorias.add(pt);
            n--;
        }
    }

    /** Método que adiciona várias trajetórias à população
     *
     * @param numberPaths       inteiro qeu representa o número de trajetórias a serem adicionadas
     * @param randomGenerator   Random utilizado para gerar as trajetórais
     * @param start             Ponto inicial de todas as trajetórias
     * @param end               Ponto final de todas as trajetórias
     * @param figs              ArrayList de FigurasGeometricas que representa os obstáculos
     */
    public void addMultiPath(int numberPaths, Random randomGenerator, Ponto start, Ponto end, ArrayList<FiguraGeometrica> figs){
        while (numberPaths > 0) {
            Trajetoria path = new Trajetoria();
            path.addMultiPoints(randomGenerator, start, end, figs);
            trajetorias.add(path);
            numberPaths--;
        }
    }


    /** Este método adiciona uma Trajetória aleatória à População
     * @param in                Inteiro que representa o número de Pontos que a trajetória vai ter
     * @param randomGenerator   Random para gerar os Pontos aleatórios
     */
    public void addOnePath(int in, Random randomGenerator){
        Trajetoria pt = new Trajetoria();
        pt.addMultiPoints(randomGenerator, in);
        trajetorias.add(pt);
    }

    /** Método que adiciona uma Trajetória à População
     *
     * @param in Scanner que irá ler a trajetória
     */
    public void addOnePath(Scanner in){
        Trajetoria pt = new Trajetoria();
        pt.addMultiPoints(in);
        trajetorias.add(pt);
    }


    /** Este método retorna uma String que representa todas as trajetórias presentes na População, com a formatação: [(x1; y1) (x2;y2) (x3; y3]
     *
     * @return      String que representa todas as trajetórias
     */
    public String toString(){
        String print = "";
        for(int i = 0; i < trajetorias.size(); i++){
            print += "[";
            for(int j = 0; j < trajetorias.get(i).getPoints().size(); j++){
                if(j+1 == trajetorias.get(i).getPoints().size())
                    print += "(" + (int) trajetorias.get(i).getPoints().get(j).get_x() + ";" + + (int) trajetorias.get(i).getPoints().get(j).get_y()+ ")";
                else
                    print += "(" + (int) trajetorias.get(i).getPoints().get(j).get_x() + ";" + + (int) trajetorias.get(i).getPoints().get(j).get_y()+ ") ";
            }
            if(i+1 != trajetorias.size())
                print += "]\n";
            else
                print += "]";
        }
        return print;
    }

    /** Este método retorna uma String que representa uma a trajetória presentes na População, com a formatação: [(x1; y1) (x2;y2) (x3; y3]
     *
     * @return      String que representa a trajetória
     */
    public String toStringOnePath(int i){
        String print = "[";
        for(int j = 0; j < trajetorias.get(i).getPoints().size(); j++){
            if(j+1 == trajetorias.get(i).getPoints().size())
                print += "(" + (int) trajetorias.get(i).getPoints().get(j).get_x() + ";" + + (int) trajetorias.get(i).getPoints().get(j).get_y()+ ")";
            else
                print += "(" + (int) trajetorias.get(i).getPoints().get(j).get_x() + ";" + + (int) trajetorias.get(i).getPoints().get(j).get_y()+ ") ";
        }
        print += "]";
        return print;
    }


    /** Este método vai receber um Random gerador de números aleatórios e uma ArrayList de FigurasGeométricas que
     * representam os obstáculos. Depois vai fazer n competições, onde n representa o número de trajetórias da
     * população. O torneio consiste em pegar em duas trajetórias e das duas dar print da que tem uma melhor avaliação.
     *
     * @param randomGenerator       Random generator
     * @param figs                  ArrayList de FigurasGeométricas
     * @return                      String - que vai conter o resultado dos n torneios realizados
     */
    public void pathsTournment(Random randomGenerator, ArrayList<FiguraGeometrica> figs){
        Populacao newPop = new Populacao();
        int n = trajetorias.size();
        int limit = n;
        int first = 0;
        int second = 0;
        Trajetoria winner = new Trajetoria();
        while(n > 0){
            first = randomGenerator.nextInt(limit);
            second = randomGenerator.nextInt(limit);
            winner = this.trajetorias.get(first).bestPath(figs,this.trajetorias.get(second));
            newPop.addOnePath(winner);
            n--;
        }
        this.trajetorias = newPop.trajetorias;
    }

    /** Este método recebe dois índices e irá misturar esses dois índice da nossa População
     *
     * @param randomGenerator       Random que gera números aleatórios
     * @param first                 Representa o índice da primeira trajetória a ser alterada
     * @param second                Representa o índice da segunda trajetória a ser alterada
     */
    public void mixPaths(Random randomGenerator, int first, int second){
        Trajetoria[] trajetorias = new Trajetoria[2];
        trajetorias = this.trajetorias.get(first).mixer(randomGenerator, this.trajetorias.get(second));
        this.trajetorias.set(first, trajetorias[0]);
        this.trajetorias.set(second, trajetorias[1]);
    }

    /** Este método vai percorrer a população inteira, percorrendo a de 2 em 2. Escolhendo aleatóriamente duas trajetórias, e fazendo um mix nelas
     * isto é criar duas novas trajétorias, que representam a mistura das anteriores. Por fim é substituída a nossa População pela nova gerada através das misturas
     *
     * @param randomGenerator
     */
    public void mixPaths(Random randomGenerator){
        if(this.trajetorias.isEmpty())
            return;
        Populacao pop = new Populacao();
        Trajetoria[] trajetorias = new Trajetoria[2];
        int first;
        int second;
            for (int i = 0; i < this.trajetorias.size(); i += 2) {
                first = randomGenerator.nextInt(this.trajetorias.size());
                second = randomGenerator.nextInt(this.trajetorias.size());
                trajetorias = this.trajetorias.get(first).mixer(randomGenerator, this.trajetorias.get(second));
                if (i + 1 == this.trajetorias.size())
                    pop.addOnePath(trajetorias[0]);
                else {
                    pop.addOnePath(trajetorias[0]);
                    pop.addOnePath(trajetorias[1]);
                }
            }
            this.trajetorias = pop.trajetorias;
    }

    /**     Este método recebe a chance da População sofrer uma mutação, e este será executado para todos as trajetórias
     *      da população
     *
     * @param randomGenerator       Random que gera números aleatórios
     * @param odds                  Double que representa a chance da mutação ocorrer
     */
    public void mutation(Random randomGenerator, double odds) {
        for(int i = 0; i < this.trajetorias.size(); i++) {
            this.trajetorias.set(i, this.trajetorias.get(i).mutation(randomGenerator, odds));
        }
    }

    /**     Este método recebe a chance da População sofrer uma mutação de adição, e este será executado para todos
     * as trajetórias da população
     *
     * @param randomGenerator       Random que gera números aleatórios
     * @param odds                  Double que representa a chance da mutação ocorrer
     */
    public void mutationAdd(Random randomGenerator, double odds) {
        for(int i = 0; i < this.trajetorias.size(); i++) {
            this.trajetorias.set(i, this.trajetorias.get(i).mutationAdd(randomGenerator, odds));
        }
    }

    /**     Este método recebe a chance da População sofrer uma mutação de remoção, e este será executado para todos
     * as trajetórias da população
     *
     * @param randomGenerator       Random que gera números aleatórios
     * @param odds                  Double que representa a chance da mutação ocorrer
     */
    public void mutationRemove(Random randomGenerator, double odds) {
        for(int i = 0; i < this.trajetorias.size(); i++) {
            this.trajetorias.set(i, this.trajetorias.get(i).mutationRemove(randomGenerator, odds));
        }
    }


    /**  Este método retorna a avaliação da melhor trajetória da população
     *
     * @param figs      ArrayList de FigurasGeométricas que representa os obstáculos
     * @return  Double que representa a melhor avaliação
     */
    public double highestEvaluetion(ArrayList<FiguraGeometrica> figs){
        double value = 0;
        for(int i = 0; i < this.trajetorias.size(); i++) {
            if (this.trajetorias.get(i).getPathValuation(figs) > value)
                value = this.trajetorias.get(i).getPathValuation(figs);
        }
        return value;
    }

    /**  Este método retorna a média da avalição de todas as trajetórias da população
     *
     * @param figs      ArrayList de FigurasGeométricas que representa os obstáculos
     * @return      Double que representa a média da avalição de todas as trajetórias da população
     */
    public double averageEvaluetion(ArrayList<FiguraGeometrica> figs){
        double value = 0;
        for(int i = 0; i < this.trajetorias.size(); i++)
                value += this.trajetorias.get(i).getPathValuation(figs);
        value = value / this.trajetorias.size();
        return value;
    }

    /**  Este método retorna a avaliação da pior trajetória da população
     *
     * @param figs      ArrayList de FigurasGeométricas que representa os obstáculos
     * @return  Double que representa a pior avaliação
     */
    public double lowestEvaluation(ArrayList<FiguraGeometrica> figs){
        double value = 1;
        for(int i = 0; i < this.trajetorias.size(); i++) {
            if (this.trajetorias.get(i).getPathValuation(figs) < value)
                value = this.trajetorias.get(i).getPathValuation(figs);
        }
        return value;
    }

    /**     Este método retorna a trajetória mais curta e número de obstáculos que intersetou
     *
     * @param figs          ArrayList de FigurasGeométricas que representa os obstáculos
     * @return      ArrayDouble que representa a trajetória mais curta e número de obstáculos que intersetou
     */
    public double[] shortestPathStats(ArrayList<FiguraGeometrica> figs){
        double[] values = new double[2];
        double value = this.trajetorias.get(0).checkHits(figs);
        int index = 0;
        for(int i = 0; i < this.trajetorias.size(); i++) {
            if (this.trajetorias.get(i).checkHits(figs) < value) {
                value = this.trajetorias.get(i).checkHits(figs);
                index = i;
            }
        }
        values[0] = this.trajetorias.get(index).getTotalDist();
        values[1] = value;
        return values;
    }

    /** Este método retorna a trajetória mais curta que não intersete obstáculos da população
     *
     * @param figs  ArrayList com os obstáculos
     * @return     Melhor trajetória
     */
    public Trajetoria shortestPath(ArrayList<FiguraGeometrica> figs){

        Trajetoria shortest = new Trajetoria();
        for(Trajetoria aux : this.trajetorias){
            if(aux.checkHits(figs) == 0) {
                shortest = aux;
                break;
            }
        }

        for(int i = 0; i < this.trajetorias.size(); i++) {
            if ((this.trajetorias.get(i).checkHits(figs) == 0) && this.trajetorias.get(i).getTotalDist() < shortest.getTotalDist()) {
                shortest = this.trajetorias.get(i);
            }
        }
        return shortest;
    }

    /** Este método retorna a distância total de cada trajetória da população em formato de String
     *
     * @return      String com a formatação da distância de todas as trajetórias da população
     */
    public String getAllDistastance(){
        String print = "";
        for(int i = 0; i < this.trajetorias.size(); i++){
            print += "index " + i + ": " + this.trajetorias.get(i).getTotalDist() + "\n";
        }
        return print;
    }

    /** Este método retorna o número de vezes que cada trajetória da população intersetou um obstáculo em formato de String
     *
     * @return      String com a formatação do número de vezes que cada trajetória da população intersetou um obstáculo
     */
    public String getAllHits(ArrayList<FiguraGeometrica> figs){
        String print = "";
        for(int i = 0; i < this.trajetorias.size(); i++){
            print += "index " + i + ": " + this.trajetorias.get(i).checkHits(figs) + "\n";
        }
        return print;
    }

    /** Método criado para resolver alguns Problemas dos PlaneadorRandoms, tais como serem geradas muitas trajetórias que nem sequer
     * estejam perto de chegar à trajetória ideal, sendo elas subsituídas por uma trajetória que já seja bem sucedida, caso esta exista
     *
     * @param randomGenerator       Random utilizado para gerar os pontos
     * @param figs                  ArrayList de obstáculos
     * @param bestPath              Trajetória bem sucedidad (caso exista), se não é null
     */
    public void fixPopulation(Random randomGenerator, ArrayList<FiguraGeometrica> figs, Trajetoria bestPath){

        if(bestPath.getPoints().size() > 0) {
            for(int i = 0; i < this.trajetorias.size(); i++){
                if(this.trajetorias.get(i).checkHits(figs) > 0){
                    this.trajetorias.set(i, bestPath);
                }
            }
        } else{
            int number = 0;
            Ponto start = this.trajetorias.get(0).getPoints().get(0);
            Ponto end = this.trajetorias.get(0).getPoints().get(this.trajetorias.get(0).getPoints().size()-1);
            for(int i = 0; i < this.trajetorias.size(); i++){
                if(this.trajetorias.get(i).checkHits(figs) > 0){
                    number++;
                    this.trajetorias.remove(i);
                    i++;
                }
            }
            this.addMultiPath(number,randomGenerator, start, end, figs);
        }

         }

    /** Este método retorna as trajetórias da população
     *
     * @return  ArrayList de trajetórias
     */

    public ArrayList<Trajetoria> getTrajetorias() {
        return trajetorias;
    }

    /** Este método apaga trajetórias repetidas da população
     *
     */
    public void clearPop(){
        for(int i = 0; i < this.trajetorias.size(); i++){
            for(int j = i+1; j < this.trajetorias.size()-1;j++){
                if(this.trajetorias.get(i).is(this.trajetorias.get(j))){
                    this.trajetorias.remove(i);
                    this.trajetorias.remove(j);
                    i++;
                    j = i+1;
                }
            }
        }
    }
}
