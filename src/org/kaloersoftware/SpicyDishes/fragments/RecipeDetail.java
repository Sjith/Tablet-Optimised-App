/*
 * Copyright 2011 Mads Kal¿r
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.kaloersoftware.SpicyDishes.fragments;

import org.kaloersoftware.SpicyDishes.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeDetail extends Fragment {

	private int mRecipeIndex;
	
	public static RecipeDetail newInstance(int recipeIndex) {
		// Create a new fragment instance
		RecipeDetail detail = new RecipeDetail();
		// Set the recipe index
		detail.setRecipeIndex(recipeIndex);
		detail.setHasOptionsMenu(true);
        return detail;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(container == null) {
			// There is no need to create the fragment in this case. It will not be visible anyway.
			return null;
		}
		
		// Inflate the view from the xml layout resource
		View v = inflater.inflate(R.layout.recipe_details, container, false);
		// Set the different details
		((TextView) v.findViewById(R.id.recipe_title)).setText(RecipeList.RECIPE_NAMES[mRecipeIndex]);
		// This should have been a real photo, but I don't have one so we use the icon
		((ImageView) v.findViewById(R.id.recipe_photo)).setImageResource(R.drawable.icon);
		((TextView) v.findViewById(R.id.recipe_description)).setText(String.format("This recipe has index #%1$d, and it is called '%2$s'.", mRecipeIndex, RecipeList.RECIPE_NAMES[mRecipeIndex]));
		
		return v;
	}
	
	public int getRecipeIndex() {
		return mRecipeIndex;
	}
	
	public void setRecipeIndex(int index) {
		mRecipeIndex = index;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.recipe_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_add:
			// TODO: add recipe
			Toast.makeText(getActivity(), "Add recipe selected", Toast.LENGTH_LONG).show();
			return true;
		case R.id.menu_delete:
			// TODO: delete recipe
			Toast.makeText(getActivity(), "Delete recipe selected", Toast.LENGTH_LONG).show();
			return true;
		case android.R.id.home:
			// TODO: Handle app icon click
			Toast.makeText(getActivity(), "Home icon selected", Toast.LENGTH_LONG).show();
			return true;
		default:		
			return super.onOptionsItemSelected(item);
		}
	}
	
}
