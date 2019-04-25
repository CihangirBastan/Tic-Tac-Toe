//(main classi)

package pro_lab;

import java.util.Scanner;

public class Pro_Lab {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int n, sayac = 0;//Tahtanin boyutu n adli deðiþkeninde saklanacaktir
        String isim, secim;//Kullanicinin ismi isim deðiþkeninde saklanacaktir
        char harf1, harf2;

        System.out.println("Oyun Tahtasinin Boyutunu Giriniz : ");
        n = input.nextInt();

        System.out.println("Isminizi Giriniz : ");
        isim = input.next();

        oyunTahtasi tahta = new oyunTahtasi(n);
        oyuncu insan = new oyuncu();

        secim = insan.karakteriAl();//Kullanicidan karakter alýyoruz
        harf1 = secim.charAt(0);//Alinan stringi chara çeviriyoruz
        harf2 = insan.karakterinTersi(harf1);//Kullanicin giriþine göre bilgisayara deðer atiyoruz

        tahta.oyunTahtasiniYazdir(n);
        boolean kontrol = true;

        while (kontrol) {
            tahta.hamleyiYaz(insan.insanOyuncuHamlesiniKotrol(), harf1, n);//Burada kullanýcýdan hamlenin koordinatini alip o koordinata yazma iþlemi yapiyoruz

            if (n == 3 && sayac != 4 || n == 5 && sayac != 12 || n == 7 && sayac != 24) {//Oyun tahtasýnda en son insan veri giriþi yapabilir matematiksel olarak burada onun kontrolünü yapiyoruz
                tahta.hamleyiYaz(insan.bilgisayarHamlesiUret(n), harf2, n);//Bilgisayar rastgele koordinata kendi harfini giriyor
            }
            if (tahta.beraberlikKontrol(harf1, n)) {//Beraberlik kontrolu yapiliyorr
                kontrol = false;//Eðer beraberlik varsa döngüden çýkmak için kontrol deðiþkenini false yapiyoruz
            }
            tahta.oyunTahtasiniYazdir(n);//Oyun tahtasini yazdiriyoruz
            if (tahta.kazanan(harf1, n) == true) {//Ýnsan için Kazanma kontrolü yapiyoruz
                System.out.println("Oyunu Siz Kazandiniz ");
                kontrol = false;//Eðer kazanma varsa döngüden çýkmak için kontrol deðiþkenini false yapiyoruz
            } else if (tahta.kazanan(harf2, n) == true) {//Bilgisayar için Kazanma kontrolü yapiyoruz
                System.out.println("Oyunu Bilgisayar Kazandi ");
                kontrol = false;
            }
            sayac++;
        }

    }
}





//oyuncu.java classý 


package pro_lab;

import java.util.Scanner;
import java.util.Random;

public class oyuncu {

    Scanner input = new Scanner(System.in);
    Random rastgele = new Random();
    public boolean insanmiKontrolu;
    private char harf;
    public String koordinat;
    private String id;

    oyuncu() {
        if (insanmiKontrolu == true) {
            harf = 'X';
            insanmiKontrolu = true;
        }

    }

    oyuncu(boolean insanmiKontrolu) {
        this.insanmiKontrolu = insanmiKontrolu;
        if (insanmiKontrolu == true) {
            harf = 'X';
        } else {
            harf = 'O';
        }
    }

    oyuncu(boolean insanmiKontrolu, char kr) {
        this.insanmiKontrolu = insanmiKontrolu;
        this.harf = kr;
    }

    String karakteriAl() {
        String geciciharf;

        System.out.println("X yada O seçiminizi yapabilirsiniz (Yapmak istemiyorsanýz * giriniz : ");
        geciciharf = input.next();
        if ("*".equals(geciciharf)) {
            return "X";
        }
        return geciciharf;
    }

    char karakterinTersi(char karakter) {
        char harf = 0;
        if (karakter == 'X') {
            harf = 'O';
        }
        if (karakter == 'O') {
            harf = 'X';
        }
        if (karakter == '*') {
            harf = 'O';
        }

        return harf;
    }

    boolean oyuncuTurunuAl() {
        return this.insanmiKontrolu == true;
    }

    String oyuncununHamlesiniAl() {
        return koordinat;
    }

    String insanOyuncuHamlesiniKotrol() {
        System.out.println("Hamle Sýrasý Sizde !!");
        System.out.println("Koordinati Giriniz : ");
        koordinat = input.next();
        return koordinat;
    }

    String bilgisayarHamlesiUret(int n) {

        int satir = rastgele.nextInt(n);
        int sutun = rastgele.nextInt(n);
        while (true) {
            if ("-".equals(oyunTahtasi.oyunTahtasi1[satir][sutun])) {
                return (((satir + 1) + "") + ((sutun + 1) + ""));

            } else {
                satir = rastgele.nextInt(n);
                sutun = rastgele.nextInt(n);
            }

        }
    }

    String bilgisayarHamlesiUret() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}


//oyunTahtasi.java classi


package pro_lab;

public class oyunTahtasi {

    public static String[][] oyunTahtasi1 = new String[7][7];

    oyunTahtasi(int n) {//Oyun tahtasinin deðerlerine baþlangiç olarak R atiyoruz
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                oyunTahtasi1[i][j] = "-";
            }
        }
    }

    oyunTahtasi(char[][] oynTahtasi) {
        oyunTahtasi oyunTahtasi = new oyunTahtasi(oynTahtasi);
    }

    String[][] oyunTahtasiniAl() {
        return oyunTahtasi1;
    }

    void oyunTahtasiniYazdir(int n) {//Oyun tahtasýný ekrana bastýrýyor
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(oyunTahtasi1[i][j] + "  ");
            }
            System.out.println();
        }
    }

    boolean hamleyiYaz(String koordinat, char oyuncu, int n) {

        int gecici5;
        int gecici6;
        gecici5 = Integer.parseInt(koordinat);
        gecici6 = Integer.parseInt(koordinat);
        gecici5 = gecici5 / 10 - 1;
        gecici6 = gecici6 % 10 - 1;
        koordinat = gecici5 + "" + gecici6 + "";

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char gecici1;
                String gecici2 = i + "";
                char gecici3;
                String gecici4 = j + "";
                gecici1 = gecici2.charAt(0);
                gecici3 = gecici4.charAt(0);
                if (koordinat.charAt(0) == gecici1 && koordinat.charAt(1) == gecici3) {
                    if ("-".equals(oyunTahtasi1[i][j])) {
                        if (oyuncu == 'X') {
                            oyunTahtasi1[i][j] = "X";
                        }
                        if (oyuncu == 'O') {
                            oyunTahtasi1[i][j] = "O";
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

     boolean kazanan(char oyuncu, int n) {
        String harf = null;
        if (oyuncu == 'X') {
            harf = "X";
        }
        if (oyuncu == 'O') {
            harf = "O";
        }

        switch (n) {

            case 3:
                for (int i = 0; i < 3; i++) {
                    if (harf.equals(oyunTahtasi1[i][0]) && harf.equals(oyunTahtasi1[i][1]) && harf.equals(oyunTahtasi1[i][2])) {//Satirlari Kontrol Ediyor
                        return true;
                    }
                    if (harf.equals(oyunTahtasi1[0][i]) && harf.equals(oyunTahtasi1[1][i]) && harf.equals(oyunTahtasi1[2][i])) {//Sutunlari Kontrol Ediyor
                        return true;
                    }
                    if (i == 0) {
                        if (harf.equals(oyunTahtasi1[i][i]) && harf.equals(oyunTahtasi1[i + 1][i + 1]) && harf.equals(oyunTahtasi1[i + 2][i + 2])) {//Sol üstten Sað asagiya gider Kosegeni kontrol Ediyor
                            return true;
                        }
                    }
                }
                if (harf.equals(oyunTahtasi1[0][2]) && harf.equals(oyunTahtasi1[1][1]) && harf.equals(oyunTahtasi1[2][0])) {//Sol Altdan Sag ustte giden köþegeni kontrol ediyor
                    return true;
                }
                return false;
            case 5:
                for (int i = 0; i < 5; i++) {
                    if (harf.equals(oyunTahtasi1[i][0]) && harf.equals(oyunTahtasi1[i][1]) && harf.equals(oyunTahtasi1[i][2]) && harf.equals(oyunTahtasi1[i][3]) && harf.equals(oyunTahtasi1[i][4])) {//Satirlari Kontrol Ediyor
                        return true;
                    }
                    if (harf.equals(oyunTahtasi1[0][i]) && harf.equals(oyunTahtasi1[1][i]) && harf.equals(oyunTahtasi1[2][i]) && harf.equals(oyunTahtasi1[3][i]) && harf.equals(oyunTahtasi1[4][i])) {//Sutunlari Kontrol Ediyor
                        return true;
                    }
                    if (i == 0) {
                        if (harf.equals(oyunTahtasi1[i][i]) && harf.equals(oyunTahtasi1[i + 1][i + 1]) && harf.equals(oyunTahtasi1[i + 2][i + 2]) && harf.equals(oyunTahtasi1[i + 3][i + 3]) && harf.equals(oyunTahtasi1[i + 4][i + 4])) {//Sol üstten Sað asagiya gider Kosegeni kontrol Ediyor
                            return true;
                        }
                    }
                }
                if (harf.equals(oyunTahtasi1[0][4]) && harf.equals(oyunTahtasi1[1][3]) && harf.equals(oyunTahtasi1[2][2]) && harf.equals(oyunTahtasi1[3][1]) && harf.equals(oyunTahtasi1[4][0])) {//Sol Altdan Sag ustte giden köþegeni kontrol ediyor
                    return true;
                }
                return false;

            case 7:

                for (int i = 0; i < 7; i++) {
                    if (harf.equals(oyunTahtasi1[i][0]) && harf.equals(oyunTahtasi1[i][1]) && harf.equals(oyunTahtasi1[i][2]) && harf.equals(oyunTahtasi1[i][3]) && harf.equals(oyunTahtasi1[i][4]) && harf.equals(oyunTahtasi1[i][5]) && harf.equals(oyunTahtasi1[i][6])) {//Satirlari Kontrol Ediyor
                        return true;
                    }
                    if (harf.equals(oyunTahtasi1[0][i]) && harf.equals(oyunTahtasi1[1][i]) && harf.equals(oyunTahtasi1[2][i]) && harf.equals(oyunTahtasi1[3][i]) && harf.equals(oyunTahtasi1[4][i]) && harf.equals(oyunTahtasi1[5][i]) && harf.equals(oyunTahtasi1[6][i])) {//Sutunlari Kontrol Ediyor
                        return true;
                    }
                    if (i == 0) {
                        if (harf.equals(oyunTahtasi1[i][i]) && harf.equals(oyunTahtasi1[i + 1][i + 1]) && harf.equals(oyunTahtasi1[i + 2][i + 2]) && harf.equals(oyunTahtasi1[i + 3][i + 3]) && harf.equals(oyunTahtasi1[i + 4][i + 4]) && harf.equals(oyunTahtasi1[i + 5][i + 5]) && harf.equals(oyunTahtasi1[i + 6][i + 6])) {//Sol üstten Sað asagiya gider Kosegeni kontrol Ediyor
                            return true;
                        }
                    }

                }
                if (harf.equals(oyunTahtasi1[0][6]) && harf.equals(oyunTahtasi1[1][5]) && harf.equals(oyunTahtasi1[2][4]) && harf.equals(oyunTahtasi1[3][3]) && harf.equals(oyunTahtasi1[4][2]) && harf.equals(oyunTahtasi1[5][1]) && harf.equals(oyunTahtasi1[6][0])) {//Sol Altdan Sag ustte giden köþegeni kontrol ediyor
                    return true;
                }
                return false;
            default:
                break;
        }
        return false;
    }

    boolean beraberlikKontrol(char oyuncu, int n
    ) {
        int sayac = 0;
        for (int i = 0; i < n; i++) {//Oyun tahtasýnýn tüm elemanlarina bakýyoruz eðer oynanmamýþ eleman varsa sayaci arttiriyoruz
            for (int j = 0; j < n; j++) {
                if ("-".equals(oyunTahtasi1[i][j])) {
                    sayac++;
                }
            }
        }
        if (sayac == 0) { //Eðer sayac 0 ise oynanmamýþ eleman kalmamýþtýr ve hale kazanan çýkmadýðý için oyun
            System.out.println("Oyun Berabere Bitmiþtir");  //berabere bitmiþtir
            return true;
        } else {
            return false;
        }
    }
}

