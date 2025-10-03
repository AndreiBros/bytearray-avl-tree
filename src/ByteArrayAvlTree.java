import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class ByteArrayAvlTree {
    private static final class Node {
        byte[] k;
        byte[] v;
        Node l;
        Node r;
        int h;
        Node(byte[] k, byte[] v) { this.k = k; this.v = v; this.h = 1; }
    }

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Node root;

    public byte[] get(byte[] key) {
        lock.readLock().lock();
        try {
            Node n = root;
            while (n != null) {
                int c = compare(key, n.k);
                if (c == 0) return n.v;
                n = c < 0 ? n.l : n.r;
            }
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    public byte[] put(byte[] key, byte[] value) {
        lock.writeLock().lock();
        try {
            Holder h = new Holder();
            root = insert(root, key, value, h);
            return h.prev;
        } finally {
            lock.writeLock().unlock();
        }
    }

    private static final class Holder { byte[] prev; }

    private Node insert(Node n, byte[] k, byte[] v, Holder h) {
        if (n == null) return new Node(Arrays.copyOf(k, k.length), Arrays.copyOf(v, v.length));
        int c = compare(k, n.k);
        if (c == 0) {
            h.prev = n.v;
            n.v = Arrays.copyOf(v, v.length);
            return n;
        } else if (c < 0) {
            n.l = insert(n.l, k, v, h);
        } else {
            n.r = insert(n.r, k, v, h);
        }
        n.h = 1 + Math.max(h(n.l), h(n.r));
        int bal = h(n.l) - h(n.r);
        if (bal > 1) {
            if (compare(k, n.l.k) < 0) return rotateRight(n);
            n.l = rotateLeft(n.l);
            return rotateRight(n);
        }
        if (bal < -1) {
            if (compare(k, n.r.k) > 0) return rotateLeft(n);
            n.r = rotateRight(n.r);
            return rotateLeft(n);
        }
        return n;
    }

    private static Node rotateRight(Node y) {
        Node x = y.l;
        Node t2 = x.r;
        x.r = y;
        y.l = t2;
        y.h = 1 + Math.max(h(y.l), h(y.r));
        x.h = 1 + Math.max(h(x.l), h(x.r));
        return x;
    }

    private static Node rotateLeft(Node x) {
        Node y = x.r;
        Node t2 = y.l;
        y.l = x;
        x.r = t2;
        x.h = 1 + Math.max(h(x.l), h(x.r));
        y.h = 1 + Math.max(h(y.l), h(y.r));
        return y;
    }

    private static int h(Node n) { return n == null ? 0 : n.h; }

    private static int compare(byte[] a, byte[] b) {
        int len = Math.min(a.length, b.length);
        for (int i = 0; i < len; i++) {
            int ai = a[i] & 0xFF;
            int bi = b[i] & 0xFF;
            if (ai != bi) return ai - bi;
        }
        return a.length - b.length;
    }
}
