import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class NameListEditor extends JFrame implements ActionListener
    {
        private TextArea textArea = new TextArea("", 0,0, TextArea.SCROLLBARS_VERTICAL_ONLY);
        private MenuBar  menuBar = new MenuBar(); // Creates the Menu Bar
        private JToolBar toolBar = new JToolBar();

        private Menu file = new Menu(); // Creates the File Menu
        private MenuItem openFile = new MenuItem(); // an open option
        private MenuItem saveFile = new MenuItem(); // a save option
        private MenuItem close = new MenuItem(); // a close option
        private JButton analyzeButton = new JButton("Analyze Spaces");
        private JButton removeButton = new JButton("Remove Spaces");

        public NameListEditor()
        {
            this.setSize(600,600); // sets the initial size of the window
            this.setTitle("Space Invaders"); // sets the title of the window
            setDefaultCloseOperation(EXIT_ON_CLOSE); // sets the default close operation (exit when closed)
            this.textArea.setFont(new Font("Arial", Font.BOLD, 12)); // sets default font
            this.getContentPane().setLayout(new BorderLayout()); // BorderLayout makes the window fill automatically
            this.getContentPane().add(textArea);
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo.jpg")));

            this.setMenuBar(this.menuBar); // adds the menu bar into the GUI
            this.menuBar.add(this.file);

            this.file.setLabel("File");

            this.openFile.setLabel("Open");
            this.openFile.addActionListener(this); // adds an action listener (so we know when the button is clicked)
            this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false)); // sets a keyboard shortcut
            this.file.add(this.openFile); // adds option to the "File" menu

            this.saveFile.setLabel("Save");
            this.saveFile.addActionListener(this);
            this.saveFile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
            this.file.add(this.saveFile);

            this.close.setLabel("Close");
            this.close.addActionListener(this);
            this.close.setShortcut(new MenuShortcut (KeyEvent.VK_F4, false));
            this.file.add(this.close);

            this.add(toolBar, BorderLayout.NORTH);
            this.toolBar.setBackground(new Color(0, 197, 58));
            this.toolBar.add(analyzeButton);
            this.analyzeButton.addActionListener(this);
            this.analyzeButton.setBackground(new Color(254, 214, 20));
            this.toolBar.add(removeButton);
            this.removeButton.addActionListener(this);
            this.removeButton.setBackground(new Color(254, 214, 20));
        }

        String finishedContents;

        public void buildFile()
        {
            String contents = textArea.getText();
            String [] contentsLines = contents.split("\r\n");
            StringBuffer scannedContents = new StringBuffer();
            for (String line : contentsLines)
            {
                scannedContents.append(line.replaceAll(" +", " ").trim() + "\r\n");
            }
            finishedContents = scannedContents.toString();
            textArea.setText(finishedContents);
        }

        public void saveFile()
        {
            JFileChooser save = new JFileChooser();
            int option = save.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
                    out.write(finishedContents);
                    out.close();
                } catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }

        public void actionPerformed (ActionEvent e) {
            if (e.getSource() == this.close) // if the source of the event was to "close"
                this.dispose(); // dispose of all resources and closes application

            else if (e.getSource() == this.openFile) // if the source of the event was to "open"
            {
                JFileChooser open = new JFileChooser(); // opens up a file chooser
                int option = open.showOpenDialog(this); // get the option that the user selected. use showOpenDialog because it's OPENing a file
                if (option == JFileChooser.APPROVE_OPTION) {
                    this.textArea.setText(""); // clears TextArea before applying file contents
                    try // creates a scanner to read the file (getSelectedFile().getPath() will get the path of the file)
                    {
                        Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
                        while (scan.hasNext()) // while there is something to read
                            this.textArea.append(scan.nextLine() + "\r\n");
                    } catch (Exception ex) // catches any exceptions and prints out to the console
                    {
                        System.out.println(ex.getMessage());
                    }
                }
            } else if (e.getSource() == this.saveFile) // if the source of the event was to "save"
            {
                buildFile();
                saveFile();
            }
            else if(e.getSource() == this.analyzeButton)
            {
                String contents = textArea.getText();
                Integer spaceCounter = 0;
                Pattern p = Pattern.compile("\\s+[ ]");
                Matcher m = p.matcher(contents);
                while(m.find())
                {
                    spaceCounter++;
                }
                int confirm = JOptionPane.showConfirmDialog(null, "Found " + spaceCounter + " Hemi(s), \nWould You Like to Remove Them?", "Space Invaders", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION)
                {
                    buildFile();
                    JOptionPane.showMessageDialog(null, "You're Welcome!\n Now Lets Save That New Name List ;)");
                    saveFile();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Fine! Then I'm going to the beach!!!");
                    System.exit(0);
                }
            }
            else if(e.getSource() == this.removeButton)
            {
                buildFile();
                JOptionPane.showMessageDialog(null, "OOOH, Straight to the Point!, I Like Your Style\n Now Lets Save That New Name List ;)");
                saveFile();
            }
        }

        public static void main(String [] args)
        {
            NameListEditor app = new NameListEditor();
            app.setVisible(true);
        }

    } // end of class

