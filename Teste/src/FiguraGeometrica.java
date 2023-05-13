
/** Classe abstrata que vai buscar os métodos dos filhos
 * @author Alexandre Carvalho
 * @version correto-17 Amazon correto version 17.0.4 23/02/2023
 * */
public abstract class FiguraGeometrica {
    public abstract boolean check_hit(SegmentoReta srHit);
    public abstract String stringToPrint(SegmentoReta srHit);
    public abstract boolean figVerification(Ponto p);
    public abstract String toString();
    public abstract Ponto[] check_hitSegment(SegmentoReta seg);
    public abstract int returType();
}
