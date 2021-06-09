package a2.cg;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Hero
{
    private int posicao_x;
    private int posicao_y;
    private static final int raio = 22;
    private int velocidade_x;
    private int velocidade_y;
    private BufferedImage img;

    public Hero(int posicao_x, int posicao_y) 
    {
        try
        {
            this.posicao_x = posicao_x;
            this.posicao_y = posicao_y;
            this.velocidade_x = 0;
            this.velocidade_y = 0;
            img = ImageIO.read(getClass().getResource("/imgs/hero2UP.png"));
        }
        catch(Exception exp)
        {
            System.out.println("Exp: "+exp.getMessage());
        }
    }
    
    public Hero() 
    {
        try
        {
            this.velocidade_x = 0;
            this.velocidade_y = 0;
            img = ImageIO.read(getClass().getResource("/imgs/hero2UP.png"));
            setarPosicaoAleatoria();
        }
        catch(Exception exp)
        {
            System.out.println("Exp: "+exp.getMessage());
        }
    }
    
    public void setarPosicaoAleatoria()
    {
        Point ponto = Tela.geraPosicaoAleatoria();
        if(ponto.x+raio*2 >= Tela.width)
            this.posicao_x = ponto.x -(raio*2) - 40;
        if(ponto.x == 0)
            this.posicao_y = 5;
        else
            this.posicao_x = ponto.x;
        
        if(ponto.y+raio*2 >= Tela.height)
            this.posicao_y = ponto.y -(raio*2) - 5;
        if(ponto.y == 0)
            this.posicao_y = 25;
        else
            this.posicao_y = ponto.y;
    }
    
    public void setImage(String caminho)
    {
        try 
        {
            this.img = ImageIO.read(getClass().getResource(caminho));
        } 
        catch (IOException exp) 
        {
             System.out.println("Exp: "+exp.getMessage());
        }
    }
    
    public Point getCentro() 
    {
        return new Point(this.posicao_x + this.raio, this.posicao_y + this.raio);
    }
     
    public int getPosicao_x() 
    {
        return posicao_x;
    }

    public void setPosicao_x(int posicao_x) 
    {
        this.posicao_x = posicao_x;
    }

    public int getPosicao_y() 
    {
        return posicao_y;
    }

    public void setPosicao_y(int posicao_y) 
    {
        this.posicao_y = posicao_y;
    }

    public int getRaio() 
    {
        return raio;
    }

    public int getVelocidade_x() 
    {
        return velocidade_x;
    }

    public void setVelocidade_x(int velocidade_x) 
    {
        this.velocidade_x = velocidade_x;
    }

    public int getVelocidade_y() 
    {
        return velocidade_y;
    }

    public void setVelocidade_y(int velocidade_y) 
    {
        this.velocidade_y = velocidade_y;
    }
    
     public BufferedImage getImg() {
        return img;
    }
}
