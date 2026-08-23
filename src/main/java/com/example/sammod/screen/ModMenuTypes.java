package com.example.sammod.screen;

import com.example.sammod.SamMod;
import com.example.sammod.screen.custom.GrowthChamberMenu;
import com.example.sammod.screen.custom.PedestalMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


//This is the deferred registry for GUI
public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, SamMod.MOD_ID);

    public static final RegistryObject<MenuType<PedestalMenu>> PEDESTAL_MENU =
            MENUS.register("pedestal_menu", () -> IForgeMenuType.create(PedestalMenu::new));

    public static final RegistryObject<MenuType<GrowthChamberMenu>> GROWTH_CHAMBER_MENU =
            MENUS.register("growth_chamber_menu", () -> IForgeMenuType.create(GrowthChamberMenu::new));

    public static void register(IEventBus bus){
        MENUS.register(bus);
    }
}
