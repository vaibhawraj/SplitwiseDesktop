package splitwisesdk.responses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class User {
	private String jsonText;
	
	public User(String jsonText) {
		this.jsonText = jsonText;
		parseJSON();
	}
	
	private void parseJSON() {
		// Logic to parse JSON
		JSONParser genParser = new JSONParser();
		try {
			JSONObject genJObj = (JSONObject) genParser.parse(jsonText.toString());
			
			if(genJObj.get("currencies")!=null)
			{
				currencyParse();
			}
			else if(genJObj.get("categories")!=null)
			{
				categoryParse();
			}
			else if(genJObj.get("expenses")!=null)
			{
				expensesParse();
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	private void expensesParse() {
		try {
			JSONParser Currparser = new JSONParser();
			JSONObject jobj = (JSONObject) Currparser.parse(jsonText.toString());
			JSONArray expArr=(JSONArray) jobj.get("expenses");
			
			System.out.println("actual Json response: "+jobj); // print whole response
			System.out.println("size of array: "+expArr.size()+"\n");
			
			for(int y=0;y<expArr.size();y++)
			{	
				System.out.println("Iteration "+(y+1));
				JSONObject expObj=(JSONObject) Currparser.parse(expArr.get(y).toString());
				
				/*Iterator iterator = expObj.keySet().iterator(); 
				System.out.println("All fields of expenses: \n");
				while (iterator.hasNext())
				{
				 String key = (String) iterator.next();
				 System.out.println(key + ": "+ expObj.get(key));
				} //end while
*/				
				
				// get single non nested field from array
				String date=(String) ((JSONObject) expArr.get(y)).get("date");	
				System.out.println("\n date: "+date);
												
			
			// Two arrays inside expArray: 'users' and 'repayments'.
			JSONArray repayArr=(JSONArray) ((JSONObject) expArr.get(y)).get("repayments");
			for(int j=0;j<repayArr.size();j++)
			{
				JSONObject repayObj=(JSONObject) Currparser.parse(repayArr.get(j).toString());
				System.out.println("repay obj: "+repayObj);  //print repay object
				
				//print individual values from repay object
				Iterator iterator1 = repayObj.keySet().iterator();
				System.out.println("repayments: "+(j+1));
				while (iterator1.hasNext()){
					 String key = (String) iterator1.next();
					 System.out.println(key + ": "+repayObj.get(key));
				}	
			
			} //end reparArr for
						
			JSONArray usersArr=(JSONArray) ((JSONObject) expArr.get(y)).get("users");
			for(int i=0;i<usersArr.size();i++)
			{	
				JSONObject userObj = (JSONObject) Currparser.parse(usersArr.get(i).toString());
				System.out.println(userObj);
				
				Iterator iterator2 = userObj.keySet().iterator();
				System.out.println("\n user: "+i);
				while (iterator2.hasNext())
				{
					 String key = (String) iterator2.next();
					 System.out.println(key + ": "+userObj.get(key));
				}
				
				JSONObject userDetail=(JSONObject) Currparser.parse(userObj.get("user").toString());
				Iterator iterator3 = userDetail.keySet().iterator();
				System.out.println("\n nested user: "+i);
				while (iterator3.hasNext())
				{
					 String key = (String) iterator3.next();
					 System.out.println(key + ": "+userDetail.get(key));
				}
				JSONObject picObj=(JSONObject) Currparser.parse(userDetail.get("picture").toString());
				System.out.println("\npic object: "+picObj.get("medium")); 
				
			} // end userArr for
			
			
			// other non array but nested fields
			JSONObject creByObj=(JSONObject) Currparser.parse(((JSONObject) expArr.get(y)).get("created_by").toString());
			JSONObject creByPicObj=(JSONObject) Currparser.parse(creByObj.get("picture").toString());
			JSONObject delByObj=(JSONObject) Currparser.parse(((JSONObject) expArr.get(y)).get("deleted_by").toString());
			JSONObject delByPicObj=(JSONObject) Currparser.parse(delByObj.get("picture").toString());
			JSONObject catObj=(JSONObject) Currparser.parse(((JSONObject) expArr.get(y)).get("category").toString());
			JSONObject recObj=(JSONObject) Currparser.parse(((JSONObject) expArr.get(y)).get("receipt").toString());
			
			System.out.println("\nreceipt: "+recObj); 
			
			System.out.println("ending iteration "+(y+1)+"\n");
			
		} // end expArr for
			
	} // end try 
	catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void categoryParse() {
		
		try {
			JSONParser Currparser = new JSONParser();
			JSONObject jobj = (JSONObject) Currparser.parse(jsonText.toString());  //create jobj - from API response which is stored in getCategory object
			
			System.out.println("Actual json response: "+jobj);
			JSONArray jsonarr = (JSONArray) jobj.get("categories");   //creating jsonarr from jobj
			
			System.out.println("root json array size "+ jsonarr.size()); // size of jsonarr =7 
			System.out.println("root json array: "+ jsonarr); //jsonarr here contains all json response
			int x=1;
			
			
			for(int oa=0;oa<jsonarr.size();oa++) // oa= outer array runs 7 times
			{
				JSONObject innerObj = (JSONObject) Currparser.parse(jsonarr.get(oa).toString());  //each index of jsonarr as a new innerObj
				
				//get any innerobject values here --
				
				///System.out.println("size of inner Obj: "+innerObj.size());  //each inner object inside category has 5 elements
				
				System.out.println("\nelement number " +x+ " of category (jsonarr) :  "+innerObj);
				x++;
				
				//inner object has 5  fields name,icon,id,subcatergories(array),icon_types.
						
				//System.out.println(innerObj.get("subcategories")); // print subcategory of each inner object
					
				 JSONArray subCatarr = (JSONArray) innerObj.get("subcategories");  //create sub category array
			
				
				System.out.println("\n"+innerObj.get("name") +"'s subCategory:");
				 
				for(int sc=0;sc<subCatarr.size();sc++) // runs n times for each subcategory array (subCatarr) as it has different sizes for each inner object
				{		
						System.out.println("\n"+(sc+1));
						JSONObject subCatObj = (JSONObject) Currparser.parse(subCatarr.get(sc).toString());
						 
						 //System.out.println("subcatobj :" + subCatObj);
						 						 
						 Iterator subIte = subCatObj.keySet().iterator();
							while (subIte.hasNext())
							{	
								 String key = (String) subIte.next();
								 System.out.println(key + ": "+subCatObj.get(key));
							}
						 						 
						 JSONObject iconObj=(JSONObject) Currparser.parse(subCatObj.get("icon_types").toString());
						 JSONObject slimObj=(JSONObject) Currparser.parse(iconObj.get("slim").toString());
						 System.out.println("small: "+slimObj.get("small"));
						 System.out.println("medium: "+slimObj.get("medium")+"\n");
				}					
			}
						
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void currencyParse() {
		try {
			JSONParser Currparser = new JSONParser();
			JSONObject jobj = (JSONObject) Currparser.parse(jsonText.toString());
			System.out.println("JSON object: "+jobj);
			
			JSONArray jsonarr = (JSONArray) jobj.get("currencies");
			System.out.println(" printing 1 object from JSON array"+jsonarr.get(4));
			System.out.println("size of json array is: "+jsonarr.size());
			//System.out.println("All Elements under Currencies");
			List <String> currCodeList= new ArrayList<String>();
			List <String> currUnitList= new ArrayList<String>();
			List<Object>  currObjList=new ArrayList<Object>();
 			for(int i=0;i<jsonarr.size();i++)
			{
				JSONObject jobj1 = (JSONObject)jsonarr.get(i);
				currObjList.add(jobj1);
				//System.out.println("\n unit: " +jobj1.get("unit"));
				currCodeList.add((String)jobj1.get("currency_code"));
				currUnitList.add((String)jobj1.get("unit"));
				
				//System.out.println(" currency_code: " + jobj1.get("currency_code"));	
			}
			System.out.println("currency code list: "+currCodeList);
			System.out.println("currency unit list: "+currUnitList);
			System.out.println("currency object list: "+currObjList);    //.get(2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
	
	
	public String toString() {
		return jsonText;
	}
}
