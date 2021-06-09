package a2.cg;

import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Comida 
{
    private int posicao_x;
    private int posicao_y;
    private static final int raio = 15;
    private BufferedImage img;
    
    public Comida() 
    {
        try
        {  
            img = ImageIO.read(getClass().getResource("/imgs/food.png"));
        }
        catch(Exception exp)
        {
            System.out.println("Exp: "+exp.getMessage());
        }
        setarPosicaoAleatoria();
    }
    
    public void setarPosicaoAleatoria()
    {
        Point ponto = Tela.geraPosicaoAleatoria();
        if(ponto.x+(raio*2) >= Tela.width)
            this.posicao_x = ponto.x -(raio*2) - 5;
        else if(ponto.x <= 0)
            this.posicao_x = 5;
        else
            this.posicao_x = ponto.x;

        if(ponto.y+(raio*2) >= Tela.height)
            this.posicao_y = ponto.y -(raio*2) - 50;
        else if(ponto.y <= 0)
            this.posicao_y = 25;
        else
            this.posicao_y = ponto.y;
    }
    
    public Point getCentro() 
    {
        return new Point(this.posicao_x + this.raio, this.posicao_y + this.raio);
    }

    public int getPosicao_x() {
        return posicao_x;
    }

    public void setPosicao_x(int posicao_x) {
        this.posicao_x = posicao_x;
    }

    public int getPosicao_y() {
        return posicao_y;
    }

    public void setPosicao_y(int posicao_y) {
        this.posicao_y = posicao_y;
    }

    public static int getRaio() {
        return raio;
    }

    public BufferedImage getImg() {
        return img;
    }
}
