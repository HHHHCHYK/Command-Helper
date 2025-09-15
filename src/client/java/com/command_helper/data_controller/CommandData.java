package com.command_helper.data_controller;

public class CommandData {
    int id;
    String name = "New-Command";
    String command;

    public CommandData(int id,String command){
        if(command.startsWith("/")){
            this.command = command;
        }
    }

    public boolean isEmpty(){
        return command.isEmpty();
    }
}

