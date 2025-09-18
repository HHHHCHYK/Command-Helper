package com.command_helper.data;


import com.command_helper.CommandManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//读取和写入数据
public class DataController {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    String fileName = "Command_Data.json";
    String fileFullPath = "\\" + fileName;

    public CommandDataCollection ReadData() {
        try{
            Path path = Path.of(getGameDir(),"Command Helper" , fileName);
            Files.createDirectories(path.getParent());
            if(!path.toFile().exists())return null;
            String json = Files.readString(path);
            return objectMapper.readValue(json,CommandDataCollection.class);
        } catch (IOException e) {
            return null;
        }
    }

    public void WriteData(CommandDataCollection data) {
        try {
            Path path = Path.of(getGameDir(),"Command Helper" , fileName);
            Files.createDirectories(path.getParent());
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), data);
        } catch (IOException e) {
            String ee = e.getMessage();
            CommandManager.Log(CommandManager.LogType.Error,e.getMessage());
        }
    }

    public boolean HasCommandDataFile(){
        Path path = Path.of(getGameDir(),"Command Helper" , fileName);
        return path.toFile().exists();
    }


    String getGameDir() {
        return CommandManager.getInstance().getClient().runDirectory.getAbsolutePath();
    }

}
