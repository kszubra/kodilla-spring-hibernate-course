package com.kodilla.course.springhibernatecourse.bytecode.reflaction;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectiontestPrivate {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Book book = new Book("Title", "Signature", 2003);

        //uzyskamy dostęp do referencji do prywatnego pola, na podstawie jego nazwy:
        Field signatureField = Book.class.getDeclaredField("signature");

        //Następnie musimy wyłączyć zabezpieczenia (prywatnego pola) poprzez ustawienie setAccessible(true) na deskryptorze pola.
        //Na zakończenie pozostaje nam jedynie odczytać zawartość pola.
        // Musimy jeszcze odczytaną wartość zrzutować na typ String (informację o typie pola możemy również odczytać z deskryptora pola)
        signatureField.setAccessible(true);

        String value = (String)signatureField.get(book);
        System.out.println(value);

        //uzyskajmy jeszcze możliwość wywołania prywatnej metody.
        Method rentBookMethod = Book.class.getDeclaredMethod("rentBook", null);

        //W efekcie powyższej linii uzyskaliśmy deskryptor metody (pamiętaj o dodaniu nowych wyjątków do sygnatury metody).
        // Teraz nadszedł czas na jej wywołanie, nim jednak to zrobimy, musimy wyłączyć mechanizmy bezpieczeństwa dla tej metody (w sensie prywatności dostępu):
        rentBookMethod.setAccessible(true);
        boolean result = (boolean) rentBookMethod.invoke(book);

        //Teraz jeszcze drobna modyfikacja metody, aby spróbować wywołać metodę, która przyjmuje jakieś parametry – będzie to metoda setDetails
        Method setDetailsMethod
                = Book.class.getDeclaredMethod("setDetails", new Class[]{String.class, int.class});

        setDetailsMethod.setAccessible(true);
        setDetailsMethod.invoke(book, "new Signature", 1990);
        System.out.println(book.getSignature());
        System.out.println(book.getYear());

    }

}
