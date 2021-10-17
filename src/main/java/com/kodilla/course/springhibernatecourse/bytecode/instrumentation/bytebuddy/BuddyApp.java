package com.kodilla.course.springhibernatecourse.bytecode.instrumentation.bytebuddy;

import java.lang.reflect.InvocationTargetException;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class BuddyApp {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        /**
         * Spróbujemy teraz w sposób dynamiczny utworzyć nową klasę (która będzie "mutacją" klasy Book) – oczywiście bez pisania jej kodu źródłowego.
         * Deskryptor typu opisującego tę klasę przypiszemy do zmiennej dynamicBookClass,
         * która będzie typu Class<?> (czyli będzie deskryptorem dowolnej klasy, gdyż ? oznacza tutaj dowolną klasę).
         * Klasa ByteBuddy posiada odpowiedni builder, którego tutaj użyjemy. Rozpoczniemy w następujący sposób:
         */

        Class<?> dynamicComicBookClass = new ByteBuddy() // Tworzymy obiekt klasy ByteBuddy (który pełni funkcję buildera naszego nowotworzonego typu).
                .subclass(ComicBook.class) //wywołujemy na nim metodę subclass(...), która informuje builder o tym, że klasa ma powstać na bazie klasy ComicBook
                .method(ElementMatchers.named("toString")) //służącej do odfiltrowania metod pasujących do wzorca (użyjemy porównania po nazwie)
                .intercept(FixedValue.value("Hello my Buddy!")) //służącej do zmiany zachowania (implementacji) metody.
                .make() //zakończenia naszego buildera
                .load(ComicBook.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER) //załadujemy tę definicję klasy do JVM przy pomocy ClassLoadera, który związany jest z klasą Book
                .getLoaded();

        Class[] parameterTypes = { String.class, String.class, int.class };

        System.out.println(dynamicComicBookClass.getDeclaredConstructor(parameterTypes)
                .newInstance("title", "author", 2010));
    }

}
