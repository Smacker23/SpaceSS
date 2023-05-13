import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Grupo7
 * @version correto-17 Amazon correto version 17.0.4 07/05/2023
 * */
public class Robot {
    private  double STOP_WASTED_BATTERY = 0.0005;
    private  double MOVE_WASTED_BATTERY = 0.005;
    private  double CHARGING_BATTERY = 0.8;
    private  double DISTANCE = 10;
    //42 = * Significa Com Peso    45 = - Sem Peso  43 = + a carregar
    private final char[] stats = {45, 42, 43};
    private Ponto chargeLocation;
    private Ponto location;
    private double battery;
    private char stat = stats[2];
    private Ponto nextPoint;
    private HashMap<Pacote, TrajetoriasRobot> paths = new HashMap<>();
    private TrajetoriasRobot path;



    /**
     * Este método é construtor do robot
     * @param location é a localização inicial em que o robot dá spawn, e consiste com o ponto de carga.
     */
    public Robot(Ponto location) {
        this.chargeLocation = location;
        this.location = location;
        this.battery = 100;
    }


    /** Este método é usado restritamente para UnitTests, assim caso pertendamos alterar os valores das variáveis de bateria,
     * os unitTests continuaram a funcionar
     *
     * @param stoped        double valor de bateria perdida enquanto parado
     * @param moving        double valor de bateria perdida enquanto em movimento
     * @param charging      double valor de bateria ganha enquanto carrega
     * @param distance      double valor de distancia percorrida pelo robot a cada ciclo
     */
    public void changeValues(double stoped, double moving, double charging, double distance){
        this.STOP_WASTED_BATTERY = stoped;
        this.MOVE_WASTED_BATTERY = moving;
        this.CHARGING_BATTERY = charging;
        this.DISTANCE = distance;
    }


    /**
     * Este método serve para ir buscar o ponto de carga do robot.
     * @return retorna o ponto de carga do robot.
     */
    public Ponto getChargeLocation(){
        return this.chargeLocation;
    }


    /**
     * Este método serve para ir buscar o stat do robot(-,+,*)
     * o "-" quer dizer que o robot está parado
     * o "+" quer dizer que o robot está a carregar.
     * o "*" quer dizer que o robtot está em movimento.
     * @return o stat do robot, consoante o seu estado(vai buscar o valor num array).
     */

    public char getStat() {
        return stat;
    }

    /**
     * Este método vai retornar a bateria atual do robot.
     * @return a bateria atual do robot.
     */
    public double getBattery(){
        return this.battery;
    }


    /**
     * Este método retorna uma TrajetoriasRobot, isto é a sua trajetória e a sua trajetória safe (a que terá de realizar caso entre em estado
     * critíco após entregar o pacote
     * @return o caminho que o robot está a percorrer.
     */
    public TrajetoriasRobot getPath(){
        return this.path;
    }


    /**
     * Este método void que  serve para dar update na bateria, ou seja consoante o estado em que o robot está(parado, a carregar ou
     * em andamento), vai gastar diferente bateria consoante as iterações que são feitas nos mesmos intervalos de tempo
     * @param it é o numero de iterações
     */
    public void updateBattery(double it){
        if(this.stat == stats[0])
            this.battery -= (it * STOP_WASTED_BATTERY);
        else if(this.stat == stats[1])
            this.battery -= (it * MOVE_WASTED_BATTERY);
        else if(this.stat == stats[2])
            this.battery += (it * CHARGING_BATTERY);
        if(this.battery >= 100)
            this.battery = 100;
    }


    /**
     * Este método serve para dar update do stat do robot.
     * @param i é o numero que representa o stat para qual stat queremos ter o robot.
     * Podemos escolher o "0 1 2" que representam os stats(*,-,+) respetivamente.
     */
    public void updateStat(int i){
        this.stat = stats[i];
    }


    public void updateLocation(Ponto location){
        Ponto p = new Ponto(((double) Math.round(location.get_x() * 100) /100), (double) Math.round(location.get_y() * 100) /100);
        this.location = p;
    }

    /**
     * Retorna a localizacao
     * @return a localizacao.
     */

    public Ponto getLocation() {
        return location;
    }


    /**
     * Este método serve para trocar o valor do próximo ponto
     * @param p é o próximo ponto
     */
    public void setNextPoint(Ponto p){
        this.nextPoint = p;
    }


    /**
     * Este método void , serve para obter todos as trajetorias possíveis para o robot, tendo em conta o pacote
     * , as figuras geométricas e a posicao atual do robot.
     * @param figs é a array list de figuras geométricas
     * @param pack é o pacote
     */
    public void getAllPaths(ArrayList<FiguraGeometrica> figs, Pacote pack){
        if(!this.paths.containsKey(pack)) {
            TrajetoriasRobot path = new TrajetoriasRobot(this.location,figs,pack);
            paths.put(pack,path);
            if(enoughEnergy(pack)) {
                this.path = paths.get(pack);
                if(this.path.getBestPath().getPoints().size() > 1)
                    setNextPoint(this.path.getBestPath().getPoints().get(1));
                else
                    setNextPoint(this.path.getBestPath().getPoints().get(0));
            }
        }
    }


    /**
     * Este método serve para verificar se o robot tem energia suficiente para fazer encomenda, e retornar até
     * a posicao de carregamento, caso necessário.
     * @param pack É o pacote que que o robot vai fazer.
     * @return retorna um booleano que retorna true , caso o robot consiga fazer aceitar esta encomenda, e falso caso
     * contrário.
     */
    public boolean enoughEnergy(Pacote pack){
        if(this.battery - (paths.get(pack).getBestPath().getTotalDist() * MOVE_WASTED_BATTERY) - (paths.get(pack).getSafePath().getTotalDist() * MOVE_WASTED_BATTERY) > ((DISTANCE * STOP_WASTED_BATTERY) - STOP_WASTED_BATTERY))
            return true;
        return false;
    }


    /**
     * Este método serve para verificar se o robot , está em estado critico de energia. Ou seja se tem que ir imediatamente
     * para o ponto de carga ou se ainda pode ficar parado no mesmo sitio a espera que fique em estado critico.
     * @return retorna true, caso o robot esteja em estado critico
     */
    public boolean criticalEnergy(){
        if (this.stat == '-') {
            if (this.battery - (this.path.getSafePath().getTotalDist() * (MOVE_WASTED_BATTERY)) <= ((DISTANCE * STOP_WASTED_BATTERY) - STOP_WASTED_BATTERY)) {
                updateStat(1);
                this.path.setPathSafePath();
                this.nextPoint = this.path.getSafePath().getPoints().get(1);
                return true;
            }
        }
        return false;
    }



    /**
     *Este método void , é responsável por mover o robot, em cada iteração. Caso o stat dele seja "*"
     * ou seja o robot está em movimento. Neste caso em cada iteração ele desloca-se apenas o valor da distancia dada por nós
     * através do angulo formada entre a trajetória e a origem.
     * @param dist é o valor da distancia que o robot vai percorrer em cada iteração.
     */
    public void moveToPoint(double dist) {
        if(this.stat == '*'){
            double distToNextPoint = this.location.dist(this.nextPoint);
            double angle = 0;
            double x_increment = 0;
            double y_increment = 0;
            double[] quadrant = new double[2];
            while (distToNextPoint <= dist) {
                this.location = this.nextPoint;
                this.nextPoint = this.path.getBestPath().getNextPoint(this.location);
                distToNextPoint = this.location.dist(this.nextPoint);
                if (dist - distToNextPoint < 0)
                    break;
                dist -= distToNextPoint;
                updateBattery(distToNextPoint);
                if (this.location.dist(this.path.getBestPath().getPoints().get(this.path.getBestPath().getPoints().size() - 1)) == 0) {
                    if(this.location.dist(this.chargeLocation) == 0){
                        updateStat(2);
                        updateBattery(dist);
                    }else{
                        updateStat(0);
                        updateBattery(dist);
                    }
                    paths.clear();
                    return;
                }
            }
            double y = (this.nextPoint.get_y() - this.location.get_y());
            double x = (this.nextPoint.get_x() - this.location.get_x());
            Ponto aux = new Ponto(1, x, y);
            angle = Math.atan(y / x);
            quadrant = aux.checkQuadrant();
            x_increment = Math.cos(angle) * dist * quadrant[0];
            y_increment = Math.sin(angle) * dist * quadrant[1];
            Ponto newLocation = new Ponto(this.location.get_x() + x_increment, this.location.get_y() + y_increment);
            updateLocation(newLocation);
            updateBattery(dist);
            paths.clear();
        } else {
            this.updateBattery(dist);
            if(this.stat == '+' && this.battery == 100)
                paths.clear();
        }

    }


    /**
     * Este método serve para mostrar o robot na formatacao que o professor quer.
     * @return retorna uma string com a formatacao do robot.
     */
    public String toStringProject(){
        String print = "(" + (Math.round(this.location.get_x() * 100) / 100) + "," + (Math.round(this.location.get_y() * 100) / 100) + "," + String.format("%.2f", this.battery) + "," + this.stat + ") ";
        return print;
    }


    /**
     * Este método serve para mostrar o robot com uma determinada formatacao do robot
     * @return uma string com uma determinada formatacao do robot
     */
    public String toString(){
        String print = "<RobotLocation: (" + (Math.round(this.location.get_x() * 100) / 100) + ";" + (Math.round(this.location.get_y() * 100) / 100) + ")>\n"
                + "<RobotBatterStats: " + String.format("%.2f", this.battery) + "," + this.stat + " >\n"
                + "<RobotSpawnLocation: (" + this.chargeLocation.get_x() + ";" + this.chargeLocation.get_y() + ")>";
        return print;
    }

    public String toStringPaths(){
        String print ="";
        if(this.path != null)
            print = this.path.toString();
        return print;
    }



    public class TrajetoriasRobot extends Robot {

        private Trajetoria bestPath;
        private Trajetoria safePath;

        /**
         * Este método é o construtor da classe trajetorias robot, que vai criar o melhor caminho deste robot e o seu
         * caminho seguro para o local de carregamento.
         * @param location é a localizacao atual do robot
         * @param figs é a arraylist de figuras geométricas
         * @param pack é o pacote que o robot está a analisar
         */

        public TrajetoriasRobot(Ponto location, ArrayList<FiguraGeometrica> figs, Pacote pack) {
            super(location);
            bestPath(figs, pack);
            safePath(figs, pack);
        }

        /**
         *Este método,serve para descobrir a melhor trajétoria para o robot fazer o pacote(menor distancia possível)
         * @param figs  é a arraylist de figuras geométricas
         * @param pack é o pacote que o robot está a analisar
         */
        public void bestPath(ArrayList<FiguraGeometrica> figs, Pacote pack){
            PlaneadorPrincipal plan = null;
            Trajetoria first = new Trajetoria();
            Trajetoria last = new Trajetoria();
            if(this.getLocation().dist(pack.getStart()) == 0)
                first.addOnePoint(this.getLocation());
            else {
                plan = new PlaneadorPrincipal(this.getLocation(), pack.getStart(),figs);
                first = plan.getBestDistance();
            }
            if(pack.getEnd().dist(pack.getStart()) == 0)
                last.addOnePoint(pack.getStart());
            else {
                plan = new PlaneadorPrincipal(pack.getStart(), pack.getEnd(),figs);
                last = plan.getBestDistance();
            }
            first.concatPaths(last);
            this.bestPath = first;
        }

        /**
         * Este método serve para definir um caminho para o robot voltar para o local de carregamento.
         * @param figs é a arraylist de figuras geométricas
         * @param pack é o pacote que o robot está a analisar
         */
        public void safePath(ArrayList<FiguraGeometrica> figs, Pacote pack) {
            PlaneadorPrincipal plan = null;
            Trajetoria first = new Trajetoria();
            if(Robot.this.chargeLocation.dist(pack.getEnd()) == 0)
                first.addOnePoint(pack.getEnd());
            else {
                plan = new PlaneadorPrincipal(pack.getEnd(), Robot.this.chargeLocation,figs);
                first = plan.getBestDistance();
            }
            this.safePath = first;

        }

        /**
         * Este método retorna a trajetória para o ponto de carga do robot.
         * @return a trajetória para o ponto de carga do robot.
         */
        public Trajetoria getSafePath(){
            return this.safePath;
        }

        /**
         * Este método retorna a trajetória para o ponto de carga do robot.
         * @return a trajetória para o ponto de carga do robot.
         */
        public Trajetoria getBestPath(){
            return this.bestPath;
        }


        /**
         * Este método void, vai dizer passar o melhor caminho a ser o caminho "safe" onde ele irá para o ponto de
         * carga, pois no nosso método para fazer o robot "andar" que é o movetopoint, só faz o robot andar pela melhor
         * trajetoria.
         */
        public void setPathSafePath(){
            this.bestPath = this.safePath;
        }


        /**
         * Este método serve para mostrar o caminho do robot nesta formatacao
         * @return retorna uma string.
         */
        public String toString(){
            String print = "Best path: " + this.bestPath.toString() +"\n";
            print += "Safe path: " + this.safePath.toString() + "\n";
            return print;
        }


    }
}
