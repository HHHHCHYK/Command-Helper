package com.command_helper.display.text;

import com.command_helper.CommandManager;
import com.command_helper.data.CommandData;
import com.command_helper.display.screen.CommandHelperScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.Iterator;

public class ButtonWidgetsController {

    ArrayList<ButtonWidgetHandle> handles;
    int offset = 0;

    int originX = 0;
    int originY = 0;

    int width;
    int height;

    protected ButtonWidgetsController(int x, int y,int width, int height, ArrayList<ButtonWidgetHandle> handles) {
        this.handles = handles;
        originX = x;
        originY = y;
        this.width = width;
        this.height = height;
        handles.forEach(widgetHandle -> {
            if(!widgetHandle.setController(this)){
                CommandManager.Log(CommandManager.LogType.Warning,"设置按钮控制器失败");
            }
        });
    }

    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        Iterator<ButtonWidgetHandle> iterator = handles.iterator();
        while (iterator.hasNext()) {
            var handle = iterator.next();
            handle.render(context, 0, offset, mouseX, mouseY, deltaTicks);
        }
    }

    public void Init() {
        offset = 0;
        for (ButtonWidgetHandle handle : handles) {
            if (handle == null) continue;
            handle.Init();
        }
    }

    //生成方法
    public static ButtonWidgetsController of(int x, int y, int width, int height, CommandData[] dataList) {
        int offset = 0;
        ArrayList<ButtonWidgetHandle> list = new ArrayList<>();
        //逐个命令分析
        for (CommandData data : dataList) {
            list.add(new ButtonWidgetHandle(x, y + offset, width, 24, data));
            offset += height;
        }
        return new ButtonWidgetsController(x, y,width,height, list);
    }

    //检测鼠标滚轮，滑动所有按钮，并且不渲染对话框外的元素
    public void mouseScrolled(int offset) {
        this.offset = Math.clamp(offset + this.offset, 0,handles.size() * handles.getFirst().widgetHeight);
    }

    boolean inArea(int x, int y) {
        return x > originX && x < originX + width && y > originY && y < originY + height;
    }

}
