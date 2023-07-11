package common.utility;

import java.io.Serializable;

public class VisibilityArgument implements Serializable {
public Visibility globalArgument=Visibility.UNLOGGED_USER;
public VisibilityArgument(Visibility globalArgument){
    this.globalArgument=globalArgument;
}
}
