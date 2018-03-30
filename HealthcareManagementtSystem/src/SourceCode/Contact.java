/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode;

/**
 *
 * @author Mizter Lee
 */
public class Contact {
    private String person;
    private String phone;
    public Contact(String person,String phone){
        this.person=person;this.phone=phone;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPerson() {
        return person;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Contact{" + "person=" + person + ", phone=" + phone + '}';
    }
    
}
