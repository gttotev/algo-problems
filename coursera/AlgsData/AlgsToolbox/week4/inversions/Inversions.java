import java.util.*;

public class Inversions {
    public static long merge(int[] arr, int li, int ri, int le, int re) {
        long invPairs = 0;
        int[] resArr = new int[ri - li + re - le + 2];
        int resShift = li;
        for (int resI = 0; resI < resArr.length; resI++) {
            if (li > ri) {
                resArr[resI] = arr[le];
                le++;
            } else if (le > re) {
                resArr[resI] = arr[li];
                li++;
            } else {
                if (arr[li] > arr[le]) {
                    resArr[resI] = arr[le];
                    le++;
                    invPairs += ri - li + 1;
                } else {
                    resArr[resI] = arr[li];
                    li++;
                }
            }
        }
        copy(resArr, arr, resShift);
        return invPairs;
    }
    
    private static long mergeSort(int[] arr, int left, int right) {
        if (right - left <= 1) {
            if (left < right && arr[left] > arr[right]) {
                int tmp = arr[left];
                arr[left] = arr[right];
                arr[right] = tmp;
                return 1;
            }
            return 0;
        }
        long numInv = 0;
        int mid = (left + right) / 2;
        numInv += mergeSort(arr, left, mid);
        numInv += mergeSort(arr, mid+1, right);
        numInv += merge(arr, left, mid, mid+1, right);
        return numInv;
    }

    private static long getNumInv(int[] arr, int arrLength) {
        return mergeSort(arr, 0, arrLength-1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        System.out.println(getNumInv(a, n));
    }
    
    private static void copy(int[] from, int[] to, int toStart) {
        for (int i = 0; i < from.length; i++) {
            to[toStart + i] = from[i];
        }
    }
}

