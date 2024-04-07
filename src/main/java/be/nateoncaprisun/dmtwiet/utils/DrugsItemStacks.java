package be.nateoncaprisun.dmtwiet.utils;

import be.nateoncaprisun.dmtwiet.DMTWiet;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DrugsItemStacks {

    static DMTWiet main = DMTWiet.getInstance();
    static FileConfiguration config = main.getConfig();

    public static ItemStack wietItem(){
        ItemStack wiet = new ItemBuilder(Material.LONG_GRASS, 1, (byte) 2)
                .setColoredName(config.getString("Wiet-Name"))
                .setLore(getLore(config.getStringList("Wiet-Lore")))
                .toItemStack();

        return wiet;
    }

    public static ItemStack opiumItem(){
        ItemStack opium = new ItemBuilder(Material.RED_ROSE, 1, (byte) 2)
                .setColoredName(config.getString("Opium-Name"))
                .setLore(getLore(config.getStringList("Opium-Lore")))
                .toItemStack();

        return opium;
    }

    public static ItemStack paddoItem(){
        ItemStack paddo = new ItemBuilder(Material.BROWN_MUSHROOM, 1, (byte) 0)
                .setColoredName(config.getString("Paddo-Name"))
                .setLore(getLore(config.getStringList("Paddo-Lore")))
                .toItemStack();

        return paddo;
    }

    public static ItemStack plukItem(){
        ItemStack plukker = new ItemBuilder(Material.valueOf(main.getConfig().getString("Plukker-Material")), 1)
                .setColoredName(config.getString("Plukker-Name"))
                .setLore(getLore(config.getStringList("Plukker-Lore")))
                .toItemStack();

        return plukker;
    }

    private static List<String> getLore(List<String> list){
        List<String> lore = new ArrayList<>();
        for (String string : list){
            lore.add(Utils.color(string));
        }
        return lore;
    }

}
