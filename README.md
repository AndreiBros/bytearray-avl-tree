Thread-safe AVL tree with `byte[]` keys and values. Methods: `get`, `put`.

## Why AVL?
- Predictable O(log n) get/put
- Deterministic byte-wise lexicographic order
- Simple thread safety via ReadWriteLock

## Usage
See `src/test/java/Main.java`.
