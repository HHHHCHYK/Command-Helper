package com.command_helper.display;

import com.command_helper.CommandManager;
import com.command_helper.display.screen.CommandHelperScreen;
import net.minecraft.client.gui.screen.Screen;

public class DisplayController {
    CommandHelperScreen commandHelperScreen;

    boolean ScreenWasActive = false;

    public DisplayController(){
        this.commandHelperScreen = CommandHelperScreen.create();
    }

    public void OpenUI(){
        CommandManager.getInstance().getClient().setScreen(commandHelperScreen);
        ScreenWasActive = true;
    }

    public void CloseUI(){
        CommandManager.getInstance().getClient().setScreen(null);
        ScreenWasActive = false;
    }

    /**
    * @Description 翻转当前UI显示
    */
    public void ReverseUI(){
        if(ScreenWasActive)CloseUI();
        else OpenUI();
    }

    public Screen getScreen(CommandManager.Screens screens){
        switch (screens){
            case Command_Helper_Screen -> {
                return commandHelperScreen;
            }
            default -> {
                return null;
            }
        }
    }


    public void FlashUI(){

    }

    public void DrawTick(){

    }


    private void DrawBackGround(){

    }

    private void DrawElementNoInteract(){

    }

    private void DrawInteractElement(){

    }

    public boolean getScreenWasActive(){
        return ScreenWasActive;
    }
}


