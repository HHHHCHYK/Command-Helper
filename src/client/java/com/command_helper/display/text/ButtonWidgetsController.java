package com.command_helper.display.text;

import com.command_helper.CommandManager;
import com.command_helper.data.CommandData;
import net.minecraft.client.gui.DrawContext;

import java.util.*;

public class ButtonWidgetsController {

    ArrayList<ButtonWidgetHandle> activeHandles;
    static ButtonWidgetHandlePool pool;

    int offset = 0;

    int originX;
    int originY;

    int width;
    int height;

    int ElementHeight;

    protected ButtonWidgetsController(int x, int y, int width, int height,int elementHeight, ArrayList<ButtonWidgetHandle> handles) {
        this.activeHandles = handles;
        originX = x;
        originY = y;
        this.width = width;
        this.height = height;
        ElementHeight = elementHeight;
        activeHandles.forEach(widgetHandle -> {
            if (!widgetHandle.setController(this)) {
                CommandManager.Log(CommandManager.LogType.Warning, "设置按钮控制器失败");
            }
        });
    }

    public void renderHandles(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        for (ButtonWidgetHandle handle : activeHandles) {
            handle.render(context, 0, offset, mouseX, mouseY, deltaTicks);
        }
    }
    public void init() {
        for (ButtonWidgetHandle handle : activeHandles) {
            if (handle == null) continue;
            handle.Init();
        }
    }
    public void flashHandles(CommandData[] dataList) {
        activeHandles.forEach(widgetHandle -> pool.remove(widgetHandle));
        activeHandles.clear();
        for(var data : dataList){
            activeHandles.add(pool.getInstance(originX,originY,width,ElementHeight,data));
        }
        gridButtonWidgetHandles();
    }

    private void gridButtonWidgetHandles(){
        int offset = 0;
        for(var handle : activeHandles){
            handle.setPosition(originX,originY + offset);
            offset += ElementHeight;
        }
    }


    //生成方法
    public static ButtonWidgetsController of(int x, int y, int width, int height, CommandData[] dataList, int elementHeight) {
        if(pool == null)pool = new ButtonWidgetHandlePool();
        int offset = 0;
        ArrayList<ButtonWidgetHandle> list = new ArrayList<>();
        //逐个命令分析
        for (CommandData data : dataList) {
            list.add(pool.getInstance(x, y + offset, width, elementHeight, data));
            offset += elementHeight;
        }
        var controller = new ButtonWidgetsController(x, y, width, height,elementHeight, list);
        controller.ElementHeight = elementHeight;
        return controller;
    }

    //检测鼠标滚轮，滑动所有按钮，并且不渲染对话框外的元素
    public void mouseScrolled(int offset) {
        this.offset = Math.clamp(offset + this.offset, 0, activeHandles.size() * activeHandles.getFirst().widgetHeight);
    }

    boolean inArea(int x, int y) {
        return x > originX && x < originX + width && y > originY && y < originY + height;
    }
}
