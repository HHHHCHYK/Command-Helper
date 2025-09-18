package com.command_helper.data;

import java.util.ArrayList;

public class CommandDataCollection {
    public ArrayList<CommandData> data = new ArrayList<>();

    public static CommandDataCollection getTestCollection(){
        CommandDataCollection n = new CommandDataCollection();
        n.data.add(CommandData.TestCommandData());
        return n;
    }
}
