package com.command_helper.command_container;

import com.command_helper.CommandManager;
import com.command_helper.data.CommandData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

public class CommandSendController {
    //依赖的类
    CommandContainer container;     //命令存储器

    //文本成员
    String NoCommandWarningText = "命令格式错误，请检查";


    public CommandSendController(@NotNull CommandContainer container){
        this.container = container;
    }

    /**
    * &#064;Description  执行最后的发送动作
    * * @param command 发送文本
    * @Author Hykal_311
    * @Date
    */
    private void SendCommand(String command){
        ClientPlayerEntity player = CommandManager.getInstance().getPlayerClient();
        if(player == null){
            CommandManager.Log( this + ":Player is null");
            return;
        }
        if(command.startsWith("/")){
            //读取所有的命令
            String[] commands = command.split(";");
            var client = CommandManager.getInstance().getClient();
            client.execute(()-> client.send(()->{//延迟1帧执行命令
                for(String ss : commands){
                    if(ss.startsWith("/")){
                        CommandManager.getInstance().getPlayerClient().networkHandler.sendChatCommand(ss.substring(1));
                    }
                }
                    })
            );
        }
        else{
            CommandManager.Log("Command Format Wrong");
            player.sendMessage(getNotCommandWarning(),false);
        }
    }

    //对上面方法的封装
    public void SendCommand(int id){
        CommandData data = CommandManager.getInstance().commandContainer.getCommandData(id);
        if(data != null && !data.isEmpty()){
            SendCommand(data.toString());
        }
        else{
            CommandManager.Log("Cannot find data with id");
        }
    }

    //--getter--
    /**
    * @Description 获取id对应的Data，封装于CommandContainer
    * * @param id 命令id
    * @return 返回命令实例
    * @Author Hykal_311
    */
    private CommandData getCommandData(int id){
        return CommandManager.getInstance().commandContainer.getCommandData(id);
    }

    private Text getNotCommandWarning(){
        MutableText text = Text.literal(NoCommandWarningText);
        text.setStyle(Style.EMPTY
                .withColor(Formatting.RED)
                //这里插入点击事件
        );
        return text;
    }
}
