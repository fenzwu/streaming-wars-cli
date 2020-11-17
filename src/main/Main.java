import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {

        // Proxy action handler invocations so we can log and authorize in one place
        ActionProxy actionProxy = new ActionProxy();
        IActions proxyInstance = (IActions) Proxy.newProxyInstance(IActions.class.getClassLoader(),
                new Class[] { IActions.class },
                actionProxy);

        TextCommandProcessor parser = new TextCommandProcessor(proxyInstance);
        parser.processCommands();

    }
}
