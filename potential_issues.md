# Potential Issues in New-Neo-Blahaj (NeoForge 26.1)

This document outlines potential logic, rendering, and architectural issues identified in the current codebase, including items that are already addressed.

## 1. Block Collision (VoxelShapes) — open

* **Location:** `CuddlyBlock.java`
* **Issue:** The `SHAPE` is a static 8x8x8 box. Since sharks are long, this collision box does not match the model when rotated.
* **Impact:** Players can walk through the nose/tail of a placed shark.

## 2. Arm posing ("hug") — partial (client extensions only)

* **Working:** `IClientItemExtensions` via `CuddlyItemClientExtensions` + `RegisterClientExtensionsEvent`: mod `CuddlyItem` stacks use `HumanoidModel.ArmPose.CROSSBOW_HOLD` when in `#newneoblahaj:plushies` (see `PlushArmPoses.HELD_PLUSH`).
* **Disabled in this build:** `HumanoidModelPlushHugMixin` and `AvatarRendererPlushArmPoseMixin` were **removed from `newneoblahaj.mixins.json`** (and their sources deleted) so large modpacks can boot without `MixinTransformerError` during unrelated mods’ `EntityRenderersEvent.RegisterRenderers` (e.g. Sophisticated Backpacks). That means **no extra arm tuck** and **no** forced crossbow pose for **non-`CuddlyItem`** entries that are only in the tag.
* **If the crash persists:** it is **not** from those mixins (they are off). Capture the full **`Caused by:`** chain under `MixinTransformerError` in `crash-reports/*.txt` to identify the real mixin.

## 3. Bed Rendering Synchronization — resolved

* **Location:** `BedPlushEvents.java`
* **Status:** After placing or removing a bed plush, the code calls `setChanged()` on the bed block entity and `level.sendBlockUpdated(...)` on the head position so clients receive block entity sync data for the attachment.

## 4. State-Driven Bed Rendering — current behavior and residual risk

* **Location:** `BedRendererMixin.java`
* **Behavior:** Resolves the head `BedBlockEntity` via `BedPlushSupport` (same as server-side placement), reads the plush attachment, and uses a **fresh** `ItemStackRenderState` per `submit` call so another bed cannot overwrite the resolved item model before geometry is submitted.
* **Residual risk:** If `BedRenderState.blockPos` were ever null in a future version while the bed still renders, the mixin returns early and the plush would not draw until the contract changes again.

## 5. Visual Overlap in Beds — mitigated

* **Location:** `BedRendererMixin.java`
* **Status:** When `BedBlock.OCCUPIED` is true at the bed head (via `level.getBlockState(headPos)`), the plush transform adds an extra downward and head-ward nudge so it reads more like a pillow under the sleeper. No player-model mixin (last resort) is implemented.

## 6. Sound Subtitles — resolved

* **Location:** `assets/newneoblahaj/sounds.json` and `assets/newneoblahaj/lang/en_us.json`
* **Status:** Each sound entry sets an explicit `subtitle` translation key, and matching strings exist under `subtitles.newneoblahaj.block.cuddly_item.*` in `en_us.json`.
