package com.command_helper;

public class CommandManager {
    //单例
    private CommandManager instance;
    public CommandManager getInstance(){
        if(instance == null)instance = new CommandManager();
        return instance;
    }

    public CommandManager(){
        Initialize();
    }

    void Initialize(){

    }

    public void OnCommandUsed(int id){

    }
}
