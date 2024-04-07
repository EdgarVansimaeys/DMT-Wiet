package be.nateoncaprisun.dmtwiet;

import be.nateoncaprisun.dmtwiet.commands.DrugsCommand;
import be.nateoncaprisun.dmtwiet.listeners.DrugsNpcClickListener;
import be.nateoncaprisun.dmtwiet.listeners.PlayerOpiumPlukListener;
import be.nateoncaprisun.dmtwiet.listeners.PlayerPaddoPlukListener;
import be.nateoncaprisun.dmtwiet.listeners.PlayerWietPlukListener;
import be.nateoncaprisun.dmtwiet.utils.ConfigurationFile;
import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.CommandManager;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.BooleanFlag;
import com.sk89q.worldguard.protection.flags.DoubleFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class DMTWiet extends JavaPlugin {
    private static @Getter DMTWiet instance;
    public BooleanFlag drugsRegionFlag = new BooleanFlag("drugsregion");
    private @Getter List<Location> busy = new ArrayList<>();
    private CommandManager commandManager;
    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new DrugsCommand());

        new PlayerWietPlukListener(this);
        new PlayerOpiumPlukListener(this);
        new PlayerPaddoPlukListener(this);
        new DrugsNpcClickListener(this);
    }

    public void onLoad() {
        FlagRegistry registry = WorldGuardPlugin.inst().getFlagRegistry();
        try {
            registry.register((Flag)drugsRegionFlag);
        } catch (FlagConflictException ignored) {
            drugsRegionFlag = (BooleanFlag) registry.get("drugsregion");
        }
    }

    public static Economy getEconomy() {
        return (Economy)instance.getServer().getServicesManager().getRegistration(Economy.class).getProvider();
    }
}
