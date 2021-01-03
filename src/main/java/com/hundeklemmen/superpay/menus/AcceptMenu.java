package com.hundeklemmen.superpay.menus;

import net.labymod.core.LabyModCore;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.gui.elements.Scrollbar;
import net.labymod.main.LabyMod;
import net.labymod.utils.DrawUtils;
import net.labymod.utils.ModColor;
import net.labymod.utils.ServerData;
import net.labymod.utils.manager.ServerInfoRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;

public class AcceptMenu extends GuiScreen {
    //Default attributes for direct connect screen

    private Scrollbar scrollbar = new Scrollbar(18);

    /*
     * Copied default constructor
     */
    public AcceptMenu() {
    }

    /*
     * Copied from ModGuiScreenServerList
     */

    public void updateScreen() {

    }

    /*
     * modified default initGui method
     */
    public void initGui() {
        super.initGui();

        this.scrollbar.init();
        this.scrollbar.setPosition(this.width / 2 + 122, 44, this.width / 2 + 126, this.height - 32 - 3);

        this.buttonList.add(new GuiButton(9914, this.width / 3, this.height - 75, this.width / 3, 20, "Accept"));
        this.buttonList.add(new GuiButton(9913, this.width / 3, this.height - 50, this.width / 3, 20, "Afvis"));


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

        if(button.id == 9913){
            Minecraft.getMinecraft().thePlayer.sendChatMessage("Du trykkede på knappen!");
        }

    }

    /*
     * edited default drawscreen method
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        LabyMod.getInstance().getDrawUtils().drawAutoDimmedBackground(this.scrollbar.getScrollY());
        double yPos = 45.0D + this.scrollbar.getScrollY() + 3.0D;


        LabyMod.getInstance().getDrawUtils().drawCenteredString("§aSuperPay", this.width / 2, 20, 2);

        LabyMod.getInstance().getDrawUtils().drawCenteredString("§aØnsker du at godkende følgende transaktion? ", this.width / 2, 50, 1);
        //Server
        LabyMod.getInstance().getDrawUtils().drawRightString("§aServer:", this.width / 2 - 25, 100, 1);
        LabyMod.getInstance().getDrawUtils().drawString("§bPink", this.width / 2 + 25, 100, 1);
        //Pakke
        LabyMod.getInstance().getDrawUtils().drawRightString("§aPakke:", this.width / 2 - 25, 125, 1);
        LabyMod.getInstance().getDrawUtils().drawString("§bVIP", this.width / 2 + 25, 125, 1);
        //Pris
        LabyMod.getInstance().getDrawUtils().drawRightString("§aPris:", this.width / 2 - 25, 150, 1);
        LabyMod.getInstance().getDrawUtils().drawString("§b100.000 Emeralder", this.width / 2 + 25, 150, 1);
        //LabyMod.getInstance().getDrawUtils().drawDynamicImageUrl("image", "https://stacket.dk/img/branding.c241aaa0.png", this.width / 2, this.height / 2, this.width / 3, this.height / 3, this.width / 3, this.height / 3);

        this.scrollbar.draw();

        super.drawScreen(mouseX, mouseY, partialTicks);

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
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
