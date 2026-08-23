package com.example.sammod.block.entity.custom;

import com.example.sammod.block.entity.ModBlockEntities;
import com.example.sammod.screen.custom.PedestalMenu;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//Defines the logic for the pedestal BlockEntity
public class PedestalBlockEntity extends BlockEntity implements MenuProvider {

    //Creates a new inventory with one slot
    //This is an example of an anonymous class
    public final ItemStackHandler INVENTORY = new ItemStackHandler(1){
        //The number returned is how many items in a stack
        //can be placed in that slot
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack){
            return 1;
        }

        //Update the block on the server side whenever
        //the item in the pedestal changes
        @Override
        protected void onContentsChanged(int slot){
            setChanged();
            if (!level.isClientSide){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private final String SAVE_TAG = "inventory";
    private float rotation;

    public PedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PEDESTAL_BE.get(), pPos, pBlockState);
    }

    //This method is used by the PedestalBlockEntityRenderer class
    public float getRenderingRotation(){
        rotation += 0.5f;
        if (rotation >= 360){
            rotation = 0;
        }
        return rotation;
    }

    //Set the first item stack to be empty
    //We only need to set the first item stack
    //because there is only one slot
    public void clearContents(){
        INVENTORY.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void drops(){

        //or new SimpleContainer(inventory.getStackInSlot(i))
        SimpleContainer inv = new SimpleContainer(INVENTORY.getSlots());
        for (int i = 0; i < INVENTORY.getSlots(); ++i){
            inv.setItem(i, INVENTORY.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    //These next two methods save and load the data stored inside
    //the block entity so that it stays consistent even when the
    //player leaves the game
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries){
        super.saveAdditional(tag, registries);

        //The inventory is stored as NBT
        tag.put(SAVE_TAG, INVENTORY.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        INVENTORY.deserializeNBT(pRegistries, pTag.getCompound(SAVE_TAG));
    }

    //These next two methods are the same for every block entity
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries){
        return saveWithoutMetadata(registries);
    }

    //These are the interface methods

    @Override
    public Component getDisplayName() {
        return Component.literal("Pedestal");
    }

    //See PedestalMenu class
    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PedestalMenu(pContainerId, pPlayerInventory, this);
    }
}
