package com.command_helper.data;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CommandData {
    @JsonProperty
    int id; //命令编号
    @JsonProperty
    String name = "New-Command"; //命令数据
    @JsonProperty
    String command; //命令主体S

    public CommandData(int id,String command){
        if(command.startsWith("/")){
            this.command = command;
        }
    }

    public boolean isEmpty(){
        return command.isEmpty();
    }

    public String getCommand(){
        return command;
    }

    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }

    @Override
    public String toString(){
        return command;
    }

    public static CommandData TestCommandData(){
        return new CommandData(-1,"/time set day");
    }
}

