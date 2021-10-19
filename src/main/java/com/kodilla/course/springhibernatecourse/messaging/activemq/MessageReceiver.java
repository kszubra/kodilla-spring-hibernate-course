package com.kodilla.course.springhibernatecourse.messaging.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    /**
     * @JmsListener przyjmuje kilka parametrów – my użyliśmy dwóch spośród nich:
     * containerFactory – nazwa beana, którego utworzyliśmy w klasie konfiguracyjnej przechowującego referencję do obiektu typu JmsListenerContainerFactory. Jak zapewne pamiętasz, bean tworzony w klasie konfiguracyjnej przyjmuje taką nazwę, jaką ma metoda go tworząca. Stąd jmsFactory – tak właśnie nazywa się nasz bean.
     * destination – nazwa kolejki, na której dany listener ma nasłuchiwać. Nasza kolejka nazywa się queue-test – w ten sposób nazwaliśmy ją w miejscu wywołania metody z klasy JmsTemplate w kontrolerze.
     */
    @JmsListener(containerFactory = "jmsFactory", destination = "queue-test")
    public void receive(String message) {
        System.out.println("Received the message: " + message);
    }

}
