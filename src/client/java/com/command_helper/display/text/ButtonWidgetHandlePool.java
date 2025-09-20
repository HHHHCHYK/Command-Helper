package com.command_helper.display.text;

import com.command_helper.data.CommandData;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class ButtonWidgetHandlePool {
    HashSet<ButtonWidgetHandle> allInstance = new HashSet<>();
    HashSet<ButtonWidgetHandle> activeInstance = new HashSet<>();

    final int DefaultCount = 10;

    public ButtonWidgetHandlePool() {
        int i = 0;
        while (i++ < DefaultCount) {
            allInstance.add(new ButtonWidgetHandle(0, 0, 0, 0, CommandData.getTestCommandData()));
        }
    }

    public ButtonWidgetHandle getInstance(int x, int y, int width, int height, @NotNull CommandData data) {
        ButtonWidgetHandle res;
        for(ButtonWidgetHandle handle : allInstance){
            if(!activeInstance.contains(handle))res = handle;
        }
        res = ExpandAndReturn();
        res.setPosition(x,y);
        res.setWidth(width);
        res.setHeight(height);
        res.resetCommandData(data);
        return res;
    }

    private void Expand(){
        int i = 0;
        while (i++ < DefaultCount) {
            allInstance.add(new ButtonWidgetHandle(0, 0, 0, 0, CommandData.getTestCommandData()));
        }
    }

    private ButtonWidgetHandle ExpandAndReturn(){
        int i =0;
        while (i++ < DefaultCount) {
            ButtonWidgetHandle handle = new ButtonWidgetHandle(0, 0, 0, 0, CommandData.getTestCommandData());
            allInstance.add(handle);
            if(i == DefaultCount)return handle;
        }

        ButtonWidgetHandle handle = new ButtonWidgetHandle(0, 0, 0, 0, CommandData.getTestCommandData());
        allInstance.add(handle);
        return handle;
    }

    public void remove(ButtonWidgetHandle handle){
        if(activeInstance.contains(handle)) activeInstance.remove(handle);
        else{
            allInstance.add(handle);//收归池有
        }
    }
}
