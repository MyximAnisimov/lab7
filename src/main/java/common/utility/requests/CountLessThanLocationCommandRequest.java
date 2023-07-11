package common.utility.requests;

public class CountLessThanLocationCommandRequest extends Request{
    public String locationName;
    public CountLessThanLocationCommandRequest(String locationName){
        super("count_less_than_location");
        this.locationName=locationName;
    }
}
