/**
 * Software Developement 2 (SD2)
 * Final Project - Chatbot
 * by Cody Gaffney & Ivan Branimir Skoric
 *
 * Main template of chatbot as taken from:
 * https://howtodoinjava.com/java/library/java-aiml-chatbot-example/
 * using program-ab AIML and OpenWeatherMaps API
 *
 */

import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

public class Chatbot {

    private static final boolean TRACE_MODE = false;
    static String botName = "super";

    public static void main(String[] args) {
        try {

            //Gets resource path
            String resourcesPath = getResourcesPath();
            System.out.println(resourcesPath);
            MagicBooleans.trace_mode = TRACE_MODE;
            //Create a new bot object
            Bot bot = new Bot("super", resourcesPath);
            Chat chatSession = new Chat(bot);
            bot.brain.nodeStats();
            String textLine = "";

            while (true) {
                //Gets user chat input
                System.out.print("Human : ");
                textLine = IOUtils.readInputTextLine();
                //If no input the bot will use a random null response
                if ((textLine == null) || (textLine.length() < 1))
                    textLine = MagicStrings.null_input;
                //q to close the bot
                if (textLine.equals("q")) {
                    System.exit(0);
                //wq saves the chat session with the bot
                } else if (textLine.equals("wq")) {
                    bot.writeQuit();
                    System.exit(0);
                //Bot reads the line and respondes based on the AIML
                } else {
                    String request = textLine;
                    if (MagicBooleans.trace_mode)
                        System.out.println(
                                "STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
                                        + ":TOPIC=" + chatSession.predicates.get("topic"));
                    String response = chatSession.multisentenceRespond(request);
                    while (response.contains("&lt;"))
                        response = response.replace("&lt;", "<");
                    while (response.contains("&gt;"))
                        response = response.replace("&gt;", ">");
                    System.out.println("Robot : " + response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }

}
