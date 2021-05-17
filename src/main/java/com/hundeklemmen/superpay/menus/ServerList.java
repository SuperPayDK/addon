package com.hundeklemmen.superpay.menus;

import com.hundeklemmen.superpay.Addon;
import com.hundeklemmen.superpay.classes.Server;
import net.labymod.gui.elements.Scrollbar;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerList extends GuiScreen {

    private Scrollbar scrollbar = new Scrollbar(50);
    private Addon addon;
    private Boolean interacted = false;
    private List<Server> serverList = new ArrayList<Server>(){};

    public ServerList(List<Server> serverList, Addon addon) {
        this.serverList = serverList;
        this.addon = addon;
    }

    public void updateScreen() {

    }

    public void initGui() {
        super.initGui();

        this.scrollbar.init();
        this.scrollbar.setPosition(this.width / 2 + 122, 44, this.width / 2 + 126, this.height - 32 - 3);

       // this.buttonList.add(new GuiButton(9914, this.width / 3, this.height - 75, this.width / 3, 20, "Accept"));
       // this.buttonList.add(new GuiButton(9913, this.width / 3, this.height - 50, this.width / 3, 20, "Afvis"));


    }

    /*
     * Edited default onGuiClosed() method
     */
    public void onGuiClosed() {
        // Default things
        Keyboard.enableRepeatEvents(false);
    }

    /*
     * edited function to handle button click
     * partly copied
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);


        //Accepter
        if(button.id == 9914){
            System.out.println("Rolf");
        }
        //Afvis
        else if(button.id == 9913){
            System.out.println("Rolf");
        }
        interacted = true;
        Minecraft.getMinecraft().player.closeScreen();
        System.out.println("Rolf");

    }

    /*
     * edited default drawscreen method
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        LabyMod.getInstance().getDrawUtils().drawAutoDimmedBackground(this.scrollbar.getScrollY());
        double yPos = 8.0D + this.scrollbar.getScrollY() + 3.0D;


        LabyMod.getInstance().getDrawUtils().drawCenteredString("§aSuperPay §bServer Liste", this.width / 2, 20, 2);

        int Start = (int) yPos;
        for (Server server : serverList) {
            if (Start < 8) {
                Start += 42;
            } else {
                Start += 50;
                if(server.isPartner() == true){
                    LabyMod.getInstance().getDrawUtils().drawImageUrl(server.getCustomImage(), this.width / 4, Start - 15, 255.0D, 255.0D, 32.0D, 32.0D);
                    LabyMod.getInstance().getDrawUtils().drawString("§a" + capitalizeString(server.getName()), this.width / 3, Start-15.0D, 1.2);
                    LabyMod.getInstance().getDrawUtils().drawRightString("§a" + server.getOnline() + "§b/§a" + server.getMax(), this.width / 2 + 115, Start-15.0D, 1.2);
                    LabyMod.getInstance().getDrawUtils().drawString("§f" + server.getDescription1(), this.width / 3, Start-2.5D, 0.9);
                    LabyMod.getInstance().getDrawUtils().drawString("§f" + server.getDescription2(), this.width / 3, Start+5.0D, 0.9);
                } else {
                    LabyMod.getInstance().getDrawUtils().drawImageUrl("https://crafatar.com/avatars/" + server.getOwner() + "?size=32&overlay=true", this.width / 4, Start - 15, 255.0D, 255.0D, 32.0D, 32.0D);
                    LabyMod.getInstance().getDrawUtils().drawString("§f" + capitalizeString(server.getName()), this.width / 3, Start - 15.0D, 1.2);
                    LabyMod.getInstance().getDrawUtils().drawRightString("§7" + server.getOnline() + "/" + server.getMax(), this.width / 2 + 115, Start - 15.0D, 1.2);
                    LabyMod.getInstance().getDrawUtils().drawString("§7" + server.getDescription1(), this.width / 3, Start - 2.5D, 0.9);
                    LabyMod.getInstance().getDrawUtils().drawString("§7" + server.getDescription2(), this.width / 3, Start + 5.0D, 0.9);
                }
                //42

            }
        }
        //LabyMod.getInstance().getDrawUtils().drawDynamicImageUrl("image", "https://stacket.dk/img/branding.c241aaa0.png", this.width / 2, this.height / 2, this.width / 3, this.height / 3, this.width / 3, this.height / 3);

        this.scrollbar.update(serverList.size());
        this.scrollbar.draw();
        Mouse.setGrabbed(false);
        super.drawScreen(mouseX, mouseY, partialTicks);

    }

    public static String capitalizeString(String str) {
        String retStr = str;
        try { // We can face index out of bound exception if the string is null
            retStr = str.substring(0, 1).toUpperCase() + str.substring(1);
        }catch (Exception e){}
        return retStr;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);

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
                    Minecraft.getMinecraft().player.sendChatMessage("/server " + server.getName());
                }
            }
        }
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);

        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        this.scrollbar.mouseInput();
    }

}
