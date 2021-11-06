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

    @Subcommand("change")
    @CommandAlias("change")
    public void change(CommandSender sender, String change) {
        var changes = instance.getGame().getChanges();
        if(changes.containsKey(change)){
            var bool = !changes.get(change);
            changes.put(change, bool);
            sender.sendMessage(ChatColor.GREEN + change + " set to " + bool);
        }
    }

    @Subcommand("play")
    public void play(CommandSender sender, Integer i){
        var game = instance.getGame();
        game.getTwitchEvents().get(i).getConsumer().accept(true);
        sender.sendMessage(ChatColor.GREEN + "Played " + i + " bits.");
    }

    @Subcommand("currentChange")
    public void current(CommandSender sender, Integer i){
        var game = instance.getGame();
        game.setCurrentChange(i);
        sender.sendMessage(ChatColor.GREEN + "Current new change " + i);
        game.getEffectStorage().refreshChange(i);

    }

}
