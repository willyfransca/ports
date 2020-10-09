
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ports {

    public static ArrayList<String> host = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int a = 0;
        int b = 0;
        int c = 0;
        int hasil = 0;
        boolean cari = true;
        while (cari) {
            if (c == 255) {
                c = 0;
                b++;
                if (a == 172 && b >= 16 && b <= 31) {
                    b++;
                } else if (a == 192 && b == 168) {
                    b++;
                }
            }
            if (b == 255) {
                a++;
                if (a == 10) {
                    a++;
                } else if (a == 256) {
                    cari = false;
                }
                c = 0;
            }
            hasil += scan(a, b, c);
            c++;

        }

        System.out.println(hasil);
        buatTxt(hasil);

    }

    public static int scan(int a, int b, int c) {
        int total = 0;
        int d;
        for (int i = 0; i < 256; i++) {
            d = i;
            String hostname = a + "." + b + "." + c + "." + d;

            Socket s = null;

            try {
                // this is to see if host exists:
                InetAddress ipaddress = InetAddress.getByName(hostname);
                int p = 22;

                try {
                    s = new Socket(hostname, p);
                    System.out.println(hostname + " A server is running on port " + p + ".");
                    total++;
                    host.add(hostname);
                    s.close();

                } catch (IOException e) {
//                    System.out.println(hostname + " No server on port " + p + ".");
                }
            } catch (UnknownHostException e) {
//                System.out.println("Could not find host: " + hostname);
            }

            if (s != null) {
                try {
                    s.close();
                } catch (IOException ioEx) {
                }
            }
        }
        return total;
    }

    public static void buatTxt(int z) {

        File file = new File("output.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            for (String string : host) {
                bw.write(string);
                bw.newLine();
            }
            bw.write(z);
        } catch (FileNotFoundException ex) {
            //Menampilkan pesan jika file tidak ditemukan
            System.out.println("File " + file.getName() + " Tidak Ditemukan");
        } catch (IOException ex) {
            //Menampilkan pesan jika terjadi error atau file tidak dapat dibaca
            System.out.println("File " + file.getName() + " Tidak Dapat DIbaca");
        }
    }

}
