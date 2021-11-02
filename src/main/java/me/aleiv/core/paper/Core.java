package me.aleiv.core.paper;

import java.time.Duration;
import java.util.List;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.commands.PaperCommandManager;
import kr.entree.spigradle.annotations.SpigotPlugin;
import lombok.Getter;
import me.aleiv.core.paper.commands.GlobalCMD;
import me.aleiv.core.paper.listeners.GlobalListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;

@SpigotPlugin
public class Core extends JavaPlugin {

    private static @Getter Core instance;
    private @Getter Game game;
    private @Getter PaperCommandManager commandManager;
    private @Getter static MiniMessage miniMessage = MiniMessage.get();
    private @Getter TwitchClient client;
    private @Getter TwitchEventsHandler twitchEventsHandler;

    @Override
    public void onEnable() {
        instance = this;

        game = new Game(this);
        game.runTaskTimerAsynchronously(this, 0L, 20L);

        //LISTENERS

        Bukkit.getPluginManager().registerEvents(new GlobalListener(this), this);


        //COMMANDS
        
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new GlobalCMD(this));

        //configuration
        this.saveDefaultConfig();
        FileConfiguration config = getConfig();

        //Build twich client
        client = TwitchClientBuilder.builder()
            .withClientId(config.getString("client_id"))
            .withClientSecret(config.getString("client_secret"))
            .withEnableChat(true)
            .withEnableHelix(true)
            .build();
        
        //join the twitch chats of these channels and enable stream/follow events

        List<String> channels = config.getStringList("channels");
        if(!channels.isEmpty()){
            client.getClientHelper().enableFollowEventListener(channels);
            channels.forEach(channel -> client.getChat().joinChannel(channel));
        }

        //register events

        this.twitchEventsHandler = new TwitchEventsHandler(this);
        this.client.getEventManager().getEventHandler(SimpleEventHandler.class).registerListener(twitchEventsHandler);
        

    }

    @Override
    public void onDisable() {

    }

    public void unregisterListener(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, instance);
    }

    public void adminMessage(String text) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("admin.perm"))
                player.sendMessage(text);
        });
    }

    public Component componentToString(String str) {
        return miniMessage.parse(str);
    }

    public void broadcastMessage(String text) {
        Bukkit.broadcast(miniMessage.parse(text));
    }

    public void sendActionBar(Player player, String text) {
        player.sendActionBar(miniMessage.parse(text));
    }

    public void showTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        player.showTitle(Title.title(miniMessage.parse(title), miniMessage.parse(subtitle), Times
                .of(Duration.ofMillis(50 * fadeIn), Duration.ofMillis(50 * stay), Duration.ofMillis(50 * fadeIn))));
    }

    public void sendHeader(Player player, String text) {
        player.sendPlayerListHeader(miniMessage.parse(text));
    }

    public void sendFooter(Player player, String text) {
        player.sendPlayerListFooter(miniMessage.parse(text));
    }

}