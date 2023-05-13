import java.util.*;

/**
 * @author Grupo7
 * @version correto-17 Amazon correto version 17.0.4 23/02/2023
 * */
public class Pacote {
    private Ponto start;
    private Ponto end;

    /** Construtor que cria um Pacote através de dois Pontos dados e por fim verifica se o Pacote está dentro dos limties do Mapa
     *
     * @param start     Ponto de recolha do Pacote
     * @param end       Ponto de entrega do Pacote
     */
    public Pacote(Ponto start, Ponto end){
        this.start = start;
        this.end = end;
        if(verification()){
            throw new IllegalArgumentException("Pacote:vi");
        }
    }

    /** Método que retorna o Ponto de recolha do pacote
     *
     * @return      Ponto
     */
    public Ponto getStart(){
        return this.start;
    }


    /** Método que retorna o Ponto de entrega do pacote
     *
     * @return      Ponto
     */
    public Ponto getEnd() {
        return this.end;
    }

    /** Método que verifica se o Pacote está dentro dos limties do Mapa
     *
     * @return  True se estiver dentro. False caso não
     */
    public boolean verification(){
        if(this.start.get_x() > 1000 || this.start.get_x() < 0)
            return true;
        else if(this.start.get_y() > 1000 || this.start.get_y() < 0)
            return true;
        if(this.end.get_x() > 1000 || this.end.get_x() < 0)
            return true;
        else if(this.end.get_y() > 1000 || this.end.get_y() < 0)
            return true;
        else
            return false;
    }


    /** toString do Pacote com a informação do Ponto de recolha e o Ponto de entrega
     *
     * @return  String
     */
    public String toString(){
        String print = "<Pacote: (" + this.start.get_x() + ";" + this.start.get_y() + ") (" + this.end.get_x() + ";" + this.end.get_y() + ")>";
        return print;
    }
}
