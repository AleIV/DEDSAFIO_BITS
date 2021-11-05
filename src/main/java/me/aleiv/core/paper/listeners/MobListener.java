package me.aleiv.core.paper.listeners;

import java.util.Random;

import com.destroystokyo.paper.event.entity.EnderDragonFireballHitEvent;
import com.destroystokyo.paper.event.entity.EnderDragonFlameEvent;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Vex;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.spigotmc.event.entity.EntityDismountEvent;

import me.aleiv.core.paper.Core;
import net.md_5.bungee.api.ChatColor;

public class MobListener implements Listener{
    
    Core instance;

    Random random = new Random();

    public MobListener(Core instance){
        this.instance = instance;
    }

    
    @EventHandler(priority = EventPriority.LOW)
    public void mobsDamageModifier(EntityDamageByEntityEvent e){
        var damager = e.getDamager();

        if(damager instanceof Player) return;

        if(damager instanceof Projectile){
            var proj = (Projectile) damager;
            var shooter = proj.getShooter();

            if(proj.getCustomName() != null && proj.getCustomName().contains("lead")){
                e.setDamage(e.getDamage()+22);
                if(random.nextBoolean() && shooter instanceof Pillager){
                    var pillager = (Pillager) shooter;
                    var loc = pillager.getLocation();

                    loc.getNearbyPlayers(20).stream().forEach(p ->{
                        p.playSound(p.getLocation(), "boomheadshot", 0.3f, 1);
                    });

                }
            }else if(proj.getCustomName() != null && proj.getCustomName().contains("golden")){
                e.setDamage(e.getDamage()+14);
            }

        }

    }

    public Integer chooseCoord(int radius) {
        var num = random.nextInt(radius);
        num = random.nextBoolean() ? ~(num) : num;
        return num;
    }

    @EventHandler
    public void demons(CreatureSpawnEvent e) {
        var entity =  e.getEntity();

        if(entity instanceof Phantom){
            var phantom = (Phantom) entity;
            phantom.setCustomName(ChatColor.GRAY + "Phantom Thief");
            phantom.setSize(2);

            var loc = phantom.getLocation();
            var players = loc.getNearbyPlayers(64, player-> player.getGameMode() == GameMode.SURVIVAL).stream().findAny();
            if(players.isPresent()) phantom.setTarget(players.get());

        }else if(entity instanceof Vex){
            var spirit = (Vex) entity;
            spirit.setCustomName(ChatColor.DARK_PURPLE + "Spirit");

        }else if(entity instanceof Ghast ghast){
            var loc = ghast.getLocation();
            var players = loc.getNearbyPlayers(64, player-> player.getGameMode() == GameMode.SURVIVAL).stream().findAny();
            if(players.isPresent()) ghast.setTarget(players.get());

        }else if(entity instanceof Enderman){
            var enderman = (Enderman) entity;
            enderman.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 600, 2));
            switch (random.nextInt(4)) {
                case 1:{
                    enderman.setCustomName(ChatColor.DARK_PURPLE + "Corrupted Demon");
                }
                    break;
                case 2:{
                    enderman.setCustomName(ChatColor.DARK_PURPLE + "Old Demon");
                }
                    break;
                case 3:{
                    enderman.setCustomName(ChatColor.DARK_PURPLE + "Demon");
                }
                    break;
            
                default:{
                    enderman.setCustomName(ChatColor.DARK_PURPLE + "Ghost");
                }
                    break;
            }

        }
    }

    @EventHandler
    public void onDamage(PlayerDeathEvent e){
        var cause = e.getEntity().getLastDamageCause().getCause();

        if(cause == DamageCause.CUSTOM || cause == DamageCause.WITHER){
            e.setDeathMessage(e.getEntity().getName() + " died from radiation");
        }
    }

    @EventHandler
    public void onShoott(EntityShootBowEvent e) {
        var entity = e.getEntity();
        var bow = e.getBow().getItemMeta();
        var proj = e.getProjectile();
        if (entity instanceof Player) {
            var player = (Player) entity;
            if (bow.getDisplayName().toString().contains("Meteor") && player.getGameMode() == GameMode.CREATIVE) {
                proj.setCustomName("Meteor");

                var armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
                armorStand.setCustomName("Meteor");
                armorStand.setGlowing(true);
                armorStand.setBodyPose(EulerAngle.ZERO);
                var meteor = new ItemStack(Material.WOODEN_HOE);
                var meta =  meteor.getItemMeta();
                meta.setDisplayName(ChatColor.BLACK + "Meteor");
                meta.setCustomModelData(6);
                meteor.setItemMeta(meta);
                armorStand.getEquipment().setItemInMainHand(meteor);
                proj.addPassenger(armorStand);



                Bukkit.getOnlinePlayers().forEach(p->{
                    p.playSound(p.getLocation(), "meteorito_cayendo", 1, 1);
                });
            }
        }
    }


    
    @EventHandler
    public void impactt(ProjectileHitEvent e) {
        var entity = e.getHitEntity();
        var block = e.getHitBlock();
        var projectile = e.getEntity();
        
        if(projectile.getCustomName() != null && projectile.getCustomName().toString().contains("Meteor")){
            if(entity != null){
                var loc = entity.getLocation();
                loc.add(0, -10, 0).createExplosion(100, true);
                entity.getWorld().strikeLightning(entity.getLocation());
                entity.getWorld().strikeLightning(entity.getLocation());
                entity.getWorld().strikeLightning(entity.getLocation());

                Bukkit.getOnlinePlayers().forEach(p ->{
                    p.playSound(p.getLocation(), "meteorito_impacto", 1, 1);
                });
                
            }else if(block != null){
                var loc = block.getLocation();
                loc.add(0, -10, 0).createExplosion(100, true);
                block.getWorld().strikeLightning(block.getLocation());
                block.getWorld().strikeLightning(block.getLocation());
                block.getWorld().strikeLightning(block.getLocation());

                Bukkit.getOnlinePlayers().forEach(p ->{
                    p.playSound(p.getLocation(), "meteorito_impacto", 1, 1);
                });
                
            }

            
        }
    }

    @EventHandler
    public void impact(ProjectileHitEvent e) {
        var projectile = e.getEntity();
        var hitblock = e.getHitBlock();
        var entity = e.getHitEntity();

        if (projectile.getCustomName() != null
                && projectile.getCustomName().toString().contains("explosive")) {

            if (hitblock != null) {
                hitblock.getLocation().createExplosion(3, true, false);
            } else if (entity != null) {
                entity.getLocation().createExplosion(3, true, false);
            }

        }

        if (e.getEntity() instanceof WitherSkull) {

            if (entity != null && entity instanceof Player) {
                var player = (Player) entity;
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 0));
            }
        }
    }

    @EventHandler
    public void onShootWeapons(EntityShootBowEvent e) {
        var bow = e.getBow().getItemMeta();
        if (!bow.hasCustomModelData())
            return;

        if ((bow.getCustomModelData() == 105 || bow.getCustomModelData() == 112)) {
            e.getProjectile().setCustomName("explosive");

        } else if (bow.getCustomModelData() == 113) {
            var skull = e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation().add(0, 1, 0),
                    EntityType.WITHER_SKULL);
            e.setProjectile(skull);

        } else if (bow.getCustomModelData() == 116) {
            //magic staff
            e.getProjectile().setCustomName("blue orb");
            var loc = e.getEntity().getLocation();

            loc.getNearbyPlayers(20).stream().forEach(p ->{
                p.playSound(p.getLocation(), "magic_1", 0.3f, 1);
            });

        }else if (bow.getCustomModelData() == 108) {
            //rifle
            e.getProjectile().setCustomName("lead bullet");
            var loc = e.getEntity().getLocation();

            loc.getNearbyPlayers(20).stream().forEach(p ->{
                p.playSound(p.getLocation(), "rifle_shoot", 0.3f, 1);
            });

                    
        }else if (bow.getCustomModelData() == 107 || bow.getCustomModelData() == 109) {
            //pistol
            e.getProjectile().setCustomName("golden bullet");
            var loc = e.getEntity().getLocation();

            loc.getNearbyPlayers(20).stream().forEach(p ->{
                p.playSound(p.getLocation(), "gun_1", 0.3f, 1);
            });

        }
        

    }

    @EventHandler
    public void onFireBall(EnderDragonFireballHitEvent e) {
        var cloud = e.getAreaEffectCloud();

        cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
        cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
        cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);

        if (e.getTargets() != null) {
            e.getTargets().forEach(target -> {
                if (target instanceof Player) {
                    target.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 0));
                }
            });
        }
    }

    @EventHandler
    public void onFireBall2(EnderDragonFlameEvent e) {
        var cloud = e.getAreaEffectCloud();

        cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
        cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
        cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
    }

    @EventHandler
    public void spiritDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Vex && e.getCause() == DamageCause.MAGIC) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void bossSpawns(CreatureSpawnEvent e) {
        var entity = e.getEntity();

        if (entity instanceof EnderDragon) {
            var dragon = (EnderDragon) entity;
            dragon.setCustomName(ChatColor.YELLOW + "Blood Ender Dragon");
            dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(400);
            dragon.setHealth(400);

        }else if(random.nextInt(5) == 1 && entity.getWorld() == Bukkit.getWorld("world_the_end") && entity instanceof Enderman){
            e.setCancelled(true);
            var loc = entity.getLocation();
            entity.getWorld().spawnEntity(loc, EntityType.ZOMBIE);

        }

    }

    @EventHandler
    public void powers(EntityDamageByEntityEvent e){
        var entity = e.getEntity();
        var proj = e.getDamager();
        if(proj instanceof ShulkerBullet && entity instanceof Player){
            var bullet = (ShulkerBullet) proj;
            var shulker = (Shulker) bullet.getShooter();
            var player = (Player) entity;

            if(shulker.getCustomName() == null){
                switch (random.nextInt(3)) {
                    case 1:{
                        shulker.setCustomName(ChatColor.GOLD + "Fire Shulker"); 
                        shulker.setColor(DyeColor.RED);
                    }break;
                        
                    case 2:{
                        shulker.setCustomName(ChatColor.BLACK + "Radioactive Shulker");
                        shulker.setColor(DyeColor.BLACK);
                    }break;
    
                    default:{
                        shulker.setCustomName(ChatColor.GREEN + "Poisonous Shulker"); 
                        shulker.setColor(DyeColor.GREEN);
                    }break;
                }
            }else if(shulker.getCustomName().contains("Fire")){
                player.setFireTicks(20*10);

            }else if(shulker.getCustomName().contains("Radioactive")){
                player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 10*20, 0));

            }else if(shulker.getCustomName().contains("Poisonous")){
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10*20, 0));

            }

            Bukkit.getScheduler().runTaskLater(instance, task->{
                player.getLocation().createExplosion(2, true, true);
            }, 5);
        }

    }
    
    @EventHandler
    public void killPassengers1(EntityDismountEvent e){
        if(e.getEntity() instanceof Parrot){
            var parrot = (Parrot) e.getEntity();
            Bukkit.getScheduler().runTaskLater(instance, ()->{
                parrot.getLocation().createExplosion(1);
            }, 20*2);
        }
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        var entity = e.getEntity();
        if (entity instanceof Skeleton && entity.getCustomName() != null
                && entity.getCustomName().contains("Sans")) {
            var loc = entity.getLocation();
            
            loc.getNearbyPlayers(20).stream().forEach(player ->{
                player.playSound(player.getLocation(), "sans_talking", 1, 1);
            });

        }
    }

    @EventHandler
    public void skeletonsDamage(EntityDamageEvent e) {
        var entity = e.getEntity();
        if (entity instanceof Skeleton && entity.getCustomName() != null
        && entity.getCustomName().contains("Sans") && (e.getCause() == DamageCause.WITHER || e.getCause() == DamageCause.ENTITY_EXPLOSION)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void sounds1(EntityDamageByEntityEvent e) {
        var damager = e.getDamager();
        var entity = e.getEntity();

        if (damager instanceof Husk) {

            var loc = damager.getLocation();
            loc.getNearbyPlayers(20).stream().forEach(player ->{
                player.playSound(player.getLocation(), "burp", 1, 1);
            });

        } else if (entity instanceof Skeleton && entity.getCustomName() != null
                && entity.getCustomName().contains("Sans") && (damager instanceof Player || damager instanceof Arrow)) {

            var loc = damager.getLocation();
            loc.getNearbyPlayers(20).stream().forEach(player ->{
                player.playSound(player.getLocation(), "sans_song", 1, 1);
            });

        }

    }

    
    @EventHandler(priority = EventPriority.LOW)
    public void entityDamage(EntityDeathEvent e) {
        var entity = e.getEntity();
        if (entity.getName().toString().contains("Rubber")) {
            var loc = entity.getLocation();
            switch (random.nextInt(4)) {
                case 1:

                    loc.getNearbyPlayers(20).stream().forEach(p -> {
                        p.playSound(p.getLocation(), "pollo", 1, 1);
                    });

                    break;
                case 2:

                    loc.getNearbyPlayers(20).stream().forEach(p -> {
                        p.playSound(p.getLocation(), "pollo_2", 1, 1);
                    });

                    break;
                case 3:

                    loc.getNearbyPlayers(20).stream().forEach(p -> {
                        p.playSound(p.getLocation(), "pollo_3", 1, 1);
                    });

                    break;

                default:

                    loc.getNearbyPlayers(20).stream().forEach(p -> {
                        p.playSound(p.getLocation(), "pollo_4", 1, 1);
                    });

                    break;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void rubberSoundItem(EntityDamageByEntityEvent e) {
        var entity = e.getEntity();
        var damager = e.getDamager();
        if (damager instanceof Player) {
            var player = (Player) damager;
            var rubber = player.getEquipment().getItemInMainHand().getItemMeta();
            if (rubber != null && rubber.hasCustomModelData() && rubber.getCustomModelData() == 137) {
                var loc = damager.getLocation();
                switch (random.nextInt(4)) {
                    case 1:

                        loc.getNearbyPlayers(20).stream().forEach(p -> {
                            p.playSound(p.getLocation(), "pollo", 1, 1);
                        });

                        break;
                    case 2:

                        loc.getNearbyPlayers(20).stream().forEach(p -> {
                            p.playSound(p.getLocation(), "pollo_2", 1, 1);
                        });

                        break;
                    case 3:

                        loc.getNearbyPlayers(20).stream().forEach(p -> {
                            p.playSound(p.getLocation(), "pollo_3", 1, 1);
                        });

                        break;

                    default:

                        loc.getNearbyPlayers(20).stream().forEach(p -> {
                            p.playSound(p.getLocation(), "pollo_4", 1, 1);
                        });

                        break;
                }
            }
        } else if (damager instanceof Spider spider && entity instanceof Player player) {
            if (spider.getCustomName() != null) {
                var name = spider.getCustomName().toString();

                if (name.contains("Green")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 30, 0));

                } else if (name.contains("Stone")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 20, 2));

                } else if (name.contains("Dust")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 0));

                } else if (name.contains("Glaciar")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 3));

                } else if (name.contains("Ancient")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20 * 5, 200));
                }

            }

        }

    }
}
