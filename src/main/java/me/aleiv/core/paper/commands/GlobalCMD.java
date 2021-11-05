package me.aleiv.core.paper.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.command.CommandSender;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import lombok.NonNull;
import me.aleiv.core.paper.Core;
import net.md_5.bungee.api.ChatColor;

@CommandAlias("twitch")
@CommandPermission("admin.perm")
public class GlobalCMD extends BaseCommand {

    private @NonNull Core instance;
    Random random = new Random();

    public GlobalCMD(Core instance) {
        this.instance = instance;

    }

    @Subcommand("events")
    public void events(CommandSender sender, Boolean bool){
        var game = instance.getGame();
        game.setActive(bool);
        sender.sendMessage(ChatColor.DARK_PURPLE + "Twitch events " + bool);

    }

    @Subcommand("resistance")
    @CommandAlias("resistance")
    public void resistanceChange(CommandSender sender, int change) {
        instance.getGame().setDamageResistance(change);
        sender.sendMessage(ChatColor.GREEN + "Resistance set to " + change);
    }

    @Subcommand("damage")
    @CommandAlias("damage")
    public void damageChange(CommandSender sender, int change) {
        instance.getGame().setDamageMultiplier(change);
        sender.sendMessage(ChatColor.GREEN + "Damage amplifier set to " + change);
    }

    @Subcommand("ruedita")
    public void ruedita(CommandSender sender, String str){
        var game = instance.getGame();
        game.ruedita();

    }

}
