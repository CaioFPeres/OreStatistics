public class BiomeData {

    private OreProperties[] ore;
    private int blockCount;


    public BiomeData(){

        ore = new OreProperties[8];
        this.blockCount = 0;

        for(int i = 0; i < 8; i++){
            ore[i] = new OreProperties();
        }

    }

    public OreProperties getOre(int ore) {
        return this.ore[ore];
    }

    public void addBlockCount(){
        this.blockCount++;
    }

    public int getBlockCount(){
        return blockCount;
    }

}
