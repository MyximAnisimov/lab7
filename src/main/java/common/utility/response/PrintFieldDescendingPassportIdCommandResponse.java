package common.utility.response;

import common.collections.Person;

import java.util.List;

public class PrintFieldDescendingPassportIdCommandResponse extends Response{
    public final List<Person> person;
    public PrintFieldDescendingPassportIdCommandResponse( List<Person> person, String error){
        super("print_field_descending_passport_id",error);
        this.person=person;

    }
    public Person getPersonList(){
        for(int i=0;i<person.size(); i++){
        return person.get(i);
    }
        return null;
}}
