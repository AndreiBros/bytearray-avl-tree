public class Main {
    static byte[] b(int... xs) {
        byte[] a = new byte[xs.length];
        for (int i = 0; i < xs.length; i++) a[i] = (byte) xs[i];
        return a;
    }
    public static void main(String[] args) {
        ByteArrayAvlTree t = new ByteArrayAvlTree();
        System.out.println(t.get(b(1)) == null);
        t.put(b(1,2,3), b(9));
        t.put(b(1,2,4), b(8));
        t.put(b(1,2), b(7));
        System.out.println(t.get(b(1,2,3))[0] == 9);
        System.out.println(t.get(b(1,2))[0] == 7);
        System.out.println(t.get(b(5)) == null);
        byte[] prev = t.put(b(1,2,3), b(10));
        System.out.println(prev[0] == 9);
        System.out.println(t.get(b(1,2,3))[0] == 10);
    }
}