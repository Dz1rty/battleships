package battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    String[][] fogField = initField();
    String[][] mainField = initField();
    List<String> ships = new ArrayList<>();

    public static void main(String[] args) {
        Main main = new Main();

        showField(main.mainField);
        main.initShips();
        main.gameStart();
    }

    public int checkField(){

        //0 - победа
        //1 - потопил
        //2 - ничего

        for (String x : this.ships){
            int startY = Integer.parseInt(x.split("\\.")[0].split("/")[0]);
            int startX = Integer.parseInt(x.split("\\.")[0].split("/")[1]);
            int length = Integer.parseInt(x.split("\\.")[1]);
            boolean gorizont = Boolean.parseBoolean(x.split("\\.")[2]);
            if(gorizont){
                for(int i = 0; i < length; i++){
                    if (this.mainField[startY][(startX + i) * 2] == "O") {
                        return 2;
                    }
                }
            }else{

            }
        }
        return 0;
    }

    public String[][] initField(){
        return new String[][]{
                {" ", " ", "1", " ", "2", " ", "3", " ", "4", " ", "5", " ", "6", " ", "7", " ", "8", " ", "9", " ", "10"},
                {"A", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~"},
                {"B", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~"},
                {"C", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~"},
                {"D", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~"},
                {"E", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~"},
                {"F", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~"},
                {"G", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~"},
                {"H", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~"},
                {"I", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~"},
                {"J", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~", " ", "~"}
        };
    }

    public void gameStart(){
        Scanner scanner = new Scanner(System.in);
        int stroka1,yacheika1;
        System.out.println();
        System.out.println("The game starts!");
        System.out.println();
        showField(this.fogField);
        System.out.println();
        System.out.println("Take a shot!");
        System.out.println();

        while (true){
            //чтение
            while (true){
                String cord01 = scanner.next();
                System.out.println();
                String[] cord1 = {cord01.substring(0,1), cord01.substring(1)};
                stroka1 = (int) cord1[0].charAt(0) - 64;
                yacheika1 = Integer.parseInt(cord1[1]);

                if(stroka1<1||stroka1>10||yacheika1>10||yacheika1<1){
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                    System.out.println();
                }else break;
            }
            //удар
            if (this.mainField[stroka1][yacheika1 * 2].equals("O")){
                this.mainField[stroka1][yacheika1*2] = "X";
                this.fogField[stroka1][yacheika1*2] = "X";
                showField(this.fogField);
                System.out.println();
                System.out.println("You hit a ship!");
                System.out.println();
                showField(this.mainField);
                //проверка
                if(checkField() == 0){
                    break;
                }
                //...
            }else if(this.mainField[stroka1][yacheika1 * 2].equals("~")) {
                this.mainField[stroka1][yacheika1*2] = "M";
                this.fogField[stroka1][yacheika1*2] = "M";
                showField(this.fogField);
                System.out.println();
                System.out.println("You missed!");
                System.out.println();
                showField(this.mainField);
            }
        }
    }

    public void newShip(int shipLength, String shipName){
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter the coordinates of the "+ shipName +" (" + shipLength + " cells):");
        while (true) {
            System.out.println();
            String cord01 = scanner.next();
            String cord02 = scanner.next();

            String[] cord1 = {cord01.substring(0,1), cord01.substring(1)};
            String[] cord2 = {cord02.substring(0,1), cord02.substring(1)};

            System.out.println();

            int stroka1 = (int) cord1[0].charAt(0) - 64;
            int yacheika1 = Integer.parseInt(cord1[1]);

            int stroka2 = (int) cord2[0].charAt(0) - 64;
            int yacheika2 = Integer.parseInt(cord2[1]);

            if(checkAndDraw(stroka1,yacheika1,stroka2,yacheika2,shipLength) == 0){
                showField(this.mainField);
                break;
            }else if (checkAndDraw(stroka1,yacheika1,stroka2,yacheika2,shipLength) == 1){
                System.out.println("Error! Wrong length of the " + shipName + "! Try again:");
            }else if(checkAndDraw(stroka1,yacheika1,stroka2,yacheika2,shipLength) == 2){
                System.out.println(2222222);
            }else if(checkAndDraw(stroka1,yacheika1,stroka2,yacheika2,shipLength) == 3){
                System.out.println("Error! Wrong ship location! Try again:");
            }else if(checkAndDraw(stroka1,yacheika1,stroka2,yacheika2,shipLength) == 4){
                System.out.println("Error! You placed it too close to another one. Try again:");
            }
        }
    }

    public int neighborCheck(int startX, int startY, boolean gorizont, int shipLength){
        List<String> neighbors = new ArrayList<>();
        this.ships.add(startY+"/"+startX+"."+shipLength+"."+gorizont);

        if(gorizont){
            neighbors.add(startX - 1 + "/" + (startY - 1));
            neighbors.add(startX - 1 + "/" + (startY));
            neighbors.add(startX - 1 + "/" + (startY + 1));

            neighbors.add(startX + shipLength + "/" + (startY - 1));
            neighbors.add(startX + shipLength + "/" + (startY));
            neighbors.add(startX + shipLength + "/" + (startY + 1));

            for(int i = 0; i < shipLength; i++){
                neighbors.add(startX + i + "/" + (startY - 1));
                neighbors.add(startX + i + "/" + (startY + 1));
            }
        }else {
            neighbors.add(startX - 1 + "/" + (startY - 1));
            neighbors.add(startX + "/" + (startY - 1));
            neighbors.add(startX + 1 + "/" + (startY - 1));

            neighbors.add(startX - 1 + "/" + (startY + shipLength));
            neighbors.add(startX + "/" + (startY + shipLength));
            neighbors.add(startX + 1 + "/" + (startY + shipLength));

            for(int i = 0; i < shipLength; i++){
                neighbors.add(startX + 1 + "/" + (startY + i));
                neighbors.add(startX - 1 + "/" + (startY + i));
            }
        }

        for(String x : neighbors){
            String[] cords = x.split("/");
            int cordX = Integer.parseInt(cords[0]);
            int cordY = Integer.parseInt(cords[1]);
            try {
                if(this.mainField[cordY][cordX * 2].equals("O")){
                    return 4;
                }
            }catch (Exception e){
            }
        }
        return 0;//значит все хорошо
    }

    public void initShips(){

        newShip(5, "Aircraft Carrier");

        newShip(4, "Battleship");

        newShip(3, "Submarine");

        newShip(3, "Cruiser");

        newShip(2, "Destroyer");

    }

    public int checkAndDraw(int stroka1, int ya1, int stroka2, int ya2, int shipLength){
        //1) поиск направления
        boolean gorizont = stroka1 == stroka2;
        int startX, startY;
        //1 - код несовпадения длины
        //2 - ячейка занята
        //3 - диагональ


        if(stroka1 != stroka2 && ya1 != ya2){
            return 3;
        }

        //2) проверка длины
        if(gorizont){
            if(Math.abs(ya1 - ya2) + 1 != shipLength){
                return 1;
            }
            //3) поиск начала
            if(ya1 > ya2){
                startX=ya2;
                startY = stroka2;
            }else{
                startX = ya1;
                startY = stroka1;
            }
        }else {
            if (Math.abs(stroka1 - stroka2) + 1 != shipLength){
                return 1;
            }
            if(stroka1 > stroka2){
                startX = ya2;
                startY = stroka2;
            }else {
                startX = ya1;
                startY = stroka1;
            }
        }
        //проверка на соседа
        if(neighborCheck(startX, startY, gorizont, shipLength) == 4){
            return 4;
        }

        //4-5) проверка на занятость + рисование
        if(gorizont){
            for(int s = startX;s < startX + shipLength;s++){
                if(this.mainField[stroka1][s*2].equals("~")){
                    this.mainField[stroka1][s*2] = "O";
                }else return 2;
            }
        }else {
            for(int s = startY;s < startY + shipLength;s++){
                if(this.mainField[s][ya1*2].equals("~")){
                    this.mainField[s][ya1*2] = "O";
                }else return 2;
            }
        }
        return 0;
    }

    public static void showField(String[][] field){
        for (int i = 0; i < field.length; i++){
            for (int j = 0; j < field[i].length; j++){
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }
}
