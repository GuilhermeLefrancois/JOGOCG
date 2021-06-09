package a2.cg;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tela 
{
    public static final int height = Toolkit.getDefaultToolkit().getScreenSize().height - 40;
    public static final int width = Toolkit.getDefaultToolkit().getScreenSize().height - 40;
    
    public Tela()
    {
        JFrame janela = new JFrame("Last Hungry Warrior Pizza's fã");
        janela.setSize(Tela.width, Tela.height);
        janela.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.width)/2, 0);
        janela.setResizable(false);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Game game = new Game();
        game.setPreferredSize(new Dimension(Tela.width, Tela.height));
        
        janela.getContentPane().add(game);
        janela.setVisible(true);
    }
    
  public static Point geraPosicaoAleatoria() 
  {
    int ponto_x = new Random().nextInt(width);
    int ponto_y = new Random().nextInt(height);
    return new Point((ponto_x < 0) ? ponto_x*-1 : ponto_x, (ponto_y < 0) ? ponto_y*-1 : ponto_y);
  }
  
}
