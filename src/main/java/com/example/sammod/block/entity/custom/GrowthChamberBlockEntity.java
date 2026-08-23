package com.example.sammod.block.entity.custom;

import com.example.sammod.block.entity.ModBlockEntities;
import com.example.sammod.screen.custom.GrowthChamberMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;


//Defines the logic for the GrowthChamberBlockEntity
public class GrowthChamberBlockEntity extends BlockEntity implements MenuProvider {

    //Creates a new inventory with 2 slots
    public final ItemStackHandler INVENTORY = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            //The inventory is updated on the server side
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };



    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int OUTPUT_SLOT = 2;

    //These are all the items that can go in the first slot
    List<Item> meatInputs = List.of(Items.CHICKEN, Items.PORKCHOP, Items.MUTTON, Items.BEEF);

    //These are the items that can go in the second slot
    List<Item> chickenFood = List.of(Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.MELON_SEEDS,
            Items.PUMPKIN_SEEDS, Items.TORCHFLOWER_SEEDS);

    List<Item> pigFood = List.of(Items.CARROT,  Items.POTATO, Items.BEETROOT);
    Item sheepAndCowFood = Items.WHEAT;


    //lazyItemHandler basically creates a cached reference to your inventory
    //This reference will have a supplier to access your inventory that is then
    //cached for future use depending on how long the block entity it is
    //associated with is loaded in the world.

    //Hoppers also use the IItemHandler capability system
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    //ContainerData allows us to synchronize certain variables
    //between the server and the client
    protected final ContainerData data;

    //Variables for crafting progress
    //These are the variables we want to synchronize
    private final int ACTUAL_MAX_PROGRESS = 72;
    private int progress = 0;
    private int maxProgress = ACTUAL_MAX_PROGRESS;


    public GrowthChamberBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GROWTH_CHAMBER_BE.get(), pPos, pBlockState);

        //getter and setter methods inside ContainerData
        //for the data we want to synchronize
        data = new ContainerData() {

            //return switch statements are different from regular switch statements
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> GrowthChamberBlockEntity.this.progress;
                    case 1 -> GrowthChamberBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0: GrowthChamberBlockEntity.this.progress = pValue;
                    break;
                    case 1: GrowthChamberBlockEntity.this.maxProgress = pValue;
                    break;
                }
            }

            //We return the number of variables we want to synchronize
            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    //This runs whenever the block entity loads
    //(block is newly placed or the chunk containing it is loaded)
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> INVENTORY);
    }


    //This runs whenever the block entity unloads
    //(block is destroyed, unloaded from the chunk being unloaded,
    //or replaced by a different entity)
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    //This is the exact same as the drops() method
    //for the PedestalBlockEntity

    //All the contents from the inventory of the block entity
    //will be dropped when the block is broken
    public void drops(){

        //or new SimpleContainer(inventory.getStackInSlot(i))
        SimpleContainer inv = new SimpleContainer(INVENTORY.getSlots());
        for (int i = 0; i < INVENTORY.getSlots(); ++i){
            inv.setItem(i, INVENTORY.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    private final String INVENTORY_TAG = "inventory";
    private final String PROGRESS_TAG = "progress";
    private final String MAX_PROGRESS_TAG = "maxProgress";

    //Save the inventory, progress, and max progress of the block entity
    //whenever player leaves the game
    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        pTag.put(INVENTORY_TAG, INVENTORY.serializeNBT(pRegistries));
        pTag.putInt(PROGRESS_TAG, progress);
        pTag.putInt(MAX_PROGRESS_TAG, maxProgress);

        super.saveAdditional(pTag, pRegistries);
    }

    //Load the inventory, progress, and max progress of the block entity
    //whenever player rejoins the game
    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);

        INVENTORY.deserializeNBT(pRegistries,  pTag.getCompound(INVENTORY_TAG));
        progress = pTag.getInt(PROGRESS_TAG);
        maxProgress = pTag.getInt(MAX_PROGRESS_TAG);
    }

    @Override
    public Component getDisplayName() {
        //This is the same translation key as the growth chamber block
        return Component.translatable("block.sammod.growth_chamber");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new GrowthChamberMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    //This is used in GrowthChamberBlock.getTicker() to allow this method to run
    //once every tick
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (hasRecipe()){
            increaseProgress();
            //Helps with synchronization
            setChanged(level, blockPos, blockState);

            if (hasCraftingFinished()){
                craftItem();
                resetProgress();
            }
        }
        else{
            resetProgress();
        }
    }

    private void craftItem() {
        if(INVENTORY.getStackInSlot(INPUT_SLOT_1).getItem() == Items.CHICKEN){
            ItemStack output = new ItemStack(Items.CHICKEN_SPAWN_EGG);
            INVENTORY.extractItem(INPUT_SLOT_1, 1, false);
            INVENTORY.extractItem(INPUT_SLOT_2, 1, false);

            INVENTORY.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                    INVENTORY.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
        }

        else if(INVENTORY.getStackInSlot(INPUT_SLOT_1).getItem() == Items.PORKCHOP){
            ItemStack output = new ItemStack(Items.PIG_SPAWN_EGG);
            INVENTORY.extractItem(INPUT_SLOT_1, 1, false);
            INVENTORY.extractItem(INPUT_SLOT_2, 1, false);

            INVENTORY.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                    INVENTORY.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
        }

        else if (INVENTORY.getStackInSlot(INPUT_SLOT_1).getItem() == Items.MUTTON){
            ItemStack output = new ItemStack(Items.SHEEP_SPAWN_EGG);
            INVENTORY.extractItem(INPUT_SLOT_1, 1, false);
            INVENTORY.extractItem(INPUT_SLOT_2, 1, false);

            INVENTORY.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                    INVENTORY.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
        }

        else if(INVENTORY.getStackInSlot(INPUT_SLOT_1).getItem() == Items.BEEF){
            ItemStack output = new ItemStack(Items.COW_SPAWN_EGG);
            INVENTORY.extractItem(INPUT_SLOT_1, 1, false);
            INVENTORY.extractItem(INPUT_SLOT_2, 1, false);

            INVENTORY.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                    INVENTORY.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
        }

        else{
            throw new IllegalArgumentException("Trying to craft an invalid item");
        }
    }

    private boolean hasCraftingFinished(){
        return progress >= maxProgress;
    }

    private void increaseProgress(){
        this.progress += 1;
    }
    private void resetProgress(){
        this.progress = 0;
        this.maxProgress = ACTUAL_MAX_PROGRESS;
    }


    //This determines if the stuff in the slots will actually make a recipe
    private boolean hasRecipe(){
        boolean flag = false;

        for(int i = 0; i < meatInputs.size(); i++){
            if (INVENTORY.getStackInSlot(INPUT_SLOT_1).is(meatInputs.get(i))){
                switch (i){
                    case 0:
                        for (Item item : chickenFood) {
                            if (INVENTORY.getStackInSlot(INPUT_SLOT_2).is(item)) {
                                flag = true;
                            }
                        }
                        ;
                    case 1:
                        for (Item item : pigFood) {
                            if (INVENTORY.getStackInSlot(INPUT_SLOT_2).is(item)) {
                                flag = true;
                            }
                        }
                        ;
                    case 2:
                        if (INVENTORY.getStackInSlot(INPUT_SLOT_2).is(sheepAndCowFood)){
                            flag = true;
                        }

                }
            }

        }
        return flag;
    }

    //This determines if the output slot is empty or if the output slot has an item
    //in it that matches the item that the player is trying to create
    private boolean canInsertItemIntoOutputSlot(List<List<Item>> lists){
        boolean outputHasProperItem = false;
        for (List<Item> list : lists){
            for (Item item : list){
                if (INVENTORY.getStackInSlot(OUTPUT_SLOT).getItem() == item){
                    outputHasProperItem = true;
                }
            }
        }
        return INVENTORY.getStackInSlot(OUTPUT_SLOT).isEmpty() || outputHasProperItem;
    }


    //This is for in case we want more than one output item at a time
    //It makes sure that we will not overfill the stack if we can stack
    //onto an item that is already there
    private boolean canInsertAmountIntoOutputSlot(int count){

        //If the output slot is empty, the max count is 64,
        //otherwise it is the max stack size of whatever item is already in there
        int maxCount = INVENTORY.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : INVENTORY.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();

        //The number of items currently in the slot
        int currentCount = INVENTORY.getStackInSlot(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }


    //These methods are necessary for synchronization between
    //server and client as well
    //These methods for block entities usually look the same
    //across all block entities
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries){
        return saveWithoutMetadata(pRegistries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this);
    }

}
