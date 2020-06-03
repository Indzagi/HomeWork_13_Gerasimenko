/**
 * b. Сервисный центр. Состоит из поля (Колонка для масла, Колонка для
 * топлива) и методов (залить топливо, заменить масло в двигателе)
 * 4. В сервисном центре может быть разное количество колонок с разным
 * * показателем количества (от 0 до 100)
 * 5. Сервисный центр может заправлять топливо или менять масло если
 * * количество ненулевое. Для этого в качестве параметра надо передать
 * * объект-автомобиль и количество требуемого топлива или масла
 */

public class ServiceCenter {
    public int[] electroColumn = new int[2];
    public int[] benzineColumn = new int[3];
    public int[] dieselColumn = new int[2];
    public int[] gasColumn = new int[1];
    public int[] oilColumn = new int[2];


    public int[] refuel(Car car) {

        String enginesType = car.getEnginesType();

        if (enginesType.equals(Application.enginesType[0])) {

            return electroColumn;

        } else if (enginesType.equals(Application.enginesType[1])) {

            return benzineColumn;

        } else if (enginesType.equals(Application.enginesType[2])) {

            return dieselColumn;

        } else if (enginesType.equals(Application.enginesType[3])) {

            return gasColumn;
        }
        return null;
    }

    public int[] addOilToTheEngine(Car car) {

        return oilColumn;
    }

}
