import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author Grupo7
 * @version correto-17 Amazon correto version 17.0.4 07/05/2023
 * */
public class Mapa implements MapaInterface{

    private ArrayList<Robot> robots = new ArrayList<>();
    private ArrayList<FiguraGeometrica> figs = new ArrayList<>();
    private Queue<Pacote> packages = new LinkedList<>();
    private int step;
    private static final double DISTANCE = 10;


    /**
     * Este método é o construtor da classe mapa.
     * @param robots o mapa necessita dos robots.
     * @param figs o mapa necessita das figuras geométricas.
     */
    public Mapa(ArrayList<Robot> robots, ArrayList<FiguraGeometrica> figs){
        this.figs = figs;
        this.robots = robots;
        errRobot();
    }


    /** Método que encerra o programa caso um Robot esteja dentro de um obstáculo
     *
     */
    private void errRobot(){
        for(FiguraGeometrica fig : this.figs){
            for(Robot robot : this.robots){
                if(fig.figVerification(robot.getChargeLocation())){
                    throw new IllegalArgumentException("\nRobotDentroDeObstaculo\n" +
                                                "RobotLocalizacao: " + robot.getChargeLocation().toString() + "\n"
                                                + fig.toString());
                }
            }
        }
    }

    /** Este método adiciona um Robot ao mapa
     *
     * @param robot Robot
     */
    public void addRobot(Robot robot){
        this.robots.add(robot);
        errRobot();
    }

    /** Método que encerra o programa caso um Pacote esteja dentro de um obstáculo
     *
     */
    private void errPacote(Pacote pack){
        for(FiguraGeometrica fig : this.figs){
            if(fig.figVerification(pack.getStart())){
                throw new IllegalArgumentException("\nRecolhaDoPacoteDentroDeObstaculo\n"+
                                                    "LocalizacaoPacoteRecolha" + pack.getStart().toString()+"\n"
                                                    + fig.toString());

            }
            if(fig.figVerification(pack.getEnd())){
                throw new IllegalArgumentException("\nEntregaDoPacoteDentroDeObstaculo\n"+
                        "LocalizacaoPacoteRecolha" + pack.getEnd().toString()+"\n"
                        + fig.toString());
            }
        }
    }


    /**
     * Este método serve para adicionar pacotes ao mapa.
     * @param pack é o pacote dado.
     */
    public void addPack(Pacote pack){
        this.packages.add(pack);
        errPacote(pack);
    }

    public void addPackages(Scanner in) throws Exception {
        Cliente2.createPack(this,in);
    }


    /**
     * Este método é o responsável por criar cada iteração do mapa.
     */
    public void moveMap(){
        Gestor now = new Gestor(this);
        now.chooseRobot();
        for(Robot robot: this.robots) {
            robot.criticalEnergy();
            robot.moveToPoint(DISTANCE);
            //System.out.println(toStringRobotPaths());
        }
    }

    /**
     * Este método serve para adicionar figuras ao mapa.
     * @param figs é o array list de figuras geométricas.
     */
    public void addFigs(ArrayList<FiguraGeometrica> figs){
        this.figs.addAll(figs);
    }


    /**
     * Este método serve para adicionar um robot ao mapa.
     * @param robots robot adicionado ao mapa.
     */
    public void addRobots(ArrayList<Robot> robots){
        this.robots.addAll(robots);
        errRobot();
    }



    /**
     * Este método serve para mostrar o mapa nesta formatação
     * @return retorna uma string.
     */
    public String toStringProject(){
        String print = "Step " + this.step + ": ";
        for(Robot robot : this.robots){
            print += robot.toStringProject();
        }
        this.step++;
        return print;
    }

    public String toString(){
        String print = "";
        int i = 0;
        double x = 0;
        double y = 0;
        print += "RobotsLocalizacao:\n";
        for(Robot robot: this.robots){
            x = robot.getLocation().get_x();
            y = robot.getLocation().get_y();
            print += "<Robot" + i + "Location: (" + x + ";" + y + ")> \n";
        }

        print += "\nRobotsPontoDeCarga:\n";
        for(Robot robot: this.robots){
            x = robot.getChargeLocation().get_x();
            y = robot.getChargeLocation().get_y();
            print += "<Robot" + i + "ChargePoint: (" + x + ";" + y + ")> \n";
        }

        print += "\nObstaculos:\n";
        for(FiguraGeometrica fig: this.figs){
            print += fig.toString() + "\n";
        }
        print = print.substring(0, print.length()-1);

        print+= "\n\nPacote:";
        if(!this.packages.isEmpty())
            print += "\n<Pacote: (" + this.packages.peek().getStart().get_x() + ";" + this.packages.peek().getStart().get_y() + ")>";
        else
            print += "\n<>";
        return print;
    }

    /** Método que retorna os Robots do Mapa
     *
     * @return ArrayList de Robots
     */
    public ArrayList<Robot> getRobots(){
        return this.robots;
    }

    @Override
    public Queue<Pacote> getPackages() {
        return this.packages;
    }

    /** Método que remove o primeiro Pacote da Queue
     *
     * @return Pacote
     */
    public Pacote getPackage(){
        return this.packages.remove();
    }

    /** Método que retorna os Obstáculos do Mapa
     *
     * @return ArrayList FigurasGeometricas
     */
    public ArrayList<FiguraGeometrica> getFigs(){
        return this.figs;
    }

    /** Método que verifica se a Queue de Pacotes está vazia
     *
     * @return True se estiver vazia. False caso tenha pacotes
     */
    public boolean isPacakageEmpty(){
        return this.packages.isEmpty();
    }

    public String toStringRobotPaths(){
        String print ="";
        for(Robot robot : this.robots){
            print += robot.toStringPaths() + "\n";
        }
        if(print.length() > 2)
            print = print.substring(0, print.length()-2);
        return print;
    }

    public void updateMap2(Scanner in, InputStreamReader in2) throws Exception {
        while(true){
            String s = Cliente2.getStringNB(in2, in, this);
            this.moveMap();
            System.out.println(this.toStringProject());
            Thread.sleep(1000);
        }
    }

    public void updateMap(Scanner in, InputStreamReader in2) throws Exception {
        String s = Cliente2.getStringNB(in2, in, this);
        this.moveMap();
        System.out.println(this.toStringProject());
        //Thread.sleep(1000);
    }

}
