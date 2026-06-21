package com.example.sammod.sound;

import com.example.sammod.SamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class MySillySounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SamMod.MOD_ID);

    //The names of the sounds in the sounds.json file in resources/assets/sammod have to match the names given here,
    //but do not have to match the name of the .ogg files in the sounds directory.

    public static final RegistryObject<SoundEvent> SUSIE_BLOCK_HIT = registerSoundEvent("susie_block_hit");
    public static final RegistryObject<SoundEvent> SUSIE_BLOCK_BREAK = registerSoundEvent("susie_block_break");
    public static final RegistryObject<SoundEvent> SUSIE_BLOCK_PLACE = registerSoundEvent("susie_block_place");

    public static final RegistryObject<SoundEvent> METEORITE_PLACE = registerSoundEvent("meteorite_place");
    public static final RegistryObject<SoundEvent> METEORITE_BREAK = registerSoundEvent("meteorite_break");
    public static final RegistryObject<SoundEvent> METEORITE_HIT = registerSoundEvent("meteorite_hit");

    public static final RegistryObject<SoundEvent> DUTTY = registerSoundEvent("dutty");
    public static final ResourceKey<JukeboxSong> DUTTY_KEY = ResourceKey.create(Registries.JUKEBOX_SONG,
            ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "dutty"));


    public static final RegistryObject<SoundEvent> SECOND_SONG = registerSoundEvent("second_song");
    public static final ResourceKey<JukeboxSong> SECOND_SONG_KEY = ResourceKey.create(Registries.JUKEBOX_SONG,
            ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, "second_song"));

    public static final ForgeSoundType METEORITE_BLOCK_SOUNDS = new ForgeSoundType(2f, 1f,
            MySillySounds.METEORITE_BREAK, MySillySounds.METEORITE_PLACE, MySillySounds.METEORITE_PLACE,
            MySillySounds.METEORITE_HIT, MySillySounds.METEORITE_HIT);

    public static final ForgeSoundType SUSIE_BLOCK_SOUNDS = new ForgeSoundType(3f, 1f,
            MySillySounds.SUSIE_BLOCK_BREAK, MySillySounds.SUSIE_BLOCK_PLACE, MySillySounds.SUSIE_BLOCK_PLACE,
            MySillySounds.SUSIE_BLOCK_HIT,  MySillySounds.SUSIE_BLOCK_HIT);

    private static RegistryObject<SoundEvent> registerSoundEvent(String name){
        return SOUND_EVENTS.register(name, ()
                -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(SamMod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
