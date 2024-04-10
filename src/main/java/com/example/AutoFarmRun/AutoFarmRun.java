package com.example.AutoFarmRun;

import com.example.AutoFarmRun.data.Booleans;
import com.example.AutoFarmRun.data.Constants;
import com.example.EthanApiPlugin.Collections.Inventory;
import com.example.EthanApiPlugin.Collections.NPCs;
import com.example.EthanApiPlugin.Collections.TileObjects;
import com.example.EthanApiPlugin.EthanApiPlugin;
import com.example.InteractionApi.TileObjectInteraction;
import com.example.PacketUtils.PacketUtilsPlugin;
import com.example.Packets.MousePackets;
import com.example.Packets.NPCPackets;
import com.example.Packets.ObjectPackets;
import com.google.inject.Inject;
import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependencies;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.HotkeyListener;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import static com.example.AutoFarmRun.data.Booleans.*;

@PluginDescriptor(name = "AutoFarmRun", enabledByDefault = false, tags = {"Kne"})
@PluginDependencies({@PluginDependency(EthanApiPlugin.class), @PluginDependency(PacketUtilsPlugin.class)})

public class AutoFarmRun extends Plugin {

    @Inject
    Client client;
    @Inject
    FarmRunConfig config;
    @Inject
    EthanApiPlugin api;
    @Inject
    private KeyManager keyManager;
    @Inject
    private OverlayManager overlayManager;
    @Inject
    private farmrunOverlay overlay;
    int timeout = 0;
    public boolean started = false;

    @Provides
    public FarmRunConfig getConfig(ConfigManager configManager) {
        return (FarmRunConfig) configManager.getConfig(FarmRunConfig.class);
    }

    protected void startUp() throws Exception {
        this.keyManager.registerKeyListener((KeyListener) this.toggle);
        this.overlayManager.add(this.overlay);
        this.timeout = 0;
    }

    protected void shutDown() throws Exception {
        this.keyManager.unregisterKeyListener((KeyListener) this.toggle);
        this.overlayManager.remove(this.overlay);
        this.started = false;
        this.timeout = 0;
    }

    private final HotkeyListener toggle = new HotkeyListener(() -> this.config.startStopHotkey()) {
        public void hotkeyPressed() {
            AutoFarmRun.this.toggle();
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
    public void onGameTick(GameTick event) {
        if (this.timeout > 0) {
            this.timeout--;
            return;
        }
        if (this.client.getGameState() != GameState.LOGGED_IN || !this.started)
            return;


        AutoFarmRunState state = getState();
        switch (state) {
            case ARDYPATCH:
                ardyPatch();
                break;
            case CATHERBY_PATCH:
                break;
            case CIVITAS_ILLA_FORTIS_PATCH:
                break;
            case FALADOR_PATCH:
                break;
            case FARMING_GUILD_PATCH:
                break;
            case HARMONY_PATCH:
                break;
            case KOUREND_PATCH:
                break;
            case MORYTANIA_PATCH:
                break;
            case TROLL_STRONGHOLD_PATCH:
                break;
            case WEISS_PATCH:
                break;
            case HERB_IS_DEAD:
                handleDeadHerbPatch();
                break;
            case HERB_IS_GROWING:
                handleHerbPatch();
                break;
            case HERB_IS_NOT_GROWING:
                handleEmptyHerbPatch();
                break;
            case HERB_IS_FULLY_GROWN:
                handleHerbPatch();
                break;
        }
    }

    public void ardyPatch() {
        EthanApiPlugin.sendClientMessage("Ardy Patch");
/*        if (config.ardougnePatch()) {
            if (config.ardyTele() == TeleportMethods.ardyTeleMethods.ARDOUGNE_CAPE) {
                MousePackets.queueClickPacket();
                WidgetPackets.queueWidgetActionPacket(4, 25362448, -1, -1);
            } else if (config.ardyTele() == TeleportMethods.ardyTeleMethods.ARDOUGNE_TELEPORT) {

            }
            timeout = 3;
        }*/
    }

    public AutoFarmRunState getState() {


/*        if (herbPatchNear()) {

            if (herbPatchReady()) {
                return AutoFarmRunState.HERB_IS_FULLY_GROWN;
            } else if (herbPatchNear4771() && herbNotGrowing(4771)) {
                return AutoFarmRunState.HERB_IS_NOT_GROWING;
            } else if (herbPatchNear4772() && herbNotGrowing(4772)) {
                return AutoFarmRunState.HERB_IS_NOT_GROWING;
            } else if (herbPatchNear4774() && herbNotGrowing(4774)) {
                return AutoFarmRunState.HERB_IS_NOT_GROWING;
            } else if (herbPatchNear4775() && herbNotGrowing(4775)) {
                return AutoFarmRunState.HERB_IS_NOT_GROWING;
            } else if (herbPatchNear4775() && herbGrowing(4775)) {
                return AutoFarmRunState.HERB_IS_GROWING;
            }
        }*/


        if (herbPatchNear()) {
            if (herbPatchReady()) {
                return AutoFarmRunState.HERB_IS_FULLY_GROWN;
            }
            else {
                if (herbPatchNear4771() && herbNotGrowing(4771)) {
                    return AutoFarmRunState.HERB_IS_NOT_GROWING;
                } else if (herbPatchNear4772() && herbNotGrowing(4772)) {
                    return AutoFarmRunState.HERB_IS_NOT_GROWING;
                } else if (herbPatchNear4774() && herbNotGrowing(4774)) {
                    return AutoFarmRunState.HERB_IS_NOT_GROWING;
                } else if (herbPatchNear4775()) {
                    if (herbNotGrowing(4775)) {
                        return AutoFarmRunState.HERB_IS_NOT_GROWING;
                    }
                    else {
                        return AutoFarmRunState.HERB_IS_GROWING;
                    }
                }
            }
        }

        if (herbPatchDead()) {
            return AutoFarmRunState.HERB_IS_DEAD;
        }

        Map<Supplier<Boolean>, Supplier<AutoFarmRunState>> patchChecks = new LinkedHashMap<>();
        patchChecks.put(() -> config.faladorPatch() && !compostedfally && !seedplantedfally, () -> AutoFarmRunState.FALADOR_PATCH);
        patchChecks.put(() -> config.morytaniaPatch() && !compostedmorytania && !seedplantedmorytania, () -> AutoFarmRunState.MORYTANIA_PATCH);
        patchChecks.put(() -> config.catherbyPatch() && !compostedcatherby && !seedplantedcatherby, () -> AutoFarmRunState.CATHERBY_PATCH);
        patchChecks.put(() -> config.kourendPatch() && !compostedkourend && !seedplantedkourend, () -> AutoFarmRunState.KOUREND_PATCH);
        patchChecks.put(() -> config.weissPatch() && !compostedweiss && !seedplantedweiss, () -> AutoFarmRunState.WEISS_PATCH);
        patchChecks.put(() -> config.trollStrongholdPatch() && !compostedtrollstronghold && !seedplantedtrollstronghold, () -> AutoFarmRunState.TROLL_STRONGHOLD_PATCH);
        patchChecks.put(() -> config.farmingGuildPatch() && !compostedfarmingguild && !seedplantedfarmingguild, () -> AutoFarmRunState.FARMING_GUILD_PATCH);
        patchChecks.put(() -> config.ardougnePatch() && !compostedardy && !seedplantedardy, () -> AutoFarmRunState.ARDYPATCH);
        patchChecks.put(() -> config.harmonyPatch() && !compostedharmony && !seedplantedharmony, () -> AutoFarmRunState.HARMONY_PATCH);
        


        for (Map.Entry<Supplier<Boolean>, Supplier<AutoFarmRunState>> entry : patchChecks.entrySet()) {
            if (entry.getKey().get()) {
                return entry.getValue().get();
            }
        }

/*

        test the code above to see if it works before deleting this code below.

        if (herbPatchDead()) {
            return AutoFarmRunState.HERB_IS_DEAD;
        }
        if (config.faladorPatch() && !compostedfally && !seedplantedfally) {
            return AutoFarmRunState.FALADOR_PATCH;
        }

        if (config.morytaniaPatch() && !compostedmorytania && !seedplantedmorytania) {
            return AutoFarmRunState.MORYTANIA_PATCH;
        }

        if (config.catherbyPatch() && !compostedcatherby && !seedplantedcatherby) {
            return AutoFarmRunState.CATHERBY_PATCH;
        }

        if (config.kourendPatch() && !compostedkourend && !seedplantedkourend) {
            return AutoFarmRunState.KOUREND_PATCH;
        }

        if (config.ardougnePatch() && !compostedardy && !seedplantedardy) {
            return AutoFarmRunState.ARDYPATCH;
        }

        if (config.farmingGuildPatch() && !compostedfarmingguild && !seedplantedfarmingguild) {
            return AutoFarmRunState.FARMING_GUILD_PATCH;
        }

        if (config.fortisPatch() && !compostedfortis && !seedplantedfortis) {
            return AutoFarmRunState.CIVITAS_ILLA_FORTIS_PATCH;
        }

        if (config.trollStrongholdPatch() && !compostedtrollstronghold && !seedplantedtrollstronghold) {
            return AutoFarmRunState.TROLL_STRONGHOLD_PATCH;
        }

        if (config.weissPatch() && !compostedweiss && !seedplantedweiss) {
            return AutoFarmRunState.WEISS_PATCH;
        }

        if (config.harmonyPatch() && !compostedweiss && !seedplantedharmony) {
            return AutoFarmRunState.HARMONY_PATCH;
        }
*/

        return null;
    }


    public void handleDeadHerbPatch() {
        List<Integer> list = Arrays.asList(199, 201, 203, 205, 207, 3049, 209, 211, 213, 3051, 215, 2485, 217, 219);
        Widget Item = Inventory.search().idInList(list).first().get();
        NPC npc = NPCs.search().withName(Constants.LEPRECHAUN).first().get();

        if (herbPatchNear()) {
            if (herbPatchDead()) {
                TileObjectInteraction.interact("Dead herbs", "Clear");
            }
            if (!Inventory.search().idInList(list).empty()) {
                MousePackets.queueClickPacket();
                NPCPackets.queueWidgetOnNPC(npc, Item);
            }
            timeout = 8;
        }

    }

    public void handleEmptyHerbPatch() {
        if (herbPatchNear()) {
            if (herbPatchNear4775() && herbNotGrowing(4775)) {
                compostPatch(compostType());
                timeout = 10;
                plantSeed(config.herbSeed());
            }

            if (herbPatchNear4774() && herbNotGrowing(4774)) {

                if(!compostedardy && getWorldLocation(Constants.ARDY_AREA_HERB)){
                    compostPatch(compostType());
                }else if (!compostedcatherby && getWorldLocation(Constants.CATHERBY_AREA_HERB)) {
                    compostPatch(compostType());
                } else if (!compostedfortis && getWorldLocation(Constants.FORTIS_AREA_HERB)) {
                    compostPatch(compostType());
                } else if (!compostedfally && getWorldLocation(Constants.FALLY_AREA_HERB)) {
                    compostPatch(compostType());
                } else if (!compostedkourend && getWorldLocation(Constants.KOUREND_AREA_HERB)) {
                    compostPatch(compostType());
                } else if (!compostedmorytania && getWorldLocation(Constants.MORYTANIA_AREA_HERB)) {
                    compostPatch(compostType());
                } else if (!compostedfarmingguild && getWorldLocation(Constants.FARMING_GUILD_AREA_HERB)) {
                    compostPatch(compostType());
                } else if (!compostedharmony && getWorldLocation(Constants.HARMONY_AREA_HERB)) {
                    compostPatch(compostType());
                } else if (!compostedtrollstronghold && getWorldLocation(Constants.TROLL_STRONGHOLD_AREA_HERB)) {
                    compostPatch(compostType());
                } else if (!compostedweiss && getWorldLocation(Constants.WEISS_AREA_HERB)) {
                    compostPatch(compostType());
                }
                onChatMessage(new ChatMessage());

                if(compostedardy && getWorldLocation(Constants.ARDY_AREA_HERB)) {
                    plantSeed(config.herbSeed());
                } else if (compostedcatherby && getWorldLocation(Constants.CATHERBY_AREA_HERB)) {
                    plantSeed(config.herbSeed());
                } else if (compostedfortis && getWorldLocation(Constants.FORTIS_AREA_HERB)) {
                    plantSeed(config.herbSeed());
                } else if (compostedfally && getWorldLocation(Constants.FALLY_AREA_HERB)) {
                    plantSeed(config.herbSeed());
                } else if (compostedkourend && getWorldLocation(Constants.KOUREND_AREA_HERB)) {
                    plantSeed(config.herbSeed());
                } else if (compostedmorytania && getWorldLocation(Constants.MORYTANIA_AREA_HERB)) {
                    plantSeed(config.herbSeed());
                }else if (!compostedfarmingguild && getWorldLocation(Constants.FARMING_GUILD_AREA_HERB)) {
                    plantSeed(config.herbSeed());
                } else if (compostedharmony && getWorldLocation(Constants.HARMONY_AREA_HERB)) {
                    plantSeed(config.herbSeed());
                } else if (compostedtrollstronghold && getWorldLocation(Constants.TROLL_STRONGHOLD_AREA_HERB)) {
                    plantSeed(config.herbSeed());
                } else if (compostedweiss && getWorldLocation(Constants.WEISS_AREA_HERB)) {
                    plantSeed(config.herbSeed());
                }
                onAnimationChanged(new AnimationChanged());

            }
            if (herbPatchNear4772() && herbNotGrowing(4772)) {
                compostPatch(compostType());

                plantSeed(config.herbSeed());

            }
            if (herbPatchNear4771() && herbNotGrowing(4771)) {
                compostPatch(compostType());

                plantSeed(config.herbSeed());

            }
        }
        timeout = 10;
    }

    public void handleHerbPatch() {
        EthanApiPlugin.sendClientMessage("test");

        //List list1 = Arrays.asList(199,201,203,205,207,3049,209,211,213,3051,215,2485,217,219);

        EthanApiPlugin.sendClientMessage("Trying to Pick");
        if (!Inventory.full()) {
            EthanApiPlugin.sendClientMessage("Trying to Pick2");
            if (herbPatchReady()) {
                EthanApiPlugin.sendClientMessage("Trying to Pick3");
                TileObjectInteraction.interact("Herbs", "Pick");
            }
            timeout = tickDelay();

        } else if (Inventory.full()) {
            List<Integer> list1 = Arrays.asList(199, 201, 203, 205, 207, 3049, 209, 211, 213, 3051, 215, 2485, 217, 219);
            Widget inventory = Inventory.search().idInList(list1).first().get();
            NPC npc1 = NPCs.search().withName(Constants.LEPRECHAUN).first().get();

            if (!Inventory.search().idInList(list1).empty()) {
                MousePackets.queueClickPacket();
                NPCPackets.queueWidgetOnNPC(npc1, inventory);
            }
            timeout = tickDelay();
        }

        timeout = tickDelay();
    }

    @Subscribe
    private void onChatMessage(ChatMessage event) {
        if (event.getType() != ChatMessageType.GAMEMESSAGE) {
            return;
        }

        Map<WorldArea, Boolean> compostedMap = new HashMap<>();
        compostedMap.put(Constants.FALLY_AREA_HERB, false);
        compostedMap.put(Constants.ARDY_AREA_HERB, false);
        compostedMap.put(Constants.FORTIS_AREA_HERB, false);
        compostedMap.put(Constants.HARMONY_AREA_HERB, false);
        compostedMap.put(Constants.TROLL_STRONGHOLD_AREA_HERB, false);
        compostedMap.put(Constants.WEISS_AREA_HERB, false);
        compostedMap.put(Constants.MORYTANIA_AREA_HERB, false);
        compostedMap.put(Constants.KOUREND_AREA_HERB, false);
        compostedMap.put(Constants.FARMING_GUILD_AREA_HERB, false);
        compostedMap.put(Constants.CATHERBY_AREA_HERB, false);

        // Add more areas here

        for (Map.Entry<WorldArea, Boolean> entry : compostedMap.entrySet()) {
            WorldArea area = entry.getKey();

            if (getWorldLocation(area) && event.getMessage().contains("You treat the herb patch")) {
                compostedMap.put(area, true);
            }
        }

        // Update individual composted variables based on the map
        compostedfally = compostedMap.get(Constants.FALLY_AREA_HERB);
        compostedardy = compostedMap.get(Constants.ARDY_AREA_HERB);
        compostedfortis = compostedMap.get(Constants.FORTIS_AREA_HERB);
        compostedharmony = compostedMap.get(Constants.HARMONY_AREA_HERB);
        compostedtrollstronghold = compostedMap.get(Constants.TROLL_STRONGHOLD_AREA_HERB);
        compostedweiss = compostedMap.get(Constants.WEISS_AREA_HERB);
        compostedmorytania = compostedMap.get(Constants.MORYTANIA_AREA_HERB);
        compostedkourend = compostedMap.get(Constants.KOUREND_AREA_HERB);
        compostedfarmingguild = compostedMap.get(Constants.FARMING_GUILD_AREA_HERB);
        compostedcatherby = compostedMap.get(Constants.CATHERBY_AREA_HERB);
    }


 /*   private void onChatMessage(ChatMessage event) {
        if (event.getType() != ChatMessageType.GAMEMESSAGE) {
            return;
        }
        if (getWorldLocation(Constants.FALLY_AREA_HERB)) {
            if (event.getMessage().contains("You have treated patch")) {
                compostedfally = true;
            }
        }
        else if (getWorldLocation(Constants.ARDY_AREA_HERB)) {
            if (event.getMessage().contains("You have treated patch")) {
                compostedardy = true;
            }
        }
        else if (getWorldLocation(Constants.FORTIS_AREA_HERB)) {
            if (event.getMessage().contains("You have treated patch")) {
                compostedfortis = true;
            }
        }
        else if (getWorldLocation(Constants.FARMING_GUILD_AREA_HERB)) {
            if (event.getMessage().contains("You have treated patch")) {
                compostedfarmingguild = true;
            }
        }
        else if (getWorldLocation(Constants.HARMONY_AREA_HERB)) {
            if (event.getMessage().contains("You have treated patch")) {
                compostedharmony = true;
            }
        }
        else if (getWorldLocation(Constants.KOUREND_AREA_HERB)) {
            if (event.getMessage().contains("You have treated patch")) {
                compostedkourend = true;
            }
        }
        else if (getWorldLocation(Constants.MORYTANIA_AREA_HERB)) {
            if (event.getMessage().contains("You have treated patch")) {
                compostedmorytania = true;
            }
        }
        else if (getWorldLocation(Constants.TROLL_STRONGHOLD_AREA_HERB)) {
            if (event.getMessage().contains("You have treated patch")) {
                compostedtrollstronghold = true;
            }
        }
        else if (getWorldLocation(Constants.WEISS_AREA_HERB)) {
            if (event.getMessage().contains("You have treated patch")) {
                compostedweiss = true;
            }
        }
        else if (getWorldLocation(Constants.CATHERBY_AREA_HERB)) {
            if (event.getMessage().contains("You have treated patch")) {
                compostedcatherby = true;
            }
        }
    }*/

    @Subscribe
    private void onAnimationChanged(AnimationChanged event) {
        if (event.getActor() == client.getLocalPlayer()) {
            int animationId = event.getActor().getAnimation();

            if (animationId == AnimationID.FARMING_PLANT_SEED && getWorldLocation(Constants.FALLY_AREA_HERB)) {
                seedplantedfally = true;
            }
            else if (animationId == AnimationID.FARMING_PLANT_SEED && getWorldLocation(Constants.MORYTANIA_AREA_HERB)) {
                seedplantedmorytania = true;
            }
            else if (animationId == AnimationID.FARMING_PLANT_SEED && getWorldLocation(Constants.KOUREND_AREA_HERB)) {
                seedplantedkourend = true;
            }
            else if (animationId == AnimationID.FARMING_PLANT_SEED && getWorldLocation(Constants.ARDY_AREA_HERB)) {
                seedplantedardy = true;
            }
            else if (animationId == AnimationID.FARMING_PLANT_SEED && getWorldLocation(Constants.FARMING_GUILD_AREA_HERB)) {
                seedplantedfarmingguild = true;
            }
            else if (animationId == AnimationID.FARMING_PLANT_SEED && getWorldLocation(Constants.FORTIS_AREA_HERB)) {
                seedplantedfortis = true;
            }
            else if (animationId == AnimationID.FARMING_PLANT_SEED && getWorldLocation(Constants.TROLL_STRONGHOLD_AREA_HERB)) {
                seedplantedtrollstronghold = true;
            }
            else if (animationId == AnimationID.FARMING_PLANT_SEED && getWorldLocation(Constants.CATHERBY_AREA_HERB)) {
                seedplantedcatherby = true;
            }
            else if (animationId == AnimationID.FARMING_PLANT_SEED && getWorldLocation(Constants.WEISS_AREA_HERB)) {
                seedplantedweiss = true;
            }
            else if (animationId == AnimationID.FARMING_PLANT_SEED && getWorldLocation(Constants.HARMONY_AREA_HERB)) {
                seedplantedharmony = true;
            }

        }
    }

    private boolean herbPatchReady() {
        Optional<TileObject> herbGrownPatches = TileObjects.search().withName("Herbs").withAction("Pick").withinDistance(12).first();
        if (herbGrownPatches.isPresent()) {
            return true;
        }
        return false;
    }

    private boolean herbPatchDead() {
        Optional<TileObject> deadHerbPatch = TileObjects.search().nameContains("Dead herbs").withAction("Clear").withinDistance(12).first();
        if (deadHerbPatch.isPresent()) {
            return true;
        }
        return false;
    }

    public void plantSeed(int ID) {
        Widget Item = Inventory.search().withId(ID).first().get();
        List<Integer> list = Arrays.asList(8152, 8151, 50697, 8150, 33979, 9372, 27115, 8153, 18816, 33176);
        TileObject herbPatch = TileObjects.search().idInList(list).first().get();

        MousePackets.queueClickPacket();
        ObjectPackets.queueWidgetOnTileObject(Item, herbPatch);

    }

    public int compostType() {
        if (config.compostType() == FarmRunConfig.DropdownOption.COMPOST) {
            return Constants.COMPOST;
        }
        if (config.compostType() == FarmRunConfig.DropdownOption.SUPERCOMPOST) {
            return Constants.SUPERCOMPOST;
        }
        if (config.compostType() == FarmRunConfig.DropdownOption.ULTRACOMPOST) {
            return Constants.ULTRACOMPOST;
        }
        if (config.compostType() == FarmRunConfig.DropdownOption.BOTTOMLESS_BUCKET) {
            return Constants.BOTTOMLESS_BUCKET;
        }
        return 0;
    }

    public void compostPatch(int ID) {
        timeout = tickDelay();

        List<Integer> list = Arrays.asList(8152, 8151, 50697, 8150, 33979, 9372, 27115, 8153, 18816, 33176);
        TileObject herbPatch = TileObjects.search().idInList(list).first().get();
        Widget Item = Inventory.search().withId(ID).first().get();

        MousePackets.queueClickPacket();
        ObjectPackets.queueWidgetOnTileObject(Item, herbPatch);
        timeout = tickDelay();
    }

    public int getFarmingVarBit(int varbit) {
        int varBit = client.getVarbitValue(varbit);
        return varBit;
    }

    public boolean herbPatchNear() {
        List<Integer> list = Arrays.asList(8152, 8151, 50697, 8150, 33979, 9372, 27115, 8153, 18816, 33176);
        Optional<TileObject> herbPatch = TileObjects.search().idInList(list).first();
        if (herbPatch.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean herbPatchNear4771() {
        List<Integer> list = Arrays.asList(18816, 33176);
        Optional<TileObject> herbPatch = TileObjects.search().idInList(list).first();
        if (herbPatch.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean herbPatchNear4772() {
        List<Integer> list = List.of(9372);
        Optional<TileObject> herbPatch = TileObjects.search().idInList(list).first();
        if (herbPatch.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean herbPatchNear4774() {
        List<Integer> list = Arrays.asList(8152, 8151, 50697, 8150, 27115, 8153);
        Optional<TileObject> herbPatch = TileObjects.search().idInList(list).first();
        if (herbPatch.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean herbPatchNear4775() {
        List<Integer> list = List.of(33979);
        Optional<TileObject> herbPatch = TileObjects.search().idInList(list).first();
        if (herbPatch.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean herbGrowing(int varbit) {
        if (getFarmingVarBit(varbit) > 3 && getFarmingVarBit(varbit) < 44) {
            return true;
        }
        return false;
    }

    public boolean herbNotGrowing(int varbit) {
        if (getFarmingVarBit(varbit) == 3) {
            return true;
        }
        return false;
    }

    public boolean getWorldLocation(WorldArea one){
        if (client.getLocalPlayer().getWorldLocation().isInArea(one)){
            return true;
        }return false;
    }
}
