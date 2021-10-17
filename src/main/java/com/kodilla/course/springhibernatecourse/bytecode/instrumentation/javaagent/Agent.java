package com.kodilla.course.springhibernatecourse.bytecode.instrumentation.javaagent;

import java.lang.instrument.Instrumentation;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.Default;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

/**
 * W naszym przypadku uruchomienie jakiegoś programu będzie wyglądało następująco:
 *
 * java -javaagent:kodilla-agent.jar MainClass
 * Gdzie MainClass jest klasą, którą chcemy uruchomić. Agent Javy musi mieć postać pliku .jar, w którym znajduje się klasa agenta, która musi zawierać dwie metody: agentmain oraz premain. Metoda premain agenta będzie wykonana przed wywołaniem metody main programu, który chcesz uruchomić. Dodatkowo agent Javy musi posiadać plik MANIFEST.MF, w którym wskazane będą parametry:
 *
 * Premain-Class – nazwa klasy zawierającej metodę premain
 * Agentmain-Class – nazwa klasy zawierającej metodę agentmain
 * Can-Redefine-Classes – flaga logiczna mówiąca o tym, czy agent ma uprawnienia do modyfikowania kodu bajtowego klas
 * Can-Retransform-Classes – flaga mówiąca o tym, czy zmodyfikowane klasy mogą być zmieniane dalej przez kolejne agenty (agenty można łączyć kaskadowo)
 * Jeżeli metoda premain ma otrzymywać jakieś parametry, podajemy je w wywołaniu po znaku równości za nazwą agenta:
 *
 * java -agent=<agent_name.jar>[=parameters] MainClass
 */

public class Agent {

    /**
     *Jedną z metod, której użyjemy, jest metoda addTransformer, która pozwala na zmodyfikowanie kodu klasy. Metoda ta przyjmuje parametr typu ClassFileTransformer, który jest interfejsem, my jako autorzy agenta musimy ten interfejs odpowiednio zaimplementować.
     * Agenty mogą być uruchamiane nie tylko jako procesy wrappujące naszą aplikację podczas uruchamiania. Agent może również podłączyć się (ang. attach) do procesu, który jest już uruchomiony na maszynie JVM – w ten sposób działają na przykład debuggery podłączające się do aplikacji na zdalnych maszynach. Metoda agentmain używana jest (i wymagana) w trybie podłączania aplikacji do istniejącego procesu, natomiast metoda premain używana jest (i wymagana) podczas wrappowania uruchamiania naszej aplikacji.
     */

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("Kodilla agent is running.");
        System.out.println(
                "Is redefine classes allowed: " + instrumentation.isRedefineClassesSupported());
        System.out.println(
                "Is retransform classes allowed: " + instrumentation.isRetransformClassesSupported());

        AgentBuilder agentBuilder = new Default()
                .type(ElementMatchers.nameStartsWith("com.kodilla"))
                .transform(((builder, typeDescription, classLoader, module) -> {
                    System.out.println("Class " + typeDescription);
                    return builder.visit(Advice.to(MyMethodMonitor.class).on(ElementMatchers.any()));
                }));

        agentBuilder.installOn(instrumentation);

    }

    public static void agentmain(String args, Instrumentation instrumentation) {
        premain(args, instrumentation);
    }
}
