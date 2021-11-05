package me.aleiv.core.paper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.aleiv.core.paper.events.GameTickEvent;
import me.aleiv.core.paper.utilities.TCT.BukkitTCT;

@Data
@EqualsAndHashCode(callSuper = false)
public class Game extends BukkitRunnable {
    Core instance;

    Random random = new Random();

    long gameTime = 0;
    long startTime = 0;
    
    Boolean active = false;

    Boolean deathAnimation = true;

    Integer damageMultiplier = 1;
    Integer damageResistance = 0;

    EffectStorage effectStorage;
    MobStorage mobStorage;

    List<String> letters = new ArrayList<>();
    HashMap<Integer, TwitchMinecraftEvent> twitchEvents =  new HashMap<>();

    public Game(Core instance) {
        this.instance = instance;
        this.startTime = System.currentTimeMillis();

        this.mobStorage = new MobStorage(instance);
        this.effectStorage = new EffectStorage(instance);

        registerEvents();

        letters.add("A"); // ROJO
        letters.add("B"); // NARANJA
        letters.add("C"); // AMARILLO
        letters.add("D"); // VERDE
        letters.add("E"); // CELESTE
        letters.add("F"); // AZUL
        letters.add("0"); // MORADO
        letters.add("1"); // ROSA
    }

    @Override
    public void run() {

        var new_time = (int) (Math.floor((System.currentTimeMillis() - startTime) / 1000.0));

        gameTime = new_time;

        Bukkit.getPluginManager().callEvent(new GameTickEvent(new_time, true));
    }

    public void registerEvents(){

        twitchEvents.put(100, new TwitchMinecraftEvent("Zombie Chef", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var loc = player.getLocation();
                        mobStorage.spawnChef(loc);
                    }
                });
            }
        }));

        twitchEvents.put(450, new TwitchMinecraftEvent("The Death", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var loc = player.getLocation();
                        mobStorage.spawnWitherSkeleton(loc);
                    }
                });
            }
        }));

        twitchEvents.put(300, new TwitchMinecraftEvent("Skeleton", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var loc = player.getLocation();
                        mobStorage.spawnSkeleton(loc);
                    }
                });
            }
        }));

    }

    public void animation(String text, String sound, String letter, int number, boolean right){

        var chain = new BukkitTCT();
    
        var count = 0;
        var character = 92;
        var charac = Character.toString((char)character);
    
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a times 0 1 60");
        
        Bukkit.getOnlinePlayers().forEach(p->{
            p.playSound(p.getLocation(), sound, 1, 1);
        });
        
        while (count < number) {
    
            final var current = count;
    
            var id = "" + (current <= 9 ? "0" + current : current);
            var code = right ? (charac + "uE" + id + letter) : (charac + "uE" + letter + id);
    
            chain.addWithDelay(new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"" + code + "\"}");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a actionbar {\"text\":\"" + text + "\"}");
                }
            }, 50);

            count++;
        }
    
        chain.execute();
    }

    public void ruedita() {
        var ruleta = letters.get(random.nextInt(letters.size()));
        animation("", "ruedita", ruleta, 97, false);
    }
}