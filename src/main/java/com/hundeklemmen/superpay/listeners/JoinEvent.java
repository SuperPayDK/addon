package com.hundeklemmen.superpay.listeners;

import com.hundeklemmen.superpay.Addon;
import com.hundeklemmen.superpay.Utils;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JoinEvent implements Consumer<ServerData> {


    private Addon adddon;

    public JoinEvent(Addon addon) {
        this.adddon = addon;
    }

    @Override
    public void accept(ServerData serverData) {
        this.adddon.server = serverData;
        String ip = serverData.getIp().toLowerCase();
        if(Utils.containsIgnoreCase(ip, "superawesome.dk") || Utils.containsIgnoreCase(ip, "superawesomeminecraftserver.com")){
            this.adddon.onSuperAwesome = true;
            if(this.adddon.verified == true) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§aSuperPay§8]§r §aDu er nu autoriseret med SuperPay"));
            } else {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§aSuperPay§8]§r §cFejl, kunne ikke autorisere dig med SuperPay!"));
            }
        } else {
            this.adddon.onSuperAwesome = false;
        }
        //God disabled by *Console*.
    }
}
