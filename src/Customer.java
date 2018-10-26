public class Customer {
    //房型，1为普通单间，2为普通标间，3为豪华单间，4为豪华标间，5为三人间
    public int type;
    //楼层，1-5
    public int floor;
    //性别，0为男，1为女
    public int sex;
    //预定日期
    public int orderDate;
    //入住日期
    public int startDate;
    //居住的时间
    public int duration;
    //是否为vip，0为否，1为是
    public int vip;
    //是否可以合住，0为否，1为是
    public int together;
    //是否成功入住，0为否，1为是
    public int liveIn;
    //房间id
    public int id;


    Customer(int type, int floor, int sex, int orderDate, int startDate, int duration, int vip, int together) {
        this.type = type;
        this.floor = floor;
        this.sex = sex;
        this.orderDate = orderDate;
        this.startDate = startDate;
        this.duration = duration;
        this.vip = vip;
        this.together = together;
        this.id = 0;
        this.liveIn = 0;
    }

    @Override
    public String toString() {
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
        return "房间类型：" + typeString + " 楼层：" + this.floor + " 性别：" + (this.sex==1?"女":"男") + " 预订日期：" +
                this.orderDate + " 入住日期：" + this.startDate + " 逗留时间：" + this.duration + " 是否为会员：" +
                (this.vip==1?"是":"否") + " 可否合住：" + (this.together==1?"是":"否") + " 是否成功入住：" +
                (this.liveIn==1?"是":"否") + " 入住房间编号：" + this.id;
    }

}
