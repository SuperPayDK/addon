package com.hundeklemmen.superpay.modules;

import com.hundeklemmen.superpay.Addon;
import net.labymod.ingamegui.Module;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;
import net.labymod.utils.ModColor;
import net.minecraft.client.gui.Gui;

public class EconomyModule extends SimpleModule {

    Addon addon;

    public EconomyModule(Addon addon){
        this.addon = addon;
    }

    @Override
    public String getDisplayName() {
        return "SPB";
    }

    @Override
    public String getDisplayValue() {
        return this.addon.balance + " Ems";
    }

    @Override
    public String getDefaultValue() {
        return "?";
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.EMERALD);
    }

    @Override
    public void loadSettings() {
    }

    @Override
    public String getSettingName() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Se din SuperPay bal";
    }

    @Override
    public int getSortingId() {
        return 200;
    }

    @Override
    public ModuleCategory getCategory(){
        return ModuleCategoryRegistry.CATEGORY_EXTERNAL_SERVICES;
    }

    @Override
    public String getControlName() {
        return "SuperPay Bal";
    }

    @Override
    public boolean isShown(){
        return true;
    }
}