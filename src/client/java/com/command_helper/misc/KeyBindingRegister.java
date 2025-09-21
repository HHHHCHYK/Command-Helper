package com.command_helper.misc;

import com.command_helper.CommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;

import java.util.HashMap;

public class KeyBindingRegister {
    private HashMap<Integer,KeyBinding> Keys = new HashMap<>();
    static int cnt = 0;
    public KeyBinding registry(int GLFWCode){
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key."+ CommandManager.MOD_ID_LOW + ".event_" + cnt++,
                GLFWCode,
                "category." + CommandManager.MOD_ID_LOW + ".controls"
                )
        );
    }

    public void AddEvent(int code,Runnable event){
        if(Keys == null)Keys = new HashMap<>();
        if(!Keys.containsKey(code)){
            KeyBinding keyBinding = registry(code);
            Keys.put(code,keyBinding);
        }
        CommandManager.getInstance().getClient().send(()->{
            ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
                while(Keys.get(code).wasPressed()){//用while防止按键次数丢失
                    event.run();
                }
            });
        });
    }
}
