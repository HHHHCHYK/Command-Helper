package com.command_helper;

import com.command_helper.command_container.CommandContainer;
import com.command_helper.command_container.CommandSendController;
import com.command_helper.data.CommandData;
import com.command_helper.data.CommandDataCollection;
import com.command_helper.data.DataController;
import com.command_helper.display.DisplayController;
import com.command_helper.display.screen.CommandHelperScreen;
import com.command_helper.misc.KeyBindingRegister;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandManager {
    //单例
    private static CommandManager instance;
    private static MinecraftClient client;
    private static ClientPlayerEntity playerClient;

    //全局常量
    public static final String MOD_ID = "COMMAND_HELPER";
    public static final String MOD_ID_LOW = "command_helper";
    private static Logger LOGGER = LoggerFactory.getLogger("CommandHelper");

    //成员--组织整个系统
    public CommandContainer commandContainer;
    public CommandSendController commandSendController;
    public DisplayController displayController;
    public KeyBindingRegister keyBindingRegister;
    public DataController dataController;



    void Initialize(){
        if(commandContainer == null)commandContainer = new CommandContainer();//必须先初始化这个
        if(commandSendController == null) commandSendController = new CommandSendController(commandContainer);
        if(displayController == null) displayController = new DisplayController();
        if(keyBindingRegister == null) keyBindingRegister = new KeyBindingRegister();
        if(dataController == null)dataController = new DataController();
        //注册事件
        EventRegistry();

        //Debug
        if(!dataController.HasCommandDataFile()){
            dataController.WriteData(CommandDataCollection.getTestCollection());
        }
    }
    void EventRegistry(){
        //按T反转Gui状态
        keyBindingRegister.AddEvent(GLFW.GLFW_KEY_T,()->{
            displayController.ReverseUI();
        });
    }

    //容器操作
    public void addCommand(String name,String command){
        if(commandContainer == null)return;
        commandContainer.addCommand(name,command);
        //刷新Container
        commandContainer.ReInitialize();
        //刷新UI
         ((CommandHelperScreen)getScreen(Screens.Command_Helper_Screen)).flashButtonList(getCurrentData()); //获取Command Data并且刷新UI
    }

    public void removeCommand(int id){
        if(commandContainer == null)return;
        commandContainer.removeCommand(id);
        //刷新Container
        commandContainer.ReInitialize();
        //刷新UI
        ((CommandHelperScreen)getScreen(Screens.Command_Helper_Screen)).flashButtonList(getCurrentData()); //获取Command Data并且刷新UI
    }


    public void OnCommandButtonClicked(int id){
        commandSendController.SendCommand(id);
    }

    public CommandData[] getCurrentData(){
        return commandContainer.getCurrentData();
    }

    public MinecraftClient getClient(){
        client = MinecraftClient.getInstance();
        if(client != null)return client;
        else throw new NullPointerException("Client is null");
    }

    public ClientPlayerEntity getPlayerClient(){
        playerClient = getClient().player;
        if(playerClient != null)return playerClient;
        else throw new NullPointerException("Client.Player is null");
    }

    public enum Screens{
        Command_Helper_Screen
    }

    public Screen getScreen(Screens screens){
        return getDisplayController().getScreen(screens);
    }

    public DisplayController getDisplayController(){
        if(displayController == null)displayController = new DisplayController();
        return displayController;
    }

    public static CommandManager getInstance(){
        if(instance == null)instance = new CommandManager();
        return instance;
    }

    private CommandContainer getCommandContainer(){
        if(commandContainer == null){
            commandContainer = new CommandContainer();
        }
        return commandContainer;
    }

    public int GetNewID(){
        return commandContainer.GetNewId();
    }

    public static void Log(String logText){
        if(LOGGER == null) {
            LOGGER = LoggerFactory.getLogger("Command_Helper");
        }
        Log(LogType.Info,logText);
    }

    public static void Log(LogType type,String logText){
        switch (type){
            case Info -> LOGGER.info(logText);
            case Error -> LOGGER.error(logText);
            case Warning -> LOGGER.warn(logText);
            default -> LOGGER.info(logText);
        }
    }


    public enum LogType{
        Info,
        Warning,
        Error
    }

}
