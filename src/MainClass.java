/**
 * Created by feduchek on 27.04.16.
 */
public class MainClass {
    /*
    EclipseCommand
    UNDERLINE(27, 45),          //подчеркнутый - (EclipseCommand.UNDERLINE, arrby) - arrby[0] = bl ? 1 : 0;
    INIT(27, 64),
    CUT(27, 91, 99, 102),
    GET_STATUS(5),
    SET_FONT(27, 91, 70),           //уст. шрифт - (EclipseCommand.SET_FONT, (byte)n)
    BOLD(27, 69),           //жирный шрифт
    CANCEL_BOLD(27, 70),                //отменить жирный шрифт
    CODEPAGE(27, 116),          //(EclipseCommand.CODEPAGE, (byte)n)
    LINE_FEED(13, 10),
    BARCODE(27, 91, 66, 93),            //не исп
    PAGE_MODE(27, 91, 94, 93, 27),
    LINE_MODE(94, 106, 124, 94),
    SET_UNIT_OF_MEASURE(27, 40, 85, 1, 0),          //не исп
    SET_CONDENSED(15);
*/
    private static final int[] PAGE_MODE = new int[] { 0x1b, 0x5b, 0x5e, 0x5d, 0x1b };          //PAGE_MODE(27, 91, 94, 93, 27),
    private static final int[] LINE_MODE = new int[] { 0x5e, 0x6a, 0x7c, 0x5e };          //LINE_MODE(94, 106, 124, 94),
    private static final int[] INIT = new int[] { 0x1b, 0x40 };         //INIT(27, 64),
    private static final int[] SET_CONDENSED = new int[] { 0xf };           //SET_CONDENSED(15);
    private static final int[] LINE_FEED = new int[] { 0xd, 0xa };
    private static final int[] STATUS = new int[] { 0x5 };
    private static final int[] CUT = new int[] { 0x1b, 0x5b, 0x63, 0x66 };

    private static final int[] BOLD = new int[] { 0x1b, 0x45 };          //BOLD(27, 69),           //жирный шрифт
    private static final int[] CANCEL_BOLD = new int[] { 0x1b, 0x46 };          //CANCEL_BOLD(27, 70),                //отменить жирный шрифт

//    private static final int[] STATUS_FUJITSU = new int[] { 0x18 };         //Fujitsu
//    private static final int[] realTimeStatusTransmission_1 = new int[] { 0x10, 0x04, 20 };   //Custom

    public static void main(String[] args) {
        try{
            ComPort comPort = new ComPort("/dev/ttyS0");
            comPort.initCom();

            System.out.println(" ----- ");
//
            comPort.sendCommand(STATUS, 0);

            System.out.println(" Init ");
            comPort.sendCommand(PAGE_MODE, 10);
            comPort.sendCommand(LINE_MODE, 10);
            comPort.sendCommand(INIT, 10);
            comPort.sendCommand(SET_CONDENSED, 10);

            System.out.println(" Status ");
            comPort.sendCommand(STATUS, 10);

            System.out.println(" Line feed ");
            comPort.sendCommand(LINE_FEED, 10); //Перебросить каретку на новую строку
            comPort.sendCommand(LINE_FEED, 10); //Перебросить каретку на новую строку
            comPort.write(" 123456789abcdef-----------------fedcba987654321");
            comPort.sendCommand(LINE_FEED, 10); //Перебросить каретку на новую строку
            comPort.write(" 123456789abcdef-----------------fedcba987654321");
            comPort.sendCommand(LINE_FEED, 10); //Перебросить каретку на новую строку
            comPort.write(" qwertyuiop[] asdfghjkl; zxcvbnm,./");
            comPort.sendCommand(LINE_FEED, 10); //Перебросить каретку на новую строку
            comPort.write(" QWERTYUIOP[] ASDFGHJKL; ZXCVBNM,./");
            comPort.sendCommand(LINE_FEED, 10); //Перебросить каретку на новую строку
            comPort.write(" йцукенгшщзхъфывапролджэячсмитьбю.");
            comPort.sendCommand(LINE_FEED, 10); //Перебросить каретку на новую строку
            comPort.write(" ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ. ,-+=");
            comPort.sendCommand(LINE_FEED, 10); //Перебросить каретку на новую строку
            comPort.sendCommand(BOLD, 10);
            comPort.write(" ЖИРНЫЙ ");
            comPort.sendCommand(LINE_FEED, 10); //Перебросить каретку на новую строку
            comPort.sendCommand(CANCEL_BOLD, 10);
            comPort.write(" NOT ЖИРНЫЙ ");
            comPort.sendCommand(LINE_FEED, 10); //Перебросить каретку на новую строку
            comPort.write(" йцукенгшщзхъфывапролджэячсмитьбю.");
            comPort.sendCommand(LINE_FEED, 10); //Перебросить каретку на новую строку
            comPort.sendCommand(LINE_FEED, 10); //Перебросить каретку на новую строку
            comPort.sendCommand(LINE_FEED, 10); //Перебросить каретку на новую строку
            comPort.sendCommand(LINE_FEED, 10); //Перебросить каретку на новую строку
            System.out.println(" Cut ");
            comPort.sendCommand(CUT, 10); //Обрезать

            System.out.println(" ----- ");
            System.out.println("OK");

            System.out.println(" Status ");
            comPort.sendCommand(STATUS, 10);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Ooops");
        }
    }

     static void sendCommand(byte[] eclipseCommand, byte ... arrby) {
         try {
             byte[] arrby2 = eclipseCommand;
             byte[] arrby3 = new byte[arrby.length + arrby2.length];
             System.arraycopy(arrby2, 0, arrby3, 0, arrby2.length);
             System.arraycopy(arrby, 0, arrby3, arrby2.length, arrby.length);
             for (int i = 0; i < arrby3.length; i++) {
                 System.out.println(arrby3[i]);
             }
         }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
