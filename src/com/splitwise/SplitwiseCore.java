package com.splitwise;

import com.splitwise.core.Activity;
import com.splitwise.core.Expense;
import com.splitwise.core.ExpenseRatio;
import com.splitwise.core.Group;
import com.splitwise.core.LedgerManager;
import com.splitwise.core.People;
import com.splitwise.gui.MainFrame;
import com.splitwise.splitwisesdk.APIException;
import com.splitwise.splitwisesdk.SplitwiseSDK;
import com.splitwise.splitwisesdk.responses.ActivityResponse;
import com.splitwise.splitwisesdk.responses.ExpenseResponse;
import com.splitwise.splitwisesdk.responses.Friend;
import com.splitwise.splitwisesdk.responses.GroupResponse;
import com.splitwise.splitwisesdk.responses.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    	fetchActivities();
    	if(callback != null) {
    		callback.callback();
    		callback = null;
    	}
    }
    
    public List<Activity> getActivities(){
    	return this.activities;
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
    
    public Group getGroup() {
    	return currentUser.getGroup(groupId);
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
    		currentUser.clearFriends();
			for(Friend friend : SplitwiseSDK.getInstance().getFriends()) {
				currentUser.addFriend(new People(friend));
				//LOGGER.info("Fetched Friend " + friend.id);
			}
			Collections.sort(currentUser.getFriends(), (f1,f2)->f1.getName().toLowerCase().compareTo(f2.getName().toLowerCase()));
			LOGGER.info("Fetched Friend " + currentUser.getFriends().size());
		} catch (APIException e) {
			e.printStackTrace();
			System.exit(0);
		}
    }
    
    public void fetchGroups() {
    	LOGGER.info("Fetching Groups");
    	try {
    		currentUser.getGroups().clear();
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
    		expenses.clear();
			for(ExpenseResponse expense : SplitwiseSDK.getInstance().getExpenses()) {
				if(!expense.isDeleted)
					expenses.add(new Expense(expense));
			}
			Collections.sort(expenses, (f1,f2)->(f1.getCreatedDate().compareTo(f2.getCreatedDate()) > 0)?-1:1);
			
			LOGGER.info("Fetched Expenses " + expenses.size());
		} catch (APIException e) {
			e.printStackTrace();
			System.exit(0);
		}
    }
    
    public void fetchActivities() {
    	LOGGER.info("Fetching Activities");
    	try {
    		activities.clear();
			for(ActivityResponse activity : SplitwiseSDK.getInstance().getActivities()) {
				activities.add(new Activity(activity));
			}
			Collections.sort(activities, (f1,f2)->(f1.getCreatedDate().compareTo(f2.getCreatedDate()) > 0)?-1:1);
			LOGGER.info("Fetched Activities " + activities.size());
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
    
    public static interface Callback {
    	public void callback();
    }

	public void setFilterByFriendId(long friendId) {
		this.userId = friendId;
	}

	public void setFilterByGroupId(long groupId) {
		this.groupId = groupId;
		
	}

	public void createSplitExpense(String cost, String description, long userIds[]) {
		createSplitExpense( cost, description, userIds, -1); //-1 to indicate that group id parameter has not to be included
	}
	public void createSplitExpense(String cost, String description, long userIds[], long groupId) {
		HashMap<String,String> expenseParams = new HashMap<String, String>();
		expenseParams.put("cost",cost);
		expenseParams.put("description",description);
		if(groupId != -1) {
			expenseParams.put("group_id",String.valueOf(groupId));
		}
		float value = Float.parseFloat(cost);
		float splitValue = value / userIds.length;
		splitValue = Math.round(splitValue * 100.0f)/100.0f;
		float remainingValue = value;
		// Users
		for(int i=0;i<userIds.length-1;i++) {
			long id = userIds[i];
			float paid_share = 0;
			float owed_share = splitValue;
			if(id == this.currentUser.getId()) {
				paid_share = value;
			}
			expenseParams.put("users__"+i+"__user_id",String.valueOf(id));
			expenseParams.put("users__"+i+"__paid_share",String.valueOf(paid_share));
			expenseParams.put("users__"+i+"__owed_share",String.valueOf(owed_share));
			remainingValue -= splitValue;
		}
		// For last user
		int i = userIds.length - 1;
		float paid_share = 0;
		if(userIds[i] == this.currentUser.getId()) {
			paid_share = value;
		}
		expenseParams.put("users__"+i+"__user_id",String.valueOf(userIds[i]));
		expenseParams.put("users__"+i+"__paid_share",String.valueOf(paid_share));
		expenseParams.put("users__"+i+"__owed_share",String.valueOf(remainingValue));
		
		for(String key : expenseParams.keySet()) {
			LOGGER.info(key + " : " + expenseParams.get(key));
		}
		
		new Thread(){
			public void run() {
				try {
					ExpenseResponse er = SplitwiseSDK.getInstance().createExpense(expenseParams);
					LOGGER.info("Created expense " + er.id);
					expenses.add(new Expense(er));
					fetchFriends();
					fetchActivities();
					fetchExpenses();
					fetchGroups();
					if(callback != null) {
						callback.callback();
					}
				} catch (APIException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void createFriend(Map<String, String> args) {
		for(String key : args.keySet()) {
			LOGGER.info(key + " : " + args.get(key));
		}
		
		
			new Thread(){
				public void run() {
					try {
						Friend friend = SplitwiseSDK.getInstance().createFriend(args);
						LOGGER.info("Created friend " + friend.id + " " + friend.first_name);
						SplitwiseCore.getInstance().getCurrentUser().addFriend(new People(friend));
						//Update Entire list of friends
						fetchFriends();
						fetchActivities();
						MainFrame.getInstance().reInitLeftPanel();
						MainFrame.getInstance().repaint();
					} catch (APIException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
		
	}
	
	public void createGroup(Map<String, String> args) {
		args.put("simplify_by_default","true");
		for(String key : args.keySet()) {
			LOGGER.info(key + " : " + args.get(key));
		}
		
		
			new Thread(){
				public void run() {
					try {
						GroupResponse groupResponse = SplitwiseSDK.getInstance().createGroup(args);
						LOGGER.info("Created Group " + groupResponse.id + " " + groupResponse.name);
						
						//Update Entire list of Groups
						fetchGroups();
						fetchActivities();
						MainFrame.getInstance().reInitLeftPanel();
						MainFrame.getInstance().repaint();
					} catch (APIException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
		
	}

	
}
