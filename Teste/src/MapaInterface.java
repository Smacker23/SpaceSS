import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public interface MapaInterface {
    void addRobot(Robot robot);
    void addPack(Pacote pack);
    void addFigs(ArrayList<FiguraGeometrica> figs);
    void moveMap();
    String toString();
    String toStringProject();
    void updateMap(Scanner in, InputStreamReader in2) throws Exception;
    void addPackages(Scanner in) throws Exception;
    ArrayList<Robot> getRobots();
    Queue<Pacote> getPackages();
    ArrayList<FiguraGeometrica> getFigs();
}
