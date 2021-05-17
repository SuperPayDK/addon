package com.hundeklemmen.superpay.menus;

import com.google.gson.JsonObject;
import com.hundeklemmen.superpay.Addon;
import com.hundeklemmen.superpay.classes.Server;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.labymod.gui.elements.Scrollbar;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerList extends Screen {

    private Scrollbar scrollbar = new Scrollbar(50);
    private Addon addon;
    private Boolean interacted = false;
    private List<Server> serverList = new ArrayList<Server>(){};

    public ServerList(List<Server> serverList, Addon addon) {
        super(new StringTextComponent("ServerList"));
        this.serverList = serverList;
        this.addon = addon;
    }

    public void init() {
        super.init();

        this.scrollbar.init();
        this.scrollbar.setPosition(this.width / 2 + 122, 44, this.width / 2 + 126, this.height - 32 - 3);

    }
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        LabyMod.getInstance().getDrawUtils().drawAutoDimmedBackground(matrixStack, this.scrollbar.getScrollY());
        double yPos = 8.0D + this.scrollbar.getScrollY() + 3.0D;
        drawCenteredString(matrixStack, this.font, "§aSuperPay §bServer Liste", this.width / 2, 20, 2);
        int Start = (int) yPos;
        for (Server server : serverList) {
            if (Start < 8) {
                Start += 42;
            } else {
                Start += 50;
                if(server.isPartner() == true){
                    LabyMod.getInstance().getDrawUtils().drawImageUrl(matrixStack, server.getCustomImage(), this.width / 4, Start - 15, 255.0D, 255.0D, 32.0D, 32.0D);
                    LabyMod.getInstance().getDrawUtils().drawString(matrixStack, "§a" + capitalizeString(server.getName()), this.width / 3, Start-15.0D, 1.2);
                    LabyMod.getInstance().getDrawUtils().drawRightString(matrixStack, "§a" + server.getOnline() + "§b/§a" + server.getMax(), this.width / 2 + 115, Start-15.0D, 1.2);
                    LabyMod.getInstance().getDrawUtils().drawString(matrixStack, "§f" + server.getDescription1(), this.width / 3, Start-2.5D, 0.9);
                    LabyMod.getInstance().getDrawUtils().drawString(matrixStack, "§f" + server.getDescription2(), this.width / 3, Start+5.0D, 0.9);
                } else {
                    LabyMod.getInstance().getDrawUtils().drawImageUrl(matrixStack, "https://crafatar.com/avatars/" + server.getOwner() + "?size=32&overlay=true", this.width / 4, Start - 15, 255.0D, 255.0D, 32.0D, 32.0D);
                    LabyMod.getInstance().getDrawUtils().drawString(matrixStack, "§f" + capitalizeString(server.getName()), this.width / 3, Start - 15.0D, 1.2);
                    LabyMod.getInstance().getDrawUtils().drawRightString(matrixStack, "§7" + server.getOnline() + "/" + server.getMax(), this.width / 2 + 115, Start - 15.0D, 1.2);
                    LabyMod.getInstance().getDrawUtils().drawString(matrixStack, "§7" + server.getDescription1(), this.width / 3, Start - 2.5D, 0.9);
                    LabyMod.getInstance().getDrawUtils().drawString(matrixStack, "§7" + server.getDescription2(), this.width / 3, Start + 5.0D, 0.9);
                }
                //42

            }
        }
        this.scrollbar.update(serverList.size());
        this.scrollbar.draw();

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public static String capitalizeString(String str) {
        String retStr = str;
        try { // We can face index out of bound exception if the string is null
            retStr = str.substring(0, 1).toUpperCase() + str.substring(1);
        }catch (Exception e){}
        return retStr;
    }

    public void onClose() {

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);

        this.scrollbar.mouseAction((int) mouseX, (int) mouseY, Scrollbar.EnumMouseAction.CLICKED);

        System.out.println("Y: " + mouseY);
        double yPos = 8.0D + this.scrollbar.getScrollY() + 3.0D;

        int Start = (int) yPos;
        for (Server server : serverList) {
            if (Start < 8) {
                Start += 42;
            } else {
                Start += 50;
                Integer realStart = Start - 15;
                Integer realEnd = realStart + 32;
                if(mouseY > realStart && mouseY < realEnd && mouseX > this.width / 4 && mouseX < this.width / 2 + 120){
                    System.out.println("REE: " + server.getName());
                    sendMessage("/server " + server.getName());
                }
            }
        }
        return true;
    }
    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        this.scrollbar.mouseAction((int) mouseX, (int) mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        this.scrollbar.mouseAction((int) mouseX, (int) mouseY, Scrollbar.EnumMouseAction.RELEASED);
        return true;
    }

}
