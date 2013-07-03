package us.Myles.EE;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

public class EE3 extends JavaPlugin implements Listener{
	public static ItemStack Stone = new ItemStack(Material.MAGMA_CREAM,1);
	private static ItemStack anyCoal = new ItemStack(Material.COAL, 1, (short)-1);
    private static ItemStack anyWood = new ItemStack(Material.LOG, 1, (short)-1);
    private static ItemStack anyPlank = new ItemStack(Material.WOOD, 1, (short)-1);
    private static ItemStack anySandStone = new ItemStack(Material.SANDSTONE, 1, (short)-1);
    public int count = 0;
    private static ItemStack dyeBoneMeal = new ItemStack(351, 1,(short) 15);
    public HashMap<String, Permission> dynamicPerms = new HashMap<String, Permission>();
    public static Permission craftPermission = new Permission("eeb.craft",PermissionDefault.TRUE);
    public static Permission usePermission = new Permission("eeb.use",PermissionDefault.TRUE);
    public static Permission workbenchPermission = new Permission("eeb.workbench",PermissionDefault.TRUE);
    public static Permission wildcardPermission = new Permission("eeb.output.*",PermissionDefault.FALSE);
    public static Permission wildcard2Permission = new Permission("eeb.*",PermissionDefault.FALSE);
    public ConcurrentHashMap<String, Boolean> currentCrafting = new ConcurrentHashMap<String, Boolean>();
    
	public void onEnable(){
		ItemMeta itemMeta = Stone.getItemMeta();
		itemMeta.setDisplayName("Minium Stone");
		itemMeta.setLore(Arrays.asList(new String[]{"1521/1521 Uses Left"}));
		Stone.setDurability((short)20);
		Stone.setItemMeta(itemMeta);
		Stone.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 2);
		loadRecipes();
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().addPermission(craftPermission);
		getServer().getPluginManager().addPermission(usePermission);
		getServer().getPluginManager().addPermission(workbenchPermission);
		getServer().getPluginManager().addPermission(wildcardPermission);
		getServer().getPluginManager().addPermission(wildcard2Permission);
		System.out.println("Loaded - " + count + " Recipes!");
	}
	public void loadRecipes() {
		a(new ItemStack(Material.COBBLESTONE, 4),new ItemStack[]{Stone, new ItemStack(Material.FLINT)});
		a(new ItemStack(Material.DIRT, 4),new ItemStack[]{Stone, new ItemStack(Material.GRAVEL)});
		a(new ItemStack(Material.SAND, 4),new ItemStack[]{Stone, anySandStone});
		r(new ItemStack(Material.WOOD),new ItemStack[]{Stone, new ItemStack(Material.STICK,2)});
		r(anyWood,new ItemStack[]{Stone, m(anyPlank,4)});
		
		a(new ItemStack(Material.CLAY_BALL),new ItemStack[]{Stone, new ItemStack(Material.GRAVEL,4)});
		r(new ItemStack(Material.CLAY_BALL),new ItemStack[]{Stone, m(anySandStone,4)});
		r(new ItemStack(Material.CLAY_BALL),new ItemStack[]{Stone, new ItemStack(Material.FLINT,4)});
		r(new ItemStack(Material.FLINT,2),new ItemStack[]{Stone, new ItemStack(Material.SANDSTONE,2,(short) 2)});
		a(new ItemStack(Material.OBSIDIAN),new ItemStack[]{Stone, m(anyWood,2)});
		
		a(new ItemStack(Material.CLAY),new ItemStack[]{Stone, new ItemStack(Material.CLAY_BALL,4)});
		
		// Minerals
		r(new ItemStack(Material.IRON_INGOT),new ItemStack[]{Stone, new ItemStack(Material.OBSIDIAN,4)});
		a(new ItemStack(Material.IRON_INGOT),new ItemStack[]{Stone, new ItemStack(Material.CLAY,4)});
		
		a(new ItemStack(Material.GOLD_INGOT),new ItemStack[]{Stone, new ItemStack(Material.IRON_INGOT,8)});
		a(new ItemStack(Material.DIAMOND),new ItemStack[]{Stone, new ItemStack(Material.GOLD_INGOT,4)});
		a(new ItemStack(Material.GOLD_BLOCK),new ItemStack[]{Stone, new ItemStack(Material.IRON_BLOCK,8)});
		a(new ItemStack(Material.DIAMOND_BLOCK),new ItemStack[]{Stone, new ItemStack(Material.GOLD_BLOCK,4)});
		
		a(new ItemStack(Material.ENDER_PEARL),new ItemStack[]{Stone, new ItemStack(Material.IRON_INGOT,4)});
		
		//Reconstructive
		a(new ItemStack(Material.BONE),new ItemStack[]{Stone,m(dyeBoneMeal,3)});
		a(new ItemStack(Material.BLAZE_ROD),new ItemStack[]{Stone, new ItemStack(Material.BLAZE_POWDER,2)});
		//Destructor
		r(new ItemStack(Material.COBBLESTONE),new ItemStack[]{Stone, new ItemStack(Material.STONE)});
		r(new ItemStack(Material.SAND),new ItemStack[]{Stone, new ItemStack(Material.GLASS)});
		r(new ItemStack(Material.GLOWSTONE_DUST,4),new ItemStack[]{Stone, new ItemStack(Material.GLOWSTONE)});
		r(new ItemStack(Material.CLAY_BRICK,4),new ItemStack[]{Stone, new ItemStack(Material.BRICK)});
		//Equivalent List
		a(new ItemStack(Material.RED_MUSHROOM),new ItemStack[]{Stone,new ItemStack(Material.BROWN_MUSHROOM)});
		a(new ItemStack(Material.RED_ROSE),new ItemStack[]{Stone,new ItemStack(Material.YELLOW_FLOWER)});
		a(new ItemStack(Material.PUMPKIN),new ItemStack[]{Stone,new ItemStack(Material.MELON_BLOCK)});
		a(new ItemStack(Material.PUMPKIN_SEEDS),new ItemStack[]{Stone,new ItemStack(Material.MELON_SEEDS)});
		a(new ItemStack(Material.PAPER,3),new ItemStack[]{Stone,new ItemStack(Material.SUGAR_CANE,3)});
		
		a(new ItemStack(Material.SANDSTONE,2,(short) 1),new ItemStack[]{Stone,new ItemStack(Material.SANDSTONE,2)});
		c(new ItemStack(Material.LOG),4);
		c(new ItemStack(Material.WOOD),4);
		c(new ItemStack(Material.WOOD_STEP),4);
		c(new ItemStack(Material.WOOD_STAIRS),4);
		c(new ItemStack(Material.DIRT),new ItemStack(Material.COBBLESTONE),new ItemStack(Material.GRASS),new ItemStack(Material.SAND));
		// Stone
		ShapedRecipe sr = new ShapedRecipe(Stone);
		sr.shape("SGS","GIG","SGS");
		sr.setIngredient('S', Material.STONE);
		sr.setIngredient('G', Material.GOLD_INGOT);
		sr.setIngredient('I', Material.IRON_INGOT);
		getServer().addRecipe(sr);
	}
	public void c(ItemStack... items){
		for(int e = 0;e<items.length;e++){
			if(e + 1 == items.length){
				r(items[0],new ItemStack[]{Stone,items[e]},false);
			}else
			{
				r(items[e + 1],new ItemStack[]{Stone,items[e]},false);
			}
			
		}
	}
	public void c(ItemStack item, int i){
		for(int e = 0;e<i;e++){
			if(e + 1 == i){
				r(new ItemStack(item.getType(),1,(short)e),new ItemStack[]{Stone,new ItemStack(item.getType(),1,(short)0)},false);
			}else
			{
				r(new ItemStack(item.getType(),1,(short)e),new ItemStack[]{Stone,new ItemStack(item.getType(),1,(short)(e+1))},false);
			}
			
		}
	}
	public ItemStack m(ItemStack item, int i) {
		item.setAmount(i);
		return item;
	}
	@EventHandler
	public void interactEvent(PlayerInteractEvent event){
		if(event.getItem() != null){
			if(event.getItem().getType().equals(Material.MAGMA_CREAM) && event.getItem().getEnchantmentLevel(Enchantment.ARROW_INFINITE) == 2){
				if(!event.getPlayer().hasPermission(workbenchPermission) && !event.getPlayer().hasPermission(wildcard2Permission)){
					return;
				}
				event.getPlayer().openWorkbench(null, true);
			}
			if(event.getItem().getType().equals(Material.MAGMA_CREAM) && event.getItem().getDurability() == 2){
				event.getPlayer().getInventory().remove(event.getItem());
				event.getPlayer().getInventory().addItem(Stone);
			}
		}
	}
	@EventHandler
	public void craftEvent(final CraftItemEvent event){
		if(event.getRecipe() instanceof ShapelessRecipe){
			if(currentCrafting.containsKey(event.getViewers().get(0).getName())){
				event.setCancelled(true);
				return;
			}
			ShapelessRecipe sr = (ShapelessRecipe) event.getRecipe();
			if(sr.getResult().isSimilar(Stone)){
				if(!event.getViewers().get(0).hasPermission(craftPermission) && !event.getViewers().get(0).hasPermission(wildcard2Permission)){
					event.setCancelled(true);
				}
				return;
			}
			if(!sr.getIngredientList().get(0).getType().equals(Material.MAGMA_CREAM)) return;
			int slot = -1;
			for(int i = 1;i<=9;i++){
				if(event.getView().getItem(i) != null){
					if(event.getView().getItem(i).getType().equals(Material.MAGMA_CREAM) && event.getView().getItem(i).getEnchantmentLevel(Enchantment.ARROW_INFINITE) == 2){
						slot = i;
						break;
					}
				}
			}
			if(slot == -1){
				return;
			}
			final int slot_data = slot;
			final ItemStack MyStone = event.getView().getItem(slot);
			if(!event.getViewers().get(0).hasPermission(usePermission) && !event.getViewers().get(0).hasPermission(wildcardPermission) && !event.getViewers().get(0).hasPermission(wildcard2Permission) || !event.getViewers().get(0).hasPermission(getPermission(sr.getResult().getType().name())) && !event.getViewers().get(0).hasPermission(wildcardPermission) && !event.getViewers().get(0).hasPermission(wildcard2Permission) || !event.getViewers().get(0).hasPermission(getPermission(sr.getResult().getType().getId() + "")) && !event.getViewers().get(0).hasPermission(wildcardPermission) && !event.getViewers().get(0).hasPermission(wildcard2Permission)){
				event.setCancelled(true);
				return;
			}
			if(slot == -1){
				event.setCancelled(true); return;
			}
			currentCrafting.put(event.getViewers().get(0).getName(), true);
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
				public void run(){
					
					ItemStack stone = createStone(MyStone);
					if(stone == null){
						for(HumanEntity h:event.getViewers()){
							Player p = (Player) h;
							p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1,1);
						}
					}
					if(event.getView().getItem(slot_data) != null){
						event.getView().setItem(slot_data, stone);
					}
					currentCrafting.remove(event.getViewers().get(0).getName());
				}
			}, 2L);
			
		}
		
		
	}
	private Permission getPermission(String name){
		return dynamicPerms.get(name);
	}
	private ItemStack createStone(ItemStack myStone) {
		if(myStone == null){
			return null;
		}
		ItemMeta im = myStone.getItemMeta();
		if(im == null){
			return null;
		}
		int n = Integer.parseInt(im.getLore().get(0).split("/")[0]) - 1;
		im.setLore(Arrays.asList(new String[]{n + "/1521 Uses Left"}));
		myStone.setItemMeta(im);
		if(n <= 0){
			
			return null;
		}
		return myStone;
	}
	public void a(ItemStack output, ItemStack[] ingredients){
		// Reversable Recipe
		r(output,ingredients);
		r(ingredients[1],new ItemStack[]{ingredients[0],output});
	}
	public void r(ItemStack output, ItemStack[] ingredients){
		r(output,ingredients,true);
	}
	public void r(ItemStack output, ItemStack[] ingredients, boolean checkSub){
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		for(ItemStack in : ingredients){
			if(in == null) continue;
			if(checkSub){
				if(in.isSimilar(anyPlank)){
					for(int i=0;i<=4;i++){
						ItemStack in2 = new ItemStack(Material.WOOD, in.getAmount(), (short)i);
						r(output,new ItemStack[]{ingredients[0],in2});
					}
				}
				if(in.isSimilar(anyWood)){
					for(int i=0;i<=4;i++){
						ItemStack in2 = new ItemStack(Material.LOG, in.getAmount(), (short)i);
						r(output,new ItemStack[]{ingredients[0],in2});
					}
				}
				if(in.isSimilar(anyCoal)){
					for(int i=0;i<=1;i++){
						ItemStack in2 = new ItemStack(Material.COAL, in.getAmount(), (short)i);
						r(output,new ItemStack[]{ingredients[0],in2});
					}
				}
				if(in.isSimilar(anySandStone)){
					for(int i=0;i<=1;i++){
						ItemStack in2 = new ItemStack(Material.SANDSTONE, in.getAmount(), (short)i);
						r(output,new ItemStack[]{ingredients[0],in2});
					}
				}
			}
			list.add(in);
		}
		if(checkSub){
			if(output.getType() == Material.LOG && list.get(1).getType() == Material.WOOD){
				output.setDurability(list.get(1).getDurability());
			}
			if(output.getType() == Material.WOOD && list.get(1).getType() == Material.LOG){
				output.setDurability(list.get(1).getDurability());
			}
		}
		if(!dynamicPerms.containsKey(output.getType().name())){
			dynamicPerms.put(output.getType().name(), new Permission("eeb.output." + output.getType().name(),PermissionDefault.TRUE));
			getServer().getPluginManager().addPermission(dynamicPerms.get(output.getType().name()));
			dynamicPerms.put(output.getType().getId() + "", new Permission("eeb.output." + output.getType().getId(),PermissionDefault.TRUE));
			getServer().getPluginManager().addPermission(dynamicPerms.get(output.getType().getId() + ""));
		}
		ShapelessRecipe sr = new ShapelessRecipe(output);
		for(ItemStack i:list){
			sr.addIngredient(i.getAmount(),i.getData());
		}
	//	if(list.size() > 1){
			//System.out.println(output.getType().name() + "[" + output.getDurability() + "]" + "->" + list.get(0).getType().name()  + "[" + list.get(0).getAmount() + "]" + "+" + list.get(1).getType().name()  + "[" + list.get(1).getDurability() + "]");
		//}
		getServer().addRecipe(sr);
		this.count += 1;
	}
}
