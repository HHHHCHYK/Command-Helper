package com.command_helper.display;

import com.command_helper.CommandManager;
import com.command_helper.display.screen.CommandHelperScreen;

public class DisplayController {
    CommandHelperScreen screen;

    boolean ScreenWasActive = false;

    public DisplayController(){
        this.screen = CommandHelperScreen.create();
    }

    public void OpenUI(){
        CommandManager.getInstance().getClient().setScreen(screen);
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


