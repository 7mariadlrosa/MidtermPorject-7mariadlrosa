package classes;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String address;

    //STREET

    public Address(){}

    public Address(String address) {
        setDirection(address);
    }

    public String getDirection() {return address;}
    public void setDirection(String direction) {this.address = direction;}

}
