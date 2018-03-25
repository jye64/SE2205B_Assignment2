package birds;

public class DataKey {
    
    private String birdName;
    private int birdSize;
    
    
    // default constructor
    public DataKey() {
	 
    } 
    
    public String getBirdName(){
        return birdName;
    }
    
    public int getBirdSize(){
        return birdSize;
    }
       
    // other constructors
    
    public DataKey(String name, int size){
        birdName = name;
        birdSize = size;
    }

    /**
    * Returns 0 if this DataKey is equal to k, returns -1 if this DataKey is smaller
    * than k, and it returns 1 otherwise. 
    */
    public int compareTo(DataKey k) {
        
        if (this.birdSize > k.birdSize){
            return 1;
        } else if (this.birdSize < k.birdSize){
            return -1;
        } else {               //determine order alphabetically
            if (this.birdName.compareTo(k.birdName)>0){
                return 1;  
            } else if (this.birdName.compareTo(k.birdName)==0)
                return 0;
            else
                return -1;
        }
                
    }
}
