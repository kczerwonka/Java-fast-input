import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

    /* https://pl.spoj.com/problems/XIWTPZA/ */
    static boolean check(long a, long b, long x, long y) {
        if (x < a && y < b) return true;
        if (x >= a && y >= b) return false;
        double beta = Math.atan2(y, x);
        double alpha = Math.asin(b / Math.sqrt(x * x + y * y)) - beta;
        if (alpha < 0) return false;
        double p = y * Math.sin(alpha), q = Math.sqrt(x * x + y * y - b * b);
        if (2 * p + q - a >= 0) return false;
        else return true;
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        int num = in.nextInt();
        int a, b, p, q;
        while (num-- > 0) {

            a = in.nextInt();
            b = in.nextInt();
            p = in.nextInt();
            q = in.nextInt();


            if (check(Math.max(a, b), Math.min(a, b), Math.max(p, q), Math.min(p, q))) {
                System.out.println("TAK");
            } else {
                System.out.println("NIE");
            }

        }
    }

}