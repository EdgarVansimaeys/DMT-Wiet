package be.nateoncaprisun.dmtwiet.enums;

import be.nateoncaprisun.dmtwiet.DMTWiet;
import be.nateoncaprisun.dmtwiet.utils.DrugsItemStacks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum DrugsEnum {

    WIET(DrugsItemStacks.wietItem(), DMTWiet.getInstance().getConfig().getInt("Glass-Hoeveelheid-Clicks-Wiet-Minimum"), DMTWiet.getInstance().getConfig().getInt("Glass-Hoeveelheid-Clicks-Wiet-Maximum")),
    OPIUM(DrugsItemStacks.opiumItem(), DMTWiet.getInstance().getConfig().getInt("Glass-Hoeveelheid-Clicks-Opium-Minimum"), DMTWiet.getInstance().getConfig().getInt("Glass-Hoeveelheid-Clicks-Opium-Maximum")),
    PADDO(DrugsItemStacks.paddoItem(), DMTWiet.getInstance().getConfig().getInt("Glass-Hoeveelheid-Clicks-Paddo-Minimum"), DMTWiet.getInstance().getConfig().getInt("Glass-Hoeveelheid-Clicks-Paddo-Maximum"));

    public final ItemStack item;
    public final int minimum;
    public final int maximum;

    DrugsEnum(ItemStack item, int minimum, int maximum) {
        this.item = item;
        this.minimum = minimum;
        this.maximum = maximum;
    }


}
