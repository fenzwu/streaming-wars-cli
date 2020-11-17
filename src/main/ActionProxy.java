import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;

public class ActionProxy implements InvocationHandler {

    private ActionHandler originalHandler;
    private HashSet<String> commands;
    private HashSet<String> readerCommands;
    private HashSet<String> editorCommands;
    private HashSet<String> adminCommands;

    ActionProxy() {
        this.originalHandler = new ActionHandler();
        this.commands = new HashSet<>(Arrays.asList("onLogin","onLogout"));
        this.readerCommands = new HashSet<>(Arrays.asList("onDisplayDemo","onDisplayStream","onDisplayStudio","onDisplayEvents", "onDisplayOffers","onDisplayTime","onDisplayActiveUser","onChangePassword"));
        this.editorCommands = new HashSet<>(Arrays.asList("onCreateDemographic","onCreateStudio","onCreateEvent","onCreateService","onOfferMovie","onOfferPPV","onWatchEvent","onNextMonth","onUpdateDemo","onUpdateService","onUpdateEvent","onRetractMovie"));
        this.adminCommands = new HashSet<>(Arrays.asList("onCreateUser","onDisplayLogs","onChangeRole","onClearLogs"));

        this.readerCommands.addAll(this.commands);
        this.editorCommands.addAll(this.readerCommands);
        this.adminCommands.addAll(this.editorCommands);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Object result = null;
        HashMap<String, String> userInfo = originalHandler.onDisplayActiveUser();

        // Log the command
        logCommand(method, args, userInfo);

        // Invoke real method and throw original exceptions
        if (isAuthorized(method, userInfo)){
            try {
                result = method.invoke(originalHandler, args);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        }
        return result;
    }

    private void logCommand(Method method, Object[] args, HashMap<String, String> userInfo) {
        String username = userInfo.get("username");
        String message = method.getName();

        if (args != null && args.length >= 1) {
            message += "," + String.valueOf(args[0]);

            for (int i = 1; i < args.length; i++) {
                message += "," + String.valueOf(args[i]);
            }
        }

        originalHandler.log(username, message);
    }

    private Boolean isAuthorized(Method method, HashMap<String, String> userInfo) throws IllegalStateException{
        String role = userInfo.get("role");
        String message = method.getName();
        if (role.equals(RoleType.READER.toString())){
            if (this.readerCommands.contains(message)){
                return true;
            } else{
                throw new IllegalStateException("Reader does not have access to this command!");
            }
        } else if (role.equals(RoleType.EDITOR.toString())){
            if (this.editorCommands.contains(message)){
                return true;
            } else{
                throw new IllegalStateException("Editor does not have access to this command!");
            }
        } else if (role.equals(RoleType.ADMIN.toString())){
            if (this.adminCommands.contains(message)){
                return true;
            } else{
                throw new IllegalStateException("Admin does not have access to this command!");
            }
        } else {
            if (this.commands.contains(message)){
                return true;
            } else{
                throw new IllegalStateException("Please log in!");
            }
        }

    }
}
