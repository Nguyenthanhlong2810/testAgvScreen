import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

public class PropertyPanel extends JPanel {
    private int value;

    PropertyPanel(){
        initComponents();
        initListener();
        value = Integer.parseInt(txt_value.getText());
    }

    private void initComponents(){
        setLayout(new BorderLayout());
        btn_add = new JButton("+");
        this.add(btn_add,BorderLayout.WEST);
        btn_minus = new JButton("-");
        this.add(btn_minus,BorderLayout.EAST);
        txt_value = new JTextField("0");
        txt_value.setHorizontalAlignment(JTextField.CENTER);
        this.add(txt_value,BorderLayout.CENTER);
    }
    private void initListener(){
        btn_add.addActionListener(l -> {
            addValue();
            revalidate();
        });
        btn_minus.addActionListener(l -> {
            minusValue();
            revalidate();
        });
    }

    private void minusValue() {
        if (validateValue()) {
            value = Integer.parseInt(txt_value.getText());
            value--;
            txt_value.setText(String.valueOf(value));
        }
    }
    private void addValue() {
        if(validateValue()) {
            value = Integer.parseInt(txt_value.getText());
            value++;
            txt_value.setText(String.valueOf(value));
        }
    }

    private boolean validateValue(){
        boolean isOK;
        try {
            value = Integer.parseInt(txt_value.getText());
            isOK = true;
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Gia tri nhap vao phai la so va lon hon 0");
            isOK = false;
        }
        return isOK;
    }

    public int getValue(){
        if (validateValue()){
            return value;
        }
        return 0;
    }

    private JButton btn_add;
    private JButton btn_minus;
    private JTextField txt_value;
}
