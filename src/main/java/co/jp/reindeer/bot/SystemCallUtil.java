package co.jp.reindeer.bot;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class SystemCallUtil {
    // TODO:Correspond to multiple arguments
    public static void execute(String executeCommand, String commandArgument) {
        String[] Command = { "cmd", "/c", executeCommand, commandArgument};
        System.out.println(executeCommand);
        System.out.println(commandArgument);
        try {
            Runtime.getRuntime().exec(Command);
        } catch (IOException e) {
            log.warn("", e);
        }
    }
}
