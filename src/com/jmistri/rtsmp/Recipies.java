package com.jmistri.rtsmp;

import org.bukkit.plugin.java.JavaPlugin;

public class Recipies extends JavaPlugin {

        ItemStack bottle = new ItemStack(Material.EXP_BOTTLE, 1);

        ShapedRecipe expBottle = new ShapedRecipe(bottle);

        expBottle.shape("*%*","%B%","*%*");

        expBottle.setIngredient('*', Material.INK_SACK, 2);
        expBottle.setIngredient('%', Material.SUGAR);
        expBottle.setIngredient('B', Material.GLASS_BOTTLE);

        getServer().addRecipe(expBottle);
}
