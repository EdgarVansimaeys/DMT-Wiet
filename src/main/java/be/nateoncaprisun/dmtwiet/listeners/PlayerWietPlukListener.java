package be.nateoncaprisun.dmtwiet.listeners;

import be.nateoncaprisun.dmtwiet.DMTWiet;
import be.nateoncaprisun.dmtwiet.enums.DrugsEnum;
import be.nateoncaprisun.dmtwiet.menus.PlukMenu;
import be.nateoncaprisun.dmtwiet.utils.DrugsItemStacks;
import be.nateoncaprisun.dmtwiet.utils.Utils;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Set;
import java.util.stream.Collectors;

public class PlayerWietPlukListener implements Listener {
    private DMTWiet main;

    public PlayerWietPlukListener(DMTWiet main){
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (block == null || block.getType().equals(Material.AIR)) return;
        if (block.getType() != Material.LONG_GRASS) return;
        if (block.getData() != (byte) 2) return;
        Set<ProtectedRegion> regions = (Set<ProtectedRegion>) WorldGuardPlugin.inst().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).getRegions().stream().filter(region -> (region.getPriority() >= 0)).collect(Collectors.toSet());
        if (!player.getInventory().getItemInMainHand().getType().equals(DrugsItemStacks.plukItem().getType())) return;
        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName() == null)  return;
        if (!player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(DrugsItemStacks.plukItem().getItemMeta().getDisplayName()))  return;
        if (main.getBusy().contains(block.getLocation())){
            player.sendMessage(Utils.color("&cIemand is al deze wiet aan het farmen!"));
            return;
        }
        if (regions.size() != 1) return;
        ProtectedRegion region = regions.iterator().next();
        if (region.getFlag(DMTWiet.getInstance().drugsRegionFlag) == null || region.getFlag(DMTWiet.getInstance().drugsRegionFlag) == false) return;
        new PlukMenu().openPlukMenu(player, DrugsEnum.WIET);
        main.getBusy().add(block.getLocation());
    }
}
