//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws IOException {
        //BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Scanner in = new Scanner(System.in);
        int n, q;
        int[] data;
        SegmentTree seg;
        while ((n = in.nextInt()) > 0) {
            q = in.nextInt();
            data = new int[n];
            for (int i = 0; i < n; i++) data[i] = in.nextInt();
            seg = new SegmentTree(data);
            //seg.print();
            for (int i = 0; i < q; i++)
                System.out.println(seg.rfq(in.nextInt() - 1, in.nextInt() - 1));
        }
        in.close();
    }

    static class SegmentTree {
        int n;
        int[] data;
        Fseg[] st;

        public SegmentTree(int[] data) {
            n = data.length;
            this.data = data;
            st = new Fseg[4 * n];
            build(1, 0, n - 1);
        }

        int rfq(int l, int r) {
            return rfq(1, l, r).freq[1];
        }

        Fseg rfq(int p, int l, int r) {
            Fseg fg = st[p];
            if (l > fg.r || r < fg.l) return null;
            if (fg.l >= l && fg.r <= r) return fg;

            Fseg lfg = rfq(p * 2, l, r);
            Fseg rfg = rfq(p * 2 + 1, l, r);

            if (lfg == null) return rfg;
            if (rfg == null) return lfg;
            return new Fseg(lfg, rfg);
        }

        Fseg build(int p, int l, int r) {
            if (l == r) {
                st[p] = new Fseg(l, data[l]);
                return st[p];
            }
            int mid = (l + r) / 2;
            Fseg lfg = build(2 * p, l, mid);
            Fseg rfg = build(2 * p + 1, mid + 1, r);

            Fseg res = new Fseg(lfg, rfg);
            st[p] = res;
            return res;
        }

        void print() {
            int nl = 1;
            for (int i = 1; i < st.length; i++) {
                System.err.format("%d: %s, ", i, (st[i] == null ? "__" : st[i].toString()));
                if (i == nl) {
                    System.err.println();
                    nl = i * 2 + 1;
                }
            }
        }
    }

    static class Fseg {
        int l, r;
        int[] max = new int[3];
        int[] freq = new int[3];

        public Fseg(int i, int m) {
            l = (r = i);
            for (int j = 0; j < 3; j++) {
                max[j] = m;
                freq[j] = 1;
            }
        }

        public Fseg(Fseg lfg, Fseg rfg) {
            l = lfg.l;
            r = rfg.r;
            max[0] = lfg.max[0];
            freq[0] = lfg.freq[0];
            max[2] = rfg.max[2];
            freq[2] = rfg.freq[2];
            if (lfg.freq[1] > rfg.freq[1]) {
                max[1] = lfg.max[1];
                freq[1] = lfg.freq[1];
            } else {
                max[1] = rfg.max[1];
                freq[1] = rfg.freq[1];
            }
            int lfgm = lfg.max[2];
            if (lfgm == rfg.max[0]) {
                int fsum = lfg.freq[2] + rfg.freq[0];
                if (fsum > freq[1]) {
                    max[1] = lfgm;
                    freq[1] = fsum;
                }
                if (max[0] == lfgm) {
                    freq[0] = fsum;
                }
                if (max[2] == lfgm) {
                    freq[2] = fsum;
                }
            }
        }

        public String toString() {
            return String.format("{%d-%d: %s, %s}", l, r, Arrays.toString(max), Arrays.toString(freq));
        }
    }
}
