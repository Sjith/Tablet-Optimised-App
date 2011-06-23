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

import org.kaloersoftware.SpicyDishes.DetailActivity;
import org.kaloersoftware.SpicyDishes.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RecipeList extends ListFragment {
	
	public static final String[] RECIPE_NAMES = new String[] { "Turtle Soup", "Frikadeller (Danish Meatballs)", "Omelette", "Cucumber Soup", "Danish Pork Sausage", "Fried Bugs" }; 
	
	private int mCurrentSelectedItemIndex = -1;
	private boolean mIsTablet = false;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, RECIPE_NAMES));
		
		// Read the selected list item after orientation changes and similar
		if (savedInstanceState != null) {
			mCurrentSelectedItemIndex = savedInstanceState.getInt("currentListIndex", -1);
	    }
		
		// This is a tablet if this view exists
		View recipeDetails = getActivity().findViewById(R.id.recipe_details);
		mIsTablet = recipeDetails != null && recipeDetails.getVisibility() == View.VISIBLE;
		
		// If this is a tablet, an item can be selected, otherwise just clicked 
		if(mIsTablet) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		} else {
			getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
		}
		
		// If this is shown on a tablet and a list item is selected, update the recipe detail view
		if(mIsTablet && mCurrentSelectedItemIndex != -1) {
			showRecipeDetails();
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mCurrentSelectedItemIndex = position;
		showRecipeDetails();
	}
	
	private void showRecipeDetails() {
		if(mIsTablet) {
			// This is a tablet. Show the recipe in the detail fragment
			
			// Set the list item as checked
            getListView().setItemChecked(mCurrentSelectedItemIndex, true);
            
            // Get the fragment instance
            RecipeDetail details = (RecipeDetail) getFragmentManager().findFragmentById(R.id.recipe_details);
            // Is the current visible recipe the same as the clicked? If so, there is no need to update
            if (details == null || details.getRecipeIndex() != mCurrentSelectedItemIndex) {
                // Make new fragment instance to show the recipe
                details = RecipeDetail.newInstance(mCurrentSelectedItemIndex);

                // Replace the old fragment with the new one
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.recipe_details, details);
                // Use a fade animation. This makes it clear that this is not a new "layer"
                // above the current, but a replacement
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
		} else {
			// This is not a tablet - start a new activity
			Intent intent = new Intent(getActivity(), DetailActivity.class);
			// Send the recipe index to the new activity
			intent.putExtra(DetailActivity.EXTRA_RECIPE_INDEX, mCurrentSelectedItemIndex);
			startActivity(intent);
		}
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentListIndex", mCurrentSelectedItemIndex);
    }

}
