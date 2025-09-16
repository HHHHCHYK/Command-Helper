package com.command_helper.display.text;

import com.command_helper.data.CommandData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class TextFieldWidgetsController {

    ArrayList<TextFieldWidgetHandle> handles;
    int offset = 0;

    int originX = 0;
    int originY = 0;
    int width = 0;
    int height = 0;

    public TextFieldWidgetsController(int x,int y, ArrayList<TextFieldWidgetHandle> handles){
        this.handles = handles;
    }

    public void Render(DrawContext context, int mouseX, int mouseY, float deltaTicks){
        //逐一调用每个元素的Draw
    }

    public static TextFieldWidgetsController of(int x,int y,int width,int height, CommandData[] dataList){
        int offset = 0;
        ArrayList<TextFieldWidgetHandle> list = new ArrayList<>();
        //逐个命令分析
        for (CommandData data : dataList){
            TextFieldWidgetHandle handle = new TextFieldWidgetHandle(x,y + offset,width,height, Text.literal(data.toString()));
            list.add(handle);
            offset += height;
        }
        return new TextFieldWidgetsController(x,y,list);
    }

    boolean inArea(int x,int y){
        return x > originX && x < originX + width && y > originY && y < originY + height;
    }

}
