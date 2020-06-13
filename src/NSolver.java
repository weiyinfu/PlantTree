import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NSolver {
int colorCount, treeCount, forestCount;
int[][] cost;
int big = (int) (1e9 + 7);

NSolver() throws FileNotFoundException {
    Scanner cin = new Scanner(System.in);
    cin = new Scanner(new FileInputStream("in.txt"));
    int cas = cin.nextInt();
    while (cas-- > 0) {
        treeCount = cin.nextInt();
        colorCount = cin.nextInt();
        forestCount = cin.nextInt();
        cost = new int[treeCount][colorCount];
        for (int i = 0; i < treeCount; i++) {
            for (int j = 0; j < colorCount; j++) {
                cost[i][j] = cin.nextInt();
            }
        }
        if (treeCount == 0) {
            System.out.println(0);
            continue;
        }
        if (colorCount == 0 && treeCount > 0
                || treeCount > 0 && forestCount == 0
                || colorCount <= 1 && forestCount > 1) {
            System.out.println(-1);
            continue;
        }
        int ma = (int) (treeCount - (forestCount - 1.0) / 2.0);
        if (ma < 0) {
            System.out.println(-1);
            continue;
        }
        //求前缀和,co[i][j]表示i种树种j棵的花费
        long[][] co = new long[colorCount][ma + 1];
        for (int i = 0; i < colorCount; i++) {
            for (int j = 1; j <= ma; j++) {
                co[i][j] += co[i][j - 1] + cost[j - 1][i];
            }
        }
//        show(co, "cost 数组");
        //f[i][j][k]表示树的最少种类数
        long[][][] f = new long[colorCount][treeCount + 1][forestCount + 1];
        for (int plant = 0; plant <= treeCount; plant++) {
            //第一棵树的森林数不可能太多
            for (int j = 0; j <= forestCount; j++) {
                if (plant == 0 && j == 0) {
                    continue;
                }
                if (plant > 0 && plant <= ma && j == 1) {
                    f[0][plant][j] = co[0][plant];
                } else {
                    f[0][plant][j] = big;
                }
            }
        }
        for (int i = 1; i < colorCount; i++) {
            for (int j = 0; j <= treeCount; j++) {
                for (int l = 0; l <= forestCount; l++) {
                    f[i][j][l] = big;
                    //当前可以植树的总数
                    for (int p = 0; p <= Math.min(ma, j); p++) {
                        //当前准备种p棵树
                        if (p > 0 && l == 0) continue;
                        int lastKind = l - 1;
                        if (p == 0) {
                            lastKind = l;
                        }
                        long now = f[i - 1][j - p][lastKind] + co[i][p];
                        f[i][j][l] = Math.min(f[i][j][l], now);
                    }
                }
            }
        }
        long ans = big;
        for (int i = 0; i <= forestCount; i++) {
            long now = f[colorCount - 1][treeCount][i];
            ans = Math.min(ans, now);
        }
        if (ans == big) {
            ans = -1;
        }
        System.out.println(ans);
    }
}

private void show(long[][] co, String desc) {
    System.out.println(desc);
    for (int i = 0; i < co.length; i++) {
        for (int j = 0; j < co[i].length; j++) {
            System.out.printf("%d ", co[i][j]);
        }
        System.out.println();
    }
    System.out.println("================");
}

public static void main(String[] args) throws FileNotFoundException {
    new NSolver();
}
}
