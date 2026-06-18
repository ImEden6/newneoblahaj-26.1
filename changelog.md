# Changelog (1.0.1)

### Added
- **Rendering Caching Optimization:** Introduced an `ItemStackRenderState` cache mapping block coordinates. This significantly reduces GC overhead and memory allocations by avoiding creating new rendering states every frame.
- **Client Resource Management:** Added a level unload listener to safely clear cached render states and prevent resource leaks.

### Fixed
- **Rendering State Conflict:** Resolved an issue where multiple beds in the same chunk would display the same plushie (or "flicker" between them) due to a shared `ItemStackRenderState`. Each bed now uses a unique render state during submission.
- **Server Crash:** Fixed a critical `EncoderException` ("Empty ItemStack not allowed") that occurred when interacting with beds (e.g., clicking a bed with a bed item). This was caused by the `BED_PLUSH` attachment sync logic failing on empty stacks; it now correctly uses `OPTIONAL_STREAM_CODEC`.
- **Plushie Alignment:** Refined the vertical translation and offsets for plushies on beds to ensure they sit naturally on the pillow and correctly nestle under the player's head when the bed is occupied.

### Changed
- **Version:** Ported mod to Minecraft 26.2 (NeoForge 26.2.0.1-beta) and bumped version to `1.0.1`.
