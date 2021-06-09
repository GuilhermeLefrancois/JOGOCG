package a2.cg;

import static a2.cg.Tela.width;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;

public class Inimigo 
{
    private int posicao_x;
    private int posicao_y;
    private int velocidade_x;
    private int velocidade_y;
    private static final int raio = 15;
    private BufferedImage img;
    
    public Inimigo()
    {
        try
        {
            img = ImageIO.read(getClass().getResource("/imgs/inimigo.png"));
            atualizarVelocidade();
            System.out.println("VX: "+velocidade_x+" VY: "+velocidade_y);
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
            this.posicao_x = ponto.x - (raio*2) - 5;
        if(ponto.x == 0)
            this.posicao_x = 5;
        else
            this.posicao_x = ponto.x;
        
        if(ponto.y+raio*2 >= Tela.height)
            this.posicao_y = ponto.y - (raio*2) - 40;
        if(ponto.y == 0)
            this.posicao_y = 25;
        else
            this.posicao_y = ponto.y;
    }
    
    public void atualizarVelocidade()
    {
        int aux_vel_x = new Random().nextInt(3);
        this.velocidade_x = (aux_vel_x == 2) ? 1 : (aux_vel_x == 1) ? 0 : -1;

        int aux_vel_y = new Random().nextInt(3);
        this.velocidade_y = (aux_vel_y == 2) ? 1 : (aux_vel_y == 1) ? 0 : -1;
        this.velocidade_y = (velocidade_x == 0 && velocidade_y == 0) ? 1 : this.velocidade_y; 
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
