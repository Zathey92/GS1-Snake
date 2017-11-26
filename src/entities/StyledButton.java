package entities;

import main.DisplayManager;

import java.awt.*;


public class StyledButton extends Button {

    private int width,heigth;
    private Image imageSelected, imageNotSelected;


    public StyledButton(int x, int y, int width, int heigth, int action, int[] param, String string, String imageNotSelected, String imageSelected) {
        super(x, y, width, heigth, action, param, string);
        this.heigth = heigth;
        this.width = width;
        this.imageNotSelected = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/menu/" + imageNotSelected));
        this.imageSelected = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/menu/" + imageSelected));
    }

    @Override
    public void init() {
        selected = false;
    }

    @Override
    public void render(Graphics g) {
        if(selected){
            g.drawImage(imageSelected,x,y,width,heigth,DisplayManager.getInstance().getRootPane());
            return;
        }
        g.drawImage(imageNotSelected,x,y,width,heigth,DisplayManager.getInstance().getRootPane());
    }

}
