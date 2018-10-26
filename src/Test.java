import java.awt.*;
import java.util.*;
import java.util.List;

public class Test {
    public static void main(String args[]) {
        //总收入
        int income = 0;
        //总订单数量
        int cusCount = 0;
        //成功入住人数
        int successCount = 0;
        //房间列表
        Room roomList[][] = new Room[5][20];
        //顾客列表
        List<Customer> cusList = new LinkedList<Customer>();
        //顾客入店列表<Room, Customer>
        Map<Room, Customer> startMap = new HashMap<Room, Customer>();
        //顾客离店列表<Room, Customer>
        Map<Room, Customer> endMap = new HashMap<Room, Customer>();
        //脏房列表[room_id]
        List<Integer> dirtyRoom = new LinkedList<Integer>();

        //初始化住房
        for(int i=0; i<5; i++) {
            for(int j=0; j<20; j++) {
                Room tempRoom;
                if(j<5) {
                    tempRoom = new Room(1, i+1);
                }
                else if(j<10) {
                    tempRoom = new Room(2, i+1);
                }
                else if(j<14) {
                    tempRoom = new Room(3, i+1);
                }
                else if(j<19) {
                    tempRoom = new Room(4, i+1);
                }
                else {
                    tempRoom = new Room(5, i+1);
                }
                roomList[i][j] = tempRoom;
            }
        }

        //初始化顾客
        for(int i=0;i<7;i++) {
            int cusRan = new Random().nextInt(80) + 20;
            for(int j=0;j<cusRan;j++) {
                double ran1 = Math.random();
                int type = 0;
                if(ran1<0.25) {
                    type = 1;
                }
                else if(ran1<0.5) {
                    type = 2;
                }
                else if(ran1<0.7) {
                    type = 3;
                }
                else if(ran1<0.95) {
                    type = 4;
                }
                else {
                    type = 5;
                }
                int floorRan = new Random().nextInt(5) + 1;
                int sexRan = new Random().nextInt(2);
                int orderDate = i;
                int startDate = i +  new Random().nextInt(3);
                double ran2 = Math.random();
                int duration = 0;
                if(ran2<0.1) {
                    duration = 0;
                }
                else if(ran2<0.6) {
                    duration = 1;
                }
                else if(ran2<0.7) {
                    duration = 2;
                }
                else if(ran2<0.8){
                    duration = 3;
                }
                else {
                    duration = new Random().nextInt(4) + 4;
                }
                int vip = new Random().nextInt(2);
                int together = new Random().nextInt(2);
                Customer tempCus = new Customer(type, floorRan, sexRan, orderDate, startDate, duration, vip, together);
                cusList.add(tempCus);
                cusCount++;
            }
        }

        System.out.println("处理前的订单：");
        for(Customer c : cusList) {
            System.out.println(c.toString());
        }

        //假定一天中事件的处理顺序为：脏房转正，需要离店的顾客最先离店，然后处理预订房间订单，最后顾客入住
        for(int nowDate=0; nowDate<7; nowDate++) {
            //脏房转正
            for(int i : dirtyRoom) {
                for(Room[] r1 : roomList) {
                    for(Room r2 : r1) {
                        if(r2.id == i) {
                            r2.state = 1;
                        }
                    }
                }
            }
            //顾客离店
            Iterator<Map.Entry<Room, Customer>> endIt = endMap.entrySet().iterator();
            while (endIt.hasNext()){
                Map.Entry<Room, Customer> e = endIt.next();
                if((e.getValue().startDate+e.getValue().duration) == nowDate) {
                    //转为脏房
                    for(Room[] r1 : roomList) {
                        for(Room r2 : r1) {
                            if(e.getKey().id == r2.id) {
                                if (r2.together == 0) {
                                    r2.state = 2;
                                    dirtyRoom.add(r2.id);
                                }
                                else {
                                    r2.cusNumber--;
                                    if(r2.cusNumber == 0) {
                                        r2.state = 2;
                                        dirtyRoom.add(r2.id);
                                    }
                                }
                                endIt.remove();
                            }
                        }
                    }
                }
            }
            //处理顾客预订
            for(Customer c : cusList) {
                if (c.orderDate == nowDate) {

                    for (Room r : roomList[c.floor - 1]) {
                        if (c.type == r.type) {
                            if (r.state == 1) {
                                r.state = 3;
                                r.cusNumber ++;
                                r.together = c.together;
                                c.id = r.id;
                                c.liveIn = 1;
                                startMap.put(r, c);
                                break;
                            }
                            if(r.state == 3) {
                                Iterator<Map.Entry<Room, Customer>> startIt = startMap.entrySet().iterator();
                                while (startIt.hasNext()) {
                                    Map.Entry<Room, Customer> e = startIt.next();
                                    if(e.getKey().id == r.id) {
                                        if(e.getValue().together==1 && e.getValue().sex==c.sex) {
                                            if(((e.getKey().type==2||e.getKey().type==4) && e.getKey().cusNumber<2)
                                            ||((e.getKey().type==5) && e.getKey().cusNumber<3)){
                                                r.cusNumber++;
                                                c.id = r.id;
                                                c.liveIn = 1;
                                                startMap.put(r, c);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            c.liveIn = 0;
                        }
                    }
                }
            }
            //顾客入店
            Iterator<Map.Entry<Room, Customer>> startIt = startMap.entrySet().iterator();
            while (startIt.hasNext()){
                Map.Entry<Room, Customer> e = startIt.next();
                if(e.getValue().startDate==nowDate) {
                    //更新待离店顾客列表，更新房间状态
                    for(Room[] r1 : roomList) {
                        for(Room r2 : r1) {
                            if(e.getKey().id == r2.id) {
                                r2.state = 4;
                                income += e.getKey().getPrice()*(e.getValue().vip==1?0.8:1);
                                endMap.put(r2, e.getValue());
                                if(e.getValue().duration == 0) {
                                    r2.state = 6;
                                    dirtyRoom.add(r2.id);
                                    endMap.remove(e.getKey());
                                }
                                if(e.getValue().duration > 1) {
                                    r2.state = 5;
                                }
                                startIt.remove();
                            }
                        }
                    }
                }
            }

            //展示今日的住房情况以及今日收入
            //成功入住的房间数
            int successRoom = 0;
            System.out.println();
            System.out.println("第" + nowDate + "日住房信息：");
            for(Room[] r1 : roomList) {
                for(Room r2 : r1) {
                    if(r2.state != 1) {
                        successRoom ++;
                    }
                    System.out.println(r2.toString());
                }
            }
            System.out.println("第" + nowDate + "日收入：" + income);
            System.out.println("第" + nowDate + "日房间利用率：" + (double)successRoom/100*100 + "%");
        }

        System.out.println();
        System.out.println("处理后的订单：");
        for(Customer c : cusList) {
            if(c.liveIn == 1) {
                successCount ++;
            }
            System.out.println(c.toString());
        }
        System.out.println("总订单数: " + cusCount);
        System.out.println("成功入住人数：" + successCount);
        System.out.println("入住率：" + (double)successCount/(double)cusCount*100 + "%");
        System.out.println("总收入: " + income);
    }

}
