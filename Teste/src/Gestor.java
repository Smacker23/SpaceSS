import java.util.Random;

/**
 * @author Grupo7
 * @version correto-17 Amazon correto version 17.0.4 07/05/2023
 * */
public class Gestor {
    private Mapa map;

    /**
     * Esta função é o construtor da class e serve para crias objetos do tipo gestor através de um mapa passado
     * como argumento.
     * @param map - Mapa
     */
    public Gestor(Mapa map){
        this.map = map;
    }

    /**
     * Esta função através de um random passado como argumento vai ao mapa e escolhe o melhor robot possivel para transportar o
     * primeiro pacote na queue de pacotes, tendo em atenção se ele já está em movimento (que quer dizer se ele já foi destacado
     * para transportar um pacote), se ele está parado (que quer dizer se ele ainda não foi destacado para transportar nenhum pacote)
     * ou se está a carregar a sua bateria.
     */
    public void chooseRobot(){
        double dist = Double.MAX_VALUE;
        int index = -1;
        Pacote check = null;
        while(!map.isPacakageEmpty()) {
            Pacote current = map.getPackage();
            if(check != null && current.equals(check)) {
                map.addPack(current);
                return;
            }
            for(int i = 0; i < map.getRobots().size(); i++){
                if( map.getRobots().get(i).getStat() == '-' || ( map.getRobots().get(i).getStat() == '+' &&  map.getRobots().get(i).getBattery() == 100) ) {
                    map.getRobots().get(i).getAllPaths(map.getFigs(), current);
                    if ( map.getRobots().get(i).enoughEnergy(current) &&  map.getRobots().get(i).getPath().getBestPath().getTotalDist() < dist) {
                        index = i;
                        dist =  map.getRobots().get(i).getPath().getBestPath().getTotalDist();
                    }
                }
            }
            if(index == -1) {
                if (check == null)
                 check = current;
                map.addPack(current);
            }
            else {
                map.getRobots().get(index).updateStat(1);
                index = -1;
                check = null;
            }
        }
    }
}