package me.aleiv.core.paper;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Illusioner;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Ravager;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class MobStorage {

    Core instance;

    Random random = new Random();

    public MobStorage(Core instance) {
        this.instance = instance;
    }

    public Entity spawnEntity(Location loc, EntityType type) {
        var world = loc.getWorld();
        return world.spawnEntity(loc, type);
    }

    public void spawnPiglin(Location loc){
        var piglin = (Piglin) spawnEntity(loc, EntityType.PIGLIN);
        piglin.setRemoveWhenFarAway(true);

        piglin.setImmuneToZombification(true);
            piglin.setAdult();
            switch (random.nextInt(4)) {
                case 1: {

                    piglin.setCustomName(ChatColor.BLUE + "Piglin Gentleman");
                    var hat = new ItemStack(Material.CARVED_PUMPKIN);
                    var hatMeta = hat.getItemMeta();
                    hatMeta.setCustomModelData(69);
                    hatMeta.setDisplayName(ChatColor.YELLOW + "Black Hat");
                    hat.setItemMeta(hatMeta);

                    var weapon = new ItemStack(Material.GOLDEN_SWORD);
                    var weaponMeta = weapon.getItemMeta();
                    weaponMeta.setCustomModelData(86);
                    weaponMeta.setDisplayName(ChatColor.YELLOW + "Walking Stick");
                    weaponMeta.setUnbreakable(true);
                    weapon.setItemMeta(weaponMeta);

                    var equipment = piglin.getEquipment();
                    equipment.setHelmet(hat);
                    equipment.setItemInMainHand(weapon);

                }
                    break;

                case 2: {
                    piglin.setCustomName(ChatColor.BLUE + "Wizard Piglin");

                    var weapon = new ItemStack(Material.CROSSBOW);
                    var weaponMeta = weapon.getItemMeta();
                    weaponMeta.setCustomModelData(112);
                    weaponMeta.setDisplayName(ChatColor.DARK_PURPLE + "Imploding Crossbow");
                    weapon.setItemMeta(weaponMeta);

                    var equipment = piglin.getEquipment();
                    equipment.setItemInMainHand(weapon);
                }
                    break;

                case 3: {
                    piglin.setCustomName(ChatColor.BLUE + "Piglin Rider");

                    var weapon = new ItemStack(Material.CROSSBOW);
                    var meta = (CrossbowMeta) weapon.getItemMeta();
                    meta.setCustomModelData(105);
                    meta.setDisplayName(ChatColor.DARK_GREEN + "Exploding Crossbow");

                    var rocket = new ItemStack(Material.FIREWORK_ROCKET);
                    var rocketMeta = (FireworkMeta) rocket.getItemMeta();
                    var fireworkEffect = FireworkEffect.builder().withColor(Color.OLIVE).withFade(Color.RED).build();
                    rocketMeta.addEffect(fireworkEffect);
                    rocket.setItemMeta(rocketMeta);

                    var proyectiles = List.of(rocket, rocket, rocket);
                    meta.setChargedProjectiles(proyectiles);
                    weapon.setItemMeta(meta);

                    var equipment = piglin.getEquipment();
                    equipment.setItemInMainHand(weapon);

                }
                    break;

                default: {
                    piglin.setCustomName(ChatColor.BLUE + "Piglin Blacksmith");

                    var hat = new ItemStack(Material.NETHERITE_HELMET);

                    var outfit = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                    var outfitMeta = outfit.getItemMeta();
                    outfitMeta.setCustomModelData(130);
                    outfitMeta.setDisplayName(ChatColor.YELLOW + "Blacksmith Outfit");
                    outfitMeta.setUnbreakable(true);
                    outfit.setItemMeta(outfitMeta);

                    var weapon = new ItemStack(Material.NETHERITE_SWORD);
                    var weaponMeta = weapon.getItemMeta();
                    weaponMeta.setCustomModelData(96);
                    weaponMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Great Hammer");

                    final AttributeModifier damage = new AttributeModifier(UUID.randomUUID(), "GENERIC.ATTACK.DAMAGE",
                            12.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                    weaponMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damage);

                    final AttributeModifier attackSpeed = new AttributeModifier(UUID.randomUUID(),
                            "GENERIC.ATTACK.SPEED", -0.90, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HAND);
                    weaponMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeed);

                    weapon.setItemMeta(weaponMeta);

                    var equipment = piglin.getEquipment();
                    equipment.setHelmet(hat);
                    equipment.setItemInMainHand(weapon);
                    equipment.setLeggings(outfit);

                }
                    break;
            }
    }

    public void spawnCreeper(Location loc){
        var creeper = (Creeper) spawnEntity(loc, EntityType.CREEPER);
        creeper.setRemoveWhenFarAway(true);

        switch (random.nextInt(5)) {
            case 1: {
                creeper.setCustomName(ChatColor.GREEN + "Tesla Creeper");
                creeper.setPowered(true);
            }
                break;
            case 2: {
                creeper.setCustomName(ChatColor.GREEN + "Impostor Creeper");
                creeper.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 300, 0));
                creeper.setSilent(true);
                creeper.setExplosionRadius(3);
            }
                break;
            case 3: {
                creeper.setCustomName(ChatColor.GREEN + "Creeperscrimer");
                creeper.setExplosionRadius(3);
                creeper.setMaxFuseTicks(5);
                creeper.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 120, 2));
            }
                break;
            case 4: {
                creeper.setCustomName(ChatColor.GREEN + "Atomic Creeper");
                creeper.setPowered(true);
                creeper.setExplosionRadius(10);
                creeper.setHealth(creeper.getHealth() / 2);
                creeper.setGlowing(true);
            }
                break;
            // rubber chicken
            default: {
                creeper.setCustomName(ChatColor.GREEN + "Rainbow Creeper");
                creeper.setSilent(true);
                creeper.setExplosionRadius(2);
                creeper.setMaxFuseTicks(5);
                creeper.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 120, 2));

                var pollo = new ItemStack(Material.RABBIT_FOOT);
                var polloMeta = pollo.getItemMeta();
                polloMeta.setCustomModelData(137);
                polloMeta.setDisplayName(ChatColor.YELLOW + "Rubber Chicken");
                pollo.setItemMeta(polloMeta);

                var miniZombie = (Zombie) creeper.getWorld().spawnEntity(creeper.getLocation(), EntityType.ZOMBIE);
                miniZombie.setCustomName(ChatColor.YELLOW + "Rubber Chicken");
                miniZombie.setSilent(true);
                miniZombie.setInvisible(true);
                miniZombie.setBaby();
                miniZombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0);

                var equipment = miniZombie.getEquipment();
                equipment.setHelmetDropChance(100);
                equipment.setHelmet(pollo);
                equipment.setChestplate(new ItemStack(Material.AIR));
                equipment.setLeggings(new ItemStack(Material.AIR));
                equipment.setBoots(new ItemStack(Material.AIR));
                equipment.setItemInMainHand(new ItemStack(Material.AIR));
                equipment.setItemInOffHand(new ItemStack(Material.AIR));

                creeper.addPassenger(miniZombie);
            }
                break;
        }
    }

    public void spawnSpider(Location loc) {
        var spider = (Spider) spawnEntity(loc, EntityType.SPIDER);
        spider.setRemoveWhenFarAway(true);

        spider.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 600, 1));
        spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 600, 1));
        spider.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 600, 1));
        switch (random.nextInt(5)) {
        case 1:
            spider.setCustomName(ChatColor.GREEN + "Green Spider");

            break;
        case 2:
            spider.setCustomName(ChatColor.GREEN + "Stone Spider");

            break;
        case 3:
            spider.setCustomName(ChatColor.GREEN + "Dust Spider");

            break;
        case 4:
            spider.setCustomName(ChatColor.GREEN + "Glaciar Spider");

            break;

        default:
            spider.setCustomName(ChatColor.GREEN + "Ancient Spider");
            break;
        }
    }

    public void spawnMage(Location loc) {

        switch (random.nextInt(3)) {
        case 1: {
            var evoker = (Evoker) spawnEntity(loc, EntityType.EVOKER);
            evoker.setCustomName(ChatColor.AQUA + "Warped Mage");
            evoker.setRemoveWhenFarAway(true);
        }
            break;

        case 2: {
            var evoker = (Evoker) spawnEntity(loc, EntityType.EVOKER);
            evoker.setCustomName(ChatColor.AQUA + "Purple Mage");
            evoker.setRemoveWhenFarAway(true);
        }
            break;

        default: {

            var illusioner = (Illusioner) loc.getWorld().spawnEntity(loc, EntityType.ILLUSIONER);
            illusioner.setRemoveWhenFarAway(true);
            illusioner.setCustomName(ChatColor.AQUA + "Illusioner");

            var magicStaff = new ItemStack(Material.BOW);
            var meta = magicStaff.getItemMeta();
            meta.setCustomModelData(116);
            meta.setDisplayName(ChatColor.DARK_AQUA + "Magic Staff");
            meta.addEnchant(Enchantment.ARROW_DAMAGE, 4, true);
            magicStaff.setItemMeta(meta);

            illusioner.getEquipment().setItemInMainHand(magicStaff);
        }
            break;
        }
    }

    public void spawnMountainer(Location loc) {
        var vindicator = (Vindicator) spawnEntity(loc, EntityType.VINDICATOR);
        vindicator.setRemoveWhenFarAway(true);

        vindicator.setCustomName(ChatColor.GOLD + "Mountaineer");
        vindicator.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 300, 1));

        var axe = new ItemStack(Material.NETHERITE_AXE);

        vindicator.getEquipment().setItemInMainHand(axe);

    }

    public void spawnRaider(Location loc) {
        var pillager = (Pillager) spawnEntity(loc, EntityType.PILLAGER);
        pillager.setRemoveWhenFarAway(true);

        var gun = new ItemStack(Material.CROSSBOW);
        var meta = (CrossbowMeta) gun.getItemMeta();
        switch (random.nextInt(3)) {
        case 1: {
            meta.setDisplayName(ChatColor.GRAY + "Sniper");
            meta.setCustomModelData(108);

            pillager.setCustomName(ChatColor.GOLD + "Servant");
        }
            break;

        case 2: {
            meta.setDisplayName(ChatColor.GRAY + "Pistol");
            meta.setCustomModelData(107);

            pillager.setCustomName(ChatColor.GOLD + "Blacksmith");
        }
            break;

        default: {
            meta.setDisplayName(ChatColor.GRAY + "Pistol");
            meta.setCustomModelData(109);

            pillager.setCustomName(ChatColor.GOLD + "Butler");
        }
            break;
        }

        gun.setItemMeta(meta);
        pillager.getEquipment().setItemInMainHand(gun);

    }

    public void spawnRedstoneGolem(Location loc) {
        var ravager = (Ravager) spawnEntity(loc, EntityType.RAVAGER);
        ravager.setRemoveWhenFarAway(true);
        ravager.setCustomName(ChatColor.RED + "Redstone Golem");

    }

    public void spawnRavagerPowerfull(Location loc) {
        var ravager = (Ravager) spawnEntity(loc, EntityType.RAVAGER);
        ravager.setRemoveWhenFarAway(true);
        ravager.setCustomName(ChatColor.BLUE + "Ravager Powerful");

    }

    public void spawnWitherSkeleton(Location loc) {
        var skeleton = (WitherSkeleton) spawnEntity(loc, EntityType.WITHER_SKELETON);
        skeleton.setRemoveWhenFarAway(true);
        skeleton.setCustomName(ChatColor.RED + "The Death");

        var weapon = new ItemStack(Material.NETHERITE_SWORD);
        var meta = weapon.getItemMeta();
        meta.setCustomModelData(90);
        meta.setDisplayName(ChatColor.BLUE + "Rhaast");
        weapon.setItemMeta(meta);
        var skeletonEquipment = skeleton.getEquipment();
        skeletonEquipment.setItemInMainHand(weapon);

    }

    public void spawnChef(Location loc) {
        var chef = (Husk) spawnEntity(loc, EntityType.HUSK);

        chef.setRemoveWhenFarAway(true);
        chef.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 600, 0));

        var chefHat = new ItemStack(Material.CARVED_PUMPKIN);
        var chefHatMeta = chefHat.getItemMeta();
        chefHatMeta.setCustomModelData(71);
        chefHatMeta.setDisplayName(ChatColor.YELLOW + "Chef Hat");
        chefHatMeta.setUnbreakable(true);
        chefHat.setItemMeta(chefHatMeta);

        var chefOutfit = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        var chefOutfitMeta = chefOutfit.getItemMeta();
        chefOutfitMeta.setCustomModelData(130);
        chefOutfitMeta.setDisplayName(ChatColor.YELLOW + "Chef Outfit");
        chefOutfitMeta.setUnbreakable(true);
        chefOutfit.setItemMeta(chefOutfitMeta);

        var taco = new ItemStack(Material.CHORUS_FRUIT);
        var tacoMeta = taco.getItemMeta();
        tacoMeta.setCustomModelData(136);
        tacoMeta.setDisplayName(ChatColor.DARK_GREEN + "Taco");
        taco.setItemMeta(tacoMeta);

        chef.setCustomName(ChatColor.RED + "Death Chef");
        var chefEquipment = chef.getEquipment();
        chefEquipment.setHelmet(chefHat);
        chefEquipment.setLeggings(chefOutfit);
        chefEquipment.setItemInMainHand(taco);
        chefEquipment.setItemInMainHandDropChance(0.1f);
    }

    public void spawnSkeleton(Location loc) {
        
        var skeleton = (Skeleton) spawnEntity(loc, EntityType.SKELETON);
        skeleton.setRemoveWhenFarAway(true);

        switch (random.nextInt(4)) {
        case 1: {
            skeleton.setCustomName(ChatColor.AQUA + "Sans");
            var bow = new ItemStack(Material.BOW);
            var meta = bow.getItemMeta();
            meta.setCustomModelData(113);
            meta.setDisplayName(ChatColor.BLUE + "Soul Bow");
            bow.setItemMeta(meta);
            var skeletonEquipment = skeleton.getEquipment();
            skeletonEquipment.setItemInMainHand(bow);
        }
            break;
        case 2: {
            skeleton.setCustomName(ChatColor.RED + "Zozo");
            var bow = new ItemStack(Material.BOW);
            var meta = bow.getItemMeta();
            meta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
            bow.setItemMeta(meta);
            var skeletonEquipment = skeleton.getEquipment();
            skeletonEquipment.setItemInMainHand(bow);
        }
            break;
        case 3: {
            skeleton.setCustomName(ChatColor.RED + "Archer");
            var bow = new ItemStack(Material.BOW);
            var meta = bow.getItemMeta();
            meta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
            bow.setItemMeta(meta);
            var skeletonEquipment = skeleton.getEquipment();
            skeletonEquipment.setItemInMainHand(bow);
        }
            break;

        default: {
            skeleton.setCustomName(ChatColor.RED + "Captain Parrot");

            var bow = new ItemStack(Material.BOW);
            var meta = bow.getItemMeta();
            meta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
            bow.setItemMeta(meta);

            var pirateHat = new ItemStack(Material.CARVED_PUMPKIN);
            var hatMeta = pirateHat.getItemMeta();
            hatMeta.setCustomModelData(81);
            hatMeta.setDisplayName(ChatColor.YELLOW + "Pirate Hat");
            hatMeta.setUnbreakable(true);
            pirateHat.setItemMeta(hatMeta);

            var pirateChest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
            var chestMeta = pirateChest.getItemMeta();
            chestMeta.setCustomModelData(131);
            chestMeta.setDisplayName(ChatColor.YELLOW + "End Rob");
            chestMeta.setUnbreakable(true);
            pirateChest.setItemMeta(chestMeta);

            var pirateLegs = new ItemStack(Material.DIAMOND_LEGGINGS);
            var legsMeta = pirateLegs.getItemMeta();
            legsMeta.setCustomModelData(128);
            legsMeta.setDisplayName(ChatColor.DARK_GREEN + "Rusty Leggings");
            pirateLegs.setItemMeta(legsMeta);

            var pirateBoots = new ItemStack(Material.DIAMOND_BOOTS);
            var bootsMeta = pirateBoots.getItemMeta();
            bootsMeta.setCustomModelData(128);
            bootsMeta.setDisplayName(ChatColor.DARK_GREEN + "Rusty Boots");
            pirateBoots.setItemMeta(bootsMeta);

            var skeletonEquipment = skeleton.getEquipment();

            skeletonEquipment.setItemInOffHand(new ItemStack(Material.IRON_SWORD));
            skeletonEquipment.setItemInMainHand(bow);
            skeletonEquipment.setHelmet(pirateHat);
            skeletonEquipment.setChestplate(pirateChest);
            skeletonEquipment.setLeggings(pirateLegs);
            skeletonEquipment.setBoots(pirateBoots);

        }
            break;
        }
    }

}
