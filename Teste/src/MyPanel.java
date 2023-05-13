import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MyPanel extends JPanel{
    private TexturePaint [] textures = new TexturePaint[0];
    private HashMap<FiguraGeometrica, TexturePaint> figsTexture = new HashMap<>();

    //Image image;
    private MapaInterface map;
    MyPanel(MyPanel previousPanel, MapaInterface map){
        super() ;
        this.setOpaque( false );
        this.setLayout( null );
        if(previousPanel != null)
            this.figsTexture = previousPanel.figsTexture;
        this.map = map;
        loadImages();
        checkfigs();
        this.setPreferredSize(new Dimension(1000,1000));
    }

    private void loadImages() {

        try {
            BufferedImage  brownCracked = ImageIO.read(new File("./images./textures/brownCracked.jpg"));
            BufferedImage  grayDirt = ImageIO.read(new File("./images./textures/grayDirt.jpg"));
            BufferedImage  sand = ImageIO.read(new File("./images./textures/sand.jpg"));
            BufferedImage  whiteCracked = ImageIO.read(new File("./images./textures/whiteCracked.jpg"));
            BufferedImage  whiteCracked2 = ImageIO.read(new File("./images./textures/whiteCracked2.jpg"));
            BufferedImage  whiteWood = ImageIO.read(new File("./images./textures/whiteWood.jpg"));
            TexturePaint[] textures = new TexturePaint[6];
            textures[0] = new TexturePaint(brownCracked, new Rectangle(0, 0, 1000, 1000));
            textures[1] = new TexturePaint(grayDirt, new Rectangle(0, 0, 1000, 1000));
            textures[2] = new TexturePaint(sand, new Rectangle(0, 0, 1000, 1000));
            textures[3] = new TexturePaint(whiteCracked, new Rectangle(0, 0, 1000, 1000));
            textures[4] = new TexturePaint(whiteCracked2, new Rectangle(0, 0, 1000, 1000));
            textures[5] = new TexturePaint(whiteWood, new Rectangle(0, 0, 1000, 1000));
            this.textures = textures;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void checkfigs(){
        if(this.map != null) {
            Random randomGenerator = new Random();
            for (FiguraGeometrica fig : this.map.getFigs()) {
                if (!figsTexture.containsKey(fig)) {
                    figsTexture.put(fig, textures[randomGenerator.nextInt(this.textures.length - 1)]);
                }
            }
        }
    }
private Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }

    public void paint(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;


        g2D.setColor(Color.GRAY);
        for(FiguraGeometrica figs : this.map.getFigs()){
            g2D.setPaint(figsTexture.get(figs));
            if(figs.returType() == 2){
                Circunferencia circ = (Circunferencia) figs;
                g2D.fillOval((int) circ.getCenter().get_x() - (int) circ.getRadius(), (int) circ.getCenter().get_y() - (int) circ.getRadius(), (int) circ.getRadius()*2, (int) circ.getRadius()*2);
            } else {
                Poligono pl = (Poligono) figs;
                g2D.fill(new Polygon(pl.getAllX(), pl.getAllY(), pl.getSize()));
            }
        }

        System.out.println(this.map.getRobots().size());
        for(Robot robot : this.map.getRobots()) {
            if(robot.getStat() == '-')
                g2D.setColor(Color.BLACK);
            else if(robot.getStat() == '*')
                g2D.setColor(Color.PINK);
            else if(robot.getStat() == '+')
                g2D.setColor(Color.GREEN);
            g2D.fillOval((int) robot.getLocation().get_x() - 40, (int) robot.getLocation().get_y() - 40, 40, 40);
        }

        g2D.setColor(Color.YELLOW);
        for(Pacote pack : this.map.getPackages())
            g2D.fillOval((int) pack.getStart().get_x() - 40, (int) pack.getStart().get_y() - 40, 40, 40 );
        g2D.setColor((Color.PINK));
        g2D.setBackground(Color.pink);
        g2D.dispose();
//        g2D.setColor(Color.RED);
//        g2D.fill(new Polygon(new int[] {400, 800, 500}, new int[]{400, 400, 200}, 3));

    }
}