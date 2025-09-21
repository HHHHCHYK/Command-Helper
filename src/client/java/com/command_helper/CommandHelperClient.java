package com.command_helper;

import net.fabricmc.api.ClientModInitializer;

public class CommandHelperClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		CommandManager.getInstance().Initialize();//初始化
		CommandManager.Log("CommandManager init finished");
	}

	private void Debug(){

	}

}