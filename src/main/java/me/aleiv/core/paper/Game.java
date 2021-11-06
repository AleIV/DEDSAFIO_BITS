package me.aleiv.core.paper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.aleiv.core.paper.events.GameTickEvent;
import me.aleiv.core.paper.utilities.TCT.BukkitTCT;
import net.md_5.bungee.api.ChatColor;
import us.jcedeno.libs.rapidinv.ItemBuilder;

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
    HashMap<String, Boolean> changes = new HashMap<>();

    Integer currentChange = 0;

    public Game(Core instance) {
        this.instance = instance;
        this.startTime = System.currentTimeMillis();

        this.mobStorage = new MobStorage(instance);
        this.effectStorage = new EffectStorage(instance);

        registerMobs();
        registerLight();
        registerExtra();

        letters.add("A"); // ROJO
        letters.add("B"); // NARANJA
        letters.add("C"); // AMARILLO
        letters.add("D"); // VERDE
        letters.add("E"); // CELESTE
        letters.add("F"); // AZUL
        letters.add("0"); // MORADO
        letters.add("1"); // ROSA

        //DANO
        //RESISTENCIA
        changes.put("ZOMBIE", false);
        changes.put("SPIDER", false);
        changes.put("CREEPER", false);
        changes.put("SKELETON", false);
    }

    @Override
    public void run() {

        var new_time = (int) (Math.floor((System.currentTimeMillis() - startTime) / 1000.0));

        gameTime = new_time;

        Bukkit.getPluginManager().callEvent(new GameTickEvent(new_time, true));
    }

    private void registerLight() {
        twitchEvents.put(100, new TwitchMinecraftEvent("Zanahoria de oro x4", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var inv = player.getInventory();
                        inv.addItem(new ItemStack(Material.GOLDEN_CARROT, 4));
                    }
                });
            }
        }));

        twitchEvents.put(125, new TwitchMinecraftEvent("Diamante x2", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var inv = player.getInventory();
                        inv.addItem(new ItemStack(Material.DIAMOND, 2));
                    }
                });
            }
        }));

        twitchEvents.put(113, new TwitchMinecraftEvent("Manzana de oro", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var inv = player.getInventory();
                        inv.addItem(new ItemStack(Material.GOLDEN_APPLE));
                    }
                });
            }
        }));

        twitchEvents.put(425, new TwitchMinecraftEvent("Manzana de Notch", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var inv = player.getInventory();
                        inv.addItem(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
                    }
                });
            }
        }));

        twitchEvents.put(450, new TwitchMinecraftEvent("Mystical Totem", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var inv = player.getInventory();
                        var totem = new ItemBuilder(Material.TOTEM_OF_UNDYING).name(ChatColor.of("#BABA9B") + "Mystical Totem")
                            .enchant(Enchantment.PROTECTION_ENVIRONMENTAL).flags(ItemFlag.HIDE_ENCHANTS).meta(meta -> meta.setCustomModelData(41)).build();
                        inv.addItem(totem);
                    }
                });
            }
        }));

    }

    private void registerExtra() {
        twitchEvents.put(10000, new TwitchMinecraftEvent("Water Drop", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var loc = player.getLocation();
                        loc.add(0, 300, 0);
                        player.teleport(loc);
                        player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
                        
                    }
                });
            }
        }));

        twitchEvents.put(20000, new TwitchMinecraftEvent("METEORITO", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                effectStorage.meteorito();
            }
        }));

        twitchEvents.put(30000, new TwitchMinecraftEvent("LLUVIA METEORITOS", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                effectStorage.lluviaMeteoritos();
            }
        }));

        twitchEvents.put(5000, new TwitchMinecraftEvent("RUEDITA", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                currentChange++;
                effectStorage.refreshChange(currentChange);
            }
        }));

    
    }

    private void registerMobs(){

        twitchEvents.put(300, new TwitchMinecraftEvent("Zombie Chef", new Consumer<>() {
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

        twitchEvents.put(375, new TwitchMinecraftEvent("Skeleton", new Consumer<>() {
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

        twitchEvents.put(400, new TwitchMinecraftEvent("Araña", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var loc = player.getLocation();
                        mobStorage.spawnSpider(loc);
                    }
                });
            }
        }));

        twitchEvents.put(475, new TwitchMinecraftEvent("Mage", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var loc = player.getLocation();
                        mobStorage.spawnMage(loc);
                    }
                });
            }
        }));

        twitchEvents.put(500, new TwitchMinecraftEvent("Raider", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var loc = player.getLocation();
                        mobStorage.spawnRaider(loc);
                    }
                });
            }
        }));

        twitchEvents.put(550, new TwitchMinecraftEvent("Piglin", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var loc = player.getLocation();
                        mobStorage.spawnPiglin(loc);
                    }
                });
            }
        }));

        twitchEvents.put(900, new TwitchMinecraftEvent("Ravager Powerfull", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var loc = player.getLocation();
                        mobStorage.spawnRavagerPowerfull(loc);
                    }
                });
            }
        }));

        twitchEvents.put(950, new TwitchMinecraftEvent("Redstone Golem", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var loc = player.getLocation();
                        mobStorage.spawnRedstoneGolem(loc);
                    }
                });
            }
        }));

        twitchEvents.put(1250, new TwitchMinecraftEvent("The Death", new Consumer<>() {
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

        twitchEvents.put(1500, new TwitchMinecraftEvent("Mountaineer", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var loc = player.getLocation();
                        mobStorage.spawnMountainer(loc);
                    }
                });
            }
        }));

        twitchEvents.put(2500, new TwitchMinecraftEvent("Creeper", new Consumer<>() {
            @Override
            public void accept(Boolean t) {
                Bukkit.getOnlinePlayers().forEach(player ->{
                    if(player.getGameMode() == GameMode.SURVIVAL){
                        var loc = player.getLocation();
                        mobStorage.spawnCreeper(loc);
                    }
                });
            }
        }));

    }

    public CompletableFuture<Boolean> animation(String text, String sound, String letter, int number, boolean right){

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
    
        return chain.execute();
    }

    public CompletableFuture<Boolean> rueditaAnim() {
        var ruleta = letters.get(random.nextInt(letters.size()));
        return animation("", "ruedita", ruleta, 97, false);
    }
}