import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
     * @author Grupo7
     * @version correto-17 Amazon correto version 17.0.4 23/02/2023
     * */
    public class Cliente2 {
    /** Método fornecido pelo Professor para formatar uma String de forma a que seja possível saber qual a classe que queremos
     * @author José Luís Valente de Oliveira
     * @param s String por formatar
     * @return  String formatada
     */
    public static String capital(String s) {
            String res = s.toLowerCase();
            Character initial = Character.toUpperCase(res.charAt(0));
            StringBuilder sb = new StringBuilder(res);
            sb.setCharAt(0, initial);
            final String answer = sb.toString();
            return answer;
        }

    /** Método dado pelo Professor para que seja possível através de uma String criar uma FiguraGeométrica, por fim retorna uma
     * ArrayList com os obstáculos
     * @author José Luís Valente de Oliveira
     * @param in    Scanner para ler o input
     * @return  ArrayList com os obstáculos
     * @throws Exception
     */
        public static ArrayList<FiguraGeometrica>  createFigs(Scanner in) throws Exception {
            System.out.println("Introduza os obstaculos:");
            ArrayList<FiguraGeometrica> figs = new ArrayList<FiguraGeometrica>();
            Constructor<?> constructor;
            Class<?> cl;
            FiguraGeometrica f;
            String s;
            String[] aos;
            while (in.hasNextLine()) {
                s = in.nextLine();
                if (s.isEmpty())
                    break;
                aos = s.split(" ");
                try {
                    cl = Class.forName(capital(aos[0]));
                    constructor = cl.getConstructor(String.class);
                    f = (FiguraGeometrica) constructor.newInstance(s);
                    figs.add(f);
                } catch (Exception e) {
                    System.out.println("Tipo de obstaculo desconhecido");
                    System.exit(0);
                }
            }
            return figs;
        }

    /** Método que cria um número de Robots escolhido pelo utilizador e mais tarde irá adicionar ao Mapa
     *
     * @param in  Scanner para ler o input
     * @return    ArrayList com os Robots
     * @throws Exception
     */
        public static ArrayList<Robot>  createRobots(Scanner in) throws Exception {
            int index = 1;
            Robot robot;
            ArrayList<Robot> robots = new ArrayList<Robot>();
            String point = "";
            String split[] = new String[2];
            System.out.print("Quantos Robots deseja adicionar: ");
            int i = in.nextInt();
            in.nextLine();
            while (index-1 < i) {
                System.out.print("Robot" + index + ": ");
                point = in.nextLine();
                split = point.split(" ");
                robot = new Robot(new Ponto(Double.parseDouble(split[0]), Double.parseDouble(split[1])));
                robots.add(robot);
                index++;
            }
            System.out.println();
            return robots;
        }

    /** Método que adiciona pacotes escolhidos pelo utilizador ao Mapa
     *
     * @param in  Scanner para ler o input
     * @throws Exception
     */
        public static void createPack(Mapa map, Scanner in) throws Exception {
            int index = 1;
            Pacote pack;
            String point = "";
            String split[] = new String[2];
            System.out.print("Quantos Pacotes deseja adicionar: ");
            int i = in.nextInt();
            in.nextLine();
            while (index-1 < i) {
                System.out.print("Pacote" + index + ": ");
                point = in.nextLine();
                split = point.split(" ");
                pack = new Pacote(new Ponto(Double.parseDouble(split[0]), Double.parseDouble(split[1])), new Ponto(Double.parseDouble(split[2]), Double.parseDouble(split[3])));
                map.addPack(pack);
                index++;
            }
            System.out.println();
        }

    /** Método fornecido pelo Professor na tutoria para parar o ciclo while ao ser pressionado Enter, foi adaptado pelo Grupo7
     * para pedir um input (no caso "Pacote, Robot, ou "Obstaculo") e consoante o input inicial dado irá adicionar aquilo que
     * o utilizador quiser
     * @author  Helder Aniceto Amadeu de Sousa Daniel e Grupo7
     * @param in  Scanner para ler o input
     * @return String com o que o utilizador quiser
     * @throws Exception
     */
        public static String getStringNB(InputStreamReader in, Scanner input, Mapa map) throws Exception {
            BufferedReader inbuf = new BufferedReader(in);
            String s="";
            String word ="";

            //If user pressed first enter
            if (inbuf.ready()) {
                int c = inbuf.read(); //discard first enter
                System.out.println("Deseja adicionar Pacote, Robot, ou Obstaculo\n");
                word = inbuf.readLine();
                if(word.toLowerCase().equals("robot")){
                    ArrayList<Robot> robots = Cliente2.createRobots(input);
                    map.addRobots(robots);
                } else if((word.toLowerCase().equals("pacote"))){
                    Cliente2.createPack(map, input);
                } else if((word.toLowerCase().equals("obstaculo"))){
                    ArrayList<FiguraGeometrica> figs = Cliente2.createFigs(input);
                    map.addFigs(figs);
                }
                //s = inbuf.readLine(); //read usr input until enter pressed again
            }
            return s;
        }
}
