package org.drools.marshalling;

import java.io.ByteArrayOutputStream;

import org.drools.KnowledgeBase;
import org.drools.ProviderInitializationException;


/**
 * <p>
 * The MarshallerFactory is used to marshal and unmarshal StatefulKnowledgeSessions. At the simplest it can be used as follows:
 * </p>
 * <pre>
 * // ksession is the StatefulKnowledgeSession
 * // kbase is the KnowledgeBase
 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
 * Marshaller marshaller = MarshallerFactory.newMarshaller( kbase );
 * marshaller.marshall( baos, ksession );
 * baos.close();
 * </pre>
 * 
 * <p>
 * However with marshalling you need more flexibility when dealing with referenced user data. To achieve this we have the 
 * ObjectMarshallingStrategy interface. Two implementations are provided, but the user can implement their own. The two
 * supplied are IdentityMarshallingStrategy and SerializeMarshallingStrategy. SerializeMarshallingStrategy is the default, as used
 * in the example above and it just calls the Serializable or Externalizable methods on a user instance. IdentityMarshallingStrategy
 * instead creates an int id for each user object and stores them in a Map the id is written to the stream. When unmarshalling
 * it simply looks to the IdentityMarshallingStrategy map to retrieve the instance. This means that if you use the IdentityMarshallingStrategy
 * it's stateful for the life of the Marshaller instance and will create ids and keep references to all objects that it attempts to marshal.
 * Here is he code to use a IdentityMarshallingStrategy.
 * </p>
 * <pre>
 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
 * Marshaller marshaller = MarshallerFactory.newMarshaller( kbase, new ObjectMarshallingStrategy[] { MarshallerFactory.newIdentityMarshallingStrategy() } );
 * marshaller.marshall( baos, ksession );
 * baos.close();
 * </pre>

 * <p>
 * For added flexability we can't assume that a single strategy is suitable for this we have added the ObjectMarshallingStrategyAcceptor interface that each 
 * ObjectMarshallingStrategy has. The Marshaller has a chain of strategies and when it attempts to read or write a user object it iterates the strategies asking
 * if they accept responsability for marshalling the user object. One one implementation is provided the ClassFilterAcceptor. This allows strings and wild cards
 * to be used to match class names. The default is "*.*", so in the above the IdentityMarshallingStrategy is used which has a default "*.*" acceptor.
 * </p>
 * 
 * <p>
 * But lets say we want to serialise all classes except for one given package, where we will use identity lookup, we could do the following:
 * </p>
 * <pre>
 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
 * ObjectMarshallingStrategyAcceptor identityAceceptor = MarshallerFactory.newClassFilterAcceptor( new String[] { "org.domain.pkg1.*" } );
 * ObjectMarshallingStrategy identityStratetgy = MarshallerFactory.newIdentityMarshallingStrategy( identityAceceptor );
 * Marshaller marshaller = MarshallerFactory.newMarshaller( kbase, new ObjectMarshallingStrategy[] { identityStratetgy, MarshallerFactory.newSerializeMarshallingStrategy() } );
 * marshaller.marshall( baos, ksession );
 * baos.close();
 * </pre>
 * 
 * <p>
 * Not that the acceptance checking order is in the natural order of the supplied array.
 * </p>
 *
 */
public class MarshallerFactory {
    private static volatile MarshallerProvider provider;

    public static ObjectMarshallingStrategyAcceptor newClassFilterAcceptor(String[] patterns) {
        return getMarshallerProvider().newClassFilterAcceptor( patterns );
    }

    public static ObjectMarshallingStrategy newIdentityMarshallingStrategy() {
        return getMarshallerProvider().newIdentityMarshallingStrategy();
    }

    public static ObjectMarshallingStrategy newIdentityMarshallingStrategy(ObjectMarshallingStrategyAcceptor acceptor) {
        return getMarshallerProvider().newIdentityMarshallingStrategy( acceptor );
    }

    public static ObjectMarshallingStrategy newSerializeMarshallingStrategy() {
        return getMarshallerProvider().newSerializeMarshallingStrategy();
    }

    public static ObjectMarshallingStrategy newSerializeMarshallingStrategy(ObjectMarshallingStrategyAcceptor acceptor) {
        return getMarshallerProvider().newSerializeMarshallingStrategy( acceptor );
    }

    /**
     * Default uses the serialise marshalling strategy.
     * @return
     */
    public static Marshaller newMarshaller(KnowledgeBase kbase) {
        return getMarshallerProvider().newMarshaller( kbase );
    }

    public static Marshaller newMarshaller(KnowledgeBase kbase,
                                           ObjectMarshallingStrategy[] strategies) {
        return getMarshallerProvider().newMarshaller( kbase,
                                                      strategies );
    }

    private static synchronized void setMarshallerProvider(MarshallerProvider provider) {
        MarshallerFactory.provider = provider;
    }

    private static synchronized MarshallerProvider getMarshallerProvider() {
        if ( provider == null ) {
            loadProvider();
        }
        return provider;
    }

    private static void loadProvider() {
        try {
            Class<MarshallerProvider> cls = (Class<MarshallerProvider>) Class.forName( "org.drools.marshalling.impl.MarshallerProviderImpl" );
            setMarshallerProvider( cls.newInstance() );
        } catch ( Exception e2 ) {
            throw new ProviderInitializationException( "Provider org.drools.marshalling.impl.MarshallerProviderImpl could not be set.",
                                                       e2 );
        }
    }
}
