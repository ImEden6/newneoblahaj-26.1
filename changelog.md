# Changelog

All notable changes to **New Blåhaj** (`newblahaj`) for Minecraft **Fabric 1.20.1** are documented here.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.10] - 2026-06-09

### Added

- **Removed Redundant png file**: Removed the fixed gray shark png as it was redundant.

## [1.0.9] - 2026-06-09

### Added

- **Soft Toy Dog ("creature") plush**: Added block registry, item registry, crafting recipe, loot table, blockstate, item model, and tag inclusion (`#newblahaj:plushies`).

## [1.0.3] - 2026-05-28

### Added

- **`PlushArmPoseHelper`**: Shared client helper for detecting plush items (`CuddlyItem` / `newblahaj:plushies`) and applying cuddle arm rotations.
- **`PlayerEntityModelMixin`**: Late `setAngles` hook (priority 2100) so plush arm pose is reapplied after **Player Animator** (`player-animator`) updates the player model. No hard dependency on that mod.

### Changed

- **`BipedEntityModelMixin`**: Arm posing now uses `TAIL` injection on `positionRightArm` / `positionLeftArm` (no cancellation), with per-arm `CROSSBOW_HOLD` checks.
- **`PlayerRendererMixin`**: Plush arm pose override moved to `@At("RETURN")` with an explicit method descriptor.

### Fixed

- Plush **cuddle arm pose** no longer breaks when **Player Animator** is installed (arms were being overwritten mid-`setAngles`).
- Client startup crash from invalid `@Shadow` fields on `PlayerEntityModel` (`rightArm` / `leftArm` live on `BipedEntityModel`).

### Removed

- **Bed plush attachment** (Fabric port cleanup): `BedPlushEvents`, bed block/entity render mixins, and related client bed renderer hook — not carried over from the NeoForge bed feature set.

## [1.0.2] - 2026-05-24

### Added

- **Cuddling arm animations**: `BipedEntityModelMixin` applies custom arm pitch/yaw when holding a plushie, for a hugging posture instead of a stiff weapon hold.

### Changed

- **Fabric 1.20.1 port** from NeoForge 26.1: blocks, items, registries, loot, villager trades, sounds, tags, recipes, and mixins updated for Yarn mappings and Fabric API.
- Mod id renamed to `newblahaj` (from `newneoblahaj`).

### Fixed

- Client startup crash in `PlayerRendererMixin` (`AbstractClientPlayerEntity` target).
- Debug log spam from arm-pose mixin.
