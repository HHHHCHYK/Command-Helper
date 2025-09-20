package com.command_helper.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
* @Description  序列化类
* @Author Hykal311
*/
public class CommandDataCollection {
    @JsonProperty
    public ArrayList<CommandData> data = new ArrayList<>();

    public static CommandDataCollection getTestCollection(){
        CommandDataCollection n = new CommandDataCollection();
        n.data.add(CommandData.getTestCommandData());
        return n;
    }

    public CommandDataCollection(){
    }

    public CommandDataCollection(CommandData[] dataList){
        if(dataList == null)return;
        for(var data : dataList){
            if(data == null)return;
            this.data.add(data);
        }
    }
}
