package me.aleiv.core.paper;

import net.md_5.bungee.api.ChatColor;

public class EffectStorage {
    
    Core instance;

    String RED = "<#ff1418>";
    String WHITE = ChatColor.WHITE + "";

    public EffectStorage(Core instance){
        this.instance = instance;

    }

    public void refreshChange(Integer i){
        var game = instance.getGame();
        var change = game.getCurrentChange();

        var ruedita = game.rueditaAnim();
        ruedita.thenAccept(action ->{
            switch (change) {
                case 1 ->{
                    game.setDamageResistance(20);
                    instance.broadcastMessage(RED + "RUEDITA! " + WHITE + "Resistencia global incremento al 20%");
                }
                case 2 ->{
                    game.getChanges().put("ZOMBIE", true);
                    instance.broadcastMessage(RED + "RUEDITA! " + WHITE + "Todos los zombies han cambiado a Zombie Chef");
                }
                case 3 ->{
                    game.getChanges().put("SKELETON", true);
                    instance.broadcastMessage(RED + "RUEDITA! " + WHITE + "Todos los esqueletos han cambiado");
                }
                case 4 ->{
                    game.setDamageResistance(40);
                    instance.broadcastMessage(RED + "RUEDITA! " + WHITE + "Resistencia global incremento al 40%");
                }
                case 5 ->{
                    game.getChanges().put("SPIDER", true);
                    instance.broadcastMessage(RED + "RUEDITA! " + WHITE + "Todas las arañas han cambiado");
                }
                case 6 ->{
                    game.getChanges().put("CREEPER", true);
                    instance.broadcastMessage(RED + "RUEDITA! " + WHITE + "Todos los creeper han cambiado");
                }

                case 7 ->{
                    game.setDamageMultiplier(2);
                    instance.broadcastMessage(RED + "RUEDITA! " + WHITE + "Multiplicador de daño a cambiado a x2");
                }

                case 8 ->{
                    game.setDamageMultiplier(3);
                    instance.broadcastMessage(RED + "RUEDITA! " + WHITE + "Multiplicador de daño a cambiado a x3");
                }

                case 9 ->{
                    game.setDamageResistance(60);
                    instance.broadcastMessage(RED + "RUEDITA! " + WHITE + "Resistencia global incremento al 60%");
                }

                case 10 ->{
                    game.setDamageMultiplier(4);
                    instance.broadcastMessage(RED + "RUEDITA! " + WHITE + "Multiplicador de daño a cambiado a x4");
                }

                case 11 ->{
                    game.setDamageResistance(80);
                    instance.broadcastMessage(RED + "RUEDITA! " + WHITE + "Resistencia global incremento al 80%");
                }

                default ->{

                }
            }
        });

    }

    public void meteorito(){
        
    }

    public void lluviaMeteoritos(){

    }



    

}
