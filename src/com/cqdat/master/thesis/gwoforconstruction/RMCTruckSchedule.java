package com.cqdat.master.thesis.gwoforconstruction;

//Thông tin chi tiết của lần lần đỗ ~ một xe
public class RMCTruckSchedule {
    public int rmcID;       // Thứ tự lần đổ
    public Site s;          // Thông tin công trường
    public int k;           // Lần đỗ thứ k của công trình j (siteID) (k được tính từ 1)
    public int delivery;    // Sô khối Bê tông cần vận chuyển cho công trình j tại lần đỗ thứ k
    public int MD;          // Tổng số thời gian bơm cho một xe
    public int CD_RMC;      // Thời gian bơm hết bê tông vào công trường của lần đỗ thứ k với loại cấu kiện tương ứng của Site
    public int truckID;     // Mã của xe đang thực hiện đỗ

    public int SDT = 0;     // Thời điểm bắt đầu khởi hành
    public int TAC = 0;     // Thời điểm đến công trường
    public int PTF = 0;     // Thời điểm bắt đầu đỗ bê tông
    public int WC = 0;      // Thời gian chờ (WC > 0: xe đợi công trường; WC < 0: công trường đợi xe; WC = 0: lý tưởng)
    public int LT = 0;      // Thời điểm rời khỏi công trường
    public int TBB = 0;     // Thời điểm về tới trạm trộn

    public int TDG;     //Thời gian từ trạm trộn đến công trường
    public int TDB;     //Thời gian từ công trường về trạm trộn

    public String StationID_Go;     // Mã trạm trộn đi
    public String StationID_Back;   // Mã trạm trộn quay về

    public void calMD_and_CD_RMC(int timeMD){
        MD = timeMD * delivery;
        CD_RMC = delivery * s.CD;
    }

    public void calDelivery(int powerOfTruck){
        if(k < s.numOfTruck) {
            delivery = powerOfTruck;
        } else {
            if(k == s.numOfTruck) {
                delivery = s.R - (powerOfTruck * (k - 1));
            } else {
                delivery = 0;
            }
        }
    }

    public void calSDT(int value, int ltOfSite, int iTDG, int iTDB){
        TDG = iTDG;
        TDB = iTDB;
        SDT = value;
        TAC = SDT + TDG;

        //Tính PTF
        if(ltOfSite == 0) {
            PTF = s.SCT;
        } else {
            PTF = ltOfSite;
        }

        WC = PTF - TAC;

        if(WC >= 0){
            LT = TAC + WC + CD_RMC;
        } else {
            LT = TAC + CD_RMC;
        }

        TBB = LT + TDB;

    }

    @Override
    public String toString(){
        return rmcID + "\t" + s.toString() + "\tTDG:" + TDG + "\tTDB:" + TDB + "\t" + k + "\t" + delivery + "\t" + MD + "\t" + CD_RMC + "\t|\tSDT:" + String.format("%04d", SDT) + "\tTAC:" + String.format("%04d", TAC) + "\tPTF:" + String.format("%04d", PTF) + "\tWC:" + String.format("%05d", WC) + "\tLT:" + LT + "\tTBB:" + String.format("%04d", TBB) + "\tTruckID:" + truckID + "\tFrom: " + StationID_Go + " -> To: " + StationID_Back;
    }
}
