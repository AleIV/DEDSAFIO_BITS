package me.aleiv.core.paper.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.aleiv.core.paper.Core;
import me.aleiv.core.paper.utilities.TCT.BukkitTCT;

public class GlobalListener implements Listener{
    
    Core instance;

    public GlobalListener(Core instance){
        this.instance = instance;
    }
    
    @EventHandler
    public void resistance(EntityDamageEvent e){
        var entity = e.getEntity();

        if(!(entity instanceof Player)){
            var mobResistance = instance.getGame().getDamageResistance();
            e.setDamage(e.getDamage()-((e.getDamage()/100)*mobResistance));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){

        var cause = e.getCause();
        var damage = instance.getGame().getDamageMultiplier();

        if(cause == DamageCause.LAVA || cause == DamageCause.DRAGON_BREATH){
            e.setDamage(e.getDamage()*damage);
        }

        if(cause == DamageCause.FIRE || cause == DamageCause.FIRE_TICK 
            || cause == DamageCause.DROWNING || cause == DamageCause.HOT_FLOOR 
                || cause == DamageCause.STARVATION || cause == DamageCause.FALL || cause == DamageCause.CONTACT 
                    || cause == DamageCause.FLY_INTO_WALL || cause == DamageCause.SUFFOCATION){
            e.setDamage(e.getDamage()*damage);
        }

        if(cause == DamageCause.WITHER || cause == DamageCause.MAGIC){
            e.setDamage(e.getDamage()*damage);
        }

    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void playerDied(PlayerDeathEvent e) {
        var player = e.getEntity();
        var game = instance.getGame();

        if (player.getGameMode() == GameMode.SPECTATOR){
            e.setDeathMessage("");
            return;
        }
            

        if(game.getDeathAnimation()){
            instance.getGame().animation(e.getDeathMessage(), "muerte", "D", 90, true);
        }

    }

}
