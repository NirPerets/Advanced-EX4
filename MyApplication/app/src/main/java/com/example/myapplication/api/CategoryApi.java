package com.example.myapplication.api;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entities.Category;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryApi {
    WebServiceApi webServiceApi;
    Retrofit retrofit;

    public CategoryApi() {
        this.retrofit = RetrofitClient.getInstance();
        this.webServiceApi =retrofit.create(WebServiceApi.class);
    }
    public void createCategory(String token, Category category, MutableLiveData<Category> addedComment, LifecycleOwner context){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", category.getName());
        jsonObject.addProperty("promoted", category.isPromoted());
        Call<JsonObject> call = webServiceApi.createCategory(token, jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()&&response.body().get("id").getAsString()!=null) {
                    category.setId(response.body().get("id").getAsString());
                    addedComment.postValue(category);
                    Log.d("CreateCategory", "Category added successfully ID=");
                    //commentF.get_id()
                } else {
                    addedComment.postValue(null);
                    Log.d("AddComment", "Failed to add Comment");
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("AddComment", "Failed to add Comment error: " + t.getMessage());
            }
        });
    }

}

}
