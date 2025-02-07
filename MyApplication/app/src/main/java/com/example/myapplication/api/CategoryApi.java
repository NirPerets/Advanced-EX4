package com.example.myapplication.api;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entities.Category;
import com.example.myapplication.entities.Movie;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryApi {
    WebServiceApi webServiceApi;
    Retrofit retrofit;

    public CategoryApi() {
        this.retrofit = RetrofitClient.getInstance();
        this.webServiceApi = retrofit.create(WebServiceApi.class);
    }

    public void createCategory(String token, Category category, MutableLiveData<Category> categoryMutableLiveData) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", category.getName());
        jsonObject.addProperty("promoted", category.isPromoted());

        // Prepend "Bearer " to the token (if required by your backend)
        String authToken = "Bearer " + token;

        Call<JsonObject> call = webServiceApi.createCategory(authToken, jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    // Check if the response body is not null
                    if (response.body() != null) {
                        JsonObject body = response.body();
                        if (body.has("id") && !body.get("id").isJsonNull()) {
                            String id = body.get("id").getAsString();
                            category.setId(id);
                            categoryMutableLiveData.postValue(category);
                            Log.d("CreateCategory", "Category added successfully with id: " + id);
                        } else {
                            Log.d("CreateCategory", "Response body doesn't contain 'id': " + body.toString());
                            categoryMutableLiveData.postValue(null);
                        }
                    } else {
                        // If the response body is empty, treat it as success if that's acceptable
                        Log.d("CreateCategory", "Empty response body, treating as success");
                        categoryMutableLiveData.postValue(category);
                    }
                } else {
                    Log.d("CreateCategory", "Response unsuccessful, code: " + response.code());
                    categoryMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                // If the error message indicates an empty input, treat that as success.
                if (t.getMessage() != null && t.getMessage().contains("End of input")) {
                    Log.d("CreateCategory", "Empty response detected in onFailure, treating as success");
                    categoryMutableLiveData.postValue(category);
                } else {
                    categoryMutableLiveData.postValue(null);
                    Log.e("CreateCategory", "Failed to add Category error: " + t.getMessage());
                }
            }
        });
    }

    public void deleteCategory(String token, String categoryId, MutableLiveData<Boolean> isDeleted) {
        // Prepend "Bearer " to the token (as done in other API calls)
        String authToken = "Bearer " + token;

        Call<JsonObject> call = webServiceApi.deleteCategoryById(authToken, categoryId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    isDeleted.postValue(true);
                    Log.d("deleteCategory", "Deleted category");
                } else {
                    isDeleted.postValue(false);
                    Log.d("deleteCategory", "Failed to delete category: response code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                isDeleted.postValue(false);
                Log.e("deleteCategory", "Failed to delete category: " + t.getMessage());
            }
        });
    }


    public void updateCategory(String token, String categoryId, Category category, MutableLiveData<Boolean> isUpdated) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", category.getName());
        jsonObject.addProperty("promoted", category.isPromoted());
        Call<JsonObject> call = webServiceApi.updateCategoryById(token, categoryId,jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    isUpdated.postValue(true);
                    Log.d("deleteCategory", "Deleted category");
                } else {
                    isUpdated.postValue(false);
                    Log.d("deleteCategory", "Failed to delete category");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                isUpdated.postValue(false);
                Log.e("deleteCategory", "Failed to delete category: " + t.getMessage());
            }
        });

    }

    public void getCategoryById(String token, String categoryId, MutableLiveData<Category> categoryMutableLiveData) {
        Call<Category> call = webServiceApi.getCategoryById(token, categoryId);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(@NonNull Call<Category> call, @NonNull Response<Category> response) {
                if (response.isSuccessful()) {
                    categoryMutableLiveData.postValue(response.body());
                    Log.d("getCategoryById", "get category");
                } else {
                    categoryMutableLiveData.postValue(null);
                    Log.d("getCategoryById", "Failed to ger category");
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                categoryMutableLiveData.postValue(null);
                Log.e("getCategoryById", "Failed to get category: " + t.getMessage());
            }
        });

    }

    public void getAllCategories(String token, MutableLiveData<List<Category>> categoriesLiveData) {
        webServiceApi.getAllCategories("Bearer " + token).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Category> categories = response.body();
                    Log.d("CATEGORY_API", "API Response Success: " + categories.size() + " categories.");

                    for (Category category : categories) {
                        Log.d("CATEGORY_API", "Raw API Data -> Name: " + category.getName() + " | ID: " + category.getId());
                    }

                    categoriesLiveData.postValue(categories);
                } else {
                    Log.e("CATEGORY_API", "Failed to fetch categories.");
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("CATEGORY_API", "Network Error: " + t.getMessage());
            }
        });
    }
}


