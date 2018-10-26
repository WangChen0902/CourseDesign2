public class Room {
    public static int count = 0;
    //房间id
    public int id;
    //房型，1为普通单间，2为普通标间，3为豪华单间，4为豪华标间，5为三人间
    public int type;
    //楼层,1-5
    public int floor;
    //状态，1为空房，2为脏房，3为预定，4为住人，5为长包，6为钟点房
    public int state;
    //顾客数量，主要应用于标间与三人间,0,1,2,3
    public int cusNumber;
    //是否为合住，0为否，1为是
    public int together;

    //构造函数
    public Room(int type, int floor, int state, int cusNumber, int together) {
        count++;
        this.id = count;
        this.type = type;
        this.floor = floor;
        this.state = state;
        this.cusNumber = cusNumber;
        this.together = together;
    }

    //默认新建住房，state=1，cusNumber=0, together=0
    public Room(int type, int floor) {
        count++;
        this.id = count;
        this.type = type;
        this.floor = floor;
        this.state = 1;
        this.cusNumber = 0;
        this.together = 0;
    }

    public int getPrice() {
        int price = 0;
        switch (this.type){
            case 1:
                price = 100;
                break;
            case 2:
                if(this.together==0 || (this.together==1 && this.cusNumber==1)) {
                    price = 180;
                }
                else {
                    price = 90;
                }
                break;
            case 3:
                price = 120;
                break;
            case 4:
                if(this.together == 0 || (this.together==1 && this.cusNumber==1)) {
                    price = 220;
                }
                else {
                    price = 110;
                }
                break;
            case 5:
                if(this.together == 0 || (this.together==1 && this.cusNumber==1)) {
                    price = 240;
                }
                else if(this.together==1 && this.cusNumber==2) {
                    price = 120;
                }
                else {
                    price = 80;
                }
                break;
        }
        return price;
    }

    @Override
    public String toString(){
        String typeString = "";
        switch (this.type) {
            case 1:
                typeString = "普通单间";
                break;
            case 2:
                typeString = "普通标间";
                break;
            case 3:
                typeString = "豪华单间";
                break;
            case 4:
                typeString = "豪华标间";
                break;
            case 5:
                typeString = "三人间";
                break;
        }
        String stateString = "";
        switch (this.state) {
            case 1:
                stateString = "空房";
                break;
            case 2:
                stateString = "脏房";
                break;
            case 3:
                stateString = "预订";
                break;
            case 4:
                stateString = "住人";
                break;
            case 5:
                stateString = "长包";
                break;
            case 6:
                stateString = "钟点房";
                break;
        }
        return "房间编号：" + this.id + " 房间类型：" + typeString + " 楼层：" + this.floor + " 状态：" + stateString +
                " 顾客数量：" + this.cusNumber + " 是否为合住：" + (this.together==1?"是":"否");
    }
}
