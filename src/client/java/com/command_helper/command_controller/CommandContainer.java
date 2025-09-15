package com.command_helper.command_controller;

import com.command_helper.data_controller.CommandData;
import com.command_helper.data_controller.DataController;

import java.util.HashMap;

public class CommandContainer {

    //构造函数
    public CommandContainer(){
        Initialize();//构造时初始化
    }

    private HashMap<Integer, CommandData> dataHashMap;
    private DataController controller;

    protected boolean WasInit;

    //初始化
    public void Initialize(){

        WasInit = true;
    }

    //重新初始化
    public void ReInitialize(){
        WasInit = false;
        Initialize();
    }

    //执行发送命令的逻辑
    public CommandData getCommandData(int id){
        return dataHashMap.get(id);
    }

    //添加新命令
    public void AddCommand(String command) {
        if(command == null)return;
        int id = GetNewId();
        CommandData data = new CommandData(id,command);
        dataHashMap.put(id,data);
        SaveCommandContainer();//修改时保存一次,之后添加Log相关代码
    }

    public boolean RemoveCommand(int id){
        if(!dataHashMap.containsKey(id))return false;
        dataHashMap.remove(id);
        SaveCommandContainer();//修改时保存一次,之后添加Log相关代码
        return true;
    }

    public boolean SaveCommandContainer(){
        return true;
    }

    private int GetNewId(){
        if(dataHashMap == null || dataHashMap.isEmpty()){
            return 1;
        }
        else{
            var keyArr = dataHashMap.keySet();
            int id = 0;
            while(keyArr.contains(++id));   //从1开始计数，返回第一个不存在的Key值
            return id;
        }
    }
}
