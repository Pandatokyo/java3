package src.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientHandler {
    private Scanner in;
    private PrintWriter out;
    private final BaseAuthService authService = new BaseAuthService();
    private static List<ClientHandler> clients = new ArrayList<>();
    private String nick;
    private Socket socket;


    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run(ClientHandler clientHandler) {
        new Thread(() -> {
            System.out.println("Новый клиент подлючился");
            auth(clientHandler);
            while (socket.isConnected()) {
                String s;
                if (in.hasNext()) {
                    s = in.nextLine();
                    if (s.startsWith("/w")) {
                        sendPrivateMessage(s);
                    } else {
                        if (s.equals("/exit"))
                            unsubscribe(this);
                        if (!s.isEmpty())
                            sendBroadcastMessage(nick + " : " + s);
                    }
                }
            }

        }).start();
    }

    private void sendPrivateMessage(String msg) {
        String[] commands = msg.split(" ");
        String msgForNick = commands[1];
        if (hasNick(msgForNick)) {
            int length = commands.length - 2;
            String[]  message = new String[length];
            System.arraycopy(commands, 2, message, 0, length);
            sendToNick(msgForNick, String.join(" ", message));
        }
    }

    private void sendToNick(String nick, String msg) {
        for (ClientHandler client : clients) {
            if (client.nick.equals(nick)) {
                String str = "From " + nick + ": " + String.join(" ", msg);
                client.out.println(str);
            }
        }
    }

    private boolean hasNick(String msgForNick) {
        for (ClientHandler client : clients) {
            if (client.nick.equals(msgForNick)) {
                return true;
            }
        }
        return  false;
    }

    private void auth(ClientHandler clientHandler) {
        while (true) {
            if (!in.hasNextLine()) continue;
            String s = in.nextLine();
            if (s.startsWith("/auth")) {
                String[] commands = s.split(" ");
                if (commands.length >= 3) {
                    String login = commands[1];
                    String password = commands[2];
                    String nick = authService.authByLoginAndPassword(login, password);
                    if (nick == null) {
                        String msg = "Invalid login or password";
                        System.out.println(msg);
                        out.println(msg);
                    } else if (isNickTaken(nick)) {
                        String msg = nick + " already taken!";
                        System.out.println(msg);
                        out.println(msg);
                    } else {
                        this.nick = nick;
                        out.println("ok");
                        subscribe(clientHandler);
                        break;
                    }
                } else {
                    out.println("wrong command");
                }
            }
        }
    }

    public boolean isNickTaken(String nick) {
        for (ClientHandler client : clients) {
            if (nick.equals(client.getNick()))
                return true;
        }
        return false;
    }

    public void subscribe(ClientHandler clientHandler) {
        String msg = clientHandler.getNick() + " connected";
        sendBroadcastMessage(msg);
        System.out.println(msg);
        clients.add(clientHandler);
    }
    public void
    unsubscribe(ClientHandler clientHandler) {
        String msg = clientHandler.getNick() + " disconnected";
        sendBroadcastMessage(msg);
        System.out.println(msg);
        clients.remove(clientHandler);
    }

    public String getNick() {
        return nick;
    }

    public static void sendBroadcastMessage(String msg) {
        for (ClientHandler client : clients) {
            client.out.println(msg);
        }
    }
    public synchronized void broadcastClientsList() {
        StringBuilder sb = new StringBuilder("/clients ");
        for (ClientHandler o : clients) {
            sb.append(o.getNick()).append(" ");
        }
        sendBroadcastMessage(sb.toString());
    }




}
