package be.nateoncaprisun.dmtwiet.listeners;

import be.nateoncaprisun.dmtwiet.DMTWiet;
import be.nateoncaprisun.dmtwiet.menus.DrugsSellMenu;
import be.nateoncaprisun.dmtwiet.utils.Utils;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DrugsNpcClickListener implements Listener {

    private DMTWiet main;

    public DrugsNpcClickListener(DMTWiet main){
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void npcClickEvent(NPCRightClickEvent event){
        NPC npc = event.getNPC();
        if (!npc.getFullName().contains(ChatColor.stripColor(Utils.color(DMTWiet.getInstance().getConfig().getString("Npc-Name"))))) return;
        new DrugsSellMenu().openDrugSellMenu(event.getClicker());
    }

}
