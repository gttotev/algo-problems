import java.io.BufferedReader;

class Main {
    public static void main(String[] args) throws java.io.IOException {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        int hr;
        double min;
        String[] in = br.readLine().split(":");
        while (in[0].charAt(0) != '0') {
            hr = Integer.parseInt(in[0]) % 12;
            min = Integer.parseInt(in[1]);
            min = Math.abs(11 * min / 2 - 30 * hr);
            System.out.format("%.3f\n", Math.min(min, 360 - min));
            in = br.readLine().split(":");
        }
    }
}
