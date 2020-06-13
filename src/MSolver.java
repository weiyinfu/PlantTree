import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MSolver {
int colorCount, treeCount, forestCount;
int[][] cost;
int big = (int) (1e9 + 7);

MSolver() throws FileNotFoundException {
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
        //f[i][j][k]表示树的最少种类数
        long[][][] f = new long[treeCount + 1][colorCount][forestCount + 1];
        for (int i = 1; i < f.length; i++)
            for (int j = 0; j < f[i].length; j++)
                for (int k = 0; k < f[i][j].length; k++) f[i][j][k] = big;
        for (int i = 0; i < treeCount; i++) {
            for (int j = 0; j < colorCount; j++) {//上一棵树的颜色
                for (int plant = 0; plant < colorCount; plant++) {
                    //当前准备种的树的颜色
                    for (int forest = 0; forest <= forestCount; forest++) {
                        //过去已经形成的森林树
                        long co = f[i][j][forest] + cost[i][plant];
                        if (plant == j && i > 0) {
                            f[i + 1][plant][forest] = Math.min(co, f[i + 1][plant][forest]);
                        } else {
                            if (forest == forestCount) continue;
                            f[i + 1][plant][forest + 1] = Math.min(co, f[i + 1][plant][forest + 1]);
                        }
                    }
                }
            }
        }
        long ans = big;
        for (int lastColor = 0; lastColor < colorCount; lastColor++) {
            ans = Math.min(ans, f[treeCount][lastColor][forestCount]);
        }
        if (ans == big) {
            ans = -1;
        }
        System.out.println(ans);
    }
}

public static void main(String[] args) throws FileNotFoundException {
    new MSolver();
}
}
