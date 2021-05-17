package com.hundeklemmen.superpay.menus;

import com.google.gson.JsonObject;
import com.hundeklemmen.superpay.Addon;
import com.hundeklemmen.superpay.classes.TransactionRequest;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.labymod.gui.elements.Scrollbar;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

import java.io.IOException;

public class AcceptMenu extends Screen {

    private Scrollbar scrollbar = new Scrollbar(18);
    private TransactionRequest anmodning;
    private Addon addon;
    private Boolean interacted = false;

    public AcceptMenu(TransactionRequest anmodning, Addon addon) {
        super(new StringTextComponent("fikocasino"));
        this.anmodning = anmodning;
        this.addon = addon;
    }

    public void init() {
        super.init();

        this.scrollbar.init();
        this.scrollbar.setPosition(this.width / 2 + 122, 44, this.width / 2 + 126, this.height - 32 - 3);

        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("type", "transaction_response");
        responseObject.addProperty("key", anmodning.getKey());
        this.addButton(new Button(this.width / 3, this.height - 75, this.width / 3, 20, new StringTextComponent("Accept"), onClick -> {
            responseObject.addProperty("accepted", true);
            interacted = true;
            closeScreen();
            addon.websocketHandler.send(responseObject.toString());
        }));
        this.addButton(new Button(this.width / 3, this.height - 50, this.width / 3, 20, new StringTextComponent("Afvis"), onClick -> {
            responseObject.addProperty("accepted", false);
            interacted = true;
            closeScreen();
            addon.websocketHandler.send(responseObject.toString());
        }));

    }
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, "§aSuperPay", this.width / 2, 20, 2);
        drawCenteredString(matrixStack, this.font, "§aØnsker du at godkende følgende transaktion? ", this.width / 2, 50, 1);
        LabyMod.getInstance().getDrawUtils().drawRightString(matrixStack, "§aServer:", this.width / 2 - 25, 100, 1);
        drawString(matrixStack, this.font, "§b" + anmodning.getServer(), this.width / 2 + 25, 100, 1);
        //Pakke
        LabyMod.getInstance().getDrawUtils().drawRightString(matrixStack, "§aPakke:", this.width / 2 - 25, 125, 1);
        drawString(matrixStack, this.font, "§b" + anmodning.getPakke(), this.width / 2 + 25, 125, 1);
        //Pris
        LabyMod.getInstance().getDrawUtils().drawRightString(matrixStack, "§aPris:", this.width / 2 - 25, 150, 1);
        drawString(matrixStack, this.font, "§b" + Addon.decimalFormat.format(anmodning.getAmount()) + " Emeralder", this.width / 2 + 25, 150, 1);

        this.scrollbar.draw();

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
    public void onClose() {
        if(interacted == false) {
            JsonObject responseObject = new JsonObject();
            responseObject.addProperty("type", "transaction_response");
            responseObject.addProperty("key", anmodning.getKey());
            responseObject.addProperty("accepted", false);
            addon.websocketHandler.send(responseObject.toString());
            addon.getApi().displayMessageInChat("§8[§aSuperPay§8]§r §cDu har nu afvist købet af §4" + anmodning.getPakke() + " §cpå serveren §4" + anmodning.getServer());
        }
    }

}
