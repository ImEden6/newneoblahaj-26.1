# New Neo Blåhaj

NeoForge mod for Minecraft **26.1.x** (Java **25**). Adds IKEA-style plush toys as items and directional blocks, plus optional plush-on-bed decoration via data attachments.

Port chain: original **Hibiii** Blåhaj (Quilt) → **DaFuqs** / Fabric lineage → **rhysdh540** NeoForge reference → this fork.

## Building

Requires JDK 25. From the project root:

```bash
./gradlew build
```

Use `./gradlew runClient` or `./gradlew runServer` for a dev environment.

## Repository layout

- **`src/`** — mod sources and resources (`main` only unless you add tests).
- **`tools/patch_render_type.py`** — optional one-off helper to add `"render_type": "cutout"` to `assets/newneoblahaj/models/block/*.json` when batch-editing models.

`_reference_blahaj/` is listed in `.gitignore` for an optional local porting checkout and is **not** part of the published tree.

## License

This project is released under the [Unlicense](LICENSE) (public domain), consistent with the upstream Blåhaj mods.

## Credits and third-party content

- **In-game models/textures** derive from the original Blåhaj mod lineage (notably **Hibiii**); see upstream repositories for full attribution.
- **Project icon** “Blobhaj” by **Heatherhorns** is licensed under **CC BY 4.0** where applicable (see upstream READMEs).
- **NeoForge MDK** and **Minecraft/Mojang mappings** are subject to their respective licenses.

## Documentation

- [NeoForge docs](https://docs.neoforged.net/)
- [NeoForge Discord](https://discord.neoforged.net/)
