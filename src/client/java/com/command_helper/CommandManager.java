package com.command_helper;

import com.command_helper.command_container.CommandContainer;
import com.command_helper.command_container.CommandSendController;
import com.command_helper.data.CommandData;
import com.command_helper.display.DisplayController;
import com.command_helper.display.screen.CommandHelperScreen;
import com.command_helper.misc.KeyBindingRegister;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.lwjgl.glfw.GLFW;

public class CommandManager {
    //单例
    private static CommandManager instance;
    private static MinecraftClient client;
    private static ClientPlayerEntity playerClient;

    //全局常量
    public static final String MOD_ID = "COMMAND_HELPER";
    public static final String MOD_ID_LOW = "command_helper";

    //成员--组织整个系统
    public CommandContainer commandContainer;
    public CommandSendController commandSendController;
    public DisplayController displayController;
    public KeyBindingRegister keyBindingRegister;



    public CommandManager(){
        Initialize();
    }

    void Initialize(){
        commandContainer = new CommandContainer();//必须先初始化这个
        commandSendController = new CommandSendController(commandContainer);
        displayController = new DisplayController();
        keyBindingRegister = new KeyBindingRegister();
        //注册事件
        EventRegistry();
    }

    void EventRegistry(){
        //按T反转Gui状态
        keyBindingRegister.AddEvent(GLFW.GLFW_KEY_T,()->{
            displayController.ReverseUI();
        });
    }

    public void OnCommandUsed(int id){

    }

    public CommandData[] getCurrentData(){
        return commandContainer.getCurrentData();
    }

    public MinecraftClient getClient(){
        if(client == null)client = MinecraftClient.getInstance();
        if(client != null)return client;
        else throw new NullPointerException("Client is null");
    }

    public ClientPlayerEntity getPlayerClient(){
        if(playerClient == null)playerClient = getClient().player;
        if(playerClient != null)return playerClient;
        else throw new NullPointerException("Client.Player is null");
    }

    public static CommandManager getInstance(){
        if(instance == null)instance = new CommandManager();
        return instance;
    }
}
