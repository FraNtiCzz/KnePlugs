package com.example.Alching;

import net.runelite.client.config.*;

@ConfigGroup("AlchPluginConfig")
public interface AlchConfig extends Config {

    @ConfigSection(name = "Nature Runes", description = "", position = 0, closedByDefault = false)
    public static final String NatureRuneSection = "Nature Runes";
    @ConfigSection(name = "Item One", description = "", position = 1, closedByDefault = false)
    public static final String ItemOneSection = "Item One";
    @ConfigSection(name = "Item Two", description = "", position = 2, closedByDefault = false)
    public static final String ItemTwoSection = "Item Two";

    @ConfigSection(name = "Item Three", description = "", position = 3, closedByDefault = false)
    public static final String ItemThreeSection = "Item Three";

    @ConfigItem
            (keyName = "NatureRuneQTY",
                    name = "Nature Rune QTY",
                    description = "",
                    position = 0,
                    section = "Nature Runes")
    default int natureRuneQTY() {
        return 0;
    }
    @ConfigItem
            (keyName = "NatureRunePrice",
                    name = "Nature Rune Price",
                    description = "",
                    position = 2,
                    section = "Nature Runes")
    default int natureRunePrice() {
        return 0;
    }

    @ConfigItem
            (keyName = "ItemOne",
                    name = "Item One",
                    description = "",
                    position = 0,
                    section = "Item One")
    default int ItemOne() {
        return 0;
    }
    @ConfigItem
            (keyName = "ItemOnePrice",
                    name = "Item One Price",
                    description = "",
                    position = 2,
                    section = "Item One")
    default int ItemOnePrice() {
        return 0;
    }

    @ConfigItem
            (keyName = "ItemOneQTY",
                    name = "Item One Quantity",
                    description = "",
                    position = 3,
                    section = "Item One")
    default int ItemOneQTY() {
        return 0;
    }


    @ConfigItem
            (keyName = "ItemTwo",
                    name = "Item Two",
                    description = "",
                    position = 0,
                    section = "Item Two")
    default int ItemTwo() {
        return 0;
    }
    @ConfigItem
            (keyName = "ItemTwoPrice",
                    name = "Item Two Price",
                    description = "",
                    position = 2,
                    section = "Item Two")
    default int ItemTwoPrice() {
        return 0;
    }

    @ConfigItem
            (keyName = "ItemTwoQTY",
                    name = "Item Two Quantity",
                    description = "",
                    position = 3,
                    section = "Item Two")
    default int ItemTwoQTY() {
        return 0;
    }

    @ConfigItem
            (keyName = "ItemThree",
                    name = "Item Three",
                    description = "",
                    position = 0,
                    section = "Item Three")
    default int ItemThree() {
        return 0;
    }
    @ConfigItem
            (keyName = "ItemThreePrice",
                    name = "Item Three Price",
                    description = "",
                    position = 2,
                    section = "Item Three")
    default int ItemThreePrice() {
        return 0;
    }

    @ConfigItem
            (keyName = "ItemThreeQTY",
                    name = "Item Three Quantity",
                    description = "",
                    position = 3,
                    section = "Item Three")
    default int ItemThreeQTY() {
        return 0;
    }


    @ConfigSection(
            name = "Game Tick Configuration",
            description = "Configure how to handles game tick delays, 1 game tick equates to roughly 600ms",
            position = 100,
            closedByDefault = true
    )
    String delayTickConfig = "delayTickConfig";

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
            name = "Start/Stop",
            description = "Start/Stop Hotkey",
            position = 100000,
            closedByDefault = false
    )
    String startStopConfig = "startStopConfig";

    @ConfigItem(keyName = "startStopHotkey",
            name = "Start/Stop Hotkey",
            position = 6,
            description = "The hotkey to start and stop the script",
            section = "startStopConfig")
    default Keybind startStopHotkey() {
        return Keybind.NOT_SET;
    }

}
