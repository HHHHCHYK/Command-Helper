package com.command_helper.command_container;

import com.command_helper.CommandManager;
import com.command_helper.data.CommandData;
import com.command_helper.data.CommandDataCollection;
import com.command_helper.data.DataController;

import java.util.HashMap;

/**
* @Description
* @Author Hykal_311
* @Date
*/
public class CommandContainer {

    //构造函数
    public CommandContainer(){
        Initialize();//构造时初始化
    }

    private final HashMap<Integer, CommandData> dataHashMap = new HashMap<>();
    private DataController dataController;

    protected boolean WasInit;

    //初始化
    public void Initialize(){
        if(dataController == null) dataController = new DataController();
        CommandDataCollection dataController = this.dataController.ReadData();
        if(dataController == null || dataController.data.isEmpty()){
            CommandManager.Log(CommandManager.LogType.Error,"读取数据失败");
            return;
        }
        else{
            CommandManager.Log(CommandManager.LogType.Info,"成功加载Command Data,Data数量为 " + dataController.data.size());
        }
        for(CommandData data : dataController.data){
            dataHashMap.put(data.getId(),data);
        }
        WasInit = true;
    }

    //重新初始化
    public void ReInitialize(){
        WasInit = false;
        Initialize();
    }

    //执行发送命令的逻辑
    public CommandData getCommandData(int id){
        return dataHashMap.getOrDefault(id, null);
    }

    //添加新命令
    public void addCommand(String name, String command) {
        if(name == null || command == null)return;
        int id = GetNewId();
        CommandData data = new CommandData(id,name,command);
        dataHashMap.put(id,data);
        SaveCommandContainer();//修改时保存一次,之后添加Log相关代码
    }

    public void removeCommand(int id){
        if(!dataHashMap.containsKey(id))return;
        dataHashMap.remove(id);
        SaveCommandContainer();//修改时保存一次,之后添加Log相关代码
    }

    public CommandData[] getCurrentData(){
//        if(dataHashMap.isEmpty()){
//            //添加测试命令
//            CommandData data = new CommandData(1,"Test-Command","/time set day");
//            dataHashMap.put(data.getId(),data);
//        }
        return dataHashMap.values().toArray(new CommandData[0]);
    }

    public void SaveCommandContainer(){
        dataController.WriteData(new CommandDataCollection(getCurrentData()));
    }

    public int GetNewId(){
        if(dataHashMap.isEmpty()){
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
