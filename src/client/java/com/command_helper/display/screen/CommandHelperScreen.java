package com.command_helper.display.screen;

import com.command_helper.CommandManager;
import com.command_helper.data.CommandData;
import com.command_helper.display.text.ButtonWidgetHandle;
import com.command_helper.display.text.ButtonWidgetsController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CommandHelperScreen extends Screen {
    public static final String ScreenTitle = "Command Helper";

    //私有常量
    final int margin = 0;
    final int ElementHeight = 24;
    final int ElementGap = 24;
    final int MaxCommandLength = 1000000000;

    //整个屏幕
    int windowWidth;
    int windowHeight;

    //顶部Bar
    String BarTextPath = "gui/button";
    Identifier BarTexture = Identifier.of(CommandManager.MOD_ID_LOW, BarTextPath);
    int TopBarHeight = 20;

    //按钮组件
    ButtonWidget exitButton;
    ButtonWidget addButton;

    //AddCanvas的组件
    TextFieldWidget NameText;
    TextFieldWidget CommandText;
    ButtonWidget ConfirmButton;

    //多行文本组件
    ButtonWidgetsController buttonWidgetsController;
    int textX = margin;
    int textY = TopBarHeight;

    CanvasType CurrentCanvas = CanvasType.NormalCanvas;

    protected CommandHelperScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        //获取屏幕长宽
        if (MinecraftClient.getInstance() != null && MinecraftClient.getInstance().getWindow() != null) {
            Window window = MinecraftClient.getInstance().getWindow();
            windowHeight = this.height;
            windowWidth = this.width;
        }
        //初始化多行按钮组件
        if (buttonWidgetsController == null) {
            buttonWidgetsController = ButtonWidgetsController.of(textX, textY, windowWidth, this.height, CommandManager.getInstance().getCurrentData(),ElementHeight);
        }

        renderCurrentCanvas();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);//默认打开Screen -- 模糊背景

        //处理一些每帧更新逻辑
        if (CurrentCanvas == CanvasType.NormalCanvas) {
            if (buttonWidgetsController == null) {
                return;
            }
            buttonWidgetsController.renderHandles(context, mouseX, mouseY, deltaTicks);//处理更新逻辑
        }
    }

    public void ChangeCanvas() {
        clearChildren();
        if (CurrentCanvas.equals(CanvasType.NormalCanvas)) {
            CurrentCanvas = CanvasType.AddCanvas;
        } else {
            CurrentCanvas = CanvasType.NormalCanvas;
        }
        renderCurrentCanvas();
    }

    private void renderCurrentCanvas() {
        clearChildren();    //清除所有Children
        renderExitButton();

        if (CurrentCanvas == CanvasType.NormalCanvas) {
            //渲染当前NormalCanvas
            buttonWidgetsController.init();
            renderAddButton();
        } else {
            //渲染AddCanvas
            renderNameText();           //命令名
            renderCommandText();        //命令文本
            renderConfirmButton();      //确认按钮
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private void renderExitButton() {
        if (exitButton == null) {
            exitButton = ButtonWidget.builder(
                    Text.literal("<"),
                    button -> {
                        if(this.CurrentCanvas == CanvasType.NormalCanvas)
                            CommandManager.getInstance().displayController.CloseUI();
                        else if(this.CurrentCanvas == CanvasType.AddCanvas){
                            ChangeCanvas();
                        }
                    }
            ).build();
            exitButton.setHeight(TopBarHeight);
            exitButton.setWidth((int) (width * 0.1));
            exitButton.setX(0);
            exitButton.setY(0);
        }
        addDrawableChild(exitButton);
    }

    public void flashButtonList(CommandData[] data){
        buttonWidgetsController.flashHandles(data);
        init();
    }

    private void renderAddButton() {
        if (addButton == null) {
            addButton = ButtonWidget.builder(
                    Text.literal("+"),
                    button -> {
                        ChangeCanvas();
                    }
            ).build();
            addButton.setHeight(TopBarHeight);
            addButton.setWidth((int) (width * 0.1));
            addButton.setX((int) (width * 0.9));
            addButton.setY(0);
        }
        addDrawableChild(addButton);
    }

    private void renderNameText() {
        if (NameText == null) {
            NameText = new TextFieldWidget(
                    textRenderer,
                    (int) (windowWidth * 0.5), ElementHeight,
                    Text.literal("命令名称")
            );

            NameText.setText("Command Name");

            NameText.setWidth((int) (windowWidth * 0.3));
            NameText.setHeight(24);
            NameText.setX((int) (width * 0.35));
            NameText.setY(height / 2 - ElementHeight/2 - ElementGap - ElementHeight);//从中间算起，减去半个元素高度（CommandText），减去一个间隔和减去本身的高度
        }
        addDrawableChild(NameText);
    }

    private void renderCommandText() {
        if (CommandText == null) {
            CommandText = new TextFieldWidget(
                    textRenderer,
                    (int) (windowWidth * 0.5), 24,
                    Text.literal("命令内容")
            );
            CommandText.setTextPredicate(string -> string.matches("^[\\x00-\\x7F]*$"));
            CommandText.setText("Command");
            CommandText.setWidth((int) (windowWidth * 0.3));
            CommandText.setHeight(24);
            CommandText.setX((int) (width * 0.35));
            CommandText.setY(height / 2 - ElementHeight / 2);
            CommandText.setMaxLength(MaxCommandLength);
        }
        addDrawableChild(CommandText);
    }

    private void renderConfirmButton() {
        if (ConfirmButton == null) {
            ConfirmButton = ButtonWidget.builder(
                    Text.literal("确认"),
                    button -> {
                        //检查文字是否合法
                        String commandText = CommandText.getText();
                        if (!commandText.startsWith("/")) {//不以‘/’开头
                            CommandManager.getInstance().getPlayerClient().sendMessage(Text.literal("命令必须以‘/’开头"), false);
                            return;
                        }
                        //创建新物品
                        CommandManager.getInstance().addCommand(NameText.getText(), CommandText.getText());
                        //切换窗口
                        ChangeCanvas();
                    }
            ).build();
            ConfirmButton.setWidth((int) (windowWidth * 0.3));
            ConfirmButton.setHeight(24);
            ConfirmButton.setX((int) (width * 0.35));
            ConfirmButton.setY(height / 2 + ElementHeight/2 + ElementGap);
        }
        addDrawableChild(ConfirmButton);
    }


    /**
     * @param widgetHandle 需要被添加的按钮Handles
     * @return None
     * @Description 添加按钮系统
     * @Author Hykal_311
     * @Date
     */
    public void addButtonChile(ButtonWidgetHandle widgetHandle) {
        //判断内容
        if (widgetHandle == null || widgetHandle.buttonWidget == null) return;  //判空
        addDrawableChild(widgetHandle.buttonWidget);
        addDrawableChild(widgetHandle.deleteButtonWidget);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (buttonWidgetsController != null) {
            int offset = (int) verticalAmount * 5;
            buttonWidgetsController.mouseScrolled(-1 * offset);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }


    public static CommandHelperScreen create() {
        return new CommandHelperScreen(Text.literal(ScreenTitle));
    }

    public enum CanvasType {
        NormalCanvas,
        AddCanvas
    }

//    //绘制顶框
//    private void renderTopBar(DrawContext context, int mouseX, int mouseY, float deltaTicks){
//        context.drawTexture(RenderPipelines.GUI_TEXTURED,BarTexture,
//                0,0,0,0,
//                windowWidth,TopBarHeight,200,20);
//        context.drawGuiTexture(RenderPipelines.GUI,BarTexture,0,0,windowWidth,TopBarHeight);
//    }

}
