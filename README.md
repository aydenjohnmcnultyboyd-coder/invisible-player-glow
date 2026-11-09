# Invisible Player Glow (Fabric) — Minecraft Java 1.21.11

A client-side Fabric mod that adds bright sparkle particles around **invisible player entities**.

## What it does
- Only targets other players who are currently invisible.
- Only checks players inside your configured render-distance radius.
- Raycasts from your eyes to the target and skips the effect when a solid block blocks the view.
- Adds vanilla End Rod sparkle particles around the target.
- Does not apply the Glowing status effect, change player visibility, or send packets.

## Install (once built)
1. Install Fabric Loader for Minecraft 1.21.11 and Fabric API.
2. Put `invisible-player-glow-1.0.0.jar` into your `.minecraft/mods` folder.
3. Launch the Fabric 1.21.11 profile.

## Build the mod JAR
Requires JDK 21 and Gradle.
Run from this project folder:

- Windows: `gradle build`
- macOS/Linux: `gradle build`

The built JAR will be in `build/libs/`.

## Notes
This is source code for a client-side mod project, not a precompiled JAR. It needs to be built with Gradle before it can be installed. Particle visibility is subject to Minecraft's normal particle settings and rendering.
