package states;

import entities.StyledButton;
import main.ActionManager;
import main.DisplayManager;
import main.StateManager;

import java.awt.*;

public class SelectGameState extends MenuState {

    @Override
    public void init() {
        Image image1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/menu/boton1.png"));
        Image image2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/menu/boton2.png"));
        Image image3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/menu/boton3.png"));
        Image imageEditada1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/menu2/boton1.png"));
        Image imageEditada2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/menu2/boton2.png"));
        Image imageEditada3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/menu2/boton3.png"));

        buttons.add(new StyledButton((DisplayManager.getInstance().getCanvas().getWidth()/2)-50,(DisplayManager.getInstance().getCanvas().getHeight()/4),105, 56, ActionManager.CHANGE_STATE, new int[]{StateManager.ORIGINALGAME}, "", image1, imageEditada1));
        buttons.add(new StyledButton((DisplayManager.getInstance().getCanvas().getWidth()/2)-50,(DisplayManager.getInstance().getCanvas().getHeight()/4 + 100),105, 56, ActionManager.CHANGE_STATE, new int[]{StateManager.ORIGINALGAME}, "",image2,imageEditada2));
        buttons.add(new StyledButton((DisplayManager.getInstance().getCanvas().getWidth()/2)-50,(DisplayManager.getInstance().getCanvas().getHeight()/4 + 200),105, 56, ActionManager.CHANGE_STATE, new int[]{StateManager.ORIGINALGAME}, "", image3, imageEditada3));

        this.entities.addAll(buttons);
        currentButton = 2;
        super.init();
    }

}
