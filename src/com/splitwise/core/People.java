package com.splitwise.core;

import java.util.ArrayList;
import java.util.Date;

import com.splitwise.splitwisesdk.responses.Friend;
import com.splitwise.splitwisesdk.responses.User;

public class People {
    protected String first_name;
    protected String last_name;
    protected String name;
    protected long id;
    private Float balance_amount;
    private Float userBalance;
    private ArrayList<People> friends = new ArrayList<>();
    protected ArrayList<Group> groups = new ArrayList<>();
    private Date updated_at;

    public People(User user) {
		this.name = user.first_name + ((user.last_name!= null && user.last_name.length() > 0)?(" " + user.last_name):"");
		this.first_name = user.first_name;
		this.last_name = user.last_name;
		this.id = user.id;
	}
    
    public People(Friend friend) {
    	this.name = friend.first_name + ((friend.last_name!= null && friend.last_name.length() > 0)?(" " + friend.last_name):"");
    	this.first_name = friend.first_name;
		this.last_name = friend.last_name;
		this.id = friend.id;
		this.updated_at = friend.updated_at;
    }

	public ArrayList<Group> getGroups() {
        return groups;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    //friends balance getter/ setter
    public Float getBalance_amount() {
        return balance_amount;
    }

    public void setBalance_amount(Float balance_amount) {
        this.balance_amount = balance_amount;
    }

    //user balance getter/setter
    public Float getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(Float userBalance) {
        this.userBalance = userBalance;
    }

    public ArrayList<People> getFriends() {
        return friends;
    }

    public People getFriend(long id) {
    	if(id == this.id) {
    		return this;
    	}
        for(People people : friends) {
        	if(people.id == id) {
        		return people;
        	}
        }
    	return null;
    }
    
    public Date getUpdatedAt() {
    	return this.updated_at;
    }

	public String getFirstName() {
		return this.first_name;
	}

	public Group getGroup(long groupId) {
        for(Group group : groups) {
        	if(group.getId() == groupId) {
        		return group;
        	}
        }
    	return null;
	}
}

