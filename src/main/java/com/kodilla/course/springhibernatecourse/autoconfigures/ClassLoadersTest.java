package com.kodilla.course.springhibernatecourse.autoconfigures;

public class ClassLoadersTest {

    public static void main(String[] args) {

        //Bootstrap odpowiedzialny jest za załadowanie do JVM kodu natywnego oraz podstawowych bibliotek JRE (czyli na przykład biblioteki rt.jar oraz znaczna część biblioteki java.utils). Pierwotny ClassLoader nie ma nazwy, jeżeli spróbujesz ją wyświetlić, w wyniku zobaczysz null.
        //
        //"Dziećmi" Boostrapu są ClassLoadery rozszerzeń JDK. W nazwie posiadają najczęściej słowo "Ex" (od ang. extension) – odpowiedzialne są między innymi za ładowanie rozszerzeń JDK z podkatalogu lib/ext pakietu JDK.
        //
        //Trzecim poziomem są ClassLoadery aplikacyjne, ich rodzicami są ClassLoadery rozszerzeń. Aplikacyjne ClassLodery posiadają w nazwie najczęściej skrót "Ap". Odpowiedzialne są one za ładowanie klas, które napisałeś w swojej aplikacji.

        System.out.println(ClassLoadersTest.class.getClassLoader());
        System.out.println(Throwable.class.getClassLoader());
    }

}
