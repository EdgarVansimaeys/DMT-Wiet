package be.nateoncaprisun.dmtwiet.commands;

import be.nateoncaprisun.dmtwiet.DMTWiet;
import be.nateoncaprisun.dmtwiet.utils.DrugsItemStacks;
import be.nateoncaprisun.dmtwiet.utils.Utils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CommandAlias("drugs")
@CommandPermission("drugs.command")
public class DrugsCommand extends BaseCommand {

    @Default
    @Subcommand("help")
    public void help(Player player){
        player.sendMessage(Utils.color("&b&m-----------------------"));
        player.sendMessage("");
        player.sendMessage(Utils.color("&b/drugs &3location add/remove &7- &bVoegt een drugs region toe of verwijdert een drugs region"));
        player.sendMessage(Utils.color("&b/drugs &3npc &7- &bPlaats een dealer"));
        player.sendMessage(Utils.color("&b/drugs &3getplukker &7- &bKrijg een plukker"));
        player.sendMessage("");
        player.sendMessage(Utils.color("&b&m-----------------------"));
    }

    @Subcommand("location")
    @Syntax("<add/remove>")
    public void location(Player player, String arg){
        Set<ProtectedRegion> regions = (Set<ProtectedRegion>) WorldGuardPlugin.inst().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).getRegions().stream().filter(region -> (region.getPriority() >= 0)).collect(Collectors.toSet());
        if (regions.size() != 1) {
            if (regions.isEmpty()) {
                Utils.sendMessage(player, "&cJe staat op dit moment niet in een region.");
            } else {
                Utils.sendMessage(player, "&cJe staat op dit moment op meer dan 1 region.");
            }
            return;
        }
        ProtectedRegion region = regions.iterator().next();
        if (arg.equals("add")){
            if (region.getFlag(DMTWiet.getInstance().drugsRegionFlag) != null && region.getFlag(DMTWiet.getInstance().drugsRegionFlag) == true){
                player.sendMessage(Utils.color("&cDit plot is al een drugs region!"));
                return;
            }
            region.setFlag(DMTWiet.getInstance().drugsRegionFlag, true);
            player.sendMessage(Utils.color("&aDe region &2" +region.getId() + "&a is toegevoegd als drugs region"));
        }
        if (arg.equals("remove")){
            if (region.getFlag(DMTWiet.getInstance().drugsRegionFlag) == null || region.getFlag(DMTWiet.getInstance().drugsRegionFlag) == false){
                player.sendMessage(Utils.color("&cDit plot is geen drugs region!"));
                return;
            }
            region.setFlag(DMTWiet.getInstance().drugsRegionFlag, false);
            player.sendMessage(Utils.color("&cDe region &4" +region.getId() + "&c is verwijderd als drugs region"));
        }
    }

    @Subcommand("npc")
    public void plaatsNpc(Player player){
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, Utils.color(DMTWiet.getInstance().getConfig().getString("Npc-Name")));
        npc.spawn(player.getLocation());
    }
    @Subcommand("getPlukker")
    public void getPlukker(Player player){
        player.getInventory().addItem(DrugsItemStacks.plukItem());
        player.sendMessage(Utils.color("&bJe hebt een plukker gekregen!"));
    }

}
