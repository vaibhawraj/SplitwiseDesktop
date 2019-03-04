package com.splitwise;

import com.splitwise.core.Activity;
import com.splitwise.core.Expense;
import com.splitwise.core.ExpenseRatio;
import com.splitwise.core.Group;
import com.splitwise.core.LedgerManager;
import com.splitwise.core.People;
import com.splitwise.splitwisesdk.APIException;
import com.splitwise.splitwisesdk.SplitwiseSDK;
import com.splitwise.splitwisesdk.responses.ExpenseResponse;
import com.splitwise.splitwisesdk.responses.Friend;
import com.splitwise.splitwisesdk.responses.GroupResponse;
import com.splitwise.splitwisesdk.responses.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class SplitwiseCore {
	private static SplitwiseCore instance;
	
    private LedgerManager ledger = new LedgerManager();
    private People currentUser;
    private ArrayList<People> friends = new ArrayList<>(); // list of friends
    private ArrayList<Group> groups = new ArrayList<>(); //list of groups
    private ArrayList<Activity> activities = new ArrayList<>();
    private ArrayList<Expense> expenses = new ArrayList<>();
    
    private Callback callback;
    final private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    private long groupId;
    private long userId;
    
    public void login(){}

    public void initialize(){
    	fetchUser();
    	fetchFriends();
    	fetchGroups();
    	fetchExpenses();
    	if(callback != null) {
    		callback.callback();
    	}
    }

    public void addFriend(){

    }

    public void createGroup(){

    }
    public void removeGroup(){

    }
    public void removeFriend(){

    }
    public void updateUserInfo(){

    }
    public void getAllActivities(){

    }
    public void getAllGroups(){

    }
    public void getAllFriends(){

    }
    public People getCurrentUser() {
    	return this.currentUser;
    }
    
    public List<Expense> getExpenses() {
    	List<Expense> retList = new ArrayList<Expense>();
    	if(this.userId == 0 && this.groupId == 0) {
    		return expenses;
    	} else if(this.userId != 0){
    		LOGGER.info("Filter by User Id " + this.userId);
    		for(Expense expense : expenses) {
    			if(expense.users.contains("" + this.userId)) {
    				retList.add(expense);
    			}
    		}
    	} else if(this.groupId != 0){
    		LOGGER.info("Filter by Group Id " + this.groupId);
    		for(Expense expense : expenses) {
    			if(expense.getGroupId() == this.groupId) {
    				retList.add(expense);
    			}
    		}
    	}
    	return retList;
    }
    
    public void fetchUser() {
    	LOGGER.info("Fetching User");
    	try {
			User user = SplitwiseSDK.getInstance().getCurrentUser();
			
			currentUser = new People(user);
			LOGGER.info("Fetched User " + currentUser.getId());
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
    }
    
    public void fetchFriends() {
    	LOGGER.info("Fetching Friends");
    	try {
			for(Friend friend : SplitwiseSDK.getInstance().getFriends()) {
				currentUser.addFriend(new People(friend));
				//LOGGER.info("Fetched Friend " + friend.id);
			}
			Collections.sort(currentUser.getFriends(), (f1,f2)->f1.getName().compareTo(f2.getName()));
			LOGGER.info("Fetched Friend " + currentUser.getFriends().size());
		} catch (APIException e) {
			e.printStackTrace();
			System.exit(0);
		}
    }
    
    public void fetchGroups() {
    	LOGGER.info("Fetching Groups");
    	try {
			for(GroupResponse group : SplitwiseSDK.getInstance().getGroups()) {
				currentUser.getGroups().add(new Group(group));
				//LOGGER.info("Fetched Friend " + friend.id);
			}
			Collections.sort(currentUser.getGroups(), (f1,f2)->f1.getName().compareTo(f2.getName()));
			LOGGER.info("Fetched Groups " + currentUser.getGroups().size());
		} catch (APIException e) {
			e.printStackTrace();
			System.exit(0);
		}
    }
    
    public void fetchExpenses() {
    	LOGGER.info("Fetching Expenses");
    	try {
			for(ExpenseResponse expense : SplitwiseSDK.getInstance().getExpenses()) {
				expenses.add(new Expense(expense));
			}
			Collections.sort(expenses, (f1,f2)->(f1.getCreatedDate().compareTo(f2.getCreatedDate()) > 0)?-1:1);
			LOGGER.info("Fetched Groups " + expenses.size());
		} catch (APIException e) {
			e.printStackTrace();
			System.exit(0);
		}
    }
    
    public void setCallback(Callback callback) {
    	this.callback = callback;
    }

    public static SplitwiseCore getInstance() {
    	if(instance == null) {
    		instance = new SplitwiseCore();
    	}
    	return instance;
    }
    
    static interface Callback {
    	public void callback();
    }

	public void setFilterByFriendId(long friendId) {
		this.userId = friendId;
	}

	public void setFilterByGroupId(long groupId) {
		this.groupId = groupId;
		
	}

	
}
