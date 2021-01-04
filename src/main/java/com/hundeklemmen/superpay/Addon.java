package com.hundeklemmen.superpay;

import com.hundeklemmen.superpay.listeners.onChat;
import com.hundeklemmen.superpay.modules.EconomyModule;
import com.hundeklemmen.superpay.websocket.WebsocketHandler;
import net.labymod.api.LabyModAddon;
import net.labymod.api.events.MessageReceiveEvent;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;

import com.hundeklemmen.superpay.listeners.JoinEvent;
import net.labymod.utils.ServerData;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Addon extends LabyModAddon {
    /**
     * Called when the addon gets enabled
     */

    public boolean onSuperAwesome = false;
    public ServerData server;
    public String session;
    private String token;
    private WebsocketHandler websocketHandler;
    public static String websocketURL = "https://superpayaddon.hundeklemmen.com:2087";

    @Override
    public void onEnable() {
        this.getApi().getEventManager().registerOnJoin(new JoinEvent(this));
        this.getApi().getEventManager().register(new onChat(this));
        this.getApi().registerModule(new EconomyModule(this));
        this.websocketHandler = new WebsocketHandler(this);
    }


    /**
     * Called when the addon gets disabled
     */
    @Override
    public void onDisable() {

    }

    /**
     * Called when this addon's config was loaded and is ready to use
     */
    @Override
    public void loadConfig() {
        this.token = getConfig().has( "token" ) ? getConfig().get( "token" ).getAsString() : "";
    }

    /**
     * Called when the addon's ingame settings should be filled
     *
     * @param subSettings a list containing the addon's settings' elements
     */
    @Override
    protected void fillSettings( List<SettingsElement> subSettings ) {

        StringElement channelStringElement = new StringElement(
            "Token",
            new ControlElement.IconData( Material.PAPER ),
            "",
            this.token
        );

        subSettings.add( channelStringElement );

    }
}
