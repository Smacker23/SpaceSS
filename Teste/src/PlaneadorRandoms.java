import java.util.ArrayList;
import java.util.Random;

/**
 * @author Alexandre Carvalho
 * @version correto-17 Amazon correto version 17.0.4 23/02/2023
 * */
public class PlaneadorRandoms {
    private Populacao pop = new Populacao();
    private ArrayList<FiguraGeometrica> figs = new ArrayList<FiguraGeometrica>();
    private Double[][] values = new Double[0][5];
    private Trajetoria[] paths = new Trajetoria[0];
    private Trajetoria bestPath = new Trajetoria();

    /**  Construto que inicializa um Planeado, já com a população e obstáculos conhecidos
     *
     * @param pop           População que contém todas as trajetórias que irão ser testadas
     * @param figs          ArrayList FigurasGerométricas que contém os obstáculos para o planeador
     */
    public PlaneadorRandoms(Populacao pop, ArrayList<FiguraGeometrica> figs){
        this.pop = pop;
        this.figs = figs;
    }


    /**     Este método irá iterar o PlaneadorRandoms uma vez, isto consiste em fazer diversas mutações na nossa trajetória, neste caso irão ser feitas:
     *      Tourneios, misturas, mutações, adições e remoções. Com o fim de melhorar a nossa população, por fim irá ser analisada a nossa população após a iteração
     *
     * @param randomGenerator       Random gerador que irá dar os valores, para os casos de ser necerssário números aleatórios
     * @param oddMutation           Double que representa a chance de poder haver uma mutação
     * @param oddAdd                Double que representa a chance de poder haver uma mutação de adição
     * @param oddRemove             Double que representa a chance de poder haver uma mutação de remoçção
     */
    public void interection(Random randomGenerator, double oddMutation, double oddAdd, double oddRemove, int i){
        this.pop.fixPopulation(randomGenerator, this.figs, this.bestPath);
        this.pop.pathsTournment(randomGenerator,this.figs);
        this.pop.mixPaths(randomGenerator);
        this.pop.mutation(randomGenerator,oddMutation);
        this.pop.mutationAdd(randomGenerator,oddAdd);
        this.pop.mutationRemove(randomGenerator,oddRemove);
        stats();
        Populacao pop = new Populacao();
        pop.trajetorias = this.pop.trajetorias;
    }

    /** Este método irá analisar a população após uma iteração, isto é: Analisar a melhor avaliação de uma trajetória, a pior, a média, o caminho mais curto e número de obstáculos que intersetou
     *
     */
    public void stats(){
        createNewArray();
        double[] values = this.pop.shortestPathStats(this.figs);
        this.values[this.values.length-1][0] = this.pop.highestEvaluetion(this.figs);
        this.values[this.values.length-1][1] = this.pop.averageEvaluetion(this.figs);
        this.values[this.values.length-1][2] = this.pop.highestEvaluetion(this.figs);
        this.values[this.values.length-1][3] = values[0];
        this.values[this.values.length-1][4] = values[1];
    }

    /** Este método é necessário para se conseguir guardar os dados das nossas análises, a cada iteração é expandido 1 ao nosso array
     *
     */
    private void createNewArray(){
        Double[][] aux = new Double[this.values.length+1][5];
        Trajetoria[] auxPath = new Trajetoria[this.paths.length+1];
        for(int i = 0; i < this.values.length; i++){
            auxPath[i] = this.paths[i];
            for(int j = 0; j < this.values[i].length; j++)
                aux[i][j] = this.values[i][j];
        }
        this.values = aux;
        this.paths = auxPath;
    }

    /** Este método irá fazer o número de iterações que se queira ao planeador
     *
     * @param n         int que representa o número de iterações
     * @param randomGenerator       Random gerador que irá dar os valores, para os casos de ser necerssário números aleatórios
     * @param oddMutation           Double que representa a chance de poder haver uma mutação
     * @param oddAdd                Double que representa a chance de poder haver uma mutação de adição
     * @param oddRemove             Double que representa a chance de poder haver uma mutação de remoçção
     */
    public void multiInterection(int n, Random randomGenerator, double oddMutation, double oddAdd, double oddRemove){
        for(int i = 0; i < n; i++){
            interection(randomGenerator, oddMutation, oddAdd, oddRemove, i);
        }
    }

    /** Este método retorna a melhor trajetória do planeador
     *
     * @return Trajetoria
     */
    public Trajetoria bestPath(){
        double dist = Double.MAX_VALUE;
        int index = 0;
        for(int i = 0; i < this.pop.getTrajetorias().size(); i++){
            if(this.values[i][4] == 0 && this.values[i][3] < dist) {
                dist = this.values[i][3];
                index = i;
            }
        }
        //System.out.println(dist);
        return this.paths[index];
    }




    /**  Este método retornará os dados do PlaneadorRandoms
     *
     * @return  String com a formatação desejada para o planeador
     */
    public String toString(){
        String print = "";
        for(int i = 0; i < this.values.length; i++){
            double last =  this.values[i][4];
            print += i + ": " + String.format("%.2f", this.values[i][0])  + " " + String.format("%.2f", this.values[i][1]) + " " + String.format("%.2f", this.values[i][2]) + " " + String.format("%.2f", this.values[i][3]) + " " +  (int) last;
            if(i + 1 < this.values.length)
                print += "\n";
        }
        return print;
    }

}
