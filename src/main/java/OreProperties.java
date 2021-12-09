public class OreProperties {

    private int[] amount;

    OreProperties(){
        amount = new int[255]; // 255 blocks high
    }

    void addOreCount(int i){
        amount[i]++;
    }

    int getAmountAt(int height){
        return amount[height];
    }

    public HighestCount[] get3HighestAmountIndex(){

        HighestCount[] biggest = new HighestCount[3];

        for(int i = 0; i < 3; i++){
            biggest[i] = new HighestCount();
        }


        for(int i = 0; i < 255; i++){

            if(biggest[0].getAmount() < amount[i]){

                biggest[2].setAmount(biggest[1].getAmount());
                biggest[2].setHeight(biggest[1].getHeight());

                biggest[1].setAmount(biggest[0].getAmount());
                biggest[1].setHeight(biggest[0].getHeight());

                biggest[0].setAmount(amount[i]);
                biggest[0].setHeight(i);

            }
            else if(biggest[1].getAmount() < amount[i]){

                biggest[2].setAmount(biggest[1].getAmount());
                biggest[2].setHeight(biggest[1].getHeight());

                biggest[1].setAmount(amount[i]);
                biggest[1].setHeight(i);

            }
            else if(biggest[2].getAmount() < amount[i]){

                biggest[2].setAmount(amount[i]);
                biggest[2].setHeight(i);

            }

        }



        return biggest;

    }



}
