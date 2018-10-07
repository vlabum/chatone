package ru.vlabum.chatone.client.gui;

import lombok.Getter;
import ru.vlabum.chatone.client.event.ClientCommandInputEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.swing.*;
import java.awt.*;


/**
 * Окно чата
 */
@Getter
@ApplicationScoped
public class ChatWindow extends JFrame {

    private Event<ClientCommandInputEvent> clientCommandInputEvent;

    private final JTextArea textAreaChat = getTextAreaChat("Mental Chat");
    private final JScrollPane textAreaChatScroll = new JScrollPane(textAreaChat);
    private final JTextField textFieldSend = getTextFieldSend();

    public ChatWindow(Event<ClientCommandInputEvent> clientCommandInputEvent) {

        this.clientCommandInputEvent = clientCommandInputEvent;

        setTitle("Mental Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        final JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BoxLayout(sendPanel, BoxLayout.X_AXIS));
        sendPanel.add(textFieldSend);
        final JButton buttonSend = getButtonSend();
        sendPanel.add(buttonSend);

        final JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(textAreaChatScroll, BorderLayout.CENTER);
        panel.add(sendPanel, BorderLayout.SOUTH);
        add(panel);
    }

    /**
     * Создание кнопки, которая отсылает сообщение
     * @return - ссылка на созданный объект JButton
     */
    private JButton getButtonSend() {
        final JButton button = new JButton("Send");
        button.addActionListener(event -> send());
        return button;
    }

    /**
     * Создание общего поля чата
     * @param caption - приветствие
     * @return - ссылка на созданный объект JTextArea
     */
    private JTextArea getTextAreaChat(String caption) {
        final JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.append(caption);
        textArea.setMargin(new Insets(5,5,5,5));
        return textArea;
    }

    /**
     * Создание поля для ввода сообщений
     * @return - ссылка на созданный объект JTextField
     */
    private JTextField getTextFieldSend() {
        final JTextField textField = new JTextField();
        textField.addActionListener(event -> send());
        return textField;
    }

    /**
     * Отсылка сообщения
     */
    private void send() {
        final String message = textFieldSend.getText();
        if (message.isEmpty()) {
            textFieldSend.requestFocus();
            return;
        }
        textFieldSend.setText("");
        textFieldSend.requestFocus();
        clientCommandInputEvent.fireAsync(new ClientCommandInputEvent(message));
    }

    /**
     * Выводим пользователю сообщения, пришедшие с сервера
     */
    public void appendMessages(final String message) {
        try {
            textAreaChat.append("\n" + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
