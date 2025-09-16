package com.command_helper.display.text;

import com.command_helper.data.CommandData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class TextFieldWidgetHandle {
    private static TextRenderer renderer;
    public static TextRenderer getRenderer(){
        if(renderer == null) renderer = MinecraftClient.getInstance().textRenderer;
        if(renderer == null){
            throw new NullPointerException("Client's TextRender is null");
        }
        return renderer;
    }
    double originX;
    double originY;

    TextFieldWidget textFieldWidget;

    //构造
    public TextFieldWidgetHandle(int x,int y,int width,int height, Text text){
        originX = x;
        originY = y;
        this.textFieldWidget = new TextFieldWidget(getRenderer(),
                x,y,width,height,
                text
        );
    }

    //绘制
    public void Render(DrawContext context, int offsetX, int offsetY, int mouseX, int mouseY, float deltaTicks){

    }

    //getter
    public double getOriginX() {
        return originX;
    }

    public double getOriginY() {
        return originY;
    }
    TextFieldWidget getTextFieldWidget(){
        return textFieldWidget;
    }
}


