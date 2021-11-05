package me.aleiv.core.paper.commands;

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

    public GlobalCMD(Core instance) {
        this.instance = instance;

    }

    @Subcommand("events")
    public void turn(CommandSender sender, Boolean bool){
        var game = instance.getGame();
        game.setActive(bool);
        sender.sendMessage(ChatColor.DARK_PURPLE + "Twitch events " + bool);

    }
}
