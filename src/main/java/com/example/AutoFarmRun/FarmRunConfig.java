package com.example.AutoFarmRun;

import net.runelite.client.config.*;

@ConfigGroup("AutoFarmRun")
public interface FarmRunConfig extends Config {
    @ConfigSection(name = "Patches",
            description = "",
            position = 2,
            closedByDefault = true)
    public static final String patchSection = "Patches";

    @ConfigSection(name = "Patch Order",
            description = "",
            position = 3,
            closedByDefault = true)
    public static final String patchOrderSection = "Patch Order";

/*    @ConfigSection(name = "Patch Types",
            description = "",
            position = 1,
            closedByDefault = true)
    public static final String patchTypeSection = "Patch Types";*/

    @ConfigSection(name = "Seed ID's",
            description = "",
            position = 0,
            closedByDefault = true)
    public static final String seedSection = "Seeds";

    @ConfigSection(name = "Teleport Methods",
            description = "",
            position = 4,
            closedByDefault = true)
    public static final String teleportSection = "Teleports";

    @ConfigSection(name = "Compost",
            description = "",
            position = 2,
            closedByDefault = true)
    public static final String otherSection = "Other";

    @ConfigItem(keyName = "faladorpatch",
            name = "Falador Patch",
            description = "This will include the Falador Patch in your farm runs.",
            position = 1,
            section = "Patches")
    default boolean faladorPatch() {
        return true;
    }

    @ConfigItem(keyName = "catherbypatch",
            name = "Catherby Patch",
            description = "This will include the Catherby Patch in your farm runs.",
            position = 2,
            section = "Patches")
    default boolean catherbyPatch() {
        return true;
    }

    @ConfigItem(keyName = "morytaniapatch", name = "Morytania Patch",
            description = "This will include the Mortania Patch in your farm runs.",
            position = 3,
            section = "Patches")
    default boolean morytaniaPatch() {
        return true;
    }

    @ConfigItem(keyName = "ardougnepatch",
            name = "Ardougne Patch",
            description = "This will include the Ardougne Patch in your farm runs.",
            position = 4,
            section = "Patches")
    default boolean ardougnePatch() {
        return true;
    }

    @ConfigItem(keyName = "farmingGuildpatch",
            name = "Farm Guild Patch",
            description = "This will include the Farming Guild Patch in your farm runs.",
            position = 5, section = "Patches")
    default boolean farmingGuildPatch() {
        return true;
    }

    @ConfigItem(keyName = "kourendpatch",
            name = "Kourend Patch",
            description = "This will include the Kourend Patch in your farm runs.",
            position = 6,
            section = "Patches")
    default boolean kourendPatch() {
        return true;
    }

    @ConfigItem(keyName = "trollpatch",
            name = "Troll Stronghold Patch",
            description = "This will include the Troll Stronghold Patch in your farm runs.",
            position = 7,
            section = "Patches")
    default boolean trollStrongholdPatch() {
        return true;
    }

    @ConfigItem(keyName = "weisspatch",
            name = "Weiss Patch",
            description = "This will include the Weiss Patch in your farm runs.",
            position = 8,
            section = "Patches")
    default boolean weissPatch() {
        return true;
    }

    @ConfigItem(keyName = "harmonypatch",
            name = "Harmony Patch",
            description = "This will include the Harmony Patch in your farm runs.",
            position = 9,
            section = "Patches")
    default boolean harmonyPatch() {
        return true;
    }

    @ConfigItem(keyName = "fortispatch",
            name = "Fortis Patch",
            description = "This will include the Ardougne Patch in your farm runs.",
            position = 10,
            section = "Patches")
    default boolean fortisPatch() {
        return true;
    }

/*    @ConfigItem(keyName = "flowerpatch",
            name = "Flower Patches",
            description = "This will include all flower patches into your farm runs.",
            position = 1,
            section = "Patch Types")
    default boolean flowerPatch() {
        return false;
    }*/

    @ConfigItem(keyName = "herbseed",
            name = "Herb Seed",
            description = "This is the seeds to be planted in herb patches.",
            position = 1,
            section = "Seeds")
    default int herbSeed() {
        return 0;
    }

/*    @ConfigItem(keyName = "flowerseeds",
            name = "Flower Seeds",
            description = "This is the seeds to be planted in flower patches.",
            position = 1,
            section = "Seeds")
    default int flowerSeeds() {
        return 0;
    }*/

    @ConfigItem(keyName = "composttype",
            name = "Compost Type",
            description = "Choose which compost you want to use",
            position = 1,
            section = "Other")
    default DropdownOption compostType() {
        return DropdownOption.BOTTOMLESS_BUCKET;
    }

    @ConfigItem(keyName = "bottomless",
            name = "Bottomless?",
            description = "Check this if using bottomless",
            position = 1,
            section = "Other")
    default boolean bottomLess() {
        return false;
    }

        enum DropdownOption{COMPOST, SUPERCOMPOST, ULTRACOMPOST, BOTTOMLESS_BUCKET}

    @ConfigItem(keyName = "ardytelemethod",
            name = "Ardougne",
            description = "This is the teleport method to get to Ardy Patch", position = 0, section = "Teleports")
    default TeleportMethods.ardyTeleMethods ardyTele() {
        return TeleportMethods.ardyTeleMethods.ARDOUGNE_CAPE;
    }

    @ConfigItem(keyName = "catherbytelemethod",
            name = "Catherby",
            description = "This is the teleport method to get to Catherby Patch", position = 1, section = "Teleports")
    default TeleportMethods.catherbyTeleMethods catherbyTele() {return TeleportMethods.catherbyTeleMethods.CAMELOT_TELEPORT;}

    @ConfigItem
            (keyName = "fortistelemethod",
            name = "Fortis",
            description = "This is the teleport method to get to Fortis Patch", position = 2, section = "Teleports")
    default TeleportMethods.fortisTeleMethods fortisTele() {
        return TeleportMethods.fortisTeleMethods.CIVITAS_ILLA_FORTIS_TELEPORT;
    }

    @ConfigItem(keyName = "faladortelemethod",
            name = "Falador",
            description = "This is the teleport method to get to Falador Patch", position = 3, section = "Teleports")
    default TeleportMethods.faladorTeleMethods faladorTele() {
        return TeleportMethods.faladorTeleMethods.EXPLORER_RING;
    }

    @ConfigItem(keyName = "farminguildtelemethod",
            name = "Farming Guild",
            description = "This is the teleport method to get to Farming Guild Patch", position = 4, section = "Teleports")
    default TeleportMethods.farmingGuildTeleMethods fgTele() {
        return TeleportMethods.farmingGuildTeleMethods.FAIRY_RINGS;
    }

    @ConfigItem(keyName = "harmonytelemethod",
            name = "Harmony",
            description = "This is the teleport method to get to Harmony Patch", position = 5, section = "Teleports")
    default TeleportMethods.Harmony harmonyTele() {
        return TeleportMethods.Harmony.HOUSE_PORTALS;
    }

    @ConfigItem(keyName = "kourendtelemethod",
            name = "Kourend",
            description = "This is the teleport method to get to Kourend Patch", position = 6, section = "Teleports")
    default TeleportMethods.kourendTeleMethods kourendTele() {
        return TeleportMethods.kourendTeleMethods.XERICS_NECKLACE;
    }

    @ConfigItem(keyName = "morytaniatelemethod",
            name = "Morytania",
            description = "This is the teleport method to get to Morytania Patch", position = 7, section = "Teleports")
    default TeleportMethods.morytaniaTeleMethods moryTele() {
        return TeleportMethods.morytaniaTeleMethods.ECTOPHIAL;
    }

    @ConfigItem(keyName = "tstelemethod",
            name = "Troll Stronghold",
            description = "This is the teleport method to get to Troll Stronghold Patch", position = 8, section = "Teleports")
    default TeleportMethods.trollStrongHoldMethod tsTele() {
        return TeleportMethods.trollStrongHoldMethod.HOUSE_PORTALS;
    }

    @ConfigItem(keyName = "weisstelemethod",
            name = "Weiss",
            description = "This is the teleport method to get to Weiss Patch", position = 9, section = "Teleports")
    default TeleportMethods.weissMethod weissTele() {
        return TeleportMethods.weissMethod.HOUSE_PORTALS;
    }

    @ConfigItem(keyName = "startStopHotkey",
            name = "Start/Stop Hotkey",
            position = 6, description = "The hotkey to start and stop the script")
    default Keybind startStopHotkey() {
        return Keybind.NOT_SET;
    }

    @Range(
            max = 10
    )
    @ConfigItem(
            keyName = "tickDelayMin",
            name = "Game Tick Min",
            description = "",
            position = 101,
            section = delayTickConfig
    )
    default int tickDelayMin() {
        return 1;
    }

    @Range(
            max = 10
    )
    @ConfigItem(
            keyName = "tickDelayMax",
            name = "Game Tick Max",
            description = "",
            position = 102,
            section = delayTickConfig
    )
    default int tickDelayMax() {
        return 3;
    }

    @ConfigItem(
            keyName = "tickDelayEnabled",
            name = "Tick delay",
            description = "enables some tick delays",
            position = 103,
            section = delayTickConfig
    )
    default boolean tickDelay() {
        return false;
    }

    @ConfigSection(
            name = "Game Tick Configuration",
            description = "Configure how to handles game tick delays, 1 game tick equates to roughly 600ms",
            position = 100,
            closedByDefault = true
    )
    String delayTickConfig = "delayTickConfig";







/*    @ConfigItem(keyName = "faladorpatch",
            name = "Falador Patch",
            description = "This will include the Falador Patch in your farm runs.",
            position = 1,
            section = "Patches")
    default int faladorPatchOrder() {
        return 1;
    }

    @ConfigItem(keyName = "catherbypatch",
            name = "Catherby Patch",
            description = "This will include the Catherby Patch in your farm runs.",
            position = 2,
            section = "Patches")
    default int catherbyPatchOrder() {
        return 2;
    }

    @ConfigItem(keyName = "morytaniapatch", name = "Morytania Patch",
            description = "This will include the Mortania Patch in your farm runs.",
            position = 3,
            section = "Patches")
    default int morytaniaPatchOrder() {
        return 3;
    }

    @ConfigItem(keyName = "ardougnepatch",
            name = "Ardougne Patch",
            description = "This will include the Ardougne Patch in your farm runs.",
            position = 4,
            section = "Patches")
    default int ardougnePatchOrder() {
        return 4;
    }

    @ConfigItem(keyName = "farmingGuildpatch",
            name = "Farm Guild Patch",
            description = "This will include the Farming Guild Patch in your farm runs.",
            position = 5, section = "Patches")
    default int farmingGuildPatchOrder() {
        return 5;
    }

    @ConfigItem(keyName = "kourendpatch",
            name = "Kourend Patch",
            description = "This will include the Kourend Patch in your farm runs.",
            position = 6,
            section = "Patches")
    default int kourendPatchOrder() {
        return 6;
    }

    @ConfigItem(keyName = "trollpatch",
            name = "Troll Stronghold Patch",
            description = "This will include the Troll Stronghold Patch in your farm runs.",
            position = 7,
            section = "Patches")
    default int trollStrongholdPatchOrder() {
        return 8;
    }

    @ConfigItem(keyName = "weisspatch",
            name = "Weiss Patch",
            description = "This will include the Weiss Patch in your farm runs.",
            position = 8,
            section = "Patches")
    default int weissPatchOrder() {
        return 9;
    }

    @ConfigItem(keyName = "harmonypatch",
            name = "Harmony Patch",
            description = "This will include the Harmony Patch in your farm runs.",
            position = 9,
            section = "Patches")
    default int harmonyPatchOrder() {
        return 10;
    }

    @ConfigItem(keyName = "fortispatch",
            name = "Fortis Patch",
            description = "This will include the Ardougne Patch in your farm runs.",
            position = 10,
            section = "Patches")
    default int fortisPatchOrder() {
        return 7;
    }*/

}