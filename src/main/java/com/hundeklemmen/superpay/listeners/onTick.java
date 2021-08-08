package com.hundeklemmen.superpay.listeners;

import com.hundeklemmen.superpay.Addon;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class onTick {


    private Addon addon;

    public onTick(Addon addon) {
        this.addon = addon;
    }

    @SubscribeEvent
    public void onTick( TickEvent.ClientTickEvent event ) {
        if(addon.openedGuiNextTick != null) {
            Minecraft.getMinecraft().displayGuiScreen(addon.openedGuiNextTick);
            addon.openedGuiNextTick = null;
        }
    }
}
