package a2.cg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.*;
import javax.imageio.ImageIO;



public class Game extends JPanel
{
    private BufferedImage bg;
    private Hero hero;
    private Comida comida;
    private ArrayList <Inimigo> inimigos = new ArrayList<Inimigo>();
    private boolean cr_cima = false, cr_baixo = false, cr_esquerda = false, cr_direita = false;
    private boolean pause = true, rodando = false;
    private int pontuacao = 0, vidas = 3, born_time = 0, speed = 1;
    
    public Game() 
    {
        try
        {
            addKeyListener(new KeyListener()
            {
                @Override
                public void keyTyped(KeyEvent e) 
                {

                }
                @Override
                public void keyReleased(KeyEvent e) 
                {
                    switch (e.getKeyCode()) 
                    {                      
                        case KeyEvent.VK_SPACE:
                                speed = 1;
                        break;
                    }
                }
                @Override
                public void keyPressed(KeyEvent e)
                {
                    switch (e.getKeyCode()) 
                    {                      
                        case KeyEvent.VK_UP: 
                            cr_cima=true;
                            cr_baixo=false;
                            cr_esquerda=false;
                            cr_direita=false;
                        break;
                        case KeyEvent.VK_DOWN:
                            cr_baixo=true;
                            cr_cima=false;
                            cr_esquerda=false;
                            cr_direita=false;
                        break;
                        case KeyEvent.VK_LEFT: 
                            cr_esquerda=true;
                            cr_cima=false;
                            cr_baixo=false;
                            cr_direita=false;
                        break;
                        case KeyEvent.VK_RIGHT: 
                            cr_direita=true;
                            cr_cima=false;
                            cr_baixo=false;
                            cr_esquerda=false;
                        break;
                        case KeyEvent.VK_SPACE:
                                speed = 3;
                        break;
                        case KeyEvent.VK_ENTER:
                            if(rodando)
                            {
                                rodando = false;
                                pause = true;
                            }
                            else
                            {
                                rodando = true;
                                pause = false;
                            }
                    }
                }
            });

            hero = new Hero();
            comida = new Comida();
            
            inimigos.add(0, new Inimigo());

            bg = ImageIO.read(getClass().getResource("/imgs/bg.png"));

            setFocusable(true);
            setLayout(null);

            new Thread(new Runnable()
            {
                @Override
                public void run()
                {              
                    try 
                    {
                        gameLoop();
                    } 
                    catch (InterruptedException ex) 
                    {
                        System.out.println("Erro: "+ex.getMessage());
                    }
                }
            }).start();
            }
        catch(Exception exp)
        {
            System.out.println("Erro: "+exp.getMessage());
        }
    }
    
    public void gameLoop() throws InterruptedException
    {
        while(true)
        {
            while(this.rodando)
            {
                handlerEvents();
                update();
                render();
                Thread.sleep(5);
                this.born_time++;
                if(this.vidas == 0)
                    this.rodando = false;
            }
            
            while(this.pause)
            {
                System.out.println("");    
            }
            hero.setVelocidade_x(0);
            hero.setVelocidade_y(0);
        }  
    }
    
    public void handlerEvents()
    {
        if(this.cr_cima)
        {
            hero.setVelocidade_y(-1);
            hero.setVelocidade_x(0);
            hero.setImage("/imgs/hero2UP.png");
        }
        else if(this.cr_baixo)
        {
            hero.setVelocidade_y(1);
            hero.setVelocidade_x(0);
            hero.setImage("/imgs/hero2DOWN.png");
        }
        else if(this.cr_esquerda)
        {
            hero.setVelocidade_x(-1);
            hero.setVelocidade_y(0);
            hero.setImage("/imgs/hero2LEFT.png");
        }
        else if(this.cr_direita)
        {
            hero.setVelocidade_x(1);
            hero.setVelocidade_y(0);
            hero.setImage("/imgs/hero2RIGHT.png");
        }
    }
    
    public void update()
    {
        confereColisao();
        hero.setPosicao_x(hero.getPosicao_x() + hero.getVelocidade_x() * speed);
        hero.setPosicao_y(hero.getPosicao_y() + hero.getVelocidade_y() * speed);
        
        inimigos.forEach((inimigo) ->
        {
            confereColisaoInimigo(inimigo);
            inimigo.setPosicao_x(inimigo.getPosicao_x() + inimigo.getVelocidade_x());
            inimigo.setPosicao_y(inimigo.getPosicao_y() + inimigo.getVelocidade_y());
        });
        
        if(this.born_time == 1000)
        {
            gerarNovoInimigo();
            this.born_time = 0;
        }
    }
    
    public void render()
    {
        repaint();
    }
    
    private void confereColisao() 
    {
        try
        {
            inimigos.forEach( (inimigo) -> 
            {
                try
                {
                    double hipotenusa = Math.sqrt(Math.pow((hero.getCentro().x - inimigo.getCentro().x), 2) + Math.pow((hero.getCentro().y - inimigo.getCentro().y), 2));
                    if (hipotenusa <= hero.getRaio() + inimigo.getRaio()) 
                    {
                        System.out.println("Bateu no inimigo");
                        limparRound();
                        throw new Exception();
                    }
                }
                catch(Exception exp){}
            });
        }
        catch(Exception exp){}
    
        
        
        if((hero.getPosicao_x() + hero.getRaio()*2) >= Tela.width)
        {
            limparRound();
        }
        else if(hero.getPosicao_x() <= 0)
        {
            limparRound();
        }
        else if((hero.getPosicao_y() + hero.getRaio()*2)  >= Tela.height-25)
        {
            limparRound();
        }
        else if(hero.getPosicao_y() <= 0)
        {
            limparRound();
        }
        
        if(isEating())
            comer();
    }
    
    private void comer()
    { 
        //cobra.add(new Hero(ponto.x , ponto.y));
        this.pontuacao++;
        if((this.pontuacao % 10) == 0)
            this.vidas++;
        gerarNovaComida();
    }
    
    private boolean isEating()
    {
        double hipotenusa = Math.sqrt(Math.pow((hero.getCentro().x - comida.getCentro().x), 2)+Math.pow((hero.getCentro().y - comida.getCentro().y), 2)); 
        return hipotenusa <= hero.getRaio() + comida.getRaio();        
    }
    
    private void gerarNovaComida()
    {
        comida.setarPosicaoAleatoria();
    }
    
    private void gerarNovoInimigo()
    {
        inimigos.add(inimigos.size(), new Inimigo());
    }
    
    public void confereColisaoInimigo(Inimigo inimigo) 
    {
        if (((inimigo.getPosicao_x() + inimigo.getRaio()*2) >= Tela.width) || (inimigo.getPosicao_x() <= 0))
        {
            inimigo.setVelocidade_x(inimigo.getVelocidade_x() * -1);
        }
        
        if (((inimigo.getPosicao_y() + inimigo.getRaio()*2+30) >= Tela.height) || (inimigo.getPosicao_y() <= 17)) 
        {
           inimigo.setVelocidade_y(inimigo.getVelocidade_y() * -1);
        } 
    }
    
    private void limparRound()
    {
        this.vidas--;
        hero.setarPosicaoAleatoria();
        hero.setVelocidade_x(0);
        hero.setVelocidade_y(0);
        inimigos.clear();
        inimigos.add(0, new Inimigo());
        this.born_time = 0;
        this.pause = true;
        this.rodando = false;
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        super.paintComponent(g);
        
        g2d.drawImage(bg, 0, 0, null);
        g2d.drawImage(hero.getImg(), hero.getPosicao_x(), hero.getPosicao_y(), this);
        g2d.drawImage(comida.getImg(), comida.getPosicao_x(), comida.getPosicao_y(), this);
        g2d.drawString("Pontuação: "+this.pontuacao, 2, 12);
        g2d.drawString("Vidas: "+this.vidas, 100, 12);
        
        inimigos.forEach((inimigo) ->
        {
            g2d.drawImage(inimigo.getImg(), inimigo.getPosicao_x(), inimigo.getPosicao_y(), this);
        });
    }
}
