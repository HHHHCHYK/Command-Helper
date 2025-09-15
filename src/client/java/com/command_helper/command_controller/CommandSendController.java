package com.command_helper.command_controller;

import com.command_helper.data_controller.CommandData;
import org.jetbrains.annotations.NotNull;

public class CommandSendController {
    CommandContainer container;

    public CommandSendController(@NotNull CommandContainer container){
        this.container = container;
    }

    //执行最后的发送命令
    public void SendCommand(String command){
        if(command.startsWith("/")){

        }
        else{

        }
    }
    //对上面方法的封装
    public void SendCommand(int id){

    }

    //获取id对应的Data
    private CommandData getCommandData(int id){
        return null;
    }


}
