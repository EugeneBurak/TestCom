import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by feduchek on 27.04.16.
 */
public class ComPort {
    private String portName;
    private SerialPort serialPort;
    public ComPort(String comPortName){
        this.portName = comPortName;
    }
    public boolean initCom(){
        serialPort = new SerialPort(portName);
        try{
            serialPort.openPort();
//            serialPort.setParams(SerialPort.BAUDRATE_9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);          //ccnet
//            serialPort.setParams(SerialPort.BAUDRATE_9600,SerialPort.DATABITS_7,SerialPort.STOPBITS_1,SerialPort.PARITY_EVEN);            //mei
//            serialPort.setParams(SerialPort.BAUDRATE_9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_EVEN);          //ict
//            serialPort.setParams(SerialPort.BAUDRATE_9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);          //cctalk
//            serialPort.setParams(SerialPort.BAUDRATE_9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_ODD);          //PULOON
//            serialPort.setParams(SerialPort.BAUDRATE_9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);          //PULOON_LCDM-1000
//            serialPort.setParams(SerialPort.BAUDRATE_9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);          //Creator CRT-531
//            serialPort.setParams(SerialPort.BAUDRATE_115200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);            //comPortForPrinter.initCom(115200, 8, 1, 0); //Fujitsu Инициализация ком порта
            serialPort.setParams(SerialPort.BAUDRATE_115200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);          //eclipse FutureLogic
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
            serialPort.addEventListener(new SerialPortListener());
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    class SerialPortListener implements SerialPortEventListener {
        @Override
        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR()) {
                int eventValue = event.getEventValue();
                if (eventValue != 0) {
                    try {
                        int[] incomingData = serialPort.readIntArray();
                        System.out.println("incoming data");
                        System.out.println(Arrays.toString(incomingData));
                        try {
                            //notifyObservers(incomingData);
                        } catch (Exception e) {
                        }
                    } catch (SerialPortException e) {
                        System.out.println("error in reading int array");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public boolean closeCOM() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            serialPort.closePort();
        } catch (SerialPortException e) {
            System.out.println("exception : " + e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean statusCOM() {
        if (serialPort.isOpened()) {
            System.out.println(portName + " is opened");
            return true;
        } else {
            System.out.println(portName + " is not opened");
            return false;
        }
    }

    public boolean sendCommand(int[] command, int delay) {
        try {
            serialPort.writeIntArray(command);
        } catch (SerialPortException e) {
            //e.printStackTrace();
            return false;
        }

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("error in send cmd_sleep " + e.toString());
            return false;
        }

        return true;
    }

    public boolean write(String text)
    {
        try {
            try {
                serialPort.writeBytes(text.getBytes("ISO-8859-5"));
            }
            catch (UnsupportedEncodingException e)
            {

            }
            return true;
        } catch (SerialPortException e) {
            return false;
        }
    }

}
