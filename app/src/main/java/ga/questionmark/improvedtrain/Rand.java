package ga.questionmark.improvedtrain;

import java.util.Random;

/**
 * Created by amelia on 19.01.2017.
 */

public class Rand {



    static Random r = new Random();

        private static int[][] setNumber(int size) {
            int s = r.nextInt(size/3) + size/3;
            int numbers[] = new int[size];
            for (int i = 0; i < s; ++i) {
                numbers[i] = i;
            }
            return randomizeMatrix(size,1);
        }

        private static int[][] makeArray(int W, int H) {
            int numbers[][] = new int[W][H];
            for (int i = 0; i < W; i++)
                for (int j = 0; j < H; j++)
                    numbers[i][j] = i * H + j + 1;
            return numbers;
        }


        public static int[][] randomizeMatrix(int W, int H) {
            int numbers[][] = makeArray(W, H);

            for (int i = 0; i < W * H; i++) {
                int ind = r.nextInt(W * H - i);
                int tmp = numbers[(W * H - i - 1) / H][(W * H - i - 1) % H];
                numbers[(W * H - i - 1) / H][(W * H - i - 1) % H] = numbers[ind / H][ind % H];
                numbers[ind / H][ind % H] = tmp;
            }
            return  numbers;
        }

        private static void randomIntervalSize2 (int[] array) {
            int size = array.length;
            int s = r.nextInt(size/3) + size/3;
            for (int i = 0; i < size; ++i) {
                if (i < s)
                    array[i] = i + 1;
                else
                    array[i] = r.nextInt(s) + 1;
            }
            randomizeArray(array);
        }

        private static void randomIntervalSize (int[] array) {
            int size = array.length;
            int s = r.nextInt(size/3) + size/3;
            int numbers[] = new int[s];
            int extra = size - s + 1;
            for (int i = 0; i < s; ++i) {
                int tmp = r.nextInt(extra) + 1;
                extra -= (tmp - 1);
                numbers[i] = tmp;
            }
            randomizeArray(numbers);
            int j = 0;
            for (int i = 0; i < s; ++i) {
                for (int k = 0; k < numbers[i]; ++k) {
                    array[j] = i + 1;
                    ++j;
                }
            }
            randomizeArray(array);
        }

        public static void randomizeArray (int[] array) {
            int size = array.length;
            for (int i = 0; i < size; ++i) {
                int ra = r.nextInt(size - i);
                int tmp = array[size - i - 1];
                array[size - i - 1] = array[ra];
                array[ra] = tmp;
            }
        }

        public static int[][] makeMatrix (int W, int H) {
            int array[] = new int[W*H];
            int solution[][] = new int[W][H];
            randomIntervalSize2(array);
            int k = 0;
            for (int i = 0; i < W; ++i) {
                for (int j = 0; j < H; ++j) {
                    solution[i][j] = array[k];
                    ++k;
                }
            }
            return solution;
        }



}
