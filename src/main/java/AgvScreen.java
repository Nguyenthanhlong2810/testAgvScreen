/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import com.fazecast.jSerialComm.SerialPort;
import ulti.AgvDevice;
import ulti.ModbusHandler;
import ulti.SerialPortCommunication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 *
 * @author long2
 */
public class AgvScreen extends javax.swing.JFrame {
    SerialPortCommunication comm = new SerialPortCommunication();
    SerialPort[] portLists = SerialPort.getCommPorts();
    ModbusHandler modbusHandler = new ModbusHandler();
    AgvDevice agvDevice = new AgvDevice(comm,modbusHandler);
    boolean connected = false;
    int valueProgress = 0;
    int sync_value = 0;
    boolean alarm = false;
    int cablib_type = 0;
    Color defaultColorButton = new Color(66, 138, 245);
    /**
     * Creates new form Test
     */
    public AgvScreen() {
        initComponents();
        addPropertyPanel();
        getComPort();
        initListener();
        btn_configure.setEnabled(false);
        tg_sync.setBackground(defaultColorButton);
        tg_noticeTurn.setBackground(defaultColorButton);
        btn_inline.setBackground(defaultColorButton);
        btn_outline.setBackground(defaultColorButton);
        btn_mark.setBackground(defaultColorButton);
    }

    private void getComPort() {
        for (SerialPort port : portLists) {
            cbx_port.addItem(port.getSystemPortName());
        }
        cbx_port.setSelectedIndex(-1);
    }

    private void initListener(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                comm.disconnect();
            }
        });
        btn_connect.addActionListener(l ->{
            connect();
        });
        btn_configure.addActionListener(l ->{
            configure();
        });

        tg_noticeTurn.addItemListener(l -> {
            AbstractButton abstractButton = (AbstractButton) l.getSource();
            ButtonModel buttonModel = abstractButton.getModel();
            boolean selected = buttonModel.isSelected();
            if (selected) {
                alarm = true;
                tg_noticeTurn.setText("OFF");
                tg_noticeTurn.setBackground(Color.ORANGE);
            } else {
                alarm = false;
                tg_noticeTurn.setText("ON");
                tg_noticeTurn.setBackground(defaultColorButton);
            }
        });

        tg_sync.addItemListener(l -> {
            AbstractButton abstractButton = (AbstractButton) l.getSource();
            ButtonModel buttonModel = abstractButton.getModel();
            boolean selected = buttonModel.isSelected();
            if (selected) {
                sync_value = 1;
                tg_sync.setText("OFF");
                tg_sync.setBackground(Color.ORANGE);
            } else {
                sync_value = 0;
                tg_sync.setText("ON");
                tg_sync.setBackground(defaultColorButton);
            }
        });

        btn_inline.addItemListener(l -> {
            AbstractButton abstractButton = (AbstractButton) l.getSource();
            ButtonModel buttonModel = abstractButton.getModel();
            boolean selected = buttonModel.isSelected();
            if (selected) {
                cablib_type = 1;
                btn_inline.setBackground(Color.ORANGE);
            } else {
                cablib_type = 0;
                btn_inline.setBackground(defaultColorButton);
            }
        });
        btn_outline.addItemListener(l -> {
            AbstractButton abstractButton = (AbstractButton) l.getSource();
            ButtonModel buttonModel = abstractButton.getModel();
            boolean selected = buttonModel.isSelected();
            if (selected) {
                cablib_type = 2;
                btn_outline.setBackground(Color.ORANGE);
            } else {
                cablib_type = 0;
                btn_outline.setBackground(defaultColorButton);
            }
        });
        btn_mark.addItemListener(l -> {
            AbstractButton abstractButton = (AbstractButton) l.getSource();
            ButtonModel buttonModel = abstractButton.getModel();
            boolean selected = buttonModel.isSelected();
            if (selected) {
                cablib_type = 3;
                btn_mark.setBackground(Color.ORANGE);
            } else {
                cablib_type = 0;
                btn_mark.setBackground(defaultColorButton);
            }
        });
    }



    public void configure() {
        try {
            int fSensor_value = ((PropertyPanel) pnl_fSensorProp).getValue();
            int sideSensor_value = ((PropertyPanel) pnl_sideSensorProp).getValue();
            int anchorSensor_value = ((PropertyPanel) pnl_anchorSensorProp).getValue();
            int lenDesk_value = ((PropertyPanel) pnl_lenDeskProp).getValue();
            int timeTact_value = ((PropertyPanel) pnl_timeTactProp).getValue();

            agvDevice.setOBDistanceTruoc(fSensor_value);
            agvDevice.setOBDistanceCanh(sideSensor_value);
            agvDevice.setOBDistanceCheo(anchorSensor_value);
            agvDevice.setLength(lenDesk_value);
            agvDevice.setTactTime(timeTact_value);
            agvDevice.setSync(sync_value);
            agvDevice.setAlarm(alarm);
            agvDevice.calib(cablib_type);
        }catch (IOException ex){
            JOptionPane.showMessageDialog(this,ex.getMessage());
        }
    }

    public void connect(){
        if (cbx_port.getSelectedIndex() != -1) {
            connected = comm.connect(cbx_port.getSelectedIndex());
            if (connected) {
                JOptionPane.showMessageDialog(this, "Ket noi thanh cong!");
            } else {
                JOptionPane.showMessageDialog(this, "Khong the ket noi!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "CHUA CHON CONG COM!");
        }
        btn_configure.setEnabled(connected);
    }
    private void addPropertyPanel(){
        pnl_fSensorProp = new PropertyPanel();
        pnl_fSensor.add(pnl_fSensorProp);

        pnl_sideSensorProp = new PropertyPanel();
        pnl_sideSensor.add(pnl_sideSensorProp);

        pnl_anchorSensorProp = new PropertyPanel();
        pnl_anchorSensor.add(pnl_anchorSensorProp);

        pnl_lenDeskProp = new PropertyPanel();
        pnl_lenDesk.add(pnl_lenDeskProp);

        pnl_timeTactProp = new PropertyPanel();
        pnl_timeTact.add(pnl_timeTactProp);

        revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnl_north = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lbl_port = new javax.swing.JLabel();
        cbx_port = new javax.swing.JComboBox<>();
        btn_connect = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btn_load = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        pnl_south = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();
        btn_configure = new javax.swing.JButton();
        pnl_container = new javax.swing.JPanel();
        pnl_main = new javax.swing.JPanel();
        pnl_fSensor = new javax.swing.JPanel();
        lbl_fsensor = new javax.swing.JLabel();
        pnl_sideSensor = new javax.swing.JPanel();
        lbl_sideSensor = new javax.swing.JLabel();
        pnl_anchorSensor = new javax.swing.JPanel();
        lbl_anchorSensor = new javax.swing.JLabel();
        pnl_lenDesk = new javax.swing.JPanel();
        lbl_lenDesk = new javax.swing.JLabel();
        pnl_timeTact = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnl_noticeTurn = new javax.swing.JPanel();
        lbl_noticeTurn = new javax.swing.JLabel();
        tg_noticeTurn = new javax.swing.JToggleButton();
        pnl_calib = new javax.swing.JPanel();
        lbl_calib = new javax.swing.JLabel();
        pnl_btnCalib = new javax.swing.JPanel();
        btn_outline = new javax.swing.JToggleButton();
        btn_inline = new javax.swing.JToggleButton();
        btn_mark = new javax.swing.JToggleButton();
        pnl_sync = new javax.swing.JPanel();
        lbl_sync = new javax.swing.JLabel();
        tg_sync = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnl_north.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnl_north.setPreferredSize(new java.awt.Dimension(1006, 50));
        pnl_north.setRequestFocusEnabled(false);
        pnl_north.setLayout(new java.awt.BorderLayout());

        lbl_port.setText("Port :");
        jPanel4.add(lbl_port);

        jPanel4.add(cbx_port);

        btn_connect.setText("Connect");
        jPanel4.add(btn_connect);

        pnl_north.add(jPanel4, java.awt.BorderLayout.LINE_START);

        jLabel2.setText("File");
        jPanel5.add(jLabel2);

        btn_load.setText("Load");
        jPanel5.add(btn_load);

        btn_save.setText("Save");
        jPanel5.add(btn_save);

        pnl_north.add(jPanel5, java.awt.BorderLayout.LINE_END);

        getContentPane().add(pnl_north, java.awt.BorderLayout.PAGE_START);

        pnl_south.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnl_south.setPreferredSize(new java.awt.Dimension(1006, 50));
        pnl_south.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        pnl_south.add(progressBar, gridBagConstraints);

        btn_configure.setText("Configure");
        pnl_south.add(btn_configure, new java.awt.GridBagConstraints());

        getContentPane().add(pnl_south, java.awt.BorderLayout.PAGE_END);

        pnl_container.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnl_container.setLayout(new java.awt.BorderLayout());

        pnl_main.setLayout(new java.awt.GridLayout(4, 2, 10, 10));

        pnl_fSensor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        pnl_fSensor.setLayout(new java.awt.BorderLayout());

        lbl_fsensor.setText("Cam bien truoc:");
        pnl_fSensor.add(lbl_fsensor, java.awt.BorderLayout.PAGE_START);

        pnl_main.add(pnl_fSensor);

        pnl_sideSensor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        pnl_sideSensor.setLayout(new java.awt.BorderLayout());

        lbl_sideSensor.setText("Cam bien canh:");
        pnl_sideSensor.add(lbl_sideSensor, java.awt.BorderLayout.PAGE_START);

        pnl_main.add(pnl_sideSensor);

        pnl_anchorSensor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        pnl_anchorSensor.setLayout(new java.awt.BorderLayout());

        lbl_anchorSensor.setText("Cam bien goc:");
        pnl_anchorSensor.add(lbl_anchorSensor, java.awt.BorderLayout.PAGE_START);

        pnl_main.add(pnl_anchorSensor);

        pnl_lenDesk.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        pnl_lenDesk.setLayout(new java.awt.BorderLayout());

        lbl_lenDesk.setText("Chieu dai ban lam viec");
        pnl_lenDesk.add(lbl_lenDesk, java.awt.BorderLayout.PAGE_START);

        pnl_main.add(pnl_lenDesk);

        pnl_timeTact.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        pnl_timeTact.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Thoi gian ban cong tac :");
        pnl_timeTact.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        pnl_main.add(pnl_timeTact);

        pnl_noticeTurn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        pnl_noticeTurn.setLayout(new java.awt.BorderLayout());

        lbl_noticeTurn.setText("Bao vao cua:");
        pnl_noticeTurn.add(lbl_noticeTurn, java.awt.BorderLayout.PAGE_START);

        tg_noticeTurn.setText("ON");
        tg_noticeTurn.setName(""); // NOI18N
        pnl_noticeTurn.add(tg_noticeTurn, java.awt.BorderLayout.CENTER);

        pnl_main.add(pnl_noticeTurn);

        pnl_calib.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        pnl_calib.setLayout(new java.awt.BorderLayout(10, 10));

        lbl_calib.setText("Calib:");
        pnl_calib.add(lbl_calib, java.awt.BorderLayout.PAGE_START);

        pnl_btnCalib.setLayout(new java.awt.GridLayout(1, 0, 10, 20));

        btn_outline.setText("OUTLINE");
        pnl_btnCalib.add(btn_outline);

        btn_inline.setText("INLINE");
        pnl_btnCalib.add(btn_inline);

        btn_mark.setText("MARK");
        pnl_btnCalib.add(btn_mark);

        pnl_calib.add(pnl_btnCalib, java.awt.BorderLayout.CENTER);

        pnl_main.add(pnl_calib);

        pnl_sync.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        pnl_sync.setLayout(new java.awt.BorderLayout());

        lbl_sync.setText("Dong bo:");
        pnl_sync.add(lbl_sync, java.awt.BorderLayout.PAGE_START);

        tg_sync.setText("ON");
        pnl_sync.add(tg_sync, java.awt.BorderLayout.CENTER);

        pnl_main.add(pnl_sync);

        pnl_container.add(pnl_main, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnl_container, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AgvScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgvScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgvScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgvScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AgvScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_configure;
    private javax.swing.JButton btn_connect;
    private javax.swing.JToggleButton btn_inline;
    private javax.swing.JButton btn_load;
    private javax.swing.JToggleButton btn_mark;
    private javax.swing.JToggleButton btn_outline;
    private javax.swing.JButton btn_save;
    private javax.swing.JComboBox<String> cbx_port;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lbl_anchorSensor;
    private javax.swing.JLabel lbl_calib;
    private javax.swing.JLabel lbl_fsensor;
    private javax.swing.JLabel lbl_lenDesk;
    private javax.swing.JLabel lbl_noticeTurn;
    private javax.swing.JLabel lbl_port;
    private javax.swing.JLabel lbl_sideSensor;
    private javax.swing.JLabel lbl_sync;
    private javax.swing.JPanel pnl_anchorSensor;
    private javax.swing.JPanel pnl_btnCalib;
    private javax.swing.JPanel pnl_calib;
    private javax.swing.JPanel pnl_container;
    private javax.swing.JPanel pnl_fSensor;
    private javax.swing.JPanel pnl_lenDesk;
    private javax.swing.JPanel pnl_main;
    private javax.swing.JPanel pnl_north;
    private javax.swing.JPanel pnl_noticeTurn;
    private javax.swing.JPanel pnl_sideSensor;
    private javax.swing.JPanel pnl_south;
    private javax.swing.JPanel pnl_sync;
    private javax.swing.JPanel pnl_timeTact;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JToggleButton tg_noticeTurn;
    private javax.swing.JToggleButton tg_sync;
    JPanel pnl_fSensorProp;
    JPanel pnl_sideSensorProp;
    JPanel pnl_anchorSensorProp;
    JPanel pnl_lenDeskProp;
    JPanel pnl_timeTactProp;
    // End of variables declaration//GEN-END:variables
}
