public class Main {
  public static void main(String[] args) {
    ByteArrayAvlTree t = new ByteArrayAvlTree();
    t.put(new byte[]{1,2,3}, new byte[]{9});
    t.put(new byte[]{1,2,4}, new byte[]{8});
    t.put(new byte[]{1,2}, new byte[]{7});
    System.out.println((t.get(new byte[]{1,2,3}))[0]);
    System.out.println((t.get(new byte[]{1,2}))[0]);
    System.out.println(t.get(new byte[]{5}) == null);
  }
}
