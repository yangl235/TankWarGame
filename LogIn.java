
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import music.AePlayWave;
public class LogIn extends JFrame {
    // �û���
    private JTextField username;
    // ����
    private JPasswordField password;
    static  AePlayWave apw=new AePlayWave("E:\\KGMusic\\1.wav");  
    private JLabel jl1;
 // С����
    private JLabel jl2;
    private JLabel jl3;
    private JLabel jl4;
    private JLabel jl5;
    private JLabel jl6;
 
    // С��ť
    private JButton bu1;
    private JButton bu2;
    private JButton bu3;
    private JButton bu4;
    private JButton bu5;
 
    // ��ѡ��
    private JCheckBox jc1;
    private JCheckBox jc2;
    private BufferedImage img;
    // �б��
    private JComboBox jcb;
 
    /*
     * ���췽��
     */
    public LogIn() {
        // ���ô��ڱ���
        this.setTitle("̹��ս�� ");
        this.getContentPane().setBackground(new Color(32,0,0));
        // ���������ʼ��
        init();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ���ò��ַ�ʽΪ���Զ�λ
        this.setLayout(null);
        this.setBounds(500, 400,955,955);
        // ���ô���ı���ͼ��
        Image image = new ImageIcon("").getImage();
        this.setIconImage(image);
         
        // �����ܸı��С
        this.setResizable(true);
         
        // ������ʾ
        this.setLocationRelativeTo(null);
 
        // ����ɼ�
        this.setVisible(true);
    }
 
    
    public void init() {
        // ����һ������
        Container con = this.getContentPane();
        jl1 = new JLabel();
        // ���ñ���ͼƬ
        Image image1 = new ImageIcon("").getImage();
        jl1.setIcon(new ImageIcon(image1));
        jl1.setBounds(0, 0, 1055, 1055);
         
        // ��Ϸ��¼ͷ���趨
        jl2 = new JLabel();
        Image image2 = new ImageIcon("").getImage();
        jl2.setIcon(new ImageIcon(image2));
        jl2.setBounds(40, 95, 50, 60);
 
        // �û������¼�����
        username = new JTextField();
        username.setBounds(500, 100, 350, 40);
        // �û������¼������Աߵ�����
        jl3 = new JLabel("USERNAME");
        jl3.setFont(new Font("USERNAME",Font.PLAIN,15));
        jl3.setForeground(Color.WHITE);
        jl3.setBounds(500, 60, 90, 40);
 
        // ���������
        password = new JPasswordField();
        password.setBounds(500, 190, 350, 40);
        // ����������Աߵ�����
        jl4 = new JLabel("PASSWORD");
        jl4.setFont(new Font("PASSWORD",Font.PLAIN,15));
        jl4.setForeground(Color.WHITE);
        jl4.setBounds(500, 160, 90, 40);
        /*
        jl5=new JLabel("TANK WORD!!!");
        jl5.setFont(new Font("TANK WORD!!!",Font.PLAIN,19));
        jl5.setForeground(Color.BLUE);
        jl5.setBounds(0, 160, 90, 40);
        */
        // ������·�����
        jc1 = new JCheckBox("password remember");
        jc1.setFont(new Font("password remember",Font.PLAIN,14));
        jc1.setForeground(Color.BLUE);
        jc1.setBounds(500, 240, 180, 30);
        
        jc2 = new JCheckBox("aoto login");
        jc2.setFont(new Font("auto login",Font.PLAIN,14));
        jc2.setForeground(Color.BLUE);
        jc2.setBounds(500, 280, 180, 30);
 
        // �û���¼״̬ѡ��
        jcb = new JComboBox();
        jcb.addItem("online");
        jcb.addItem("invisible");
        jcb.addItem("logout");
        jcb.setBounds(300, 100, 155, 40);
 
        // ��ť�趨
        bu1 = new JButton("login");
        bu1.setFont(new Font("login",Font.PLAIN,14));
        bu1.setForeground(Color.BLUE);
        bu1.setBounds(730, 350,120, 30);
        
        bu4 = new JButton("close music");
        bu4.setFont(new Font("close music",Font.PLAIN,18));
        bu4.setForeground(Color.BLUE);
        bu4.setBounds(300, 160,155, 40);
        // ����ť���1���¼�
        bu5 = new JButton("open music");
        bu5.setFont(new Font("open music",Font.PLAIN,18));
        bu5.setForeground(Color.BLUE);
        bu5.setBounds(300, 210,155, 40);
       
        bu1.addActionListener(new ActionListener() {
             
            @Override
            public void actionPerformed(ActionEvent e) {
                String str=e.getActionCommand();
                if("login".equals(str)){
                    String getName =username.getText();
//                  String getPwd =password.getText();
                     JOptionPane.showConfirmDialog(null, "The username you inputed is: "+getName);
                     dispose(); 
                     TankClient t=new TankClient();
                     t.setExtendedState(JFrame.MAXIMIZED_BOTH);
             		t.launchFrame();
                }
                 
            }
        });
        bu4.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		String str=e.getActionCommand();
        		if("close music".equalsIgnoreCase(str)){
        			apw.stop();
        		}
        	}
        });
        bu5.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		String str=e.getActionCommand();
        		if("open music".equalsIgnoreCase(str)){
        			apw.start();
        		}
        	}
        });
         
         
        bu2 = new JButton("mutiplayer");
        bu2.setFont(new Font("multiplayer",Font.PLAIN,14));
        bu2.setForeground(Color.BLUE);
        bu2.setBounds(500, 350, 120, 30);
        bu3 = new JButton("set");
        bu3.setBounds(620, 350, 120, 30);
        bu3.setFont(new Font("set",Font.PLAIN,14));
        bu3.setForeground(Color.BLUE);
 
        // �������������װ��
        jl1.add(jl2);
        jl1.add(jl3);
        jl1.add(jl4);
       // jl1.add(jl5);
        //jl1.add(jl6);
        jl1.add(jc1);
        jl1.add(jc2);
        jl1.add(jcb);
        jl1.add(bu1);
        jl1.add(bu2);
        jl1.add(bu3);
        jl1.add(bu4);
        jl1.add(bu5);
        con.add(jl1);
        con.add(username);
        con.add(password);
 
    }
    public static void main(String[] args) {
		LogIn LogIn_TankWar = new LogIn();
		//AePlayWave apw=new AePlayWave("E:\\KGMusic\\1.wav");  
		 // apw.start(); 

	}
}   
 
 

