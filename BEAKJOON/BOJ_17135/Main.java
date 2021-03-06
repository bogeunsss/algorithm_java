package BEAKJOON.BOJ_17135;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static int[][] field;
    public static int n, m, d;
    public static int answer = 0;

    public static Deque<int[]> archers = new ArrayDeque<>();

    public static int[][] copyField() {
        int[][] ret = new int[n+1][m];

        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                ret[i][j] = field[i][j];
            }
        }

        return ret;
    }

    public static List<int[]> copyArchers() {
        List<int[]> ret = new ArrayList<>();

        for(int[] archer : archers) {
            int[] newArcher = new int[2];

            newArcher[0] = archer[0];
            newArcher[1] = archer[1];
            ret.add(newArcher);
        }

        return ret;
    }

    public static void defense() {
        List<int[]> newArchers = copyArchers();
        int[][] newField = copyField();
        boolean[][] visited = new boolean[n+1][m];
        int cnt = 0;

        Queue<int[]> q;
        for(int i=0; i<n; i++) {
            q = new LinkedList<>();

            for(int j=0; j<3; j++) {
                loop:
                for(int distance=1; distance<=d; distance++) {
                    int dx = -1;
                    int dy = (distance - 1) * -1;

                    for(int k=1; k<2*distance; k++) {
                        int nx = newArchers.get(j)[0] + dx;
                        int ny = newArchers.get(j)[1] + dy;

                        dx = k >= distance ? dx + 1 : dx - 1;
                        dy++;

                        if(nx < 0 || ny < 0 || ny >= m) continue;
                        if(newField[nx][ny] == 1) {
                            if(!visited[nx][ny]) {
                                cnt++;
                                visited[nx][ny] = true;
                                q.offer(new int[]{nx, ny});
                            }
                            break loop;
                        }
                    }
                }
                newArchers.get(j)[0]--;
            }
            while(!q.isEmpty()) {
                newField[q.peek()[0]][q.peek()[1]] = 0;
                q.poll();
            }
        }
        answer = Math.max(answer, cnt);
    }

    public static void arrange(int archer, int index) {
        if(archer >= 3) {
            defense();
            return;
        }
        if(index >= m || archer + m - index < 3) {
            return;
        }

        archers.offer(new int[]{n, index});
        arrange(archer + 1, index + 1);
        archers.pollLast();
        arrange(archer, index + 1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());

        field = new int[n+1][m];

        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine(), " ");

            for(int j=0; j<m; j++) {
                field[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        arrange(0, 0);

        System.out.println(answer);
    }
}