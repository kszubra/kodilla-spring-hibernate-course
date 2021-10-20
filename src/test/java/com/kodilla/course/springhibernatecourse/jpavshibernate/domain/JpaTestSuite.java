package com.kodilla.course.springhibernatecourse.jpavshibernate.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaTestSuite {

    @PersistenceUnit
    private EntityManagerFactory emf;

    /**
     * test tworzy przy użyciu jednostki persystencji obiekt reprezentujący "sesję" w ramach połączenia z bazą danych, czyli obiekt klasy EntityManager.
     * Klasa ta pozwala na manipulowanie danymi w bazie danych – wykonywanie zapytań, zapytań typu native, a także udostępnia metody persist(Entity e)
     * oraz merge(Entity e), które pozwalają zapisać obiekty w bazie danych. Jak wspomnieliśmy, EntityManager powiązany jest z cache poziomu 1 tej sesji,
     * więc aby mieć pewność, że dane zostały "wypchnięte" z cache do bazy danych możemy użyć metody flush(), która "zrzuca" zawartość cache do
     * bazy danych bez czekania na cykl synchronizacji.
     */

    @Test
    void shouldPersistCustomer() {
        //Given
        EntityManager em = emf.createEntityManager();
        Customer kodilla =
                new Customer(null, "Kodilla", "ul. Racławicka 13", "Wrocław");

        //When
        em.getTransaction().begin();
        em.persist(kodilla);
        em.flush();
        em.getTransaction().commit();

        //Then
        Long key = kodilla.getId();
        Customer readCustomer = em.find(Customer.class, key);
        em.close();
        assertEquals(readCustomer.getName(), kodilla.getName());
    }

}
