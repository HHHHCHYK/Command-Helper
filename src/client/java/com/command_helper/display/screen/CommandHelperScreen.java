package com.command_helper.display.screen;

import com.command_helper.CommandManager;
import com.command_helper.display.text.TextFieldWidgetsController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;

public class CommandHelperScreen extends Screen {
    public static final String ScreenTitle = "Command Helper";

    int margin = 0;

    //整个屏幕
    int windowWidth;
    int windowHeight;

    //顶部Bar
    RenderPhase.Texture BarTexture;
    int TopBarHeight = 20;


    //多行文本组件
    TextFieldWidgetsController textFieldWidgetsController;
    int textX = margin;
    int textY = TopBarHeight;

    protected CommandHelperScreen(Text title) {
        super(title);
    }

    @Override
    protected void init(){
        //获取屏幕长宽
        if(MinecraftClient.getInstance() != null && MinecraftClient.getInstance().getWindow() != null){
            Window window = MinecraftClient.getInstance().getWindow();
            windowHeight = window.getHeight();
            windowWidth = window.getWidth();
        }

        //初始化多行文本组件
        if(textFieldWidgetsController == null){
            textFieldWidgetsController = TextFieldWidgetsController.of(textX,textY,windowWidth,windowHeight - TopBarHeight, CommandManager.getInstance().getCurrentData());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks){
        //super的行为：
        //遍历Screen的Drawable，并且执行render
        super.render(context, mouseX, mouseY, deltaTicks);


//        renderBackground();
//        renderTextFieldWidgets();
//        renderTopBar();//最后绘制封顶
    }

    //绘制背景
    protected void renderBackground(){}

    //绘制顶框
    protected void renderTopBar(){}

    //绘制文本组件
    protected void renderTextFieldWidgets(){}


    public static CommandHelperScreen create(){
        return new CommandHelperScreen(Text.literal(ScreenTitle));
    }
}
