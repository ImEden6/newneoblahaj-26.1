import pathlib

root = pathlib.Path(__file__).resolve().parents[1] / "src/main/resources/assets/newneoblahaj/models/block"
for p in root.glob("*.json"):
    t = p.read_text(encoding="utf-8")
    if '"render_type"' in t:
        continue
    if t.lstrip().startswith("{"):
        t2 = t.replace("{", '{\n  "render_type": "cutout",', 1)
        p.write_text(t2, encoding="utf-8")
print("patched", len(list(root.glob("*.json"))), "models")
