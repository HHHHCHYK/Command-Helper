package com.command_helper.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CommandData {
    @JsonProperty
    public int id; //命令编号
    @JsonProperty
    public String name = "New-Command"; //命令数据
    @JsonProperty
    public String command; //命令主体S

    @JsonCreator
    public CommandData(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("command") String command){
        if(command.startsWith("/")){
            this.id = id;
            this.name = name;
            this.command = command;
        }
    }


    @JsonIgnore
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

    public static CommandData getTestCommandData(){
        return new CommandData(-1,"Test-Command","/time set day");
    }
}

