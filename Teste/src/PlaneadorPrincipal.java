import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Grupo7
 * @version correto-17 Amazon correto version 17.0.4 07/05/2023
 * */
public class PlaneadorPrincipal {
    private Generation[] generation = new Generation[1];
    private ArrayList<FiguraGeometrica> figs = new ArrayList<>();
    private Trajetoria bestDistance;

    /** Este construtor irá criar um Planeador que irá fazer diversas iterações até chegar à melhor trajetória
     * do Robot
     *
     * @param start Ponto inicial da trajetória
     * @param end   Ponto final da trajetória
     * @param figs  ArrayList de Obstaculos
     */
    public PlaneadorPrincipal(Ponto start, Ponto end, ArrayList<FiguraGeometrica> figs){
        Trajetoria path = new Trajetoria();
        path.addOnePoint(start);
        path.addOnePoint(end);
        Populacao pop = new Populacao();
        pop.addOnePath(path);
        this.figs = figs;
        this.generation[0] = new Generation(pop);
        firstGeneration();
        newGeneration();
    }


    //Enquanto na última geração houver pelo menos uma trajetoria a intersetar obstáculo, vai ser criada uma nova geração

    /** Este método vai criar constantemente novas gerações até que a última esteja vazia, ou que todas as suas trajetórais
     * cheguem ao Ponto final sem intersetar nenhum obstáculo, este método também verifica entre as trajetórias disponiveis
     * se são melhores que o "bestDistance" e caso sejam atualiza o "bestDistance"
     */
    private void newGeneration(){
        while (!this.generation[getNewGenerationIndex()].checkAllPathAvailable() && (getNewGenerationIndex() < 50)) {
            increamentGenerationSize();
            this.generation[getNewGenerationIndex()] = new Generation(this.generation[getNewGenerationIndex() - 1].getPop());
            for (Trajetoria path : this.generation[getNewGenerationIndex()].pop.getTrajetorias()) {
                if (this.generation[getNewGenerationIndex()].getAvailablePath().get(path)) {
                    if (this.bestDistance == null)
                        this.bestDistance = path;
                    else if (bestDistance.getTotalDist() > path.getTotalDist())
                        this.bestDistance = path;
                }
            }
            this.generation[getNewGenerationIndex()].pop.clearPop();
        }
    }

    /** Este método é usado para criar a primeira geração, caso esta já tenha uma trajetória "possível" esta será inserida na
     * variável "bestDistance"
     *
     */
    private void firstGeneration(){
        for(Trajetoria path : this.generation[0].pop.getTrajetorias()){
            if(this.generation[0].getAvailablePath().get(path)) {
                if (this.bestDistance == null)
                    this.bestDistance = path;
                else if (bestDistance.getTotalDist() > path.getTotalDist())
                    this.bestDistance = path;
            }
        }
    }

    /** Este método retorna a melhor trajetória do planeador
     *
     * @return      Trajetoria
     */
    public Trajetoria getBestDistance() {
        return bestDistance;
    }

    /** Este método retorna o index da geração mais recente
     *
     * @return      int que representa o index
     */
    private int getNewGenerationIndex(){
        return this.generation.length - 1;
    }


    /** Este método copia os dados do "this.generation" para um Array de gerações auxiliar e por fim incrementa
     * o tamanho do "this.generation" e volta a inserir os dados que ele tinha
     *
     */
    private void increamentGenerationSize(){
        Generation[] aux = this.generation;
        this.generation = new Generation[this.generation.length+1];
        for(int i = 0; i < aux.length; i++)
            this.generation[i] = aux[i];
    }


    /** Este método retorna uma String com a informação de todas as gerações do Planeador
     *
     * @return  String
     */
    public String toString(){
        int i = 0;
        String print = "";
        String stat = "";
        for(Generation generation : this.generation){
            i++;
            print += "Geracao " + i + ":\n";
            for(Trajetoria path : generation.getPop().getTrajetorias()){
                if(generation.getAvailablePath().get(path))
                    stat = "true";
                else
                    stat = "false";
                print += path.toString2() + "        " + stat + "\n";
            }
        }
        return print;
    }

    public class Generation{
        private Populacao pop = new Populacao();
        private HashMap<Trajetoria, Boolean> availablePath = new HashMap<>();

        /** Este construtor vai criar uma Geração, através de uma População (da geração anterior), a geração é essenciamente
         * uma População com a diferença que para cada uma das suas trajetórias terá um valor de true ou false, guardado numa
         * HashMap que representa se a trajetória é "bem sucedida", ou não
         *
         * @param pop
         */
        public Generation(Populacao pop){
            this.pop = createNewGeneration(pop);
            checkPathAvailable();
        }

        /** Este método irá ver cada trajetória da geração e irá ver se ela é "bem sucedida", ou não e irá guardar esse resposta
         * com um boleano na HashMap, True caso sim e False caso não
         *
         */
        private void checkPathAvailable(){
            for(Trajetoria path : this.pop.getTrajetorias()){
                if (path.checkHits(PlaneadorPrincipal.this.figs) == 0)
                    this.availablePath.put(path,true);
                else
                    this.availablePath.put(path,false);
            }
        }

        /** Este método verifica se todos as trajetórias não intersetam obstáculos
         *
         * @return True caso não interesetem. False caso haja pelo menos uma que intersete
         */
        private boolean checkAllPathAvailable(){
            for(Trajetoria path : this.pop.getTrajetorias()){
                if (path.checkHits(PlaneadorPrincipal.this.figs) > 0)
                    return false;
            }
            return true;
        }


        /** Este método retorna a população da geração
         *
         * @return População
         */
        public Populacao getPop() {
            return pop;
        }

        /** Este método criar uma nova geração
         *
         * @param pop população da geração anterio
         * @return população da nova geração
         */
        public Populacao createNewGeneration(Populacao pop){
            Populacao newPop = new Populacao();
            SegmentoReta seg;
            Reta rect;
            Trajetoria first;
            Trajetoria second;
            Trajetoria[] paths;
            for(Trajetoria path : pop.getTrajetorias()){
                paths = path.checkFirstHit(PlaneadorPrincipal.this.figs);
                if(paths != null) {
                    if (paths[0] != null && (bestDistance == null || bestDistance.getTotalDist() > paths[0].getTotalDist())) {
                        newPop.addOnePath(paths[0]);
                    }
                    if (paths[1] != null && (bestDistance == null || bestDistance.getTotalDist() > paths[1].getTotalDist())) {
                        newPop.addOnePath(paths[1]);
                    }
                } else if(path.checkHits(PlaneadorPrincipal.this.figs) == 0)
                    newPop.addOnePath(path);
            }
            return newPop;
        }

        /** Este método retorna a HashMap com a informação se a trajetória interesta ou não obstáculos
         *
         * @return Key - Trajetória  . Value - boolean, True se não interseta, False caso contrário
         */
        public HashMap<Trajetoria, Boolean> getAvailablePath() {
            return availablePath;
        }
    }
}
