# Temporary Changelog (1.0.2)

### Fixed
- **Rendering State Conflict:** Resolved an issue where multiple beds in the same chunk would display the same plushie (or "flicker" between them) due to a shared `ItemStackRenderState`. Each bed now uses a unique render state during submission.
- **Server Crash:** Fixed a critical `EncoderException` ("Empty ItemStack not allowed") that occurred when interacting with beds (e.g., clicking a bed with a bed item). This was caused by the `BED_PLUSH` attachment sync logic failing on empty stacks; it now correctly uses `OPTIONAL_STREAM_CODEC`.
- **Plushie Alignment:** Refined the vertical translation and offsets for plushies on beds to ensure they sit naturally on the pillow and correctly nestle under the player's head when the bed is occupied.

### Changed
- **Version:** Bumped mod version to `1.0.2`.
