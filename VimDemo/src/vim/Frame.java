package vim;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Frame extends JFrame implements KeyListener {

    private File VimFile = new File("Vim.txt");
    private File SavaFile = new File("VimSave.txt");

    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;

    private JTextArea textArea;
    private JTextField cmdField;

    private InputMap keyMatch;
    private ArrayList<String> cmd = new ArrayList<>(30);

    private int offset;
    private int start;
    private int end;
    private boolean pressed = false;
    private boolean changed = false;
    private String originalText;
    private String paste = "";

    private void setMyCursor() {
        textArea.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        textArea.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                JTextArea p = (JTextArea) e.getComponent();
                p.getCaret().setVisible(false);
            }

            public void focusGained(FocusEvent e) {
                JTextArea p = (JTextArea) e.getComponent();
                p.getCaret().setVisible(true);
            }
        });
    }

    private void SetCmdFiled() {
        cmdField.setText("");
        cmd.clear();
    }

    private void MyTimerTask() {
        TimerTask timeTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    textArea.write(new FileWriter(SavaFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timeTask, 60 * 1000L);
    }

    //绑定和解绑h,j,k,l
    private void InputActions() {
        keyMatch = textArea.getInputMap();
        keyMatch.put(KeyStroke.getKeyStroke("typed h"), DefaultEditorKit.backwardAction);
        keyMatch.put(KeyStroke.getKeyStroke("typed j"), DefaultEditorKit.downAction);
        keyMatch.put(KeyStroke.getKeyStroke("typed k"), DefaultEditorKit.upAction);
        keyMatch.put(KeyStroke.getKeyStroke("typed l"), DefaultEditorKit.forwardAction);
    }

    private void RemoveActions() {
        keyMatch = textArea.getInputMap();
        keyMatch.remove(KeyStroke.getKeyStroke("typed h"));
        keyMatch.remove(KeyStroke.getKeyStroke("typed j"));
        keyMatch.remove(KeyStroke.getKeyStroke("typed k"));
        keyMatch.remove(KeyStroke.getKeyStroke("typed l"));
    }

    public Frame() throws IOException {

        setSize(WIDTH, HEIGHT);

        textArea = new JTextArea();
        cmdField = new JTextField();

        if (!VimFile.exists()) {
            VimFile.createNewFile();
        }
        if (!SavaFile.exists()) {
            SavaFile.createNewFile();
        }

        textArea.read(new FileReader(VimFile), "Vim.txt");
        originalText = textArea.getText();

        //设置光标焦点
        setMyCursor();

        //普通模式下无法编辑
        textArea.setEditable(false);
        cmdField.setEditable(false);

        //布局
        add(cmdField, BorderLayout.SOUTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        //键盘监听事件
        addKeyListener(this);
        cmdField.addKeyListener(this);
        textArea.addKeyListener(this);

        //绑定hjkl
        InputActions();

        //定时备份
        MyTimerTask();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //基本操作
        BasicOperation(e);
        //IO模式
        try {
            IO_operation(e);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        //字符串操作
        StringOperation(e);
    }

    public void BasicOperation(KeyEvent e) {
        //Esc
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            textArea.setEditable(false);
            textArea.requestFocus();
            cmdField.setEditable(false);
            InputActions();
            SetCmdFiled();
        }
        //p
        if (!cmdField.isEditable() && !textArea.isEditable() && e.getKeyCode() == KeyEvent.VK_P) {
            if (!paste.equals("")) {
                textArea.insert(paste, textArea.getCaretPosition());
            }
        }
        //yy
        if (!cmdField.isEditable() && !textArea.isEditable() && e.getKeyCode() == KeyEvent.VK_Y) {
            cmd.add("y");
            cmdField.setText(cmdField.getText() + "y");
            if (cmd.size() == 2) {
                try {
                    offset = textArea.getLineOfOffset(textArea.getCaretPosition());
                    start = textArea.getLineStartOffset(offset);
                    end = textArea.getLineEndOffset(offset);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
                try {
                    paste = textArea.getText(start, end - start);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
                SetCmdFiled();
            }
        }
        //dd
        if (!cmdField.isEditable() && !textArea.isEditable() && e.getKeyCode() == KeyEvent.VK_D) {
            cmd.add("d");
            cmdField.setText(cmdField.getText() + "d");
            if (cmd.size() == 2) {
                try {
                    offset = textArea.getLineOfOffset(textArea.getCaretPosition());
                    start = textArea.getLineStartOffset(offset);
                    end = textArea.getLineEndOffset(offset);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
                try {
                    textArea.setText(textArea.getText(0, start) + textArea.getText(end, textArea.getLineEndOffset(textArea.getLineCount() - 1) - end));
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
                SetCmdFiled();
            }
        }
    }

    //IO模式
    public void IO_operation(KeyEvent e) throws IOException {
        if (!textArea.isEditable() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_SEMICOLON) {
            SetCmdFiled();
            cmd.add(":");
            cmdField.setText(":");
            pressed = true;
        }
        //:x
        if (pressed && !cmdField.isEditable() && e.getKeyCode() == KeyEvent.VK_X) {
            FileWriter writer = new FileWriter(VimFile);
            textArea.write(writer);
            writer.flush();
            System.exit(0);
        }
        //:w
        if (pressed && !cmdField.isEditable() && e.getKeyCode() == KeyEvent.VK_W) {
            cmdField.setText(cmdField.getText() + "w");
            textArea.write(new FileWriter(VimFile));
            SetCmdFiled();
            pressed = false;
        }
        //:q
        if (pressed && !cmdField.isEditable() && e.getKeyCode() == KeyEvent.VK_Q) {
            cmd.add("q");
            cmdField.setText(cmdField.getText() + "q");
            if (textArea.getText().equals(originalText)) {
                System.exit(0);
            } else {
                if (!changed) {
                    cmd.clear();
                    changed = true;
                    cmdField.setText("有未保存的改动，是否强制退出？(q!强制退出)");
                }
            }
        }
        //:q!
        if (cmd.size() == 2 && !cmdField.isEditable() && pressed && cmd.get(1).equals("q") && e.getKeyCode() == KeyEvent.VK_1) {
            System.exit(0);
        }
    }

    public void StringOperation(KeyEvent e) {
        if (!textArea.isEditable() && cmd.size() == 0 && e.getKeyCode() == KeyEvent.VK_SLASH) {
            cmd.add("/");
            cmdField.setText(cmdField.getText() + "/");
            cmdField.setEditable(true);
            cmdField.requestFocus();
            RemoveActions();
        }

        if (pressed && e.getKeyCode() == KeyEvent.VK_5) {
            cmd.add("%");
            cmdField.setText(cmdField.getText() + "%");
            cmdField.setEditable(true);
            cmdField.requestFocus();
            RemoveActions();
        }

        // 字符串匹配
        if (!pressed && cmdField.isEditable() && e.getKeyCode() == KeyEvent.VK_ENTER) {
            cmdField.setEditable(false);
            String text = textArea.getText();
            String[] str = cmdField.getText().split("/");
            Pattern p = Pattern.compile(str[1]);
            Matcher m = p.matcher(text);
            if (m.find()) {
                try {
                    int offset = textArea.getLineOfOffset(m.start());
                    cmdField.setText(new String(String.valueOf(offset + 1)));
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            } else cmdField.setText("Not Find!");
            textArea.requestFocus();
            InputActions();
        }
        //字符串替换
        if (pressed && cmdField.isEditable() && e.getKeyCode() == KeyEvent.VK_ENTER) {
            cmdField.setEditable(false);
            String text = textArea.getText();
            String[] str = cmdField.getText().split("/");
            Pattern p = Pattern.compile(str[1]);
            Matcher m = p.matcher(text);
            if (m.find()) {
                text = m.replaceAll(str[2]);
                textArea.setText(text);
                cmdField.setText("Finished!");
            } else cmdField.setText("Not Find!");
            textArea.requestFocus();
            InputActions();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!cmdField.isEditable() && !textArea.isEditable() && e.getKeyCode() == KeyEvent.VK_I) {
            RemoveActions();
            textArea.setEditable(true);
        }
    }
}
