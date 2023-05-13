import javax.swing.*;
import java.awt.*;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MyFrame extends JFrame{

    private MyPanel panel;
    private MapaInterface map;

    MyFrame(MapaInterface map) throws Exception {
        Scanner in = new Scanner(System.in);
        InputStreamReader in2 = new InputStreamReader(System.in);
        this.map = map;
        //panel = new MyPanel(map);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(1000, 1000));
        this.setLocationRelativeTo(null);
        ImageIcon background = new ImageIcon("./images/background.jpg");
        Image imageT = background.getImage();
        Image back = imageT.getScaledInstance(1000,1000,Image.SCALE_SMOOTH);
        background = new ImageIcon(back);
        JLabel imagem = new JLabel("",background,JLabel.CENTER);
        imagem.setBounds(0,0,1000,1000);
        this.add(imagem);
        this.setVisible(true);

        while (true){
            this.map.updateMap(in, in2);
            panel = new MyPanel(panel, map);
            this.getContentPane().removeAll();
            this.repaint();
            this.add(panel);
            this.pack();
            Thread.sleep(100);
        }
    }
}