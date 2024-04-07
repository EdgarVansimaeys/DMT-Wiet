package be.nateoncaprisun.dmtwiet.menus;

import be.nateoncaprisun.dmtwiet.DMTWiet;
import be.nateoncaprisun.dmtwiet.utils.DrugsItemStacks;
import be.nateoncaprisun.dmtwiet.utils.ItemBuilder;
import be.nateoncaprisun.dmtwiet.utils.Utils;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DrugsSellMenu {

    DMTWiet main = DMTWiet.getInstance();

    public void openDrugSellMenu(Player player){
        Gui gui = Gui.gui()
                .title(Component.text(Utils.color(main.getConfig().getString("Drugs-Sell-Gui"))))
                .disableAllInteractions()
                .rows(3)
                .create();

        GuiItem wiet = new GuiItem(new ItemBuilder(DrugsItemStacks.wietItem().getType(), 1,DrugsItemStacks.wietItem().getData().getData())
                .setColoredName(main.getConfig().getString("Wiet-Sell-Name"))
                .setLore(getLore(main.getConfig().getStringList("Wiet-Sell-Lore")))
                .toItemStack());

        GuiItem opium = new GuiItem(new ItemBuilder(DrugsItemStacks.opiumItem().getType(), 1,DrugsItemStacks.wietItem().getData().getData())
                .setColoredName(main.getConfig().getString("Opium-Sell-Name"))
                .setLore(getLore(main.getConfig().getStringList("Opium-Sell-Lore")))
                .toItemStack());

        GuiItem paddo = new GuiItem(new ItemBuilder(DrugsItemStacks.paddoItem().getType(), 1,DrugsItemStacks.wietItem().getData().getData())
                .setColoredName(main.getConfig().getString("Paddo-Sell-Name"))
                .setLore(getLore(main.getConfig().getStringList("Paddo-Sell-Lore")))
                .toItemStack());

        gui.getFiller().fill(new GuiItem(new ItemBuilder(Material.STAINED_GLASS_PANE,1, (byte) 15).toItemStack()));

        gui.setItem(11, wiet);
        gui.setItem(13, opium);
        gui.setItem(15, paddo);

        gui.open(player);

        gui.setDefaultTopClickAction(event -> {
            ItemStack clicked = event.getCurrentItem();
            if (!event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) return;
            if (clicked == null) return;
            if (clicked.equals(wiet.getItemStack())){
                if (!containsClicked(player.getInventory(), DrugsItemStacks.wietItem())){
                    player.sendMessage(Utils.color("&cJe hebt geen wiet bij je!"));
                    return;
                }
                int wietAmount = 0;
                for (int i = 0; i < player.getInventory().getContents().length; i++) {
                    ItemStack item = player.getInventory().getContents()[i];
                    if (item != null && item.isSimilar(DrugsItemStacks.wietItem())){
                        wietAmount += item.getAmount();
                        player.getInventory().setItem(i, null);
                    }
                }

                Double prijs = Double.parseDouble(String.valueOf(wietAmount))*main.getConfig().getDouble("Wiet-Sell-Value");
                main.getEconomy().depositPlayer(player, prijs);
                player.sendMessage(Utils.color("&aJe hebt al je wiet verkocht voor &2€" +prijs));
            }
            if (clicked.equals(opium.getItemStack())){
                if (!containsClicked(player.getInventory(), DrugsItemStacks.opiumItem())){
                    player.sendMessage(Utils.color("&cJe hebt geen opium bij je!"));
                    return;
                }
                int wietAmount = 0;
                for (int i = 0; i < player.getInventory().getContents().length; i++) {
                    ItemStack item = player.getInventory().getContents()[i];
                    if (item != null && item.isSimilar(DrugsItemStacks.opiumItem())){
                        wietAmount += item.getAmount();
                        player.getInventory().setItem(i, null);
                    }
                }

                Double prijs = Double.parseDouble(String.valueOf(wietAmount))*main.getConfig().getDouble("Opium-Sell-Value");
                main.getEconomy().depositPlayer(player, prijs);
                player.sendMessage(Utils.color("&aJe hebt al je opium verkocht voor &2€" +prijs));
            }
            if (clicked.equals(paddo.getItemStack())){
                if (!containsClicked(player.getInventory(), DrugsItemStacks.paddoItem())){
                    player.sendMessage(Utils.color("&cJe hebt geen paddo's bij je!"));
                    return;
                }
                int wietAmount = 0;
                for (int i = 0; i < player.getInventory().getContents().length; i++) {
                    ItemStack item = player.getInventory().getContents()[i];
                    if (item != null && item.isSimilar(DrugsItemStacks.paddoItem())){
                        wietAmount += item.getAmount();
                        player.getInventory().setItem(i, null);
                    }
                }

                Double prijs = Double.parseDouble(String.valueOf(wietAmount))*main.getConfig().getDouble("Paddo-Sell-Value");
                main.getEconomy().depositPlayer(player, prijs);
                player.sendMessage(Utils.color("&aJe hebt al je paddo's verkocht voor &2€" +prijs));
            }
        });
    }

    private static List<String> getLore(List<String> list){
        List<String> lore = new ArrayList<>();
        for (String string : list){
            lore.add(Utils.color(string));
        }
        return lore;
    }

    private boolean containsClicked(org.bukkit.inventory.Inventory inventory, ItemStack click) {
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.isSimilar(click)) {
                return true;
            }
        }
        return false;
    }

}
