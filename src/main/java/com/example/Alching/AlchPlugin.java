package com.example.Alching;


import com.example.Alching.data.Constants;
import com.example.Alching.data.SpellUtil;
import com.example.AutoFarmRun.farmrunOverlay;
import com.example.EthanApiPlugin.Collections.Inventory;
import com.example.EthanApiPlugin.Collections.Widgets;
import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.PacketUtils.PacketUtilsPlugin;
import com.example.Packets.MousePackets;
import com.example.Packets.WidgetPackets;
import com.google.inject.Inject;
import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependencies;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.HotkeyListener;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@PluginDescriptor(name = "AlchForProfit", enabledByDefault = false, tags = {"Kne"})
@PluginDependencies({@PluginDependency(EthanApiPlugin.class), @PluginDependency(PacketUtilsPlugin.class)})

public class AlchPlugin extends Plugin {
    @Inject
    Client client;
    @Inject
    AlchConfig config;
    @Inject
    EthanApiPlugin api;
    @Inject
    private KeyManager keyManager;
    @Inject
    private OverlayManager overlayManager;
    @Inject
    private AutoAlchOverlay overlay;
    int timeout = 0;
    public boolean started = false;
    @Inject
    private ItemManager itemManager;
    private AlchState state;


    @Provides
    public AlchConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(AlchConfig.class);
    }

    protected void startUp() throws Exception {
        keyManager.registerKeyListener(this.toggle);
        overlayManager.add(this.overlay);
        timeout = 0;
    }

    protected void shutDown() throws Exception {
        keyManager.unregisterKeyListener(this.toggle);
        overlayManager.remove(this.overlay);
        started = false;
        timeout = 0;
    }

    private final HotkeyListener toggle = new HotkeyListener(() -> this.config.startStopHotkey()) {
        public void hotkeyPressed() {
            AlchPlugin.this.toggle();
        }
    };

    public void toggle() {
        if (this.client.getGameState() != GameState.LOGGED_IN)
            return;
        this.started = !this.started;
    }

    private int tickDelay() {
        return config.tickDelay() ? ThreadLocalRandom.current().nextInt(config.tickDelayMin(), config.tickDelayMax()) : 0;
    }
    @Subscribe
    public void onGameTick(GameTick event) throws Exception {
        if (timeout > 0) {
            timeout--;
            return;
        }

        if (client.getGameState() != GameState.LOGGED_IN || !started) {
            return;
        }

        AlchState state = getState();

        switch (state) {
            case GET_LVL_55_MAGIC:
                magicLeveling();
                break;
            case OPEN_GE:
                geOpen();
                break;
            case BREAK:
                break;
            case GATHER_ITEMS:
                gatherItems();
                break;
            case BUY_NATURE_RUNES:
                handleExchangeItem1(Constants.NATURE_RUNE, config.natureRuneQTY(), config.natureRunePrice(), 0);
                break;
            case BUYITEM1:
                handleExchangeItem1(config.ItemOne(),config.ItemOneQTY(),config.ItemOnePrice(), config.ItemOne());
                break;
            case BUYITEM2:
                handleExchangeItem1(config.ItemTwo(), config.ItemTwoQTY(), config.ItemTwoPrice(), config.ItemTwo());
                break;
            case BUYITEM3:
                handleExchangeItem1(config.ItemThree(), config.ItemThreeQTY(), config.ItemThreePrice(), config.ItemThree());
                break;
            case ALCHING_ITEM_ONE:
                alchItemsInInventory(config.ItemOne());
                break;
            case ALCHING_ITEM_TWO:
                alchItemsInInventory(config.ItemTwo());
                break;
            case ALCHING_ITEM_THREE:
                alchItemsInInventory(config.ItemThree());
                break;
            case ALCHING_ITEM_ONE_NOTE:
                alchItemsInInventory(GrandExchange.getNotedId(config.ItemOne()));
                break;
            case ALCHING_ITEM_TWO_NOTE:
                alchItemsInInventory(GrandExchange.getNotedId(config.ItemTwo()));
                break;
            case ALCHING_ITEM_THREE_NOTE:
                alchItemsInInventory(GrandExchange.getNotedId(config.ItemThree()));
                break;
        }
    }

    private void gatherItems() {
        GrandExchange.collectAll();
        timeout = tickDelay();
    }
    public String alchState(){
        return getState().toString();
    }

    private AlchState getState() {
        int [] newList = {config.ItemOne(),
                config.ItemThree(),
                config.ItemTwo(),
                GrandExchange.getNotedId(config.ItemOne()),
                GrandExchange.getNotedId(config.ItemTwo()),
                GrandExchange.getNotedId(config.ItemThree())};

        if (!canPlayerAlch()){
            return AlchState.GET_LVL_55_MAGIC;
        }

        if (checkInventoryForAlchs(Constants.NATURE_RUNE) && checkInventoryForAlchs(newList)){
            if (checkInventoryForAlchs(config.ItemOne())){
                return AlchState.ALCHING_ITEM_ONE;
            }
            if(checkInventoryForAlchs(config.ItemTwo())){
                return AlchState.ALCHING_ITEM_TWO;
            }
            if(checkInventoryForAlchs(config.ItemThree())){
                return AlchState.ALCHING_ITEM_THREE;
            }
            if(checkInventoryForAlchs(GrandExchange.getNotedId(config.ItemOne()))){
                return AlchState.ALCHING_ITEM_ONE_NOTE;
            }
            if(checkInventoryForAlchs(GrandExchange.getNotedId(config.ItemTwo()))){
                return AlchState.ALCHING_ITEM_TWO_NOTE;
            }
            if(checkInventoryForAlchs(GrandExchange.getNotedId(config.ItemThree()))){
                return AlchState.ALCHING_ITEM_THREE_NOTE;
            }
        }

        if(!checkInventoryForAlchs(Constants.NATURE_RUNE) && !GrandExchange.isOpen()){
            return AlchState.OPEN_GE;
        }

        if (!checkInventoryForAlchs(newList) && !GrandExchange.isOpen()){
            //EthanApiPlugin.sendClientMessage("Open Ge");
            return AlchState.OPEN_GE;
        }

        if (!checkInventoryForAlchs(newList) && GrandExchange.isFull() && !offerCompleted()){
            //EthanApiPlugin.sendClientMessage("Taking a Break");
            return AlchState.BREAK;
        }

        if (offerCompleted() && collectWidgetVisisble()){
            return AlchState.GATHER_ITEMS;
        }

        if (!checkInventoryForAlchs(Constants.NATURE_RUNE) && !geOfferAlreadyCreated(Constants.NATURE_RUNE)){
            //EthanApiPlugin.sendClientMessage("Buying Nature Runes");
            return AlchState.BUY_NATURE_RUNES;
        }

        if (!checkInventoryForAlchs(config.ItemOne()) && GrandExchange.isOpen() && !geOfferAlreadyCreated(config.ItemOne())){
            //EthanApiPlugin.sendClientMessage("ItemOne");
            return AlchState.BUYITEM1;
        }

        if (!checkInventoryForAlchs(config.ItemTwo()) && GrandExchange.isOpen() && !geOfferAlreadyCreated(config.ItemTwo())){
            //EthanApiPlugin.sendClientMessage("ItemTwo");
            return AlchState.BUYITEM2;
        }

        if (!checkInventoryForAlchs(config.ItemThree()) && GrandExchange.isOpen() &&  !geOfferAlreadyCreated(config.ItemThree())){
            //EthanApiPlugin.sendClientMessage("ItemThree");
            return AlchState.BUYITEM3;
        }

        return null;
    }

    private boolean collectWidgetVisisble() {
        Optional<Widget> collectWidget = Widgets.search().withText("Collect").hiddenState(false).first();

        if(collectWidget.isPresent()){
            return true;
        }return false;
    }

    public void geOpen(){
        if (!GrandExchange.isOpen()){
            GrandExchange.open();
        }
    }

    public String getItemName(int ItemID){
        ItemComposition itemComposition = itemManager.getItemComposition(ItemID);
        if (itemComposition != null){
            return itemComposition.getName();
        }else {
            return "unknown item";
        }
    }

    public boolean geOfferAlreadyCreated(int itemID){
        Optional<Widget> buyWidget = Widgets.search().withId(30474247).withText(getItemName(itemID)).hiddenState(false).first();
        Optional<Widget> buyWidget4 = Widgets.search().withId(30474247).withText(getItemName(itemID)).hiddenState(true).first();
        Optional<Widget> buyWidget2 = Widgets.search().withId(30474248).withText(getItemName(itemID)).hiddenState(false).first();
        Optional<Widget> buyWidget5 = Widgets.search().withId(30474248).withText(getItemName(itemID)).hiddenState(true).first();
        Optional<Widget> buyWidget3 = Widgets.search().withId(30474249).withText(getItemName(itemID)).hiddenState(false).first();
        Optional<Widget> buyWidget6 = Widgets.search().withId(30474249).withText(getItemName(itemID)).hiddenState(true).first();

        if (buyWidget.isPresent() || buyWidget2.isPresent() || buyWidget3.isPresent() || buyWidget4.isPresent() || buyWidget5.isPresent() || buyWidget6.isPresent())
        {
            return true;
        }return false;
    }

    public void handleExchangeItem1(int itemToBuy, int itemQTYToBuy, int itemPriceToBuy, int itemNumber) {
        if (GrandExchange.isFull()){
            return;
        }
        if (geOfferAlreadyCreated(itemNumber)){
            EthanApiPlugin.sendClientMessage("Already Buying this item in the GE");
            return;
        } else if (!geOfferAlreadyCreated(itemNumber)) {
            GrandExchange.buyItem(itemToBuy, itemQTYToBuy, itemPriceToBuy);
        }
        timeout = tickDelay();
    }

    public boolean checkInventoryForAlchs(int ITEM) {
        return checkInventoryForAlchs(new int[]{ITEM}); // Redirect to the method that handles an array
    }

    public boolean checkInventoryForAlchs(int[] ITEMs) {
        ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);

        if (inventory == null) {
            return false;
        }
        for (Item item : inventory.getItems()) {
            for (int itemId : ITEMs) {
                if (item.getId() == itemId) {
                    return true; // Found an item, return true
                }
            }
        }
        return false; // No items found
    }

    public boolean offerCompleted() {
        List<GrandExchangeOffer> completedOffers = GrandExchange.getCompletedOffers();
        return !completedOffers.isEmpty();
    }

    public void alchItemsInInventory(int itemID) {
        if (GrandExchange.isOpen()){
            client.runScript(29);
            timeout = tickDelay();
        }
        if (!checkInventoryForAlchs(itemID)) {
            return;
        }
        queHighAlch(itemID);
        timeout = tickDelay();
    }

    private void queHighAlch(int ID) {
        Widget highAlch = SpellUtil.getSpellWidget(client, "High level alchemy");
        Widget Item = Inventory.search().withId(ID).first().get();
        MousePackets.queueClickPacket();
        WidgetPackets.queueWidgetOnWidget(highAlch, Item);
/*        MousePackets.queueClickPacket();
        WidgetPackets.queueResumePause(12648448, 0);
        MousePackets.queueClickPacket();
        WidgetPackets.queueResumePause(14352385, 1);*/
    }

    private boolean canPlayerAlch(){
        if(EthanApiPlugin.getClient().getRealSkillLevel(Skill.MAGIC) >= 55){
            return true;
        }
        return false;
    }

    private void magicLeveling() {
        EthanApiPlugin.sendClientMessage("Level Magic to 55 before continuing");
        EthanApiPlugin.stopPlugin(this);
    }
}


