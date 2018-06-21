package co.jp.reindeer.bot;

import lombok.extern.slf4j.Slf4j;
import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class ReindeerBot extends Bot {

    @Value("${slackBotToken}")
    private String slackToken;

    @Value("${slackBotUser}")
    private String slackBotUser;

    @Value("${slackWebhookUrl}")
    private String slackWebhookUrl;

    @Value("${targetWindowName}")
    private String targetWindowName;

    @Value("${bgmFilePath}")
    private String bgmFilePath;

    @Value("${executeCommand}")
    private String executeCommand;

    @Value("${commandArgument}")
    private String commandArgument;

    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    @PostConstruct
    private void init() {
        System.setProperty("java.awt.headless", "false");
    }

    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE}, pattern = "update")
    public void onReceiveUpdate(WebSocketSession session, Event event) throws InterruptedException {
        setFocusWindow(targetWindowName);
        Thread.sleep(2000);
        playSound();
        keyPress(KeyEvent.VK_F5);
    }

    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE}, pattern = "exec")
    public void onReceiveExec(WebSocketSession session, Event event) throws InterruptedException {
        playSound();
        SystemCallUtil.execute(executeCommand, commandArgument);
    }

    private String replaceUserIdToBlank(String input) {
        return input.replaceAll("\\<.*\\> ", "");
    }

    private void reply(String message, String channel) {
        SlackMessage slackMessage = new SlackMessage();
        slackMessage.setIcon(":dog2:");
        slackMessage.setChannel(channel);
        slackMessage.setText(message);
        SlackApi api = new SlackApi(slackWebhookUrl);
        api.call(slackMessage);
    }

    private void setFocusWindow(String windowName) {
        boolean result = Win32ApiUtil.User32.INSTANCE.SetForegroundWindow(
                Win32ApiUtil.User32.INSTANCE.FindWindow(null, windowName)
        );
    }

    private void keyPress(int key) {
        try {
            Robot robot = new Robot();
            robot.keyPress(key);
            robot.keyRelease(key);
        } catch (AWTException e) {
            log.warn("", e);
        }
    }

    private void playSound() {
        try (AudioStream as = new AudioStream(new FileInputStream(bgmFilePath))) {
            AudioPlayer.player.start(as);
        } catch (IOException e) {
            log.warn("", e);
        }
    }
}
