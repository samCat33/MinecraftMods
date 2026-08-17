package com.example.sammod.loot;


import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

//This adds items to a vanilla loot table
public class AddItemModifier extends LootModifier {


    //Codecs convert Java objects into storage formats such as JSON or NBT files

    //This Codec variable allows for a new item with a custom amount of this item
    //defined in the item and count instance variables
    public static final MapCodec<AddItemModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            codecStart(inst).and(
                    inst.group(
                            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item),
                            Codec.INT.optionalFieldOf("count", 1).forGetter(m -> m.count)
                    )
            ).apply(inst, AddItemModifier::new));

    private final Item item;
    private final int count;


    public AddItemModifier(LootItemCondition[] conditionsIn, Item item, int count) {
        super(conditionsIn);
        this.item = item;
        this.count = count;
    }

    public AddItemModifier(LootItemCondition[] conditionsIn, Item item) {
        super(conditionsIn);
        this.item = item;
        this.count = 1;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        //If any of the conditions for adding the item do not apply,
        //return the loot that we would have added without actually doing anything
        for (LootItemCondition condition : this.conditions) {
            if(!(condition.test(context))){
                return generatedLoot;
            }
        }

        //If all the conditions are met, add the item to the generatedLoot list
        generatedLoot.add(new ItemStack(this.item, this.count));
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
