package com.hundeklemmen.superpay.menus;

import com.google.gson.JsonObject;
import com.hundeklemmen.superpay.Addon;
import com.hundeklemmen.superpay.classes.TransactionRequest;
import net.labymod.gui.elements.Scrollbar;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class AcceptMenu extends GuiScreen {

    private Scrollbar scrollbar = new Scrollbar(18);
    private TransactionRequest anmodning;
    private Addon addon;
    private Boolean interacted = false;

    public AcceptMenu(TransactionRequest anmodning, Addon addon) {
        this.anmodning = anmodning;
        this.addon = addon;
    }

    public void updateScreen() {

    }

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
        if(interacted == false) {
            JsonObject responseObject = new JsonObject();
            responseObject.addProperty("type", "transaction_response");
            responseObject.addProperty("key", anmodning.getKey());
            responseObject.addProperty("accepted", false);
            addon.websocketHandler.send(responseObject.toString());
            addon.getApi().displayMessageInChat("§8[§aSuperPay§8]§r §cDu har nu afvist købet af §4" + anmodning.getPakke() + " §cpå serveren §4" + anmodning.getServer());
        }
    }

    /*
     * edited function to handle button click
     * partly copied
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("type", "transaction_response");
        responseObject.addProperty("key", anmodning.getKey());
        //Accepter
        if(button.id == 9914){
            responseObject.addProperty("accepted", true);
        }
        //Afvis
        else if(button.id == 9913){
            responseObject.addProperty("accepted", false);
        }
        interacted = true;
        Minecraft.getMinecraft().player.closeScreen();
        addon.websocketHandler.send(responseObject.toString());

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
        LabyMod.getInstance().getDrawUtils().drawString("§b" + anmodning.getServer(), this.width / 2 + 25, 100, 1);
        //Pakke
        LabyMod.getInstance().getDrawUtils().drawRightString("§aPakke:", this.width / 2 - 25, 125, 1);
        LabyMod.getInstance().getDrawUtils().drawString("§b" + anmodning.getPakke(), this.width / 2 + 25, 125, 1);
        //Pris
        LabyMod.getInstance().getDrawUtils().drawRightString("§aPris:", this.width / 2 - 25, 150, 1);
        LabyMod.getInstance().getDrawUtils().drawString("§b" + Addon.decimalFormat.format(anmodning.getAmount()) + " Emeralder", this.width / 2 + 25, 150, 1);
        //LabyMod.getInstance().getDrawUtils().drawDynamicImageUrl("image", "https://stacket.dk/img/branding.c241aaa0.png", this.width / 2, this.height / 2, this.width / 3, this.height / 3, this.width / 3, this.height / 3);

        this.scrollbar.draw();

        Mouse.setGrabbed(false);
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
