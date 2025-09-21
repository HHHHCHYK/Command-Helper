package com.command_helper.display.text;

import com.command_helper.CommandManager;
import com.command_helper.data.CommandData;
import com.command_helper.display.screen.CommandHelperScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class ButtonWidgetHandle {
    private static TextRenderer renderer;

    public static TextRenderer getRenderer() {
        if (renderer == null) renderer = MinecraftClient.getInstance().textRenderer;
        if (renderer == null) {
            throw new NullPointerException("Client's TextRender is null");
        }
        return renderer;
    }

    int originX;
    int originY;

    int widgetWidth;
    int widgetHeight;

    ButtonWidgetsController controller;

    public int commandID = -1;
    public String name;

    public ButtonWidget buttonWidget;
    public ButtonWidget deleteButtonWidget;

    //构造
    public ButtonWidgetHandle(int x, int y, int width, int height,@NotNull  CommandData data) {
        originX = x;
        originY = y;
        widgetWidth = width;
        widgetHeight = height;

        //初始化命令按钮
        this.buttonWidget = ButtonWidget.builder(Text.literal(data.getName()), (buttonWidget) -> {
            //唯一作用故不作为参数传递
            CommandManager.getInstance().OnCommandButtonClicked(this.commandID);
            //Debug:测试按钮点击
            CommandManager.Log(CommandManager.LogType.Info, "按钮被点击");
        }).build();
        buttonWidget.setWidth((int) (widgetWidth * 0.8));
        buttonWidget.setHeight(widgetHeight);

        commandID = data.getId();
        this.name = data.getName();

        //初始化删除按钮
        deleteButtonWidget = ButtonWidget.builder(
                Text.literal("删除"),
                button -> {
                    CommandManager.Log(CommandManager.LogType.Info, "按钮被删除，CommandID = " + commandID);
                    CommandManager.getInstance().removeCommand(commandID);
                }
        ).build();
        deleteButtonWidget.setWidth((int) (widgetWidth * 0.1));
        deleteButtonWidget.setHeight(widgetHeight);
    }

    public void resetCommandData(@NotNull CommandData data){
        this.commandID = data.getId();
        this.name = data.name;
        buttonWidget.setMessage(Text.literal(data.name));
    }

    public void Init() {
        var screen = CommandManager.getInstance().getScreen(CommandManager.Screens.Command_Helper_Screen);
        if (isVisible() && screen instanceof CommandHelperScreen commandHelperScreen) {
            commandHelperScreen.addButtonChile(this);
        }
    }

    //绘制
    public void render(DrawContext context, int offsetX, int offsetY, int mouseX, int mouseY, float deltaTicks) {
        buttonWidget.setX(originX + offsetX);
        buttonWidget.setY(originY + offsetY);
        deleteButtonWidget.setX((int) (originX + offsetX + widgetWidth*0.9));
        deleteButtonWidget.setY(originY + offsetY);

        //context.drawBorder(originX + offsetX,originY + offsetY - widgetHeight/2,widgetWidth,widgetHeight, 0xFFFFFFFF);
        //buttonWidget.render(context,mouseX,mouseY,deltaTicks);
    }

    public void setPosition(int x,int y){
        originX = x;
        originY = y;
    }

    public void setWidth(int width){
        this.widgetWidth = width;
        buttonWidget.setWidth((int) (width * 0.8));
        deleteButtonWidget.setWidth((int) (width * 0.1));
    }

    public void setHeight(int height){
        widgetHeight = height;
        buttonWidget.setHeight(height);
        deleteButtonWidget.setHeight(height);
    }

    private boolean isVisible() {
        if (controller == null) return true;

        int btnLeft = originX;
        int btnTop = originY;
        int btnRight = originX + widgetWidth;
        int btnBottom = originY + widgetHeight;

        int boxLeft = controller.originX;
        int boxTop = controller.originY;
        int boxRight = controller.originX + controller.width;
        int boxBottom = controller.originY + controller.height;

        // 判断两个矩形是否相交
        return btnRight > boxLeft &&
                btnLeft < boxRight &&
                btnBottom > boxTop &&
                btnTop < boxBottom;
    }


    //getter
    public double getOriginX() {
        return originX;
    }

    public double getOriginY() {
        return originY;
    }

    ButtonWidget getButtonWidget() {
        return buttonWidget;
    }

    //setter
    public boolean setController(ButtonWidgetsController controller) {
        if (controller == null) return false;
        if (this.controller == null) {
            this.controller = controller;
            return true;
        } else return false;
    }


}


