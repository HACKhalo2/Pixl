package com.hackhalo2.creative;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Pixl extends JavaPlugin {
    public PluginDescriptionFile pdf;
    public String version = null;
    public HashMap<Player, Boolean> toggled = new HashMap<Player, Boolean>();
    public HashMap<Player, Integer> set = new HashMap<Player, Integer>();
    public HashMap<Player, Boolean> breakMode = new HashMap<Player, Boolean>();

    private final PixlPlayer pListener = new PixlPlayer(this);

    //Help
    //public boolean helpEnabled = false; //enabled boolean

    public void onEnable() {

        pdf = this.getDescription();
        version = pdf.getVersion();
        //Set up the Plugin Manager
        PluginManager pm = getServer().getPluginManager();
        //Register Listeners
        pm.registerEvent(Event.Type.PLAYER_JOIN, pListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_KICK, pListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, pListener, Event.Priority.Normal, this);
        System.out.println("[Pixl] "+version+" Loaded");

        getCommand("pixl").setExecutor(new PixlCommand(this));
    }

    public void onDisable() { }

    public void setToggle(final Player p, final boolean v) { toggled.put(p, v); }
    public boolean isToggled(final Player p) {
        if(toggled.containsKey(p)) { return toggled.get(p); }
        else { return false; }
    }

    public void setBreak(final Player p, final boolean v) { breakMode.put(p, v); }
    public boolean breakMode(final Player p) {
        if(breakMode.containsKey(p)) { return breakMode.get(p); }
        else { return false; }
    }

    public void removeValue(final Player p) { set.remove(p); }
    public void setValue(final Player p, final int v) { set.put(p, v); }
    public Integer isSet(final Player p) {
        if(set.containsKey(p)) { return set.get(p); }
        else { return null; }
    }

    public boolean checkPermissions(Player p, String s, boolean f) {
        if(isToggled(p) || breakMode(p) || f) { //check to see if player is toggled or forced
            return p.hasPermission(s);
        }
        return false;
    }
}
