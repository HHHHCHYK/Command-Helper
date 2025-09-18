package com.command_helper.display.text;

import com.command_helper.CommandManager;
import com.command_helper.data.CommandData;
import com.command_helper.display.screen.CommandHelperScreen;
import com.sun.jna.platform.win32.Variant;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.Objects;

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

    //构造
    public ButtonWidgetHandle(int x, int y, int width, int height, CommandData data) {
        originX = x;
        originY = y;
        widgetWidth = width;
        widgetHeight = height;
        this.buttonWidget = ButtonWidget.builder(Text.literal(data.getName()), (buttonWidget) -> {
            //唯一作用故不作为参数传递
            CommandManager.getInstance().OnCommandButtonClicked(this.commandID);
            //Debug:测试按钮点击
            /*Objects.requireNonNull(CommandManager.getInstance().getClient().player)
                    .sendMessage(Text.literal("按钮被点击"),false);*/
        }).build();
        commandID = data.getId();
        this.name = data.getName();
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
        //context.drawBorder(originX + offsetX,originY + offsetY - widgetHeight/2,widgetWidth,widgetHeight, 0xFFFFFFFF);
        //buttonWidget.render(context,mouseX,mouseY,deltaTicks);
    }

    private boolean isVisible() {if (controller == null) return true;

        int btnLeft   = originX;
        int btnTop    = originY;
        int btnRight  = originX + widgetWidth;
        int btnBottom = originY + widgetHeight;

        int boxLeft   = controller.originX;
        int boxTop    = controller.originY;
        int boxRight  = controller.originX + controller.width;
        int boxBottom = controller.originY + controller.height;

        // 判断两个矩形是否相交
        return  btnRight > boxLeft &&
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

    ButtonWidget getTextFieldWidget() {
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


