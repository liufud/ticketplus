package rpc;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import entity.Item;
import entity.Item.ItemBuilder;

public class JSONHelperTest {

	@Test
	public void testGetJSONArray() throws JSONException {
		Set<String> category = new HashSet<String>();
		category.add("category one");
		ItemBuilder builderOne = new ItemBuilder();
		builderOne.setItemId("one");
		builderOne.setRating(5);
		builderOne.setCategories(category);
		Item one = builderOne.build();
		
		ItemBuilder builderTwo = new ItemBuilder();
		builderTwo.setItemId("two");
		builderTwo.setRating(5);
		builderTwo.setCategories(category);
		Item two = builderOne.build();
		
		List<Item> listItem = new ArrayList<Item>();
		listItem.add(one);
		listItem.add(two);
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(one.toJSONObject());
		jsonArray.put(two.toJSONObject());
		
		JSONAssert.assertEquals(jsonArray, JSONHelper.getJSONArray(listItem), true);
	}
	
	@Test
	public void testGetJSONArrayCornerCases() throws JSONException {
		Set<String> category = new HashSet<String>();
		category.add("category one");
		
		List<Item> listItem = new ArrayList<Item>();
		JSONArray jsonArray = new JSONArray();
		JSONAssert.assertEquals(jsonArray, JSONHelper.getJSONArray(listItem), true);

		ItemBuilder builderOne = new ItemBuilder();
		builderOne.setItemId("one");
		builderOne.setRating(5);
		builderOne.setCategories(category);
		Item one = builderOne.build();
		
		ItemBuilder builderTwo = new ItemBuilder();
		builderTwo.setItemId("two");
		builderTwo.setRating(5);
		builderTwo.setCategories(category);
		Item two = builderOne.build();
		
		listItem.add(one);
		listItem.add(two);
		
		jsonArray.put(one.toJSONObject());
		jsonArray.put(two.toJSONObject());	
		JSONAssert.assertEquals(jsonArray, JSONHelper.getJSONArray(listItem), true);
		
		Item empty = new ItemBuilder().build();
		listItem.add(empty);
		jsonArray.put(empty.toJSONObject());
		JSONAssert.assertEquals(jsonArray, JSONHelper.getJSONArray(listItem), true);
	}


}


