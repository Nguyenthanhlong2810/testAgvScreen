package ulti;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerialPortCommunication implements HalfDuplexCommunication{

    SerialPort serialCommPort;
    @Override
    public byte[] communicate(byte[] request) {
        serialCommPort.writeBytes(request, request.length);
        InputStream f = serialCommPort.getInputStream();
        byte[] response = new byte[256];
        int n = 0;
        try {
            for (int i = 0; i < 30; i++) {
                n = f.available();
                if (n != 0) {
                    response = Arrays.copyOfRange(response, 0, f.read(response));
                    break;
                }
                Thread.sleep(100);
            }
            if (n == 0) {
                throw new IOException("RequestTimeOut");
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(SerialPortCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    @Override
    public boolean connect(int index) {
        SerialPort[] portLists = SerialPort.getCommPorts();
        serialCommPort = portLists[index];
        serialCommPort.setBaudRate(115200);
        serialCommPort.setNumDataBits(8);
        serialCommPort.setNumStopBits(1);
        serialCommPort.setParity(0);
        serialCommPort.openPort();

        if (serialCommPort.isOpen()) {
            try {
            } catch (Exception ex) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean disconnect() {
        try {
            serialCommPort.closePort();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
