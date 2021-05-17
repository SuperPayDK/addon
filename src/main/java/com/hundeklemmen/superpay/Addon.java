package com.hundeklemmen.superpay;

import com.google.gson.JsonObject;
import com.hundeklemmen.superpay.listeners.commandEvent;
import com.hundeklemmen.superpay.modules.EconomyModule;
import com.hundeklemmen.superpay.websocket.WebsocketHandler;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import com.hundeklemmen.superpay.listeners.JoinEvent;
import net.labymod.utils.ServerData;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.List;

public class Addon extends LabyModAddon {
    /**
     * Called when the addon gets enabled
     */

    public boolean onSuperAwesome = false;
    public ServerData server;
    public String session;
    public String token;
    public WebsocketHandler websocketHandler;
    public static String websocketURL = "wss://superpayaddon.hundeklemmen.com:2087";
    public static DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
    public boolean verified = false;
    public double balance = 0.0;
    public static Integer SuperPayAddonVersion = 1;

    @Override
    public void onEnable() {
        this.getApi().getEventService().registerListener(new JoinEvent(this));
        this.getApi().getEventService().registerListener(new commandEvent(this));
        this.getApi().registerModule(new EconomyModule(this));

        try {
            this.websocketHandler = new WebsocketHandler(this, this.websocketURL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("Connecting to API");
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
                this.token,
            new Consumer<String>() {
                @Override
                public void accept( String tokenStr ) {
                    if(tokenStr.length() == 24) {
                        System.out.println("New token: " + tokenStr);
                        token = tokenStr;
                        getConfig().addProperty("token", token);
                        if(websocketHandler.isClosed() == false) {
                            try {
                                JsonObject verification = new JsonObject();
                                verification.addProperty("type", "verification");
                                verification.addProperty("token", token);
                                verification.addProperty("username", getApi().getPlayerUsername());
                                verification.addProperty("uuid", getApi().getPlayerUUID().toString());
                                websocketHandler.send(verification.toString());
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        ).maxLength(24);

        subSettings.add( channelStringElement );

    }
}
