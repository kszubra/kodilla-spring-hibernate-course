package com.kodilla.course.springhibernatecourse.bytecode.reflaction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        //Obiekt deskryptora umożliwia nam dostęp do wielu metadanych
        // opisujących klasę, na przykład do list pól, listy metod, listy konstruktorów, do nazwy klasy i tak dalej
        Class<Book> descriptor = Book.class;

        //Metoda getMethods() deskryptora klasy zwraca tablicę obiektów typu
        // Method, które też są deskryptorami, tyle że nie klasy a metod – te deskryptory również udostępniają wiele przydatnych informacji o metodach
        for (Method method : descriptor.getMethods()) {
            System.out.println(method.getName());
        }

        //Możemy również sprawdzić, jakie akcesory zastosowano dla klasy – w tym celu użyjemy metody getModifiers()
        //Zwraca ona szereg flag zakodowanych w postaci bitowej opisujących wszystkie modyfikatory,
        // jakie zastosowano dla klasy, na przykład abstract, static, ale także i akcesory, czyli private, public itd.
        // Do zdekodowania konkretnej interesującej nas informacji możemy użyć metod klasy Modifier,
        // których jest całkiem sporo i dostarczają nam pełnej informacji o wszystkich użytych (bądź nie) modyfikatorach klasy
        int modifiers = descriptor.getModifiers();
        boolean isPublic = Modifier.isPublic(modifiers);
        boolean isAbstract = Modifier.isAbstract(modifiers);

        System.out.println(isPublic);
        System.out.println(isAbstract);

        //Możemy dowiedzieć się znacznie więcej o naszej klasie, na przykład, jakie implementuje interfejsy
        // (przy pomocy metody getInterfaces()), czy też, jaką inną klasę rozszerza (przy pomocy metody getSuperClass()).
        Constructor<Book> constructor = descriptor.getConstructor(new Class[]{String.class, String.class, int.class});
        Book book = constructor.newInstance("Title", "Signature", 2000);
        System.out.println(book.getTitle());
    }

}
