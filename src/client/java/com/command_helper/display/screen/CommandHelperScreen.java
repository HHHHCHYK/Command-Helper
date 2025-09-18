package com.command_helper.display.screen;

import com.command_helper.CommandManager;
import com.command_helper.display.text.ButtonWidgetHandle;
import com.command_helper.display.text.ButtonWidgetsController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CommandHelperScreen extends Screen {
    public static final String ScreenTitle = "Command Helper";

    int margin = 0;
    int WidgetPerPage = 10;

    //整个屏幕
    int windowWidth;
    int windowHeight;

    //顶部Bar
    String BarTextPath = "gui/button";
    Identifier BarTexture = Identifier.of(CommandManager.MOD_ID_LOW,BarTextPath);
    int TopBarHeight = 20;


    //多行文本组件
    ButtonWidgetsController buttonWidgetsController;
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
            windowHeight = this.height;
            windowWidth = this.width;
        }

        //初始化多行按钮组件
        if(buttonWidgetsController == null){
            buttonWidgetsController = ButtonWidgetsController.of(textX,textY, (int) (windowWidth * 0.9),this.height, CommandManager.getInstance().getCurrentData());
        }
        buttonWidgetsController.Init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks){
        super.render(context, mouseX, mouseY, deltaTicks);//默认打开Screen -- 模糊背景，游戏暂停

        renderButtonWidgets(context, mouseX, mouseY, deltaTicks);
        renderTopBar(context, mouseX, mouseY, deltaTicks);
        renderExitButton();
    }

    @Override
    public boolean shouldPause(){
        return false;
    }

    ButtonWidget exitButton;
    ButtonWidget addButton;
    private void renderExitButton(){
        if(exitButton == null){
            exitButton = ButtonWidget.builder(
                    Text.literal("<"),
                    button -> {
                        CommandManager.getInstance().displayController.CloseUI();
                    }
            ).build();
        }
    }

    private void renderAddButton(){
        if(addButton == null){
            addButton = ButtonWidget.builder(
                    Text.literal("<"),
                    button -> {
                        CommandManager.getInstance().displayController.CloseUI();
                    }
            ).build();
        }
    }
    //绘制顶框
    private void renderTopBar(DrawContext context, int mouseX, int mouseY, float deltaTicks){
//        context.drawTexture(RenderPipelines.GUI_TEXTURED,BarTexture,
//                0,0,0,0,
//                windowWidth,TopBarHeight,200,20);
        //context.drawGuiTexture(RenderPipelines.GUI,BarTexture,0,0,windowWidth,TopBarHeight);
    }

    //绘制文本组件
    private void renderButtonWidgets(DrawContext context, int mouseX, int mouseY, float deltaTicks){
        if(buttonWidgetsController == null){
            return;
        }
        buttonWidgetsController.render(context, mouseX, mouseY, deltaTicks);
    }

    public void addButtonChile(ButtonWidgetHandle widgetHandle){
        //判断内容
        if(widgetHandle == null || widgetHandle.buttonWidget == null)return;//判空
        addDrawableChild(widgetHandle.buttonWidget);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount){
        if(buttonWidgetsController != null) {
            int offset = (int) verticalAmount * 5;
            buttonWidgetsController.mouseScrolled(-1 * offset);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }


    public static CommandHelperScreen create(){
        return new CommandHelperScreen(Text.literal(ScreenTitle));
    }

}
