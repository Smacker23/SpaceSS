

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
/**
 * @author Alexandre Carvalho
 * @version correto-17 Amazon correto version 17.0.4 23/02/2023
 * @inv A ArrayList srs não pode haver dois com os mesmos valores
 * */
public class Trajetoria {
    private ArrayList<Ponto> points = new ArrayList<Ponto>();
    private ArrayList<SegmentoReta> segs = new ArrayList<SegmentoReta>();
    private HashMap<Ponto, Integer> flags = new HashMap<>();
    private HashMap<Ponto, Integer> times = new HashMap<>();



    /**
     * Este método adiciona um Ponto à trajetória
     *
     * @param p Ponto que vai ser adicionado à trajetória
     */
    public void addOnePoint(Ponto p) {
        this.points.add(p);
        err();
        pathSegs();
    }

    public void addOnePoint(int i, Ponto p) {
        if(i == 1) {
            this.points.add(p);
        }
    }

    /**
     * Este método adiciona um Ponto aleatória à trajetória
     *f
     * @param randomGenerator Random para gerar os Pontos aleatórios
     */
    public void addOnePoint(Random randomGenerator) {
        int n = 1;
        while (n > 0) {
            Ponto point = new Ponto(randomGenerator.nextInt(100), randomGenerator.nextInt(100));
            if (!contaisPoint(point)) {
                this.points.add(point);
                n--;
            }
        }
        pathSegs();
    }

    /**
     * Este método adiciona vários Pontos aleatória à trajetória
     *
     * @param randomGenerator Random para gerar os Pontos aleatórios
     * @param n               Inteiro que representa o número de Pontos a ser adicionado
     */
    public void addMultiPoints(Random randomGenerator, int n) {
        while (n > 0) {
            Ponto point = new Ponto(randomGenerator.nextInt(100), randomGenerator.nextInt(100));
            if (!contaisPoint(point)) {
                this.points.add(point);
                n--;
            }
        }
        pathSegs();
    }

    /**
     * Este método adiciona vários Pontos aleatória à trajetória
     *
     * @param randomGenerator Random para gerar os Pontos aleatórios
     * @param n               Inteiro que representa o número de Pontos a ser adicionado
     * @param start           Ponto inicial da trajetória
     * @param end             Ponto final da trajetória
     */
    public void addMultiPoints(Random randomGenerator, int n, Ponto start, Ponto end) {
        this.points.add(start);
        while (n > 0) {
            Ponto point = new Ponto(randomGenerator.nextInt(100), randomGenerator.nextInt(100));
            if (!contaisPoint(point) && point.dist(end) != 0) {
                this.points.add(point);
                n--;
            }
        }
        this.points.add(end);
        pathSegs();
    }

    /** Este método adiciona vários Pontos aleatória à trajetória
     *
     * @param randomGenerator Random para gerar os Pontos aleatórios
     * @param start Ponto inicial da trajetória
     * @param end Ponto final da trajetória
     * @param figs ArrayList de obstáculos
     */

    public void addMultiPoints(Random randomGenerator, Ponto start, Ponto end, ArrayList<FiguraGeometrica> figs) {
        this.points.add(start);
        int flag = 0;
        int n = randomGenerator.nextInt(20);
        while (n > 0) {
            flag = 0;
            Ponto point = new Ponto(randomGenerator.nextInt(100), randomGenerator.nextInt(100));
            if ((!contaisPoint(point) && point.dist(end) != 0)) {
                for (FiguraGeometrica fig : figs) {
                    if (fig.figVerification(point)) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    this.points.add(point);
                    n--;
                }
            }
        }
        this.points.add(end);
        pathSegs();
    }

    /**
     * Este método adiciona vários Pontos à trajetória
     *
     * @param in Scanner para ler a linha que representa os pontos
     */
    public void addMultiPoints(Scanner in) {
        String input = in.nextLine();
        String[] splt = input.split(" ");
        for (int i = 0; i < splt.length; i += 2)
            this.points.add(new Ponto(Integer.parseInt(splt[i]), Integer.parseInt(splt[i + 1])));
        pathSegs();
    }


    /**
     * Este método verifica se um novo Ponto já existe na trajetória
     *
     * @param point Ponto que vai ser verificado
     * @return True se já existir. False caso não
     */
    private boolean contaisPoint(Ponto point) {
        for (int i = 0; i < this.points.size(); i++) {
            if (points.get(i).dist(point) == 0)
                return true;
        }
        return false;
    }


    /**
     * Este método adiciona lê um Ponto e adiciona-o à trajetória. Por fim verifica se é formado uma trajetória e é criado os Segmentos através do Pontos
     *
     * @param in Scanner que vai ler o Ponto
     */
    public void addOnePoint(Scanner in) {
        this.points.add(new Ponto(in.nextDouble(), in.nextDouble()));
        err();
        pathSegs();
    }

    /**
     * Este método adiciona lê vários Pontos e adiciona-os à trajetória. Por fim verifica se é formado uma trajetória e é criado os Segmentos através do Pontos
     *
     * @param in Scanner que vai ler o Ponto
     * @param n  Quantidade de Pontos que vai ser lido
     */
    public void addMultiPoints(Scanner in, int n) {
        while (n > 0) {
            this.points.add(new Ponto(in.nextDouble(), in.nextDouble()));
            n--;
        }
        err();
        pathSegs();
    }

    /**
     * Este método dá print "Trajetoria:vi" e encerra o programa caso não forme uma trajetória
     */
    private void err() {
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if (points.get(i).dist(points.get(j)) == 0) {
                    System.out.println("Trajetoria:vi");
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Cria SegmentosReta através dos Pontos da trajetória
     */
    public void pathSegs() {
        ArrayList<SegmentoReta> segs = new ArrayList<SegmentoReta>();
        for (int i = 1; i < points.size(); i++) {
            SegmentoReta sr = new SegmentoReta(points.get(i - 1), points.get(i));
            segs.add(sr);
        }
        this.segs = segs;
    }

    /**
     * Este método retorna a ArrayList com todos os Pontos da trajetória
     *
     * @return ArrayList com os Pontos da trajetória
     */
    public ArrayList<Ponto> getPoints() {
        return this.points;
    }

    /**
     * Este método verifica se os SegmnetosReta da trajetória intersetam uma ArrayList de FigurasGeométricas.
     *
     * @param figs ArrayList de FigurasGeométricas (Retângulo, Triângulo, Circunferência)
     * @return Double que representa o número de vezes que a trajetória bateu nos Obstáculos
     */
    public double checkHits(ArrayList<FiguraGeometrica> figs) {
        int hits = 0;
        for (int i = 0; i < figs.size(); i++) {
            for (int j = 0; j < segs.size(); j++) {
                if (figs.get(i).check_hit(segs.get(j))) {
                    hits++;
                    break;
                }
            }
        }
        return hits;
    }

    /**
     * Este método cálcula a distância total da trajetória
     *
     * @return Double que representa a distância total da trajetória
     */
    public double getTotalDist() {
        double dist = 0;
        for (int i = 0; i < this.points.size() - 1; i++) {
            dist += this.points.get(i).dist(this.points.get(i + 1));
        }
        return dist;
    }

    /**
     * Este método faz uma avaliação da trajetória usando a expressão 1 / (distância total + número de obstáculos que intersetou)
     *
     * @param figs ArrayList FiguraGeométrica que representa os obstáculos
     * @return Double valor da avaliação na escala de 0 a 1
     */
    public double getPathValuation(ArrayList<FiguraGeometrica> figs) {
        return 1 / (getTotalDist() + Math.exp(checkHits(figs)));
    }

    /**
     * Este método Recebe duas Trajetórias e compara-as, retornando o índice da trajetória com melhor avaliação. Este
     * também é dado como argumento.
     *
     * @param figs   ArrayList de FigurasGeométricas (obstáculos das trajetórias)
     * @param second Path que representa a segunda trajetória
     * @return int que representa o índice da trajetória vencedora
     */
    public Trajetoria bestPath(ArrayList<FiguraGeometrica> figs, Trajetoria second) {
        if (this.getPathValuation(figs) >= second.getPathValuation(figs))
            return this;
        else
            return second;
    }

    /**
     * Este método recebe dois Paths e irá cortar ambos ao meio, escolhendo o índice de corte aleatoriamente
     * através de um gerador aleatório. Depois irá ser juntado a primeira parte do primeiro com a segunda do segundo
     * e vice-versa. Caso o ponto adicionado, já exista este será ignorado.
     *
     * @param randomGenerator Random que serve para gerar números aleatórios
     * @param aux             Path auxiliar que serve para para ser reconstruído com o do construtor
     * @return Path[] com tamanho 2, que vai representar os dois novos paths, misturados
     */
    public Trajetoria[] mixer(Random randomGenerator, Trajetoria aux) {
        Trajetoria[] trajetorias = new Trajetoria[2];
        Trajetoria first = new Trajetoria(), second = new Trajetoria();
        int splitFirst = randomGenerator.nextInt(this.getPoints().size() - 1);
        int splitSecond = randomGenerator.nextInt(aux.getPoints().size() - 1);


        for (int i = 0; i <= splitFirst; i++)
            first.addOnePoint(this.points.get(i));

        for (int i = splitSecond + 1; i < aux.points.size(); i++) {
            if (!first.contaisPoint(aux.points.get(i)))
                first.addOnePoint(aux.points.get(i));
        }

        for (int i = 0; i <= splitSecond; i++)
            second.addOnePoint(aux.points.get(i));

        for (int i = splitFirst + 1; i < this.points.size(); i++) {
            if (!second.contaisPoint(this.points.get(i)))
                second.addOnePoint(this.points.get(i));
        }
        first.pathSegs();
        second.pathSegs();
        trajetorias[0] = first;
        trajetorias[1] = second;
        return trajetorias;
    }


    /**
     * Este método recebe uma trajetória, e um double que representa a chance de haver uma mutação. Caso o valor gerado
     * pela trajetória (aleatóriamente), seja menor que a chance, será mutado um dos pontos da trajetória, para isso será
     * gerado outro número que irá representar o índice mutado. Caso o número mutado, já exista esse será ignorado
     *
     * @param randomGenerator Random que serve para gerar números aleatórios
     * @param odds            Double que representa a chance da mutação ocorrer
     * @return Path após sofrer, ou não as alterações
     */
    public Trajetoria mutation(Random randomGenerator, double odds) {
        if (this.points.size() <= 2)
            return this;
        double chance = randomGenerator.nextDouble();
        if (chance < odds) {
            int index = randomGenerator.nextInt(this.points.size() - 2) + 1;
            Ponto newPoint = new Ponto(randomGenerator);
            if (!this.contaisPoint(newPoint))
                this.points.set(index, newPoint);
        }
        pathSegs();
        return this;
    }

    /**
     * Este método recebe uma trajetória, e um double que representa a chance de haver uma mutação. Caso o valor gerado
     * pela trajetória (aleatóriamente), seja menor que a chance, será adicionado um novo Ponto à trajetória, o índice
     * onde será adicionado, é gerado aleatóriamente
     *
     * @param randomGenerator Random que serve para gerar números aleatórios
     * @param odds            Double que representa a chance da mutação ocorrer
     * @return Path após sofrer, ou não as alterações
     */
    public Trajetoria mutationAdd(Random randomGenerator, double odds) {
        double chance = randomGenerator.nextDouble();
        if (chance < odds) {
            if (this.points.size() <= 2) {
                Ponto newPoint = new Ponto(randomGenerator);
                if (!this.contaisPoint(newPoint))
                    this.points.add(1, newPoint);
                return this;
            }
            int index = randomGenerator.nextInt(this.points.size() - 2) + 2;
            Ponto newPoint = new Ponto(randomGenerator);
            while (this.contaisPoint(newPoint))
                newPoint = new Ponto(randomGenerator);
            this.points.add(index, newPoint);
        }
        pathSegs();
        return this;
    }

    /**
     * Este método recebe uma trajetória, e um double que representa a chance de haver uma mutação. Caso o valor gerado
     * pela trajetória (aleatóriamente), seja menor que a chance, será removido um Ponto da trajetória, este será escolhido
     * através da geração de um índice aleatóriamente
     *
     * @param randomGenerator Random que serve para gerar números aleatórios
     * @param odds            Double que representa a chance da mutação ocorrer
     * @return Path após sofrer, ou não as alterações
     */
    public Trajetoria mutationRemove(Random randomGenerator, double odds) {
        if (this.points.size() <= 2)
            return this;
        double chance = randomGenerator.nextDouble();
        if (chance < odds) {
            int index = randomGenerator.nextInt(this.points.size() - 2) + 1;
            this.points.remove(index);
        }
        pathSegs();
        return this;
    }

    /** Este método confirma se ao ser adicionado um Ponto é criado uma trajetória:VI
     *
     * @param point Ponto a ser adicionado
     * @return True se sim. False caso não
     */

    public boolean checkPathVI(Ponto point){
        if(this.contaisPoint(point))
            return true;
        else
            return false;
    }



    /** Este método retorna uma String com a formatação da trajetória, isto é todos os pontos da trajetória com duas casas decimais
     *
     * @return String
     */
    public String toString() {
        String print = "[";
        for (int j = 0; j < this.getPoints().size(); j++) {
            if (j + 1 == this.getPoints().size())
                print += "(" +  String.format("%.2f", this.getPoints().get(j).get_x()) + ";" +  String.format("%.2f", this.getPoints().get(j).get_y()) + ")";
            else
                print += "(" +  String.format("%.2f", this.getPoints().get(j).get_x()) + ";" +  String.format("%.2f", this.getPoints().get(j).get_y()) + ") ";
        }
        print += "]";
        return print;
    }

    /**
     * Este método irá formatar a População para ser dado com uma formatação String
     *
     * @return String com a formatação pretendida
     */
    public String toString2() {
        String print = "[";
        for (int j = 0; j < this.getPoints().size(); j++) {
            if (j + 1 == this.getPoints().size())
                print += "(" + (int) this.getPoints().get(j).get_x() + ";" + +(int) this.getPoints().get(j).get_y() + ")";
            else
                print += "(" + (int) this.getPoints().get(j).get_x() + ";" + +(int) this.getPoints().get(j).get_y() + ") ";
        }
        print += "]";
        return print;
    }


    /** Este método junta duas trajetórais numa só
     *
     * @param aux Nova trajetória que representa a junção das duas anteriores
     */
    public void concatPaths(Trajetoria aux) {
        if (this.points.size() > 1 || this.points.get(0).dist(aux.points.get(0)) == 0)
            this.points.remove(this.points.size() - 1);
        this.points.addAll(aux.points);
        this.pathSegs();
    }

    /** Este método vai verificar qual é o Ponto atual em que se está na trajetória e irá retornar o próximo
     *
     * @param location Ponto atual
     * @return O próximo Ponto da trajetória
     */
    //duas HashMaps para fazer o tracking dos pontos repetidos
    //flags -> o número de vezes que o ponto foi passado
    //times -> contador apenas desta vez que o método foi chamada e vai conter o número de vezes que o número foi percorrido ATUALMENTE (só desta iteração)
    //o times serve para acompanhar a flag
    //contador atual que leva reset a cada vez que é chamado
    //flag que não é resetado
    public Ponto getNextPoint(Ponto location) {
        int time = 0;
        if(!flags.containsKey(this.points.get(0)))      //é preciso fazer esta condiação caso esteja inicialmente vazio
            flags.put(this.points.get(0),1);
        for (int i = 0; i < this.points.size(); i++) {
            if (times.containsKey(this.points.get(i))) {
                time = times.get(this.points.get(i));       //aqui pegamos o valor para incrementar e voltamos a adicionar ao HashMap
                time++;
                times.put(this.points.get(i), time);
            } else
                times.put(this.points.get(i), 1);
            if ((this.points.get(i).dist(location) == 0) && (i + 1 != this.points.size())) {
                if (!flags.containsKey(this.points.get(i)) || times.get(this.points.get(i)) < flags.get(this.points.get(i))) {
                    if (flags.containsKey(this.points.get(i))) {
                        time = flags.get(this.points.get(i));
                        time++;
                        flags.put(this.points.get(i), time);
                    } else                                      //é preciso fazer esta condiação caso esteja inicialmente vazio
                        flags.put(this.points.get(i), 1);
                    times.clear();
                    return this.points.get(i + 1);
                }
            }
        }
        times.clear();
        return this.points.get(this.points.size() - 1);
    }


    /** Este método irá retornar duas novas Trajetórias que são resultado da intereseção da trajetória atenrior num obstáculo
     *
     * @param figs ArrayList de obstáculos
     * @return Array com duas trajetórias
     */

    public Trajetoria[] checkFirstHit(ArrayList<FiguraGeometrica> figs){
        Trajetoria[] paths = new Trajetoria[2];
        Trajetoria first = new Trajetoria();
        first.points = new ArrayList<>(this.points);
        Trajetoria second = new Trajetoria();
        second.points = new ArrayList<>(this.points);
        Ponto[] resultFinal = null;
        Ponto[] current = null;
        int flag = 0;
            for(int i = 0; i < this.segs.size(); i++) {
                for (FiguraGeometrica fig : figs) {
                    current = fig.check_hitSegment(this.segs.get(i));
                    if (current != null) {
                        if ((resultFinal == null || resultFinal[0].dist(this.segs.get(i).get_first()) > current[0].dist(this.segs.get(i).get_first()))) {
                            resultFinal = current;
                            flag = fig.returType();
                        }
                    }
                }
                if (resultFinal != null) {
                    if(flag == 2){
                        paths = this.updateForCirc(paths,first,second,resultFinal,i);
                        return paths;
                    }
                    if (first.contaisPoint(resultFinal[1]) || !resultFinal[1].checkFirstQuadrant()) {
                        first = null;
                    } else {
                        first.points.add(i + 1, resultFinal[1]);
                        first.pathSegs();
                    }
                    if (second.contaisPoint(resultFinal[2]) || !resultFinal[2].checkFirstQuadrant())
                        second = null;
                    else {
                        second.points.add(i + 1, resultFinal[2]);
                        second.pathSegs();
                    }
                    paths[0] = first;
                    paths[1] = second;
                    return paths;
                }
            }
        return null;
    }

    /** Este método retorna os segmentosReta da Trajetoría
     *
     * @return ArrayList com SegmentosReta
     */
    public ArrayList<SegmentoReta> getSegs() {
        return segs;
    }


    /** Este método é utilizado para dar update da trajetória, no caso de ela intersetar uma circunferencia, uma vez que para este
     * obstáculo será necessário adicionar mais pontos
     *
     * @param paths As duas trajetórias geradas apoós a interseção
     * @param first Primeira trajetória
     * @param second Segunda trajetória
     * @param resultFinal Os Pontos gerados para ser possível não tocar na circunferência
     * @param i inteiro que representa o índice do semgnetoReta que intersetou a cirncunferência
     * @return As duas trajetórias já corretas
     */

    public Trajetoria[] updateForCirc(Trajetoria[] paths, Trajetoria first, Trajetoria second, Ponto[] resultFinal, int i){
        int flag3 = i + 1;
        int flag2 = 0;
        for(int j = 0; j < resultFinal.length; j++){
            if(flag2 == 3 && resultFinal[j] == this.segs.get(i).get_second())
                flag2 = 4;
            if(flag2 == 3) {
                if(second.contaisPoint(resultFinal[j])){
                    second = null;
                    flag2 = 4;
                }if(second != null)
                    second.points.add(flag3, resultFinal[j]);
                flag3++;
            }
            if(flag2 == 2) {
                flag2 = 3;
                flag3 = i + 1;
            }
            if(resultFinal[j] == this.segs.get(i).get_second())
                flag2 = 2;
            if(flag2 == 1) {
                if(first.contaisPoint(resultFinal[j])){
                    first = null;
                    flag2 = 2;
                } if(first != null)
                    first.points.add(flag3, resultFinal[j]);
                flag3++;
            }
            if(flag2 == 0 && resultFinal[j] == this.segs.get(i).get_first())
                flag2 = 1;
        }
        if(first == null || first.points.size() == 2)
            first = null;
        if(second == null || second.points.size() == 2)
            second = null;
        paths[0] = first;
        paths[1] = second;
        if(first != null)
            first.pathSegs();
        if(second != null)
            second.pathSegs();
        return paths;
    }

//    public Trajetoria[] updateForCirc(Trajetoria[] paths, Trajetoria first, Trajetoria second, Ponto[] resultFinal, int j){
//        int i = 1; //Para passarmos o primeiro ponto à frente
//        while(resultFinal[i] != this.segs.get(j).get_second()){
//            if(first == null || first.contaisPoint(resultFinal[j])) {
//                first = null;
//            } else
//                first.points.add(resultFinal[i]);
//            i++;
//        }
//        System.out.println(this.segs.get(j).get_second());
//        System.out.println(resultFinal[i]);
//        i++;
//        System.out.println(resultFinal[i]);
//        while (resultFinal[i] != this.segs.get(j).get_second()){
//            if(second == null || second.contaisPoint(resultFinal[j])) {
//                second = null;
//            } else
//                second.points.add(resultFinal[i]);
//            i++;
//        }
//        if(first == null || first.points.size() == 2)
//            first = null;
//        if(second == null || second.points.size() == 2)
//            second = null;
//        paths[0] = first;
//        paths[1] = second;
//        if(first != null)
//            first.pathSegs();
//        if(second != null)
//            second.pathSegs();
//        return paths;
//    }

    /** Este método verifica se uma trajetória tem pontos fora do mapa
     *
     * @return True se tiver. False caso esteja dentro do mapa
     */
    public boolean killPath(){
        for(Ponto point : this.getPoints()){
            if(point.get_x() < 0 || point.get_y() < 0 || point.get_y() >= 1000 || point.get_x() >= 1000)
                return true;
        }
        return false;
    }

    /** Este método verifica se duas trajetórias são iguais
     *
     * @param aux  Trajetória auxiliar
     * @return True se forem iguais e false caso não
     */
    public boolean is(Trajetoria aux){
        if(this.points.size() != aux.points.size()) {
            return false;
        }
        for (int i = 0; i < this.points.size(); i++) {
            if(points.get(i).get_x() != aux.points.get(i).get_x() || points.get(i).get_y() != aux.points.get(i).get_y())
                return false;

        }
        return true;
    }




}