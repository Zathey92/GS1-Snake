package entities;

import main.DisplayManager;

import java.awt.*;


public class StyledButton extends Button {

    private int width, height;
    private String stringSelected, stringNotSelected;
    private Image imageSelected, imageNotSelected;


    public StyledButton(int x, int y, int width, int height, int action, int[] param, String string, String stringNotSelected, String stringSelected) {
        super(x, y, width, height, action, param, string);
        this.height = height;
        this.width = width;
        this.stringNotSelected = stringNotSelected;
        this.stringSelected = stringSelected;

    }

    @Override
    public void init() {
        super.init();
        selected = false;
        this.imageNotSelected = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/menu/" + stringNotSelected));
        this.imageSelected = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/menu/" + stringSelected));
    }

    @Override
    public void render(Graphics g) {
        if(selected){
            g.drawImage(imageSelected,x,y,width, height,DisplayManager.getInstance().getRootPane());
            return;
        }
        g.drawImage(imageNotSelected,x,y,width, height,DisplayManager.getInstance().getRootPane());
    }

}
