package be.nateoncaprisun.dmtwiet.menus;

import be.nateoncaprisun.dmtwiet.DMTWiet;
import be.nateoncaprisun.dmtwiet.enums.DrugsEnum;
import be.nateoncaprisun.dmtwiet.utils.ItemBuilder;
import be.nateoncaprisun.dmtwiet.utils.Utils;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PlukMenu {

    DMTWiet main = DMTWiet.getInstance();

    public void openPlukMenu(Player player, DrugsEnum type){
        Gui gui = Gui.gui()
                .rows(3)
                .title(Component.text(Utils.color("&aPluk Menu")))
                .disableAllInteractions()
                .create();

        GuiItem click = new GuiItem(new ItemBuilder(Material.STAINED_GLASS_PANE,1 , (byte)13)
                .setColoredName(main.getConfig().getString("Glass-Name"))
                .addEnchant(Enchantment.DURABILITY, 1)
                .toItemStack());

        GuiItem notClick = new GuiItem(new ItemBuilder(Material.STAINED_GLASS_PANE,1 , (byte)14)
                .setColoredName("&cKLIK HIER NIET")
                .addEnchant(Enchantment.DURABILITY, 1)
                .toItemStack());

        gui.getFiller().fill(new GuiItem(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).toItemStack()));

        Random random = new Random();
        int random1 = random.nextInt(type.minimum, type.maximum);

        for (int i = 0 ; i < random1 ; i++){
            Random r = new Random();
            int rd = r.nextInt(0, 27);
            gui.setItem(rd, click);
        }

        gui.open(player);

        gui.setDefaultTopClickAction(event -> {
            ItemStack schaar = player.getInventory().getItemInMainHand();
            ItemStack clicked = event.getCurrentItem();
            int damage = schaar.getDurability()-4;
            if (clicked == null) return;
            if (clicked.equals(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).toItemStack())) return;
            if (clicked.equals(click.getItemStack())){
                gui.updateItem(event.getSlot(), notClick);
                if (!containsClicked(event.getClickedInventory(), click)){
                    player.getInventory().addItem(type.item);
                    if (schaar.getDurability() <= 0){
                        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                        player.sendMessage(Utils.color("&cJe schaar is kapot gegaan!"));
                    } else {
                        schaar.setDurability((short) damage);
                    }
                    Block target = player.getTargetBlock(null, 5);
                    Bukkit.getScheduler().runTaskLater(main, () -> {
                        target.setType(type.item.getType());
                        target.setData(type.item.getData().getData());
                    }, 20L*main.getConfig().getInt("Plant-Herplaatst"));
                    gui.close(player);
                    Bukkit.getScheduler().runTaskLater(main, () -> {
                        target.setType(Material.AIR);
                    }, 2L);
                }
            }
            if (clicked.equals(notClick.getItemStack())){
                if (schaar.getDurability() <= 0){
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    player.sendMessage(Utils.color("&cJe schaar is kapot gegaan!"));
                } else {
                    schaar.setDurability((short) damage);
                }
                //schaar.setDurability((short) (schaar.getDurability()-4));
                if (schaar == null){
                    gui.close(player);
                    player.sendMessage(Utils.color("&cJe schaar is kapot gegaan!"));
                }
            }
        });

        gui.setCloseGuiAction(event -> {
            Block target = player.getTargetBlock(null, 5);
            main.getBusy().remove(target.getLocation());
        });
    }

    private boolean containsClicked(org.bukkit.inventory.Inventory inventory, GuiItem click) {
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.equals(click.getItemStack())) {
                return true;
            }
        }
        return false;
    }

}
