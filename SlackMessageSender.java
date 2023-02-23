import java.sql.Connection;

public class SlackMessageSender {
    public void SuccessSender() {
        Database db = new Database();
        Connection conn = db.connectToDB("database", "postgres", "soil467seamwall");
        String email = db.readEmail(conn, "users");

        SlackUtils slackUtils = new SlackUtils();
        SlackMessage slackMessage = SlackMessage.builder()
                .channel("Storm")
                .username("Bot1")
                .text("User with email "+email+" have entered.")
                .icon_emoji(":twice:")
                .build();
        slackUtils.sendMessage(slackMessage);
    }

    public void FailSender() {
        Database db = new Database();
        Connection conn = db.connectToDB("database", "postgres", "soil467seamwall");
        String email = db.readEmail(conn, "users");

        SlackUtils slackUtils = new SlackUtils();
        SlackMessage slackMessage = SlackMessage.builder()
                .channel("Storm")
                .username("Bot1")
                .text("User with email "+email+" failed to enter.")
                .icon_emoji(":twice:")
                .build();
        slackUtils.sendMessage(slackMessage);
    }
}